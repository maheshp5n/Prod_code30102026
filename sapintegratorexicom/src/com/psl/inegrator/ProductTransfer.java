package com.psl.inegrator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class ProductTransfer implements Runnable {
	TransferCompleateCheck _checkC=new TransferCompleateCheck();
	String user;
	public ProductTransfer() {
		this.user=App.user;
	}
	
	public void run() {
//		while(true) {
			try {
				synkProductTransfer();
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			try {
				synkReturnOrder();
			}catch(Exception ex) {
				
			}
			
//			try {
//				Thread.sleep(1000*60);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
	}
	
	private void synkProductTransfer() {
		SimpleDateFormat dformat=new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat tformat=new SimpleDateFormat("HHmmss");
		SqlHelper _crm=new SqlHelper("CRM");
		SqlHelper crm=new SqlHelper("CRM");
		SqlHelper sap=new SqlHelper("SAP");
		try {
			crm.beginTransaction();
			sap.beginTransaction();
			List<Map<String,Object>> transferItems=_crm.getResultSet("SELECT \r\n" + 
					"	trn.location_product_transfer_manual_id as tranmanualid,\r\n"+
					"	rq.`destination_location_location_id` AS destinationLocationId,\r\n" + 
					"	rq.`source_location_location_id` AS sourceLocationId,\r\n" + 
					"	rq.location_product_request_manual_id as requestManualId,\r\n"+
					" 	dloc.`location_name` as dlocname,\r\n"+
					"	sloc.`location_name` as slocname,\r\n"+
					" 	dloc.`plant` as dplant,\r\n"+
					" 	sloc.`plant` as splant,\r\n"+
					"	trn.`location_product_transfer_manual_id` AS transferManualId,\r\n" + 
					"	item.`created_date` AS itemCreatedDate,\r\n" + 
					"	item.`lp_tranfer_location_product_transfer_id` AS transferId,\r\n" + 
					"	item.`lp_transfer_item_id` AS transferItemId,\r\n" + 
					"	item.`product_product_part_code` AS partCode,\r\n" + 
					"	item.`transfer_quantity` AS transferQty,\r\n" + 
					" 	emp.first_name,\r\n"+
					"	emp.last_name,\r\n"+
					"	emp.middle_name,\r\n"+
					" 	trn.shipping_mode as MOT,\r\n"+
					"	trn.tracking_number as LorryNo\r\n"+
				//	"	litem.`product_serial_number` AS itemSerialNumber\r\n" + 
					" FROM `lptransfer_item` item\r\n" + 
					"	JOIN `lptrasfer` trn ON trn.`location_product_transfer_id`=item.`lp_tranfer_location_product_transfer_id`\r\n" + 
					//"	LEFT JOIN `lptransfer_line_item` litem ON litem.`transfer_item_lp_transfer_item_id`=item.`lp_transfer_item_id`\r\n" + 
					"	JOIN `lproduct_request` rq ON rq.`location_product_request_id`=trn.`location_product_request_location_product_request_id`\r\n" +
					"  	JOIN `location` dloc on dloc.location_id=rq.destination_location_location_id\r\n"+
					"	JOIN `location` sloc on sloc.location_id=rq.source_location_location_id\r\n"+
					" 	JOIN `employee_details` emp on emp.email_id=trn.owner_email_id\r\n"+
					"	WHERE item.synked=FALSE and dloc.location_type_location_type_id in (1,4) and sloc.location_type_location_type_id in (1,4)");
			for(Map<String,Object> re:transferItems) {
				List<Map<String,Object>> checkRequestExists=null;
				try {
					checkRequestExists=sap.getResultSet("select * from CRM_STK_REQUEST where CRM_DOCNO=? and MATNR=?",new Object[] {
							re.get("requestManualId"),
							re.get("partCode")
					});
					
				}catch(Exception ex) {
					continue;
				}
				if(checkRequestExists==null||checkRequestExists.size()==0) {
					continue;
				}
//				if(checkRequestExists.get("EBELN")==null||checkRequestExists.get("EBELN").equals(""))
//					continue;
//				//
//				if(checkRequestExists.get("EBELP")==null||checkRequestExists.get("EBELP").equals(""))
//					continue;
				
				Map<String,Object> checkexist_hdr=null;
				try {
					checkexist_hdr=sap.getSingleResult("select * from CRM_STO_HDR where CRM_DOCNO=?",new Object[] {re.get("tranmanualid")});
				}catch(Exception ex) {
					
				}
						
				if(checkexist_hdr==null) {
					
					String motNumber=re.get("MOT")==null?"":re.get("MOT").toString();
					if(motNumber.length()>30)
						motNumber=motNumber.substring(0, 29);
					
					String larryNum=re.get("LorryNo")==null?"":re.get("LorryNo").toString();
					if(larryNum.length()>20)
						larryNum=larryNum.substring(0, 19);
					
					sap.executeUpdate("insert into CRM_STO_HDR(CRM_DOCNO,BUDAT,IWERKS,RWERKS,SFLAG,TFLAG,SYDAT,SYTIM,EBELN,ISLOC,RSLOC,PR_CRM_DOCNO,MOT,LorryNo,LFLAG) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,'IN')", new Object[] {
							re.get("tranmanualid"),
							dformat.format(new Date()),
							re.get("splant"),
							re.get("dplant"),
							"CR",
							"N",
							dformat.format(new Date()),
							tformat.format(new Date()),
							checkRequestExists.get(0).get("EBELN"),
							re.get("sourceLocationId"),
							re.get("destinationLocationId"),
							re.get("requestManualId"),
							motNumber,//re.get("MOT"),
							larryNum//re.get("LorryNo")
					});
				}
				
				
				
				
				sap.executeUpdate("insert into CRM_STO_ITEM(CRM_DOCNO,CRM_LITM,USNAM,MATNR,ERFMG,SFLAG,TFLAG,SYDAT,SYTIM,PR_CRM_DOCNO,PR_CRM_LITM) values(?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
						re.get("tranmanualid"),
						re.get("transferItemId"),
						re.get("first_name")==null?"":re.get("first_name")+""+re.get("middle_name")==null?"":" "+re.get("middle_name") +re.get("last_name")==null?"":re.get("last_name"),
						re.get("partCode"),
						re.get("transferQty"),
						//re.get("itemSerialNumber"),
						"CR",
						"N",
						dformat.format(new Date()),
						tformat.format(new Date()),
						checkRequestExists.get(0).get("CRM_DOCNO"),
						checkRequestExists.get(0).get("CRM_LITM")
				});
				crm.executeUpdate("update lptransfer_item set synked=? where lp_transfer_item_id=?", new Object[] {
					true,
					re.get("transferItemId")
				});
			}
			sap.commit();
			crm.commit();
		}catch(Exception ex) {
			ex.printStackTrace();
			crm.rollBack();
			sap.rollBack();
		}
		_checkC.checkForPT();

	}
	
	private void synkReturnOrder() {
		SimpleDateFormat dformat=new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat tformat=new SimpleDateFormat("HHmmss");
		SqlHelper _crm=new SqlHelper("CRM");
		SqlHelper crm=new SqlHelper("CRM");
		SqlHelper sap=new SqlHelper("SAP");
		try {
			crm.beginTransaction();
			sap.beginTransaction();
			List<Map<String,Object>> rorders=_crm.getResultSet("SELECT 	ro.`created_date` AS createdDate,\r\n" + 
					"	ro.`destination_location_location_id` AS dLocationId,\r\n" + 
					"	ro.`lpr_order_manual_id` AS returnOrderManualId,\r\n" + 
					"	ro.`source_location_location_id` AS sLocationId,\r\n" + 
					"	emp.`first_name` AS firstName,\r\n" + 
					"	emp.`middle_name` AS middleName,\r\n" + 
					"	emp.`last_name` AS lastName,\r\n" + 
					"	sloc.`location_name` AS sLocationName,\r\n" + 
					"	dloc.`location_name` AS dLocationName,\r\n" + 
					"	sloc.plant as splant,\r\n"+
					" 	dloc.plant as dplant,\r\n"+
					"	item.`lpro_itemid` AS itemId,\r\n" + 
					"	item.`product_product_part_code` AS partCode,\r\n" + 
					"	item.`total_return_quantity` AS returnQty,\r\n" + 
					" 	ro.shipping_mode as MOT,\r\n"+
					"	ro.tracking_number as LorryNo\r\n"+
					"	\r\n" + 
					"	FROM `lproduct_return_order_item` item \r\n" + 
					"	JOIN `lproduct_return_order` ro ON ro.`lpr_orderid`=item.`location_product_return_order_lpr_orderid`\r\n" + 
					"	JOIN `location` sloc ON sloc.`location_id`=ro.`source_location_location_id`\r\n" + 
					"	JOIN `location` dloc ON dloc.`location_id`=ro.`destination_location_location_id`\r\n" + 
					"	JOIN `employee_details` emp ON emp.`email_id`=ro.`owner_email_id`\r\n" + 
					"	WHERE item.synked=FALSE and sloc.location_type_location_type_id in (1,4) and dloc.location_type_location_type_id in (1,4) and ro.location_return_order_status!='Draft'");
			for(Map<String,Object> ro:rorders) {
				
				Map<String,Object> checkrequestexists=null;
				try {
					checkrequestexists=sap.getSingleResult("select * from CRM_STK_REQUEST where CRM_DOCNO=? and CRM_LITM=?",new Object[] {
							ro.get("returnOrderManualId"),
							ro.get("itemId")
							});
				}catch(Exception ex) {
					
				}
				if(checkrequestexists==null) {
					sap.executeUpdate("insert into CRM_STK_REQUEST(CRM_DOCNO,CRM_LITM,RSDAT,WEMPF,WERKS,WERKS_R,KOSTL,MATNR,ERFMG,SFLAG,TFLAG,SYDAT,SYTIM,SLOC,BDTER) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
							ro.get("returnOrderManualId"),
							ro.get("itemId"),
							dformat.format(new Date()),
							ro.get("firstName")==null?"":ro.get("firstName")+""+ro.get("middleName")==null?"":" "+ro.get("middleName") +ro.get("lastName")==null?"":ro.get("lastName"),
							ro.get("splant"),
							ro.get("dplant"),
							"NULL",
							ro.get("partCode"),
							ro.get("returnQty"),
							"CR",
							"N",
							dformat.format(new Date()),
							tformat.format(new Date()),
							ro.get("sLocationId"),
							dformat.format(new Date())
							
					});
					try {
						checkrequestexists=sap.getSingleResult("select * from CRM_STK_REQUEST where CRM_DOCNO=? and CRM_LITM=?",new Object[] {
								ro.get("returnOrderManualId"),
								ro.get("itemId")
								});
					}catch(Exception ex) {
						
					}
				}
				
//				if(checkrequestexists.get("EBELN")==null||checkrequestexists.get("EBELN").equals(""))
//					continue;
//				if(checkrequestexists.get("EBELP")==null||checkrequestexists.get("EBELP").equals(""))
//					continue;
				
				Map<String,Object> checkexist_hdr=null;
				try {
					checkexist_hdr=sap.getSingleResult("select * from CRM_STO_HDR where CRM_DOCNO=?",new Object[] {ro.get("returnOrderManualId")});
				}catch(Exception ex) {
					
				}
						
				if(checkexist_hdr==null) {
					
					String motNumber=ro.get("MOT")==null?"":ro.get("MOT").toString();
					if(motNumber.length()>30)
						motNumber=motNumber.substring(0, 29);
					
					String larryNum=ro.get("LorryNo")==null?"":ro.get("LorryNo").toString();
					if(larryNum.length()>20)
						larryNum=larryNum.substring(0, 19);
					
					sap.executeUpdate("insert into CRM_STO_HDR(CRM_DOCNO,BUDAT,IWERKS,RWERKS,SFLAG,TFLAG,SYDAT,SYTIM,EBELN,ISLOC,RSLOC,PR_CRM_DOCNO,MOT,LorryNo,LFLAG) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,'IN')", new Object[] {
							ro.get("returnOrderManualId"),
							dformat.format(new Date()),
							ro.get("splant"),
							ro.get("dplant"),
							"CR",
							"N",
							dformat.format(new Date()),
							tformat.format(new Date()),
							checkrequestexists.get("EBELN"),
							ro.get("sLocationId"),
							ro.get("dLocationId"),
							ro.get("returnOrderManualId"),
							motNumber,//ro.get("MOT")==null?"":ro.get("MOT").toString(),
							larryNum
					});
				}
				
				
				sap.executeUpdate("insert into CRM_STO_ITEM(CRM_DOCNO,CRM_LITM,USNAM,MATNR,ERFMG,SFLAG,TFLAG,SYDAT,SYTIM,PR_CRM_DOCNO,PR_CRM_LITM) values(?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
						ro.get("returnOrderManualId"),
						ro.get("itemId"),
						ro.get("firstName")==null?"":ro.get("firstName")+""+ro.get("middleName")==null?"":" "+ro.get("middleName") +ro.get("lastName")==null?"":ro.get("lastName"),
						ro.get("partCode"),
						ro.get("returnQty"),
						//re.get("itemSerialNumber"),
						"CR",
						"N",
						dformat.format(new Date()),
						tformat.format(new Date()),
						checkrequestexists.get("CRM_DOCNO"),
						checkrequestexists.get("CRM_LITM")
				});
				crm.executeUpdate("update lproduct_return_order_item set synked=? where lpro_itemid=?", new Object[] {
					true,
					ro.get("itemId")
				});
			}
			
			crm.commit();
			sap.commit();
		}catch(Exception ex) {
			ex.printStackTrace();
			sap.rollBack();
			crm.rollBack();
		}

		_checkC.checkForRo();
	}
	
	private void synkProductTransferStoHdr() {
		
		SqlHelper _sap=new SqlHelper("SAP");
		try {
			List<Map<String,Object>> lst=_sap.getResultSet("select * from CRM_STO_HDR where TFLAG='N' ");
			for(Map<String,Object> re:lst) {
				SqlHelper sap=new SqlHelper("SAP");
				SqlHelper crm=new SqlHelper("CRM");
				try {
					
					crm.executeUpdate("insert into budat,crm_docno,deliv,document1,ebeln,invoice,isloc,iwersks,mblnrg,mjahrg,remark,remarkd,remarkg,remarki,sflag,sydat,sytim,tflag", new Object[] {
						re.get("BUDAT"),
						re.get("CRM_DOCNO"),
						re.get("DELIV"),
						re.get("Document1"),
						re.get("EBELN"),
						re.get("INVOICE"),
						re.get("ISLOC"),
						re.get("IWERKS"),
						
					});
					
					crm.commit();
					sap.commit();
				}catch(Exception ex) {
					sap.rollBack();
					crm.rollBack();
				}
			}
		}catch(Exception ex) {
			
		}
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
	Map<String,Object> getRootPlant(long locationid,SqlHelper helper){
		try {
			Map<String,Object> warehouse=helper.getSingleResult("select * from location where location_id=?",new Object[] {locationid});
			if(warehouse.get("location_type_location_type_id").toString().equalsIgnoreCase("4"))
				return warehouse;
			else
				return getRootPlant(Long.parseLong(warehouse.get("parent_location_location_id").toString()),helper);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return null;
	}
}
