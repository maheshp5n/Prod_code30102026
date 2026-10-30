package com.psl.inegrator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;


public class VendorPoSynk implements Runnable {
	
    @Override
    public void run() {
//        while (true) {
        	try {
        		synkVendorPo();
        	}catch(Exception ex) {
        		ex.printStackTrace();
        	}
            
//            try {
//                sleep(60 * 1000 * 15);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

    }

    private void synkVendorPo() {
        SqlHelper _sap = new SqlHelper("SAP");
        Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            List<Map<String, Object>> _data = _sap
                    .getResultSet("select * from CRM_PRICELIST where SFLAG='CR' AND TFLAG='N'");
            for (Map<String, Object> obj : _data) {
                SqlHelper sap = new SqlHelper("SAP");
                SqlHelper crm = new SqlHelper("CRM");
                try {
                    sap.beginTransaction();
                    crm.beginTransaction();
                    
                    Map<String,Object> circleCheck=crm.getSingleResult("select * from circle_details where circle_name=?",new Object[] {obj.get("SUBMI_DESC")});
                    if(circleCheck==null)
                    	throw new Exception("Circle "+obj.get("SUBMI_DESC")+" not found");
                    
                    crm.executeUpdate(
                            "insert into vendor_po_details(active,activity_price,cost_centre,created_date,gl_code,last_modified_date,po_expiry_date,po_number,po_start_date,remaining_quantity,synked,total_quantity,vendor_code,vendor_name,created_by_email_id,last_modified_by_email_id,owner_email_id,po_type_id,po_date,line_item,activity_number,activity_proce_list_description,coas_center_description,gl_code_description,service_activity_code,service_activity_description,tax_rate,circle_circle_name) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                            new Object[] { 
                            		true,
                            		obj.get("NETPR"),
                            		obj.get("KOSTL"),
                            		today,
                            		obj.get("GLCODE"),
                            		today,
                                    obj.get("VALIDTO") != null ? dateFormat.parse(obj.get("VALIDTO").toString()) : null,
                                    obj.get("PONO"),
                                    obj.get("VALIDFM") != null ? dateFormat.parse(obj.get("VALIDFM").toString()) : null,
                                    obj.get("QUANTITY"),
                                    true,
                                    obj.get("QUANTITY"), 
                                    obj.get("VENDOR"),
                                    obj.get("VENNAME"),
                                    App.user,
                                    App.user,
                                    App.user,
                                    getPoTypeId(obj.get("GLCODE").toString()),// obj.get("SACODE"),
                                    today,
                                    obj.get("LITM"),
                                    obj.get("PLCODE"),
                                    obj.get("PLTEXT"),
                                    obj.get("CCTEXT"),
                                    obj.get("GLTEXT"),
                                    obj.get("SACODE"),
                                    obj.get("ACTTEXT"),
                                    obj.get("TXRATE")==null||obj.get("TXRATE").equals("")||obj.get("TXRATE").equals(" ")?18.00:obj.get("TXRATE"),
                                    obj.get("SUBMI_DESC")
                                    
                     });
                    sap.executeUpdate(
                            "update CRM_PRICELIST set TFLAG='Y' where PONO=? and VALIDFM=? and VALIDTO=? and LITM=? and VENDOR=? and PLCODE=? and SACODE=? and GLCODE=? and KOSTL=?",
                            new Object[] { obj.get("PONO"), obj.get("VALIDFM"), obj.get("VALIDTO"), obj.get("LITM"),
                                    obj.get("VENDOR"), obj.get("PLCODE"), obj.get("SACODE"), obj.get("GLCODE"),
                                    obj.get("KOSTL") });
                    crm.commit();
                    sap.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                    sap.rollBack();
                    crm.rollBack();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    Object getPoTypeId(String glCode) {
    	if(glCode.equalsIgnoreCase("800278"))
    		return 1;
    	if(glCode.equalsIgnoreCase("800287"))
    		return 2;
    	if(glCode.equalsIgnoreCase("800121"))
    		return 3;
    	if(glCode.equalsIgnoreCase("800319"))
    		return 4;
    	if(glCode.equalsIgnoreCase("800353"))
    		return 5;
        if(glCode.equalsIgnoreCase("800354"))
            return 10;
    	return null;
    }
}