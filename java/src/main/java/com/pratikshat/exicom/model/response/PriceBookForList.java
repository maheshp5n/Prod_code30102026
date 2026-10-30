package com.pratikshat.exicom.model.response;

public class PriceBookForList {
	long id;
	String name;

	public PriceBookForList(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
