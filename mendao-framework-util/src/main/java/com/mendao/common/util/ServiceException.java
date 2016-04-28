package com.mendao.common.util;

/**
 * service异常处理类
 * @author zhaolei
 *
 */
public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = 1389958090308317369L;

	public ServiceException() {
		super();
	}

	public ServiceException(String msg, Throwable clause) {
		super(msg, clause);
	}

	public ServiceException(String msg) {
		super(msg);
	}

	public ServiceException(Throwable clause) {
		super(clause);
	}
	
}
