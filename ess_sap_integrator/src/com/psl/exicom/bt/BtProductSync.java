package com.psl.exicom.bt;

import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BtProductSync implements Runnable {
  public void run() {
    productCreate();
    productUpdate();
  }
  
  private void productCreate() {
    SqlHelper _sap = new SqlHelper("SAP");
    try {
      List<Map<String, Object>> _productMaster = _sap.getResultSet("select MATNR,P_FAMILY,PS_FAMILY,MAKTX,MTART,MEINS,ISNULL(STATUS,0) AS ACTIVE,ISNULL(CRM_DOCNO,'') AS CRM_DOCNO,SFLAG,TFLAG,SYDAT,SYTIM,cast(VERPR as decimal(10,2)) as VERPR from CRM_BT_PROD_MASTER where TFLAG='N' and SFLAG='CR'");
      if (_productMaster != null && _productMaster.size() > 0)
        for (Map<String, Object> obj : _productMaster) {
          SqlHelper sap = new SqlHelper("SAP");
          SqlHelper crm = new SqlHelper("CRM");
          sap.beginTransaction();
          crm.beginTransaction();
          try {
            Map<String, Object> productfamily = crm.getSingleResult("select * from product_family where product_family_name=?", new Object[] { obj.get("P_FAMILY") });
            if (productfamily == null) {
              crm.executeUpdate("insert into product_family(product_family_name,synced) values (?,?)", new Object[] { obj.get("P_FAMILY"), Boolean.valueOf(false) });
              productfamily = crm.getSingleResult("select * from product_family where product_family_name=?", new Object[] { obj.get("P_FAMILY") });
            } 
            Map<String, Object> subProduct = crm.getSingleResult("select * from product_family_sub_product where product_family_product_family_id=? and product_faily_sub_product_name=?", new Object[] { productfamily.get("product_family_id"), obj.get("PS_FAMILY") });
            if (subProduct == null) {
              crm.executeUpdate("insert into product_family_sub_product(product_faily_sub_product_name,synced,product_family_product_family_id) values(?,?,?)", new Object[] { obj.get("PS_FAMILY"), Boolean.valueOf(true), productfamily.get("product_family_id") });
              subProduct = crm.getSingleResult("select * from product_family_sub_product where product_family_product_family_id=? and product_faily_sub_product_name=?", new Object[] { productfamily.get("product_family_id"), obj.get("PS_FAMILY") });
            } 
            Map<String, Object> productType = crm.getSingleResult("select * from product_type where type_name=?", new Object[] { obj.get("MTART") });
            if (productType == null) {
              crm.executeUpdate("insert into product_type(synced,type_name) values(?,?)", new Object[] { Boolean.valueOf(true), obj.get("MTART") });
              productType = crm.getSingleResult("select * from product_type where type_name=?", new Object[] { obj.get("MTART") });
            } 
            try {
              boolean active = true;
              if (obj.get("STATUS") != null)
                active = obj.get("STATUS").toString().contains("A"); 
              Map<String, Object> isInserted = crm.getSingleResult("select id, manual_id from bt_products where manual_id=?", new Object[] { obj.get("MATNR") });
              AutoKeyNumber key = new AutoKeyNumber();
              if (isInserted == null) {
                crm.executeUpdate("INSERT INTO `bt_products` (\r\n\t`created_date`,\r\n\t`last_modified_date`,\r\n\t`active`,\r\n\t`manual_id`,\r\n\t`product_price`,\r\n\t`product_type`,\r\n\t`synced`,\r\n\t`created_by_email_id`,\r\n\t`last_modified_by_email_id`,\r\n\t`hsn_details_hsn_code`,\r\n\t`owner_email_id`,\r\n\t`sub_product_family_product_family_sub_product_id`,\r\n\t`product_description`\r\n) VALUE(?,?,?,?,?,?,?,?,?,?,?,?,?)", 
                    
                    new Object[] { 
                      new Date(), 
                      new Date(), 
                      Boolean.valueOf(active), 
                      obj.get("MATNR"), 
                      obj.get("VERPR"), 
                      productType.get("type_name"), 
                      Boolean.valueOf(true), 
                      App.user, 
                      App.user, 
                      
                      App.user, 
                      subProduct.get("product_family_sub_product_id"), 
                      obj.get("MAKTX") }, key);
                crm.executeUpdate("insert into global_search_index(key_name,object_details_url,object_id_name,object_id_value,object_name,search_string) values(?,?,?,?,?,?)", new Object[] { "productPartCode", "products/" + URLEncoder.encode(obj.get("MATNR").toString(), "UTF-8"), "productPartCode", obj.get("MATNR"), "Products", obj.get("MATNR") });
                sap.executeUpdate("update CRM_BT_PROD_MASTER set CRM_DOCNO=?,TFLAG=? where MATNR=?", new Object[] { key.getKey(), "Y", obj.get("MATNR") });
              } else {
                crm.executeUpdate("update bt_products set last_modified_date=?,product_description=?,product_price=?,product_type=?,synced=?,sub_product_family_product_family_sub_product_id=?,last_modified_by_email_id=? where id=?", 
                    
                    new Object[] { new Date(), obj.get("MAKTX"), obj.get("VERPR"), productType.get("type_name"), Boolean.valueOf(true), 
                      subProduct.get("product_family_sub_product_id"), App.user, isInserted.get("id") });
                sap.executeUpdate("update CRM_BT_PROD_MASTER set CRM_DOCNO=?,TFLAG=? where MATNR=?", new Object[] { isInserted.get("id"), "Y", obj.get("MATNR") });
              } 
            } catch (Exception ext) {
              throw ext;
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
      _productMaster = null;
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  private void productUpdate() {
    SqlHelper _sap = new SqlHelper("SAP");
    try {
      List<Map<String, Object>> _products = _sap.getResultSet("select * from CRM_BT_PROD_MASTER where TFLAG='N' and SFLAG='CH'");
      if (_products != null && _products.size() > 0) {
        SqlHelper sap = new SqlHelper("SAP");
        SqlHelper crm = new SqlHelper("CRM");
        try {
          sap.beginTransaction();
          crm.beginTransaction();
          for (Map<String, Object> obj : _products) {
            Map<String, Object> productfamily = crm.getSingleResult("select * from product_family where product_family_name=?", new Object[] { obj.get("P_FAMILY") });
            if (productfamily == null) {
              crm.executeUpdate("insert into product_family(product_family_name,synced) values (?,?)", new Object[] { obj.get("P_FAMILY"), Boolean.valueOf(false) });
              productfamily = crm.getSingleResult("select * from product_family where product_family_name=?", new Object[] { obj.get("P_FAMILY") });
            } 
            Map<String, Object> subProduct = crm.getSingleResult("select * from product_family_sub_product where product_family_product_family_id=? and product_faily_sub_product_name=?", new Object[] { productfamily.get("product_family_id"), obj.get("PS_FAMILY") });
            if (subProduct == null) {
              crm.executeUpdate("insert into product_family_sub_product(product_faily_sub_product_name,synced,product_family_product_family_id) values(?,?,?)", new Object[] { obj.get("PS_FAMILY"), Boolean.valueOf(true), productfamily.get("product_family_id") });
              subProduct = crm.getSingleResult("select * from product_family_sub_product where product_family_product_family_id=? and product_faily_sub_product_name=?", new Object[] { productfamily.get("product_family_id"), obj.get("PS_FAMILY") });
            } 
            Map<String, Object> productType = crm.getSingleResult("select * from product_type where type_name=?", new Object[] { obj.get("MTART") });
            if (productType == null) {
              crm.executeUpdate("insert into product_type(synced,type_name) values(?,?)", new Object[] { Boolean.valueOf(true), obj.get("MTART") });
              productType = crm.getSingleResult("select * from product_type where type_name=?", new Object[] { obj.get("MTART") });
            } 
            boolean active = true;
            if (obj.get("STATUS") != null)
              active = obj.get("STATUS").toString().contains("A"); 
            Map<String, Object> product = crm.getSingleResult("SELECT * FROM `bt_products` WHERE `manual_id`=?", new Object[] { obj.get("MATNR") });
            if (product != null) {
              crm.executeUpdate("UPDATE `bt_products` SET `last_modified_date`=?,`active`=?,`product_description`=?,`product_price`=?,`product_type`=?,`synced`=?,`last_modified_by_email_id`=?,`sub_product_family_product_family_sub_product_id`=? WHERE `id`=?", 
                  
                  new Object[] { new Date(), Boolean.valueOf(active), obj.get("MAKTX"), obj.get("VERPR"), productType.get("type_name"), 
                    Boolean.valueOf(true), App.user, subProduct.get("product_family_sub_product_id"), product.get("id") });
              sap.executeUpdate("update CRM_BT_PROD_MASTER set CRM_DOCNO=?,TFLAG=? where MATNR=?", new Object[] { product.get("id"), "Y", obj.get("MATNR") });
            } else {
              AutoKeyNumber key = new AutoKeyNumber();
              crm.executeUpdate("INSERT INTO `bt_products` (\r\n\t`created_date`,\r\n\t`last_modified_date`,\r\n\t`active`,\r\n\t`manual_id`,\r\n\t`product_price`,\r\n\t`product_type`,\r\n\t`synced`,\r\n\t`created_by_email_id`,\r\n\t`last_modified_by_email_id`,\r\n\t`hsn_details_hsn_code`,\r\n\t`owner_email_id`,\r\n\t`sub_product_family_product_family_sub_product_id`,\r\n\t`product_description`\r\n) VALUE(?,?,?,?,?,?,?,?,?,?,?,?,?)", 
                  
                  new Object[] { 
                    new Date(), 
                    new Date(), 
                    Boolean.valueOf(active), 
                    obj.get("MATNR"), 
                    obj.get("VERPR"), 
                    productType.get("type_name"), 
                    Boolean.valueOf(true), 
                    App.user, 
                    App.user, 
                    
                    App.user, 
                    subProduct.get("product_family_sub_product_id"), 
                    obj.get("MAKTX") }, key);
              crm.executeUpdate("insert into global_search_index(key_name,object_details_url,object_id_name,object_id_value,object_name,search_string) values(?,?,?,?,?,?)", new Object[] { "productPartCode", "products/" + URLEncoder.encode(obj.get("MATNR").toString(), "UTF-8"), "productPartCode", obj.get("MATNR"), "Products", obj.get("MATNR") });
              sap.executeUpdate("update CRM_BT_PROD_MASTER set CRM_DOCNO=?,TFLAG=? where MATNR=?", new Object[] { key.getKey(), "Y", obj.get("MATNR") });
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
      _products = null;
    } catch (Exception exception) {}
  }
}
