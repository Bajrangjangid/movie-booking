package com.publicis.sapient.service;

import com.publicis.sapient.entity.City;
import com.publicis.sapient.exception.ApiException;
import com.publicis.sapient.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

	@Autowired
	private CityRepository cityRepository;

	@Override
	public City addCity(City city) throws ApiException {
		if (city == null) throw new ApiException(HttpStatus.BAD_REQUEST,"City doesn't exist");
		cityRepository.saveAndFlush(city);
		return city;
	}

	@Override
	public City removeCity(City city) {
		cityRepository.delete(city);
		return city;
	}

	@Override
	public City updateCity(City city) {
		cityRepository.saveAndFlush(city);
		return cityRepository.findById(city.getCityId()).get();
	}


	@Override
	public City viewCity(int cityId) {
		return cityRepository.findById(cityId).get();
	}

	@Override
	public List<City> viewCityList() throws ApiException {
		List<City> ml = cityRepository.findAll();
		return ml;
	}

}
