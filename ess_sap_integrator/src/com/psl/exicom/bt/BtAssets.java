package com.psl.exicom.bt;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class BtAssets implements Runnable {
  String user = App.user;
  
  public void run() {
    synkBtAssetsCreated();
    synkBtAssetsModified();
  }
  
  public void synkBtAssetsCreated() {
    SqlHelper _sap = new SqlHelper("SAP");
    try {
      List<Map<String, Object>> _assets = _sap.getResultSet(
          "select * from CRM_BT_ASSET_DATA where TFLAG='N' and SFLAG='CR' and ISSUE_RETURN='I'");
      if (_assets != null && _assets.size() > 0)
        for (Map<String, Object> m : _assets) {
          SqlHelper sap = new SqlHelper("SAP");
          SqlHelper crm = new SqlHelper("CRM");
          try {
            sap.beginTransaction();
            crm.beginTransaction();
            String serialNumber = m.get("SERNR").toString();
            Date poDate = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            if (m.get("BSTDK") != null && !m.get("BSTDK").toString().equalsIgnoreCase(""))
              poDate = sdf.parse(m.get("BSTDK").toString()); 
            String _stDateOfDelivery = null;
            if (m.get("LFDAT") != null)
              _stDateOfDelivery = m.get("LFDAT").toString(); 
            Date dateOfDelivery = null;
            if (_stDateOfDelivery != null)
              dateOfDelivery = sdf.parse(_stDateOfDelivery); 
            Calendar cl = Calendar.getInstance(TimeZone.getTimeZone("IST"));
            cl.setTime(dateOfDelivery);
            System.out.println((new StringBuilder()).append(m.get("WARRANTY_D")).append("Warranty date").toString());
            cl.add(2, Integer.parseInt((m.get("WARRANTY_D") == null || m.get("WARRANTY_D").equals("") || m.get("WARRANTY_D").equals(" ") || m.get("WARRANTY_D") == "--") ? "0" : (String)m.get("WARRANTY_D")));
            cl.add(11, -24);
            Date dateOfDeliveryExpiry = cl.getTime();
            Map<String, Object> product = crm.getSingleResult("SELECT * FROM bt_products WHERE manual_id=?", 
                new Object[] { m.get("MATNR") });
            if (product == null)
              throw new Exception("Can not create Asset " + m.get("SERNR") + " product part code " + 
                  m.get("MATNR") + " not found"); 
            Map<String, Object> checkExist = crm.getSingleResult(
                "SELECT * FROM bt_assets WHERE manual_id=? AND products_id=?", 
                new Object[] { m.get("SERNR"), product.get("id") });
            if (checkExist == null) {
              Map<String, Object> account = crm.getSingleResult(
                  "SELECT * FROM accounts WHERE sap_customer_number=?", 
                  new Object[] { m.get("KUNNR") });
              if (account == null)
                throw new Exception("Customer account not found " + m.get("KUNNR")); 
              if (Integer.parseInt(account.get("catagory_id").toString()) == 2)
                crm.executeUpdate("UPDATE `accounts` SET `catagory_id`=3 where sap_customer_number=? ", new Object[] { m.get("KUNNR") }); 
              if (m.get("SKUNNR") != null) {
                Map<String, Object> parentAccount = crm.getSingleResult(
                    "SELECT * FROM accounts WHERE sap_customer_number=?", 
                    new Object[] { m.get("SKUNNR") });
                if (parentAccount == null)
                  throw new Exception("Primary Account not found " + m.get("SKUNNR")); 
              } 
              AutoKeyNumber number = new AutoKeyNumber();
              crm.executeUpdate("INSERT INTO bt_assets (`created_date`,\r\n`last_modified_date`,\r\n`customer`,\r\n`manual_id`,\r\n`plant`,\r\n`remarks`,\r\n`source`,\r\n`created_by_email_id`,\r\n`last_modified_by_email_id`,\r\n`account_sap_customer_number`,\r\n`owner_email_id`,\r\n`primary_account_sap_customer_number`,\r\n`products_id`,\r\n`site_details_id`,\r\n`status_assets_status_id`)\r\nVALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", 
                  
                  new Object[] { 
                    new Date(), new Date(), m.get("KUNNR"), m.get("SERNR"), 
                    m.get("WERKS"), m.get("REMARK"), "SAP", this.user, this.user, m.get("KUNNR"), 
                    this.user, m.get("SKUNNR"), product.get("id") }, number);
              crm.executeUpdate(
                  "INSERT INTO `bt_assets_purchase_details` (id,`created_date`,`last_modified_date`,`purchase_order_date`,`purchase_order_number`,`sales_doc_number`,`sap_delivery_document_number`,`sap_material_document_number`,`created_by_email_id`,`last_modified_by_email_id`,`owner_email_id`)VALUES (?,?,?,?,?,?,?,?,?,?,?)", 
                  
                  new Object[] { number.getKey(), new Date(), new Date(), poDate, m.get("BSTKD"), m.get("VBELN"), 
                    m.get("MBLNR"), this.user, this.user, this.user });
              crm.executeUpdate(
                  "INSERT INTO `bt_assets_warranty_details` (id,`created_date`,`last_modified_date`,`date_of_delivery_expiry_date`,`install_date`,`install_expiry_date`,`purchase_date`,`warranty_expiry_date`,`warranty_from_date_of_delivery_in_month`,`warranty_from_date_of_installation_in_month`,`created_by_email_id`,`last_modified_by_email_id`,`owner_email_id`)VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)", 
                  
                  new Object[] { 
                    number.getKey(), new Date(), new Date(), dateOfDeliveryExpiry, dateOfDelivery, dateOfDeliveryExpiry, 
                    m.get("WARRANTY_D"), m.get("WARRANTY_I"), this.user, this.user, 
                    this.user });
              crm.executeUpdate("INSERT INTO `bt_assets_amcdetails` (`id`, `created_date`, `last_modified_date`, `amc_expiry_date`, `amc_period`, `amc_purchase_order_date`, `amc_purchase_order_number`, `amc_start_date`,`created_by_email_id`, `last_modified_by_email_id`, `owner_email_id`) VALUES (?,?,?,?,?,?,?,?,?,?,?)", 
                  
                  new Object[] { number.getKey(), new Date(), new Date(), Integer.valueOf(0), this.user, this.user, this.user });
              crm.executeUpdate(
                  "INSERT INTO `global_search_index` (`key_name`,`object_details_url`,`object_id_name`,`object_id_value`,`object_name`,`search_string`)\r\nVALUES (?,?,?,?,?,?)", 
                  
                  new Object[] { "manualId", "bat_asset/" + number.getKey(), "id", number.getKey(), 
                    "BtAssets", serialNumber });
              sap.executeUpdate(
                  "update CRM_BT_ASSET_DATA set TFLAG='Y',CRM_DOCNO=? where SERNR=? and MATNR=?", 
                  new Object[] { number.getKey(), m.get("SERNR"), m.get("MATNR") });
            } else {
              sap.executeUpdate(
                  "update CRM_BT_ASSET_DATA set TFLAG='Y',CRM_DOCNO=? where SERNR=? and MATNR=?", 
                  new Object[] { checkExist.get("id"), m.get("SERNR"), m.get("MATNR") });
            } 
            sap.commit();
            crm.commit();
          } catch (Exception e) {
            crm.rollBack();
            sap.rollBack();
            e.printStackTrace();
          } 
          sap = null;
          crm = null;
        }  
      _assets = null;
    } catch (Exception e) {
      e.printStackTrace();
    } 
    _sap = null;
  }
  
  private void synkBtAssetsModified() {
    SqlHelper _sap = new SqlHelper("SAP");
    try {
      List<Map<String, Object>> _assets = _sap.getResultSet("  select * from CRM_BT_ASSET_DATA where TFLAG='N' and SFLAG='CH' and ISSUE_RETURN='I'");
      for (Map<String, Object> m : _assets) {
        SqlHelper sap = new SqlHelper("SAP");
        SqlHelper crm = new SqlHelper("CRM");
        try {
          sap.beginTransaction();
          crm.beginTransaction();
          Date poDate = null;
          SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
          if (m.get("BSTDK") != null && !m.get("BSTDK").toString().equalsIgnoreCase(""))
            poDate = sdf.parse(m.get("BSTDK").toString()); 
          String _stDateOfDelivery = null;
          if (m.get("LFDAT") != null)
            _stDateOfDelivery = m.get("LFDAT").toString(); 
          Date dateOfDelivery = null;
          if (_stDateOfDelivery != null)
            dateOfDelivery = sdf.parse(_stDateOfDelivery); 
          Calendar cl = Calendar.getInstance(TimeZone.getTimeZone("IST"));
          cl.setTime(dateOfDelivery);
          cl.add(2, 
              Integer.parseInt((m.get("WARRANTY_D").equals("") || m.get("WARRANTY_D") == null || 
                m.get("WARRANTY_D").equals("  ") || m.get("WARRANTY_D") == "--") ? "0" : 
                (String)m.get("WARRANTY_D")));
          cl.add(11, -24);
          Date dateOfDeliveryExpiry = cl.getTime();
          Map<String, Object> product = crm.getSingleResult("SELECT * FROM bt_products WHERE manual_id=?", 
              new Object[] { m.get("MATNR") });
          if (product == null)
            throw new Exception("Can not create Asset " + m.get("SERNR") + " product part code " + 
                m.get("MATNR") + " not found"); 
          Map<String, Object> checkExists = crm.getSingleResult("SELECT * FROM `bt_assets` WHERE `manual_id`=? AND products_id=?", 
              new Object[] { m.get("SERNR"), product.get("id") });
          if (checkExists != null) {
            Map<String, Object> account = crm.getSingleResult("select * from accounts where sap_customer_number =?", new Object[] { m.get("KUNNR") });
            if (account == null)
              throw new Exception("Customer Account " + m.get("KUNNR") + " not found to update Assets "); 
            if (Integer.parseInt(account.get("catagory_id").toString()) == 2)
              crm.executeUpdate("UPDATE `accounts` SET `catagory_id`=3 where sap_customer_number=? ", new Object[] { m.get("KUNNR") }); 
            Map<String, Object> skunnar = crm.getSingleResult("select * from accounts where sap_customer_number=?", new Object[] { m.get("SKUNNR") });
            if (skunnar == null)
              throw new Exception("Primary Account " + m.get("SKUNNR") + " not found to update Assets"); 
            crm.executeUpdate("UPDATE `bt_assets` SET `last_modified_date`=?,`plant`=?,`last_modified_by_email_id`=?,`account_sap_customer_number`=?,`primary_account_sap_customer_number`=?where id=?", 
                
                new Object[] { new Date(), m.get("WERKS"), this.user, m.get("KUNNR"), m.get("SKUNNR"), checkExists.get("id") });
            crm.executeUpdate("UPDATE `bt_assets_warranty_details` SET `last_modified_date`=?,`date_of_delivery_expiry_date`=?,`install_date`=?,`install_expiry_date`=?,`purchase_date`=?,`warranty_expiry_date`=?,`warranty_from_date_of_delivery_in_month`=?,`warranty_from_date_of_installation_in_month`=?,`last_modified_by_email_id`=? WHERE id=?", 
                
                new Object[] { new Date(), dateOfDeliveryExpiry, dateOfDelivery, dateOfDeliveryExpiry, m.get("WARRANTY_D"), m.get("WARRANTY_I"), this.user, checkExists.get("id") });
            crm.executeUpdate("UPDATE `bt_assets_purchase_details` SET `last_modified_date`=?,`purchase_order_date`=?,`purchase_order_number`=?,`sales_doc_number`=?,`sap_delivery_document_number`=?,`sap_material_document_number`=?,`last_modified_by_email_id`=? WHERE id=?", 
                
                new Object[] { new Date(), poDate, m.get("BSTDK"), m.get("VBLEN"), 
                  m.get("MBLNR"), this.user, checkExists.get("id") });
            sap.executeUpdate("update CRM_BT_ASSET_DATA set TFLAG='Y' where CRM_DOCNO=?", new Object[] { checkExists.get("id") });
          } 
          sap.commit();
          crm.commit();
        } catch (Exception e) {
          e.printStackTrace();
          sap.rollBack();
          crm.rollBack();
        } 
        sap = null;
        crm = null;
      } 
      _assets = null;
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
