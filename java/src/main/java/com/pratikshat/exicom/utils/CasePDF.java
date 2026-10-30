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

public class CasePDF {

	private Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL, BaseColor.BLACK);
	private Font normalFontBold = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);
	private Font timesBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);
	private Font headingFont = new Font(Font.FontFamily.TIMES_ROMAN, 22, Font.BOLD);

	EntityManager entityManager = null;
	FileStorageProperties fileStorageProperties = null;

	String heading = "Case";

	public CasePDF(EntityManager entityManager, FileStorageProperties fileStorageProperties) {

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
			
			addSubHeading(document, "1. CASE INFORMATION");
			addGeneralInformation(document, id);
//			addFaultObservedByCustomer(document, id);
			addSubHeading(document, "2. Additional Information");
			additionalinfo(document, id);
			addSubHeading(document, "3. SITE DETAIL");
			addSiteObservation(document, id);
			addSubHeading(document, "4. Problem Discription");
			problemDescption(document, id);
			addSubHeading(document, "5. ASSIGNMENT DETAILS");
			AssignmentDetails(document, id);
			addSubHeading(document, "6. PROOF OF DELIVERY(FOR SCH USE ONLY)");
			podschonly(document, id);
        	addSubHeading(document, "7. CUSTOMER PO AND INVOICE DETAILS");
        	
			addSubHeading(document, "8. VENDOR INVOICE DETAILS");
			VendorInvDetails(document,id);
			addSubHeading(document, "9. SYSTEM INFORMATION");
			sysInfo(document,id);
			addSubHeading(document,"REALTED > SERVICE APPOINTMENT");
			realatedSA(document,id);
//			addSubHeading(document, "10. SYSTEM INFORMATION");
//			addSolutionImparted(document, id);
//			addSubHeading(document, " ");
//			addCaseStatus(document, id);
//			addSubHeading(document, " ");
//			addEngInfo(document, id);

//			addSubHeading(document, "10. CUSTOMER REMARKS (If Any):");
//			addCustomerRemarksIfAny(document, id);

			document.close();

		} catch (DocumentException ex) {
			throw new BadRequestException("error in creating pdf");
		}

		return new ByteArrayInputStream(out.toByteArray());
	}

	private void addHeader(Document document, long id) {
		try {
			Path fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
			Image image = Image.getInstance(fileStorageLocation + "/" + "logo.png");

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

			String query = "SELECT cas.`manual_id` AS `Case Manual Id` FROM `bt_case` cas WHERE cas.`id`=?1";
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
			String query = "SELECT lg.`legal_entity_name` AS `Service Center Name`, cs.`manual_id` AS `Case Number`,DATE(cs.`created_date`) AS `Date Opened`, DATE(cs.`close_date`) \r\n" + 
					"AS `Close Date`,\r\n" + 
					"csst.`case_status_name` AS `Case Status`, cst.`case_types_name` AS `Case Type`,crd.`case_reason_details_name` AS `Case Reason`,\r\n" + 
					"cssd.`case_stage_name` AS `Case Stage`,\r\n" + 
					"\r\n" + 
					"(SELECT DATE(sa.`visit_date`) FROM `bt_service_appointment` sa WHERE sa.`case_details_id`=cs.`id` ORDER BY sa.visit_date DESC LIMIT 1) AS `Date of Visit`, \r\n" + 
					"pr.`pending_responsibility_name` AS `Pending Responsibility`, src.`name` AS `Engineer Name`,cd.`employee_code` AS `Employee Code`,\r\n" + 
					" st.`circle_circle_name` AS ` Circle`,ssa.`name` AS `Zone/SSA`,st.`manual_id` AS `Site-ID`, st.`site_name` AS `Exchange/Site Name`, \r\n" + 
					" ast.`manual_id` AS `Asset Serial No`, prd.`pdoduct_description` AS `Asset Description`, \r\n" + 
					" (SELECT GROUP_CONCAT( DISTINCT `problem_name`) FROM `bt_case_problem_reported` WHERE `case_details_id`=cs.id ) AS `Problem Reported`, \r\n" + 
					" cs.`other_problem_reported` AS `Other Problem Reported`,sa.`fault_observed` AS `Fault Observed`, \r\n" + 
					" sa.`remark`AS `Remark`,\r\n" + 
					" CONCAT(emp.`first_name`) AS `Owner`, CONCAT_WS(cstmr.`first_name`,\" \",NULLIF(TRIM(cstmr.`middle_name`),''),\" \",cstmr.`last_name`) AS `Customer Name`\r\n" + 

					"FROM `bt_case` cs \r\n" + 
					"LEFT JOIN `bt_assets` ast ON ast.`id`=cs.`bt_assets_id`\r\n" + 
					"LEFT JOIN `bt_products` prd ON prd.`id`= ast.`products_id` \r\n" + 
					"JOIN `case_status_details` csst ON csst.`id`=cs.`status_id` \r\n" + 
					"JOIN `case_stage_details` cssd ON cssd.`id`=cs.`stage_id` \r\n" + 
					"JOIN `case_reason_details` csr ON csr.`reason_id`=cs.`case_reason_reason_id` \r\n" + 
					"LEFT JOIN `service_recource` src ON src.`service_resource_id` =cs.`engineer_service_resource_id` \r\n" + 
					"JOIN `case_types` cst ON cst.`case_type_id` =cs.`case_type_case_type_id`\r\n" + 
					" JOIN `bt_site_details` st ON st.`id`=cs.`site_id`\r\n" + 
					"  JOIN `accounts` acts ON acts.`sap_customer_number`=st.`account_sap_customer_number` \r\n" + 
					"  JOIN `ssa` ssa ON ssa.`id`=st.`ssa_id` \r\n" + 
					"  JOIN `customer_deatils` cdst ON cdst.`customer_id`=acts.`parent_customer_customer_id` \r\n" + 
					"  JOIN `contact_details`cd ON cd.`contact_id`=cs.`contact_person_contact_id` \r\n" + 
					"  LEFT JOIN `legal_entity` lg ON lg.`legal_entity_id` = cs.`service_center_legal_entity_id`\r\n" + 
					"   JOIN `pending_responsibility` pr ON pr.`pending_responsibility_id`=cs.`pending_responsibility_pending_responsibility_id` \r\n" + 
					"   JOIN `case_reason_details` crd ON crd.`reason_id`=cs.`case_reason_reason_id` \r\n" + 
					"   LEFT JOIN `bt_service_appointment` sa ON sa.`case_details_id`=cs.`id`\r\n" + 
					"   JOIN `employee_details` emp ON emp.`email_id`=cs.`owner_details_email_id`\r\n" + 
					"   JOIN  `contact_details` cstmr ON cstmr.`contact_id`=cs.`contact_person_contact_id`"+
					"	WHERE cs.`id`=?1";

			Query getQuery = entityManager.createNativeQuery(query);
			getQuery.setParameter(1, id);
			Object[] generalInformation = (Object[]) getQuery.getSingleResult();

			PdfPTable table = new PdfPTable(5);
			table.setWidths(new int[] { 5, 10, 1, 5, 10 });
			table.addCell(addhead(" Case No. "));
			table.addCell(addCell( ""+generalInformation[1].toString()));
			table.addCell(addCells(" "));
			table.addCell(addhead(" Case Owner"));
			table.addCell(addCell(" " +generalInformation[22].toString()));
			table.completeRow();

			table.addCell(addhead("Pre Registration Number"));
			table.addCell(addCell(" " +""));
			table.addCell(addCells(" "));
			table.addCell(addhead("Case Reason"));
			table.addCell(addCell(" " +generalInformation[6].toString()));
			table.completeRow();
			
			table.addCell(addhead("Type"));
			table.addCell(addCell(" "+generalInformation[5].toString()));
			table.addCell(addCells(" "));
			table.addCell(addhead("Customer Mobile"));
			table.addCell(addCell(" " + "987463210"));
			table.completeRow();
			
			table.addCell(addhead("Date Of Visit"));
			try {
				table.addCell(addCell(" "+"15-12-2021"));	
			}
			catch(Exception ex) {
				table.addCell(" ");
			}
			
			table.addCell(addCells(" "));
			table.addCell(addhead("Customer Name"));
			table.addCell(addCell(" " +generalInformation[23].toString()));
			table.completeRow();

			table.addCell(addhead(" Call  Center Rep. Name "));
			table.addCell(addCell(" " +"CC"));
			table.addCell(addCells(" "));
			table.addCell(addhead(" Date time closed "));
			try {
				table.addCell(addCell(" " +generalInformation[3].toString()));
			}catch(Exception ex) {
				table.addCell(addCell(" " +""));
			}
		
			table.completeRow();

			
			table.addCell(addhead(" Date time opened "));
			table.addCell(addCell(" " +generalInformation[2].toString()));
			table.addCell(addCells(" "));
			table.addCell(addhead(" Vendor Name "));
			table.addCell(addCell(" " +""));
			table.completeRow();

			
			table.addCell(addhead(" RJTIL No "));
			table.addCell(addCell(" " +"RJTIL0025"));
			table.addCell(addCells(" "));
			table.addCell(addhead(" RMA No"));
			table.addCell(addCell(" " +"RMA2563333222"));
			table.completeRow();

			
			table.addCell(addhead("RMA Status"));
			table.addCell(addCell(" " +""));
			table.addCell(addCells(" "));
			table.addCell(addhead("RMA Required"));
			table.addCell(addCell(" " +"true"));
			table.completeRow();

//shagufta 
//			table.addCell(addhead(" Circle/State "));
//			table.addCell(addCell(" " + generalInformation[2].toString()));
//			table.addCell(addCells(" "));
//			table.addCell(addhead(" CMP "));
//			try {
//				table.addCell(addCell(" " + generalInformation[3].toString()));
//			} catch (Exception e) {
//				table.addCell(" ");
//			}
//			table.completeRow();
//
//			table.addCell(addhead(" Site ID "));
//			table.addCell(addCell(" " + generalInformation[4].toString()));
//			table.addCell(addCells(" "));
//			table.addCell(addhead(" Site Name "));
//			table.addCell(addCell(" " + generalInformation[5].toString()));
//			table.completeRow();
//
//			table.addCell(addhead(" Case Logged By "));
//			table.addCell(addCell(" " + generalInformation[6].toString()));
//			table.addCell(addCells(" "));
//			table.addCell(addhead(" Contact Number "));
//			table.addCell(addCell(" " + generalInformation[7].toString()));
//			table.completeRow();
//
//			table.addCell(addhead(" Contact Person At Site "));
//			table.addCell(addCell(" " + generalInformation[8].toString()));
//			table.addCell(addCells(" "));
//			table.addCell(addhead(" Site Eng Contact No. "));
//			table.addCell(addCell(" " + generalInformation[9].toString()));
//			table.completeRow();
//
//			table.addCell(addhead(" Date of Case Generated "));
//			table.addCell(addCell(" " + generalInformation[10].toString()));
//			table.addCell(addCells(" "));
//			table.addCell(addhead(" Date of Site Visit "));
//			table.addCell(addCell(" " + generalInformation[11].toString()));
//			table.completeRow();
//shagufta
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

	private void customerpoInvoice(Document document, long id) {
		try {
			PdfPTable table = new PdfPTable(5);
			
			table.setWidths(new int[] { 5, 10, 1, 5, 10 });
			String query = "SELECT lg.`legal_entity_name` AS `Service Center Name`, cs.`manual_id` AS `Case Number`,DATE(cs.`created_date`) AS `Date Opened`, DATE(cs.`close_date`) \r\n" + 
					"AS `Close Date`,\r\n" + 
					"csst.`case_status_name` AS `Case Status`, cst.`case_types_name` AS `Case Type`,crd.`case_reason_details_name` AS `Case Reason`,\r\n" + 
					"cssd.`case_stage_name` AS `Case Stage`,\r\n" + 
					"\r\n" + 
					"(SELECT DATE(sa.`visit_date`) FROM `bt_service_appointment` sa WHERE sa.`case_details_id`=cs.`id` ORDER BY sa.visit_date DESC LIMIT 1) AS `Date of Visit`, \r\n" + 
					"pr.`pending_responsibility_name` AS `Pending Responsibility`, src.`name` AS `Engineer Name`,cd.`employee_code` AS `Employee Code`,\r\n" + 
					" st.`circle_circle_name` AS ` Circle`,ssa.`name` AS `Zone/SSA`,st.`manual_id` AS `Site-ID`, st.`site_name` AS `Exchange/Site Name`, \r\n" + 
					" ast.`manual_id` AS `Asset Serial No`, prd.`pdoduct_description` AS `Asset Description`, \r\n" + 
					" (SELECT GROUP_CONCAT( DISTINCT `problem_name`) FROM `bt_case_problem_reported` WHERE `case_details_id`=cs.id ) AS `Problem Reported`, \r\n" + 
					" cs.`other_problem_reported` AS `Other Problem Reported`,sa.`fault_observed` AS `Fault Observed`, \r\n" + 
					" sa.`remark`AS `Remark`,\r\n" + 
					" CONCAT(emp.`first_name`) AS `Owner`, CONCAT_WS(cstmr.`first_name`,\" \",NULLIF(TRIM(cstmr.`middle_name`),''),\" \",cstmr.`last_name`) AS `Customer Name`\r\n" + 
					"FROM `bt_case` cs \r\n" + 
					"LEFT JOIN `bt_assets` ast ON ast.`id`=cs.`bt_assets_id`\r\n" + 
					"LEFT JOIN `bt_products` prd ON prd.`id`= ast.`products_id` \r\n" + 
					"JOIN `case_status_details` csst ON csst.`id`=cs.`status_id` \r\n" + 
					"JOIN `case_stage_details` cssd ON cssd.`id`=cs.`stage_id` \r\n" + 
					"JOIN `case_reason_details` csr ON csr.`reason_id`=cs.`case_reason_reason_id` \r\n" + 
					"LEFT JOIN `service_recource` src ON src.`service_resource_id` =cs.`engineer_service_resource_id` \r\n" + 
					"JOIN `case_types` cst ON cst.`case_type_id` =cs.`case_type_case_type_id`\r\n" + 
					" JOIN `bt_site_details` st ON st.`id`=cs.`site_id`\r\n" + 
					"  JOIN `accounts` acts ON acts.`sap_customer_number`=st.`account_sap_customer_number` \r\n" + 
					"  JOIN `ssa` ssa ON ssa.`id`=st.`ssa_id` \r\n" + 
					"  JOIN `customer_deatils` cdst ON cdst.`customer_id`=acts.`parent_customer_customer_id` \r\n" + 
					"  JOIN `contact_details`cd ON cd.`contact_id`=cs.`contact_person_contact_id` \r\n" + 
					"  LEFT JOIN `legal_entity` lg ON lg.`legal_entity_id` = cs.`service_center_legal_entity_id`\r\n" + 
					"   JOIN `pending_responsibility` pr ON pr.`pending_responsibility_id`=cs.`pending_responsibility_pending_responsibility_id` \r\n" + 
					"   JOIN `case_reason_details` crd ON crd.`reason_id`=cs.`case_reason_reason_id` \r\n" + 
					"   LEFT JOIN `bt_service_appointment` sa ON sa.`case_details_id`=cs.`id`\r\n" + 
					"   JOIN `employee_details` emp ON emp.`email_id`=cs.`owner_details_email_id`\r\n" + 
					"   JOIN  `contact_details` cstmr ON cstmr.`contact_id`=cs.`contact_person_contact_id`"+
					"	WHERE cs.`id`=?1";
			Query getQuery = entityManager.createNativeQuery(query);
			getQuery.setParameter(1, id);
			Object[] engInfoItem = (Object[]) getQuery.getSingleResult();

			table.addCell(addhead("PO Number"));
			table.addCell(addCell(" " + "PO1512"));
			table.addCell(addCells(" "));
			table.addCell(addhead("PO Date"));
			table.addCell(addCell(" " + "2021-15-12"));
			table.completeRow();
			
			table.addCell(addhead("Invoice Number"));
			table.addCell(addCell(" " + "INV-15122021111"));
			table.addCell(addCells(" "));
			table.addCell(addhead("Invoice Date"));
			table.addCell(addCell(" " + "2021-15-12"));
			table.completeRow();
			
			table.addCell(addhead("PO Amount"));
			table.addCell(addCell(" " + "25600"));
			table.addCell(addCells(" "));
			table.addCell(addhead("Line Item"));
			table.addCell(addCell(" " + "2"));
			table.completeRow();



			document.add(table);

		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		}

	}

	private void addSiteObservation(Document document, long id) {
		try {
			String query = "SELECT lg.`legal_entity_name` AS `Service Center Name`, cs.`manual_id` AS `Case Number`,DATE(cs.`created_date`) AS `Date Opened`, DATE(cs.`close_date`) \r\n" + 
					"AS `Close Date`,\r\n" + 
					"csst.`case_status_name` AS `Case Status`, cst.`case_types_name` AS `Case Type`,crd.`case_reason_details_name` AS `Case Reason`,\r\n" + 
					"cssd.`case_stage_name` AS `Case Stage`,\r\n" + 
					"\r\n" + 
					"(SELECT DATE(sa.`visit_date`) FROM `bt_service_appointment` sa WHERE sa.`case_details_id`=cs.`id` ORDER BY sa.visit_date DESC LIMIT 1) AS `Date of Visit`, \r\n" + 
					"pr.`pending_responsibility_name` AS `Pending Responsibility`, src.`name` AS `Engineer Name`,cd.`employee_code` AS `Employee Code`,\r\n" + 
					" st.`circle_circle_name` AS ` Circle`,ssa.`name` AS `Zone/SSA`,st.`manual_id` AS `Site-ID`, st.`site_name` AS `Exchange/Site Name`, \r\n" + 
					" ast.`manual_id` AS `Asset Serial No`, prd.`pdoduct_description` AS `Asset Description`, \r\n" + 
					" (SELECT GROUP_CONCAT( DISTINCT `problem_name`) FROM `bt_case_problem_reported` WHERE `case_details_id`=cs.id ) AS `Problem Reported`, \r\n" + 
					" cs.`other_problem_reported` AS `Other Problem Reported`,sa.`fault_observed` AS `Fault Observed`, \r\n" + 
					" sa.`remark`AS `Remark`,\r\n" + 
					" CONCAT(emp.`first_name`) AS `Owner`, CONCAT_WS(cstmr.`first_name`,\" \",NULLIF(TRIM(cstmr.`middle_name`),''),\" \",cstmr.`last_name`) AS `Customer Name`\r\n" + 
					"FROM `bt_case` cs \r\n" + 
					"LEFT JOIN `bt_assets` ast ON ast.`id`=cs.`bt_assets_id`\r\n" + 
					"LEFT JOIN `bt_products` prd ON prd.`id`= ast.`products_id` \r\n" + 
					"JOIN `case_status_details` csst ON csst.`id`=cs.`status_id` \r\n" + 
					"JOIN `case_stage_details` cssd ON cssd.`id`=cs.`stage_id` \r\n" + 
					"JOIN `case_reason_details` csr ON csr.`reason_id`=cs.`case_reason_reason_id` \r\n" + 
					"LEFT JOIN `service_recource` src ON src.`service_resource_id` =cs.`engineer_service_resource_id` \r\n" + 
					"JOIN `case_types` cst ON cst.`case_type_id` =cs.`case_type_case_type_id`\r\n" + 
					" JOIN `bt_site_details` st ON st.`id`=cs.`site_id`\r\n" + 
					"  JOIN `accounts` acts ON acts.`sap_customer_number`=st.`account_sap_customer_number` \r\n" + 
					"  JOIN `ssa` ssa ON ssa.`id`=st.`ssa_id` \r\n" + 
					"  JOIN `customer_deatils` cdst ON cdst.`customer_id`=acts.`parent_customer_customer_id` \r\n" + 
					"  JOIN `contact_details`cd ON cd.`contact_id`=cs.`contact_person_contact_id` \r\n" + 
					"  LEFT JOIN `legal_entity` lg ON lg.`legal_entity_id` = cs.`service_center_legal_entity_id`\r\n" + 
					"   JOIN `pending_responsibility` pr ON pr.`pending_responsibility_id`=cs.`pending_responsibility_pending_responsibility_id` \r\n" + 
					"   JOIN `case_reason_details` crd ON crd.`reason_id`=cs.`case_reason_reason_id` \r\n" + 
					"   LEFT JOIN `bt_service_appointment` sa ON sa.`case_details_id`=cs.`id`\r\n" + 
					"   JOIN `employee_details` emp ON emp.`email_id`=cs.`owner_details_email_id`\r\n" + 
					"   JOIN  `contact_details` cstmr ON cstmr.`contact_id`=cs.`contact_person_contact_id`"+
					"	WHERE cs.`id`=?1";
			Query getQuery = entityManager.createNativeQuery(query);
			getQuery.setParameter(1, id);
			Object[] siteObservation = (Object[]) getQuery.getSingleResult();
			PdfPTable table = new PdfPTable(5);
			table.setWidths(new int[] { 5, 10, 1, 5, 10 });

			table.addCell(addhead("Account Name"));
			table.addCell(addCell(" " + "RJIL"));
			table.addCell(addCells(" "));
			table.addCell(addhead("Customer"));
			table.addCell(addCell(" " +siteObservation[23].toString()));
			table.completeRow();

			table.addCell(addhead("Site "));
			table.addCell(addCell(" " +siteObservation[14].toString()));
			table.addCell(addCells(" "));
			table.addCell(addhead("Site Name"));
			table.addCell(addCell(" " + siteObservation[15].toString()));
			table.completeRow();

			table.addCell(addhead(" Asset Serial Number"));
			table.addCell(addCell(" " + siteObservation[16].toString()));
			table.addCell(addCells(" "));
			table.addCell(addhead(" Asset Discription"));
             try {			
    	      table.addCell(addCell(" " +siteObservation[17].toString()));}
               catch(Exception ex) {
	           table.addCell(addCell(" " +""));
                }
			table.completeRow();

			table.addCell(addhead("Missing Asset"));
			table.addCell(addCell(" " + ""));
			table.addCell(addCells(" "));
			table.addCell(addhead(" Asset Type Choice"));
			table.addCell(addCell(" " +""));
			table.completeRow();

			document.add(table);
		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		}

	}


	private void additionalinfo(Document document, long id) {
		try {
			PdfPTable table = new PdfPTable(5);
			table.setWidths(new int[] { 5, 10, 1, 5, 10 });
			String query = "SELECT lg.`legal_entity_name` AS `Service Center Name`, cs.`manual_id` AS `Case Number`,DATE(cs.`created_date`) AS `Date Opened`, DATE(cs.`close_date`) \r\n" + 
					"AS `Close Date`,\r\n" + 
					"csst.`case_status_name` AS `Case Status`, cst.`case_types_name` AS `Case Type`,crd.`case_reason_details_name` AS `Case Reason`,\r\n" + 
					"cssd.`case_stage_name` AS `Case Stage`,\r\n" + 
					"\r\n" + 
					"(SELECT DATE(sa.`visit_date`) FROM `bt_service_appointment` sa WHERE sa.`case_details_id`=cs.`id` ORDER BY sa.visit_date DESC LIMIT 1) AS `Date of Visit`, \r\n" + 
					"pr.`pending_responsibility_name` AS `Pending Responsibility`, src.`name` AS `Engineer Name`,cd.`employee_code` AS `Employee Code`,\r\n" + 
					" st.`circle_circle_name` AS ` Circle`,ssa.`name` AS `Zone/SSA`,st.`manual_id` AS `Site-ID`, st.`site_name` AS `Exchange/Site Name`, \r\n" + 
					" ast.`manual_id` AS `Asset Serial No`, prd.`pdoduct_description` AS `Asset Description`, \r\n" + 
					" (SELECT GROUP_CONCAT( DISTINCT `problem_name`) FROM `bt_case_problem_reported` WHERE `case_details_id`=cs.id ) AS `Problem Reported`, \r\n" + 
					" cs.`other_problem_reported` AS `Other Problem Reported`,sa.`fault_observed` AS `Fault Observed`, \r\n" + 
					" sa.`remark`AS `Remark`,\r\n" + 
					" CONCAT(emp.`first_name`) AS `Owner`, CONCAT_WS(cstmr.`first_name`,\" \",NULLIF(TRIM(cstmr.`middle_name`),''),\" \",cstmr.`last_name`) AS `Customer Name`\r\n" + 
					"FROM `bt_case` cs \r\n" + 
					"LEFT JOIN `bt_assets` ast ON ast.`id`=cs.`bt_assets_id`\r\n" + 
					"LEFT JOIN `bt_products` prd ON prd.`id`= ast.`products_id` \r\n" + 
					"JOIN `case_status_details` csst ON csst.`id`=cs.`status_id` \r\n" + 
					"JOIN `case_stage_details` cssd ON cssd.`id`=cs.`stage_id` \r\n" + 
					"JOIN `case_reason_details` csr ON csr.`reason_id`=cs.`case_reason_reason_id` \r\n" + 
					"LEFT JOIN `service_recource` src ON src.`service_resource_id` =cs.`engineer_service_resource_id` \r\n" + 
					"JOIN `case_types` cst ON cst.`case_type_id` =cs.`case_type_case_type_id`\r\n" + 
					" JOIN `bt_site_details` st ON st.`id`=cs.`site_id`\r\n" + 
					"  JOIN `accounts` acts ON acts.`sap_customer_number`=st.`account_sap_customer_number` \r\n" + 
					"  JOIN `ssa` ssa ON ssa.`id`=st.`ssa_id` \r\n" + 
					"  JOIN `customer_deatils` cdst ON cdst.`customer_id`=acts.`parent_customer_customer_id` \r\n" + 
					"  JOIN `contact_details`cd ON cd.`contact_id`=cs.`contact_person_contact_id` \r\n" + 
					"  LEFT JOIN `legal_entity` lg ON lg.`legal_entity_id` = cs.`service_center_legal_entity_id`\r\n" + 
					"   JOIN `pending_responsibility` pr ON pr.`pending_responsibility_id`=cs.`pending_responsibility_pending_responsibility_id` \r\n" + 
					"   JOIN `case_reason_details` crd ON crd.`reason_id`=cs.`case_reason_reason_id` \r\n" + 
					"   LEFT JOIN `bt_service_appointment` sa ON sa.`case_details_id`=cs.`id`\r\n" + 
					"   JOIN `employee_details` emp ON emp.`email_id`=cs.`owner_details_email_id`\r\n" + 
					"   JOIN  `contact_details` cstmr ON cstmr.`contact_id`=cs.`contact_person_contact_id`"+
					"	WHERE cs.`id`=?1";
			Query getQuery = entityManager.createNativeQuery(query);
			getQuery.setParameter(1, id);
			Object[] resolutionMethodItem =(Object[]) getQuery.getSingleResult();
//			table.setWidths(new int[] { 5, 10, 1, 5, 10 });

			table.addCell(addhead("Status"));
			table.addCell(addCell(" " +resolutionMethodItem[23].toString()));
			table.addCell(addCells(" "));
			table.addCell(addhead(" Stage"));
			table.addCell(addCell(" " + resolutionMethodItem[7].toString()));
			table.completeRow();

			table.addCell(addhead(" Case Origin"));
			table.addCell(addCell(" " + "Phone"));
			table.addCell(addCells(" "));
			table.addCell(addhead("Priority"));
			table.addCell(addCell(" " + "Medium"));
			table.completeRow();

			table.addCell(addhead("Pending Responsibility"));
			table.addCell(addCell(" " +"Exicom"));
			table.addCell(addCells(" "));
			table.addCell(addhead("SLA Time"));
			table.addCell(addCell(" " + "33 days 14 hours 23 mins"));
			table.completeRow();

			document.add(table);

		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		}
	}

	private void realatedSA(Document document, long id) {
		try {
			PdfPTable table = new PdfPTable(4);
			table.setWidths(new int[] { 4, 4, 4, 4 });
			List<String> headers = new ArrayList<>();
			headers.add("SA Manual Id");
			headers.add("Case Type");
			headers.add("Status");
			headers.add("Case reason");

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
			String query = "SELECT sa.`manual_id` AS ` SA Manual Id`,\r\n" + 
					"ty.`case_types_name` AS `Case Type` ,\r\n" + 
					"crd.`case_reason_details_name` AS `Case Reason`,\r\n" + 
					"sast.`status_name` AS `Status`\r\n" + 
					" FROM `bt_case` cs\r\n" + 
					" LEFT JOIN `bt_service_appointment` sa ON sa.`case_details_id`=cs.`id`\r\n" + 
					" JOIN `case_types` ty ON ty.`case_type_id`=cs.`case_type_case_type_id`\r\n" + 
					" JOIN `case_reason_details` crd ON crd.`reason_id`=cs.`case_reason_reason_id`\r\n" + 
					" JOIN `service_appointment_status` sast ON sast.`status_id`=sa.`status_status_id`\r\n" + 
					" WHERE cs.`id`=?1";
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

	private void problemDescption(Document document, long id) {
		try {
			String query = "SELECT lg.`legal_entity_name` AS `Service Center Name`, cs.`manual_id` AS `Case Number`,DATE(cs.`created_date`) AS `Date Opened`, DATE(cs.`close_date`) \r\n" + 
					"AS `Close Date`,\r\n" + 
					"csst.`case_status_name` AS `Case Status`, cst.`case_types_name` AS `Case Type`,crd.`case_reason_details_name` AS `Case Reason`,\r\n" + 
					"cssd.`case_stage_name` AS `Case Stage`,\r\n" + 
					"\r\n" + 
					"(SELECT DATE(sa.`visit_date`) FROM `bt_service_appointment` sa WHERE sa.`case_details_id`=cs.`id` ORDER BY sa.visit_date DESC LIMIT 1) AS `Date of Visit`, \r\n" + 
					"pr.`pending_responsibility_name` AS `Pending Responsibility`, src.`name` AS `Engineer Name`,cd.`employee_code` AS `Employee Code`,\r\n" + 
					" st.`circle_circle_name` AS ` Circle`,ssa.`name` AS `Zone/SSA`,st.`manual_id` AS `Site-ID`, st.`site_name` AS `Exchange/Site Name`, \r\n" + 
					" ast.`manual_id` AS `Asset Serial No`, prd.`pdoduct_description` AS `Asset Description`, \r\n" + 
					" (SELECT GROUP_CONCAT( DISTINCT `problem_name`) FROM `bt_case_problem_reported` WHERE `case_details_id`=cs.id ) AS `Problem Reported`, \r\n" + 
					" cs.`other_problem_reported` AS `Other Problem Reported`,sa.`fault_observed` AS `Fault Observed`, \r\n" + 
					" sa.`remark`AS `Remark`,\r\n" + 
					" CONCAT(emp.`first_name`) AS `Owner`, CONCAT_WS(cstmr.`first_name`,\" \",NULLIF(TRIM(cstmr.`middle_name`),''),\" \",cstmr.`last_name`) AS `Customer Name`\r\n" + 
					"FROM `bt_case` cs \r\n" + 
					"LEFT JOIN `bt_assets` ast ON ast.`id`=cs.`bt_assets_id`\r\n" + 
					"LEFT JOIN `bt_products` prd ON prd.`id`= ast.`products_id` \r\n" + 
					"JOIN `case_status_details` csst ON csst.`id`=cs.`status_id` \r\n" + 
					"JOIN `case_stage_details` cssd ON cssd.`id`=cs.`stage_id` \r\n" + 
					"JOIN `case_reason_details` csr ON csr.`reason_id`=cs.`case_reason_reason_id` \r\n" + 
					"LEFT JOIN `service_recource` src ON src.`service_resource_id` =cs.`engineer_service_resource_id` \r\n" + 
					"JOIN `case_types` cst ON cst.`case_type_id` =cs.`case_type_case_type_id`\r\n" + 
					" JOIN `bt_site_details` st ON st.`id`=cs.`site_id`\r\n" + 
					"  JOIN `accounts` acts ON acts.`sap_customer_number`=st.`account_sap_customer_number` \r\n" + 
					"  JOIN `ssa` ssa ON ssa.`id`=st.`ssa_id` \r\n" + 
					"  JOIN `customer_deatils` cdst ON cdst.`customer_id`=acts.`parent_customer_customer_id` \r\n" + 
					"  JOIN `contact_details`cd ON cd.`contact_id`=cs.`contact_person_contact_id` \r\n" + 
					"  LEFT JOIN `legal_entity` lg ON lg.`legal_entity_id` = cs.`service_center_legal_entity_id`\r\n" + 
					"   JOIN `pending_responsibility` pr ON pr.`pending_responsibility_id`=cs.`pending_responsibility_pending_responsibility_id` \r\n" + 
					"   JOIN `case_reason_details` crd ON crd.`reason_id`=cs.`case_reason_reason_id` \r\n" + 
					"   LEFT JOIN `bt_service_appointment` sa ON sa.`case_details_id`=cs.`id`\r\n" + 
					"   JOIN `employee_details` emp ON emp.`email_id`=cs.`owner_details_email_id`\r\n" + 
					"   JOIN  `contact_details` cstmr ON cstmr.`contact_id`=cs.`contact_person_contact_id`\r\n" + 
					"   WHERE cs.id=?1";
			Query getQuery = entityManager.createNativeQuery(query);
			getQuery.setParameter(1, id);
			Object[] siteObservation = (Object[]) getQuery.getSingleResult();
			
			PdfPTable table = new PdfPTable(5);
			table.setWidths(new int[] { 5, 10, 1, 5, 10 });

			table.addCell(addhead("Number of Battery Modules"));
			table.addCell(addCell(" " + "2"));
			table.addCell(addCells(" "));
			table.addCell(addhead("Other problem reports"));
			table.addCell(addCell(" " + ""));
			table.completeRow();

			table.addCell(addhead("Problem Master Type "));
			table.addCell(addCell(" " + "BATTERY ALL TYPE EX MEDIUM & HIGH VOLTAGE"));
			table.addCell(addCells(" "));
			table.addCell(addhead("Problem(s) Report"));
			table.addCell(addCell(" " + "Battery not charging,Battery SOH Low,Battery not charging"));
//			for (int i = 0; i < siteObservation.size(); i++) {
//				Object[] objectArray = siteObservation.get(i);
//				for (int j = 0; j < objectArray.length; j++) {
//					try {
//						PdfPCell hcell = new PdfPCell(new Phrase(objectArray[j].toString(), normalFont));
//					}catch(Exception ex) {
//						PdfPCell hcell = new PdfPCell(new Phrase("", normalFont));
//					}
//				}}
			table.completeRow();

			table.addCell(addhead(" Remarks"));
			table.addCell(addCell(" " + "testing pdf"));
			
			table.addCell(addCells(" "));
			table.completeRow();
		
			table.completeRow();

			document.add(table);
		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		}

	}

	private void AssignmentDetails(Document document, long id) {
		try {
			PdfPTable table = new PdfPTable(5);
		
			table.setWidths(new int[] { 5, 10, 1, 5, 10 });
			String query = "SELECT lg.`legal_entity_name` AS `Service Center Name`, cs.`manual_id` AS `Case Number`,DATE(cs.`created_date`) AS `Date Opened`, DATE(cs.`close_date`) \r\n" + 
					"AS `Close Date`,\r\n" + 
					"csst.`case_status_name` AS `Case Status`, cst.`case_types_name` AS `Case Type`,crd.`case_reason_details_name` AS `Case Reason`,\r\n" + 
					"cssd.`case_stage_name` AS `Case Stage`,\r\n" + 
					"\r\n" + 
					"(SELECT DATE(sa.`visit_date`) FROM `bt_service_appointment` sa WHERE sa.`case_details_id`=cs.`id` ORDER BY sa.visit_date DESC LIMIT 1) AS `Date of Visit`, \r\n" + 
					"pr.`pending_responsibility_name` AS `Pending Responsibility`, src.`name` AS `Engineer Name`,cd.`employee_code` AS `Employee Code`,\r\n" + 
					" st.`circle_circle_name` AS ` Circle`,ssa.`name` AS `Zone/SSA`,st.`manual_id` AS `Site-ID`, st.`site_name` AS `Exchange/Site Name`, \r\n" + 
					" ast.`manual_id` AS `Asset Serial No`, prd.`pdoduct_description` AS `Asset Description`, \r\n" + 
					" (SELECT GROUP_CONCAT( DISTINCT `problem_name`) FROM `bt_case_problem_reported` WHERE `case_details_id`=cs.id ) AS `Problem Reported`, \r\n" + 
					" cs.`other_problem_reported` AS `Other Problem Reported`,sa.`fault_observed` AS `Fault Observed`, \r\n" + 
					" sa.`remark`AS `Remark`,\r\n" + 
					" CONCAT(emp.`first_name`) AS `Owner`, CONCAT_WS(cstmr.`first_name`,\" \",NULLIF(TRIM(cstmr.`middle_name`),''),\" \",cstmr.`last_name`) AS `Customer Name`\r\n" + 
					"FROM `bt_case` cs \r\n" + 
					"LEFT JOIN `bt_assets` ast ON ast.`id`=cs.`bt_assets_id`\r\n" + 
					"LEFT JOIN `bt_products` prd ON prd.`id`= ast.`products_id` \r\n" + 
					"JOIN `case_status_details` csst ON csst.`id`=cs.`status_id` \r\n" + 
					"JOIN `case_stage_details` cssd ON cssd.`id`=cs.`stage_id` \r\n" + 
					"JOIN `case_reason_details` csr ON csr.`reason_id`=cs.`case_reason_reason_id` \r\n" + 
					"LEFT JOIN `service_recource` src ON src.`service_resource_id` =cs.`engineer_service_resource_id` \r\n" + 
					"JOIN `case_types` cst ON cst.`case_type_id` =cs.`case_type_case_type_id`\r\n" + 
					" JOIN `bt_site_details` st ON st.`id`=cs.`site_id`\r\n" + 
					"  JOIN `accounts` acts ON acts.`sap_customer_number`=st.`account_sap_customer_number` \r\n" + 
					"  JOIN `ssa` ssa ON ssa.`id`=st.`ssa_id` \r\n" + 
					"  JOIN `customer_deatils` cdst ON cdst.`customer_id`=acts.`parent_customer_customer_id` \r\n" + 
					"  JOIN `contact_details`cd ON cd.`contact_id`=cs.`contact_person_contact_id` \r\n" + 
					"  LEFT JOIN `legal_entity` lg ON lg.`legal_entity_id` = cs.`service_center_legal_entity_id`\r\n" + 
					"   JOIN `pending_responsibility` pr ON pr.`pending_responsibility_id`=cs.`pending_responsibility_pending_responsibility_id` \r\n" + 
					"   JOIN `case_reason_details` crd ON crd.`reason_id`=cs.`case_reason_reason_id` \r\n" + 
					"   LEFT JOIN `bt_service_appointment` sa ON sa.`case_details_id`=cs.`id`\r\n" + 
					"   JOIN `employee_details` emp ON emp.`email_id`=cs.`owner_details_email_id`\r\n" + 
					"   JOIN  `contact_details` cstmr ON cstmr.`contact_id`=cs.`contact_person_contact_id`"+
					"	WHERE cs.`id`=?1";
			Query getQuery = entityManager.createNativeQuery(query);
			getQuery.setParameter(1, id);
			Object[] engInfoItem = (Object[]) getQuery.getSingleResult();

			table.addCell(addhead("Service Center"));
			table.addCell(addCell(" " + "	PSL_legalentity"));
			table.addCell(addCells(" "));
			table.addCell(addhead("Service Resource"));
			table.addCell(addCell(" " + "Engineer Engineer"));
			table.completeRow();

			document.add(table);

		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		}

	}

	private void podschonly(Document document, long id) {
		try {
			PdfPTable table = new PdfPTable(5);
		
			table.setWidths(new int[] { 5, 10, 1, 5, 10 });
			String query = "SELECT lg.`legal_entity_name` AS `Service Center Name`, cs.`manual_id` AS `Case Number`,DATE(cs.`created_date`) AS `Date Opened`, DATE(cs.`close_date`) \r\n" + 
					"AS `Close Date`,\r\n" + 
					"csst.`case_status_name` AS `Case Status`, cst.`case_types_name` AS `Case Type`,crd.`case_reason_details_name` AS `Case Reason`,\r\n" + 
					"cssd.`case_stage_name` AS `Case Stage`,\r\n" + 
					"\r\n" + 
					"(SELECT DATE(sa.`visit_date`) FROM `bt_service_appointment` sa WHERE sa.`case_details_id`=cs.`id` ORDER BY sa.visit_date DESC LIMIT 1) AS `Date of Visit`, \r\n" + 
					"pr.`pending_responsibility_name` AS `Pending Responsibility`, src.`name` AS `Engineer Name`,cd.`employee_code` AS `Employee Code`,\r\n" + 
					" st.`circle_circle_name` AS ` Circle`,ssa.`name` AS `Zone/SSA`,st.`manual_id` AS `Site-ID`, st.`site_name` AS `Exchange/Site Name`, \r\n" + 
					" ast.`manual_id` AS `Asset Serial No`, prd.`pdoduct_description` AS `Asset Description`, \r\n" + 
					" (SELECT GROUP_CONCAT( DISTINCT `problem_name`) FROM `bt_case_problem_reported` WHERE `case_details_id`=cs.id ) AS `Problem Reported`, \r\n" + 
					" cs.`other_problem_reported` AS `Other Problem Reported`,sa.`fault_observed` AS `Fault Observed`, \r\n" + 
					" sa.`remark`AS `Remark`,\r\n" + 
					" CONCAT(emp.`first_name`) AS `Owner`, CONCAT_WS(cstmr.`first_name`,\" \",NULLIF(TRIM(cstmr.`middle_name`),''),\" \",cstmr.`last_name`) AS `Customer Name`\r\n" + 
					"FROM `bt_case` cs \r\n" + 
					"LEFT JOIN `bt_assets` ast ON ast.`id`=cs.`bt_assets_id`\r\n" + 
					"LEFT JOIN `bt_products` prd ON prd.`id`= ast.`products_id` \r\n" + 
					"JOIN `case_status_details` csst ON csst.`id`=cs.`status_id` \r\n" + 
					"JOIN `case_stage_details` cssd ON cssd.`id`=cs.`stage_id` \r\n" + 
					"JOIN `case_reason_details` csr ON csr.`reason_id`=cs.`case_reason_reason_id` \r\n" + 
					"LEFT JOIN `service_recource` src ON src.`service_resource_id` =cs.`engineer_service_resource_id` \r\n" + 
					"JOIN `case_types` cst ON cst.`case_type_id` =cs.`case_type_case_type_id`\r\n" + 
					" JOIN `bt_site_details` st ON st.`id`=cs.`site_id`\r\n" + 
					"  JOIN `accounts` acts ON acts.`sap_customer_number`=st.`account_sap_customer_number` \r\n" + 
					"  JOIN `ssa` ssa ON ssa.`id`=st.`ssa_id` \r\n" + 
					"  JOIN `customer_deatils` cdst ON cdst.`customer_id`=acts.`parent_customer_customer_id` \r\n" + 
					"  JOIN `contact_details`cd ON cd.`contact_id`=cs.`contact_person_contact_id` \r\n" + 
					"  LEFT JOIN `legal_entity` lg ON lg.`legal_entity_id` = cs.`service_center_legal_entity_id`\r\n" + 
					"   JOIN `pending_responsibility` pr ON pr.`pending_responsibility_id`=cs.`pending_responsibility_pending_responsibility_id` \r\n" + 
					"   JOIN `case_reason_details` crd ON crd.`reason_id`=cs.`case_reason_reason_id` \r\n" + 
					"   LEFT JOIN `bt_service_appointment` sa ON sa.`case_details_id`=cs.`id`\r\n" + 
					"   JOIN `employee_details` emp ON emp.`email_id`=cs.`owner_details_email_id`\r\n" + 
					"   JOIN  `contact_details` cstmr ON cstmr.`contact_id`=cs.`contact_person_contact_id`"+
					"	WHERE cs.`id`=?1";
			Query getQuery = entityManager.createNativeQuery(query);
			getQuery.setParameter(1, id);
			Object[] engInfoItem = (Object[]) getQuery.getSingleResult();

			table.addCell(addhead("POD Number"));
			table.addCell(addCell(" " + "POD-1512143"));
			table.addCell(addCells(" "));
			table.addCell(addhead("POD Date"));
			table.addCell(addCell(" " + "2021-15-12"));
			table.completeRow();
			
			table.addCell(addhead("POD Shipping Expected Date"));
			table.addCell(addCell(" " + ""));
			table.addCell(addCells(" "));
			table.addCell(addhead("POD Shipping Mode"));
			table.addCell(addCell(" " + "In Hand"));
			table.completeRow();
			
			table.addCell(addhead("POD Shipping Name"));
			table.addCell(addCell(" " + "IN Hand"));
			table.addCell(addCells(" "));
			table.addCell(addhead("POD Shipping Tracking Number"));
			table.addCell(addCell(" " + "235966332584"));
			table.completeRow();



			document.add(table);

		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		}

	}
	private void VendorInvDetails(Document document, long id) {
		try {
			PdfPTable table = new PdfPTable(5);
		
			table.setWidths(new int[] { 5, 10, 1, 5, 10 });
			String query = "SELECT lg.`legal_entity_name` AS `Service Center Name`, cs.`manual_id` AS `Case Number`,DATE(cs.`created_date`) AS `Date Opened`, DATE(cs.`close_date`) \r\n" + 
					"AS `Close Date`,\r\n" + 
					"csst.`case_status_name` AS `Case Status`, cst.`case_types_name` AS `Case Type`,crd.`case_reason_details_name` AS `Case Reason`,\r\n" + 
					"cssd.`case_stage_name` AS `Case Stage`,\r\n" + 
					"\r\n" + 
					"(SELECT DATE(sa.`visit_date`) FROM `bt_service_appointment` sa WHERE sa.`case_details_id`=cs.`id` ORDER BY sa.visit_date DESC LIMIT 1) AS `Date of Visit`, \r\n" + 
					"pr.`pending_responsibility_name` AS `Pending Responsibility`, src.`name` AS `Engineer Name`,cd.`employee_code` AS `Employee Code`,\r\n" + 
					" st.`circle_circle_name` AS ` Circle`,ssa.`name` AS `Zone/SSA`,st.`manual_id` AS `Site-ID`, st.`site_name` AS `Exchange/Site Name`, \r\n" + 
					" ast.`manual_id` AS `Asset Serial No`, prd.`pdoduct_description` AS `Asset Description`, \r\n" + 
					" (SELECT GROUP_CONCAT( DISTINCT `problem_name`) FROM `bt_case_problem_reported` WHERE `case_details_id`=cs.id ) AS `Problem Reported`, \r\n" + 
					" cs.`other_problem_reported` AS `Other Problem Reported`,sa.`fault_observed` AS `Fault Observed`, \r\n" + 
					" sa.`remark`AS `Remark`,\r\n" + 
					" CONCAT(emp.`first_name`) AS `Owner`, CONCAT_WS(cstmr.`first_name`,\" \",NULLIF(TRIM(cstmr.`middle_name`),''),\" \",cstmr.`last_name`) AS `Customer Name`\r\n" + 
					"FROM `bt_case` cs \r\n" + 
					"LEFT JOIN `bt_assets` ast ON ast.`id`=cs.`bt_assets_id`\r\n" + 
					"LEFT JOIN `bt_products` prd ON prd.`id`= ast.`products_id` \r\n" + 
					"JOIN `case_status_details` csst ON csst.`id`=cs.`status_id` \r\n" + 
					"JOIN `case_stage_details` cssd ON cssd.`id`=cs.`stage_id` \r\n" + 
					"JOIN `case_reason_details` csr ON csr.`reason_id`=cs.`case_reason_reason_id` \r\n" + 
					"LEFT JOIN `service_recource` src ON src.`service_resource_id` =cs.`engineer_service_resource_id` \r\n" + 
					"JOIN `case_types` cst ON cst.`case_type_id` =cs.`case_type_case_type_id`\r\n" + 
					" JOIN `bt_site_details` st ON st.`id`=cs.`site_id`\r\n" + 
					"  JOIN `accounts` acts ON acts.`sap_customer_number`=st.`account_sap_customer_number` \r\n" + 
					"  JOIN `ssa` ssa ON ssa.`id`=st.`ssa_id` \r\n" + 
					"  JOIN `customer_deatils` cdst ON cdst.`customer_id`=acts.`parent_customer_customer_id` \r\n" + 
					"  JOIN `contact_details`cd ON cd.`contact_id`=cs.`contact_person_contact_id` \r\n" + 
					"  LEFT JOIN `legal_entity` lg ON lg.`legal_entity_id` = cs.`service_center_legal_entity_id`\r\n" + 
					"   JOIN `pending_responsibility` pr ON pr.`pending_responsibility_id`=cs.`pending_responsibility_pending_responsibility_id` \r\n" + 
					"   JOIN `case_reason_details` crd ON crd.`reason_id`=cs.`case_reason_reason_id` \r\n" + 
					"   LEFT JOIN `bt_service_appointment` sa ON sa.`case_details_id`=cs.`id`\r\n" + 
					"   JOIN `employee_details` emp ON emp.`email_id`=cs.`owner_details_email_id`\r\n" + 
					"   JOIN  `contact_details` cstmr ON cstmr.`contact_id`=cs.`contact_person_contact_id`"+
					"	WHERE cs.`id`=?1";
			Query getQuery = entityManager.createNativeQuery(query);
			getQuery.setParameter(1, id);
			Object[] engInfoItem = (Object[]) getQuery.getSingleResult();

			table.addCell(addhead("Vendor Invoice Number"));
			table.addCell(addCell(" " + "INV-1525226"));
			table.addCell(addCells(" "));
			table.addCell(addhead("Vendor Invoice Date"));
			table.addCell(addCell(" " + "2021-15-12"));
			table.completeRow();

			document.add(table);

		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		}

	}
	private void sysInfo(Document document,long id) {

		try {
			PdfPTable table = new PdfPTable(5);
		
			table.setWidths(new int[] { 5, 10, 1, 5, 10 });
			String query = "SELECT lg.`legal_entity_name` AS `Service Center Name`, cs.`manual_id` AS `Case Number`,DATE(cs.`created_date`) AS `Date Opened`, DATE(cs.`close_date`) \r\n" + 
					"AS `Close Date`,\r\n" + 
					"csst.`case_status_name` AS `Case Status`, cst.`case_types_name` AS `Case Type`,crd.`case_reason_details_name` AS `Case Reason`,\r\n" + 
					"cssd.`case_stage_name` AS `Case Stage`,\r\n" + 
					"\r\n" + 
					"(SELECT DATE(sa.`visit_date`) FROM `bt_service_appointment` sa WHERE sa.`case_details_id`=cs.`id` ORDER BY sa.visit_date DESC LIMIT 1) AS `Date of Visit`, \r\n" + 
					"pr.`pending_responsibility_name` AS `Pending Responsibility`, src.`name` AS `Engineer Name`,cd.`employee_code` AS `Employee Code`,\r\n" + 
					" st.`circle_circle_name` AS ` Circle`,ssa.`name` AS `Zone/SSA`,st.`manual_id` AS `Site-ID`, st.`site_name` AS `Exchange/Site Name`, \r\n" + 
					" ast.`manual_id` AS `Asset Serial No`, prd.`pdoduct_description` AS `Asset Description`, \r\n" + 
					" (SELECT GROUP_CONCAT( DISTINCT `problem_name`) FROM `bt_case_problem_reported` WHERE `case_details_id`=cs.id ) AS `Problem Reported`, \r\n" + 
					" cs.`other_problem_reported` AS `Other Problem Reported`,sa.`fault_observed` AS `Fault Observed`, \r\n" + 
					" sa.`remark`AS `Remark`,\r\n" + 
					" CONCAT(emp.`first_name`) AS `Owner`, CONCAT_WS(cstmr.`first_name`,\" \",NULLIF(TRIM(cstmr.`middle_name`),''),\" \",cstmr.`last_name`) AS `Customer Name`\r\n" + 
					"FROM `bt_case` cs \r\n" + 
					"LEFT JOIN `bt_assets` ast ON ast.`id`=cs.`bt_assets_id`\r\n" + 
					"LEFT JOIN `bt_products` prd ON prd.`id`= ast.`products_id` \r\n" + 
					"JOIN `case_status_details` csst ON csst.`id`=cs.`status_id` \r\n" + 
					"JOIN `case_stage_details` cssd ON cssd.`id`=cs.`stage_id` \r\n" + 
					"JOIN `case_reason_details` csr ON csr.`reason_id`=cs.`case_reason_reason_id` \r\n" + 
					"LEFT JOIN `service_recource` src ON src.`service_resource_id` =cs.`engineer_service_resource_id` \r\n" + 
					"JOIN `case_types` cst ON cst.`case_type_id` =cs.`case_type_case_type_id`\r\n" + 
					" JOIN `bt_site_details` st ON st.`id`=cs.`site_id`\r\n" + 
					"  JOIN `accounts` acts ON acts.`sap_customer_number`=st.`account_sap_customer_number` \r\n" + 
					"  JOIN `ssa` ssa ON ssa.`id`=st.`ssa_id` \r\n" + 
					"  JOIN `customer_deatils` cdst ON cdst.`customer_id`=acts.`parent_customer_customer_id` \r\n" + 
					"  JOIN `contact_details`cd ON cd.`contact_id`=cs.`contact_person_contact_id` \r\n" + 
					"  LEFT JOIN `legal_entity` lg ON lg.`legal_entity_id` = cs.`service_center_legal_entity_id`\r\n" + 
					"   JOIN `pending_responsibility` pr ON pr.`pending_responsibility_id`=cs.`pending_responsibility_pending_responsibility_id` \r\n" + 
					"   JOIN `case_reason_details` crd ON crd.`reason_id`=cs.`case_reason_reason_id` \r\n" + 
					"   LEFT JOIN `bt_service_appointment` sa ON sa.`case_details_id`=cs.`id`\r\n" + 
					"   JOIN `employee_details` emp ON emp.`email_id`=cs.`owner_details_email_id`\r\n" + 
					"   JOIN  `contact_details` cstmr ON cstmr.`contact_id`=cs.`contact_person_contact_id`"+
					"	WHERE cs.`id`=?1";
			Query getQuery = entityManager.createNativeQuery(query);
			getQuery.setParameter(1, id);
			Object[] engInfoItem = (Object[]) getQuery.getSingleResult();

			table.addCell(addhead("Created By"));
			table.addCell(addCell(" " + "Call center 2021-15-12"));
			table.addCell(addCells(" "));
			table.addCell(addhead("Modified date"));
			table.addCell(addCell(" " + "Call center 2021-15-12"));
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
