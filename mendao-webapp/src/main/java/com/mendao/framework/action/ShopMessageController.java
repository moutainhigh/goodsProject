package com.mendao.framework.action;




import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;



import com.mendao.business.entity.ShopMessage;
import com.mendao.business.service.ShopMessageService;
import com.mendao.entity.util.QRCode;
import com.mendao.framework.enums.UserUtil;
import com.mendao.framework.service.ShopUserService;
import com.mendao.framework.show.BaseController;
import com.mendao.util.PropertiesUtil;

@Controller
@RequestMapping("/back/shop/")
@SessionAttributes(types = UserUtil.class)
public class ShopMessageController extends BaseController{
	
	@Autowired
	ShopMessageService shopMessageService;
	
	@Autowired
	ShopUserService shopUserService;
	
	@RequestMapping(value = "myshop")
	public String query(Model model, HttpServletRequest request) throws Exception {
		UserUtil userUtil = super.getSessionUser(request.getSession());
		ShopMessage shopMessage = shopMessageService.findByUserId(userUtil.getId());
		//如果店铺链接不存在，自动生成
		if(shopMessage == null){
			ShopMessage sm = new ShopMessage();
			sm.setUser(shopUserService.findById(userUtil.getId()));
			sm.setShopUrl(PropertiesUtil.getProperty("service.cdn")+"/f/"+userUtil.getId());
			sm.setQrcodeUrl(PropertiesUtil.getProperty("service.cdn")+QRCode.createQRcode(sm.getShopUrl()));
			sm.setShopPwd("111111");
			sm.setCreateDate(new Date());
			sm = shopMessageService.save(sm);
			model.addAttribute("shopMessage", sm);
		}else{
			model.addAttribute("shopMessage", shopMessage);
		}
		return "shop/myshop";
	}
	
	@ResponseBody
	@RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
	public Map<String,Object> save(Model model, HttpServletRequest request) {
		Map<String,Object> result = new HashMap<String, Object>();
		String pwd = request.getParameter("password");
		String shopName = request.getParameter("shopName");
		String notice = request.getParameter("notice");
		UserUtil userUtil = super.getSessionUser(request.getSession());
		try{
			ShopMessage shopMessage = shopMessageService.findByUserId(userUtil.getId());
			if(pwd != null && !pwd.equals("")){
				shopMessage.setShopPwd(pwd);
			}
			if(shopName != null && !shopName.equals("")){
				shopMessage.setShopName(shopName);
			}
			if(notice != null && !notice.equals("")){
				shopMessage.setNotice(notice);
			}
			shopMessageService.update(shopMessage);
			result.put("status", 1);
			result.put("msg", "修改成功");
		}catch(Exception e){
			result.put("status", 0);
			result.put("msg", "修改成功");
		}
		return result;
	}
	
	
}
