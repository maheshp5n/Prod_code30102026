package com.psl.inegrator;

import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;


public class CustomerMaster implements Runnable {
	
	String user;
	public CustomerMaster() {
		this.user=App.user;
	}
	public void run() {
//		while(true) {
			synkCustomerMaster();
//			try {
//				Thread.sleep(1000*60*15);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
	}
	void synkCustomerMaster(){
		
		SqlHelper _sap=new SqlHelper("SAP");
		
		try {
			Date today=new Date();
			List<Map<String,Object>> nCustomer=_sap.getResultSet("SELECT * FROM CRM_CUST_MASTER WHERE SFLAG=? and TFLAG=?",new Object[] {"CR","N"});
			if(nCustomer!=null&&nCustomer.size()>0) {
				for(Map<String,Object> c:nCustomer) {
					SqlHelper crm=new SqlHelper("CRM");
					SqlHelper sap=new SqlHelper("SAP");
					
					try {
						sap.beginTransaction();
						crm.beginTransaction();
						
						Map<String,Object> existCustomer=crm.getSingleResult("SELECT * from customer_deatils WHERE customer_name=?",new Object[] {c.get("HKUNNR")});
						if(existCustomer==null) {
							crm.executeUpdate("INSERT INTO customer_deatils(created_date,customer_name,modified_date,synced,created_by_email_id,modified_by_email_id) VALUES(?,?,?,?,?,?)",new Object[] {
									today,
									c.get("HKUNNR"),
									today,
									true,
									user,
									user
							});
							existCustomer=crm.getSingleResult("SELECT * FROM customer_deatils WHERE customer_name=?",new Object[] {c.get("HKUNNR")});
						}
						Map<String,Object> exitAccount=crm.getSingleResult("select * from accounts where sap_customer_number=?",new Object[] {c.get("KUNNR")});
						if(exitAccount==null) {
//							Map<String,Object> state=crm.getSingleResult("SELECT state_id FROM `state_province_details` WHERE gst_state_code=?",new Object[] {c.get("BEZEI")});
//							if(state==null) {
//								throw new Exception("staet code not found");
//							}
//							crm.executeUpdate("INSERT INTO address_information(address,city,pincode,street,synced,latitude,longitude) values(?,?,?,?,?,?,0,0) ", new Object[] {
//									c.get("LANDX"),
//									c.get("CITY"),
//									c.get("POST_CODE"),
//									c.get("STREET"),
//									true
//									//state.get("state_id")
//							});
//							
//							List<Map<String,Object>> _address=crm.getResultSet("SELECT address_info_id FROM address_information WHERE address=? AND city=? AND pincode=? AND street=?", new Object[] {
//									c.get("LANDX"),
//									c.get("CITY"),
//									c.get("POST_CODE"),
//									c.get("STREET"),
//									//c.get("BEZEI"),
//									//state.get("state_id")
//							});
//							Map<String,Object> address=_address.get(0);
							crm.executeUpdate("INSERT INTO accounts(sap_customer_number,created_date,description,email_id,last_modified,name,phone_number,synced,account_owner_details_id,account_type,created_by,modified_by,parent_customer_customer_id,catagory_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
									c.get("KUNNR"),
									today,
									c.get("REMARK"),
									"",
									today,
									c.get("NAME"),
									c.get("TELF1"),
									true,
									user,
									2,
									//address.get("address_info_id"),
									user,
									user,
									existCustomer.get("customer_id"),
									2
							});
							crm.executeUpdate("insert into global_search_index(key_name,object_details_url,object_id_name,object_id_value,object_name,search_string) values(?,?,?,?,?,?)",new Object[] {"sapCustomerNumber","accounts/"+URLEncoder.encode(c.get("KUNNR").toString(),"UTF-8"),"sapCustomerNumber",c.get("KUNNR"),"Accounts",c.get("KUNNR")});
							sap.executeUpdate("update CRM_CUST_MASTER set CRM_DOCNO=?,TFLAG=? where HKUNNR=? and KUNNR=?", new Object[] {
									c.get("KUNNR"),
									"Y",
									c.get("HKUNNR"),
									c.get("KUNNR")
							});
						}
						else {
							sap.executeUpdate("update CRM_CUST_MASTER set CRM_DOCNO=?,TFLAG=? where HKUNNR=? and KUNNR=?", new Object[] {
									c.get("KUNNR"),
									"Y",
									c.get("HKUNNR"),
									c.get("KUNNR")
							});
						}
						
						crm.commit();
						sap.commit();
					}catch(Exception ex) {
						ex.printStackTrace();
						crm.rollBack();
						sap.rollBack();
					}
					crm=null;
					sap=null;
				}
			}
			_sap=null;
			nCustomer=null;
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
}
