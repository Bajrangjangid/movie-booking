package com.publicis.sapient.validator;

public interface InputValidator {

	boolean nameValidator(String name);

	boolean contactValidator(String contact);

	boolean emailValidator(String email);

	boolean passwordValidator(String password);

	boolean usernameValidator(String username);

}
