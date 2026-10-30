package com.pratikshat.exicom.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BtJobCardQcCorrectiveActiontaken {
    @Id
    Long id;
      
    String value;

    public BtJobCardQcCorrectiveActiontaken(Long id) {
        this.id = id;
    }
    public BtJobCardQcCorrectiveActiontaken() {
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
