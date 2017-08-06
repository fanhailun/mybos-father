package cn.itcast.bos.service.qp;

import cn.itcast.bos.crm.domain.Customer;
import cn.itcast.bos.domain.qp.NoticeBill;

public interface NoticebillService {

	Customer findCustomerByTelephone(String telephone);

	void save(NoticeBill model, String province, String city, String district);

}
