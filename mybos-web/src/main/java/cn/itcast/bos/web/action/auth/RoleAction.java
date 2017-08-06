package cn.itcast.bos.web.action.auth;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.auth.Role;
import cn.itcast.bos.web.action.base.BaseAction;

@SuppressWarnings("all")
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage(value = "mybos")
public class RoleAction extends BaseAction<Role> {

	@Action(value = "roleAction_save", results = { @Result(name = "save", location = "/WEB-INF/pages/admin/role.jsp") })
	public String save() {
		String menuIds = getParameter("menuIds");
		String[] functionIds = getRequest().getParameterValues("functionIds");
		facadeService.getRoleService().save(model, menuIds, functionIds);
		return "save";
	}

	@Action(value = "roleAction_pagination")
	public String pagination() {
		Page<Role> pageData = facadeService.getRoleService().pageQuery(
				getPagerequest());
		setPageData(pageData);
		return "pagination";
	}

	@Action(value = "roleAction_ajaxList", results = { @Result(name = "ajaxList", type = "fastjson", params = {
			"includeProperties", "id,name" }) })
	public String ajaxList() {
		List<Role> list = facadeService.getRoleService().ajaxList();
		push(list);
		return "ajaxList";
	}
}
