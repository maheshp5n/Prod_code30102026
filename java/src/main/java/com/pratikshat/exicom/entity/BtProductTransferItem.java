package com.pratikshat.exicom.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.pratikshat.exicom.interceptor.ObjectAuditListener;

@Entity
@EntityListeners({AuditingEntityListener.class,ObjectAuditListener.class})
public class BtProductTransferItem extends Auditable<EmployeeDetails> implements Serializable,OldPresist<BtProductTransferItem>{
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	Long id;
	String manualId;
	
	@ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	BtProductTransfer btpTransfer;
	
	@ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	BtProductRequestItem btpRequestItem;
	
	float transferQuantity;
	float receivedQuantity;
	
	@ManyToOne(fetch=javax.persistence.FetchType.LAZY)
	EmployeeDetails owner;
	boolean synced=false;

	


	public BtProductTransferItem() {}
	
	public BtProductTransferItem(long id) {
		this.id=id;
	}

	
	
	public BtProductTransfer getBtpTransfer() {
		return btpTransfer;
	}

	public void setBtpTransfer(BtProductTransfer btpTransfer) {
		this.btpTransfer = btpTransfer;
	}

	public BtProductRequestItem getBtpRequestItem() {
		return btpRequestItem;
	}

	/**
	 * @return the btpTransfer
	 */
	public BtProductTransfer getBtProductTransfer() {
		return btpTransfer;
	}
	/**
	 * @return the btpRequestItem
	 */
	public BtProductRequestItem getBtRequestItem() {
		return btpRequestItem;
	}
	/**
	 * @return the transferQuantity
	 */
	public float getTransferQuantity() {
		return transferQuantity;
	}
	/**
	 * @return the receivedQuantity
	 */
	public float getReceivedQuantity() {
		return receivedQuantity;
	}
	
	
	/**
	 * @return the owner
	 */
	public EmployeeDetails getOwner() {
		return owner;
	}

	/**
	 * @param btpTransfer the btpTransfer to set
	 */
	public void setBtProductTransfer(BtProductTransfer btpTransfer) {
		this.btpTransfer = btpTransfer;
	}
	/**
	 * @param btpRequestItem the btpRequestItem to set
	 */
	public void setBtpRequestItem(BtProductRequestItem btpRequestItem) {
		this.btpRequestItem = btpRequestItem;
	}
	/**
	 * @param transferQuantity the transferQuantity to set
	 */
	public void setTransferQuantity(float transferQuantity) {
		this.transferQuantity = transferQuantity;
	}
	/**
	 * @param receivedQuantity the receivedQuantity to set
	 */
	public void setReceivedQuantity(float receivedQuantity) {
		this.receivedQuantity = receivedQuantity;
	}
	
	/**
	 * @param owner the owner to set
	 */
	public void setOwner(EmployeeDetails owner) {
		this.owner = owner;
	}
	public boolean isSynced() {
		return synced;
	}
	public void setSynced(boolean synced) {
		this.synced = synced;
	}
	

	@javax.persistence.Transient transient BtProductTransferItem old;
	@Override
	@javax.persistence.PostLoad
	public void old() {
		old=(BtProductTransferItem)org.apache.commons.lang.SerializationUtils.clone(this);
	}
	@Override
	public BtProductTransferItem gOld() {
		return old;
	}
	public String toString() {
			return ""+id;
	}
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}
	@Override
	public void setId(Long id) {

		this.id = id;
		
	}
	@Override
	public String getManualId() {
		
		return manualId;
	}
	@Override
	public void setManualId(String manualId) {

		this.manualId = manualId;
		
	}
}
