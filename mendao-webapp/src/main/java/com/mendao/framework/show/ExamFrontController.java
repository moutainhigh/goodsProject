package com.mendao.framework.show;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mendao.business.dto.GrowthReport;
import com.mendao.business.entity.Exam;
import com.mendao.business.entity.ExamSubject;
import com.mendao.business.entity.School;
import com.mendao.business.service.ExamService;
import com.mendao.business.service.SchoolService;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.base.jpa.ParamsUtil;

@Controller
@RequestMapping(value = "/front/growth")
public class ExamFrontController extends BaseController{

	@Autowired
	ExamService examService;
	
	@Autowired
	private SchoolService schoolService;
	
	@RequestMapping(value = {"", "/"})
	public String index(final HttpServletRequest request, final Model model){
		return "/front/exam/index";
	}
	
	@RequestMapping(value = "/exam/list", method = RequestMethod.GET)
	public String examList(final HttpServletRequest request, final Model model){
		PageEntity<Exam> pageBean = ParamsUtil.createPageEntityFromRequest(request, 8);
		pageBean.getParams().put("status", 1);
		pageBean.setOrderBy(" order by o.sort");
		pageBean = examService.findByPage(pageBean);
		model.addAttribute("pageBean", pageBean);
		ParamsUtil.addAttributeModle(model, pageBean);
		return "/front/exam/list";
	}
	
	@RequestMapping(value = "/exam/detail", method = RequestMethod.GET)
	public String examDetail(final HttpServletRequest request, final Model model
			,@RequestParam(value="id", required = true) Long id){
		Exam exam = examService.findOne(id);
		model.addAttribute("exam", exam);
		return "/front/exam/detail";
	}
	
	@RequestMapping(value = "/exam/process", method = RequestMethod.GET)
	public String examProcess(final HttpServletRequest request, final Model model
			,@RequestParam(value="id", required = true) Long id){
		Exam exam = examService.findOne(id);
		List<ExamSubject> subjects = examService.findSubjectsByExam(id);
		
		model.addAttribute("exam", exam);
		model.addAttribute("subjects", subjects);
		return "/front/exam/subject";
	}
	
	@RequestMapping(value = "/exam/report", method = RequestMethod.GET)
	public String reportShow(final HttpServletRequest request, final Model model){
		GrowthReport report = (GrowthReport)request.getSession().getAttribute("CURRENT_REPORT");
		if(report != null){
			report = examService.examProcess(report);
			String categories = "";
			for(Long key : report.getStickers().keySet()){
				categories = categories.concat(",").concat(key+"");
			}
			model.addAttribute("categories", categories.length() > 0 ? categories.substring(1):"");
			model.addAttribute("report", report);
			model.addAttribute("exam", examService.findOne(report.getId()));
		}
		
		return "/front/exam/report";
	}
	
	@RequestMapping(value = "/exam/report", method = RequestMethod.POST)
	public String report(final HttpServletRequest request, final Model model){
		Long id = Long.valueOf(request.getParameter("examid"));
		List<String> optionIds = Arrays.asList(request.getParameterValues("option"));
		GrowthReport report = new GrowthReport();
		report.setId(id);
		report.setOptions(optionIds);
		request.getSession().setAttribute("CURRENT_REPORT", report);
		return "redirect:/front/growth/exam/report?id=" + id;
	}
	
	@RequestMapping(value="/exam/report/school", method = RequestMethod.GET)
	public String recommandSchool(final HttpServletRequest request, final Model model){
		String categoryId = request.getParameter("cids");
		PageEntity<School> pageEntity = ParamsUtil.createPageEntityFromRequest(request, 8);
		if(categoryId != null && !categoryId.equals("")){
			pageEntity.getParams().put("categoryId", categoryId);
			pageEntity = schoolService.getSchoolPage(pageEntity);
		}
		model.addAttribute("pageBean", pageEntity);
		ParamsUtil.addAttributeModle(model, pageEntity);
		return "/front/exam/school_list";
	}
}
