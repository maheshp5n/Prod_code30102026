//package com.pratikshat.exicom.entity;
//
//import java.io.Serializable;
//
//import javax.persistence.Entity;
//import javax.persistence.EntityListeners;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.ManyToOne;
//import javax.persistence.PostLoad;
//import javax.persistence.Transient;
//
//import org.apache.commons.lang.SerializationUtils;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import com.pratikshat.exicom.interceptor.ObjectAuditListener;
//
//@Entity
//@EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
//public class BtCustomerWccFilesUpload extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtCustomerWccFilesUpload>{
//	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	Long id;
//	String manualId;
//	
//	@ManyToOne(fetch = FetchType.LAZY)
//	BtCustomerWcc wccCustomerDetail;
//	
//	String uploadFileName;
//	String uploadPhysicalLocation;
//	
//	@ManyToOne(fetch=FetchType.LAZY)
//	EmployeeDetails owner;
//	
//	public BtCustomerWccFilesUpload() {
//	
//	}
//
//	public BtCustomerWccFilesUpload(Long id) {
//		this.id = id;
//	}
//
//	@Override
//	public Long getId() {
//		// TODO Auto-generated method stub
//		return this.id;
//	}
//
//	@Override
//	public void setId(Long id) {
//		this.id=id;
//		
//	}
//
//	@Override
//	public EmployeeDetails getOwner() {
//		// TODO Auto-generated method stub
//		return this.owner;
//	}
//
//	@Override
//	public void setOwner(EmployeeDetails u) {
//		// TODO Auto-generated method stub
//		this.owner=u;
//		
//	}
//
//	@Override
//	public String getManualId() {
//		// TODO Auto-generated method stub
//		return this.manualId;
//	}
//
//	@Override
//	public void setManualId(String manualId) {
//		// TODO Auto-generated method stub
//		this.manualId=manualId;
//		
//	}
//
//
//
//	public BtCustomerWcc getWccCustomerDetail() {
//		return wccCustomerDetail;
//	}
//
//	public void setWccCustomerDetail(BtCustomerWcc wccCustomerDetail) {
//		this.wccCustomerDetail = wccCustomerDetail;
//	}
//
//	public String getUploadFileName() {
//		return uploadFileName;
//	}
//
//	public void setUploadFileName(String uploadFileName) {
//		this.uploadFileName = uploadFileName;
//	}
//
//	public String getUploadPhysicalLocation() {
//		return uploadPhysicalLocation;
//	}
//
//	public void setUploadPhysicalLocation(String uploadPhysicalLocation) {
//		this.uploadPhysicalLocation = uploadPhysicalLocation;
//	}
//	
//	@Transient transient BtCustomerWccFilesUpload old;
//    @Override
//    @PostLoad
//    public void old() {
//       this.old=(BtCustomerWccFilesUpload)SerializationUtils.clone(this);
//    }
//
//
//
//    @Override
//    public BtCustomerWccFilesUpload gOld() {
//        
//        return this.old;
//    }
//   
//	
//	@Override
//	public String toString() {
//		return String.valueOf(this.id);
//	}
//
//}
