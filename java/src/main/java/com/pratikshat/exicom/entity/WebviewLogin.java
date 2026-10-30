package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class WebviewLogin implements java.io.Serializable,OldPresist<WebviewLogin>{
	@Id
	String userEmailId;
	//user tocken
	String sto;
	//run time keytoken
	String rnsl;
	//calculated server hash
	String serverHash;
	
	String callbackurl;
	
	public String getUserEmailId() {
		return userEmailId;
	}
	public void setUserEmailId(String userEmailId) {
		this.userEmailId = userEmailId;
	}
	public String getSto() {
		return sto;
	}
	public void setSto(String sto) {
		this.sto = sto;
	}
	public String getRnsl() {
		return rnsl;
	}
	public void setRnsl(String rnsl) {
		this.rnsl = rnsl;
	}
	public String getCallbackurl() {
		return callbackurl;
	}
	public void setCallbackurl(String callbackurl) {
		this.callbackurl = callbackurl;
	}
	public String getServerHash() {
		return serverHash;
	}
	public void setServerHash(String serverHash) {
		this.serverHash = serverHash;
	}
	
	

	@javax.persistence.Transient transient WebviewLogin old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(WebviewLogin)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public WebviewLogin gOld() {
		return old;
	}
	public String toString() {
			return ""+userEmailId;
	}
}
