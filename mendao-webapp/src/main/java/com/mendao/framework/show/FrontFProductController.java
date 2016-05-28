package com.mendao.framework.show;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mendao.business.entity.FProduct;
import com.mendao.business.entity.PKind;
import com.mendao.business.entity.ProductPic;
import com.mendao.business.entity.ShopMessage;
import com.mendao.business.service.DFUserRelationService;
import com.mendao.business.service.ProductPicService;
import com.mendao.business.service.ProductService;
import com.mendao.business.service.ShopMessageService;
import com.mendao.entity.util.FProductUtil;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.base.jpa.ParamsUtil;
import com.mendao.framework.enums.UserUtil;
import com.mendao.framework.service.ShopUserService;
import com.mendao.framework.show.BaseController;


@Controller
@RequestMapping("/front/fproduct")
@SessionAttributes(types = UserUtil.class)
public class FrontFProductController extends BaseController {
	
	@Autowired
	ProductService productService;
	@Autowired
	ShopUserService shopUserService;
	
	@Autowired
	ProductPicService productPicService;
	
	@Autowired
	DFUserRelationService dFUserRelationService;
	
	@Autowired
	ShopMessageService shopMessageService;
	
	@RequestMapping(value = "index/{id}", method = RequestMethod.GET)
	public String index(@PathVariable("id") Long id,Model model, HttpServletRequest request) throws Exception {
		
		return "f/index";
	}
	
	@RequestMapping(value = "index/{id}", method = RequestMethod.POST)
	public String indexPost(@PathVariable("id") Long id,Model model, HttpServletRequest request, final HttpSession session) throws Exception {
		ShopMessage shopMessage = shopMessageService.findByUserId(id);
		String password = request.getParameter("password");
		if(shopMessage != null && password.equals(shopMessage.getShopPwd())){
			//将用户访问情况记录到session
			session.setAttribute("USER_ACCESS", "1");
			return "redirect:/front/fproduct/list/"+id;
		}else{
			model.addAttribute("message","输入有误，请重新输入。");
		}
		return "f/index";
	}
	
	@RequestMapping(value = "list/{id}")
	public String list(@PathVariable("id") Long id,Model model, HttpServletRequest request) throws Exception {
		List<PKind> kindList = productService.queryAllByYewuId(id);
		String flag = (String) request.getSession().getAttribute("USER_ACCESS");
		if(flag != null && flag.equals("1")){
			model.addAttribute("id",id);
			model.addAttribute("kindList",kindList);
			return "f/front_list";
		}else{
			return "redirect:/front/fproduct/index/"+id;
		}
	}
	
	@RequestMapping(value = "getItem/{id}")
	public String getItem(@PathVariable("id") Long id,Model model, HttpServletRequest request) throws Exception {
		PageEntity<FProduct> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 1);
		String kindId = request.getParameter("kindId");
		Map<String, Object> params = new HashMap<String, Object>();
		if(kindId != null && !kindId.equals("")){
			params.put("kindId", kindId);
		}
		params.put("modifyUserId.id", id);
		params.put("onSale", 1);
		params.put("deleteFlag", 0);
		pageEntity.setParams(params);
		pageEntity =  this.productService.getFProductPage(pageEntity);
		List<FProductUtil> fProductList = new ArrayList<FProductUtil>();
		for(FProduct fProduct : pageEntity.getResult()){
			FProductUtil fProductUtil = new FProductUtil();
			BeanUtils.copyProperties(fProduct, fProductUtil);
			List<ProductPic> picList = new ArrayList<ProductPic>();
			picList = productPicService.getPicByFProductId(fProduct.getId());
			if(picList != null && picList.size() > 0){
				fProductUtil.setImageList(picList);
				fProductUtil.setFirstImage(picList.get(0).getImageUrl());
			}
			fProductList.add(fProductUtil);
		}
		model.addAttribute("pageBean", pageEntity);
		model.addAttribute("id", id);
		model.addAttribute("list", fProductList);
		return "f/front_product_item";
	}
	
	@RequestMapping(value = "detail/{id}/{yewuId}")
	public String detail(@PathVariable("id") Long id,@PathVariable("yewuId") Long yewuId,Model model, HttpServletRequest request) throws Exception {
		String flag = (String) request.getSession().getAttribute("USER_ACCESS");
		if(flag != null && flag.equals("1")){
			FProduct fProduct = this.productService.getDProductById(id);
			FProductUtil fProductUtil = new FProductUtil();
			BeanUtils.copyProperties(fProduct, fProductUtil);
			List<ProductPic> picList = new ArrayList<ProductPic>();
			picList = productPicService.getPicByFProductId(fProduct.getId());
			if(picList != null && picList.size() > 0){
				fProductUtil.setImageList(picList);
				fProductUtil.setFirstImage(picList.get(0).getImageUrl());
			}
			model.addAttribute("fProduct", fProductUtil);
			//获取该业务的其他产品
			List<FProduct> fpList = productService.getByModifyUserId(fProduct.getModifyUserId().getId(),id,6);
			List<FProductUtil> ftUtilList = new ArrayList<FProductUtil>();
			for(FProduct fp:fpList){
				FProductUtil fpu = new FProductUtil();
				BeanUtils.copyProperties(fp, fpu);
				List<ProductPic> picUtilList = productPicService.getPicByFProductId(fp.getId());
				if(picUtilList != null && picUtilList.size() > 0){
					fpu.setImageList(picUtilList);
					fpu.setFirstImage(picUtilList.get(0).getImageUrl());
				}
				ftUtilList.add(fpu);
			}
			model.addAttribute("ftList", ftUtilList);
			model.addAttribute("id", yewuId);
			return "f/front_product-detail";
		}else{
			return "redirect:/front/fproduct/index/"+yewuId;
		}
	}
}
