package cn.itcast.bos.realm;

import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.bos.domain.auth.Function;
import cn.itcast.bos.domain.auth.Role;
import cn.itcast.bos.domain.user.User;
import cn.itcast.bos.service.facade.FacadeService;

public class MyBosRealm extends AuthorizingRealm {

	@Autowired
	private FacadeService facadeService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		System.out.println("授权");
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		Subject subject = SecurityUtils.getSubject();
		User loginuser = (User) subject.getPrincipal();
		if ("xxx@166.com".equals(loginuser.getEmail())
				|| "xxx@163.com".equals(loginuser.getEmail())) {
			// 超级管理员,info存放所有角色和权限关键字
			List<Function> list = facadeService.getFunctionService().findAll();
			if (list != null && list.size() != 0) {
				for (Function func : list) {
					info.addStringPermission(func.getCode());
				}
			}
			List<Role> roles = facadeService.getRoleService().findAll();
			if (roles != null && roles.size() != 0) {
				for (Role role : roles) {
					info.addRole(role.getCode());
				}
			}

		} else {
			// 非超级管理员
			List<Role> roles = facadeService.getRoleService()
					.findAllRolesByUserId(loginuser.getId());
			if (roles != null && roles.size() != 0) {
				for (Role role : roles) {
					info.addRole(role.getCode());
					Set<Function> functions = role.getFunctions();
					if (functions != null && functions.size() != 0) {
						for (Function func : functions) {
							info.addStringPermission(func.getCode());
						}
					}

				}
			}

		}
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		System.out.println("认证");
		UsernamePasswordToken mytoken = (UsernamePasswordToken) token;
		User loginuser = facadeService.getUserService().findByEmail(
				mytoken.getUsername());
		if (loginuser == null) {
			return null;
		} else {
			// shiro对比密码,数据库提供密码即可
			// 第一个参数:用户对象
			// 参数二:证书,数据库密码
			// 参数三:realm注册名称id
			AuthenticationInfo info = new SimpleAuthenticationInfo(loginuser,
					loginuser.getPassword(), super.getName());
			return info;
		}

	}

}
