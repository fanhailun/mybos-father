package cn.itcast.bos.service.user.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.user.UserDao;
import cn.itcast.bos.domain.auth.Role;
import cn.itcast.bos.domain.user.User;
import cn.itcast.bos.service.user.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}

	@Override
	public User login(String email, String password) {
		return userDao.login(email, password);
	}

	@Override
	public User findByTelephone(String telephone) {

		return userDao.findByTelephone(telephone);
	}

	@Override
	public void updateByTelephone(String password, String telephone) {
		userDao.updateByTelephone(password, telephone);
	}

	@Override
	public User findByEmail(String email) {

		return userDao.findByEmail(email);
	}

	@Override
	public void save(User model, String[] roles) {
		model = userDao.saveAndFlush(model);
		Set<Role> role = model.getRoles();
		// 添加中间表user_role
		if (roles != null && roles.length != 0) {
			for (String rid : roles) {
				Role r = new Role();
				r.setId(rid);
				role.add(r);
			}
		}
	}

	@Override
	public Page<User> pageQuery(PageRequest pagerequest) {

		return userDao.findAll(pagerequest);
	}

}
