package cn.itcast.bos.web.action.bc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.bc.Staff;
import cn.itcast.bos.web.action.base.BaseAction;

@SuppressWarnings("all")
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage(value = "mybos")
public class StaffAction extends BaseAction<Staff> {

	@Action(value = "staffAction_findByPhoneAjax", results = { @Result(name = "findByPhoneAjax", type = "fastjson") })
	public String findByPhoneAjax() {
		Staff staff = facadeService.getStaffService().findByPhoneAjax(
				model.getTelephone());
		if (staff == null) {
			push(true);
		} else {
			push(false);
		}
		return "findByPhoneAjax";
	}

	@Action(value = "staffAction_save", results = { @Result(name = "save", location = "/WEB-INF/pages/base/staff.jsp") })
	public String save() {
		if ("on".equals(model.getHaspda())) {
			model.setHaspda("1");
		} else {
			model.setHaspda("0");
		}
		if (StringUtils.isNotBlank(model.getNewtelephone())) {
			model.setTelephone(model.getNewtelephone());
		}
		// System.out.println(model);
		facadeService.getStaffService().save(model);
		return "save";
	}

	@Action(value = "staffAction_pagination", results = { @Result(name = "pagination", type = "fastjson") })
	public String pagination() {
		Map<String, Object> map = new HashMap<String, Object>();
		PageRequest pagerequest = new PageRequest(page - 1, rows);
		Specification<Staff> spec = new Specification<Staff>() {

			@Override
			public Predicate toPredicate(Root<Staff> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				// 集合作用 存放 请求参数可变 条件对象的!
				if (StringUtils.isNotBlank(model.getName())) {
					Predicate p1 = cb.like(root.get("name").as(String.class),
							"%" + model.getName() + "%");
					// p1 满足查询对象
					list.add(p1);
				}
				if (StringUtils.isNotBlank(model.getTelephone())) {
					Predicate p2 = cb.equal(
							root.get("telephone").as(String.class),
							model.getTelephone());
					// p2 满足查询对象
					list.add(p2);
				}
				if (StringUtils.isNotBlank(model.getStation())) {
					Predicate p3 = cb.like(
							root.get("station").as(String.class),
							"%" + model.getStation() + "%");
					// p3 满足查询对象
					list.add(p3);
				}
				if (StringUtils.isNotBlank(model.getStandard())) {
					Predicate p4 = cb.equal(
							root.get("standard").as(String.class),
							model.getStandard());
					// p3 满足查询对象
					list.add(p4);
				}
				// 集合转换 数组
				Predicate ps[] = new Predicate[list.size()];
				return cb.and(list.toArray(ps));

			}
		};
		Page<Staff> pageData = facadeService.getStaffService().pagequery(
				pagerequest, spec);
		map.put("total", pageData.getTotalElements());
		map.put("rows", pageData.getContent());
		push(map);
		return "pagination";

	}

	@Action(value = "staffAction_deltagList", results = { @Result(name = "deltagList", type = "fastjson") })
	public String deltagList() {
		try {
			String idsString = getParameter("ids");
			facadeService.getStaffService().updateDeltag(idsString);
			push(true);
		} catch (Exception e) {
			e.printStackTrace();
			push(false);
		}
		return "deltagList";

	}

	@Action(value = "staffAction_findNameByAjax", results = { @Result(name = "findNameByAjax", type = "fastjson", params = {
			"includeProperties", "id,sname" }) })
	public String findNameByAjax() {
		// String dId = getParameter("decidedId");
		List<Staff> list = facadeService.getStaffService().findNameByAjax();
		push(list);
		return "findNameByAjax";

	}

	@Action(value = "staffAction_importStaffName", results = { @Result(name = "importStaffName", type = "fastjson", params = {
			"includeProperties", "id,sname" }) })
	public String importStaffName() {
		String sid = getParameter("sid");
		Staff staff = facadeService.getStaffService().importStaffName(sid);
		push(staff);
		return "importStaffName";

	}
}
