package com.mendao.framework.action;



import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.base.jpa.ParamsUtil;
import com.mendao.framework.entity.FwDuty;
import com.mendao.framework.service.DutyService;
import com.mendao.framework.service.SourceService;
import com.mendao.framework.show.BaseController;
import com.mendao.framework.show.JavaScriptEncoder;

@Controller
@RequestMapping("/backend/duty")
@SessionAttributes(types = FwDuty.class)
public class DutyController extends BaseController{
    @Autowired
    private DutyService dutyService;
    @Autowired
    private SourceService sourceService;


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new FwDuty());
        return "backend/duty/duty_edit";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAdd(Model model, @ModelAttribute FwDuty duty, BindingResult result,
                             SessionStatus status) {
        return this.processEdit(model, duty);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(@RequestParam("queryId") Long id, Model model) {
    	FwDuty duty = (FwDuty)dutyService.getRoleById(id);
    	model.addAttribute(duty);
        return "backend/duty/duty_edit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String processEdit(Model model, @ModelAttribute FwDuty duty) {
    	duty.setDutyType("1");
    	dutyService.saveRole(duty);
        return "redirect:/backend/user/duty/query";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(HttpServletRequest request) {
    	String[] ids = request.getParameterValues("id");
        List<FwDuty> roles = dutyService.findDatasByIds(ids);
        if (roles != null && roles.size() > 0) {
        	dutyService.deleteAll(roles);
        }
        return "redirect:/c/duty/query";
    }

    @RequestMapping(value = "/query")
    public String query(Model model, HttpServletRequest request) {
    	PageEntity<FwDuty> myPage = ParamsUtil.createPageEntityFromRequest(request, 20);
		   myPage =  this.dutyService.getPage(myPage);
		model.addAttribute("pageBean", myPage);
		ParamsUtil.addAttributeModle(model, myPage);
        return "/admin/duty/duty_list";
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
//        List<FwSource> menuList = null;
//        //List<FwAction> actionList = null;
//        FwAccount account = (FwAccount)request.getSession().getAttribute(LOGIN_ACCOUNT);
//        List<FwAction> actionList = new ArrayList<FwAction>();
//        if (account.getId()== 1) { // administrator
//            menuList = sourceService.getMenuSources();
//            actionList = srcActionService.getAllAction();
//        } else {
////            menuList = ThreadLocalClient.get().getOperator().permitSourceList;
////            Iterator<FwAction> permitActions = ThreadLocalClient.get().getOperator().getPermitActionMap().values().iterator();
////            while (permitActions.hasNext()) {
////            	FwAction action = permitActions.next();
////            	if(!actionList.contains(action)){
////            		actionList.add(action);
////                }
////            }
//        }
//        FwDuty duty = this.dutyService.getRoleById(id);
//        model.addAttribute("duty", duty);
//        StringBuffer authorityBuffer = new StringBuffer();
//        Iterator<FwDutyAction> it = duty.getFwDutyActions().iterator();
//        while (it.hasNext()) {
//            FwDutyAction fwDutyAction = it.next();
//            authorityBuffer.append(",s").append(
//                    fwDutyAction.getFwAction().getFwSource().getTreePath())
//                    .append(",i").append(fwDutyAction.getFwAction().getId());
//        }
//        String authorityStr = authorityBuffer.append(",").toString();
//
//        StringBuffer nodeBuffer = new StringBuffer("");
//        Map<Long,FwSource> sourceMap = new HashMap<Long, FwSource>();
//        for (FwSource source : menuList) {
//            FwSource nodesource = source;
////            while(nodesource!=null){
////                if(sourceMap.containsKey(nodesource.getId())){
////                    break;
////                }
////                sourceMap.put(nodesource.getId(), nodesource);
////                boolean isCheck = authorityStr.indexOf(",s"
////                        + nodesource.getStyle().substring(0, 2 * nodesource.getLevel())) >= 0;
////                getTreeNodeString(nodeBuffer, nodesource.getId().toString()+"s", nodesource
////                        .getParent() == null ? "0s" : nodesource.getParent().getId()
////                        .toString()+"s", nodesource.getName(), "checked:"+isCheck+",flag:1");
////                nodeBuffer.append(",\n");
////                nodesource = (FwSource)nodesource.getParent();
////            }
//        }
//        for (FwAction action : actionList) {
//            if(!sourceMap.containsKey(action.getFwSource().getId())){
//                continue;
//            }
//            boolean isCheck = authorityStr.indexOf(",i" + action.getId()+",") >= 0;
//            getTreeNodeString(nodeBuffer, action.getId().toString(), action
//                    .getFwSource().getId().toString()+"s", action.getActionName(),
//                    "checked:"+isCheck+",flag:2");
//            nodeBuffer.append(",\n");
//
//        }
//        getTreeNodeString(nodeBuffer, "0s", "","功能权限","open:true");
//        model.addAttribute("treeNodes", "["+nodeBuffer.toString()+"]");

        return "admin/duty/action_assign";
    }

    private void getTreeNodeString(StringBuffer nodeBuffer, String id,
                                   String pid, String name, String isCheck) {
                               nodeBuffer.append("{id:'").append(id).append("',pId:'").append(pid)
                                       .append("',name:'").append(JavaScriptEncoder.INSTANCE.encode(name)).append("',").append(
                                               isCheck).append("}");
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

}
