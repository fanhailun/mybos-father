package cn.itcast.bos.web.action.filter;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Component;

import cn.itcast.bos.domain.user.User;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

@SuppressWarnings("all")
@Component(value = "MyLoginInterceptor")
public class MyLoginInterceptor extends MethodFilterInterceptor {

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		User user = (User) ServletActionContext.getRequest().getSession()
				.getAttribute("loginuser");
		if (user == null) {
			return "no_login";
		}
		return invocation.invoke();
	}

}
