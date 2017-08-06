package cn.itcast.bos.service.bc.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.itcast.bos.dao.bc.StandardDao;
import cn.itcast.bos.domain.bc.Standard;
import cn.itcast.bos.service.bc.StandardService;

@Service
@Transactional
public class StandardServiceImpl implements StandardService {

	@Autowired
	private StandardDao standardDao;

	public void save(Standard model) {
		standardDao.save(model);
	}

	@Override
	public Page<Standard> pageQuery(PageRequest pagerequest) {
		Page<Standard> all = standardDao.findAll(pagerequest);
		return all;
	}

	@Override
	public void updatedeltag(String ids) {
		if (!StringUtils.isEmpty(ids)) {
			String[] arr = ids.split(",");
			for (int i = 0; i < arr.length; i++) {
				standardDao.delBatch(Integer.parseInt(arr[i]));
			}
		}

	}

	@Override
	public List<Standard> findByDeltag() {
		return standardDao.findByajax();
	}

}
