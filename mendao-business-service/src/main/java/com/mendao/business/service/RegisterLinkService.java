package com.mendao.business.service;

import java.util.List;

import com.mendao.business.entity.RegisterLink;


public interface RegisterLinkService {

	List<RegisterLink> getLinkByUserId(Long id);

	void save(RegisterLink rl);

	
}
