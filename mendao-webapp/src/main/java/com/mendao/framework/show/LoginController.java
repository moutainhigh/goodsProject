package com.mendao.framework.show;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mendao.common.util.StringUtil;
import com.mendao.constant.MendaoConstant;
import com.mendao.exception.BusinessCheckException;
import com.mendao.framework.entity.Question;
import com.mendao.framework.entity.ShopUser;
import com.mendao.framework.entity.UserQuestion;
import com.mendao.framework.enums.UserUtil;
import com.mendao.framework.service.QuestionService;
import com.mendao.framework.service.RoleService;
import com.mendao.framework.service.ShopUserService;
import com.mendao.framework.service.UserQuestionService;
import com.mendao.util.CacheUtil;
import com.mendao.util.RegexUtil;


/**
 * 登录Controller
 * @author libra
 *
 */
@Controller
public class LoginController extends BaseController{
	
	private static final Logger _logger = Logger.getLogger(LoginController.class);
	private static final String VERIFY_CODE = "VerifyCode"; 
	private static final String REDIRECT_HOME = "redirect:/index";
	private static final String COOKIE_UNAME = "md_uname_cookie";
	private static final String PAGE_TOKEN = "token";
	
	
	@Autowired
	ShopUserService shopUserService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	QuestionService questionService;
	
	@Autowired
	private UserQuestionService  userQuestionService;
	
	
	@RequestMapping(value={"/", "/home"},method=RequestMethod.GET)
	public String index(final HttpSession session, Model model, HttpServletRequest request){
		return "home";
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
	public String doLogin(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response, final Model model){
		
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		
		//完成用户登录操作
		UserUtil userUtil = shopUserService.login(userName,password);
		if(userUtil.getMessage() != null && !userUtil.getMessage().equals("")){
			model.addAttribute("username", userName);
			model.addAttribute("message", userUtil.getMessage());
			return "home";
		}
		//将用户数据写入Session
		super.setSessionUser(session, userUtil);
		return REDIRECT_HOME;
	}
	
	
	/**
	 * 注册页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/register"},method=RequestMethod.GET)
	public String register(final HttpServletRequest request, final HttpSession session, final Model model){
		model.addAttribute("message", null);
		String uuid = request.getParameter("uuid");
		model.addAttribute("uuid", uuid);
		List<Question> list = questionService.getAllQuestion();
		model.addAttribute("list", list);
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
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String userName = request.getParameter("username");
		String nickName = request.getParameter("nickname");
		String uuid = request.getParameter("uuid");
		String questionId = request.getParameter("questionId");
		String answer = request.getParameter("answer");
		String role = request.getParameter("optionsRole");
		
		ShopUser shopUser = new ShopUser();
		shopUser.setNickName(nickName);
		shopUser.setUserName(userName);
		shopUser.setPassword(password);
		shopUser.setPhone(phone);
		shopUser.setEmail(email);
		
		//校验账户注册信息
		String flag = checkUserMessage(shopUser);
		if(flag != null){
			model.addAttribute("message", flag);
			model.addAttribute("uuid", uuid);
			model.addAttribute("user", shopUser);
			return REGISTER;
		}
		//后台校验角色
		if(role == null || role.equals("")){
			model.addAttribute("message", "请选择角色");
			model.addAttribute("uuid", uuid);
			return REGISTER;
		}
		//设置用户注册角色
		shopUser.setRole(roleService.findById(Long.valueOf(role)));
		// 提交注册信息
		shopUser = shopUserService.register(shopUser,uuid);
		//增加用户安全问题
		if(questionId != null && answer != null){
			UserQuestion uq = new UserQuestion();
			uq.setUser(shopUser);
			uq.setQuestion(questionService.findById(Long.valueOf(questionId)));
			uq.setAnswer(answer);
			userQuestionService.addUserQuestion(uq);
		}
		return LOGIN;
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
	 * 跳转到找回密码页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/forgetPwd",method=RequestMethod.GET)
	public String forgetPassword(final Model model){
		List<Question> list = questionService.getAllQuestion();
		model.addAttribute("list", list);
		return "forget_pwd";
	}
	
	@RequestMapping(value="/forgetPwd",method=RequestMethod.POST)
	public String forgetPasswordPost(final HttpServletRequest request,final Model model){
		String userName = request.getParameter("username");
		String questionId = request.getParameter("questionId");
		String answer = request.getParameter("answer");
		List<ShopUser> suList = shopUserService.getUserByUserName(userName);
		String flag = checkUserForgetPwd(userName,questionId,answer);
		if(flag != null){
			List<Question> list = questionService.getAllQuestion();
			model.addAttribute("list", list);
			model.addAttribute("message", flag);
			return "forget_pwd";
		}
		if(suList.size() == 0){
			model.addAttribute("message", "该用户不存在，请重新输入。");
			List<Question> list = questionService.getAllQuestion();
			model.addAttribute("list", list);
			model.addAttribute("username", userName);
			return "forget_pwd";
		}else{
			List<UserQuestion> uqList = userQuestionService.findByUserId(suList.get(0).getId());
			if(uqList.size() == 0){
				model.addAttribute("message", "安全问题不存在");
				List<Question> list = questionService.getAllQuestion();
				model.addAttribute("username", userName);
				model.addAttribute("list", list);
				return "forget_pwd";
			}else{
				UserQuestion uq = uqList.get(0);
				if(uq.getQuestion().getId() != Long.valueOf(questionId) || !uq.getAnswer().equals(answer)){
					model.addAttribute("message", "安全问题不正确，请重新输入。");
					List<Question> list = questionService.getAllQuestion();
					model.addAttribute("username", userName);
					model.addAttribute("list", list);
					return "forget_pwd";
				}
			}
			model.addAttribute("id", suList.get(0).getId());
			return "/reset_pwd";
		}
		
	}
	
	@RequestMapping(value="/resetPassword",method=RequestMethod.POST)
	public String resetPassword(final HttpServletRequest request, final Model model){
		String password = request.getParameter("password");
		String repassword = request.getParameter("repassword");
		String id = request.getParameter("id");
		if(password.equals(repassword)){
			shopUserService.resetPasswordById(Long.valueOf(id),password);
			return "/reset_suc";
		}else{
			model.addAttribute("message", "两次输入密码不一致。");
			model.addAttribute("id", id);
			return "/reset_pwd";
		}
	}
	
	
	/**
	 * 用户管理主页面
	 * @param session
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String userIndex(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response, final Model model){
		UserUtil userutil = super.getSessionUser(request.getSession());
		if(userutil != null){
			return "index";
		}else{
			return "/home";
		}
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
		if(!StringUtil.isBlank(shopUser.getEmail())){
			List<ShopUser> suPhoneList = shopUserService.getUserByEmail(shopUser.getEmail());
			if(suPhoneList.size() > 0){
				return "您的邮箱已经被注册，请重新输入";
			}
		}
		return null;
	}
	/**
	 * 检验用户找回密码输入的信息
	 * @param userName
	 * @param questionId
	 * @param answer
	 * @return
	 */
	private String checkUserForgetPwd(String userName,String questionId,String answer){
		//校验用户名格式
		if(StringUtil.isBlank(userName)){
			return "账号不能为空";
		}
		if(StringUtil.isBlank(questionId)){
			return "请选择找回密码安全问题";
		}
		if(StringUtil.isBlank(answer)){
			return "请输入问题答案";
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
