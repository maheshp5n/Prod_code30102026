package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BtJobCardBatteryBalanceAt {
    @Id
    String btName;

    public BtJobCardBatteryBalanceAt(){}
    public BtJobCardBatteryBalanceAt(String btName){
        this.btName=btName;
    }

    public String getBtName() {
        return btName;
    }

    public void setBtName(String btName) {
        this.btName = btName;
    }

    public String toString(){
       return this.btName;
    }
    
}
