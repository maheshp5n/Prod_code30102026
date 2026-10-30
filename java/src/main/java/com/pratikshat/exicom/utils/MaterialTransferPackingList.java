package com.pratikshat.exicom.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pratikshat.exicom.config.FileStorageProperties;
import com.pratikshat.exicom.entity.ExpenseManagement;
import com.pratikshat.exicom.entity.LPTrasfer;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.event.ExpenseHeaderFooterPageEvent;
import com.pratikshat.exicom.model.response.ExpenseManagementResponse;
import com.pratikshat.exicom.model.response.QuoteLineItemResponse;

public class MaterialTransferPackingList {

	private  Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL, BaseColor.BLACK);//DARK_GRAY
	private  Font normalFontBold = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD);
	private  Font titleBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD);
	private  Font tableNormalFontBold = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD);
	private  Font tableNormalFont = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL, BaseColor.BLACK);
	
	EntityManager entityManager = null;
	FileStorageProperties fileStorageProperties = null;
	
	
	public MaterialTransferPackingList(EntityManager entityManager, FileStorageProperties fileStorageProperties) {
		
		this.entityManager = entityManager;
		this.fileStorageProperties = fileStorageProperties;
	}
	
	public ByteArrayInputStream createPdf(LoginUser user, long id, String type) throws Exception {
		
		String headerQuery = null;
		String itemQuery = null;
		String summaryQuery = null;
		
		
		switch(type) {
		case "ProductTransfer":{
			headerQuery = "SELECT \r\n" + 
					"trf.`location_product_transfer_manual_id` AS `manualId`,\r\n" + 
					"trf.`created_date` AS `createdDate`,\r\n" + 
					"srl.`location_name` AS `sourceLocation`,\r\n" + 
					"dtl.`location_name` AS `destinationLocation`,\r\n" + 
					"srlg.`legal_entity_name` AS `sourceLegalEntity`,\r\n" + 
					"dtlg.`legal_entity_name` AS `destinationLegalEntity`\r\n" + 
					"FROM `lptrasfer` trf  \r\n" + 
					"JOIN `lproduct_request` pr ON pr.`location_product_request_id`=trf.`location_product_request_location_product_request_id`\r\n" + 
					"JOIN `location` srl ON srl.`location_id`=pr.`source_location_location_id`\r\n" + 
					"JOIN `location` dtl ON dtl.`location_id`=pr.`destination_location_location_id`\r\n" + 
					"JOIN `legal_entity` srlg ON srlg.`legal_entity_id`=srl.`legal_entity_legal_entity_id` \r\n" + 
					"JOIN `legal_entity` dtlg ON dtlg.`legal_entity_id`=dtl.`legal_entity_legal_entity_id`   \r\n" + 
					"WHERE trf.`location_product_transfer_id`=?1";
			
			
			itemQuery = "SELECT \r\n" + 
					"@sn\\:=@sn+1 AS sno, \r\n" + 
					"prd.`product_part_code`, \r\n" + 
					"prd.`pdoduct_description`, \r\n" + 
					"prd.`product_type`, \r\n" + 
					"'Ok', \r\n" + 
					"(CASE\r\n" + 
					"	WHEN litem.`product_serial_number` IS NOT NULL THEN litem.`product_serial_number`\r\n" + 
					"	ELSE item.`transfer_quantity`\r\n" + 
					"END) AS qty\r\n" + 
					"FROM (SELECT @sn\\:=0) AS r, \r\n" + 
					"`lptransfer_item` item\r\n" + 
					"LEFT JOIN `lptransfer_line_item` litem ON litem.`transfer_item_lp_transfer_item_id`=item.`lp_transfer_item_id` \r\n" + 
					"JOIN `products` prd ON prd.`product_part_code`=item.`product_product_part_code` \r\n" + 
					"WHERE `lp_tranfer_location_product_transfer_id` = ?1";
			
			
			summaryQuery = "SELECT\r\n" + 
					"prd.`product_type`,\r\n" + 
					"SUM((CASE\r\n" + 
					"	WHEN litem.`product_serial_number` IS NOT NULL THEN 1\r\n" + 
					"	ELSE item.`transfer_quantity`\r\n" + 
					"END)) AS qty\r\n" + 
					"FROM `lptransfer_item` item\r\n" + 
					"LEFT JOIN `lptransfer_line_item` litem ON litem.`transfer_item_lp_transfer_item_id`=item.`lp_transfer_item_id`\r\n" + 
					"JOIN `products` prd ON prd.`product_part_code`=item.`product_product_part_code`\r\n" + 
					"WHERE `lp_tranfer_location_product_transfer_id`= ?1 \r\n" + 
					"GROUP BY prd.`product_type`";
			break;
		}
		case "ReturnOrder":{
			headerQuery = "SELECT \r\n" + 
					"rto.`lpr_order_manual_id` AS `manualId`,\r\n" + 
					"rto.`created_date` AS `createdDate`,\r\n" + 
					"srl.`location_name` AS `sourceLocation`,\r\n" + 
					"dtl.`location_name` AS `destinationLocation`,\r\n" + 
					"srlg.`legal_entity_name` AS `sourceLegalEntity`,\r\n" + 
					"dtlg.`legal_entity_name` AS `destinationLegalEntity`\r\n" + 
					"FROM `lproduct_return_order` rto  \r\n" + 
					"JOIN `location` srl ON srl.`location_id`=rto.`source_location_location_id`\r\n" + 
					"JOIN `location` dtl ON dtl.`location_id`=rto.`destination_location_location_id`\r\n" + 
					"JOIN `legal_entity` srlg ON srlg.`legal_entity_id`=srl.`legal_entity_legal_entity_id` \r\n" + 
					"JOIN `legal_entity` dtlg ON dtlg.`legal_entity_id`=dtl.`legal_entity_legal_entity_id`   \r\n" + 
					"WHERE rto.`lpr_orderid`=?1";
			
			itemQuery = "SELECT \r\n" + 
					"@sn\\:=@sn+1 AS sno, \r\n" + 
					"prd.`product_part_code`, \r\n" + 
					"prd.`pdoduct_description`, \r\n" + 
					"prd.`product_type`, \r\n" + 
					"st.`product_state_name`, \r\n" + 
					"(CASE \r\n" + 
					"	WHEN litem.`product_line_item_serial_number` IS NOT NULL THEN litem.`product_line_item_serial_number` \r\n" + 
					"	ELSE item.`total_return_quantity` \r\n" + 
					"END) AS qty \r\n" + 
					"FROM (SELECT @sn\\:=0) AS r, \r\n" + 
					"`lproduct_return_order_item` item \r\n" + 
					"LEFT JOIN `lproduct_return_order_line_item` litem ON litem.`return_order_item_lpro_itemid`=item.`lpro_itemid`\r\n" + 
					"JOIN  `product_line_item_product_state` st ON st.`product_state_id`=litem.`line_item_state`\r\n" + 
					"JOIN `products` prd ON prd.`product_part_code`=item.`product_product_part_code`\r\n" + 
					"WHERE `location_product_return_order_lpr_orderid` = ?1";
			
			summaryQuery = "SELECT\r\n" + 
					"prd.`product_type`,\r\n" + 
					"SUM((CASE\r\n" + 
					"	WHEN litem.`product_line_item_serial_number` IS NOT NULL THEN 1\r\n" + 
					"	ELSE item.`total_return_quantity`\r\n" + 
					"END)) AS qty\r\n" + 
					"FROM `lproduct_return_order_item` item \r\n" + 
					"LEFT JOIN `lproduct_return_order_line_item` litem ON litem.`return_order_item_lpro_itemid`=item.`lpro_itemid`\r\n" + 
					"JOIN `products` prd ON prd.`product_part_code`=item.`product_product_part_code`\r\n" + 
					"WHERE `location_product_return_order_lpr_orderid` = ?1\r\n" + 
					"GROUP BY prd.`product_type`";
			break;
		}
		case "ServiceOrder":{
			headerQuery = "SELECT \r\n" + 
					"so.`manual_id` AS `manualId`,\r\n" + 
					"so.`created_date` AS `createdDate`,\r\n" + 
					"srl.`location_name` AS `sourceLocation`,\r\n" + 
					"dtl.`location_name` AS `destinationLocation`,\r\n" + 
					"srlg.`legal_entity_name` AS `sourceLegalEntity`,\r\n" + 
					"dtlg.`legal_entity_name` AS `destinationLegalEntity`\r\n" + 
					"FROM `service_order` so  \r\n" + 
					"JOIN `location` srl ON srl.`location_id`=so.`ware_house_location_id`\r\n" + 
					"JOIN `location` dtl ON dtl.`location_id`=so.`repari_center_location_id`\r\n" + 
					"JOIN `legal_entity` srlg ON srlg.`legal_entity_id`=srl.`legal_entity_legal_entity_id` \r\n" + 
					"JOIN `legal_entity` dtlg ON dtlg.`legal_entity_id`=dtl.`legal_entity_legal_entity_id`   \r\n" + 
					"WHERE so.`id`=?1";
			
			itemQuery = "SELECT \r\n" + 
					"@sn\\:=@sn+1 AS sno, \r\n" + 
					"prd.`product_part_code`, \r\n" + 
					"prd.`pdoduct_description`, \r\n" + 
					"prd.`product_type`, \r\n" + 
					"st.`product_state_name`, \r\n" + 
					"item.`serial_number` AS qty \r\n" + 
					"FROM (SELECT @sn\\:=0) AS r, \r\n" + 
					"`service_order_item` item \r\n" + 
					"JOIN  `product_line_item_product_state` st ON st.`product_state_id`=item.`state_product_state_id`\r\n" + 
					"JOIN `products` prd ON prd.`product_part_code`=item.`product_product_part_code`\r\n" + 
					"WHERE `service_order_id` = ?1";
			
			summaryQuery = "SELECT\r\n" + 
					"prd.`product_type`,\r\n" + 
					"COUNT(1) AS qty\r\n" + 
					"FROM `service_order_item` item \r\n" + 
					"JOIN `products` prd ON prd.`product_part_code`=item.`product_product_part_code`\r\n" + 
					"WHERE `service_order_id` = ?1\r\n" + 
					"GROUP BY prd.`product_type`";
			break;
		}
		
		case "ServiceOrderReturn":{
			
			headerQuery = "SELECT \r\n" + 
					"sor.`manual_id` AS `manualId`,\r\n" + 
					"sor.`created_date` AS `createdDate`,\r\n" + 
					"srl.`location_name` AS `sourceLocation`,\r\n" + 
					"dtl.`location_name` AS `destinationLocation`,\r\n" + 
					"srlg.`legal_entity_name` AS `sourceLegalEntity`,\r\n" + 
					"dtlg.`legal_entity_name` AS `destinationLegalEntity`\r\n" + 
					"FROM `service_order_return` sor \r\n" + 
					"JOIN `location` srl ON srl.`location_id`=sor.`source_loccation_location_id`\r\n" + 
					"JOIN `location` dtl ON dtl.`location_id`=sor.`destination_location_location_id`\r\n" + 
					"JOIN `legal_entity` srlg ON srlg.`legal_entity_id`=srl.`legal_entity_legal_entity_id`\r\n" + 
					"JOIN `legal_entity` dtlg ON dtlg.`legal_entity_id`=dtl.`legal_entity_legal_entity_id`   \r\n" + 
					"WHERE sor.`id`=?1";
			
			itemQuery = "SELECT \r\n" + 
					"@sn\\:=@sn+1 AS sno, \r\n" + 
					"prd.`product_part_code`, \r\n" + 
					"prd.`pdoduct_description`, \r\n" + 
					"prd.`product_type`, \r\n" + 
					"st.`product_state_name`, \r\n" + 
					"soitem.`serial_number` AS qty \r\n" + 
					"FROM (SELECT @sn\\:=0) AS r, \r\n" + 
					"`service_order_item_return` item \r\n" + 
					"JOIN `service_order_item` soitem ON soitem.`id`=item.`id`\r\n" + 
					"JOIN  `product_line_item_product_state` st ON st.`product_state_id`=item.`state_product_state_id`\r\n" + 
					"JOIN `products` prd ON prd.`product_part_code`=soitem.`product_product_part_code`\r\n" + 
					"WHERE `service_order_return_id` = ?1";
			
			summaryQuery = "SELECT\r\n" + 
					"prd.`product_type`,\r\n" + 
					"COUNT(1) AS qty\r\n" + 
					"FROM `service_order_item_return` item \r\n" + 
					"JOIN `service_order_item` soitem ON soitem.`id`=item.`id`\r\n" + 
					"JOIN `products` prd ON prd.`product_part_code`=soitem.`product_product_part_code`\r\n" + 
					"WHERE `service_order_return_id` = ?1\r\n" + 
					"GROUP BY prd.`product_type`";
			
			break;
		}
		}
		

		Document document = new Document(PageSize.A4, 0, 0, 40, 40);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {

			PdfWriter writer = PdfWriter.getInstance(document, out);
			document.open();

			addHeader(document, headerQuery, id, type);
			
			addTitle(document, "Packing List", titleBoldFont, Element.ALIGN_CENTER);
			
			addItemTable(document, itemQuery, summaryQuery, id);
			
			addSignatureArea(document, "Signature:", normalFontBold, Element.ALIGN_LEFT);
			
			document.close();
		} catch (DocumentException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("error in creating pdf");
		}

		return new ByteArrayInputStream(out.toByteArray());
	}
	
	public void addHeader(Document documnet, String headerQuery, long id, String type) {
		
		try {
			
			Query getQuery = entityManager.createNativeQuery(headerQuery);
			getQuery.setParameter(1, id);
			Object[] productTransfer = (Object[])getQuery.getSingleResult();
			
			String manualId = productTransfer[0].toString();
			Date createdDate = (Date)productTransfer[1];
			
			String sourceWarehouse = productTransfer[2].toString();
			String destinationWarehouse = productTransfer[3].toString();
			
			String sourceLegalEntity = productTransfer[4].toString();
			String destinationLegalEntity = productTransfer[5].toString();
			
			PdfPTable header = new PdfPTable(2);
			header.getDefaultCell().setBorderWidth(0);
			
			PdfPTable line11 = new PdfPTable(2);
			Phrase sourceHeadingPhrase = new Phrase();
			sourceHeadingPhrase.add(new Chunk("From Warehouse (Source): ", normalFontBold));
			PdfPCell sourceHeadingCell = new PdfPCell(sourceHeadingPhrase);
			sourceHeadingCell.setBorder(Rectangle.NO_BORDER);
			sourceHeadingCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			sourceHeadingCell.setPaddingBottom(10);
			line11.addCell(sourceHeadingCell);
			
			Phrase sourceValuePhrase = new Phrase();
			sourceValuePhrase.add(new Chunk(sourceWarehouse, normalFont));
			PdfPCell sourceValueCell = new PdfPCell(sourceValuePhrase);
			sourceValueCell.setBorder(Rectangle.NO_BORDER);
			sourceValueCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			sourceValueCell.setPaddingBottom(10);
			line11.addCell(sourceValueCell);
			header.addCell(line11);
			
			PdfPTable line12 = new PdfPTable(2);
			Phrase destinationHeadingPhrase = new Phrase();
			destinationHeadingPhrase.add(new Chunk("To Warehouse (Destination): ", normalFontBold));
			PdfPCell destinationHeadingCell = new PdfPCell(destinationHeadingPhrase);
			destinationHeadingCell.setBorder(Rectangle.NO_BORDER);
			destinationHeadingCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			destinationHeadingCell.setPaddingBottom(10);
			line12.addCell(destinationHeadingCell);
			
			Phrase destinationValuePhrase = new Phrase();
			destinationValuePhrase.add(new Chunk(destinationWarehouse, normalFont));
			PdfPCell destinationValueCell = new PdfPCell(destinationValuePhrase);
			destinationValueCell.setBorder(Rectangle.NO_BORDER);
			destinationValueCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			destinationValueCell.setPaddingBottom(10);
			line12.addCell(destinationValueCell);
			header.addCell(line12);
			header.completeRow();
			
			
			PdfPTable line21 = new PdfPTable(2);
			Phrase sourceLegalEntityHeadingPhrase = new Phrase();
			sourceLegalEntityHeadingPhrase.add(new Chunk("From Legal Entity (Source): ", normalFontBold));
			PdfPCell sourceLegalEntityHeadingCell = new PdfPCell(sourceLegalEntityHeadingPhrase);
			sourceLegalEntityHeadingCell.setBorder(Rectangle.NO_BORDER);
			sourceLegalEntityHeadingCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			sourceLegalEntityHeadingCell.setPaddingBottom(10);
			line21.addCell(sourceLegalEntityHeadingCell);
			
			Phrase sourceLegalEntityValuePhrase = new Phrase();
			sourceLegalEntityValuePhrase.add(new Chunk(sourceLegalEntity, normalFont));
			PdfPCell sourceLegalEntityValueCell = new PdfPCell(sourceLegalEntityValuePhrase);
			sourceLegalEntityValueCell.setBorder(Rectangle.NO_BORDER);
			sourceLegalEntityValueCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			sourceLegalEntityValueCell.setPaddingBottom(10);
			line21.addCell(sourceLegalEntityValueCell);
			header.addCell(line21);
			
			PdfPTable line22 = new PdfPTable(2);
			Phrase destinationLegalEntityHeadingPhrase = new Phrase();
			destinationLegalEntityHeadingPhrase.add(new Chunk("To Legal Entity (Destination): ", normalFontBold));
			PdfPCell destinationLegalEntityHeadingCell = new PdfPCell(destinationLegalEntityHeadingPhrase);
			destinationLegalEntityHeadingCell.setBorder(Rectangle.NO_BORDER);
			destinationLegalEntityHeadingCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			destinationLegalEntityHeadingCell.setPaddingBottom(10);
			line22.addCell(destinationLegalEntityHeadingCell);
			
			Phrase destinationLegalEntityValuePhrase = new Phrase();
			destinationLegalEntityValuePhrase.add(new Chunk(destinationLegalEntity, normalFont));
			PdfPCell destinationLegalEntityValueCell = new PdfPCell(destinationLegalEntityValuePhrase);
			destinationLegalEntityValueCell.setBorder(Rectangle.NO_BORDER);
			destinationLegalEntityValueCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			destinationLegalEntityValueCell.setPaddingBottom(10);
			line22.addCell(destinationLegalEntityValueCell);
			header.addCell(line22);
			header.completeRow();
			
			
			String ManualIdHeader = "";
			switch(type) {
			case "ProductTransfer" : {
				ManualIdHeader = "PT Number: ";
				break;
			}
			case "ReturnOrder" : {
				ManualIdHeader = "RO Number: ";
				break;
			}
			case "ServiceOrder" : {
				ManualIdHeader = "SO Number: ";
				break;
			}
			case "ServiceOrderReturn" : {
				ManualIdHeader = "SOR Number: ";
				break;
			}
			}
			
			PdfPTable line31 = new PdfPTable(4);
			line31.getDefaultCell().setBorderWidth(0);
			Phrase manualIdHeaderPhrase = new Phrase();
			manualIdHeaderPhrase.add(new Chunk(ManualIdHeader, normalFontBold));
			PdfPCell  manualIdHeaderCell = new PdfPCell(manualIdHeaderPhrase);
			manualIdHeaderCell.setBorder(Rectangle.NO_BORDER);
			manualIdHeaderCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			manualIdHeaderCell.setPaddingBottom(10);
			line31.addCell(manualIdHeaderCell);
			
			Phrase manualIdValuePhrase = new Phrase();
			manualIdValuePhrase.add(new Chunk(manualId, normalFont));
			PdfPCell  manualIdValueCell = new PdfPCell(manualIdValuePhrase);
			manualIdValueCell.setBorder(Rectangle.NO_BORDER);
			manualIdValueCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			manualIdValueCell.setPaddingBottom(10);
			line31.addCell(manualIdValueCell);
			line31.completeRow();
			
			String createDateHeader = "";
			switch(type) {
			case "ProductTransfer" : {
				createDateHeader = "Creation Date of PT: ";
				break;
			}
			case "ReturnOrder" : {
				createDateHeader = "Creation Date of RO: ";
				break;
			}
			case "ServiceOrder" : {
				createDateHeader = "Creation Date of SO: ";
				break;
			}
			case "ServiceOrderReturn" : {
				createDateHeader = "Creation Date of SOR: ";
				break;
			}
			}
			
			PdfPTable line41 = new PdfPTable(4);
			line41.getDefaultCell().setBorderWidth(0);
			Phrase createdDateHeaderPhrase = new Phrase();
			createdDateHeaderPhrase.add(new Chunk(createDateHeader, normalFontBold));
			PdfPCell  createdDateHeaderCell = new PdfPCell(createdDateHeaderPhrase);
			createdDateHeaderCell.setBorder(Rectangle.NO_BORDER);
			createdDateHeaderCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			createdDateHeaderCell.setPaddingBottom(10);
			line41.addCell(createdDateHeaderCell);
			
			Phrase createdDateValuePhrase = new Phrase();
			SimpleDateFormat outputDate = new SimpleDateFormat("dd/MM/yyyy");
			createdDateValuePhrase.add(new Chunk(outputDate.format(createdDate), normalFont));
			PdfPCell  createdDateValueCell = new PdfPCell(createdDateValuePhrase);
			createdDateValueCell.setBorder(Rectangle.NO_BORDER);
			createdDateValueCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			createdDateValueCell.setPaddingBottom(10);
			line41.addCell(createdDateValueCell);
			line41.completeRow();
			
			
			documnet.add(header);
			//documnet.add(line21);
			documnet.add(line31);
			documnet.add(line41);
		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		}
	}
	
	public void addTitle(Document doc, String title, Font font, int alignment) {
		try {
			PdfPTable titleHeader = new PdfPTable(1);
			PdfPCell cellTitle = new PdfPCell(new Paragraph(title, font));
			cellTitle.setColspan(3);
			cellTitle.setPadding(2);
			cellTitle.setHorizontalAlignment(alignment);
			cellTitle.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cellTitle.setBorder(Rectangle.NO_BORDER);
			cellTitle.setPaddingTop(20);
			cellTitle.setPaddingBottom(20);
			titleHeader.addCell(cellTitle);
			doc.add(titleHeader);
		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		} catch (Exception e) {
			throw new ExceptionConverter(e);
		}
	}
	
	public void addItemTable(Document document, String itemQuery, String summaryQuery, long id) {
		try {
			PdfPTable table = new PdfPTable(6);
			table.setWidths(new int[] {2, 4, 6, 4, 2, 4 });
			List<String> headers = new ArrayList<>();
			headers.add("S. No.");
			headers.add("Part Code");
			headers.add("Description");
			headers.add("Product Type");
			headers.add("Product Status");
			headers.add("Quantity/Serial No.");

			if (headers.size() > 0) {
				
				for (int i = 0; i < headers.size(); i++) {
					PdfPCell hcell;
					hcell = new PdfPCell(new Phrase(headers.get(i), tableNormalFontBold));
					hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(hcell);
				}
			}
			
			
			Query getItemQuery = entityManager.createNativeQuery(itemQuery);
			getItemQuery.setParameter(1, id);
			List<Object[]> itemList = getItemQuery.getResultList();
			
			Query getSummaryQuery = entityManager.createNativeQuery(summaryQuery);
			getSummaryQuery.setParameter(1, id);
			List<Object[]> summaryList = getSummaryQuery.getResultList();
			
			if(itemList!=null && !itemList.isEmpty()) {
				
				// Item list
				for (int i = 0; i < itemList.size(); i++) {
					Object[] objectArray = itemList.get(i);
					for (int j = 0; j < objectArray.length; j++) {
						PdfPCell hcell;
						if(j==0) {
							hcell = new PdfPCell(new Phrase(String.valueOf((int)Double.parseDouble(objectArray[j].toString())), tableNormalFont));	
						}else {
							hcell = new PdfPCell(new Phrase(objectArray[j].toString(), tableNormalFont));
						}
						
						hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
						hcell.setVerticalAlignment(Element.ALIGN_CENTER);
						hcell.setPadding(5);
						table.addCell(hcell);	
					}
					table.completeRow();
				}
				document.add(table);
				
				// Summary List
				PdfPTable summaryTable = new PdfPTable(2);
				summaryTable.setWidths(new int[] {18, 4 });
				int grandTotal= 0;
				for (int i = 0; i < summaryList.size(); i++) {
					Object[] objectArray = summaryList.get(i);
					for (int j = 0; j < objectArray.length; j++) {
						PdfPCell hcell;
						if(j==0) {
							hcell = new PdfPCell(new Phrase(objectArray[j].toString()+" "+"Total", tableNormalFontBold));	
						}else {
							grandTotal =grandTotal+(int)Double.parseDouble(objectArray[j].toString());
							hcell = new PdfPCell(new Phrase(String.valueOf((int)Double.parseDouble(objectArray[j].toString())), tableNormalFontBold));
						}
						
						hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
						hcell.setVerticalAlignment(Element.ALIGN_CENTER);
						hcell.setPadding(5);
						summaryTable.addCell(hcell);	
					}
					summaryTable.completeRow();
				}
				PdfPCell gtHeaderCell = new PdfPCell(new Phrase("Grand Total", tableNormalFontBold));
				gtHeaderCell.setHorizontalAlignment(Element.ALIGN_LEFT);
				gtHeaderCell.setVerticalAlignment(Element.ALIGN_CENTER);
				gtHeaderCell.setPadding(5);
				summaryTable.addCell(gtHeaderCell);
				
				PdfPCell gtValueCell = new PdfPCell(new Phrase(String.valueOf(grandTotal), tableNormalFontBold));
				gtValueCell.setHorizontalAlignment(Element.ALIGN_LEFT);
				gtValueCell.setVerticalAlignment(Element.ALIGN_CENTER);
				gtValueCell.setPadding(5);
				summaryTable.addCell(gtValueCell);
				summaryTable.completeRow();
				
				document.add(summaryTable);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addSignatureArea(Document doc, String title, Font font, int alignment) {
		try {
			PdfPTable titleHeader = new PdfPTable(1);
			PdfPCell cellTitle = new PdfPCell(new Paragraph(title, font));
			cellTitle.setColspan(3);
			cellTitle.setPadding(2);
			cellTitle.setHorizontalAlignment(alignment);
			cellTitle.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cellTitle.setBorder(Rectangle.NO_BORDER);
			cellTitle.setPaddingTop(70);
			titleHeader.addCell(cellTitle);
			doc.add(titleHeader);
		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		} catch (Exception e) {
			throw new ExceptionConverter(e);
		}
	}
}
