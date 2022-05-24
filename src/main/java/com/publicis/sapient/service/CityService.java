package com.publicis.sapient.service;

import com.publicis.sapient.entity.City;
import com.publicis.sapient.exception.ApiException;

import java.util.List;

public interface CityService {

	City addCity(City city) throws ApiException;

	City removeCity(City city) throws ApiException;

	City updateCity(City city) throws ApiException;

	City viewCity(int cityId) throws ApiException;

	List<City> viewCityList() throws ApiException;

//	List<City> viewCityWiseTheatre() throws ApiException;
}
