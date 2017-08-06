package cn.itcast.bos.service.auth.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.auth.MenuDao;
import cn.itcast.bos.domain.auth.Menu;
import cn.itcast.bos.service.auth.MeunService;

@Service
@Transactional
@SuppressWarnings("all")
public class MeunServiceImpl implements MeunService {

	@Autowired
	private MenuDao menuDao;

	@Override
	public List<Menu> ajaxListHasSonMenus() {

		return menuDao.ajaxListHasSonMenus();
	}

	@Override
	public Page<Menu> pageQuery(PageRequest pagerequest) {

		Page<Menu> all = menuDao.findAll(pagerequest);
		/*
		 * List<Menu> list = all.getContent(); for (Menu menu : list) {
		 * Hibernate.initialize(menu.getMenu()); }
		 */
		return all;
	}

	@Override
	public void save(Menu model) {
		menuDao.save(model);
	}

	@Override
	public List<Menu> ajaxList() {

		return menuDao.ajaxList();
	}

	@Override
	public List<Menu> findMenuByUserId(Integer id) {

		return menuDao.findMenuByUserId(id);
	}

}
