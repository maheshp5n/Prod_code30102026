package com.pratikshat.exicom.utils;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pratikshat.exicom.model.response.BtPacking;
import com.pratikshat.exicom.model.response.BtPackingItem;
import com.pratikshat.exicom.services.BtProductTransferService;
import com.pratikshat.exicom.services.BtReturnOrderService;
import com.pratikshat.exicom.services.BtServiceOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BtProductPackingPdfGen {

	@Autowired
	BtProductTransferService btTansfer;
	@Autowired
	BtReturnOrderService btReturnOrder;
	@Autowired BtServiceOrderService btSo;

	private Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL, BaseColor.BLACK);// DARK_GRAY
	private Font normalFontBold = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD);
	private Font titleBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD);
	private Font tableNormalFontBold = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD);
	private Font tableNormalFont = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL, BaseColor.BLACK);

	public void getTransferDocumentPT(Long id, OutputStream out) throws Exception {
		Document document = new Document(PageSize.A4, 0, 0, 40, 40);

		try {
			BtPacking pak = btTansfer.getPacking(id);
			PdfWriter writer = PdfWriter.getInstance(document, out);
			document.open();

			addHeader(document, pak, "ProductTransfer");

			addTitle(document, "Packing List", titleBoldFont, Element.ALIGN_CENTER);

			addItemTable(document, pak.getItems());

			addSignatureArea(document, "Signature:", normalFontBold, Element.ALIGN_LEFT);

			document.close();
			writer.flush();
		} catch (DocumentException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("error in creating pdf");
		}
	}

	public void getTransferDocumentRO(Long id, OutputStream out) throws Exception {
		Document document = new Document(PageSize.A4, 0, 0, 40, 40);

		try {
			BtPacking pak = btReturnOrder.getPacking(id);
			PdfWriter writer = PdfWriter.getInstance(document, out);
			document.open();

			addHeader(document, pak, "ReturnOrder");

			addTitle(document, "Packing List", titleBoldFont, Element.ALIGN_CENTER);

			addItemTable(document, pak.getItems());

			addSignatureArea(document, "Signature:", normalFontBold, Element.ALIGN_LEFT);

			document.close();
			writer.flush();
		} catch (DocumentException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("error in creating pdf");
		}
	}
	public void getTransferDocumentSO(Long id,OutputStream out) throws Exception{
		
		Document document = new Document(PageSize.A4, 0, 0, 40, 40);

		try {
			BtPacking pak = btSo.getPackingSo(id);

			PdfWriter writer = PdfWriter.getInstance(document, out);
			document.open();

			addHeader(document, pak, "ServiceOrder");

			addTitle(document, "Packing List", titleBoldFont, Element.ALIGN_CENTER);

			addItemTable(document, pak.getItems());

			addSignatureArea(document, "Signature:", normalFontBold, Element.ALIGN_LEFT);

			document.close();
			writer.flush();
		} catch (DocumentException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("error in creating pdf");
		}
	}

	public void getTransferDocumentSOR(Long id,OutputStream out) throws Exception{
		Document document = new Document(PageSize.A4, 0, 0, 40, 40);

		try {
			BtPacking pak = btSo.getPackingSor(id);

			PdfWriter writer = PdfWriter.getInstance(document, out);
			document.open();

			addHeader(document, pak, "ServiceOrderReturn");

			addTitle(document, "Packing List", titleBoldFont, Element.ALIGN_CENTER);

			addItemTable(document, pak.getItems());

			addSignatureArea(document, "Signature:", normalFontBold, Element.ALIGN_LEFT);

			document.close();
			writer.flush();
		} catch (DocumentException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("error in creating pdf");
		}
	}

	public void addHeader(Document documnet, BtPacking packing, String type) {

		try {

			String manualId = packing.getRef();// productTransfer[0].toString();
			Date createdDate = (Date) packing.getCreationDate();

			String sourceWarehouse = packing.getSrouceWarehouseName(); // productTransfer[2].toString();
			String destinationWarehouse = packing.getDestinationWarehouseName(); // productTransfer[3].toString();

			String sourceLegalEntity = packing.getSourceLegalEntity();// productTransfer[4].toString();
			String destinationLegalEntity = packing.getDestinationLegalEntity();// productTransfer[5].toString();

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
			switch (type) {
				case "ProductTransfer": {
					ManualIdHeader = "PT Number: ";
					break;
				}
				case "ReturnOrder": {
					ManualIdHeader = "RO Number: ";
					break;
				}
				case "ServiceOrder": {
					ManualIdHeader = "SO Number: ";
					break;
				}
				case "ServiceOrderReturn": {
					ManualIdHeader = "SOR Number: ";
					break;
				}
			}

			PdfPTable line31 = new PdfPTable(4);
			line31.getDefaultCell().setBorderWidth(0);
			Phrase manualIdHeaderPhrase = new Phrase();
			manualIdHeaderPhrase.add(new Chunk(ManualIdHeader, normalFontBold));
			PdfPCell manualIdHeaderCell = new PdfPCell(manualIdHeaderPhrase);
			manualIdHeaderCell.setBorder(Rectangle.NO_BORDER);
			manualIdHeaderCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			manualIdHeaderCell.setPaddingBottom(10);
			line31.addCell(manualIdHeaderCell);

			Phrase manualIdValuePhrase = new Phrase();
			manualIdValuePhrase.add(new Chunk(manualId, normalFont));
			PdfPCell manualIdValueCell = new PdfPCell(manualIdValuePhrase);
			manualIdValueCell.setBorder(Rectangle.NO_BORDER);
			manualIdValueCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			manualIdValueCell.setPaddingBottom(10);
			line31.addCell(manualIdValueCell);
			line31.completeRow();

			String createDateHeader = "";
			switch (type) {
				case "ProductTransfer": {
					createDateHeader = "Creation Date of PT: ";
					break;
				}
				case "ReturnOrder": {
					createDateHeader = "Creation Date of RO: ";
					break;
				}
				case "ServiceOrder": {
					createDateHeader = "Creation Date of SO: ";
					break;
				}
				case "ServiceOrderReturn": {
					createDateHeader = "Creation Date of SOR: ";
					break;
				}
			}

			PdfPTable line41 = new PdfPTable(4);
			line41.getDefaultCell().setBorderWidth(0);
			Phrase createdDateHeaderPhrase = new Phrase();
			createdDateHeaderPhrase.add(new Chunk(createDateHeader, normalFontBold));
			PdfPCell createdDateHeaderCell = new PdfPCell(createdDateHeaderPhrase);
			createdDateHeaderCell.setBorder(Rectangle.NO_BORDER);
			createdDateHeaderCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			createdDateHeaderCell.setPaddingBottom(10);
			line41.addCell(createdDateHeaderCell);

			Phrase createdDateValuePhrase = new Phrase();
			SimpleDateFormat outputDate = new SimpleDateFormat("dd/MM/yyyy");
			createdDateValuePhrase.add(new Chunk(outputDate.format(createdDate), normalFont));
			PdfPCell createdDateValueCell = new PdfPCell(createdDateValuePhrase);
			createdDateValueCell.setBorder(Rectangle.NO_BORDER);
			createdDateValueCell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			createdDateValueCell.setPaddingBottom(10);
			line41.addCell(createdDateValueCell);
			line41.completeRow();

			documnet.add(header);
			// documnet.add(line21);
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

	public void addItemTable(Document document, List<BtPackingItem> items) {
		try {
			PdfPTable table = new PdfPTable(6);
			table.setWidths(new int[] { 2, 3, 6, 4, 2, 5 });
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

			// Query getItemQuery = entityManager.createNativeQuery(itemQuery);
			// getItemQuery.setParameter(1, id);
			// List<Object[]> itemList = getItemQuery.getResultList();

			// Query getSummaryQuery = entityManager.createNativeQuery(summaryQuery);
			// getSummaryQuery.setParameter(1, id);
			// List<Object[]> summaryList = getSummaryQuery.getResultList();

			if (items != null && !items.isEmpty()) {

				Float grandTotal = 0F;
				Map<String, Float> otherComponent = new HashMap<String, Float>();
				// Item list
				for (int i = 0; i < items.size(); i++) {
					grandTotal += items.get(i).getQuantity();

					if (otherComponent.get(items.get(i).getProductType()) != null) {
						Float qty = otherComponent.get(items.get(i).getProductType());
						qty += items.get(i).getQuantity();
						otherComponent.put(items.get(i).getProductType(), qty);
					} else
						otherComponent.put(items.get(i).getProductType(), items.get(i).getQuantity());

					// Object[] objectArray = itemList.get(i);
					// for (int j = 0; j < objectArray.length; j++) {
					// PdfPCell hcell;
					// if(j==0) {
					// hcell = new PdfPCell(new
					// Phrase(String.valueOf((int)Double.parseDouble(objectArray[j].toString())),
					// tableNormalFont));
					// }else {
					// hcell = new PdfPCell(new Phrase(objectArray[j].toString(), tableNormalFont));
					// }

					// hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					// hcell.setVerticalAlignment(Element.ALIGN_CENTER);
					// hcell.setPadding(5);
					// table.addCell(hcell);
					// }

					PdfPCell hcell1;

					hcell1 = new PdfPCell(new Phrase("" + (i + 1), tableNormalFont));
					hcell1.setHorizontalAlignment(Element.ALIGN_LEFT);
					hcell1.setVerticalAlignment(Element.ALIGN_CENTER);
					hcell1.setPadding(5);
					table.addCell(hcell1);

					// table.completeRow();

					PdfPCell hcell2;

					hcell2 = new PdfPCell(new Phrase(items.get(i).getPartCode(), tableNormalFont));
					hcell2.setHorizontalAlignment(Element.ALIGN_LEFT);
					hcell2.setVerticalAlignment(Element.ALIGN_CENTER);
					hcell2.setPadding(5);
					table.addCell(hcell2);

					// table.completeRow();

					PdfPCell hcell3;

					hcell3 = new PdfPCell(new Phrase(items.get(i).getDescription(), tableNormalFont));
					hcell3.setHorizontalAlignment(Element.ALIGN_LEFT);
					hcell3.setVerticalAlignment(Element.ALIGN_CENTER);
					hcell3.setPadding(5);
					table.addCell(hcell3);

					// table.completeRow();

					PdfPCell hcell4;

					hcell4 = new PdfPCell(new Phrase(items.get(i).getProductType(), tableNormalFont));
					hcell4.setHorizontalAlignment(Element.ALIGN_LEFT);
					hcell4.setVerticalAlignment(Element.ALIGN_CENTER);
					hcell4.setPadding(5);
					table.addCell(hcell4);

					// table.completeRow();

					PdfPCell hcell5;

					hcell5 = new PdfPCell(new Phrase(items.get(i).getProductStatus(), tableNormalFont));
					hcell5.setHorizontalAlignment(Element.ALIGN_LEFT);
					hcell5.setVerticalAlignment(Element.ALIGN_CENTER);
					hcell5.setPadding(5);
					table.addCell(hcell5);

					// table.completeRow();

					PdfPCell hcell6;
					if (items.get(i).getSerialNumber() == null || items.get(i).getSerialNumber().equalsIgnoreCase(""))
						hcell6 = new PdfPCell(new Phrase("" + items.get(i).getQuantity(), tableNormalFont));

					else
						hcell6 = new PdfPCell(new Phrase(items.get(i).getSerialNumber(), tableNormalFont));
					hcell6.setHorizontalAlignment(Element.ALIGN_RIGHT);
					hcell6.setVerticalAlignment(Element.ALIGN_CENTER);
					hcell6.setPadding(5);
					table.addCell(hcell6);

					table.completeRow();
				}
				document.add(table);

				// // Summary List
				PdfPTable summaryTable = new PdfPTable(2);

				// summaryTable.setWidths(new int[] {18, 4 });
				// int grandTotal= 0;
				// for (int i = 0; i < summaryList.size(); i++) {
				// Object[] objectArray = summaryList.get(i);
				// for (int j = 0; j < objectArray.length; j++) {
				// PdfPCell hcell;
				// if(j==0) {
				// hcell = new PdfPCell(new Phrase(objectArray[j].toString()+" "+"Total",
				// tableNormalFontBold));
				// }else {
				// grandTotal =grandTotal+(int)Double.parseDouble(objectArray[j].toString());
				// hcell = new PdfPCell(new
				// Phrase(String.valueOf((int)Double.parseDouble(objectArray[j].toString())),
				// tableNormalFontBold));
				// }

				// hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
				// hcell.setVerticalAlignment(Element.ALIGN_CENTER);
				// hcell.setPadding(5);
				// summaryTable.addCell(hcell);
				// }
				// summaryTable.completeRow();
				// }

				PdfPCell gtHeaderCell = new PdfPCell(new Phrase("Grand Total", tableNormalFontBold));
				gtHeaderCell.setHorizontalAlignment(Element.ALIGN_LEFT);
				gtHeaderCell.setVerticalAlignment(Element.ALIGN_CENTER);
				gtHeaderCell.setPadding(5);
				summaryTable.addCell(gtHeaderCell);

				PdfPCell gtValueCell = new PdfPCell(new Phrase(String.valueOf(grandTotal), tableNormalFontBold));
				gtValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
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
