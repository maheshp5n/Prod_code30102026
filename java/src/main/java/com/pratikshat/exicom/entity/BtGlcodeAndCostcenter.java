package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BtGlcodeAndCostcenter {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String glCodeAndcostCenter;
	private Boolean isDeleted;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getGlCodeAndcostCenter() {
		return glCodeAndcostCenter;
	}
	public void setGlCodeAndcostCenter(String glCodeAndcostCenter) {
		this.glCodeAndcostCenter = glCodeAndcostCenter;
	}
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	
	
	
}
