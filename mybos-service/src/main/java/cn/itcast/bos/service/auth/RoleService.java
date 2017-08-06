package cn.itcast.bos.service.auth;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import cn.itcast.bos.domain.auth.Role;

public interface RoleService {

	void save(Role model, String menuIds, String[] functionIds);

	Page<Role> pageQuery(PageRequest pagerequest);

	List<Role> ajaxList();

	List<Role> findAll();

	List<Role> findAllRolesByUserId(Integer id);

}
