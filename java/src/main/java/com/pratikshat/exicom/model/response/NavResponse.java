package com.pratikshat.exicom.model.response;

public class NavResponse {
	long id;
	String name;
	String url;
	String icon;
	String parentName;
	int orderId;
	String defaultUrl;
	public NavResponse() {}
	public NavResponse(long id,String name,String url,String icon,String parentName,int orderId,String defaultUrl) {
		this.id=id;
		this.name=name;
		this.url=url;
		this.icon=icon;
		this.parentName=parentName;
		this.orderId=orderId;
		this.defaultUrl=defaultUrl;
	}
	
	public NavResponse(long id, String name, String url, String icon) {
		super();
		this.id = id;
		this.name = name;
		this.url = url;
		this.icon = icon;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @return the parentName
	 */
	public String getParentName() {
		return parentName;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @param parentName the parentName to set
	 */
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	/**
	 * @return the orderId
	 */
	public int getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the defaultUrl
	 */
	public String getDefaultUrl() {
		return defaultUrl;
	}
	/**
	 * @param defaultUrl the defaultUrl to set
	 */
	public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
	}
	
}
