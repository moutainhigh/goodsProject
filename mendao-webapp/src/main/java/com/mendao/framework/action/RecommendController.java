package com.mendao.framework.action;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mendao.business.entity.PayImage;
import com.mendao.business.entity.PayMessage;
import com.mendao.business.service.PayImageService;
import com.mendao.entity.util.UserRelationUtil;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.base.jpa.ParamsUtil;
import com.mendao.framework.entity.ShopUser;
import com.mendao.framework.entity.UserRelation;
import com.mendao.framework.enums.UserUtil;
import com.mendao.framework.service.RecommendService;
import com.mendao.framework.show.BaseController;

@Controller
@RequestMapping("/back/my/")
@SessionAttributes(types = UserUtil.class)
public class RecommendController extends BaseController{
	
	@Autowired
	RecommendService recommendService;
	
	@RequestMapping(value = "recommend")
	public String query(Model model, HttpServletRequest request) throws Exception {
		PageEntity<UserRelation> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 10);
		UserUtil userUtil = super.getSessionUser(request.getSession());
		pageEntity.getParams().put("parent.id", userUtil.getId());
		pageEntity =  this.recommendService.getPage(pageEntity);
		List<UserRelationUtil> urList = new ArrayList<UserRelationUtil>();
		for(UserRelation ur:pageEntity.getResult()){
			UserRelationUtil userRelationUtil = new UserRelationUtil();
			BeanUtils.copyProperties(ur,userRelationUtil);
			int day = (int) (((new Date()).getTime()-ur.getCurrentUser().getCreateDate().getTime())/1000/60/60/24);
			if(day > 0){
				userRelationUtil.setUseDay(day);
			}else{
				userRelationUtil.setUseDay(0);
			}
			urList.add(userRelationUtil);
		}
		model.addAttribute("urList", urList);
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		return "my_recommend";
	}
	
}
