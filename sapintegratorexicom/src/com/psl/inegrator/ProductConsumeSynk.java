package com.psl.inegrator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ProductConsumeSynk implements Runnable {

	public void run() {
		// while(true) {
		try {
			synkServiceAppointment();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		try {
			repariCenterConsumed();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			syncedFromBulkUpload();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// try {
		// Thread.sleep(1000*60);
		// } catch (InterruptedException e) {
		//
		// e.printStackTrace();
		// }
		// }
	}

	private void synkServiceAppointment() {
		SqlHelper _crm = new SqlHelper("CRM");
		SqlHelper crm = new SqlHelper("CRM");
		SqlHelper sap = new SqlHelper("SAP");
		try {
			sap.beginTransaction();
			crm.beginTransaction();
			SimpleDateFormat dformat = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat tformat = new SimpleDateFormat("HHmmss");
			List<Map<String, Object>> cdata = _crm.getResultSet("SELECT scon.`id` AS consumeid,\r\n" +
					"	scon.`created_date` AS cdate,\r\n" +
					"	scon.`quantity` AS cquantity,\r\n" +
					"	scon.`product_product_part_code` AS partcode,\r\n" +
					"	scon.`consumption_type_consumption_type_name`,\r\n" +
					"	sr.`location_location_id` AS locationid,\r\n" +
					"	sa.`appointment_number` AS appointmentnumber,\r\n" +
					"	sa.`cost_center` AS costcenter,\r\n" +
					"	slitem.`consumed_line_item_serial_number` as spart,\r\n" +
					"	emp.`first_name` AS firstName,\r\n" +
					"	emp.`middle_name` AS middleName,\r\n" +
					"	emp.`last_name` AS lastName,\r\n" +
					"	substring(scon.`remark`,1,100) as remark\r\n" +
					"	\r\n" +
					"	FROM `service_appoinment_product_consumed` scon\r\n" +
					"	JOIN `service_appointment` sa ON sa.`service_appointment_id`=scon.`service_appointment_service_appointment_id`\r\n"
					+
					"	JOIN `service_recource` sr ON sr.`service_resource_id`=sa.`service_resource_service_resource_id`\r\n"
					+
					"	JOIN `employee_details` emp ON emp.`email_id`=sr.`user_email_id`\r\n" +
					"	LEFT JOIN `service_appointment_product_consumed_line_items` slitem ON scon.`id`=slitem.`product_consumed_id`\r\n"
					+
					"	WHERE scon.`synked`=FALSE AND sa.`status_status_id` IN (4,5,6,7) AND scon.`consumption_type_consumption_type_name` LIKE 'Consumed' ");

			for (Map<String, Object> sa : cdata) {
				Date bldate = (Date) sa.get("cdate");

				Map<String, Object> wareHouse = getWarehouse(Long.parseLong(sa.get("locationid").toString()), crm);

				sap.executeUpdate(
						"insert into CRM_STOCK_CONS(CRM_DOCNO,CRM_LITM,BKTXT,BLDAT,BUDAT,USNAM,MATNR,BWARTWA,GRUND,LGORT,ERFMG,SERNR,WERKS,KOSTL,SFLAG,TFLAG,SYDAT,SYTIM,REMARK) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
						new Object[] {
								sa.get("appointmentnumber"),
								sa.get("consumeid"),
								sa.get("remark") == null ? "" : sa.get("remark"),
								dformat.format(bldate),
								dformat.format(bldate),
								(sa.get("firstName") == null ? "" : "" + sa.get("firstName"))
										+ (sa.get("lastName") == null ? "" : " " + sa.get("lastName")),
								sa.get("partcode"),
								"",
								"",
								wareHouse.get("location_id"),
								sa.get("spart") == null ? sa.get("cquantity") : "1",
								sa.get("spart") == null ? null : sa.get("spart"),
								wareHouse.get("plant"),
								sa.get("costcenter") == null ? "" : sa.get("costcenter"),
								"CR",
								"N",
								dformat.format(new Date()),
								tformat.format(new Date()),
								""
						});
				crm.executeUpdate("update service_appoinment_product_consumed set synked=? where id=?", new Object[] {
						true,
						sa.get("consumeid")
				});
			}
			sap.commit();
			crm.commit();
		} catch (Exception ex) {
			sap.rollBack();
			crm.rollBack();
			ex.printStackTrace();
		}
	}

	private void repariCenterConsumed() {
		SqlHelper _crm = new SqlHelper("CRM");
		SqlHelper crm = new SqlHelper("CRM");
		SqlHelper sap = new SqlHelper("SAP");
		try {
			sap.beginTransaction();
			crm.beginTransaction();
			SimpleDateFormat dformat = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat tformat = new SimpleDateFormat("HHmmss");
			List<Map<String, Object>> cdata = _crm.getResultSet("SELECT   \r\n" +
					"						ic.`id` AS consumeId,  \r\n" +
					"						ic.`product_product_part_code` AS partCode,  \r\n" +
					"						ic.`quantity` AS qty,  \r\n" +
					"						sr.`location_location_id` AS warehouseId,  \r\n" +
					"						rp.`created_date` AS createdDate,  \r\n" +
					"						rp.`remarks` AS remarks,  \r\n" +
					"						jb.`manual_id` AS jobCardId,  \r\n" +
					"						emp.`first_name` AS firstname,  \r\n" +
					"						emp.`middle_name` AS middlename,  \r\n" +
					"						emp.`last_name` AS lastname,  \r\n" +
					"						cs.`serial_number` AS serialNumber  \r\n" +
					"						FROM `dct_repair_item_consumed` ic   \r\n" +
					"						JOIN dct_repair rp ON rp.id=ic.repair_id  \r\n" +
					"						JOIN `service_order_job_card` jb ON jb.`id`=rp.`job_card_id`  \r\n" +
					"                        JOIN `service_recource` sr ON sr.service_resource_id=jb.repair_eng_service_resource_id\r\n"
					+
					"                        JOIN employee_details emp ON emp.`email_id`=sr.user_email_id\r\n" +
					"						LEFT JOIN `dcr_repari_citems_serial` cs ON cs.items_id=ic.id  \r\n" +
					"						WHERE ic.synked=FALSE");
			for (Map<String, Object> con : cdata) {
				Date bldate = (Date) con.get("createdDate");
				Map<String, Object> wareHOuse = getWarehouse(Long.parseLong(con.get("warehouseId").toString()), crm);
				sap.executeUpdate(
						"insert into CRM_STOCK_CONS(CRM_DOCNO,CRM_LITM,BKTXT,BLDAT,BUDAT,USNAM,MATNR,BWARTWA,GRUND,LGORT,ERFMG,SERNR,WERKS,KOSTL,SFLAG,TFLAG,SYDAT,SYTIM,REMARK) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
						new Object[] {
								con.get("jobCardId"),
								con.get("consumeId"),
								con.get("remarks") == null ? "" : con.get("remarks"),
								dformat.format(bldate),
								dformat.format(bldate),
								(con.get("firstname") == null ? "" : "" + con.get("firstname"))
										+ (con.get("middlename") == null ? "" : " " + con.get("middlename"))
										+ (con.get("lastname") == null ? "" : " " + con.get("lastname")),
								con.get("partCode"),
								"",
								"",
								wareHOuse.get("location_id"),
								con.get("serialNumber") == null ? con.get("qty") : "1",
								con.get("serialNumber") == null ? null : con.get("serialNumber"),
								wareHOuse.get("plant"),
								"",
								"CR",
								"N",
								dformat.format(new Date()),
								tformat.format(new Date()),
								""
						});
				crm.executeUpdate("update dct_repair_item_consumed set synked=? where id=?", new Object[] {
						true,
						con.get("consumeId")
				});
			}
			sap.commit();
			crm.commit();
		} catch (Exception ex) {
			sap.rollBack();
			crm.rollBack();
			ex.printStackTrace();
		}
	}

	void syncedFromBulkUpload() {
		SqlHelper _crm = new SqlHelper("CRM");
		List<Map<String, Object>> conmss = null;
		SimpleDateFormat dformat = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat tformat = new SimpleDateFormat("HHmmss");
		try {
			conmss = _crm.getResultSet("SELECT "
					+ " cm.id as CRM_LITM, "
					+ " cm.manual_id as CRM_DOCNO, "
					+ " cm.remarks as BKTXT, "
					+ " cm.created_date as BLDAT, "
					+ " cm.created_date as BUDAT, "
					+ " emp.first_name as USNAM_first_name, "
					+ " emp.middle_name as USNAM_middle_name, "
					+ " emp.last_name as USNAM_last_name, "
					+ " cm.consumed_product_product_part_code as MATNR, "
					+ " '' as BWARTWA, "
					+ " '' as GRUND, "
					+ " cm.location_location_id as LGORT, "
					+ " cm.consumed_quantity as ERFMG, "
					+ " null as SERNR, "
					+ " '' as KOSTL, "
					+ " 'CR' AS SFLAG, "
					+ " 'N' AS TFLAG, "
					+ " now() as SYDAT, "
					+ " now() as SYTIM, "
					+ " '' as REMARK "
					+ " FROM dct_repair_bulk_consumption cm join employee_details emp on cm.owner_email_id=emp.email_id where cm.scynced=false;");

			for (Map<String, Object> cons : conmss) {
				SqlHelper crm = new SqlHelper("CRM");
				SqlHelper sap = new SqlHelper("SAP");
				try {
					sap.beginTransaction();
					crm.beginTransaction();
					String username = cons.get("USNAM_first_name") == null ? ""
							: cons.get("USNAM_first_name").toString();
					username += cons.get("USNAM_middle_name") == null ? ""
							: (" " + cons.get("USNAM_middle_name").toString());
					username += cons.get("USNAM_last_name") == null ? ""
							: (" " + cons.get("USNAM_last_name").toString());
					Date bldate = (Date) cons.get("BLDAT");
					String remarks = cons.get("BKTXT") == null ? "" : cons.get("BKTXT").toString();
					if (remarks.length() > 199)
						remarks = remarks.substring(0, 199);
					if (username.length() > 24)
						username = username.substring(0, 24);
					Map<String, Object> wareHOuse = getWarehouse(Long.parseLong(cons.get("LGORT").toString()), crm);

					sap.executeUpdate(
							"insert into CRM_STOCK_CONS(CRM_DOCNO,CRM_LITM,BKTXT,BLDAT,BUDAT,USNAM,MATNR,BWARTWA,GRUND,LGORT,ERFMG,SERNR,WERKS,KOSTL,SFLAG,TFLAG,SYDAT,SYTIM,REMARK) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
							new Object[] {
									cons.get("CRM_DOCNO"),
									cons.get("CRM_LITM"),
									remarks,
									dformat.format(bldate),
									dformat.format(bldate),
									username,
									cons.get("MATNR"),
									"",
									"",
									wareHOuse.get("location_id"),
									cons.get("ERFMG"),
									"",
									wareHOuse.get("plant"),
									"",
									"CR",
									"N",
									dformat.format(new Date()),
									tformat.format(new Date()),
									""
							});
					crm.executeUpdate("update dct_repair_bulk_consumption set scynced=? where id=?",
							new Object[] { true, cons.get("CRM_LITM") });

					sap.commit();
					crm.commit();
				} catch (Exception e) {
					crm.rollBack();
					sap.rollBack();
					e.printStackTrace();
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
}
