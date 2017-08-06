package cn.itcast.bos.dao.auth;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.auth.Role;

public interface RoleDao extends JpaRepository<Role, String>,
		JpaSpecificationExecutor<Role> {
	@Query("from Role r inner join fetch r.users u where u.id=?1 ")
	List<Role> findAllRolesByUserId(Integer id);

}
