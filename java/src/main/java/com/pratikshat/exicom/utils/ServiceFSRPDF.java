package com.pratikshat.exicom.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

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

public class ServiceFSRPDF {

	private Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL, BaseColor.BLACK);
	private Font normalFontBold = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);
	private Font timesBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);
	private Font headingFont = new Font(Font.FontFamily.TIMES_ROMAN, 22, Font.BOLD);

	EntityManager entityManager = null;
	FileStorageProperties fileStorageProperties = null;

	String heading = "Field Service Report";

	public ServiceFSRPDF(EntityManager entityManager, FileStorageProperties fileStorageProperties) {

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
			addSubHeading(document, "1. FAULT REPORTED BY CUSTOMER");
			addFaultObservedByCustomer(document, id);
			addSubHeading(document, "2. SITE OBSERVATION ");
			addSiteObservation(document, id);
			addSubHeading(document, "3. ASSET MASTER UPDATE");
			addAssetMasterUpdate(document, id);
			addSubHeading(document, "4. FAULTY ASSET PARAMETERS");
			addFaultyAssetParameters(document, id);
			addSubHeading(document, "5. FAULT OBSERVED BY EXICOM ENGINEER");
			addFaultObservedByExicomEng(document, id);
			addSubHeading(document, "6. CORRECTIVE ACTION IN DETAIL");
			addCorrectiveActionInDetails(document, id);
			addSubHeading(document, "7. RESOLUTION METHOD");
			addResolutionMethod(document, id);
			addSubHeading(document, "8. SOLUTION IMPARTED");
			addSolutionImparted(document, id);
			addSubHeading(document, " ");
			addCaseStatus(document, id);
			addSubHeading(document, " ");
			addEngInfo(document, id);

			addSubHeading(document, "10. CUSTOMER REMARKS (If Any):");
			addCustomerRemarksIfAny(document, id);

			document.close();

		} catch (DocumentException ex) {
			throw new BadRequestException("error in creating pdf");
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
			cellImage.setBorder(Rectangle.BOTTOM);
			header.addCell(image);

			Phrase headPhrase = new Phrase();
			headPhrase.add(new Chunk(heading, headingFont));
			PdfPCell headingCell = new PdfPCell(headPhrase);
			headingCell.setBorder(Rectangle.NO_BORDER);
			headingCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			headingCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			headingCell.setPadding(7);
//			headingTable.addCell(headingCell);
//			headingTable.completeRow();
			header.addCell(headingCell);

			String query = "SELECT \r\n" + "	fsr.`manual_id` AS `Service FSR Manual Id`\r\n"
					+ "		FROM `bt_fsr` fsr\r\n" + "		WHERE fsr.`id` =?1";
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
			String query = "SELECT  	\r\n"
					+ "	ca.`manual_id` AS `Case No`,\r\n"
					+ "	CONCAT(cdt.`first_name`,' ',cdt.`last_name` ) AS `Customer Name`,\r\n"
					+ "	sd.`circle_circle_name` AS `Circle Name`, 	sd.`cmp` AS `CMP`,\r\n"
					+ "	sd.`manual_id` AS `Site Id`, 	sd.`site_name` AS `Site Name`,\r\n"
					+ "	CONCAT( emp.`first_name`,' ',emp.`last_name` ) AS `Case Logged By`,\r\n"
					+ "	cdt.`mobile` `Contact Number`,\r\n"
					+ "	fsr.`contact_person_at_site` AS `Contact Person At Site`,\r\n"
					+ "	fsr.`contact_person_at_site_contact_no` AS `Contact Person At Site Contact No`,\r\n"
					+ "	DATE_FORMAT( ca.`created_date`, '%d/%m/%Y') AS `Date Of Case Generated`,\r\n"
					+ "	DATE_FORMAT(fsr.`date_of_site_visit`,'%d/%m/%Y' ) AS `Date Of Site Visit`,\r\n"
					+ "	cdt.`mobile` AS `Mobile` 	\r\n"
					+ "		FROM `bt_fsr` fsr\r\n"
					+ "		JOIN `bt_service_appointment` sa ON sa.`service_appointment_id` =fsr.`bt_service_appointment_service_appointment_id` \r\n"
					+ "		JOIN `bt_case` ca ON ca.`id`=sa.`case_details_id`\r\n"
					+ "		JOIN `employee_details` emp ON emp.`email_id` =ca.`owner_details_email_id`\r\n"
					+ "		JOIN `contact_details` cdt ON cdt.`contact_id` =ca.`contact_person_contact_id`\r\n"
					+ "		JOIN `bt_site_details` sd ON sd.`id` =ca.`site_id`\r\n"
					+ "		JOIN `circle_details` crl ON crl.`circle_name` =sd.`circle_circle_name`\r\n"
					+ "		WHERE fsr.`id`=?1";
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
			table.addCell(addCell(" " + generalInformation[7].toString()));
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

//			
//			PdfPTable table = new PdfPTable(3);
//			table.setWidths(new int[] {10, 1,10 });
//
//			table.addCell(addhead(" Case No :: "+ generalInformation[0].toString()));
//			table.addCell(addCells(" "));
//			table.addCell(addhead(" Customer's Name :: "+ generalInformation[1].toString()));
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
//			table.addCell(addhead(" Contact Person at Site :: "+ generalInformation[8].toString()));
//			table.addCell(addCells(" "));
//			table.addCell(addhead(" Site Eng. Contact No. :: "+ generalInformation[9].toString()));
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

	private void addFaultObservedByCustomer(Document document, long id) {
		try {
			String query = "SELECT \r\n"
					+ "	GROUP_CONCAT(cpr.`problem_name` SEPARATOR ' , ' ) AS `Fault Observed By Customer`\r\n"
					+ "		FROM `bt_fsr` fsr\r\n"
					+ "		JOIN `bt_service_appointment` sa ON sa.`service_appointment_id` =fsr.`bt_service_appointment_service_appointment_id` \r\n"
					+ "		JOIN `bt_case` ca ON ca.`id`=sa.`case_details_id`\r\n"
					+ "		JOIN `bt_case_problem_reported` cpr ON cpr.`case_details_id` =ca.`id`\r\n"
					+ "		WHERE fsr.`id` = ?1";
			Query getQuery = entityManager.createNativeQuery(query);
			getQuery.setParameter(1, id);
			String faultObservedByCustomer = (String) getQuery.getSingleResult();

//			String getFirstLetter = faultObservedByCustomer.substring(0, 1);
//			String getRemainingLetter = faultObservedByCustomer.substring(1,faultObservedByCustomer.length());
//			
//			String firstLetter = getFirstLetter.toUpperCase();
//			String remainingLetter = getRemainingLetter.toLowerCase();
//			
//			String fullName = firstLetter + remainingLetter;
//			
			PdfPTable table = new PdfPTable(1);
			table.addCell(addCell(" " + faultObservedByCustomer));
			table.completeRow();

			document.add(table);
		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		}

	}

	private void addSiteObservation(Document document, long id) {
		try {
			String query = "SELECT \r\n" + "	st.`type_name` AS `Site Type`,\r\n"
					+ "	sec.`type_name` AS `Site Environmental Condition`,\r\n"
					+ "	fsr.`earthing_status` AS `Earthing Status`,\r\n"
					+ "	cm.`type_value` AS `Cooling Method`,\r\n"
					+ "	fsr.`ambient_temperature` AS `Ambient Temp`,\r\n"
					+ "	ppm.`type_value` AS `Power Plant Make`,\r\n"
					+ "	fsr.`total_equipmentdcload` AS `Total Equipment DC Load`,\r\n"
					+ "	fsr.`active_rectifiers` AS `Active Rectifiers`,\r\n"
					+ "	fsr.`smps_to_cbms_vol_gap` AS `SMPS To CBMS Vol Gap`,\r\n"
					+ "	fsr.`cbms_to_bat_vol_gap` AS `CBMS to Bat Vol Gap`\r\n" + "		FROM `bt_fsr` fsr\r\n"
					+ "		JOIN `bt_site_type` st ON st.`id` =fsr.`bt_site_type_id`\r\n"
					+ "		JOIN `bt_site_environmental_conditions` sec ON sec.`id` =fsr.`bt_site_environmental_conditions_id`\r\n"
					+ "		JOIN `bt_cooling_method` cm ON cm.`id` =fsr.`bt_cooling_method_id` \r\n"
					+ "		JOIN `bt_power_plant_make` ppm ON ppm.`id` =fsr.`bt_power_plant_make_id`\r\n"
					+ "		WHERE fsr.`id`=?1";
			Query getQuery = entityManager.createNativeQuery(query);
			getQuery.setParameter(1, id);
			Object[] siteObservation = (Object[]) getQuery.getSingleResult();
			PdfPTable table = new PdfPTable(5);
			table.setWidths(new int[] { 8, 10, 1, 8, 10 });

			table.addCell(addhead(" Site Type"));
			table.addCell(addCell(" " + siteObservation[0].toString()));
			table.addCell(addCells(" "));
			table.addCell(addhead(" Site Environmental Conditions"));
			table.addCell(addCell(" " + siteObservation[1].toString()));
			table.completeRow();

			table.addCell(addhead(" Earthing Status"));
			table.addCell(addCell(" " + siteObservation[2].toString()));
			table.addCell(addCells(" "));
			table.addCell(addhead(" Cooling Method"));
			table.addCell(addCell(" " + siteObservation[3].toString()));
			table.completeRow();

			table.addCell(addhead(" Ambient Temperature (Deg C)"));
			table.addCell(addCell(" " + siteObservation[4].toString()));
			table.addCell(addCells(" "));
			table.addCell(addhead(" Power Plant Make"));
			table.addCell(addCell(" " + siteObservation[5].toString()));
			table.completeRow();

			table.addCell(addhead(" Total Equipment DC Load(Amp.)"));
			table.addCell(addCell(" " + siteObservation[6].toString()));
			table.addCell(addCells(" "));
			table.addCell(addhead(" Active Rectifiers"));
			table.addCell(addCell(" " + siteObservation[7].toString()));
			table.completeRow();

			table.addCell(addhead(" SMPS To CBMS Vol Gap"));
			table.addCell(addCell(" " + siteObservation[8].toString()));
			table.addCell(addCells(" "));
			table.addCell(addhead(" CBMS To Bat Vol Gap"));
			table.addCell(addCell(" " + siteObservation[9].toString()));
			table.completeRow();

			document.add(table);
		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		}

	}

	private void addAssetMasterUpdate(Document document, long id) {
		try {

			PdfPTable table = new PdfPTable(8);
			table.setWidths(new int[] { 3, 4, 4, 4, 2, 4, 4, 3 });
			List<String> headers = new ArrayList<>();
			headers.add("EQUIPMENT TYPE");
			headers.add("PART CODE");
			headers.add("MODEL NO");
			headers.add("EQUIPMENT SR. NO.");
			headers.add("S/W VERS.");
			headers.add("WARRANTY STATUS");
			headers.add("INSTALLATION DATE");
			headers.add("CURRENT CONDITION");

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

			String query = "SELECT \r\n" + "		astm.`equipment_type` AS `Equipment Type`,\r\n"
					+ "		prd.`manual_id` AS `Part Code`,\r\n"
					+ "		prd.`product_description` AS `Model No`,\r\n"
					+ "		astm.`equipment_sr_no` AS `Equipment Sr No`,\r\n"
					+ "		astm.`software_version` AS `Software Version`,\r\n"
					+ "		(CASE WHEN awd.`warranty_expiry_date` > NOW()  THEN 'Under Warranty'\r\n"
					+ "		ELSE 'Without Warranty' END)AS warrantyStatus ,\r\n"
					+ "		DATE_FORMAT(awd.`install_date`,'%d/%m/%Y' ) AS `Installation Date`,\r\n"
					+ "		astm.`current_condition` AS `Current Condition`\r\n"
					+ "			FROM `bt_asset_master_update` astm\r\n"
					+ "			 JOIN `bt_assets` ast ON ast.`id` =astm.`bt_assets_id`\r\n"
					+ "			JOIN `bt_products` prd ON prd.`id` =ast.`products_id`\r\n"
					+ "			JOIN `bt_assets_warranty_details` awd ON awd.`id` =ast.`id`\r\n"
					+ "			JOIN `bt_fsr` fsr ON fsr.`id` =astm.`bt_fsr_id`\r\n"
					+ "			WHERE fsr.`id`=?1\r\n" + "			ORDER BY CAST(astm.`sr_no` AS SIGNED INTEGER) ASC";
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

	private void addFaultyAssetParameters(Document document, long id) {
		try {
			PdfPTable table = new PdfPTable(13);
			table.setWidths(new int[] { 3, 2, 2, 2, 4, 3, 2, 2, 2, 2, 2, 2, 3 });
			List<String> headers = new ArrayList<>();
			headers.add("");
			headers.add("Vol");
			headers.add("SOC");
			headers.add("SOH");
			headers.add("Active Alarms");
			headers.add("Problem Since");
			headers.add("Min Cell Voltage");
			headers.add("Max Cell Voltage");
			headers.add("Val Gap");
			headers.add("Battery Temp");
			headers.add("Last Cycle Del");
			headers.add("Log Status");
			headers.add("Battery Required At BRC");
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
			String query = "SELECT\r\n" + "	flt.`equipment_name` AS `Equipment Name`,\r\n"
					+ "	flt.`voltage` AS `Vol`,\r\n" + "	flt.`soc` AS `SOC`,\r\n" + "	flt.`soh` AS `SOH`,\r\n"
					+ "	aa.`type_name` AS `Active Alarm`,\r\n"
					+ "	DATE_FORMAT( flt.`problem_since`, '%d/%m/%Y')AS `Problem Since`,\r\n"
					+ "	flt.`min_cell_voltage` AS `Min Cell Voltage`,\r\n"
					+ "	flt.`max_cell_voltage` AS `Max Cell Voltage`,\r\n" + "	flt.`vol_gap` AS `Vol Gap`,\r\n"
					+ "	flt.`battery_temp` AS `Battery Temp`,\r\n" + "	flt.`last_cycle_del` AS `Last Cycle Del`,\r\n"
					+ "	flt.`log_status` AS `Log Status`,\r\n"
					+ "	flt.`battery_required_atbrc` AS `Battery Required At BRC`\r\n"
					+ "		FROM `bt_faulty_asset_parameters` flt\r\n"
					+ "		JOIN `active_alarms` aa ON aa.`id` =flt.`active_alarms_id`\r\n"
					+ "		JOIN `bt_fsr` fsr ON fsr.`id` =flt.`bt_fsr_id`\r\n" + "			WHERE fsr.`id`=?1";
			Query getQuery = entityManager.createNativeQuery(query);
			getQuery.setParameter(1, id);
			List<Object[]> faultyAssetParametersItem = getQuery.getResultList();

			for (int i = 0; i < faultyAssetParametersItem.size(); i++) {

				Object[] objectArray = faultyAssetParametersItem.get(i);

				for (int j = 0; j < objectArray.length; j++) {
					PdfPCell hcell = new PdfPCell(new Phrase(objectArray[j].toString(), normalFont));
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

			document.add(table);
		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		}

	}

	private void addFaultObservedByExicomEng(Document document, long id) {
		try {
			PdfPTable table = new PdfPTable(1);
			String query = "SELECT \r\n"
					+ "	fsr.`fault_observed_by_exicom_eng` AS `Fault Observed By Exicom Eng` \r\n"
					+ "		FROM `bt_fsr`fsr WHERE fsr.`id`=?1";
			Query getQuery = entityManager.createNativeQuery(query);
			getQuery.setParameter(1, id);
			String faultObservedByExicomEngItem = (String) getQuery.getSingleResult();

			String getFirstLetter = faultObservedByExicomEngItem.substring(0, 1);
			String getRemainingLetter = faultObservedByExicomEngItem.substring(1,
					faultObservedByExicomEngItem.length());

			String firstLetter = getFirstLetter.toUpperCase();
			String remainingLetter = getRemainingLetter.toLowerCase();

			String fullName = firstLetter + remainingLetter;

			table.addCell(addCell(" " + fullName));
			table.completeRow();

			document.add(table);
		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		}

	}

	private void addCorrectiveActionInDetails(Document document, long id) {
		try {
			PdfPTable table = new PdfPTable(1);
			String query = "SELECT \r\n"
					+ "	fsr.`corrective_actions_in_details` AS `Corrective Action In Detail`\r\n"
					+ "		FROM `bt_fsr`fsr	WHERE fsr.`id` =?1";
			Query getQuery = entityManager.createNativeQuery(query);
			getQuery.setParameter(1, id);
			String correctiveActionInDetailsItem = (String) getQuery.getSingleResult();

			String getFirstLetter = correctiveActionInDetailsItem.substring(0, 1);
			String getRemainingLetter = correctiveActionInDetailsItem.substring(1,
					correctiveActionInDetailsItem.length());

			String firstLetter = getFirstLetter.toUpperCase();
			String remainingLetter = getRemainingLetter.toLowerCase();

			String fullName = firstLetter + remainingLetter;

			table.addCell(addCell(" " + fullName));
			table.completeRow();
			document.add(table);
		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		}

	}

	private void addResolutionMethod(Document document, long id) {
		try {
			PdfPTable table = new PdfPTable(1);
			String query = "SELECT \r\n" + "	rm.`name` AS `Resolution Method`	FROM `bt_fsr` fsr\r\n"
					+ "		JOIN `bt_resolution_method` rm ON rm.`id` =fsr.`bt_resolution_method_id`\r\n"
					+ "		WHERE fsr.`id` =?1";
			Query getQuery = entityManager.createNativeQuery(query);
			getQuery.setParameter(1, id);
			String resolutionMethodItem = (String) getQuery.getSingleResult();

			table.addCell(addCell(" " + resolutionMethodItem));
			table.completeRow();
			document.add(table);
		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		}
	}

	private void addSolutionImparted(Document document, long id) {
		try {
			PdfPTable table = new PdfPTable(1);
			String query = "SELECT \r\n" + "	sol.`name` AS `Solution Imparted`	FROM `bt_fsr` fsr\r\n"
					+ "		JOIN `bt_solution_imparted` sol ON sol.`id` =fsr.`bt_solution_imparted_id`\r\n"
					+ "		WHERE fsr.`id` =?1";
			Query getQuery = entityManager.createNativeQuery(query);
			getQuery.setParameter(1, id);
			String solutionImpartedItem = (String) getQuery.getSingleResult();

			table.addCell(addCell(" " + solutionImpartedItem));
			table.completeRow();
			document.add(table);
		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		}
	}

	private void addCaseStatus(Document document, long id) {
		try {
			PdfPTable table = new PdfPTable(6);
			table.setWidths(new int[] { 3, 3, 4, 3, 4, 6 });
			String query = "SELECT \r\n" + "	fsr.`case_status` AS `Case Status`,\r\n"
					+ "	fsr.`pending_bucket` AS `Pending Bucket`,\r\n"
					+ "	(CASE WHEN fsr.`pending_bucket`='Exicom' \r\n" + "	THEN fsr.`pending_reason_exicom_bucket`\r\n"
					+ "	ELSE prcb.`type_value` END ) AS `Pending Reason`\r\n" + "		FROM `bt_fsr` fsr\r\n"
					+ "		LEFT JOIN `bt_pending_reason_customer_bucket` prcb ON prcb.`id` =fsr.`bt_pending_reason_customer_bucket_id`\r\n"
					+ "		WHERE fsr.`id` =?1";
			Query getQuery = entityManager.createNativeQuery(query);
			getQuery.setParameter(1, id);
			Object[] customerRemarksItem = (Object[]) getQuery.getSingleResult();
			table.addCell(addCells("9. Case Status "));
			table.addCell(addCell(" " + customerRemarksItem[0].toString()));

			String pendingBucket = customerRemarksItem[1].toString();

			table.addCell(addCells(" Pending Bucket (If Any) "));
			try {
			table.addCell(addCell(" " + pendingBucket));
			} catch(Exception e) {
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
			}catch(Exception e) {
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
			String query = "SELECT \r\n" + "	fsr.`complain_attend_by_eng_name` AS `Eng Name`,\r\n"
					+ "	fsr.`engineer_no` AS `Exicom Eng No`\r\n" + "		FROM `bt_fsr` fsr\r\n"
					+ "		WHERE fsr.`id` =?1";
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

	private void addCustomerRemarksIfAny(Document document, long id) {
		try {
			PdfPTable table = new PdfPTable(1);
			String query = "SELECT \r\n" + "	fsr.`customer_remarks` AS `Customer Remarks`\r\n"
					+ "		FROM `bt_fsr` fsr\r\n" + "		WHERE fsr.`id` =?1";
			Query getQuery = entityManager.createNativeQuery(query);
			getQuery.setParameter(1, id);
			String customerRemarksItem = (String) getQuery.getSingleResult();
			try {
			String getFirstLetter = customerRemarksItem.substring(0, 1);
			String getRemainingLetter = customerRemarksItem.substring(1, customerRemarksItem.length());

			String firstLetter = getFirstLetter.toUpperCase();
			String remainingLetter = getRemainingLetter.toLowerCase();

			String fullName = firstLetter + remainingLetter;

			table.addCell(addCell(" " + fullName));
			} catch (Exception e) {
				table.addCell(" ");
			}
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
