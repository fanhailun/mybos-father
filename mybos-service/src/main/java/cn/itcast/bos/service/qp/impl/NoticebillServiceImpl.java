package cn.itcast.bos.service.qp.impl;

import java.sql.Date;
import java.util.Set;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.crm.domain.Customer;
import cn.itcast.bos.dao.bc.DecidedzoneDao;
import cn.itcast.bos.dao.bc.RegionDao;
import cn.itcast.bos.dao.qp.NoticebillDao;
import cn.itcast.bos.dao.qp.WorkBillDao;
import cn.itcast.bos.domain.bc.DecidedZone;
import cn.itcast.bos.domain.bc.Region;
import cn.itcast.bos.domain.bc.Staff;
import cn.itcast.bos.domain.bc.Subarea;
import cn.itcast.bos.domain.qp.NoticeBill;
import cn.itcast.bos.domain.qp.WorkBill;
import cn.itcast.bos.service.base.BaseInterface;
import cn.itcast.bos.service.qp.NoticebillService;

@SuppressWarnings("all")
@Service
@Transactional
public class NoticebillServiceImpl implements NoticebillService {
	@Autowired
	private NoticebillDao noticebillDao;

	@Autowired
	private WorkBillDao workBillDao;

	@Autowired
	private DecidedzoneDao decidedzoneDao;

	@Autowired
	private RegionDao regionDao;

	@Autowired
	@Qualifier("jmsQueueTemplate")
	private JmsTemplate jmsTemplate;

	@Override
	public Customer findCustomerByTelephone(String telephone) {
		String url = BaseInterface.CRM_BASE_URL + "findCustomerByTelephone/"
				+ telephone;
		Customer c = WebClient.create(url).accept(MediaType.APPLICATION_JSON)
				.get(Customer.class);
		return c;
	}

	@Override
	public void save(final NoticeBill model, String province, String city,
			String district) {
		// 3: 业务层 3.1 业务通知单录入 3.2 自动分单 (地址库完全匹配/管理分区匹配法) 3.3 客户新客户 crm(插入)录入
		// 3.4 老客户地址更新
		// 业务通知单录入
		boolean flag = false;// 控制老客户地址是否更新
		final String address = model.getPickaddress();
		model.setPickaddress(province + city + district
				+ model.getPickaddress());
		noticebillDao.saveAndFlush(model);// 让model从瞬时态-->持久态
		System.out.println(model.getId());
		// 1.地址库完全匹配
		String url = BaseInterface.CRM_BASE_URL + "findCustomerByAddress/"
				+ model.getPickaddress();
		Customer customer = WebClient.create(url)
				.accept(MediaType.APPLICATION_JSON).get(Customer.class);
		if (customer != null) {
			String decidedzoneId = customer.getDecidedzoneId();
			if (StringUtils.isNotBlank(decidedzoneId)) {
				// 通过定区id查询到关联的取派员id
				DecidedZone decidedzone = decidedzoneDao.findOne(decidedzoneId);
				final Staff staff = decidedzone.getStaff();
				model.setStaff(staff);
				model.setOrdertype("自动");
				// 自动分单已经生成,生成一张工单
				generateWorkBill(model, staff);
				System.out.println("规则1");
				// 发送短信mq
				jmsTemplate.send("bos_staff", new MessageCreator() {
					@Override
					public Message createMessage(Session session)
							throws JMSException {
						MapMessage mapMessage = session.createMapMessage();
						mapMessage.setString("stafftelephone",
								staff.getTelephone());
						mapMessage.setString("customername",
								model.getCustomerName());
						mapMessage.setString("customeraddress", address);
						mapMessage.setString("customertelephone",
								model.getTelephone());
						return mapMessage;
					}
				});
				flag = true;
				crmCustomer(model, flag);// 地址库完全匹配,不需要更新crm的客户的地址
				return;
			}

		}
		// 自动分单,规则2:通过管理分区匹配
		Region region = regionDao.findRegionByProvinceAndCityAndDistrict(
				province, city, district);
		Set<Subarea> subareas = region.getSubareas();
		if (subareas != null && subareas.size() != 0) {

			for (Subarea subarea : subareas) {
				if (model.getPickaddress().contains(subarea.getAddresskey())) {
					DecidedZone zone = subarea.getDecidedZone();
					if (zone != null) {
						final Staff staff = zone.getStaff();
						model.setStaff(staff);
						model.setOrdertype("自动");
						generateWorkBill(model, staff);
						System.out.println("管理分区匹配法");
						// 发送短信mq
						jmsTemplate.send("bos_staff", new MessageCreator() {
							@Override
							public Message createMessage(Session session)
									throws JMSException {
								MapMessage mapMessage = session
										.createMapMessage();
								mapMessage.setString("stafftelephone",
										staff.getTelephone());
								mapMessage.setString("customername",
										model.getCustomerName());
								mapMessage
										.setString("customeraddress", address);
								mapMessage.setString("customertelephone",
										model.getTelephone());
								return mapMessage;
							}
						});
						flag = false;
						crmCustomer(model, flag);// 需要更新地址
						return;
					}
				}
			}
		}
		// 自动分单失败
		crmCustomer(model, flag);
		model.setOrdertype("人工");

	}

	private void generateWorkBill(final NoticeBill model, final Staff staff) {
		WorkBill bill = new WorkBill();
		bill.setAttachbilltimes(0);
		bill.setBuildtime(new Date(System.currentTimeMillis()));
		bill.setNoticeBill(model);
		bill.setType("新");
		bill.setStaff(staff);
		bill.setRemark(model.getRemark());
		bill.setPickstate("新单");
		workBillDao.save(bill);
	}

	private void crmCustomer(final NoticeBill model, boolean flag) {
		// 1.判断客户是否为新客户
		if (!("null".equalsIgnoreCase(String.valueOf(model.getCustomerId())))) {
			if (!flag) {
				// 老客户,不管地址改变,更新,定区id置空
				String url = BaseInterface.CRM_BASE_URL + "updateAddressById/"
						+ model.getPickaddress() + "/" + model.getCustomerId();
				WebClient.create(url).put(null);
			}

		} else {
			// 新客户,crm录入
			String url = BaseInterface.CRM_BASE_URL + "save";
			Customer c = new Customer();
			c.setAddress(model.getPickaddress());
			c.setDecidedzoneId(null);
			c.setName(model.getCustomerName());
			c.setTelephone(model.getTelephone());
			c.setStation("上海传智校区");
			Response post = WebClient.create(url)
					.accept(MediaType.APPLICATION_JSON).post(c);
			// 响应体,获得实体模型
			Customer entity = post.readEntity(Customer.class);
			model.setCustomerId(entity.getId());
		}
	}

}
