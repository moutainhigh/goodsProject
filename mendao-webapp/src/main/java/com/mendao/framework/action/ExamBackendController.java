package com.mendao.framework.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mendao.business.entity.Exam;
import com.mendao.business.entity.ExamGroup;
import com.mendao.business.entity.ExamOption;
import com.mendao.business.entity.ExamReport;
import com.mendao.business.entity.ExamSticker;
import com.mendao.business.entity.ExamSubject;
import com.mendao.business.entity.Sticker;
import com.mendao.business.service.ExamService;
import com.mendao.business.service.StickerService;
import com.mendao.common.util.StringUtil;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.base.jpa.ParamsUtil;
import com.mendao.framework.show.BaseController;

@Controller
@RequestMapping(value = "/backend/exam")
public class ExamBackendController extends BaseController{

	@Autowired
	ExamService examService;
	
	@Autowired
	StickerService stickerService;
	
	@RequestMapping(value = {"", "/"})
	public String index(final HttpServletRequest request, final Model model){
		PageEntity<Exam> pageBean = ParamsUtil.createPageEntityFromRequest(request, 20);
		pageBean.setOrderBy(" order by o.sort");
		pageBean = examService.findByPage(pageBean);
		model.addAttribute("pageBean", pageBean);
		ParamsUtil.addAttributeModle(model, pageBean);
		return "/backend/exam/index";
	}
	
