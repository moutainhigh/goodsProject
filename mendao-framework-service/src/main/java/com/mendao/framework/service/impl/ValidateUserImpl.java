package com.mendao.framework.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.common.util.StringUtil;
import com.mendao.exception.BusinessCheckException;
import com.mendao.framework.entity.FwAccount;
import com.mendao.framework.repository.AccountRepository;
import com.mendao.framework.security.IValidateUser;
import com.mendao.framework.security.SecurityLoginInterFace;
import com.mendao.util.EncryptService;
@Service
public class ValidateUserImpl implements IValidateUser {

	@Autowired
	EncryptService encryptServiceImpl;
	@Autowired
	SecurityLoginInterFace securityLogin;
	
	@Autowired
	AccountRepository fwAccountRepository ;

	public Object getUser(String userName, String password) throws BusinessCheckException{
		if(StringUtil.isBlank(userName)||StringUtil.isBlank(password)){
			throw new BusinessCheckException("用户账户或者密码不能为空");
		}
		FwAccount account = new FwAccount();
		account.setAcctName(userName);
		if (password == null) {
			throw new BusinessCheckException("用户密码不能为空");
		}
		account = (FwAccount)getUserByAccount(userName);
		if (account == null) {
			throw new BusinessCheckException("用户账户不存在");
		}
		String md5Password = encryptServiceImpl.encrypt(password);
		if (!md5Password.equals(account.getAcctPwd())) {
			if (securityLogin != null) {
				securityLogin.passwordErrorNumRecord(account.getId());
			}
			throw new BusinessCheckException("密码错误");
		}
		if (securityLogin != null) {
			securityLogin.unlocked(account.getId());
		}
		if(account.getStatus() == 0){
            throw new BusinessCheckException("用户账户已经禁用，请联系管理员");
        }
		return account;
	}
	@SuppressWarnings("unchecked")
	private Object getUserByAccount(String userName){
		List<FwAccount> acctList = fwAccountRepository.findFwAccountByAcctName(userName.toLowerCase());
		if(acctList.size() == 1){
			return acctList.get(0);
		}
		return null;

	}

}
