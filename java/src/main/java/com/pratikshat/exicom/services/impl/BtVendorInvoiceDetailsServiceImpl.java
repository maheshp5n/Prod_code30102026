package com.pratikshat.exicom.services.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.pratikshat.exicom.entity.BtCase;
import com.pratikshat.exicom.entity.BtToBeApproved;
import com.pratikshat.exicom.entity.BtVendorInvoiceDetails;
import com.pratikshat.exicom.entity.BtVendorInvoiceGrn;
import com.pratikshat.exicom.entity.BtVendorPoDetails;
import com.pratikshat.exicom.entity.BtWccDetails;
import com.pratikshat.exicom.entity.BtWccServiceAppointment;
import com.pratikshat.exicom.entity.ContactDetails;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.InternalServiceCode;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.LoginUserRoles;
import com.pratikshat.exicom.entity.ToBeApproved;
import com.pratikshat.exicom.entity.VendorInvoiceStatus;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.BtCaseRepository;
import com.pratikshat.exicom.repository.BtVendorInvoiceDetailsRepository;
import com.pratikshat.exicom.repository.BtVendorPoDetailsRepository;
import com.pratikshat.exicom.repository.BtWccDetailsRepository;
import com.pratikshat.exicom.repository.BtWccServiceAppointmentRepository;
import com.pratikshat.exicom.repository.ContactDetailsRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtVendorInvoiceDetailsService;

@Service
public class BtVendorInvoiceDetailsServiceImpl implements BtVendorInvoiceDetailsService {

	@Autowired
	BtWccDetailsRepository dbWcc;
	@PersistenceContext
	EntityManager em;
	@Autowired
	BtVendorInvoiceDetailsRepository dbVendor;
	@Autowired
	BtCaseRepository dbCase;
	@Autowired
	ContactDetailsRepository dbContact;
	@Autowired
	BtWccServiceAppointmentRepository dbWccApp;
	@Autowired
	UserProfileRepository dbProfile;
	@Autowired
	JdbcTemplate jdbc;
	@Autowired
	BtVendorPoDetailsRepository dbPo;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public Map<String, Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException {
		boolean view = dbProfile.hasObjectAccess(user.getUsername(), BtVendorInvoiceDetails.class,
				UserProfileRepository.VIEW);
		boolean viewAll = dbProfile.hasObjectAccess(user.getUsername(), BtVendorInvoiceDetails.class,
				UserProfileRepository.VIEW_ALL);
		if (view || viewAll) {
			long _id = Long.parseLong(id.toString());

			return dbVendor.getSingleVendorInvoice(_id);
		} else {
			throw new UnauthoriseException("You  are not authorized");
		}
	}

