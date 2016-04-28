package com.mendao.framework.show;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mendao.business.dto.Drawing;
import com.mendao.business.dto.UserProfile;
import com.mendao.business.entity.UserInfo;
import com.mendao.business.service.UserActionService;
import com.mendao.business.service.VerifyUserService;
import com.mendao.exception.BusinessCheckException;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.base.jpa.ParamsUtil;
import com.mendao.framework.entity.FwAccountIO;
import com.mendao.util.RegexUtil;

import jodd.util.StringUtil;

@Controller
@RequestMapping("/front/m/account")
public class UserAccountFrontController extends BaseController{
	
	@Autowired
	UserActionService userActionService;
	
	@Autowired
	VerifyUserService verifyService;
	
	/**
	 * 我的账户－－账户余额
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String userAccount(final HttpServletRequest request, final Model model){		
		return "front/user/account/index";
	}
	
	/**
	 * 我的账户－－账户余额
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public String userAccountInfo(final HttpServletRequest request, final Model model){
		
		UserProfile profile = getSessionUser(request.getSession());
		UserInfo user = userActionService.findOneUser(profile.getId());
		profile.setUserInfo(user);
		model.addAttribute("user", user);
		return "front/user/account/account";
	}
	
	/**
	 * 我的账户－－我要付款
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/payment", method = RequestMethod.GET)
	public String userAccountPayment(final HttpServletRequest request, final Model model){
		return "front/user/account/payment";
	}
	
	/**
	 * 我的账户－－我要付款－－支付密码
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/payment", method = RequestMethod.POST)
	public String userAccountPayment(final HttpServletRequest request, final Model model,
			RedirectAttributes attr){
		
		String targetUser = request.getParameter("targetName");
		String amount = request.getParameter("amount");
		if(StringUtil.isBlank(targetUser) || StringUtil.isBlank(amount)){
			attr.addFlashAttribute("targetName", targetUser);
			attr.addFlashAttribute("amount", amount);
			attr.addFlashAttribute(ERROR_MESSAGE, "请输入对方账户和付款金额");
			return "redirect:/front/account/payment";
		}
		
		if(!RegexUtil.matchMoney(amount)){
			attr.addFlashAttribute("targetName", targetUser);
			attr.addFlashAttribute("amount", amount);
			attr.addFlashAttribute(ERROR_MESSAGE, "付款金额格式错误");
			return "redirect:/front/account/payment";
		}
		
		/************************************************************************/
		/**	检查目标用户是否存在					       						   **/
		/************************************************************************/
		UserProfile target = userActionService.findUserInfoByAcctName(targetUser);
		if(null == target){
			attr.addFlashAttribute("targetName", targetUser);
			attr.addFlashAttribute("amount", amount);
			attr.addFlashAttribute(ERROR_MESSAGE, "对方账号有误");
			return "redirect:/front/m/account/payment";
		}
		
		/************************************************************************/
		/**	检查当前用户余额     					       						   **/
		/************************************************************************/
		UserProfile profile = getSessionUser(request.getSession());
		UserInfo user = userActionService.findOneUser(profile.getId());
		profile.setUserInfo(user);
		BigDecimal fund = new BigDecimal(amount);
		if(fund.compareTo(user.getFwAccount().getFund()) > 0){
			attr.addFlashAttribute("targetName", targetUser);
			attr.addFlashAttribute("amount", amount);
			attr.addFlashAttribute(ERROR_MESSAGE, "您的余额不足");
			return "redirect:/front/m/account/payment";
		}
		
