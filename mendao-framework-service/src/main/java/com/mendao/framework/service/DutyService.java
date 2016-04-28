package com.mendao.framework.service;

import java.util.List;

import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.entity.FwAccount;
import com.mendao.framework.entity.FwDuty;

public interface DutyService {
	public void saveRole(FwDuty duty);
	
	public void assignOperation(Long roleId,Long[] operationIds);
	
	public List<FwDuty> getAvailableRoles();
	
	public FwDuty getRoleById(Long roleId);
	/**
	 * 根据操作类型，完成更新用户操作.
	 *
	 * @param user
	 *            用户对象
	 * @param type
	 *            操作类型
	 */
	void updateUser(List<FwAccount> user, String type);
	
	public PageEntity<FwDuty> getPage(PageEntity<FwDuty> myPage);
	
	public List<FwDuty> findDatasByIds(String[] ids);
	
	public void deleteAll(List<FwDuty> list);
}
