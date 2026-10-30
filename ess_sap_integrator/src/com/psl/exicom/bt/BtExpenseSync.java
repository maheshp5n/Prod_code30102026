package com.psl.exicom.bt;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BtExpenseSync implements Runnable {
  public void run() {
    try {
      ExpenseCreated();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    try {
      updateTFlag();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void ExpenseCreated() {
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat systime = new SimpleDateFormat("HHmmss");
    SqlHelper _crm = new SqlHelper("CRM");
    try {
      List<Map<String, Object>> expenseData = _crm.getResultSet("SELECT  '1' AS BUZEI,\r\n\t\t\t\t\tcd.employee_code AS LIFNR, cd.grade_name AS GRADE,cd.department AS KOSTL,\r\n\t\t\t\t\tlg.legal_entity_name AS BKTXT_O, ex.manual_id AS XBLNR, ex.created_date AS BLDAT,\r\n\t\t\t\t\tex.last_modified_date AS BUDAT_A, NOW() AS BUDAT, bp.business_place_name AS BUPLA,\r\n\t\t\t\t\tex.`advance_amount` AS WRBTR_A, ex.`net_amount_to_pay` AS WRBTR, cd.employee_code AS ZUONR,\r\n\t\t\t\t\tSUBSTRING(ea.narration,1,49) AS SGTXT, ex.`id` AS SFDC_ID, cc.cost_center_name AS ex_cost_center,\r\n\t\t\t\t\tea.`post_park` as is_post,'CR' AS SFLAG, 'N' AS TFLAG, ex.`expense_type_expense_type_id` AS extype\r\n\t\t\t\t\tFROM `bt_expense_management` ex\r\n\t\t\t\t\tJOIN `bt_expense_accounts_only` ea ON ea.`expense_id`= ex.`id`\r\n\t\t\t\t\tJOIN `contact_details` cd ON cd.`contact_id`= ex.`contact_contact_id`\r\n\t\t\t\t\tLEFT JOIN legal_entity lg ON lg.legal_entity_id= cd.legal_entity_id\r\n\t\t\t\t\tLEFT JOIN `expense_management_cost_center` cc ON cc.`cost_center_id`= ex.`cost_center_cost_center_id`\r\n\t\t\t\t\tLEFT JOIN `expense_management_business_area` ba ON ea.`business_area_business_area_id`= ba.business_area_id\r\n\t\t\t\t\tLEFT JOIN `expense_management_business_place` bp ON ea.`business_place_business_place_id`= bp.business_place_id\r\n\t\t\t\t\tWHERE is_synced=FALSE AND ex.`status_status_id`=6");
      if (expenseData != null && expenseData.size() > 0)
        for (Map<String, Object> ob : expenseData) {
          SqlHelper sap = new SqlHelper("SAP");
          SqlHelper crm = new SqlHelper("CRM");
          if (ob.get("XBLNR").toString().length() == 17)
            continue; 
          try {
            crm.beginTransaction();
            sap.beginTransaction();
            System.out.println("Expense Managment Manual Id " + ob.get("XBLNR"));
            String costCenter = getCostCenter(ob.get("ex_cost_center").toString());
            String busArea = getBusinessArea(costCenter);
            sap.executeUpdate("insert into CRM_BT_EXPENSE(BUZEI,LIFNR,GRADE,KOSTL,BKTXT_O,XBLNR,BLDAT,BUDAT_A,BUDAT,BUPLA,WRBTR_A,WRBTR,ZUONR,SGTXT,CRM_DOCNO,SFLAG,TFLAG,POST,SYDAT,SYTIM,GSBER) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] { 
                  (ob.get("BUZEI") == null) ? "" : ob.get("BUZEI"), 
                  (ob.get("LIFNR") == null) ? "" : ob.get("LIFNR"), 
                  (ob.get("GRADE") == null) ? "" : ob.get("GRADE"), 
                  getCostCenter(ob.get("ex_cost_center").toString()), 
                  ob.get("BKTXT_O"), 
                  ob.get("XBLNR"), 
                  format.format(ob.get("BLDAT")), 
                  format.format(ob.get("BUDAT_A")), 
                  format.format(ob.get("BUDAT")), 
                  getBupla(ob.get("BUPLA").toString()), 
                  ob.get("WRBTR_A"), 
                  ob.get("WRBTR"), 
                  ob.get("ZUONR"), 
                  ob.get("SGTXT"), 
                  ob.get("SFDC_ID"), 
                  ob.get("SFLAG"), 
                  ob.get("TFLAG"), 
                  ob.get("is_post").equals(Boolean.valueOf(true)) ? "N" : "Y", 
                  format.format(new Date()), 
                  systime.format(new Date()), 
                  "2000" });
            int buzy = 2;
            List<Map<String, Object>> loging = crm.getResultSet("SELECT le.`amount_to_disbursed` AS WRBTR\r\nFROM `bt_expense_management` emx \r\nJOIN `bt_lodging_expense` le ON le.`bt_expense_management_id`= emx.id\r\nWHERE le.is_deleted=FALSE AND emx.manual_id=?", 
                
                new Object[] { ob.get("XBLNR") });
            float lodgingExpeTotal = 0.0F;
            for (Map<String, Object> lg : loging)
              lodgingExpeTotal += Float.parseFloat(lg.get("WRBTR").toString()); 
            List<Map<String, Object>> tourExpenseList = crm.getResultSet("SELECT le.`amount_to_disbursed` AS WRBTR\r\nFROM `bt_expense_management` emx \r\nJOIN `bt_tour_expense` le ON le.`bt_expense_management_id`= emx.id\r\nWHERE le.is_deleted=FALSE AND emx.manual_id=?", 
                
                new Object[] { ob.get("XBLNR") });
            for (Map<String, Object> lg : tourExpenseList)
              lodgingExpeTotal += Float.parseFloat(lg.get("WRBTR").toString()); 
            List<Map<String, Object>> boardingExpenseList = crm.getResultSet("SELECT le.`amount_to_disbursed` AS WRBTR\r\nFROM `bt_expense_management` emx \r\nJOIN `bt_boarding_expense` le ON le.`bt_expense_management_id`= emx.id\r\nWHERE le.is_deleted=FALSE AND emx.manual_id=?", 
                
                new Object[] { ob.get("XBLNR") });
            for (Map<String, Object> lg : boardingExpenseList)
              lodgingExpeTotal += Float.parseFloat(lg.get("WRBTR").toString()); 
            if ((ob.get("extype").toString().equalsIgnoreCase("2") || ob.get("extype").toString().equalsIgnoreCase("4")) && 
              lodgingExpeTotal > 0.0F) {
              sap.executeUpdate(
                  "insert into CRM_BT_EXPENSE(BUZEI,LIFNR,GRADE,KOSTL,BKTXT_O,XBLNR,BLDAT,BUDAT_A,BUDAT,BUPLA,WRBTR_A,WRBTR,ZUONR,SGTXT,CRM_DOCNO,SFLAG,TFLAG,POST,HKONT,GSBER) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", 
                  new Object[] { 
                    Integer.valueOf(buzy), (ob.get("LIFNR") == null) ? "" : ob.get("LIFNR"), 
                    (ob.get("GRADE") == null) ? "" : ob.get("GRADE"), 
                    getCostCenter(ob.get("ex_cost_center").toString()), ob.get("BKTXT_O"), 
                    ob.get("XBLNR"), format.format(ob.get("BLDAT")), 
                    format.format(ob.get("BUDAT_A")), format.format(ob.get("BUDAT")), 
                    getBupla(ob.get("BUPLA").toString()), 
                    "0", Float.valueOf(lodgingExpeTotal), ob.get("ZUONR"), 
                    ob.get("SGTXT"), ob.get("SFDC_ID"), ob.get("SFLAG"), ob.get("TFLAG"), 
                    ob.get("is_post").equals(Boolean.valueOf(true)) ? "N" : "Y", "800205", 
                    "2000" });
              buzy++;
            } 
            float totalLocationConvince = 0.0F;
            List<Map<String, Object>> lstLocalCon = crm.getResultSet("SELECT le.`amount` AS WRBTR,le.`amount_to_disbursed` AS `amount_to_disbursed`,'800211' AS `HKONT` \r\n\tFROM `bt_expense_management` emx\r\n\tJOIN `bt_local_conveyance_expense` le ON le.`bt_expense_management_id`= emx.id\r\n\tWHERE le.`is_deleted`=FALSE AND emx.manual_id=?", 
                
                new Object[] { ob.get("XBLNR") });
            for (Map<String, Object> lg : lstLocalCon) {
              if (ob.get("extype").toString().equalsIgnoreCase("2") || ob.get("extype").toString().equalsIgnoreCase("4")) {
                totalLocationConvince += Float.parseFloat(lg.get("WRBTR").toString());
                continue;
              } 
              lodgingExpeTotal += Float.parseFloat(lg.get("WRBTR").toString());
            } 
            if (ob.get("extype").toString().equalsIgnoreCase("2") || ob.get("extype").toString().equalsIgnoreCase("4")) {
              if (totalLocationConvince > 0.0F) {
                sap.executeUpdate(
                    "insert into CRM_BT_EXPENSE(BUZEI,LIFNR,GRADE,KOSTL,BKTXT_O,XBLNR,BLDAT,BUDAT_A,BUDAT,BUPLA,WRBTR_A,WRBTR,ZUONR,SGTXT,CRM_DOCNO,SFLAG,TFLAG,POST,HKONT,GSBER) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", 
                    new Object[] { 
                      Integer.valueOf(buzy), (ob.get("LIFNR") == null) ? "" : ob.get("LIFNR"), 
                      (ob.get("GRADE") == null) ? "" : ob.get("GRADE"), 
                      getCostCenter(ob.get("ex_cost_center").toString()), ob.get("BKTXT_O"), 
                      ob.get("XBLNR"), format.format(ob.get("BLDAT")), 
                      format.format(ob.get("BUDAT_A")), format.format(ob.get("BUDAT")), 
                      getBupla(ob.get("BUPLA").toString()), 
                      "0", Float.valueOf(totalLocationConvince), 
                      ob.get("ZUONR"), ob.get("SGTXT"), ob.get("SFDC_ID"), ob.get("SFLAG"), 
                      ob.get("TFLAG"), ob.get("is_post").equals(Boolean.valueOf(true)) ? "N" : "Y", "800211", 
                      "2000" });
                buzy++;
              } 
            } else if (lodgingExpeTotal > 0.0F) {
              sap.executeUpdate(
                  "insert into CRM_BT_EXPENSE(BUZEI,LIFNR,GRADE,KOSTL,BKTXT_O,XBLNR,BLDAT,BUDAT_A,BUDAT,BUPLA,WRBTR_A,WRBTR,ZUONR,SGTXT,CRM_DOCNO,SFLAG,TFLAG,POST,HKONT,GSBER) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", 
                  new Object[] { 
                    Integer.valueOf(buzy), (ob.get("LIFNR") == null) ? "" : ob.get("LIFNR"), 
                    (ob.get("GRADE") == null) ? "" : ob.get("GRADE"), 
                    getCostCenter(ob.get("ex_cost_center").toString()), ob.get("BKTXT_O"), 
                    ob.get("XBLNR"), format.format(ob.get("BLDAT")), 
                    format.format(ob.get("BUDAT_A")), format.format(ob.get("BUDAT")), 
                    getBupla(ob.get("BUPLA").toString()), 
                    "0", Float.valueOf(lodgingExpeTotal), ob.get("ZUONR"), 
                    ob.get("SGTXT"), ob.get("SFDC_ID"), ob.get("SFLAG"), ob.get("TFLAG"), 
                    ob.get("is_post").equals(Boolean.valueOf(true)) ? "N" : "Y", "800205", 
                    "2000" });
              buzy++;
            } 
            List<Map<String, Object>> lstOtherCharges = crm.getResultSet("SELECT SUM(le.`amount`) AS WRBTR,\r\nSUM(le.`amount_to_disbursed`) AS `amount_to_disbursed`,\r\noc.name AS HKNOTString\r\nFROM `bt_expense_management` ex\r\nJOIN `bt_other_charges_expense` le ON ex.id= le.`bt_expense_management_id`\r\nJOIN `other_charges_types` oc ON le.`other_charges_type_id`= oc.id\r\nWHERE le.is_deleted=FALSE AND ex.manual_id=?\r\nGROUP BY oc.name", 
                
                new Object[] { ob.get("XBLNR") });
            for (Map<String, Object> lg : lstOtherCharges) {
              sap.executeUpdate(
                  "insert into CRM_BT_EXPENSE(BUZEI,LIFNR,GRADE,KOSTL,BKTXT_O,XBLNR,BLDAT,BUDAT_A,BUDAT,BUPLA,WRBTR_A,WRBTR,ZUONR,SGTXT,CRM_DOCNO,SFLAG,TFLAG,POST,HKONT,GSBER) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", 
                  new Object[] { 
                    Integer.valueOf(buzy), (ob.get("LIFNR") == null) ? "" : ob.get("LIFNR"), 
                    (ob.get("GRADE") == null) ? "" : ob.get("GRADE"), 
                    getCostCenter(ob.get("ex_cost_center").toString()), ob.get("BKTXT_O"), 
                    ob.get("XBLNR"), format.format(ob.get("BLDAT")), 
                    format.format(ob.get("BUDAT_A")), format.format(ob.get("BUDAT")), 
                    getBupla(ob.get("BUPLA").toString()), 
                    "0", lg.get("WRBTR"), 
                    ob.get("ZUONR"), ob.get("SGTXT"), ob.get("SFDC_ID"), ob.get("SFLAG"), 
                    ob.get("TFLAG"), ob.get("is_post").equals(Boolean.valueOf(true)) ? "N" : "Y", 
                    getGlcodeFromOtherChargesType(lg.get("HKNOTString").toString()), "2000" });
              buzy++;
            } 
            List<Map<String, Object>> serAppCharges = crm.getResultSet("SELECT SUM(le.`claim_amount`) as WRBTR ,\r\nSUM(le.`amount_to_disbursed`) as amount_to_disbursed \r\n\r\n\tFROM  `bt_expense_management` ex\r\n\tJOIN  `bt_service_appointment_exp` le ON ex.id=le.`bt_expense_management_id`\r\n\tWHERE le.`is_deleted`=FALSE AND ex.manual_id=?", 
                
                new Object[] { "XBLNR" });
            float serviceAppExp = 0.0F;
            for (Map<String, Object> lg : serAppCharges) {
              serviceAppExp += (lg.get("WRBTR") == null) ? 0.0F : Float.parseFloat(lg.get("WRBTR").toString());
              if (serviceAppExp > 0.0F) {
                sap.executeUpdate(
                    "insert into CRM_BT_EXPENSE(BUZEI,LIFNR,GRADE,KOSTL,BKTXT_O,XBLNR,BLDAT,BUDAT_A,BUDAT,BUPLA,WRBTR_A,WRBTR,ZUONR,SGTXT,CRM_DOCNO,SFLAG,TFLAG,POST,HKONT,GSBER) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", 
                    new Object[] { 
                      Integer.valueOf(buzy), (ob.get("LIFNR") == null) ? "" : ob.get("LIFNR"), 
                      (ob.get("GRADE") == null) ? "" : ob.get("GRADE"), 
                      getCostCenter(ob.get("ex_cost_center").toString()), ob.get("BKTXT_O"), 
                      ob.get("XBLNR"), format.format(ob.get("BLDAT")), 
                      format.format(ob.get("BUDAT_A")), format.format(ob.get("BUDAT")), 
                      getBupla(ob.get("BUPLA").toString()), 
                      "0", lg.get("WRBTR"), 
                      ob.get("ZUONR"), ob.get("SGTXT"), ob.get("SFDC_ID"), ob.get("SFLAG"), 
                      ob.get("TFLAG"), ob.get("is_post").equals(Boolean.valueOf(true)) ? "N" : "Y", 
                      "800205", "2000" });
                buzy++;
              } 
            } 
            sap.executeUpdate("update CRM_BT_EXPENSE set SYDAT=?,SYTIM=? where XBLNR=?", new Object[] { format.format(new Date()), systime.format(new Date()), ob.get("XBLNR") });
            crm.executeUpdate("UPDATE `bt_expense_management` SET `is_expense_synced`=?, `is_synced`=? WHERE `id`=?", new Object[] { Boolean.valueOf(false), Boolean.valueOf(true), ob.get("SFDC_ID") });
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
      expenseData = null;
    } catch (Exception e) {
      e.printStackTrace();
    } 
    _crm = null;
  }
  
  private void updateTFlag() {
    try {
      SqlHelper _crm = new SqlHelper("CRM");
      List<Map<String, Object>> tFlags = _crm.getResultSet("SELECT ex.id AS id, ex.manual_id as manual_id FROM `bt_expense_management` ex JOIN `bt_expense_accounts_only`ea ON ea.`expense_id`= ex.`id` WHERE ex.`is_expense_synced`=?  AND ea.`post_park`=?", new Object[] { Boolean.valueOf(false), Boolean.valueOf(true) });
      for (Map<String, Object> f : tFlags) {
        SqlHelper sap = new SqlHelper("SAP");
        SqlHelper crm = new SqlHelper("CRM");
        try {
          sap.beginTransaction();
          crm.beginTransaction();
          List<Map<String, Object>> exist = sap.getResultSet("select * from CRM_BT_EXPENSE where XBLNR=?", new Object[] { f.get("manual_id") });
          if (exist != null && exist.size() > 0 && (
            (Map)exist.get(0)).get("TFLAG") != null && ((Map)exist.get(0)).get("TFLAG").toString().equalsIgnoreCase("Y"))
            crm.executeUpdate("UPDATE `bt_expense_management` SET `is_expense_synced`=? WHERE id=?", new Object[] { Boolean.valueOf(true), f.get("id") }); 
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
      tFlags = null;
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private Object getGlcodeFromOtherChargesType(String otherCharge) {
    String[] st = otherCharge.split("-");
    return st[st.length - 1].trim();
  }
  
  private Object getBupla(String msg) {
    return msg.split("-")[0].trim();
  }
  
  private String getBusinessArea(String costCenter) {
    String busa = "";
    if (costCenter.equalsIgnoreCase("UPS")) {
      busa = "2100";
    } else if (costCenter.equalsIgnoreCase("2142021020") || costCenter.equalsIgnoreCase("2142022000")) {
      busa = "2000";
    } else if (costCenter.equalsIgnoreCase("1042101031")) {
      busa = "1000";
    } 
    return busa;
  }
  
  private String getCostCenter(String costCenter) {
    String[] st = costCenter.split("/");
    return st[st.length - 1].trim();
  }
}
