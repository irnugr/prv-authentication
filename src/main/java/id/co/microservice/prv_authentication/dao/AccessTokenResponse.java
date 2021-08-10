package id.co.microservice.prv_authentication.dao;

import java.util.UUID;

public class AccessTokenResponse {
	
	private String sub;
	private String aud;
	private String iss;
	private UUID jti;
	private Long iat;
	private Long exp;
	
	public Long getIat() {
		return iat;
	}
	public void setIat(Long iat) {
		this.iat = iat;
	}
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	public String getAud() {
		return aud;
	}
	public void setAud(String aud) {
		this.aud = aud;
	}
	public String getIss() {
		return iss;
	}
	public void setIss(String iss) {
		this.iss = iss;
	}
	public UUID getJti() {
		return jti;
	}
	public void setJti(UUID jti) {
		this.jti = jti;
	}
	public Long getExp() {
		return exp;
	}
	public void setExp(Long exp) {
		this.exp = exp;
	}

}
