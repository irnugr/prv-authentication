package id.co.microservice.prv_authentication.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import id.co.microservice.commons.response.ResponseMessage;
import id.co.microservice.prv_authentication.commons.RequestIDGenerator;
import id.co.microservice.prv_authentication.dao.UserLoginRequest;
import id.co.microservice.prv_authentication.service.AuthenticationService;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/")
public class AuthenticationController extends AbstractController {
	
	@Autowired
	AuthenticationService authService;
	
	@PostMapping("/authenticate")
	public ResponseMessage<String> validateUser(
			@RequestParam(value = "requestId", required = false) String requestId,
			@RequestBody UserLoginRequest authenticationRequest,
			HttpServletResponse httpServletResponse) {
		
		requestId = requestId == null ? RequestIDGenerator.getID() : requestId;
		
		String response = authService.authenticate(authenticationRequest);
		
		if (response.isEmpty()) {
			httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		} else {
			httpServletResponse.setStatus(HttpServletResponse.SC_OK);
		}
		
		return this.buildResponse(requestId, response, HttpStatus.OK.value(), HttpStatus.OK.name(), "");
	}

}
