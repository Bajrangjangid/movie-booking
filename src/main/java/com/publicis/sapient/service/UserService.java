package com.publicis.sapient.service;

import com.publicis.sapient.entity.User;
import com.publicis.sapient.exception.ApiException;

public interface UserService {

	User addUser(User user) throws ApiException;

	User removeUser(User user) throws ApiException;
}
