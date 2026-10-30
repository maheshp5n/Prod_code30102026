package com.pratikshat.exicom.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.border.Border;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pratikshat.exicom.config.FileStorageProperties;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.model.request.Login;

public class CommissioningFSRPdf {

	private Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL, BaseColor.BLACK);
	private Font normalFontBold = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);
	private Font timesBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);
	private Font headingFont = new Font(Font.FontFamily.TIMES_ROMAN, 17, Font.BOLD);

	EntityManager entityManager = null;
	FileStorageProperties fileStorageProperties = null;
	String heading = "Battery Bank Commissioning Report";

	public CommissioningFSRPdf(EntityManager entityManager, FileStorageProperties fileStorageProperties) {
		this.entityManager = entityManager;
		this.fileStorageProperties = fileStorageProperties;
	}

	public ByteArrayInputStream createPdf(LoginUser user, long id) throws BadRequestException {
		Document document = new Document(PageSize.A4, -50, -50, 20, 20);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {

			PdfWriter writer = PdfWriter.getInstance(document, out);

			document.open();

			addHeader(document, id);
			addGeneralInformation(document, id);
//			addSubHeading(document, "1. Fault observed by customer");
//			addFaultObservedByCustomer(document, id);
			addSubHeading(document, "1. SITE OBSERVATION ");
			addSiteObservation(document, id);
			addSubHeading(document, "2. ASSET MASTER UPDATE");
			addAssetMasterUpdate(document, id);
			addSubHeading(document, "3. EXICOM ENG REMARKS (If Any) ");
			addExicomEngRemark(document, id);
			addSubHeading(document, "4. CUSTOMER REMARKS (If Any)");
			addCustomerRemark(document, id);
			addSubHeading(document, " ");
			addCommStatus(document, id);
			addSubHeading(document, " ");
			addEngInfo(document, id);
//			addSubHeading(document, "Pending Reason Customer Bucket");
//			addPendingReasonCustomerBucket(document, id);

			document.close();
		} catch (DocumentException de) {
			throw new BadRequestException("Error in creating pdf");
		}
		return new ByteArrayInputStream(out.toByteArray());
	}

	private void addHeader(Document document, long id) {
		try {
			Path fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
			Image image = Image.getInstance(fileStorageLocation + "/" + "logos.png");

			PdfPTable header = new PdfPTable(3);
			header.setWidths(new int[] { 3, 10, 5 });
			header.getDefaultCell().setBorderWidth(0f);

			image.scalePercent(20);
			image.setAlignment(Element.ALIGN_LEFT);
			PdfPCell cellImage = new PdfPCell(image);
			header.addCell(image);

			Phrase headPhrase = new Phrase();
			headPhrase.add(new Chunk(heading, headingFont));
			PdfPCell headingCell = new PdfPCell(headPhrase);
			headingCell.setBorder(Rectangle.NO_BORDER);
			headingCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			headingCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			headingCell.setPadding(7);
			header.addCell(headingCell);

			String query = "SELECT \r\n" + "	cfsr.`manual_id` AS `Commissioning FSR Manual Id`\r\n"
					+ "		FROM `bt_commissioningfsr` cfsr\r\n" + "		WHERE cfsr.`id` =?1";
			Query getQuery = entityManager.createNativeQuery(query);
			getQuery.setParameter(1, id);
			String generalInformation = (String) getQuery.getSingleResult();
			Phrase phrase = new Phrase();
			phrase.add(new Chunk(generalInformation, normalFontBold));
			PdfPCell blankCell = new PdfPCell(phrase);
			blankCell.setBorder(Rectangle.NO_BORDER);
			blankCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			blankCell.setVerticalAlignment(Element.ALIGN_TOP);
			header.addCell(blankCell);

			header.completeRow();

			document.add(header);

		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		} catch (MalformedURLException e) {
			throw new ExceptionConverter(e);
		} catch (IOException e) {
			throw new ExceptionConverter(e);
		}

	}

	private void addSubHeading(Document document, String text) {

		try {
			PdfPTable subHeadingTable = new PdfPTable(1);

			Phrase subHeadingPhrase = new Phrase();
			subHeadingPhrase.add(new Chunk(text, timesBoldFont));
			PdfPCell subHeadingCell = new PdfPCell(subHeadingPhrase);
			subHeadingCell.setPaddingBottom(5);
			subHeadingCell.setBorder(Rectangle.NO_BORDER);
			subHeadingCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			subHeadingCell.setVerticalAlignment(com.itextpdf.text.Element.ALIGN_MIDDLE);
			subHeadingCell.setPaddingTop(10);
			subHeadingTable.addCell(subHeadingCell);
			subHeadingTable.completeRow();

			document.add(subHeadingTable);
		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		}

	}

	private void addGeneralInformation(Document document, long id) {
		try {
			String query = "SELECT  	ca.`manual_id` AS `Case No`,\r\n"
					+ "	CONCAT(cdt.`first_name`,' ',cdt.`last_name` ) AS `Customer Name`,\r\n"
					+ "	sd.`circle_circle_name` AS `Circle Name`, 	sd.`cmp` AS `CMP`,\r\n"
					+ "	sd.`manual_id` AS `Site manual id`, 	sd.`site_name` AS `Site Name`,\r\n"
					+ "	CONCAT( emp.`first_name`,' ',emp.`last_name` ) AS `Case Logged By`,\r\n"
					+ "	cdt.`mobile` `Contact Number`,\r\n"
					+ "	cfsr.`contact_person_at_site` AS `Contact Person At Site`,\r\n"
					+ "	cfsr.`contact_person_at_site_contact_no` AS `Contact Person At Site Contact No`,\r\n"
					+ "	DATE_FORMAT( ca.`created_date`, '%d/%m/%Y') AS `Date Of Case Generated`,\r\n"
					+ "	DATE_FORMAT(cfsr.`date_of_site_visit`,'%d/%m/%Y' ) AS `Date Of Site Visit`,\r\n"
					+ "	cdt.`mobile` AS `Mobile`\r\n"
					+ "	\r\n"
					+ "		FROM `bt_commissioningfsr` cfsr\r\n"
					+ "		JOIN `bt_service_appointment` sa ON sa.`service_appointment_id` =cfsr.`bt_service_appointment_service_appointment_id` \r\n"
					+ "		JOIN `bt_case` ca ON ca.`id`=sa.`case_details_id`\r\n"
					+ "		JOIN `employee_details` emp ON emp.`email_id` =ca.`owner_details_email_id`\r\n"
					+ "		JOIN `contact_details` cdt ON cdt.`contact_id` =ca.`contact_person_contact_id`\r\n"
					+ "		JOIN `bt_site_details` sd ON sd.`id` =ca.`site_id`\r\n"
					+ "		JOIN `circle_details` crl ON crl.`circle_name` =sd.`circle_circle_name`\r\n"
					+ "		WHERE cfsr.`id`=?1";
			Query getQuery = entityManager.createNativeQuery(query);
			getQuery.setParameter(1, id);
			Object[] generalInformation = (Object[]) getQuery.getSingleResult();

			PdfPTable table = new PdfPTable(5);
			table.setWidths(new int[] { 5, 10, 1, 5, 10 });

			table.addCell(addhead(" Case No. "));
			table.addCell(addCell(" " + generalInformation[0].toString()));
			table.addCell(addCells(" "));

			table.addCell(addhead(" Customer Name "));
			table.addCell(addCell(" " + generalInformation[1].toString()));
			table.completeRow();

			table.addCell(addhead(" Circle/State "));
			table.addCell(addCell(" " + generalInformation[2].toString()));
			table.addCell(addCells(" "));
			table.addCell(addhead(" CMP "));
			try {
				table.addCell(addCell(" " + generalInformation[3].toString()));
			} catch (Exception e) {
				table.addCell(" ");
			}
			table.completeRow();

			table.addCell(addhead(" Site ID "));
			table.addCell(addCell(" " + generalInformation[4].toString()));
			table.addCell(addCells(" "));
			table.addCell(addhead(" Site Name "));
			table.addCell(addCell(" " + generalInformation[5].toString()));
			table.completeRow();

			table.addCell(addhead(" Case Logged By "));
			table.addCell(addCell(" " + generalInformation[1].toString()));
			table.addCell(addCells(" "));
			table.addCell(addhead(" Contact Number "));
			table.addCell(addCell(" " + generalInformation[12].toString()));
			table.completeRow();

			table.addCell(addhead(" Contact Person At Site "));
			table.addCell(addCell(" " + generalInformation[8].toString()));
			table.addCell(addCells(" "));
			table.addCell(addhead(" Site Eng Contact No. "));
			table.addCell(addCell(" " + generalInformation[9].toString()));
			table.completeRow();

			table.addCell(addhead(" Date of Case Generated "));
			table.addCell(addCell(" " + generalInformation[10].toString()));
			table.addCell(addCells(" "));
			table.addCell(addhead(" Date of Site Visit "));
			table.addCell(addCell(" " + generalInformation[11].toString()));
			table.completeRow();

//			PdfPTable table = new PdfPTable(3);
//			table.setWidths(new int[] {10, 1,10 });
//
//			table.addCell(addhead(" Case No :: "+ generalInformation[0].toString()));
//			table.addCell(addCells(" "));
//			table.addCell(addhead(" Customer Name :: "+ generalInformation[1].toString()));
//			table.completeRow();
//
//			table.addCell(addhead(" Circle/State :: "+ generalInformation[2].toString()));
//			table.addCell(addCells(" "));
//			table.addCell(addhead(" CMP :: "+ generalInformation[3].toString()));
//			table.completeRow();
//
//			table.addCell(addhead(" Site ID :: "+ generalInformation[4].toString()));
//			table.addCell(addCells(" "));
//			table.addCell(addhead(" Site Name :: "+ generalInformation[5].toString()));
//			table.completeRow();
//
//			table.addCell(addhead(" Case Logged By :: "+ generalInformation[6].toString()));
//			table.addCell(addCells(" "));
//			table.addCell(addhead(" Contact Number :: "+ generalInformation[7].toString()));
//			table.completeRow();
//
//			table.addCell(addhead(" Contact Person At Site :: "+ generalInformation[8].toString()));
//			table.addCell(addCells(" "));
//			table.addCell(addhead(" Site Eng Contact No. :: "+ generalInformation[9].toString()));
//			table.completeRow();
//
//			table.addCell(addhead(" Date of Case Generated :: "+ generalInformation[10].toString()));
//			table.addCell(addCells(" "));
//			table.addCell(addhead(" Date of Site Visit :: "+ generalInformation[11].toString()));
//			table.completeRow();

			document.add(table);
		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		}

	}

