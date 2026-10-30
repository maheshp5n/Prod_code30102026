package com.psl.inegrator;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

public class BillOfMaterialSynk implements Runnable{

	String userEmailId;
	
	public BillOfMaterialSynk() {
		userEmailId=App.user;
	}
	
	public void run() {
		try {
			syncBom();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
//		try {
//			Thread.sleep(1000*60*30);
//		} catch (InterruptedException e) {
//			
//			e.printStackTrace();
//		}
	}
	private void syncBom() {
		SqlHelper _sap=new SqlHelper("SAP");
		try {
			List<Map<String,Object>> bomList=_sap.getResultSet("SELECT * FROM CRM_BOM_EXPL WHERE TFLAG='N'");
			if(bomList!=null&&bomList.size()>0) {
				for(Map<String,Object> bom:bomList) {
					SqlHelper sap=new SqlHelper("SAP");
					SqlHelper crm=new SqlHelper("CRM");
					
					try {
						sap.beginTransaction();
						crm.beginTransaction();
						System.out.println(bom.get("P_MATNR"));
						System.out.println(bom.get("MATNR"));
						
						Map<String,Object> existRow=crm.getSingleResult("SELECT * FROM bill_of_material WHERE f_product_product_part_code=? AND r_product_product_part_code=?",new Object[] {bom.get("P_MATNR"),bom.get("MATNR")});
						if(existRow!=null) {
							crm.executeUpdate("UPDATE bill_of_material set modified_date=?,modified_by_email_id=?,req_qty=?,level=?,material_type=?,plant1=?,plant2=?,umo=? where id=?",new Object[] {
									new Date(),
									userEmailId,
									Float.parseFloat(bom.get("MENGE").toString()),
									bom.get("STUFE"),
									bom.get("MTART"),
									bom.get("WERKS"),
									bom.get("WERKS01"),
									bom.get("MEINS"),
									existRow.get("id")
							});
							
							StringBuilder sb=new StringBuilder();
							if(bom.get("WTH_CKT_REF").toString().equalsIgnoreCase("Y")) {
								if(bom.get("CKT_REF")!=null&&!bom.get("CKT_REF").toString().equalsIgnoreCase("")) sb.append(bom.get("CKT_REF")+",");
								if(bom.get("CKT_RF1")!=null&&!bom.get("CKT_RF1").toString().equalsIgnoreCase("")) sb.append(bom.get("CKT_RF1")+",");
								if(bom.get("CKT_RF2")!=null&&!bom.get("CKT_RF2").toString().equalsIgnoreCase("")) sb.append(bom.get("CKT_RF2")+",");
								if(bom.get("CKT_RF3")!=null&&!bom.get("CKT_RF3").toString().equalsIgnoreCase("")) sb.append(bom.get("CKT_RF3")+",");
								if(bom.get("CKT_RF4")!=null&&!bom.get("CKT_RF4").toString().equalsIgnoreCase("")) sb.append(bom.get("CKT_RF4")+",");
								if(bom.get("UPTXT")!=null&&!bom.get("UPTXT").toString().equalsIgnoreCase("")) sb.append(bom.get("UPTXT"));
								
								String[] loc=sb.toString().split(",");
								StringBuilder inString=new StringBuilder();
								int incounter=0;
								for(String l:loc) {
									if(l!=null&&!l.equalsIgnoreCase("")) {
										//CHECKING IF LOCATION ALREADY EXISTS OR NOT
										Map<String,Object> locRef=crm.getSingleResult("select * from bill_of_material_location where bom_id=? and location_name=?",new Object[] {
												existRow.get("id"),
												l
										});
										if(locRef==null) {
											crm.executeUpdate("insert into bill_of_material_location(created_date,location_name,modified_date,bom_id,created_by_email_id,modified_by_email_id) values(?,?,?,?,?,?)", new Object[] {
													new Date(),
													l,
													new Date(),
													existRow.get("id"),
													userEmailId,
													userEmailId
											});
										}
										if(incounter==0)
											inString.append("'"+l+"'");
										else
											inString.append(",'"+l+"'");
										incounter++;
									}
								}
								try {
									
								}catch(Exception ex) {
									crm.executeUpdate("delete from bill_of_material_location where bom_id=? and location_name not in ("+inString.toString()+")",new Object[] {
											existRow.get("id")
									});	
								}
								
							}
							else {
								//CHECK FALSE LOCATION EXIST OR NOT
								int rq=1;//Integer.parseInt(bom.get("MENGE").toString().trim());
								StringBuilder inQuery=new StringBuilder();
								for(int i=0;i<rq;i++) {
									Map<String,Object> locRef=crm.getSingleResult("select * from bill_of_material_location where bom_id=? and location_name=?", new Object[] {
											existRow.get("id"),
											i+"_"+bom.get("MATNR")
									});	
									if(locRef==null) {
										crm.executeUpdate("insert into bill_of_material_location(created_date,location_name,modified_date,bom_id,created_by_email_id,modified_by_email_id) values(?,?,?,?,?,?)", new Object[] {
											new Date(),
											i+"_"+bom.get("MATNR"),
											new Date(),
											existRow.get("id"),
											userEmailId,
											userEmailId
										});
									}
									if(i==0)
										inQuery.append("'"+i+"_"+bom.get("MATNR")+"'");
									else
										inQuery.append(",'"+i+"_"+bom.get("MATNR")+"'");
								}
								try {
									crm.executeUpdate("delete from bill_of_material_location where bom_id=? and location_name not in("+inQuery.toString()+")",new Object[] {
											existRow.get("id")
									});
								}catch(Exception ex) {
									
								}
								
							}
						}
						else {
							AutoKeyNumber key=new AutoKeyNumber();
							crm.executeUpdate("insert into bill_of_material (created_date,modified_date,req_qty,created_by_email_id,f_product_product_part_code,modified_by_email_id,owner_email_id,r_product_product_part_code,`level`,material_type,plant1,plant2,umo) values(?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
								new Date(),
								new Date(),
								Float.parseFloat(bom.get("MENGE").toString()),
								userEmailId,
								bom.get("P_MATNR"),
								userEmailId,
								userEmailId,
								bom.get("MATNR"),
								bom.get("STUFE"),
								bom.get("MTART"),
								bom.get("WERKS"),
								bom.get("WERKS01"),
								bom.get("MEINS")
							},key);
							StringBuilder sb=new StringBuilder();
							if(bom.get("WTH_CKT_REF").toString().equalsIgnoreCase("Y")) {
								if(bom.get("CKT_REF")!=null&&!bom.get("CKT_REF").toString().equalsIgnoreCase("")) sb.append(bom.get("CKT_REF")+",");
								if(bom.get("CKT_RF1")!=null&&!bom.get("CKT_RF1").toString().equalsIgnoreCase("")) sb.append(bom.get("CKT_RF1")+",");
								if(bom.get("CKT_RF2")!=null&&!bom.get("CKT_RF2").toString().equalsIgnoreCase("")) sb.append(bom.get("CKT_RF2")+",");
								if(bom.get("CKT_RF3")!=null&&!bom.get("CKT_RF3").toString().equalsIgnoreCase("")) sb.append(bom.get("CKT_RF3")+",");
								if(bom.get("CKT_RF4")!=null&&!bom.get("CKT_RF4").toString().equalsIgnoreCase("")) sb.append(bom.get("CKT_RF4")+",");
								if(bom.get("UPTXT")!=null&&!bom.get("UPTXT").toString().equalsIgnoreCase("")) sb.append(bom.get("UPTXT"));							
								String[] loc=sb.toString().split(",");								
								for(String l:loc) {
									crm.executeUpdate("insert into bill_of_material_location(created_date,location_name,modified_date,bom_id,created_by_email_id,modified_by_email_id) values(?,?,?,?,?,?)", new Object[] {
											new Date(),
											l,
											new Date(),
											key.getKey().longValue(),
											userEmailId,
											userEmailId
									});
								}
							}
							else {
								//CREATING A FALSE LOCATION REFRE 
								int rq=1;//Integer.parseInt(bom.get("MENGE").toString().trim());
								for(int i=0;i<rq;i++) {
									crm.executeUpdate("insert into bill_of_material_location(created_date,location_name,modified_date,bom_id,created_by_email_id,modified_by_email_id) values(?,?,?,?,?,?)", new Object[] {
											new Date(),
											i+"_"+bom.get("MATNR"),
											new Date(),
											key.getKey().longValue(),
											userEmailId,
											userEmailId
									});
								}
							}
							
						}
						
						sap.executeUpdate("update CRM_BOM_EXPL set TFLAG='Y' where P_MATNR=? and STUFE=? and POSNR=? and MATNR=?",new Object[] {bom.get("P_MATNR"),bom.get("STUFE"),bom.get("POSNR"),bom.get("MATNR")});
						
						crm.commit();
						sap.commit();
					}
					catch(Exception e) {
						e.printStackTrace();
						sap.rollBack();
						crm.rollBack();
					}
					crm=null;
					sap=null;
				}
				
			}
			_sap=null;
			bomList=null;
		}catch(Exception ex) {
			ex.printStackTrace();
			
		}
		
	}
}