	@Override
	public Page<Map<String, Object>> getPage(Pageable page, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		boolean view = dbProfile.hasObjectAccess(user.getUsername(), BtVendorInvoiceDetails.class,
				UserProfileRepository.VIEW);
		boolean viewAll = dbProfile.hasObjectAccess(user.getUsername(), BtVendorInvoiceDetails.class,
				UserProfileRepository.VIEW_ALL);
		if (!view || viewAll) {
			return dbVendor.getPage(page);
		} else {
			throw new UnauthoriseException("You  are not authorized");
		}
	}

	
	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = { Exception.class, BadRequestException.class,
			UnauthoriseException.class, RuntimeException.class }, propagation = Propagation.REQUIRES_NEW)
	public Map<String, Object> saveObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {

		EmployeeDetails emp = new EmployeeDetails(user.getUsername());
		// VALIDATION
		if (request.get("remarks") != null && request.get("remarks").toString().length() > 500)
			throw new BadRequestException("Remarks length can not be greater then 500");
		BtWccDetails _wcc = dbWcc.getOne(Long.parseLong(request.get("wccID").toString()));

		if (!_wcc.getOwner().getEmailId().equalsIgnoreCase(user.getUsername()))
			throw new UnauthoriseException("You are not authorised");

		if (_wcc.getWccStatus().getId() != 2) {
			throw new BadRequestException(String.format("Invalid request %s is not approved", _wcc.getManualId()));
		}
		if (_wcc.isInvoiced())
			throw new BadRequestException("Invoice already generated for this Wcc");
		ContactDetails _vendorContact = dbContact.getContactDetailsWithEmail(user.getUsername());

		if (_vendorContact.getAccount().getAccountType() == null
				|| _vendorContact.getAccount().getAccountType().getAccountTypeId() != 1) {
			throw new UnauthoriseException("Only vendor can generate Invoice");
		}
		List<BtWccServiceAppointment> lstService = dbWccApp.getAllAppointmentWithWcc(_wcc);
		float totalAmount = 0;
		int totalNumber = 0;
		BtVendorPoDetails po = null;
		if (lstService != null && lstService.size() > 0) {
			totalNumber = lstService.size();
			for (BtWccServiceAppointment ap : lstService) {
				totalAmount += (float) ap.getPoDetails().getActivityPrice();
				po = ap.getPoDetails();
			}
		}
		BtVendorInvoiceDetails _invoice = new BtVendorInvoiceDetails();

		_invoice.setInvoiceAmount(_wcc.getWccAmount());
		_invoice.setInvoiceStatus(new VendorInvoiceStatus(6));
		_invoice.setOwner(emp);
		_invoice.setVendorId(_vendorContact.getEmployeeCode());
		try {
			_invoice.setVendorInvoiceDate((sdf.parse(request.get("vendorInvoiceDate").toString())));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		_invoice.setVendorInvoiceNumber((String) request.get("VendorInvoiceNumber"));
		_invoice.setVendorName(_vendorContact.getAccount().getName());
		_invoice.setVendorPo(po);
		_invoice.setWccDetails(_wcc);
		_invoice.setPoDate(po.getPoDate());
		_invoice.setRemarks(request.get("remarks").toString());
		_invoice.setInvoiceTaxAmount(_wcc.getWccTaxAmount());
		_invoice.setInvoiceTotalAmount(_wcc.getWccTotalAmount());
		dbVendor.saveDetails(_invoice, user);
		List<BtCase> listCaseDetails = dbCase.getCaseWithWcc(_invoice.getWccDetails());
		for (BtCase cds : listCaseDetails) {
			cds.setVendorInvoiceNumber(_invoice.getVendorInvoiceNumber());
			cds.setVendorInvoiceDate(_invoice.getVendorInvoiceDate());
			dbCase.updateDetails(cds, user);
		}
		return null;
	}

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = { Exception.class, BadRequestException.class,
			UnauthoriseException.class, RuntimeException.class }, propagation = Propagation.REQUIRES_NEW)
	public Map<String, Object> updateObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		BtVendorInvoiceDetails _invoice = dbVendor.getDetailsWithId(Long.parseLong(request.get("id").toString()));

		if (_invoice.getInvoiceStatus().getId() == 3 || _invoice.getInvoiceStatus().getId() == 5) {
		} else {
			throw new UnauthoriseException("You are not authorised");
		}
		if (!_invoice.getOwner().getEmailId().equalsIgnoreCase(user.getUsername())) {
			throw new UnauthoriseException("You are not authorised");
		}

		if (request.get("remarks") != null && request.get("remarks").toString().length() > 500)
			throw new BadRequestException("Remarks length can not be greater then 500");
		BtWccDetails _wcc = dbWcc.getBtWccDetailsWithID(Long.parseLong(request.get("WccId").toString()));
		if (!_wcc.getOwner().getEmailId().equalsIgnoreCase(user.getUsername()))
			throw new UnauthoriseException("You are not authorised");
		if (_wcc.isInvoiced())
			throw new BadRequestException("Invoice already generated for this Wcc");
		if (_wcc.getWccStatus().getId() != 2)
			throw new BadRequestException(String.format("WCC %s not approved", _wcc.getManualId()));

		ContactDetails _vendorContact = dbContact.getContactDetailsWithEmail(user.getUsername());
		if (_vendorContact.getAccount().getAccountType() == null
				|| _vendorContact.getAccount().getAccountType().getAccountTypeId() != 1) {
			throw new UnauthoriseException("Only vendor can generate Invoice");
		}

		List<BtWccServiceAppointment> lstService = dbWccApp.getAllAppointmentWithWcc(_wcc);
		BtVendorPoDetails po = null;
		if (lstService != null && lstService.size() > 0) {
			for (BtWccServiceAppointment ap : lstService) {
				po = ap.getPoDetails();
			}
		}
		_invoice.setInvoiceAmount(_wcc.getWccAmount());
		_invoice.setInvoiceStatus(new VendorInvoiceStatus(1));
		_invoice.setOwner(new EmployeeDetails(user.getUsername()));
		_invoice.setVendorId(_vendorContact.getEmployeeCode());

		try {
			_invoice.setVendorInvoiceDate((sdf.parse(request.get("vendorInvoiceDate").toString())));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		_invoice.setVendorInvoiceNumber((String) request.get("VendorInvoiceNumber"));
		_invoice.setVendorName(_vendorContact.getAccount().getName());
		_invoice.setVendorPo(po);
		_invoice.setWccDetails(_wcc);
		_invoice.setPoDate(po.getPoDate());
		_invoice.setRemarks(request.get("remarks").toString());
		_invoice.setInvoiceStatus(new VendorInvoiceStatus(6));
		_invoice.setInvoiceTaxAmount(_wcc.getWccTaxAmount());
		_invoice.setInvoiceTotalAmount(_wcc.getWccTotalAmount());
		dbVendor.updateDetails(_invoice, user);
		List<BtCase> listCaseDetails = dbCase.getCaseWithWcc(_invoice.getWccDetails());
		for (BtCase cds : listCaseDetails) {
			cds.setVendorInvoiceNumber(_invoice.getVendorInvoiceNumber());
			cds.setVendorInvoiceDate(_invoice.getVendorInvoiceDate());
			dbCase.updateDetails(cds, user);
		}
		return request;
	}

	@Override
	public List<Map<String, Object>> searchForList(String search, LoginUser user)
			throws BadRequestException, UnauthoriseException {

		return null;
	}

	@Override
	public List<Map<String, Object>> advanceSearch(Map<String, Object> searchKeys, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		List<Object> value = new ArrayList<>();
		String _query = "SELECT \r\n"
				+ "	inv.`id` AS invoiceId,inv.`manual_id` AS invoiceManualId,inv.`invoice_amount` AS invoiceAmount,inv.`invoice_status_id` AS invoiceStatusId,\r\n"
				+ "	inv.`po_date` AS invoicePoDate,inv.`remarks` AS invoiceRemarks,inv.`vendor_id` AS invoiceVendorId,\r\n"
				+ "	inv.`vendor_invoice_date` AS vendorInvoiceDate,inv.`vendor_invoice_number` AS vendorInvoiceNumber,inv.`vendor_name` AS vendorName,\r\n"
				+ "	inv.`vendor_po_id` AS vendorPoID,inv.`wcc_details_id` AS wccDetailsId,inv.invoice_copy_url AS invoiceCopyUrl,\r\n"
				+ "	inv.invoice_tax_amount AS invoiceTaxAmount,inv.invoice_total_amount AS invoiceTotalAmount,vis.`name` AS invoiceStatus,\r\n"
				+ "	po.`busineed_area` AS poBusineesArea,po.`circle_circle_name` AS poCircleName,po.`code_for_internal_ref` AS poCodeForInternalRef,\r\n"
				+ "	po.`cost_centre` AS poCostCentre,po.`gl_code` AS glCode,po.`po_category_id` AS poCategoryId,po.`po_date` AS poDate,\r\n"
				+ "	po.`po_expiry_date` AS poExpiryDate,po.`po_number` AS poNumber,po.`remaining_quantity` AS poRemainingQuantity,\r\n"
				+ "	po.`total_quantity` AS poTotalQuantity,po.`vendor_code` AS povendorCode,po.`vendor_name` AS povendorName,vis.`name` AS vendorInvocieStatus,\r\n"
				+ "	wcc.`service_center_legal_entity_id` AS serviceCenterId,wcc.`manual_id` AS wccManualId,lgl.`legal_entity_name` AS serviceCenterName,\r\n"
				+ "	crb.`first_name` AS createdByFirstName,crb.`last_name` AS createdByLastName,crb.`middle_name` AS createdByMiddleName,\r\n"
				+ "	mdb.`first_name` AS lastModifiedByFirstName,mdb.`last_name` AS lastModifiedByLastName,mdb.`middle_name` AS lastModifiedByMiddleName,\r\n"
				+ "	cts.`contact_id` AS vendorContactId,cts.`account_id` AS vendorAccountSapCode,inv.`created_by_email_id` AS invoiceCreatedByEmailId,\r\n"
				+ "	inv.`last_modified_by_email_id` AS invoiceLastModifiedEmailId,inv.`created_date` AS CreatedDate, inv.`last_modified_date` AS ModifiedDate\r\n"
				+ "FROM `bt_vendor_invoice_details` inv\r\n"
				+ "JOIN `bt_vendor_po_details` po ON po.`id` = inv.`vendor_po_id`\r\n"
				+ "JOIN `bt_wcc_details` wcc ON wcc.`id`= inv.`wcc_details_id`\r\n"
				+ "JOIN `vendor_invoice_status` vis ON inv.`invoice_status_id`=vis.`id`\r\n"
				+ "JOIN `contact_details` cts ON cts.`email_id`=inv.`created_by_email_id`\r\n"
				+ "JOIN `legal_entity` lgl ON lgl.`legal_entity_id`=wcc.`service_center_legal_entity_id`\r\n"
				+ "JOIN `employee_details` crb ON crb.`email_id`=inv.`created_by_email_id`\r\n"
				+ "JOIN `employee_details` mdb ON mdb.`email_id`=inv.`last_modified_by_email_id`\r\n" + "WHERE 1=1";
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(_query);
		if (searchKeys.get("invoiceNumber") != null && !searchKeys.get("invoiceNumber").equals("")) {
			queryBuilder.append(" and inv.`manual_id` like ?");
			value.add("%" + searchKeys.get("invoiceNumber") + "%");
		}
		if (searchKeys.get("vendor_name") != null && !searchKeys.get("vendor_name").equals("")) {
			queryBuilder.append(" and inv.`vendor_name` like ?");
			value.add("%" + searchKeys.get("vendor_name") + "%");
		}
		if (searchKeys.get("vendor_code") != null && !searchKeys.get("vendor_code").equals("")) {
			queryBuilder.append(" and po.`vendor_code` like ?");
			value.add("%" + searchKeys.get("vendor_code") + "%");
		}
		if (searchKeys.get("circle_name") != null && !searchKeys.get("circle_name").equals("")) {
			queryBuilder.append(" and po.`circle_circle_name` like ?");
			value.add("%" + searchKeys.get("circle_name") + "%");
		}
		if (searchKeys.get("po_number") != null && !searchKeys.get("po_number").equals("")) {
			queryBuilder.append(" and po.`po_number` like ?");
			value.add("%" + searchKeys.get("po_number") + "%");
		}
		if(searchKeys.get("po_date")!=null&&!searchKeys.get("po_date").equals("")) {
			queryBuilder.append(" and date_format(po.`po_date`,'%Y-%m-%d')=date_format(?,'%Y-%m-%d')");
			value.add(searchKeys.get("po_date"));	
		}
		if (searchKeys.get("vendor_invoice_number") != null && !searchKeys.get("vendor_invoice_number").equals("")) {
			queryBuilder.append(" and inv.`vendor_invoice_number` like ?");
			value.add("%" + searchKeys.get("vendor_invoice_number") + "%");
		}
		if(searchKeys.get("vendor_invoice_date")!=null&&!searchKeys.get("vendor_invoice_date").equals("")) {
			queryBuilder.append(" and date_format(inv.`vendor_invoice_date`,'%Y-%m-%d')= date_format(?,'%Y-%m-%d')");
			value.add(searchKeys.get("vendor_invoice_date"));	
		}
		if (searchKeys.get("invoice_status") != null && !searchKeys.get("invoice_status").equals("")) {
			queryBuilder.append(" and vis.`name` like ?");
			value.add("%" + searchKeys.get("invoice_status") + "%");
		}
		return jdbc.queryForList(queryBuilder.toString(), value.toArray());

	}

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = { Exception.class, BadRequestException.class,
			UnauthoriseException.class, RuntimeException.class }, propagation = Propagation.REQUIRES_NEW)
	public Map<String, Object> saveVendorInvoiceWithCopy(Map<String, Object> request, LoginUser user,
			MultipartFile invoice) throws BadRequestException, UnauthoriseException {
		EmployeeDetails emp = new EmployeeDetails(user.getUsername());

		if (invoice.isEmpty())
			throw new BadRequestException("Please provide invoice soft copy");
		// VALIDATION
		if (request.get("remarks") != null && request.get("remarks").toString().length() > 500)
			throw new BadRequestException("Remarks lenght can not be greater then 500");
		BtWccDetails _wcc = dbWcc.getOne(Long.parseLong(request.get("wccId").toString()));

		if (!_wcc.getOwner().getEmailId().equalsIgnoreCase(user.getUsername()))
			throw new UnauthoriseException("You are not authorised");

		if (_wcc.getWccStatus().getId() != 2) {
			throw new BadRequestException(String.format("Invalid request %s is not approved", _wcc.getManualId()));
		}
		if (_wcc.isInvoiced())
			throw new BadRequestException("Invoice already genreated for this Wcc");
		ContactDetails _vendorContact = dbContact.getContactDetailsWithEmail(user.getUsername());

		if (_vendorContact.getAccount().getAccountType() == null
				|| _vendorContact.getAccount().getAccountType().getAccountTypeId() != 1) {
			throw new UnauthoriseException("Vendor can only genreate Invoice");
		}
		List<BtWccServiceAppointment> lstService = dbWccApp.getAllAppointmentWithWcc(_wcc);
		float totalAmount = 0;
		int totalNumber = 0;
		BtVendorPoDetails po = null;
		if (lstService != null && lstService.size() > 0) {
			totalNumber = lstService.size();
			for (BtWccServiceAppointment ap : lstService) {
				totalAmount += (float) ap.getPoDetails().getActivityPrice();
				po = ap.getPoDetails();
			}
		}
		BtVendorInvoiceDetails _invoice = new BtVendorInvoiceDetails();
		try {
			_invoice.setVendorInvoiceDate((sdf.parse(request.get("vendorInvoiceDate").toString())));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		_invoice.setInvoiceAmount(_wcc.getWccAmount());
		_invoice.setInvoiceStatus(new VendorInvoiceStatus(6));
		_invoice.setOwner(emp);
		_invoice.setVendorId(_vendorContact.getEmployeeCode());
		_invoice.setVendorInvoiceNumber((String) request.get("vendorInvoiceNumber"));
		_invoice.setVendorName(_vendorContact.getAccount().getName());
		_invoice.setVendorPo(po);
		_invoice.setWccDetails(_wcc);
		_invoice.setPoDate(po.getPoDate());
		if (request.get("remarks") !=null)
			{
			_invoice.setRemarks(request.get("remarks").toString());
			}
		_invoice.setInvoiceTaxAmount(_wcc.getWccTaxAmount());
		_invoice.setInvoiceTotalAmount(_wcc.getWccTotalAmount());
		List<BtCase> listCaseDetails = dbCase.getCaseWithWcc(_invoice.getWccDetails());

		// TO DO Work Here
		dbVendor.saveVendorInvoiceWithCopy(_invoice, user, invoice);

		_wcc.setInvoiced(true);
		dbWcc.updateDetails(_wcc, user);
		for (BtCase cds : listCaseDetails) {
			cds.setVendorInvoiceNumber(_invoice.getVendorInvoiceNumber());
			cds.setVendorInvoiceDate(_invoice.getVendorInvoiceDate());
			dbCase.updateDetails(cds, user);
		}
		return dbVendor.getSingleVendorInvoice(_invoice.getId());
	}

	@Override
	public Map<String, Object> updateVendorSapDetails(Map<String, Object> req, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		return null;
	}

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = { Exception.class, BadRequestException.class,
			UnauthoriseException.class, RuntimeException.class }, propagation = Propagation.REQUIRES_NEW)
	public Map<String, Object> updateVendorInvoiceWithCopy(Map<String, Object> req, LoginUser user,
			MultipartFile invoice) throws BadRequestException, UnauthoriseException {
		BtVendorInvoiceDetails _invoice = dbVendor.getDetailsWithId(Long.parseLong(req.get("id").toString()));

		if (!_invoice.getOwner().getEmailId().equalsIgnoreCase(user.getUsername()))
			throw new UnauthoriseException("You are not authorise");

		if (!_invoice.getWccDetails().getId().equals(Long.parseLong(req.get("wccId").toString())))
			throw new BadRequestException("Invalid wcc id");

		if (_invoice.getInvoiceStatus().getId() == 2 || _invoice.getInvoiceStatus().getId() == 4
				|| _invoice.getInvoiceStatus().getId() == 1)
			throw new BadRequestException("Can not edit record");

		BtWccDetails wcc = _invoice.getWccDetails();
		if (wcc.getWccAmount() != _invoice.getInvoiceAmount()) {
			_invoice.setInvoiceAmount(wcc.getWccAmount());
			_invoice.setInvoiceTaxAmount(wcc.getWccTaxAmount());
			_invoice.setInvoiceTotalAmount(wcc.getWccTotalAmount());
		}
		_invoice.setInvoiceStatus(new VendorInvoiceStatus(6));
		_invoice.setLastModifiedBy(new EmployeeDetails(user.getUsername()));
		_invoice.setLastModifiedDate(new Date());
		try {
			_invoice.setVendorInvoiceDate(sdf.parse(req.get("vendorInvoiceDate").toString()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		_invoice.setVendorInvoiceNumber(req.get("vendorInvoiceNumber").toString());
		_invoice.setRemarks(req.get("remarks").toString());
		dbVendor.updateVendorInvoiceWithCopy(_invoice, user, invoice);
		
		List<BtCase> getcase= dbCase.getCaseWithWcc(_invoice.getWccDetails());
	
		for(BtCase cds : getcase) {
			cds.setVendorInvoiceNumber(_invoice.getVendorInvoiceNumber());
			cds.setVendorInvoiceDate(_invoice.getVendorInvoiceDate());
			dbCase.updateDetails(cds, user);
		}
		
		return null;
	}

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = { Exception.class, BadRequestException.class,
			UnauthoriseException.class, RuntimeException.class }, propagation = Propagation.REQUIRES_NEW)
	public Map<String, Object> updateGrnDetails(Map<String, Object> req, LoginUser user)
			throws BadRequestException, UnauthoriseException {

		BtVendorInvoiceDetails _invoice = dbVendor
				.getDetailsWithId(Long.parseLong(req.get("vendorInvoiceId").toString()));
////////////////////////////////////////////////////////////////////////////////////////////
		
		boolean found = false; 
		
		BtToBeApproved _toBeApproved=null;
		try {
			_toBeApproved=em.createQuery("select apr from BtToBeApproved apr  where apr.ObjectName=?1 and apr.objectId=?2 and apr.status=?3",BtToBeApproved.class)
					.setParameter(1,"BtVendorInvoiceDetails")
					.setParameter(2,_invoice.getId())
					.setParameter(3,false)
					.getSingleResult();
		}catch(javax.persistence.NoResultException ex) {
			throw new BadRequestException("You are not authorised");
		}catch(javax.persistence.NonUniqueResultException ex) {
			throw new BadRequestException("You are not authorised");
		}
		
	String[] assignto=_toBeApproved.getAssignedTo().split(":",2);
	if(assignto[0].equalsIgnoreCase("USER"))
	{
	 if(assignto[1].equalsIgnoreCase(user.getUsername()))
	 {
		found=true;
	 }
	 else
	 {
		 found = false;
			throw new BadRequestException("You are not authorised");
	 }
	}
	if(assignto[0].equalsIgnoreCase("ROLE"))
	{
		java.util.List<LoginUserRoles> userRoles=em.createQuery("select usr from LoginUserRoles usr join usr.loginUser lg where usr.authority=?1",LoginUserRoles.class)
                .setParameter(1, assignto[1])
                .getResultList();
            if(userRoles!=null&&!userRoles.isEmpty()){
                for(LoginUserRoles ur:userRoles){
                	if(ur.getLoginUser().getUsername().equalsIgnoreCase(user.getUsername()))
                	{
                		found=true;
                	}
                }
		
	}}
	
	///////////////////////////////////////////////////////////////////////////////

// 		TO DO BtApproved in not Build till now.
//		try {
//			itemApp=em.createQuery("select item from ToBeApproved item where item.ObjectName=?1 and objectId=?2 and stage=?3",ToBeApproved.class)
//					.setParameter(1,"BtVendorInvoiceDetails")
//					.setParameter(2,req.get("vendorInvoiceId"))
//					.setParameter(3, 1).getResultList();
//		}
//		catch(Exception ex) {
//			throw new UnauthoriseException("You are not authorised");
//		}

		if (!found) {
			throw new UnauthoriseException("You are not authorised");
		}
		if (_invoice.getInvoiceStatus().getId() == 1 || _invoice.getInvoiceStatus().getId() == 2) {

			SimpleDateFormat frm=new SimpleDateFormat("yyyy-MM-dd");

			BtVendorInvoiceGrn grn = null;
			try {
				grn = em.createQuery("select grn from BtVendorInvoiceGrn grn where grn=?1", BtVendorInvoiceGrn.class)
						.setParameter(1, new BtVendorInvoiceGrn(_invoice)).getSingleResult();
			} catch (NoResultException ex) {
				grn = new BtVendorInvoiceGrn();
				grn.setInvoice(_invoice);
			} catch (NonUniqueResultException ex) {
				throw new BadRequestException("Multiple GRN entry found");
			}
			grn.setEntrySheetNumber(req.get("entrySheetNumber").toString());
//			String grnDate=frm.format(req.get("grnDate").toString());
			grn.setGrnDate(req.get("grnDate").toString());
			grn.setGrnNumber(req.get("grnNumber").toString());
			grn.setInvoice(_invoice);
			grn.setOwner(new EmployeeDetails(user.getUsername()));
			grn.setInternalServiceCode(new InternalServiceCode((Integer) req.get("internalServiceCode")));
			em.persist(grn);
		} else {
			throw new UnauthoriseException("You are not authorise");
		}
		return null;

	}

	@Override
	public Page<Map<String, Object>>  getData(LoginUser user, Pageable page, Integer filtername)
			throws BadRequestException, UnauthoriseException {
		if(dbProfile.hasObjectAccess(user.getUsername(), BtVendorInvoiceDetails.class, UserProfileRepository.VIEW_ALL)) {
			if(filtername!=null&&filtername!=0)
				return dbVendor.getVendorInvoiceList(filtername,page);
			return dbVendor.getPage(page);
		}
			if(dbProfile.hasObjectAccess(user.getUsername(), BtVendorInvoiceDetails.class, UserProfileRepository.VIEW_ALL)||dbProfile.hasObjectAccess(user.getUsername(), BtVendorInvoiceDetails.class, UserProfileRepository.VIEW)) {
				List<String> userList=dbProfile.getListOfUserAndChildByUser(user.getUsername());
				if(filtername!=null&&filtername!=0) {
					return dbVendor.getVendorInvoiceListByOwners(userList,filtername,page);}
				return dbVendor.getVendorInvoiceListByOwners(userList,page);
				
			}
		return Page.empty();
	}

	@Override
	public List<String> getInvoiceStatusList() {
		return dbVendor.getInvoiceStatusList();
		 
	}

	@Override
	public List<Map<String, Object>> getPoCategory() {
		// TODO Auto-generated method stub
		return dbPo.getPoCategory();
	}

	@Override
	public List<Map<String, Object>> getAllPoType() {
		// TODO Auto-generated method stub
		return dbPo.getAllPoType();
	}

	@Override
	public void submitForApproval(LoginUser user, Long id) throws BadRequestException, UnauthoriseException {
		Optional<BtVendorInvoiceDetails> optionalBtVendorInvocie=dbVendor.findById(id);
		if(!optionalBtVendorInvocie.isPresent())
			throw new BadRequestException("Invalid Vendor Invoice id");
		BtVendorInvoiceDetails invoice=optionalBtVendorInvocie.get();

		if(!invoice.getOwner().getEmailId().equalsIgnoreCase(user.getUsername()))
			throw new UnauthoriseException("You are not authorised");

		if(invoice.getInvoiceStatus().getId().equals(6)||invoice.getInvoiceStatus().getId().equals(5)||invoice.getInvoiceStatus().getId().equals(3)){
			invoice.setInvoiceStatus(new VendorInvoiceStatus(1));
			dbVendor.updateDetails(invoice, user);
		}
		else
			throw new BadRequestException("Already submitted for approval");
	}


}