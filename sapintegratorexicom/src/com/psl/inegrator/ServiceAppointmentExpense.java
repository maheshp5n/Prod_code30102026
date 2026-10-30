package com.psl.inegrator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;


public class ServiceAppointmentExpense implements Runnable{
	
	public void run() {
//		while(true) {
			try {
				serviceAppointmentExpenseSynked();
			}catch(Exception ex) {
				
			}
			try {
				updateTFlag();
			}catch(Exception ex) {}
//			try {
//				Thread.sleep(1000*60);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
	}
	private void serviceAppointmentExpenseSynked() {
		SqlHelper _crm=new SqlHelper("CRM");
		
		try {
			SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat systime=new SimpleDateFormat("HHmmss");
			List<Map<String,Object>> saData=_crm.getResultSet("SELECT \r\n" + 
					"	ctd.`employee_code`,\r\n" + 
					"	ctd.`grade_name`,\r\n" + 
					"	ctd.`department`,\r\n" + 
					"	lg.`legal_entity_name`,\r\n" + 
					"	sa.`appointment_number`,\r\n" + 
					"	cs.close_date,\r\n" + 
					"	sa.`approved_by_finance`,\r\n" + 
					"	sa.`cost_center`,\r\n" + 
					"	lg.`legal_entity_fixed_payout`,\r\n" + 
					"	sa.fixed_payout,\r\n"+
					"	sa.`service_appointment_id`,\r\n" + 
					"	sa.`visit_date`,\r\n" +
					"	SUBSTRING(exbupla.`business_place_name`,1,4) AS bupla, \r\n"+
					" 	crd.`cost_center` AS sa_costcenter\r\n"+
					" FROM  `service_appointment` sa\r\n" + 
					"	JOIN `case_details` cs ON sa.`case_details_case_id`=cs.`case_id`\r\n" + 
					"	JOIN `case_reason_details` crd ON crd.`reason_id`=cs.`case_reason_reason_id`"+
					"	JOIN `service_recource` sr ON sr.`service_resource_id`=sa.`service_resource_service_resource_id`\r\n" + 
					"	JOIN `contact_details` ctd ON ctd.`contact_id`=sr.`contact_contact_id`\r\n" + 
					"	JOIN `contact_designation` ctdd ON ctdd.`designation_id`=ctd.`contact_designation_designation_id`\r\n" + 
					" 	LEFT JOIN `expense_management_business_place` exbupla ON exbupla.`business_place_id`=ctd.`business_place_business_place_id`\r\n"+
					"	JOIN `legal_entity` lg ON lg.`legal_entity_id`=ctd.`legal_entity_id` WHERE sa.`is_approved_by_finance`='1' AND `sync_flag`='0'");
			if(saData!=null&&saData.size()>0) {
				for(Map<String,Object> sa:saData) {
					SqlHelper sap=new SqlHelper("SAP");
					SqlHelper crm=new SqlHelper("CRM");
					try {
						sap.beginTransaction();
						crm.beginTransaction();
						String costCenter=sa.get("sa_costcenter")==null?sa.get("cost_center").toString():sa.get("sa_costcenter").toString();
						
						sap.executeUpdate("insert into CRM_EXPENSE(LIFNR,GRADE,KOSTL,BKTXT_O,XBLNR,BKTXT,BLDAT,BUDAT_A,BUDAT,BUPLA,WRBTR,CRM_DOCNO,SFLAG,TFLAG,BUZEI,POST,GSBER,SYDAT,SYTIM,ZUONR,SGTXT) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,'1','Y','"+getBuisnessArea(costCenter)+"',?,?,?,?)", new Object[] {
								sa.get("employee_code")==null?"NA":sa.get("employee_code"),
								sa.get("grade_name"),
								sa.get("sa_costcenter")==null?sa.get("cost_center"):sa.get("sa_costcenter"),//sa.get("department"),
								sa.get("legal_entity_name"),
								sa.get("appointment_number"),
								"",
								sa.get("visit_date")==null?null:format.format((Date)sa.get("visit_date")),
								sa.get("approved_by_finance")==null?null:format.format((Date)sa.get("approved_by_finance")),
								format.format(new Date()),
								sa.get("bupla"),
								sa.get("fixed_payout"),//sa.get("legal_entity_fixed_payout"),
								sa.get("service_appointment_id"),
								"CR",
								"N",
								format.format(new Date()),
								systime.format(new Date()),
								sa.get("employee_code")==null?"NA":sa.get("employee_code"),
								getRemarks(Long.parseLong(sa.get("service_appointment_id").toString()))
						});
						
						sap.executeUpdate("insert into CRM_EXPENSE(LIFNR,GRADE,KOSTL,BKTXT_O,XBLNR,BKTXT,BLDAT,BUDAT_A,BUDAT,BUPLA,WRBTR,CRM_DOCNO,SFLAG,TFLAG,BUZEI,POST,HKONT,GSBER,WRBTR_A,SYDAT,SYTIM,ZUONR,SGTXT) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,'2','Y','800205','"+getBuisnessArea(costCenter)+"','0.0',?,?,?,?)", new Object[] {
								sa.get("employee_code")==null?"NA":sa.get("employee_code"),
								sa.get("grade_name"),
								sa.get("sa_costcenter")==null?sa.get("cost_center"):sa.get("sa_costcenter"),//sa.get("department"),
								sa.get("legal_entity_name"),
								sa.get("appointment_number"),
								"",
								sa.get("visit_date")==null?null:format.format((Date)sa.get("visit_date")),
								sa.get("approved_by_finance")==null?null:format.format((Date)sa.get("approved_by_finance")),
								format.format(new Date()),
								sa.get("bupla"),
								sa.get("fixed_payout"),//sa.get("legal_entity_fixed_payout"),
								sa.get("service_appointment_id"),
								"CR",
								"N",
								format.format(new Date()),
								systime.format(new Date()),
								sa.get("employee_code")==null?"NA":sa.get("employee_code"),
										getRemarks(Long.parseLong(sa.get("service_appointment_id").toString()))
						});
					crm.executeUpdate("update service_appointment set sync_flag=?,t_flag=?,claim_status_claim_status_id=? where service_appointment_id=?",new Object[] {
							true,
							"N",
							9,
							sa.get("service_appointment_id")
					});				
						sap.commit();
						crm.commit();
					}catch(Exception ex) {
						crm.rollBack();
						sap.rollBack();
						ex.printStackTrace();
					}
					
				}
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	void updateTFlag() {
		try {
			SqlHelper _crm=new SqlHelper("CRM");
			List<Map<String,Object>> nFlatSa=_crm.getResultSet("SELECT * FROM service_appointment where t_flag='N'");
			
			for(Map<String,Object> f:nFlatSa) {
				SqlHelper sap=new SqlHelper("SAP");
				SqlHelper crm=new SqlHelper("CRM");
				try {
					sap.beginTransaction();
					crm.beginTransaction();
					
					List<Map<String,Object>> exist=sap.getResultSet("SELECT * FROM CRM_EXPENSE WHERE XBLNR=?",new Object[] {f.get("appointment_number")});
					if(exist!=null&&exist.size()>0&&exist.get(0).get("TFLAG").toString().equalsIgnoreCase("Y"))
						crm.executeUpdate("update service_appointment set t_flag=? where service_appointment_id=?",new Object[] {
								"Y",
								f.get("service_appointment_id")
						});	
					
					sap.commit();
					crm.commit();
				}catch(Exception ex) {
					ex.printStackTrace();
					crm.rollBack();
					sap.rollBack();
					
				}
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	String getRemarks(long saNumber){
		SqlHelper crm=new SqlHelper("CRM");
		Map<String,Object> re=null;
		try {
			re=crm.getSingleResult("select remark from to_be_approved where object_name='ServiceAppointment' and object_id=? and id=(select max(id) as id from to_be_approved where object_name='ServiceAppointment' and object_id=? )",new Object[] {saNumber,saNumber});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return re.get("remark")==null?"":re.get("remark").toString();
	}
	
	String getBuisnessArea(String msg){
		if(msg.equalsIgnoreCase("1042101030"))
			//return "4210";
			return "1000";
		//return "3000";
		return "1100";
	}
}
