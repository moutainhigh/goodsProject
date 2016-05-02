package com.mendao.framework.show;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
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
import com.mendao.business.entity.UserMessage;
import com.mendao.business.service.CategoryService;
import com.mendao.business.service.RecommendService;
import com.mendao.business.service.SchoolStickerService;
import com.mendao.business.service.UserActionService;
import com.mendao.business.service.UserMessageService;
import com.mendao.business.service.VerifyUserService;
import com.mendao.common.util.StringUtil;
import com.mendao.constant.MendaoConstant;
import com.mendao.entity.util.CategorySchoolUtil;
import com.mendao.entity.util.CodeUtil;
import com.mendao.exception.BusinessCheckException;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.base.jpa.ParamsUtil;
import com.mendao.framework.entity.ShopUser;
import com.mendao.framework.service.RoleService;
import com.mendao.framework.service.ShopUserService;
import com.mendao.remote.util.RemoteUtil;
import com.mendao.util.CacheUtil;
import com.mendao.util.RegexUtil;

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
	ShopUserService shopUserService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	private RecommendService recommendService;
	
	@Autowired
	private SchoolStickerService schoolStickerService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private UserMessageService userMessageService;
	
	@RequestMapping(value={"/", "/home"},method=RequestMethod.GET)
	public String index(final HttpSession session, Model model, HttpServletRequest request){
		
		UserMessage um = userMessageService.checkUser("123", "123");
		
		System.out.println(um.getUserName());
		
		UserMessage um1 = new UserMessage();
		um1.setNickName("111");
		um1.setUserName("zhaoyifan");
		um1.setUserType(1);
		um1.setStatus(1);
		um1.setCreateDate(new Date());
		um1.setExpirationDate(new Date());
		um1.setUserPwd("1232131");
		um1.setCode(CodeUtil.generateShortUuid());
		
		userMessageService.addUser(um1);
		
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
	public String register(final HttpServletRequest request, final HttpSession session, final Model model){
		model.addAttribute("message", "");
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
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		String nickName = request.getParameter("nickname");
		
		
		ShopUser shopUser = new ShopUser();
		shopUser.setNickName(nickName);
		shopUser.setUserName(nickName);
		shopUser.setPassword(password);
		shopUser.setPhone(phone);
		
		//校验账户注册信息
		String flag = checkUserMessage(shopUser);
		if(flag != null){
			model.addAttribute("message", flag);
//			attr.addFlashAttribute("message", flag);
			return REGISTER;
		}
		//设置用户注册角色
		shopUser.setRole(roleService.findById((long)1));
		// 提交注册信息
		shopUserService.register(shopUser);
		return LOGIN;	// TODO 注册完成欢迎页面？
		
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
	 * 检测用户注册信息
	 * @param shopUser
	 * @return
	 */
	private String checkUserMessage(ShopUser shopUser){
		//校验用户名格式
		if(StringUtil.isBlank(shopUser.getUserName())){
			return "账号不能为空";
		}
		//校验登录密码是否有效
		if(StringUtil.isBlank(shopUser.getPassword())){
			return "密码不能为空";
		}
		if(!RegexUtil.matchPassword(shopUser.getPassword())){
			return "您输入的密码格式不正确"; //用户账户为6至12个字符
		}
		List<ShopUser> suList = shopUserService.getUserByUserName(shopUser.getUserName());
		if(suList.size() > 0){
			return "您的用户名已经被注册，请重新输入";
		}
		if(!StringUtil.isBlank(shopUser.getPhone())){
			List<ShopUser> suPhoneList = shopUserService.getUserByPhone(shopUser.getPhone());
			if(suPhoneList.size() > 0){
				return "您的手机号码已经被注册，请重新输入";
			}
		}
		
		return null;
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
