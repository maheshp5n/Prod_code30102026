package com.psl.inegrator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

public class ProductRequest implements Runnable {

	String user;

	public ProductRequest() {
		this.user = App.user;
	}

	public void run() {
		System.out.println("Product request started");
		// while(true) {
		try {
			synkRequestToPlant();
		} catch (Exception ex) {
		}

		try {
			receiveFromPlat();
		} catch (Exception ex) {
		}

		try {
			updateReceivingStatus();
		} catch (Exception ex) {

		}

		// try {
		// Thread.sleep(1000*60);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
	}

	private void synkRequestToPlant() {
		SqlHelper _crm = new SqlHelper("CRM");
		SqlHelper crm = new SqlHelper("CRM");
		SqlHelper sap = new SqlHelper("SAP");
		try {
			crm.beginTransaction();
			sap.beginTransaction();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat timeformat = new SimpleDateFormat("HHmmss");

			List<Map<String, Object>> request = _crm
					.getResultSet("SELECT req.`location_product_request_id` AS req_id,\r\n" +
							"	req.`location_product_request_manual_id` AS manual_id,\r\n" +
							// " req.location_product_request_id as req_id,\r\n"+
							"	loc.plant AS splant,\r\n" +
							" 	dloc.plant as dplant,\r\n" +
							"	req.`need_by_date_time` AS need_by,\r\n" +
							"	litem.`quantity` AS req_quty,\r\n" +
							"	litem.`product_product_part_code` AS part_code,\r\n" +
							"	emp.`first_name` AS first_name,\r\n" +
							"	emp.`middle_name` AS middle_name,\r\n" +
							"	req.`created_date` AS created_date,\r\n" +
							"	litem.`location_product_request_line_item_id` as line_item_id,\r\n" +
							" 	req.`description`,\r\n" +
							"	dloc.location_name as dlocation_name,\r\n" +
							"	dloc.location_id as dlocation_id,\r\n" +
							"	req.`source_location_location_id` as sLocationId,\r\n" +
							"	emp.`last_name` AS last_name\r\n" +
							"	\r\n" +
							"	FROM `lproduct_request_line_item` litem \r\n" +
							"	JOIN `lproduct_request` req ON litem.`location_product_request_location_product_request_id`=req.`location_product_request_id`\r\n"
							+
							"	JOIN `location` loc ON loc.`location_id`=req.`source_location_location_id` \r\n" +
							"	JOIN `location_type` ltype ON ltype.`location_type_id`=loc.`location_type_location_type_id`\r\n"
							+
							"	JOIN `employee_details` emp ON emp.`email_id`=req.`owner_details_email_id` \r\n" +
							"  	JOIN `location` dloc on dloc.`location_id`=req.destination_location_location_id\r\n" +
							"	WHERE loc.location_type_location_type_id in (1,4) and dloc.location_type_location_type_id in (1,4) AND litem.`synked`=FALSE and req.location_product_request_status not in ('Draft','Submitted BY SCH')");
			for (Map<String, Object> re : request) {
				String rsdt = "";
				if (re.get("need_by") != null) {
					rsdt = format.format((Date) re.get("need_by"));
				}
				String bdtr = format.format((Date) re.get("created_date"));
				String sydt = format.format(new Date());
				String sytim = timeformat.format(new Date());

				if (re.get("dplant") == null || re.get("splant") == null)
					continue;

				sap.executeUpdate(
						"insert into CRM_STK_REQUEST(CRM_DOCNO,RSDAT,WEMPF,WERKS,MATNR,ERFMG,BDTER,SFLAG,TFLAG,SYDAT,SYTIM,CRM_LITM,REMARK,WERKS_R,SLOC,KOSTL) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'null')",
						new Object[] {
								re.get("manual_id"),
								rsdt,
								String.format("%s %s %s", re.get("first_name"), re.get("middle_name"),
										re.get("last_name")),
								re.get("splant"),
								re.get("part_code"),
								re.get("req_quty"),
								bdtr,
								"CR",
								"N",
								sydt,
								sytim,
								re.get("line_item_id"),
								re.get("description"),
								re.get("splant").equals("4210") ? "4220" : re.get("dplant"),
								re.get("sLocationId")
						});
				crm.executeUpdate(
						"update lproduct_request_line_item set synked=? where location_product_request_line_item_id=?",
						new Object[] { true, re.get("line_item_id") });
				crm.executeUpdate("update lproduct_request set synked=? where location_product_request_id=?",
						new Object[] { true, re.get("req_id") });
			}
			crm.commit();
			sap.commit();
		} catch (Exception e) {
			sap.rollBack();
			crm.rollBack();
			e.printStackTrace();
		}
	}

