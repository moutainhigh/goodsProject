package com.mendao.framework.action;




import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mendao.business.entity.Advert;
import com.mendao.business.entity.Sticker;
import com.mendao.business.entity.SysDictionary;
import com.mendao.business.entity.UserInfo;
import com.mendao.business.service.AdvertService;
import com.mendao.business.service.SysConfigService;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.base.jpa.ParamsUtil;
import com.mendao.framework.show.BaseController;

/**
 * 广告管理
 * @author warden
 *
 */
@Controller
@RequestMapping("/backend/advert")
@SessionAttributes(types = Sticker.class)
public class AdvertController extends BaseController{
	

	@Autowired
	private AdvertService advertService;
	
	@Autowired
	private SysConfigService dictionaryService;
	/**
	 * 广告列表
	 * @param model
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String query(Model model, HttpServletRequest request) {
		PageEntity<Advert> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 20);
		pageEntity = advertService.getPage(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		return "backend/advert/advert_list";
	}
	/**
	 * 广告添加
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, HttpServletRequest request) {
		model.addAttribute("advert", new Advert());
		//获取广告位置数据字典
		List<SysDictionary> list = dictionaryService.findListByCode("ADVERT_PLACE");
		model.addAttribute("placeList", list);
		return "backend/advert/advert_edit";
	}
	/**
	 * 广告添加post
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addPost(Model model, HttpServletRequest request, @ModelAttribute Advert advert) {
		UserInfo userInfo = super.getSessionUser(request.getSession()).getUserInfo();
		if(userInfo != null){
			if(advert != null && advert.getId() != null && advert.getId() > 0){
				Advert advertUpdate = advertService.findById(advert.getId());
				advertUpdate.setImagePath(super.moveImageToRealPath(advert.getImagePath()));
				advertUpdate.setLinkPath(advert.getLinkPath());
				advertUpdate.setName(advert.getName());
				advertUpdate.setPlace(advert.getPlace());
				advertService.save(advertUpdate);
			}else{
				advert.setCreatedTime(new Date());
				advertService.save(advert);
			}
			return "redirect:/backend/advert/list";
		}else{
			return "/login";
		}
	}
	/**
	 * 广告编辑
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/{queryId}", method = RequestMethod.GET)
	public String edit(@PathVariable("queryId") Long id, Model model) {
		Advert advert = advertService.findById(id);
		model.addAttribute("advert",advert);
		//获取广告位置数据字典
		List<SysDictionary> list = dictionaryService.findListByCode("ADVERT_PLACE");
		model.addAttribute("placeList", list);
		return "backend/advert/advert_edit";
	}
	/**
	 * 广告删除
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/delete/{queryId}", method = RequestMethod.GET)
	public String delete(@PathVariable("queryId") Long id, Model model) {
		advertService.deleteById(id);
		return "redirect:/backend/advert/list";
	}
}
