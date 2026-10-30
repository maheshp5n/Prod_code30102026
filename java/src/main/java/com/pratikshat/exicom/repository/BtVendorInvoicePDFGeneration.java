package com.pratikshat.exicom.repository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

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
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pratikshat.exicom.config.FileStorageProperties;
import com.pratikshat.exicom.entity.LoginUser;

@Component
public class BtVendorInvoicePDFGeneration {
	@Autowired
	JdbcTemplate jdbc;

	@Autowired
	FileStorageProperties storage;
	
	final static Logger logger=LoggerFactory.getLogger(BtVendorInvoicePDFGeneration.class);
	
	private Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL, BaseColor.BLACK);
	private Font headerDetailsFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK);
	private Font headerDetailsFontNormal = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLACK);
	private Font normalFontBlack = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL);
	private Font headingFont = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD);
	private Font productTableDataFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL, BaseColor.BLACK);
	private Font productTableHeaderFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD, BaseColor.BLACK);

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
//	public VendorInvoicePDFGeneration(EntityManager em,FileStorageProperties storage) {
//		this.em=em;
//		this.storage=storage;
//	}
	
	public ByteArrayOutputStream generatePDF(Long vendorInvoiceId,LoginUser user) throws Exception {
		
		Map<String,Object> vendorInvoiceDeatils=jdbc.queryForMap("SELECT \r\n"
				+ "	vd.`manual_id` AS invoice_number,\r\n"
				+ "	DATE_FORMAT(vd.`created_date`,'%d/%m/%y') AS invoice_submission_date,\r\n"
				+ "	vd.`vendor_name` AS`vendor_name`,\r\n"
				+ "	vd.`vendor_id` AS `vendor_code`,\r\n"
				+ "	vd.`vendor_invoice_number` AS venodr_invoice_number,\r\n"
				+ "	DATE_FORMAT(DATE_ADD(vd.`vendor_invoice_date`, INTERVAL 330 MINUTE) ,'%d/%m/%y') AS vendor_invoice_date,\r\n"
				+ "	vd.`invoice_total_amount` AS invoice_total_amount,\r\n"
				+ "	wcc.`manual_id` AS `wcc_number`,\r\n"
				+ "	DATE_FORMAT(vd.`last_modified_date`,'%d/%m/%y') AS wcc_approval_date,\r\n"
				+ "	wcc.`id` AS wccc_id\r\n"
				+ "	\r\n"
				+ "		FROM `bt_vendor_invoice_details` vd\r\n"
				+ "		JOIN `bt_wcc_details` wcc ON vd.`wcc_details_id` =wcc.`id` WHERE vd.`id`=?",vendorInvoiceId);
		
		if(vendorInvoiceDeatils==null)
			throw new Exception("Vendor Invoice Details not found");
		
		logger.info("vendor invoice details are ");
		logger.info("Data {}",vendorInvoiceDeatils);
		
		List<Map<String,Object>> lineItems=jdbc.queryForList("SELECT \r\n"
				+ "	sa.`circle_circle_name` ,\r\n"
				+ "	cust.`customer_name` ,\r\n"
				+ "	sd.`manual_id` AS manual_site_id ,\r\n"
				+ "	cd.`manual_id` ,\r\n"
				+ "	vpd.`activity_proce_list_description`,\r\n"
				+ "	sa.`manual_id` AS appointment_number,\r\n"
				+ "	vpd.`activity_price`,\r\n"
				+ "	sa.`visit_date`,\r\n"
				+ "	wsa.`amount`,\r\n"
				+ "	wsa.`total_amount`\r\n"
				+ "		FROM `bt_wcc_service_appointment` wsa\r\n"
				+ "		JOIN `bt_service_appointment` sa ON sa.`service_appointment_id` =wsa.`service_appointment_service_appointment_id`\r\n"
				+ "		LEFT JOIN `bt_case` cd ON cd.`id` =sa.`case_details_id`\r\n"
				+ "		LEFT JOIN `bt_site_details` sd ON sd.`id` =cd.`site_id`\r\n"
				+ "		LEFT JOIN `accounts` act ON act.`sap_customer_number` =sd.`account_sap_customer_number`\r\n"
				+ "		LEFT JOIN `customer_deatils` cust ON cust.`customer_id` =act.`parent_customer_customer_id`\r\n"
				+ "		LEFT JOIN `bt_vendor_po_details` vpd ON vpd.`id` = wsa.`po_details_id`\r\n"
				+ "		WHERE wsa.`wcc_id`=?",vendorInvoiceDeatils.get("wccc_id"));
		System.out.println(vendorInvoiceDeatils.get("wccc_id")+" wccc_id");
		
		if(lineItems==null||lineItems.size()==0)
			throw new Exception("Line items for vendor invoice details not found");
		
		List<Map<String,Object>> termAndCondation=jdbc.queryForList("select * from `quote_terms_and_conditions` where `object_name` like 'VendorInvoiceDetails' ");
		
		if(termAndCondation==null||termAndCondation.size()==0)
			throw new Exception("Vendor invoice term and condation not found");
		
		vendorInvoiceDeatils.put("line_item_count", lineItems.size());
		
		Document document = new Document(PageSize.A4.rotate(), -40, -40, 40, 40);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		try {

			PdfWriter writer = PdfWriter.getInstance(document, out);

			document.open();

			addHeader(document, writer);
			addHeading(document, "Invoice");
			addEmptyLine(new Paragraph(), 1, document);
			addTitle(document, "Information");
			addAnnexureDetails(document, vendorInvoiceDeatils);
			addEmptyLine(new Paragraph(), 1, document);
			addTitle(document, "Invoice Line Items");
			addEmptyLine(new Paragraph(), 1, document);
			addItemTable(document, lineItems);
			addEmptyLine(new Paragraph(), 1, document);
			addEmptyLine(new Paragraph(), 1, document);
			addGuidelineTitle(document, "Guidelines for invoice submission : ");
			//addEmptyLine(new Paragraph(), 1, document);
			addTermsAndCondition(document, termAndCondation);
			addEmptyLine(new Paragraph(), 1, document);
			addEmptyLine(new Paragraph(), 1, document);
			addEmptyLine(new Paragraph(), 1, document);
			addSignatureArea(document);

			document.close();
		} catch (DocumentException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("error in creating pdf");
		}
		
		return out;
	}
	
	public void addHeader(Document documnet, PdfWriter writer) {
		PdfPTable header = new PdfPTable(2);
		try {

			Path fileStorageLocation = Paths.get(storage.getUploadDir()).toAbsolutePath().normalize();
			Image image = Image.getInstance(fileStorageLocation + "/" + "logo.png");

			image.scalePercent(50);
			image.setAlignment(Element.ALIGN_LEFT);
			PdfPCell cellImage = new PdfPCell(image);
			cellImage.setBorder(Rectangle.NO_BORDER);
			header.addCell(cellImage);
			header.setPaddingTop(0);

			final Phrase p = new Phrase();
			p.add(Chunk.NEWLINE);
			p.add(Chunk.NEWLINE);
			p.add(new Chunk("Exicom Tele-Systems Ltd.", headerDetailsFont));
			p.add(Chunk.NEWLINE);
			p.add(new Chunk("Industrial Plot No 2A,", headerDetailsFont));
			p.add(Chunk.NEWLINE);
			p.add(new Chunk("Sector-18 Gurgaon, Haryana-122015", headerDetailsFont));

			PdfPCell celltitle = new PdfPCell(p);
			celltitle.setBorder(Rectangle.NO_BORDER);
			celltitle.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
			celltitle.setPaddingBottom(20);

			header.getDefaultCell().setBorder(Rectangle.BOTTOM);
			header.addCell(celltitle);
			header.completeRow();

			PdfContentByte canvas = writer.getDirectContent();
			CMYKColor magentaColor = new CMYKColor(1.f, 1.f, 1.f, 1.f);
			canvas.setColorStroke(magentaColor);
			canvas.setLineWidth(1);
			canvas.moveTo(40, 480);
			canvas.lineTo(794, 480);
			canvas.closePathStroke();

			documnet.add(header);
		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		} catch (MalformedURLException e) {
			throw new ExceptionConverter(e);
		} catch (IOException e) {
			throw new ExceptionConverter(e);
		}
	}
	
	public void addHeading(Document documnet, String title) {
		try {
			PdfPTable titleHeader = new PdfPTable(1);

			Font myContentStyle = new Font(); /* Define a new Font Object */
			myContentStyle.setFamily("Times New Roman");
			myContentStyle.setStyle("bold");
			myContentStyle.setStyle("underline");
			myContentStyle.setSize(12);
			PdfPCell cellTitle = new PdfPCell(new Paragraph(title, myContentStyle));
			cellTitle.setColspan(3);
			cellTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellTitle.setPadding(5.0f);
			// BaseColor myColor = new BaseColor(222,229,236);
			// cellTitle.setBackgroundColor(myColor);
			cellTitle.setBorder(Rectangle.NO_BORDER);
			titleHeader.addCell(cellTitle);
			documnet.add(titleHeader);
		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		}
	}
	public void addEmptyLine(Paragraph paragraph, int number, Document document) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
			try {
				document.add(paragraph);
			} catch (DocumentException e) {
				throw new ExceptionConverter(e);
			}
		}
	}
	public void addTitle(Document documnet, String title) {
		try {
			PdfPTable titleHeader = new PdfPTable(1);
			PdfPCell cellTitle = new PdfPCell(new Paragraph(title, headingFont));
			cellTitle.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellTitle.setPadding(5.0f);
			cellTitle.setBorder(Rectangle.NO_BORDER);
			titleHeader.addCell(cellTitle);
			documnet.add(titleHeader);
		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		}
	}
	
	public void addAnnexureDetails(Document document, Map<String,Object> vendorInvoiceDetails) {
		PdfPTable titleHeader = new PdfPTable(2);
		titleHeader.getDefaultCell().setBorder(Rectangle.NO_BORDER);

		PdfPTable t1 = new PdfPTable(2);
		t1.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		t1.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell annexureHeadingCell = new PdfPCell();
		Paragraph annexureHeadingParagraph = new Paragraph();
		annexureHeadingParagraph.setLeading(10);
		annexureHeadingParagraph.setAlignment(Element.ALIGN_LEFT);
		annexureHeadingParagraph.add(new Chunk("Invoice Number :", normalFontBlack));
		annexureHeadingCell.addElement(annexureHeadingParagraph);
		annexureHeadingCell.setBorder(Rectangle.NO_BORDER);
		t1.addCell(annexureHeadingCell);

		PdfPCell annexureValueCell = new PdfPCell();
		Paragraph annexureValueParagraph = new Paragraph();
		annexureValueParagraph.setLeading(10);
		annexureValueParagraph.setAlignment(Element.ALIGN_LEFT);
		annexureValueParagraph.add(new Chunk(vendorInvoiceDetails.get("invoice_number").toString(), normalFont));
		annexureValueCell.addElement(annexureValueParagraph);
		annexureValueCell.setBorder(Rectangle.NO_BORDER);
		t1.addCell(annexureValueCell);

		PdfPCell vendorNameHeadingCell = new PdfPCell();
		Paragraph vendorNameHeadingParagraph = new Paragraph();
		vendorNameHeadingParagraph.setLeading(10);
		vendorNameHeadingParagraph.setAlignment(Element.ALIGN_LEFT);
		vendorNameHeadingParagraph.add(new Chunk("Vendor Name :", normalFontBlack));
		vendorNameHeadingCell.addElement(vendorNameHeadingParagraph);
		vendorNameHeadingCell.setBorder(Rectangle.NO_BORDER);
		t1.addCell(vendorNameHeadingCell);

		PdfPCell vendorNameValueCell = new PdfPCell();
		Paragraph vendorNameValueParagraph = new Paragraph();
		vendorNameValueParagraph.setLeading(10);
		vendorNameValueParagraph.setAlignment(Element.ALIGN_LEFT);
		vendorNameValueParagraph.add(new Chunk(vendorInvoiceDetails.get("vendor_name").toString(), normalFont));
		vendorNameValueCell.addElement(vendorNameValueParagraph);
		vendorNameValueCell.setBorder(Rectangle.NO_BORDER);
		t1.addCell(vendorNameValueCell);

		PdfPCell invoiceNumberHeadingCell = new PdfPCell();
		Paragraph invoiceNumberHeadingParagraph = new Paragraph();
		invoiceNumberHeadingParagraph.setLeading(10);
		invoiceNumberHeadingParagraph.setAlignment(Element.ALIGN_LEFT);
		invoiceNumberHeadingParagraph.add(new Chunk("Vendor Invoice Number :", normalFontBlack));
		invoiceNumberHeadingCell.addElement(invoiceNumberHeadingParagraph);
		invoiceNumberHeadingCell.setBorder(Rectangle.NO_BORDER);
		t1.addCell(invoiceNumberHeadingCell);

		PdfPCell invoiceNumberValueCell = new PdfPCell();
		Paragraph invoiceNumberValueParagraph = new Paragraph();
		invoiceNumberValueParagraph.setLeading(10);
		invoiceNumberValueParagraph.setAlignment(Element.ALIGN_LEFT);
		invoiceNumberValueParagraph.add(new Chunk(vendorInvoiceDetails.get("venodr_invoice_number").toString(), normalFont));
		invoiceNumberValueCell.addElement(invoiceNumberValueParagraph);
		invoiceNumberValueCell.setBorder(Rectangle.NO_BORDER);
		t1.addCell(invoiceNumberValueCell);

		PdfPCell lineItemCountHeadingCell = new PdfPCell();
		Paragraph lineItemCountHeadingParagraph = new Paragraph();
		lineItemCountHeadingParagraph.setLeading(10);
		lineItemCountHeadingParagraph.setAlignment(Element.ALIGN_LEFT);
		lineItemCountHeadingParagraph.add(new Chunk("Line Item Count", normalFont));
		lineItemCountHeadingCell.addElement(lineItemCountHeadingParagraph);
		lineItemCountHeadingCell.setBorder(Rectangle.NO_BORDER);
		t1.addCell(lineItemCountHeadingCell);

		PdfPCell lineItemCountValueCell = new PdfPCell();
		Paragraph lineItemCountParagraph = new Paragraph();
		lineItemCountParagraph.setLeading(10);
		lineItemCountParagraph.setAlignment(Element.ALIGN_LEFT);
		lineItemCountParagraph.add(new Chunk(String.valueOf(vendorInvoiceDetails.get("line_item_count").toString()), normalFont));
		
		lineItemCountValueCell.addElement(lineItemCountParagraph);
		lineItemCountValueCell.setBorder(Rectangle.NO_BORDER);
		t1.addCell(lineItemCountValueCell);

		//WCCC NUMBER 
		
		PdfPCell wccCountHeadingCell = new PdfPCell();
		Paragraph wccCountHeadingParagraph = new Paragraph();
		wccCountHeadingParagraph.setLeading(10);
		wccCountHeadingParagraph.setAlignment(Element.ALIGN_LEFT);
		wccCountHeadingParagraph.add(new Chunk("WCC Number", normalFont));
		wccCountHeadingCell.addElement(wccCountHeadingParagraph);
		wccCountHeadingCell.setBorder(Rectangle.NO_BORDER);
		t1.addCell(wccCountHeadingCell);

		PdfPCell wccCountValueCell = new PdfPCell();
		Paragraph wccCountParagraph = new Paragraph();
		wccCountParagraph.setLeading(10);
		wccCountParagraph.setAlignment(Element.ALIGN_LEFT);
		wccCountParagraph.add(new Chunk(String.valueOf(vendorInvoiceDetails.get("wcc_number").toString()), normalFont));
		
		wccCountValueCell.addElement(wccCountParagraph);
		wccCountValueCell.setBorder(Rectangle.NO_BORDER);
		t1.addCell(wccCountValueCell);
		
		//END WCC NUMBER
		
		PdfPCell approvedByCircleHeadingCell = new PdfPCell();
		Paragraph approvedByCircleHeadingParagraph = new Paragraph();
		approvedByCircleHeadingParagraph.setLeading(10);
		approvedByCircleHeadingParagraph.setAlignment(Element.ALIGN_LEFT);
		approvedByCircleHeadingParagraph.add(new Chunk("Approved By Circle :", normalFontBlack));
		approvedByCircleHeadingCell.addElement(approvedByCircleHeadingParagraph);
		approvedByCircleHeadingCell.setBorder(Rectangle.NO_BORDER);
		t1.addCell(approvedByCircleHeadingCell);

		PdfPCell approvedByCircleValueCell = new PdfPCell();
		Paragraph approvedByCircleValueParagraph = new Paragraph();
		approvedByCircleValueParagraph.setLeading(10);
		approvedByCircleValueParagraph.setAlignment(Element.ALIGN_LEFT);
		
//		if (annexure.getApprovedByCircleDate() != null) {
//			approvedByCircleValueParagraph.add(new Chunk(sdf.format(annexure.getApprovedByCircleDate()), normalFont));
//		} else {
//			
//		}
		
		approvedByCircleValueParagraph.add(new Chunk("", normalFont));
		
		approvedByCircleValueCell.addElement(approvedByCircleValueParagraph);
		approvedByCircleValueCell.setBorder(Rectangle.NO_BORDER);
		t1.addCell(approvedByCircleValueCell);

		titleHeader.addCell(t1);

		PdfPTable t2 = new PdfPTable(2);
		t2.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		t1.setHorizontalAlignment(Element.ALIGN_RIGHT);

		PdfPCell submittedDateHeadingCell = new PdfPCell();
		Paragraph submittedDateHeadingParagraph = new Paragraph();
		submittedDateHeadingParagraph.setLeading(10);
		submittedDateHeadingParagraph.setAlignment(Element.ALIGN_RIGHT);
		submittedDateHeadingParagraph.add(new Chunk("Invoice Submission Date :", normalFontBlack));
		submittedDateHeadingCell.addElement(submittedDateHeadingParagraph);
		submittedDateHeadingCell.setBorder(Rectangle.NO_BORDER);
		t2.addCell(submittedDateHeadingCell);

		PdfPCell submittedDateValueCell = new PdfPCell();
		Paragraph submittedDateValueParagraph = new Paragraph();
		submittedDateValueParagraph.setLeading(10);
		submittedDateValueParagraph.setAlignment(Element.ALIGN_RIGHT);
		if (vendorInvoiceDetails.get("invoice_submission_date") != null) {
			submittedDateValueParagraph.add(new Chunk(vendorInvoiceDetails.get("invoice_submission_date").toString(), normalFont));
		} else {
			submittedDateValueParagraph.add(new Chunk("", normalFont));
		}
		submittedDateValueCell.addElement(submittedDateValueParagraph);
		submittedDateValueCell.setBorder(Rectangle.NO_BORDER);
		t2.addCell(submittedDateValueCell);

		PdfPCell vendorCodeHeadingCell = new PdfPCell();
		Paragraph vendorCodeHeadingParagraph = new Paragraph();
		vendorCodeHeadingParagraph.setLeading(10);
		vendorCodeHeadingParagraph.setAlignment(Element.ALIGN_RIGHT);
		vendorCodeHeadingParagraph.add(new Chunk("Vendor Code :", normalFontBlack));
		vendorCodeHeadingCell.addElement(vendorCodeHeadingParagraph);
		vendorCodeHeadingCell.setBorder(Rectangle.NO_BORDER);
		t2.addCell(vendorCodeHeadingCell);

		PdfPCell vendorCodeValueCell = new PdfPCell();
		Paragraph vendorCodeValueParagraph = new Paragraph();
		vendorCodeValueParagraph.setLeading(10);
		vendorCodeValueParagraph.setAlignment(Element.ALIGN_RIGHT);
		vendorCodeValueParagraph.add(new Chunk(vendorInvoiceDetails.get("vendor_code").toString(), normalFont));
		vendorCodeValueCell.addElement(vendorCodeValueParagraph);
		vendorCodeValueCell.setBorder(Rectangle.NO_BORDER);
		t2.addCell(vendorCodeValueCell);

		PdfPCell invoiceDateHeadingCell = new PdfPCell();
		Paragraph invoiceDateHeadingParagraph = new Paragraph();
		invoiceDateHeadingParagraph.setLeading(10);
		invoiceDateHeadingParagraph.setAlignment(Element.ALIGN_RIGHT);
		invoiceDateHeadingParagraph.add(new Chunk("Vendor Invoice Date :", normalFontBlack));
		invoiceDateHeadingCell.addElement(invoiceDateHeadingParagraph);
		invoiceDateHeadingCell.setBorder(Rectangle.NO_BORDER);
		t2.addCell(invoiceDateHeadingCell);

		PdfPCell invoiceDateValueCell = new PdfPCell();
		Paragraph invoiceDateValueParagraph = new Paragraph();
		invoiceDateValueParagraph.setLeading(10);
		invoiceDateValueParagraph.setAlignment(Element.ALIGN_RIGHT);
		if (vendorInvoiceDetails.get("vendor_invoice_date") != null) {
			invoiceDateValueParagraph.add(new Chunk(vendorInvoiceDetails.get("vendor_invoice_date").toString(), normalFont));
		} else {
			invoiceDateValueParagraph.add(new Chunk("", normalFont));
		}
		invoiceDateValueCell.addElement(invoiceDateValueParagraph);
		invoiceDateValueCell.setBorder(Rectangle.NO_BORDER);
		t2.addCell(invoiceDateValueCell);

		PdfPCell totalAmountHeadingCell = new PdfPCell();
		Paragraph totalAmountHeadingParagraph = new Paragraph();
		totalAmountHeadingParagraph.setLeading(10);
		totalAmountHeadingParagraph.setAlignment(Element.ALIGN_RIGHT);
		totalAmountHeadingParagraph.add(new Chunk("Total Amount :", normalFontBlack));
		totalAmountHeadingCell.addElement(totalAmountHeadingParagraph);
		totalAmountHeadingCell.setBorder(Rectangle.NO_BORDER);
		t2.addCell(totalAmountHeadingCell);

		PdfPCell totalAmountValueCell = new PdfPCell();
		Paragraph totalAmountValueParagraph = new Paragraph();
		totalAmountValueParagraph.setLeading(10);
		totalAmountValueParagraph.setAlignment(Element.ALIGN_RIGHT);
		totalAmountValueParagraph
				.add(new Chunk(String.format("Rs. %S", String.valueOf(vendorInvoiceDetails.get("invoice_total_amount").toString())), normalFont));
		totalAmountValueCell.addElement(totalAmountValueParagraph);
		totalAmountValueCell.setBorder(Rectangle.NO_BORDER);
		t2.addCell(totalAmountValueCell);

		PdfPCell approvedByCommercialHeadingCell = new PdfPCell();
		Paragraph approvedByCommercialHeadingParagraph = new Paragraph();
		approvedByCommercialHeadingParagraph.setLeading(10);
		approvedByCommercialHeadingParagraph.setAlignment(Element.ALIGN_RIGHT);
		approvedByCommercialHeadingParagraph.add(new Chunk("Approved By Commercial :", normalFontBlack));
		approvedByCommercialHeadingCell.addElement(approvedByCommercialHeadingParagraph);
		approvedByCommercialHeadingCell.setBorder(Rectangle.NO_BORDER);
		t2.addCell(approvedByCommercialHeadingCell);

		PdfPCell approvedByCommercialValueCell = new PdfPCell();
		Paragraph approvedByCommercialValueParagraph = new Paragraph();
		approvedByCommercialValueParagraph.setLeading(10);
		approvedByCommercialValueParagraph.setAlignment(Element.ALIGN_RIGHT);
//		if (annexure.getApprovedByCommercialDate() != null) {
//			approvedByCommercialValueParagraph
//					.add(new Chunk(sdf.format(annexure.getApprovedByCommercialDate()), normalFont));
//		} else {
//			approvedByCommercialValueParagraph.add(new Chunk("", normalFont));
//		}
		approvedByCommercialValueParagraph.add(new Chunk("", normalFont));
		approvedByCommercialValueCell.addElement(approvedByCommercialValueParagraph);
		approvedByCommercialValueCell.setBorder(Rectangle.NO_BORDER);
		t2.addCell(approvedByCommercialValueCell);
		
		
		titleHeader.addCell(t2);

		try {
			document.add(titleHeader);
		} catch (DocumentException e) {
			throw new ExceptionConverter(e);
		}
	}
	public void addItemTable(Document document, List<Map<String,Object>> lineItemList) {
		try {
			PdfPTable table = new PdfPTable(11);
			table.setWidths(new int[] { 1, 2, 2, 2, 3, 2, 2, 2, 2, 2, 2 });
			List<String> headers = new ArrayList<>();
			headers.add("Sl. No.");
			headers.add("Circle");
			headers.add("Customer Name");
			headers.add("Site ID");
//			headers.add("Exchange/Site Name");
			headers.add("Case Number");
			headers.add("Activity");
			headers.add("Service Appointment");
			headers.add("Activity Price");
//			headers.add("Work Status");
			headers.add("Work Completion Date");
			headers.add("Rate");
			headers.add("Amount(With Tax)");
//			headers.add("Exicom Invoice Number");
//			headers.add("Exicom Invoice Date");

			if (headers.size() > 0) {
				for (int i = 0; i < headers.size(); i++) {
					PdfPCell hcell;
					hcell = new PdfPCell(new Phrase(headers.get(i), productTableHeaderFont));
					hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
					hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					hcell.setPaddingLeft(1);
					hcell.setPaddingRight(1);
					hcell.setPaddingBottom(4);
					hcell.setPaddingTop(1);
					table.addCell(hcell);
				}
			}

			if (lineItemList.size() > 0) {
				PdfPCell cell = null;
				for (int k = 0; k < lineItemList.size(); k++) {

					Map<String,Object> lineItem = lineItemList.get(k);

					cell = new PdfPCell(new Phrase(String.valueOf(k + 1), productTableDataFont));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingLeft(1);
					cell.setPaddingRight(1);
					cell.setPaddingBottom(4);
					cell.setPaddingTop(1);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(lineItem.get("circle_circle_name").toString(), productTableDataFont));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingLeft(1);
					cell.setPaddingRight(1);
					cell.setPaddingBottom(4);
					cell.setPaddingTop(1);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(lineItem.get("customer_name").toString(), productTableDataFont));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingLeft(1);
					cell.setPaddingRight(1);
					cell.setPaddingBottom(4);
					cell.setPaddingTop(1);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(lineItem.get("manual_site_id").toString(), productTableDataFont));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingLeft(1);
					cell.setPaddingRight(1);
					cell.setPaddingBottom(4);
					cell.setPaddingTop(1);
					table.addCell(cell);

//					cell = new PdfPCell(new Phrase(lineItem.getSiteName(), productTableDataFont));
//					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//					cell.setPaddingLeft(1);
//					cell.setPaddingRight(1);
//					cell.setPaddingBottom(4);
//					cell.setPaddingTop(1);
//					table.addCell(cell);

					cell = new PdfPCell(new Phrase(lineItem.get("manual_id").toString(), productTableDataFont));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingLeft(1);
					cell.setPaddingRight(1);
					cell.setPaddingBottom(4);
					cell.setPaddingTop(1);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(lineItem.get("activity_proce_list_description").toString(), productTableDataFont));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingLeft(1);
					cell.setPaddingRight(1);
					cell.setPaddingBottom(4);
					cell.setPaddingTop(1);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(lineItem.get("appointment_number").toString(), productTableDataFont));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingLeft(1);
					cell.setPaddingRight(1);
					cell.setPaddingBottom(4);
					cell.setPaddingTop(1);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(lineItem.get("activity_price").toString(), productTableDataFont));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingLeft(1);
					cell.setPaddingRight(1);
					cell.setPaddingBottom(4);
					cell.setPaddingTop(1);
					table.addCell(cell);

