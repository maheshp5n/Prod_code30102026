package com.pratikshat.exicom.model.response;

import java.util.Set;

import com.pratikshat.exicom.entity.BtSchUseOnlyDetails;

public class BtSchOnlyResponseCopy {
	
	long SchOnlyId;
	String sparePartName;
	long sparePartQuantity;
	String sparePartReplaced;
	long resolutionMethodId;
	String resolutionMethodName;
	String sparePartReplacedFrom;
	String correctiveAction;
	String customerRate;                               ////////for site visit charge
	String customerBillingRate;                        ////////for customer spare charge
	
	Set<BtSolutionImpartedProblemResponse> problem;

	public BtSchOnlyResponseCopy(long schOnlyId, String sparePartName, long sparePartQuantity, String sparePartReplaced,
			long resolutionMethodId, String resolutionMethodName, String sparePartReplacedFrom, String correctiveAction,
			String customerRate, String customerBillingRate, Set<BtSolutionImpartedProblemResponse> problem) {
		super();
		SchOnlyId = schOnlyId;
		this.sparePartName = sparePartName;
		this.sparePartQuantity = sparePartQuantity;
		this.sparePartReplaced = sparePartReplaced;
		this.resolutionMethodId = resolutionMethodId;
		this.resolutionMethodName = resolutionMethodName;
		this.sparePartReplacedFrom = sparePartReplacedFrom;
		this.correctiveAction = correctiveAction;
		this.customerRate = customerRate;
		this.customerBillingRate = customerBillingRate;
		this.problem = problem;
	}

	public BtSchOnlyResponseCopy(BtSchUseOnlyDetails btSchUseOnlyDetails) {
		setData(btSchUseOnlyDetails, null);
	}
	 
	public void setData(BtSchUseOnlyDetails btSchUseOnlyDetails,Set<BtSolutionImpartedProblemResponse> btSolutionImpartedProblem) {
		this.SchOnlyId=btSchUseOnlyDetails.getId();
		this.sparePartName=btSchUseOnlyDetails.getSparePartName();
		this.sparePartQuantity=btSchUseOnlyDetails.getSparePartQuantity();
		this.sparePartReplaced=btSchUseOnlyDetails.getSparePartReplaced();
		if(btSchUseOnlyDetails!=null) {
			this.resolutionMethodId=btSchUseOnlyDetails.getResolutionMethod().getId();
			this.resolutionMethodName=btSchUseOnlyDetails.getResolutionMethod().getName();
		}
		this.sparePartReplacedFrom=btSchUseOnlyDetails.getSparePartReplacedFrom();
		this.correctiveAction=btSchUseOnlyDetails.getCorrectiveActions();
		this.customerBillingRate=btSchUseOnlyDetails.getCustomerBillingRate();
		this.customerRate=btSchUseOnlyDetails.getCustomerRate();
		if(btSolutionImpartedProblem!=null) {
			this.problem=btSolutionImpartedProblem;
			}
	}

	public long getSchOnlyId() {
		return SchOnlyId;
	}

	public String getCustomerRate() {
		return customerRate;
	}

	public void setCustomerRate(String customerRate) {
		this.customerRate = customerRate;
	}

	public String getCustomerBillingRate() {
		return customerBillingRate;
	}

	public void setCustomerBillingRate(String customerBillingRate) {
		this.customerBillingRate = customerBillingRate;
	}

	public void setSchOnlyId(long schOnlyId) {
		SchOnlyId = schOnlyId;
	}

	public String getSparePartName() {
		return sparePartName;
	}

	public void setSparePartName(String sparePartName) {
		this.sparePartName = sparePartName;
	}

	public long getSparePartQuantity() {
		return sparePartQuantity;
	}

	public void setSparePartQuantity(long sparePartQuantity) {
		this.sparePartQuantity = sparePartQuantity;
	}

	public String getSparePartReplaced() {
		return sparePartReplaced;
	}

	public void setSparePartReplaced(String sparePartReplaced) {
		this.sparePartReplaced = sparePartReplaced;
	}

	public long getResolutionMethodId() {
		return resolutionMethodId;
	}

	public void setResolutionMethodId(long resolutionMethodId) {
		this.resolutionMethodId = resolutionMethodId;
	}

	public String getResolutionMethodName() {
		return resolutionMethodName;
	}

	public void setResolutionMethodName(String resolutionMethodName) {
		this.resolutionMethodName = resolutionMethodName;
	}

	public String getSparePartReplacedFrom() {
		return sparePartReplacedFrom;
	}

	public void setSparePartReplacedFrom(String sparePartReplacedFrom) {
		this.sparePartReplacedFrom = sparePartReplacedFrom;
	}

	public String getCorrectiveAction() {
		return correctiveAction;
	}

	public void setCorrectiveAction(String correctiveAction) {
		this.correctiveAction = correctiveAction;
	}

	public Set<BtSolutionImpartedProblemResponse> getProblem() {
		return problem;
	}

	public void setProblem(Set<BtSolutionImpartedProblemResponse> problem) {
		this.problem = problem;
	}
}
