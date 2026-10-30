package com.psl.inegrator;

import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;




public class ProductMaster implements Runnable {

	String user;
	public ProductMaster() {
		this.user=App.user;
	}
	public void run() {
//		while(true) {
			try {
				
				synkProductMaster();
			}catch(Exception ex) {
				
				ex.printStackTrace();
			}
			try {
				synkProductMasterCHANGE();
			}catch(Exception ex) {
				
			}
			
//			try {
//				Thread.sleep(15*1000*60);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
	}
	
	public void synkProductMaster() throws Exception {
		
		SqlHelper _sap=new SqlHelper("SAP");
		try {
			List<Map<String,Object>> _productMaster=_sap.getResultSet("select MATNR,P_FAMILY,PS_FAMILY,MAKTX,MTART,MEINS,ISNULL(STATUS,0) AS ACTIVE,ISNULL(CRM_DOCNO,'') AS CRM_DOCNO,SFLAG,TFLAG,SYDAT,SYTIM,cast(VERPR as decimal(10,2)) as VERPR from CRM_PROD_MASTER where TFLAG='N' and SFLAG='CR'");
			if(_productMaster!=null&&_productMaster.size()>0) {
				for(Map<String,Object> obj:_productMaster) {
					SqlHelper sap=new SqlHelper("SAP");
					SqlHelper crm=new SqlHelper("CRM");
					sap.beginTransaction();
					crm.beginTransaction();
					try {
						
						Map<String,Object> productfamily=crm.getSingleResult("select * from product_family where product_family_name=?",new Object[] { obj.get("P_FAMILY")});
								
						if(productfamily==null) {
							crm.executeUpdate("insert into product_family(product_family_name,synced) values (?,?)",new Object[]{ obj.get("P_FAMILY"),false});
							productfamily=crm.getSingleResult("select * from product_family where product_family_name=?",new Object[] {obj.get("P_FAMILY")});
						}
						Map<String,Object> subProduct=crm.getSingleResult("select * from product_family_sub_product where product_family_product_family_id=? and product_faily_sub_product_name=?",new Object[] {productfamily.get("product_family_id"),obj.get("PS_FAMILY")});
						
						if(subProduct==null) {
							crm.executeUpdate("insert into product_family_sub_product(product_faily_sub_product_name,synced,product_family_product_family_id) values(?,?,?)",new Object[] {obj.get("PS_FAMILY"),true,productfamily.get("product_family_id")});
							subProduct=crm.getSingleResult("select * from product_family_sub_product where product_family_product_family_id=? and product_faily_sub_product_name=?",new Object[] {productfamily.get("product_family_id"),obj.get("PS_FAMILY")});
						}
						Map<String,Object> productType=crm.getSingleResult("select * from product_type where type_name=?",new Object[] { obj.get("MTART")});
						
						
						if(productType==null) {
							crm.executeUpdate("insert into product_type(synced,type_name) values(?,?)",new Object[] {true,obj.get("MTART")});
							productType=crm.getSingleResult("select * from product_type where type_name=?",new Object[] {obj.get("MTART")});
						}
						try {
							boolean active=true;
							if(obj.get("STATUS")!=null) {
								active=obj.get("STATUS").toString().equalsIgnoreCase("B")?false:true;
							}
							Map<String,Object> isInserted=crm.getSingleResult("select product_part_code from products where product_part_code=?",new Object[] {obj.get("MATNR")});
							
							String description = obj.get("MAKTX")==null?"":obj.get("MAKTX").toString();
							description = description.replaceAll("\"","");
							
							if(isInserted==null) {
								crm.executeUpdate("insert into products(product_part_code,createddate,modified_date,pdoduct_description,product_price,product_type,synced,sub_product_family_product_family_sub_product_id,active) values(?,?,?,?,?,?,?,?,?)",new Object[] {obj.get("MATNR"),new Date(),new Date(),description,obj.get("VERPR"),productType.get("type_name"),true,subProduct.get("product_family_sub_product_id"),active});
								crm.executeUpdate("insert into global_search_index(key_name,object_details_url,object_id_name,object_id_value,object_name,search_string) values(?,?,?,?,?,?)",new Object[] {"productPartCode","products/"+URLEncoder.encode(obj.get("MATNR").toString(),"UTF-8"),"productPartCode",obj.get("MATNR"),"Products",obj.get("MATNR")});
							}
							else {
								crm.executeUpdate("update products set modified_date=?,pdoduct_description=?,product_price=?,product_type=?,synced=?,sub_product_family_product_family_sub_product_id=?,active=? where product_part_code=?",new Object[]{new Date(),description,obj.get("VERPR"),productType.get("type_name"),true,subProduct.get("product_family_sub_product_id"),active,obj.get("MATNR")});
							}
							sap.executeUpdate("update CRM_PROD_MASTER set CRM_DOCNO=?,TFLAG=? where MATNR=?", new Object[] {obj.get("MATNR"),"Y",obj.get("MATNR")});
							
							
						}catch(Exception ext) {
							
							throw ext;
							
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
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void synkProductMasterCHANGE() throws Exception {
	
		SqlHelper _sap=new SqlHelper("SAP");
		try {
			List<Map<String,Object>> _productMaster=_sap.getResultSet("select MATNR,P_FAMILY,PS_FAMILY,MAKTX,MTART,MEINS,ISNULL(STATUS,0) AS ACTIVE,ISNULL(CRM_DOCNO,'') AS CRM_DOCNO,SFLAG,TFLAG,SYDAT,SYTIM,cast(VERPR as decimal(10,2)) as VERPR from CRM_PROD_MASTER where TFLAG='N' and SFLAG='CH'");
			if(_productMaster!=null&&_productMaster.size()>0) {
				for(Map<String,Object> obj:_productMaster) {
					SqlHelper sap=new SqlHelper("SAP");
					SqlHelper crm=new SqlHelper("CRM");
					sap.beginTransaction();
					crm.beginTransaction();
					try {
						
						Map<String,Object> productfamily=crm.getSingleResult("select * from product_family where product_family_name=?",new Object[] { obj.get("P_FAMILY")});
								
						if(productfamily==null) {
							crm.executeUpdate("insert into product_family(product_family_name,synced) values (?,?)",new Object[]{ obj.get("P_FAMILY"),false});
							productfamily=crm.getSingleResult("select * from product_family where product_family_name=?",new Object[] {obj.get("P_FAMILY")});
						}
						Map<String,Object> subProduct=crm.getSingleResult("select * from product_family_sub_product where product_family_product_family_id=? and product_faily_sub_product_name=?",new Object[] {productfamily.get("product_family_id"),obj.get("PS_FAMILY")});
						
						if(subProduct==null) {
							crm.executeUpdate("insert into product_family_sub_product(product_faily_sub_product_name,synced,product_family_product_family_id) values(?,?,?)",new Object[] {obj.get("PS_FAMILY"),true,productfamily.get("product_family_id")});
							subProduct=crm.getSingleResult("select * from product_family_sub_product where product_family_product_family_id=? and product_faily_sub_product_name=?",new Object[] {productfamily.get("product_family_id"),obj.get("PS_FAMILY")});
						}
						Map<String,Object> productType=crm.getSingleResult("select * from product_type where type_name=?",new Object[] { obj.get("MTART")});
						
						
						if(productType==null) {
							crm.executeUpdate("insert into product_type(synced,type_name) values(?,?)",new Object[] {true,obj.get("MTART")});
							productType=crm.getSingleResult("select * from product_type where type_name=?",new Object[] {obj.get("MTART")});
						}
						try {
							boolean active=true;
							if(obj.get("STATUS")!=null) {
								active=obj.get("STATUS").toString().equalsIgnoreCase("B")?false:true;
							}
							Map<String,Object> isInserted=crm.getSingleResult("select * from products where product_part_code=?",new Object[] {obj.get("MATNR")});
							
							String descrition = obj.get("MAKTX")==null?"":obj.get("MAKTX").toString();
							descrition =  descrition.replaceAll("\"", "");
							
							if(isInserted==null) {
								crm.executeUpdate("update products set modified_date=?,pdoduct_description=?,product_price=?,product_type=?,synced=?,sub_product_family_product_family_sub_product_id=?,active=? where product_part_code=?",new Object[] {new Date(),descrition,obj.get("VERPR"),productType.get("type_name"),true,subProduct.get("product_family_sub_product_id"),active,obj.get("MATNR"),});
								//crm.executeUpdate("insert into global_search_index(key_name,object_details_url,object_id_name,object_id_value,object_name,search_string) values(?,?,?,?,?,?)",new Object[] {"productPartCode","products/"+obj.get("MATNR"),"productPartCode",obj.get("MATNR"),"Products",obj.get("MATNR")});
							}
							else {
								crm.executeUpdate("update products set modified_date=?,pdoduct_description=?,product_price=?,product_type=?,synced=?,sub_product_family_product_family_sub_product_id=?,active=? where product_part_code=?",new Object[]{new Date(),descrition,obj.get("VERPR"),productType.get("type_name"),true,subProduct.get("product_family_sub_product_id"),active,obj.get("MATNR")});
							}

							if(isInserted!=null && !isInserted.get("product_type").toString().equalsIgnoreCase(productType.get("type_name").toString())){
								crm.executeUpdate("insert into object_log(object_id,object_name,old_value,value,created_date,key_name,logged_by_email_id) value(?,?,?,?,?,?,?)", new Object[]{
									obj.get("MATNR"),
									"Products",
									isInserted.get("product_type"),
									productType.get("type_name"),
									new Date(),
									"productType",
									this.user
								});
							}

							sap.executeUpdate("update CRM_PROD_MASTER set CRM_DOCNO=?,TFLAG=? where MATNR=?", new Object[] {obj.get("MATNR"),"Y",obj.get("MATNR")});
							

							
						}catch(Exception ext) {
							
							throw ext;
							
						}
						
						crm.commit();
						sap.commit();
					}catch(Exception ex) {
						ex.printStackTrace();
						sap.rollBack();
						crm.rollBack();
					}
					crm=null;
					sap=null;
				}
			}
			_productMaster=null;
			_sap=null;
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
