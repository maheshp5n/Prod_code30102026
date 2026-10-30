package com.psl.exicom.bt;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class BtPlant extends Thread {
  String user = App.user;
  
  public void run() {
    while (true) {
      synkPlant();
      try {
        sleep(900000L);
      } catch (Exception exception) {}
    } 
  }
  
  private void synkPlant() {
    SqlHelper _sap = new SqlHelper("SAP");
    Date today = new Date();
    try {
      List<Map<String, Object>> plants = _sap.getResultSet("select * from CRM_BT_PLANT where TFLAG='N'");
      for (Map<String, Object> p : plants) {
        SqlHelper sap = new SqlHelper("SAP");
        SqlHelper crm = new SqlHelper("CRM");
        try {
          sap.beginTransaction();
          crm.beginTransaction();
          if (p.get("SFLAG").toString().equalsIgnoreCase("CR")) {
            Map<String, Object> ex = crm.getSingleResult("select * from location where plant=?", new Object[] { p.get("WERKS") });
            if (ex == null) {
              crm.executeUpdate("insert into location(created_date,inventory_location,location_name,mobile_location,modified_date,created_by_email_id,location_type_location_type_id,modified_by_email_id,plant,city,country_key,house_number,name2,postal_code,reason_code,remarks,owner_email_id,company_code,profit_center,`business_domain_business_domain_id`) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] { 
                    today, 
                    Boolean.valueOf(true), 
                    p.get("NAME1"), 
                    Boolean.valueOf(false), 
                    today, 
                    this.user, 
                    Integer.valueOf(p.get("PLANT_TYPE").toString().equalsIgnoreCase("F") ? 4 : 1), 
                    this.user, 
                    p.get("WERKS"), 
                    p.get("ORT01"), 
                    p.get("COUNC"), 
                    p.get("STRAS"), 
                    p.get("NAME2"), 
                    p.get("PSTLZ"), 
                    p.get("REGIO"), 
                    p.get("REMARK"), 
                    this.user, 
                    p.get("BUKRS"), 
                    p.get("PRCTR"), 
                    Integer.valueOf(1) });
              try {
                crm.executeUpdate("insert into bt_plant_details(plant_code,plant_name) values(?,?)", new Object[] { p.get("WERKS"), p.get("NAME1") });
              } catch (Exception exception) {}
              sap.executeUpdate("update CRM_BT_PLANT set TFLAG='Y' where WERKS=?", new Object[] { p.get("WERKS") });
            } else {
              crm.executeUpdate("update location set inventory_location=?,location_name=?,mobile_location=?,modified_date=?,location_type_location_type_id=?,modified_by_email_id=?,city=?,country_key=?,house_number=?,name2=?,postal_code=?,reason_code=?,remarks=?,company_code=?,profit_center=?,`business_domain_business_domain_id`=? where plant=?", new Object[] { 
                    Boolean.valueOf(true), 
                    p.get("NAME1"), 
                    Boolean.valueOf(false), 
                    today, 
                    Integer.valueOf(p.get("PLANT_TYPE").toString().equalsIgnoreCase("F") ? 4 : 1), 
                    this.user, 
                    p.get("ORT01"), 
                    p.get("COUNC"), 
                    p.get("STRAS"), 
                    p.get("NAME2"), 
                    p.get("PSTLZ"), 
                    p.get("REGIO"), 
                    p.get("REMARK"), 
                    p.get("BUKRS"), 
                    p.get("PRCTR"), 
                    Integer.valueOf(3), 
                    p.get("WERKS") });
              crm.executeUpdate("update bt_plant_details set plant_name=? where plant_code=?", new Object[] { (p.get("NAME1") == null) ? p.get("WERKS") : p.get("NAME1"), p.get("WERKS") });
              sap.executeUpdate("update CRM_BT_PLANT set TFLAG='Y' where WERKS=?", new Object[] { p.get("WERKS") });
            } 
          } 
          if (p.get("SFLAG").toString().equalsIgnoreCase("CH")) {
            crm.executeUpdate("update location set inventory_location=?,location_name=?,mobile_location=?,modified_date=?,location_type_location_type_id=?,modified_by_email_id=?,city=?,country_key=?,house_number=?,name2=?,postal_code=?,reason_code=?,remarks=?,company_code=?,profit_center=?,`business_domain_business_domain_id`=? where plant=?", new Object[] { 
                  Boolean.valueOf(true), 
                  p.get("NAME1"), 
                  Boolean.valueOf(false), 
                  today, 
                  Integer.valueOf(p.get("PLANT_TYPE").toString().equalsIgnoreCase("F") ? 4 : 1), 
                  this.user, 
                  p.get("ORT01"), 
                  p.get("COUNC"), 
                  p.get("STRAS"), 
                  p.get("NAME2"), 
                  p.get("PSTLZ"), 
                  p.get("REGIO"), 
                  p.get("REMARK"), 
                  p.get("BUKRS"), 
                  p.get("PRCTR"), 
                  Integer.valueOf(3), 
                  p.get("WERKS") });
            try {
              crm.executeUpdate("update bt_plant_details set plant_name=? where plant_code=?", new Object[] { (p.get("NAME1") == null) ? p.get("WERKS") : p.get("NAME1"), p.get("WERKS") });
            } catch (Exception exception) {}
            sap.executeUpdate("update CRM_BT_PLANT set TFLAG='Y' where WERKS=?", new Object[] { p.get("WERKS") });
          } 
          crm.commit();
          sap.commit();
        } catch (Exception ex) {
          ex.printStackTrace();
          sap.rollBack();
          crm.rollBack();
        } 
        sap = null;
        crm = null;
      } 
      plants = null;
    } catch (Exception e) {
      e.printStackTrace();
    } 
    _sap = null;
  }
}
