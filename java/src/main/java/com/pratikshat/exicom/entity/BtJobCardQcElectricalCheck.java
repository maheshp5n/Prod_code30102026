package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BtJobCardQcElectricalCheck {
	@Id
    Long id;
    String value;

    public BtJobCardQcElectricalCheck() {
    }
    public BtJobCardQcElectricalCheck(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }

    
}
