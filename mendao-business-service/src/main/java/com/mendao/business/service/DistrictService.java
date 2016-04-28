package com.mendao.business.service;

import java.util.List;

import com.mendao.business.entity.City;
import com.mendao.business.entity.District;
import com.mendao.business.entity.Province;

public interface DistrictService {

	List<Province> getAllProvince();

	List<City> getAllCityByProvinceName(String province);

	List<City> getAllCityByProvinceId(int provinceId);

	List<District> getAllDistrictByCityName(String city);

	List<District> getAllDistrictByCityId(int cityId);

}
