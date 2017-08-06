package cn.itcast.bos.web.action.bc;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.bc.Region;
import cn.itcast.bos.utils.DownLoadUtils;
import cn.itcast.bos.utils.PinYin4jUtils;
import cn.itcast.bos.web.action.base.BaseAction;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

@SuppressWarnings("all")
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage(value = "mybos")
public class RegionAction extends BaseAction<Region> {
	private File upload;

	// setXxx的名字和从前端传来的name属性值保持一致
	public void setUpload(File upload) {
		this.upload = upload;
	}

	@Action(value = "regionAction_onclickimport", results = { @Result(name = "onclickimport", type = "json") })
	public String onclickimport() {
		// 使用apache poi解析excel文件
		if (upload != null) {

			try {
				// 创建excel工作薄的对象
				HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(
						upload));
				// 创建对工作表的引用
				HSSFSheet sheet = workbook.getSheetAt(0);
				// 封装region
				List<Region> list = new ArrayList<Region>();
				for (Row row : sheet) {
					int rownum = row.getRowNum();// 行号从0开始
					if (rownum == 0) {
						continue;
					}
					Region region = new Region();
					region.setId(row.getCell(0).getStringCellValue());
					String province = row.getCell(1).getStringCellValue();
					region.setProvince(province);
					String city = row.getCell(2).getStringCellValue();
					region.setCity(city);
					String district = row.getCell(3).getStringCellValue();
					region.setDistrict(district);
					region.setPostcode(row.getCell(4).getStringCellValue());
					// 城市编码
					city = city.substring(0, city.length() - 1);
					region.setCitycode(PinYin4jUtils.hanziToPinyin(city, ""));
					// shortcode
					province = province.substring(0, province.length() - 1);
					district = district.substring(0, district.length() - 1);
					String[] arr;
					if (province.equalsIgnoreCase(city)) {
						arr = PinYin4jUtils
								.getHeadByString(province + district);
					} else {
						arr = PinYin4jUtils.getHeadByString(province + city
								+ district);
					}
					String shortcode = getHeadFromArray(arr);
					region.setShortcode(shortcode);
					list.add(region);
				}
				facadeService.getRegionService().save(list);
				push(true);
			} catch (Exception e) {
				push(false);
			}
		} else {
			push(false);
		}
		return "onclickimport";
	}

	private String getHeadFromArray(String[] arr) {
		if (arr == null || arr.length == 0) {
			return null;
		} else {
			StringBuilder sb = new StringBuilder();
			for (String s : arr) {
				sb.append(s);
			}
			return sb.toString();
		}

	}

	@Action(value = "regionAction_pagination", results = { @Result(name = "pagination", type = "fastjson", params = {
			"root", "obj" }) })
	public String pagination() {
		Specification<Region> spec = getSpecification();
		Page<Region> pageData = facadeService.getRegionService().pageQuery(
				getPagerequest(), spec);
		setPageData(pageData);
		return "pagination";
	}

	@Action(value = "regionAction_pagequery")
	public String pagequery() {
		Specification<Region> spec = getSpecification();
		HttpServletResponse response = getResponse();
		response.setContentType("text/json;charset=utf-8");
		String jsonvalue = facadeService.getRegionService().pageFromRedis(
				getPagerequest(), spec);
		try {
			response.getWriter().println(jsonvalue);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return NONE;
	}

	private Specification<Region> getSpecification() {
		Specification<Region> spec = new Specification<Region>() {
			@Override
			public Predicate toPredicate(Root<Region> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (StringUtils.isNotBlank(model.getProvince())) {
					Predicate p1 = cb.like(root.get("province")
							.as(String.class), "%" + model.getProvince() + "%");
					list.add(p1);
				}
				if (StringUtils.isNotBlank(model.getCity())) {
					Predicate p2 = cb.like(root.get("city").as(String.class),
							"%" + model.getCity() + "%");
					list.add(p2);
				}

				if (StringUtils.isNotBlank(model.getDistrict())) {
					Predicate p3 = cb.like(root.get("district")
							.as(String.class), "%" + model.getDistrict() + "%");
					list.add(p3);
				}
				// 集合转换 数组
				Predicate ps[] = new Predicate[list.size()];
				return cb.and(list.toArray(ps));
			}
		};
		return spec;
	}

	@Action(value = "regionAction_validateId", results = { @Result(name = "validateId", type = "json") })
	public String validateId() {
		Region region = facadeService.getRegionService()
				.findById(model.getId());
		if (region == null) {
			push(true);
		} else {
			push(false);
		}
		return "validateId";
	}

	@Action(value = "regionAction_ajaxList", results = { @Result(name = "ajaxList", type = "fastjson", params = {
			"includeProperties", "id,name" }) })
	public String selectprovinceAjax() {
		String param = getParameter("q");
		List<Region> list = facadeService.getRegionService()
				.findAllByPcd(param);
		push(list);
		return "ajaxList";
	}

	@Action(value = "regionAction_validatePostcode", results = { @Result(name = "validatePostcode", type = "json") })
	public String validatePostcode() {
		Region region = facadeService.getRegionService().findByPostcode(
				model.getPostcode());
		if (region == null) {
			push(true);
		} else {
			push(false);
		}
		return "validatePostcode";
	}

	@Action(value = "regionAction_save", results = { @Result(name = "save", location = "/WEB-INF/pages/base/region.jsp") })
	public String save() {
		if (StringUtils.isNotBlank(model.getNewpostcode())) {
			model.setPostcode(model.getNewpostcode());
		}
		facadeService.getRegionService().save(model);
		return "save";
	}

	@Action(value = "regionAction_importpcd", results = { @Result(name = "importpcd", type = "fastjson", params = {
			"includeProperties", "id,name" }) })
	public String importpcd() {
		String rid = getParameter("rid");
		Region region = facadeService.getRegionService().importpcd(rid);
		push(region);
		return "importpcd";
	}

	@Action(value = "regionAction_export")
	public String export() {
		// 1.获得所有区域数据
		List<Region> list = facadeService.getRegionService().findAll();
		// 2.pdf下载

		// 3.创建document对象
		Document document = new Document();
		// 4.获得输出流
		HttpServletResponse response = getResponse();
		try {
			PdfWriter writer = PdfWriter.getInstance(document,
					response.getOutputStream());
			writer.setEncryption("itcast".getBytes(), "heima04".getBytes(),
					PdfWriter.ALLOW_SCREENREADERS,
					PdfWriter.STANDARD_ENCRYPTION_128);
			// 下载两头一流
			String filename = "区域报表.pdf";
			response.setContentType(ServletActionContext.getServletContext()
					.getMimeType(filename));
			response.setHeader(
					"Content-Disposition",
					"attachment;filename="
							+ DownLoadUtils.getAttachmentFileName(filename,
									getRequest().getHeader("user-agent")));
			// 打开文档
			document.open();
			Table table = new Table(5, list.size() + 1);
			// table.setBorderWidth(1f);
			// table.setAlignment(1);// 其中1为居中对齐，2为右对齐，3为左对
			// table.setBorder(1);
			// table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);//
			// 水平对齐方式
			// table.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);
			// // 垂直对齐方式
			// 设置表格字体
			BaseFont cn = BaseFont.createFont("STSongStd-Light",
					"UniGB-UCS2-H", false);
			Font font = new Font(cn, 10, Font.NORMAL, Color.BLUE);

			// 表头
			table.addCell(buildCell("省", font));
			table.addCell(buildCell("市", font));
			table.addCell(buildCell("区", font));
			table.addCell(buildCell("邮编", font));
			table.addCell(buildCell("简码", font));
			// 表格数据
			for (Region region : list) {
				table.addCell(buildCell(region.getProvince(), font));
				table.addCell(buildCell(region.getCity(), font));
				table.addCell(buildCell(region.getDistrict(), font));
				table.addCell(buildCell(region.getPostcode(), font));
				table.addCell(buildCell(region.getShortcode(), font));
			}
			// 向文档添加表格
			document.add(table);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return NONE;
	}

	private Cell buildCell(String content, Font font) throws Exception {
		Phrase phrase = new Phrase(content, font);
		Cell cell = new Cell(phrase);
		// 设置垂直居中
		cell.setVerticalAlignment(1);
		// 设置水平居中
		cell.setHorizontalAlignment(1);
		return cell;
	}
}