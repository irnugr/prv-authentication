package id.co.microservice.prv_authentication.entity;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MasterParamEntity {
	
	@Id
	private UUID id;
	
	private String paramCode;
	private String paramValue;
	private String paramDesc;
	private Timestamp createDate;
	private UUID createBy;
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getParamCode() {
		return paramCode;
	}
	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	public String getParamDesc() {
		return paramDesc;
	}
	public void setParamDesc(String paramDesc) {
		this.paramDesc = paramDesc;
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

}
