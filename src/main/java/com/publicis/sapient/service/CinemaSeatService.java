package com.publicis.sapient.service;

import com.publicis.sapient.entity.CinemaSeat;
import com.publicis.sapient.exception.ApiException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CinemaSeatService {

	CinemaSeat addCinemaSeat(CinemaSeat cinemaSeat,Integer cinemaHallId) throws ApiException;

	CinemaSeat removeCinemaSeat(int cinemaSeatId) throws ApiException;

	CinemaSeat updateCinemaSeat(CinemaSeat cinemaSeat,Integer cinemaHallId) throws ApiException;

	CinemaSeat viewCinemaSeat(int cinemaSeatId) throws ApiException;

	List<CinemaSeat> viewCinemaSeatList() throws ApiException;
}