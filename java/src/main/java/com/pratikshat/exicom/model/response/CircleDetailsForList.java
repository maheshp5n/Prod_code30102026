package com.pratikshat.exicom.model.response;

public class CircleDetailsForList {
	String circleName;
	public CircleDetailsForList() {}
	public CircleDetailsForList(String circleName) {
		this.circleName=circleName;
	}
	/**
	 * @return the circleName
	 */
	public String getCircleName() {
		return circleName;
	}

	/**
	 * @param circleName the circleName to set
	 */
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
}
