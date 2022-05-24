package com.publicis.sapient.controller;

import com.publicis.sapient.constant.Constants;
import com.publicis.sapient.entity.City;
import com.publicis.sapient.exception.ApiException;
import com.publicis.sapient.service.CityService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.TransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CityController {

	Logger logger = LoggerFactory.getLogger(CityController.class);

	@Autowired
	private CityService cityService;

	@PostMapping("/city")
	public ResponseEntity<City> addCity(@RequestBody City city)
			throws ApiException {
		try {
			city = cityService.addCity(city);
		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return new ResponseEntity<>(city, HttpStatus.CREATED);
	}


	@PutMapping("/city/{cityId}")
	public ResponseEntity<City> updateCity(@PathVariable int cityId,@RequestBody City city)
			throws ApiException {
		try {
			if (city == null) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			city.setCityId(cityId);
			city = cityService.updateCity(city);

		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return new ResponseEntity<>(city, HttpStatus.ACCEPTED);
	}


	@GetMapping("/cities")
	public ResponseEntity<List<City>> viewCityList() throws ApiException {
		List<City> citys = null;
		try {
			citys = cityService.viewCityList();
		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return ResponseEntity.ok(citys);
	}

	@GetMapping("/city/{cityId}")
	public ResponseEntity<City> viewCity(@PathVariable int cityId) throws ApiException {
		City city = null;
		try {
			city = cityService.viewCity(cityId);
		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return new ResponseEntity<>(city, HttpStatus.OK);
	}


	@DeleteMapping("/city/{cityId}")
	public ResponseEntity<City> removeCity(@PathVariable int cityId)
			throws ApiException {
		City city = null;
		try {
			city = cityService.viewCity(cityId);
			if (city == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			cityService.removeCity(city);
		} catch (ApiException apiEx) {
			throw apiEx;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return new ResponseEntity<>(city, HttpStatus.OK);
	}

//	@GetMapping("/city/theatre/{theatreId}")
//	public ResponseEntity<List<City>> viewCityByTheatreId(@PathVariable int theatreId) throws ApiException {
//		List<City> citys = null;
//		try {
//			citys = citysService.viewCityList(theatreId);
//		} catch (ApiException apiEx) {
//			throw apiEx;
//		} catch (Exception e) {
//			if (e instanceof TransactionException) {
//				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
//			}
//			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
//		}
//		return new ResponseEntity<>(citys, HttpStatus.OK);
//	}

}
