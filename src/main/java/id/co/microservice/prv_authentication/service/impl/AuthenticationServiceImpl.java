package id.co.microservice.prv_authentication.service.impl;

import id.co.microservice.prv_authentication.dao.AccessTokenResponse;
import id.co.microservice.prv_authentication.dao.RefreshTokenResponse;
import id.co.microservice.prv_authentication.dao.UserLoginRequest;
import id.co.microservice.prv_authentication.dao.JwkKeys;
import id.co.microservice.prv_authentication.entity.UsersEntity;
import id.co.microservice.prv_authentication.service.AuthenticationService;
import id.co.microservice.prv_authentication.repository.UsersRepository;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.jose4j.jwt.NumericDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.OctetSequenceKey;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	
	@Autowired
	UsersRepository usersRepository;
	
	private static final float mMinutes = 15;
	private UUID uuid = UUID.randomUUID();

	@Override
	public Map<String, Object> authenticationService(UserLoginRequest userLoginRequest) {
		
		UsersEntity usersEntity = usersRepository.getUsersByUsername(userLoginRequest.getUsername());
		AccessTokenResponse accessToken = new AccessTokenResponse();
		RefreshTokenResponse refreshToken = new RefreshTokenResponse();
		Map<String, Object> response = new HashMap<>();
		Long exp = null;
		
		if (usersEntity.getUsername() != null) {
			
			Boolean passwordCorrect =  doPasswordsMatch(userLoginRequest.getPassword(), usersEntity.getPassword());
			
			if (Boolean.TRUE.equals(passwordCorrect)) {
				
				accessToken = accessTokenBuilder(usersEntity.getUsername());
				refreshToken = refreshTokenBuilder(usersEntity.getUsername());
				exp = offsetFromNow(mMinutes);
				
			}
			
		}
		
		response.put("access_token", accessToken);
		response.put("refresh_token", refreshToken);
		response.put("exp", exp);
		
		return response;
	}
	
	private Boolean doPasswordsMatch(String rawPassword,String encodedPassword) {
		  BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	      return passwordEncoder.matches(rawPassword, encodedPassword);
	}
	
	private AccessTokenResponse accessTokenBuilder(String username) {
		
		AccessTokenResponse accessToken = new AccessTokenResponse();
		NumericDate numericDate = NumericDate.now();
		
		Long exp = offsetFromNow(mMinutes);
		Long iat = numericDate.getValue();
		
		accessToken.setSub(username);
		accessToken.setAud("core-wfm");
		accessToken.setIss("prv-apps");
		accessToken.setJti(uuid);
		accessToken.setIat(iat);
		accessToken.setExp(exp);
		
		return accessToken;
	}
	
	private RefreshTokenResponse refreshTokenBuilder(String username) {
		
		RefreshTokenResponse refreshToken = new RefreshTokenResponse();
		Long exp = offsetFromNow(mMinutes+30);
		
		refreshToken.setSub(username);
		refreshToken.setAud("core-wfm");
		refreshToken.setIss("prv-apps");
		refreshToken.setJti(uuid);
		refreshToken.setExp(exp);
		
		return refreshToken;
	}
	
	private Long offsetFromNow(float offsetMinutes)
	{
	  NumericDate numericDate = NumericDate.now();
	  float secondsOffset = offsetMinutes * 60;
	  numericDate.addSeconds((long)secondsOffset);
	  
	  return numericDate.getValue();
	}

	@Override
	public Map<String, Object> jwkSet() throws NoSuchAlgorithmException {
		// Generate a secret key
		SecretKey hmacKey = null;
		try {
			hmacKey = getKeyFromPassword("noneofthisissecretkey", "noneofthisissalt");
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		
	    JWK jwk = new OctetSequenceKey.Builder(hmacKey)
			    .keyID("sim2") // give the key some ID (optional)
			    .algorithm(JWSAlgorithm.HS256) // indicate the intended key alg (optional)
			    .build();
	    
	    JwkKeys jwkKeys = new JwkKeys();
	    jwkKeys.setKty(jwk.getKeyType().toString());
	    jwkKeys.setAlg(jwk.getAlgorithm().toString());
	    jwkKeys.setKid(jwk.getKeyID());
	    jwkKeys.setK(jwk.getRequiredParams().get("k").toString());
	    
	    List<JwkKeys> keys = new ArrayList<>();
	    keys.add(jwkKeys);
	    
	    Map<String, Object> response = new HashMap<>();
	    response.put("keys", keys);
	    
	    return response;
	}
	
	private static SecretKey getKeyFromPassword(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
		
		return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
	}

}
