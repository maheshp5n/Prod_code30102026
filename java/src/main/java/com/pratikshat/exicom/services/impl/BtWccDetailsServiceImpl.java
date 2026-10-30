
package com.pratikshat.exicom.services.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.BtServiceAppointment;
import com.pratikshat.exicom.entity.BtVendorPoDetails;
import com.pratikshat.exicom.entity.BtWccDetails;
import com.pratikshat.exicom.entity.BtWccServiceAppointment;
import com.pratikshat.exicom.entity.CaseReasonDetails;
import com.pratikshat.exicom.entity.CaseTypes;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.ServiceRecource;
import com.pratikshat.exicom.entity.WccStatus;
import com.pratikshat.exicom.entity.WccTypes;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.WccDetailsRequest;
import com.pratikshat.exicom.model.request.WccServiceAppointmentRequest;
import com.pratikshat.exicom.repository.BtServiceAppointmentRepository;
import com.pratikshat.exicom.repository.BtVendorPoDetailsRepository;
import com.pratikshat.exicom.repository.BtWccDetailsRepository;
import com.pratikshat.exicom.repository.BtWccDetailsRepositoryCustom;
import com.pratikshat.exicom.repository.BtWccServiceAppointmentRepository;
import com.pratikshat.exicom.repository.CaseReasonDetailsRepository;
import com.pratikshat.exicom.repository.CaseTypesRepository;
import com.pratikshat.exicom.repository.ServiceRecourseRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.repository.VendorPoDetailsRepository;
import com.pratikshat.exicom.services.BtWccDetailsService;

@Service
public class BtWccDetailsServiceImpl implements BtWccDetailsService {

	@Autowired
	UserProfileRepository dbProfile;
	@Autowired
	BtWccDetailsRepository dbWcc;
	@PersistenceContext
	EntityManager em;
	@Autowired
	ServiceRecourseRepository dbService;
	@Autowired
	BtServiceAppointmentRepository dbAppointment;
	@Autowired
	VendorPoDetailsRepository dbVendor;
	@Autowired
	CaseTypesRepository dbCaseType;
	@Autowired
	CaseReasonDetailsRepository dbReason;
	@Autowired
	JdbcTemplate jdbc;
	@Autowired
	BtVendorPoDetailsRepository dbPoDetails;
	@Autowired
	BtWccServiceAppointmentRepository dbWccApp;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public Map<String, Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException {

		boolean view = dbProfile.hasObjectAccess(user.getUsername(), BtWccDetails.class, UserProfileRepository.VIEW);
		boolean viewAll = dbProfile.hasObjectAccess(user.getUsername(), BtWccDetails.class,
				UserProfileRepository.VIEW_ALL);
		if (view || viewAll) {
			long _id = Long.parseLong(id.toString());
			return dbWcc.getSingleWcc(_id);
		} else {
			throw new UnauthoriseException("You are Not Authorized");
		}
	}

	@Override
	public Page<Map<String, Object>> getPage(Pageable page, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		boolean view = dbProfile.hasObjectAccess(user.getUsername(), BtWccDetails.class, UserProfileRepository.VIEW);
		boolean viewAll = dbProfile.hasObjectAccess(user.getUsername(), BtWccDetails.class,
				UserProfileRepository.VIEW_ALL);
		if (view || viewAll) {
			return dbWcc.getPage(page);
		} else {
			throw new UnauthoriseException("You are Not Authorized");
		}
	}
	
