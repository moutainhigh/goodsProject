package com.mendao.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mendao.business.dto.UserProfile;
import com.mendao.business.entity.Attachment;
import com.mendao.common.util.StringUtil;
import com.mendao.framework.entity.FwAccount;
import com.mendao.util.Constant;

/**
 * 拦截未登陆的用户
 * 
 * @author zhaolei
 *
 */
public class UserSecurityInterceptor implements HandlerInterceptor {
	/**
	 * 日志
	 */
	private static final Logger _logger = LoggerFactory.getLogger(UserSecurityInterceptor.class);
	private String LOGIN_ACCOUNT = "LoginAccount";
	public static final String MENU_ID = "menuId";
	public static final String PAGE_URL = "queryPageUrl";

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 验证用户是否登陆
		String url = request.getServletPath();//request.getRequestURI().toString();
		if (!StringUtil.contains(url, "/login")) {
			// 判断是否有权限
			UserProfile account = (UserProfile) request.getSession().getAttribute(LOGIN_ACCOUNT);
			if (account == null) {
				response.sendRedirect("/");
				return false;
			}
			// 错误消息浮框处理
			String iserrorRedirect = request.getParameter(Constant.ERROR_REDIRECT);
			if (!StringUtil.equals(iserrorRedirect, Constant.REDIRECT_BOOLEAN)) {
				request.getSession().setAttribute(Constant.ERROR_MESSAGE, null);
			}
			if (StringUtil.isNotBlank(request.getParameter(MENU_ID))) {
				request.getSession().setAttribute(MENU_ID, Long.valueOf(request.getParameter(MENU_ID)));
			}
			if (!StringUtil.contains(url, "/menu/getMenu")) {
//				FwAction fwAction = account.getPermitActionMap().get(url);
//				if (fwAction == null) {
//					request.setAttribute("run_error", "您没有操作权限，请联系管理员！");
//					try {
//						String upUrl = request.getHeader("Referer");
//						String DisUrl = StringUtil.substring(upUrl,
//								StringUtil.indexOf(upUrl, String.valueOf(request.getServerPort())));
//						DisUrl = StringUtil.substring(DisUrl, StringUtil.indexOf(DisUrl, "/"));
//						request.getRequestDispatcher(DisUrl).forward(request, response);
//					} catch (Exception e) {
//						LOG.error(e.getMessage());
//						response.sendRedirect(request.getHeader("Referer"));
//					}
//					return false;
//				} else {
//					if (StringUtil.contains(url, "/query")) {
//						account.getToolAddress().clear();
//						account.getToolAddress().put(fwAction.getFwSource().getName(), url);
//					} else {
//						account.getToolAddress().put(fwAction.getName(), "#");
//					}
//				}
			}
			request.getSession().setAttribute(LOGIN_ACCOUNT, account);
		}

		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

	protected static String getActionName(HttpServletRequest request) {
		String actionName = "";
		if (StringUtil.isBlank(actionName)) {
			String requestURL = request.getRequestURI();
			if (requestURL != null) {
				actionName = requestURL.substring(requestURL.lastIndexOf("/") + 1);
			}
		}
		return actionName;
	}

}
