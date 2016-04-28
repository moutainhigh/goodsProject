package com.mendao.framework.show;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mendao.business.entity.FreeStudy;
import com.mendao.business.service.FreeStudyService;

@Controller
@RequestMapping("/front/free")
public class FreeStudyController {

	@Autowired
	private FreeStudyService freeStudyService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String freeStudy(final HttpServletRequest request, final Model model){
		model.addAttribute("type", 4);
		return "/front/freeStudy/index";
	}
	
	@RequestMapping(value = "/{type}", method = RequestMethod.GET)
	public String freeStudyOne(final HttpServletRequest request, final Model model, @PathVariable("type") int type){
		model.addAttribute("type", type);
		return "/front/freeStudy/index";
	}
	
	@ResponseBody
	@RequestMapping(value = "/addFreeStudy", method= RequestMethod.POST)
	public Map<String,Object> addFreeStudy(final HttpServletRequest request, final Model model, @ModelAttribute FreeStudy freeStudy){
		Map<String,Object> map = new HashMap<String, Object>();
		try{
			freeStudy.setCreatedTime(new Date());
			freeStudyService.save(freeStudy);
			map.put("msg", 1);
		}catch(Exception e){
			map.put("msg", -1);
		}
		return map;
	}
	
	@RequestMapping(value = "/parents", method= RequestMethod.GET)
	public String parents(final HttpServletRequest request, final Model model){
		return "/front/freeStudy/parents";
	}
	
	@RequestMapping(value = "/heart", method = RequestMethod.GET)
	public String heart(final HttpServletRequest request, final Model model){
		return "/front/freeStudy/heart";
	}
	
	@RequestMapping(value = "/ability", method= RequestMethod.GET)
	public String ability(final HttpServletRequest request, final Model model){
		return "/front/freeStudy/ability";
	}
	
	@RequestMapping(value = "/leader", method = RequestMethod.GET)
	public String leader(final HttpServletRequest request, final Model model){
		return "/front/freeStudy/leader";
	}
	
	@RequestMapping(value = "/grow", method= RequestMethod.GET)
	public String grow(final HttpServletRequest request, final Model model){
		return "/front/freeStudy/grow";
	}
}
