package com.pratikshat.exicom.services.impl;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.config.FileStorageProperties;
import com.pratikshat.exicom.entity.BtExpenseManagement;
import com.pratikshat.exicom.entity.BtSiteDetails;
import com.pratikshat.exicom.entity.BusinessDomain;
import com.pratikshat.exicom.entity.ContactDetails;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.ExpenseManagement;
import com.pratikshat.exicom.entity.ExpenseManagementBusinessArea;
import com.pratikshat.exicom.entity.ExpenseManagementBusinessPlace;
import com.pratikshat.exicom.entity.ExpenseManagementCostCenter;
import com.pratikshat.exicom.entity.ExpenseManagementExpensePurpose;
import com.pratikshat.exicom.entity.ExpenseManagementExpenseType;
import com.pratikshat.exicom.entity.ExpenseManagementStatus;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.BtExpenseManagementRequest;
import com.pratikshat.exicom.model.request.ExpenseAccountsOnlyRequest;
import com.pratikshat.exicom.model.response.BtExpenseManagementResponse;
import com.pratikshat.exicom.model.response.ExpenseManagementResponse;
import com.pratikshat.exicom.model.response.GlCodeResponse;
import com.pratikshat.exicom.repository.BtExpenseManagementCustomRepositoryImpl;
import com.pratikshat.exicom.repository.BtExpenseManagementRepository;
import com.pratikshat.exicom.repository.ExpenseManagementBusinessAreaRepository;
import com.pratikshat.exicom.repository.ExpenseManagementBusinessPlaceRepository;
import com.pratikshat.exicom.repository.ExpenseManagementCostCenterRepository;
import com.pratikshat.exicom.repository.ExpenseManagementExpensePurposeRepository;
import com.pratikshat.exicom.repository.ExpenseManagementExpenseTypeRepository;
import com.pratikshat.exicom.repository.ExpenseManagementRecentVisitRepository;
import com.pratikshat.exicom.repository.ExpenseManagementRepository;
import com.pratikshat.exicom.repository.ExpenseManagementStatusRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtExpenceService;
import com.pratikshat.exicom.utils.BtExpensePDFGeneration;
import com.pratikshat.exicom.utils.ExpensePDFGeneration;

@Service
public class BtExpenceServiceImpl implements BtExpenceService {

	@Autowired
	BtExpenseManagementCustomRepositoryImpl customRepositoryImpl;
	@Autowired
	ExpenseManagementExpensePurposeRepository btExpensePurposeDB;
	@Autowired
	ExpenseManagementBusinessAreaRepository btBusinessAreaDB;
	@Autowired
	ExpenseManagementBusinessPlaceRepository btBusinessPlaceDB;
	@Autowired
	ExpenseManagementCostCenterRepository btCostCenterDB;
	@Autowired
	ExpenseManagementExpenseTypeRepository btExpenseTypeDb;
	@Autowired
	ExpenseManagementRecentVisitRepository btRecentVisitDB;
	@Autowired
	BtExpenseManagementRepository btExpenseManagementDB;
	@Autowired
	com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	@Autowired
	ExpenseManagementStatusRepository btManagementStatusDB;
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	FileStorageProperties fileStorageProperties;
	
	@Autowired
    JdbcTemplate jdbc;
	
	SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public Map<String, Object> getSingle(Object id, LoginUser user)  {
		
		Map<String, Object> map=btExpenseManagementDB.getByid(Long.valueOf(id.toString()));
		return map;
		
	}

