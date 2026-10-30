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
import com.itextpdf.text.pdf.codec.Base64;
import com.pratikshat.exicom.config.FileStorageProperties;
import com.pratikshat.exicom.entity.LoginUser;

public class ClosedBtServiceAppointmentPDF {
	private  Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL, BaseColor.BLACK);
	private  Font normalFontBold = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD);
	private  Font timesBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
	
	private  Font headingFont = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD|Font.UNDERLINE);
	
	EntityManager entityManager = null;
	FileStorageProperties fileStorageProperties = null;
	
	String address = "Exicom Tele-Systems Ltd 2A, Sub. Major Laxmi Chand Road, Maruti Udyog, Sector 18, Gurugram, Haryana 122015";
	String heading = "Field Service Report";
	
	public ClosedBtServiceAppointmentPDF(EntityManager entityManager, FileStorageProperties fileStorageProperties) {
		
		this.entityManager = entityManager;
		this.fileStorageProperties = fileStorageProperties;
	}
	
	
	public ByteArrayInputStream createPdf(LoginUser user, long id) throws Exception {
		

		Document document = new Document(PageSize.A4, -50, -50, 20, 20);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {

			PdfWriter writer = PdfWriter.getInstance(document, out);

			document.open();

			addHeader(document);
			
			addSubHeading(document, "General Information");
			addGeneralInformation(document,id);
			addSubHeading(document, "Products Consumed Details");
			addProductConsumptionDetails(document,id);

			document.close();
		} catch (DocumentException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("error in creating pdf");
		}

		return new ByteArrayInputStream(out.toByteArray());
	}
	
	
	
	public void addHeader(Document document) {

		try {
			Path fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
			Image image = Image.getInstance(fileStorageLocation + "/" + "logo.png");

			PdfPTable header = new PdfPTable(2);
			header.setWidths(new int[] { 5, 10 });

			image.scalePercent(50);
			image.setAlignment(Element.ALIGN_LEFT);
			PdfPCell cellImage = new PdfPCell(image);
			cellImage.setPadding(2);
			cellImage.setBorder(Rectangle.NO_BORDER);
			header.addCell(cellImage);
			
			PdfPCell blankCell = new PdfPCell();
			blankCell.setBorder(Rectangle.NO_BORDER);
			header.addCell(blankCell);
			header.completeRow();
			
			
			PdfPTable headingTable = new PdfPTable(1);
			//headingTable.setSpacingBefore(10);
			headingTable.setSpacingAfter(10);
			
			Phrase headingPhrase = new Phrase();
			headingPhrase.add(new Chunk(heading, headingFont));
			PdfPCell  headingCell = new PdfPCell(headingPhrase);
			headingCell.setBorder(Rectangle.NO_BORDER);
			headingCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			headingCell.setVerticalAlignment(com.itextpdf.text.Element.ALIGN_MIDDLE);
			headingCell.setPaddingTop(5);
			headingCell.setPaddingBottom(5);
			headingTable.addCell(headingCell);
			headingTable.completeRow();
			
			
			
			PdfPTable addressTable = new PdfPTable(1);
			Phrase addressPhrase = new Phrase();
			addressPhrase.add(new Chunk(address, timesBoldFont));
			PdfPCell  addressCell = new PdfPCell(addressPhrase);
			addressCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			addressCell.setVerticalAlignment(com.itextpdf.text.Element.ALIGN_MIDDLE);
			addressCell.setPaddingTop(5);
			addressCell.setPaddingBottom(5);
			addressTable.addCell(addressCell);
			addressTable.completeRow();
			
			document.add(header);
			document.add(headingTable);
			document.add(addressTable);
			
		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		} catch (MalformedURLException e) {
			throw new ExceptionConverter(e);
		} catch (IOException e) {
			throw new ExceptionConverter(e);
		}
	}
	
	
	
	public void addSubHeading(Document document, String text) {

		try {
			
			PdfPTable subHeadingTable = new PdfPTable(1);
			
			Phrase subHeadingPhrase = new Phrase();
			subHeadingPhrase.add(new Chunk(text, timesBoldFont));
			PdfPCell  subHeadingCell = new PdfPCell(subHeadingPhrase);
			subHeadingCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			subHeadingCell.setVerticalAlignment(com.itextpdf.text.Element.ALIGN_MIDDLE);
			subHeadingCell.setPaddingTop(15);
			subHeadingCell.setPaddingBottom(15);
			subHeadingTable.addCell(subHeadingCell);
			subHeadingTable.completeRow();
			
			document.add(subHeadingTable);
			
		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		}
	}
	
	
