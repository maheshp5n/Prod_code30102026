package com.psl.exicom.bt;

import java.util.List;
import java.util.Map;

public class BtServiceOrderSynk implements Runnable {
  public void run() {
    synkServiceOrder();
    soReturnSync();
  }
  
  private void synkServiceOrder() {
    try {
      SqlHelper crm = new SqlHelper("CRM");
      List<Map<String, Object>> soInData = crm.getResultSet("SELECT so.manual_id AS CRM_DOCNO,\r\n\titem.id AS CRM_LITM,\r\n\tDATE_FORMAT(so.last_modified_by_email_id, '%Y%m%d')AS RSDAT,\r\n\tCONCAT(IFNULL(emp.first_name,''),' ',IFNULL(emp.middle_name,''),' ',IFNULL(emp.last_name,''))AS WEMPF,\r\n\tso.`source_location_location_id` AS s_plant_id,\r\n\tso.`destination_location_location_id` AS s_plant_id,\r\n\tso.`source_location_location_id` AS SLOC,\r\n\t'NULL' AS KOSTL,\r\n\tprd.manual_id AS MATNR,\r\n\t1 AS ERFMG,\r\n\tDATE_FORMAT(so.`created_date`,'%Y%m%d') AS BDTER,\r\n\t'CR' AS SFLAG,\r\n\t'N' AS TFLAG,\r\n\tDATE_FORMAT(NOW(),'%Y%m%d') AS SYDAT,\r\n\tDATE_FORMAT(NOW(),'%H%i%s') AS SYTIM,\r\n\tso.shipping_mode AS MOT,\r\n\tso.tracking_number AS LorryNo\r\n\t\tFROM `bt_service_order_item` item \r\n\t\tJOIN `bt_service_order` so ON so.id= item.service_order_id\r\n\t\tJOIN bt_products prd ON prd.id= item.product_id\r\n\t\tJOIN employee_details emp ON emp.email_id= so.created_by_email_id\r\n\t\tWHERE item.`synced`=FALSE AND item.customer_inventory=FALSE");
      if (soInData != null && soInData.size() > 0)
        for (Map<String, Object> d : soInData) {
          SqlHelper _crm = new SqlHelper("CRM");
          SqlHelper _sap = new SqlHelper("SAP");
          try {
            _sap.beginTransaction();
            _crm.beginTransaction();
            _sap.executeUpdate("INSERT INTO CRM_BT_STK_REQUEST(CRM_DOCNO,CRM_LITM,RSDAT,WEMPF,WERKS,WERKS_R,SLOC,KOSTL,MATNR,ERFMG,BDTER,SFLAG,TFLAG,SYDAT,SYTIM) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] { 
                  d.get("CRM_DOCNO"), 
                  d.get("CRM_LITM"), 
                  d.get("RSDAT"), 
                  d.get("WEMPF"), 
                  getWarehouse(Long.parseLong(d.get("s_plant_id").toString()), _crm).get("plant"), 
                  getWarehouse(Long.parseLong(d.get("d_plant_id").toString()), _crm).get("plant"), 
                  d.get("SLOC"), 
                  d.get("KOSTL"), 
                  d.get("MATNR"), 
                  d.get("ERFMG"), 
                  d.get("BDTER"), 
                  d.get("SFLAG"), 
                  d.get("TFLAG"), 
                  d.get("SYDAT"), 
                  d.get("SYTIM") });
            Map<String, Object> checkexist_hdr = null;
            try {
              checkexist_hdr = _sap.getSingleResult("select * from CRM_STO_HDR where CRM_DOCNO=?", new Object[] { d.get("CRM_DOCNO") });
            } catch (Exception exception) {}
            if (checkexist_hdr == null)
              _sap.executeUpdate("insert into CRM_BT_STO_HDR(CRM_DOCNO,BUDAT,IWERKS,RWERKS,SFLAG,TFLAG,SYDAT,SYTIM,EBELN,ISLOC,RSLOC,PR_CRM_DOCNO,MOT,LorryNo) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] { 
                    d.get("CRM_DOCNO"), 
                    d.get("SYDAT"), 
                    getWarehouse(Long.parseLong(d.get("s_plant_id").toString()), _crm).get("plant"), 
                    getWarehouse(Long.parseLong(d.get("d_plant_id").toString()), _crm).get("plant"), 
                    d.get("SFLAG"), 
                    d.get("TFLAG"), 
                    d.get("SYDAT"), 
                    d.get("SYTIM"), 
                    
                    d.get("s_plant_id").toString(), 
                    d.get("d_plant_id").toString(), 
                    d.get("CRM_DOCNO"), 
                    d.get("MOT"), 
                    d.get("LorryNo") }); 
            Map<String, Object> checkLineItemExists = null;
            try {
              checkLineItemExists = _sap.getSingleResult("select * from CRM_BT_STO_ITEM where CRM_DOCNO=? and MATNR=?", new Object[] { d.get("CRM_DOCNO"), d.get("MATNR") });
            } catch (Exception exception) {}
            if (checkLineItemExists == null) {
              _sap.executeUpdate("INSERT INTO CRM_BT_STO_ITEM(CRM_DOCNO,CRM_LITM,PR_CRM_DOCNO,PR_CRM_LITM,USNAM,MATNR,ERFMG,SFLAG,TFLAG,SYDAT,SYTIM) VALUES(?,?,?,?,?,?,?,?,?,?,?)", new Object[] { 
                    d.get("CRM_DOCNO"), 
                    d.get("CRM_LITM"), 
                    d.get("CRM_DOCNO"), 
                    d.get("CRM_LITM"), 
                    d.get("WEMPF"), 
                    d.get("MATNR"), 
                    d.get("ERFMG"), 
                    d.get("SFLAG"), 
                    d.get("TFLAG"), 
                    d.get("SYDAT"), 
                    d.get("SYTIM") });
            } else {
              long prevousqty = Long.parseLong(checkLineItemExists.get("ERFMG").toString());
              prevousqty++;
              _sap.executeUpdate("update CRM_BT_STO_ITEM set ERFMG=? where CRM_DOCNO=? and MATNR=?", new Object[] { Long.valueOf(prevousqty), d.get("CRM_DOCNO"), d.get("MATNR") });
            } 
            _crm.executeUpdate("update bt_service_order_item set synced=? where id=?", new Object[] { Boolean.valueOf(true), d.get("CRM_LITM") });
            _sap.commit();
            _crm.commit();
          } catch (Exception e) {
            _crm.rollBack();
            _sap.rollBack();
            e.printStackTrace();
            continue;
          } finally {
            _crm = null;
            _sap = null;
          } 
        }  
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private void soReturnSync() {
    try {
      SqlHelper crm = new SqlHelper("CRM");
      List<Map<String, Object>> soInData = crm.getResultSet("SELECT soritem.id AS item_id,\r\n\tsor.manual_id AS CRM_DOCNO,\r\n\tsoritem.id AS CRM_LITM,\r\n\tDATE_FORMAT(sor.created_date,'%Y%m%d') AS RSDAT,\r\n\tCONCAT(IFNULL(emp.first_name,''),' ',IFNULL(emp.middle_name,''),' ',IFNULL(emp.last_name,'')) AS WEMPF,\r\n\tso.`source_location_location_id` AS d_plant_id,\r\n\tso.`destination_location_location_id` AS s_plant_id ,\r\n\tso.`destination_location_location_id` AS SLOC,\r\n\t'NULL' AS KOSTL,\r\n\tprd.manual_id AS MATNR,\r\n\t1 AS ERFMG,\r\n\tDATE_FORMAT(so.created_date,'%Y%m%d') AS BDTER,\r\n\t'CR' AS SFLAG,\r\n\t'N' AS TFLAG,\r\n\tDATE_FORMAT(NOW(),'%Y%m%d') AS SYSDAT,\r\n\tDATE_FORMAT(NOW(),'%Y%m%d') AS SYSTIM \r\n\tFROM `bt_service_order_item` item\r\n\tJOIN `bt_service_order` so ON so.id= item.service_order_id\r\n\tJOIN  `bt_service_order_item_return` soritem ON soritem.id=item.id\r\n\tJOIN  `bt_service_order_return` sor ON sor.id= soritem.so_return_id\r\n\tJOIN bt_products prd ON prd.id = item.`product_id`\r\n\tJOIN employee_details emp ON emp.email_id=sor.`created_by_email_id`\r\n\tWHERE soritem.synced= FALSE AND item.customer_inventory= FALSE\t");
      if (soInData != null && soInData.size() > 0)
        for (Map<String, Object> d : soInData) {
          SqlHelper _crm = new SqlHelper("CRM");
          SqlHelper _sap = new SqlHelper("SAP");
          try {
            _sap.beginTransaction();
            _crm.beginTransaction();
            _sap.executeUpdate("INSERT INTO CRM_BT_STK_REQUEST(CRM_DOCNO,CRM_LITM,RSDAT,WEMPF,WERKS,WERKS_R,SLOC,KOSTL,MATNR,ERFMG,BDTER,SFLAG,TFLAG,SYDAT,SYTIM) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] { 
                  d.get("CRM_DOCNO"), 
                  d.get("CRM_LITM"), 
                  d.get("RSDAT"), 
                  d.get("WEMPF"), 
                  getWarehouse(Long.parseLong(d.get("s_plant_id").toString()), _crm).get("plant"), 
                  getWarehouse(Long.parseLong(d.get("d_plant_id").toString()), _crm).get("plant"), 
                  d.get("SLOC"), 
                  d.get("KOSTL"), 
                  d.get("MATNR"), 
                  d.get("ERFMG"), 
                  d.get("BDTER"), 
                  d.get("SFLAG"), 
                  d.get("TFLAG"), 
                  d.get("SYDAT"), 
                  d.get("SYTIM") });
            Map<String, Object> checkexist_hdr = null;
            try {
              checkexist_hdr = _sap.getSingleResult("select * from CRM_BT_STO_HDR where CRM_DOCNO=?", new Object[] { d.get("CRM_DOCNO") });
            } catch (Exception exception) {}
            if (checkexist_hdr == null)
              _sap.executeUpdate("insert into CRM_BT_STO_HDR(CRM_DOCNO,BUDAT,IWERKS,RWERKS,SFLAG,TFLAG,SYDAT,SYTIM,EBELN,ISLOC,RSLOC,PR_CRM_DOCNO) values(?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] { 
                    d.get("CRM_DOCNO"), 
                    d.get("SYDAT"), 
                    getWarehouse(Long.parseLong(d.get("s_plant_id").toString()), _crm).get("plant"), 
                    getWarehouse(Long.parseLong(d.get("d_plant_id").toString()), _crm).get("plant"), 
                    d.get("SFLAG"), 
                    d.get("TFLAG"), 
                    d.get("SYDAT"), 
                    d.get("SYTIM"), 
                    
                    d.get("s_plant_id").toString(), 
                    d.get("d_plant_id").toString(), 
                    d.get("CRM_DOCNO") }); 
            Map<String, Object> checkLineItemExists = null;
            try {
              checkLineItemExists = _sap.getSingleResult("select * from CRM_BT_STO_ITEM where CRM_DOCNO=? and MATNR=?", new Object[] { d.get("CRM_DOCNO"), d.get("MATNR") });
            } catch (Exception exception) {}
            if (checkLineItemExists == null) {
              _sap.executeUpdate("INSERT INTO CRM_BT_STO_ITEM(CRM_DOCNO,CRM_LITM,PR_CRM_DOCNO,PR_CRM_LITM,USNAM,MATNR,ERFMG,SFLAG,TFLAG,SYDAT,SYTIM) VALUES(?,?,?,?,?,?,?,?,?,?,?)", new Object[] { 
                    d.get("CRM_DOCNO"), 
                    d.get("CRM_LITM"), 
                    d.get("CRM_DOCNO"), 
                    d.get("CRM_LITM"), 
                    d.get("WEMPF"), 
                    d.get("MATNR"), 
                    d.get("ERFMG"), 
                    d.get("SFLAG"), 
                    d.get("TFLAG"), 
                    d.get("SYDAT"), 
                    d.get("SYTIM") });
            } else {
              long prevousqty = Long.parseLong(checkLineItemExists.get("ERFMG").toString());
              prevousqty++;
              _sap.executeUpdate("update CRM_BT_STO_ITEM set ERFMG=? where CRM_DOCNO=? and MATNR=?", new Object[] { Long.valueOf(prevousqty), d.get("CRM_DOCNO"), d.get("MATNR") });
            } 
            _crm.executeUpdate("update bt_service_order_item_return set synced=? where id=?", new Object[] { Boolean.valueOf(true), d.get("CRM_LITM") });
            _sap.commit();
            _crm.commit();
          } catch (Exception ex) {
            _crm.rollBack();
            _sap.rollBack();
            ex.printStackTrace();
            continue;
          } finally {
            _crm = null;
            _sap = null;
          } 
        }  
    } catch (Exception exception) {}
  }
  
  private Map<String, Object> getWarehouse(long locationId, SqlHelper helper) {
    try {
      Map<String, Object> warehouse = helper.getSingleResult("select * from location where location_id=?", new Object[] { Long.valueOf(locationId) });
      if (warehouse.get("location_type_location_type_id").toString().equalsIgnoreCase("1"))
        return warehouse; 
      return getWarehouse(Long.parseLong(warehouse.get("parent_location_location_id").toString()), helper);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }
}
