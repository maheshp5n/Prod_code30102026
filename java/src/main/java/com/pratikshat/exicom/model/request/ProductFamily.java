package com.pratikshat.exicom.model.request;

public class ProductFamily {
	long id;
	String name;
	
	
	/**
	 * 
	 */
	public ProductFamily() {
	}
	/**
	 * @param id
	 * @param name
	 */
	public ProductFamily(long id, String name) {
		this.id = id;
		this.name = name;
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}