	@Override
	public Page<Map<String, Object>> getPage(Pageable page, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		boolean view = dbProfile.hasObjectAccess(user.getUsername(), BtSiteDetails.class, UserProfileRepository.VIEW);
		boolean viewAll = dbProfile.hasObjectAccess(user.getUsername(), BtExpenseManagement.class,
				UserProfileRepository.VIEW_ALL);
		if (view || viewAll) {
		Page<Map<String, Object>> map=btExpenseManagementDB.getAll(page);
		return map;
		}
		else
		{
			throw new UnauthoriseException("You are not authorised");	
		}
	}
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = { Exception.class,BadRequestException.class,UnauthoriseException.class,
			RuntimeException.class }, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Map<String, Object> saveObject(Map<String, Object> map, LoginUser user) throws BadRequestException, UnauthoriseException
			 {

	
	if(map.get("expenseTypeId")==null || map.get("expenseTypeId").toString().trim().equalsIgnoreCase(""))
	{throw new com.pratikshat.exicom.exception.BadRequestException("Expense Type Id not found");}
	if(map.get("expensePurposeId")==null || map.get("expensePurposeId").toString().trim().equalsIgnoreCase(""))
	{throw new com.pratikshat.exicom.exception.BadRequestException("Expense Purpose Id not found");}
	if(map.get("costCenterId")==null || map.get("costCenterId").toString().trim().equalsIgnoreCase(""))
	{throw new com.pratikshat.exicom.exception.BadRequestException("Cost CenterId ID not found");}
		
	BtExpenseManagementRequest expenseManagementRequest=new BtExpenseManagementRequest();
		
		BtExpenseManagementRequest	btExpenseManagementReq=null;
		
		if(map.get("expenseTypeId").toString()!=null && !map.get("expenseTypeId").toString().equalsIgnoreCase("null") && (!map.get("expenseTypeId").toString().trim().equalsIgnoreCase(""))) {
			expenseManagementRequest.setExpenseTypeId(Integer.valueOf(String.valueOf(map.get("expenseTypeId"))));}
			if(map.get("expensePurposeId").toString()!=null && !map.get("expensePurposeId").toString().equalsIgnoreCase("null") && (!map.get("expensePurposeId").toString().trim().equalsIgnoreCase(""))) {
			expenseManagementRequest.setExpensePurposeId(Integer.valueOf(String.valueOf(map.get("expensePurposeId"))));}
			if(map.get("costCenterId").toString()!=null && !map.get("costCenterId").toString().equalsIgnoreCase("null") && (!map.get("costCenterId").toString().trim().equalsIgnoreCase(""))) {
			expenseManagementRequest.setCostCenterId(Integer.valueOf(map.get("costCenterId").toString()));}
			if(map.get("purposeOfVisit").toString()!=null && !map.get("purposeOfVisit").toString().equalsIgnoreCase("null") && (!map.get("purposeOfVisit").toString().trim().equalsIgnoreCase(""))) {
			expenseManagementRequest.setPurposeOfVisit(map.get("purposeOfVisit").toString());}
			if(map.get("remark").toString()!=null && !map.get("remark").toString().equalsIgnoreCase("null") && (!map.get("remark").toString().trim().equalsIgnoreCase(""))) {
			expenseManagementRequest.setRemark(String.valueOf(map.get("remark")));}
			if(map.get("narration").toString()!=null && !map.get("narration").toString().equalsIgnoreCase("null") && (!map.get("narration").toString().trim().equalsIgnoreCase(""))) {
			expenseManagementRequest.setNarration(map.get("narration").toString());}
			if(map.get("businessAreaId").toString()!=null && !map.get("businessAreaId").toString().equalsIgnoreCase("null") && (!map.get("businessAreaId").toString().trim().equalsIgnoreCase(""))) {
			expenseManagementRequest.setBusinessAreaId(Integer.valueOf(map.get("businessAreaId").toString()));}
			if(map.get("businessPlaceId").toString()!=null && !map.get("businessPlaceId").toString().equalsIgnoreCase("null") && (!map.get("businessPlaceId").toString().trim().equalsIgnoreCase(""))) {
			expenseManagementRequest.setBusinessPlaceId(Integer.valueOf(map.get("businessPlaceId").toString()));}
			expenseManagementRequest.setBusinessDomainID(1);
			
			
			customRepositoryImpl.checkFroinKey(expenseManagementRequest.getExpenseTypeId(),expenseManagementRequest.getCostCenterId(),expenseManagementRequest.getExpensePurposeId(),expenseManagementRequest.getBusinessDomainID());
			
			customRepositoryImpl.checkFroinKeys(expenseManagementRequest.getBusinessAreaId(),expenseManagementRequest.getBusinessPlaceId());
			
			btExpenseManagementReq = btExpenseManagementDB.saveDetails(expenseManagementRequest, user);
			
			System.out.println("data inserted");
		
		 // TODO Auto-generated method stub
		return btExpenseManagementDB.getByid(btExpenseManagementReq.getId());
	}
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = { Exception.class,BadRequestException.class,UnauthoriseException.class,
			RuntimeException.class }, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Map<String, Object> updateObject(Map<String, Object> map, LoginUser user) throws BadRequestException, UnauthoriseException {
		
	if(map.get("id")==null || map.get("id").toString().trim().equalsIgnoreCase(""))
	{throw new com.pratikshat.exicom.exception.BadRequestException("Id not found");}
	
	
	BtExpenseManagementRequest req=null;
	BtExpenseManagementRequest expenseManagementRequest=new BtExpenseManagementRequest();
	System.out.print(map.get("narration")+" = narration test");
		if(map.get("id").toString()!=null && !map.get("id").toString().equalsIgnoreCase("null") && (!map.get("id").toString().trim().equalsIgnoreCase(""))) {
		expenseManagementRequest.setId(Long.parseLong(map.get("id").toString()));}
		if(map.get("expenseTypeId")!=null && !map.get("expenseTypeId").toString().equalsIgnoreCase("null") && (!map.get("expenseTypeId").toString().trim().equalsIgnoreCase(""))) {
		expenseManagementRequest.setExpenseTypeId(Integer.valueOf(String.valueOf(map.get("expenseTypeId"))));}
		if(map.get("expensePurposeId")!=null && !map.get("expensePurposeId").toString().equalsIgnoreCase("null") && (!map.get("expensePurposeId").toString().trim().equalsIgnoreCase(""))) {
		expenseManagementRequest.setExpensePurposeId(Integer.valueOf(String.valueOf(map.get("expensePurposeId"))));}
		if(map.get("costCenterId")!=null && !map.get("costCenterId").toString().equalsIgnoreCase("null") && (!map.get("costCenterId").toString().trim().equalsIgnoreCase(""))) {
		expenseManagementRequest.setCostCenterId(Integer.valueOf(map.get("costCenterId").toString()));}
		if(map.get("purposeOfVisit")!=null && !map.get("purposeOfVisit").toString().equalsIgnoreCase("null") && (!map.get("purposeOfVisit").toString().trim().equalsIgnoreCase(""))) {
		expenseManagementRequest.setPurposeOfVisit(map.get("purposeOfVisit").toString());}
		if(map.get("remark")!=null && !map.get("remark").toString().equalsIgnoreCase("null") && (!map.get("remark").toString().trim().equalsIgnoreCase(""))) {
		expenseManagementRequest.setRemark(String.valueOf(map.get("remark")));}

		if(map.get("narration")!=null && !map.get("narration").toString().equalsIgnoreCase("null") && (!map.get("narration").toString().trim().equalsIgnoreCase(""))) {
		expenseManagementRequest.setNarration(map.get("narration").toString());}
		if(map.get("businessAreaId")!=null && !map.get("businessAreaId").toString().equalsIgnoreCase("null") && (!map.get("businessAreaId").toString().trim().equalsIgnoreCase(""))) {
		expenseManagementRequest.setBusinessAreaId(Integer.valueOf(map.get("businessAreaId").toString()));}
		if(map.get("businessPlaceId")!=null && !map.get("businessPlaceId").toString().equalsIgnoreCase("null") && (!map.get("businessPlaceId").toString().trim().equalsIgnoreCase(""))) {
		expenseManagementRequest.setBusinessPlaceId(Integer.valueOf(map.get("businessPlaceId").toString()));}
		expenseManagementRequest.setBusinessDomainID(1);
		
	customRepositoryImpl.checkFroinKey(expenseManagementRequest.getExpenseTypeId(),expenseManagementRequest.getCostCenterId(),expenseManagementRequest.getExpensePurposeId(),expenseManagementRequest.getBusinessDomainID());
	customRepositoryImpl.checkFroinKeys(expenseManagementRequest.getBusinessAreaId(),expenseManagementRequest.getBusinessPlaceId());
	
		
		req=btExpenseManagementDB.updateDetails(expenseManagementRequest,user);
		System.out.println("data updated");
	
		return btExpenseManagementDB.getByid(req.getId());
		}
	