	@Override
	@Transactional(rollbackFor ={
			BadRequestException.class, UnauthoriseException.class, Exception.class	}, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
	public Map<String, Object> submitForapproval(long wccId, LoginUser user ) throws BadRequestException, UnauthoriseException
	{
	
		dbWcc.submitforApproval(wccId, user);
		return null;
		
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, Exception.class })
	public Map<String, Object> saveObject(WccDetailsRequest _request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		boolean create = dbProfile.hasObjectAccess(user.getUsername(), BtWccDetails.class,
				UserProfileRepository.CREATE);
		if (create) {
			List<WccDetailsRequest> lstReq = new ArrayList<WccDetailsRequest>();
			for (WccServiceAppointmentRequest r : _request.getSaIds()) {
				BtVendorPoDetails tempPo = null;
				try {
					tempPo = dbPoDetails.getPoDetailsWithID(r.getPoId());
					boolean found = false;
					for (int i = 0; i < lstReq.size(); i++) {
						if (lstReq.get(i).getPonumber().equalsIgnoreCase(tempPo.getPoNumber())) {
							found = true;
							if (lstReq.get(i).getSaIds() == null) {
								List<WccServiceAppointmentRequest> t = new ArrayList<WccServiceAppointmentRequest>();
								lstReq.get(i).setSaIds(t);
							}
							lstReq.get(i).getSaIds().add(r);
						}
					}
					if (!found) {
						WccDetailsRequest tr = new WccDetailsRequest();
						tr.setWccTypeId(_request.getWccTypeId());
						tr.setStartDate(_request.getStartDate());
						tr.setToDate(_request.getToDate());
						tr.setPonumber(tempPo.getPoNumber());
						List<WccServiceAppointmentRequest> t = new ArrayList<WccServiceAppointmentRequest>();
						t.add(r);
						tr.setSaIds(t);
						lstReq.add(tr);
					}
				} catch (Exception ex) {
					throw new BadRequestException("Invalid PO Id");
				}
			}
			BtWccDetails _wcc = new BtWccDetails();
			for (WccDetailsRequest request : lstReq) {
				EmployeeDetails emp = new EmployeeDetails(user.getUsername());
				ServiceRecource res = dbService.getServiceResourceWithUser(emp);
				_wcc.setFromDate(request.getStartDate());
				_wcc.setOwner(emp);
				_wcc.setServiceCenter(res.getContact().getLegalEntity());
				_wcc.setSynked(false);
				_wcc.setWccStatus(new WccStatus(4));
				_wcc.setWccType(new WccTypes(request.getWccTypeId()));
				_wcc.setToDate(request.getToDate());
				_wcc.setWccAmount(0);
				_wcc.setWccTaxAmount(0);
				_wcc.setWccTotalAmount(0);
				dbWcc.saveDetails(_wcc, user);
				for (WccServiceAppointmentRequest r : request.getSaIds()) {
					BtServiceAppointment apt = dbAppointment.getServiceAppointment(r.getSaId());
					BtVendorPoDetails poDetails = null;
					if (!apt.getServiceResource().getUser().getEmailId().equalsIgnoreCase(user.getUsername())) {
						throw new UnauthoriseException("You are not authorised");
					}
					if (apt.isWcc())
						throw new BadRequestException(String.format("Service Appointment %s already added in Wcc",
								((BtServiceAppointment) apt).getManualId()));
					poDetails = dbPoDetails.getPoDetailsWithID(r.getPoId());
					if (poDetails == null) {
						throw new BadRequestException("No result Found With this id");
					}
					BtWccServiceAppointment s = new BtWccServiceAppointment();
					s.setServiceAppointment(apt);
					poDetails.setRemainingQuantity(poDetails.getRemainingQuantity() - 1);
					em.persist(poDetails);
					s.setPoDetails(poDetails);
					s.setAmount(poDetails.getActivityPrice());
					s.setTaxRate(poDetails.getTaxRate());
					s.setTaxAmount(s.getAmount() * s.getTaxRate() / 100);
					s.setTotalAmount(s.getAmount() + s.getTaxAmount());
					
					if(poDetails.getRemainingQuantity()<0)
						throw new BadRequestException("po limit exceeded");
					s.setWcc(_wcc);
					em.persist(s);
					apt.setWcc(true);
					apt.setWccId("" + _wcc.getId());
					apt.setWccManualId(_wcc.getManualId());
					dbAppointment.updateDetails(apt, user);

					_wcc.setWccAmount(_wcc.getWccAmount() + s.getAmount());
					_wcc.setWccTaxAmount(_wcc.getWccTaxAmount() + s.getTaxAmount());
					_wcc.setWccTotalAmount(_wcc.getWccTotalAmount() + s.getTotalAmount());
				}
				dbWcc.updateDetails(_wcc, user);
//Insert
				request.setId(_wcc.getId());
			}
			return dbWcc.getSingleWcc(_wcc.getId());
		} else {
			throw new UnauthoriseException("you are not authorized");
		}
	}

