package com.psl.inegrator;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class ServiceOrderSynk implements Runnable {

	TransferCompleateCheck _checkC=new TransferCompleateCheck();

	@Override
	public void run() {
		
		synkServiceOrder();
		
		SoReturnSync();
	}
	
	
	private void synkServiceOrder() {
		

		try {
			SqlHelper crm=new SqlHelper("CRM");
			List<Map<String,Object>> soIndata=crm.getResultSet("SELECT so.`manual_id` AS `CRM_DOCNO`,\r\n" + 
					"	item.id AS `CRM_LITM`,\r\n" + 
					"	DATE_FORMAT(so.`last_modified_date`,'%Y%m%d') AS `RSDAT`,\r\n" + 
					"	CONCAT(IFNULL(emp.`first_name`,''),' ',IFNULL(emp.`middle_name`,''),' ',IFNULL(emp.`last_name`,'')) AS WEMPF,\r\n" + 
					"	so.`ware_house_location_id` AS s_plant_id,\r\n" + 
					"	so.`repari_center_location_id` AS d_plant_id,\r\n" + 
					"	so.`ware_house_location_id` AS SLOC,\r\n" + 
					"	'NULL' AS KOSTL,\r\n" + 
					"	item.`product_product_part_code` AS MATNR,\r\n" + 
					"	1 AS ERFMG,\r\n" + 
					"	DATE_FORMAT(so.`created_date`,'%Y%m%d') AS BDTER,\r\n" + 
					"	'CR' AS SFLAG,\r\n" + 
					"	'N' AS TFLAG,\r\n" + 
					"	DATE_FORMAT(NOW(),'%Y%m%d') AS SYDAT,\r\n" + 
					"	DATE_FORMAT(NOW(),'%H%i%s') AS SYTIM,\r\n" +
					" so.shipping_mode as MOT,\r\n"+
					" so.tracking_number as LorryNo" +
					"\r\n" + 
					"FROM `service_order_item` item JOIN `service_order` so ON so.id=item.`service_order_id` JOIN employee_details emp ON emp.email_id=so.`created_by_email_id` WHERE item.`synked`=FALSE and item.customer_inventory=false"); 
			if(soIndata!=null&&soIndata.size()>0) {
				for(Map<String,Object> d:soIndata) {
					SqlHelper _crm=new SqlHelper("CRM");
					SqlHelper _sap=new SqlHelper("SAP");
					try {
						_sap.beginTransaction();
						_crm.beginTransaction();
						
						_sap.executeUpdate("INSERT INTO CRM_STK_REQUEST(CRM_DOCNO,CRM_LITM,RSDAT,WEMPF,WERKS,WERKS_R,SLOC,KOSTL,MATNR,ERFMG,BDTER,SFLAG,TFLAG,SYDAT,SYTIM) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",new Object[] {
								d.get("CRM_DOCNO"),
								d.get("CRM_LITM"),
								d.get("RSDAT"),
								d.get("WEMPF"),
								getWarehouse(Long.parseLong(d.get("s_plant_id").toString()),_crm).get("plant"),
								getWarehouse(Long.parseLong(d.get("d_plant_id").toString()),_crm).get("plant"),
								d.get("SLOC"),
								d.get("KOSTL"),
								d.get("MATNR"),
								d.get("ERFMG"),
								d.get("BDTER"),
								d.get("SFLAG"),
								d.get("TFLAG"),
								d.get("SYDAT"),
								d.get("SYTIM")
						});
						
						Map<String,Object> checkexist_hdr=null;
						try {
							checkexist_hdr=_sap.getSingleResult("select * from CRM_STO_HDR where CRM_DOCNO=?",new Object[] {d.get("CRM_DOCNO")});
						}catch(Exception ex) {
							
						}
						if(checkexist_hdr==null) {
							_sap.executeUpdate("insert into CRM_STO_HDR(CRM_DOCNO,BUDAT,IWERKS,RWERKS,SFLAG,TFLAG,SYDAT,SYTIM,EBELN,ISLOC,RSLOC,PR_CRM_DOCNO,MOT,LorryNo,LFLAG) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,'IN')", new Object[] {
									d.get("CRM_DOCNO"),
									d.get("SYDAT"),
									getWarehouse(Long.parseLong(d.get("s_plant_id").toString()),_crm).get("plant"),
									getWarehouse(Long.parseLong(d.get("d_plant_id").toString()),_crm).get("plant"),
									d.get("SFLAG"),
									d.get("TFLAG"),
									d.get("SYDAT"),
									d.get("SYTIM"),
									null,
									d.get("s_plant_id").toString(),
									d.get("d_plant_id").toString(),
									d.get("CRM_DOCNO"),
									d.get("MOT"),
									d.get("LorryNo")
							});
						}
						Map<String,Object> checkLineItemExists=null;
						try {
							
							checkLineItemExists=_sap.getSingleResult("select * from CRM_STO_ITEM where CRM_DOCNO=? and MATNR=?",new Object[] { d.get("CRM_DOCNO"),d.get("MATNR")});
							
						}catch(Exception ex) {
							throw new Exception(ex);
						}
						
						if(checkLineItemExists==null) {
							_sap.executeUpdate("INSERT INTO CRM_STO_ITEM(CRM_DOCNO,CRM_LITM,PR_CRM_DOCNO,PR_CRM_LITM,USNAM,MATNR,ERFMG,SFLAG,TFLAG,SYDAT,SYTIM) VALUES(?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
									d.get("CRM_DOCNO"),
									d.get("CRM_LITM"),
									d.get("CRM_DOCNO"),
									d.get("CRM_LITM"),
									d.get("WEMPF"),
									d.get("MATNR"),
									d.get("ERFMG"),
									d.get("SFLAG"),
									d.get("TFLAG"),
									d.get("SYDAT"),
									d.get("SYTIM")
							});
						}
						else {
							
							long prevousqty=Long.parseLong(checkLineItemExists.get("ERFMG").toString());
							prevousqty++;
							
							_sap.executeUpdate("update CRM_STO_ITEM set ERFMG=? where CRM_DOCNO=? and MATNR=?",new Object[] { prevousqty,d.get("CRM_DOCNO"),d.get("MATNR")});
						}
						
						
						_crm.executeUpdate("update service_order_item set synked=? where id=?",new Object[] {true,d.get("CRM_LITM")});
						
						_sap.commit();
						_crm.commit();
					}catch(Exception ex) {
						_crm.rollBack();
						_sap.rollBack();
						ex.printStackTrace();
					}
					finally {
						_crm=null;
						_sap=null;
					}
				}
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}

		_checkC.checkForSo();
	}
	
	
	private void SoReturnSync() {
		try {
			SqlHelper crm=new SqlHelper("CRM");
			List<Map<String,Object>> soIndata=crm.getResultSet("SELECT soritem.id AS item_id, sor.`manual_id` AS `CRM_DOCNO`,\r\n" + 
					"	soritem.id AS `CRM_LITM`,\r\n" + 
					"	DATE_FORMAT(sor.`created_date`,'%Y%m%d') AS `RSDAT`,\r\n" + 
					"	CONCAT(IFNULL(emp.`first_name`,''),' ',IFNULL(emp.`middle_name`,''),' ',IFNULL(emp.`last_name`,'')) AS WEMPF,\r\n" + 
					"	so.`ware_house_location_id` AS d_plant_id,\r\n" + 
					"	so.`repari_center_location_id` AS s_plant_id ,\r\n" + 
					"	so.`repari_center_location_id` AS SLOC,\r\n" + 
					"	'NULL' AS KOSTL,\r\n" + 
					"	item.`product_product_part_code` AS MATNR,\r\n" + 
					"	1 AS ERFMG,\r\n" + 
					"	DATE_FORMAT(so.`created_date`,'%Y%m%d') AS BDTER,\r\n" + 
					"	'CR' AS SFLAG,\r\n" + 
					"	'N' AS TFLAG,\r\n" + 
					"	DATE_FORMAT(NOW(),'%Y%m%d') AS SYDAT,\r\n" + 
					"	DATE_FORMAT(NOW(),'%H%i%s') AS SYTIM\r\n" + 
					"\r\n" + 
					"FROM `service_order_item` item \r\n" + 
					"JOIN `service_order` so ON so.id=item.`service_order_id` \r\n" + 
					"JOIN `service_order_item_return` soritem ON soritem.id=item.id\r\n" + 
					"JOIN `service_order_return` sor ON sor.id=soritem.`service_order_return_id`\r\n" + 
					"JOIN employee_details emp ON emp.email_id=sor.`created_by_email_id`\r\n" + 
					"where soritem.synked=false and item.customer_inventory=false"); 
			if(soIndata!=null&&soIndata.size()>0) {
				for(Map<String,Object> d:soIndata) {
					SqlHelper _crm=new SqlHelper("CRM");
					SqlHelper _sap=new SqlHelper("SAP");
					try {
						_sap.beginTransaction();
						_crm.beginTransaction();
						
						_sap.executeUpdate("INSERT INTO CRM_STK_REQUEST(CRM_DOCNO,CRM_LITM,RSDAT,WEMPF,WERKS,WERKS_R,SLOC,KOSTL,MATNR,ERFMG,BDTER,SFLAG,TFLAG,SYDAT,SYTIM) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",new Object[] {
								d.get("CRM_DOCNO"),
								d.get("CRM_LITM"),
								d.get("RSDAT"),
								d.get("WEMPF"),
								getWarehouse(Long.parseLong(d.get("s_plant_id").toString()),_crm).get("plant"),
								getWarehouse(Long.parseLong(d.get("d_plant_id").toString()),_crm).get("plant"),
								d.get("SLOC"),
								d.get("KOSTL"),
								d.get("MATNR"),
								d.get("ERFMG"),
								d.get("BDTER"),
								d.get("SFLAG"),
								d.get("TFLAG"),
								d.get("SYDAT"),
								d.get("SYTIM")
						});
						
						Map<String,Object> checkexist_hdr=null;
						try {
							checkexist_hdr=_sap.getSingleResult("select * from CRM_STO_HDR where CRM_DOCNO=?",new Object[] {d.get("CRM_DOCNO")});
						}catch(Exception ex) {
							
						}
						if(checkexist_hdr==null) {
							_sap.executeUpdate("insert into CRM_STO_HDR(CRM_DOCNO,BUDAT,IWERKS,RWERKS,SFLAG,TFLAG,SYDAT,SYTIM,EBELN,ISLOC,RSLOC,PR_CRM_DOCNO,LFLAG) values(?,?,?,?,?,?,?,?,?,?,?,?,'IN')", new Object[] {
									d.get("CRM_DOCNO"),
									d.get("SYDAT"),
									getWarehouse(Long.parseLong(d.get("s_plant_id").toString()),_crm).get("plant"),
									getWarehouse(Long.parseLong(d.get("d_plant_id").toString()),_crm).get("plant"),
									d.get("SFLAG"),
									d.get("TFLAG"),
									d.get("SYDAT"),
									d.get("SYTIM"),
									null,
									d.get("s_plant_id").toString(),
									d.get("d_plant_id").toString(),
									d.get("CRM_DOCNO")
							});
						}
						
						Map<String,Object> checkLineItemExists=null;
						try {
							
							checkLineItemExists=_sap.getSingleResult("select * from CRM_STO_ITEM where CRM_DOCNO=? and MATNR=?",new Object[] { d.get("CRM_DOCNO"),d.get("MATNR")});
							
						}catch(Exception ex) {
							
						}
						
						if(checkLineItemExists==null) {
							_sap.executeUpdate("INSERT INTO CRM_STO_ITEM(CRM_DOCNO,CRM_LITM,PR_CRM_DOCNO,PR_CRM_LITM,USNAM,MATNR,ERFMG,SFLAG,TFLAG,SYDAT,SYTIM) VALUES(?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
									d.get("CRM_DOCNO"),
									d.get("CRM_LITM"),
									d.get("CRM_DOCNO"),
									d.get("CRM_LITM"),
									d.get("WEMPF"),
									d.get("MATNR"),
									d.get("ERFMG"),
									d.get("SFLAG"),
									d.get("TFLAG"),
									d.get("SYDAT"),
									d.get("SYTIM")
							});
						}
						else {
							
							long prevousqty=Long.parseLong(checkLineItemExists.get("ERFMG").toString());
							prevousqty++;
							
							_sap.executeUpdate("update CRM_STO_ITEM set ERFMG=? where CRM_DOCNO=? and MATNR=?",new Object[] { prevousqty,d.get("CRM_DOCNO"),d.get("MATNR")});
						}
						
//						
//						
//						_sap.executeUpdate("INSERT INTO CRM_STO_ITEM(CRM_DOCNO,CRM_LITM,PR_CRM_DOCNO,PR_CRM_LITM,USNAM,MATNR,ERFMG,SFLAG,TFLAG,SYDAT,SYTIM) VALUES(?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
//								d.get("CRM_DOCNO"),
//								d.get("CRM_LITM"),
//								d.get("CRM_DOCNO"),
//								d.get("CRM_LITM"),
//								d.get("WEMPF"),
//								d.get("MATNR"),
//								d.get("ERFMG"),
//								d.get("SFLAG"),
//								d.get("TFLAG"),
//								d.get("SYDAT"),
//								d.get("SYTIM")
//						});
						
						_crm.executeUpdate("update service_order_item_return set synked=? where id=?",new Object[] {true,d.get("CRM_LITM")});
						
						_sap.commit();
						_crm.commit();
					}catch(Exception ex) {
						_crm.rollBack();
						_sap.rollBack();
						ex.printStackTrace();
					}finally {
						_crm=null;
						_sap=null;
					}
				}
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}

		_checkC.checkForSoR();
	}
	
    Map<String,Object> getWarehouse(long locationId,SqlHelper helper) {
		try {
			Map<String,Object> warehouse=helper.getSingleResult("select * from location where location_id=?",new Object[] {locationId});
			if(warehouse.get("location_type_location_type_id").toString().equalsIgnoreCase("1"))
				return warehouse;
			else
				return getWarehouse(Long.parseLong(warehouse.get("parent_location_location_id").toString()),helper);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return null;
	}
}
