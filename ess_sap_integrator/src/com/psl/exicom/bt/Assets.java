package com.psl.exicom.bt;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class Assets implements Runnable {
  String user = App.user;
  
  public void run() {
    try {
      synkAssetsCreated();
    } catch (Exception exception) {}
    try {
      synkedAssetsModified();
    } catch (Exception exception) {}
  }
  
  public void synkAssetsCreated() {
    SqlHelper _sap = new SqlHelper("SAP");
    try {
      List<Map<String, Object>> _assets = _sap.getResultSet("select * from CRM_BT_ASSET_DATA where TFLAG='N' and SFLAG='CR' and ISSUE_RETURN='I'");
      for (Map<String, Object> m : _assets) {
        SqlHelper sap = new SqlHelper("SAP");
        SqlHelper crm = new SqlHelper("CRM");
        try {
          sap.beginTransaction();
          crm.beginTransaction();
          String serialNumber = m.get("SERNR").toString();
          Date poDate = null;
          SimpleDateFormat sapDateFormat = new SimpleDateFormat("yyyyMMdd");
          if (m.get("BSTDK") != null)
            poDate = sapDateFormat.parse(m.get("BSTDK").toString()); 
          String _stDateOfDelvery = null;
          if (m.get("LFDAT") != null)
            _stDateOfDelvery = m.get("LFDAT").toString(); 
          Date dateOfDelvery = null;
          if (_stDateOfDelvery != null)
            dateOfDelvery = sapDateFormat.parse(_stDateOfDelvery); 
          Calendar cl = Calendar.getInstance(TimeZone.getTimeZone("IST"));
          cl.setTime(dateOfDelvery);
          cl.add(2, Integer.parseInt((m.get("WARRANTY_D") == null || m.get("WARRANTY_D").equals("") || m.get("WARRANTY_D").equals(" ") || m.get("WARRANTY_D") == "--") ? "0" : (String)m.get("WARRANTY_D")));
          cl.add(11, -24);
          Date dateOfDevleveryExpire = cl.getTime();
          Map<String, Object> product = crm.getSingleResult("select * from bt_products where manual_id=?", new Object[] { m.get("MATNR") });
          if (product == null)
            throw new Exception("can not create Assets " + m.get("SERNR") + " Product partcode " + m.get("MATNR") + " not found"); 
          Map<String, Object> checkExists = crm.getSingleResult("SELECT * FROM bt_assets where manual_id=? and bt_products_id=?", new Object[] { serialNumber, product.get("id") });
          if (checkExists == null) {
            Map<String, Object> account = crm.getSingleResult("select * from accounts where sap_customer_number =?", new Object[] { m.get("KUNNR") });
            if (account == null)
              throw new Exception("Customer Acount not found " + m.get("KUNNR")); 
            Map<String, Object> skunnar = crm.getSingleResult("select * from accounts where sap_customer_number=?", new Object[] { m.get("SKUNNR") });
            if (skunnar == null)
              throw new Exception("Primary Account not found " + m.get("SKUNNR")); 
            AutoKeyNumber number = new AutoKeyNumber();
            crm.executeUpdate("INSERT INTO `bt_assets` (`created_date`,`last_modified_date`,`customer`,`manual_id`,`plant`,`remarks`,`source`,`created_by_email_id`,`last_modified_by_email_id`,`account_sap_customer_number`,`owner_email_id`,`primary_account_sap_customer_number`,`products_id`,`site_details_id`,`status_assets_status_id`) VALUE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", 
                
                new Object[] { 
                  new Date(), 
                  new Date(), 
                  m.get("KUNNR"), 
                  serialNumber, 
                  m.get("WERKS"), 
                  "", 
                  "SAP", 
                  App.user, 
                  App.user, 
                  m.get("KUNNR"), 
                  App.user, 
                  m.get("SKUNNR"), 
                  product.get("id") }, number);
            crm.executeUpdate("insert into global_search_index (key_name,object_details_url,object_id_name,object_id_value,object_name,search_string) values(?,?,?,?,?,?)", new Object[] { "serialNumber", 
                  "bat_asset/" + number.getKey(), 
                  "serialNumber", 
                  serialNumber, 
                  "Assets", 
                  serialNumber });
            sap.executeUpdate("update CRM_ASSET_DATA set TFLAG='Y',CRM_DOCNO=? where SERNR=? and MATNR=?", new Object[] { serialNumber, m.get("SERNR"), m.get("MATNR") });
          } else {
            sap.executeUpdate("update CRM_ASSET_DATA set TFLAG='Y',CRM_DOCNO=? where SERNR=? and MATNR=?", new Object[] { serialNumber, m.get("SERNR"), m.get("MATNR") });
          } 
          sap.commit();
          crm.commit();
        } catch (Exception ex) {
          crm.rollBack();
          sap.rollBack();
          ex.printStackTrace();
        } 
        sap = null;
        crm = null;
      } 
      _assets = null;
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  public void synkedAssetsModified() {
    SqlHelper _sap = new SqlHelper("SAP");
    try {
      List<Map<String, Object>> _assets = _sap.getResultSet("select * from CRM_ASSET_DATA where TFLAG='N' and SFLAG='CH' and ISSUE_RETURN='I'");
      for (Map<String, Object> m : _assets) {
        SqlHelper sap = new SqlHelper("SAP");
        SqlHelper crm = new SqlHelper("CRM");
        try {
          sap.beginTransaction();
          crm.beginTransaction();
          String serialNumber = String.valueOf(m.get("SERNR").toString()) + "-" + m.get("MATNR").toString();
          SimpleDateFormat sapDateFormat = new SimpleDateFormat("yyyyMMdd");
          Date poDate = null;
          if (m.get("BSTDK") != null)
            poDate = sapDateFormat.parse(m.get("BSTDK").toString()); 
          String _stDateOfDelvery = null;
          if (m.get("LFDAT") != null)
            _stDateOfDelvery = m.get("LFDAT").toString(); 
          Date dateOfDelvery = null;
          if (_stDateOfDelvery != null)
            dateOfDelvery = sapDateFormat.parse(_stDateOfDelvery); 
          Calendar cl = Calendar.getInstance(TimeZone.getTimeZone("IST"));
          cl.setTime(dateOfDelvery);
          cl.add(2, Integer.parseInt((m.get("WARRANTY_D") == null || m.get("WARRANTY_D").equals(" ")) ? "0" : (String)m.get("WARRANTY_D")));
          cl.add(11, -24);
          Date dateOfDevleveryExpire = cl.getTime();
          Map<String, Object> checkExists = crm.getSingleResult("SELECT * FROM assets where serial_number=?", new Object[] { serialNumber });
          if (checkExists != null) {
            Map<String, Object> account = crm.getSingleResult("select * from accounts where sap_customer_number =?", new Object[] { m.get("KUNNR") });
            if (account == null)
              throw new Exception("Customer Account " + m.get("KUNNR") + " not found to update Assets "); 
            Map<String, Object> skunnar = crm.getSingleResult("select * from accounts where sap_customer_number=?", new Object[] { m.get("SKUNNR") });
            if (skunnar == null)
              throw new Exception("Primary Account " + m.get("SKUNNR") + " not found to update Assets"); 
            crm.executeUpdate("update assets set sap_delivery_document_number=?,sap_material_document_number=?,synced=?,warranty_from_date_of_delivery_in_month=?,warranty_from_date_of_installation_in_month=?,account_sap_customer_number=?,modifyed_by_email_id=?,plant_plant_code=?,primary_account_sap_customer_number=?,product_product_part_code=?,date_of_delivery_expiry_date=?,modifyed_date=?,`purchase_order_date`=?,`purchase_order_number`=? where serial_number=?", 
                
                new Object[] { 
                  m.get("VBELN"), 
                  m.get("MBLNR"), 
                  Boolean.valueOf(true), 
                  m.get("WARRANTY_D"), 
                  m.get("WARRANTY_I"), 
                  m.get("KUNNR"), 
                  
                  this.user, 
                  m.get("WERKS"), 
                  m.get("SKUNNR"), 
                  m.get("MATNR"), 
                  dateOfDevleveryExpire, 
                  new Date(), 
                  poDate, 
                  m.get("BSTKD"), 
                  serialNumber });
            sap.executeUpdate("update CRM_ASSET_DATA set TFLAG='Y' where CRM_DOCNO=?", new Object[] { serialNumber });
          } 
          sap.commit();
          crm.commit();
        } catch (Exception ex) {
          crm.rollBack();
          sap.rollBack();
          ex.printStackTrace();
        } 
        sap = null;
        crm = null;
      } 
      _assets = null;
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    _sap = null;
  }
}
