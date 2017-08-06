package cn.itcast.bos.service.bc;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.crm.domain.Customer;
import cn.itcast.bos.domain.bc.DecidedZone;

public interface DecidedzoneService {

	public void save(String[] sid, DecidedZone model);

	public Page<DecidedZone> pagination(PageRequest pagerequest);

	public Page<DecidedZone> pagination(PageRequest pagerequest,
			Specification<DecidedZone> spec);

	public DecidedZone findById(String id);

	public List<Customer> findAssociationCustomers(String id);

	public List<Customer> findNoAssociationCustomers();

	public void assigncustomerstodecidedzone(String id, String[] customerids);

}
