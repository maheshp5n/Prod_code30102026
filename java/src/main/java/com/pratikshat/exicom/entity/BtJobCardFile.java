package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class BtJobCardFile extends Auditable<EmployeeDetails> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String fileType;
    String filePath;
    String fileName;

    public BtJobCardFile(){}
    public BtJobCardFile(Long id){
        this.id=id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    BtJobCard jobCard;

    @ManyToOne(fetch = FetchType.LAZY)
    EmployeeDetails owner;

    

    public String getFileType() {
        return fileType;
    }
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public BtJobCard getJobCard() {
        return jobCard;
    }
    public void setJobCard(BtJobCard jobCard) {
        this.jobCard = jobCard;
    }
    @Override
    public Long getId() {
        
        return id;
    }

    @Override
    public void setId(Long id) {
        
        this.id=id;
    }

    @Override
    public EmployeeDetails getOwner() {
        
        return this.owner;
    }

    @Override
    public void setOwner(EmployeeDetails u) {
        this.owner=u;
    }

    @Override
    public String getManualId() {
        return null;
    }

    @Override
    public void setManualId(String manualId) {
    }
    
}
