package com.psl.exicom.bt;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BtServiceAppointmentExpense implements Runnable {
  public void run() {
    btServiceAppointmentExpenseSynked();
  }
  
  private void btServiceAppointmentExpenseSynked() {
    SqlHelper _crm = new SqlHelper("CRM");
    try {
      SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
      SimpleDateFormat systime = new SimpleDateFormat("HHmmss");
      List<Map<String, Object>> saData = _crm.getResultSet("SELECT  ctd.employee_code,\r\n\tctd.grade_name,\r\n\tctd.department,\r\n\tlg.legal_entity_name,\r\n\tsa.manual_id ,\r\n\tcs.close_date,\r\n\tsa.approved_by_finance,\r\n\tsa.`cost_center`,\r\n\tlg.legal_entity_fixed_payout,\r\n\tsa.fixed_payout,\r\n\tsa.service_appointment_id,\r\n\tsa.visit_date,\r\n\tSUBSTRING(exbupla.business_place_name,1,4) AS bupla,\r\n\tcrd.cost_center AS sa_costcenter\r\n\tFROM `bt_service_appointment` sa\r\n\tJOIN `bt_case` cs ON sa.`case_details_id`= cs.id\r\n\tJOIN `case_reason_details` crd ON crd.`reason_id`=cs.`case_reason_reason_id` \r\n\tJOIN `service_recource` sr ON sr.`service_resource_id`=sa.`service_resource_service_resource_id`\r\n\tJOIN contact_details ctd ON ctd.contact_id= sr.contact_contact_id\r\n\tJOIN `contact_designation` ctdd ON ctdd.`designation_id`=ctd.`contact_designation_designation_id`\r\n\tLEFT JOIN `expense_management_business_place` exbupla ON exbupla.`business_place_id`=ctd.`business_place_business_place_id`\r\n\tJOIN `legal_entity` lg ON lg.`legal_entity_id`=ctd.`legal_entity_id` \r\n\tWHERE sa.`is_approved_by_finance`='1' AND sa.`sync_flag`='0'");
      if (saData != null && saData.size() > 0)
        for (Map<String, Object> sa : saData) {
          SqlHelper sap = new SqlHelper("SAP");
          SqlHelper crm = new SqlHelper("CRM");
          try {
            sap.beginTransaction();
            crm.beginTransaction();
            String costCenter = (sa.get("sa_costcenter") == null) ? sa.get("cost_center").toString() : sa.get("sa_costcenter").toString();
            sap.executeUpdate("insert into CRM_BT_EXPENSE(LIFNR,GRADE,KOSTL,BKTXT_O,XBLNR,BKTXT,BLDAT,BUDAT_A,BUDAT,BUPLA,WRBTR,CRM_DOCNO,SFLAG,TFLAG,BUZEI,POST,GSBER,SYDAT,SYTIM,ZUONR,SGTXT) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,'1','Y','4202',?,?,?,?)", new Object[] { 
                  (sa.get("employee_code") == null) ? "NA" : sa.get("employee_code"), 
                  sa.get("grade_name"), 
                  (sa.get("sa_costcenter") == null) ? sa.get("cost_center") : sa.get("sa_costcenter"), 
                  sa.get("legal_entity_name"), 
                  sa.get("manual_id"), 
                  "", 
                  (sa.get("visit_date") == null) ? null : format.format((Date)sa.get("visit_date")), 
                  (sa.get("approved_by_finance") == null) ? null : format.format((Date)sa.get("approved_by_finance")), 
                  format.format(new Date()), 
                  sa.get("bupla"), 
                  sa.get("fixed_payout"), 
                  sa.get("service_appointment_id"), 
                  "CR", 
                  "N", 
                  format.format(new Date()), 
                  systime.format(new Date()), 
                  (sa.get("employee_code") == null) ? "NA" : sa.get("employee_code"), 
                  getRemarks(Long.parseLong(sa.get("service_appointment_id").toString())) });
            crm.executeUpdate("UPDATE `bt_service_appointment` SET `sync_flag`=?,`t_flag`=?,`claim_status_claim_status_id`=? WHERE `service_appointment_id`=?", new Object[] { Boolean.valueOf(true), "N", Integer.valueOf(9), sa.get("service_appointment_id") });
            sap.commit();
            crm.commit();
          } catch (Exception e) {
            crm.rollBack();
            sap.rollBack();
            e.printStackTrace();
          } 
        }  
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  void updateTFlag() {
    try {
      SqlHelper _crm = new SqlHelper("CRM");
      List<Map<String, Object>> nFlatSa = _crm.getResultSet("SELECT * FROM bt_service_appointment where t_flag='N'");
      for (Map<String, Object> f : nFlatSa) {
        SqlHelper sap = new SqlHelper("SAP");
        SqlHelper crm = new SqlHelper("CRM");
        try {
          sap.beginTransaction();
          crm.beginTransaction();
          List<Map<String, Object>> exist = sap.getResultSet("SELECT * FROM CRM_BT_EXPENSE WHERE XBLNR=?", new Object[] { f.get("manual_id") });
          if (exist != null && exist.size() > 0 && ((Map)exist.get(0)).get("TFLAG").toString().equalsIgnoreCase("Y"))
            crm.executeUpdate("update bt_service_appointment set t_flag=? where service_appointment_id=?", new Object[] { "Y", 
                  f.get("service_appointment_id") }); 
          sap.commit();
          crm.commit();
        } catch (Exception ex) {
          ex.printStackTrace();
          crm.rollBack();
          sap.rollBack();
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private Object getRemarks(long saNumber) {
    SqlHelper crm = new SqlHelper("CRM");
    Map<String, Object> re = null;
    try {
      re = crm.getSingleResult("select remark from bt_to_be_approved where object_name='BtServiceAppointment' and object_id=? and id=(select max(id) as id from bt_to_be_approved where object_name='BtServiceAppointment' and object_id=? )", new Object[] { Long.valueOf(saNumber), Long.valueOf(saNumber) });
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return (re.get("remark") == null) ? "" : re.get("remark").toString();
  }
  
  private String getBuisnessArea(String costCenter) {
    if (costCenter.equalsIgnoreCase("1042101030"))
      return "1000"; 
    return "1100";
  }
}
