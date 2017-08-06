package cn.itcast.bos.web.action.bc;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.bc.DecidedZone;
import cn.itcast.bos.domain.bc.Region;
import cn.itcast.bos.domain.bc.Subarea;
import cn.itcast.bos.utils.DownLoadUtils;
import cn.itcast.bos.web.action.base.BaseAction;

@SuppressWarnings("all")
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage(value = "mybos")
public class SubareaAction extends BaseAction<Subarea> {

	@Action(value = "subareaAction_save", results = { @Result(name = "save", location = "/WEB-INF/pages/base/subarea.jsp") })
	public String save() {
		facadeService.getSubareaService().save(model);
		return "save";
	}

	@Action(value = "subareaAction_pagination")
	public String pagination() {
		Specification<Subarea> spec = getSpecification();
		Page<Subarea> pageData = facadeService.getSubareaService().querypage(
				getPagerequest(), spec);
		setPageData(pageData);
		return "pagination";
	}

	private Specification<Subarea> getSpecification() {
		Specification<Subarea> spec = new Specification<Subarea>() {

			@Override
			public Predicate toPredicate(Root<Subarea> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> list = new ArrayList<Predicate>();
				// 多方的一表
				if (model.getRegion() != null) {
					// 连接Region表
					Join<Subarea, Region> join = root.join(root.getModel()
							.getSingularAttribute("region", Region.class),
							JoinType.LEFT);
					if (StringUtils.isNotBlank(model.getRegion().getProvince())) {
						list.add(cb.like(join.get("province").as(String.class),
								"%" + model.getRegion().getProvince() + "%"));
					}
					if (StringUtils.isNotBlank(model.getRegion().getCity())) {
						list.add(cb.like(join.get("city").as(String.class), "%"
								+ model.getRegion().getCity() + "%"));
					}
					if (StringUtils.isNotBlank(model.getRegion().getDistrict())) {
						list.add(cb.like(join.get("district").as(String.class),
								"%" + model.getRegion().getDistrict() + "%"));
					}
				}
				// 连接decidedZone表
				if (model.getDecidedZone() != null
						&& StringUtils.isNotBlank(model.getDecidedZone()
								.getId())) {
					list.add(cb.equal(
							root.get("decidedZone").as(DecidedZone.class),
							model.getDecidedZone()));
				}
				if (StringUtils.isNotBlank(model.getAddresskey())) {
					list.add(cb.equal(root.get("addresskey").as(String.class),
							model.getAddresskey()));
				}
				Predicate ps[] = new Predicate[list.size()];
				return cb.and(list.toArray(ps));
			}
		};
		return spec;
	}

	@Action(value = "subareaAction_download")
	public String download() {
		try {
			List<Subarea> data = facadeService.getSubareaService()
					.findAllByAjax(getSpecification());
			// workbook sheet row cell
			HSSFWorkbook book = new HSSFWorkbook();
			HSSFSheet sheet = book.createSheet("分区数据1");
			HSSFRow first = sheet.createRow(0);
			first.createCell(0).setCellValue("分区编号");
			first.createCell(1).setCellValue("省");
			first.createCell(2).setCellValue("市");
			first.createCell(3).setCellValue("区");
			first.createCell(4).setCellValue("关键字");
			first.createCell(5).setCellValue("起始号");
			first.createCell(6).setCellValue("终止号");
			first.createCell(7).setCellValue("单双号");
			first.createCell(8).setCellValue("位置");
			// 添加数据
			if (data != null && data.size() != 0) {
				for (Subarea s : data) {
					// 循环一次创建一行
					int lastRowNum = sheet.getLastRowNum();// 获取当前excel最后一行行号
					HSSFRow row = sheet.createRow(lastRowNum + 1);
					row.createCell(0).setCellValue(s.getId());
					row.createCell(1).setCellValue(s.getRegion().getProvince());
					row.createCell(2).setCellValue(s.getRegion().getCity());
					row.createCell(3).setCellValue(s.getRegion().getDistrict());
					row.createCell(4).setCellValue(s.getAddresskey());
					row.createCell(5).setCellValue(s.getStartnum());
					row.createCell(6).setCellValue(s.getEndnum());
					row.createCell(7).setCellValue(s.getOodnum() + "");
					row.createCell(8).setCellValue(s.getPosition());
				}
				// 第一个sheet数据完成
			}
			// 下载两头一流

			String filename = "分区数据.xls";
			HttpServletResponse response = getResponse();
			response.setHeader(
					"Content-Disposition",
					"attachment;filename="
							+ DownLoadUtils.getAttachmentFileName(filename,
									getRequest().getHeader("user-agent")));
			response.setContentType(ServletActionContext.getServletContext()
					.getMimeType(filename));
			book.write(response.getOutputStream());
			return NONE;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Action(value = "subareaAction_ajaxList", results = { @Result(name = "ajaxList", type = "fastjson", params = {
			"includeProperties", "sid,addresskey,position" }) })
	public String ajaxList() {
		List<Subarea> list = facadeService.getSubareaService()
				.findNoAssociationSubarea();
		push(list);
		return "ajaxList";
	}

	@Action(value = "subareaAction_getAssociationSubarea", results = { @Result(name = "getAssociationSubarea", type = "fastjson") })
	public String getAssociationSubarea() {
		String did = getParameter("did");
		List<Subarea> list = facadeService.getSubareaService()
				.getAssociationSubarea(did);
		push(list);
		return "getAssociationSubarea";
	}

}
