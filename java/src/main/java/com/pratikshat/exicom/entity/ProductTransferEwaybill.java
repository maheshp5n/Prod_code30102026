package com.pratikshat.exicom.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_transfer_ewaybill")
public class ProductTransferEwaybill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_transfer_id")
    private Long productTransferId;

    @Column(name = "product_transfer_number")
    private String productTransferNumber;

    @Column(name = "ewaybill_number")
    private String ewaybillNumber;

    @Column(name = "ewaybill_date")
    private LocalDate ewaybillDate;

    @Column(name = "file_path")
    private String filePath;
    
    @Column(name = "ewb_status")
    private int ewb_status;


    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;
    @Column(name = "request_number")
    private String requestNumber;

    @Column(name = "created_date")
    private java.time.LocalDateTime createdDate;

    @Column(name = "approval_status")
    private String approvalStatus;

    @Column(name = "approved_date")
    private java.time.LocalDateTime approvedDate;

    @Column(name = "remarks")
    private String remarks;


    public String getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(String requestNumber) {
        this.requestNumber = requestNumber;
    }

    public int getEwb_status() {
		return ewb_status;
	}

	public void setEwb_status(int ewb_status) {
		this.ewb_status = ewb_status;
	}

	public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public LocalDateTime getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(LocalDateTime approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductTransferId() {
        return productTransferId;
    }

    public void setProductTransferId(Long productTransferId) {
        this.productTransferId = productTransferId;
    }

    public String getProductTransferNumber() {
        return productTransferNumber;
    }

    public void setProductTransferNumber(String productTransferNumber) {
        this.productTransferNumber = productTransferNumber;
    }

    public String getEwaybillNumber() {
        return ewaybillNumber;
    }

    public void setEwaybillNumber(String ewaybillNumber) {
        this.ewaybillNumber = ewaybillNumber;
    }

    public LocalDate getEwaybillDate() {
        return ewaybillDate;
    }

    public void setEwaybillDate(LocalDate ewaybillDate) {
        this.ewaybillDate = ewaybillDate;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    
}
