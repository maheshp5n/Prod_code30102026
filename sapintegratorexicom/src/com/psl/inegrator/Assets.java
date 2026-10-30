package com.psl.inegrator;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TimerTask;


public class Assets implements Runnable {	
	String user;
	public Assets() {
		this.user=App.user;
	}
	public void run() {
			try {
				synkAssetsCreated();	
			}catch(Exception ex) {
				
			}
			try {
				synkedAssetsModified();
			}catch(Exception ex) {
				
			}

	}
	public void synkAssetsCreated() {
		SqlHelper _sap=new SqlHelper("SAP");
		try {
			List<Map<String,Object>> _assets=_sap.getResultSet("select * from CRM_ASSET_DATA where TFLAG='N' and SFLAG='CR' and ISSUE_RETURN='I'");
			for(Map<String,Object> m:_assets) {
				SqlHelper sap=new SqlHelper("SAP");
				SqlHelper crm=new SqlHelper("CRM");
				try {
					sap.beginTransaction();
					crm.beginTransaction();
					String serialNumber=m.get("SERNR").toString()+"-"+m.get("MATNR").toString();
					Date poDate=null;
					SimpleDateFormat sapDateFormat=new SimpleDateFormat("yyyyMMdd");
					if(m.get("BSTDK")!=null) {
						poDate=sapDateFormat.parse(m.get("BSTDK").toString());
					}
					
					String _stDateOfDelvery=null;
					if(m.get("LFDAT")!=null)
						_stDateOfDelvery=m.get("LFDAT").toString();
					Date dateOfDelvery=null;
					if(_stDateOfDelvery!=null)
						dateOfDelvery= sapDateFormat.parse(_stDateOfDelvery);
					Calendar cl=Calendar.getInstance(TimeZone.getTimeZone("IST"));
					cl.setTime(dateOfDelvery);
					cl.add(Calendar.MONTH, Integer.parseInt((String)(m.get("WARRANTY_D")==null||m.get("WARRANTY_D").equals("")||m.get("WARRANTY_D").equals(" ")||m.get("WARRANTY_D")=="--"?"0":m.get("WARRANTY_D"))));
					cl.add(Calendar.HOUR_OF_DAY, -24);
					Date dateOfDevleveryExpire=cl.getTime();
					
					Map<String,Object> checkExists=crm.getSingleResult("SELECT * FROM assets where serial_number=?",new Object[] {serialNumber});
					if(checkExists==null) {
						
						Map<String,Object> account=crm.getSingleResult("select * from accounts where sap_customer_number =?",new Object[] { m.get("KUNNR")});
						if(account==null)
							throw new Exception("Customer Acount not found "+ m.get("KUNNR"));
						Map<String,Object> skunnar=crm.getSingleResult("select * from accounts where sap_customer_number=?",new Object[] {m.get("SKUNNR")});
						if(skunnar==null)
							throw new Exception("Primary Account not found "+m.get("SKUNNR"));
						
						crm.executeUpdate("insert into assets("
								+ "serial_number,"
								+ "created_date,"
								+ "sap_delivery_document_number,"
								+ "sap_material_document_number,"
								+ "synced,"
								+ "warranty_from_date_of_delivery_in_month,"
								+ "warranty_from_date_of_installation_in_month,"
								+ "account_sap_customer_number,"
								+ "created_by_email_id,"
								+ "modifyed_by_email_id,"
								+ "plant_plant_code,"
								+ "primary_account_sap_customer_number,"
								+ "product_product_part_code,"
								+ "date_of_delivery_expiry_date,"
								+ "modifyed_date,"
								+ "amc_period,"
								+ "actl_serial_number,"
								+ "`source`,"
								+ "`purchase_order_number`,"
								+ "`purchase_order_date`,"
								+ "`warranty_expiry_date`,"
								+ "`purchase_date`"
								+ ") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
								serialNumber,
								new Date(),
								m.get("VBELN"),
								m.get("MBLNR"),
								true,
								m.get("WARRANTY_D"),
								m.get("WARRANTY_I"),
								m.get("KUNNR"),
								user,
								user,
								m.get("WERKS"),
								m.get("SKUNNR")==null||m.get("SKUNNR").equals("")?null:m.get("SKUNNR").toString(),//account.get("parent_account_sap_customer_number"),
								m.get("MATNR"),
								dateOfDevleveryExpire,
								new Date(),
								"0",
								m.get("SERNR"),
								"SAP",
								m.get("BSTKD"),
								poDate,
								dateOfDevleveryExpire,
								dateOfDelvery
						});
						crm.executeUpdate("insert into global_search_index (key_name,object_details_url,object_id_name,object_id_value,object_name,search_string) values(?,?,?,?,?,?)",new Object[] {
								"serialNumber",
								"assets/"+URLEncoder.encode(serialNumber,"UTF-8"),
								"serialNumber",
								serialNumber,
								"Assets",
								serialNumber
						});
						sap.executeUpdate("update CRM_ASSET_DATA set TFLAG='Y',CRM_DOCNO=? where SERNR=? and MATNR=?",new Object[] {serialNumber,m.get("SERNR"),m.get("MATNR")});
					}
					else {
						sap.executeUpdate("update CRM_ASSET_DATA set TFLAG='Y',CRM_DOCNO=? where SERNR=? and MATNR=?",new Object[] {serialNumber,m.get("SERNR"),m.get("MATNR")});
					}
					sap.commit();
					crm.commit();
				}catch(Exception ex) {
					crm.rollBack();
					sap.rollBack();
					ex.printStackTrace();
				}
				sap=null;
				crm=null;
			}
			_assets=null;
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	public void synkedAssetsModified() {
		System.out.println("Assets Modification start");
		SqlHelper _sap=new SqlHelper("SAP");
		try {
			List<Map<String,Object>> _assets=_sap.getResultSet("select * from CRM_ASSET_DATA where TFLAG='N' and SFLAG='CH' and ISSUE_RETURN='I'");
			for(Map<String,Object> m:_assets) {
				SqlHelper sap=new SqlHelper("SAP");
				SqlHelper crm=new SqlHelper("CRM");
				try {
					sap.beginTransaction();
					crm.beginTransaction();
					String serialNumber=m.get("SERNR").toString()+"-"+m.get("MATNR").toString();
					
					SimpleDateFormat sapDateFormat=new SimpleDateFormat("yyyyMMdd");
					Date poDate=null;
					
					if(m.get("BSTDK")!=null) {
						poDate=sapDateFormat.parse(m.get("BSTDK").toString());
					}
					
					String _stDateOfDelvery=null;
					if(m.get("LFDAT")!=null)
						_stDateOfDelvery=m.get("LFDAT").toString();
					Date dateOfDelvery=null;
					if(_stDateOfDelvery!=null)
						dateOfDelvery= sapDateFormat.parse(_stDateOfDelvery);
					Calendar cl=Calendar.getInstance(TimeZone.getTimeZone("IST"));
					cl.setTime(dateOfDelvery);
					cl.add(Calendar.MONTH, Integer.parseInt((String)(m.get("WARRANTY_D")==null||m.get("WARRANTY_D").equals(" ")?"0":m.get("WARRANTY_D"))));
					cl.add(Calendar.HOUR_OF_DAY, -24);
					Date dateOfDevleveryExpire=cl.getTime();
					
					Map<String,Object> checkExists=crm.getSingleResult("SELECT * FROM assets where serial_number=?",new Object[] {serialNumber});
					if(checkExists!=null) {
						
						Map<String,Object> account=crm.getSingleResult("select * from accounts where sap_customer_number =?",new Object[] { m.get("KUNNR")});
						if(account==null)
							throw new Exception("Customer Account "+m.get("KUNNR")+" not found to update Assets ");
						
						Map<String,Object> skunnar=crm.getSingleResult("select * from accounts where sap_customer_number=?",new Object[] {m.get("SKUNNR")});
						if(skunnar==null)
							throw new Exception("Primary Account "+m.get("SKUNNR")+" not found to update Assets");
						
						crm.executeUpdate("update assets set "
								+ "sap_delivery_document_number=?,"
								+ "sap_material_document_number=?,"
								+ "synced=?,"
								+ "warranty_from_date_of_delivery_in_month=?,"
								+ "warranty_from_date_of_installation_in_month=?,"
								+ "account_sap_customer_number=?,"
//								+ "created_by_email_id=?,"
								+ "modifyed_by_email_id=?,"
								+ "plant_plant_code=?,"
								+ "primary_account_sap_customer_number=?,"
								+ "product_product_part_code=?,"
								+ "date_of_delivery_expiry_date=?,"
								+ "modifyed_date=?,"
								+ "`purchase_order_date`=?,"
								+ "`purchase_order_number`=?, "
								+ " warranty_expiry_date=? "
								+ "where serial_number=?", new Object[] {
								m.get("VBELN"),
								m.get("MBLNR"),
								true,
								m.get("WARRANTY_D"),
								m.get("WARRANTY_I"),
								m.get("KUNNR"),
//								user,
								user,
								m.get("WERKS"),
								m.get("SKUNNR"), //account.get("parent_account_sap_customer_number"),
								m.get("MATNR"),
								dateOfDevleveryExpire,
								new Date(),								
								poDate,
								m.get("BSTKD"),
								dateOfDevleveryExpire,
								serialNumber
						});
						crm.executeUpdate("update assets a set a.warranty_expiry_date=if(a.date_of_delivery_expiry_date>IFNULL(a.install_expiry_date,NOW()),a.date_of_delivery_expiry_date,IFNULL(a.install_expiry_date,a.date_of_delivery_expiry_date)) where a.serial_number = ?", new Object[]{serialNumber});
						sap.executeUpdate("update CRM_ASSET_DATA set TFLAG='Y' where CRM_DOCNO=?",new Object[] {serialNumber});


						
					}
					sap.commit();
					crm.commit();
				}catch(Exception ex) {
					crm.rollBack();
					sap.rollBack();
					ex.printStackTrace();
				}
				sap=null;
				crm=null;
			}
			_assets=null;
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		_sap=null;
	}
}
