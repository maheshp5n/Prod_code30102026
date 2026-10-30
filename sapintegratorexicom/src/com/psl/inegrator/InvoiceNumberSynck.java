package com.psl.inegrator;

import java.util.List;
import java.util.Map;
import java.util.TimerTask;

public class InvoiceNumberSynck implements Runnable{

	@Override
	public void run() {
		try {
			productTransferCsToCs();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		try {
			returnOrderInvoiceScynk();
		}catch(Exception ex) {
			
		}
		try {
			
			//productTransferCsToFactory();
			
		}catch(Exception ex) {
			
		}
		try {
			soreturnOrderInvoiceScynk();
			//productTransferCsToFactory();
			
		}catch(Exception ex) {
			
		}
		try {
			serviceOrderInvoiceScynk();	
		}catch(Exception e) {
			e.printStackTrace();
			
		}
	}
	
	private void productTransferCsToCs() {
		SqlHelper _crm=new SqlHelper("CRM");
		try {
			List<Map<String,Object>> lst=_crm.getResultSet("SELECT lpt.location_product_transfer_id,lpt.location_product_transfer_manual_id,req.location_product_request_manual_id FROM `lptrasfer` lpt \r\n" + 
					"	JOIN `lproduct_request` req ON lpt.`location_product_request_location_product_request_id`=req.`location_product_request_id`\r\n" + 
					"	JOIN `location` sloc ON sloc.location_id=req.`source_location_location_id`\r\n" + 
					"	JOIN `location` dloc ON dloc.location_id=req.`destination_location_location_id`\r\n" + 
					"	WHERE sloc.`location_type_location_type_id` =1 AND dloc.`location_type_location_type_id` =1 AND (lpt.invoice IS NULL OR lpt.invoice='') ");
			for(Map<String,Object> pt:lst) {
				SqlHelper sap=new SqlHelper("SAP");
				SqlHelper crm=new SqlHelper("CRM");
				try {
					crm.beginTransaction();
					sap.beginTransaction();
					Map<String,Object> sapdata=sap.getSingleResult("select * from CRM_STO_HDR where CRM_DOCNO=? and PR_CRM_DOCNO=?",new Object[] {pt.get("location_product_transfer_manual_id"),pt.get("location_product_request_manual_id")});
					if(sapdata!=null) {
						crm.executeUpdate("update lptrasfer set deliv=?,invoice=?,mblnr_g=?,mjahr_g=?,remark_d=?,remark_g=?,remark_i=? where location_product_transfer_id=?",new Object[] {
								sapdata.get("DELIV"),
								sapdata.get("INVOICE"),
								sapdata.get("MBLNR_G"),
								sapdata.get("MJAHR_G"),
								sapdata.get("REMARK_D"),
								sapdata.get("REMARK_G"),
								sapdata.get("REMARK_I"),
								pt.get("location_product_transfer_id")
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
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void returnOrderInvoiceScynk() {
		SqlHelper _crm=new SqlHelper("CRM");
		try {
			List<Map<String,Object>> lst=_crm.getResultSet("select * from lproduct_return_order where invoice is null or invoice=''");
			for(Map<String,Object> pt:lst) {
				SqlHelper sap=new SqlHelper("SAP");
				SqlHelper crm=new SqlHelper("CRM");
				try {
					sap.beginTransaction();
					crm.beginTransaction();
					Map<String,Object> sapdata=sap.getSingleResult("select * from CRM_STO_HDR where CRM_DOCNO=? and PR_CRM_DOCNO=?",new Object[] {pt.get("lpr_order_manual_id"),pt.get("lpr_order_manual_id")});
					if(sapdata!=null) {
						crm.executeUpdate("update lproduct_return_order set deliv=?,invoice=?,mblnr_g=?,mjahr_g=?,remark_d=?,remark_g=?,remark_i=? where lpr_orderid=?",new Object[] {
								sapdata.get("DELIV"),
								sapdata.get("INVOICE"),
								sapdata.get("MBLNR_G"),
								sapdata.get("MJAHR_G"),
								sapdata.get("REMARK_D"),
								sapdata.get("REMARK_G"),
								sapdata.get("REMARK_I"),
								pt.get("lpr_orderid")
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
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void productTransferCsToFactory() {
		SqlHelper _crm=new SqlHelper("CRM");
		try {
			List<Map<String,Object>> lst=_crm.getResultSet("SELECT lpt.location_product_transfer_id,lpt.location_product_transfer_manual_id,req.location_product_request_manual_id  FROM `lptrasfer` lpt \r\n" + 
					"	JOIN `lproduct_request` req ON lpt.`location_product_request_location_product_request_id`=req.`location_product_request_id`\r\n" + 
					"	JOIN `location` sloc ON sloc.location_id=req.`source_location_location_id`\r\n" + 
					"	JOIN `location` dloc ON dloc.location_id=req.`destination_location_location_id`\r\n" + 
					"	WHERE sloc.`location_type_location_type_id`=4 AND dloc.`location_type_location_type_id`=1 AND lpt.invoice IS NULL OR lpt.invoice='' ");
			for(Map<String,Object> pt:lst) {
				SqlHelper sap=new SqlHelper("SAP");
				SqlHelper crm=new SqlHelper("CRM");
				try {
					sap.beginTransaction();
					crm.beginTransaction();
					List<Map<String,Object>> _sapdata=sap.getResultSet("select distinct rq.CRM_TRN_DOC_NO,rq.CRM_DOCNO,hdr.INVOICE_NO,hdr.MBLNR_G,hdr.MJAHR_G,hdr.REMARK_C,hdr.REMARK_G,hdr.REMARK_U from CRM_STK_HDR hdr join CRM_STK_TRANSFER rq on rq.CRM_DOCNO=hdr.CRM_DOCNO and hdr.MBLNR=rq.MBLNR where rq.CRM_TRN_DOC_NO=? and rq.CRM_DOCNO=?",new Object[] {pt.get("location_product_transfer_manual_id"),pt.get("location_product_request_manual_id")});
					for(Map<String,Object> sapdata:_sapdata) {
						if(sapdata!=null) {
							crm.executeUpdate("update lptrasfer set invoice=?,mblnr_g=?,mjahr_g=?,remark_d=?,remark_g=?,remark_i=? where location_product_transfer_id=?",new Object[] {
									
									sapdata.get("INVOICE_NO"),
									sapdata.get("MBLNR_G"),
									sapdata.get("MJAHR_G"),
									sapdata.get("REMARK_C"),
									sapdata.get("REMARK_G"),
									sapdata.get("REMARK_U"),
									pt.get("location_product_transfer_id")
							});
						}
					}
					
					
					crm.commit();
					sap.commit();
				}catch(Exception ex) {
					ex.printStackTrace();
					sap.rollBack();
					crm.rollBack();
				}
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	// update by shagufta
	
	
	private void soreturnOrderInvoiceScynk() {
		SqlHelper _crm=new SqlHelper("CRM");
		try {
			List<Map<String,Object>> lst=_crm.getResultSet("select * from service_order_return where invoice is null or invoice=''");
			for(Map<String,Object> pt:lst) {
				SqlHelper sap=new SqlHelper("SAP");
				SqlHelper crm=new SqlHelper("CRM");
				try {
					sap.beginTransaction();
					crm.beginTransaction();
					Map<String,Object> sapdata=sap.getSingleResult("select * from CRM_STO_HDR where CRM_DOCNO=? and PR_CRM_DOCNO=?",new Object[] {pt.get("manual_id"),pt.get("manual_id")});
					if(sapdata!=null) {
						crm.executeUpdate("update service_order_return set invoice=? where manual_id=?",new Object[] {
								sapdata.get("INVOICE"),
								sapdata.get("CRM_DOCNO")
								
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
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void serviceOrderInvoiceScynk() {
		SqlHelper _crm=new SqlHelper("CRM");
		try {
			List<Map<String,Object>> lst=_crm.getResultSet("select * from service_order where invoice is null or invoice=''");
			for(Map<String,Object> pt:lst) {
				SqlHelper sap=new SqlHelper("SAP");
				SqlHelper crm=new SqlHelper("CRM");
				try {
					sap.beginTransaction();
					crm.beginTransaction();
					Map<String,Object> sapdata=sap.getSingleResult("select * from CRM_STO_HDR where CRM_DOCNO=? and PR_CRM_DOCNO=?",new Object[] {pt.get("manual_id"),pt.get("manual_id")});
					if(sapdata!=null) {
						crm.executeUpdate("update service_order set invoice=? where manual_id=?",new Object[] {
								sapdata.get("INVOICE"),
								sapdata.get("CRM_DOCNO")
								
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
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
