package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BtBerRemarks {
    @Id
    String btName;

    public BtBerRemarks(){}
    public BtBerRemarks(String btName){
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
