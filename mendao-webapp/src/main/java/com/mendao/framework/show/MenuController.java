package com.mendao.framework.show;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mendao.framework.entity.FwAccount;
import com.mendao.framework.entity.FwSource;
@Controller
@RequestMapping("/c/menu")
public class MenuController {
	private static String LOGIN_ACCOUNT = "LoginAccount";

	@RequestMapping(value = "/getMenu")
	public String getMenu(final Model model, final HttpServletRequest request,final HttpServletResponse response) throws Exception{
			return getModule(model,request,response);
	}

	public static String getModule(final Model model, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		FwAccount user =  (FwAccount)request.getSession().getAttribute(LOGIN_ACCOUNT) ;
		//获取导航条信息
//		List<FwSource> sourceList  = user.getUserMenuTree();
//		String menuUrl = "";
//		String menuId = request.getParameter("menuId");
//		FwSource firstSource = null ;
//		for(int i=0;i<sourceList.size();i++){
//			FwSource source = sourceList.get(i);
//			if(source.getLevel().intValue()==1){
//				 if(StringUtil.isBlank(menuId)){
//					 menuId = String.valueOf(source.getId()) ;
//				 }
//				menuUrl = "/c/menu/getMenu?menuId="+source.getId();
//				source.setMenuUrl(menuUrl.toString());
//			}
//		}
//		if(StringUtil.isNotBlank(menuId)){
//			firstSource = (FwSource)getObject(user.getUserMenuTree(), Long.valueOf(menuId));
//		}
//		user.setUserMenuTree(sourceList);
//		user.setCheckMenu(firstSource);
//		user.getToolAddress().clear();
//		ClientInfoBean cientInfo = (ClientInfoBean) ThreadLocalClient.get();
//		cientInfo.setOperator(user);
//		request.getSession().setAttribute(LOGIN_ACCOUNT, user);
		
		return "layout/home";
	}
	/**
	 * 根据数据ID获取数据列表中的对象.
	 *
	 * @param datas
	 *            数据列表.
	 * @param id
	 *            查询ID.
	 * @return 返回查询的数据对象.
	 */
	private static FwSource getObject(final List<FwSource> datas,
			final Long id) {
		if (datas == null || id == null) {
			return null;
		}
		for (int i = 0; i < datas.size(); i++) {
			if (datas.get(i).getId().intValue() == id.intValue()) {
				return datas.get(i);
			}
		}
		return null;
	}

}
