package com.mendao.framework.security;

import com.mendao.exception.BusinessCheckException;

public interface IValidateUser {
	public Object getUser(String userName,String password) throws BusinessCheckException;
}
