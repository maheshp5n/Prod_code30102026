package com.pratikshat.exicom.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BtJobCardEngResolution {
    @Id
    String btName;

    public BtJobCardEngResolution(){
        
    }
    public BtJobCardEngResolution(String btName){
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
