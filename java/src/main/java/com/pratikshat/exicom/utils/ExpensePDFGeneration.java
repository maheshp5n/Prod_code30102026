package com.pratikshat.exicom.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pratikshat.exicom.config.FileStorageProperties;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.event.ExpenseHeaderFooterPageEvent;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.model.request.Contacts;
import com.pratikshat.exicom.model.response.BoardingExpenseResponse;
import com.pratikshat.exicom.model.response.ExpenseManagementResponse;
import com.pratikshat.exicom.model.response.LocalConveyanceExpenseResponse;
import com.pratikshat.exicom.model.response.LodgingExpenseResponse;
import com.pratikshat.exicom.model.response.OtherChargesExpenseResponse;
import com.pratikshat.exicom.model.response.TourExpenseResponse;
import com.pratikshat.exicom.model.response.UserResponse;

public class ExpensePDFGeneration {
	
	
	private  Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL, BaseColor.BLACK);//DARK_GRAY
	private  Font normalFontBold = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);
	private  Font timesBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
	
	EntityManager entityManager = null;
	FileStorageProperties fileStorageProperties = null;
	
	
	public ExpensePDFGeneration(EntityManager entityManager, FileStorageProperties fileStorageProperties) {
		
		this.entityManager = entityManager;
		this.fileStorageProperties = fileStorageProperties;
	}
	
	public ByteArrayInputStream createPdf(LoginUser user, ExpenseManagementResponse response) throws Exception {
		
		Contacts contact = null;
		try {
			Query getQuery = entityManager.createQuery("select new com.pratikshat.exicom.model.request.Contacts(ct) from ContactDetails ct left join ct.contactDesignation cd left join ct.contactType  ctp where ct.contactId=?1", Contacts.class);
			getQuery.setParameter(1, response.getContactId());
			contact = (Contacts) getQuery.getSingleResult();
			
		}catch(NoResultException ex) {
			throw new BadRequestException("Contact does not exist");
		}
		
		if(contact==null) {
			throw new BadRequestException("Contact does not exist");
		}
		
		
		UserResponse employeeDetails = null;
		try {
			Query getQuery = entityManager.createQuery("select new com.pratikshat.exicom.model.response.UserResponse(coalesce(em.emailId,''),coalesce(ac.name,''),coalesce(ct.contactId,0),coalesce(ct.firstName,''),coalesce(ct.lastName,''),coalesce(ct.middleName,''),coalesce(ac.sapCustomerNumber,''),coalesce(ccdes.designationName,''),coalesce(ct.emailId,''),coalesce(ct.employeeCode,''),coalesce(ety.businessDomainId,''),coalesce(ety.businessDomainName,''),coalesce(l.accountNonExpired,false),coalesce(l.accountNonLocked,false),coalesce(l.credentialsNonExpired,false),coalesce(l.enabled,false),coalesce(profile.profileName,''),coalesce(manager.emailId,''),coalesce(manager.firstName,''),coalesce(manager.middleName,''),coalesce(manager.lastName,''),coalesce(aprmanager.emailId,''),coalesce(aprmanager.firstName,''),coalesce(aprmanager.middleName,''),coalesce(aprmanager.lastName,''), coalesce(cby.firstName,''), coalesce(cby.middleName,''), coalesce(cby.lastName,''), cby.emailId, em.createdDate, coalesce(mby.firstName,''), coalesce(mby.middleName,''), coalesce(mby.lastName,''), mby.emailId, em.modifiedDate) from LoginUser l join EmployeeDetails em on em.emailId=l.username left join em.contact ct left join ct.account ac left join ct.contactDesignation ccdes left join em.emptype ety left join em.userProfile profile left join em.manager manager left join em.approvalManager aprmanager left join em.createdBy cby left join em.modifiedBy mby where l.username=?1", UserResponse.class);
			getQuery.setParameter(1, contact.getContactEmailId());
			employeeDetails = (UserResponse) getQuery.getSingleResult();
			
		}catch(NoResultException ex) {
			throw new BadRequestException("User deos not exist");
		}
		
		if(employeeDetails==null) {
			throw new BadRequestException("User deos not exist");
		}
	
		List<TourExpenseResponse> tourExpenseList = null;
		try {
			Query getQuery = entityManager.createQuery("select new com.pratikshat.exicom.model.response.TourExpenseResponse(te) from TourExpense te left join te.expenseManagement em where em.expenseManagementId=?1", TourExpenseResponse.class);
			getQuery.setParameter(1, response.getExpenseManagementId());
			tourExpenseList = getQuery.getResultList();
			
		}catch(NoResultException ex) {
			
		}
		
		
		List<LodgingExpenseResponse> lodgingExpenseList = null;
		try {
			Query getQuery = entityManager.createQuery("select new com.pratikshat.exicom.model.response.LodgingExpenseResponse(le) from LodgingExpense le left join le.expenseManagement em where em.expenseManagementId=?1", LodgingExpenseResponse.class);
			getQuery.setParameter(1, response.getExpenseManagementId());
			lodgingExpenseList = getQuery.getResultList();
			
		}catch(NoResultException ex) {
			
		}
		
		
		List<BoardingExpenseResponse> boardingExpenseList = null;
		try {
			Query getQuery = entityManager.createQuery("select new com.pratikshat.exicom.model.response.BoardingExpenseResponse(be) from BoardingExpense be left join be.expenseManagement em where em.expenseManagementId=?1", BoardingExpenseResponse.class);
			getQuery.setParameter(1, response.getExpenseManagementId());
			boardingExpenseList = getQuery.getResultList();
			
		}catch(NoResultException ex) {
			
		}
		
		List<LocalConveyanceExpenseResponse> localConveyanceExpenseList = null;
		try {
			Query getQuery = entityManager.createQuery("select new com.pratikshat.exicom.model.response.LocalConveyanceExpenseResponse(le) from LocalConveyanceExpense le left join le.expenseManagement em where em.expenseManagementId=?1", LocalConveyanceExpenseResponse.class);
			getQuery.setParameter(1, response.getExpenseManagementId());
			localConveyanceExpenseList = getQuery.getResultList();
			
		}catch(NoResultException ex) {
			
		}
		
		List<OtherChargesExpenseResponse> otherChargesExpenseList = null;
		try {
			Query getQuery = entityManager.createQuery("select new com.pratikshat.exicom.model.response.OtherChargesExpenseResponse(oc) from OtherChargesExpense oc left join oc.expenseManagement em where em.expenseManagementId=?1", OtherChargesExpenseResponse.class);
			getQuery.setParameter(1, response.getExpenseManagementId());
			otherChargesExpenseList = getQuery.getResultList();
			
		}catch(NoResultException ex) {
			
		}

		Document document = new Document(PageSize.A4, -52, -52, 17, 20);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {

			PdfWriter writer = PdfWriter.getInstance(document, out);

			ExpenseHeaderFooterPageEvent event = new ExpenseHeaderFooterPageEvent();
			writer.setPageEvent(event);

			document.open();

			addHeader(document);
			addGeneralInformation(document, contact, employeeDetails, response);

			addTitle(document, "Air/Rail/Bus Fair", normalFontBold, Element.ALIGN_LEFT);
			travelExpenseTableHeader(document);
			travelExpenseTableData(document, tourExpenseList, response.getTotalTourAmount());

			addTitle(document, "Lodging (Voucher Attached)", normalFontBold, Element.ALIGN_LEFT);
			lodgingExpenseTableHeader(document);
			lodgingExpenseTableData(document, lodgingExpenseList, response.getTotalLodgingAmount());

			addTitle(document, "Boarding (Voucher Attached)", normalFontBold, Element.ALIGN_LEFT);
			boardingExpenseTableHeader(document);
			boardingExpenseTableData(document, boardingExpenseList, response.getTotalBoardingAmount());

			addTitle(document, "Conveyance Charges", normalFontBold, Element.ALIGN_LEFT);
			conveyanceExpenseTableHeader(document);
			conveyanceExpenseTableData(document, localConveyanceExpenseList, response.getTotalLocalConveyanceAmount());

			addTitle(document, "Miscellaneous Expense", normalFontBold, Element.ALIGN_LEFT);
			otherChargesExpenseTableHeader(document);
			otherChargesExpenseTableData(document, otherChargesExpenseList, response.getTotalOtherChargesAmount());

			addTitle(document,
					"Note: If mode of travel or conveyance higher than the admissible is used. Specific approval of HOD concerned is requried",
					normalFont, Element.ALIGN_LEFT);
			addTitle(document, "CLAIM", normalFontBold, Element.ALIGN_CENTER);

			addSummaryHeader(document);
			addSummaryData(document, response);

			addFooterSignatureArea(document, "Sender's Signature : ____________________ ",
					"Head of Department/Approving Autority : ____________________");
			addFooterDateArea(document, "Date : ______________________");
			addFooterSignatureArea(document, "Checked By : __________________________ ",
					"Finance Department : ________________________________");

			document.close();
		} catch (DocumentException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("error in creating pdf");
		}

		return new ByteArrayInputStream(out.toByteArray());
	}

	public void addHeader(Document documnet) {

		try {
			Path fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
			Image image = Image.getInstance(fileStorageLocation + "/" + "logo.png");

			PdfPTable header = new PdfPTable(2);
			header.setWidths(new int[] { 5, 10 });

			image.scalePercent(25);
			image.setAlignment(Element.ALIGN_LEFT);
			PdfPCell cellImage = new PdfPCell(image);
			cellImage.setPadding(2);
			header.addCell(cellImage);

			final Phrase p = new Phrase();
			p.add(new Chunk("Exicom Tele-Systems", timesBoldFont));
			p.add(Chunk.NEWLINE);
			p.add(new Chunk("Travel Bill / Claim", timesBoldFont));
			p.add(Chunk.NEWLINE);
			PdfPCell celltitle = new PdfPCell(p);
			celltitle.setPaddingTop(5);
			celltitle.setHorizontalAlignment(Element.ALIGN_CENTER);
			celltitle.setVerticalAlignment(Element.ALIGN_CENTER);
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

	public void addGeneralInformation(Document documnet, Contacts contact, UserResponse employeeDetails,
			ExpenseManagementResponse response) {
		PdfPTable table = new PdfPTable(5);
		try {
			table.setWidths(new int[] { 3, 2, 2, 1, 2 });
			String name = null;
			if(contact.getContactFirstName()!=null && !contact.getContactFirstName().equalsIgnoreCase(""))
				name = contact.getContactFirstName();
			if(contact.getContactMiddleName()!=null && !contact.getContactMiddleName().equalsIgnoreCase(""))
				name = name +" "+ contact.getContactMiddleName();
			if(contact.getContactLastName()!=null && !contact.getContactLastName().equalsIgnoreCase(""))
				name = name +" "+ contact.getContactLastName();
			
			
			PdfPCell cellContactName = new PdfPCell();
			Paragraph paragraphContactName = new Paragraph();
			paragraphContactName.add(new Chunk("Name :  ", normalFontBold));
			paragraphContactName.add(new Chunk(name, normalFont));
			paragraphContactName.add(Chunk.SPACETABBING);
			paragraphContactName.add(Chunk.SPACETABBING);
			paragraphContactName.setLeading(5);
			paragraphContactName.setPaddingTop(0);
			cellContactName.addElement(paragraphContactName);
			cellContactName.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellContactName.setBorder(0);
			table.addCell(cellContactName);

			PdfPCell cellDesignation = new PdfPCell();
			Paragraph paragraphDesignation = new Paragraph();
			paragraphDesignation.add(new Chunk("Designation :  ", normalFontBold));
			paragraphDesignation.add(new Chunk(contact.getContactDesignationName(), normalFont));
			paragraphDesignation.add(Chunk.SPACETABBING);
			paragraphDesignation.add(Chunk.SPACETABBING);
			paragraphDesignation.setLeading(5);
			paragraphDesignation.setPaddingTop(0);
			cellDesignation.addElement(paragraphDesignation);
			cellDesignation.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellDesignation.setBorder(0);
			table.addCell(cellDesignation);

			PdfPCell cellDepartment = new PdfPCell();
			Paragraph paragraphDepartment = new Paragraph();
			paragraphDepartment.add(new Chunk("Deptt :  ", normalFontBold));
			paragraphDepartment.add(new Chunk(employeeDetails.getBuisnessDomainName(), normalFont));
			paragraphDepartment.add(Chunk.SPACETABBING);
			paragraphDepartment.add(Chunk.SPACETABBING);
			paragraphDepartment.setLeading(5);
			paragraphDepartment.setPaddingTop(0);
			cellDepartment.addElement(paragraphDepartment);
			cellDepartment.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellDepartment.setBorder(0);
			table.addCell(cellDepartment);

			PdfPCell cellGrade = new PdfPCell();
			Paragraph paragraphGrade = new Paragraph();
			paragraphGrade.add(new Chunk("Grade :  ", normalFontBold));
			paragraphGrade.add(new Chunk(contact.getGradeName(), normalFont));
			paragraphGrade.add(Chunk.SPACETABBING);
			paragraphGrade.add(Chunk.SPACETABBING);
			paragraphGrade.setLeading(5);
			paragraphGrade.setPaddingTop(0);
			cellGrade.addElement(paragraphGrade);
			cellGrade.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellGrade.setBorder(0);
			table.addCell(cellGrade);

			PdfPCell cellEmployeeCode = new PdfPCell();
			Paragraph paragraphEmployeeCode = new Paragraph();
			paragraphEmployeeCode.add(new Chunk("Emp Code :  ", normalFontBold));
			paragraphEmployeeCode.add(new Chunk(contact.getEmployeeCode()==null?"":contact.getEmployeeCode(), normalFont));
			paragraphEmployeeCode.add(Chunk.SPACETABBING);
			paragraphEmployeeCode.add(Chunk.SPACETABBING);
			paragraphEmployeeCode.setLeading(5);
			paragraphEmployeeCode.setPaddingTop(0);
			cellEmployeeCode.addElement(paragraphEmployeeCode);
			cellEmployeeCode.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellEmployeeCode.setBorder(0);
			table.addCell(cellEmployeeCode);

			PdfPCell cellExpenseId = new PdfPCell();
			Paragraph paragraphExpenseId = new Paragraph();
			paragraphExpenseId.add(new Chunk("Expense Claim No. :  ", normalFontBold));
			paragraphExpenseId.add(new Chunk(response.getExpenseManagementManualId(), normalFont));
			paragraphExpenseId.add(Chunk.SPACETABBING);
			paragraphExpenseId.add(Chunk.SPACETABBING);
			paragraphExpenseId.setLeading(5);
			paragraphExpenseId.setPaddingTop(0);
			cellExpenseId.addElement(paragraphExpenseId);
			cellExpenseId.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellExpenseId.setBorder(0);
			table.addCell(cellExpenseId);

			PdfPCell cellPurposeOfVisit = new PdfPCell();
			cellPurposeOfVisit.setColspan(3);
			Paragraph paragraphPurposeOfVisit = new Paragraph();
			paragraphPurposeOfVisit.add(new Chunk("Purpose of Visit :  ", normalFontBold));
			paragraphPurposeOfVisit.add(new Chunk(response.getPurposeOfVisit(), normalFont));
			paragraphPurposeOfVisit.add(Chunk.SPACETABBING);
			paragraphPurposeOfVisit.add(Chunk.SPACETABBING);
			paragraphPurposeOfVisit.setLeading(5);
			paragraphPurposeOfVisit.setPaddingTop(0);
			cellPurposeOfVisit.addElement(paragraphPurposeOfVisit);
			cellPurposeOfVisit.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellPurposeOfVisit.setBorder(0);

			table.addCell(cellPurposeOfVisit);

			PdfPCell cellLegalEntity = new PdfPCell();
			Paragraph paragraphLegalEntity = new Paragraph();
			paragraphLegalEntity.add(new Chunk("Place of Posting :  ", normalFontBold));
			paragraphLegalEntity.add(new Chunk(contact.getLeagalEntityName(), normalFont));
			paragraphLegalEntity.add(Chunk.SPACETABBING);
			paragraphLegalEntity.add(Chunk.SPACETABBING);
			paragraphLegalEntity.setLeading(5);
			paragraphLegalEntity.setPaddingTop(0);
			cellLegalEntity.addElement(paragraphLegalEntity);
			cellLegalEntity.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellLegalEntity.setBorder(0);
			table.addCell(cellLegalEntity);

			documnet.add(table);
		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		} catch (Exception e) {
			throw new ExceptionConverter(e);
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
			titleHeader.addCell(cellTitle);
			doc.add(titleHeader);
		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		} catch (Exception e) {
			throw new ExceptionConverter(e);
		}
	}

	private void travelExpenseTableHeader(Document doc) {
		PdfPTable table = new PdfPTable(8);
		try {
			table.setWidths(new int[] { 2, 2, 2, 2, 2, 2, 2, 2 });
			PdfPCell cell;
			cell = new PdfPCell(new Phrase("Departure", normalFontBold));
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Arrival", normalFontBold));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setColspan(2);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Mode and class of Travel", normalFontBold));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setRowspan(2);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Amount Self Paid", normalFontBold));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setRowspan(2);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Put \"X\" if Company Paid", normalFontBold));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setRowspan(2);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Remarks", normalFontBold));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setRowspan(2);
			table.addCell(cell);

			table.addCell(new Phrase("Date Time", normalFontBold));
			table.addCell(new Phrase("Station", normalFontBold));
			table.addCell(new Phrase("Date Time", normalFontBold));
			table.addCell(new Phrase("Station", normalFontBold));
			doc.add(table);

		} catch (DocumentException e) {
			throw new ExceptionConverter(e);
		} catch (Exception e) {
			throw new ExceptionConverter(e);
		}
	}

	private void travelExpenseTableData(Document doc, List<TourExpenseResponse> tourExpenseList,
			double totalTourAmount) {

		PdfPTable table = new PdfPTable(8);
		try {
			table.setWidths(new int[] { 2, 2, 2, 2, 2, 2, 2, 2 });
			PdfPCell cell;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			if (tourExpenseList != null && !tourExpenseList.isEmpty()) {
				for (int i = 0; i < tourExpenseList.size(); i++) {

					TourExpenseResponse tourExpense = tourExpenseList.get(i);

					cell = new PdfPCell(new Phrase(
							sdf.format(tourExpense.getDepartureDate()) + " " + tourExpense.getDepartureTime(),
							normalFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(tourExpense.getFromPlace(), normalFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(
							sdf.format(tourExpense.getArrivalDate()) + " " + tourExpense.getArrivalTime(), normalFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(tourExpense.getToPlace(), normalFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(tourExpense.getModeOfTravelName(), normalFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(String.valueOf(tourExpense.getFare()), normalFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);

					table.addCell(new Phrase(" ", normalFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(new Phrase(tourExpense.getRemark(), normalFont));

				}

			}

			cell = new PdfPCell(new Phrase("", normalFont));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("", normalFont));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("", normalFont));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("", normalFont));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Total", normalFont));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(String.valueOf(totalTourAmount), normalFont));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			table.addCell(new Phrase(" ", normalFont));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(new Phrase("", normalFont));

			doc.add(table);
		} catch (DocumentException e) {
			throw new ExceptionConverter(e);
		} catch (Exception e) {
			throw new ExceptionConverter(e);
		}
	}

	private void lodgingExpenseTableHeader(Document doc) {
		PdfPTable table = new PdfPTable(8);
		try {
			table.setWidths(new int[] { 2, 2, 2, 2, 2, 2, 2, 2 });
			PdfPCell cell;
			cell = new PdfPCell(new Phrase("Date", normalFontBold));
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("No. of Days", normalFontBold));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setRowspan(2);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Place", normalFontBold));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setRowspan(2);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Hotel Charges Self Paid", normalFontBold));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setRowspan(2);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Put \"X\" if Company Paid", normalFontBold));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setRowspan(2);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Remarks", normalFontBold));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setRowspan(2);
			cell.setColspan(2);
			table.addCell(cell);

			table.addCell(new Phrase("From", normalFontBold));
			table.addCell(new Phrase("To", normalFontBold));
			doc.add(table);

		} catch (DocumentException e) {
			throw new ExceptionConverter(e);
		} catch (Exception e) {
			throw new ExceptionConverter(e);
		}
	}

	private void lodgingExpenseTableData(Document doc, List<LodgingExpenseResponse> lodgingExpenseList,
			double totalLodgingAmount) {

		PdfPTable table = new PdfPTable(8);
		try {
			table.setWidths(new int[] { 2, 2, 2, 2, 2, 2, 2, 2 });
			PdfPCell cell;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			
			if (lodgingExpenseList != null && !lodgingExpenseList.isEmpty()) {
				for (int i = 0; i < lodgingExpenseList.size(); i++) {

					LodgingExpenseResponse lodgingExpense = lodgingExpenseList.get(i);

					cell = new PdfPCell(new Phrase(sdf.format(lodgingExpense.getFromDate())+" "+lodgingExpense.getFromTime(), normalFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(sdf.format(lodgingExpense.getToDate())+" "+lodgingExpense.getToTime(), normalFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);

//					long days = TimeUnit.DAYS.convert(
//							lodgingExpense.getToDate().getTime() - lodgingExpense.getFromDate().getTime(),
//							TimeUnit.MILLISECONDS);
//					String strDays = "0";
//					if (days < 9) {
//						strDays = "0" + days;
//					} else {
//						strDays = String.valueOf(days);
//					}

					cell = new PdfPCell(new Phrase(String.valueOf(lodgingExpense.getNoOfDays()), normalFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(lodgingExpense.getCityName(), normalFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(String.valueOf(lodgingExpense.getAmount()), normalFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);

					table.addCell(new Phrase(" ", normalFont));

					cell = new PdfPCell(new Phrase(lodgingExpense.getRemark(), normalFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setColspan(2);
					table.addCell(cell);

				}

			}

			table.addCell(new Phrase("", normalFont));

			table.addCell(new Phrase("", normalFont));

			table.addCell(new Phrase("", normalFont));

			cell = new PdfPCell(new Phrase("Total", normalFont));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(String.valueOf(totalLodgingAmount), normalFont));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			table.addCell(new Phrase("", normalFont));

			cell = new PdfPCell(new Phrase("", normalFont));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setColspan(2);
			table.addCell(cell);

			doc.add(table);
		} catch (DocumentException e) {
			throw new ExceptionConverter(e);
		} catch (Exception e) {
			throw new ExceptionConverter(e);
		}
	}

	private void boardingExpenseTableHeader(Document doc) {
		PdfPTable table = new PdfPTable(8);
		try {
			table.setWidths(new int[] { 2, 2, 2, 2, 2, 2, 2, 2 });
			PdfPCell cell;
			cell = new PdfPCell(new Phrase("Date", normalFontBold));
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("No. of Days", normalFontBold));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setColspan(3);
			cell.setRowspan(2);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Place", normalFontBold));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setColspan(2);
			cell.setRowspan(2);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Amount Self Paid", normalFontBold));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setRowspan(2);
			table.addCell(cell);

			table.addCell(new Phrase("From", normalFontBold));
			table.addCell(new Phrase("To", normalFontBold));
			doc.add(table);

		} catch (DocumentException e) {
			throw new ExceptionConverter(e);
		} catch (Exception e) {
			throw new ExceptionConverter(e);
		}
	}

	private void boardingExpenseTableData(Document doc, List<BoardingExpenseResponse> boardingExpenseList,
			double totalBoardingAmount) {

		PdfPTable table = new PdfPTable(8);
		try {
			table.setWidths(new int[] { 2, 2, 2, 2, 2, 2, 2, 2 });
			PdfPCell cell;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			if (boardingExpenseList != null && !boardingExpenseList.isEmpty()) {
				for (int i = 0; i < boardingExpenseList.size(); i++) {

					BoardingExpenseResponse boardingExpense = boardingExpenseList.get(i);

					cell = new PdfPCell(new Phrase(sdf.format(boardingExpense.getFromDate())+" "+boardingExpense.getFromTime(), normalFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(sdf.format(boardingExpense.getToDate())+" "+boardingExpense.getToTime(), normalFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(String.valueOf(boardingExpense.getNoOfDayes()), normalFont));
					cell.setColspan(3);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(boardingExpense.getCityName(), normalFont));
					cell.setColspan(2);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(String.valueOf(boardingExpense.getAmount()), normalFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);
				}

			}

			table.addCell(new Phrase("", normalFont));

			table.addCell(new Phrase("", normalFont));

			cell = new PdfPCell(new Phrase("", normalFont));
			cell.setColspan(3);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Total", normalFont));
			cell.setColspan(2);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(String.valueOf(totalBoardingAmount), normalFont));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			doc.add(table);
		} catch (DocumentException e) {
			throw new ExceptionConverter(e);
		} catch (Exception e) {
			throw new ExceptionConverter(e);
		}
	}

	private void conveyanceExpenseTableHeader(Document doc) {
		PdfPTable table = new PdfPTable(8);
		try {
			table.setWidths(new int[] { 2, 2, 2, 2, 2, 2, 2, 2 });
			PdfPCell cell;
			cell = new PdfPCell(new Phrase("Date", normalFontBold));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Place (From)", normalFontBold));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Place (To)", normalFontBold));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Mode of Travel", normalFontBold));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Km.(Self Vehicle)", normalFontBold));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Amount Self Paid", normalFontBold));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Put \"X\" if Company Paid", normalFontBold));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Remarks", normalFontBold));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			doc.add(table);

		} catch (DocumentException e) {
			throw new ExceptionConverter(e);
		} catch (Exception e) {
			throw new ExceptionConverter(e);
		}
	}

	private void conveyanceExpenseTableData(Document doc,
			List<LocalConveyanceExpenseResponse> localConveyanceExpenseList, double totalConveyanceAmount) {

		PdfPTable table = new PdfPTable(8);
		try {
			table.setWidths(new int[] { 2, 2, 2, 2, 2, 2, 2, 2 });
			PdfPCell cell;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			if (localConveyanceExpenseList != null && !localConveyanceExpenseList.isEmpty()) {
				for (int i = 0; i < localConveyanceExpenseList.size(); i++) {

					LocalConveyanceExpenseResponse localConveyanceExpense = localConveyanceExpenseList.get(i);

					cell = new PdfPCell(new Phrase(sdf.format(localConveyanceExpense.getDate()), normalFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(localConveyanceExpense.getFromPlace(), normalFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(localConveyanceExpense.getToPlace(), normalFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(localConveyanceExpense.getModeOfTravelName(), normalFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(String.valueOf(localConveyanceExpense.getDistance()), normalFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(String.valueOf(localConveyanceExpense.getAmount()), normalFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("", normalFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(localConveyanceExpense.getRemark(), normalFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);
				}

			}

			table.addCell(new Phrase("", normalFont));

			table.addCell(new Phrase("", normalFont));

			table.addCell(new Phrase("", normalFont));

			table.addCell(new Phrase("", normalFont));

			cell = new PdfPCell(new Phrase("Total", normalFont));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(String.valueOf(totalConveyanceAmount), normalFont));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			table.addCell(new Phrase("", normalFont));

			table.addCell(new Phrase("", normalFont));

			doc.add(table);
		} catch (DocumentException e) {
			throw new ExceptionConverter(e);
		} catch (Exception e) {
			throw new ExceptionConverter(e);
		}
	}

	private void otherChargesExpenseTableHeader(Document doc) {
		PdfPTable table = new PdfPTable(8);
		try {
			table.setWidths(new int[] { 2, 2, 2, 2, 2, 2, 2, 2 });
			PdfPCell cell;
			cell = new PdfPCell(new Phrase("S. No.", normalFontBold));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Date", normalFontBold));
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Paticulars", normalFontBold));
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Amount", normalFontBold));
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Remarks", normalFontBold));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			doc.add(table);

		} catch (DocumentException e) {
			throw new ExceptionConverter(e);
		} catch (Exception e) {
			throw new ExceptionConverter(e);
		}
	}

	private void otherChargesExpenseTableData(Document doc, List<OtherChargesExpenseResponse> otherChargesExpenseList,
			double totalOtherChargesAmount) {

		PdfPTable table = new PdfPTable(8);
		try {
			table.setWidths(new int[] { 2, 2, 2, 2, 2, 2, 2, 2 });
			PdfPCell cell;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			if (otherChargesExpenseList != null && !otherChargesExpenseList.isEmpty()) {
				for (int i = 0; i < otherChargesExpenseList.size(); i++) {

					OtherChargesExpenseResponse otherChargesExpense = otherChargesExpenseList.get(i);

					cell = new PdfPCell(new Phrase(String.valueOf(i + 1), normalFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(sdf.format(otherChargesExpense.getDate()), normalFont));
					cell.setColspan(2);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(otherChargesExpense.getOtherChargesTypeName(), normalFont));
					cell.setColspan(2);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(String.valueOf(otherChargesExpense.getAmount()), normalFont));
					cell.setColspan(2);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(otherChargesExpense.getRemark(), normalFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);
				}

			}

			table.addCell(new Phrase("", normalFont));

			cell = new PdfPCell(new Phrase("", normalFont));
			cell.setColspan(2);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Total", normalFont));
			cell.setColspan(2);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(String.valueOf(totalOtherChargesAmount), normalFont));
			cell.setColspan(2);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			table.addCell(new Phrase("", normalFont));

			doc.add(table);
		} catch (DocumentException e) {
			throw new ExceptionConverter(e);
		} catch (Exception e) {
			throw new ExceptionConverter(e);
		}
	}

	private void addSummaryHeader(Document doc) {
		PdfPTable table = new PdfPTable(8);
		try {
			table.setWidths(new int[] { 2, 2, 2, 2, 2, 2, 2, 2 });
			PdfPCell cell;

			cell = new PdfPCell(new Phrase("To be filled by the individual", normalFontBold));
			cell.setColspan(3);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("For Accounts Deptt.", normalFontBold));
			cell.setColspan(5);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			doc.add(table);

		} catch (DocumentException e) {
			throw new ExceptionConverter(e);
		} catch (Exception e) {
			throw new ExceptionConverter(e);
		}
	}

	private void addSummaryData(Document doc, ExpenseManagementResponse response) {
		PdfPTable table = new PdfPTable(8);
		try {
			table.setWidths(new int[] { 2, 2, 2, 2, 2, 2, 2, 2 });
			PdfPCell cell;

			cell = new PdfPCell(new Phrase("S. No.", normalFontBold));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Particulars", normalFontBold));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Amount Claimed(Rs.)", normalFontBold));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("", normalFontBold));
			cell.setColspan(5);
			cell.setRowspan(9);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("1", normalFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Tour", normalFontBold));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(String.valueOf(response.getTotalTourAmount()), normalFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("2", normalFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Lodging", normalFontBold));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(String.valueOf(response.getTotalLodgingAmount()), normalFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("3", normalFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Local Conveyance", normalFontBold));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(String.valueOf(response.getTotalLocalConveyanceAmount()), normalFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("4", normalFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Daily Allowance", normalFontBold));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(String.valueOf(response.getTotalBoardingAmount()), normalFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("5", normalFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Miscellaneous Expenditure", normalFontBold));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(String.valueOf(response.getTotalOtherChargesAmount()), normalFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("", normalFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Total Expense", normalFontBold));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(String.valueOf(response.getTotalExpense()), normalFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("", normalFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Less: Advance and Company Paid", normalFontBold));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(String.valueOf(response.getTotalCompanyPaid() + response.getAdvanceAmount()),
					normalFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("", normalFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Net Claim", normalFontBold));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(String.valueOf(response.getNetAmountToPay()), normalFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			doc.add(table);

		} catch (DocumentException e) {
			throw new ExceptionConverter(e);
		} catch (Exception e) {
			throw new ExceptionConverter(e);
		}
	}

	private void addFooterSignatureArea(Document doc, String firstCol, String secondCol) {
		PdfPTable table = new PdfPTable(2);
		try {
			table.setWidths(new int[] { 2, 2 });
			PdfPCell cell;
			cell = new PdfPCell(new Phrase(firstCol, normalFontBold));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setPaddingBottom(5);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(secondCol, normalFontBold));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setPaddingBottom(5);
			table.addCell(cell);
			doc.add(table);
		} catch (DocumentException e) {
			throw new ExceptionConverter(e);
		} catch (Exception e) {
			throw new ExceptionConverter(e);
		}
	}

	private void addFooterDateArea(Document doc, String firstCol) {
		PdfPTable table = new PdfPTable(1);
		try {
			table.setPaddingTop(10);
			table.setWidths(new int[] { 5 });
			PdfPCell cell;
			cell = new PdfPCell(new Phrase(firstCol, normalFontBold));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setPaddingBottom(5);
			table.addCell(cell);
			doc.add(table);
		} catch (DocumentException e) {
			throw new ExceptionConverter(e);
		} catch (Exception e) {
			throw new ExceptionConverter(e);
		}
	}

}
