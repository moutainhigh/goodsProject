package com.mendao.framework.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.mendao.business.dto.UserProfile;
import com.mendao.business.entity.UserInfo;
import com.mendao.business.service.UserActionService;
import com.mendao.business.service.VerifyUserService;
import com.mendao.exception.BusinessCheckException;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.base.jpa.ParamsUtil;
import com.mendao.framework.entity.FwAccount;
import com.mendao.framework.service.AccountService;
import com.mendao.framework.service.DutyService;
import com.mendao.framework.show.BaseController;
@Controller
@RequestMapping("/backend/user")
@SessionAttributes(types = UserInfo.class)
public class UserController extends BaseController{
	@Autowired
	private VerifyUserService verifyUserService;
	
	@Autowired
	UserActionService userActionService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private DutyService dutyService ;
	
	@RequestMapping(value = "")
	public String query(Model model, HttpServletRequest request) throws Exception {
		PageEntity<UserInfo> myPage = ParamsUtil.createPageEntityFromRequest(request, pageSize);
		myPage =  this.userActionService.getPage(myPage);
		model.addAttribute("pageBean", myPage);
		ParamsUtil.addAttributeModle(model, myPage);
		return "backend/user/user_list";
	}
	

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		model.addAttribute(new UserInfo());
		return "backend/user/user_edit";
	}


	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String processAdd(final Model model,@ModelAttribute UserInfo userInfo,
			BindingResult result, SessionStatus status) throws BusinessCheckException {
		return this.processEditUser(model,userInfo);
	}

	@RequestMapping(value = "/edit/{queryId}", method = RequestMethod.GET)
	public String edit(@PathVariable("queryId") Long id, Model model) {
		UserInfo user = userActionService.findOneUser(id);
		model.addAttribute(user);
		return "backend/user/user_edit";
	}

	@RequestMapping(value = "/edit/{queryId}", method = RequestMethod.POST)
	public String processEditUser(final Model model,@ModelAttribute UserInfo userInfo) throws BusinessCheckException {
		UserProfile profile = new UserProfile();
		profile.setUserInfo(userInfo);
		if(null == userInfo.getId() || userInfo.getId() == 0L){
			verifyUserService.register(profile);
		}else{
			userActionService.updateUserInfo(profile);//.updateAccount(account);
		}
		return "redirect:/backend/user";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(HttpServletRequest request) throws Exception {
		   String[] ids = request.getParameterValues("id");
		List<FwAccount> accounts = accountService.findDatasByIds(ids);
		if (accounts != null && accounts.size() > 0) {
			accountService.deleteAll(accounts);
		}
		return "redirect:/backend/user/query";
	}
	

//	@RequestMapping(value ="/assign", method = RequestMethod.GET)
//    public String assignDuty(@RequestParam("queryId") Long id, Model model, HttpServletRequest request) {
//
//		Set<FwAccountDuty> fw = accountService.getAccountById(id).getFwAccountDuties();
//		List<Long> dutys = new ArrayList<Long>();
//		if (fw != null) {
//			Iterator<FwAccountDuty> it = fw.iterator();
//			while (it.hasNext()) {
//				dutys.add(it.next().getFwDuty().getId());
//			}
//		}
//		model.addAttribute("dutys", dutys);
//		PageEntity<FwDuty> myPage = ParamsUtil.createPageEntityFromRequest(request, 20);
//		myPage = this.dutyService.getPage(myPage);
//		model.addAttribute("pageBean", myPage);
//		ParamsUtil.addAttributeModle(model, myPage);
//		model.addAttribute("account", accountService.getAccountById(id));
//		return "admin/account/duty_assign";
//    }
//
//    @RequestMapping(value = "/assign", method = RequestMethod.POST)
//    public String processAssignGroup(@RequestParam("accountId") Long accountId,
//            @RequestParam(value = "dutyId", required = false) Long[] dutyId) {
//        if (null == dutyId || dutyId.length == 0) {
//            accountService.deleteAccount(accountId,dutyId);
//            return "redirect:/c/user/query";
//        } else {
//            accountService.processAssignDuty(accountId, dutyId);
//            return "redirect:/c/user/query";
//        }
//    }
}