//					cell = new PdfPCell(new Phrase(lineItem.getWorkStatus(), productTableDataFont));
//					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//					cell.setPaddingLeft(1);
//					cell.setPaddingRight(1);
//					cell.setPaddingBottom(4);
//					cell.setPaddingTop(1);
//					table.addCell(cell);

					if(lineItem.get("visit_date")!=null) {
						cell = new PdfPCell(new Phrase(lineItem.get("visit_date").toString(), productTableDataFont));
					}else {
						cell = new PdfPCell(new Phrase("", productTableDataFont));
					}
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingLeft(1);
					cell.setPaddingRight(1);
					cell.setPaddingBottom(4);
					cell.setPaddingTop(1);
					table.addCell(cell);

					cell = new PdfPCell(
							new Phrase(String.format("Rs. %s", lineItem.get("amount")), productTableDataFont));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingLeft(1);
					cell.setPaddingRight(1);
					cell.setPaddingBottom(4);
					cell.setPaddingTop(1);
					table.addCell(cell);

					cell = new PdfPCell(
							new Phrase(String.format("Rs. %s", lineItem.get("total_amount")), productTableDataFont));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingLeft(1);
					cell.setPaddingRight(1);
					cell.setPaddingBottom(4);
					cell.setPaddingTop(1);
					table.addCell(cell);

