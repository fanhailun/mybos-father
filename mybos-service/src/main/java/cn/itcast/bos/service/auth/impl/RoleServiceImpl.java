package cn.itcast.bos.service.auth.impl;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.auth.RoleDao;
import cn.itcast.bos.domain.auth.Function;
import cn.itcast.bos.domain.auth.Menu;
import cn.itcast.bos.domain.auth.Role;
import cn.itcast.bos.service.auth.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;

	@Override
	public void save(Role model, String menuIds, String[] functionIds) {
		model = roleDao.saveAndFlush(model);
		System.out.println(model);
		// 添加权限role_function
		Set<Function> functions = model.getFunctions();
		if (functionIds != null && functionIds.length != 0) {
			for (String fid : functionIds) {
				Function f = new Function();
				f.setId(fid);
				functions.add(f);
			}
		}
		// 添加菜单role_menu
		Set<Menu> menus = model.getMenus();
		if (StringUtils.isNotBlank(menuIds)) {
			String[] arr = menuIds.split(",");
			for (String mid : arr) {
				Menu m = new Menu();
				m.setId(mid);
				menus.add(m);
			}
		}
	}

	@Override
	public Page<Role> pageQuery(PageRequest pagerequest) {

		return roleDao.findAll(pagerequest);
	}

	@Override
	public List<Role> ajaxList() {

		return roleDao.findAll();
	}

	@Override
	public List<Role> findAll() {
		return roleDao.findAll();
	}

	@Override
	public List<Role> findAllRolesByUserId(Integer id) {

		return roleDao.findAllRolesByUserId(id);
	}

}
