package com.mendao.framework.show;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mendao.business.dto.UserProfile;
import com.mendao.business.entity.Category;
import com.mendao.business.entity.Recommend;
import com.mendao.business.service.CategoryService;
import com.mendao.business.service.RecommendService;
import com.mendao.business.service.SchoolStickerService;
import com.mendao.business.service.UserActionService;
import com.mendao.business.service.VerifyUserService;
import com.mendao.common.util.StringUtil;
import com.mendao.constant.MendaoConstant;
import com.mendao.entity.util.CategorySchoolUtil;
import com.mendao.exception.BusinessCheckException;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.base.jpa.ParamsUtil;
import com.mendao.remote.util.RemoteUtil;
import com.mendao.util.CacheUtil;

import net.sf.json.JSONObject;

/**
 * 登录Controller
 * @author libra
 *
 */
@Controller
public class LoginController extends BaseController{
	
	private static final Logger _logger = Logger.getLogger(LoginController.class);
	private static final String VERIFY_CODE = "VerifyCode"; 
	private static final String REDIRECT_HOME = "redirect:/";//"redirect:/c/menu/getMenu";
	private static final String COOKIE_UNAME = "md_uname_cookie";
	private static final String PAGE_TOKEN = "token";
	
	@Autowired
	UserActionService userActionService;
	
	@Autowired 
	VerifyUserService verifyService;
	
	@Autowired
	private RecommendService recommendService;
	
	@Autowired
	private SchoolStickerService schoolStickerService;
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(value={"/", "/home"},method=RequestMethod.GET)
	@SuppressWarnings("unchecked")
	public String index(final HttpSession session, Model model, HttpServletRequest request){
		//UserProfile profile = super.getSessionUser(session);
		//获取后台推荐的话题
		PageEntity<Recommend> topicEntity = ParamsUtil.createPageEntityFromRequest(request, 100);
		topicEntity.getParams().put("type", 1);
		topicEntity = recommendService.getPage(topicEntity);
		model.addAttribute("topicList", topicEntity.getResult());
		//获取后台推荐的活动
		PageEntity<Recommend> activityEntity = ParamsUtil.createPageEntityFromRequest(request, 100);
		activityEntity.getParams().put("type", 2);
		activityEntity = recommendService.getPage(activityEntity);
		model.addAttribute("activityList", activityEntity.getResult());
		//获取后台推荐的热门机构
		PageEntity<Recommend> SchoolEntity = ParamsUtil.createPageEntityFromRequest(request, 100);
		SchoolEntity.getParams().put("type", 3);
		SchoolEntity = recommendService.getPage(SchoolEntity);
		model.addAttribute("schoolList", SchoolEntity.getResult());
		//获取机构分类标签以及各个分类下推荐的机构
		List<Category> categoryList = categoryService.getListByAttributes("STICKER",1, "SCHOOL_STICKER");
		List<CategorySchoolUtil> list = new ArrayList<CategorySchoolUtil>();
		for(Category cl:categoryList){
			CategorySchoolUtil cs = new CategorySchoolUtil();
			cs.setCategory(cl);
			cs.setSchool(recommendService.getSchoolListByAttributes(4,cl.getId()));
			list.add(cs);
		}
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("categorySchoolList", list);
		
		return "front/home";
	}
	
	/**
	 * 跳转登录页面
	 * @return
	 */
	@RequestMapping(value={"login"},method=RequestMethod.GET)
	public String login(final HttpServletRequest request, final HttpSession session, final Model model){
		
		Cookie c = getCookie(request, COOKIE_UNAME);
		if(null != c){
			model.addAttribute("mobile", c.getValue());
		}else{
			model.addAttribute("mobile", "");
		}
		return LOGIN;
	}
	