	@Override
	public List<Map<String, Object>> searchForList(String searchKey, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if(searchKey!=null) {
		List<Map<String,Object>> list=btExpenseManagementDB.getManualId(searchKey);
		System.out.println("list map => "+list);
		return list;
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> advanceSearch(Map<String, Object> filterData, LoginUser user, List<String> users)
			throws BadRequestException, UnauthoriseException {
		List<Object> value = new ArrayList<>();
		StringBuilder queryStringBuilder=new StringBuilder();
		System.out.println(filterData.values());
		queryStringBuilder.append("SELECT em.`id`,em.`manual_id` AS `manualId`,cnt.`contact_id` AS `contactId`,CONCAT(IFNULL(cnt.`first_name`,' '),IFNULL(cnt.`middle_name`,' '),IFNULL(cnt.`last_name`,' ')) AS `contactName`,cnt.`employee_code` AS `employeeCode`,em.`document_date` AS `documentDate`,bd.`business_domain_id` AS `businessDomainId`,bd.`business_domain_name` AS `businessDomainName`,ep.`expense_purpose_id` AS `expensePurposeId`,ep.`expense_purpose_name` AS `expensePurposeName`,et.`expense_type_id` AS `expenseTypeId`,et.`expense_type_name` AS `expenseTypeName`,cc.`cost_center_id` AS `costCenterId`,cc.`cost_center_name` AS `costCenterName`,em.`purpose_of_visit` AS `purposeOfVisit`,es.`status_id` AS `statusId`,es.`status_name` AS `statusName`,em.`remark`,em.`narration`,ba.`business_area_id` AS `businessAreaId`,ba.`business_area_name` AS `businessAreaName`,bp.`business_place_id` AS `businessPlaceId`,bp.`business_place_name` AS `businessPlaceName`,em.`is_post` AS `isPost`,em.`is_expense_synced` AS `isExpenseSynced`,em.`advance_amount` AS `advanceAmount`,em.`visible_to_finance` AS `visibleToFinance`,em.`total_tour_amount` AS `totalTourAmount`,em.`total_lodging_amount` AS `totalLodgingAmount`,em.`total_boarding_amount` AS `totalBoardingAmount`,em.`total_bp_amount` AS `totalBpAmount`,em.`total_local_conveyance_amount` AS `totalLocalConveyanceAmount`,em.`total_other_charges_amount` AS `totalOtherChargesAmount`,em.`total_expense` AS `totalExpense`,em.`total_tour_amount_company_paid` AS `totalTourAmountCompanyPaid`,em.`total_lodging_company_paid` AS `totalLodgingCompanyPaid`,em.`total_company_paid` AS `totalCompanyPaid`,em.`net_amount_to_pay` AS `netAmountToPay`,em.`total_tour_days` AS `totalTourDays`,em.`eligible_tour_days` AS `eligibleTourDays`,em.`total_boarding_days` AS `totalBoardingDays`,em.`total_lodging_days` AS `totalLodgingDays`,cby.`email_id` AS `createdByEmailId`,CONCAT(IFNULL(cby.`first_name`,' '),IFNULL(cby.`middle_name`,' '),IFNULL(cby.`last_name`,' '))AS `createdByName`,em.`created_date` AS `createdDate`,em.`last_modified_by_email_Id` AS `modifiedByEmailId`,CONCAT(IFNULL(mody.`first_name`,' '),IFNULL(mody.`middle_name`,' '),IFNULL(mody.`last_name`,''))AS `modifiedByName`,em.`last_modified_date` AS `lastModifiedDate`,cby.`email_id` AS `ownerEmailId` ,CONCAT(IFNULL(cby.`first_name`,''),IFNULL(cby.`middle_name`,''),IFNULL(cby.`last_name`,''))AS `ownerName`,em.`is_synced` AS `isSynced`,gd.`boarding_name` AS `gradeName` FROM bt_expense_management em LEFT JOIN business_domain bd ON em.business_domain_business_domain_id=bd.business_domain_id LEFT JOIN contact_details cnt ON em.contact_contact_id=cnt.contact_id LEFT JOIN boarding_loading_details gd ON cnt.grade_name=gd.boarding_name LEFT JOIN expense_management_expense_purpose ep ON em.expense_purpose_expense_purpose_id=ep.expense_purpose_id LEFT JOIN expense_management_expense_type et ON em.expense_type_expense_type_id=et.expense_type_id LEFT JOIN expense_management_cost_center cc ON em.cost_center_cost_center_id=cc.cost_center_id LEFT JOIN expense_management_status es ON em.status_status_id=es.status_id LEFT JOIN expense_management_business_area ba ON em.business_area_business_area_id=ba.business_area_id LEFT JOIN expense_management_business_place bp ON em.business_place_business_place_id=bp.business_place_id LEFT JOIN employee_details cby ON em.created_by_email_Id=cby.email_id LEFT JOIN employee_details mody ON em.last_modified_by_email_Id=mody.email_id LEFT JOIN bt_expense_accounts_only aco ON aco.expense_id=em.id WHERE em.is_deleted=FALSE");
		if(filterData.get("manualId")!=null&&!filterData.get("manualId").equals("")) {
			queryStringBuilder.append(" and em.manual_id like '%"+filterData.get("manualId")+"%'");
	value.add("%"+filterData.get("manualId")+"%");}
		if(filterData.get("employee")!=null&&!filterData.get("employee").equals("")) {
			queryStringBuilder.append(" and CONCAT(IFNULL(cnt.`first_name`,' '),IFNULL(cnt.`middle_name`,' '),IFNULL(cnt.`last_name`,' ')) like '%"+filterData.get("employee")+"%'");
		value.add("%"+filterData.get("employee")+"%");}
		if(filterData.get("employeeCode")!=null&&!filterData.get("employeeCode").equals("")) {
			queryStringBuilder.append(" and cnt.employee_code like '%"+filterData.get("employeeCode")+"%'");
		value.add("%"+filterData.get("employeeCode")+"%");}
		if(filterData.get("expensePurposeName")!=null&&!filterData.get("expensePurposeName").equals("")) {
			queryStringBuilder.append(" and ep.expense_purpose_name like '%"+filterData.get("expensePurposeName")+"%'");
		value.add("%"+filterData.get("expensePurposeName")+"%");}
		if(filterData.get("expenseTypeName")!=null&&!filterData.get("expenseTypeName").equals("")) {

			queryStringBuilder.append(" and et.expense_type_name like '"+filterData.get("expenseTypeName")+"%'");
		value.add(""+filterData.get("expenseTypeName")+"%");}
		if(filterData.get("statusName")!=null&&!filterData.get("statusName").equals("")) {
			queryStringBuilder.append(" and es.status_name like '%"+filterData.get("statusName")+"%'");
		value.add("%"+filterData.get("statusName")+"%");}
	    if(filterData.get("documentDate")!=null&&!filterData.get("documentDate").equals("")&&!filterData.get("documentDate").equals("null")) { 
	       queryStringBuilder.append(" and DATE( em.document_date) LIKE '%"+filterData.get("documentDate")+"%'"); 
	    value.add("%"+filterData.get("documentDate")+"%");}
	    
	    
	    if(users!=null) {
	    	queryStringBuilder.append(" and em.owner_email_id in (");
	    	for(int i=0;i<users.size();i++) {
			queryStringBuilder.append(" '"+users.get(i)+"'");
			if(i<=users.size()-2) {
			queryStringBuilder.append(",");}
	    	}
	    	queryStringBuilder.append(" )");
	}
	    System.out.println("users  =  "+users);
	 	System.out.println("user   "+user.getUsername());
System.out.println("queryStringBuilder  =>  "+queryStringBuilder);
System.out.println("value  =>  "+value.toString());
System.out.println("value  =>  "+value);
		return jdbc.queryForList(queryStringBuilder.toString());
	
	}



	@Override
	public List<Map<String, Object>> advanceSearch(Map<String, Object> searchRequest, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		return null;
	}

	@Override
	public List<Map<String,Object>> getBusinessArea(int businessDomainId) {
		return btBusinessAreaDB.getByBusinessDomainId(businessDomainId);
	}

	@Override
	public List<Map<String,Object>> getBusinessPlace(int businessDomainId) {
		return btBusinessPlaceDB.getByBusinessDomainId(businessDomainId);
	}

	@Override
	public List<Map<String,Object>> getCostCenter(int businessDomainId) {
		return btCostCenterDB.getByBusinessDomainId(businessDomainId);
	}

	@Override
	public List<Map<String,Object>> getExpensePurpose(int businessDomainId) {
		return btExpensePurposeDB.getByBusinessDomainId(businessDomainId);
	}

	@Override
	public List<Map<String,Object>> getExpenseType(int businessDomainId) {
		return btExpenseTypeDb.getByBusinessDomainId(businessDomainId);
	}

	@Override
	public List<Map<String,Object>> getStatus(int businessDomainId) {
		return btManagementStatusDB.getByBusinessDomainId(businessDomainId);
	}

	@Transactional(isolation=Isolation.READ_UNCOMMITTED,rollbackFor = {Exception.class,BadRequestException.class,UnauthoriseException.class,RuntimeException.class},propagation = Propagation.REQUIRES_NEW)
	@Override
	public Map<String, Object> saveUpdateExpenseAccountsOnly(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if(Long.parseLong(request.get("btExpenseManagementId").toString())==0) {
			throw new BadRequestException("BtExpense Id 0");
		}else if(Long.parseLong(request.get("businessAreaId").toString())==0) {
			throw new BadRequestException("Business Area Id 0");
		}else if(Long.parseLong(request.get("businessPlaceId").toString())==0) {
			throw new BadRequestException("Business Place Id 0");
		}else 
			if(request.get("documentDate").toString()==null && request.get("documentDate").toString().equalsIgnoreCase("") && request.get("documentDate").toString().equalsIgnoreCase("null")) {
			throw new BadRequestException("Document Date is null");
		}else {
			Map<String, Object> expenseManagement = btExpenseManagementDB.getsById(Long.parseLong(request.get("btExpenseManagementId").toString()));
			if(expenseManagement==null) {
				throw new BadRequestException("Expense does not exist");	
			}
			
			if(Boolean.parseBoolean(expenseManagement.get("isPost").toString())) {
				throw new BadRequestException("You can't edit this record");
			}
			
			ExpenseManagementBusinessArea businessArea= btBusinessAreaDB.findByBusinessAreaId(Integer.parseInt(request.get("businessAreaId").toString()));
			if(businessArea==null) {
				throw new BadRequestException("Business Area does not exist");	
			}
			
			ExpenseManagementBusinessPlace businessPlace = btBusinessPlaceDB.findByBusinessPlaceId(Integer.parseInt(request.get("businessPlaceId").toString()));
			if(businessPlace==null) {
				throw new BadRequestException("Business Place does not exist");
			}
			ExpenseAccountsOnlyRequest req=new ExpenseAccountsOnlyRequest();
			if(request.get("btExpenseManagementId")!=null && Long.parseLong(request.get("btExpenseManagementId").toString())!=0 && !request.get("btExpenseManagementId").toString().equalsIgnoreCase("null")  && !request.get("btExpenseManagementId").toString().equalsIgnoreCase("")) {
					req.setExpenseManagementId(Long.parseLong(request.get("btExpenseManagementId").toString()));
			}if(request.get("businessAreaId")!=null && Long.parseLong(request.get("businessAreaId").toString())!=0 && !request.get("businessAreaId").toString().equalsIgnoreCase("") && !request.get("businessAreaId").toString().equalsIgnoreCase("null")) {
				req.setBusinessAreaId(Integer.parseInt(request.get("businessAreaId").toString()));
			}if(request.get("businessPlaceId")!=null && Long.parseLong(request.get("businessPlaceId").toString())!=0 && !request.get("businessPlaceId").toString().equalsIgnoreCase("") && !request.get("businessPlaceId").toString().equalsIgnoreCase("null")) {
				req.setBusinessPlaceId(Integer.parseInt(request.get("businessPlaceId").toString()));
			}
			if(request.get("documentDate")!=null && !request.get("documentDate").toString().equalsIgnoreCase("") && !request.get("documentDate").toString().equalsIgnoreCase("null")) {
			try {
				req.setDocumentDate(sdf.parse(request.get("documentDate").toString()));
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
			}
			if(request.get("postPark")!=null && !request.get("postPark").toString().equalsIgnoreCase("") && !request.get("postPark").toString().equalsIgnoreCase("null")) {
			req.setPostPark(Boolean.parseBoolean(request.get("postPark").toString()));
			}
			if(request.get("narration")!=null && !request.get("narration").toString().equalsIgnoreCase("") && !request.get("narration").toString().equalsIgnoreCase("null")) {
			req.setNarration(request.get("narration").toString());
			}
			ExpenseAccountsOnlyRequest accountsOnly = customRepositoryImpl.saveUpdateExpenseAccountsOnly(req, user);
			
			return btExpenseManagementDB.getsById(accountsOnly.getExpenseManagementId());
		}
	}

	@Transactional(isolation=Isolation.READ_UNCOMMITTED,rollbackFor = {Exception.class,BadRequestException.class,UnauthoriseException.class,RuntimeException.class},propagation = Propagation.REQUIRES_NEW)
	@Override
	public void submitForApproval(long btExpenseManagementId, String remark, int businessDoaminId, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		customRepositoryImpl.submitForApproval(btExpenseManagementId, remark, businessDoaminId, user);
		
	}

	@Override
	public ByteArrayInputStream createPdf(LoginUser user, BtExpenseManagementResponse response)
			throws BadRequestException, UnauthoriseException {
		
		BtExpensePDFGeneration expensePDFGeneration = new BtExpensePDFGeneration(entityManager, fileStorageProperties);
		try {
			return expensePDFGeneration.createPdf(user, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	@Transactional(isolation=Isolation.READ_UNCOMMITTED,rollbackFor = {Exception.class,BadRequestException.class,UnauthoriseException.class,RuntimeException.class},propagation = Propagation.REQUIRES_NEW)
	@Override
	public List<GlCodeResponse> getGLCodeSummary(long btExpenseManagementId, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		return customRepositoryImpl.getGLCodeSummary(btExpenseManagementId, user);
		 
	}
	
	
}