public void addGeneralInformation(Document document, long id) {
		
		try {
			
			String query = "SELECT   \r\n"
					+ "					sa.`manual_id` AS `Service Appointment Number`,  \r\n"
					+ "					CONCAT(CONCAT(emp.`first_name`,' '), IFNULL(CONCAT(emp.`middle_name`,' '),''),emp.`last_name`) AS `Owner`,  \r\n"
					+ "					st.`manual_id` AS `Site`,  \r\n"
					+ "					st.`site_name` AS `Site Name`,  \r\n"
					+ "					cd.`manual_id` AS `Case`,  \r\n"
					+ "					(CASE  \r\n"
					+ "						WHEN sa.`status_status_id`=1 THEN 'Unassigned'  \r\n"
					+ "						WHEN sa.`status_status_id`=2 THEN 'Scheduled'	  \r\n"
					+ "						WHEN sa.`status_status_id`=3 THEN 'Dispatched'  \r\n"
					+ "						WHEN sa.`status_status_id`=4 THEN 'Completed'	  \r\n"
					+ "						WHEN sa.`status_status_id`=5 THEN 'In-Completed'  \r\n"
					+ "						WHEN sa.`status_status_id`=6 THEN 'Cannot Complete'	  \r\n"
					+ "						WHEN sa.`status_status_id`=7 THEN 'Subsequently Completed'  \r\n"
					+ "						WHEN sa.`status_status_id`=8 THEN 'Rejected'		  \r\n"
					+ "					END) AS `SA Status`,  \r\n"
					+ "					(CASE  \r\n"
					+ "						WHEN cd.`case_type_case_type_id`=5 THEN 'Exceptional Case'  \r\n"
					+ "						WHEN cd.`case_type_case_type_id`=6 THEN 'Installation'	  \r\n"
					+ "						WHEN cd.`case_type_case_type_id`=7 THEN 'Under Warranty'  \r\n"
					+ "						WHEN cd.`case_type_case_type_id`=8 THEN 'Without Warranty'  \r\n"
					+ "						WHEN cd.`case_type_case_type_id`=9 THEN 'Under AMC'		  \r\n"
					+ "					END) AS `Case Type`,  \r\n"
					+ "					(CASE  \r\n"
					+ "						WHEN cd.`status_id`=1 THEN 'New'  \r\n"
					+ "						WHEN cd.`status_id`=2 THEN 'Closed'	  \r\n"
					+ "						WHEN cd.`status_id`=3 THEN 'In-Process'  \r\n"
					+ "						WHEN cd.`status_id`=4 THEN 'Rejected'		  \r\n"
					+ "					END) AS `Case Status`,  \r\n"
					+ "					act.`name` AS `Account`,  \r\n"
					+ "					crd.`case_reason_details_name` AS `Case Reason`,  \r\n"
					+ "					CONCAT(CONCAT(cnt.`first_name`,' '), IFNULL(CONCAT(cnt.`middle_name`,' '),''),cnt.`last_name`) AS `Customer Name`,  \r\n"
					+ "					IFNULL(sa.`fault_observed`,'') AS `Fault Observed`,  \r\n"
					+ "					IFNULL(cnt.`mobile`,'') AS `Customer Contact`,  \r\n"
					+ "					IFNULL(sa.`punch_point_exiom`,'') AS `Punch Points(Exicom)`,  \r\n"
					+ "					IFNULL(prd.`manual_id`,'') AS `Asset Part Code`,  \r\n"
					+ "					DATE_FORMAT(sa.`visit_date`,'%d-%m-%Y') AS `Date of Visit`,  \r\n"
					+ "					IFNULL(prd.`pdoduct_description`,'') AS `Asset Description`,  \r\n"
					+ "					(CASE  \r\n"
					+ "						WHEN sa.`pending_responsibility_pending_responsibility_id`=1 THEN 'Exicom'  \r\n"
					+ "						WHEN sa.`pending_responsibility_pending_responsibility_id`=2 THEN 'Customer'  \r\n"
					+ "						WHEN sa.`pending_responsibility_pending_responsibility_id`=3 THEN 'None'  \r\n"
					+ "					END) AS `Pending Responsibility`,  \r\n"
					+ "					IFNULL(sa.`corrective_actions`,'') AS `Corrective Action`,  \r\n"
					+ "					IFNULL(sa.`punch_point_customer`,'') AS `Punch Points(Customer)`,  \r\n"
					+ "					IFNULL(sa.`remark`,'') AS `Remarks`,  \r\n"
					+ "					IFNULL(sa.`acknowledged_by_name`,'') AS `Customer Signoff-Name`,  \r\n"
					+ "					IFNULL(sa.`acknowledged_by_phone`,'') AS `Customer Signoff-Number`,  \r\n"
					+ "					#sa. `closed_from_platform` AS `Closed From Platform`,\r\n"
					+ "					esg.`e_signature` AS `E-Signature`  \r\n"
					+ "					FROM `bt_service_appointment` sa  \r\n"
					+ "					JOIN `bt_case` cd ON cd.`id`=sa.`case_details_id`  \r\n"
					+ "					JOIN `case_reason_details` crd ON crd.`reason_id`=cd.`case_reason_reason_id`  \r\n"
					+ "					LEFT JOIN `bt_assets` ast ON ast.`id`=cd.`bt_assets_id`  \r\n"
					+ "					JOIN `bt_products` prd ON prd.`id`=ast.`products_id`  \r\n"
					+ "					JOIN `bt_site_details` st ON st.`id`=cd.`site_id` \r\n"
					+ "					JOIN `accounts` act ON act.`sap_customer_number`=st.`account_sap_customer_number`  \r\n"
					+ "					JOIN `employee_details` emp ON emp.`email_id`=sa.`owner_details_email_id`  \r\n"
					+ "					JOIN `contact_details` cnt ON cnt.`contact_id`=cd.`contact_person_contact_id` \r\n"
					+ "					LEFT JOIN `esignature_onsa` esg ON esg.`id_service_appointment_id`=sa.`service_appointment_id`\r\n"+
					"WHERE sa.`service_appointment_id`=?1";
			
			Query getQuery = entityManager.createNativeQuery(query);
			getQuery.setParameter(1, id);
			Object[] generalInformation = (Object[])getQuery.getSingleResult();
			
			
			PdfPTable table = new PdfPTable(4);
			table.setWidths(new int[] { 5, 10, 5, 10 });
			
			table.addCell(addCell("Appointment Number"));
			table.addCell(addCell(generalInformation[0].toString()));
			
			table.addCell(addCell("Owner"));
			table.addCell(addCell(generalInformation[1].toString()));
			table.completeRow();
			
			
			table.addCell(addCell("Site"));
			table.addCell(addCell(generalInformation[2].toString()));
			
			table.addCell(addCell("Site Name"));
			table.addCell(addCell(generalInformation[3].toString()));
			table.completeRow();
			
			table.addCell(addCell("Case"));
			table.addCell(addCell(generalInformation[4].toString()));
			
			table.addCell(addCell("SA Status"));
			table.addCell(addCell(generalInformation[5].toString()));
			table.completeRow();
			
			
			table.addCell(addCell("Case Type"));
			table.addCell(addCell(generalInformation[6].toString()));
			
			table.addCell(addCell("Case Status"));
			table.addCell(addCell(generalInformation[7].toString()));
			table.completeRow();
			
			
			table.addCell(addCell("Account"));
			table.addCell(addCell(generalInformation[8].toString()));
			
			table.addCell(addCell("Case Reason"));
			table.addCell(addCell(generalInformation[9].toString()));
			table.completeRow();
			
			
			table.addCell(addCell("Asset Serial Number"));
			table.addCell(addCell(generalInformation[10].toString()));
			
			table.addCell(addCell("Customer Name"));
			table.addCell(addCell(generalInformation[11].toString()));
			table.completeRow();
			
			
			table.addCell(addCell("Fault Observed"));
			table.addCell(addCell(generalInformation[12].toString()));
			
			table.addCell(addCell("Customer Contact"));
			table.addCell(addCell(generalInformation[13].toString()));
			table.completeRow();
			
			
			table.addCell(addCell("Punch Points(Exicom)"));
			table.addCell(addCell(generalInformation[14].toString()));
			
			table.addCell(addCell("Asset Part Code"));
			table.addCell(addCell(generalInformation[15].toString()));
			table.completeRow();
			
			
			table.addCell(addCell("Date Of Visit"));
			table.addCell(addCell(generalInformation[16].toString()));
			
			table.addCell(addCell("Asset description"));
			table.addCell(addCell(generalInformation[17].toString()));
			table.completeRow();
			
			
			table.addCell(addCell("Pending Responsibility"));
			table.addCell(addCell(generalInformation[18].toString()));
			
			table.addCell(addCell("Corrective Actions"));
			table.addCell(addCell(generalInformation[19].toString()));
			table.completeRow();
			
			
			table.addCell(addCell("Punch Points(Customer)"));
			table.addCell(addCell(generalInformation[20].toString()));
			
			table.addCell(addCell(""));
			table.addCell(addCell(""));
			table.completeRow();
			
			
			table.addCell(addCell(" "));
			PdfPCell c2 = addCell("");
			c2.setColspan(3);
			table.addCell(c2);
			table.completeRow();
			
			
			table.addCell(addCell("Remarks"));
			PdfPCell remarksValueCell = addCell(generalInformation[21].toString());
			remarksValueCell.setColspan(3);
			table.addCell(remarksValueCell);
			table.completeRow();
			
			
			table.addCell(addCell("Customer Signoff-Name"));
			table.addCell(addCell(generalInformation[22].toString()));
			
			table.addCell(addCell("Customer Signoff-Number"));
			table.addCell(addCell(generalInformation[23].toString()));
			table.completeRow();
			
			
			table.addCell(addCell("Customer E-Signature"));
			
			
			
			PdfPCell eSignatureValueCell = null;
			if(generalInformation[25]!=null) {
				Image image = Image.getInstance(Base64.decode(generalInformation[25].toString()));
				image.scalePercent(25);
				image.setAlignment(Element.ALIGN_LEFT);	
				eSignatureValueCell = new PdfPCell(image);
				eSignatureValueCell.setPaddingLeft(5);
			}else{
				String message = "";
				if(generalInformation[24]!=null) {
					if(generalInformation[24].toString().equalsIgnoreCase("WEB")) {
						message = "Closed from WEB";
					}
				}else {
					message = "No E-Signature";
				}
				Phrase phrase = new Phrase();
				phrase.add(new Chunk(message, normalFont));
				eSignatureValueCell = new PdfPCell(phrase);
				eSignatureValueCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
				eSignatureValueCell.setVerticalAlignment(com.itextpdf.text.Element.ALIGN_MIDDLE);
				eSignatureValueCell.setPaddingTop(5);
				eSignatureValueCell.setPaddingBottom(5);
			}
			eSignatureValueCell.setColspan(3);
			table.addCell(eSignatureValueCell);
			table.completeRow();
			
			PdfPCell c3 = addCell(" ");
			c3.setColspan(4);
			table.addCell(c3);
			table.completeRow();
			
			document.add(table);
		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		}catch (MalformedURLException e) {
			throw new ExceptionConverter(e);
		} catch (IOException e) {
			throw new ExceptionConverter(e);
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
	
	
	public void addProductConsumptionDetails(Document document, long id) {
		try {
			PdfPTable table = new PdfPTable(8);
			table.setWidths(new int[] {2, 4, 2, 4, 4, 4, 4, 4 });
			List<String> headers = new ArrayList<>();
			headers.add("S.No.");
			headers.add("Part Code");
			headers.add("Quantity");
			headers.add("Consumed Type");
			headers.add("OK Serial Number");
			headers.add("Faulty Part Code");
			headers.add("Faulty Serial Number");
			headers.add("Remarks");

			if (headers.size() > 0) {
				
				for (int i = 0; i < headers.size(); i++) {
					PdfPCell hcell;
					hcell = new PdfPCell(new Phrase(headers.get(i), normalFontBold));
					hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(hcell);
				}
			}
			
			String query = "SELECT \r\n" + 
					"@sn\\:=@sn+1 AS sno, \r\n" + 
					"con.`product_product_part_code` AS `Part Code`,\r\n" + 
					"(CASE \r\n" + 
					"	WHEN con.`consumption_type_consumption_type_name`='Replaced' THEN 1\r\n" + 
					"	ELSE con.`quantity`\r\n" + 
					"END) AS `Quantity`,\r\n" + 
					"con.`consumption_type_consumption_type_name` AS `Consumed Type`,\r\n" + 
					"IFNULL(con_item.`consumed_line_item_serial_number`,'') AS `OK Serial Number`,\r\n" + 
					"IFNULL(con_item.`faulty_partcode`,'') AS `Faulty Part Code`,\r\n" + 
					"IFNULL(con_item.`replaced_line_item_serial_number`,'') AS `Faulty Serial Number`,\r\n" + 
					"IFNULL(con.`remark`,'') AS `Remarks`\r\n" + 
					"FROM (SELECT @sn\\:=0) AS r, \r\n" + 
					"`service_appoinment_product_consumed` con\r\n" + 
					"LEFT JOIN `service_appointment_product_consumed_line_items` con_item ON con_item.`product_consumed_id`=con.`id` \r\n" + 
					"WHERE `service_appointment_service_appointment_id` = ?1";
			
			Query getItemQuery = entityManager.createNativeQuery(query);
			getItemQuery.setParameter(1, id);
			List<Object[]> itemList = getItemQuery.getResultList();
			
			if(itemList!=null && !itemList.isEmpty()) {
				
				// Item list
				for (int i = 0; i < itemList.size(); i++) {
					Object[] objectArray = itemList.get(i);
					for (int j = 0; j < objectArray.length; j++) {
						PdfPCell hcell;
						if(j==0 || j==2) {
							hcell = new PdfPCell(new Phrase(String.valueOf((int)Double.parseDouble(objectArray[j].toString())), normalFont));	
						}else {
							hcell = new PdfPCell(new Phrase(objectArray[j].toString(), normalFont));
						}
						
						hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
						hcell.setVerticalAlignment(Element.ALIGN_CENTER);
						hcell.setPadding(5);
						table.addCell(hcell);	
					}
					table.completeRow();
				}
				document.add(table);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
