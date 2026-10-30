package com.pratikshat.exicom.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pratikshat.exicom.config.FileStorageProperties;
import com.pratikshat.exicom.entity.Annexure;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.QuoteTermsAndConditions;
import com.pratikshat.exicom.model.response.AnnexureLineItemResponse;

public class AnnexurePDFGeneration {

	EntityManager entityManager = null;
	FileStorageProperties fileStorageProperties = null;

	private Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL, BaseColor.BLACK);
	private Font headerDetailsFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK);
	private Font headerDetailsFontNormal = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLACK);
	private Font normalFontBlack = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL);
	private Font headingFont = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD);
	private Font productTableDataFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL, BaseColor.BLACK);
	private Font productTableHeaderFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD, BaseColor.BLACK);

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public AnnexurePDFGeneration(EntityManager entityManager, FileStorageProperties fileStorageProperties) {

		this.entityManager = entityManager;
		this.fileStorageProperties = fileStorageProperties;
	}

	
	
	public ByteArrayInputStream createPdf(long annexureId, LoginUser user) throws Exception {

		Annexure annexure = null;
		try {
			Query selectAnnexureQuery = entityManager.createQuery(
					"select anx from Annexure anx left join anx.vendor vnd left join anx.circle cir left join anx.createdBy cby left join anx.modifiedBy mby left join anx.owner onr where anx.id=?1",
					Annexure.class);
			selectAnnexureQuery.setParameter(1, annexureId);
			annexure = (Annexure) selectAnnexureQuery.getSingleResult();
		} catch (NoResultException ex) {

		}
		if (annexure == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Annexure does not exist");
		}
		
		List<AnnexureLineItemResponse> lineItemList = null;
		try {
			Query getQuery = entityManager.createQuery("select new com.pratikshat.exicom.model.response.AnnexureLineItemResponse(ali) from AnnexureLineItem ali left join ali.annexure anx left join ali.serviceAppointment sa left join ali.glCode gl left join ali.createdBy cby left join ali.modifiedBy mby left join sa.caseDetails cd left join cd.serviceCenter cs left join cd.site st left join st.account where anx.id=?1", AnnexureLineItemResponse.class);
			getQuery.setParameter(1, annexureId);
			lineItemList = getQuery.getResultList();
			
		}catch(NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Line Item does not exists");
		}
		

		if (lineItemList == null || lineItemList.isEmpty()) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Line Item does not exists");
		}

		
		List<QuoteTermsAndConditions> termsAndCondition = null;
		try {
			Query selectQuoteTermsAndConditionsQuery = entityManager.createQuery("select tc from QuoteTermsAndConditions tc where tc.objectName=?1 order by tc.serialNumber asc",QuoteTermsAndConditions.class);
			selectQuoteTermsAndConditionsQuery.setParameter(1, "Annexure");
			termsAndCondition = selectQuoteTermsAndConditionsQuery.getResultList();
			
		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Terms and Conditions not found for the template");
		}

		if (termsAndCondition == null || termsAndCondition.isEmpty()) {
			throw new com.pratikshat.exicom.exception.BadRequestException(
					"Terms and Conditions not found for the template");
		}

		Document document = new Document(PageSize.A4.rotate(), -40, -40, 40, 40);

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {

			PdfWriter writer = PdfWriter.getInstance(document, out);

			document.open();

			addHeader(document, writer);
			addHeading(document, "ANNEXURE");
			addEmptyLine(new Paragraph(), 1, document);
			addTitle(document, "Information");
			addAnnexureDetails(document, annexure);
			addEmptyLine(new Paragraph(), 1, document);
			addTitle(document, "Annexure Line Items");
			addEmptyLine(new Paragraph(), 1, document);
			addItemTable(document, lineItemList);
			addEmptyLine(new Paragraph(), 1, document);
			addEmptyLine(new Paragraph(), 1, document);
			addGuidelineTitle(document, "Guidelines for invoice submission : ");
			addEmptyLine(new Paragraph(), 1, document);
			addTermsAndCondition(document, termsAndCondition);
			addEmptyLine(new Paragraph(), 1, document);
			addEmptyLine(new Paragraph(), 1, document);
			addEmptyLine(new Paragraph(), 1, document);
			addSignatureArea(document);

			document.close();
		} catch (DocumentException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("error in creating pdf");
		}

		return new ByteArrayInputStream(out.toByteArray());
	}

	public void addHeader(Document documnet, PdfWriter writer) {
		PdfPTable header = new PdfPTable(2);
		try {

			Path fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
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

	public void addAnnexureDetails(Document document, Annexure annexure) {
		PdfPTable titleHeader = new PdfPTable(2);
		titleHeader.getDefaultCell().setBorder(Rectangle.NO_BORDER);

		PdfPTable t1 = new PdfPTable(2);
		t1.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		t1.setHorizontalAlignment(Element.ALIGN_LEFT);

		PdfPCell annexureHeadingCell = new PdfPCell();
		Paragraph annexureHeadingParagraph = new Paragraph();
		annexureHeadingParagraph.setLeading(10);
		annexureHeadingParagraph.setAlignment(Element.ALIGN_LEFT);
		annexureHeadingParagraph.add(new Chunk("Annexure Number :", normalFontBlack));
		annexureHeadingCell.addElement(annexureHeadingParagraph);
		annexureHeadingCell.setBorder(Rectangle.NO_BORDER);
		t1.addCell(annexureHeadingCell);

		PdfPCell annexureValueCell = new PdfPCell();
		Paragraph annexureValueParagraph = new Paragraph();
		annexureValueParagraph.setLeading(10);
		annexureValueParagraph.setAlignment(Element.ALIGN_LEFT);
		annexureValueParagraph.add(new Chunk(annexure.getManualId(), normalFont));
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
		vendorNameValueParagraph.add(new Chunk(annexure.getVendor().getAccount().getName(), normalFont));
		vendorNameValueCell.addElement(vendorNameValueParagraph);
		vendorNameValueCell.setBorder(Rectangle.NO_BORDER);
		t1.addCell(vendorNameValueCell);

		PdfPCell invoiceNumberHeadingCell = new PdfPCell();
		Paragraph invoiceNumberHeadingParagraph = new Paragraph();
		invoiceNumberHeadingParagraph.setLeading(10);
		invoiceNumberHeadingParagraph.setAlignment(Element.ALIGN_LEFT);
		invoiceNumberHeadingParagraph.add(new Chunk("Invoice Number :", normalFontBlack));
		invoiceNumberHeadingCell.addElement(invoiceNumberHeadingParagraph);
		invoiceNumberHeadingCell.setBorder(Rectangle.NO_BORDER);
		t1.addCell(invoiceNumberHeadingCell);

		PdfPCell invoiceNumberValueCell = new PdfPCell();
		Paragraph invoiceNumberValueParagraph = new Paragraph();
		invoiceNumberValueParagraph.setLeading(10);
		invoiceNumberValueParagraph.setAlignment(Element.ALIGN_LEFT);
		invoiceNumberValueParagraph.add(new Chunk(annexure.getInvoiceNumber(), normalFont));
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
		lineItemCountParagraph.add(new Chunk(String.valueOf(annexure.getLineItemCount()), normalFont));
		lineItemCountValueCell.addElement(lineItemCountParagraph);
		lineItemCountValueCell.setBorder(Rectangle.NO_BORDER);
		t1.addCell(lineItemCountValueCell);

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
		if (annexure.getApprovedByCircleDate() != null) {
			approvedByCircleValueParagraph.add(new Chunk(sdf.format(annexure.getApprovedByCircleDate()), normalFont));
		} else {
			approvedByCircleValueParagraph.add(new Chunk("", normalFont));
		}
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
		submittedDateHeadingParagraph.add(new Chunk("Submitted Date :", normalFontBlack));
		submittedDateHeadingCell.addElement(submittedDateHeadingParagraph);
		submittedDateHeadingCell.setBorder(Rectangle.NO_BORDER);
		t2.addCell(submittedDateHeadingCell);

		PdfPCell submittedDateValueCell = new PdfPCell();
		Paragraph submittedDateValueParagraph = new Paragraph();
		submittedDateValueParagraph.setLeading(10);
		submittedDateValueParagraph.setAlignment(Element.ALIGN_RIGHT);
		if (annexure.getSubmittedDate() != null) {
			submittedDateValueParagraph.add(new Chunk(sdf.format(annexure.getSubmittedDate()), normalFont));
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
		vendorCodeValueParagraph.add(new Chunk(annexure.getVendor().getAccount().getSapCustomerNumber(), normalFont));
		vendorCodeValueCell.addElement(vendorCodeValueParagraph);
		vendorCodeValueCell.setBorder(Rectangle.NO_BORDER);
		t2.addCell(vendorCodeValueCell);

		PdfPCell invoiceDateHeadingCell = new PdfPCell();
		Paragraph invoiceDateHeadingParagraph = new Paragraph();
		invoiceDateHeadingParagraph.setLeading(10);
		invoiceDateHeadingParagraph.setAlignment(Element.ALIGN_RIGHT);
		invoiceDateHeadingParagraph.add(new Chunk("Invoice Date :", normalFontBlack));
		invoiceDateHeadingCell.addElement(invoiceDateHeadingParagraph);
		invoiceDateHeadingCell.setBorder(Rectangle.NO_BORDER);
		t2.addCell(invoiceDateHeadingCell);

		PdfPCell invoiceDateValueCell = new PdfPCell();
		Paragraph invoiceDateValueParagraph = new Paragraph();
		invoiceDateValueParagraph.setLeading(10);
		invoiceDateValueParagraph.setAlignment(Element.ALIGN_RIGHT);
		if (annexure.getInvoiceDate() != null) {
			invoiceDateValueParagraph.add(new Chunk(sdf.format(annexure.getInvoiceDate()), normalFont));
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
				.add(new Chunk(String.format("Rs. %S", String.valueOf(annexure.getTotalAmount())), normalFont));
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
		if (annexure.getApprovedByCommercialDate() != null) {
			approvedByCommercialValueParagraph
					.add(new Chunk(sdf.format(annexure.getApprovedByCommercialDate()), normalFont));
		} else {
			approvedByCommercialValueParagraph.add(new Chunk("", normalFont));
		}
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

	public void addItemTable(Document document, List<AnnexureLineItemResponse> lineItemList) {
		try {
			PdfPTable table = new PdfPTable(15);
			table.setWidths(new int[] { 1, 2, 2, 2, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 });
			List<String> headers = new ArrayList<>();
			headers.add("Sl. No.");
			headers.add("Circle");
			headers.add("Customer Name");
			headers.add("Site ID");
			headers.add("Exchange/Site Name");
			headers.add("Case Number");
			headers.add("Activity");
			headers.add("Service Appointment");
			headers.add("GL Code");
			headers.add("Work Status");
			headers.add("Work Completion Date");
			headers.add("Rate");
			headers.add("Amount(With Tax)");
			headers.add("Exicom Invoice Number");
			headers.add("Exicom Invoice Date");

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

					AnnexureLineItemResponse lineItem = lineItemList.get(k);

					cell = new PdfPCell(new Phrase(String.valueOf(k + 1), productTableDataFont));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingLeft(1);
					cell.setPaddingRight(1);
					cell.setPaddingBottom(4);
					cell.setPaddingTop(1);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(lineItem.getCircleName(), productTableDataFont));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingLeft(1);
					cell.setPaddingRight(1);
					cell.setPaddingBottom(4);
					cell.setPaddingTop(1);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(lineItem.getAccountName(), productTableDataFont));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingLeft(1);
					cell.setPaddingRight(1);
					cell.setPaddingBottom(4);
					cell.setPaddingTop(1);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(lineItem.getSiteIdManual(), productTableDataFont));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingLeft(1);
					cell.setPaddingRight(1);
					cell.setPaddingBottom(4);
					cell.setPaddingTop(1);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(lineItem.getSiteName(), productTableDataFont));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingLeft(1);
					cell.setPaddingRight(1);
					cell.setPaddingBottom(4);
					cell.setPaddingTop(1);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(lineItem.getCaseIdManual(), productTableDataFont));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingLeft(1);
					cell.setPaddingRight(1);
					cell.setPaddingBottom(4);
					cell.setPaddingTop(1);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(lineItem.getActivity(), productTableDataFont));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingLeft(1);
					cell.setPaddingRight(1);
					cell.setPaddingBottom(4);
					cell.setPaddingTop(1);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(lineItem.getServiceAppointmentName(), productTableDataFont));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingLeft(1);
					cell.setPaddingRight(1);
					cell.setPaddingBottom(4);
					cell.setPaddingTop(1);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(lineItem.getGlCodeName(), productTableDataFont));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingLeft(1);
					cell.setPaddingRight(1);
					cell.setPaddingBottom(4);
					cell.setPaddingTop(1);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(lineItem.getWorkStatus(), productTableDataFont));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingLeft(1);
					cell.setPaddingRight(1);
					cell.setPaddingBottom(4);
					cell.setPaddingTop(1);
					table.addCell(cell);

					if(lineItem.getWorkCompletionDate()!=null) {
						cell = new PdfPCell(new Phrase(sdf.format(lineItem.getWorkCompletionDate()), productTableDataFont));
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
							new Phrase(String.format("Rs. %s", lineItem.getAmount()), productTableDataFont));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingLeft(1);
					cell.setPaddingRight(1);
					cell.setPaddingBottom(4);
					cell.setPaddingTop(1);
					table.addCell(cell);

					cell = new PdfPCell(
							new Phrase(String.format("Rs. %s", lineItem.getAmountWithTax()), productTableDataFont));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingLeft(1);
					cell.setPaddingRight(1);
					cell.setPaddingBottom(4);
					cell.setPaddingTop(1);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(lineItem.getExicomInvoiceNumber(), productTableDataFont));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPaddingLeft(1);
					cell.setPaddingRight(1);
					cell.setPaddingBottom(4);
					cell.setPaddingTop(1);
					table.addCell(cell);

					if(lineItem.getExicomInvoiceDate()!=null) {
						cell = new PdfPCell(new Phrase(sdf.format(lineItem.getExicomInvoiceDate()), productTableDataFont));
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
				}
			}
			document.add(table);
		} catch (Exception e) {
			throw new ExceptionConverter(e);
		}
	}

	public void addTermsAndCondition(Document documnet, List<QuoteTermsAndConditions> termsAndCondition) {
		PdfPTable header = new PdfPTable(1);
		if (termsAndCondition != null && termsAndCondition.size() > 0) {
			final Phrase p = new Phrase();
			for (int i = 0; i < termsAndCondition.size(); i++) {
				QuoteTermsAndConditions tAndC = termsAndCondition.get(i);

				p.add(new Chunk(tAndC.getSerialNumber() + ". " + tAndC.getRule() + ".", headerDetailsFontNormal));
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
