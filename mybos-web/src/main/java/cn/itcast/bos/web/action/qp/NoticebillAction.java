package cn.itcast.bos.web.action.qp;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.crm.domain.Customer;
import cn.itcast.bos.domain.qp.NoticeBill;
import cn.itcast.bos.domain.user.User;
import cn.itcast.bos.web.action.base.BaseAction;

@SuppressWarnings("all")
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage(value = "mybos")
public class NoticebillAction extends BaseAction<NoticeBill> {

	@Action(value = "noticebillAction_findCustomerByTelephone", results = { @Result(name = "findCustomerByTelephone", type = "fastjson") })
	public String findCustomerByTelephone() {
		Customer c = facadeService.getNoticebillService()
				.findCustomerByTelephone(model.getTelephone());
		push(c);
		return "findCustomerByTelephone";
	}

	@Action(value = "noticebillAction_save", results = { @Result(name = "save", location = "/WEB-INF/pages/qupai/noticebill_add.jsp") })
	public String save() {
		User user = (User) getSessionAttribute("loginuser");
		model.setUser(user);// 受理人
		String province = getParameter("nprovince");
		String city = getParameter("ncity");
		String district = getParameter("ndistrict");
		facadeService.getNoticebillService().save(model, province, city,
				district);
		return "save";
	}

}
