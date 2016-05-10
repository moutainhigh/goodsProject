package com.mendao.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.business.entity.PayImage;
import com.mendao.business.repository.PayImageRepository;
import com.mendao.business.service.PayImageService;

@Service
public class PayImageServiceImpl implements PayImageService{

	@Autowired
	private PayImageRepository payImageRepository;
	
	@Override
	public List<PayImage> getAll() {
		return (List<PayImage>) payImageRepository.findAll();
	}
	/**
	 * 保存付款图片修改
	 */
	@Override
	public PayImage save(PayImage payImage) {
		//先删除原有的图片   再保存
		payImageRepository.deleteAll();
		return payImageRepository.save(payImage);
	}

	
}
