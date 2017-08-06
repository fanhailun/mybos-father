package cn.itcast.bos.web.action.auth;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.auth.Menu;
import cn.itcast.bos.domain.user.User;
import cn.itcast.bos.web.action.base.BaseAction;

@SuppressWarnings("all")
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage(value = "mybos")
public class MenuAction extends BaseAction<Menu> {

	@Action(value = "menuAction_ajaxListHasSonMenus", results = { @Result(name = "ajaxListHasSonMenus", type = "fastjson", params = {
			"includeProperties", "id,name" }) })
	public String ajaxListHasSonMenus() {
		List<Menu> list = facadeService.getMeunService().ajaxListHasSonMenus();
		push(list);
		return "ajaxListHasSonMenus";
	}

	@Action(value = "menuAction_pagination")
	public String pagination() {
		super.setPage(Integer.parseInt(getParameter("page")));
		Page<Menu> pageData = facadeService.getMeunService().pageQuery(
				getPagerequest());
		setPageData(pageData);
		return "pagination";
	}

	@Action(value = "menuAction_save", results = { @Result(name = "save", location = "/WEB-INF/pages/admin/menu.jsp") })
	public String save() {
		if (model.getMenu() == null
				|| StringUtils.isBlank(model.getMenu().getId())) {
			model.setMenu(null);
		}
		facadeService.getMeunService().save(model);
		return "save";
	}

	@Action(value = "menuAction_ajaxList", results = { @Result(name = "ajaxList", type = "fastjson", params = {
			"includeProperties", "id,name,pId,page" }) })
	public String ajaxList() {
		List<Menu> list = facadeService.getMeunService().ajaxList();
		push(list);
		return "ajaxList";
	}

	@Action(value = "menuAction_findMenuByUserId", results = { @Result(name = "findMenuByUserId", type = "fastjson", params = {
			"includeProperties", "id,name,pId,page" }) })
	public String findMenuByUserId() {
		Subject sub = SecurityUtils.getSubject();
		User loginuser = (User) sub.getPrincipal();
		List<Menu> list = facadeService.getMeunService().findMenuByUserId(
				loginuser.getId());
		push(list);
		return "findMenuByUserId";
	}
}
