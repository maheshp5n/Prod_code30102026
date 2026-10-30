package com.psl.inegrator;

import java.util.List;
import java.util.Map;

public class TransferCompleateCheck {

     void checkForPT(){
        try {
            Thread.sleep(6*1000*60);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SqlHelper sapHelper=new SqlHelper("SAP");
        try {
            List<Map<String,Object>> lstforpt=sapHelper.getResultSet("SELECT DISTINCT CRM_DOCNO FROM CRM_STO_HDR WHERE CRM_DOCNO LIKE 'PT-%' AND LFLAG='IN'");
            for(Map<String,Object> ls:lstforpt){
                SqlHelper crm=new SqlHelper("CRM");
                SqlHelper sap=new SqlHelper("SAP");
                try {
                    sap.beginTransaction();
                    crm.beginTransaction();
                    Map<String,Object> crmTotalQuantity=crm.getSingleResult("SELECT SUM(item.`transfer_quantity`) AS total_transfer_qty FROM `lptransfer_item` item JOIN `lptrasfer` trn ON item.`lp_tranfer_location_product_transfer_id`=trn.`location_product_transfer_id` WHERE trn.`location_product_transfer_manual_id`=?",new Object[]{ls.get("CRM_DOCNO")});
                    Map<String,Object> sapTotalQuantity=sap.getSingleResult("SELECT SUM(CAST (ERFMG AS DECIMAL(16,3))) AS total_transfer_sap_qty   FROM [CRM_STO_ITEM] WHERE CRM_DOCNO=?",new Object[]{ls.get("CRM_DOCNO")});
                    
                    float crmTQty= Float.parseFloat(crmTotalQuantity.get("total_transfer_qty").toString());
                    float sapTQt=Float.parseFloat(sapTotalQuantity.get("total_transfer_sap_qty").toString());
                    
                    if(crmTQty==sapTQt){
                        sap.executeUpdate("UPDATE CRM_STO_HDR SET LFLAG='CO' WHERE CRM_DOCNO=?", new Object[]{ls.get("CRM_DOCNO")});
                    }
                    sap.commit();
                    crm.commit();
                } catch (Exception e) {
                    System.out.println("error on checkForPT()");
                    e.printStackTrace();
                    crm.rollBack();
                    sap.rollBack();
                }
            }
        } catch (Exception e) {
           e.printStackTrace();

        }
    }
     void checkForRo(){
         try {
             Thread.sleep(6*1000*60);
         } catch (Exception e) {
             e.printStackTrace();
         }
        SqlHelper sapHelper=new SqlHelper("SAP");
        try {
            List<Map<String,Object>> lstforpt=sapHelper.getResultSet("SELECT DISTINCT CRM_DOCNO FROM CRM_STO_HDR WHERE CRM_DOCNO LIKE 'RO-%' AND LFLAG='IN'");
            for(Map<String,Object> ls:lstforpt){
                SqlHelper crm=new SqlHelper("CRM");
                SqlHelper sap=new SqlHelper("SAP");
                try {
                    sap.beginTransaction();
                    crm.beginTransaction();
                    Map<String,Object> crmTotalQuantity=crm.getSingleResult("SELECT COUNT(*) AS total_transfer_qty FROM `lproduct_return_order_line_item` litem "
                    +" JOIN `lproduct_return_order_item` item ON item.`lpro_itemid`=litem.`return_order_item_lpro_itemid` "
                    +" JOIN `lproduct_return_order` ro ON ro.`lpr_orderid`=item.`location_product_return_order_lpr_orderid` "
                    +" WHERE ro.`lpr_order_manual_id`=?",new Object[]{ls.get("CRM_DOCNO")});
                    Map<String,Object> sapTotalQuantity=sap.getSingleResult("SELECT SUM(CAST (ERFMG AS DECIMAL(16,3))) AS total_transfer_sap_qty   FROM [CRM_STO_ITEM] WHERE CRM_DOCNO=?",new Object[]{ls.get("CRM_DOCNO")});
                    // float crmTQty=(float)crmTotalQuantity.get("total_transfer_qty");
                    // float sapTQt=(float)sapTotalQuantity.get("total_transfer_sap_qty");
                    float crmTQty= Float.parseFloat(crmTotalQuantity.get("total_transfer_qty").toString());
                    float sapTQt=Float.parseFloat(sapTotalQuantity.get("total_transfer_sap_qty").toString());

                    if(crmTQty==sapTQt){
                        sap.executeUpdate("UPDATE CRM_STO_HDR SET LFLAG='CO' WHERE CRM_DOCNO=?", new Object[]{ls.get("CRM_DOCNO")});
                    }
                    sap.commit();
                    crm.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                    crm.rollBack();
                    sap.rollBack();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }
     void checkForSo(){
        try {
            Thread.sleep(6*1000*60);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SqlHelper sapHelper=new SqlHelper("SAP");
        try {
            List<Map<String,Object>> lstforpt=sapHelper.getResultSet("SELECT DISTINCT CRM_DOCNO FROM CRM_STO_HDR WHERE CRM_DOCNO LIKE 'SO-%' AND LFLAG='IN'");
            for(Map<String,Object> ls:lstforpt){
                SqlHelper crm=new SqlHelper("CRM");
                SqlHelper sap=new SqlHelper("SAP");
                try {
                    sap.beginTransaction();
                    crm.beginTransaction();
                    Map<String,Object> crmTotalQuantity=crm.getSingleResult("select count(*) as total_transfer_qty from service_order_item item join service_order ord on ord.id=item.service_order_id where ord.manual_id= ? and item.customer_inventory = false",new Object[]{ls.get("CRM_DOCNO")});
                    Map<String,Object> sapTotalQuantity=sap.getSingleResult("SELECT SUM(CAST (ERFMG AS DECIMAL(16,3))) AS total_transfer_sap_qty  FROM [CRM_STO_ITEM] WHERE CRM_DOCNO=?",new Object[]{ls.get("CRM_DOCNO")});
                    // float crmTQty=(float)crmTotalQuantity.get("total_transfer_qty");
                    // float sapTQt=(float)sapTotalQuantity.get("total_transfer_sap_qty");

                    float crmTQty= Float.parseFloat(crmTotalQuantity.get("total_transfer_qty").toString());
                    float sapTQt=Float.parseFloat(sapTotalQuantity.get("total_transfer_sap_qty").toString());

                    if(crmTQty==sapTQt){
                        sap.executeUpdate("UPDATE CRM_STO_HDR SET LFLAG='CO' WHERE CRM_DOCNO=?", new Object[]{ls.get("CRM_DOCNO")});
                    }
                    sap.commit();
                    crm.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                    crm.rollBack();
                    sap.rollBack();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }
     void checkForSoR(){
        try {
            Thread.sleep(6*1000*60);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SqlHelper sapHelper=new SqlHelper("SAP");
        try {
            List<Map<String,Object>> lstforpt=sapHelper.getResultSet("SELECT DISTINCT CRM_DOCNO FROM CRM_STO_HDR WHERE CRM_DOCNO LIKE 'SOR-%' AND LFLAG='IN'");
            for(Map<String,Object> ls:lstforpt){
                SqlHelper crm=new SqlHelper("CRM");
                SqlHelper sap=new SqlHelper("SAP");
                try {
                    sap.beginTransaction();
                    crm.beginTransaction();
                    Map<String,Object> crmTotalQuantity=crm.getSingleResult("SELECT count(*) as total_transfer_qty FROM service_order_item_return rtnitem join service_order_return rtn on rtn.id=rtnitem.service_order_return_id where rtn.manual_id=? and rtnitem.customer_inventory=false",new Object[]{ls.get("CRM_DOCNO")});
                    Map<String,Object> sapTotalQuantity=sap.getSingleResult("SELECT SUM(CAST (ERFMG AS DECIMAL(16,3))) AS total_transfer_sap_qty  FROM [CRM_STO_ITEM] WHERE CRM_DOCNO=?",new Object[]{ls.get("CRM_DOCNO")});
                    // float crmTQty=(float)crmTotalQuantity.get("total_transfer_qty");
                    // float sapTQt=(float)sapTotalQuantity.get("total_transfer_sap_qty");

                    float crmTQty= Float.parseFloat(crmTotalQuantity.get("total_transfer_qty").toString());
                    float sapTQt=Float.parseFloat(sapTotalQuantity.get("total_transfer_sap_qty").toString());

                    
                    if(crmTQty==sapTQt){
                        sap.executeUpdate("UPDATE CRM_STO_HDR SET LFLAG='CO' WHERE CRM_DOCNO=?", new Object[]{ls.get("CRM_DOCNO")});
                    }
                    sap.commit();
                    crm.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                    crm.rollBack();
                    sap.rollBack();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }
    
}
