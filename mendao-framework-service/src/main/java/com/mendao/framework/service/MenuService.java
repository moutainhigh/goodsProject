package com.mendao.framework.service;


import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.entity.Menu;

public interface MenuService  {
	public Menu findByUuid(String uuid);
	PageEntity<Menu> findPage(PageEntity<Menu> pageBean);
}