	/**
	 * 登录操作
	 * @param session
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/doLogin",method = RequestMethod.POST)
	public String doLogin(final HttpSession session, final HttpServletRequest request, 
			final HttpServletResponse response) throws UnsupportedEncodingException {
		
		String userName = request.getParameter("mobile");
		String password = request.getParameter("password");
		
		try{
			//完成用户登录操作
			UserProfile profile = verifyService.login(userName, password);
			String remember = request.getParameter("remember");
			if(StringUtil.equals(remember, "1")){
				setCookie(response, COOKIE_UNAME, userName, 30*24*60*60);
			}else{
				removeCookie(response, COOKIE_UNAME);
			}
			//将用户数据写入Session
			super.setSessionUser(session, profile);
			
			return REDIRECT_HOME;
		}catch(Exception e) {
			e.printStackTrace();
			if (_logger.isDebugEnabled()) {
				_logger.debug(e.getMessage());
			}
			request.setAttribute(ERROR_MESSAGE, e.getMessage());
			return LOGIN ;
		}
	}
	
	
	/**
	 * 注册页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/register"},method=RequestMethod.GET)
	public String register(final HttpServletRequest request, final HttpSession session, final Model model, 
			@ModelAttribute("mobile") String mobile, @ModelAttribute("nickName") String nickName){
//		model.addAttribute("nickName", nickName);
//		model.addAttribute("mobile", mobile);
		
		/**
		 * 向页面设置token，防止跨域提交及多次请求荣联发送短信
		 */
		String token = UUID.randomUUID().toString();
		model.addAttribute(PAGE_TOKEN, token);
		session.setAttribute(PAGE_TOKEN, token);
		return REGISTER;
	}
	
	/**
	 * 注册操作
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/register"}, method=RequestMethod.POST)
	public String register(final HttpServletRequest request, final HttpServletResponse response, 
			final HttpSession session, final Model model, RedirectAttributes attr ){
		String mobile = request.getParameter("mobile");
		String password = request.getParameter("password");
		String nickName = request.getParameter("nickName");
		
		//**************************
		//* 检查是否是跨域提交。
		//**************************
		String memoryToken = (String) session.getAttribute(PAGE_TOKEN);
		String token = request.getParameter(PAGE_TOKEN);
		if(!StringUtil.equals(memoryToken, token)){
			attr.addFlashAttribute("mobile", mobile);
			attr.addFlashAttribute("nickName", nickName);
			attr.addFlashAttribute(ERROR_MESSAGE, "禁止跨域提交数据");
			return "redirect:/register";
		}
		
		//**********************************************************
		//* 检查验证码是否正确。Web应用可以通过Session回话保存验证码并进行验证
		//* 考虑是否需要将该部分转义到Service层去实现？？？
		//**********************************************************
		String verifyCode = request.getParameter("verifyCode");
		String memoryCode = (String)CacheUtil.getValue(MendaoConstant.VerifyCodePrefix + mobile);
		session.removeAttribute(VERIFY_CODE);
		if(StringUtil.isBlank(verifyCode)){
			attr.addFlashAttribute("mobile", mobile);
			attr.addFlashAttribute("nickName", nickName);
			attr.addFlashAttribute(ERROR_MESSAGE,  "请输入验证码");
			return "redirect:/register";
		}
		if(!StringUtil.equals(verifyCode, memoryCode)){
			attr.addFlashAttribute("mobile", mobile);
			attr.addFlashAttribute("nickName", nickName);
			attr.addFlashAttribute(ERROR_MESSAGE,  "您输入的短信验证码不正确");
			return "redirect:/register";
		}
		
		//**********************************************************
		//* 将页面提交的数据装载入用户对象。
		//* 注意新注册的用户会将注册电话号码设置为默认的账号
		//**********************************************************
		UserProfile profile = new UserProfile();
		//profile.setAcctName(mobile);
		profile.setMobile(mobile);
		profile.setPassword(password);
		profile.setNickName(nickName);
		try {
			// 提交注册信息
			verifyService.register(profile);
			return LOGIN;	// TODO 注册完成欢迎页面？
		} catch (BusinessCheckException e) {
			attr.addFlashAttribute("mobile", mobile);
			attr.addFlashAttribute("nickName", nickName);
			attr.addFlashAttribute(ERROR_MESSAGE, e.getMessage());
			e.printStackTrace();
			return "redirect:/register";
		}
	}
	
	
	/**
	 * 安全退出登录
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/logout"},method=RequestMethod.GET)
	public String logout(HttpServletRequest request){
		request.getSession().invalidate();
		return REDIRECT_LOGIN;
	}
	/**
	 * 异步登录操作
	 * @param session
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxDoLogin",method = RequestMethod.POST)
	public Map<String,Object> ajaxDoLogin(final HttpSession session, final HttpServletRequest request, 
			final HttpServletResponse response) throws UnsupportedEncodingException {
		
		String userName = request.getParameter("mobile");
		String password = request.getParameter("password");
		Map<String,Object> map = new HashMap<String, Object>();
		try{
			//完成用户登录操作
			UserProfile profile = verifyService.login(userName, password);
			String remember = request.getParameter("remember");
			if(StringUtil.equals(remember, "1")){
				setCookie(response, COOKIE_UNAME, userName, 30*24*60*60);
			}else{
				removeCookie(response, COOKIE_UNAME);
			}
			//将用户数据写入Session
			super.setSessionUser(session, profile);
			//代表登陆成功
			map.put("msg", 1);
			return map;
		}catch(Exception e) {
			e.printStackTrace();
			if (_logger.isDebugEnabled()) {
				_logger.debug(e.getMessage());
			}
			request.setAttribute(ERROR_MESSAGE, e.getMessage());
			//代表登录失败
			map.put("msg", -1);
			return map;
		}
	}
	
	@RequestMapping(value="/forgetPwd",method=RequestMethod.GET)
	public String forgetPassword(){
		return "/front/forgetpwd/forget_pwd";
	}
	
	@RequestMapping(value="/forget/askVerifyPg",method=RequestMethod.GET)
	public String askVerifyPage(){
		return "/front/forgetpwd/ask_verify_code";
	}
	
	@RequestMapping(value="/forget/checkVerify/{target}",method=RequestMethod.POST)
	public String resetPassword(final HttpServletRequest request, final Model model, 
			@PathVariable("target") String target, RedirectAttributes attr){
		String mobile = request.getParameter("mobile");
		String email = request.getParameter("email");
		String verifyCode = request.getParameter("verifyCode");
		String key = (target.equals("mobile")? mobile : email);
		String memCode = (String)CacheUtil.getValue(MendaoConstant.VerifyCodePrefix + key);
		if(memCode == null || !StringUtil.equals(verifyCode, memCode)){
			attr.addFlashAttribute("mobile", mobile);
			attr.addFlashAttribute("email", email);
			attr.addFlashAttribute("target", target);
			attr.addFlashAttribute(ERROR_MESSAGE, "验证码错误");
			return "redirect:/forget/askVerifyPg";
		}
		
		model.addAttribute("target", target);
		model.addAttribute("targetValue", key);
		return "/front/forgetpwd/reset_pwd";
	}
	
	@RequestMapping(value="/forgetPwd/resetPwd",method=RequestMethod.GET)
	public String resetPassword(final HttpServletRequest request){
		
		return "/front/forgetpwd/reset_pwd";
	}
	
	@RequestMapping(value="/forgetPwd/resetPwd",method=RequestMethod.POST)
	public String resetPassword(final HttpServletRequest request, RedirectAttributes attr){
		
		String pwd = request.getParameter("password");
		String pwd2 = request.getParameter("confirm");
		String target = request.getParameter("target");
		String tValue = request.getParameter("targetValue");
		if(!StringUtil.equals(pwd, pwd2)){
			attr.addFlashAttribute("target", target);
			attr.addFlashAttribute("targetValue", tValue);
			attr.addFlashAttribute(ERROR_MESSAGE, "两次输入的密码不匹配");
			
			return "redirect:/forgetPwd/resetPwd";
		}
		
		try {
			verifyService.resetPassword(tValue, pwd);
		} catch (BusinessCheckException e) {
			e.printStackTrace();
			attr.addFlashAttribute("target", target);
			attr.addFlashAttribute("targetValue", tValue);
			attr.addFlashAttribute(ERROR_MESSAGE, e.getMessage());
			return "redirect:/forgetPwd/resetPwd";
		}
		
		return "/front/forgetpwd/reset_success";
	}
	
	/**
	 * 微博登录
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "weibologin", method = RequestMethod.GET)
	public String weibologin(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String host = request.getRequestURL().toString().replace(request.getRequestURI(), "");
		response.setContentType("text/html;charset=utf-8");
		try {
			response.sendRedirect("https://api.weibo.com/oauth2/authorize?client_id=1268671789&response_type=code&redirect_uri=" + host + "/weibologinwork.htm");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return LOGIN;
	}
	
	
	@RequestMapping(value = "weibologinwork", method = { RequestMethod.GET,
			RequestMethod.POST, RequestMethod.PUT })
	public String weibologinwork(String code, HttpServletRequest request,
			HttpServletResponse response, Model model) {
//		try {
//			User u = getUser(code);
//			Member member = memberService.weibologin(u);
//			if (member != null) {
//				HttpSession session = getSession(request);
//				session.setAttribute("member", member);
//				model.addAttribute("menu", 8);
//				return "member/index";
//			}
//		} catch (Exception e) {
//		}
		return "login";
	}
	
//	@RequestMapping(value = "qqlogin", method = RequestMethod.GET)
//	public String qqlogin(HttpServletRequest request,
//			HttpServletResponse response, Model model) {
//		response.setContentType("text/html;charset=utf-8");
//		try {
//			response.sendRedirect(new Oauth().getAuthorizeURL(request));
//		} catch (QQConnectException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return "login";
//	}
	
	
	private static String getUser(String code, Model model) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("client_id", "1268671789");
		params.put("client_secret", "af93ea51617ee5799e571c0b18bc6084");
		params.put("grant_type", "authorization_code");
		params.put("redirect_uri", "http://www.91mydoor.com/weibologinwork.htm");
		params.put("code", code);
		String result = RemoteUtil.getJsonResult("https://api.weibo.com/oauth2/access_token", "POST", params);
		if(StringUtil.isBlank(result)){
			model.addAttribute(ERROR_MESSAGE, "授权登录出错，请联系门道客服");
			return LOGIN;
		}else{
			JSONObject json = JSONObject.fromObject(result);
			String uid = json.getString("uid");
			String acces_token = json.getString("access_token");
		}
		
		return "";
	}
	
	private void setCookie(final HttpServletResponse response, 
			String key, String value, int maxAage){
		Cookie cookie = new Cookie(key, value);
		cookie.setPath("/");
		cookie.setMaxAge(maxAage);
		response.addCookie(cookie);
	}
	
	private void removeCookie(final HttpServletResponse response, String key){
		Cookie cookie = new Cookie(key, null);
		cookie.setPath("/");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}
	
	private Cookie getCookie(final HttpServletRequest request, String key){
		Cookie[] cookies = request.getCookies();
		if (cookies!=null){
			for(Cookie c : cookies){
				if(StringUtil.equals(key, c.getName())){
					return c;
				}
				
			}
		}
		return null;
	}
}
