package com.pratikshat.exicom.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class HelpGuide {
	@Id
	Long id;
	@Column(length = 1000)
	String topic;
	String platForm;
	String stackHolder;
	String lanuguage;
	@Column(length =1000)
	String url;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getPlatForm() {
		return platForm;
	}
	public void setPlatForm(String platForm) {
		this.platForm = platForm;
	}
	public String getStackHolder() {
		return stackHolder;
	}
	public void setStackHolder(String stackHolder) {
		this.stackHolder = stackHolder;
	}
	
	public String getLanuguage() {
		return lanuguage;
	}
	public void setLanuguage(String lanuguage) {
		this.lanuguage = lanuguage;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
