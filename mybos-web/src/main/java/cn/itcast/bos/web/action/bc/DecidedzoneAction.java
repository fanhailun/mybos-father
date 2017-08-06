package cn.itcast.bos.web.action.bc;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.crm.domain.Customer;
import cn.itcast.bos.domain.bc.DecidedZone;
import cn.itcast.bos.domain.bc.Staff;
import cn.itcast.bos.web.action.base.BaseAction;

@SuppressWarnings("all")
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage(value = "mybos")
public class DecidedzoneAction extends BaseAction<DecidedZone> {

	@Action(value = "decidedzoneAction_save", results = { @Result(name = "save", location = "/WEB-INF/pages/base/decidedzone.jsp") })
	public String save() {
		String[] sid = getRequest().getParameterValues("sid");
		facadeService.getDecidedzoneService().save(sid, model);
		return "save";
	}

	@Action(value = "decidedzoneAction_pagination")
	public String pagination() {
		Specification<DecidedZone> spec = new Specification<DecidedZone>() {

			@Override
			public Predicate toPredicate(Root<DecidedZone> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (StringUtils.isNotBlank(model.getId())) {
					Predicate p1 = cb.equal(root.get("id").as(String.class),
							model.getId());
					list.add(p1);
				}
				if (StringUtils.isNotBlank(model.getName())) {
					Predicate p3 = cb.like(root.get("name").as(String.class),
							"%" + model.getName() + "%");
					list.add(p3);
				}
				// 连接staff表
				Join<DecidedZone, Staff> join = root.join(root.getModel()
						.getSingularAttribute("staff", Staff.class),
						JoinType.LEFT);
				if (model.getStaff() != null) {
					if (StringUtils.isNotBlank(model.getStaff().getStation())) {
						Predicate p2 = cb.like(
								join.get("station").as(String.class), "%"
										+ model.getStaff().getStation() + "%");
						list.add(p2);
					}
				}
				// 连接subarea
				String param = getParameter("isAssociationSubarea");
				// SetJoin<DecidedZone, Subarea> subareajoin = root.join(root
				// .getModel().getSet("subareas", Subarea.class),
				// JoinType.LEFT);
				if (StringUtils.isNotBlank(param)) {
					if ("1".equals(param)) {
						Predicate p3 = cb.isNotEmpty(root.get("subareas").as(
								Set.class));
						list.add(p3);
					} else if ("0".equals(param)) {
						Predicate p3 = cb.isEmpty(root.get("subareas").as(
								Set.class));
						list.add(p3);
					}
				}
				Predicate ps[] = new Predicate[list.size()];
				return cb.and(list.toArray(ps));
			}
		};
		Page<DecidedZone> pageData = facadeService.getDecidedzoneService()
				.pagination(getPagerequest(), spec);
		setPageData(pageData);
		return "pagination";
	}

	@Action(value = "decidedzoneAction_validateId", results = { @Result(name = "validateId", type = "fastjson") })
	public String validateId() throws Exception {
		// String dname = new
		// String(getParameter("dname").getBytes("ISO8859-1"),
		// "UTF-8");
		DecidedZone decidedzone = facadeService.getDecidedzoneService()
				.findById(model.getId());
		if (decidedzone == null) {
			push(true);
		} else {
			push(false);
		}
		return "validateId";
	}

	@Action(value = "decidedzoneAction_findAssociationCustomers", results = { @Result(name = "findAssociationCustomers", type = "fastjson", params = {
			"includeProperties", "id,name" }) })
	public String findAssociationCustomers() {
		List<Customer> list = facadeService.getDecidedzoneService()
				.findAssociationCustomers(model.getId());
		push(list);
		return "findAssociationCustomers";
	}

	@Action(value = "decidedzoneAction_findNoAssociationCustomers", results = { @Result(name = "findNoAssociationCustomers", type = "fastjson", params = {
			"includeProperties", "id,name" }) })
	public String findNoAssociationCustomers() {
		List<Customer> list = facadeService.getDecidedzoneService()
				.findNoAssociationCustomers();
		push(list);
		return "findNoAssociationCustomers";
	}

	@Action(value = "decidedzoneAction_assigncustomerstodecidedzone", results = { @Result(name = "assigncustomerstodecidedzone", location = "/WEB-INF/pages/base/decidedzone.jsp") })
	public String assigncustomerstodecidedzone() {
		String[] customerids = getRequest().getParameterValues("customerIds");
		facadeService.getDecidedzoneService().assigncustomerstodecidedzone(
				model.getId(), customerids);
		return "assigncustomerstodecidedzone";
	}

	@Action(value = "decidedzoneAction_getAssociationCustomer", results = { @Result(name = "getAssociationCustomer", type = "fastjson") })
	public String getAssociationCustomer() {
		List<Customer> list = facadeService.getDecidedzoneService()
				.findAssociationCustomers(model.getId());
		push(list);
		return "getAssociationCustomer";
	}
}
