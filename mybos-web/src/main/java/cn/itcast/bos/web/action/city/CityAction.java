package cn.itcast.bos.web.action.city;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.city.City;
import cn.itcast.bos.web.action.base.BaseAction;

@SuppressWarnings("all")
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage(value = "mybos")
public class CityAction extends BaseAction<City> {
	@Action(value = "cityAction_load", results = { @Result(name = "load", type = "fastjson") })
	public String load() {
		HttpServletResponse response = getResponse();
		response.setContentType("text/json;charset=utf-8");
		String jsonvalue = facadeService.getCityService().loadCity(
				model.getPid());
		try {
			response.getWriter().println(jsonvalue);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return NONE;

	}
}
