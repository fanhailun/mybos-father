package cn.itcast.bos.web.action.base;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import cn.itcast.bos.service.facade.FacadeService;
import cn.itcast.bos.utils.DownLoadUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

// 复用 Action 代码
@SuppressWarnings("all")
public abstract class BaseAction<T> extends ActionSupport implements
		ModelDriven<T> {

	protected T model;

	public T getModel() {
		return model;
	}

	private Page<T> pageData;

	// 父类封装map,存入值栈
	public Object getObj() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", pageData.getTotalElements());
		map.put("rows", pageData.getContent());
		return map;
	}

	// 子类调用
	public void setPageData(Page<T> pageData) {
		this.pageData = pageData;
	}

	// 子类调用pagerequest
	public PageRequest getPagerequest() {
		PageRequest pagerequest = new PageRequest(page - 1, rows);
		return pagerequest;
	}

	// 注入门面类
	@Autowired
	protected FacadeService facadeService;

	public BaseAction() {
		// 对model进行实例化， 通过子类 类声明的泛型
		Type superclass = this.getClass().getGenericSuperclass();
		// 转化为参数化类型
		ParameterizedType parameterizedType = (ParameterizedType) superclass;
		// 获取一个泛型参数
		Class<T> modelClass = (Class<T>) parameterizedType
				.getActualTypeArguments()[0];
		try {
			model = modelClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	// 获取请求参数
	public String getParameter(String name) {
		return ServletActionContext.getRequest().getParameter(name);
	}

	// 获取Session Attribute
	public Object getSessionAttribute(String name) {
		return ServletActionContext.getRequest().getSession()
				.getAttribute(name);
	}

	// 向session保存属性
	public void setSessionAttribute(String name, Object value) {
		ServletActionContext.getRequest().getSession()
				.setAttribute(name, value);
	}

	// 删除session属性
	public void removeSessionAttribute(String name) {
		ServletActionContext.getRequest().getSession().removeAttribute(name);
	}

	// 压栈root
	public void push(Object obj) {
		ActionContext.getContext().getValueStack().push(obj);
	}

	public void set(String key, Object obj) {
		ActionContext.getContext().getValueStack().set(key, obj);
	}

	// map栈
	public void put(String key, Object obj) {
		ActionContext.getContext().put(key, obj);
	}

	// 下载,封装response
	public HttpServletResponse getResponse() {

		return ServletActionContext.getResponse();
	}

	// 封装resquest
	public HttpServletRequest getRequest() {

		return ServletActionContext.getRequest();
	}

	// 两个头一个流,下载 需要文件名和浏览器类型,filename:真实文件名, path参数:下载文件在服务器中的路径

	public void downLoad(String filename, String path) {
		HttpServletResponse response = getResponse();
		ServletContext context = ServletActionContext.getServletContext();
		try {
			response.setHeader(
					"Content-Disposition",
					"attachment;filename="
							+ DownLoadUtils.getAttachmentFileName(filename,
									ServletActionContext.getRequest()
											.getHeader("user-agent")));
			response.setContentType(context.getMimeType(filename));
			ServletOutputStream out = response.getOutputStream();
			InputStream in = new FileInputStream(path);
			int len;
			byte[] arr = new byte[1024 * 8];
			while ((len = in.read(arr)) != -1) {
				out.write(arr, 0, len);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 分页请求属性驱动
	protected int page; // 页码
	protected int rows; // 每页 记录数

	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
}