//					cell = new PdfPCell(new Phrase(lineItem.getExicomInvoiceNumber(), productTableDataFont));
//					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//					cell.setPaddingLeft(1);
//					cell.setPaddingRight(1);
//					cell.setPaddingBottom(4);
//					cell.setPaddingTop(1);
//					table.addCell(cell);
//
//					if(lineItem.getExicomInvoiceDate()!=null) {
//						cell = new PdfPCell(new Phrase(sdf.format(lineItem.getExicomInvoiceDate()), productTableDataFont));
//					}else {
//						cell = new PdfPCell(new Phrase("", productTableDataFont));
//					}
//					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//					cell.setPaddingLeft(1);
//					cell.setPaddingRight(1);
//					cell.setPaddingBottom(4);
//					cell.setPaddingTop(1);
//					table.addCell(cell);
				}
			}
			document.add(table);
		} catch (Exception e) {
			throw new ExceptionConverter(e);
		}
	}
	
	public void addGuidelineTitle(Document documnet, String title) {
		try {
			PdfPTable titleHeader = new PdfPTable(1);
			PdfPCell cellTitle = new PdfPCell(new Paragraph(title, headerDetailsFont));
			cellTitle.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellTitle.setPadding(5.0f);
			cellTitle.setBorder(Rectangle.NO_BORDER);
			titleHeader.addCell(cellTitle);
			documnet.add(titleHeader);
			
		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		}
	}
	
	public void addTermsAndCondition(Document documnet, List<Map<String,Object>> termsAndCondition) {
		PdfPTable header = new PdfPTable(1);
		if (termsAndCondition != null && termsAndCondition.size() > 0) {
			final Phrase p = new Phrase();
			for (int i = 0; i < termsAndCondition.size(); i++) {
				Map<String,Object> tAndC = termsAndCondition.get(i);

				p.add(new Chunk(tAndC.get("serial_number").toString() + ". " + tAndC.get("rule").toString() + ".", headerDetailsFontNormal));
				p.add(Chunk.NEWLINE);
			}

			try {

				PdfPCell celltitle = new PdfPCell(p);

				celltitle.setBorder(0);
				header.getDefaultCell().setBorderWidth(Rectangle.NO_BORDER);
				header.addCell(celltitle);
				header.completeRow();
				documnet.add(header);
			} catch (DocumentException de) {
				throw new ExceptionConverter(de);
			} catch (Exception e) {
				throw new ExceptionConverter(e);
			}
		}
	}
	
	public void addSignatureArea(Document document) {

		try {
			PdfPTable titleHeader = new PdfPTable(4);
			titleHeader.getDefaultCell().setBorder(Rectangle.NO_BORDER);

			PdfPCell annexureHeadingCell = new PdfPCell();
			Paragraph annexureHeadingParagraph = new Paragraph();
			annexureHeadingParagraph.setAlignment(Element.ALIGN_CENTER);
			annexureHeadingParagraph.add(new Chunk("Vendor Singnature", headerDetailsFontNormal));
			annexureHeadingCell.addElement(annexureHeadingParagraph);
			annexureHeadingCell.setBorder(Rectangle.NO_BORDER);
			titleHeader.addCell(annexureHeadingCell);

			PdfPCell annexureValueCell = new PdfPCell();
			Paragraph annexureValueParagraph = new Paragraph();
			annexureValueParagraph.setAlignment(Element.ALIGN_CENTER);
			annexureValueParagraph.add(new Chunk("Circle Manager Signature", headerDetailsFontNormal));
			annexureValueCell.addElement(annexureValueParagraph);
			annexureValueCell.setBorder(Rectangle.NO_BORDER);
			titleHeader.addCell(annexureValueCell);

			PdfPCell vendorNameHeadingCell = new PdfPCell();
			Paragraph vendorNameHeadingParagraph = new Paragraph();
			vendorNameHeadingParagraph.setAlignment(Element.ALIGN_CENTER);
			vendorNameHeadingParagraph.add(new Chunk("Commercial Head Signature", headerDetailsFontNormal));
			vendorNameHeadingCell.addElement(vendorNameHeadingParagraph);
			vendorNameHeadingCell.setBorder(Rectangle.NO_BORDER);
			titleHeader.addCell(vendorNameHeadingCell);

			PdfPCell vendorNameValueCell = new PdfPCell();
			Paragraph vendorNameValueParagraph = new Paragraph();
			vendorNameValueParagraph.setAlignment(Element.ALIGN_CENTER);
			vendorNameValueParagraph.add(new Chunk("HoD Signature", headerDetailsFontNormal));
			vendorNameValueCell.addElement(vendorNameValueParagraph);
			vendorNameValueCell.setBorder(Rectangle.NO_BORDER);
			titleHeader.addCell(vendorNameValueCell);

			document.add(titleHeader);
		} catch (DocumentException e) {
			throw new ExceptionConverter(e);
		}
	}

}
