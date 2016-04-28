package com.mendao.framework.action;



import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.alibaba.fastjson.JSONObject;
import com.mendao.business.dto.BtTreeNode;
import com.mendao.business.entity.Category;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.base.jpa.ParamsUtil;
import com.mendao.framework.entity.FwDuty;
import com.mendao.framework.entity.FwSource;
import com.mendao.framework.service.DutyService;
import com.mendao.framework.service.SourceService;
import com.mendao.framework.show.BaseController;
import com.mendao.framework.show.JavaScriptEncoder;

@Controller
@RequestMapping("/backend/source")
@SessionAttributes(types = FwSource.class)
public class SourceController extends BaseController{
    @Autowired
    private DutyService dutyService;
    @Autowired
    private SourceService sourceService;

    @RequestMapping(value = "")
    public String query(Model model, HttpServletRequest request) {
    	PageEntity<FwSource> myPage = ParamsUtil.createPageEntityFromRequest(request, 10);
		myPage =  sourceService.findPage(myPage);
		model.addAttribute("pageBean", myPage);
		ParamsUtil.addAttributeModle(model, myPage);
        return "backend/source/source_list";
    }

    /**
     * 加载修改页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(final HttpServletRequest request, final Model model
    		, @RequestParam(value = "id", required = false) Long id) {
    	FwSource source = null;
    	if(null == id || 0l == id){
    		source = new FwSource();
    		source.setParent(new FwSource());
    	}else{
    		source = sourceService.findOne(id);
    		if(source.getParent() == null){
    			source.setParent(new FwSource());
    		}
    	}
    	model.addAttribute(source);
    	setTreeModel(model, source);
        return "backend/source/source_edit";
    }

    /**
     * 提交修改
     * @param model
     * @param source
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String processEdit(final HttpServletRequest request, final Model model
    		, @ModelAttribute FwSource source) {
    	sourceService.saveSource(source);
    	return "redirect:/backend/source";
    }

    

    /**
     * 分配角色方法.
     *
     * @param id
     *            角色id
     * @param model
     *            对象
     * @return 跳转到角色分配页面
     */
    @RequestMapping(value = "/assign", method = RequestMethod.GET)
    public String assignAction(@RequestParam("queryId") final Long id,
            final Model model, HttpServletRequest request) {
    	
    	setTreeModel(model, new FwSource());
        return "backend/source/source_assign";
    }
    
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public String sourceTree(final HttpServletRequest request, final Model model){
    	model.addAttribute("text", request.getParameter("text"));
		return "/backend/source/source_tree";
    }
    
    @ResponseBody
	@RequestMapping(value = "/tree/data", method = RequestMethod.GET)
    public List<BtTreeNode> sourceTreeNode(final HttpServletRequest request, final Model model){
    	List<FwSource> sources = sourceService.getAllSourceDataAsTree();
    	List<BtTreeNode> treeNode = new ArrayList<BtTreeNode>();
		Map<String, BtTreeNode> maps = new HashMap<String, BtTreeNode>();
		sources.forEach(p->{
			BtTreeNode node = new BtTreeNode();
			node.setText(p.getSourceName());
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
		//model.addAttribute("data", JSONObject.toJSON(treeNode));
		return treeNode;
    }

    /**
     * 分配保存方法.
     *
     * @param id
     *            id
     * @param actionIds
     *            分配数组
     * @return 跳转到查询方法
     */
    @RequestMapping(value = "/assign", method = RequestMethod.POST)
    public String processAssignAction(@RequestParam("dutyId") final Long id,
            @RequestParam("actionId") final Long[] actionIds) {
            this.dutyService.assignOperation(id, actionIds);
        return "redirect:/c/duty/query";
    }

    
    private void setTreeModel(final Model model, FwSource source){
    	List<FwSource> sources = sourceService.getAllSourceDataAsTree();
    	StringBuffer nodeBuffer = new StringBuffer("");
    	for (FwSource s : sources) {
    		getTreeNodeString(nodeBuffer, s.getId().toString()+"", 
    				s.getParent() == null ? "0" : s.getParent().getId() + "",
    				s.getSourceName(), "checked:" + 
    				(source.getParent() != null && source.getParent().getId() == s.getId()) + ",flag:1"
    						+ ",sortSeq:" + s.getSortSeq());
    		nodeBuffer.append(",\n");
    	}
    	getTreeNodeString(nodeBuffer, "0", "","功能权限","open:true,sortSeq:0");
    	model.addAttribute("treeNodes", "["+nodeBuffer.toString()+"]");
    }
    
	private void getTreeNodeString(StringBuffer nodeBuffer, String id, String pid, String name, String isCheck) {
		nodeBuffer.append("{id:'").append(id).append("',pId:'").append(pid).append("',name:'")
				.append(JavaScriptEncoder.INSTANCE.encode(name)).append("',").append(isCheck).append("}");
	}
}
