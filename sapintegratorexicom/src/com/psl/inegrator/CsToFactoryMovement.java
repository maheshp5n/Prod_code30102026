package com.psl.inegrator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CsToFactoryMovement implements Runnable{
	private String headerString="<html>\r\n" + 
			"\r\n" + 
			"<head>\r\n" + 
			"<meta http-equiv=Content-Type content=\"text/html; charset=windows-1252\">\r\n" + 
			"<meta name=Generator content=\"Microsoft Word 15 (filtered)\">\r\n" + 
			"<style>\r\n" + 
			"<!--\r\n" + 
			" /* Font Definitions */\r\n" + 
			" @font-face\r\n" + 
			"	{font-family:\"Cambria Math\";\r\n" + 
			"	panose-1:2 4 5 3 5 4 6 3 2 4;}\r\n" + 
			"@font-face\r\n" + 
			"	{font-family:Calibri;\r\n" + 
			"	panose-1:2 15 5 2 2 2 4 3 2 4;}\r\n" + 
			" /* Style Definitions */\r\n" + 
			" p.MsoNormal, li.MsoNormal, div.MsoNormal\r\n" + 
			"	{margin:0cm;\r\n" + 
			"	font-size:11.0pt;\r\n" + 
			"	font-family:\"Calibri\",sans-serif;}\r\n" + 
			".MsoChpDefault\r\n" + 
			"	{font-family:\"Calibri\",sans-serif;}\r\n" + 
			".MsoPapDefault\r\n" + 
			"	{margin-bottom:8.0pt;\r\n" + 
			"	line-height:107%;}\r\n" + 
			"@page WordSection1\r\n" + 
			"	{size:595.3pt 841.9pt;\r\n" + 
			"	margin:72.0pt 72.0pt 72.0pt 72.0pt;}\r\n" + 
			"div.WordSection1\r\n" + 
			"	{page:WordSection1;}\r\n" + 
			"-->\r\n" + 
			"</style>\r\n" + 
			"\r\n" + 
			"</head>\r\n" + 
			"\r\n" + 
			"<body lang=EN-IN style='word-wrap:break-word'>\r\n" + 
			"\r\n" + 
			"<div class=WordSection1>\r\n" + 
			"\r\n" + 
			"<p class=MsoNormal><span style='color:#1F497D'>&nbsp;</span></p>\r\n" + 
			"\r\n" + 
			"<p class=MsoNormal><span style='color:#1F497D'>Dear User,</span></p>\r\n" + 
			"\r\n" + 
			"<p class=MsoNormal><span style='color:#1F497D'>&nbsp;</span></p>\r\n" + 
			"\r\n" + 
			"<p class=MsoNormal><span style='color:#1F497D'>This is to inform you that Spare\r\n" + 
			"sales adjustment executed in SAP for customer billing and under update in Pulse\r\n" + 
			"(CRM). The Updation will take 30 min to sync records in SAP and CRM.</span></p>\r\n" + 
			"\r\n" + 
			"<p class=MsoNormal><span style='color:#1F497D'>&nbsp;</span></p>\r\n" + 
			"\r\n" + 
			"<p class=MsoNormal><span style='color:#1F497D'>Note: Please do not raise any\r\n" + 
			"Product Transfer Request in CRM for next 30 min. This will impact on inventory\r\n" + 
			"mismatch if any PT/RO initiated during this interval. </span></p>\r\n" + 
			"\r\n" + 
			"<p class=MsoNormal><span style='color:#1F497D'>&nbsp;</span></p>\r\n" + 
			"\r\n" + 
			"<p class=MsoNormal><span style='color:#1F497D'>Following are the invoice\r\n" + 
			"details for records purpose.</span></p>\r\n" + 
			"\r\n" + 
			"<p class=MsoNormal><span style='color:#1F497D'>&nbsp;</span></p>\r\n" + 
			"\r\n" + 
			"<table class=MsoNormalTable border=0 cellspacing=0 cellpadding=0 width=793\r\n" + 
			" style='width:595.1pt;border-collapse:collapse'>\r\n" + 
			" <tr style='height:15.0pt'>\r\n" + 
			"  <td width=121 nowrap style='width:91.0pt;border:solid windowtext 1.0pt;\r\n" + 
			"  background:#D9D9D9;padding:0cm 5.4pt 0cm 5.4pt;height:15.0pt'>\r\n" + 
			"  <p class=MsoNormal><b><span style='color:black'>Customer Name</span></b></p>\r\n" + 
			"  </td>\r\n" + 
			"  <td width=121 nowrap style='width:91.0pt;border:solid windowtext 1.0pt;\r\n" + 
			"  border-left:none;background:#D9D9D9;padding:0cm 5.4pt 0cm 5.4pt;height:15.0pt'>\r\n" + 
			"  <p class=MsoNormal><b><span style='color:black'>Part Code</span></b></p>\r\n" + 
			"  </td>\r\n" + 
			"  <td width=121 nowrap style='width:91.0pt;border:solid windowtext 1.0pt;\r\n" + 
			"  border-left:none;background:#D9D9D9;padding:0cm 5.4pt 0cm 5.4pt;height:15.0pt'>\r\n" + 
			"  <p class=MsoNormal><b><span style='color:black'>SAP Invoice No.</span></b></p>\r\n" + 
			"  </td>\r\n" + 
			"  <td width=121 nowrap style='width:91.0pt;border:solid windowtext 1.0pt;\r\n" + 
			"  border-left:none;background:#D9D9D9;padding:0cm 5.4pt 0cm 5.4pt;height:15.0pt'>\r\n" + 
			"  <p class=MsoNormal><b><span style='color:black'>SAP Invoice Date</span></b></p>\r\n" + 
			"  </td>\r\n" + 
			"  <td width=121 nowrap style='width:91.0pt;border:solid windowtext 1.0pt;\r\n" + 
			"  border-left:none;background:#D9D9D9;padding:0cm 5.4pt 0cm 5.4pt;height:15.0pt'>\r\n" + 
			"  <p class=MsoNormal><b><span style='color:black'>Qty.</span></b></p>\r\n" + 
			"  </td>\r\n" + 
			"  <td width=187 nowrap style='width:140.1pt;border:solid windowtext 1.0pt;\r\n" + 
			"  border-left:none;background:#D9D9D9;padding:0cm 5.4pt 0cm 5.4pt;height:15.0pt'>\r\n" + 
			"  <p class=MsoNormal><b><span style='color:black'>Process Date &amp; Time</span></b></p>\r\n" + 
			"  </td>\r\n" + 
			" </tr>";
	private String footerString="</table>\r\n" + 
			"\r\n" + 
			"<p class=MsoNormal><span style='color:#1F497D'>&nbsp;</span></p>\r\n" + 
			"\r\n" + 
			"<p class=MsoNormal><span style='color:#1F497D'>&nbsp;</span></p>\r\n" + 
			"\r\n" + 
			"<p class=MsoNormal><span style='color:#1F497D'>This is system generated mail.\r\n" + 
			"Please do not reply.</span></p>\r\n" + 
			"\r\n" + 
			"<p class=MsoNormal><span style='color:#1F497D'>&nbsp;</span></p>\r\n" + 
			"\r\n" + 
			"<p class=MsoNormal><span style='color:#1F497D'>Thanks.</span></p>\r\n" + 
			"\r\n" + 
			"<p class=MsoNormal><span style='color:#1F497D'>Pulse Support</span></p>\r\n" + 
			"\r\n" + 
			"<p class=MsoNormal><span style='color:#1F497D'>&nbsp;</span></p>\r\n" + 
			"\r\n" + 
			"<p class=MsoNormal><span style='color:#1F497D'>&nbsp;</span></p>\r\n" + 
			"\r\n" + 
			"<p class=MsoNormal>&nbsp;</p>\r\n" + 
			"\r\n" + 
			"</div>\r\n" + 
			"\r\n" + 
			"</body>\r\n" + 
			"\r\n" + 
			"</html>";

	@Override
	public void run() {
		
		synkSalesDaata();
	}
	
	private void synkSalesDaata() {
		
		SqlHelper _sap=new SqlHelper("SAP");
		
		Map<String,List<Map<String,Object>>> sldata=new HashMap<>();
		try {
			
			List<Map<String,Object>> _sapSales=_sap.getResultSet("select * from CRM_STK_CS_FACT where SFLAG='CR' AND TFLAG='N'");
			if(_sapSales!=null&&_sapSales.size()>0) {
				for(Map<String,Object> d:_sapSales) {
					
					SqlHelper sap=new SqlHelper("SAP");
					SqlHelper crm=new SqlHelper("CRM");
					
					try {
						sap.beginTransaction();
						crm.beginTransaction();
						
						Map<String,Object> product=crm.getSingleResult("select * from products where product_part_code=?",new Object[]{d.get("MATNR")});
						Map<String,Object> sLocation=crm.getSingleResult("select * from location where location_id=?",new Object[] {d.get("LGORT")});
						if(sLocation==null)
							throw new Exception("Sales Location Not found");
						
						
						AutoKeyNumber key=new AutoKeyNumber();
						crm.executeUpdate("insert into cs_to_factory_movement(bstdk,bstkd,invoice,invoice_date,kunnr,lfimg,lgort,matnr,mblnr,mjahr,remark,sernr,sflag,skunnr,sydat,sytim,tflag,vbeln,wadat,werks,posnr,bwartwa) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
								d.get("BSTDK"),
								d.get("BSTKD"),
								d.get("INVOICE"),
								d.get("INVOICE_DATE"),
								d.get("KUNNR"),
								d.get("LFIMG"),
								d.get("LGORT"),
								d.get("MATNR"),
								d.get("MBLNR"),
								d.get("MJAHR"),
								d.get("REMARK"),
								d.get("SERNR"),
								d.get("SFLAG"),
								d.get("SKUNNR"),
								d.get("SYDAT"),
								d.get("SYTIM"),
								"Y",
								d.get("VBELN"),
								d.get("WADAT"),
								d.get("WERKS"),
								d.get("POSNR"),
								d.get("BWARTWA")
						},key);
						
						
						if(product.get("product_type").toString().equalsIgnoreCase("Component")) {
							
							System.out.println("Product part code "+product.get("product_part_code")+" is component");
							
							Map<String,Object> stock=crm.getSingleResult("SELECT * FROM location_product_item WHERE location_location_id=? AND product_product_part_code=?",new Object[] {sLocation.get("location_id"),product.get("product_part_code")});
							
							
							float okQty= Float.parseFloat(stock.get("ok_quantity").toString());
							float inHandQty=Float.parseFloat(stock.get("quantity_in_on_hand").toString());
							
							
							
							float salesQty=Float.parseFloat(d.get("LFIMG").toString());
							
							System.out.println("Sales Quantity is "+salesQty+" existing ok qty "+okQty+" inHandQty "+inHandQty);
							okQty=okQty-salesQty;
							inHandQty=inHandQty-salesQty;
							
							System.out.println("After operation okqty "+okQty+" inHandQty "+inHandQty);
							
							if(okQty<0)
								throw new Exception("Stock not found for product "+product.get("product_part_code")+" at location id"+sLocation.get("location_id"));
							
							crm.executeUpdate("update location_product_item set ok_quantity=?, quantity_in_on_hand=? where id=?",new Object[] {
									okQty,
									inHandQty,
									stock.get("id")
							});
							
							
							//crm.executeUpdate("insert into tem_sales_insert(stok_id,ok_qty,in_hand_qty) values(?,?,?)", new Object[] {stock.get("id"),okQty,inHandQty});
						}
						
						else {
							
							System.out.print("Product part code "+product.get("product_part_code")+" is Sub products");
							
							List<Map<String,Object>> stocks=crm.getResultSet("SELECT item.id AS itemid,litem.id AS litemid,litem.`item_serial_number`,item.`ok_quantity`,item.`quantity_in_on_hand`,item.product_product_part_code,item.location_location_id FROM `location_product_line_items` litem JOIN `location_product_item` item ON item.id=litem.`location_product_item_id` WHERE litem.`customer_inventory`=FALSE AND litem.`location_proeuct_line_item_state_product_state_id`=1 AND item.product_product_part_code=? AND item.location_location_id=? limit ?",new Object[] {product.get("product_part_code"),sLocation.get("location_id"),(int)Float.parseFloat(d.get("LFIMG").toString())});
							float salesQty=Float.parseFloat(d.get("LFIMG").toString());
							if(stocks==null||stocks.size()!=salesQty)
								throw new Exception("Ok Serial number not found as required for partcode "+product.get("product_part_code")+" at location id "+sLocation.get("location_id"));
							
							float okQty= Float.parseFloat(stocks.get(0).get("ok_quantity").toString());
							float inHandQty=Float.parseFloat(stocks.get(0).get("quantity_in_on_hand").toString());
							
							System.out.println("Sales Quantity is "+salesQty+" existing ok qty "+okQty+" inHandQty "+inHandQty);
							
							okQty=okQty-salesQty;
							inHandQty=inHandQty-salesQty;
							
							System.out.println("After operation okqty "+okQty+" inHandQty "+inHandQty);
							
							if(okQty<0)
								throw new Exception("Stock not found for product "+product.get("product_part_code")+" at location id "+sLocation.get("location_id"));
							
							crm.executeUpdate("update location_product_item set ok_quantity=?, quantity_in_on_hand=? where id=?",new Object[] {
									okQty,
									inHandQty,
									stocks.get(0).get("itemid")
							});
							
							//crm.executeUpdate("insert into tem_sales_insert(stok_id,ok_qty,in_hand_qty) values(?,?,?)", new Object[] {stocks.get(0).get("itemid"),okQty,inHandQty});
							
							for(Map<String,Object> st:stocks) {
								crm.executeUpdate("insert into cs_to_factory_movement_serial(serial_number,sap_sales_data_id) values(?,?) ", new Object[] {st.get("item_serial_number"),key.getKey().longValue()});
								crm.executeUpdate("delete from location_product_line_items where id=?", new Object[] {st.get("litemid")});
							}
						}
						
						sap.executeUpdate("update CRM_STK_CS_FACT set TFLAG='Y',CRM_DOCNO=? where VBELN=? and MATNR=? and POSNR=?", new Object[] {key.getKey(),d.get("VBELN"),d.get("MATNR"),d.get("POSNR")});
						
						
						List<Map<String,Object>> dd=sldata.get(sLocation.get("location_id").toString());
						
						if(dd!=null) {
							System.out.println("Location alrady exists with data "+dd);
							dd.add(d);
							sldata.put(sLocation.get("location_id").toString(),dd);
						}
						else {
							dd=new ArrayList<Map<String,Object>>();
							dd.add(d);
							sldata.put(sLocation.get("location_id").toString(), dd);
						}
						
						System.out.println("Sldata exists is"+sldata);
						
						sap.commit();
						crm.commit();
					}catch(Exception ex) {
						sap.rollBack();
						crm.rollBack();
						
						String remarks=ex.getLocalizedMessage();
						ex.printStackTrace();
						if(remarks==null)
							remarks="NULL_POINTER";
						else {
							if(remarks.length()>99)
								remarks=remarks.substring(0, 99);
						}
							
						
						sap.executeUpdate("update CRM_STK_CS_FACT set SFLAG='ER',REMARK=? where VBELN=? and MATNR=? and POSNR=?", new Object[] {remarks,d.get("VBELN"),d.get("MATNR"),d.get("POSNR")});
						crm.executeUpdate("insert into cs_to_factory_movement(bstdk,bstkd,invoice,invoice_date,kunnr,lfimg,lgort,matnr,mblnr,mjahr,remark,sernr,sflag,skunnr,sydat,sytim,tflag,vbeln,wadat,werks,posnr,bwartwa) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
								d.get("BSTDK"),
								d.get("BSTKD"),
								d.get("INVOICE"),
								d.get("INVOICE_DATE"),
								d.get("KUNNR"),
								d.get("LFIMG"),
								d.get("LGORT"),
								d.get("MATNR"),
								d.get("MBLNR"),
								d.get("MJAHR"),
								remarks,
								d.get("SERNR"),
								"ER",
								d.get("SKUNNR"),
								d.get("SYDAT"),
								d.get("SYTIM"),
								"N",
								d.get("VBELN"),
								d.get("WADAT"),
								d.get("WERKS"),
								d.get("POSNR"),
								d.get("BWARTWA")
						});
					}
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sendMailtoOwner(sldata);
	}
	
	private  void sendMailtoOwner(Map<String,List<Map<String,Object>>> data) {
		System.out.println("Mail trigre start "+data);
		if(!data.isEmpty()&&data.size()>0) {
			System.out.println("Mail trigre start data is not empty");
			Iterator<String> keyset=data.keySet().iterator();
			while(keyset.hasNext()) {
				String locationId=keyset.next();
				SqlHelper crm=new SqlHelper("CRM");
				List<String> userEmailIds=null;
				
				try {
					
					List<Map<String,Object>> ser=crm.getResultSet("select user_email_id from service_recource where location_location_id=?",new Object[] {locationId});
					userEmailIds=new ArrayList<>();
					for(Map<String,Object> s:ser) {
						String mailId=s.get("user_email_id")!=null?s.get("user_email_id").toString():"";
						if(!mailId.equalsIgnoreCase(""))
							userEmailIds.add(s.get("user_email_id").toString());
					}
					
					MailHelper mailHelper=new MailHelper("CS to factory – Inventory Update Scheduler in Pulse", prepareFullBody(data.get(locationId)),userEmailIds);
					
					Thread th =new Thread(mailHelper);
					th.start();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		
	}
	private String prepareFullBody(List<Map<String,Object>> data) {
		StringBuilder sb=new StringBuilder();
		sb.append(headerString.replaceAll("\r\n",""));
		
		for(Map<String,Object> d:data) {
			sb.append(prepareDadtdaLine(d));
		}
		
		sb.append(footerString.replaceAll("\r\n", ""));
		return sb.toString();
	}
	
	private String prepareDadtdaLine(Map<String,Object> data) {
		
		SimpleDateFormat processDate=new SimpleDateFormat("dd-MM-yyyy, HH:mm:ss");
		SimpleDateFormat invoiceDateFormat=new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sapDateFormat=new SimpleDateFormat("ddMMyyyy");
		Date ivnoiceDate=null;
		String sapDate="";
		try {
			ivnoiceDate=sapDateFormat.parse(data.get("INVOICE_DATE").toString());
			sapDate=invoiceDateFormat.format(ivnoiceDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		StringBuilder sb=new StringBuilder();
		sb.append("<tr style='height:15.0pt'>\r\n" + 
				"  <td width=121 nowrap valign=bottom style='width:91.0pt;border:solid windowtext 1.0pt;\r\n" + 
				"  border-top:none;padding:0cm 5.4pt 0cm 5.4pt;height:15.0pt'>\r\n" + 
				"  <p class=MsoNormal><span style='color:black'>"+data.get("KUNNR").toString()+"</span></p>\r\n" + 
				"  </td>\r\n" + 
				"  <td width=121 nowrap valign=bottom style='width:91.0pt;border-top:none;\r\n" + 
				"  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n" + 
				"  padding:0cm 5.4pt 0cm 5.4pt;height:15.0pt'>\r\n" + 
				"  <p class=MsoNormal><span style='color:black'>"+data.get("MATNR")+"</span></p>\r\n" + 
				"  </td>\r\n" + 
				"  <td width=121 nowrap valign=bottom style='width:91.0pt;border-top:none;\r\n" + 
				"  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n" + 
				"  padding:0cm 5.4pt 0cm 5.4pt;height:15.0pt'>\r\n" + 
				"  <p class=MsoNormal><span style='color:black'>"+data.get("INVOICE").toString()+"</span></p>\r\n" + 
				"  </td>\r\n" + 
				"  <td width=121 nowrap valign=bottom style='width:91.0pt;border-top:none;\r\n" + 
				"  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n" + 
				"  padding:0cm 5.4pt 0cm 5.4pt;height:15.0pt'>\r\n" + 
				"  <p class=MsoNormal><span style='color:black'>"+sapDate+"</span></p>\r\n" + 
				"  </td>\r\n" + 
				"  <td width=121 nowrap valign=bottom style='width:91.0pt;border-top:none;\r\n" + 
				"  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n" + 
				"  padding:0cm 5.4pt 0cm 5.4pt;height:15.0pt'>\r\n" + 
				"  <p class=MsoNormal><span style='color:black'>"+data.get("LFIMG").toString()+"</span></p>\r\n" + 
				"  </td>\r\n" + 
				"  <td width=187 nowrap valign=bottom style='width:140.1pt;border-top:none;\r\n" + 
				"  border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n" + 
				"  padding:0cm 5.4pt 0cm 5.4pt;height:15.0pt'>\r\n" + 
				"  <p class=MsoNormal><span style='color:black'>"+processDate.format(new Date())+"</span></p>\r\n" + 
				"  </td>\r\n" + 
				" </tr>");
		
		return sb.toString();
	}
}
