package cn.itcast.bos.service.bc.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.bc.StaffDao;
import cn.itcast.bos.domain.bc.Staff;
import cn.itcast.bos.service.bc.StaffService;

@Service
@Transactional
public class StaffServiceImpl implements StaffService {

	@Autowired
	private StaffDao staffDao;

	@Override
	public Staff findByPhoneAjax(String telephone) {

		return staffDao.findByPhoneAjax(telephone);
	}

	@Override
	public void save(Staff model) {
		staffDao.save(model);
	}

	@Override
	public Page<Staff> pagequery(PageRequest pagerequest) {

		return staffDao.findAll(pagerequest);
	}

	@Override
	public void updateDeltag(String ids) {
		if (StringUtils.isNotBlank(ids)) {
			String[] arr = ids.split(",");
			for (int i = 0; i < arr.length; i++) {
				staffDao.updateDeltag(arr[i]);
			}
		}

	}

	@Override
	public Page<Staff> pagequery(PageRequest pagerequest,
			Specification<Staff> spec) {
		return staffDao.findAll(spec, pagerequest);
	}

	@Override
	public List<Staff> findNameByAjax() {
		return staffDao.findNameByAjax();
	}

	@Override
	public Staff importStaffName(String sid) {
		return staffDao.importStaffName(sid);
	}

}
