package com.pratikshat.exicom.config;

import java.util.Date;

public class ApiCallLog {
   private String userEmailId;
   private String callingApi;
   private Date callingDate;

    public ApiCallLog(String userEmailId,String callingApi){
        this.userEmailId=userEmailId;
        this.callingApi=callingApi;
        this.callingDate=new Date();
    }

    public String getUserEmailId() {
        return userEmailId;
    }

    // public void setUserEmailId(String userEmailId) {
    //     this.userEmailId = userEmailId;
    // }

    public String getCallingApi() {
        return callingApi;
    }

    // public void setCallingApi(String callingApi) {
    //     this.callingApi = callingApi;
    // }

    public Date getCallingDate() {
        return callingDate;
    }

    // public void setCallingDate(Date callingDate) {
    //     this.callingDate = callingDate;
    // }

    
}
