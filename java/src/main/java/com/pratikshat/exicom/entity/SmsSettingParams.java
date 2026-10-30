package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class SmsSettingParams implements java.io.Serializable,OldPresist<SmsSettingParams>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String keyParam;
	String valueParam;
	String paramType;
	@ManyToOne(fetch=FetchType.LAZY)
	SmsSetting smsSetting;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public String getKeyParam() {
		return keyParam;
	}
	public void setKeyParam(String keyParam) {
		this.keyParam = keyParam;
	}
	public String getValueParam() {
		return valueParam;
	}
	public void setValueParam(String valueParam) {
		this.valueParam = valueParam;
	}
	public SmsSetting getSmsSetting() {
		return smsSetting;
	}
	public void setSmsSetting(SmsSetting smsSetting) {
		this.smsSetting = smsSetting;
	}
	public String getParamType() {
		return paramType;
	}
	public void setParamType(String paramType) {
		this.paramType = paramType;
	}
	

	@javax.persistence.Transient transient SmsSettingParams old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(SmsSettingParams)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public SmsSettingParams gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
