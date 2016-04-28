package com.mendao.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendao.business.entity.City;
import com.mendao.business.entity.District;
import com.mendao.business.entity.Province;
import com.mendao.business.repository.CityRepository;
import com.mendao.business.repository.DistrictRepository;
import com.mendao.business.repository.ProvinceRepository;
import com.mendao.business.service.DistrictService;

@Service("districtService")
public class DistrictServiceImpl implements DistrictService {

	@Autowired
	DistrictRepository districtRep;
	
	@Autowired
	CityRepository cityRep;
	
	@Autowired
	ProvinceRepository provinceRep;
	
	@Override
	public List<Province> getAllProvince(){
		
		//List<Province> provinces = new ArrayList<Province>();
		//provinceRep.findAll().forEach(p -> { provinces.add(p); });
		
		return provinceRep.findAllProvince();
	}
	
	@Override
	public List<City> getAllCityByProvinceName(String province){
		return cityRep.findAllByProvinceName(province);
	}
	
	
	@Override
	public List<City> getAllCityByProvinceId(int provinceId){
		return cityRep.findAllByProvinceId(provinceId);
	}
	
	@Override
	public List<District> getAllDistrictByCityName(String city){
		return districtRep.findAllByCityName(city);
	}
	
	@Override
	public List<District> getAllDistrictByCityId(int cityId){
		return districtRep.findAllByCityId(cityId);
	}
}
