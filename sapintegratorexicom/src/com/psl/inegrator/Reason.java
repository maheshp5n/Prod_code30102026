package com.psl.inegrator;

import java.util.List;
import java.util.Map;


public class Reason extends Thread{
	

	public void run() {
		
		while(true) {
			
			synkReason();
			
			try {
				sleep(60*1000*60);
			}catch(Exception ex) {
				
			}
		}
		
	}
	
	private void synkReason() {
		SqlHelper _sap=new SqlHelper("SAP");
		try {
			List<Map<String,Object>> _reason=_sap.getResultSet("select * from CRM_REGION where TFLAG=?",new Object[] {"N"});
			if(_reason!=null&&_reason.size()>0) {
				for(Map<String,Object> r:_reason) {
					SqlHelper sap=new SqlHelper("SAP");
					SqlHelper crm=new SqlHelper("CRM");
					try {
						sap.beginTransaction();
						crm.beginTransaction();
						Map<String,Object> exist=crm.getSingleResult("select * from state_province_details where gst_state_code=? and country_id=?",new Object[] {r.get("bland"),r.get("land1")});
						if(exist==null) {
							crm.executeUpdate("insert into state_province_details(state_name,synced,country_id,gst_state_code) values(?,?,?,?)",new Object[] {
									r.get("bezei"),
									true,
									r.get("land1"),
									r.get("bland")
							});
							sap.executeUpdate("update CRM_REGION set TFLAG='Y' where land1=? and bland=?",new Object[] {
									r.get("land1"),
									r.get("bland")
							});
						}
						crm.commit();
						sap.commit();
					}catch(Exception ex) {
						ex.printStackTrace();
						sap.rollBack();
						crm.rollBack();
					}
					
				}	
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
}