		model.addAttribute("targetId", target.getId());
		model.addAttribute("amount", amount);
		return "front/user/account/payment_confirm";
	}
	
	/**
	 * 我的账户－－我要付款－－支付成功／失败
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/paymentConfirm", method = RequestMethod.POST)
	public Map<String, Object> userAccountConfirmPayment(final HttpServletRequest request, final Model model,
			RedirectAttributes attr){
		Map<String, Object> result = new HashMap<String, Object>();
		boolean success = true;
		
		/***********************************************/
		/**	检查当前用户密码     					      **/
		/***********************************************/
		UserProfile profile = getSessionUser(request.getSession());
		try {
			verifyService.verifyUserSecurity(profile.getId(), request.getParameter("security"));
		} catch (BusinessCheckException e) {
			result.put("message", "密码错误");
			success = false;
		}
		
		/***********************************************/
		/**	用户付款          					      **/
		/***********************************************/
		Long targetId = Long.valueOf(request.getParameter("targetId"));
		BigDecimal amount = new BigDecimal(request.getParameter("amount"));
		try {
			userActionService.payment(profile.getId(), targetId, amount);
		} catch (BusinessCheckException e) {
			result.put("message", e.getMessage());
			success = false;
		}
		
		if(success){
			result.put("mark", true);
			result.put("message", "付款成功");
		}else{
			result.put("mark", false);
		}
		return result;
	}
	
	
	/**
	 * 我的账户－－我要收款
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/gathering", method = RequestMethod.GET)
	public String userAccountGathering(final HttpServletRequest request, final Model model,
			RedirectAttributes attr){
		return "front/user/account/gathering";
	}
	
	/**
	 * 我的账户－－交易记录
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/transaction", method = RequestMethod.GET)
	public String userAccountTransaction(final HttpServletRequest request, final Model model){
		UserProfile profile = getSessionUser(request.getSession());
		PageEntity<FwAccountIO> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 10);
		pageEntity.getParams().put("fwAccount.id", profile.getId());
		pageEntity = userActionService.getPageOfAccountIO(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		return "front/user/account/transaction";
	}
	
	@RequestMapping(value = "/drawing", method = RequestMethod.GET)
	public String userAccountDrawing(final HttpServletRequest request, final Model model){
		return "front/user/account/drawing";
	}
	
	@RequestMapping(value = "/drawing", method = RequestMethod.POST)
	public String userAccountDrawing(final HttpServletRequest request, final Model model, RedirectAttributes attr){
		String bankName = request.getParameter("bankName");
		String bankNum = request.getParameter("bankNum");
		String acctName = request.getParameter("acctName");
		String amount = request.getParameter("amount");
		attr.addFlashAttribute("bankName", bankName);
		attr.addFlashAttribute("bankNum", bankNum);
		attr.addFlashAttribute("acctName", acctName);
		attr.addFlashAttribute("amount", amount);
		
		if(!RegexUtil.matchMoney(amount)){
			attr.addFlashAttribute(ERROR_MESSAGE, "提现金额格式不正确");
		}
		
		model.addAttribute("bankName", bankName);
		model.addAttribute("bankNum", bankNum);
		model.addAttribute("acctName", acctName);
		model.addAttribute("amount", new BigDecimal(amount));
		
		return "front/user/account/drawing_confirm";
	}
	
	@ResponseBody
	@RequestMapping(value = "/drawing/confirm", method = RequestMethod.POST)
	public Map<String, Object> userAccountDrawingConfirm(final HttpServletRequest request, final Model model
			, RedirectAttributes attr){
		Map<String, Object> result = new HashMap<String, Object>();
		String bankName = request.getParameter("bankName");
		String bankNum = request.getParameter("bankNum");
		String acctName = request.getParameter("acctName");
		String amount = request.getParameter("amount");
		String password = request.getParameter("security");
		try{
			Drawing drawing = new Drawing();
			drawing.setAmount(new BigDecimal(amount));
			drawing.setBankAcctName(acctName);
			drawing.setBankName(bankName);
			drawing.setBankNum(bankNum);
			userActionService.applyDrawing(getSessionUserId(request.getSession()), drawing, password);
			result.put("mark", true);
		}catch (BusinessCheckException e) {
			result.put("mark", false);
			result.put("message", e.getMessage());
		}
		
		return result;
	}
}
