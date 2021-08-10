package id.co.microservice.prv_authentication.entity;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

@Entity
public class UsersEntity {
	
	@Id
	private UUID id;
	
	private String username;
	private String password;
	private String email;
	private String firstname;
	private String lastname;
	private Timestamp createDate;
	
	@Type(type="pg-uuid")
	private UUID createBy;
	private Timestamp updateDate;
	private UUID updateBy;
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public UUID getCreateBy() {
		return createBy;
	}
	public void setCreateBy(UUID createBy) {
		this.createBy = createBy;
	}
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	public UUID getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(UUID updateBy) {
		this.updateBy = updateBy;
	}

}
