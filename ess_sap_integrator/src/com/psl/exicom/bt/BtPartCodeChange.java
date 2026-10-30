package com.psl.exicom.bt;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BtPartCodeChange implements Runnable {
  String user = App.user;
  
  public void run() {
    try {
      partcodeChangeRequest();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  private void partcodeChangeRequest() {
    SimpleDateFormat sapFormat = new SimpleDateFormat("yyyyMMdd");
    SqlHelper _crm = new SqlHelper("CRM");
    try {
      List<Map<String, Object>> lstData = _crm.getResultSet("SELECT con.created_date ,con.created_by_email_id, con.id,con.`last_modified_by_email_id`,\r\n\toldprd.manual_id AS old_partcode, newprd.manual_id AS new_part_code, con.remark,con.synked,sr.`location_location_id`\r\n\tFROM  `bt_service_appoinment_product_consumed` con\r\n\tJOIN bt_products oldprd ON oldprd.id= con.`consumed_id`\r\n\tJOIN bt_products newprd ON newprd.id= con.`replaced_id`\r\n\tJOIN `bt_service_appointment` ser ON ser.`service_appointment_id`= con.`service_appointment_service_appointment_id`\r\n\tJOIN `service_recource` sr ON sr.`service_resource_id`= ser.`service_resource_service_resource_id`\r\n\tWHERE `consumption_type_consumption_type_name` LIKE 'Replaced' AND synked = FALSE");
      for (Map<String, Object> data : lstData) {
        SqlHelper crm = new SqlHelper("CRM");
        SqlHelper sap = new SqlHelper("SAP");
        try {
          String cby = (data.get("created_by_email_id") == null) ? "" : data.get("created_by_email_id").toString();
          if (cby.length() > 24)
            cby = cby.substring(0, 24); 
          String lmby = (data.get("last_modified_by_email_id") == null) ? "" : data.get("last_modified_by_email_id").toString();
          if (lmby.length() > 24)
            lmby = lmby.substring(0, 24); 
          crm.beginTransaction();
          sap.beginTransaction();
          sap.executeUpdate("insert into CRM_BT_MAT_CONV(CRM_DOCNO,CRM_LITM,BKTXT,BLDAT,BUDAT,USNAM,MATNR,MATNR_N,BWARTWA,GRUND,LGORT,ERFMG,SERNR,WERKS,KOSTL,WEMPF,SFLAG,TFLAG,SYDAT,REMARK) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", 
              
              new Object[] { 
                data.get("id"), 
                data.get("id"), 
                "", 
                sapFormat.format(data.get("created_date")), 
                sapFormat.format(new Date()), 
                cby, 
                data.get("old_partcode"), 
                data.get("new_part_code"), 
                "", 
                "", 
                data.get("location_location_id"), 
                Integer.valueOf(1), 
                "", 
                getWarehousePlantCode(Long.parseLong(data.get("location_location_id").toString())), 
                "", 
                "", 
                "CR", 
                "N", 
                sapFormat.format(new Date()), 
                "" });
          crm.executeUpdate("UPDATE `bt_service_appoinment_product_consumed` SET synked=? WHERE id=?", new Object[] { Boolean.valueOf(true), data.get("id") });
          crm.commit();
          sap.commit();
        } catch (Exception e) {
          e.printStackTrace();
          System.err.println("Unable to update part code chagne for request " + data.get("new_part_code") + " with old part code" + data.get("old_partcode"));
          sap.rollBack();
          crm.rollBack();
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private Object getWarehousePlantCode(long warehouseId) throws Exception {
    String plantCode = "";
    SqlHelper crm = new SqlHelper("CRM");
    Map<String, Object> _p = crm.getSingleResult("select location_id,location_type_location_type_id,parent_location_location_id,plant from location where location_id=?", new Object[] { Long.valueOf(warehouseId) });
    if (_p.get("location_type_location_type_id").toString().equalsIgnoreCase("1")) {
      plantCode = _p.get("plant").toString();
      return plantCode;
    } 
    return getWarehousePlantCode(Long.parseLong(_p.get("parent_location_location_id").toString()));
  }
}
