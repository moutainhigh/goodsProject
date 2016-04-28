package com.mendao.business.service;

import java.io.Serializable;

public interface BaseService<T, ID extends Serializable> {

	public T findOne(ID id);

	public T saveBean(T bean);
}
