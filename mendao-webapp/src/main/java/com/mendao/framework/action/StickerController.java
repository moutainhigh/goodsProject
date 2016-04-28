package com.mendao.framework.action;




import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mendao.business.entity.Sticker;
import com.mendao.business.service.StickerService;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.base.jpa.ParamsUtil;
import com.mendao.framework.show.BaseController;

/**
 * 标签管理
 * @author warden
 *
 */
@Controller
@RequestMapping("/backend/sticker")
public class StickerController extends BaseController{
	

	@Autowired
	private StickerService stickerService;
	/**
	 * 标签列表
	 * @param model
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String query(Model model, HttpServletRequest request) {
		PageEntity<Sticker> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 20);
		pageEntity.setOrderBy(" order by o.sortSeq");
		pageEntity = stickerService.getPage(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		return "backend/sticker/sticker_list";
	}
	
	/**
	 * 标签编辑
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/{queryId}", method = RequestMethod.GET)
	public String edit(@PathVariable("queryId") Long id, Model model) {
		Sticker sticker = stickerService.findById(id);
		model.addAttribute("sticker",sticker);
		return "backend/sticker/sticker_edit";
	}
	/**
	 * 标签删除
	 * @param id
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete/{queryId}", method = RequestMethod.POST)
	public String delete(@PathVariable("queryId") Long id, Model model) {
		stickerService.deleteById(id);
		return "redirect:/backend/sticker/list";
	}
	
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String editProcess(final HttpServletRequest request, final Model model, @ModelAttribute Sticker sticker){
		stickerService.save(sticker);
		return "redirect:/backend/sticker/list";
	}
}
