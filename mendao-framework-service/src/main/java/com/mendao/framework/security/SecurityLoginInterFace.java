package com.mendao.framework.security;

public interface SecurityLoginInterFace {
	public void passwordErrorNumRecord(Long accountId);
	public boolean isAccountLocked(Long accountId);
	public void unlocked(Long accountId);
}
