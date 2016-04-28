package com.mendao.framework.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

import com.mendao.business.dto.BtTreeNode;
import com.mendao.business.dto.TreeNode;
import com.mendao.business.entity.Category;
import com.mendao.business.entity.School;
import com.mendao.business.service.CategoryService;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.base.jpa.ParamsUtil;
import com.mendao.framework.show.BaseController;

import jodd.util.StringUtil;

@Controller
@RequestMapping("/backend/config")
public class ConfigController extends BaseController{

	@Autowired
	CategoryService categoryService;
	
	/**
	 * 配置中心
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String config(final HttpServletRequest request, final Model model){
		
		return "backend/config/config";
	}
	
	/**
	 * 配置中心－－类目
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/category", method = RequestMethod.GET)
	public String category(final HttpServletRequest request, final Model model){
		setCategoryCodesToPage(model);
		return "backend/config/category/index";
	}
	
	/**
	 * 配置中心－－类目列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/category/list", method = RequestMethod.GET)
	public String categoryList(final HttpServletRequest request, final Model model){
		PageEntity<Category> pageBean = ParamsUtil.createPageEntityFromRequest(request, 10);
		pageBean.setOrderBy(" order by o.sortSeq ");
		pageBean = categoryService.getPage(pageBean);
		model.addAttribute("pageBean", pageBean);
		return "backend/config/category/list";
	}
	
	/**
	 * 配置中心－－添加类目
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/category/add", method = RequestMethod.GET)
	public String addCategory(final HttpServletRequest request, final Model model){
		setCategoryCodesToPage(model);
		Category category = new Category();
		category.setParent(new Category());
		model.addAttribute("category", new Category());
		return "backend/config/category/category_edit";
	}
	
	/**
	 * 配置中心－－添加类目（POST）
	 * @param request
	 * @param model
	 * @param category
	 * @return
	 */
	@RequestMapping(value = "/category/add", method = RequestMethod.POST)
	public String addCategory(final HttpServletRequest request, final Model model, @ModelAttribute Category category){
		//setCategoryCodesToPage(model);
		categoryService.save(category);
		return "redirect:/backend/config/category";
	}
	
	@RequestMapping(value = "/category/edit/{id}", method = RequestMethod.GET)
	public String editCategory(final HttpServletRequest request, final Model model, @PathVariable("id") Long id){
		Category category = categoryService.findOne(id);
		setCategoryCodesToPage(model);
		model.addAttribute("category", category);
		return "backend/config/category/category_edit";
	}
	
	//@ResponseBody
	@RequestMapping(value = "/category/edit/{id}", method = RequestMethod.POST)
	public String editCategoryProcess(final HttpServletRequest request, final Model model, @PathVariable("id") Long id, @ModelAttribute Category category){
		categoryService.save(category);
		setCategoryCodesToPage(model);
		model.addAttribute("category", category);
		return "backend/config/category/category_edit";
	}
	
	/**
	 * 
	 * @param code
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/category/treehtml/{code}")
	public String printCategoryTree(final HttpServletRequest request, @PathVariable("code") String code){
		TreeNode root = new TreeNode();
		root.setId(0L);
		root.setNodeName("root");
		List<Category> categories = categoryService.getAllCategoryByCode(code);
		
		categories.forEach(p->{
			TreeNode node = new TreeNode();
			node.setId(p.getId());
			node.setNodeName(p.getDisplay());
			Long parentId = null != p.getParent() ? p.getParent().getId() : 0L;
			TreeNode parent = root.findTreeNodeById(parentId);
			if(parent != null){
				node.setParentId(parentId);
				node.setLevel(parent.getLevel() + 1);
				node.setParentNode(parent);
				parent.addChildNode(node);
			}
		});
		
		StringBuffer bf = new StringBuffer("");
		//bf.append("<ul><li><label class='ul_label'>+</label>机构分类根");
		String parent = request.getParameter("parent");
		Long parentId = Long.valueOf(StringUtil.isBlank(parent) ? "0" : parent);
		super.createTreeUl(root, bf, (parentId));
		//System.out.println(bf.append("\n</li>\n</ul>").toString());
		
		return bf.toString();
	}
	
	/**
	 * 获取类别编码
	 * @param model
	 */
	private void setCategoryCodesToPage(final Model model){
		//***************************************************************
		//* 获取所有的分类编码。
		//* 分类编码为该分类对应的对象类型名称。
		//* 例如：机构的分类，则code＝School.class.getName()；
		//* 对于话题的分类，则code=Topic.class.getName();
		Map<String, String> codeMap = new HashMap<String, String>();
		codeMap.put("STICKER", "标签维护");
		
		model.addAttribute("codeMap", codeMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/categoryTree", method = RequestMethod.GET)
    public List<BtTreeNode> categoryTreeNode(final HttpServletRequest request){
    	List<BtTreeNode> treeNode = new ArrayList<BtTreeNode>();
    	
    	List<Category> categories = categoryService.getAllCategoryByCode("School");
		Map<String, BtTreeNode> maps = new HashMap<String, BtTreeNode>();
		categories.forEach(p->{
			BtTreeNode node = new BtTreeNode();
			node.setText(p.getDisplay());
			node.setItemid(p.getId());
			
			node.setCustAttr("{\"realId\": " + p.getId() + ", \"sortSeq\":\"" + p.getSortSeq() + "\"}");
			
			if(p.getParent()!= null && maps.containsKey(p.getParent().getId() + "")){
				BtTreeNode parent = maps.get(p.getParent().getId() + "");
				if(parent.getNodes() == null){
					parent.setNodes(new ArrayList<BtTreeNode>());
				}
				parent.getNodes().add(node);
			}else{
				treeNode.add(node);
			}
			
			maps.put(p.getId() + "", node);
		});
		
		maps.forEach((key, p)->{
			if(null != p.getNodes() && p.getNodes().size() > 0){
				maps.get(key).setTags(new String[]{ p.getNodes().size()+ ""});
			}
		});
		
		return treeNode;
    }
}