	/**
	 * 编辑测评
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(final HttpServletRequest request, final Model model
			, @RequestParam(value = "id", required = false) Long id){
		Exam exam = null;
		if(null == id || id == 0L){
			exam = new Exam();
		}else{
			exam = examService.findOne(id);
		}
		setEditModel(model, exam);
		return "/backend/exam/edit";
	}
	
	/**
	 * 提交编辑结果
	 * @param request
	 * @param model
	 * @param exam
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String edit(final HttpServletRequest request, final Model model
			, final RedirectAttributes attr, @ModelAttribute Exam exam){
		exam = examService.saveBean(exam);
		setEditModel(model, exam);
		attr.addFlashAttribute(SUCCESS_MESSAGE, "修改成功");
		return "redirect:/backend/exam/edit?id="+exam.getId();
	}
	
	/**
	 * 加载编辑分组页面
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/edit/group", method = RequestMethod.GET)
	public String editGroup(final HttpServletRequest request, final Model model
			, @RequestParam(value = "id", required = false) Long id){
		
		Exam exam = examService.findOne(id);
		this.setEditGroupModel(model, exam);
		
		return "/backend/exam/edit_group";
	}
	
	/**
	 * 编辑测评分组
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit/group", method = RequestMethod.POST)
	public String editGroupProcess(final HttpServletRequest request, final Model model, final RedirectAttributes attr){
		Long examid = Long.valueOf(request.getParameter("examid"));
		List<String> ids = Arrays.asList(request.getParameterValues("id"));
		List<String> names = Arrays.asList(request.getParameterValues("name"));
		List<String> scores = Arrays.asList(request.getParameterValues("score"));
		List<String> sorts = Arrays.asList(request.getParameterValues("sort"));
		
		Exam exam = examService.findOne(examid);
		Set<ExamGroup> groups = new HashSet<ExamGroup>();
		for(int i=0; i<ids.size(); i++){
			if(StringUtil.isNotBlank(ids.get(i))){
				ExamGroup group = new ExamGroup();
				group.setId(Long.valueOf(ids.get(i)));
				group.setExam(exam);
				group.setName(names.get(i));
				group.setScore(Integer.valueOf(scores.get(i)));
				group.setSort(Integer.valueOf(sorts.get(i)));
				groups.add(group);
			}
		}
		if(!groups.isEmpty()){
			examService.mergeGroups(groups);
		}
		//this.setEditGroupModel(model, exam);
		attr.addFlashAttribute(SUCCESS_MESSAGE, "修改成功");
		return "redirect:/backend/exam/edit/group?id=" + examid;
	}
	
	/**
	 * 加载图片页面
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/edit/image", method = RequestMethod.GET)
	public String editImage(final HttpServletRequest request, final Model model
			, @RequestParam(value = "id", required = false) Long id){
		
		Exam exam = examService.findOne(id);
		model.addAttribute("id", id);
		model.addAttribute("icon", exam.getIcon());
		model.addAttribute("image", exam.getImage());
		return "/backend/exam/edit_image";
	}
	
	@RequestMapping(value = "/edit/image", method = RequestMethod.POST)
	public String editImageProcess(final HttpServletRequest request, final Model model){
		Long id = Long.valueOf(request.getParameter("id"));
		String icon = request.getParameter("icon");
		String image = request.getParameter("image");
		Exam exam = examService.findOne(id);
		
		exam.setIcon(moveImageToRealPath(icon));
		exam.setImage(moveImageToRealPath(image));
		examService.saveBean(exam);
		
		model.addAttribute("id", id);
		model.addAttribute("icon", exam.getIcon());
		model.addAttribute("image", exam.getImage());
		return "/backend/exam/edit_image";
	}
	
	/**
	 * 加载题目
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/edit/subject", method = RequestMethod.GET)
	public String editSubject(final HttpServletRequest request, final Model model
			, @RequestParam(value = "id", required = false) Long id){
		Exam exam = examService.findOne(id);
		this.setEditSubjectModel(model, exam);
		
		return "/backend/exam/edit_subject";
	}
	
	@RequestMapping(value = "/edit/subject", method = RequestMethod.POST)
	public String editSubjectProcess(final HttpServletRequest request, final Model model){
		Long id = Long.valueOf(request.getParameter("examid"));
		Exam exam = examService.findOne(id);
		
		List<String> ids = Arrays.asList(request.getParameterValues("id"));
		List<String> questions = Arrays.asList(request.getParameterValues("question"));
		List<String> groups = Arrays.asList(request.getParameterValues("examGroup.id"));
		List<String> sorts = Arrays.asList(request.getParameterValues("sort"));
		List<String> types = Arrays.asList(request.getParameterValues("type"));
		
		Set<ExamSubject> subjects = new HashSet<ExamSubject>();
		for(int i=0; i<ids.size(); i++){
			if(StringUtil.isNotBlank(ids.get(i))){
				Long sid = Long.valueOf(ids.get(i));
				ExamSubject subject = new ExamSubject();
				ExamGroup group = new ExamGroup();
				group.setId(Long.valueOf(groups.get(i)));
				subject.setExamGroup(group);
				subject.setQuestion(questions.get(i));
				subject.setType(Integer.valueOf(types.get(i)));
				subject.setId(sid);
				int sort = StringUtil.isBlank(sorts.get(i)) ? 0 : Integer.valueOf(sorts.get(i));
				subject.setSort(sort);
				subjects.add(subject);
			}
		}
		if(!subjects.isEmpty()){
			examService.mergeSubjects(subjects);
		}
		this.setEditSubjectModel(model, exam);
		return "/backend/exam/edit_subject";
	}
	
	/**
	 * 加载选项
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/edit/option", method = RequestMethod.GET)
	public String editOption(final HttpServletRequest request, final Model model
			, @RequestParam(value = "id", required = false) Long id){
		ExamSubject subject = examService.findOneSubject(id);
		
		List<ExamOption> options = examService.findOptionsBySubject(id);
		if(options.isEmpty() && subject.getType() == 0){
			ExamSubject s = new ExamSubject();
			s.setId(id);
			Set<ExamOption> opts = new HashSet<ExamOption>();
			
			ExamOption option = new ExamOption();
			option.setCode("1");
			option.setScore(0);
			option.setSort(0);
			option.setText("是");
			option.setSubject(s);
			opts.add(option);
			
			ExamOption option1 = new ExamOption();
			option1.setCode("2");
			option1.setScore(0);
			option1.setSort(1);
			option1.setText("否");
			option1.setSubject(s);
			opts.add(option1);
			
			examService.mergeOptions(opts);
			options = examService.findOptionsBySubject(id);
		}
		model.addAttribute("subjectid", id);
		model.addAttribute("options", options);
		return "/backend/exam/edit_option";
	}
	
	@RequestMapping(value = "/edit/option", method = RequestMethod.POST)
	public String editOptionProcess(final HttpServletRequest request, final Model model){
		Long id = Long.valueOf(request.getParameter("subjectid"));
		model.addAttribute("subjectid", id);
		ExamSubject subject = examService.findOneSubject(id);
		List<String> ids = Arrays.asList(request.getParameterValues("id"));
		List<String> codes = Arrays.asList(request.getParameterValues("code"));
		List<String> texts = Arrays.asList(request.getParameterValues("text"));
		List<String> scores = Arrays.asList(request.getParameterValues("score"));
		List<String> sorts = Arrays.asList(request.getParameterValues("sort"));
		
		Set<ExamOption> options = new HashSet<ExamOption>();
		for(int i=0; i<ids.size(); i++){
			if(StringUtil.isNotBlank(ids.get(i))){
				Long oid = Long.valueOf(ids.get(i));
				ExamOption op = new ExamOption();
				op.setCode(codes.get(i));
				op.setText(texts.get(i));
				op.setId(oid);
				op.setScore(Integer.valueOf(scores.get(i)));
				op.setSort(Integer.valueOf(sorts.get(i)));
				op.setSubject(subject);
				options.add(op);
			}
		}
		if(!options.isEmpty()){
			examService.mergeOptions(options);
		}
		model.addAttribute("subjectid", id);
		model.addAttribute("options", examService.findOptionsBySubject(id));
		return "/backend/exam/edit_option";
	}
	
	@RequestMapping(value = "/edit/sticker", method = RequestMethod.GET)
	public String editSticker(final HttpServletRequest request, final Model model
			, @RequestParam(value = "id", required = false) Long id){
		List<Sticker> list = stickerService.findAllByCode(Sticker.SCHOOL_STICKER).stream()
				.sorted((x1, x2)->{
					return StringUtil.defaultIfBlank(x1.getCategory().getSortSeq())
					.compareTo(StringUtil.defaultIfBlank(x2.getCategory().getSortSeq()));
				}).collect(Collectors.toList());
		model.addAttribute("id", id);
		model.addAttribute("list", list);
		
		List<ExamSticker> stickers = examService.findStickerByReport(id);
		
		if(!stickers.isEmpty()){
			model.addAttribute("stickers", stickers.stream().map(ExamSticker::getSticker).map(Sticker::getId).collect(Collectors.toList()));
		}else{
			model.addAttribute("stickers", Arrays.asList(0));
		}
		return "/backend/exam/edit_sticker";
	}
	
	@RequestMapping(value = "/edit/sticker", method = RequestMethod.POST)
	public String editStickerProcess(final HttpServletRequest request, final Model model){
		Long id = Long.valueOf(request.getParameter("id"));
		List<String> stickerIds = Arrays.asList(request.getParameterValues("stickerid"));
		Set<ExamSticker> stickers = new HashSet<ExamSticker>();
		ExamReport report = new ExamReport();
		report.setId(id);
		stickerIds.forEach(p->{
			if(StringUtil.isNotBlank(p)){
				ExamSticker examSticker = new ExamSticker();
				Sticker sticker = new Sticker();
				sticker.setId(Long.valueOf(p));
				examSticker.setReport(report);
				examSticker.setSticker(sticker);
				stickers.add(examSticker);
			}
		});
		examService.mergeStickers(id, stickers);
		return "redirect:/backend/exam/edit/sticker?id=" + id;
	}
	
	/**
	 * 加载结果报告
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/edit/report", method = RequestMethod.GET)
	public String editReport(final HttpServletRequest request, final Model model
			, @RequestParam(value = "id", required = false) Long id){
		Exam exam = examService.findOne(id);
		setEditReportMode(model, exam);
		return "/backend/exam/edit_report";
	}
	
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Map<String, Object> deleteExam(final HttpServletRequest request){
		List<String> strIds = Arrays.asList(request.getParameter("ids").split(","));
		List<Long> ids = new ArrayList<Long>();
		strIds.forEach(p-> {
			if(StringUtil.isNotBlank(p)){
				ids.add(Long.valueOf(p));
			}
		});
		examService.deleteExam(ids);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("mark", true);
		return result;
	}
	
	@RequestMapping(value = "/edit/report", method = RequestMethod.POST)
	public String editReportProcess(final HttpServletRequest request, final Model model){
		Long id = Long.valueOf(request.getParameter("examid"));
		Exam exam = examService.findOne(id);
		
		List<String> ids = Arrays.asList(request.getParameterValues("id"));
		List<String> names = Arrays.asList(request.getParameterValues("name"));
		List<String> maxScores = Arrays.asList(request.getParameterValues("maxScore"));
		List<String> minScores = Arrays.asList(request.getParameterValues("minScore"));
		List<String> contents = Arrays.asList(request.getParameterValues("content"));
		List<String> sorts = Arrays.asList(request.getParameterValues("sort"));
		List<String> groups = Arrays.asList(request.getParameterValues("examGroup.id"));
		
		Set<ExamReport> reports = new HashSet<ExamReport>();
		for(int i=0; i<ids.size(); i++){
			if(StringUtil.isNotBlank(ids.get(i))){
				Long repid = Long.valueOf(ids.get(i));
				ExamReport rep = new ExamReport();
				rep.setId(repid);
				rep.setContent(contents.get(i));
				rep.setName(names.get(i));
				rep.setMaxScore(Integer.valueOf(maxScores.get(i)));
				rep.setMinScore(Integer.valueOf(minScores.get(i)));
				rep.setSort(Integer.valueOf(sorts.get(i)));
				
				ExamGroup group = new ExamGroup();
				group.setId(Long.valueOf(groups.get(i)));
				rep.setExamGroup(group);
				reports.add(rep);
			}
		}
		if(!reports.isEmpty()){
			examService.mergeReports(reports);
		}
		
		setEditReportMode(model, exam);
		return "redirect:/backend/exam/edit/report?id="+id;
	}
	
	private void setEditModel(final Model model, Exam exam){
		if(null == exam.getId() || exam.getId() == 0L){
			Set<ExamGroup> groups = new HashSet<ExamGroup>();
			groups.add(new ExamGroup());
			exam.setExamGroups(groups);
		}
		model.addAttribute("exam", exam);
	}
	
	private void setEditGroupModel(final Model model, Exam exam){
		if(exam.getType() == 0 && !exam.getExamGroups().isEmpty()){
			model.addAttribute("show", false);
		}else{
			model.addAttribute("show", true);
		}
		model.addAttribute("examid", exam.getId());
		model.addAttribute("groups", examService.findGroupsByExam(exam.getId()));
	}
	
	private void setEditSubjectModel(final Model model, Exam exam){
		model.addAttribute("examid", exam.getId());
		model.addAttribute("groups", exam.getExamGroups());
		
//		List<ExamSubject> subjects = new ArrayList<ExamSubject>();
//		for(ExamGroup g : exam.getExamGroups()){
//			subjects.addAll(g.getSubjects());
//		}
		List<ExamSubject> subjects = examService.findSubjectsByExam(exam.getId());
		model.addAttribute("subjects", subjects);
	}
	
	private void setEditReportMode(final Model model, Exam exam){
		model.addAttribute("examid", exam.getId());
		model.addAttribute("groups", exam.getExamGroups());
		model.addAttribute("reports", examService.findReportsByExam(exam.getId()));
	}
}
