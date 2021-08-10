package id.co.microservice.prv_authentication.controller;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import id.co.microservice.prv_authentication.dao.UserLoginRequest;
import id.co.microservice.prv_authentication.service.AuthenticationService;

@RestController
@CrossOrigin
public class AuthenticationController {
	
	@Autowired
	AuthenticationService authService;
	
	@PostMapping("/token-issuer")
	public ResponseEntity<Map<String, Object>> validateUserPassword(@RequestBody UserLoginRequest authenticationRequest) {
		
		Map<String, Object> response = authService.authenticationService(authenticationRequest);
		if (response.get("exp") == null)
			return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
		else {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}
	
	@GetMapping("/jwks.json")
	public Map<String, Object> keys() throws NoSuchAlgorithmException {
		return this.authService.jwkSet();
	}

}
