package com.publicis.sapient.service;

import com.publicis.sapient.entity.User;
import com.publicis.sapient.exception.ApiException;
import com.publicis.sapient.repository.QueryClass;
import com.publicis.sapient.repository.UserRepository;
import com.publicis.sapient.validator.InputValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	InputValidator validate;

	@Override
	public User addUser(User user) throws ApiException {
		if (!validate.usernameValidator(user.getName()))
			throw new ApiException(HttpStatus.BAD_REQUEST,"Check Username !!!!");
		if (!validate.passwordValidator(user.getPassword()))
			throw new ApiException(HttpStatus.BAD_REQUEST,"Cannot register this User with this password");
		return userRepository.saveAndFlush(user);
	}

	@Override
	public User removeUser(User user) throws ApiException {
		userRepository.delete(user);
		return user;
	}

}
