package com.mendao.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.business.entity.RegisterLink;
import com.mendao.business.repository.RegisterLinkRepository;
import com.mendao.business.service.RegisterLinkService;

@Service
public class RegisterLinkServiceImpl implements RegisterLinkService{

	@Autowired
	private RegisterLinkRepository registerLinkRepository;
	
	@Override
	public List<RegisterLink> getLinkByUserId(Long id) {
		return registerLinkRepository.getLinkByUserId(id);
	}

	@Override
	public void save(RegisterLink rl) {
		registerLinkRepository.save(rl);
	}

	
}
