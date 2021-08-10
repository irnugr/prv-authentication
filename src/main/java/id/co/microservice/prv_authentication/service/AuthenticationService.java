package id.co.microservice.prv_authentication.service;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

import id.co.microservice.prv_authentication.dao.UserLoginRequest;

public interface AuthenticationService {

	Map<String, Object> authenticationService(UserLoginRequest userLoginRequest);
	
	Map<String, Object> jwkSet() throws NoSuchAlgorithmException;
}
