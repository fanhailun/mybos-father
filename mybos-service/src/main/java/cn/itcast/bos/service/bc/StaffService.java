package cn.itcast.bos.service.bc;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.bc.Staff;

public interface StaffService {

	public Staff findByPhoneAjax(String telephone);

	public void save(Staff model);

	public Page<Staff> pagequery(PageRequest pagerequest);

	public void updateDeltag(String idsString);

	public Page<Staff> pagequery(PageRequest pagerequest,
			Specification<Staff> spec);

	public List<Staff> findNameByAjax();

	public Staff importStaffName(String sid);

}
