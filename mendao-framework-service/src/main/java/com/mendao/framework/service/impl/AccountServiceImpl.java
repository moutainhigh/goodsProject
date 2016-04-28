package com.mendao.framework.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mendao.common.util.StringUtil;
import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.entity.FwAccount;
import com.mendao.framework.entity.FwAccountDuty;
import com.mendao.framework.entity.FwDuty;
import com.mendao.framework.repository.AccountIORepository;
import com.mendao.framework.repository.AccountRepository;
import com.mendao.framework.service.AccountService;
import com.mendao.util.EncryptService;
import com.mendao.util.Util;
@Service
public class AccountServiceImpl  implements
		AccountService {
    @Autowired
	EncryptService encryptServiceImpl;
    
    @Autowired
    private AccountRepository fwAccountRepository ;
    
    @Autowired
    private AccountIORepository fwAccountIORepository ;
    
    @Transactional
	public void saveAccount(FwAccount account) {
			account.setAcctPwd(encryptServiceImpl.encrypt("111111"));
			fwAccountRepository.save(account);
	}
	@Transactional
	public void updateAccount(FwAccount account) {
		if(StringUtil.isBlank(account.getAcctPwd())){
			account.setAcctPwd(encryptServiceImpl.encrypt("111111"));
		}
		fwAccountRepository.merge(account);
}
	@Transactional
	public void processAssignDuty(Long accountId, Long[] dutyId) {
		FwAccount account = fwAccountRepository.findOne(accountId);
		account.getFwAccountDuties().clear();
		for (Long curDutyId : dutyId) {
			FwDuty fwDuty = new FwDuty();
			fwDuty.setId(curDutyId);
			FwAccountDuty actDuty = new FwAccountDuty();
			actDuty.setFwDuty(fwDuty);
			actDuty.setFwAccount(account);
			account.getFwAccountDuties().add(actDuty);
		}
		fwAccountRepository.merge(account);

	}

	public FwAccount getAccountById(Long accountId) {
	    FwAccount fwAccount= fwAccountRepository.findOne(accountId);
	    
	    return fwAccount;
	}

	public PageEntity<FwAccount> getPage(PageEntity<FwAccount> myPage) {
		
        return fwAccountRepository.findByPage(myPage);
    }
 
	@SuppressWarnings("unchecked")
	public List<FwAccount> getAvailableAccount() {
		return fwAccountRepository.findListByHql("select t from FwAccount t where t.status='A'");
	}
	@Transactional
	public void deleteAccount(Long accountId, Long[] dutyId) {
		FwAccount account = fwAccountRepository.findOne(accountId);
		account.getFwAccountDuties().clear();
		fwAccountRepository.merge(account);

	}
	/**
	 * 根据用户名称查找用户.
	 * @param acctName 用户名称
	 * @return 用户列表
	 */
	public List<FwAccount> getAccountByAcctName(String acctName) {

		return fwAccountRepository.findFwAccountByAcctName(acctName);
	}
	@Transactional
	public void deleteAll(List<FwAccount> list) {
		 fwAccountRepository.delete(list);
	}



	/**
	 * 更新用户修改后的密码.
	 *
	 * @param user
	 *            用户的对象
	 */
	public void updatePwd(final FwAccount user) {
//		FwAccount account = ThreadLocalClient.get().getOperator();
//		account.setAcctPwd(encryptServiceImpl.encrypt(user.getNewPwd()));
//		hibernateTemplate.update(account);
	}
	
	@SuppressWarnings("unchecked")
	public List findDatasByIds(String[] ids) {
		String param =Util.getIdSQLParam(ids);
		if (param != null) {
			return fwAccountRepository.findListByHql("from FwAccount t where t.id in (" + param + ")");
		}
		return null;
	}

}
