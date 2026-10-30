package com.psl.exicom.bt;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BtProductConsumeSynk implements Runnable {
  public void run() {
    try {
      synkServiceAppointment();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    try {
      repariCenterConsumed();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  private void synkServiceAppointment() {
    SqlHelper _crm = new SqlHelper("CRM");
    SqlHelper crm = new SqlHelper("CRM");
    SqlHelper sap = new SqlHelper("SAP");
    try {
      sap.beginTransaction();
      crm.beginTransaction();
      SimpleDateFormat dformat = new SimpleDateFormat("yyyyMMdd");
      SimpleDateFormat tformat = new SimpleDateFormat("HHmmss");
      List<Map<String, Object>> cdata = _crm.getResultSet("SELECT scon.id AS consumeid,\r\n\tscon.`created_date` AS cdate,\r\n\tscon.`quantity` AS cquantity,\r\n\tprd.manual_id AS partcode,\r\n\tscon.`consumption_type_consumption_type_name`,\r\n\tsr.`location_location_id` AS locationid,\r\n\tsa.manual_id AS appointmentnumber,\r\n\tsa.cost_center AS costcenter,\r\n\temp.first_name AS firstName,\r\n\temp.middle_name AS middleName,\r\n\temp.last_name AS lastName,\r\n\tSUBSTRING(scon.remark,1,100) AS remark\r\n\tFROM `bt_service_appoinment_product_consumed` scon\r\n\tJOIN `bt_service_appointment` sa ON sa.`service_appointment_id`= scon.`service_appointment_service_appointment_id`\r\n\tJOIN bt_products prd ON prd.id= scon.`consumed_id`\r\n\tJOIN `service_recource` sr ON sr.`service_resource_id`= sa.`service_resource_service_resource_id`\r\n\tJOIN `employee_details` emp ON emp.`email_id`=sr.`user_email_id`\r\n\tWHERE scon.`synked`=FALSE AND sa.`status_status_id` IN (4,5,6,7) AND scon.`consumption_type_consumption_type_name` LIKE 'Consumed'");
      for (Map<String, Object> sa : cdata) {
        Date bldate = (Date)sa.get("cdate");
        Map<String, Object> wareHouse = getWareHouse(Long.parseLong(sa.get("locationid").toString()), crm);
        sap.executeUpdate("insert into CRM_BT_STOCK_CONS(CRM_DOCNO,CRM_LITM,BKTXT,BLDAT,BUDAT,USNAM,MATNR,BWARTWA,GRUND,LGORT,ERFMG,SERNR,WERKS,KOSTL,SFLAG,TFLAG,SYDAT,SYTIM,REMARK) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] { 
              sa.get("appointmentnumber"), 
              sa.get("consumeid"), 
              (sa.get("remark") == null) ? "" : sa.get("remark"), 
              dformat.format(bldate), 
              dformat.format(bldate), 
              String.valueOf((sa.get("firstName") == null) ? "" : sa.get("firstName")) + ((sa.get("lastName") == null) ? "" : (" " + sa.get("lastName"))), 
              sa.get("partcode"), 
              "", 
              "", 
              wareHouse.get("location_id"), 
              sa.get("cquantity"), 
              "null", 
              wareHouse.get("plant"), 
              (sa.get("costcenter") == null) ? "" : sa.get("costcenter"), 
              "CR", 
              "N", 
              dformat.format(new Date()), 
              tformat.format(new Date()), 
              "" });
        crm.executeUpdate("UPDATE `bt_service_appoinment_product_consumed` SET synked=? WHERE id=?", new Object[] { Boolean.valueOf(true), sa.get("consumeid") });
      } 
      sap.commit();
      crm.commit();
    } catch (Exception e) {
      sap.rollBack();
      crm.rollBack();
      e.printStackTrace();
    } 
  }
  
  private void repariCenterConsumed() {
    SqlHelper _crm = new SqlHelper("CRM");
    SqlHelper crm = new SqlHelper("CRM");
    SqlHelper sap = new SqlHelper("SAP");
    try {
      sap.beginTransaction();
      crm.beginTransaction();
      SimpleDateFormat dformat = new SimpleDateFormat("yyyyMMdd");
      SimpleDateFormat tformat = new SimpleDateFormat("HHmmss");
      List<Map<String, Object>> cdata = _crm.getResultSet("SELECT pc.id AS consumeId,\r\n\t\t\t\t\t\t\tprd.manual_id AS partCode,\r\n\t\t\t\t\t\t\tpc.quantity AS qty,\r\n\t\t\t\t\t\t\tsr.location_location_id AS warehouseid,\r\n\t\t\t\t\t\t\tpc.created_date AS createdDate,\r\n\t\t\t\t\t\t\tjc.eng_remarks AS remarks,\r\n\t\t\t\t\t\t\tjc.manual_id AS jobCardId,\r\n\t\t\t\t\t\t\temp.first_name AS firstname,\r\n\t\t\t\t\t\t\temp.middle_name AS middlename,\r\n\t\t\t\t\t\t\temp.last_name AS lastname\r\n\t\t\t\t\t\t\tFROM `bt_job_card_product_consume` pc\r\n\t\t\t\t\t\t\tJOIN `bt_job_card` jc ON jc.id= pc.`job_card_id`\r\n\t\t\t\t\t\t\tJOIN `bt_products` prd ON prd.`id`= pc.`product_id`\r\n\t\t\t\t\t\t\tJOIN employee_details emp ON emp.email_id= pc.owner_email_id\r\n\t\t\t\t\t\t\tJOIN `service_recource` sr ON sr.user_email_id= emp.email_id\r\n\t\t\t\t\t\t\tWHERE pc.consume_from_stock=TRUE AND pc.synced=FALSE");
      for (Map<String, Object> con : cdata) {
        Date bldate = (Date)con.get("createdDate");
        Map<String, Object> wareHouse = getWareHouse(Long.parseLong(con.get("warehouseid").toString()), crm);
        sap.executeUpdate("insert into CRM_BT_STOCK_CONS(CRM_DOCNO,CRM_LITM,BKTXT,BLDAT,BUDAT,USNAM,MATNR,BWARTWA,GRUND,LGORT,ERFMG,SERNR,WERKS,KOSTL,SFLAG,TFLAG,SYDAT,SYTIM,REMARK) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] { 
              con.get("jobCardId"), 
              con.get("consumeId"), 
              (con.get("remarks") == null) ? "" : con.get("remarks"), 
              dformat.format(bldate), 
              dformat.format(bldate), 
              String.valueOf((con.get("firstname") == null) ? "" : con.get("firstname")) + ((con.get("middlename") == null) ? "" : (" " + con.get("middlename"))) + ((con.get("lastname") == null) ? "" : (" " + con.get("lastname"))), 
              con.get("partCode"), 
              "", 
              "", 
              wareHouse.get("location_id"), 
              con.get("qty"), 
              "null", 
              wareHouse.get("plant"), 
              "", 
              "CR", 
              "N", 
              dformat.format(new Date()), 
              tformat.format(new Date()), 
              "" });
        crm.executeUpdate("UPDATE `bt_job_card_product_consume` SET `synced`=? WHERE  id=?", new Object[] { Boolean.valueOf(true), con.get("consumeId") });
      } 
      sap.commit();
      crm.commit();
    } catch (Exception e) {
      sap.rollBack();
      crm.rollBack();
      e.printStackTrace();
    } 
  }
  
  private Map<String, Object> getWareHouse(long locationId, SqlHelper helper) {
    try {
      Map<String, Object> warehouse = helper.getSingleResult("select * from location where location_id=?", new Object[] { Long.valueOf(locationId) });
      if (warehouse.get("location_type_location_type_id").toString().equalsIgnoreCase("1"))
        return warehouse; 
      return getWareHouse(Long.parseLong(warehouse.get("parent_location_location_id").toString()), helper);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }
}
