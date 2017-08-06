package cn.itcast.bos.web.action.result;

import java.util.Collections;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsStatics;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.util.TextParseUtil;
import com.opensymphony.xwork2.util.ValueStack;

@SuppressWarnings("all")
public class FastjsonResult implements Result {
	// action业务代码,是搜索栈顶数据还是搜索值栈的一个标识
	private String root;
	// 不需要序列化的属性集合
	protected Set<String> excludeProperties = Collections.emptySet();
	// 需要序列化的属性集合
	protected Set<String> includeProperties = Collections.emptySet();

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public Set<String> getExcludeProperties() {
		return excludeProperties;
	}

	public void setExcludeProperties(String excludeProperties) {
		// 该TextParseUtil 由struts2 框架提供 用于切割 逗号分隔的字符串 该方法来源 struts2 自带的拦截器源码
		this.excludeProperties = TextParseUtil
				.commaDelimitedStringToSet(excludeProperties);
	}

	public Set<String> getIncludeProperties() {
		return includeProperties;
	}

	public void setIncludeProperties(String includeProperties) {
		this.includeProperties = TextParseUtil
				.commaDelimitedStringToSet(includeProperties);
	}

	public void execute(ActionInvocation invocation) throws Exception {
		ActionContext actionContext = invocation.getInvocationContext();
		HttpServletResponse response = (HttpServletResponse) actionContext
				.get(StrutsStatics.HTTP_RESPONSE);
		// 结果集 执行的方法
		// 从值栈中获取目标对象 进行fastjson 自定义属性序列化
		response.setContentType("text/json;charset=utf-8");
		Object rootObject = findRootObject(invocation);//
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
		// 阿里过滤器 配置哪些属性需要梳序列化 哪些属性 不需要序列化
		// 序列化json的属性对象
		if (includeProperties != null && includeProperties.size() != 0) {
			for (String in : includeProperties) {
				filter.getIncludes().add(in);// 将需要的属性添加即可
			}
		}
		if (excludeProperties != null && excludeProperties.size() != 0) {
			for (String ex : excludeProperties) {
				filter.getExcludes().add(ex);// 不需要序列化json字符串的集合添加
			}
		}
		// String jsonString = JSON.toJSONString(rootObject, filter);
		// 解除检测会出现内存溢出异常,是因为双向关联,多表互查,导致死循环,解决:外键由多方维护
		String jsonString = JSON.toJSONString(rootObject, filter,
				SerializerFeature.DisableCircularReferenceDetect);
		System.out.println("fastjson 序列化json字符串=" + jsonString);
		response.getWriter().println(jsonString);
	}

	// 搜索值栈获取序列化java对象
	protected Object findRootObject(ActionInvocation invocation) {
		Object rootObject;
		if (this.root != null) {
			ValueStack stack = invocation.getStack();
			rootObject = stack.findValue(root);
		} else {
			rootObject = invocation.getStack().peek(); // model overrides action
		}
		return rootObject;
	}

}