	@Override
	@Transactional
	public Map<String, Object> updateObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		BtWccDetails _wcc = dbWcc.getBtWccDetailsWithID(Long.parseLong(request.get("id").toString()));

		if (!_wcc.getOwner().getEmailId().equalsIgnoreCase(user.getUsername()))
			throw new UnauthoriseException("You are not authorise");

		if (_wcc.getWccStatus().getId() == 1 || _wcc.getWccStatus().getId() == 2)
			throw new BadRequestException("Can not edit WCC " + _wcc.getManualId());
		List<BtWccServiceAppointment> _pSa = dbWccApp.getAllAppointmentWithWcc(_wcc);
		if (_pSa != null && _pSa.size() > 0) {
			for (BtWccServiceAppointment s : _pSa) {
				BtServiceAppointment apt = s.getServiceAppointment();
				apt.setWcc(false);
				apt.setWccId("");
				apt.setWccManualId("");
				dbAppointment.updateDetails(apt, user);
				BtVendorPoDetails po = s.getPoDetails();
				po.setRemainingQuantity(po.getRemainingQuantity() + 1);
				em.persist(po);
			}
			dbWccApp.deleteAppointmentWithWcc(_wcc);
		}
		EmployeeDetails emp = new EmployeeDetails(user.getUsername());
		ServiceRecource res = dbService.getServiceResourceWithUser(emp);
		CaseTypes ctypes = null;
		CaseReasonDetails cres = null;
		try {
			List<CaseTypes> lst = dbCaseType.getCaseUsingWccTypeId((int) request.get("wccTypeId"));
			if (lst == null || lst.size() == 0)
				throw new BadRequestException("Case types not found with Wcc type");
			ctypes = lst.get(0);
		} catch (Exception ex) {
			throw new BadRequestException("Case types not found with Wcc type");
		}
		try {
			List<CaseReasonDetails> lst = dbReason.getCaseReasonDetailsUsingCaseType(ctypes);
			if (lst == null || lst.size() == 0)
				throw new BadRequestException("Case Reason not found with Wcc type");
			cres = lst.get(0);
		} catch (Exception ex) {
			throw new BadRequestException("Case Reason not found with Wcc type");
		}
		_wcc.setCaseReason(cres);
		_wcc.setCaseType(ctypes);
		try {
			_wcc.setFromDate(sdf.parse(request.get("startDate").toString()));
			_wcc.setToDate(sdf.parse(request.get("toDate").toString()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		_wcc.setServiceCenter(res.getContact().getLegalEntity());
		_wcc.setSynked(false);
		_wcc.setWccStatus(new WccStatus(4));
		_wcc.setWccType(new WccTypes((int) request.get("wccTypeId")));
		_wcc.setWccAmount(0);
		_wcc.setWccTaxAmount(0);
		_wcc.setWccTotalAmount(0);

		dbWcc.updateDetails(_wcc, user);
		List<Map<String, Object>> re = (List<Map<String, Object>>) request.get("saIds");
		for (int i = 0; i < re.size(); i++) {
			BtServiceAppointment apt = dbAppointment
					.getServiceAppointment(Long.parseLong(re.get(i).get("saId").toString()));

			if (!apt.getServiceResource().getUser().getEmailId().equalsIgnoreCase(user.getUsername())) {
				throw new UnauthoriseException("You are not authorised");
			}
			if (apt.isWcc())
				throw new BadRequestException(
						String.format("Service Appointment %s already added in Wcc", apt.getManualId()));
			BtVendorPoDetails poDetails = dbPoDetails
					.getPoDetailsWithID(Long.parseLong(re.get(i).get("poId").toString()));
			BtWccServiceAppointment s = new BtWccServiceAppointment();
			s.setServiceAppointment(apt);
			poDetails.setRemainingQuantity(poDetails.getRemainingQuantity() - 1);
			em.persist(poDetails);
			s.setWcc(_wcc);
			s.setAmount(poDetails.getActivityPrice());
			s.setTaxRate(poDetails.getTaxRate());
			s.setTaxAmount(s.getAmount() * s.getTaxRate() / 100);
			s.setTotalAmount(s.getAmount() + s.getTaxAmount());
			s.setPoDetails(poDetails);
			em.persist(s);
			apt.setWcc(true);
			apt.setWccId("" + _wcc.getId());
			apt.setWccManualId(_wcc.getManualId());
			dbAppointment.updateDetails(apt, user);

			_wcc.setWccAmount(_wcc.getWccAmount() + s.getAmount());
			_wcc.setWccTaxAmount(_wcc.getWccTaxAmount() + s.getTaxAmount());
			_wcc.setWccTotalAmount(_wcc.getWccTotalAmount() + s.getTotalAmount());
		}
		dbWcc.updateDetails(_wcc, user);
		return dbWcc.getSingleWcc(_wcc.getId());
	}

	@Override
	public List<Map<String, Object>> searchForList(String search, LoginUser user)
			throws BadRequestException, UnauthoriseException {

		return null;
	}

	@Override
	public List<Map<String, Object>> advanceSearchs(Map<String, Object> request, List<String> userLists)
			throws BadRequestException, UnauthoriseException {
		List<Object> value = new ArrayList<>();
		String _query = "SELECT	wcc.`id` AS id,wcc.`manual_id` AS manulId,wcc.invoiced AS invoiced,wcc.`wcc_amount` AS wccAmount,wcc.`wcc_tax_amount` AS wccTaxAmount,\r\n"
				+ "				wcc.`wcc_total_amount` AS wccTotalAmount,wcc.`circle_circle_name` AS circleName,\r\n"
				+ "				act.`name` AS vendorName,act.`sap_customer_number` AS vendorSapNumber,\r\n"
				+ "				cod.`first_name` AS vendorUserFirstName,cod.`last_name` AS vendorUserLastName,\r\n"
				+ "				cod.`middle_name` AS vendorUserMiddleName,cod.`employee_code` AS vendorUserCode,\r\n"
				+ "				cod.`contact_id` AS vendorContactId,\r\n"
				+ "				lg.`legal_entity_name` AS serviceCenter,ty.`wcc_type_name` AS wccType,\r\n"
				+ "				ct.`case_types_name` AS caseType,cr.`case_reason_details_name` AS caseReasonDetails,\r\n"
				+ "				st.`name` AS wccStatus,wcc.`created_date` AS createdDate,cby.`email_id` AS createdByEmailId, cby.`first_name` AS createdByFirstName,\r\n"
				+ "				cby.`middle_name` AS createdByMiddleName,cby.`last_name`AS createdByLastName,\r\n"
				+ "				mby.`email_id` AS modifiedByEmailId, mby.`first_name` AS modifiedByFirstName,\r\n"
				+ "				mby.`middle_name` AS modifiedByMiddleName,mby.`last_name`AS modifiedByLastName,wcc.to_date AS toDate,wcc.from_date AS fromDate,\r\n"
				+ "				wcc.wcc_type_id AS wccTypeId,\r\n"
				+ "				(SELECT approved_date FROM to_be_approved WHERE `object_name`='BtWccDetails' AND `object_id`=wcc.`id` ORDER BY id DESC LIMIT 1) AS lastActionDate\r\n"
				+ "				\r\n"
				+ "			FROM `bt_wcc_details` wcc\r\n"
				+ "			JOIN `wcc_status` st ON st.`id`=wcc.`wcc_status_id`\r\n"
				+ "			JOIN `wcc_types` ty ON ty.`id`=wcc.`wcc_type_id`\r\n"
				+ "			JOIN `legal_entity` lg ON lg.`legal_entity_id`=wcc.`service_center_legal_entity_id`\r\n"
				+ "			LEFT JOIN `case_reason_details` cr ON cr.`reason_id` = wcc.`case_reason_reason_id`\r\n"
				+ "			LEFT JOIN `case_types` ct ON ct.`case_type_id` =wcc.`case_type_case_type_id`\r\n"
				+ "			JOIN `service_recource` sr ON sr.`user_email_id`= wcc.`owner_email_id`\r\n"
				+ "			JOIN `contact_details` cod ON sr.`contact_contact_id`=cod.`contact_id`\r\n"
				+ "			JOIN `accounts` act ON cod.`account_id`=act.`sap_customer_number`\r\n"
				+ "			JOIN `employee_details` cby ON cby.`email_id`= wcc.`created_by_email_id`\r\n"
				+ "			JOIN `employee_details` mby ON mby.`email_id`=wcc.`last_modified_by_email_id`\r\n"
				+ "			LEFT JOIN `to_be_approved` tobe ON tobe.`object_name`='BtWccDetails' AND tobe.`object_id`=wcc.`id` AND tobe.`action`='Approved' AND tobe.`status`=1" + " WHERE 1=1";
		StringBuilder queryStringBuilder = new StringBuilder();
		queryStringBuilder.append(_query);
		if (request.get("manualId") != null && !request.get("manualId").equals("")) {
			queryStringBuilder.append(" and wcc.`manual_id` like ?");
			value.add("%" + request.get("manualId") + "%");
		}
		if (request.get("vendorName") != null && !request.get("vendorName").equals("")) {
			queryStringBuilder.append(" and act.`name` like ?");
			value.add("%" + request.get("vendorName") + "%");
		}

		if (request.get("vendorCode") != null && !request.get("vendorCode").equals("")) {
			queryStringBuilder.append(" and act.`sap_customer_number` like ?");
			value.add("%" + request.get("vendorCode") + "%");
		}

		if (request.get("serviceCenter") != null && !request.get("serviceCenter").equals("")) {
			queryStringBuilder.append(" and lg.`legal_entity_name` like ?");
			value.add("%" + request.get("serviceCenter") + "%");
		}

		if (request.get("wccType") != null && !request.get("wccType").equals("")) {
			queryStringBuilder.append(" and ty.`wcc_type_name` like ?");
			value.add("%" + request.get("wccType") + "%");
		}

		if (request.get("isInvoiced") != null && !request.get("isInvoiced").equals("")) {
			if (request.get("isInvoiced").toString().equalsIgnoreCase("true")) {
				queryStringBuilder.append(" and wcc.invoiced=true");
			} else {
				queryStringBuilder.append(" and wcc.invoiced=false");
			}
		}
		if(userLists!=null) {
			queryStringBuilder.append(" AND wcc.`owner_email_id` IN ");
			StringBuilder em=new StringBuilder();
			int i=0;
			for(String usr:userLists) {
				if(i!=0)
					em.append(",");
				em.append("'"+usr+"'");
				i++;
			}
			queryStringBuilder.append("("+em.toString()+")");
		}
		return jdbc.queryForList(queryStringBuilder.toString(), value.toArray());
	}

	@Override
	public Map<String, Object> saveObject(Map<String, Object> assets, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		return null;
	}

	@Override
	public List<Map<String, Object>> advanceSearch(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		return null;
	}

	@Override
	public List<Map<String, Object>> getPendingToInvoiceWccByUser(String userEmailId) {
		return dbWcc.getPendingToInvoiceWccByUser(userEmailId);
		
	}

}