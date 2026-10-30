package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import org.apache.commons.lang.SerializationUtils;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@javax.persistence.EntityListeners(ObjectAuditListener.class)
public class BtServicePayout implements  java.io.Serializable,OldPresist<BtServicePayout>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private boolean synk;
	
	
	public BtServicePayout() {
		super();
	}
	public BtServicePayout(Long id) {
	
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isSynk() {
		return synk;
	}
	public void setSynk(boolean synk) {
		this.synk = synk;
	}
	
	@Transient transient BtServicePayout old;
	@Override
	@PostLoad
	public void old() {
		old=(BtServicePayout) SerializationUtils.clone(this);
	}
	@Override
	public BtServicePayout gOld() {
		return old;
	}
	
	public String toString() {
		return ""+String.valueOf(id);
	} 
}
