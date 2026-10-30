package com.pratikshat.exicom.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class BtJobCardQcPhysicalcheck{
    @Id
    Long id;
   
    String value;

    public BtJobCardQcPhysicalcheck(){}
    public BtJobCardQcPhysicalcheck(Long id){
        this.id=id;
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