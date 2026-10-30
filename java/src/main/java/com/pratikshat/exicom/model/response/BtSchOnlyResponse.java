//package com.pratikshat.exicom.model.response;
//
//import java.util.Set;
//
//import com.pratikshat.exicom.entity.BtSchUseOnlyDetails;
//
//public class BtSchOnlyResponse {
//	
//	long SchOnlyId;
//	String sparePartName;
//	long sparePartQuantity;
//	String sparePartReplaced;
//	long resolutionMethodId;
//	String resolutionMethodName;
//	String sparePartReplacedFrom;
//	String correctiveAction;
//	
//	public BtSchOnlyResponse(BtSchUseOnlyDetails sch) {
//		this.SchOnlyId=sch.getId();
//		this.sparePartName=sch.getSparePartName();
//		this.sparePartQuantity=sch.getSparePartQuantity();
//		this.sparePartReplaced=sch.getSparePartReplaced();
//		this.resolutionMethodId=sch.getResolutionMethod().getId();
//		this.resolutionMethodName=sch.getResolutionMethod().getName();
//		this.sparePartReplacedFrom=sch.getSparePartReplacedFrom();
//		this.correctiveAction=sch.getCorrectiveActions();
//	}
//	
////	public void setData(BtSchUseOnlyDetails sch, Set<BtSolutionImpartedProblemResponse> problemResponses){
////		
////		if(problemResponses!=null) {
////			this.impartedProblemResponses=problemResponses;
////		}
////	}
//	
//	public BtSchOnlyResponse(long schOnlyId, String sparePartName, long sparePartQuantity, String sparePartReplaced,
//			long resolutionMethodId,String resolutionMethodName, String sparePartReplacedFrom) {
//		super();
//		SchOnlyId = schOnlyId;
//		this.sparePartName = sparePartName;
//		this.sparePartQuantity = sparePartQuantity;
//		this.sparePartReplaced = sparePartReplaced;
//		this.resolutionMethodId = resolutionMethodId;
//		this.resolutionMethodName=resolutionMethodName;
//		this.sparePartReplacedFrom = sparePartReplacedFrom;
//	}
//	public BtSchOnlyResponse() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//	public long getSchOnlyId() {
//		return SchOnlyId;
//	}
//	public void setSchOnlyId(long schOnlyId) {
//		SchOnlyId = schOnlyId;
//	}
//	public String getSparePartName() {
//		return sparePartName;
//	}
//	public void setSparePartName(String sparePartName) {
//		this.sparePartName = sparePartName;
//	}
//	public long getSparePartQuantity() {
//		return sparePartQuantity;
//	}
//	public void setSparePartQuantity(long sparePartQuantity) {
//		this.sparePartQuantity = sparePartQuantity;
//	}
//	public String getSparePartReplaced() {
//		return sparePartReplaced;
//	}
//	public void setSparePartReplaced(String sparePartReplaced) {
//		this.sparePartReplaced = sparePartReplaced;
//	}
//	public long getResolutionMethodId() {
//		return resolutionMethodId;
//	}
//	public void setResolutionMethodId(long resolutionMethodId) {
//		this.resolutionMethodId = resolutionMethodId;
//	}
//	
//	public String getResolutionMethodName() {
//		return resolutionMethodName;
//	}
//
//	public void setResolutionMethodName(String resolutionMethodName) {
//		this.resolutionMethodName = resolutionMethodName;
//	}
//
//	public String getSparePartReplacedFrom() {
//		return sparePartReplacedFrom;
//	}
//	public void setSparePartReplacedFrom(String sparePartReplacedFrom) {
//		this.sparePartReplacedFrom = sparePartReplacedFrom;
//	}
//
//	public String getCorrectiveAction() {
//		return correctiveAction;
//	}
//
//	public void setCorrectiveAction(String correctiveAction) {
//		this.correctiveAction = correctiveAction;
//	}
//	
//	
//}
