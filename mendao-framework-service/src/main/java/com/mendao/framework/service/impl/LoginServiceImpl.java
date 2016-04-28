package com.mendao.framework.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.framework.entity.FwAccount;
import com.mendao.framework.entity.FwAccountDuty;
import com.mendao.framework.entity.FwSource;
import com.mendao.framework.repository.AccountRepository;
import com.mendao.framework.repository.SourceRepository;
import com.mendao.framework.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	private AccountRepository accountRepository ;
	
	@Autowired
	private SourceRepository fwSourceRepository ;

	
	public FwAccount loadUserAuthority(final Long accountId) {
		
		if (accountId == null) {
			//需要处理
		}
		FwAccount account = accountRepository.findOne(accountId);
		if (account == null) {
			//需要处理
		}
		//获取角色列表
		Iterator<FwAccountDuty> it = account.getFwAccountDuties().iterator();
		while(it.hasNext()){
			FwAccountDuty  fwAccountDuty = it.next();
			fwAccountDuty.getFwDuty().getDutyName();
		}
		// get user default authority
		initAccountDefaultAuthority(account);
//		account.setPermitSource(getAccountPermitSource(account.getId()));
//		account.setUserMenuTree(getUserMenuTree(account.getPermitSource()));
//		Map<String, FwSource> userMenuMap = new HashMap<String, FwSource>();
//		for(FwSource fwSource:account.getUserMenuTree()){
//			userMenuMap.put(fwSource.getId().toString(), fwSource);
//		}
//		account.setUserMenuMap(userMenuMap);
		return account;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.huatek.framework.service.LoginService1#initAccountDefaultAuthority
	 * (com.huatek.framework.FwAccount, java.util.Hashtable)
	 */
	@SuppressWarnings("unchecked")
	public void initAccountDefaultAuthority(final FwAccount account) {
//		String defaultActionSql = "select distinct t from FwAction "
//				+ " t,FwDutyAction d,FwAccountDuty a "
//				+ " where t.id=d.fwAction.id "
//				+ " and d.fwDuty.id=a.fwDuty.id " + " and a.fwAccount.id="+account.getId()
//				//+ " and a.fwDuty.fwSystem.sysCode=? "
//				+ " and d.fwDuty.status = 1" + " order by t.sortSeq asc";
//		List<FwAction> permitActionList =  fwActionRepository.findListByHql(defaultActionSql);
//		HashMap<String, List<FwAction>> menusActionMap = new HashMap<String, List<FwAction>>();
//		HashMap<String,FwAction> permitActionMap = new HashMap<String,FwAction>();
//		for (FwAction fwAction : permitActionList) {
//			if(menusActionMap.containsKey(fwAction.getFwSource().getId().toString())){
//				menusActionMap.get(fwAction.getFwSource().getId().toString()).add(fwAction);
//			}else{
//				List<FwAction> menusActionList = new ArrayList<FwAction>();
//				menusActionList.add(fwAction);
//				menusActionMap.put(fwAction.getFwSource().getId().toString(), menusActionList);
//			}
//			String url = "";//fwAction.getActionPath();
//			if (StringUtil.isEmpty(url)) {
//				url = fwAction.getFwSource().getSourceCode();
//			}
//			if(StringUtil.contains(url, "/")){
//				url = url.substring(0,url.lastIndexOf("/"));
//			}
//			String[] methods = fwAction.getActionMethods().split(";");
//			for (String method : methods) {
//				if (method.trim().length() > 0) {
//					String actionKey = Util.getURL(url, method);
//					if(!permitActionMap.containsKey(actionKey)){
//						permitActionMap.put(actionKey,fwAction);
//					}
//
//				}
//			}
//		}
//		account.setMenusActionMap(menusActionMap);
//		account.setPermitActionMap(permitActionMap);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.huatek.framework.service.LoginService1#getAccountPermitSource(java
	 * .lang.Long, java.util.Vector)
	 */

	public List<FwSource> getAccountPermitSource(Long accountId) {
		String HSQl = "select distinct t "
				+ " from FwSource t,FwAction s,FwDutyAction d,FwAccountDuty a "
				+ " where t.id = s.fwSource.id and s.id = d.fwAction.id and d.fwDuty.id=a.fwDuty.id "
				+ " and a.fwAccount.id=" + accountId + " and a.fwDuty.status=1";
		List<FwSource> list = fwSourceRepository.findListByHql(HSQl + " order by t.style asc");
		return list;

	}


	private List<FwSource> getUserMenuTree(final List<FwSource> inputlist) {
		List<FwSource> list = getMenuTree(inputlist);
		for (int i = 0; i < list.size(); i++) {
			FwSource source = list.get(i);
//			if (source.getIsMenu() == 0) {
//				list.remove(i);
//				i--;
//				continue;
//			}
//			source.setNodeLabel(source.getSourceName());
//			source.setTitle(source.getSourceName());
//			source.setAlt(source.getAlt());
//			source.setLink(source.getSourceCode()
//					+ (source.getSourceCode().indexOf("?") > 0 ? "&" : "?")
//					+ "menuId=" + source.getId());
		}
		return list;
	}


	public List<FwSource> loadAllFwSource() {
		return fwSourceRepository.findAllFwSource();
	}

	public List<FwSource> getMenuTree(List<FwSource> childSourceList) {
		List<FwSource> allSourceList = loadAllFwSource();
		for (int i = 0; i < allSourceList.size(); i++) {
			FwSource source = allSourceList.get(i);
			if (!isContain(source, childSourceList)) {
				allSourceList.remove(i);
				i--;
			}
		}
		return allSourceList;
	}
	public static boolean isContain(FwSource entity, List<FwSource> childList) {
		int level = (int) entity.getTreeLevel()* 2;
		String prefix = entity.getTreePath().substring(0, level);
		for (int i = 0; i < childList.size(); i++) {
			FwSource childSource =  childList.get(i);
			if (childSource.getTreePath().substring(0, level).equals(prefix)) {
				return true;
			}
		}
		return false;
	}
	public void flushCache(boolean islocal) {

	}
}
