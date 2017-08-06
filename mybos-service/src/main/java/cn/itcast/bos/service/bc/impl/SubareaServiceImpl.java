package cn.itcast.bos.service.bc.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.bc.SubareaDao;
import cn.itcast.bos.domain.bc.Subarea;
import cn.itcast.bos.service.bc.SubareaService;

@Service
@Transactional
public class SubareaServiceImpl implements SubareaService {

	@Autowired
	private SubareaDao subareaDao;

	public void save(Subarea model) {
		subareaDao.save(model);
	}

	@Override
	public Page<Subarea> querypage(PageRequest pagerequest) {

		Page<Subarea> all = subareaDao.findAll(pagerequest);
		// 立即加载
		List<Subarea> list = all.getContent();
		for (Subarea subarea : list) {
			// 初始化代理对象
			Hibernate.initialize(subarea.getRegion());
		}
		return all;

	}

	@Override
	public Page<Subarea> querypage(PageRequest pagerequest,
			Specification<Subarea> spec) {
		Page<Subarea> all = subareaDao.findAll(spec, pagerequest);
		// 立即加载
		List<Subarea> list = all.getContent();
		for (Subarea subarea : list) {
			// 初始化代理对象
			Hibernate.initialize(subarea.getRegion());
		}
		return all;
	}

	@Override
	public List<Subarea> findAllByAjax(Specification<Subarea> specification) {

		List<Subarea> all = subareaDao.findAll(specification);
		for (Subarea subarea : all) {
			Hibernate.initialize(subarea.getRegion());
		}
		return all;

	}

	@Override
	public List<Subarea> findNoAssociationSubarea() {

		return subareaDao.findNoAssociationSubarea();
	}

	@Override
	public List<Subarea> getAssociationSubarea(String did) {

		List<Subarea> all = subareaDao.getAssociationSubarea(did);
		for (Subarea subarea : all) {
			// Hibernate.initialize(subarea.getDecidedZone());
			Hibernate.initialize(subarea.getRegion());
		}
		return all;
	}
}
