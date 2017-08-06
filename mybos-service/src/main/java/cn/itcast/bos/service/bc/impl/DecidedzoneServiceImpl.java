package cn.itcast.bos.service.bc.impl;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.crm.domain.Customer;
import cn.itcast.bos.dao.bc.DecidedzoneDao;
import cn.itcast.bos.dao.bc.SubareaDao;
import cn.itcast.bos.domain.bc.DecidedZone;
import cn.itcast.bos.service.base.BaseInterface;
import cn.itcast.bos.service.bc.DecidedzoneService;

@SuppressWarnings("all")
@Service
@Transactional
public class DecidedzoneServiceImpl implements DecidedzoneService {

	@Autowired
	private DecidedzoneDao decidedzoneDao;

	@Autowired
	private SubareaDao subareaDao;

	public void save(String[] sid, DecidedZone model) {
		decidedzoneDao.save(model);
		if (sid != null && sid.length != 0) {
			for (String id : sid) {
				subareaDao.updateByDecidedzone(id, model);
			}
		}
	}

	@Override
	public Page<DecidedZone> pagination(PageRequest pagerequest) {

		Page<DecidedZone> all = decidedzoneDao.findAll(pagerequest);
		List<DecidedZone> list = all.getContent();
		for (DecidedZone decidedZone : list) {
			Hibernate.initialize(decidedZone.getStaff());
		}
		return all;
	}

	@Override
	public Page<DecidedZone> pagination(PageRequest pagerequest,
			Specification<DecidedZone> spec) {

		Page<DecidedZone> all = decidedzoneDao.findAll(spec, pagerequest);
		List<DecidedZone> list = all.getContent();
		for (DecidedZone decidedZone : list) {
			Hibernate.initialize(decidedZone.getStaff());
		}
		return all;
	}

	@Override
	public DecidedZone findById(String id) {

		return decidedzoneDao.findById(id);
	}

	@Override
	public List<Customer> findAssociationCustomers(String id) {
		String url = BaseInterface.CRM_BASE_URL + "association/" + id;
		List<Customer> list = (List<Customer>) WebClient.create(url)
				.accept(MediaType.APPLICATION_JSON)
				.getCollection(Customer.class);
		return list;
	}

	@Override
	public List<Customer> findNoAssociationCustomers() {
		String url = BaseInterface.CRM_BASE_URL + "noassociation";
		List<Customer> list = (List<Customer>) WebClient.create(url)
				.accept(MediaType.APPLICATION_JSON)
				.getCollection(Customer.class);
		return list;
	}

	@Override
	public void assigncustomerstodecidedzone(String id, String[] customerids) {
		String url = BaseInterface.CRM_BASE_URL + "assignedCustomer/" + id;
		if (customerids != null && customerids.length != 0) {
			StringBuffer sb = new StringBuffer();
			for (String cid : customerids) {
				sb.append(cid).append(",");
			}
			String s = sb.substring(0, sb.length() - 1);
			url = url + "/" + s;
		} else {
			// 处理关联客户栏没有客户
			url = url + "/tps";
		}
		WebClient.create(url).put(null);
	}
}
