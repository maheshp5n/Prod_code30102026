package com.psl.inegrator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

public class PartcodeChange implements Runnable {
    String user;
    public  PartcodeChange(){
        this.user=App.user;
    }
    public  void  run(){
        try{
            partcodeChangeRequest();

        }catch (Exception ex){
            ex.printStackTrace();
        }
        try {
        	getSapNotification();
        }catch(Exception ex) {
        	ex.printStackTrace();
        }
    }
    private  void partcodeChangeRequest() {
        
    	System.out.println("PART CODE CHANGE INIT");
    	
    	SimpleDateFormat sapFormat=new SimpleDateFormat("yyyyMMdd");
        
        SqlHelper _crm=new SqlHelper("CRM");
        try {
            List<Map<String,Object>> lstdata =_crm.getResultSet("select apr.approval_date,apr.created_by_email_id,apr.created_date,apr.id,apr.location_location_id,apr.modified_by_email_id,apr.new_part_code,apr.new_serialnumber,apr.old_partcode,apr.old_serial_number,apr.remarks,apr.synced,rq.consumed_product_product_part_code,rq.consumed_product_serial_number from partcode_change_request_approval apr join partcode_change_request rq on rq.id=apr.id where apr.`synced`=false");
            for(Map<String,Object> data:lstdata){
                SqlHelper crm=new SqlHelper("CRM");
                SqlHelper sap=new SqlHelper("SAP");
                try{
                	String cby=data.get("created_by_email_id")==null?"":data.get("created_by_email_id").toString();
                	if(cby.length()>24)
                		cby=cby.substring(0, 24);
                	
                    crm.beginTransaction();
                    sap.beginTransaction();
                    sap.executeUpdate("insert into CRM_MAT_CONV(" +
                            "CRM_DOCNO," +
                            "CRM_LITM," +
                            "BKTXT," +
                            "BLDAT," +
                            "BUDAT," +
                            "USNAM," +
                            "MATNR," +
                            "MATNR_N," +
                            "BWARTWA," +
                            "GRUND," +
                            "LGORT," +//storaage location
                            "ERFMG," +
                            "SERNR," +
                            "WERKS," +
                            "KOSTL," +
                            "WEMPF," +
                            "SFLAG," +
                            "TFLAG," +
                            "SYDAT," +
                            "REMARK) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",new Object[]{
                                    data.get("id"),
                                    data.get("id"),
                                    "",
                                    sapFormat.format((Date)data.get("created_date")),
                                    sapFormat.format(new Date()),
                                    data.get("created_by_email_id"),
                                    data.get("consumed_product_product_part_code"),
                                    data.get("new_part_code"),
                                    "",
                                    "",
                                    data.get("location_location_id").toString(),
                                    1,
                                    data.get("new_serialnumber"),
                                    getWarehousePlantCode(Long.parseLong(data.get("location_location_id").toString())),
                                    "",
                                    "",
                                    "CR",
                                    "N",
                                    sapFormat.format(new Date()),
                                    ""//data.get("remarks")
                            });
                    
                    crm.executeUpdate("update partcode_change_request_approval set synced=? where id=?", new Object[] {true,data.get("id")});
                    
                    crm.commit();
                    sap.commit();
                }catch (Exception ex){
                	ex.printStackTrace();
                	System.err.println("Unable to update part code chagne for request "+data.get("new_part_code")+" with old part code"+data.get("old_partcode")+" and serial number "+data.get("new_serialnumber"));
                    sap.rollBack();
                    crm.rollBack();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String getWarehousePlantCode(Long warehouseId) throws Exception {
        String plantCode="";
        SqlHelper crm=new SqlHelper("CRM");
        Map<String,Object> _p= crm.getSingleResult("select location_id,location_type_location_type_id,parent_location_location_id,plant from location where location_id=?",new Object[]{warehouseId});
        if(_p.get("location_type_location_type_id").toString().equalsIgnoreCase("1")) {
        	plantCode=_p.get("plant").toString();
        	return plantCode;
        }
        else {
        	return getWarehousePlantCode(Long.parseLong(_p.get("parent_location_location_id").toString()));
        }
        
    }
    
    private void getSapNotification(){
    	SqlHelper _crm=new SqlHelper("CRM");
    	try {
    		List<Map<String,Object>> pendingList=_crm.getResultSet("select * from partcode_change_request_approval where mblnr_c is null or mblnr_c=''");
    		for(Map<String,Object> pending:pendingList) {
    			SqlHelper sap=new SqlHelper("SAP");
    			SqlHelper crm=new SqlHelper("CRM");
    			try {
    				sap.beginTransaction();
    				crm.beginTransaction();
    				
    				Map<String,Object> sapdetails=sap.getSingleResult("select * from CRM_MAT_CONV where CRM_DOCNO=? and CRM_LITM=?",new Object[] {pending.get("id"),pending.get("id")});
    				crm.executeUpdate("update partcode_change_request_approval set "
    						+ "mblnr=?,"
    						+ "mblnr_c=?,"
    						+ "mblnr_u=?,"
    						+ "mjahr=?,"
    						+ "mjahr_c=?,"
    						+ "mjahr_u=?,"
    						+ "remark_c=?,"
    						+ "remark_u=?"
    						+ " where id=?", new Object[] {
    						sapdetails.get("MBLNR"),
    						sapdetails.get("MBLNR_C"),
    						sapdetails.get("MBLNR_U"),
    						sapdetails.get("MJAHR"),
    						sapdetails.get("MJAHR_C"),
    						sapdetails.get("MJAHR_U"),
    						sapdetails.get("REMARK_C"),
    						sapdetails.get("REMARK_U"),
    						pending.get("id")
    				});
    				
    				crm.commit();
    				sap.commit();    				
    			}catch(Exception ex) {
    				crm.rollBack();
    				sap.rollBack();
    				ex.printStackTrace();
    			}
    		}
    		
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    }
}
