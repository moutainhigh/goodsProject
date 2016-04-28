package com.mendao.business.dto;

import com.mendao.exception.BusinessCheckException;

/**
 * Hello world!
 *
 */
public class FormObject 
{
    private String returnUrl;
    
    private BusinessCheckException businessException;

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public BusinessCheckException getBusinessException() {
		return businessException;
	}

	public void setBusinessException(BusinessCheckException businessException) {
		this.businessException = businessException;
	}
    
    
}
