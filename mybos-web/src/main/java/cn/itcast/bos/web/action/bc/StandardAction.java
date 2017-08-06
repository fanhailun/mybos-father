package cn.itcast.bos.web.action.bc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.bc.Standard;
import cn.itcast.bos.domain.user.User;
import cn.itcast.bos.web.action.base.BaseAction;

@SuppressWarnings("all")
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage(value = "mybos")
public class StandardAction extends BaseAction<Standard> {
	@Action(value = "standardAction_save", results = { @Result(name = "save", location = "/WEB-INF/pages/base/standard.jsp") })
	public String save() {
		if (model.getId() == null) {
			model.setDeltag(1);
		}
		User user = (User) getSessionAttribute("loginuser");
		model.setOperator(user.getEmail());
		model.setOperatorcompany(user.getStation());
		System.out.println(model.toString());
		facadeService.getStandardService().save(model);
		return "save";
	}

	@Action(value = "standardAction_pagination", results = { @Result(name = "pagination", type = "json") })
	public String pagination() {
		Map<String, Object> map = new HashMap<String, Object>();
		PageRequest pagerequest = new PageRequest(page - 1, rows);
		Page<Standard> pageData = facadeService.getStandardService().pageQuery(
				pagerequest);
		map.put("total", pageData.getTotalElements());
		map.put("rows", pageData.getContent());
		push(map);
		return "pagination";
	}

	@Action(value = "standardAction_deltag", results = { @Result(name = "deltag", type = "json") })
	public String deltag() {
		try {
			String ids = getParameter("ids");
			facadeService.getStandardService().updatedeltag(ids);
			push(true);
		} catch (Exception e) {
			e.printStackTrace();
			push(false);
		}
		return "deltag";
	}

	// 加载收派员添加栏的下拉框的值
	@Action(value = "standardAction_selectAjax", results = { @Result(name = "selectAjax", type = "json") })
	public String selectAjax() {
		List<Standard> list = facadeService.getStandardService().findByDeltag();
		push(list);
		return "selectAjax";
	}
}
