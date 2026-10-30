package com.psl.exicom.bt;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BtProductTransfer implements Runnable {
  public void run() {
    SynkBtProductTransfer();
    synkReturnOrder();
  }
  
  public void SynkBtProductTransfer() {
    System.out.println("SynkBtProductTransfer start....");
    SimpleDateFormat dformat = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat tformat = new SimpleDateFormat("HHmmss");
    SqlHelper _crm = new SqlHelper("CRM");
    SqlHelper sap = new SqlHelper("SAP");
    SqlHelper crm = new SqlHelper("CRM");
    try {
      crm.beginTransaction();
      sap.beginTransaction();
      List<Map<String, Object>> transferItems = _crm.getResultSet("SELECT \r\n\tpt.manual_id AS ptmanualid,\r\n\tpr.`destination_location_location_id` AS destinationLocationId,\r\n\tpr.`source_location_location_id` AS sourceLocationId,\r\n pr.manual_id AS requestManualId,\r\n\tdloc.location_name AS dlocname,\r\n\tsloc.location_name AS slocname,\r\n\tdloc.plant AS dplant,\r\n\tsloc.plant AS splant,\r\n\tpti.`created_date` AS itemCreatedDate,\r\n\tpti.`btp_transfer_id` AS transferId,\r\n\tpti.id AS transferItemId,\r\n\tprd.manual_id AS partCode,\r\n\tpti.transfer_quantity AS transferQty,\r\n\temp.first_name,\r\n\temp.middle_name,\r\n\temp.last_name,\r\n\tpt.`shipping_mode` AS MOT,\r\n\tpt.`tracking_number` AS LorryNo\r\n\tFROM `bt_product_transfer_item` pti\r\n\tJOIN `bt_product_transfer` pt ON pt.`id`= pti.`btp_transfer_id`\r\n\tJOIN `bt_product_request_item` pri ON pri.id= pti.`btp_request_item_id`\r\n\tJOIN `bt_product_request` pr ON pr.`id`= pri.bt_product_request_id\r\n\tJOIN  bt_products prd ON prd.id = pri.bt_product_id\r\n\tJOIN location dloc ON dloc.`location_id`=pr.`destination_location_location_id`\r\n\tJOIN location sloc ON sloc.`location_id`=pr.`source_location_location_id`\r\n\tJOIN employee_details emp ON emp.`email_id`=pt.`owner_email_id`\r\n\tWHERE pti.`synced`=FALSE AND dloc.`location_type_location_type_id` IN (1,4) \r\n\tAND sloc.`location_type_location_type_id` IN (1,4)\t");
      for (Map<String, Object> re : transferItems) {
        Map<String, Object> checkRequestExists = null;
        try {
          checkRequestExists = sap.getSingleResult("select * from CRM_BT_STK_REQUEST where CRM_DOCNO=? and MATNR=?", new Object[] { re.get("requestManualId"), re.get("partCode") });
        } catch (Exception e) {
          continue;
        } 
        if (checkRequestExists == null)
          continue; 
        Map<String, Object> checkexist_hdr = null;
        try {
          checkexist_hdr = sap.getSingleResult("select * from CRM_BT_STO_HDR where CRM_DOCNO=?", new Object[] { re.get("ptmanualid") });
        } catch (Exception exception) {}
        if (checkexist_hdr == null) {
          String motNumber = (re.get("MOT") == null) ? "" : re.get("MOT").toString();
          if (motNumber.length() > 30)
            motNumber = motNumber.substring(0, 29); 
          String LorryNum = (re.get("LorryNo") == null) ? "" : re.get("LorryNo").toString();
          if (LorryNum.length() > 20)
            LorryNum = LorryNum.substring(0, 19); 
          sap.executeUpdate("insert into CRM_BT_STO_HDR( CRM_DOCNO, BUDAT, IWERKS, RWERKS, SFLAG, TFLAG, SYDAT, SYTIM, EBELN, ISLOC, RSLOC, PR_CRM_DOCNO, MOT, LorryNo) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] { 
                re.get("ptmanualid"), 
                dformat.format(new Date()), 
                re.get("splant"), 
                re.get("dplant"), 
                "CR", 
                "N", 
                dformat.format(new Date()), 
                tformat.format(new Date()), 
                checkRequestExists.get("EBELN"), 
                re.get("sourceLocationId"), 
                re.get("destinationLocationId"), 
                re.get("requestManualId"), 
                motNumber, 
                LorryNum });
        } 
        String fullName = null;
        String firstName = (re.get("first_name") == null) ? "" : re.get("first_name").toString();
        String middleName = (re.get("middle_name") == null) ? "" : re.get("middle_name").toString();
        String lastName = (re.get("last_name") == null) ? "" : re.get("last_name").toString();
        fullName = String.valueOf(firstName) + middleName + lastName;
        sap.executeUpdate("insert into CRM_BT_STO_ITEM(CRM_DOCNO,CRM_LITM,USNAM,MATNR,ERFMG,SFLAG,TFLAG,SYDAT,SYTIM,PR_CRM_DOCNO,PR_CRM_LITM) values(?,?,?,?,?,?,?,?,?,?,?)", new Object[] { 
              re.get("ptmanualid"), 
              re.get("transferItemId"), 
              fullName, 
              re.get("partCode"), 
              re.get("transferQty"), 
              "CR", 
              "N", 
              dformat.format(new Date()), 
              tformat.format(new Date()), 
              checkRequestExists.get("CRM_DOCNO"), 
              checkRequestExists.get("CRM_LITM") });
        crm.executeUpdate("UPDATE `bt_product_transfer_item` SET `synced`=? WHERE `id`=?", new Object[] { Boolean.valueOf(true), re.get("transferItemId") });
      } 
      sap.commit();
      crm.commit();
      System.out.println("SynkBtProductTransfer done....");
    } catch (Exception e) {
      e.printStackTrace();
      crm.rollBack();
      sap.rollBack();
    } 
  }
  
  public void synkReturnOrder() {
    System.out.println("RO Request Start...");
    SimpleDateFormat dformat = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat tformat = new SimpleDateFormat("HHmmss");
    SqlHelper _crm = new SqlHelper("CRM");
    SqlHelper crm = new SqlHelper("CRM");
    SqlHelper sap = new SqlHelper("SAP");
    try {
      crm.beginTransaction();
      sap.beginTransaction();
      List<Map<String, Object>> rorders = _crm.getResultSet("SELECT \r\n\tro.`created_date` AS createdDate,\r\n\tro.`destination_location_id` AS dLocationId,\r\nro.manual_id AS returnOrderManualId,ro.id as returnOrderId,\r\n\tro.`source_location_id` AS sLocationId,\r\n\temp.first_name AS firstName,\r\n\temp.middle_name AS middleName,\r\n\temp.last_name AS lastName,\r\n\tsloc.location_name AS sLocationName,\r\n\tdloc.location_name AS dLocationName,\r\n\tsloc.plant AS sPlant,\r\n\tdloc.plant AS dPlant,\r\n\titem.id AS itemId,\r\n\tprd.manual_id AS partCode,\r\n\titem.quantity_returned AS returnQty,\r\n\tro.`shipping_mode` AS MOT,\r\n\tro.`tracking_number` AS LorryNo\r\n\tFROM `bt_product_return_order_item` item\r\n\tJOIN `bt_product_return_order` ro ON ro.id=item.`return_order_id`\r\n\tJOIN bt_products prd ON prd.id = item.`products_id`\r\n\tJOIN location dloc ON dloc.location_id= ro.`destination_location_id`\r\n\tJOIN location sloc ON sloc.location_id= ro.`source_location_id`\r\n\tJOIN employee_details emp ON emp.email_id = ro.`owner_email_id`\r\n\tWHERE item.synced=FALSE and sloc.location_type_location_type_id IN (1,4) \r\n\tAND dloc.location_type_location_type_id IN (1,4) \r\n\tAND ro.return_order_status!='Draft' ");
      for (Map<String, Object> ro : rorders) {
        Map<String, Object> checkRequestExists = null;
        try {
          checkRequestExists = sap.getSingleResult("select * from CRM_BT_STK_REQUEST where CRM_DOCNO=? and CRM_LITM=?", new Object[] { ro.get("returnOrderManualId"), ro.get("itemid") });
        } catch (Exception e) {
          e.printStackTrace();
        } 
        String fullName = null;
        String firstName = (ro.get("firstName") == null) ? "" : ro.get("firstName").toString();
        String middleName = (ro.get("middleName") == null) ? "" : ro.get("middleName").toString();
        String lastName = (ro.get("lastName") == null) ? "" : ro.get("lastName").toString();
        fullName = String.valueOf(firstName) + middleName + lastName;
        if (checkRequestExists == null) {
          sap.executeUpdate("insert into CRM_BT_STK_REQUEST(CRM_DOCNO,CRM_LITM,RSDAT,WEMPF,WERKS,WERKS_R,KOSTL,MATNR,ERFMG,SFLAG,TFLAG,SYDAT,SYTIM,SLOC,BDTER) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] { 
                ro.get("returnOrderManualId"), 
                ro.get("itemId"), 
                dformat.format(new Date()), 
                fullName, 
                ro.get("sPlant"), 
                ro.get("dPlant"), 
                "null", 
                ro.get("partCode"), 
                ro.get("returnQty"), 
                "CR", 
                "N", 
                dformat.format(new Date()), 
                tformat.format(new Date()), 
                ro.get("sLocationId"), 
                dformat.format(new Date()) });
          System.out.println("Insert into CRM_BT_STK_REQUEST HEEEE...");
          try {
            checkRequestExists = sap.getSingleResult("select * from CRM_BT_STK_REQUEST where CRM_DOCNO=? and CRM_LITM=?", new Object[] { ro.get("returnOrderManualId"), ro.get("itemId") });
            System.out.println(checkRequestExists + " Has Value");
          } catch (Exception e) {
            e.printStackTrace();
          } 
        } 
        Map<String, Object> checkexist_hdr = null;
        try {
          checkexist_hdr = sap.getSingleResult("select * from CRM_BT_STO_HDR where CRM_DOCNO=?", new Object[] { ro.get("returnOrderManualId") });
        } catch (Exception e) {
          e.printStackTrace();
        } 
        if (checkexist_hdr == null) {
          String motNumber = (ro.get("MOT") == null) ? "" : ro.get("MOT").toString();
          if (motNumber.length() > 30)
            motNumber = motNumber.substring(0, 29); 
          String larryNum = (ro.get("LorryNo") == null) ? "" : ro.get("LorryNo").toString();
          if (larryNum.length() > 20)
            larryNum = larryNum.substring(0, 19); 
          sap.executeUpdate("insert into CRM_BT_STO_HDR(CRM_DOCNO,BUDAT,IWERKS,RWERKS,SFLAG,TFLAG,SYDAT,SYTIM,EBELN,ISLOC,RSLOC,PR_CRM_DOCNO,MOT,LORRYNO) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] { 
                ro.get("returnOrderManualId"), 
                dformat.format(new Date()), 
                ro.get("sPlant"), 
                ro.get("dPlant"), 
                "CR", 
                "N", 
                dformat.format(new Date()), 
                tformat.format(new Date()), 
                (checkRequestExists.get("EBELN") == null) ? null : checkRequestExists.get("EBELN"), 
                ro.get("sLocationId"), 
                ro.get("dLocationId"), 
                ro.get("returnOrderManualId"), 
                motNumber, 
                larryNum });
        } 
        sap.executeUpdate("insert into CRM_BT_STO_ITEM(CRM_DOCNO,CRM_LITM,USNAM,MATNR,ERFMG,SFLAG,TFLAG,SYDAT,SYTIM,PR_CRM_DOCNO,PR_CRM_LITM) values(?,?,?,?,?,?,?,?,?,?,?)", new Object[] { 
              ro.get("returnOrderManualId"), 
              ro.get("itemId"), 
              fullName, 
              ro.get("partCode"), 
              ro.get("returnQty"), 
              "CR", 
              "N", 
              dformat.format(new Date()), 
              tformat.format(new Date()), 
              checkRequestExists.get("CRM_DOCNO"), 
              checkRequestExists.get("CRM_LITM") });
        crm.executeUpdate("UPDATE `bt_product_return_order_item` SET `synced`=? WHERE id=?", new Object[] { Boolean.valueOf(true), ro.get("itemId") });
        crm.executeUpdate("UPDATE `bt_product_return_order` SET `synced`=? WHERE id=?", new Object[] { Boolean.valueOf(true), ro.get("returnOrderId") });
      } 
      crm.commit();
      sap.commit();
      System.out.println("RO request Done....");
    } catch (Exception e) {
      e.printStackTrace();
      sap.rollBack();
      crm.rollBack();
    } 
  }
}
