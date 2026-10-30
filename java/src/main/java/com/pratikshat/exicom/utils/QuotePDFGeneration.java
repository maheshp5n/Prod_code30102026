package com.pratikshat.exicom.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.Quote;
import com.pratikshat.exicom.entity.QuotePdfHeaderAndFooter;
import com.pratikshat.exicom.entity.QuoteTermsAndConditions;
import com.pratikshat.exicom.entity.StateWithQuotePdf;
import com.pratikshat.exicom.entity.TaxAndVisitCharges;
import com.pratikshat.exicom.entity.UploadedFiles;
import com.pratikshat.exicom.event.QuoteHeaderFooterPageEvent;
import com.pratikshat.exicom.model.response.QuoteLineItemResponse;

public class QuotePDFGeneration {

	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private  Font catFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL,BaseColor.DARK_GRAY);
	private  Font normalFont = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL, BaseColor.DARK_GRAY);
	private  Font headerDetailsFont = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL, BaseColor.DARK_GRAY);
	private  Font normalFontBlack = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);
	private  Font productTableDataFont = new Font(Font.FontFamily.UNDEFINED,9,Font.NORMAL,BaseColor.DARK_GRAY);
	private  Font productTableHeaderFont = new Font(Font.FontFamily.HELVETICA,10,Font.NORMAL,BaseColor.WHITE);
	
	EntityManager entityManager = null;
	FileStorageProperties fileStorageProperties = null;
	
	
	public QuotePDFGeneration(EntityManager entityManager, FileStorageProperties fileStorageProperties) {
		
		this.entityManager = entityManager;
		this.fileStorageProperties = fileStorageProperties;
	}
	
	
	public ByteArrayInputStream createPdf(long quoteId, LoginUser user) throws Exception {

		Quote quote = null;
		try {
			Query selectQuoteQuery = entityManager.createQuery(
					"select qt from Quote qt left join qt.oppertunity opr left join opr.accounts acn left join opr.caseDetails cd left join cd.site st left join st.circle crl left join qt.quoteStatus qtst left join qt.billingCountry bc left join qt.billingState bs left join qt.shippingCountry sc left join qt.shippingState ss left join qt.createdBy createdby left join qt.modifiedBy modified where qt.id=?1",
					Quote.class);
			selectQuoteQuery.setParameter(1, quoteId);
			quote = (Quote) selectQuoteQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Quote does not exist");
		}

		TaxAndVisitCharges taxAndVisitCharges = null;
		try {
			Query selectTaxAndVisitChargesQuery = entityManager.createQuery(
					"select ts from TaxAndVisitCharges ts left join ts.businessDomain bd where bd.businessDomainId=?1",
					TaxAndVisitCharges.class);
			selectTaxAndVisitChargesQuery.setParameter(1, quote.getBusinessDomain().getBusinessDomainId());
			taxAndVisitCharges = (TaxAndVisitCharges) selectTaxAndVisitChargesQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Taxes for this business does not exist");
		}

		if (taxAndVisitCharges == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Taxes for this business does not exist");
		}

		List<QuoteLineItemResponse> quoteLineItemList = null;
		try {
			Query selectQuoteLineItemQuery = entityManager.createQuery(
					"select new com.pratikshat.exicom.model.response.QuoteLineItemResponse(qli) from QuoteLineItem qli left join qli.quote qt left join qli.priceBookEntries pbe left join pbe.product prd left join pbe.priceBook where qli.isDeleted=false and qt.id=?1",
					QuoteLineItemResponse.class);
			selectQuoteLineItemQuery.setParameter(1, quoteId);
			quoteLineItemList = selectQuoteLineItemQuery.getResultList();
			
		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Line Item does not exists");
		}

		if (quoteLineItemList == null || quoteLineItemList.isEmpty()) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Line Item does not exists");
		}
		
		StateWithQuotePdf stateWithQuotePdf = null;
		try {
			Query selectStateWithQuotePdfQuery = entityManager.createQuery(
					"select sqp from StateWithQuotePdf sqp where sqp.state.stateId=?1 And sqp.businessDomain.businessDomainId=?2",
					StateWithQuotePdf.class);
			selectStateWithQuotePdfQuery.setParameter(1, quote.getBillingState().getStateId());
			selectStateWithQuotePdfQuery.setParameter(2, quote.getBusinessDomain().getBusinessDomainId());
			stateWithQuotePdf = (StateWithQuotePdf)selectStateWithQuotePdfQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("No Template for this state");
		}
		
		
		if (stateWithQuotePdf == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("No Template for this state");
		}
		
		QuotePdfHeaderAndFooter headerAndFooter = null;
		try {
			Query selectQuotePdfHeaderAndFooterQuery = entityManager.createQuery("select qfh from QuotePdfHeaderAndFooter qfh where qfh.id=?1 and qfh.isActive=?2",QuotePdfHeaderAndFooter.class);
			selectQuotePdfHeaderAndFooterQuery.setParameter(1, stateWithQuotePdf.getQuotePdfHeaderAndFooter().getId());
			selectQuotePdfHeaderAndFooterQuery.setParameter(2, true);
			headerAndFooter = (QuotePdfHeaderAndFooter)selectQuotePdfHeaderAndFooterQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Header and Footer not found for the template");
		}
		
		if (headerAndFooter == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Header and Footer not found for the template");
		}

		List<QuoteTermsAndConditions> termsAndCondition = null;
		try {
			Query selectQuoteTermsAndConditionsQuery = entityManager.createQuery("select tc from QuoteTermsAndConditions tc where tc.objectName=?1 and tc.isActive=?2 order by tc.serialNumber asc",QuoteTermsAndConditions.class);
			selectQuoteTermsAndConditionsQuery.setParameter(1, "Quote");
			selectQuoteTermsAndConditionsQuery.setParameter(2, true);
			termsAndCondition = selectQuoteTermsAndConditionsQuery.getResultList();
			
		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Terms and Conditions not found for the template");
		}

		if (termsAndCondition == null || termsAndCondition.isEmpty()) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Terms and Conditions not found for the template");
		}

		Document document = new Document(PageSize.A4, -20, -20, 20, 10);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {

			PdfWriter writer = PdfWriter.getInstance(document, out);
			writer.setFullCompression();

			QuoteHeaderFooterPageEvent event = new QuoteHeaderFooterPageEvent(headerAndFooter);
			writer.setPageEvent(event);
			
			document.open();
			addHeader(document, headerAndFooter);
			addTitle(document, "QUOTATION");
			addAddress(document, quote);
			addQuoteDetails(document, quote);
			addEmptyLine(new Paragraph(), 1, document);
			addItemTable(document, quoteLineItemList);
			addEmptyLine(new Paragraph(), 1, document);
			addTaxAndPriceDetails(document, quote, taxAndVisitCharges);
			addEmptyLine(new Paragraph(), 1, document);
			addTitle(document, "TERM & CONDITIONS");
			addTermsAndCondition(document, termsAndCondition);
			addEmptyLine(new Paragraph(), 1, document);
			addEmptyLine(new Paragraph(), 1, document);
			addSignature(document, headerAndFooter);
			addEmptyLine(new Paragraph(), 1, document);
			
			document.close();
		} catch (DocumentException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("error in creating pdf");
		}
		
		Path fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
		String fileName = quote.getQuoteName()+".pdf";
		
		FileOutputStream fos = null;
		
		try {
			if(Files.exists(fileStorageLocation.resolve(fileName))) {
				fileName = quote.getQuoteName()+"_"+new Date().getTime()+".pdf";
			}
			File file = new File(fileStorageLocation+"/"+fileName);
			
		    fos = new FileOutputStream(fileStorageLocation+"/"+fileName); 

		    out.writeTo(fos);
		    
		    String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
	                .path("/api/downloadFile/")
	                .path(fileName)
	                .toUriString()
	                .replace("http:","https:");
		    
		    UploadedFiles uploadedFiles = new UploadedFiles();
            uploadedFiles.setObjectName("Quote");
            uploadedFiles.setObjectId(String.valueOf(quote.getId()));
            uploadedFiles.setFileName(fileName);
            uploadedFiles.setFileSize(file.length());
            uploadedFiles.setFileContentType("application/pdf");
            uploadedFiles.setFileDownloadUri(fileDownloadUri);
            uploadedFiles.setCreatedDate(new Date());
            uploadedFiles.setCreatedBy(new EmployeeDetails(user.getUsername()));
            
            entityManager.persist(uploadedFiles);
		    
		} catch(IOException ioe) {
		    ioe.printStackTrace();
		    throw new com.pratikshat.exicom.exception.BadRequestException("error in creating pdf");
		} finally {
		    fos.close();
		}
		
		return new ByteArrayInputStream(out.toByteArray());
	}

	public void addHeader(Document documnet, QuotePdfHeaderAndFooter headerAndFooter) {
		PdfPTable header = new PdfPTable(2);
		try {

			Path fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
			
			String lPath=headerAndFooter.getLogo().substring(headerAndFooter.getLogo().lastIndexOf("/")+1);
			
			Image image = Image.getInstance(fileStorageLocation+File.separator+lPath);

			image.scalePercent(50);
			image.setAlignment(Element.ALIGN_LEFT);
			PdfPCell cellImage = new PdfPCell(image);
			cellImage.setBorder(Rectangle.NO_BORDER);
			header.addCell(cellImage);
			header.setPaddingTop(0);

			final Phrase p = new Phrase();
			p.add(new Chunk(headerAndFooter.getCompanyName(), headerDetailsFont));
			p.add(Chunk.NEWLINE);
			p.add(new Chunk(headerAndFooter.getAddressLine1(), headerDetailsFont));
			p.add(Chunk.NEWLINE);
			if (headerAndFooter.getAddressLine2() != null) {
				p.add(new Chunk(headerAndFooter.getAddressLine2(), headerDetailsFont));
				p.add(Chunk.NEWLINE);
			}

			p.add(new Chunk(headerAndFooter.getCity() + " - " + headerAndFooter.getPincode() + ", "
					+ headerAndFooter.getStateName(), headerDetailsFont));
			p.add(Chunk.NEWLINE);
			if (headerAndFooter.getCin() != null) {
				p.add(new Chunk("CIN: " + headerAndFooter.getCin(), headerDetailsFont));
				p.add(Chunk.NEWLINE);
			}

			if (headerAndFooter.getDocumentNumber() != null) {
				p.add(new Chunk("Document No. - " + headerAndFooter.getDocumentNumber(), headerDetailsFont));
				p.add(Chunk.NEWLINE);
			}

			if (headerAndFooter.getTelephone() != null) {
				p.add(new Chunk("Tel : " + headerAndFooter.getTelephone(), headerDetailsFont));
				p.add(Chunk.NEWLINE);
			}

			if (headerAndFooter.getGstNumber() != null) {
				p.add(new Chunk("GST No. " + headerAndFooter.getGstNumber(), headerDetailsFont));
				p.add(Chunk.NEWLINE);
			}

			if (headerAndFooter.getWebsite() != null) {
				p.add(new Chunk("Web: " + headerAndFooter.getWebsite(), headerDetailsFont));
			}

			PdfPCell celltitle = new PdfPCell(p);
			celltitle.setBorder(Rectangle.NO_BORDER);
			celltitle.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
			celltitle.setPaddingBottom(20);

			header.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			header.addCell(celltitle);
			header.completeRow();

			documnet.add(header);
		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		} catch (MalformedURLException e) {
			throw new ExceptionConverter(e);
		} catch (IOException e) {
			throw new ExceptionConverter(e);
		}
	}

	public void addTitle(Document documnet, String title) {
		try {
			PdfPTable titleHeader = new PdfPTable(1);
			PdfPCell cellTitle = new PdfPCell(new Paragraph(title, catFont));
			cellTitle.setColspan(3);
			cellTitle.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellTitle.setPadding(5.0f);
			BaseColor myColor = new BaseColor(222, 229, 236);
			cellTitle.setBackgroundColor(myColor);
			cellTitle.setBorder(Rectangle.NO_BORDER);
			titleHeader.addCell(cellTitle);
			documnet.add(titleHeader);
		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		}
	}

	public void addAddress(Document documnet, Quote quote) {
		try {
			PdfPTable table = new PdfPTable(1);
			PdfPCell celltitle = new PdfPCell();

			Paragraph p = new Paragraph("To,", normalFont);
			p.add(Chunk.NEWLINE);
			celltitle.addElement(p);
			celltitle.setPaddingBottom(10);
			if (quote.getBillingName() != null) {
				Paragraph p2 = new Paragraph(quote.getBillingName(), normalFont);
				p2.add(Chunk.NEWLINE);
				celltitle.addElement(p2);
			}

			if (quote.getBillingStreet() != null && !quote.getBillingStreet().equalsIgnoreCase("")) {
				Paragraph p3 = new Paragraph(quote.getBillingStreet(), normalFont);
				p3.add(Chunk.NEWLINE);
				celltitle.addElement(p3);
			}

			if (quote.getBillingCity() != null && !quote.getBillingCity().equalsIgnoreCase("")) {
				if (quote.getBillingPostalCode() != null && !quote.getBillingPostalCode().equalsIgnoreCase("")) {
					Paragraph p3 = new Paragraph(quote.getBillingCity() + "-" + quote.getBillingPostalCode(),
							normalFont);
					p3.add(Chunk.NEWLINE);
					celltitle.addElement(p3);
				} else {
					Paragraph p3 = new Paragraph(quote.getBillingCity(), normalFont);
					p3.add(Chunk.NEWLINE);
					celltitle.addElement(p3);
				}
			}

			if (quote.getBillingState() != null) {
				Paragraph p4 = new Paragraph(quote.getBillingState().getStateName(), normalFont);
				p4.add(Chunk.NEWLINE);
				celltitle.addElement(p4);

			}

			if (quote.getBillingCountry() != null) {
				Paragraph p4 = new Paragraph(quote.getBillingCountry().getCountryName(), normalFont);
				p4.add(Chunk.NEWLINE);
				celltitle.addElement(p4);
			}

			celltitle.setBorder(Rectangle.NO_BORDER);
			table.addCell(celltitle);
			documnet.add(table);
		} catch (DocumentException e) {
			throw new ExceptionConverter(e);
		}
	}

	public void addQuoteDetails(Document document, Quote quote) {
		PdfPTable titleHeader = new PdfPTable(2);
		titleHeader.getDefaultCell().setBorder(Rectangle.NO_BORDER);

		PdfPTable t1 = new PdfPTable(2);
		t1.getDefaultCell().setBorder(Rectangle.NO_BORDER);

		PdfPCell quoteheadingCell = new PdfPCell();
		Paragraph quoteheadingParagraph = new Paragraph();
		quoteheadingParagraph.add(new Chunk("Quote Number", normalFontBlack));
		quoteheadingCell.addElement(quoteheadingParagraph);
		quoteheadingCell.setBorder(Rectangle.NO_BORDER);
		t1.addCell(quoteheadingCell);

		PdfPCell quoteValueCell = new PdfPCell();
		Paragraph quoteValueParagraph = new Paragraph();
		quoteValueParagraph.add(new Chunk(quote.getQuoteName(), normalFont));
		quoteValueCell.addElement(quoteValueParagraph);
		quoteValueCell.setBorder(Rectangle.NO_BORDER);
		t1.addCell(quoteValueCell);

		PdfPCell caseheadingCell = new PdfPCell();
		Paragraph caseheadingParagraph = new Paragraph();
		caseheadingParagraph.add(new Chunk("Case", normalFontBlack));
		caseheadingCell.addElement(caseheadingParagraph);
		caseheadingCell.setBorder(Rectangle.NO_BORDER);
		t1.addCell(caseheadingCell);

		PdfPCell caseValueCell = new PdfPCell();
		Paragraph caseValueParagraph = new Paragraph();
		caseValueParagraph.add(new Chunk(quote.getOppertunity().getCaseDetails().getManualId(), normalFont));
		caseValueCell.addElement(caseValueParagraph);
		caseValueCell.setBorder(Rectangle.NO_BORDER);
		t1.addCell(caseValueCell);
		
//		 
		
		PdfPCell dateheadingCellst = new PdfPCell();
		Paragraph dateheadingParagraphst = new Paragraph();
		dateheadingParagraphst.add(new Chunk("Site Id", normalFontBlack));
		dateheadingCellst.addElement(dateheadingParagraphst);
		dateheadingCellst.setBorder(Rectangle.NO_BORDER);
		t1.addCell(dateheadingCellst);
		
		PdfPCell dateValueCellst = new PdfPCell();
		Paragraph dateValueParagraphst = new Paragraph();
//		SimpleDateFormat outputDatest = new SimpleDateFormat("dd/MM/yyyy");
		dateValueParagraphst.add(new Chunk(quote.getOppertunity().getCaseDetails().getSite().getManualSiteId(), normalFont));
		dateValueCellst.addElement(dateValueParagraphst);
		dateValueCellst.setBorder(Rectangle.NO_BORDER);
		t1.addCell(dateValueCellst);
		titleHeader.addCell(t1);
//		

		PdfPTable t2 = new PdfPTable(2);
		t2.getDefaultCell().setBorder(Rectangle.NO_BORDER);

		PdfPCell siteheadingCell = new PdfPCell();
		Paragraph siteheadingParagraph = new Paragraph();
		siteheadingParagraph.add(new Chunk("Site Name", normalFontBlack));
		siteheadingCell.addElement(siteheadingParagraph);
		siteheadingCell.setBorder(Rectangle.NO_BORDER);
		t2.addCell(siteheadingCell);

		PdfPCell siteValueCell = new PdfPCell();
		Paragraph siteValueParagraph = new Paragraph();
		siteValueParagraph.add(new Chunk(quote.getOppertunity().getCaseDetails().getSite().getSiteName(), normalFont));
		siteValueCell.addElement(siteValueParagraph);
		siteValueCell.setBorder(Rectangle.NO_BORDER);
		t2.addCell(siteValueCell);

		PdfPCell dateheadingCell = new PdfPCell();
		Paragraph dateheadingParagraph = new Paragraph();
		dateheadingParagraph.add(new Chunk("Created Date", normalFontBlack));
		dateheadingCell.addElement(dateheadingParagraph);
		dateheadingCell.setBorder(Rectangle.NO_BORDER);
		t2.addCell(dateheadingCell);

		PdfPCell dateValueCell = new PdfPCell();
		Paragraph dateValueParagraph = new Paragraph();
		SimpleDateFormat outputDate = new SimpleDateFormat("dd/MM/yyyy");
		dateValueParagraph.add(new Chunk(outputDate.format(quote.getCreatedDate()), normalFont));
		dateValueCell.addElement(dateValueParagraph);
		dateValueCell.setBorder(Rectangle.NO_BORDER);
		t2.addCell(dateValueCell);
		
		if(quote.getOppertunity().getCaseDetails().getSite().getCustomer()!=null && quote.getOppertunity().getCaseDetails().getSite().getCustomer().contains("INDUS")) {
		PdfPCell dateheadingCellIndus = new PdfPCell();
		Paragraph dateheadingParagraphIndus = new Paragraph();
	
		dateheadingParagraphIndus.add(new Chunk("Indus Id", normalFontBlack));
		dateheadingCellIndus.addElement(dateheadingParagraphIndus);
		dateheadingCellIndus.setBorder(Rectangle.NO_BORDER);
		t2.addCell(dateheadingCellIndus);
		
		PdfPCell dateValueCellindus = new PdfPCell();
		Paragraph dateValueParagraphindus = new Paragraph();

		dateValueParagraphindus.add(new Chunk(quote.getOppertunity().getCaseDetails().getSite().getIndusId(), normalFont));
		dateValueCellindus.addElement(dateValueParagraphindus);
		dateValueCellindus.setBorder(Rectangle.NO_BORDER);

		t2.addCell(dateValueCellindus);
		}

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

	public void addItemTable(Document document, List<QuoteLineItemResponse> quoteLineItemList) {
		try {
			PdfPTable table = new PdfPTable(5);
			table.setWidths(new int[] { 3, 8, 2, 2, 2 });
			List<String> headers = new ArrayList<>();
			headers.add("Part Code");
			headers.add("Product Description");
			headers.add("Quantity");
			headers.add("Price");
			headers.add("Total Price");

			if (headers.size() > 0) {
				BaseColor myColor = new BaseColor(117, 117, 117);
				for (int i = 0; i < headers.size(); i++) {
					PdfPCell hcell;
					hcell = new PdfPCell(new Phrase(headers.get(i), productTableHeaderFont));
					if (i == 0 || i == 1) {
						hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
					} else {
						hcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					hcell.setBorder(Rectangle.NO_BORDER);
					hcell.setPaddingBottom(5);
					hcell.setPaddingLeft(5);
					hcell.setPaddingRight(5);
					hcell.setPaddingTop(2);
					hcell.setBackgroundColor(myColor);
					table.addCell(hcell);
				}
			}

			if (quoteLineItemList.size() > 0) {
				PdfPCell cell = null;
				BaseColor myColor = new BaseColor(238, 238, 238);
				for (int k = 0; k < quoteLineItemList.size(); k++) {

					cell = new PdfPCell(
							new Phrase(quoteLineItemList.get(k).getProductPartCode(), productTableDataFont));
					cell.setPadding(4);
					cell.setBorder(0);
					if (k % 2 == 0) {
						cell.setBackgroundColor(myColor);
					}
					table.addCell(cell);

					cell = new PdfPCell(
							new Phrase(quoteLineItemList.get(k).getPdoductDescription(), productTableDataFont));
					cell.setPadding(4);
					cell.setBorder(0);
					cell.setBorder(Rectangle.LEFT);
					if (k % 2 == 0) {
						cell.setBackgroundColor(myColor);
					}
					table.addCell(cell);

					cell = new PdfPCell(
							new Phrase(String.valueOf(quoteLineItemList.get(k).getQuantity()), productTableDataFont));
					cell.setPadding(4);
					cell.setBorder(0);
					cell.setBorder(Rectangle.LEFT);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					if (k % 2 == 0) {
						cell.setBackgroundColor(myColor);
					}
					table.addCell(cell);

					cell = new PdfPCell(
							new Phrase(String.format("%.2f",quoteLineItemList.get(k).getPrice()), productTableDataFont));
					cell.setPadding(4);
					cell.setBorder(0);
					cell.setBorder(Rectangle.LEFT);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					if (k % 2 == 0) {
						cell.setBackgroundColor(myColor);
					}
					table.addCell(cell);

					cell = new PdfPCell(
							new Phrase(String.format("%.2f",quoteLineItemList.get(k).getTotalPrice()), productTableDataFont));
					cell.setPadding(4);
					cell.setBorder(0);
					cell.setBorder(Rectangle.LEFT);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					if (k % 2 == 0) {
						cell.setBackgroundColor(myColor);
					}
					table.addCell(cell);
				}
			}
			document.add(table);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addTaxAndPriceDetails(Document doucment, Quote quote, TaxAndVisitCharges taxAndVisitCharges) {
		try {
			PdfPTable table = new PdfPTable(2);
			table.setWidths(new int[] { 8, 8 });

			PdfPCell subTotalheadingCell = new PdfPCell(new Phrase("Sub Total", normalFont));
			subTotalheadingCell.setBorder(Rectangle.NO_BORDER);
			subTotalheadingCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(subTotalheadingCell);

			PdfPCell subTotalValueCell = new PdfPCell(new Phrase(String.format("%.2f",quote.getSubTotal()), normalFont));
			subTotalValueCell.setBorder(Rectangle.NO_BORDER);
			subTotalValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(subTotalValueCell);

			PdfPCell sgstheadingCell = new PdfPCell(
					new Phrase("SGST " + taxAndVisitCharges.getSgst() + "%", normalFont));
			sgstheadingCell.setBorder(Rectangle.NO_BORDER);
			sgstheadingCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(sgstheadingCell);

			PdfPCell sgstValueCell = new PdfPCell(new Phrase(String.format("%.2f",quote.getSgstAmount()), normalFont));
			sgstValueCell.setBorder(Rectangle.NO_BORDER);
			sgstValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(sgstValueCell);
			table.completeRow();

			PdfPCell cgstheadingCell = new PdfPCell(
					new Phrase("CGST " + taxAndVisitCharges.getCgst() + "%", normalFont));
			cgstheadingCell.setBorder(Rectangle.NO_BORDER);
			cgstheadingCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cgstheadingCell);

			PdfPCell cgstValueCell = new PdfPCell(new Phrase(String.format("%.2f",quote.getCgstAmount()), normalFont));
			cgstValueCell.setBorder(Rectangle.NO_BORDER);
			cgstValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cgstValueCell);
			table.completeRow();

			PdfPCell igstheadingCell = new PdfPCell(
					new Phrase("IGST " + taxAndVisitCharges.getIgst() + "%", normalFont));
			igstheadingCell.setBorder(Rectangle.NO_BORDER);
			igstheadingCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(igstheadingCell);

			PdfPCell igstValueCell = new PdfPCell(new Phrase(String.format("%.2f",quote.getIgstAmount()), normalFont));
			igstValueCell.setBorder(Rectangle.NO_BORDER);
			igstValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(igstValueCell);
			table.completeRow();

			PdfPCell grandTotalheadingCell = new PdfPCell(new Phrase("Grand Total", normalFont));
			grandTotalheadingCell.setBorder(Rectangle.NO_BORDER);
			grandTotalheadingCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(grandTotalheadingCell);

			PdfPCell grandTotalValueCell = new PdfPCell(
					new Phrase(String.format("%.2f",quote.getTotalAmountWithTax()), normalFont));
			grandTotalValueCell.setBorder(Rectangle.NO_BORDER);
			grandTotalValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(grandTotalValueCell);

			doucment.add(table);
		} catch (DocumentException e) {
			throw new ExceptionConverter(e);
		}
	}

	public void addTermsAndCondition(Document documnet, List<QuoteTermsAndConditions> termsAndCondition) {
		PdfPTable header = new PdfPTable(1);
		if (termsAndCondition != null && termsAndCondition.size() > 0) {
			final Phrase p = new Phrase();
			for (int i = 0; i < termsAndCondition.size(); i++) {
				QuoteTermsAndConditions tAndC = termsAndCondition.get(i);

				p.add(new Chunk(tAndC.getSerialNumber() + "- " + tAndC.getRule() + ".", normalFont));
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

	public void addSignature(Document document, QuotePdfHeaderAndFooter headerAndFooter) {
		PdfPTable header = new PdfPTable(1);
		try {

			Path fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
			String lPath=headerAndFooter.getSignature().substring(headerAndFooter.getSignature().lastIndexOf("/")+1);
			Image image = Image.getInstance(fileStorageLocation+File.separator+lPath);

			//image.scalePercent(3); edit by shagufta for signature size decrease
			image.scaleToFit(70,70);
			image.setAlignment(Element.ALIGN_RIGHT);
			PdfPCell cellImage = new PdfPCell(image);
			cellImage.setBorder(Rectangle.NO_BORDER);
			cellImage.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
			header.addCell(cellImage);
			header.setPaddingTop(0);

			Paragraph paragraph = new Paragraph();
			paragraph.add(new Chunk("Authorized Signatory", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
			PdfPCell celltitle = new PdfPCell(paragraph);
			celltitle.setBorder(Rectangle.NO_BORDER);
			celltitle.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);

			header.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			header.addCell(celltitle);
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

}
