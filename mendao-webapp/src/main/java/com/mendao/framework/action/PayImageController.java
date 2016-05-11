package com.mendao.framework.action;



import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mendao.business.entity.PayImage;
import com.mendao.business.service.PayImageService;
import com.mendao.framework.entity.ShopUser;
import com.mendao.framework.enums.UserUtil;
import com.mendao.framework.show.BaseController;

@Controller
@RequestMapping("/back/pay/")
@SessionAttributes(types = UserUtil.class)
public class PayImageController extends BaseController{
	@Autowired
	PayImageService payImageService;
	
	@RequestMapping(value = "image")
	public String query(Model model, HttpServletRequest request) throws Exception {
		List<PayImage> list = payImageService.getAll();
		if(list.size() > 0){
			model.addAttribute("payImage", list.get(0));
		}else{
			model.addAttribute("payImage", new PayImage());
		}
		return "pay/pay_image";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Model model, HttpServletRequest request, @ModelAttribute PayImage payImage) {
		payImage.setCreateDate(new Date());
		payImage = payImageService.save(payImage);
		return "redirect:/back/pay/image";
	}
	
	
}
