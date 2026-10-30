package com.pratikshat.exicom.model.response;

public class AssetsForList {
	String assetsSerialNumber;
	String partCode;
	String productDescription;
	
	public AssetsForList() {}
	public AssetsForList(String assetsSerialNumber,String partCode,String productDescription) {
		this.assetsSerialNumber=assetsSerialNumber;
		this.partCode=partCode;
		this.productDescription=productDescription;
	}
	/**
	 * @return the assetsSerialNumber
	 */
	public String getAssetsSerialNumber() {
		return assetsSerialNumber;
	}
	/**
	 * @return the partCode
	 */
	public String getPartCode() {
		return partCode;
	}
	/**
	 * @return the productDescription
	 */
	public String getProductDescription() {
		return productDescription;
	}
	/**
	 * @param assetsSerialNumber the assetsSerialNumber to set
	 */
	public void setAssetsSerialNumber(String assetsSerialNumber) {
		this.assetsSerialNumber = assetsSerialNumber;
	}
	/**
	 * @param partCode the partCode to set
	 */
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	/**
	 * @param productDescription the productDescription to set
	 */
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	
}
