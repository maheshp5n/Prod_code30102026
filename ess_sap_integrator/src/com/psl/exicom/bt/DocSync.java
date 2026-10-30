package com.psl.exicom.bt;

import java.util.List;
import java.util.Map;

public class DocSync implements Runnable {
  public void run() {
    try {
      productTransferCsToCs();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    try {
      returnOrderInvoiceScynk();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  private void productTransferCsToCs() {
    SqlHelper _crm = new SqlHelper("CRM");
    try {
      List<Map<String, Object>> lst = _crm.getResultSet("SELECT  pt.`id` AS transferId , pt.`manual_id` AS transferManualId FROM `bt_product_transfer` pt WHERE\r\npt.invoice IS NULL OR pt.invoice=''");
      for (Map<String, Object> pt : lst) {
        SqlHelper sap = new SqlHelper("SAP");
        SqlHelper crm = new SqlHelper("CRM");
        try {
          crm.beginTransaction();
          sap.beginTransaction();
          Map<String, Object> sapdata = sap.getSingleResult("select * from CRM_BT_STO_HDR where CRM_DOCNO=?", new Object[] { pt.get("transferManualId") });
          if (sapdata != null)
            crm.executeUpdate("UPDATE `bt_product_transfer` SET deliv=?,invoice=?,`mblnr_g`=?,`mjahr_g`=?,`remark_d`=?,`remark_g`=?,`remark_i`=? WHERE `id`=?", new Object[] { sapdata.get("DELIV"), 
                  sapdata.get("INVOICE"), 
                  sapdata.get("MBLNR_G"), 
                  sapdata.get("MJAHR_G"), 
                  sapdata.get("REMARK_D"), 
                  sapdata.get("REMARK_G"), 
                  sapdata.get("REMARK_I"), 
                  pt.get("transferId") }); 
          crm.commit();
          sap.commit();
        } catch (Exception ex) {
          ex.printStackTrace();
          sap.rollBack();
          crm.rollBack();
        } 
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  private void returnOrderInvoiceScynk() {
    SqlHelper _crm = new SqlHelper("CRM");
    try {
      List<Map<String, Object>> lst = _crm.getResultSet(" SELECT * FROM `bt_product_return_order` WHERE invoice IS NULL OR invoice=''");
      for (Map<String, Object> pt : lst) {
        SqlHelper sap = new SqlHelper("SAP");
        SqlHelper crm = new SqlHelper("CRM");
        try {
          sap.beginTransaction();
          crm.beginTransaction();
          Map<String, Object> sapdata = sap.getSingleResult("select * from CRM_BT_STO_HDR where CRM_DOCNO=? and PR_CRM_DOCNO=?", new Object[] { pt.get("manual_id"), pt.get("manual_id") });
          if (sapdata != null)
            crm.executeUpdate("UPDATE bt_product_return_order SET deliv=?,invoice=?,`mblnr_g`=?,`mjahr_g`=?,`remark_d`=?,`remark_g`=?,`remark_i`=? WHERE `id`=?", new Object[] { sapdata.get("DELIV"), 
                  sapdata.get("INVOICE"), 
                  sapdata.get("MBLNR_G"), 
                  sapdata.get("MJAHR_G"), 
                  sapdata.get("REMARK_D"), 
                  sapdata.get("REMARK_G"), 
                  sapdata.get("REMARK_I"), 
                  sapdata.get("id") }); 
          crm.commit();
          sap.commit();
        } catch (Exception ex) {
          ex.printStackTrace();
          sap.rollBack();
          crm.rollBack();
        } 
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
}
