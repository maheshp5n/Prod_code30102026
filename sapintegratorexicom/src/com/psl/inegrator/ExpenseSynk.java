package com.psl.inegrator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

public class ExpenseSynk implements Runnable {

	public void run() {
		//while (true) {
			try {
				expenseSynked();
			} catch (Exception ex) {

			}
			try {
				updateTFlag();
			} catch (Exception ex) {

			}

//			try {
//				Thread.sleep(1000 * 60);
//			} catch (InterruptedException e) {
//
//				e.printStackTrace();
//			}
		//}
	}

	private void expenseSynked() {

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat systime = new SimpleDateFormat("HHmmss");
		SqlHelper _crm = new SqlHelper("CRM");
		try {
			List<Map<String, Object>> exdata = _crm.getResultSet("SELECT \r\n" + "	'1' AS BUZEI,\r\n"
					+ "	cd.`employee_code` AS LIFNR,\r\n" + "	cd.`grade_name` AS GRADE,\r\n"
					+ "	cd.`department` AS KOSTL,\r\n" + "	lg.`legal_entity_name` AS BKTXT_O,\r\n"
					+ "	ea.`expense_management_manual_id` AS XBLNR,\r\n" + "	ea.`created_date` AS BLDAT,\r\n"
					+ "	ea.`modified_date` AS BUDAT_A,\r\n" + "	NOW() AS BUDAT,\r\n"
					+ "	 bp.`business_place_name` AS BUPLA,\r\n" + "	 ea.`advance_amount` AS WRBTR_A,\r\n"
					+ "	 ea.`net_amount_to_pay` AS WRBTR,\r\n" + "	 cd.`employee_code` AS ZUONR,\r\n"
					+ "	 SUBSTRING(ea.`narration`,1,49) AS SGTXT,\r\n" + "	 ea.`expense_management_id` AS SFDC_ID,\r\n"
					+ " 	expcost.cost_center_name as ex_cost_center,\r\n" + "	ea.is_post,\r\n"
					+ "	 'CR' AS SFLAG,\r\n" + "	 'N' AS TFLAG,\r\n" 
					+"	ea.expense_type_expense_type_id as extype\r\n"
					+ " FROM `expense_approved` ea\r\n"
					+ "	JOIN `contact_details` cd ON cd.`contact_id`=ea.`contact_contact_id`\r\n"
					+ " LEFT	JOIN `legal_entity` lg ON lg.`legal_entity_id`=cd.`legal_entity_id`\r\n"
					+ " LEFT 	JOIN expense_management_cost_center expcost on expcost.cost_center_id=ea.cost_center_cost_center_id\r\n"
					+ " LEFT	JOIN `expense_management_business_area` bra ON bra.`business_area_id`=ea.`business_area_business_area_id`\r\n"
					+ " LEFT	JOIN `expense_management_business_place` bp ON bp.`business_place_id`=ea.`business_place_business_place_id` WHERE ea.is_synced='0'\r\n"
					+ "	");
			if (exdata != null && exdata.size() > 0) {

				for (Map<String, Object> _ob : exdata) {

					if (_ob.get("XBLNR").toString().length() == 17)
						continue;

					SqlHelper crm = new SqlHelper("CRM");
					SqlHelper sap = new SqlHelper("SAP");

					try {
						crm.beginTransaction();
						sap.beginTransaction();
						String costCenter = getConstCenter(_ob.get("ex_cost_center").toString());
						String busArea = getBuisnessArea(costCenter);

						sap.executeUpdate(
								"insert into CRM_EXPENSE(BUZEI,LIFNR,GRADE,KOSTL,BKTXT_O,XBLNR,BLDAT,BUDAT_A,BUDAT,BUPLA,WRBTR_A,WRBTR,ZUONR,SGTXT,CRM_DOCNO,SFLAG,TFLAG,POST,SYDAT,SYTIM,GSBER) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
								new Object[] { _ob.get("BUZEI") == null ? "" : _ob.get("BUZEI"),
										_ob.get("LIFNR") == null ? "" : _ob.get("LIFNR"),
										_ob.get("GRADE") == null ? "" : _ob.get("GRADE"),
										getConstCenter(_ob.get("ex_cost_center").toString()), // _ob.get("KOSTL"),
										_ob.get("BKTXT_O"), _ob.get("XBLNR"), format.format(_ob.get("BLDAT")),
										format.format(_ob.get("BUDAT_A")), format.format(_ob.get("BUDAT")),
										getBupla(_ob.get("BUPLA").toString()), _ob.get("WRBTR_A"), _ob.get("WRBTR"),
										_ob.get("ZUONR"), _ob.get("SGTXT"), _ob.get("SFDC_ID"), _ob.get("SFLAG"),
										_ob.get("TFLAG"), _ob.get("is_post").equals("1") ? "Y" : "N",
										format.format(new Date()), systime.format(new Date()), busArea });

						int buzy = 2;
						// LODGIN EXPENSE

						List<Map<String, Object>> loging = crm.getResultSet("SELECT  \r\n" + "	'2' AS `BUZEI`,\r\n"
								+ "	le.`approved_amount` AS `WRBTR`,\r\n" + "	'800205' AS `HKONT`,\r\n"
								+ "	le.`amount` AS `GenralAmount`,\r\n"
								+ "	le.`amount_to_disbursed` AS `amount_to_disbursed`\r\n"
								+ "FROM expense_approved exa \r\n"
								+ "	JOIN `expense_management` emx ON exa.`expense_management_manual_id`=emx.`expense_management_manual_id`\r\n"
								+ "	JOIN lodging_expense le ON emx.`expense_management_id`=le.`expense_management_expense_management_id` WHERE le.is_deleted=FALSE and exa.expense_management_manual_id=?",
								new Object[] { _ob.get("XBLNR") });
						float lodgingExpeTotal = 0;

						for (Map<String, Object> lg : loging) {
							lodgingExpeTotal += Float.parseFloat(lg.get("WRBTR").toString());

//							sap.executeUpdate("insert into CRM_EXPENSE(BUZEI,LIFNR,GRADE,KOSTL,BKTXT_O,XBLNR,BLDAT,BUDAT_A,BUDAT,BUPLA,WRBTR_A,WRBTR,ZUONR,SGTXT,CRM_DOCNO,SFLAG,TFLAG,POST,HKONT,SYDAT,SYTIM,GSBER) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
//									buzy,	//_ob.get("BUZEI")==null?"":_ob.get("BUZEI"),
//									_ob.get("LIFNR")==null?"":_ob.get("LIFNR"),
//									_ob.get("GRADE")==null?"":_ob.get("GRADE"),
//									getConstCenter(_ob.get("ex_cost_center").toString()), //_ob.get("KOSTL"),
//									_ob.get("BKTXT_O"),
//									_ob.get("XBLNR"),
//									format.format(_ob.get("BLDAT")),
//									format.format(_ob.get("BUDAT_A")),
//									format.format(_ob.get("BUDAT")),
//									getBupla(_ob.get("BUPLA").toString()),
//									"0",//_ob.get("WRBTR_A"),
//									lodgingExpeTotal,//_ob.get("WRBTR"),
//									_ob.get("ZUONR"),
//									_ob.get("SGTXT"),
//									_ob.get("SFDC_ID"),
//									_ob.get("SFLAG"),
//									_ob.get("TFLAG"),
//									_ob.get("is_post").equals("1")?"Y":"N",
//									"800205",//lg.get("HKONT"),
//									format.format(new Date()),
//									systime.format(new Date()),
//									busArea
//							});
//							buzy++;

						}

						// TOUR EXPENSE

						List<Map<String, Object>> tourExpenseList = crm.getResultSet("SELECT  \r\n"
								+ "	le.`approved_amount` AS `WRBTR`,\r\n" + "	'800205' AS `HKONT`,\r\n"
								+ "	le.`arrival_date`,\r\n" + "	le.`departure_date`,\r\n"
								+ "	le.`amount_to_disbursed` AS `amount_to_disbursed`\r\n"
								+ "FROM expense_approved exa \r\n"
								+ "	JOIN `expense_management` emx ON exa.`expense_management_manual_id`=emx.`expense_management_manual_id`\r\n"
								+ "	JOIN tour_expense le ON emx.`expense_management_id`=le.`expense_management_expense_management_id` WHERE le.is_deleted=FALSE AND exa.expense_management_manual_id=?",
								new Object[] { _ob.get("XBLNR") });

						for (Map<String, Object> lg : tourExpenseList) {
							lodgingExpeTotal += Float.parseFloat(lg.get("WRBTR").toString());
//							sap.executeUpdate("insert into CRM_EXPENSE(BUZEI,LIFNR,GRADE,KOSTL,BKTXT_O,XBLNR,BLDAT,BUDAT_A,BUDAT,BUPLA,WRBTR_A,WRBTR,ZUONR,SGTXT,CRM_DOCNO,SFLAG,TFLAG,POST,HKONT,GSBER) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
//									buzy,	//_ob.get("BUZEI")==null?"":_ob.get("BUZEI"),
//									_ob.get("LIFNR")==null?"":_ob.get("LIFNR"),
//									_ob.get("GRADE")==null?"":_ob.get("GRADE"),
//									getConstCenter(_ob.get("ex_cost_center").toString()), //_ob.get("KOSTL"),
//									_ob.get("BKTXT_O"),
//									_ob.get("XBLNR"),
//									format.format(_ob.get("BLDAT")),
//									format.format(_ob.get("BUDAT_A")),
//									format.format(_ob.get("BUDAT")),
//									getBupla(_ob.get("BUPLA").toString()),
//									"0",//_ob.get("WRBTR_A"),
//									lg.get("WRBTR"),//_ob.get("WRBTR"),
//									_ob.get("ZUONR"),
//									_ob.get("SGTXT"),
//									_ob.get("SFDC_ID"),
//									_ob.get("SFLAG"),
//									_ob.get("TFLAG"),
//									_ob.get("is_post").equals("1")?"Y":"N",
//									lg.get("HKONT"),
//									busArea
//							});
//							buzy++;
						}

						// BOARDING EXPENSE

						List<Map<String, Object>> boardingExpense = crm.getResultSet("SELECT  \r\n"
								+ "	le.`amount` AS `WRBTR`,\r\n" + "	'800205' AS `HKONT`,\r\n"
								+ "	le.`amount_to_disbursed` AS `amount_to_disbursed`\r\n" + "	\r\n"
								+ "FROM expense_approved exa \r\n"
								+ "	JOIN `expense_management` emx ON exa.`expense_management_manual_id`=emx.`expense_management_manual_id`\r\n"
								+ "	JOIN boarding_expense le ON emx.`expense_management_id`=le.`expense_management_expense_management_id` WHERE  le.`is_deleted`=FALSE AND exa.expense_management_manual_id=?",
								new Object[] { _ob.get("XBLNR") });
						for (Map<String, Object> lg : boardingExpense) {
							lodgingExpeTotal += Float.parseFloat(lg.get("WRBTR").toString());
//							sap.executeUpdate("insert into CRM_EXPENSE(BUZEI,LIFNR,GRADE,KOSTL,BKTXT_O,XBLNR,BLDAT,BUDAT_A,BUDAT,BUPLA,WRBTR_A,WRBTR,ZUONR,SGTXT,CRM_DOCNO,SFLAG,TFLAG,POST,HKONT,GSBER) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
//									buzy,
//									_ob.get("LIFNR")==null?"":_ob.get("LIFNR"),
//									_ob.get("GRADE")==null?"":_ob.get("GRADE"),
//									getConstCenter(_ob.get("ex_cost_center").toString()),
//									_ob.get("BKTXT_O"),
//									_ob.get("XBLNR"),
//									format.format(_ob.get("BLDAT")),
//									format.format(_ob.get("BUDAT_A")),
//									format.format(_ob.get("BUDAT")),
//									getBupla(_ob.get("BUPLA").toString()),
//									"0",
//									lg.get("WRBTR"),
//									_ob.get("ZUONR"),
//									_ob.get("SGTXT"),
//									_ob.get("SFDC_ID"),
//									_ob.get("SFLAG"),
//									_ob.get("TFLAG"),
//									_ob.get("is_post").equals("1")?"Y":"N",
//									lg.get("HKONT"),
//									busArea
//							});
//							buzy++;
						}
						if(_ob.get("extype").toString().equalsIgnoreCase("2")||_ob.get("extype").toString().equalsIgnoreCase("4")) {
							if(lodgingExpeTotal>0) {
								sap.executeUpdate(
										"insert into CRM_EXPENSE(BUZEI,LIFNR,GRADE,KOSTL,BKTXT_O,XBLNR,BLDAT,BUDAT_A,BUDAT,BUPLA,WRBTR_A,WRBTR,ZUONR,SGTXT,CRM_DOCNO,SFLAG,TFLAG,POST,HKONT,GSBER) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
										new Object[] { buzy, _ob.get("LIFNR") == null ? "" : _ob.get("LIFNR"),
												_ob.get("GRADE") == null ? "" : _ob.get("GRADE"),
												getConstCenter(_ob.get("ex_cost_center").toString()), _ob.get("BKTXT_O"),
												_ob.get("XBLNR"), format.format(_ob.get("BLDAT")),
												format.format(_ob.get("BUDAT_A")), format.format(_ob.get("BUDAT")),
												getBupla(_ob.get("BUPLA").toString()), "0", lodgingExpeTotal, _ob.get("ZUONR"),
												_ob.get("SGTXT"), _ob.get("SFDC_ID"), _ob.get("SFLAG"), _ob.get("TFLAG"),
												_ob.get("is_post").equals("1") ? "Y" : "N", "800205", // lg.get("HKONT"),
												busArea });
								buzy++;
							}
						}
						
						

						// LOCAL CONVINCE
						float totalLocationConvince = 0;
						List<Map<String, Object>> lstLocalCon = crm.getResultSet("SELECT  \r\n"
								+ "	le.`amount` AS `WRBTR`,\r\n" + "	'800211' AS `HKONT`,\r\n"
								+ "	le.`amount_to_disbursed` AS `amount_to_disbursed`\r\n" + "	\r\n"
								+ "FROM expense_approved exa \r\n"
								+ "	JOIN `expense_management` emx ON exa.`expense_management_manual_id`=emx.`expense_management_manual_id`\r\n"
								+ "	JOIN local_conveyance_expense le ON emx.`expense_management_id`=le.`expense_management_expense_management_id` WHERE  le.`is_deleted`=FALSE AND exa.expense_management_manual_id=?",
								new Object[] { _ob.get("XBLNR") });
						for (Map<String, Object> lg : lstLocalCon) {
							if(_ob.get("extype").toString().equalsIgnoreCase("2")||_ob.get("extype").toString().equalsIgnoreCase("4"))
								totalLocationConvince += Float.parseFloat(lg.get("WRBTR").toString());
							else
								lodgingExpeTotal+= Float.parseFloat(lg.get("WRBTR").toString());
//							sap.executeUpdate("insert into CRM_EXPENSE(BUZEI,LIFNR,GRADE,KOSTL,BKTXT_O,XBLNR,BLDAT,BUDAT_A,BUDAT,BUPLA,WRBTR_A,WRBTR,ZUONR,SGTXT,CRM_DOCNO,SFLAG,TFLAG,POST,HKONT,GSBER) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] {
//									buzy,
//									_ob.get("LIFNR")==null?"":_ob.get("LIFNR"),
//									_ob.get("GRADE")==null?"":_ob.get("GRADE"),
//									getConstCenter(_ob.get("ex_cost_center").toString()),
//									_ob.get("BKTXT_O"),
//									_ob.get("XBLNR"),
//									format.format(_ob.get("BLDAT")),
//									format.format(_ob.get("BUDAT_A")),
//									format.format(_ob.get("BUDAT")),
//									getBupla(_ob.get("BUPLA").toString()),
//									"0",
//									lg.get("WRBTR"),
//									_ob.get("ZUONR"),
//									_ob.get("SGTXT"),
//									_ob.get("SFDC_ID"),
//									_ob.get("SFLAG"),
//									_ob.get("TFLAG"),
//									_ob.get("is_post").equals("1")?"Y":"N",
//									lg.get("HKONT"),
//									busArea
//							});
//							buzy++;
						}
						if(_ob.get("extype").toString().equalsIgnoreCase("2")||_ob.get("extype").toString().equalsIgnoreCase("4")) {
							if(totalLocationConvince>0) {
								sap.executeUpdate(
										"insert into CRM_EXPENSE(BUZEI,LIFNR,GRADE,KOSTL,BKTXT_O,XBLNR,BLDAT,BUDAT_A,BUDAT,BUPLA,WRBTR_A,WRBTR,ZUONR,SGTXT,CRM_DOCNO,SFLAG,TFLAG,POST,HKONT,GSBER) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
										new Object[] { buzy, _ob.get("LIFNR") == null ? "" : _ob.get("LIFNR"),
												_ob.get("GRADE") == null ? "" : _ob.get("GRADE"),
												getConstCenter(_ob.get("ex_cost_center").toString()), _ob.get("BKTXT_O"),
												_ob.get("XBLNR"), format.format(_ob.get("BLDAT")),
												format.format(_ob.get("BUDAT_A")), format.format(_ob.get("BUDAT")),
												getBupla(_ob.get("BUPLA").toString()), "0", totalLocationConvince, // lg.get("WRBTR"),
												_ob.get("ZUONR"), _ob.get("SGTXT"), _ob.get("SFDC_ID"), _ob.get("SFLAG"),
												_ob.get("TFLAG"), _ob.get("is_post").equals("1") ? "Y" : "N", "800211", // lg.get("HKONT"),
												busArea });
								buzy++;
							}
						}
						else {
							if(lodgingExpeTotal>0) {
								sap.executeUpdate(
										"insert into CRM_EXPENSE(BUZEI,LIFNR,GRADE,KOSTL,BKTXT_O,XBLNR,BLDAT,BUDAT_A,BUDAT,BUPLA,WRBTR_A,WRBTR,ZUONR,SGTXT,CRM_DOCNO,SFLAG,TFLAG,POST,HKONT,GSBER) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
										new Object[] { buzy, _ob.get("LIFNR") == null ? "" : _ob.get("LIFNR"),
												_ob.get("GRADE") == null ? "" : _ob.get("GRADE"),
												getConstCenter(_ob.get("ex_cost_center").toString()), _ob.get("BKTXT_O"),
												_ob.get("XBLNR"), format.format(_ob.get("BLDAT")),
												format.format(_ob.get("BUDAT_A")), format.format(_ob.get("BUDAT")),
												getBupla(_ob.get("BUPLA").toString()), "0", lodgingExpeTotal, _ob.get("ZUONR"),
												_ob.get("SGTXT"), _ob.get("SFDC_ID"), _ob.get("SFLAG"), _ob.get("TFLAG"),
												_ob.get("is_post").equals("1") ? "Y" : "N", "800205", // lg.get("HKONT"),
												busArea });
								buzy++;
							}
						}
						
						
						// OTHER EXPENSE

						List<Map<String, Object>> lstOtherCharges = crm.getResultSet("SELECT  \r\n"
								+ "	sum(le.`amount`) AS `WRBTR`,\r\n"
								+ "	sum(le.`amount_to_disbursed`) AS `amount_to_disbursed`,\r\n"
								+ "	oc.`name` AS `HKNOTString`\r\n" + "FROM expense_approved exa \r\n"
								+ "	JOIN `expense_management` emx ON exa.`expense_management_manual_id`=emx.`expense_management_manual_id`\r\n"
								+ "	JOIN other_charges_expense le ON emx.`expense_management_id`=le.`expense_management_expense_management_id` \r\n"
								+ "	JOIN `other_charges_types` oc ON oc.`id`=le.`other_charges_type_id`\r\n"
								+ "	WHERE le.`is_deleted`=FALSE AND exa.expense_management_manual_id=? group by oc.`name`",
								new Object[] { _ob.get("XBLNR") });
						for (Map<String, Object> lg : lstOtherCharges) {
							sap.executeUpdate(
									"insert into CRM_EXPENSE(BUZEI,LIFNR,GRADE,KOSTL,BKTXT_O,XBLNR,BLDAT,BUDAT_A,BUDAT,BUPLA,WRBTR_A,WRBTR,ZUONR,SGTXT,CRM_DOCNO,SFLAG,TFLAG,POST,HKONT,GSBER) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
									new Object[] { buzy, _ob.get("LIFNR") == null ? "" : _ob.get("LIFNR"),
											_ob.get("GRADE") == null ? "" : _ob.get("GRADE"),
											getConstCenter(_ob.get("ex_cost_center").toString()), _ob.get("BKTXT_O"),
											_ob.get("XBLNR"), format.format(_ob.get("BLDAT")),
											format.format(_ob.get("BUDAT_A")), format.format(_ob.get("BUDAT")),
											getBupla(_ob.get("BUPLA").toString()), "0", lg.get("WRBTR"),
											_ob.get("ZUONR"), _ob.get("SGTXT"), _ob.get("SFDC_ID"), _ob.get("SFLAG"),
											_ob.get("TFLAG"), _ob.get("is_post").equals("1") ? "Y" : "N",
											// lg.get("HKONT")1212
											getGlcodeFromOtherChargesType(lg.get("HKNOTString").toString()), busArea });
							buzy++;
						}
						sap.executeUpdate("update CRM_EXPENSE set SYDAT=?,SYTIM=? where XBLNR=?", new Object[] {
								format.format(new Date()), systime.format(new Date()), _ob.get("XBLNR") });

						crm.executeUpdate("update expense_approved set is_synced=? where expense_management_id=?",
								new Object[] { true, _ob.get("SFDC_ID") });
						crm.executeUpdate(
								"update expense_management set is_expense_synced=?,is_synced=? where expense_management_id=?",
								new Object[] { false, true, _ob.get("SFDC_ID") });
						sap.commit();
						crm.commit();
					} catch (Exception ex) {
						crm.rollBack();
						sap.rollBack();
						ex.printStackTrace();
					}
					sap = null;
					crm = null;
				}
			}
			exdata = null;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		_crm = null;
	}

	private void updateTFlag() {
		try {
			SqlHelper _crm = new SqlHelper("CRM");
			List<Map<String, Object>> tFlags = _crm.getResultSet(
					"select * from expense_management where is_expense_synced=? and is_post=?",
					new Object[] { false, true });
			for (Map<String, Object> f : tFlags) {
				SqlHelper sap = new SqlHelper("SAP");
				SqlHelper crm = new SqlHelper("CRM");
				try {
					sap.beginTransaction();
					crm.beginTransaction();
					List<Map<String, Object>> exist = sap.getResultSet("select * from CRM_EXPENSE where XBLNR=?",
							new Object[] { f.get("expense_management_manual_id") });
					if (exist != null && exist.size() > 0) {
						if (exist.get(0).get("TFLAG") != null
								&& exist.get(0).get("TFLAG").toString().equalsIgnoreCase("Y"))
							crm.executeUpdate(
									"UPDATE expense_management SET is_expense_synced=? WHERE expense_management_id=?",
									new Object[] { true, f.get("expense_management_id") });
					}

					sap.commit();
					crm.commit();
				} catch (Exception ex) {
					ex.printStackTrace();
					sap.rollBack();
					crm.rollBack();
				}
				sap = null;
				crm = null;
			}
			tFlags = null;
		} catch (Exception ex) {

			ex.printStackTrace();
		}
	}

	String getConstCenter(String costCenter) {
		String[] st = costCenter.split("/");
		return st[st.length - 1].trim();
	}

	String getGlcodeFromOtherChargesType(String otherCharege) {
		String[] st = otherCharege.split("-");
		return st[st.length - 1].trim();
	}

	String getBuisnessArea(String costCenter) {
		String busa = "";
		if (costCenter.equalsIgnoreCase("1142082000") || costCenter.equalsIgnoreCase("1142082010")
				|| costCenter.equalsIgnoreCase("1042101031"))
			//busa = "3000";
			busa="1100";
		else if (costCenter.equalsIgnoreCase("1042101030"))
			//busa = "4210";
			busa="1000";
		return busa;
	}

	String getBupla(String msg) {
		return msg.split("-")[0].trim();
	}

	String getRemarks(long expNumber) {
		SqlHelper crm = new SqlHelper("CRM");
		Map<String, Object> re = null;
		try {
			re = crm.getSingleResult(
					"select remark from to_be_approved where object_name='ExpenseManagement' and object_id=? and id=(select max(id) as id from to_be_approved where object_name='ExpenseManagement' and object_id=? )",
					new Object[] { expNumber, expNumber });
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return re.get("remark") == null ? "" : re.get("remark").toString();
	}
}
