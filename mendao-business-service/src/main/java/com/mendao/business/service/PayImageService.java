package com.mendao.business.service;

import java.util.List;

import com.mendao.business.entity.PayImage;

public interface PayImageService {

	List<PayImage> getAll();

	PayImage save(PayImage payImage);

	
}
