package com.pratikshat.exicom.entity;
public interface DomainObject{
    void setId(Long id);
	Long getId();
	void setOwner(LoginUser owner);
	LoginUser getOwner();
}