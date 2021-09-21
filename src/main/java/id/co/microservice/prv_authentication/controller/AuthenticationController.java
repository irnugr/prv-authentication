package id.co.microservice.prv_authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.co.microservice.prv_authentication.dao.UserLoginRequest;
import id.co.microservice.prv_authentication.service.AuthenticationService;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/")
public class AuthenticationController {
	
	@Autowired
	AuthenticationService authService;
	
	@PostMapping("/authenticate")
	public ResponseEntity<String> validateUser(@RequestBody UserLoginRequest authenticationRequest) {
		
		String response = authService.authenticate(authenticationRequest);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
