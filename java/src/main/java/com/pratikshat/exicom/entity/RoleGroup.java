package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@javax.persistence.EntityListeners(com.pratikshat.exicom.interceptor.ObjectAuditListener.class)
public class RoleGroup implements java.io.Serializable,OldPresist<RoleGroup>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	String groupName;
	
	public RoleGroup() {}
	
	public RoleGroup(Integer id) {
		this.id=id;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}


	@javax.persistence.Transient transient RoleGroup old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(RoleGroup)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public RoleGroup gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
}
