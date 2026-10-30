package com.pratikshat.exicom.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BtJobCardQcVoltagegap {
    @Id
    Long id;
    
    String value;

    public BtJobCardQcVoltagegap(Long id) {
        this.id = id;
    }
    public BtJobCardQcVoltagegap() {
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
