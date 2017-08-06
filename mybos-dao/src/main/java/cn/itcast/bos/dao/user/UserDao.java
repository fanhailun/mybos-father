package cn.itcast.bos.dao.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.user.User;

public interface UserDao extends JpaRepository<User, Integer> {

	@Query("from User where email=?1 and password=?2")
	public User login(String email, String password);

	@Query("from User where telephone=?1")
	public User findByTelephone(String telephone);

	@Modifying
	@Query("update User set password=?1 where telephone=?2")
	public void updateByTelephone(String password, String telephone);

	@Query("from User where email=?1")
	public User findByEmail(String email);

}
