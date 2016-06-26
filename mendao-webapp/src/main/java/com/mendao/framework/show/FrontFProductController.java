package com.mendao.framework.show;

import java.util.ArrayList;
import java.util.Date;
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

import com.mendao.business.entity.DFUserRelation;
import com.mendao.business.entity.FProduct;
import com.mendao.business.entity.PKind;
import com.mendao.business.entity.ProductPic;
import com.mendao.business.entity.ShopMessage;
import com.mendao.business.entity.SystemSwitch;
import com.mendao.business.service.DFUserRelationService;
import com.mendao.business.service.ProductPicService;
import com.mendao.business.service.ProductService;
import com.mendao.business.service.ShopMessageService;
import com.mendao.business.service.SystemSwitchService;
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
	
	@Autowired
	SystemSwitchService systemSwitchService;
	
	@RequestMapping(value = "index/{id}", method = RequestMethod.GET)
	public String index(@PathVariable("id") Long id,Model model, HttpServletRequest request) throws Exception {
		
		return "f/index";
	}
	
	@RequestMapping(value = "index/{id}", method = RequestMethod.POST)
	public String indexPost(@PathVariable("id") Long id,Model model, HttpServletRequest request, final HttpSession session) throws Exception {
		//判断管理员是否关闭系统
		List<SystemSwitch> list = systemSwitchService.getAll();
		if(list!= null && list.size() > 0){
			if(list.get(0).getStatus() == 0){
				model.addAttribute("message", list.get(0).getMessage());
				return "f/index";
			}
		}
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
			ShopMessage shopMessage = shopMessageService.findByUserId(id);
			model.addAttribute("id",id);
			model.addAttribute("kindList",kindList);
			model.addAttribute("shopMessage", shopMessage);
			return "f/front_list";
		}else{
			return "redirect:/front/fproduct/index/"+id;
		}
	}
	
	@RequestMapping(value = "getItem/{id}")
	public String getItem(@PathVariable("id") Long id,Model model, HttpServletRequest request) throws Exception {
		PageEntity<FProduct> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 10);
		String kindId = request.getParameter("kindId");
		String pName = request.getParameter("pName");
		Map<String, Object> params = new HashMap<String, Object>();
		if(kindId != null && !kindId.equals("")){
			params.put("kindId", kindId);
		}
		if(pName != null && !pName.equals("")){
			params.put("pName", pName);
		}
		params.put("modifyUserId.id", id);
		params.put("onSale", 1);
		params.put("deleteFlag", 0);
		params.put("createUserId.endDate", (new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
		pageEntity.setParams(params);
		pageEntity =  this.productService.getFProductPageBySql(pageEntity);
		List<FProductUtil> fProductList = new ArrayList<FProductUtil>();
		for(FProduct fProduct : pageEntity.getResult()){
			FProductUtil fProductUtil = new FProductUtil();
			BeanUtils.copyProperties(fProduct, fProductUtil);
			if(fProductUtil.getpName().length() > 10){
				fProductUtil.setpName(fProductUtil.getpName().substring(0, 10)+"...");
			}
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
			//获取产品的标签
			List<PKind> kindList = productService.getKindByIds(fProduct.getKindId());
			StringBuffer sb = new StringBuffer();
			if(kindList != null && kindList.size()>0){
				for(PKind kl:kindList){
					sb.append(kl.getKindName());
					sb.append(",");
				}
			}
			if(sb.toString().length() >= 1){
				fProductUtil.setKindString(sb.toString().substring(0, sb.toString().length()-1));
			}
			//重置产品的视频
			if(fProductUtil.getVideoUrl() !=null && !"".equals(fProductUtil.getVideoUrl())){
				
			}else{
				fProductUtil.setVideoUrl("");
			}
			model.addAttribute("fProduct", fProductUtil);
			//获取该业务的其他产品
			List<FProduct> fpList = productService.getByModifyUserId(fProduct.getModifyUserId().getId(),id,6);
			List<FProductUtil> ftUtilList = new ArrayList<FProductUtil>();
			for(FProduct fp:fpList){
				FProductUtil fpu = new FProductUtil();
				BeanUtils.copyProperties(fp, fpu);
				if(fpu.getpName() != null && fpu.getpName().length() > 10){
					fpu.setpName(fpu.getpName().substring(0, 10)+"...");
				}
				List<ProductPic> picUtilList = productPicService.getPicByFProductId(fp.getId());
				if(picUtilList != null && picUtilList.size() > 0){
					fpu.setImageList(picUtilList);
					fpu.setFirstImage(picUtilList.get(0).getImageUrl());
				}
				ftUtilList.add(fpu);
			}
			model.addAttribute("ftList", ftUtilList);
			model.addAttribute("id", yewuId);
			//获取业务的代理标签
			List<DFUserRelation>  dfuserRelationList = dFUserRelationService.getByProperty(fProduct.getCreateUserId().getId(), fProduct.getModifyUserId().getId());
			if(dfuserRelationList != null && dfuserRelationList.size()>0){
				model.addAttribute("dailiDesc", dfuserRelationList.get(0).getDesc());
			}
			return "f/front_product-detail";
		}else{
			return "redirect:/front/fproduct/index/"+yewuId;
		}
	}
	/**
	 * 获取该店其他在售产品
	 * @param id
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getOtherItem/{id}/{productId}")
	public String getOtherItem(@PathVariable("id") Long id,@PathVariable("productId") Long productId,Model model, HttpServletRequest request) throws Exception {
		PageEntity<FProduct> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 4);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("modifyUserId.id", id);
		params.put("onSale", 1);
		params.put("id_notin", productId);
		params.put("deleteFlag", 0);
		params.put("dProduct.status", 1);
		params.put("dProduct.deleteFlag", 0);
		params.put("createUserId.endDate_s", new Date());
		pageEntity.setParams(params);
		pageEntity =  this.productService.getFProductPage(pageEntity);
		List<FProductUtil> fProductList = new ArrayList<FProductUtil>();
		for(FProduct fProduct : pageEntity.getResult()){
			FProductUtil fProductUtil = new FProductUtil();
			BeanUtils.copyProperties(fProduct, fProductUtil);
			if(fProductUtil.getpName().length() > 10){
				fProductUtil.setpName(fProductUtil.getpName().substring(0, 10)+"...");
			}
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
}
