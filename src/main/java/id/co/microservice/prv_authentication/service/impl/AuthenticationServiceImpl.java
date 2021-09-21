package id.co.microservice.prv_authentication.service.impl;

import id.co.microservice.prv_authentication.dao.UserLoginRequest;
import id.co.microservice.prv_authentication.dao.JwtPayload;
import id.co.microservice.prv_authentication.entity.UsersEntity;
import id.co.microservice.prv_authentication.entity.MasterParamEntity;
import id.co.microservice.prv_authentication.service.AuthenticationService;
import id.co.microservice.prv_authentication.repository.UsersRepository;
import id.co.microservice.prv_authentication.repository.MasterParamRepository;
import id.co.microservice.prv_authentication.config.AuthenticationConstants;

import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	
	@Autowired
	UsersRepository usersRepository;
	
	@Autowired
	MasterParamRepository masterParamRepository;

	@Override
	public String authenticate(UserLoginRequest userLoginRequest) {
		
		UsersEntity usersEntity = usersRepository.getUsersByUsername(userLoginRequest.getUsername());
		String response = null;
		
		if (usersEntity.getUsername() != null) {
			
			Boolean passwordCorrect =  doPasswordsMatch(userLoginRequest.getPassword(), usersEntity.getPassword());
			
			if (Boolean.TRUE.equals(passwordCorrect)) {
				
				RestTemplate restTemplate = new RestTemplate();
				String urlString = getMasterParamValue(AuthenticationConstants.APISIX_JWTSIGN_URL_PARAM);
				
				JwtPayload jwtpayload = new JwtPayload();
				jwtpayload.setAud("core-wfm");
				jwtpayload.setIss("prv-authentication");
				jwtpayload.setSub(usersEntity.getUsername());
				String jsonPayload = null;
				
				ObjectMapper mapper = new ObjectMapper();
				try {
					jsonPayload = mapper.writeValueAsString(jwtpayload);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
					return e.getMessage();
				}
				
				@SuppressWarnings("deprecation")
				UriComponents builder = UriComponentsBuilder.fromHttpUrl(urlString)
		                .queryParam("key",getMasterParamValue(AuthenticationConstants.JWTSIGN_SECRET_KEY))
		                        .queryParam("payload",URLEncoder.encode(jsonPayload)).build();
			    
			    response = restTemplate.getForObject(
			    		builder.toUriString(), 
			    		String.class);
			}
			
		}
		
		return response;
	}
	
	private Boolean doPasswordsMatch(String rawPassword,String encodedPassword) {
		  BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	      return passwordEncoder.matches(rawPassword, encodedPassword);
	}
	
	private String getMasterParamValue(String paramCode) {
		
		MasterParamEntity masterParamEntity = masterParamRepository.getParamByParamCode(paramCode);
		
		return masterParamEntity.getParamValue();
	}

}
