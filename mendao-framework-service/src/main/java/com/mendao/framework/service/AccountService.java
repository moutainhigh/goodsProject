package com.mendao.framework.service;

import java.util.List;

import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.entity.FwAccount;
public interface AccountService{
	 void saveAccount(FwAccount account);
	 void processAssignDuty(Long accountId,Long[] dutyId);
	 FwAccount getAccountById(Long accountId);
	 List<FwAccount> getAvailableAccount();
	 public PageEntity<FwAccount> getPage(PageEntity<FwAccount> myPage);
	 void deleteAccount(Long accountId, Long[] dutyId);
	 
	 public void updateAccount(FwAccount account);
	/**
	 * 根据用户名称查找用户.
	 * @param acctName 用户名称
	 * @return 用户列表
	 */
	List<FwAccount> getAccountByAcctName(String acctName);

	/**
	 * 更新用户修改后的密码.
	 *
	 * @param user
	 *            用户的对象
	 */
	void updatePwd(FwAccount user);
	
	public List findDatasByIds(String[] ids);
	
	public void deleteAll(List<FwAccount> list);
}
