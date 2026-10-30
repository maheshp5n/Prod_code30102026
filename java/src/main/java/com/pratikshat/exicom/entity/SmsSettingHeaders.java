package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class SmsSettingHeaders implements java.io.Serializable,OldPresist<SmsSettingHeaders>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@ManyToOne(fetch=FetchType.LAZY)
	SmsSetting smsSetting;
	String keyHeader;
	String valueHeader;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public SmsSetting getSmsSetting() {
		return smsSetting;
	}
	public void setSmsSetting(SmsSetting smsSetting) {
		this.smsSetting = smsSetting;
	}
	public String getKeyHeader() {
		return keyHeader;
	}
	public void setKeyHeader(String keyHeader) {
		this.keyHeader = keyHeader;
	}
	public String getValueHeader() {
		return valueHeader;
	}
	public void setValueHeader(String valueHeader) {
		this.valueHeader = valueHeader;
	}
	

	@javax.persistence.Transient transient SmsSettingHeaders old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(SmsSettingHeaders)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public SmsSettingHeaders gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
