package com.mendao.framework.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.framework.base.jpa.PageEntity;
import com.mendao.framework.entity.FwSource;
import com.mendao.framework.entity.Menu;
import com.mendao.framework.repository.MenuRepository;
import com.mendao.framework.service.MenuService;
@Service
public class MenuServiceImpl  implements MenuService{

	@Autowired
	private MenuRepository menuRepository ;

	@Override
	public Menu findByUuid(String uuid) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 分页查询
	 */
	@Override
	public PageEntity<Menu> findPage(PageEntity<Menu> pageBean) {
		return menuRepository.findByPage(pageBean);
	}
	
	
}
