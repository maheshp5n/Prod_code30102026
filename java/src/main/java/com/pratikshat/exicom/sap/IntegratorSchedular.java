package com.pratikshat.exicom.sap;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Component
public class IntegratorSchedular {
	@Autowired
	com.pratikshat.exicom.sap.SapConnectionProvider dbSap;
	@Autowired
	com.pratikshat.exicom.repository.LocationProductRequestRepository dbProductRequest;
	
	@Autowired
	JdbcTemplate template;
	final static String SAP_ID ="sap@exicom.in";
	final static Logger LOGGER=LogManager.getLogger(IntegratorSchedular.class);
	
	///@Scheduled(fixedDelay = 15000*60)
	public void assetsIntegratr() throws Exception {
		Connection con =dbSap.beginTransaction();
		try {
			List<Map<String,Object>> _assets=dbSap.getResultSet("select * from ASSET_DATA where TFLAG='N'");
			if(_assets!=null&_assets.size()>0) {
				for(Map<String,Object> obj:_assets) {
					System.out.printf("Assets Serial Number %s and Product Part code %s\n",obj.get("SERNR"),obj.get("MATNR"));
					Map<String,Object> account=null;
					try {
						account=template.queryForMap("select * from accounts where sap_customer_number =?",obj.get("KUNNR"));
					}catch(EmptyResultDataAccessException emptyAccount) {
						continue;
					}
					Map<String,Object> product=null;
					try {
						product=template.queryForMap("select * from products where product_part_code=?",obj.get("MATNR"));
					}catch(EmptyResultDataAccessException emptyProduct) {
						continue;
					}
					Map<String,Object> plant=null;
					try {
						plant=template.queryForMap("select * from plant_details where plant_code=?",obj.get("WERKS"));
					}catch(EmptyResultDataAccessException emptyPlant) {
						template.update("insert into plant_details(plant_code,plant_name) values(?,?)",new Object[] {obj.get("WERKS"),obj.get("WERKS")});
						plant=template.queryForMap("select * from plant_details where plant_code=?",obj.get("WERKS"));
					}
					SimpleDateFormat sapDateFormat=new SimpleDateFormat("yyyyMMdd");
					String _stDateOfDelvery=null;
					if(obj.get("LFDAT")!=null)
						_stDateOfDelvery=(String)obj.get("LFDAT");
					Date dateOfDelvery=null;
					if(_stDateOfDelvery!=null)
						dateOfDelvery= sapDateFormat.parse(_stDateOfDelvery);
					Calendar cl=Calendar.getInstance(TimeZone.getTimeZone("IST"));
					cl.setTime(dateOfDelvery);
					cl.add(Calendar.MONTH, Integer.parseInt((String)obj.get("WARRANTY_D")));
					Date dateOfDevleveryExpire=cl.getTime();
					
					
					
					if(obj.get("SFLAG").equals("CR")) {
						try {
							template.update("insert into assets(serial_number,created_date,sap_delivery_document_number,sap_material_document_number,synced,warranty_from_date_of_delivery_in_month,warranty_from_date_of_installation_in_month,account_sap_customer_number,created_by_email_id,modifyed_by_email_id,plant_plant_code,primary_account_sap_customer_number,product_product_part_code,date_of_delivery_expiry_date,modifyed_date,amc_period) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,0)",
										new Object[] {
												obj.get("SERNR")+"#"+obj.get("MATNR"),
												new Date(),obj.get("VBELN"),
												obj.get("MBLNR"),
												true,
												obj.get("WARRANTY_D"),
												obj.get("WARRANTY_I"),
												obj.get("KUNNR"),
												SAP_ID,SAP_ID,
												obj.get("WERKS"),
												account.get("parent_account_sap_customer_number"),
												obj.get("MATNR"),
												dateOfDevleveryExpire,
												new Date()
											}
									);
							dbSap.executeUpdate("update ASSET_DATA set SFDC_ID=?,TFLAG=? where SERNR=? and MATNR=?",new Object[] {obj.get("SFDC_ID"),"Y",obj.get("SERNR"),obj.get("MATNR")},con);
						}catch(Exception ex) {
							dbSap.executeUpdate("update ASSET_DATA set SFDC_ID=?,TFLAG=? where SERNR=? and MATNR=?",new Object[] {obj.get("SFDC_ID"),"E",obj.get("SERNR"),obj.get("MATNR")},con);
							ex.printStackTrace();
						}
					}
					if(obj.get("SFLAG").equals("CH")) {
						
					}
				}
			}
			
			dbSap.commitTransaction(con);
			
		}catch(Exception ex) {
			dbSap.rollBack(con);
			
			ex.printStackTrace();
			throw ex;
		}
		try {
			Thread.sleep(60*1000*5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//@Scheduled(fixedDelay=15000*60)
	public void customerMaster() throws Exception {
		Connection con=dbSap.beginTransaction();
		try {
			System.out.printf("Customer Integration Started\n");
			List<Map<String,Object>> _customer=dbSap.getResultSet("select isnull(HKUNNR,'NA') as HKUNNR,KUNNR,NAME,FAX_NUMBER,CITY,LANDX,BEZEI,TELF1,POST_CODE,STREET,SFDC_ID,SFLAG,TFLAG from CUSTOMER_MASTER where TFLAG='N'");
			System.out.println("Size of customer"+ _customer.size());
			if(_customer!=null&&_customer.size()>0) {
				for(Map<String,Object> _obj:_customer) {
					System.out.println("Customer Inte "+_obj.get("HKUNNR"));
					try {
						Map<String,Object> parentCustomer=null;
						try {
							parentCustomer=template.queryForMap("select * from customer_deatils where customer_name=?",new Object[] {_obj.get("HKUNNR")});
						}catch(EmptyResultDataAccessException empty) {
							
						}
						catch(Exception ex) {
						
							ex.printStackTrace();
						}
						
						if(parentCustomer==null) {
							template.update("insert into customer_deatils(created_date,customer_name,modified_date,synced,created_by_email_id,modified_by_email_id) values(?,?,?,?,?,?)",new Object[] {new Date(),_obj.get("HKUNNR"),new Date(),true,SAP_ID,SAP_ID});
							parentCustomer=template.queryForMap("select * from customer_deatils where customer_name=?",new Object[] {_obj.get("HKUNNR")});
						}
						Map<String,Object> billingAddress=null;
						try {
							
							
						}catch(EmptyResultDataAccessException empty) {}
						Map<String,Object> shippingAddres=null;
						Map<String,Object> isAlreadyExit=null;
						try {
							
							isAlreadyExit=template.queryForMap("select * from accounts where sap_customer_number=?",new Object[] {_obj.get("KUNNR"),});
							
						}catch(EmptyResultDataAccessException empty) {}
						if(isAlreadyExit==null) {
							if(!_obj.get("SFLAG").equals("DL")) {
								KeyHolder addresskey=new GeneratedKeyHolder();
								template.update("insert into address_information (address,city,latitude,longitude,pincode,street,synced,country,state) values(?,?,0,0,?,?,true,?,?)",
										new Object[] {
											"NA",
											_obj.get("CITY")==null?"":_obj.get("CITY").toString(),
											_obj.get("POST_CODE")==null?"":_obj.get("POST_CODE").toString(),
											_obj.get("STREET")==null?"":_obj.get("STREET").toString(),
											_obj.get("LANDX")==null?"":_obj.get("LANDX").toString(),
											_obj.get("BEZEI")==null?"":_obj.get("BEZEI").toString()
										},addresskey
										);
								
								
								template.update("INSERT INTO accounts(sap_customer_number,created_date,last_modified,name,phone_number,synced,account_owner_details_id,account_type,created_by,modified_by,parent_customer_customer_id,billing_info_id) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)",
										new Object[] {_obj.get("KUNNR"),new Date(),new Date(),_obj.get("NAME"),_obj.get("FAX_NUMBER"),true,SAP_ID,2,SAP_ID,SAP_ID,parentCustomer.get("customer_id"),(long)(addresskey.getKey())});
								template.update("insert into global_search_index(key_name,object_details_url,object_id_name,object_id_value,object_name,search_string) values(?,?,?,?,?,?)",
									new Object[] {
											"sapCustomerNumber",
											"accounts/"+_obj.get("KUNNR"),
											"sapCustomerNumber",
											_obj.get("KUNNR"),
											"Accounts",
											_obj.get("KUNNR")
									});
								dbSap.executeUpdate("update CUSTOMER_MASTER set TFLAG='Y' where KUNNR=? and HKUNNR=?",new Object[] {_obj.get("KUNNR"),_obj.get("HKUNNR")},con);
							}
						}
						if(_obj.get("SFLAG").equals("CH")) {
							
						}
						if(_obj.get("SFLAG").equals("DL")) {
							try {
								template.update("delete from accounts where sap_customer_number=?",new Object[] {_obj.get("KUNNR")});
								try {
									template.update("delete from global_search_index where object_name=? and object_id_value=?",new Object[] {"Accounts",_obj.get("KUNNR")});
								}catch(Exception ex) {
									
								}
								dbSap.executeUpdate("update CUSTOMER_MASTER set TFLAG='Y',SFDC_ID=? where KUNNR=? and HKUNNR=?",new Object[] {_obj.get("KUNNR"),_obj.get("KUNNR"),_obj.get("HKUNNR")},con);
								
							}catch(Exception ex) {
								
								dbSap.executeUpdate("update CUSTOMER_MASTER set TFLAG='E',SFDC_ID=? where KUNNR=? and HKUNNR=?",new Object[] {_obj.get("KUNNR"),_obj.get("KUNNR"),_obj.get("HKUNNR")},con);
							}
						}
						
					}catch(Exception loopException) {
						loopException.printStackTrace();
					}
				}
				
			}
			dbSap.commitTransaction(con);
		}catch(Exception ex) {
			dbSap.rollBack(con);
			ex.printStackTrace();
			throw ex;
		}

	}
	
	//@Scheduled(fixedDelay=15000*60)
	public void synkProductMaster() throws Exception {
		Connection con=dbSap.beginTransaction();
		try {
			List<Map<String,Object>> _productMaster=dbSap.getResultSet("select MATNR,P_FAMILY,PS_FAMILY,MAKTX,MTART,MEINS,ISNULL(ACTIVE,0) AS ACTIVE,ISNULL(SFDC_ID,'') AS SFDC_ID,SFLAG,TFLAG,SYDAT,SYTIM,cast(VERPR as decimal(10,2)) as VERPR from PRODUCT_MASTER where TFLAG='N'");
			if(_productMaster!=null&&_productMaster.size()>0) {
				for(Map<String,Object> obj:_productMaster) {
					
					Map<String,Object> productfamily=null;
					try {
						productfamily=template.queryForMap("select * from product_family where product_family_name=?",obj.get("P_FAMILY"));
					}catch(EmptyResultDataAccessException emptyEx) {}
							
					if(productfamily==null) {
						template.update("insert into product_family(product_family_name,synced) values (?,?)",obj.get("P_FAMILY"),false);
						productfamily=template.queryForMap("select * from product_family where product_family_name=?",obj.get("P_FAMILY"));
					}
					Map<String,Object> subProduct=null;
					try {
						
						subProduct=template.queryForMap("select * from product_family_sub_product where product_family_product_family_id=? and product_faily_sub_product_name=?",new Object[] {productfamily.get("product_family_id"),obj.get("PS_FAMILY")});
					
					}catch(EmptyResultDataAccessException emptyEx) {
						
					}
					if(subProduct==null) {
						template.update("insert into product_family_sub_product(product_faily_sub_product_name,synced,product_family_product_family_id) values(?,?,?)",new Object[] {obj.get("PS_FAMILY"),true,productfamily.get("product_family_id")});
						subProduct=template.queryForMap("select * from product_family_sub_product where product_family_product_family_id=? and product_faily_sub_product_name=?",new Object[] {productfamily.get("product_family_id"),obj.get("PS_FAMILY")});
					}
					Map<String,Object> productType=null;
					try {
						productType=template.queryForMap("select * from product_type where type_name=?",obj.get("MTART"));
					}catch(EmptyResultDataAccessException ex) {}
					
					if(productType==null) {
						template.update("insert into product_type(synced,type_name) values(?,?)",new Object[] {true,obj.get("MTART")});
						productType=template.queryForMap("select * from product_type where type_name=?",obj.get("MTART"));
					}
					try {
						boolean active=false;
						if(obj.get("ACTIVE")!=null) {
							active=obj.get("ACTIVE").equals("T")?true:false;
						}
						Map<String,Object> isInserted=null;
						try {
							isInserted=template.queryForMap("select product_part_code from products where product_part_code=?",obj.get("MATNR"));	
						}catch(EmptyResultDataAccessException ex) {
							
						}
						
						if(isInserted==null) {
							template.update("insert into products(product_part_code,createddate,modified_date,pdoduct_description,product_price,product_type,synced,sub_product_family_product_family_sub_product_id,active) values(?,?,?,?,?,?,?,?,?)",new Object[] {obj.get("MATNR"),new Date(),new Date(),obj.get("MAKTX"),obj.get("VERPR"),productType.get("type_name"),true,subProduct.get("product_family_sub_product_id"),active});
							template.update("insert into global_search_index(key_name,object_details_url,object_id_name,object_id_value,object_name,search_string) values(?,?,?,?,?,?)",new Object[] {"productPartCode","products/"+obj.get("MATNR"),"productPartCode",obj.get("MATNR"),"Products",obj.get("MATNR")});
						}
						else {
							template.update("update products set modified_date=?,pdoduct_description=?,product_price=?,product_type=?,synced=?,sub_product_family_product_family_sub_product_id=?,active=? where product_part_code=?",new Object[]{new Date(),obj.get("MAKTX"),obj.get("VERPR"),productType.get("type_name"),true,subProduct.get("product_family_sub_product_id"),active,obj.get("MATNR")});
						}
						dbSap.executeUpdate("update PRODUCT_MASTER set SFDC_ID=?,TFLAG=? where MATNR=?", new Object[] {obj.get("MATNR"),"Y",obj.get("MATNR")},con);
						
						
					}catch(Exception ext) {
						
						
						ext.printStackTrace();
					}
					
				}
			}
			dbSap.commitTransaction(con);		
		}catch(Exception ex) {
			dbSap.rollBack(con);
			
			ex.printStackTrace();
			throw ex;
		}
	}

	//@Scheduled(fixedDelay = 15000*60)
	public void sednRequest() throws Exception {
		Connection con=dbSap.beginTransaction();
		try {
			SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat timeformat=new SimpleDateFormat("HH:mm");
			List<Map<String,Object>> request=template.queryForList("SELECT req.`location_product_request_id` AS req_id,\r\n" + 
					"	req.`location_product_request_manual_id` AS manual_id,\r\n" + 
					"	req.location_product_request_id as req_id,\r\n"+
					"	loc.plant AS plant,\r\n" + 
					"	req.`need_by_date_time` AS need_by,\r\n" + 
					"	litem.`quantity` AS req_quty,\r\n" + 
					"	litem.`product_product_part_code` AS part_code,\r\n" + 
					"	emp.`first_name` AS first_name,\r\n" + 
					"	emp.`middle_name` AS middle_name,\r\n" + 
					"	req.`created_date` AS created_date,\r\n"+
					"	litem.`location_product_request_line_item_id` as line_item_id,\r\n"+
					"	emp.`last_name` AS last_name\r\n" + 
					"	\r\n" + 
					"	FROM `lproduct_request_line_item` litem \r\n" + 
					"	JOIN `lproduct_request` req ON litem.`location_product_request_location_product_request_id`=req.`location_product_request_id`\r\n" + 
					"	JOIN `location` loc ON loc.`location_id`=req.`source_location_location_id` \r\n" + 
					"	JOIN `location_type` ltype ON ltype.`location_type_id`=loc.`location_type_location_type_id`\r\n" + 
					"	JOIN `employee_details` emp ON emp.`email_id`=req.`owner_details_email_id` \r\n" + 
					"	WHERE ltype.`location_type_name`= 'Plant' AND litem.`synked`=FALSE");
			if(request!=null&&request.size()>0) {
				for(Map<String,Object> re:request) {
					String rsdt="";
					if(re.get("need_by")!=null){
						rsdt=format.format((Date)re.get("need_by"));
					}
					String bdtr=format.format((Date)re.get("created_date"));
					String sydt=format.format(new Date());
					String sytim=timeformat.format(new Date());
					try {
						List<Map<String,Object>> existing=dbSap.getResultSet("select * from SFDC_REQUISITION_V1 where MATNR=? and SFDC_ID=?",new Object[] {re.get("line_item_id").toString(),re.get("manual_id").toString()});
						if(existing!=null&&existing.size()>0) {
							String mode=existing.get(0).get("SFLAG").toString();
							String tf=existing.get(0).get("TFLAG").toString();
							if(tf.equalsIgnoreCase("Y")) {
								mode="CH";
								tf="N";
							}
							float qty=Float.parseFloat(existing.get(0).get("ERFMG").toString());
							qty+=Float.parseFloat(re.get("req_quty").toString());
							dbSap.executeUpdate("update SFDC_REQUISITION_V1 set ERFMG=?,SFLAG=?,TFLAG=?,SYDAT=?SYTIM=? where SFDC_ID=? and MATNR=?",new Object[] {
									qty,
									mode,
									tf,
									sydt,
									sytim,
									existing.get(0).get("SFDC_ID"),
									existing.get(0).get("MATNR")
							},con);
						}
						else {
							dbSap.executeUpdate("insert into SFDC_REQUISITION_V1(SFDC_ID,KOSTL,RSDAT,WEMPF,WERKS,MATNR,ERFMG,BDTER,SFLAG,TFLAG,SYDAT,SYTIM,SFDC_LNID) values(?,'null',?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
									re.get("manual_id"),
									rsdt,
									String.format("%s %s %s", re.get("first_name"),re.get("middle_name"),re.get("last_name")),
									re.get("plant"),
									re.get("part_code"),
									re.get("req_quty"),
									bdtr,
									"CR",
									"N",
									sydt,
									sytim,
									re.get("line_item_id")
							},con);
						}
						
						template.update("update lproduct_request_line_item set synked=? where location_product_request_line_item_id=?",new Object[] {true,re.get("line_item_id")});
						template.update("update lproduct_request set synked=? where location_product_request_id=?",new Object[] {true,re.get("req_id")});
						
					} catch (Exception e) {
						throw e;
					}	
				}
				
			}
			dbSap.commitTransaction(con);
		}catch(Exception ex) {
			dbSap.rollBack(con);
			throw ex;
		}
		
	}

	///@Scheduled(fixedDelay=15000*60)
	public void receiveItem() throws Exception {
		Connection con=dbSap.beginTransaction();
		try {
			List<Map<String,Object>> req=dbSap.getResultSet("select SFDC_ID,count(*) as no_of_element from SAP_STOCK_TRANSFER where SFLAG='CR' AND TFLAG='N' group by SFDC_ID");
			if(req!=null&&req.size()>0) {
				for(Map<String,Object>obj:req) {
					try {
						Date todate=new Date();
						Map<String,Object> sreq=template.queryForMap("select * from lproduct_request where location_product_request_manual_id=?",obj.get("SFDC_ID"));
						List<Map<String,Object>> titems=dbSap.getResultSet("select MATNR,sum(ERFMG) as qty from SAP_STOCK_TRANSFER where SFDC_ID=? and SFLAG='CR' AND TFLAG='N' group by MATNR",new Object[] {obj.get("SFDC_ID")});
						KeyHolder keyHolder=new GeneratedKeyHolder();
						template.update("insert into lptrasfer(actual_delivery_date,created_date,modified_date,shipping_mode,shipping_to_name,tracking_number,created_by_email_id,location_product_request_location_product_request_id,modified_by_email_id,owner_email_id)", new Object[] {
								new Date(),
								new Date(),
								new Date(),
								"SAP",
								"",
								"SAP",
								SAP_ID,
								sreq.get("location_product_request_id"),
								SAP_ID,
								sreq.get("owner_details_email_id")
						},keyHolder);
						long tId=(long)keyHolder.getKey();
						template.update("update lptrasfer set location_product_transfer_manual_id=? where location_product_transfer_id=?",new Object[] {
								String.format("PT-%s", com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(tId),5)),
								tId
						});
						float totalQIssued=0;
						for(Map<String,Object> titem:titems) {
							KeyHolder k=new GeneratedKeyHolder();
							Map<String,Object> req_item=template.queryForMap("select * from lproduct_request_line_item where location_product_request_location_product_request_id=? and product_product_part_code=?",new Object[] {
									sreq.get("location_product_request_id"),
									titem.get("MATNR")
							});
							template.update("insert into lptransfer_item(created_date,modified_date,received_quantity,transfer_quantity,created_by_email_id,lp_request_line_item_location_product_request_line_item_id,lp_tranfer_location_product_transfer_id,modified_by_email_id,owner_email_id,product_product_part_code)",new Object[] {
									new Date(),
									new Date(),
									0,
									(Float.parseFloat(titem.get("ERFMG").toString())/titems.size()),
									SAP_ID,
									req_item.get("location_product_request_line_item_id"),
									tId,
									SAP_ID,
									sreq.get("owner_details_email_id"),
									titem.get("MATNR")
							},k);
							template.update("update lptransfer_item set lp_transfer_item_id_manual_id=? where lp_transfer_item_id=?",new Object[] {
									String.format("PTI-%s",com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf((long)k.getKey()),5)),
									(long)k.getKey()
							});
							float totalIQutyIssued=Float.parseFloat(req_item.get("quantity_issued").toString())+(Float.parseFloat(titem.get("ERFMG").toString())/titems.size());
							totalQIssued+=totalIQutyIssued;
							template.update("update lproduct_request_line_item set quantity_issued=? where location_product_request_line_item_id=?",new Object[] {totalIQutyIssued,req_item.get("location_product_request_line_item_id")});
							
							List<Map<String,Object>> lineitems=dbSap.getResultSet("select SERNR from SAP_STOCK_TRANSFER where SFDC_ID=? and MATNR=? and SFLAG='CR' AND TFLAG='N'",new Object[] {obj.get("SFDC_ID"),titem.get("MATNR")});
							for(Map<String,Object> litem:lineitems) {
								if(litem.get("SERNR")!=null) {
									KeyHolder lKey=new GeneratedKeyHolder();
									template.update("insert into lptransfer_line_item(created_date,modified_date,product_serial_number,product_status,quantity_received,quantity_transfer,received,created_by_email_id,modified_by_email_id,owner_email_id,transfer_item_lp_transfer_item_id) ",new Object[]{
										new Date(),
										new Date(),
										litem.get("SERNR"),
										"IN-Transit",
										0,
										1,
										false,
										SAP_ID,
										SAP_ID,
										sreq.get("owner_details_email_id"),
										(long)k.getKey()
									},lKey);
									template.update("update lptransfer_line_item set lp_transfer_lineitem_id_manual_id=? where lp_transfer_lineitem_id=?",new Object[] {
											String.format("PTLI-%s", com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf((long)lKey.getKey()),5)),
											(long)lKey.getKey()
									});
								}
							}
							dbSap.executeUpdate("update SAP_STOCK_TRANSFER set TFLAG='Y' where SFDC_ID=? and MATNR=? and SFLAG='CR' AND TFLAG='N'", new Object[] {obj.get("SFDC_ID"),titem.get("MATNR")},con);
						}
						template.update("updtae lproduct_request set total_quantity_issued=? where location_product_request_id=?",new Object[] {
								totalQIssued,
								sreq.get("location_product_request_id")
						});
					}catch(Exception exInner) {
						throw exInner;
					}
				}
			}
			dbSap.commitTransaction(con);
		}catch(Exception ex) {
			dbSap.rollBack(con);
			ex.printStackTrace();
			throw ex;
		}
		
	}

	//@Scheduled(fixedDelay = 15*60*1000)
	public void serviceAppointmentExpenseSynked() {
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
		List<Map<String,Object>> saData=template.queryForList("SELECT \r\n" + 
				"	ctd.`employee_code`,\r\n" + 
				"	ctd.`grade_name`,\r\n" + 
				"	ctd.`department`,\r\n" + 
				"	lg.`legal_entity_name`,\r\n" + 
				"	sa.`appointment_number`,\r\n" + 
				"	cs.close_date,\r\n" + 
				"	sa.`approved_by_finance`,\r\n" + 
				"	sa.`cost_center`,\r\n" + 
				"	lg.`legal_entity_fixed_payout`,\r\n" + 
				"	sa.`service_appointment_id`,\r\n" + 
				"	sa.`visit_date`,\r\n" +
				"	SUBSTRING(exbupla.`business_place_name`,1,4) AS bupla \r\n"+
				" FROM  `service_appointment` sa\r\n" + 
				"	JOIN `case_details` cs ON sa.`case_details_case_id`=cs.`case_id`\r\n" + 
				"	JOIN `service_recource` sr ON sr.`service_resource_id`=sa.`service_resource_service_resource_id`\r\n" + 
				"	JOIN `contact_details` ctd ON ctd.`contact_id`=sr.`contact_contact_id`\r\n" + 
				"	JOIN `contact_designation` ctdd ON ctdd.`designation_id`=ctd.`contact_designation_designation_id`\r\n" + 
				" LEFT JOIN `expense_management_business_place` exbupla ON exbupla.`business_place_id`=ctd.`business_place_business_place_id`\r\n"+
				"	JOIN `legal_entity` lg ON lg.`legal_entity_id`=ctd.`legal_entity_id` WHERE sa.`is_approved_by_finance`='1' AND `sync_flag`='0'");
		if(saData!=null&&saData.size()>0) {
			for(Map<String,Object> sa:saData) {
				Connection con=dbSap.beginTransaction();
				LOGGER.info("LIFNR={}",sa.get("employee_code"));
				LOGGER.info("GRADE={}",sa.get("grade_name"));
				LOGGER.info("KOSTL={}",sa.get("department"));
				LOGGER.info("BKTXT_O={}",sa.get("legal_entity_name"));
				LOGGER.info("XBLNR={}",sa.get("appointment_number"));
				LOGGER.info("BKTXT={}",sa.get(""));
				LOGGER.info("BLDAT={}",sa.get("visit_date"));
				LOGGER.info("BUDAT_A={}",sa.get("approved_by_finance"));
				LOGGER.info("BUDAT={}",sa.get(""));
				LOGGER.info("BUPLA={}",sa.get("cost_center"));
				LOGGER.info("WRBTR={}",sa.get("legal_entity_fixed_payout"));
				LOGGER.info("SFDC_ID={}",sa.get("service_appointment_id"));
								try {
					dbSap.executeUpdate("insert into EXPENSE_REPORT_SA(LIFNR,GRADE,KOSTL,BKTXT_O,XBLNR,BKTXT,BLDAT,BUDAT_A,BUDAT,BUPLA,WRBTR,SFDC_ID,SFLAG,TFLAG,BUZEI) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,'1')", new Object[] {
							sa.get("employee_code")==null?"NA":sa.get("employee_code"),
							sa.get("grade_name"),
							sa.get("department"),
							sa.get("legal_entity_name"),
							sa.get("appointment_number"),
							"",
							sa.get("visit_date")==null?null:format.format((Date)sa.get("visit_date")),
							sa.get("approved_by_finance")==null?null:format.format((Date)sa.get("approved_by_finance")),
							format.format(new Date()),
							sa.get("bupla"),
							sa.get("legal_entity_fixed_payout"),
							sa.get("service_appointment_id"),
							"CR",
							"N"
					}, con);
				template.update("update service_appointment set sync_flag=?,t_flag=?,claim_status_claim_status_id=? where service_appointment_id=?",new Object[] {
						true,
						"Y",
						9,
						sa.get("service_appointment_id")
				});
					dbSap.commitTransaction(con);
				}catch(Exception ex) {
					dbSap.rollBack(con);
					ex.printStackTrace();
				}
				
			}
		}
	}
	//@Scheduled(fixedDelay = 15*60*1000)
	public void expenseSynked() {
		LOGGER.info("Expense Integration Started");
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
		List<Map<String,Object>> exdata=template.queryForList("SELECT \r\n" + 
				"	'1' AS BUZEI,\r\n" + 
				"	cd.`employee_code` AS LIFNR,\r\n" + 
				"	cd.`grade_name` AS GRADE,\r\n" + 
				"	cd.`department` AS KOSTL,\r\n" + 
				"	lg.`legal_entity_name` AS BKTXT_O,\r\n" + 
				"	ea.`expense_management_manual_id` AS XBLNR,\r\n" + 
				"	ea.`created_date` AS BLDAT,\r\n" + 
				"	ea.`modified_date` AS BUDAT_A,\r\n" + 
				"	NOW() AS BUDAT,\r\n" + 
				"	 bp.`business_place_name` AS BUPLA,\r\n" + 
				"	 ea.`advance_amount` AS WRBTR_A,\r\n" + 
				"	 ea.`net_amount_to_pay` AS WRBTR,\r\n" + 
				"	 cd.`employee_code` AS ZUONR,\r\n" + 
				"	 ea.`narration` AS SGTXT,\r\n" + 
				"	 ea.`expense_management_id` AS SFDC_ID,\r\n" + 
				"	 'CR' AS SFLAG,\r\n" + 
				"	 'Y' AS TFLAG\r\n" + 
				" FROM `expense_approved` ea\r\n" + 
				"	JOIN `contact_details` cd ON cd.`contact_id`=ea.`contact_contact_id`\r\n" + 
				" LEFT	JOIN `legal_entity` lg ON lg.`legal_entity_id`=cd.`legal_entity_id`\r\n" + 
				" LEFT	JOIN `expense_management_business_area` bra ON bra.`business_area_id`=ea.`business_area_business_area_id`\r\n" + 
				" LEFT	JOIN `expense_management_business_place` bp ON bp.`business_place_id`=ea.`business_place_business_place_id` WHERE ea.is_synced='0'\r\n" + 
				"	");
		if(exdata!=null&&exdata.size()>0) {
			LOGGER.info("Number of Record for expence is {}",exdata.size());
			for(Map<String,Object> _ob:exdata) {
				Connection con=dbSap.beginTransaction();
				
				try {
					dbSap.executeUpdate("insert into EXPENSE_REPORT(BUZEI,LIFNR,GRADE,KOSTL,BKTXT_O,XBLNR,BLDAT,BUDAT_A,BUDAT,BUPLA,WRBTR_A,WRBTR,ZUONR,SGTXT,SFDC_ID,SFLAG,TFLAG) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
							_ob.get("BUZEI")==null?"":_ob.get("BUZEI"),
							_ob.get("LIFNR")==null?"":_ob.get("LIFNR"),
							_ob.get("GRADE")==null?"":_ob.get("GRADE"),
							_ob.get("KOSTL"),
							_ob.get("BKTXT_O"),
							_ob.get("XBLNR"),
							format.format(_ob.get("BLDAT")),
							format.format(_ob.get("BUDAT_A")),
							format.format(_ob.get("BUDAT")),
							_ob.get("BUPLA"),
							_ob.get("WRBTR_A"),
							_ob.get("WRBTR"),
							_ob.get("ZUONR"),
							_ob.get("SGTXT"),
							_ob.get("SFDC_ID"),
							_ob.get("SFLAG"),
							_ob.get("TFLAG")
					}, con);
					template.update("update expense_approved set is_synced=? where expense_management_id=?",new Object[] {true,_ob.get("SFDC_ID")});
					template.update("update expense_management set is_expense_synced=?,is_post=?,is_synced=? where expense_management_id=?",new Object[] {
							true,
							true,
							true,
							_ob.get("SFDC_ID")
					});
					dbSap.commitTransaction(con);
				}catch(Exception ex) {
					ex.printStackTrace();
					dbSap.rollBack(con);
				}
			}
		}
	}
}
