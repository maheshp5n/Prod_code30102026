package com.psl.exicom.bt;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BtProductRequest implements Runnable {
  String user = null;
  
  public BtProductRequest() {
    this.user = App.user;
  }
  
  public void run() {
    try {
      SynkRequestToPlant();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    try {
      SynkBtProductTransfer();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    try {
      updateReceivingStatus();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void SynkRequestToPlant() {
    SqlHelper _crm = new SqlHelper("CRM");
    SqlHelper crm = new SqlHelper("CRM");
    SqlHelper sap = new SqlHelper("SAP");
    try {
      crm.beginTransaction();
      sap.beginTransaction();
      SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
      SimpleDateFormat timeformat = new SimpleDateFormat("HHmmss");
      List<Map<String, Object>> request = _crm.getResultSet("SELECT pr.id AS req_id,\r\npr.manual_id AS manual_id,\r\nloc.plant AS splant,\r\ndloc.plant AS dplant,\r\npr.`need_by_date_time` AS need_by,\r\nitem.quantity AS req_quty,\r\nprd.manual_id AS part_code,\r\nemp.first_name AS first_name,\r\nemp.middle_name AS middle_name,\r\nemp.last_name AS last_name,\r\npr.`created_date` AS created_date,\r\nitem.id AS item_id,\r\npr.`description` ,\r\ndloc.location_name AS dlocation_name,\r\ndloc.location_id AS dlocation_id,\r\npr.`source_location_location_id` AS sLocationId\r\nFROM `bt_product_request_item` item\r\nJOIN `bt_product_request` pr ON pr.`id`= item.`bt_product_request_id`\r\nJOIN `bt_products` prd ON prd.`id`= item.`bt_product_id`\r\nJOIN location loc ON loc.location_id= pr.`source_location_location_id`\r\nJOIN location dloc ON dloc.location_id= pr.`destination_location_location_id`\r\nJOIN employee_details emp ON emp.email_id= pr.`owner_details_email_id`\r\nWHERE loc.`location_type_location_type_id` IN (1,4) \r\nAND dloc.`location_type_location_type_id` IN (1,4)\r\nAND item.`synced`=FALSE\r\nAND pr.`dummy_status` NOT IN ('Draft', 'Archive')");
      for (Map<String, Object> re : request) {
        String rsdt = "";
        if (re.get("need_by") != null)
          rsdt = format.format((Date)re.get("need_by")); 
        String bdtr = format.format((Date)re.get("created_date"));
        String sysdt = format.format(new Date());
        String sytim = timeformat.format(new Date());
        if (re.get("dplant") == null || re.get("splant") == null)
          continue; 
        sap.executeUpdate("insert into CRM_BT_STK_REQUEST(CRM_DOCNO,RSDAT,WEMPF,WERKS,MATNR,ERFMG,BDTER,SFLAG,TFLAG,SYDAT,SYTIM,CRM_LITM,REMARK,WERKS_R,SLOC,KOSTL) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'null')", new Object[] { 
              re.get("manual_id"), 
              rsdt, 
              (re.get("first_name") == null) ? "" : (((new StringBuilder()).append(re.get("first_name")).append(re.get("middle_name")).toString() == null) ? "" : (((new StringBuilder()).append(re.get("middle_name")).append(re.get("last_name")).toString() == null) ? "" : re.get("last_name"))), 
              re.get("splant"), 
              re.get("part_code"), 
              re.get("req_quty"), 
              bdtr, 
              "CR", 
              "N", 
              sysdt, 
              sytim, 
              re.get("item_id"), 
              re.get("description"), 
              re.get("splant").equals("4210") ? "4202" : re.get("dplant"), 
              re.get("sLocationId") });
        crm.executeUpdate("UPDATE `bt_product_request_item` SET `synced`=? WHERE `id`=?", new Object[] { Boolean.valueOf(true), re.get("item_id") });
        crm.executeUpdate("UPDATE `bt_product_request` SET `synced`=? WHERE id=?", new Object[] { Boolean.valueOf(true), re.get("req_id") });
      } 
      crm.commit();
      sap.commit();
    } catch (Exception e) {
      sap.rollBack();
      crm.rollBack();
      e.printStackTrace();
    } 
  }
  
  public void SynkBtProductTransfer() {
    System.out.println("ProductTransfer From Plant");
    SqlHelper crm = new SqlHelper("CRM");
    SqlHelper sap = new SqlHelper("SAP");
    try {
      crm.beginTransaction();
      sap.beginTransaction();
      List<Map<String, Object>> req = sap.getResultSet("select CRM_DOCNO,EBELN,count(*) as no_of_element from CRM_BT_STK_TRANSFER where SFLAG='CR' AND TFLAG='N' group by CRM_DOCNO,EBELN");
      for (Map<String, Object> obj : req) {
        try {
          Float ERFMG = Float.valueOf(0.0F);
          List<Map<String, Object>> qnty = sap.getResultSet("select ERFMG from CRM_BT_STK_TRANSFER where CRM_DOCNO=?", new Object[] { obj.get("CRM_DOCNO") });
          for (Map<String, Object> qt : qnty)
            ERFMG = Float.valueOf(ERFMG.floatValue() + Float.parseFloat(qt.get("ERFMG").toString())); 
          Date todate = new Date();
          Map<String, Object> sreq = crm.getSingleResult("SELECT * FROM `bt_product_request` WHERE `manual_id`=?", new Object[] { obj.get("CRM_DOCNO") });
          if (sreq == null)
            continue; 
          List<Map<String, Object>> nonSerial = sap.getResultSet("select MATNR,cast(ERFMG as DECIMAL(10,3)) as qty from CRM_BT_STK_TRANSFER where CRM_DOCNO=? and EBELN=? and SFLAG='CR' and TFLAG='N' ", new Object[] { obj.get("CRM_DOCNO"), obj.get("EBELN") });
          AutoKeyNumber keyHolder = new AutoKeyNumber();
          System.out.println(todate + " " + ERFMG + " " + this.user + " " + sreq.get("destination_location_location_id").toString() + " " + sreq.get("owner_details_email_id").toString() + " " + sreq.get("source_location_location_id").toString());
          crm.executeUpdate(
              "INSERT INTO `bt_product_transfer` (`created_date`,`last_modified_date`,`actual_delivery_date`,`manual_id`,`received_date`,`shipping_mode`,`shipping_to_name`,`total_quantity_issued`,\r\n`total_quantity_received`,`tracking_number`,`transfer_status`,`created_by_email_id`,\r\n`last_modified_by_email_id`,`destination_location_location_id`,`owner_email_id`,`source_location_location_id`,synced)\r\nVALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", 
              
              new Object[] { 
                todate, todate, todate, 
                "SAP", "Main WareHouse", ERFMG, 
                "SAP", "Pending to Receive", this.user, 
                this.user, 
                sreq.get("destination_location_location_id"), sreq.get("owner_details_email_id"), sreq.get("source_location_location_id"), Boolean.valueOf(true) }, keyHolder);
          long tId = keyHolder.getKey().longValue();
          String manualId = String.format("PT-BT-%s", new Object[] { MiscellaneousUtils.fillZero(String.valueOf(tId), 5) });
          crm.executeUpdate("update `bt_product_transfer` set manual_id=? where id=?", new Object[] { manualId, Long.valueOf(tId) });
          sap.executeUpdate("update CRM_BT_STK_TRANSFER set CRM_TRN_DOC_NO=? where CRM_DOCNO=? and EBELN=? and TFLAG='N'", new Object[] { manualId, obj.get("CRM_DOCNO"), obj.get("EBELN") });
          for (Map<String, Object> titem : nonSerial) {
            Map<String, Object> product = crm.getSingleResult("SELECT prd.`id` AS bt_prd_id FROM `bt_product_request_item` pri\r\nJOIN `bt_product_request` pr ON pr.`id` = pri.`bt_product_request_id`\r\nJOIN `bt_products` prd ON prd.`id` = pri.`bt_product_id`\r\nWHERE pr.`id`=? AND prd.`manual_id`=?", 
                
                new Object[] { sreq.get("id"), titem.get("MATNR") });
            AutoKeyNumber k = new AutoKeyNumber();
            Map<String, Object> req_item = crm.getSingleResult("SELECT pri.id as pri_id,pri.quantity_issued as quantity_issued  FROM bt_product_request_item pri JOIN `bt_products` prd ON prd.`id` = pri.`bt_product_id` WHERE `bt_product_request_id`=? AND prd.`id`=?", new Object[] { sreq.get("id"), product.get("bt_prd_id") });
            crm.executeUpdate("INSERT INTO `bt_product_transfer_item` (`created_date`,`last_modified_date`,`received_quantity`,`synced`,`transfer_quantity`,`created_by_email_id`,`btp_request_item_id`,`btp_transfer_id`,`owner_email_id`,`last_modified_by_email_id`)\r\nVALUES (?,?,?,?,?,?,?,?,?,?)", 
                new Object[] { new Date(), new Date(), Integer.valueOf(0), Boolean.valueOf(true), Float.valueOf(Float.parseFloat(titem.get("qty").toString())), this.user, req_item.get("pri_id"), Long.valueOf(tId), sreq.get("owner_details_email_id"), this.user }, k);
            String ptiManualId = "PTI-BT-" + MiscellaneousUtils.fillZero(String.valueOf(k.getKey()), 5);
            crm.executeUpdate("update `bt_product_transfer_item` set manual_id=? where id=?", new Object[] { ptiManualId, Long.valueOf(k.getKey().longValue()) });
            float totalIQutyIssued = Float.parseFloat(req_item.get("quantity_issued").toString()) + Float.parseFloat(titem.get("qty").toString());
            totalIQutyIssued += totalIQutyIssued;
            crm.executeUpdate("update bt_product_request_item set quantity_issued=? where id=?", new Object[] { Float.valueOf(totalIQutyIssued), req_item.get("pri_id") });
            sap.executeUpdate("update CRM_BT_STK_TRANSFER set TFLAG='Y' where CRM_DOCNO=? and MATNR=? and EBELN=? and SFLAG='CR' and TFLAG='N'", new Object[] { obj.get("CRM_DOCNO"), titem.get("MATNR"), obj.get("EBELN") });
          } 
        } catch (Exception e) {
          throw e;
        } 
      } 
      crm.commit();
      sap.commit();
    } catch (Exception e) {
      e.printStackTrace();
      sap.rollBack();
      crm.rollBack();
    } 
  }
  
  private void updateReceivingStatus() {
    System.out.println("Update Receiving Status in STK HDR START....");
    SqlHelper sap = new SqlHelper("SAP");
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
    try {
      List<Map<String, Object>> pendingListToUpdate = sap.getResultSet("   select distinct trn.CRM_TRN_DOC_NO,trn.CRM_DOCNO,hdr.MBLNR,hdr.EBELN \r\n  from CRM_BT_STK_HDR  hdr\r\n  join CRM_BT_STK_TRANSFER trn on hdr.MBLNR=trn.MBLNR and hdr.CRM_DOCNO=trn.CRM_DOCNO \r\n  where hdr.CRM_DOCDATE is null or hdr.CRM_DOCDATE=''");
      for (Map<String, Object> obj : pendingListToUpdate) {
        SqlHelper _sap = new SqlHelper("SAP");
        SqlHelper _crm = new SqlHelper("CRM");
        try {
          _sap.beginTransaction();
          _crm.beginTransaction();
          if (obj.get("CRM_TRN_DOC_NO") != null) {
            List<Map<String, Object>> trn = _crm.getResultSet("SELECT ptr.`receive_date` AS receive_date FROM `bt_product_transfer` pt\r\n JOIN `bt_product_transfer_item` pti ON pti.`btp_transfer_id`=pt.id\r\n JOIN `bt_product_transfer_receive` ptr ON ptr.id= pti.id WHERE pt.`manual_id`=?", 
                
                new Object[] { obj.get("CRM_TRN_DOC_NO") });
            if (trn.size() > 0) {
              System.out.println(((Map)trn.get(0)).get("receive_date"));
              if (((Map)trn.get(0)).get("receive_date") != null) {
                String sm = format.format(((Map)trn.get(0)).get("receive_date"));
                _sap.executeUpdate("update CRM_BT_STK_HDR set CRM_DOCDATE=?,GFLAG='N' where MBLNR=? and CRM_DOCNO=? and (GFLAG is null or GFLAG!='Y')", new Object[] { sm, obj.get("MBLNR"), obj.get("CRM_DOCNO") });
                System.out.println("INNN");
              } 
              System.out.println("IN");
            } 
          } 
          _crm.commit();
          _sap.commit();
          System.out.println("Update Receiving Status in STK HDR Done....");
        } catch (Exception e) {
          e.printStackTrace();
          _sap.rollBack();
          _crm.rollBack();
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