//	private void addFaultObservedByCustomer(Document document, long id) {
//		try {
//			String query = "SELECT \r\n" + "	ca.`other_problem_reported` AS `Fault Observed By Customer`\r\n"
//					+ "		FROM `bt_commissioningfsr` cfsr\r\n"
//					+ "		JOIN `bt_service_appointment` sa ON sa.`service_appointment_id` =cfsr.`bt_service_appointment_service_appointment_id` \r\n"
//					+ "		JOIN `bt_case` ca ON ca.`id`=sa.`case_details_id`		\r\n" + "		WHERE cfsr.`id`=?1";
//			Query getQuery = entityManager.createNativeQuery(query);
//			getQuery.setParameter(1, id);
//			String faultObservedByCustomer = (String) getQuery.getSingleResult();
//			PdfPTable table = new PdfPTable(1);
//
//			table.addCell(addCell(" " + faultObservedByCustomer));
//			table.completeRow();
//
//			document.add(table);
//		} catch (DocumentException de) {
//			throw new ExceptionConverter(de);
//		}
//
//	}

	private void addSiteObservation(Document document, long id) {
		try {
			String query = "SELECT \r\n" + "	st.`type_name` AS `Site Type`,\r\n"
					+ "	sec.`type_name` AS `Site Environmental Condition`,\r\n"
					+ "	cfsr.`earthing_status` AS `Earthing Status`,\r\n"
					+ "	cm.`type_value` AS `Cooling Method`,\r\n"
					+ "	cfsr.`ambient_temperature` AS `Ambient Temp`,\r\n"
					+ "	cfsr.`commission_scope` AS `Commissioning Scope`,\r\n"
					+ "	cfsr.`total_equipmentdcload` AS `Total Equipment DC Load`,\r\n"
					+ "	cfsr.`installation_status` AS `Installation Status`,\r\n"
					+ "	cfsr.`smps_to_cbms_vol_gap` AS `SMPS To CBMS Vol Gap`,\r\n"
					+ "	cfsr.`cbms_to_bat_vol_gap` AS `CBMS to Bat Vol Gap`,\r\n"
					+ "	cfsr.`dc_load_current` AS `DC Load`,\r\n"
					+ "	cfsr.`max_module_temp` AS `Max module temp`,\r\n"
					+ "	cfsr.`charge_voltage` AS `Charge voltage`,\r\n"
					+ "	cfsr.`float_voltage` AS `Float Voltage`,\r\n" + "	ppm.`type_value` AS `Power Plant Make`,\r\n"
					+ "	cfsr.`active_rectifiers` AS `Active Rectifiers`\r\n"
					+ "		FROM `bt_commissioningfsr` cfsr\r\n"
					+ "		JOIN `bt_site_type` st ON st.`id` =cfsr.`bt_site_type_id`\r\n"
					+ "		JOIN `bt_site_environmental_conditions` sec ON sec.`id` =cfsr.`bt_site_environmental_conditions_id`\r\n"
					+ "		JOIN `bt_cooling_method` cm ON cm.`id` =cfsr.`bt_cooling_method_id` \r\n"
					+ "		JOIN `bt_power_plant_make` ppm ON ppm.`id` =cfsr.`bt_power_plant_make_id`\r\n"
					+ "		WHERE cfsr.`id`=?1";
			Query getQuery = entityManager.createNativeQuery(query);
			getQuery.setParameter(1, id);
			Object[] siteObservation = (Object[]) getQuery.getSingleResult();
			PdfPTable table = new PdfPTable(5);
			table.setWidths(new int[] { 8, 10, 1, 8, 10 });

			table.addCell(addhead(" Site Type"));
			table.addCell(addCell(" " + siteObservation[0].toString()));
			table.addCell(addCells(" "));
			table.addCell(addhead(" Site Environmental conditions"));
			table.addCell(addCell(" " + siteObservation[1].toString()));
			table.completeRow();

			table.addCell(addhead(" Earthing Status"));
			table.addCell(addCell(" " + siteObservation[2].toString()));
			table.addCell(addCells(" "));
			table.addCell(addhead(" Cooling method"));
			table.addCell(addCell(" " + siteObservation[3].toString()));
			table.completeRow();

			table.addCell(addhead(" Ambient temperature (deg C)"));
			table.addCell(addCell(" " + siteObservation[4].toString()));
			table.addCell(addCells(" "));
			table.addCell(addhead(" Commission Scope"));
			table.addCell(addCell(" " + siteObservation[5].toString()));
			table.completeRow();

			table.addCell(addhead(" Total Equipment DC Load(Amp.)"));
			table.addCell(addCell(" " + siteObservation[6].toString()));
			table.addCell(addCells(" "));
			table.addCell(addhead(" Installation Status"));
			table.addCell(addCell(" " + siteObservation[7].toString()));
			table.completeRow();

			table.addCell(addhead(" SMPS To CBMS vol gap"));
			table.addCell(addCell(" " + siteObservation[8].toString()));
			table.addCell(addCells(" "));
			table.addCell(addhead(" CBMS To Bat Vol Gap"));
			table.addCell(addCell(" " + siteObservation[9].toString()));
			table.completeRow();

			table.addCell(addhead(" DC Voltage (V)"));
			table.addCell(addCell(" " + siteObservation[10].toString()));
			table.addCell(addCells(" "));
			table.addCell(addhead(" Max Module temp"));
			table.addCell(addCell(" " + siteObservation[11].toString()));
			table.completeRow();

			table.addCell(addhead(" Charge Voltage"));
			table.addCell(addCell(" " + siteObservation[12].toString()));
			table.addCell(addCells(" "));
			table.addCell(addhead(" Float Voltage"));
			table.addCell(addCell(" " + siteObservation[13].toString()));
			table.completeRow();

			table.addCell(addhead(" Power plant make"));
			table.addCell(addCell(" " + siteObservation[14].toString()));
			table.addCell(addCells(" "));
			table.addCell(addhead(" Active rectifiers"));
			table.addCell(addCell(" " + siteObservation[15].toString()));
			table.completeRow();

			document.add(table);
		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		}

	}

	private void addAssetMasterUpdate(Document document, long id) {
		try {

			PdfPTable table = new PdfPTable(10);
			table.setWidths(new int[] { 4, 4, 4, 4, 2, 2, 2, 4, 4, 4 });
			List<String> headers = new ArrayList<>();
			headers.add("EQUIPMENT TYPE");
			headers.add("PART CODE");
			headers.add("MODEL NO");
			headers.add("EQUIPMENT Sr. No.");
			headers.add("S/W VER.");
			headers.add("SOC");
			headers.add("SOH");
			headers.add("WARRANTY STATUS");
			headers.add("INSTALLATION DATE");
			headers.add("BATTERY BANK CONDITION");

			if (headers.size() > 0) {

				for (int i = 0; i < headers.size(); i++) {
					PdfPCell hcell;
					hcell = new PdfPCell(new Phrase(headers.get(i), normalFontBold));
					hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					hcell.setBorderWidthBottom(new Float(0.1));
					hcell.setBorderWidthLeft(new Float(0.1));
					hcell.setBorderWidthRight(new Float(0.1));
					hcell.setBorderWidthTop(new Float(0.1));
					table.addCell(hcell);
				}
			}

			String query = "SELECT \r\n" + "	castm.`equipment_type` AS `Equipment Type`,\r\n"
					+ "	prd.`manual_id` AS `Part Code`,\r\n" + "	prd.`product_description` AS `Model No`,\r\n"
					+ "	castm.`equipment_sr_no` AS `Equipment Sr No`,\r\n"
					+ "	castm.`software_version` AS `Software Version`,\r\n" + "	castm.`soc` AS `SOC`,\r\n"
					+ "	castm.`soh` AS `SOH`,\r\n"
					+ "	(CASE WHEN astwr.`warranty_expiry_date` > NOW()  THEN 'Under Warranty'\r\n"
					+ "		ELSE 'Without Warranty' END)AS warrantyStatus ,\r\n"
					+ "	DATE_FORMAT(astwr.`install_date`,'%d/%m/%Y' ) AS `Installation Date`,\r\n"
					+ "	castm.`current_condition` AS `Current Condition`\r\n"
					+ "		FROM `bt_commissioning_asset_master_update` castm\r\n"
					+ "		JOIN `bt_assets` ast ON ast.`id` =castm.`bt_assets_id`\r\n"
					+ "		JOIN `bt_products` prd ON prd.`id` =ast.`products_id`\r\n"
					+ "		JOIN `bt_assets_warranty_details` astwr ON astwr.`id` =ast.`id`\r\n"
					+ "		JOIN `bt_commissioningfsr` cfsr ON cfsr.`id` =castm.`bt_commissioningfsr_id`\r\n"
					+ "		WHERE cfsr.`id`=?1" + "		ORDER BY CAST(castm.`sr_no` AS SIGNED INTEGER) ASC";
			Query getQuery = entityManager.createNativeQuery(query);
			getQuery.setParameter(1, id);
			List<Object[]> assestMasterListItem = getQuery.getResultList();

			if (assestMasterListItem != null && !assestMasterListItem.isEmpty()) {

				for (int i = 0; i < assestMasterListItem.size(); i++) {
					Object[] objectArray = assestMasterListItem.get(i);
					for (int j = 0; j < objectArray.length; j++) {
						try {
							PdfPCell hcell = new PdfPCell(new Phrase(objectArray[j].toString(), normalFont));
							hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
							hcell.setVerticalAlignment(Element.ALIGN_CENTER);
							hcell.setBorderWidthBottom(new Float(0.1));
							hcell.setBorderWidthLeft(new Float(0.1));
							hcell.setBorderWidthRight(new Float(0.1));
							hcell.setBorderWidthTop(new Float(0.1));
							hcell.setPadding(5);
							table.addCell(hcell);
						} catch (Exception e) {
							PdfPCell hcell = new PdfPCell(new Phrase(" ", normalFont));
							hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
							hcell.setVerticalAlignment(Element.ALIGN_CENTER);
							hcell.setBorderWidthBottom(new Float(0.1));
							hcell.setBorderWidthLeft(new Float(0.1));
							hcell.setBorderWidthRight(new Float(0.1));
							hcell.setBorderWidthTop(new Float(0.1));
							hcell.setPadding(5);
							table.addCell(hcell);
						}

					}
				}
				document.add(table);
			}

		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		}

	}

	private void addExicomEngRemark(Document document, long id) {
		try {
			PdfPTable table = new PdfPTable(1);
			String query = "SELECT \r\n" + "	cfsr.`exicom_eng_remarks` AS `Exicom Eng Remark`\r\n"
					+ "		FROM `bt_commissioningfsr` cfsr\r\n" + "		WHERE cfsr.`id`=?1";
			Query getQuery = entityManager.createNativeQuery(query);
			getQuery.setParameter(1, id);

			String exicomEngRemark = (String) getQuery.getSingleResult();
			try {
			String engRemarkFirstLetter = exicomEngRemark.substring(0, 1);
			String engRemarkRemainingLetter = exicomEngRemark.substring(1, exicomEngRemark.length());

			String firstLetter = engRemarkFirstLetter.toUpperCase();
			String remainingLetter = engRemarkRemainingLetter.toLowerCase();

			exicomEngRemark = firstLetter + remainingLetter;

			table.addCell(addCell(" " + exicomEngRemark));
			} catch(Exception e) {
				table.addCell(" ");
			}
			table.completeRow();
			document.add(table);
		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		}

	}

	private void addCustomerRemark(Document document, long id) {
		try {
			PdfPTable table = new PdfPTable(1);
			String query = "SELECT \r\n" + "	cfsr.`customer_remarks` AS `Customer Remark`\r\n"
					+ "		FROM `bt_commissioningfsr` cfsr\r\n" + "		WHERE cfsr.`id`=?1";
			Query getQuery = entityManager.createNativeQuery(query);
			getQuery.setParameter(1, id);
			String customerRemark = (String) getQuery.getSingleResult();
			try {
			String custRemarkFirstLetter = customerRemark.substring(0, 1);
				String custRemarkRemainingLetter = customerRemark.substring(1, customerRemark.length());

				String firstLetter = custRemarkFirstLetter.toUpperCase();
				String remainingLetter = custRemarkRemainingLetter.toLowerCase();

				customerRemark = firstLetter + remainingLetter;

				table.addCell(addCell(" " + customerRemark));
			} catch (Exception e) {
				table.addCell(" ");
			}
			table.completeRow();

			document.add(table);
		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		}

	}

	private void addCommStatus(Document document, long id) {
		try {
			PdfPTable table = new PdfPTable(6);
			table.setWidths(new int[] { 3, 3, 4, 3, 4, 6 });
			String query = "SELECT \r\n" + "	cfsr.`comm_status` AS `Comm Status`,\r\n"
					+ "	cfsr.`pending_bucket` AS `Pending Bucket`,\r\n" + "	\r\n"
					+ "	(CASE WHEN cfsr.`pending_bucket`='EXICOM' \r\n"
					+ "	THEN cfsr.`pending_reason_exicom_bucket` \r\n"
					+ "	ELSE prcb.`type_value` END )AS `Pending Reason`\r\n" + "	\r\n"
					+ "		FROM `bt_commissioningfsr` cfsr\r\n"
					+ "		LEFT JOIN `bt_pending_reason_customer_bucket` prcb ON prcb.`id`=cfsr.`bt_pending_reason_customer_bucket_id`\r\n"
					+ "		WHERE cfsr.`id` =?1";
			Query getQuery = entityManager.createNativeQuery(query);
			getQuery.setParameter(1, id);
			Object[] customerRemarksItem = (Object[]) getQuery.getSingleResult();
			table.addCell(addCells("6. Comm Status "));
			table.addCell(addCell(" " + customerRemarksItem[0].toString()));

			String pendingBucket = customerRemarksItem[1].toString();
			table.addCell(addCells(" Pending Bucket (If Any) "));
			try {
				table.addCell(addCell(" " + pendingBucket));
			} catch (Exception e) {
				table.addCell(" ");
			}
			table.addCell(addCells(" Pending Reason "));
			try {
				String getFirstLetter = customerRemarksItem[2].toString().substring(0, 1);
				String getRemainingLetter = customerRemarksItem[2].toString().substring(1,
						customerRemarksItem[2].toString().length());

				String firstLetter = getFirstLetter.toUpperCase();
				String remainingLetter = getRemainingLetter.toLowerCase();

				String fullName = firstLetter + remainingLetter;

				table.addCell(addCell(" " + fullName));
			} catch (Exception e) {
				table.addCell(addCell(" "));
			}
			table.completeRow();

			document.add(table);
		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		}

	}

	private void addEngInfo(Document document, long id) {
		try {
			PdfPTable table = new PdfPTable(2);
			table.setWidths(new int[] { 15, 15 });
			String query = "SELECT \r\n" + "	cfsr.`complain_attandby_eng_name` AS `Eng Name`,\r\n"
					+ "	cfsr.`eng_no` AS `Exicom Eng No`\r\n" + "		FROM `bt_commissioningfsr` cfsr\r\n"
					+ "		WHERE cfsr.`id` =?1";
			Query getQuery = entityManager.createNativeQuery(query);
			getQuery.setParameter(1, id);
			Object[] engInfoItem = (Object[]) getQuery.getSingleResult();

			String engFirstLetter = engInfoItem[0].toString().substring(0, 1);
			String engRemainingLetter = engInfoItem[0].toString().substring(1, engInfoItem[0].toString().length());

			String firstLetter = engFirstLetter.toUpperCase();
			String remainingLetter = engRemainingLetter.toLowerCase();

			String engName = firstLetter + remainingLetter;

			table.addCell(addCell(" Eng Name :: " + engName));

			table.addCell(addCell(" Exicom Eng No. :: " + engInfoItem[1].toString()));
			table.completeRow();
			document.add(table);

		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		}

	}

	private void addPendingReasonCustomerBucket(Document document, long id) {
		try {
			PdfPTable table = new PdfPTable(1);
			String query = "SELECT \r\n" + "	prcb.`type_value` AS `Pending Reason Customer Bucket`\r\n"
					+ "		FROM `bt_commissioningfsr` cfsr\r\n"
					+ "		JOIN  `bt_pending_reason_customer_bucket` prcb ON prcb.`id` =cfsr.`bt_pending_reason_customer_bucket_id`\r\n"
					+ " 	WHERE cfsr.`id`=?1";
			Query getQuery = entityManager.createNativeQuery(query);
			getQuery.setParameter(1, id);
			String pendingReasonCustomerBucket = (String) getQuery.getSingleResult();

			String pendingReasonCustomerBucketRemarkFirstLetter = pendingReasonCustomerBucket.substring(0, 1);
			String pendingReasonCustomerBucketRemainingLetter = pendingReasonCustomerBucket.substring(1,
					pendingReasonCustomerBucket.length());

			String firstLetter = pendingReasonCustomerBucketRemarkFirstLetter.toUpperCase();
			String remainingLetter = pendingReasonCustomerBucketRemainingLetter.toLowerCase();

			pendingReasonCustomerBucket = firstLetter + remainingLetter;

			table.addCell(addCell(" " + pendingReasonCustomerBucket));
			table.completeRow();

			document.add(table);
		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		}

	}

	private PdfPCell addCell(String text) {
		Phrase phrase = new Phrase();
		phrase.add(new Chunk(text, normalFont));
		PdfPCell cell = new PdfPCell(phrase);
		cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		cell.setVerticalAlignment(com.itextpdf.text.Element.ALIGN_MIDDLE);
//		cell.setBorderWidth(1);
		cell.setBorderWidthBottom(new Float(0.1));
		cell.setBorderWidthLeft(new Float(0.1));
		cell.setBorderWidthRight(new Float(0.1));
		cell.setBorderWidthTop(new Float(0.1));
		cell.setPaddingTop(5);
		cell.setPaddingBottom(5);

		return cell;
	}

	private PdfPCell addhead(String text) {
		Phrase phrase = new Phrase();
		phrase.add(new Chunk(text, normalFontBold));
		PdfPCell cell = new PdfPCell(phrase);
		cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		cell.setVerticalAlignment(com.itextpdf.text.Element.ALIGN_MIDDLE);
//		cell.setBorderWidth(Rectangle.NO_BORDER);
		cell.setBorderWidthBottom(new Float(0.1));
		cell.setBorderWidthLeft(new Float(0.1));
		cell.setBorderWidthRight(new Float(0.1));
		cell.setBorderWidthTop(new Float(0.1));
		cell.setPaddingTop(5);
		cell.setPaddingBottom(5);

		return cell;

	}

	private PdfPCell addCells(String text) {
		Phrase phrase = new Phrase();
		phrase.add(new Chunk(text, normalFontBold));
		PdfPCell cell = new PdfPCell(phrase);
		cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
		cell.setVerticalAlignment(com.itextpdf.text.Element.ALIGN_MIDDLE);
		cell.setBorderWidth(Rectangle.NO_BORDER);
		cell.setPaddingTop(5);
		cell.setPaddingBottom(5);

		return cell;
	}
}
