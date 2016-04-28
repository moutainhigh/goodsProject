package com.mendao.framework.service;

import java.util.List;

import com.mendao.framework.entity.FwAccount;
import com.mendao.framework.entity.FwSource;


public interface LoginService {
	 List<FwSource> loadAllFwSource();
	 FwAccount loadUserAuthority(final Long accountId);
	 abstract void initAccountDefaultAuthority(FwAccount account);
	 abstract List<FwSource> getAccountPermitSource(Long accountId);
	 void flushCache(boolean islocal);
}
