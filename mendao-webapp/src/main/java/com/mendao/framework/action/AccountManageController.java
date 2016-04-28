package com.mendao.framework.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mendao.business.service.UserActionService;
import com.mendao.common.enums.AccountIOEnum;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.base.jpa.ParamsUtil;
import com.mendao.framework.entity.FwAccountIO;
import com.mendao.framework.show.BaseController;

@Controller
@RequestMapping("/backend/account")
public class AccountManageController extends BaseController{

	@Autowired
	UserActionService userActionService;
	/**
	 * 获取账户资金流动列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
	public String index(final HttpServletRequest request, final Model model){
		PageEntity<FwAccountIO> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 10);
		String ioType = request.getParameter("q_ioType");
		pageEntity.getParams().put("ioType", AccountIOEnum.valueOf(ioType));
		pageEntity = userActionService.getPageOfAccountIO(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		
		return "/backend/account/index";
	}
	
	@ResponseBody
	@RequestMapping(value = "/drawing/approve")
	public Map<String, Object> approveDrawing(final HttpServletRequest request, final Model model
			, @RequestParam("id") Long id){
		Map<String, Object> result = new HashMap<String, Object>();
		
		userActionService.approveDrawing(id);
		result.put("mark", true);
		return result;
	}
}
