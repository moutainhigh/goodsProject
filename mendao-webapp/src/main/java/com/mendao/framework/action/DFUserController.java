package com.mendao.framework.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mendao.business.entity.DFUserRelation;
import com.mendao.business.entity.DProduct;
import com.mendao.business.service.DFUserRelationService;
import com.mendao.business.service.ProductService;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.base.jpa.ParamsUtil;
import com.mendao.framework.entity.ShopUser;
import com.mendao.framework.enums.UserUtil;
import com.mendao.framework.service.ShopUserService;
import com.mendao.framework.show.BaseController;

@Controller
@RequestMapping("/df/user")
@SessionAttributes(types = UserUtil.class)
public class DFUserController extends BaseController {
	
	@Autowired
	ShopUserService shopUserService;
	
	@Autowired
	DFUserRelationService dFUserRelationService;
	
	@RequestMapping(value = "list")
	public String query(Model model, HttpServletRequest request) throws Exception {
		@SuppressWarnings("unchecked")
		PageEntity<DFUserRelation> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 10);
		pageEntity.getParams().put("parent.id", 3);
		pageEntity.getParams().put("status", 2);
		pageEntity =  this.dFUserRelationService.getPage(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		return "df/user_list";
	}
	
	@RequestMapping(value = "getAllFenxiao")
	public String getAllF(Model model, HttpServletRequest request) throws Exception {
		PageEntity<ShopUser> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 10);
		List<Long> list = new ArrayList<Long>();
		UserUtil userUtil = super.getSessionUser(request.getSession());
		//获取已经添加的分销商的ID列表
		list = this.dFUserRelationService.getChildId(userUtil.getId());
		if(list.size() > 0){
			pageEntity.getParams().put("id_notin", list);
		}
		pageEntity.getParams().put("role.id", (long)3);
		pageEntity =  this.shopUserService.getFenxiaoPage(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		return "df/add_user_list";
	}
	
	
}