	private void receiveFromPlat() {

		System.out.println("from plant started");

		SqlHelper crm = new SqlHelper("CRM");
		SqlHelper sap = new SqlHelper("SAP");
		try {
			crm.beginTransaction();
			sap.beginTransaction();
			List<Map<String, Object>> req = sap.getResultSet(
					"select CRM_DOCNO,EBELN,MBLNR,count(*) as no_of_element from CRM_STK_TRANSFER where SFLAG='CR' AND TFLAG='N' group by CRM_DOCNO,EBELN,MBLNR");
			for (Map<String, Object> obj : req) {
				try {
					Date todate = new Date();
					Map<String, Object> sreq = crm.getSingleResult(
							"select * from lproduct_request where location_product_request_manual_id=?",
							new Object[] { obj.get("CRM_DOCNO") });
					if (sreq == null)
						continue;
					List<Map<String, Object>> titems = sap.getResultSet(
							"select MATNR,count(*) as qty from CRM_STK_TRANSFER where CRM_DOCNO=? and EBELN=? and SFLAG='CR' AND TFLAG='N' AND SERNR !='NULL' and MBLNR=? group by MATNR",
							new Object[] { obj.get("CRM_DOCNO"), obj.get("EBELN"), obj.get("MBLNR") });
					List<Map<String, Object>> nonSerial = sap.getResultSet(
							"select MATNR,cast(ERFMG as DECIMAL(10,3)) as qty from CRM_STK_TRANSFER where CRM_DOCNO=? and EBELN=? and SFLAG='CR' AND TFLAG='N' AND SERNR ='NULL' and MBLNR=?",
							new Object[] { obj.get("CRM_DOCNO"), obj.get("EBELN"), obj.get("MBLNR") });
					AutoKeyNumber keyHolder = new AutoKeyNumber();
					Map<String, Object> invDetails = sap.getSingleResult("select * from CRM_STK_HDR where MBLNR=?",
							new Object[] { obj.get("MBLNR") });
					crm.executeUpdate(
							"insert into lptrasfer(actual_delivery_date,created_date,modified_date,shipping_mode,shipping_to_name,tracking_number,created_by_email_id,location_product_request_location_product_request_id,modified_by_email_id,owner_email_id,invoice,mblnr_g,mjahr_g) values(?,?,?,?,?,?,?,?,?,?,?,?,?)",
							new Object[] {
									todate,
									todate,
									todate,
									"SAP",
									"",
									"SAP",
									user,
									sreq.get("location_product_request_id"),
									user,
									sreq.get("owner_details_email_id"),
									invDetails.get("INVOICE_NO"),
									invDetails.get("MBLNR_G"),
									invDetails.get("MJAHR_G")
							}, keyHolder);

					long tId = keyHolder.getKey().longValue();
					String mnId = String.format("PT-%s", App.fillZero(String.valueOf(tId), 5));
					crm.executeUpdate(
							"update lptrasfer set location_product_transfer_manual_id=? where location_product_transfer_id=?",
							new Object[] {
									mnId,
									tId
							});

					sap.executeUpdate(
							"update CRM_STK_TRANSFER set CRM_TRN_DOC_NO=? where CRM_DOCNO=? and EBELN=? AND TFLAG='N' and MBLNR = ?",
							new Object[] { mnId, obj.get("CRM_DOCNO"), obj.get("EBELN"), obj.get("MBLNR") });

					float totalQIssued = 0;
					for (Map<String, Object> titem : titems) {
						AutoKeyNumber k = new AutoKeyNumber();
						Map<String, Object> req_item = crm.getSingleResult(
								"select * from lproduct_request_line_item where location_product_request_location_product_request_id=? and product_product_part_code=?",
								new Object[] {
										sreq.get("location_product_request_id"),
										titem.get("MATNR")
								});
						crm.executeUpdate(
								"insert into lptransfer_item(created_date,modified_date,received_quantity,transfer_quantity,created_by_email_id,lp_request_line_item_location_product_request_line_item_id,lp_tranfer_location_product_transfer_id,modified_by_email_id,owner_email_id,product_product_part_code,synked) values(?,?,?,?,?,?,?,?,?,?,true)",
								new Object[] {
										new Date(),
										new Date(),
										0,
										(Float.parseFloat(titem.get("qty").toString())),
										user,
										req_item.get("location_product_request_line_item_id"),
										tId,
										user,
										sreq.get("owner_details_email_id"),
										titem.get("MATNR")
								}, k);
						crm.executeUpdate(
								"update lptransfer_item set lp_transfer_item_id_manual_id=? where lp_transfer_item_id=?",
								new Object[] {
										String.format("PTI-%s", App.fillZero(String.valueOf((long) k.getKey()), 5)),
										k.getKey().longValue()
								});
						float totalIQutyIssued = Float.parseFloat(req_item.get("quantity_issued").toString())
								+ (Float.parseFloat(titem.get("qty").toString()));
						totalQIssued += totalIQutyIssued;
						crm.executeUpdate(
								"update lproduct_request_line_item set quantity_issued=? where location_product_request_line_item_id=?",
								new Object[] { totalIQutyIssued,
										req_item.get("location_product_request_line_item_id") });

						List<Map<String, Object>> lineitems = sap.getResultSet(
								"select SERNR from CRM_STK_TRANSFER where CRM_DOCNO=? and MATNR=? and EBELN=? and SFLAG='CR' AND TFLAG='N' and MBLNR=?",
								new Object[] { obj.get("CRM_DOCNO"), titem.get("MATNR"), obj.get("EBELN"),
										obj.get("MBLNR") });
						for (Map<String, Object> litem : lineitems) {
							if (litem.get("SERNR") != null && !litem.get("SERNR").toString().equalsIgnoreCase("NULL")) {

								AutoKeyNumber lKey = new AutoKeyNumber();
								crm.executeUpdate(
										"insert into lptransfer_line_item(created_date,modified_date,product_serial_number,product_status,quantity_received,quantity_transfer,received,created_by_email_id,modified_by_email_id,owner_email_id,transfer_item_lp_transfer_item_id,synked) values(?,?,?,?,?,?,?,?,?,?,?,true)",
										new Object[] {
												new Date(),
												new Date(),
												litem.get("SERNR"),
												"IN-Transit",
												0,
												1,
												false,
												user,
												user,
												sreq.get("owner_details_email_id"),
												k.getKey().longValue()
										}, lKey);
								Map<String, Object> productLineItem = null;
								try {
									productLineItem = crm.getSingleResult(
											"select * from product_line_item where product_product_part_code=? and serial_number=?",
											new Object[] {
													titem.get("MATNR"),
													litem.get("SERNR")
											});

									System.out.println("DUPLICATE SERIAL NUMBER FOUND FOR PART CODE "
											+ titem.get("MATNR") + ", " + litem.get("SERNR"));

								} catch (Exception exp) {
									exp.printStackTrace();
								}
								if (productLineItem != null) {
									crm.executeUpdate("insert into product_line_item(" +
											"created_date," +
											"modified_date," +
											"position," +
											"position_state," +
											"serial_number," +
											"created_by_email_id," +
											"modified_by_email_id," +
											"product_product_part_code," +
											"state_product_state_id," +
											"status_status_id," +
											"customer_inventory," +
											"refurbished)" +
											" values(?,?,?,?,?,?,?,?,?,?,?,?)",
											new Object[] {
													new Date(),
													new Date(),
													("PT," + tId),
													"IN_TRANSIT",
													litem.get("SERNR"),
													user,
													user,
													titem.get("MATNR"),
													"1",
													"2",
													false,
													false
											});
								}
								crm.executeUpdate(
										"update lptransfer_line_item set lp_transfer_lineitem_id_manual_id=? where lp_transfer_lineitem_id=?",
										new Object[] {
												String.format("PTLI-%s",
														App.fillZero(String.valueOf(lKey.getKey().longValue()), 5)),
												lKey.getKey().longValue()
										});
							}
						}
						sap.executeUpdate(
								"update CRM_STK_TRANSFER set TFLAG='Y' where CRM_DOCNO=? and MATNR=? and EBELN=? and SFLAG='CR' AND TFLAG='N' and MBLNR=?",
								new Object[] { obj.get("CRM_DOCNO"), titem.get("MATNR"), obj.get("EBELN"),
										obj.get("MBLNR") });
					}
					for (Map<String, Object> titem : nonSerial) {
						AutoKeyNumber k = new AutoKeyNumber();
						Map<String, Object> req_item = crm.getSingleResult(
								"select * from lproduct_request_line_item where location_product_request_location_product_request_id=? and product_product_part_code=?",
								new Object[] {
										sreq.get("location_product_request_id"),
										titem.get("MATNR")
								});
						crm.executeUpdate(
								"insert into lptransfer_item(created_date,modified_date,received_quantity,transfer_quantity,created_by_email_id,lp_request_line_item_location_product_request_line_item_id,lp_tranfer_location_product_transfer_id,modified_by_email_id,owner_email_id,product_product_part_code,synked) values(?,?,?,?,?,?,?,?,?,?,true)",
								new Object[] {
										new Date(),
										new Date(),
										0,
										(Float.parseFloat(titem.get("qty").toString())),
										user,
										req_item.get("location_product_request_line_item_id"),
										tId,
										user,
										sreq.get("owner_details_email_id"),
										titem.get("MATNR")
								}, k);
						crm.executeUpdate(
								"update lptransfer_item set lp_transfer_item_id_manual_id=? where lp_transfer_item_id=?",
								new Object[] {
										String.format("PTI-%s", App.fillZero(String.valueOf((long) k.getKey()), 5)),
										k.getKey().longValue()
								});
						float totalIQutyIssued = Float.parseFloat(req_item.get("quantity_issued").toString())
								+ (Float.parseFloat(titem.get("qty").toString()));
						totalQIssued += totalIQutyIssued;
						crm.executeUpdate(
								"update lproduct_request_line_item set quantity_issued=? where location_product_request_line_item_id=?",
								new Object[] { totalIQutyIssued,
										req_item.get("location_product_request_line_item_id") });
						sap.executeUpdate(
								"update CRM_STK_TRANSFER set TFLAG='Y' where CRM_DOCNO=? and MATNR=? and EBELN=? and SFLAG='CR' AND TFLAG='N' and MBLNR=?",
								new Object[] { obj.get("CRM_DOCNO"), titem.get("MATNR"), obj.get("EBELN"),
										obj.get("MBLNR") });
					}

					// UPDATE OVERALL QUANTITY

					List<Map<String, Object>> qtyUpdate = crm.getResultSet(
							"SELECT ifnull(sum(quantity_issued),0) as smt FROM lproduct_request_line_item where location_product_request_location_product_request_id =?",
							new Object[] { sreq.get("location_product_request_id") });

					crm.executeUpdate(
							"update lproduct_request set total_quantity_issued=? where location_product_request_id=?",
							new Object[] {
									qtyUpdate.get(0).get("smt"),
									sreq.get("location_product_request_id")
							});

				} catch (Exception exInner) {

					throw exInner;

				}
			}

			crm.commit();
			sap.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			sap.rollBack();
			crm.rollBack();
		}

	}

