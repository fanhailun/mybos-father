package cn.itcast.bos.web.action.user;

import java.util.concurrent.TimeUnit;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.user.User;
import cn.itcast.bos.utils.SendMsgUtils;
import cn.itcast.bos.web.action.base.BaseAction;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

@SuppressWarnings("all")
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage(value = "mybos")
public class UserAction extends BaseAction<User> {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Autowired
	@Qualifier("jmsQueueTemplate")
	private JmsTemplate jmsTemplate;

	@Action(value = "userAction_login", results = {
			@Result(name = "login-error", location = "/login.jsp"),
			@Result(name = "login-ok", type = "redirect", location = "/index.jsp"),
			@Result(name = "login-params-error", location = "/login.jsp") })
	@InputConfig(resultName = "login-params-error")
	public String login() {
		// User loginuser =
		// facadeService.getUserService().login(model.getEmail(),
		// model.getPassword());
		removeSessionAttribute("key");
		try {
			// 1.获取subject对象
			Subject subject = SecurityUtils.getSubject();
			// 2.获取令牌对象(封装参数数据)
			UsernamePasswordToken token = new UsernamePasswordToken(
					model.getEmail(), model.getPassword());
			subject.login(token);
			return "login-ok";
		} catch (AuthenticationException e) {
			e.printStackTrace();
			this.addActionError(this.getText("login.emailorpassword.error"));
			return "login-error";
		}
	}

	@Action(value = "userAction_checkcode", results = { @Result(name = "valicode", type = "json") })
	public String checkcode() {
		String inputcode = getParameter("inputcode");
		String sessioncode = (String) getSessionAttribute("key");
		if (sessioncode.equalsIgnoreCase(inputcode)) {
			push(true);
		} else {
			push(false);
		}

		return "valicode";
	}

	// 发送验证码给mq,redis存储
	@Action(value = "userAction_sendMsg", results = { @Result(name = "sendmsg", type = "json") })
	public String sendMsg() {
		try {
			final String code = SendMsgUtils.getCode();
			redisTemplate.opsForValue().set(model.getTelephone(), code, 120,
					TimeUnit.SECONDS);
			System.out.println(code + "---发送短信获取验证码---");
			// 发送给mq
			jmsTemplate.send("bos_sms", new MessageCreator() {

				@Override
				public Message createMessage(Session session)
						throws JMSException {
					MapMessage mapMessage = session.createMapMessage();
					mapMessage.setString("telephone", model.getTelephone());
					mapMessage.setString("msg", code);
					return mapMessage;
				}
			});
			push(true);
		} catch (JmsException e) {
			push(false);
		}
		return "sendmsg";
	}

	@Action(value = "userAction_smsPassword", results = { @Result(name = "smspassword", type = "json") })
	public String smsPassword() {
		// 1.检查手机号是否存在
		User user = facadeService.getUserService().findByTelephone(
				model.getTelephone());
		// 0表示手机号不存在,1表示验证码过期,2表示验证码错误,3表示正确
		if (user == null) {
			push("0");
		} else {
			String code = getParameter("checkcode");
			String rediscode = redisTemplate.opsForValue().get(
					model.getTelephone());
			if (StringUtils.isNotBlank(rediscode)) {
				if (rediscode.equals(code)) {
					push("3");
				} else {
					push("2");
				}
			} else {
				push("1");
			}
		}
		return "smspassword";
	}

	@Action(value = "userAction_gobackpassword", results = { @Result(name = "gobackpassword", type = "json") })
	public String gobackpassword() {
		try {
			facadeService.getUserService().updateByTelephone(
					model.getPassword(), model.getTelephone());
			push(true);
		} catch (Exception e) {
			e.printStackTrace();
			push(false);
		}
		return "gobackpassword";
	}

	// 用户退出
	@Action(value = "userAction_loginout", results = { @Result(name = "loginout", type = "redirect", location = "/login.jsp") })
	public String loginout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "loginout";
	}

	@Action(value = "userAction_save", results = { @Result(name = "save", location = "/WEB-INF/pages/admin/userlist.jsp") })
	public String save() {
		String[] roles = getRequest().getParameterValues("roleIds");
		facadeService.getUserService().save(model, roles);
		return "save";
	}

	@Action(value = "userAction_pagination", results = { @Result(name = "pagination", type = "json", params = {
			"root", "obj" }) })
	public String pagination() {
		Page<User> pageData = facadeService.getUserService().pageQuery(
				getPagerequest());
		setPageData(pageData);
		return "pagination";
	}
}
