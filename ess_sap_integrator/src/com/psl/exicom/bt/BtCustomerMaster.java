package com.psl.exicom.bt;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class BtCustomerMaster implements Runnable {
  String user = App.user;
  
  public void run() {
    SynkBtCustomerMasterCreate();
    SynkBtCustomerMasterUpdate();
  }
  
  public void SynkBtCustomerMasterCreate() {
    SqlHelper _sap = new SqlHelper("SAP");
    try {
      List<Map<String, Object>> _accounts = _sap.getResultSet("select * from CRM_BT_CUST_MASTER WHERE TFLAG='N' and SFLAG='CR'");
      if (_accounts != null && _accounts.size() > 0)
        for (Map<String, Object> m : _accounts) {
          SqlHelper sap = new SqlHelper("SAP");
          SqlHelper crm = new SqlHelper("CRM");
          try {
            Date today = new Date();
            sap.beginTransaction();
            crm.beginTransaction();
            List<Map<String, Object>> customerExist = crm.getResultSet("SELECT * FROM `customer_deatils` WHERE `customer_name`=?", new Object[] { m.get("HKUNNR") });
            if (customerExist.size() == 0 || customerExist == null) {
              crm.executeUpdate("INSERT INTO customer_deatils(created_date,customer_name,modified_date,synced,created_by_email_id,modified_by_email_id) VALUES(?,?,?,?,?,?)", new Object[] { today, 
                    m.get("HKUNNR"), 
                    today, 
                    Boolean.valueOf(true), 
                    this.user, 
                    this.user });
              customerExist = crm.getResultSet("SELECT * FROM `customer_deatils` WHERE `customer_name`=?", new Object[] { m.get("HKUNNR") });
            } 
            Map<String, Object> account = crm.getSingleResult("SELECT * FROM `accounts` WHERE `sap_customer_number`=?", new Object[] { m.get("KUNNR") });
            if (account == null) {
              crm.executeUpdate("INSERT INTO `accounts` (`sap_customer_number`,`created_date`,`description`,`email_id`,`last_modified`,`name`,`phone_number`,`synced`,`account_owner_details_id`,`account_type`,`billing_info_id`,`catagory_id`,`created_by`,`modified_by`,`parent_account_sap_customer_number`,`parent_customer_customer_id`,`shipping_info_id`,`crm_id`) VALUE (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", 
                  
                  new Object[] { 
                    m.get("KUNNR"), new Date(), new Date(), m.get("NAME"), 
                    m.get("TELF1"), Boolean.valueOf(true), App.user, 
                    
                    Integer.valueOf(1), App.user, 
                    App.user, 
                    ((Map)customerExist.get(0)).get("customer_id") });
              sap.executeUpdate("update CRM_BT_CUST_MASTER set TFLAG='Y', CRM_DOCNO=? where KUNNR=? and HKUNNR=?", 
                  new Object[] { m.get("KUNNR"), m.get("KUNNR"), m.get("HKUNNR") });
            } else {
              if (Integer.parseInt(account.get("catagory_id").toString()) == 2)
                crm.executeUpdate("UPDATE `accounts` SET `catagory_id`=3 where sap_customer_number=? ", 
                    new Object[] { m.get("KUNNR") }); 
              sap.executeUpdate("update CRM_BT_CUST_MASTER set TFLAG='Y', CRM_DOCNO=? where KUNNR=? and HKUNNR=?", 
                  new Object[] { account.get("sap_customer_number"), m.get("KUNNR"), m.get("HKUNNR") });
            } 
            sap.commit();
            crm.commit();
          } catch (Exception e) {
            sap.rollBack();
            crm.rollBack();
            e.printStackTrace();
          } 
          sap = null;
          crm = null;
        }  
      _sap = null;
      _accounts = null;
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void SynkBtCustomerMasterUpdate() {
    SqlHelper _sap = new SqlHelper("SAP");
    try {
      List<Map<String, Object>> _accounts = _sap
        .getResultSet("select * from CRM_BT_CUST_MASTER WHERE TFLAG='N' and SFLAG='CH'");
      if (_accounts != null && _accounts.size() > 0) {
        SqlHelper sap = new SqlHelper("SAP");
        SqlHelper crm = new SqlHelper("CRM");
        try {
          sap.beginTransaction();
          crm.beginTransaction();
          for (Map<String, Object> m : _accounts) {
            Map<String, Object> account = crm.getSingleResult(
                "SELECT * FROM `accounts` WHERE `sap_customer_number`=?", 
                new Object[] { m.get("KUNNR") });
            if (account != null) {
              crm.executeUpdate("UPDATE `address_information` SET `address`=?,`city`=?,`latitude`=?,`longitude`=?,`pincode`=?,`street`=?,`synced`=?,`state_id`=?,`crm_id`=?,`address_type`=? WHERE `address_info_id`=?", 
                  
                  new Object[0]);
              Map<String, Object> customerExist = crm.getSingleResult("SELECT * FROM `customer_deatils` WHERE `customer_name`=?", new Object[] { account.get("HKUNNR") });
              if (customerExist == null)
                throw new Exception("No Parent customer found " + m.get("HKUNNR") + " this"); 
              crm.executeUpdate("UPDATE `accounts` SET `last_modified`=?,`name`=?,`phone_number`=?,`modified_by`=?`parent_account_sap_customer_number`=?,`parent_customer_customer_id`=?, WHERE `sap_customer_number`=?", 
                  
                  new Object[] { new Date(), m.get("NAME"), m.get("TELF1"), App.user, 
                    customerExist.get("customer_id"), m.get("KUNNR") });
            } 
            sap.commit();
            crm.commit();
          } 
        } catch (Exception e) {
          e.printStackTrace();
          sap.rollBack();
          crm.rollBack();
        } 
        sap = null;
        crm = null;
      } 
      _accounts = null;
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
