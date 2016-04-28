package com.mendao.framework.action;




import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mendao.business.entity.SysDictionary;
import com.mendao.business.service.SysConfigService;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.base.jpa.ParamsUtil;
import com.mendao.framework.show.BaseController;

/**
 * 字典管理
 * @author warden
 *
 */
@Controller
@RequestMapping("/backend/dictionary")
@SessionAttributes(types = SysDictionary.class)
public class DictionaryController extends BaseController{
	

	@Autowired
	private SysConfigService dictionaryService;
	/**
	 * 字典列表
	 * @param model
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String query(Model model, HttpServletRequest request) {
		PageEntity<SysDictionary> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 20);
		pageEntity = dictionaryService.getPageOfDictionary(pageEntity);
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		return "backend/dictionary/dictionary_list";
	}
	/**
	 * 字典添加
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, HttpServletRequest request) {
		model.addAttribute("sysDictionary", new SysDictionary());
		return "backend/dictionary/dictionary_edit";
	}
	/**
	 * 字典添加post
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addPost(Model model, HttpServletRequest request, @ModelAttribute SysDictionary sysDictionary) {
		dictionaryService.editDictionary(sysDictionary);
		return "redirect:/backend/dictionary/list";
	}
	/**
	 * 字典编辑
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/{queryId}", method = RequestMethod.GET)
	public String edit(@PathVariable("queryId") Long id, Model model) {
		SysDictionary dictionary = dictionaryService.findById(id);
		model.addAttribute("sysDictionary",dictionary);
		return "backend/dictionary/dictionary_edit";
	}
	/**
	 * 字典删除
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/delete/{queryId}", method = RequestMethod.GET)
	public String delete(@PathVariable("queryId") Long id, Model model) {
		dictionaryService.deleteDictionary(id);
		return "redirect:/backend/dictionary/list";
	}
}
