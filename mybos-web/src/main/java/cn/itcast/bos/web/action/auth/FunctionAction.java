package cn.itcast.bos.web.action.auth;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.auth.Function;
import cn.itcast.bos.web.action.base.BaseAction;

@SuppressWarnings("all")
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage(value = "mybos")
public class FunctionAction extends BaseAction<Function> {

	@Action(value = "functionAction_save", results = { @Result(name = "save", location = "/WEB-INF/pages/admin/function.jsp") })
	public String save() {
		facadeService.getFunctionService().save(model);
		return "save";
	}

	@Action(value = "functionAction_pagination")
	public String pagination() {
		Page<Function> pageData = facadeService.getFunctionService().pageQuery(
				getPagerequest());
		setPageData(pageData);
		return "pagination";
	}

	@Action(value = "functionAction_update", results = { @Result(name = "update", type = "fastjson") })
	public String update() {
		try {
			facadeService.getFunctionService().save(model);
			push(true);
		} catch (Exception e) {
			e.printStackTrace();
			push(false);
		}
		return "update";
	}

	@Action(value = "functionAction_ajaxList", results = { @Result(name = "ajaxList", type = "fastjson", params = {
			"includeProperties", "id,name" }) })
	public String ajaxList() {
		List<Function> list = facadeService.getFunctionService().findAll();
		push(list);
		return "ajaxList";
	}
}
