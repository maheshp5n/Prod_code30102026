package com.pratikshat.exicom.repository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
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
import com.pratikshat.exicom.entity.AnnexureApprovalStatus;
import com.pratikshat.exicom.entity.AnnexureGLCode;
import com.pratikshat.exicom.entity.AnnexureLineItem;
import com.pratikshat.exicom.entity.BusinessDomain;
import com.pratikshat.exicom.entity.CircleDetails;
import com.pratikshat.exicom.entity.ContactDetails;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.Quote;
import com.pratikshat.exicom.entity.QuotePdfHeaderAndFooter;
import com.pratikshat.exicom.entity.QuoteTermsAndConditions;
import com.pratikshat.exicom.entity.ServiceAppointment;
import com.pratikshat.exicom.event.QuoteHeaderFooterPageEvent;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.AnnexureLineItemRequest;
import com.pratikshat.exicom.model.request.AnnexureRequest;
import com.pratikshat.exicom.model.response.AnnexureLineItemResponse;
import com.pratikshat.exicom.model.response.QuoteLineItemResponse;
import com.pratikshat.exicom.utils.AnnexurePDFGeneration;

@Repository
public class AnnexureCustomRepositoryImpl implements AnnexureCustomRepository {

	@PersistenceContext
	EntityManager entityManager;
	@Autowired
	com.pratikshat.exicom.interceptor.WorkflowExecuter workflow;
	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@Autowired
	FileStorageProperties fileStorageProperties;

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			Exception.class, RuntimeException.class,BadRequestException.class,UnauthoriseException.class, SQLException.class })
	@Override
	public AnnexureRequest saveAnnexure(AnnexureRequest request, LoginUser user) throws Exception {

		EmployeeDetails employeeDetails = null;
		try {
			Query selectContactDetailsQuery = entityManager.createQuery(
					"select ed from EmployeeDetails ed left join ed.contact ct left join ct.contactType cty left join ed.approvalManager apr left join apr.contact aprcnt where ed.emailId=?1 and cty.typeId=?2",
					EmployeeDetails.class);
			selectContactDetailsQuery.setParameter(1, user.getUsername());
			selectContactDetailsQuery.setParameter(2, 3L); // for Vendor type= 3
			employeeDetails = (EmployeeDetails) selectContactDetailsQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.UnauthoriseException("You are not registered as Vendor");
		}
		if (employeeDetails.getContact() == null) {
			throw new com.pratikshat.exicom.exception.UnauthoriseException("You are not registered as Vendor");
		}

		BusinessDomain businessDomain = null;
		try {
			Query selectBusinessDomainQuery = entityManager
					.createQuery("select bd from BusinessDomain bd where bd.businessDomainId=?1", BusinessDomain.class);
			selectBusinessDomainQuery.setParameter(1, request.getBusinessDomainId());
			businessDomain = (BusinessDomain) selectBusinessDomainQuery.getSingleResult();
		} catch (NoResultException ex) {

		}
		if (businessDomain == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Business Domain does not exist");
		}

		CircleDetails circleDetails = null;
		if (businessDomain.getBusinessDomainId() == 1) { // Battery
			if (employeeDetails.getApprovalManager() != null) {
				if (employeeDetails.getApprovalManager().getContact() != null) {
					try {
						Query selectCircleDetailsQuery = entityManager.createQuery(
								"select cd from CircleDetails cd left join cd.circleManagerBattry mgrb where mgrb.contactId=?1",
								CircleDetails.class);
						selectCircleDetailsQuery.setParameter(1,
								employeeDetails.getApprovalManager().getContact().getContactId());
						circleDetails = (CircleDetails) selectCircleDetailsQuery.getSingleResult();
					} catch (NoResultException ex) {
						throw new com.pratikshat.exicom.exception.BadRequestException("Circle does not exist");
					}
				} else {
					throw new com.pratikshat.exicom.exception.BadRequestException("Approver Manager does not exist");
				}
			} else {
				throw new com.pratikshat.exicom.exception.BadRequestException("Approver Manager does not exist");
			}

		} else if (businessDomain.getBusinessDomainId() == 2) { // DCT
			if (employeeDetails.getApprovalManager() != null) {
				if (employeeDetails.getApprovalManager().getContact() != null) {
					try {
						Query selectCircleDetailsQuery = entityManager.createQuery(
								"select cd from CircleDetails cd left join cd.circleManagerPower mgrp where mgrp.contactId=?1",
								CircleDetails.class);
						selectCircleDetailsQuery.setParameter(1,
								employeeDetails.getApprovalManager().getContact().getContactId());
						circleDetails = (CircleDetails) selectCircleDetailsQuery.getSingleResult();
					} catch (NoResultException ex) {
						throw new com.pratikshat.exicom.exception.BadRequestException("Circle does not exist");
					}
				} else {
					throw new com.pratikshat.exicom.exception.BadRequestException("Approver Manager does not exist");
				}
			} else {
				throw new com.pratikshat.exicom.exception.BadRequestException("Approver Manager does not exist");
			}
		} else if (businessDomain.getBusinessDomainId() == 3) { // EV
			if (employeeDetails.getApprovalManager() != null) {
				if (employeeDetails.getApprovalManager().getContact() != null) {
					try {
						Query selectCircleDetailsQuery = entityManager.createQuery(
								"select cd from CircleDetails cd left join cd.circleManagerEv mgre where mgre.contactId=?1",
								CircleDetails.class);
						selectCircleDetailsQuery.setParameter(1,
								employeeDetails.getApprovalManager().getContact().getContactId());
						circleDetails = (CircleDetails) selectCircleDetailsQuery.getSingleResult();
					} catch (NoResultException ex) {
						throw new com.pratikshat.exicom.exception.BadRequestException("Circle does not exist");
					}
				} else {
					throw new com.pratikshat.exicom.exception.BadRequestException("Approver Manager does not exist");
				}
			} else {
				throw new com.pratikshat.exicom.exception.BadRequestException("Approver Manager does not exist");
			}
		}

		Annexure annexure = new Annexure();

		annexure.setApprovalStatus(new AnnexureApprovalStatus(1)); // Draft
		annexure.setBusinessDomain(new BusinessDomain(request.getBusinessDomainId()));
		annexure.setCircle(circleDetails);
		annexure.setCreatedBy(new EmployeeDetails(user.getUsername()));
		annexure.setCreatedDate(new Date());
		annexure.setInvoiceDate(request.getInvoiceDate());
		annexure.setInvoiceNumber(request.getInvoiceNumber());
		annexure.setModifiedBy(new EmployeeDetails(user.getUsername()));
		annexure.setModifiedDate(new Date());
		annexure.setOwner(new EmployeeDetails(user.getUsername()));
		// annexure.setSubmittedDate(new java.util.Date());
		annexure.setVendor(employeeDetails.getContact());
		annexure.setPoNumber(request.getPoNumber());
		annexure.setPoDate(request.getPoDate());
		workflow.executeWorkflow(entityManager, annexure, "INSERT", "PRE", user);
		entityManager.persist(annexure);
		workflow.executeWorkflow(entityManager, annexure, "INSERT", "POST", user);
		Query updateManualId = entityManager.createQuery("update Annexure anx set anx.manualId=?1 where anx.id=?2");
		SimpleDateFormat dateformatter = new SimpleDateFormat("yyyyMMdd");
		String manualId = "EXI-" + dateformatter.format(new java.util.Date()) + "-"
				+ com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(annexure.getId()), 5);
		updateManualId.setParameter(1, manualId);
		updateManualId.setParameter(2, annexure.getId());
		updateManualId.executeUpdate();

		request.setId(annexure.getId());

		// search index entry
		try {
			com.pratikshat.exicom.entity.GlobalSearchIndex _search = new com.pratikshat.exicom.entity.GlobalSearchIndex();
			_search.setKeyName("manualId");
			_search.setObjectDetailsUrl("annexure/" + annexure.getId());
			_search.setObjectIdName("id");
			_search.setObjectIdValue("" + annexure.getId());
			_search.setObjectName("Annexure");
			_search.setSearchString(manualId);
			entityManager.persist(_search);

		} catch (Exception e) {
		}

		entityManager.flush();
		entityManager.clear();

		return request;
	}

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			Exception.class, RuntimeException.class, SQLException.class,BadRequestException.class,UnauthoriseException.class })
	@Override
	public AnnexureRequest updateAnnexure(AnnexureRequest request, LoginUser user) throws Exception {

		Annexure annexure = null;
		try {
			Query selectAnnexureQuery = entityManager.createQuery(
					"select anx from Annexure anx left join anx.vendor vnd left join anx.circle cir left join anx.createdBy cby left join anx.modifiedBy mby left join anx.owner onr where anx.id=?1",
					Annexure.class);
			selectAnnexureQuery.setParameter(1, request.getId());
			annexure = (Annexure) selectAnnexureQuery.getSingleResult();
		} catch (NoResultException ex) {

		}
		
		if (annexure == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Annexure does not exist");
		}

		if (!annexure.getOwner().getEmailId().equalsIgnoreCase(user.getUsername())) {
			throw new com.pratikshat.exicom.exception.BadRequestException("You are not authorised");
		}

		List<Integer> statusList = new ArrayList<>();
		statusList.add(2); // Approved by SCH
		statusList.add(4); // Approved by Commercial
		statusList.add(6); // Submitted for Approval
		statusList.add(7); // Approved by Finance
		statusList.add(9); // Posted in SAP
		
		if(statusList.contains(annexure.getApprovalStatus().getId())) {
			throw new com.pratikshat.exicom.exception.BadRequestException("You can't edit this record");
		}
		
		ContactDetails vendor = null;
		try {
			Query selectContactDetailsQuery = entityManager.createQuery(
					"select ct from EmployeeDetails ed left join ed.contact ct left join ct.contactType cty where ed.emailId=?1 and cty.typeId=?2",
					ContactDetails.class);
			selectContactDetailsQuery.setParameter(1, user.getUsername());
			selectContactDetailsQuery.setParameter(2, 3L); // for Vendor type= 3
			vendor = (ContactDetails) selectContactDetailsQuery.getSingleResult();
		} catch (NoResultException ex) {

		}
		if (vendor == null) {
			throw new com.pratikshat.exicom.exception.UnauthoriseException("You are not registered as Vendor");
		}

		if (annexure.getVendor().getContactId() != vendor.getContactId()) {
			throw new com.pratikshat.exicom.exception.UnauthoriseException(
					"You are not authriezed to change this annexure");
		}

		if (request.getApprovalStatusId() != 0) {
			annexure.setApprovalStatus(new AnnexureApprovalStatus(request.getApprovalStatusId()));
		} else {
			annexure.setApprovalStatus(null);
		}

		annexure.setInvoiceDate(request.getInvoiceDate());
		annexure.setInvoiceNumber(request.getInvoiceNumber());
		annexure.setModifiedBy(new EmployeeDetails(user.getUsername()));
		annexure.setModifiedDate(new Date());
		annexure.setPoNumber(request.getPoNumber());
		annexure.setPoDate(request.getPoDate());
		workflow.executeWorkflow(entityManager, annexure, "UPDATE", "PRE", user);
		entityManager.persist(annexure);
		workflow.executeWorkflow(entityManager, annexure, "UPDATE", "POST", user);
		entityManager.flush();
		entityManager.clear();

		return request;
	}

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			Exception.class, RuntimeException.class,UnauthoriseException.class,BadRequestException.class, SQLException.class })
	@Override
	public AnnexureLineItemRequest addAnnexureLineItem(AnnexureLineItemRequest request, LoginUser user)
			throws Exception {

		Annexure annexure = null;
		try {
			Query selectAnnexureQuery = entityManager.createQuery(
					"select anx from Annexure anx left join anx.vendor vnd left join anx.owner onr where anx.id=?1",
					Annexure.class);
			selectAnnexureQuery.setParameter(1, request.getAnnexureId());
			annexure = (Annexure) selectAnnexureQuery.getSingleResult();
		} catch (NoResultException ex) {

		}
		if (annexure == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Annexure does not exist");
		}

		if (!annexure.getOwner().getEmailId().equalsIgnoreCase(user.getUsername())) {
			throw new com.pratikshat.exicom.exception.BadRequestException("You are not authorised");
		}

		
		List<Integer> statusList = new ArrayList<>();
		statusList.add(2); // Approved by SCH
		statusList.add(4); // Approved by Commercial
		statusList.add(6); // Submitted for Approval
		statusList.add(7); // Approved by Finance
		statusList.add(9); // Posted in SAP
		
		if(statusList.contains(annexure.getApprovalStatus().getId())) {
			throw new com.pratikshat.exicom.exception.BadRequestException("You can't edit this record");
		}
		
		ServiceAppointment serviceAppointment = null;
		try {
			Query selectServiceAppointmentQuery = entityManager.createQuery(
					"select sa from ServiceAppointment sa where sa.serviceAppointmentId=?1", ServiceAppointment.class);
			selectServiceAppointmentQuery.setParameter(1, request.getServiceAppointmentId());
			serviceAppointment = (ServiceAppointment) selectServiceAppointmentQuery.getSingleResult();
		} catch (NoResultException ex) {

		}
		if (serviceAppointment == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Service Appointment does not exist");
		}

		ContactDetails vendor = null;
		try {
			Query selectContactDetailsQuery = entityManager.createQuery(
					"select ct from EmployeeDetails ed left join ed.contact ct left join ct.contactType cty where ed.emailId=?1 and cty.typeId=?2",
					ContactDetails.class);
			selectContactDetailsQuery.setParameter(1, user.getUsername());
			selectContactDetailsQuery.setParameter(2, 3L); // for Vendor type= 3
			vendor = (ContactDetails) selectContactDetailsQuery.getSingleResult();
		} catch (NoResultException ex) {

		}
		if (vendor == null) {
			throw new com.pratikshat.exicom.exception.UnauthoriseException("Your are not registered as Vendor");
		}

		if (annexure.getVendor().getContactId() != vendor.getContactId()) {
			throw new com.pratikshat.exicom.exception.UnauthoriseException(
					"Your are not authriezed to change this annexure");
		}

		AnnexureGLCode annexureGLCode = null;
		try {
			Query selectAnnexureGLCodeQuery = entityManager
					.createQuery("select gl from AnnexureGLCode gl where gl.id=?1", AnnexureGLCode.class);
			selectAnnexureGLCodeQuery.setParameter(1, request.getGlCodeId());
			annexureGLCode = (AnnexureGLCode) selectAnnexureGLCodeQuery.getSingleResult();
		} catch (NoResultException ex) {

		}
		if (annexureGLCode == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Annexure GL code does not exist");
		}

		AnnexureLineItem annexureLineItem = null;
		try {
			Query selectAnnexureLineItemQuery = entityManager.createQuery(
					"select ali from AnnexureLineItem ali join ali.annexure anx join ali.serviceAppointment sa where sa.serviceAppointmentId=?1",
					AnnexureLineItem.class);
			selectAnnexureLineItemQuery.setParameter(1, request.getServiceAppointmentId());
			annexureLineItem = (AnnexureLineItem) selectAnnexureLineItemQuery.getSingleResult();
		} catch (NoResultException ex) {

		}
		if (annexureLineItem != null) {
			throw new com.pratikshat.exicom.exception.BadRequestException(
					"Service Appointment " + serviceAppointment.getAppointmentNumber()
							+ " already exist in the Annexure " + annexureLineItem.getAnnexure().getManualId());
		}

		AnnexureLineItem lineItem = new AnnexureLineItem();
		lineItem.setActivity(request.getActivity());

		double amount = annexureGLCode.getAmount();
		double taxRate = annexureGLCode.getTaxRate();

		double amountWithTax = amount + (amount * taxRate) / 100;

		lineItem.setAmount(amount);
		lineItem.setAmountWithTax(amountWithTax);
		lineItem.setAnnexure(annexure);
		lineItem.setCreatedBy(new EmployeeDetails(user.getUsername()));
		lineItem.setCreatedDate(new Date());
		lineItem.setDeleted(false);
		lineItem.setExicomInvoiceDate(request.getExicomInvoiceDate());
		lineItem.setExicomInvoiceNumber(request.getExicomInvoiceNumber());
		lineItem.setGlCode(annexureGLCode);
		lineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
		lineItem.setModifiedDate(new Date());
		lineItem.setRemark(request.getRemark());
		lineItem.setServiceAppointment(serviceAppointment);
		lineItem.setWorkCompletionDate(request.getWorkCompletionDate());
		lineItem.setWorkStatus(request.getWorkStatus());
		workflow.executeWorkflow(entityManager, lineItem, "INSERT", "PRE", user);
		entityManager.persist(lineItem);
		workflow.executeWorkflow(entityManager, lineItem, "INSERT", "POST", user);
		request.setId(lineItem.getId());

		Query updateManualId = entityManager
				.createQuery("update AnnexureLineItem ali set ali.manualId=?1 where ali.id=?2");
		SimpleDateFormat dateformatter = new SimpleDateFormat("yyyyMMdd");
		String manualId = "ALI-" + dateformatter.format(new java.util.Date()) + "-"
				+ com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(lineItem.getId()), 5);
		updateManualId.setParameter(1, manualId);
		updateManualId.setParameter(2, lineItem.getId());
		updateManualId.executeUpdate();

		Object[] countAndSum = null;
		try {
			Query countAndSumQuery = entityManager.createQuery(
					"select COALESCE(count(ali.id),0), COALESCE(sum(ali.amountWithTax),0) from AnnexureLineItem ali left join ali.annexure anx where ali.isDeleted=false and anx.id=?1");
			countAndSumQuery.setParameter(1, annexure.getId());
			countAndSum = (Object[]) countAndSumQuery.getSingleResult();

		} catch (NoResultException ex) {

		}

		if (countAndSum != null) {
			annexure.setLineItemCount((long) countAndSum[0]);
			annexure.setTotalAmount((double) countAndSum[1]);
		}

		workflow.executeWorkflow(entityManager, annexure, "UPDATE", "PRE", user);
		entityManager.persist(annexure);
		workflow.executeWorkflow(entityManager, annexure, "UPDATE", "POST", user);
		entityManager.flush();
		entityManager.clear();
		return request;
	}

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			Exception.class, RuntimeException.class,BadRequestException.class,UnauthoriseException.class, SQLException.class })
	@Override
	public AnnexureLineItemRequest updateAnnexureLineItem(AnnexureLineItemRequest request, LoginUser user)
			throws Exception {

		AnnexureLineItem annexureLineItem = null;
		try {
			Query selectAnnexureLineItemQuery = entityManager.createQuery(
					"select ali from AnnexureLineItem ali left join ali.serviceAppointment sa left join ali.annexure anx left join ali.glCode gl left join ali.createdBy cby left join ali.modifiedBy mby where ali.id=?1",
					AnnexureLineItem.class);
			selectAnnexureLineItemQuery.setParameter(1, request.getId());
			annexureLineItem = (AnnexureLineItem) selectAnnexureLineItemQuery.getSingleResult();
		} catch (NoResultException ex) {

		}
		if (annexureLineItem == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Annexure LineItem does not exist");
		}

		Annexure annexure = null;
		try {
			Query selectAnnexureQuery = entityManager.createQuery(
					"select anx from Annexure anx left join anx.vendor vnd left join anx.owner onr where anx.id=?1",
					Annexure.class);
			selectAnnexureQuery.setParameter(1, request.getAnnexureId());
			annexure = (Annexure) selectAnnexureQuery.getSingleResult();
		} catch (NoResultException ex) {

		}
		if (annexure == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Annexure does not exist");
		}

		if (!annexure.getOwner().getEmailId().equalsIgnoreCase(user.getUsername())) {
			throw new com.pratikshat.exicom.exception.BadRequestException("You are not authorised");
		}

		List<Integer> statusList = new ArrayList<>();
		statusList.add(2); // Approved by SCH
		statusList.add(4); // Approved by Commercial
		statusList.add(6); // Submitted for Approval
		statusList.add(7); // Approved by Finance
		statusList.add(9); // Posted in SAP
		
		if(statusList.contains(annexure.getApprovalStatus().getId())) {
			throw new com.pratikshat.exicom.exception.BadRequestException("You can't edit this record");
		}
		
		ServiceAppointment serviceAppointment = null;
		try {
			Query selectServiceAppointmentQuery = entityManager.createQuery(
					"select sa from ServiceAppointment sa where sa.serviceAppointmentId=?1", ServiceAppointment.class);
			selectServiceAppointmentQuery.setParameter(1, request.getServiceAppointmentId());
			serviceAppointment = (ServiceAppointment) selectServiceAppointmentQuery.getSingleResult();
		} catch (NoResultException ex) {

		}
		if (serviceAppointment == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Service Appointment does not exist");
		}

		ContactDetails vendor = null;
		try {
			Query selectContactDetailsQuery = entityManager.createQuery(
					"select ct from EmployeeDetails ed left join ed.contact ct left join ct.contactType cty where ed.emailId=?1 and cty.typeId=?2",
					ContactDetails.class);
			selectContactDetailsQuery.setParameter(1, user.getUsername());
			selectContactDetailsQuery.setParameter(2, 3L); // for Vendor type= 3
			vendor = (ContactDetails) selectContactDetailsQuery.getSingleResult();
		} catch (NoResultException ex) {

		}
		if (vendor == null) {
			throw new com.pratikshat.exicom.exception.UnauthoriseException("Your are not registered as Vendor");
		}

		if (annexure.getVendor().getContactId() != vendor.getContactId()) {
			throw new com.pratikshat.exicom.exception.UnauthoriseException(
					"Your are not authriezed to change this annexure");
		}

		AnnexureGLCode annexureGLCode = null;
		try {
			Query selectAnnexureGLCodeQuery = entityManager
					.createQuery("select gl from AnnexureGLCode gl where gl.id=?1", AnnexureGLCode.class);
			selectAnnexureGLCodeQuery.setParameter(1, request.getGlCodeId());
			annexureGLCode = (AnnexureGLCode) selectAnnexureGLCodeQuery.getSingleResult();
		} catch (NoResultException ex) {

		}
		if (annexureGLCode == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Annexure GL code does not exist");
		}

		if (annexureLineItem.getGlCode().getId() != request.getGlCodeId()) {

			double amount = annexureGLCode.getAmount();
			double taxRate = annexureGLCode.getTaxRate();

			double amountWithTax = amount + (amount * taxRate) / 100;

			annexureLineItem.setAmount(amount);
			annexureLineItem.setAmountWithTax(amountWithTax);
		}

		annexureLineItem.setActivity(request.getActivity());
		annexureLineItem.setExicomInvoiceDate(request.getExicomInvoiceDate());
		annexureLineItem.setExicomInvoiceNumber(request.getExicomInvoiceNumber());
		annexureLineItem.setGlCode(annexureGLCode);
		annexureLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
		annexureLineItem.setModifiedDate(new Date());
		annexureLineItem.setRemark(request.getRemark());
		annexureLineItem.setServiceAppointment(serviceAppointment);
		annexureLineItem.setWorkCompletionDate(request.getWorkCompletionDate());
		annexureLineItem.setWorkStatus(request.getWorkStatus());
		workflow.executeWorkflow(entityManager, annexureLineItem, "UPDATE", "PRE", user);
		entityManager.persist(annexureLineItem);
		workflow.executeWorkflow(entityManager, annexureLineItem, "UPDATE", "POST", user);
		Object[] countAndSum = null;
		try {
			Query countAndSumQuery = entityManager.createQuery(
					"select coalesce(count(ali.id),0), coalesce(sum(ali.amountWithTax),0) from AnnexureLineItem ali left join ali.annexure anx where ali.isDeleted=false and anx.id=?1");
			countAndSumQuery.setParameter(1, annexure.getId());
			countAndSum = (Object[]) countAndSumQuery.getSingleResult();

		} catch (NoResultException ex) {

		}

		if (countAndSum != null && countAndSum.length == 2) {
			annexure.setLineItemCount((long) countAndSum[0]);
			annexure.setTotalAmount((double) countAndSum[1]);
		}
		workflow.executeWorkflow(entityManager, annexure, "UPDATE", "PRE", user);
		entityManager.persist(annexure);
		workflow.executeWorkflow(entityManager, annexure, "UPDATE", "POST", user);

		entityManager.flush();
		entityManager.clear();

		return request;
	}

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			Exception.class, RuntimeException.class,BadRequestException.class,UnauthoriseException.class, SQLException.class })
	@Override
	public void deleteAnnexureLineItem(long annexureLineItemId, LoginUser user) throws Exception {

		AnnexureLineItem annexureLineItem = null;
		try {
			Query selectAnnexureLineItemQuery = entityManager.createQuery(
					"select ali from AnnexureLineItem ali left join ali.serviceAppointment sa left join ali.annexure anx left join ali.glCode gl left join ali.createdBy cby left join ali.modifiedBy mby where ali.id=?1",
					AnnexureLineItem.class);
			selectAnnexureLineItemQuery.setParameter(1, annexureLineItemId);
			annexureLineItem = (AnnexureLineItem) selectAnnexureLineItemQuery.getSingleResult();
		} catch (NoResultException ex) {

		}
		if (annexureLineItem == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Annexure LineItem does not exist");
		}

		Annexure annexure = null;
		try {
			Query selectAnnexureQuery = entityManager.createQuery(
					"select anx from Annexure anx left join anx.vendor vnd left join anx.owner onr where anx.id=?1",
					Annexure.class);
			selectAnnexureQuery.setParameter(1, annexureLineItem.getAnnexure().getId());
			annexure = (Annexure) selectAnnexureQuery.getSingleResult();
		} catch (NoResultException ex) {

		}
		if (annexure == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Annexure does not exist");
		}

		if (!annexure.getOwner().getEmailId().equalsIgnoreCase(user.getUsername())) {
			throw new com.pratikshat.exicom.exception.BadRequestException("You are not authorised");
		}

		List<Integer> statusList = new ArrayList<>();
		statusList.add(2); // Approved by SCH
		statusList.add(4); // Approved by Commercial
		statusList.add(6); // Submitted for Approval
		statusList.add(7); // Approved by Finance
		statusList.add(9); // Posted in SAP
		
		if(statusList.contains(annexure.getApprovalStatus().getId())) {
			throw new com.pratikshat.exicom.exception.BadRequestException("You can't edit this record");
		}
		
		annexureLineItem.setDeleted(true);
		entityManager.persist(annexureLineItem);

		Object[] countAndSum = null;
		try {
			Query countAndSumQuery = entityManager.createQuery(
					"select COALESCE(count(ali.id),0), COALESCE(sum(ali.amountWithTax),0) from AnnexureLineItem ali left join ali.annexure anx where ali.isDeleted=false and anx.id=?1");
			countAndSumQuery.setParameter(1, annexure.getId());
			countAndSum = (Object[]) countAndSumQuery.getSingleResult();

		} catch (NoResultException ex) {

		}

		if (countAndSum != null && countAndSum.length == 2) {
			annexure.setLineItemCount((long) countAndSum[0]);
			annexure.setTotalAmount((double) countAndSum[1]);
		}

		entityManager.persist(annexure);

		entityManager.flush();
		entityManager.clear();

	}

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			Exception.class, RuntimeException.class,BadRequestException.class,UnauthoriseException.class, SQLException.class })
	@Override
	public void submitForApproval(long annexureId, int businessDomainId, LoginUser user) throws Exception {
		Annexure annexure = null;
		try {
			Query getQuery = entityManager.createQuery(
					"select anx from Annexure anx left join anx.approvalStatus st left join anx.owner onr where anx.id=?1",
					Annexure.class);
			getQuery.setParameter(1, annexureId);
			annexure = (Annexure) getQuery.getSingleResult();

		} catch (javax.persistence.NoResultException ex) {
			throw new BadRequestException("Annexure does not exist");
		}

		if (!annexure.getOwner().getEmailId().equalsIgnoreCase(user.getUsername())) {
			throw new com.pratikshat.exicom.exception.BadRequestException("You are not authorised");
		}

		List<Integer> statusList = new ArrayList<>();
		statusList.add(2); // Approved by SCH
		statusList.add(4); // Approved by Commercial
		statusList.add(6); // Submitted for Approval
		statusList.add(7); // Approved by Finance
		statusList.add(9); // Posted in SAP
		
		if(statusList.contains(annexure.getApprovalStatus().getId())) {
			throw new com.pratikshat.exicom.exception.BadRequestException("You can't edit this record");
		}
		
		List<AnnexureLineItem> annexureLineItems = null;
		try {
			Query selectAnnexureLineItemQuery = entityManager.createQuery(
					"select ali from AnnexureLineItem ali join ali.annexure anx where anx.id=?1",
					AnnexureLineItem.class);
			selectAnnexureLineItemQuery.setParameter(1, annexureId);
			annexureLineItems = selectAnnexureLineItemQuery.getResultList();
		} catch (NoResultException ex) {
			throw new BadRequestException("Please add the Annexure line item");
		}

		if(annexureLineItems!=null && annexureLineItems.size()==0) {
			throw new BadRequestException("Please add the Annexure line item");
		}
		
		annexure.setSubmittedDate(new java.util.Date());
		annexure.setApprovalStatus(new AnnexureApprovalStatus(6)); // Submitted for Approval
		workflow.executeWorkflow(entityManager, annexure, "UPDATE", "PRE", user);
		entityManager.persist(annexure);
		workflow.executeWorkflow(entityManager, annexure, "UPDATE", "POST", user);

		// approval process entry point
		try {

			java.util.Set<String> _roles = new java.util.HashSet<String>();
			java.util.Iterator ite = user.getAuthorities().iterator();
			while (ite.hasNext()) {
				com.pratikshat.exicom.entity.LoginUserRoles _rr = (com.pratikshat.exicom.entity.LoginUserRoles) ite
						.next();
				_roles.add(_rr.getAuthority());
			}

			boolean roleFound = false;
			boolean userFound = false;
			com.pratikshat.exicom.entity.Approval _appliedRule = new com.pratikshat.exicom.entity.Approval();

			java.util.List<com.pratikshat.exicom.entity.Approval> _rulesLst = new java.util.ArrayList<com.pratikshat.exicom.entity.Approval>();

			_rulesLst = entityManager.createQuery(
					"select apr from Approval apr where apr.objectClassName=?1 and apr.active=?2 and apr.businessDomain.businessDomainId=?3",
					com.pratikshat.exicom.entity.Approval.class).setParameter(1, "Annexure").setParameter(2, true)
					.setParameter(3, businessDomainId).getResultList();

			for (com.pratikshat.exicom.entity.Approval _appr : _rulesLst) {
				String submitters[] = _appr.getInitialSubmitters().split(",");
				for (String submi : submitters) {
					String _subs[] = submi.split(":");
					if (_subs[0].equalsIgnoreCase("ROLE")) {
						if (_roles.contains(_subs[1])) {
							roleFound = true;
							break;
						}
					}
					if (_subs[0].equalsIgnoreCase("USER")) {
						if (user.getUsername().equalsIgnoreCase(_subs[1])) {
							userFound = true;
							break;
						}
					}
				}
				if (roleFound || userFound) {
					_appliedRule = _appr;
					break;
				}
			}

			com.pratikshat.exicom.entity.ApprovalSteps _stapes = entityManager
					.createQuery("select stp from ApprovalSteps stp where stp.approval=?1 and stp.approvedStage=?2",
							com.pratikshat.exicom.entity.ApprovalSteps.class)
					.setParameter(1, _appliedRule).setParameter(2, 1).getSingleResult();

			String assigned[] = _stapes.getApprovedBy().split(":");

			String assigneUserEmailId = "";

			if (assigned[0].equalsIgnoreCase("USER")) {
				assigneUserEmailId = assigned[1];
			}
			if (assigned[0].equalsIgnoreCase("APPROVAL-MANAGER")) {
				assigneUserEmailId = entityManager.createQuery(
						"select apm.emailId from EmployeeDetails emp join emp.approvalManager apm where emp.emailId=?1",
						String.class).setParameter(1, user.getUsername()).getSingleResult();
			}

			if (assigned[0].equalsIgnoreCase("MANAGER")) {
				assigneUserEmailId = entityManager.createQuery(
						"select apm.emailId from EmployeeDetails emp join emp.manager apm where emp.emailId=?1",
						String.class).setParameter(1, user.getUsername()).getSingleResult();
			}

			if (assigned[0].equalsIgnoreCase("ROLE")) {
				assigneUserEmailId = getApproverEmail(assigned[1], user.getUsername(), entityManager);
			}

			com.pratikshat.exicom.entity.ToBeApproved _toBeApproved = new com.pratikshat.exicom.entity.ToBeApproved();
			_toBeApproved.setAssignedTo(new com.pratikshat.exicom.entity.EmployeeDetails(assigneUserEmailId));
			_toBeApproved.setAssignedDate(new java.util.Date());
			_toBeApproved.setObjectName("Annexure");
			_toBeApproved.setObjectId(annexure.getId());
			_toBeApproved.setObjectManualId(annexure.getManualId());
			_toBeApproved.setObjectUrl("/annexure/" + annexure.getId());
			_toBeApproved.setRemark("");
			_toBeApproved.setApprovalRuleId(_appliedRule.getId());
			_toBeApproved.setStage(1);
			_toBeApproved.setStatus(false);
			_toBeApproved.setPreviousRemarks(null);

			workflow.executeWorkflow(entityManager, _toBeApproved, "INSERT", "PRE", user);
			entityManager.persist(_toBeApproved);
			workflow.executeWorkflow(entityManager, _toBeApproved, "INSERT", "POST", user);

			com.pratikshat.exicom.entity.ApplicationNotificationDetails notificationDetails = new com.pratikshat.exicom.entity.ApplicationNotificationDetails();
			notificationDetails.setNofificationDate(new java.util.Date());
			notificationDetails.setNotificationActionUrl("/itemtobeapproved");
			notificationDetails.setNotificationMessage(
					String.format("Annexure %s has been send to you for approval", annexure.getManualId()));
			notificationDetails.setNotificationRead(false);
			notificationDetails.setUserEmailId(assigneUserEmailId);

			entityManager.persist(notificationDetails);

			try {
				messagingTemplate.convertAndSend("/topic/" + assigneUserEmailId, notificationDetails);
			} catch (Exception ex) {
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BadRequestException("Unable to submit request for approval");

		}

		entityManager.flush();
		entityManager.clear();
	}

	private String getApproverEmail(String role, String userMailId, javax.persistence.EntityManager _em) {
		com.pratikshat.exicom.entity.EmployeeDetails emp = null;
		emp = _em
				.createQuery("select mng from EmployeeDetails emp join emp.manager mng where emp.emailId=?1",
						com.pratikshat.exicom.entity.EmployeeDetails.class)
				.setParameter(1, userMailId).getSingleResult();

		java.util.List<String> roles = _em.createQuery(
				"select rol.authority from LoginUserRoles rol join rol.loginUser usr where usr.username=?1",
				String.class).setParameter(1, emp.getEmailId()).getResultList();

		for (String _rl : roles) {
			if (_rl.equalsIgnoreCase(role)) {
				return emp.getEmailId();
			}
		}
		return getApproverEmail(role, emp.getEmailId(), _em);

	}

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = { Exception.class,BadRequestException.class,UnauthoriseException.class,
			RuntimeException.class }, propagation = Propagation.REQUIRES_NEW)
	@Override
	public ByteArrayInputStream createPdf(long annexureId, LoginUser user) throws Exception {

		AnnexurePDFGeneration annexurePDFGeneration = new AnnexurePDFGeneration(entityManager, fileStorageProperties);
		
		
		return annexurePDFGeneration.createPdf(annexureId, user);
	}
}