	private void updateReceivingStatus() {
		SqlHelper sap = new SqlHelper("SAP");
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		try {
			List<Map<String, Object>> pendingListToUpdate = sap.getResultSet(
					"select distinct trn.CRM_TRN_DOC_NO,trn.CRM_DOCNO,hdr.MBLNR,hdr.EBELN from CRM_STK_HDR hdr join CRM_STK_TRANSFER trn on hdr.MBLNR=trn.MBLNR and hdr.CRM_DOCNO=trn.CRM_DOCNO where hdr.CRM_DOCDATE is null or hdr.CRM_DOCDATE=''");
			for (Map<String, Object> obj : pendingListToUpdate) {
				SqlHelper _sap = new SqlHelper("SAP");
				SqlHelper _crm = new SqlHelper("CRM");
				try {
					_sap.beginTransaction();
					_crm.beginTransaction();
					if (obj.get("CRM_TRN_DOC_NO") != null) {
						List<Map<String, Object>> trn = _crm.getResultSet(
								"SELECT `received_date` FROM `lptrasfer` WHERE `location_product_transfer_manual_id`=?",
								new Object[] { obj.get("CRM_TRN_DOC_NO") });
						if (trn.size() > 0) {
							if (trn.get(0).get("received_date") != null) {
								String sm = format.format(trn.get(0).get("received_date"));
								_sap.executeUpdate(
										"update CRM_STK_HDR set CRM_DOCDATE=?,GFLAG='N' where MBLNR=? and CRM_DOCNO=? and (GFLAG is null or GFLAG !='Y')",
										new Object[] { sm, obj.get("MBLNR"), obj.get("CRM_DOCNO") });
							}
						}
					}
					_crm.commit();
					_sap.commit();
				} catch (Exception ex) {
					ex.printStackTrace();
					_sap.rollBack();
					_crm.rollBack();
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	Map<String, Object> getWarehouse(long locationId, SqlHelper helper) {
		try {
			Map<String, Object> warehouse = helper.getSingleResult("select * from location where location_id=?",
					new Object[] { locationId });
			if (warehouse.get("location_type_location_type_id").toString().equalsIgnoreCase("1"))
				return warehouse;
			else
				return getWarehouse(Long.parseLong(warehouse.get("parent_location_location_id").toString()), helper);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

	Map<String, Object> getRootPlant(long locationid, SqlHelper helper) {
		try {
			Map<String, Object> warehouse = helper.getSingleResult("select * from location where location_id=?",
					new Object[] { locationid });
			if (warehouse.get("location_type_location_type_id").toString().equalsIgnoreCase("4"))
				return warehouse;
			else
				return getRootPlant(Long.parseLong(warehouse.get("parent_location_location_id").toString()), helper);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}
}