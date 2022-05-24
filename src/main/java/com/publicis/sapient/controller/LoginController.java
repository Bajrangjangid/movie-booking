package com.publicis.sapient.controller;

import com.publicis.sapient.constant.Constants;
import com.publicis.sapient.domain.Login;
import com.publicis.sapient.entity.User;
import com.publicis.sapient.exception.ApiException;
import com.publicis.sapient.service.LoginService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.TransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

	Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	LoginService loginService;

	@PostMapping("/login")
	public ResponseEntity<Login> loginUser(@RequestBody User user) throws ApiException {
		Login login = null;
		try {
			login = loginService.loginWithData(user.getName(), user.getPassword());

		} catch (ApiException e) {
			throw new ApiException(HttpStatus.UNAUTHORIZED, ExceptionUtils.getMessage(e),  e);
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		logger.info("-----------------Login Successful----------------");
		return ResponseEntity.ok(login);
	}


	@PostMapping("/logout")
	public ResponseEntity<String> logOut() throws ApiException {
		try {
			if (!this.loginStatus()) {
				throw new ApiException(Constants.NOT_YET_LOGIN);
			}
			loginService.logoutPresentUser();

		} catch (ApiException e) {
			throw e;
		} catch (Exception e) {
			if (e instanceof TransactionException) {
				throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_CONNECTING_MYSQL,  e);
			}
			throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, ExceptionUtils.getMessage(e),  e);
		}
		return new ResponseEntity<>(Constants.LOGIN_SUCCESS, HttpStatus.ACCEPTED);
	}

	public boolean loginStatus() {
		return loginService.loginStatus();
	}

	public String getRole() {
		return loginService.getRole();
	}

}