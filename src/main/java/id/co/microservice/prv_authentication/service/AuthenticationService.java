package id.co.microservice.prv_authentication.service;

import id.co.microservice.prv_authentication.dao.UserLoginRequest;

public interface AuthenticationService {
	
	String authenticate(UserLoginRequest userLoginRequest);
}
