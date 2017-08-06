package cn.itcast.bos.service.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import cn.itcast.bos.domain.user.User;

public interface UserService {
	public void save(User user, String[] roles);

	public List<User> findAll();

	// 登录
	public User login(String email, String password);

	public User findByTelephone(String telephone);

	public void updateByTelephone(String password, String telephone);

	public User findByEmail(String username);

	public Page<User> pageQuery(PageRequest pagerequest);

}
