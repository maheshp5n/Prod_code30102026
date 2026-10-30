package com.pratikshat.exicom.services.impl;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import javax.mail.Multipart;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.apache.commons.collections4.map.HashedMap;
import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.graphbuilder.struc.LinkedList;
import com.pratikshat.exicom.config.FileStorageProperties;
import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.BtAssets;
import com.pratikshat.exicom.entity.BtAssetsAmcdetails;
import com.pratikshat.exicom.entity.BtAssetsTypeChoice;
import com.pratikshat.exicom.entity.BtCase;
import com.pratikshat.exicom.entity.BtCaseExpectedProblems;
import com.pratikshat.exicom.entity.BtCaseProblemReported;
import com.pratikshat.exicom.entity.BtCaseRejectLog;
import com.pratikshat.exicom.entity.BtChangeCaseTypeLog;
import com.pratikshat.exicom.entity.BtCustomerWcc;
import com.pratikshat.exicom.entity.BtProblemMaster;
import com.pratikshat.exicom.entity.BtRMADetails;
import com.pratikshat.exicom.entity.BtServiceAppointment;
import com.pratikshat.exicom.entity.BtSiteDetails;
import com.pratikshat.exicom.entity.BtWccCustomStatus;
import com.pratikshat.exicom.entity.BusinessDomain;
import com.pratikshat.exicom.entity.CaseDetails;
import com.pratikshat.exicom.entity.CaseReasonDetails;
import com.pratikshat.exicom.entity.CaseRejectLog;
import com.pratikshat.exicom.entity.CaseStageDetails;
import com.pratikshat.exicom.entity.CaseStatusDetails;
import com.pratikshat.exicom.entity.CaseTypes;
import com.pratikshat.exicom.entity.ChangeCaseTypeConditions;
import com.pratikshat.exicom.entity.ChangeCaseTypeLog;
import com.pratikshat.exicom.entity.ClaimStatus;
import com.pratikshat.exicom.entity.ContactDetails;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.PODFileUpload;
import com.pratikshat.exicom.entity.PendingResponsibility;
import com.pratikshat.exicom.entity.Quote;
import com.pratikshat.exicom.entity.ServiceAppointment;
import com.pratikshat.exicom.entity.ServiceAppointmentStatus;
import com.pratikshat.exicom.entity.ServiceRecource;
import com.pratikshat.exicom.entity.UploadedFiles;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.BtCaseRequest;
import com.pratikshat.exicom.model.request.CaseProofOfDeliveryRequest;
import com.pratikshat.exicom.model.request.ChangeCaseTypeRequest;
import com.pratikshat.exicom.model.request.InvoiceUpdateRequest;
import com.pratikshat.exicom.model.request.PodRequest;
import com.pratikshat.exicom.model.response.BtCaseResponse;
import com.pratikshat.exicom.repository.BtAssetsAmcdetailsRepository;
import com.pratikshat.exicom.repository.BtAssetsRepository;
import com.pratikshat.exicom.repository.BtAssetsTypeChoiceRepository;
import com.pratikshat.exicom.repository.BtCaseExpectedProblemsRepository;
import com.pratikshat.exicom.repository.BtCaseProblemReportedRepository;
import com.pratikshat.exicom.repository.BtCaseRejectLogRepository;
import com.pratikshat.exicom.repository.BtCaseRepository;
import com.pratikshat.exicom.repository.BtChangeCaseTypeLogRepository;
import com.pratikshat.exicom.repository.BtCustomerInventoryInRepository;
import com.pratikshat.exicom.repository.BtCustomerWccRepository;
import com.pratikshat.exicom.repository.BtProblemMasterRepository;
import com.pratikshat.exicom.repository.BtRmaDetailsRepository;
import com.pratikshat.exicom.repository.BtServiceAppointmentRepository;
import com.pratikshat.exicom.repository.BtSiteDetailsRepository;
import com.pratikshat.exicom.repository.BtWccCustomerStatusRepository;
import com.pratikshat.exicom.repository.BusinessDomainRepository;
import com.pratikshat.exicom.repository.CaseReasonDetailsRepository;
import com.pratikshat.exicom.repository.CaseStageDetailsRepository;
import com.pratikshat.exicom.repository.CaseTypesRepository;
import com.pratikshat.exicom.repository.ChangeCaseTypeConditionsRepository;
import com.pratikshat.exicom.repository.ContactDetailsRepository;
import com.pratikshat.exicom.repository.PODFileUploadRepository;
import com.pratikshat.exicom.repository.ServiceRecourseRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtCaseService;
import com.pratikshat.exicom.services.FileStorageService;
import com.pratikshat.exicom.utils.CasePDF;

@Service
public class BtCaseServiceimpl implements BtCaseService {

	@Autowired
	CaseStageDetailsRepository dbCaseStage;
	
	@Autowired
	JdbcTemplate jdbc;
	@Autowired
	BtAssetsRepository dbAssets;
	@Autowired
	CaseReasonDetailsRepository dbCaseReson;
	@Autowired
	ServiceRecourseRepository dbserviceRes;
	@Autowired
	BtCaseRepository btCaseRepository;
	@Autowired
	ContactDetailsRepository dbContactdetail;
	@Autowired
	BtAssetsTypeChoiceRepository dbTypeChoice;
	@Autowired
	UserProfileRepository dbProfile;
	@Autowired
	BtSiteDetailsRepository dbSite;
	@Autowired
	BtAssetsAmcdetailsRepository dbAssetsAmc;
	@Autowired
	BusinessDomainRepository dbDomain;
	@Autowired
	BtServiceAppointmentRepository serviceRepo;
	@Autowired
	BtCaseProblemReportedRepository dbCaseProblem;
	@Autowired
	CaseTypesRepository dbcaseTypes;
	@Autowired
	BtChangeCaseTypeLogRepository dbLog;
	@Autowired
	BtCaseRejectLogRepository dbCaseReject;
	@Autowired
	ChangeCaseTypeConditionsRepository dbChangeType;
	@Autowired
	BtRmaDetailsRepository dbRmaDetailsRepo;
	@Autowired
	BtServiceAppointmentRepository dbService;
	@Autowired
	BtProblemMasterRepository dbProblem;
	@Autowired
	BtCaseExpectedProblemsRepository dbExpected;
	@Autowired
	private FileStorageService fileStorageService;
	@Autowired
	FileStorageProperties fileStorageProperties;
	
	@Autowired
	EntityManager em;
	@Autowired
	PODFileUploadRepository dbfileUpload;
	@Autowired
	BtWccCustomerStatusRepository dbWccCustomStatus;
	@Autowired
	BtCustomerInventoryInRepository dbBRCCase;

	@Autowired
	BtCustomerWccRepository dbCustomerWcc;
	SimpleDateFormat inputSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat outputSdf = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public Map<String, Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException {

		if (id == null)
			throw new BadRequestException("Invalid product id");
		boolean view = dbProfile.hasObjectAccess(user.getUsername(), BtCase.class, UserProfileRepository.VIEW);
		boolean viewall = dbProfile.hasObjectAccess(user.getUsername(), BtCase.class, UserProfileRepository.VIEW_ALL);
		if (view || viewall) {
			Long _id = Long.parseLong(id.toString());
			return btCaseRepository.getSingleCase(_id);
		} else {
			throw new UnauthoriseException("You are not authorise");
		}
	}

	@Override
	public Page<Map<String, Object>> getPage(Pageable page, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		boolean view = dbProfile.hasObjectAccess(user.getUsername(), BtCase.class, UserProfileRepository.VIEW);
		boolean viewall = dbProfile.hasObjectAccess(user.getUsername(), BtCase.class, UserProfileRepository.VIEW_ALL);
		if (view || viewall) {
			return btCaseRepository.getPage(page);
		} else {
			throw new UnauthoriseException("You are not authorise");
		}
	}

	@Override
	@Transactional(rollbackFor = { BadRequestException.class, UnauthoriseException.class,
			Exception.class }, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
	public Map<String, Object> saveObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		return null;
	}

	@Override
	public List<Map<String, Object>> searchForList(String search, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if (search != null && !search.equalsIgnoreCase("")) {
			List<Object> value = new ArrayList<>();
			String _query = "SELECT btcase.`manual_id` AS caseManualId, btS.`manual_id` AS siteManualID   \r\n"
					+ "				FROM `bt_case` btcase \r\n"
					+ "				JOIN `bt_site_details` btS ON btcase.`site_id`=btS.`id` \r\n" + "	WHERE";
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(_query);
			stringBuilder.append(" btcase.`manual_id` like ?");
			value.add("%" + search + "%");
			stringBuilder.append(" or btS.`manual_id` like ?");
			value.add("%" + search + "%");
			return jdbc.queryForList(stringBuilder.toString(), value.toArray());
		}
		return null;
	}

//	@Override
	public List<Map<String, Object>> advanceSearch(Map<String, Object> searchKey, LoginUser user, List<String> users,
			int size, int page) throws BadRequestException, UnauthoriseException {
		SimpleDateFormat dformatter = new SimpleDateFormat("yyyy-mm-dd");
		List<Object> value = new ArrayList<>();
		String _query = "SELECT btcase.`id` AS id, btcase.`manual_id` AS manualId,btcase.`poddate` AS PODDate,\r\n"
				+ "							btcase.`podnumber` AS PODNumber, btcase.`podate` AS PODate , btP.`id` AS productId,\r\n"
				+ "							btcase.`ponumber` AS PONumber,btcase.`case_reason_reason_id` AS caseReasonId,\r\n"
				+ "							btcase.`input_from_eng` AS inputFromEng,btcase.`rjiltt01no` AS RJILTT01No,btcase.`missing_assets` AS missingAssets,\r\n"
				+ "							btcase.`other_problem_reported`AS otherProblemReported, btcase.`is_assign_by_assignment_rule` AS isAssignByAssignmentRule,\r\n"
				+ "							btcase.`internal_remark` AS internalRemark, pres.`pending_responsibility_id` AS pendingResponsibility,\r\n"
				+ "							pres.`pending_responsibility_name` AS PendingResponsibilityName,btcase.`close_date` AS closeDate,\r\n"
				+ "							btsite.`id` AS siteId,btsite.`manual_id` AS siteManualId, btsite.`site_name` AS siteName,\r\n"
				+ "							service.`legal_entity_id` AS serviceCenterId,service.`legal_entity_name`AS serviceCenterName,\r\n"
				+ "							vertical.`business_domain_id` AS caseRecordTypeId , vertical.`business_domain_name` AS caseRecordTypeName,\r\n"
				+ "							asstes.`id` AS assetsId,asstes.`manual_id` AS assetsSerialNumber,\r\n"
				+ "							act.`sap_customer_number` AS accountSapNumber, act.`name` AS accountName,\r\n"
				+ "							pact.`sap_customer_number` AS primaryAccountSapNumber, pact.`name` AS primaryAccountName,\r\n"
				+ "							btcase.`created_date` AS createdDate,stage.`case_stage_name` AS caseStageName,stage.`id` AS caseStageId,\r\n"
				+ "							cby.`email_id` AS createdByEmailId ,cby.`first_name` AS createdByFirstName,cby.`middle_name` AS createdByMiddleName,\r\n"
				+ "							cby.`last_name` AS createdByLastName,mby.`first_name` AS modifiedByFirstName,mby.`middle_name`  AS modifiedByMiddleName,\r\n"
				+ "							mby.`email_id` AS modifiedByEmailId,mby.`last_name` AS modifiedByLastName, btcase.`last_modified_date` AS modifiedDate,\r\n"
				+ "							own.`email_id` AS ownerEmailId,own.`first_name` AS ownerFirstName,own.`middle_name`  AS ownerMiddleName,\r\n"
				+ "							own.`last_name` AS ownerLastName,prc.`manual_id` AS prcManualId,prc.`id` AS prcId,\r\n"
				+ "							caseType.`case_type_id` AS caseTypeId,caseType.`case_types_name` AS caseTypeName,\r\n"
				+ "							st.`case_status_name` AS statusName,serRes.`service_resource_id` AS serviceId,serRes.`name` AS serviceName,\r\n"
				+ "							reason.`case_reason_details_name`AS caseReasonName,atc.`id` AS assetsTypeChoiceId,atc.`name` AS assetstypeChoiceName,\r\n"
				+ "							origin.`case_origin_id` AS caseOriginId,origin.`case_orign_name` AS caseOriginName,priority.`case_priority_id` AS casePriorityId,\r\n"
				+ "							priority.`case_priority_name`AS casePriorityName\r\n"
				+ "							,\r\n" + "							(CASE\r\n"
				+ "								WHEN acc.`account_type`=1 THEN acc.`name`\r\n"
				+ "								ELSE NULL\r\n" + "							END) AS `vendorName`,\r\n"
				+ "							(CASE\r\n"
				+ "								WHEN acc.`account_type`=1 THEN acc.`sap_customer_number`\r\n"
				+ "								ELSE NULL\r\n"
				+ "							END) AS `vendorSapCustomerNumber`,(CASE\r\n"
				+ "				WHEN btcase.`status_id`=2 THEN CONCAT(TIMESTAMPDIFF(DAY,btcase.`created_date`,btcase.`close_date`),' Days ',FLOOR(TIMESTAMPDIFF(HOUR,btcase.`created_date`,btcase.`close_date`) MOD 24),' Hours ',FLOOR(TIMESTAMPDIFF(MINUTE,btcase.`created_date`,btcase.`close_date`) MOD 60),' Minutes')\r\n"
				+ "				ELSE 			   CONCAT(TIMESTAMPDIFF(DAY,btcase.`created_date`,NOW()),' Days ',FLOOR(TIMESTAMPDIFF(HOUR,btcase.`created_date`,NOW()) MOD 24),' Hours ',FLOOR(TIMESTAMPDIFF(MINUTE,btcase.`created_date`,NOW()) MOD 60),' Minutes')\r\n"
				+ "			END) AS SLATime\r\n" + "							FROM `bt_case` btcase \r\n"
				+ "							LEFT JOIN `pending_responsibility` pres ON btcase.`pending_responsibility_pending_responsibility_id`=pres.`pending_responsibility_id`\r\n"
				+ "							LEFT JOIN `bt_site_details` btsite ON btcase.`site_id` = btsite.`id`\r\n"
				+ "						        LEFT JOIN `legal_entity` service ON btcase.`service_center_legal_entity_id`= service.`legal_entity_id`\r\n"
				+ "							LEFT JOIN `case_stage_details` stage ON btcase.`stage_id` =stage.`id`\r\n"
				+ "							LEFT JOIN `business_domain` vertical ON btcase.`business_domain_business_domain_id`=vertical.`business_domain_id`\r\n"
				+ "			LEFT JOIN `bt_assets` asstes ON btcase.`bt_assets_id`=asstes.`id`\r\n"
				+ "			LEFT JOIN `bt_products` btP ON asstes.`products_id`=btP.`id` \r\n"
				+ "		        LEFT JOIN `accounts` act ON asstes.`account_sap_customer_number`=act.`sap_customer_number`\r\n"
				+ "			LEFT JOIN `accounts` pact ON asstes.`primary_account_sap_customer_number`=pact.`sap_customer_number`\r\n"
				+ "			JOIN `case_types` caseType ON btcase.`case_type_case_type_id`=caseType.`case_type_id`\r\n"
				+ "			JOIN `case_status_details` st ON btcase.`status_id`=st.`id`\r\n"
				+ "			LEFT JOIN `service_recource` serRes ON btcase.`engineer_service_resource_id`=serRes.`service_resource_id`\r\n"
				+ "			LEFT JOIN `contact_details` conDetail ON conDetail.`contact_id`=serRes.`contact_contact_id`\r\n"
				+ "			LEFT JOIN `accounts` acc ON acc.`sap_customer_number`=conDetail.`account_id`\r\n"
				+ "			JOIN `employee_details` cby ON cby.`email_id`=btcase.`created_by_email_id`\r\n"
				+ "			JOIN `employee_details` mby ON mby.`email_id`=btcase.`last_modified_by_email_id`\r\n"
				+ "			LEFT JOIN `employee_details` own ON own.`email_id`=btcase.`owner_details_email_id`\r\n"
				+ "			LEFT JOIN `bt_pre_registration_cases` prc ON prc.`id`=btcase.`prc_id`\r\n"
				+ "			JOIN `case_reason_details` reason ON reason.`reason_id`=btcase.`case_reason_reason_id`\r\n"
				+ "			JOIN `case_origin_details` origin ON origin.`case_origin_id`=btcase.`origin_case_origin_id`\r\n"
				+ "			JOIN `case_priority_details` priority ON priority.`case_priority_id`=btcase.`priority_case_priority_id`\r\n"
				+ "			LEFT JOIN `bt_assets_type_choice` atc ON atc.`id`=btcase.`assets_type_choice_id`"
				+ "			WHERE 1=1 ";
		StringBuilder queryBuilder = new StringBuilder();

		String rowCounter = "SELECT COUNT(*) AS rowCount" + " FROM `bt_case` btcase "
				+ "											LEFT JOIN `pending_responsibility` pres ON btcase.`pending_responsibility_pending_responsibility_id`=pres.`pending_responsibility_id`\r\n"
				+ "											LEFT JOIN `bt_site_details` btsite ON btcase.`site_id` = btsite.`id`\r\n"
				+ "										        LEFT JOIN `legal_entity` service ON btcase.`service_center_legal_entity_id`= service.`legal_entity_id`\r\n"
				+ "											LEFT JOIN `case_stage_details` stage ON btcase.`stage_id` =stage.`id`\r\n"
				+ "											LEFT JOIN `business_domain` vertical ON btcase.`business_domain_business_domain_id`=vertical.`business_domain_id`\r\n"
				+ "							LEFT JOIN `bt_assets` asstes ON btcase.`bt_assets_id`=asstes.`id`\r\n"
				+ "							LEFT JOIN `bt_products` btP ON asstes.`products_id`=btP.`id` \r\n"
				+ "						        LEFT JOIN `accounts` act ON asstes.`account_sap_customer_number`=act.`sap_customer_number`\r\n"
				+ "							LEFT JOIN `accounts` pact ON asstes.`primary_account_sap_customer_number`=pact.`sap_customer_number`\r\n"
				+ "							JOIN `case_types` caseType ON btcase.`case_type_case_type_id`=caseType.`case_type_id`\r\n"
				+ "							JOIN `case_status_details` st ON btcase.`status_id`=st.`id`\r\n"
				+ "							LEFT JOIN `service_recource` serRes ON btcase.`engineer_service_resource_id`=serRes.`service_resource_id`\r\n"
				+ "							LEFT JOIN `contact_details` conDetail ON conDetail.`contact_id`=serRes.`contact_contact_id`\r\n"
				+ "							LEFT JOIN `accounts` acc ON acc.`sap_customer_number`=conDetail.`account_id`\r\n"
				+ "							JOIN `employee_details` cby ON cby.`email_id`=btcase.`created_by_email_id`\r\n"
				+ "							JOIN `employee_details` mby ON mby.`email_id`=btcase.`last_modified_by_email_id`\r\n"
				+ "							LEFT JOIN `employee_details` own ON own.`email_id`=btcase.`owner_details_email_id`\r\n"
				+ "							LEFT JOIN `bt_pre_registration_cases` prc ON prc.`id`=btcase.`prc_id`\r\n"
				+ "							JOIN `case_reason_details` reason ON reason.`reason_id`=btcase.`case_reason_reason_id`\r\n"
				+ "							JOIN `case_origin_details` origin ON origin.`case_origin_id`=btcase.`origin_case_origin_id`\r\n"
				+ "							JOIN `case_priority_details` priority ON priority.`case_priority_id`=btcase.`priority_case_priority_id`\r\n"
				+ "							LEFT JOIN `bt_assets_type_choice` atc ON atc.`id`=btcase.`assets_type_choice_id`"
				+ " WHERE 1=1";
		StringBuilder rowBuilder = new StringBuilder();
		rowBuilder.append(rowCounter);
		queryBuilder.append(_query);

		if (users != null) {
			StringBuilder urs = new StringBuilder();
			for (int i = 0; i < users.size(); i++) {
				if (i != 0)
					urs.append(",");
				urs.append("'" + users.get(i) + "'");
			}
			queryBuilder.append(" and (btcase.owner_details_email_id in (" + urs.toString()
					+ ") or serRes.`user_email_id` in  (" + urs.toString() + "))");
			rowBuilder.append(" and (btcase.owner_details_email_id in (" + urs.toString()
					+ ") or serRes.`user_email_id` in  (" + urs.toString() + "))");
		}

		if (searchKey.get("manualID") != null && !searchKey.get("manualID").toString().equalsIgnoreCase("")) {
			queryBuilder.append(" and btcase.`manual_id` like ?");
			rowBuilder.append(" and btcase.`manual_id` like ?");
			value.add("%" + searchKey.get("manualID") + "%");
		}
		if (searchKey.get("siteManualID") != null && !searchKey.get("siteManualID").toString().equalsIgnoreCase("")) {
			queryBuilder.append(" and btsite.`manual_id` like ?");
			rowBuilder.append(" and btsite.`manual_id` like ?");
			value.add("%" + (String) searchKey.get("siteManualID") + "%");
		}
		if (searchKey.get("caseTypeName") != null && !searchKey.get("caseTypeName").toString().equalsIgnoreCase("")) {
			queryBuilder.append(" and caseType.`case_types_name` like ?");
			rowBuilder.append(" and caseType.`case_types_name` like ?");
			value.add("%" + searchKey.get("caseTypeName") + "%");
		}
		if (searchKey.get("caseStatusName") != null
				&& !searchKey.get("caseStatusName").toString().equalsIgnoreCase("")) {
			queryBuilder.append(" and st.`case_status_name` like ?");
			rowBuilder.append(" and st.`case_status_name` like ?");
			value.add("%" + searchKey.get("caseStatusName") + "%");
		}
		if (searchKey.get("engName") != null && !searchKey.get("engName").toString().equalsIgnoreCase("")) {
			queryBuilder.append(" and serRes.`name` like ?");
			rowBuilder.append(" and serRes.`name` like ?");
			value.add("%" + searchKey.get("engName") + "%");
		}
		if (searchKey.get("sapCustomerNumber") != null
				&& !searchKey.get("sapCustomerNumber").toString().equalsIgnoreCase("")) {
			queryBuilder.append(" and act.`sap_customer_number` like ?");
			rowBuilder.append(" and act.`sap_customer_number` like ?");
			value.add("%" + searchKey.get("sapCustomerNumber") + "%");
		}
		if (searchKey.get("assetsSerialNumber") != null
				&& !searchKey.get("assetsSerialNumber").toString().equalsIgnoreCase("")) {
			queryBuilder.append(" and asstes.`manual_id` like ?");
			rowBuilder.append(" and asstes.`manual_id` like ?");
			value.add("%" + searchKey.get("assetsSerialNumber") + "%");
		}
		if (searchKey.get("reasonName") != null && !searchKey.get("reasonName").toString().equalsIgnoreCase("")) {
			queryBuilder.append(" and reason.`case_reason_details_name` like ?");
			rowBuilder.append(" and reason.`case_reason_details_name` like ?");
			value.add("%" + searchKey.get("reasonName") + "%");
		}
		if (searchKey.get("stage") != null && !searchKey.get("stage").toString().equalsIgnoreCase("")) {
			queryBuilder.append(" and stage.`case_stage_name` like ?");
			rowBuilder.append(" and stage.`case_stage_name` like ?");
			value.add("%" + searchKey.get("stage") + "%");
		}

		// Using date for filter

		if ((searchKey.get("dateTimeOpenFrom") != null
				&& !searchKey.get("dateTimeOpenFrom").toString().equalsIgnoreCase(""))
				&& (searchKey.get("dateTimeOpenTo") != null
						&& !searchKey.get("dateTimeOpenTo").toString().equalsIgnoreCase(""))) {
			queryBuilder.append(
					" and DATE_FORMAT (btcase.`created_date`,'%Y, %m, %d') BETWEEN DATE_FORMAT( ? ,'%Y, %m, %d') AND DATE_FORMAT( ?,'%Y, %m, %d')");
			rowBuilder.append(
					" and DATE_FORMAT (btcase.`created_date`,'%Y, %m, %d') BETWEEN DATE_FORMAT( ? ,'%Y, %m, %d') AND DATE_FORMAT( ?,'%Y, %m, %d')");
			value.add(searchKey.get("dateTimeOpenFrom"));
			value.add(searchKey.get("dateTimeOpenTo"));

		}

		if (searchKey.get("dateTimeOpenTo") == null && (searchKey.get("dateTimeOpenFrom") != null
				&& !searchKey.get("dateTimeOpenFrom").toString().equalsIgnoreCase(""))) {
			queryBuilder
					.append(" and DATE_FORMAT(btcase.`created_date`, '%Y, %m, %d')>= DATE_FORMAT( ?, '%Y, %m, %d')");
			rowBuilder.append(" and DATE_FORMAT(btcase.`created_date`, '%Y, %m, %d')>= DATE_FORMAT( ?, '%Y, %m, %d')");
			value.add(searchKey.get("dateTimeOpenFrom"));
		}

		if ((searchKey.get("dateTimeOpenTo") != null
				&& !searchKey.get("dateTimeOpenTo").toString().equalsIgnoreCase(""))
				&& searchKey.get("dateTimeOpenFrom") == null) {
			queryBuilder
					.append(" and DATE_FORMAT(btcase.`created_date`, '%Y, %m, %d')>= DATE_FORMAT( ?, '%Y, %m, %d')");
			rowBuilder.append(" and DATE_FORMAT(btcase.`created_date`, '%Y, %m, %d')>= DATE_FORMAT( ?, '%Y, %m, %d')");
			value.add(searchKey.get("dateTimeOpenTo"));
		}

		if ((searchKey.get("dateTimeCloseFrom") != null
				&& !searchKey.get("dateTimeCloseFrom").toString().equalsIgnoreCase(""))
				&& (searchKey.get("dateTimeCloseTo") != null
						&& !searchKey.get("dateTimeCloseTo").toString().equalsIgnoreCase(""))) {
			queryBuilder.append(
					" and DATE_FORMAT (btcase.`close_date`,'%Y, %m, %d') BETWEEN DATE_FORMAT( ? ,'%Y, %m, %d') AND DATE_FORMAT( ?,'%Y, %m, %d')");
			rowBuilder.append(
					" and DATE_FORMAT (btcase.`close_date`,'%Y, %m, %d') BETWEEN DATE_FORMAT( ? ,'%Y, %m, %d') AND DATE_FORMAT( ?,'%Y, %m, %d')");
			value.add(searchKey.get("dateTimeCloseFrom"));
			value.add(searchKey.get("dateTimeCloseTo"));
		}

		if (searchKey.get("dateTimeCloseTo") == null && (searchKey.get("dateTimeCloseFrom") != null
				&& !searchKey.get("dateTimeCloseFrom").toString().equalsIgnoreCase(""))) {
			queryBuilder.append(" and DATE_FORMAT(btcase.`close_date`, '%Y, %m, %d')>= DATE_FORMAT( ?, '%Y, %m, %d')");
			rowBuilder.append(" and DATE_FORMAT(btcase.`close_date`, '%Y, %m, %d')>= DATE_FORMAT( ?, '%Y, %m, %d')");
			value.add(searchKey.get("dateTimeCloseFrom"));
		}
		if ((searchKey.get("dateTimeCloseTo") != null
				&& !searchKey.get("dateTimeCloseTo").toString().equalsIgnoreCase(""))
				&& searchKey.get("dateTimeCloseFrom") == null) {
			queryBuilder.append(" and DATE_FORMAT(btcase.`close_date`, '%Y, %m, %d')>= DATE_FORMAT( ?, '%Y, %m, %d')");
			rowBuilder.append(" and DATE_FORMAT(btcase.`close_date`, '%Y, %m, %d')>= DATE_FORMAT( ?, '%Y, %m, %d')");
			value.add(searchKey.get("dateTimeCloseTo"));
		}

		Map<String, Object> map = jdbc.queryForMap(rowBuilder.toString(), value.toArray());
		int count = Integer.parseInt(map.get("rowCount").toString());
		int offset = size * page;
		queryBuilder.append(" ORDER BY btcase.created_date ");
		queryBuilder.append(" LIMIT ?");
		value.add(size);
		queryBuilder.append(" OFFSET ?");
		value.add(offset);
		return jdbc.queryForList(queryBuilder.toString(), value.toArray());

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = { RuntimeException.class,
			BadRequestException.class, UnauthoriseException.class,
			Exception.class }, propagation = Propagation.REQUIRES_NEW)
	public BtCaseResponse updateObjects(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		BtCase _caseDetails = null;
		boolean edit = dbProfile.hasObjectAccess(user.getUsername(), BtCase.class, UserProfileRepository.EDIT);
		if (edit) {

			_caseDetails = btCaseRepository.getCaseWihtId(Long.valueOf(request.get("id").toString()));
			
			if (_caseDetails != null) {
				if (!_caseDetails.getOwnerDetails().getEmailId().equalsIgnoreCase(user.getUsername())) {
					Map<String, String> group = dbProfile.getAccountAndRoleGroup(user);
					if (group.get("roleGroup").equalsIgnoreCase("CUSTOMERCARE")
							|| group.get("roleGroup").equalsIgnoreCase("ADMIN")) {

					} else {
						new com.pratikshat.exicom.exception.BadRequestException(
								"You are not authorised to edit this record");
					}
				}
				List<Integer> list = new ArrayList<>();
				list.add(2); // Closed
				list.add(4); // Rejected

				if (list.contains(_caseDetails.getStatus().getId())) {
					throw new com.pratikshat.exicom.exception.BadRequestException("You can't edit this record");
				}
				if (request.get("assetsId") != null && !request.get("assetsId").toString().equalsIgnoreCase("")) {
					BtAssets assets = null;
					assets = dbAssets.getAssetsForAppointment(Long.parseLong(request.get("assetsId").toString()));

					if (assets != null) {
						
//						if(assets.getId()!=_caseDetails.getId()) {
//						// Find asset
//						
//						long checkAsset = btCaseRepository.checkAssetStatusInAllCaseInUpdate(assets.getId(),_caseDetails.getId());
//	
//						if (checkAsset > 0)
//							throw new BadRequestException("Case already logged on this Asset");
//
//						// Find Asset with Open Status in BRC Case
//
//						long checkAssetInBRC = dbBRCCase.checkAssetStatusIbBRCCase(assets.getId());
//	
//						if (checkAssetInBRC > 0)
//							throw new BadRequestException("Case already logged on this Assets");
//
//						//
//						}
						
						_caseDetails.setBtAssets(assets);
						_caseDetails.setMissingAssets("");
					} else {
						throw new com.pratikshat.exicom.exception.BadRequestException("Asset does not exist");
					}
					if (assets != null && assets.getStatus() != null) {
						if (assets.getStatus().getAssetsStatusId() == 1) { // Dismantle
							throw new com.pratikshat.exicom.exception.BadRequestException("Asset is dismantle");
						}
					}
					
					// Find asset
					
					long checkAsset = btCaseRepository.checkAssetStatusInAllCaseInUpdate(assets.getId(),_caseDetails.getId());

					if (checkAsset > 0)
						throw new BadRequestException("Case already logged on this Asset");

					// Find Asset with Open Status in BRC Case

					long checkAssetInBRC = dbBRCCase.checkAssetStatusIbBRCCase(assets.getId());

					if (checkAssetInBRC > 0)
						throw new BadRequestException("Case already logged on this Asset");

					//

				} else {
					_caseDetails.setBtAssets(null);
					List<Long> caseReasonList = new ArrayList<>();
					caseReasonList.add(7L); // Exceptional Case
					caseReasonList.add(8L); // Installation
					caseReasonList.add(51L); // Warehouse Health check-ups
					caseReasonList.add(48L); // Site Survey
					caseReasonList.add(47L); // Battery Bank Charging

//					if (caseReasonList.contains(Long.valueOf(request.get("caseReasonId").toString()))) {
					if (Long.valueOf(request.get("caseReasonId").toString()) == 7L
							|| (Long.valueOf(request.get("caseReasonId").toString()) == 8L)) {
						if (request.get("missingAssets") == null) {
							throw new com.pratikshat.exicom.exception.BadRequestException("Please fill missing asset");
						}
					}
//					} else {
//						throw new com.pratikshat.exicom.exception.BadRequestException("Please select asset");
//					}
				}
				BtSiteDetails site = null;
				if (request.get("siteId") != null) {
					int siteID = (Integer) request.get("siteId");
					site = dbSite.getSiteWithId(Long.valueOf(siteID));
					if (site != null) {
						_caseDetails.setSite(site);
					} else {
						throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist");
					}
				} else {
					throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist");
				}
				if (request.get("assetsId") != null && !request.get("assetsId").toString().equalsIgnoreCase("")) {
					BtAssets assets = dbAssets
							.getAssetsForAppointment(Long.parseLong(request.get("assetsId").toString()));
					if (assets.getSiteDetails() != null) {
						if (assets.getSiteDetails().getId() != Long.parseLong(request.get("siteId").toString())) {
							throw new com.pratikshat.exicom.exception.BadRequestException(
									"Given site mismatch with the assets installed on the site");
						}
					} else {
						throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist");
					}
				}
				ContactDetails contact = dbContactdetail.getWithContactIdAndAccountId(
						Long.valueOf(request.get("contactPersonId").toString()),
						site.getAccount().getSapCustomerNumber());
				if (contact != null) {
					_caseDetails.setContactPerson(contact);
				} else {
					throw new com.pratikshat.exicom.exception.BadRequestException("Contact does not exist");
				}

				if (request.get("missingAssets") != null) {

					CaseReasonDetails caseReason = dbCaseReson
							.getCaseReasonWithId(Long.parseLong(request.get("caseReasonId").toString()));
					_caseDetails.setCaseReason(caseReason);

				} else {
					try {
						long caseTypeId = Long.parseLong(request.get("caseTypeId").toString());
						List<CaseReasonDetails> reasons = dbCaseReson.getResonWithCasetypeAndDomainId(
								Long.valueOf(caseTypeId), (int) request.get("caseRecordTypeId"));
						List<Long> reasonList = new ArrayList<>();
						for (int i = 0; i < reasons.size(); i++) {
							CaseReasonDetails reason = reasons.get(i);
							reasonList.add(reason.getReasonId());
						}
						if (request.get("caseReasonId") != null
								&& !reasonList.contains(Long.parseLong(request.get("caseReasonId").toString()))) {
							throw new com.pratikshat.exicom.exception.BadRequestException("Wrong case reason");
						}
					} catch (NoResultException ex) {
						throw new com.pratikshat.exicom.exception.BadRequestException("Wrong case reason");
					}
					_caseDetails.setCaseReason(new com.pratikshat.exicom.entity.CaseReasonDetails(
							Long.parseLong(request.get("caseReasonId").toString())));
				}

				if (request.get("typeChoice") != null && request.get("typeChoice").equals("0")) {

					BtAssetsTypeChoice assetsTypeChoice = dbTypeChoice
							.getTypeById(Integer.parseInt(request.get("typeChoice").toString()));
					_caseDetails.setAssetsTypeChoice(assetsTypeChoice);
					if (assetsTypeChoice == null) {
						throw new BadRequestException("No type choice found");
					}
				}
				try {
					List<Integer> reasonList = dbCaseStage.getStageIdWithStatusAndDomainId(
							(int) request.get("caseStatusId"), (int) request.get("caseRecordTypeId"));

					if (!reasonList.contains(request.get("caseStageId"))) {
						throw new com.pratikshat.exicom.exception.BadRequestException("Wrong case stage");
					}
				} catch (NoResultException ex) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Wrong case stage");
				}
				if (request.get("serviceResourceId") != null) {
					ServiceRecource serviceRecource = null;
					serviceRecource = dbserviceRes.getServiceResourceWithIdAndServiceid(
							Long.valueOf(request.get("serviceCenterId").toString()),
							Long.valueOf(request.get("serviceResourceId").toString()));
					if (serviceRecource == null) {
						throw new com.pratikshat.exicom.exception.BadRequestException("Service resource is wrong");
					}
					_caseDetails.setEngineer(serviceRecource);
				}
				_caseDetails.setCaseType(
						new com.pratikshat.exicom.entity.CaseTypes(Long.valueOf(request.get("caseTypeId").toString())));
				// Installation for Battery // Installation for DCT
				if ((int) request.get("caseTypeId") == 2 || (int) request.get("caseTypeId") == 6) {

					if (request.get("poDate") != null) {
						try {
							_caseDetails.setPODate(inputSdf.parse(request.get("poDate").toString()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					if (request.get("poamount") != null)
						_caseDetails.setPoamount(request.get("poamount").toString());
					if (request.get("lineitem") != null)
						_caseDetails.setLineitem(request.get("lineitem").toString());
					if (request.get("poNumber") != null)
						_caseDetails.setPONumber(request.get("poNumber").toString());

				}
				if (request.get("noOfSmr") != null) {
					_caseDetails.setNoOfsmr(request.get("noOfSmr").toString());
				}
////				_caseDetails.setCasesubReason(request.get("casesubReason"));
//				 _caseDetails.setCasesubReason(MiscellaneousUtils.objToString(request.get("casesubReason")));
				_caseDetails.setOrigin(
						new com.pratikshat.exicom.entity.CaseOriginDetails((int) request.get("caseOriginId")));
				_caseDetails.setPriority(
						new com.pratikshat.exicom.entity.CasePriorityDetails((int) request.get("casePriorityId")));
				if (request.get("remarks") != null) {
					_caseDetails.setRemarks(request.get("remarks").toString());
				}

				if (request.get("rma") != null) {
					_caseDetails.setRma((boolean) request.get("rma"));
				}

				
				if (request.get("rol") != null) {
						if (Boolean.parseBoolean(request.get("rma").toString())==false && Boolean.parseBoolean(request.get("rol").toString())==true) {
							
						_caseDetails.setRol((boolean) request.get("rol"));

						if (Boolean.parseBoolean(request.get("rol").toString()) == true) {
							if (request.get("rolRemark") == null || request.get("rolRemark").toString().equalsIgnoreCase("")) {
								throw new BadRequestException("Please fill ROL remark");
							}
							_caseDetails.setRolRemark(request.get("rolRemark").toString());
						}

					}
				}

				_caseDetails
						.setStage(new com.pratikshat.exicom.entity.CaseStageDetails((int) request.get("caseStageId")));
				_caseDetails.setStatus(
						new com.pratikshat.exicom.entity.CaseStatusDetails((int) request.get("caseStatusId")));
				_caseDetails.setSynked(false);
				if (request.get("missingAssets") != null) {
					_caseDetails.setMissingAssets(request.get("missingAssets").toString());
				}
				if (request.get("serviceCenterId") != null) {
					_caseDetails.setServiceCenter(new com.pratikshat.exicom.entity.LegalEntity(
							Long.valueOf(request.get("serviceCenterId").toString())));
				}
				if (request.get("otherProblemReported") != null) {
					_caseDetails.setOtherProblemReported(request.get("otherProblemReported").toString());
				}
				if (request.get("isAssignByAssignmentRule") != null) {
					_caseDetails.setAssignByAssignmentRule((boolean) request.get("isAssignByAssignmentRule"));
				}
				if (request.get("inputFromEng") != null) {
					_caseDetails.setInputFromEng(request.get("inputFromEng").toString());
				}
				if (request.get("internalRemark") != null) {
					_caseDetails.setInternalRemark(request.get("internalRemark").toString());
				}
				if (request.get("RJILTT01No") != null) {
					_caseDetails.setRJILTT01No(request.get("RJILTT01No").toString());
				}
				////////////////////////////////////////////////////// update openDate and ticketNo ////////////////////////////////
				
				if(request.get("ticketNo")!=null) {
					_caseDetails.setTicketNo(request.get("ticketNo").toString());
				}
				
				if(request.get("casesubReason")!=null) {
					_caseDetails.setCasesubReason(request.get("casesubReason").toString());
				}
				if(request.get("openDate")!=null) {
				
						_caseDetails.setOpenDate(request.get("openDate").toString());
				}
				if (request.get("pendingResponsibility") != null) {
					_caseDetails.setPendingResponsibility(
							new PendingResponsibility(Long.valueOf(request.get("pendingResponsibility").toString())));
				}

				if ((int) request.get("caseStatusId") == 2) { // Closed

					List<Long> saStatusList = new ArrayList<>();
					saStatusList.add(1L); // Unassigned
					saStatusList.add(2L); // Scheduled
//					saStatusList.add(3L); // Dispatched				//comment for rol
					List<BtServiceAppointment> serviceAppointmentList = serviceRepo
							.getListWithCaseIDandStatus(_caseDetails.getId(), saStatusList);
					if (serviceAppointmentList != null && serviceAppointmentList.size() > 0) {
						throw new com.pratikshat.exicom.exception.BadRequestException(
								"Service Appointment already present in dispatch, unassigned or scheduled status");
					}

					_caseDetails.setCloseDate(new Date());
					_caseDetails.setClosedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
				}

				if (request.get("btProblemMaster") != null) {
					List<Integer> lst = new ArrayList<>();
					lst.add(1);
					lst.add(2);
					lst.add(3);
					System.out.println(request.get("btProblemMaster"));
					if (lst.contains(request.get("btProblemMaster"))) {
						_caseDetails.setBtProblemMaster(
								new BtProblemMaster(Integer.parseInt(request.get("btProblemMaster").toString())));
					} else {
						throw new BadRequestException("No Problem Type Found");
					}
				}
				if (request.get("caseProblemReported") != null) {
					btCaseRepository.deleteCaseProblemReported(_caseDetails.getId());
					List<Map<String, Object>> ls = (List<Map<String, Object>>) request.get("caseProblemReported");
					for (int i = 0; i < ls.size(); i++) {
						BtCaseProblemReported caseProblemReported = new BtCaseProblemReported();
						caseProblemReported.setProblemName(ls.get(i).get("problemName").toString());
						caseProblemReported.setCaseDetails(_caseDetails);
						btCaseRepository.saveDetails(caseProblemReported, user);
					}
				}
				btCaseRepository.updateDetails(_caseDetails, user );
			} else {
				throw new BadRequestException("No data found for update");
			}
		} else {
			throw new UnauthoriseException("You are not authorize");
		}
		if (_caseDetails.getStatus() != null && _caseDetails.getStatus().getId() == 3) {

			BtServiceAppointment appointment = new BtServiceAppointment();

			List<Long> saStatusList = new ArrayList<>();
			saStatusList.add(1L); // Unassigned
			saStatusList.add(2L); // Scheduled
			saStatusList.add(3L); // Dispatched
			List<BtServiceAppointment> serviceAppointmentList = serviceRepo
					.getListWithCaseIDandStatus(_caseDetails.getId(), saStatusList);

			if (serviceAppointmentList != null && serviceAppointmentList.size() > 0) {
				throw new com.pratikshat.exicom.exception.BadRequestException(
						"Service Appointment already present in dispatch, unassigned or scheduled status");
			}
			BtSiteDetails btSiteDetails = dbSite.getSiteWithId(_caseDetails.getSite().getId());
			EmployeeDetails caseCreatedBy = btCaseRepository.employeeDetails(_caseDetails.getCreatedBy().getEmailId());
			BtAssets assets = null;
			if (_caseDetails.getBtAssets() != null) {
				assets = dbAssets.getAssetsForAppointment(_caseDetails.getBtAssets().getId());
			}
			appointment.setCaseDetails(_caseDetails);
			appointment.setSyncFlag(false);
			appointment.setApprovedByFinance(false);
			appointment.setCircle(btSiteDetails.getCircle());
			if (_caseDetails.getBtAssets() != null) {
				appointment.setAssets(assets);
				appointment.setProduct(assets.getProducts());
			}
			if (_caseDetails.getCaseReason() != null) {
				try {
					CaseReasonDetails caseReason = dbCaseReson
							.getCaseReasonWithId(_caseDetails.getCaseReason().getReasonId());
					appointment.setCostCenter(caseReason.getCostCenter());
				} catch (NoResultException ex) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Case reason does not exist");
				} catch (Exception e) {
					throw new com.pratikshat.exicom.exception.BadRequestException(e.getMessage());
				}
			}
			String circleManagerEmailId = null;
			if (_caseDetails.getBusinessDomain().getBusinessDomainId() == 1) { // Battery
				if (btSiteDetails.getCircle() != null) {
					if (btSiteDetails.getCircle().getCircleManagerBattry() != null) {
						appointment.setCircleManager(btSiteDetails.getCircle().getCircleManagerBattry());
						circleManagerEmailId = btSiteDetails.getCircle().getCircleManagerBattry().getEmailId();
					}
				}
			}
			appointment.setStatus(new ServiceAppointmentStatus(3));
			appointment.setPendingResponsibility(new PendingResponsibility(1));

			String name = null;
			if (caseCreatedBy.getFirstName() != null && !caseCreatedBy.getFirstName().equalsIgnoreCase("")) {
				name = caseCreatedBy.getFirstName();
			}

			if (caseCreatedBy.getMiddleName() != null && !caseCreatedBy.getMiddleName().equalsIgnoreCase("")) {
				name = name + " " + caseCreatedBy.getMiddleName();
			}

			if (caseCreatedBy.getLastName() != null && !caseCreatedBy.getLastName().equalsIgnoreCase("")) {
				name = name + " " + caseCreatedBy.getLastName();
			}
			appointment.setCustomerRepresentativeName(name);

			if (caseCreatedBy.getContact() != null) {
				appointment.setCustomerRepresentativeContact(caseCreatedBy.getContact().getMobile());
			}

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

			Calendar cal = Calendar.getInstance();
			String time = timeFormat.format(cal.getTime());
			appointment.setEarliestStartPermittedDate(dateFormat.format(cal.getTime()));
			appointment.setEarliestStartPermittedTime(time);
			cal.add(Calendar.DATE, 30);
			appointment.setDueDate(dateFormat.format(cal.getTime()));
			appointment.setDueTime(time);
			appointment.settFlag("N");
			appointment.setBusinessDomain(_caseDetails.getBusinessDomain());
			appointment.setClaimStatus(new ClaimStatus(2L)); // To be Claimed
			appointment.setApprovedByFinance(false);
			appointment.setRma(false);
			CaseStageDetails caseStageDetails = null;
			try {
				caseStageDetails = dbCaseStage.getCaseStageWithId(_caseDetails.getStage().getId());
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case stage does not exist");
			}
			if (_caseDetails.isAssignByAssignmentRule()) {
				if (caseStageDetails.getCaseStageName().equalsIgnoreCase("Assigned To Engineer")) {
					ServiceRecource serviceRecource = dbserviceRes.getServiceResourceWithCaseId(_caseDetails.getId());
					if (serviceRecource != null) {
						System.out.println(serviceRecource.getUser().getEmailId() + "service appointment owner");
						appointment.setOwnerDetails(serviceRecource.getUser());
						appointment.setServiceResource(serviceRecource);
					} else {
						try {
							ServiceRecource sch = dbserviceRes.getByEmail(circleManagerEmailId);
							appointment.setOwnerDetails(sch.getUser());
							appointment.setServiceResource(sch);
						} catch (NoResultException ex) {
							throw new com.pratikshat.exicom.exception.BadRequestException("SCH does not exist");
						}
					}
				} else if (caseStageDetails.getCaseStageName().equalsIgnoreCase("Assigned To Dispatcher")) {
					try {
						ServiceRecource sch = dbserviceRes.getByEmail(circleManagerEmailId);
						appointment.setOwnerDetails(sch.getUser());
						appointment.setServiceResource(sch);
					} catch (NoResultException ex) {
						throw new com.pratikshat.exicom.exception.BadRequestException("SCH does not exist");
					}
				} else {
					try {
						ServiceRecource sch = dbserviceRes.getByEmail(circleManagerEmailId);
						appointment.setOwnerDetails(sch.getUser());
						appointment.setServiceResource(sch);
					} catch (NoResultException ex) {
						throw new com.pratikshat.exicom.exception.BadRequestException("SCH does not exist");
					}
				}

			} else {
				if (caseStageDetails.getCaseStageName().equalsIgnoreCase("Assigned To Engineer")) {
					ServiceRecource serviceRecource = dbserviceRes
							.getServiceResourceWithId(_caseDetails.getEngineer().getServiceResourceId());
					if (serviceRecource != null) {
						System.out.println(serviceRecource.getName() + "Serive resource name");
						appointment.setOwnerDetails(serviceRecource.getUser());
						appointment.setServiceResource(serviceRecource);
					} else {
						try {
							ServiceRecource sch = dbserviceRes.getByEmail(circleManagerEmailId);
							appointment.setOwnerDetails(sch.getUser());
							appointment.setServiceResource(sch);
						} catch (NoResultException ex) {
							throw new com.pratikshat.exicom.exception.BadRequestException("SCH does not exist");
						}
					}

				} else if (caseStageDetails.getCaseStageName().equalsIgnoreCase("Assigned To Dispatcher")) {
					try {
						ServiceRecource sch = dbserviceRes.getByEmail(circleManagerEmailId);
						appointment.setOwnerDetails(sch.getUser());
						appointment.setServiceResource(sch);
					} catch (NoResultException ex) {
						throw new com.pratikshat.exicom.exception.BadRequestException("SCH does not exist");
					}
				} else {
					try {
						ServiceRecource sch = dbserviceRes.getByEmail(circleManagerEmailId);
						appointment.setOwnerDetails(sch.getUser());
						appointment.setServiceResource(sch);
					} catch (NoResultException ex) {
						throw new com.pratikshat.exicom.exception.BadRequestException("SCH does not exist");
					}
				}
			}
			List<BtServiceAppointment> serviceAppointmentList1 = serviceRepo
					.getListWithCaseId(_caseDetails.getId());
			if(serviceAppointmentList1.size()==0)
			{
			serviceRepo.saveDetails(appointment, user);
			}
		}
		return btCaseRepository.getByCaseId(_caseDetails.getId());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, RuntimeException.class, Exception.class })
	public Map<String, Object> btCaseReject(Map<String, Object> btcase, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		Map<String, String> group = dbProfile.getAccountAndRoleGroup(user);
		System.out.println(group.get("roleGroup"));
		if (group.get("roleGroup").equalsIgnoreCase("ADMIN")) {
			BtCase caseDetails = btCaseRepository.getCaseForInvoiceUpdate(btcase.get("caseManualId").toString().trim());

			if (caseDetails == null) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
			}
			if (caseDetails.getStatus().getId() == 1 || caseDetails.getStatus().getId() == 3) {
				List<BtServiceAppointment> serviceAppointmentList = serviceRepo.getListWithCaseId(caseDetails.getId());

				boolean isComplete = false;
				if (serviceAppointmentList != null) {
					for (int i = 0; i < serviceAppointmentList.size(); i++) {
						BtServiceAppointment sa = serviceAppointmentList.get(i);
						if (sa.getStatus().getStatusId() == 4) { // Completed
							isComplete = true;
						}
					}
				}
				if (!isComplete) {
					caseDetails.setStatus(new CaseStatusDetails(4)); // Rejected
					caseDetails.setStage(new CaseStageDetails(15)); // Rejected
					// entityManager.persist(caseDetails);
					btCaseRepository.updateDetails(caseDetails, user);

					if (serviceAppointmentList != null) {
						for (int i = 0; i < serviceAppointmentList.size(); i++) {

							BtServiceAppointment sa = serviceAppointmentList.get(i);
							sa.setStatus(new ServiceAppointmentStatus(8)); // Rejected'
							serviceRepo.updateDetails(sa, user);

						}
					}

					BtCaseRejectLog log = new BtCaseRejectLog();
					log.setCaseManualId(btcase.get("caseManualId").toString().trim());
					log.setRemark(btcase.get("remark").toString().trim());
					log.setFile(btcase.get("file").toString());
					log.setCreatedBy(new EmployeeDetails(user.getUsername()));
					log.setCreatedDate(new Date());

					dbCaseReject.saveDetails(log, user);
				} else {
					throw new com.pratikshat.exicom.exception.BadRequestException("You can not reject this record");
				}
			} else if (caseDetails.getStatus().getId() == 2) { // Closed

				List<Integer> list = new ArrayList<>();
				list.add(17); // Document Awaited - Customer
				list.add(22); // Document in-Transit
				list.add(9); // To Be Invoiced

				if (list.contains(caseDetails.getStage().getId())) {
					caseDetails.setStage(new CaseStageDetails(24)); // Not To Be Invoiced

					btCaseRepository.saveDetails(caseDetails, user);

					BtCaseRejectLog log = new BtCaseRejectLog();
					log.setCaseManualId(btcase.get("caseManualId").toString().trim());
					log.setRemark(btcase.get("remark").toString().trim());
					log.setFile(btcase.get("file").toString());
					log.setCreatedBy(new EmployeeDetails(user.getUsername()));
					log.setCreatedDate(new Date());

					dbCaseReject.saveDetails(log, user);
				} else {
					throw new com.pratikshat.exicom.exception.BadRequestException("You can not reject this record");
				}
			} else {
				throw new com.pratikshat.exicom.exception.BadRequestException("You can not reject this record");
			}
		} else if (group.get("roleGroup").equalsIgnoreCase("CUSTOMERCARE")) {
			BtCase _caseDetails = btCaseRepository
					.getCaseForInvoiceUpdate(btcase.get("caseManualId").toString().trim());

			if (_caseDetails == null) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
			}

			// New //In-Process
			if (_caseDetails.getStatus().getId() == 1 || _caseDetails.getStatus().getId() == 3) {
				List<BtServiceAppointment> serviceAppointmentList = serviceRepo.getListWithCaseId(_caseDetails.getId());

				boolean isComplete = false;
				if (serviceAppointmentList != null) {
					for (int i = 0; i < serviceAppointmentList.size(); i++) {
						BtServiceAppointment sa = serviceAppointmentList.get(i);
						if (sa.getStatus().getStatusId() == 4) { // Completed
							isComplete = true;
						}
					}
				}

				if (!isComplete) {
					_caseDetails.setStatus(new CaseStatusDetails(4)); // Rejected
					_caseDetails.setStage(new CaseStageDetails(15)); // Rejected

					btCaseRepository.updateDetails(_caseDetails, user);

					if (serviceAppointmentList != null) {
						for (int i = 0; i < serviceAppointmentList.size(); i++) {

							BtServiceAppointment sa = serviceAppointmentList.get(i);

							sa.setStatus(new ServiceAppointmentStatus(8)); // Rejectet

							serviceRepo.updateDetails(sa, user);

						}
					}
				}
				
				//changes on 15/02/2022  RB

//				if(serviceAppointmentList!=null) {
//				
//					if(serviceAppointmentList.size()>1)
//						throw new BadRequestException("You Can't reject this case");
//					
//					if(serviceAppointmentList.size()>0) {
//					if(serviceAppointmentList.get(0).getStatus().getStatusId()!=3) //Dispatched
//						throw new BadRequestException("Case can only reject when SA in dispatched mode ");
//					
//					BtServiceAppointment sa = serviceAppointmentList.get(0);
//					sa.setStatus(new ServiceAppointmentStatus(8)); // Reject
//					
//					serviceRepo.updateDetails(sa, user);
//					}
//				}
//				
//				_caseDetails.setStatus(new CaseStatusDetails(4)); // Rejected
//				_caseDetails.setStage(new CaseStageDetails(15)); // Rejected
//				
//				btCaseRepository.updateDetails(_caseDetails, user);
				
				//End
				
					BtCaseRejectLog log = new BtCaseRejectLog();
					log.setCaseManualId(btcase.get("caseManualId").toString().trim());
					log.setRemark(btcase.get("remark").toString().trim());
					log.setFile(btcase.get("file").toString());

					log.setCreatedBy(new EmployeeDetails(user.getUsername()));
					log.setCreatedDate(new Date());

					dbCaseReject.saveDetails(log, user);
				
			} else {
				throw new com.pratikshat.exicom.exception.BadRequestException("You can not reject this record");
			}
			System.out.println(group.get("roleGroup"));
		} else if (group.get("roleGroup").equalsIgnoreCase("COMMERCIAL")) {
			BtCase _caseDetails = btCaseRepository
					.getCaseForInvoiceUpdate(btcase.get("caseManualId").toString().trim());

			if (_caseDetails == null) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
			}

			if (_caseDetails.getStatus().getId() == 2) { // Closed

				List<Integer> list = new ArrayList<>();
				list.add(17); // Document Awaited - Customer
				list.add(21); // Document in-Transit
				list.add(1); // To Be Invoiced

				if (list.contains(_caseDetails.getStage().getId())) {
					_caseDetails.setStage(new CaseStageDetails(24)); // Not To Be Invoiced

					btCaseRepository.updateDetails(_caseDetails, user);

					BtCaseRejectLog log = new BtCaseRejectLog();
					log.setCaseManualId(btcase.get("caseManualId").toString().trim());
					log.setRemark(btcase.get("remark").toString().trim());
					log.setFile(btcase.get("file").toString());

					log.setCreatedBy(new EmployeeDetails(user.getUsername()));
					log.setCreatedDate(new Date());

					dbCaseReject.saveDetails(log, user);
				} else {
					throw new com.pratikshat.exicom.exception.BadRequestException("You can not reject this record");
				}
			} else {
				throw new com.pratikshat.exicom.exception.BadRequestException("You can not reject this record");
			}
//		}  else if (group.get("roleGroup").equalsIgnoreCase("SCH")) {
//			
//			BtCase _caseDetails = btCaseRepository
//					.getCaseForInvoiceUpdate(btcase.get("caseManualId").toString().trim());
//			
//			if (_caseDetails == null) {
//				throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
//			}
//
//			if (_caseDetails.getStatus().getId() == 1) { // new
//				
//				if(!_caseDetails.getServiceCenter().getAccountOwner().toString().equalsIgnoreCase(user.getUsername()))
//					throw new BadRequestException("Only "+_caseDetails.getServiceCenter().getLegalEntityName()+" SCH can reject this case");
//				
//				_caseDetails.setStage(new CaseStageDetails(15)); //Stage Rejected
//				_caseDetails.setStatus(new CaseStatusDetails(4)); //Status Rejected
//				
////				_caseDetails.getServiceCenter().get
//				btCaseRepository.updateDetails(_caseDetails, user);
//				
//				BtCaseRejectLog log = new BtCaseRejectLog();
//				log.setCaseManualId(btcase.get("caseManualId").toString().trim());
//				log.setRemark(btcase.get("remark").toString().trim());
//				log.setFile(btcase.get("file").toString());
//
//				log.setCreatedBy(new EmployeeDetails(user.getUsername()));
//				log.setCreatedDate(new Date());
//
//				dbCaseReject.saveDetails(log, user);
//				
//			}else {
//				throw new BadRequestException("You can only reject case in new stage");
//			}
//			
//		
		}else {
			throw new com.pratikshat.exicom.exception.BadRequestException(
					"Your are not authorised to edit this record");
		}
		return btcase;

	}

	@Override
	public Page<Map<String, Object>> getAll(LoginUser user, Pageable page, String filtername)
			throws BadRequestException, UnauthoriseException {
		if (dbProfile.hasObjectAccess(user.getUsername(), BtCase.class, UserProfileRepository.VIEW_ALL)) {

			if (filtername != null && filtername.equalsIgnoreCase("Document Awaited - Customer")) {
				return btCaseRepository.getCasesByStage(filtername, page);
			}
			if (filtername != null && filtername.equalsIgnoreCase("All")) {
				return btCaseRepository.getPage(page);
			}
			if (filtername != null && filtername.equalsIgnoreCase("closed")) {
				List<String> list = new ArrayList<>();

				list.add("Closed");
				list.add("Rejected");

				return btCaseRepository.getCasesByStatus(list, page);
			}
			if (filtername != null && filtername.equalsIgnoreCase("open")) {
				List<String> list = new ArrayList<>();
				list.add("New");
				list.add("In-Process");

				return btCaseRepository.getCasesByStatus(list, page);
			}
			if (filtername != null && filtername.equalsIgnoreCase("To Be Invoiced")) {
				return btCaseRepository.getCasesByStage(filtername, page);
			}

			// COMMERCIAL DETAILS FILTER
			if (filtername != null && filtername.equalsIgnoreCase("Work_Order_Received"))
				return btCaseRepository.getCasesByStage("Work Order Received", page);
			if (filtername != null && filtername.equalsIgnoreCase("Invoiced"))
				return btCaseRepository.getCasesByStage(filtername, page);
			if (filtername != null && filtername.equalsIgnoreCase("QuoteSent"))
				return btCaseRepository.getCasesByStage("Quote Sent", page);
			if (filtername != null && filtername.equalsIgnoreCase("toBeInvoice"))
				return btCaseRepository.getCasesByStage("To Be Invoiced", page);
			if (filtername != null && filtername.equalsIgnoreCase("docAwaitedInTransit"))
				return btCaseRepository.getCasesByStage("Document in-Transit", page);

			return btCaseRepository.getPage(page);
		}
		if (dbProfile.hasObjectAccess(user.getUsername(), BtCase.class, UserProfileRepository.VIEW)) {
			List<String> _us = new ArrayList<String>();
			_us = dbProfile.getListOfUserAndChildByUser(user.getUsername());
			List<String> userLis = new ArrayList<String>();
			for (String u : _us) {
				userLis.add(u);
			}

			if (filtername != null && filtername.equalsIgnoreCase("Document Awaited - Customer")) {
				return btCaseRepository.getCasesByStage(filtername, userLis, page);
			}
			if (filtername != null && filtername.equalsIgnoreCase("All")) {
				return btCaseRepository.getAllCase(userLis, page);
			}
			if (filtername != null && filtername.equalsIgnoreCase("closed")) {
				List<String> list = new ArrayList<>();

				list.add("Closed");
				list.add("Rejected");

				return btCaseRepository.getCasesByStatus(list, userLis, page);
			}
			if (filtername != null && filtername.equalsIgnoreCase("open")) {
				List<String> list = new ArrayList<>();
				list.add("New");
				list.add("In-Process");

				return btCaseRepository.getCasesByStatus(list, userLis, page);
			}
			if (filtername != null && filtername.equalsIgnoreCase("To Be Invoiced")) {
				return btCaseRepository.getCasesByStage(filtername, userLis, page);
			}

			// COMMERCIAL DETAILS FILTER
			if (filtername != null && filtername.equalsIgnoreCase("Work_Order_Received"))
				return btCaseRepository.getCasesByStage("Work Order Received", userLis, page);
			if (filtername != null && filtername.equalsIgnoreCase("Invoiced"))
				return btCaseRepository.getCasesByStage(filtername, userLis, page);
			if (filtername != null && filtername.equalsIgnoreCase("QuoteSent"))
				return btCaseRepository.getCasesByStage("Quote Sent", userLis, page);
			if (filtername != null && filtername.equalsIgnoreCase("toBeInvoice"))
				return btCaseRepository.getCasesByStage("To Be Invoiced", userLis, page);
			if (filtername != null && filtername.equalsIgnoreCase("docAwaitedInTransit"))
				return btCaseRepository.getCasesByStage("Document in-Transit", userLis, page);
			return btCaseRepository.getAllCase(userLis, page);
		}
		return Page.empty();
	}

	@Override
	public Page<Map<String, Object>> getServiceAppointmentByCaseId(long caseid, Pageable page) {
		return serviceRepo.getServiceAppointmentByCaseId(caseid, page);

	}

	@Override
	public List<Map<String, Object>> getCaseForList(List<String> caseStatus) {
		return btCaseRepository.getCaseForList(caseStatus);
	}

	@Override
	@Transactional(rollbackFor = { BadRequestException.class, UnauthoriseException.class,
			Exception.class }, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
	public Map<String, Object> updateProofOfDelivery(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if (request.get("podDate") != null && request.get("podNumber") != null && request.get("caseId") != null) {
			BtCase caseDetails = btCaseRepository.getCaseWihtId(Long.parseLong(request.get("caseId").toString()));
			if (caseDetails == null) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
			}
			if (!caseDetails.getOwnerDetails().getEmailId().equalsIgnoreCase(user.getUsername())) {
				throw new com.pratikshat.exicom.exception.BadRequestException("You are not authorized");
			}
			if (caseDetails.getStatus().getId() != 2) { // Closed
				throw new com.pratikshat.exicom.exception.BadRequestException("Case is not closed");
			}
			List<Integer> list = new ArrayList<>();
			list.add(17); // Document Awaited - Customer
			list.add(18); // Document Awaited - Customer
			if (!list.contains(caseDetails.getStage().getId())) {
				throw new com.pratikshat.exicom.exception.BadRequestException("You can't update this record");
			}
			if (request.get("podDate") != null && request.get("podNumber") != null) {
				try {
					caseDetails.setPODDate(outputSdf.parse(request.get("podDate").toString()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				caseDetails.setPodShippingTrackingNumber(request.get("podShippingTrackingNumber").toString());
				caseDetails.setPodShippingExpectedDate(request.get("podShippingExpectedDate").toString());
				caseDetails.setPODNumber(request.get("podNumber").toString());
				caseDetails.setPodShippingToName(request.get("podShippingToName").toString());
				caseDetails.setPodShippingMode(request.get("podShippingMode").toString());
				if (caseDetails.getBusinessDomain().getBusinessDomainId() == 1) { // Battery
					caseDetails.setStage(new CaseStageDetails(21));
				}
				btCaseRepository.updateDetails(caseDetails, user);
				return btCaseRepository.getSingleCase(caseDetails.getId());
			} else {
				throw new com.pratikshat.exicom.exception.BadRequestException("Please fill the PO date and PO number");
			}
		} else {
			throw new com.pratikshat.exicom.exception.BadRequestException("Request parameter not filled");
		}
	}

	@Override
	public BtCaseResponse getByCaseId(LoginUser user, long caseid) throws BadRequestException, UnauthoriseException {
		boolean view = dbProfile.hasObjectAccess(user.getUsername(), BtCase.class, UserProfileRepository.VIEW);
		if (view) {
			BtCaseResponse _case = btCaseRepository.getByCaseId(caseid);
			if (_case != null) {
				_case.setProblemReported(dbCaseProblem.getByCaseId(_case.getCaseId()));
//				_case.setFileUploadResponses(dbfileUpload.getBtCaseID(_case.getCaseId()));

			}
			return _case;
		} else
			throw new UnauthoriseException("You are not authorize");

	}

	@Override
	@Transactional(rollbackFor = { BadRequestException.class, UnauthoriseException.class,
			Exception.class }, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
	public InvoiceUpdateRequest updateInvoice(InvoiceUpdateRequest req, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		Map<String, String> group = dbProfile.getAccountAndRoleGroup(user);

		if (group.get("roleGroup").equalsIgnoreCase("COMMERCIAL")) {
			BtCase _caseDetails = btCaseRepository.getCaseForInvoiceUpdate(req.getCaseNumber().trim());
			if (_caseDetails == null) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
			}
			if (req.getPODate() != null && req.getPONumber() != null) {
				_caseDetails.setPODate(req.getPODate());
				_caseDetails.setPONumber(req.getPONumber().trim());
			}
			if (req.getInvoiceDate() != null && req.getInvoiceNumber() != null) {
				if (_caseDetails.getStage().getId() != 3) { // To be Invoiced
					throw new com.pratikshat.exicom.exception.BadRequestException(
							"Case not in the to be invoiced stage");
				}
				_caseDetails.setInvoiceDate(req.getInvoiceDate());
				_caseDetails.setInvoiceNumber(req.getInvoiceNumber().trim());

				_caseDetails.setStage(new CaseStageDetails(4)); // Invoiced
			}
			if (req.getVendorInvoiceDate() != null && req.getVendorInvoiceNumber() != null) {
				_caseDetails.setVendorInvoiceDate(req.getVendorInvoiceDate());
				_caseDetails.setVendorInvoiceNumber(req.getVendorInvoiceNumber().trim());
			}
			btCaseRepository.updateDetails(_caseDetails, user);
		} else {
			throw new com.pratikshat.exicom.exception.BadRequestException("You are not authorised to edit this record");
		}
		return req;
	}

	@Override
	@Transactional(rollbackFor = { BadRequestException.class, UnauthoriseException.class,
			Exception.class }, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
	public CaseProofOfDeliveryRequest updateProofOfDelivery(CaseProofOfDeliveryRequest request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if (request.getPodDate() != null && request.getPodNumber() != null && request.getCaseId() != 0) {

			BtCase caseDetails = btCaseRepository.getAppointment(request.getCaseId());
			if (caseDetails == null) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
			}

			if (!caseDetails.getOwnerDetails().getEmailId().equalsIgnoreCase(user.getUsername())) {
				throw new com.pratikshat.exicom.exception.BadRequestException("You are not authorized");
			}

			if (caseDetails.getStatus().getId() != 2) { // Closed
				throw new com.pratikshat.exicom.exception.BadRequestException("Case is not closed");
			}

			List<Integer> list = new ArrayList<>();
			list.add(17); // Document Awaited - Customer
			list.add(18); // Document Awaited - Customer

			if (!list.contains(caseDetails.getStage().getId())) {
				throw new com.pratikshat.exicom.exception.BadRequestException("You can't update this record");
			}

			if (request.getPodDate() != null && request.getPodNumber() != null) {
				caseDetails.setPODDate(request.getPodDate());
				caseDetails.setPODNumber(request.getPodNumber());
				caseDetails.setPodShippingMode(request.getPodShippingMode());
				caseDetails.setPodShippingToName(request.getPodShippingToName());
				caseDetails.setPodShippingTrackingNumber(request.getPodShippingTrackingNumber());
				caseDetails.setPodShippingExpectedDate(request.getPodShippingExpectedDate());
				if (caseDetails.getBusinessDomain().getBusinessDomainId() == 1) { // Battery
					caseDetails.setStage(new CaseStageDetails(21));
				}
				btCaseRepository.updateDetails(caseDetails, user);
				return request;

			} else {
				throw new com.pratikshat.exicom.exception.BadRequestException("Please fill the PO date and PO number");
			}
		} else {
			throw new com.pratikshat.exicom.exception.BadRequestException("Request parameter not filled");
		}
	}

	@Override
	public void documentReceived(long caseId, LoginUser user) throws BadRequestException, UnauthoriseException {
		BtCase caseDetails = btCaseRepository.getAppointment(caseId);

		if (caseDetails != null) {
			if (caseDetails.getStatus().getId() != 2) { // Closed
				throw new com.pratikshat.exicom.exception.BadRequestException("Case not closed");
			}
			List<Integer> stageList = new ArrayList<Integer>();
			stageList.add(21); // Document in-Transit
			stageList.add(22); // Document in-Transit

			if (!stageList.contains(caseDetails.getStage().getId())) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case not in document in-transit stage");
			}

			if (caseDetails.getBusinessDomain().getBusinessDomainId() == 1) { // Battery
				caseDetails.setStage(new CaseStageDetails(3)); // To Be Invoiced
			}
			btCaseRepository.updateDetails(caseDetails, user);

		} else {
			throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
		}

	}

	@Override
	public Page<Map<String, Object>> getCaseListByStage(String stage, Pageable page, String filtername, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if (dbProfile.hasObjectAccess(user.getUsername(), BtCase.class, UserProfileRepository.VIEW_ALL)) {
			if (filtername != null)
				stage = filtername;
			if (stage != null && stage.equalsIgnoreCase("Work_Order_Received"))
				return btCaseRepository.getCasesByStage("Work_Order_Received", page);
			if (stage != null && stage.equalsIgnoreCase("QuoteSent"))
				return btCaseRepository.getCasesByStage("Quote Sent", page);
			if (stage != null && stage.equalsIgnoreCase("toBeInvoiced"))
				return btCaseRepository.getCasesByStage("To Be Invoiced", page);
			if (stage != null && stage.equalsIgnoreCase("docAwaitedInTransit"))
				return btCaseRepository.getCasesByStage("Document in-Transit", page);

			return btCaseRepository.getCasesByStage(stage, page);
		}
		if (dbProfile.hasObjectAccess(user.getUsername(), BtCase.class, UserProfileRepository.VIEW)) {
			List<String> emps = new ArrayList<String>();
			List<String> usr = dbProfile.getListOfUserAndChildByUser(user.getUsername());
			for (String u : usr) {
				emps.add(u);
			}
			if (filtername != null)
				stage = filtername;

			if (stage != null && stage.equalsIgnoreCase("Work_Order_Received"))
				return btCaseRepository.getCasesByStage("Work_Order_Received", page);
			if (stage != null && stage.equalsIgnoreCase("QuoteSent"))
				return btCaseRepository.getCasesByStage("Quote Sent", page);
			if (stage != null && stage.equalsIgnoreCase("toBeInvoiced"))
				return btCaseRepository.getCasesByStage("To Be Invoiced", page);
			if (stage != null && stage.equalsIgnoreCase("docAwaitedInTransit"))
				return btCaseRepository.getCasesByStage("Document in-Transit", page);
			return btCaseRepository.getCasesByStage(stage, emps, page);
		}
		return Page.empty();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, RuntimeException.class, Exception.class })
	public ChangeCaseTypeRequest changeCaseType(ChangeCaseTypeRequest req, LoginUser user)
			throws BadRequestException, UnauthoriseException {

		BtCase _caseDetails = btCaseRepository.getCaseForInvoiceUpdate(req.getCaseManualId().trim());

//		List<Quote> quoteList = null;
//		try {
//			Query selectQuoteQuery = entityManager.createQuery(
//					"select qt from Quote qt left join qt.quoteStatus qts left join qt.oppertunity op left join op.caseDetails cd where cd.manualId=?1",
//					Quote.class);
//			selectQuoteQuery.setParameter(1, req.getCaseManualId().trim());
//			quoteList = (List<Quote>) selectQuoteQuery.getResultList();
//		} catch (NoResultException ex) {
//		} catch (NonUniqueResultException e) {
//		}catch(Exception ex) {
//			throw new com.pratikshat.exicom.exception.BadRequestException(ex.getMessage());
//		}
//		
//		if(quoteList!=null && quoteList.size()>0) {
//			
//			List<Integer> quoteStatuslist = new ArrayList<>();
//			quoteStatuslist.add(1);	// Draft
//			quoteStatuslist.add(2);	// Work Order Received
//			quoteStatuslist.add(4);	// Quote Sent
//			quoteStatuslist.add(6);	// Submitted For Approval
//			quoteStatuslist.add(7);	// Approved
//			quoteStatuslist.add(8); // Invoiced
//			quoteStatuslist.add(10); // Approved by Store
//			
//			for(int i=0;i<quoteList.size();i++) {
//				Quote quote = quoteList.get(i);
//				if(quoteStatuslist.contains(quote.getQuoteStatus().getId())) {
//					throw new com.pratikshat.exicom.exception.BadRequestException("Quote "+quote.getQuoteName()+" is in the "+quote.getQuoteStatus().getName()+" status, Please reject the Quote");
//				}
//			}
//		}

		if (_caseDetails != null) {


			CaseTypes caseType = dbcaseTypes.getByCaseTypesNameAndVertical(req.getCaseType().trim(),
					_caseDetails.getBusinessDomain().getBusinessDomainId());

			if (caseType == null) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case type does not exist");
			}

			CaseReasonDetails caseReason = dbCaseReson.getCaseresonbyNameAndCase(req.getCaseReason().trim(),
					_caseDetails.getBusinessDomain().getBusinessDomainId(), caseType.getCaseTypeId());
			if (caseReason == null) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case reason does not exist");
			}

			ChangeCaseTypeConditions conditions = dbChangeType.getChangeCaseTypeConditions(
					_caseDetails.getCaseType().getCaseTypesName(),
					_caseDetails.getCaseReason().getCaseReasonDetailsName(), req.getCaseType().trim(),
					req.getCaseReason().trim());

			if (conditions == null) {
				throw new com.pratikshat.exicom.exception.BadRequestException("This operation is not allowed");
			}

			List<BtServiceAppointment> serviceAppointmentList = serviceRepo.getListWithCaseId(_caseDetails.getId());

			BtChangeCaseTypeLog log = new BtChangeCaseTypeLog();

			Date poDate = null;
			if (caseType != null && caseType.getCaseTypeId() == 9) { // Under AMC
				if (req.getPoNo() == null || req.getPoNo().equalsIgnoreCase("")) {
					throw new com.pratikshat.exicom.exception.BadRequestException("PO no. is blank");
				}

				if (req.getPoDate() != null) {
					try {
						String StrPoDate = outputSdf.format(req.getPoDate());
						poDate = outputSdf.parse(StrPoDate);
					} catch (Exception ex) {
						throw new com.pratikshat.exicom.exception.BadRequestException("PO date format exception");
					}
				} else {
					throw new com.pratikshat.exicom.exception.BadRequestException("PO date is null");
				}

				log.setPoNo(req.getPoNo().trim());
				log.setPoDate(poDate);

				_caseDetails.setPONumber(req.getPoNo().trim());
				_caseDetails.setPODate(poDate);
			}

			log.setCaseDetails(_caseDetails);
			log.setPreviousCaseType(_caseDetails.getCaseType());
			log.setPreviousCaseReason(_caseDetails.getCaseReason());

			log.setCurrentCaseType(caseType);
			log.setCurrentCaseReason(caseReason);

			log.setModifiedBy(new EmployeeDetails(user.getUsername()));
			log.setModifiedDate(new Date());

			_caseDetails.setCaseType(caseType);
			_caseDetails.setCaseReason(caseReason);

			btCaseRepository.updateDetails(_caseDetails, user);

			dbLog.updateDetails(log, user);

			if (serviceAppointmentList != null) {
				for (int i = 0; i < serviceAppointmentList.size(); i++) {

					BtServiceAppointment sa = serviceAppointmentList.get(i);

					sa.setCostCenter(caseReason.getCostCenter());

					serviceRepo.updateDetails(sa, user);
				}
			}
			return req;

//				}else {
//					throw new com.pratikshat.exicom.exception.BadRequestException("Case not of Without Warranty type");
//				}
//			}else {
//				throw new com.pratikshat.exicom.exception.BadRequestException("Case not closed");	
//			}
		} else {
			throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
		}
	}

	@Override
	@Transactional(rollbackFor = { BadRequestException.class, UnauthoriseException.class,
			Exception.class }, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
	public BtCaseResponse saveDetails(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		boolean create = dbProfile.hasObjectAccess(user.getUsername(), BtCase.class, UserProfileRepository.CREATE);
		BtCase btCase = new BtCase();
		if (create) {
//			btCase.setCasesubReason("casesubReason");
//			 System.out.print("===MiscellaneousUtils=="+MiscellaneousUtils.objToString(request.get("casesubReason")));
//
//		 btCase.setCasesubReason(MiscellaneousUtils.objToString(request.get("casesubReason")));
//		 
//		 
       if (request.get("assetsId") != null && !request.get("assetsId").toString().equalsIgnoreCase("")) {
				BtAssets assets = null;

			assets = dbAssets.getAssetsForAppointment(Long.parseLong(request.get("assetsId").toString()));
				BtAssetsAmcdetails amcdetails = null;
				if (assets != null) {
					btCase.setBtAssets(assets);
					amcdetails = dbAssetsAmc.getWithId(assets.getId());
					if (amcdetails.getAmcExpiryDate() != null) {
						Date expiryDate;
						try {
							expiryDate = outputSdf.parse(outputSdf.format(amcdetails.getAmcExpiryDate()));
							Date today = outputSdf.parse(outputSdf.format(new Date()));
							if (expiryDate.compareTo(today) >= 0) {
								btCase.setPODate(outputSdf.parse(amcdetails.getAmcPurchaseOrderDate()));
								btCase.setPONumber(amcdetails.getAmcPurchaseOrderNumber());
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				} else {
					throw new com.pratikshat.exicom.exception.BadRequestException("Asset does not exist");
				}

				if (assets != null && assets.getStatus() != null) {
					if (assets.getStatus().getAssetsStatusId() == 1) {
						throw new com.pratikshat.exicom.exception.BadRequestException("Asset is dismantle");
					}
				}

				// Find asset

				long checkAsset = btCaseRepository.checkAssetStatusInAllCase(assets.getId());

				if (checkAsset > 0)
					throw new BadRequestException("Case already logged on this Asset");

				// Find Asset with Open Status in BRC Case

				long checkAssetInBRC = dbBRCCase.checkAssetStatusIbBRCCase(assets.getId());

				if (checkAssetInBRC > 0)
					throw new BadRequestException("Case already logged on this Assets");

				//
			} else {
				List<Long> caseReasonList = new ArrayList<>();
				caseReasonList.add(7L); // Exceptional Case
				caseReasonList.add(8L); // Installation
				caseReasonList.add(51L); // Warehouse Health check-ups
				caseReasonList.add(48L); // Site Survey
				caseReasonList.add(47L); // Battery Bank Charging

//				if (caseReasonList.contains(Long.parseLong(request.get("caseReasonId").toString()))) {
//					/Audit 											// Installation
				if ((Integer) request.get("caseReasonId") == 7L || (Integer) request.get("caseReasonId") == 8L) {
					if (request.get("missingAssets") == null) {
						throw new com.pratikshat.exicom.exception.BadRequestException("Please fill missing asset");
					}
				}
//				} else {
//					throw new com.pratikshat.exicom.exception.BadRequestException("Please select asset");
//				}
			}

			BtSiteDetails site = null;
			if (request.get("siteId") != null) {
				int siteID = (Integer) request.get("siteId");
				site = dbSite.getSiteWithId(Long.valueOf(siteID));
				if (site != null) {
					btCase.setSite(site);
				} else {
					throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist");
				}
			} else {
				throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist");
			}

			if (request.get("assetsId") != null && !request.get("assetsId").toString().equalsIgnoreCase("")) {
				BtAssets assets = dbAssets.getAssetsForAppointment(Long.parseLong(request.get("assetsId").toString()));
				if (assets.getSiteDetails() != null) {
					if (assets.getSiteDetails().getId() != Long.parseLong(request.get("siteId").toString())) {
						throw new com.pratikshat.exicom.exception.BadRequestException(
								"Given site mismatch with the assets installed on the site");
					}
				} else {
					throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist");
				}
			}
			long contactPersonId = Long.parseLong(request.get("contactPersonId").toString());
			ContactDetails contact = dbContactdetail.getWithContactIdAndAccountId(Long.valueOf(contactPersonId),
					site.getAccount().getSapCustomerNumber());
			if (contact != null) {
				btCase.setContactPerson(contact);
			} else {
				throw new com.pratikshat.exicom.exception.BadRequestException("Contact does not exist");
			}

			if (request.get("missingAssets") != null) {

				CaseReasonDetails caseReason = dbCaseReson
						.getCaseReasonWithId(Long.parseLong(request.get("caseReasonId").toString()));
				btCase.setCaseReason(caseReason);

			} else {
				try {
					long caseTypeId = Long.parseLong(request.get("caseTypeId").toString());
					List<CaseReasonDetails> reasons = dbCaseReson.getResonWithCasetypeAndDomainId(
							Long.valueOf(caseTypeId), (int) request.get("caseRecordTypeId"));
					List<Long> reasonList = new ArrayList<>();
					for (int i = 0; i < reasons.size(); i++) {
						CaseReasonDetails reason = reasons.get(i);
						reasonList.add(reason.getReasonId());
					}
					if (request.get("caseReasonId") != null
							&& !reasonList.contains(Long.parseLong(request.get("caseReasonId").toString()))) {
						throw new com.pratikshat.exicom.exception.BadRequestException("Wrong case reason");
					}
				} catch (NoResultException ex) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Wrong case reason");
				}
				btCase.setCaseReason(new com.pratikshat.exicom.entity.CaseReasonDetails(
						Long.parseLong(request.get("caseReasonId").toString())));
			}

			if (request.get("typeChoice") != null) {
				BtAssetsTypeChoice assetsTypeChoice = dbTypeChoice
						.getTypeById(Integer.parseInt(request.get("typeChoice").toString()));
				btCase.setAssetsTypeChoice(assetsTypeChoice);
				if (assetsTypeChoice == null) {
					throw new BadRequestException("No type choice found");
				}
			}

			try {
				List<CaseStageDetails> stages = dbCaseStage.getStageWithStatusAndDomainId(
						(int) request.get("caseStatusId"), (int) request.get("caseRecordTypeId"));
				List<Integer> reasonList = new ArrayList<>();

				for (int i = 0; i < stages.size(); i++) {
					CaseStageDetails stage = stages.get(i);
					reasonList.add(stage.getId());
				}
				if (!reasonList.contains(Integer.parseInt(request.get("caseStageId").toString()))) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Wrong case stage");
				}
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Wrong case stage");
			}
			if (request.get("serviceResourceId") != null) {
				int serviceCenterId = (Integer) request.get("serviceCenterId");
				int serviceResourceId = (Integer) request.get("serviceResourceId");
				ServiceRecource serviceRecource = dbserviceRes.getServiceResourceWithIdAndServiceid(
						Long.valueOf(serviceCenterId), Long.valueOf(serviceResourceId));
				if (serviceRecource == null) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Service resource is wrong");
				}
				int serviceResource = (Integer) request.get("serviceResourceId");
				btCase.setEngineer(new com.pratikshat.exicom.entity.ServiceRecource(Long.valueOf(serviceResource)));
			}

			btCase.setCaseType(
					new com.pratikshat.exicom.entity.CaseTypes(Long.parseLong(request.get("caseTypeId").toString())));
			btCase.setCasesubReason(request.get("casesubReason").toString());
//			
//			System.out.println("++++++++");

			// Installation for Battery // Installation for DCT
			if ((Integer) request.get("caseTypeId") == 2 || (Integer) request.get("caseTypeId") == 6) {
				if (request.get("poDate") != null && !request.get("poDate").toString().equalsIgnoreCase("")) {

					try {
						btCase.setPODate(inputSdf.parse(request.get("poDate").toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if (request.get("poamount") != null) {
					btCase.setPoamount(request.get("poamount").toString());

				}
				if (request.get("lineitem") != null) {
					btCase.setLineitem(request.get("lineitem").toString());
				}
				if (request.get("poNumber") != null && !request.get("poNumber").toString().equalsIgnoreCase("")) {
					btCase.setPONumber(request.get("poNumber").toString());
				}
			}

			if ((Integer) request.get("caseStatusId") == 2 || (Integer) request.get("caseStatusId") == 4) { // Closed
				btCase.setCloseDate(new Date());
				btCase.setClosedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			}
			if (request.get("noOfSmr") != null) {
				btCase.setNoOfsmr(request.get("noOfSmr").toString());
			}
			btCase.setOrigin(new com.pratikshat.exicom.entity.CaseOriginDetails((int) request.get("caseOriginId")));
			btCase.setOwnerDetails(new EmployeeDetails(user.getUsername()));
			btCase.setPriority(
					new com.pratikshat.exicom.entity.CasePriorityDetails((int) request.get("casePriorityId")));
			btCase.setBusinessDomain(
					new com.pratikshat.exicom.entity.BusinessDomain((int) request.get("caseRecordTypeId")));
			if (request.get("remarks") != null) {
				btCase.setRemarks(request.get("remarks").toString());
			}
			btCase.setStage(new com.pratikshat.exicom.entity.CaseStageDetails((int) request.get("caseStageId")));
			btCase.setStatus(new com.pratikshat.exicom.entity.CaseStatusDetails((int) request.get("caseStatusId")));
			btCase.setSynked(false);
			if (request.get("missingAssets") != null) {
				btCase.setMissingAssets(request.get("missingAssets").toString());
			}
			if (request.get("serviceCenterId") != null) {
				int serviceCenterId = (Integer) request.get("serviceCenterId");
				btCase.setServiceCenter(new com.pratikshat.exicom.entity.LegalEntity(Long.valueOf(serviceCenterId)));
			}
			if (request.get("otherProblemReported") != null) {
				btCase.setOtherProblemReported(request.get("otherProblemReported").toString());
			}
			if (request.get("isAssignByAssignmentRule") != null) {
				btCase.setAssignByAssignmentRule((boolean) request.get("isAssignByAssignmentRule"));
			}
			if (request.get("inputFromEng") != null) {
				btCase.setInputFromEng(request.get("inputFromEng").toString());
			}
			if (request.get("internalRemark") != null) {
				btCase.setInternalRemark(request.get("internalRemark").toString());
			}
			if (request.get("RJILTT01No") != null) {
				btCase.setRJILTT01No(request.get("RJILTT01No").toString());
			}
			
			
			if(request.get("openDate")!=null) {
				
				
					btCase.setOpenDate(request.get("openDate").toString());
					
			}
			if(request.get("ticketNo")!=null) {
				
				btCase.setTicketNo(request.get("ticketNo").toString());
			}
			
			if(request.get("casesubReason")!=null) {
				
				btCase.setCasesubReason(request.get("casesubReason").toString());
			}
			
			
			if (request.get("pendingResponsibility") != null) {
				int pendingResponsibility = (Integer) request.get("pendingResponsibility");
				btCase.setPendingResponsibility(new PendingResponsibility(Long.valueOf(pendingResponsibility)));
			}
			if ((Integer) request.get("caseStatusId") == 2) { // Closed
				btCase.setCloseDate(new Date());
				btCase.setClosedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			}
			if (request.get("rma") != null) {
				btCase.setRma((boolean) request.get("rma"));
			}

			if (request.get("btProblemMaster") != null) {
				List<Integer> list = new ArrayList<>();
				list.add(1);
				list.add(2);
				list.add(3);

				if (list.contains(Integer.parseInt(request.get("btProblemMaster").toString()))) {
					btCase.setBtProblemMaster(
							new BtProblemMaster(Integer.parseInt(request.get("btProblemMaster").toString())));
				} else {
					throw new BadRequestException("No Problem Type Found");
				}
			}
			btCaseRepository.saveDetails(btCase, user);
			if (request.get("caseProblemReported") != null) {

				@SuppressWarnings("unchecked")
				List<Map<String, Object>> ls = (List<Map<String, Object>>) request.get("caseProblemReported");
				for (int i = 0; i < ls.size(); i++) {
					BtCaseProblemReported caseProblemReported = new BtCaseProblemReported();
					caseProblemReported.setProblemName(ls.get(i).get("problemName").toString());
					caseProblemReported.setCaseDetails(btCase);
					btCaseRepository.saveDetails(caseProblemReported, user);
				}
			}
			
			
			///////////////////////// Sending SMS ////////////////////////////////////////////////////////
			

			com.pratikshat.exicom.entity.TemplateDetails templateDetails = null;
   					try {
       				javax.persistence.Query smsTemplateQuery = em.createQuery(
       				"select tmpd from TemplateDetails tmpd where tmpd.active=?1 and tmpd.templateType=?2 and tmpd.businessDomain=?3 and tmpd.eventName=?4",
       				com.pratikshat.exicom.entity.TemplateDetails.class);
       				smsTemplateQuery.setParameter(1,true);
       				smsTemplateQuery.setParameter(2,com.pratikshat.exicom.entity.TemplateType.SMS);
       				smsTemplateQuery.setParameter(3,new com.pratikshat.exicom.entity.BusinessDomain(2));
       				smsTemplateQuery.setParameter(4,"ESS_Case_Log");
       				
       				templateDetails = (com.pratikshat.exicom.entity.TemplateDetails) smsTemplateQuery.getSingleResult();
   					}catch(Exception ex)
   					{
   						
   					}
       				if(templateDetails!=null)
       				{
//		    	   com.pratikshat.exicom.entity.BtSiteDetails sites=em.createQuery("select st from BtSiteDetails st where st.id=?1",com.pratikshat.exicom.entity.BtSiteDetails.class).setParameter(1, btCase.getSite().getId()).getSingleResult();
		   		   java.util.List<com.pratikshat.exicom.entity.BtCaseProblemReported> pb=em.createQuery("select pr from BtCaseProblemReported pr join pr.caseDetails cs where cs.id=?1",com.pratikshat.exicom.entity.BtCaseProblemReported.class).setParameter(1, btCase.getId()).getResultList();
		       			
		                               
		       		/*	} catch (javax.persistence.NoResultException nex) {
		       				nex.printStackTrace();
		       			} catch (javax.persistence.NonUniqueResultException e) {
		       				e.printStackTrace();
		       			}*/
		       			java.lang.StringBuilder prob=new StringBuilder();

		       			
		       			if(pb!=null)
		       			{
		       				prob.append(pb.get(0).getProblemName().toString());
//		       				for(com.pratikshat.exicom.entity.BtCaseProblemReported prb:pb)
//		       				{
//		       					prob.append(prb.getProblemName().toString()+", ");
//		                                      /* throw new com.pratikshat.exicom.exception.BadRequestException("prob="+bcase.getId().toString());*/
//		       				}
		       			}
		       			

		       				String smsMessage = null;
		       			if (templateDetails != null) {
		       				smsMessage = templateDetails.getBody();
		       				smsMessage = smsMessage.replaceFirst("<var>", btCase.getManualId());
		       				smsMessage = smsMessage.replaceFirst("<var>", btCase.getSite().getManualId());
		                              if(btCase.getBtAssets()!=null)
		                              {
		       				smsMessage = smsMessage.replaceFirst("<var>", ""+ btCase.getBtAssets().getManualId());
		                                }
		                               else
		                                {
		       				smsMessage = smsMessage.replaceFirst("<var>", ""+btCase.getMissingAssets());
		                                }

		              String msgs=prob.toString();


		               if(msgs.length()>=30){
		                        msgs=  msgs.substring(0,29);
		                        
		                 }
		                   
		                  smsMessage = smsMessage.replaceFirst("<var>", msgs);
		               /*throw new com.pratikshat.exicom.exception.BadRequestException("prob="+bcase.getId().toString());*/
		       			}
		       			

		       			if (smsMessage != null && !smsMessage.equalsIgnoreCase("")) {
		       			/*	com.pratikshat.exicom.utils.SmsNotifier sNotifier = new com.pratikshat.exicom.utils.SmsNotifier(em, "9911100679",
		       					smsMessage, btCase.getOwnerDetails().getEmailId(), templateDetails.getTemplaeCode());
		       				sNotifier.sendNotification();*/
//			com.pratikshat.exicom.utils.SmsNotifier sNotifier1 = new com.pratikshat.exicom.utils.SmsNotifier(em, "9825272799",
//		       					smsMessage, btCase.getOwnerDetails().getEmailId(), templateDetails.getTemplaeCode());
//		       				sNotifier1.sendNotification();
//		       	com.pratikshat.exicom.utils.SmsNotifier sNotifier3 = new com.pratikshat.exicom.utils.SmsNotifier(em, "9560919594",
//			       					smsMessage, btCase.getOwnerDetails().getEmailId(), templateDetails.getTemplaeCode());
//			       				sNotifier3.sendNotification();

		com.pratikshat.exicom.utils.SmsNotifier sNotifier2 = new com.pratikshat.exicom.utils.SmsNotifier(em, btCase.getSite().getCircle().getCircleManagerBattry().getMobile(),
		   					smsMessage, btCase.getSite().getCircle().getCircleManagerBattry().getEmailId(), templateDetails.getTemplaeCode());
		     			sNotifier2.sendNotification();
		       			
		       			}
       				}
		       			/*throw new com.pratikshat.exicom.exception.BadRequestException("prob="+prob);*/

		      /* 		}catch(Exception ex) {
		       			ex.printStackTrace();
		       		}*/
       				
       				
       				
//       				////////////////////////////////Sending Mail//////////////////////////////////////
//       				
//
//       				com.pratikshat.exicom.entity.TemplateDetails _template=null;
//       				try{
//       				_template=em.createQuery("select tp from TemplateDetails tp where tp.active=?1 and tp.templateType=?2 and tp.templaeCode=?3",com.pratikshat.exicom.entity.TemplateDetails.class).setParameter(1,true).setParameter(2,com.pratikshat.exicom.entity.TemplateType.MAIL).setParameter(3,"NEW_CASE_TO_SCH").getSingleResult();
//
//
//       				}
//       				catch(Exception e){
//       				}
//       				if(_template!=null)
//       				{
//       				String dataBody=_template.getBody();
//       				/*Object[] details=null;
//       				try{
//       				 details=(Object[])em.createNativeQuery("SELECT \r\n"
//       				        			+ "IFNULL(ast.`manual_id`,cs.`missing_assets`) AS serialNumber,\r\n"
//       				        			+ "	IFNULL(prd.`product_description`,'NA') AS assetsDescription,\r\n"
//       				        			+ "	IFNULL(st.`circle_circle_name`,'NA') AS circleName,\r\n"
//       				        			+ "	IFNULL(ss.`name`,'NA') AS ssaName,\r\n"
//       				        			+ "	IFNULL(st.`manual_id`,'NA') AS siteId,\r\n"
//       				        			+ "	IFNULL(st.`site_name`,'NA') AS siteName,\r\n"
//       				        			+ "	IFNULL(ct.`case_types_name`,'NA') AS caseType,\r\n"
//       				        			+ "	cs.`id` AS caseId,\r\n"
//       				        			+ "	IFNULL(cs.`manual_id`,'NA') AS caseManualId,\r\n"
//       				        			+ "	IFNULL(cs.`created_date`,NOW()) AS createdDate,\r\n"
//       				        			+ "	CONCAT(IFNULL(cus.`first_name`,' '),IFNULL(cus.`middle_name`,''),' ',IFNULL(cus.`last_name`,' ') ) AS ContactName,\r\n"
//       				        			+ "	IFNULL(cus.`mobile`,'NA') AS contactMobileNumber,\r\n"
//       				        			+ "	IFNULL(cs.`other_problem_reported`,'NA') AS otherProblemReported,\r\n"
//       				        			+ "	IFNULL(con.`email_id`,'NA') AS btschemailId,\r\n"
//       				        			+ "IFNULL((SELECT GROUP_CONCAT(pr.`problem_name`) FROM `bt_case_problem_reported` pr WHERE pr.`case_details_id`=cs.id),'NA') AS `problemReported` \r\n"
//       				        			+ "FROM bt_case cs\r\n"
//       				        			+ "JOIN `case_types` ct ON ct.case_type_id=cs.`case_type_case_type_id`\r\n"
//       				        			+ "LEFT JOIN bt_assets ast ON ast.id=cs.`bt_assets_id`\r\n"
//       				        			+ "LEFT JOIN bt_products prd ON prd.id= ast.`products_id` \r\n"
//       				        			+ "JOIN contact_details cus ON cus.contact_id= cs.`contact_person_contact_id`\r\n"
//       				        			+ "LEFT JOIN `bt_site_details` st ON st.`id`=  cs.`site_id` \r\n"
//       				        			+ "LEFT JOIN `ssa` ss ON ss.`id`=st.`ssa_id`\r\n"
//       				        			+ "LEFT JOIN `circle_details` crc ON crc.`circle_name`=st.`circle_circle_name`\r\n"
//       				        			+ "LEFT JOIN `contact_details` con ON con.contact_id= crc.`circle_manager_battry_id`\r\n"
//       				        			+ "WHERE cs.`id`=?1").setParameter(1,bcase.getId()).getSingleResult();
//       				}
//       				catch(Exception e){
//       				throw new com.pratikshat.exicom.exception.BadRequestException("RUC"+e+bcase.getId());
//       				}*/
//       				if(btCase!=null){
//       	   		   java.util.List<com.pratikshat.exicom.entity.BtCaseProblemReported> pbs=em.createQuery("select pr from BtCaseProblemReported pr join pr.caseDetails cs where cs.id=?1",com.pratikshat.exicom.entity.BtCaseProblemReported.class).setParameter(1, btCase.getId()).getResultList();
//       	   		   CaseTypes ct=em.createQuery("select ct from CaseTypes ct where ct.caseTypeId=?1",com.pratikshat.exicom.entity.CaseTypes.class)
//       	   				   .setParameter(1, Long.parseLong(request.get("caseTypeId").toString())).getSingleResult();
//                   
//		       
//		       			java.lang.StringBuilder prob=new StringBuilder();
//
//       				if(pbs!=null)
//	       			{
//	       				for(com.pratikshat.exicom.entity.BtCaseProblemReported prb:pbs)
//	       				{
//	       					prob.append(prb.getProblemName().toString()+", ");
//	       				}
//	       			}
//       					if(btCase.getBtAssets()!=null)
//       					{
//       				dataBody=dataBody.replace("{{assetsserialnumber}}",btCase.getBtAssets().getManualId());
//       				dataBody=dataBody.replace("{{partcodedescription}}",btCase.getBtAssets().getProducts().getProductDescription());
//       			
//       					}else {
//       					dataBody=dataBody.replace("{{assetsserialnumber}}",	btCase.getMissingAssets());
//           				dataBody=dataBody.replace("{{partcodedescription}}","NA");
//           				System.out.println("Missing asset"+	btCase.getMissingAssets());
//       					}
//
//       				dataBody=dataBody.replace("{{circlename}}",btCase.getSite().getCircle().getCircleName());
//       				dataBody=dataBody.replace("{{ssa}}",btCase.getSite().getSsa().getName());
//       				dataBody=dataBody.replace("{{siteManualId}}",btCase.getSite().getManualId());
//       				dataBody=dataBody.replace("{{sitename}}",btCase.getSite().getSiteName());
//       				
//       			
//       				dataBody=dataBody.replace("{{casetype}}",ct.getCaseTypesName());
//       				dataBody=dataBody.replace("{{casenumber}}",btCase.getManualId());
//       				dataBody=dataBody.replace("{{date}}",new java.util.Date().toString());
//       				String customerMName="";
//       				if(btCase.getContactPerson().getMiddleName()!=null)
//       				{
//       					 customerMName =btCase.getContactPerson().getMiddleName();
//       				}
//       				else
//       				{
//           				 customerMName ="";
//       				}
//       				
//       				String customerLName="";
//       				if(btCase.getContactPerson().getLastName()!=null)
//       				{
//       					 customerLName =btCase.getContactPerson().getLastName();
//       				}
//       				else
//       				{
//           				 customerLName ="";
//       				}
//       				dataBody=dataBody.replace("{{contactname}}",btCase.getContactPerson().getFirstName()+" "+customerMName+" "+customerLName);
//       				dataBody=dataBody.replace("{{contactnumber}}",btCase.getContactPerson().getMobile());
//       				if(btCase.getOtherProblemReported()!=null)
//       				{
//       				dataBody=dataBody.replace("{{otherproblemreported}}",btCase.getOtherProblemReported());
//
//       				}
//       				else
//       				{
//           				dataBody=dataBody.replace("{{otherproblemreported}}","NA");
//       				}
//       				dataBody=dataBody.replace("{{problemreported}}",prob);
//
//       				java.util.List<String>mailTo=new java.util.ArrayList<String>();
//       				mailTo.add("kalindi@pratikshat.com");
//       				
//       				if(mailTo.size()>0){
//       				new com.pratikshat.exicom.utils.Mailer(em, mailTo, dataBody, "New Case ID "+btCase.getManualId()+" registered" ,null, user.getUsername());
//       				}
//
//
//       				}
//
//
//       				}
       				
       				///////////////////////////////////////Mail///////////////////////////////////////////

		} else {
			throw new UnauthoriseException("You are not authorised");
		}

		/////////////////////////////////////////// BTService
		/////////////////////////////////////////// ////////////////////////////////////////////////////////
		/////////////////////////////////////////// Appointment////////////////////////////////////////////////////////
		if (btCase.getStatus() != null && btCase.getStatus().getId() == 3) {

			BtServiceAppointment appointment = new BtServiceAppointment();

			List<Long> saStatusList = new ArrayList<>();
			saStatusList.add(1L); // Unassigned
			saStatusList.add(2L); // Scheduled
			saStatusList.add(3L); // Dispatched
			List<BtServiceAppointment> serviceAppointmentList = serviceRepo.getListWithCaseIDandStatus(btCase.getId(),
					saStatusList);

			if (serviceAppointmentList != null && serviceAppointmentList.size() > 0) {
				throw new com.pratikshat.exicom.exception.BadRequestException(
						"Service Appointment already present in dispatch, unassigned or scheduled status");
			}
			BtSiteDetails btSiteDetails = dbSite.getSiteWithId(btCase.getSite().getId());
			EmployeeDetails caseCreatedBy = btCaseRepository.employeeDetails(btCase.getCreatedBy().getEmailId());
			BtAssets assets = null;
			if (btCase.getBtAssets() != null) {
				assets = dbAssets.getAssetsForAppointment(btCase.getBtAssets().getId());
			}
			appointment.setCaseDetails(btCase);
			appointment.setSyncFlag(false);
			appointment.setApprovedByFinance(false);
			appointment.setCircle(btSiteDetails.getCircle());
			if (btCase.getBtAssets() != null) {
				appointment.setAssets(assets);
				appointment.setProduct(assets.getProducts());
			}
			if (btCase.getCaseReason() != null) {
				try {
					CaseReasonDetails caseReason = dbCaseReson
							.getCaseReasonWithId(btCase.getCaseReason().getReasonId());
					appointment.setCostCenter(caseReason.getCostCenter());
				} catch (NoResultException ex) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Case reason does not exist");
				} catch (Exception e) {
					throw new com.pratikshat.exicom.exception.BadRequestException(e.getMessage());
				}
			}
			String circleManagerEmailId = null;
			if (btCase.getBusinessDomain().getBusinessDomainId() == 1) { // Battery
				if (btSiteDetails.getCircle() != null) {
					if (btSiteDetails.getCircle().getCircleManagerBattry() != null) {
						appointment.setCircleManager(btSiteDetails.getCircle().getCircleManagerBattry());
						circleManagerEmailId = btSiteDetails.getCircle().getCircleManagerBattry().getEmailId();
					}
				}
			}
			appointment.setStatus(new ServiceAppointmentStatus(3));
			appointment.setPendingResponsibility(new PendingResponsibility(1));

			String name = null;
			if (caseCreatedBy.getFirstName() != null && !caseCreatedBy.getFirstName().equalsIgnoreCase("")) {
				name = caseCreatedBy.getFirstName();
			}

			if (caseCreatedBy.getMiddleName() != null && !caseCreatedBy.getMiddleName().equalsIgnoreCase("")) {
				name = name + " " + caseCreatedBy.getMiddleName();
			}

			if (caseCreatedBy.getLastName() != null && !caseCreatedBy.getLastName().equalsIgnoreCase("")) {
				name = name + " " + caseCreatedBy.getLastName();
			}
			appointment.setCustomerRepresentativeName(name);

			if (caseCreatedBy.getContact() != null) {
				appointment.setCustomerRepresentativeContact(caseCreatedBy.getContact().getMobile());
			}

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

			Calendar cal = Calendar.getInstance();
			String time = timeFormat.format(cal.getTime());
			appointment.setEarliestStartPermittedDate(dateFormat.format(cal.getTime()));
			appointment.setEarliestStartPermittedTime(time);
			cal.add(Calendar.DATE, 30);
			appointment.setDueDate(dateFormat.format(cal.getTime()));
			appointment.setDueTime(time);
			appointment.settFlag("N");
			appointment.setBusinessDomain(btCase.getBusinessDomain());
			appointment.setClaimStatus(new ClaimStatus(2L)); // To be Claimed
			appointment.setApprovedByFinance(false);
			appointment.setRma(false);
			CaseStageDetails caseStageDetails = null;
			try {
				caseStageDetails = dbCaseStage.getCaseStageWithId(btCase.getStage().getId());
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case stage does not exist");
			}
			if (btCase.isAssignByAssignmentRule()) {
				if (caseStageDetails.getCaseStageName().equalsIgnoreCase("Assigned To Engineer")) {
					ServiceRecource serviceRecource = dbserviceRes.getServiceResourceWithCaseId(btCase.getId());
					if (serviceRecource != null) {
						appointment.setOwnerDetails(serviceRecource.getUser());
						appointment.setServiceResource(serviceRecource);
					} else {
						try {
							ServiceRecource sch = dbserviceRes.getByEmail(circleManagerEmailId);
							appointment.setOwnerDetails(sch.getUser());
							appointment.setServiceResource(sch);
						} catch (NoResultException ex) {
							throw new com.pratikshat.exicom.exception.BadRequestException("SCH does not exist");
						}
					}
				} else if (caseStageDetails.getCaseStageName().equalsIgnoreCase("Assigned To Dispatcher")) {
					try {
						ServiceRecource sch = dbserviceRes.getByEmail(circleManagerEmailId);
						appointment.setOwnerDetails(sch.getUser());
						appointment.setServiceResource(sch);
					} catch (NoResultException ex) {
						throw new com.pratikshat.exicom.exception.BadRequestException("SCH does not exist");
					}
				} else {
					try {
						ServiceRecource sch = dbserviceRes.getByEmail(circleManagerEmailId);
						appointment.setOwnerDetails(sch.getUser());
						appointment.setServiceResource(sch);
					} catch (NoResultException ex) {
						throw new com.pratikshat.exicom.exception.BadRequestException("SCH does not exist");
					}
				}

			} else {
				if (caseStageDetails.getCaseStageName().equalsIgnoreCase("Assigned To Engineer")) {
					ServiceRecource serviceRecource = dbserviceRes
							.getServiceResourceWithId(btCase.getEngineer().getServiceResourceId());
					if (serviceRecource != null) {
						appointment.setOwnerDetails(serviceRecource.getUser());
						appointment.setServiceResource(serviceRecource);
					} else {
						try {
							ServiceRecource sch = dbserviceRes.getByEmail(circleManagerEmailId);
							appointment.setOwnerDetails(sch.getUser());
							appointment.setServiceResource(sch);
						} catch (NoResultException ex) {
							throw new com.pratikshat.exicom.exception.BadRequestException("SCH does not exist");
						}
					}

				} else if (caseStageDetails.getCaseStageName().equalsIgnoreCase("Assigned To Dispatcher")) {
					try {
						ServiceRecource sch = dbserviceRes.getByEmail(circleManagerEmailId);
						appointment.setOwnerDetails(sch.getUser());
						appointment.setServiceResource(sch);
					} catch (NoResultException ex) {
						throw new com.pratikshat.exicom.exception.BadRequestException("SCH does not exist");
					}
				} else {
					try {
						ServiceRecource sch = dbserviceRes.getByEmail(circleManagerEmailId);
						appointment.setOwnerDetails(sch.getUser());
						appointment.setServiceResource(sch);
					} catch (NoResultException ex) {
						throw new com.pratikshat.exicom.exception.BadRequestException("SCH does not exist");
					}
				}
			}
			serviceRepo.saveDetails(appointment, user);
		}
		return btCaseRepository.getByCaseId(btCase.getId());
	}

	@Override
	@Transactional(rollbackFor = { BadRequestException.class, UnauthoriseException.class,
			Exception.class }, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
	public BtCaseResponse saveDetailRequest(BtCaseRequest request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		boolean create = dbProfile.hasObjectAccess(user.getUsername(), BtCase.class, UserProfileRepository.CREATE);
		BtCase btCase = new BtCase();
		if (create) {
			
//			btCase.setCasesubReason(request.getCasesubReason());

			BtAssets assets = null;
			if (request.getAssetsSerialNumber() != null) {
				assets = dbAssets.getAssestBySerialNoAndPartCode(request.getAssetsSerialNumber(),
						request.getAssetsPartCode());
				BtAssetsAmcdetails amcdetails = null;
				if (assets != null) {
					btCase.setBtAssets(assets);
					amcdetails = dbAssetsAmc.getWithId(assets.getId());
					if (amcdetails.getAmcExpiryDate() != null) {
						Date expiryDate;
						try {
							expiryDate = outputSdf.parse(outputSdf.format(amcdetails.getAmcExpiryDate()));
							Date today = outputSdf.parse(outputSdf.format(new Date()));
							if (expiryDate.compareTo(today) >= 0) {
								btCase.setPODate(outputSdf.parse(amcdetails.getAmcPurchaseOrderDate()));
								btCase.setPONumber(amcdetails.getAmcPurchaseOrderNumber());
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					
					// Find asset

					long checkAsset = btCaseRepository.checkAssetStatusInAllCase(assets.getId());

					if (checkAsset > 0)
						throw new BadRequestException("Case already logged on this Asset");

					// Find Asset with Open Status in BRC Case

					long checkAssetInBRC = dbBRCCase.checkAssetStatusIbBRCCase(assets.getId());

					if (checkAssetInBRC > 0)
						throw new BadRequestException("Case already logged on this Asset");

					//
					
				} else {
					throw new com.pratikshat.exicom.exception.BadRequestException("Asset does not exist");
				}

				if (assets != null && assets.getStatus() != null) {
					if (assets.getStatus().getAssetsStatusId() == 1) {
						throw new com.pratikshat.exicom.exception.BadRequestException("Asset is dismantle");
					}
				}
			} else {
				List<Long> caseReasonList = new ArrayList<>();
				caseReasonList.add(12L); // Exceptional Case
				caseReasonList.add(18L); // Installation
				caseReasonList.add(2L); // Exceptional Case
				caseReasonList.add(8L); // Installation
				caseReasonList.add(43L); // Warehouse Health check-ups
				caseReasonList.add(45L); // Site Survey
				caseReasonList.add(46L); // Battery Bank Charging
				caseReasonList.add(40L); // Acceptance Testing
				caseReasonList.add(42L); // SMPS Kitting Warehouse

				if ((int) request.getCaseReasonId() == 7L || (int) request.getCaseReasonId() == 8L) {
					if (request.getMissingAssets() == null) {
						throw new com.pratikshat.exicom.exception.BadRequestException("Please fill missing asset");
					}
				}
			}
			BtSiteDetails site = null;
			if (request.getSiteId() != 0) {
				int siteID = (int) request.getSiteId();
				site = dbSite.getSiteWithId(Long.valueOf(siteID));
				if (site != null) {
					btCase.setSite(site);
				} else {
					throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist");
				}
			} else {
				throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist");
			}
			if (request.getAssetsSerialNumber() != null
					&& !request.getAssetsSerialNumber().toString().equalsIgnoreCase("")) {

				BtAssets asset = dbAssets.getAssestBySerialNoAndPartCode(request.getAssetsSerialNumber(),
						request.getAssetsPartCode());

				if (assets.getId() != null && !assets.getId().toString().equalsIgnoreCase("")) {
//				BtAssets assets = dbAssets.getAssetsForAppointment(Long.parseLong(assets.getId().toString()));
					if (assets.getSiteDetails() != null) {
						if (assets.getSiteDetails().getId() != request.getSiteId()) {
							throw new com.pratikshat.exicom.exception.BadRequestException(
									"Given site mismatch with the assets installed on the site");
						}
					} else {
						throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist");
					}
				}
			}
			long contactPersonId = request.getContactPersonId();
			ContactDetails contact = dbContactdetail.getWithContactIdAndAccountId(Long.valueOf(contactPersonId),
					site.getAccount().getSapCustomerNumber());
			if (contact != null) {
				btCase.setContactPerson(contact);
			} else {
				throw new com.pratikshat.exicom.exception.BadRequestException("Contact does not exist");
			}

			if (request.getMissingAssets() != null) {

				CaseReasonDetails caseReason = dbCaseReson.getCaseReasonWithId(request.getCaseReasonId());
				btCase.setCaseReason(caseReason);

			} else {
				try {
					long caseTypeId = request.getCaseTypeId();
					List<CaseReasonDetails> reasons = dbCaseReson.getResonWithCasetypeAndDomainId(
							Long.valueOf(caseTypeId), (int) request.getCaseRecordTypeId());
					List<Long> reasonList = new ArrayList<>();
					for (int i = 0; i < reasons.size(); i++) {
						CaseReasonDetails reason = reasons.get(i);
						reasonList.add(reason.getReasonId());
					}
					if (request.getCaseReasonId() != 0 && !reasonList.contains(request.getCaseReasonId())) {
						throw new com.pratikshat.exicom.exception.BadRequestException("Wrong case reason");
					}
				} catch (NoResultException ex) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Wrong case reason");
				}
				btCase.setCaseReason(new com.pratikshat.exicom.entity.CaseReasonDetails(request.getCaseReasonId()));
			}

			if (request.getAssetsTypeChoice() != 0) {
				BtAssetsTypeChoice assetsTypeChoice = dbTypeChoice.getTypeById(request.getAssetsTypeChoice());
				btCase.setAssetsTypeChoice(assetsTypeChoice);
				if (assetsTypeChoice == null) {
					throw new BadRequestException("No type choice found");
				}
			}

			try {
				List<CaseStageDetails> stages = dbCaseStage.getStageWithStatusAndDomainId(
						(int) request.getCaseStatusId(), (int) request.getCaseRecordTypeId());
				List<Integer> reasonList = new ArrayList<>();

				for (int i = 0; i < stages.size(); i++) {
					CaseStageDetails stage = stages.get(i);
					reasonList.add(stage.getId());
				}
				if (!reasonList.contains(request.getCaseStageId())) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Wrong case stage");
				}
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Wrong case stage");
			}

			if (request.getServiceResourceId() != 0) {
				int serviceCenterId = (int) request.getServiceCenterId();
				int serviceResourceId = (int) request.getServiceResourceId();
				ServiceRecource serviceRecource = dbserviceRes.getServiceResourceWithIdAndServiceid(
						Long.valueOf(serviceCenterId), Long.valueOf(serviceResourceId));
				if (serviceRecource == null) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Service resource is wrong");
				}
				int serviceResource = (int) request.getServiceResourceId();
				btCase.setEngineer(new com.pratikshat.exicom.entity.ServiceRecource(Long.valueOf(serviceResource)));
			}
			btCase.setCaseType(new com.pratikshat.exicom.entity.CaseTypes(request.getCaseTypeId()));

//			 Installation for Battery // Installation for DCT
//			if ((int) request.getCaseTypeId() == 2 || (int) request.getCaseTypeId() == 6) {
////				if (request.getPoDate() != null && request.getPoNumber() != null) {
//					try {
//						btCase.setPODate(inputSdf.parse(request.getPoDate().toString()));
//					} catch (ParseException e) {
//						e.printStackTrace();
//					}
//					btCase.setPoamount(request.get("poamount").toString());
//					btCase.setLineitem(request.get("lineitem").toString());
//					btCase.setPONumber(request.getPodNumber().toString());
//				} else {
//					throw new com.pratikshat.exicom.exception.BadRequestException("PO details missing");
//				}
//			}

			if ((Integer) request.getCaseStatusId() == 2 || (Integer) request.getCaseStatusId() == 4) { // Closed
				btCase.setCloseDate(new Date());
				btCase.setClosedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			}
			if (request.getNoOfSmr() != null) {
				btCase.setNoOfsmr(request.getNoOfSmr().toString());
			}
			btCase.setOrigin(new com.pratikshat.exicom.entity.CaseOriginDetails(request.getCaseOriginId()));
			btCase.setOwnerDetails(new EmployeeDetails(user.getUsername()));
			btCase.setPriority(new com.pratikshat.exicom.entity.CasePriorityDetails(request.getCasePriorityId()));
			btCase.setBusinessDomain(
					new com.pratikshat.exicom.entity.BusinessDomain((int) request.getCaseRecordTypeId()));
			if (request.getRemarks() != null) {
				btCase.setRemarks(request.getRemarks().toString());
			}
			btCase.setStage(new com.pratikshat.exicom.entity.CaseStageDetails(request.getCaseStageId()));
			btCase.setStatus(new com.pratikshat.exicom.entity.CaseStatusDetails(request.getCaseStatusId()));
			btCase.setSynked(false);
			if (request.getMissingAssets() != null) {
				btCase.setMissingAssets(request.getMissingAssets().toString());
			}
			if (request.getServiceCenterId() != 0) {
				int serviceCenterId = (int) request.getServiceCenterId();
				btCase.setServiceCenter(new com.pratikshat.exicom.entity.LegalEntity(Long.valueOf(serviceCenterId)));
			}
			if (request.getOtherProblemReported() != null) {
				btCase.setOtherProblemReported(request.getOtherProblemReported().toString());
			}
			if (request.isAssignByAssignmentRule() != false) {
				btCase.setAssignByAssignmentRule((boolean) request.isAssignByAssignmentRule());
			}
			if (request.getInputFromEng() != null) {
				btCase.setInputFromEng(request.getInputFromEng().toString());
			}
			if (request.getInternalRemark() != null) {
				btCase.setInternalRemark(request.getInternalRemark().toString());
			}
			if (request.getRJILTT01No() != null) {
				btCase.setRJILTT01No(request.getRJILTT01No().toString());
			}
			if (request.getPendingResponsibility() != 0) {
				int pendingResponsibility = (int) request.getPendingResponsibility();
				btCase.setPendingResponsibility(new PendingResponsibility(Long.valueOf(pendingResponsibility)));
			}
			if (request.getCaseStatusId() == 2) { // Closed
				btCase.setCloseDate(new Date());
				btCase.setClosedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			}

			btCase.setBtProblemMaster(new BtProblemMaster(request.getProblemMaster()));

			btCaseRepository.saveDetails(btCase, user);
			if (request.getCaseProblemReported() != null) {
//
//				@SuppressWarnings("unchecked")
//				Set<BtCaseProblemReported> ls =   (Set<BtCaseProblemReported>) request.getCaseProblemReported();
				for (BtCaseProblemReported caseprblmReported : request.getCaseProblemReported()) {
					System.out.println(caseprblmReported.getProblemName() + " problem name");
					BtCaseProblemReported caseProblemReported = new BtCaseProblemReported();
					BtCaseExpectedProblems btCaseExpectedProblems = dbExpected.findByBtProblemMaster_IdAndProblemName(
							request.getProblemMaster(), caseprblmReported.getProblemName());
					if (btCaseExpectedProblems == null) {
						throw new BadRequestException("Problem Master and Problem Reported Not Match");
					}
					caseProblemReported.setProblemName(caseprblmReported.getProblemName());
					caseProblemReported.setCaseDetails(btCase);
					btCaseRepository.saveDetails(caseProblemReported, user);
				}
			}

		} else {
			throw new UnauthoriseException("You are not authorised");
		}

		/////////////////////////////////////////// BTService
		/////////////////////////////////////////// ////////////////////////////////////////////////////////
		/////////////////////////////////////////// Appointment////////////////////////////////////////////////////////
		System.out.println("status is " + btCase.getStatus());
		if (btCase.getStatus() != null && btCase.getStatus().getId() == 3) {

			BtServiceAppointment appointment = new BtServiceAppointment();

			List<Long> saStatusList = new ArrayList<>();
			saStatusList.add(1L); // Unassigned
			saStatusList.add(2L); // Scheduled
			saStatusList.add(3L); // Dispatched
			List<BtServiceAppointment> serviceAppointmentList = serviceRepo.getListWithCaseIDandStatus(btCase.getId(),
					saStatusList);

			if (serviceAppointmentList != null && serviceAppointmentList.size() > 0) {
				throw new com.pratikshat.exicom.exception.BadRequestException(
						"Service Appointment already present in dispatch, unassigned or scheduled status");
			}
			BtSiteDetails btSiteDetails = dbSite.getSiteWithId(btCase.getSite().getId());
			EmployeeDetails caseCreatedBy = btCaseRepository.employeeDetails(btCase.getCreatedBy().getEmailId());
			BtAssets assets = null;
			if (btCase.getBtAssets() != null) {
				assets = dbAssets.getAssetsForAppointment(btCase.getBtAssets().getId());
			}
			appointment.setCaseDetails(btCase);
			appointment.setSyncFlag(false);
			appointment.setApprovedByFinance(false);
			appointment.setCircle(btSiteDetails.getCircle());
			if (btCase.getBtAssets() != null) {
				appointment.setAssets(assets);
				appointment.setProduct(assets.getProducts());
			}
			if (btCase.getCaseReason() != null) {
				try {
					CaseReasonDetails caseReason = dbCaseReson
							.getCaseReasonWithId(btCase.getCaseReason().getReasonId());
					appointment.setCostCenter(caseReason.getCostCenter());
				} catch (NoResultException ex) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Case reason does not exist");
				} catch (Exception e) {
					throw new com.pratikshat.exicom.exception.BadRequestException(e.getMessage());
				}
			}
			String circleManagerEmailId = null;
			if (btCase.getBusinessDomain().getBusinessDomainId() == 1) { // Battery
				if (btSiteDetails.getCircle() != null) {
					if (btSiteDetails.getCircle().getCircleManagerBattry() != null) {
						appointment.setCircleManager(btSiteDetails.getCircle().getCircleManagerBattry());
						circleManagerEmailId = btSiteDetails.getCircle().getCircleManagerBattry().getEmailId();
					}
				}
			}
			appointment.setStatus(new ServiceAppointmentStatus(3));
			appointment.setPendingResponsibility(new PendingResponsibility(1));

			String name = null;
			if (caseCreatedBy.getFirstName() != null && !caseCreatedBy.getFirstName().equalsIgnoreCase("")) {
				name = caseCreatedBy.getFirstName();
			}

			if (caseCreatedBy.getMiddleName() != null && !caseCreatedBy.getMiddleName().equalsIgnoreCase("")) {
				name = name + " " + caseCreatedBy.getMiddleName();
			}

			if (caseCreatedBy.getLastName() != null && !caseCreatedBy.getLastName().equalsIgnoreCase("")) {
				name = name + " " + caseCreatedBy.getLastName();
			}
			appointment.setCustomerRepresentativeName(name);

			if (caseCreatedBy.getContact() != null) {
				appointment.setCustomerRepresentativeContact(caseCreatedBy.getContact().getMobile());
			}

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

			Calendar cal = Calendar.getInstance();
			String time = timeFormat.format(cal.getTime());
			appointment.setEarliestStartPermittedDate(dateFormat.format(cal.getTime()));
			appointment.setEarliestStartPermittedTime(time);
			cal.add(Calendar.DATE, 30);
			appointment.setDueDate(dateFormat.format(cal.getTime()));
			appointment.setDueTime(time);
			appointment.settFlag("N");
			appointment.setBusinessDomain(btCase.getBusinessDomain());
			appointment.setClaimStatus(new ClaimStatus(2L)); // To be Claimed
			appointment.setApprovedByFinance(false);
			appointment.setRma(false);
			CaseStageDetails caseStageDetails = null;
			try {
				caseStageDetails = dbCaseStage.getCaseStageWithId(btCase.getStage().getId());
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case stage does not exist");
			}
			if (btCase.isAssignByAssignmentRule()) {
				if (caseStageDetails.getCaseStageName().equalsIgnoreCase("Assigned To Engineer")) {
					ServiceRecource serviceRecource = dbserviceRes.getServiceResourceWithCaseId(btCase.getId());
					if (serviceRecource != null) {
						appointment.setOwnerDetails(serviceRecource.getUser());
						appointment.setServiceResource(serviceRecource);
					} else {
						try {
							ServiceRecource sch = dbserviceRes.getByEmail(circleManagerEmailId);
							appointment.setOwnerDetails(sch.getUser());
							appointment.setServiceResource(sch);
						} catch (NoResultException ex) {
							throw new com.pratikshat.exicom.exception.BadRequestException("SCH does not exist");
						}
					}
				} else if (caseStageDetails.getCaseStageName().equalsIgnoreCase("Assigned To Dispatcher")) {
					try {
						ServiceRecource sch = dbserviceRes.getByEmail(circleManagerEmailId);
						appointment.setOwnerDetails(sch.getUser());
						appointment.setServiceResource(sch);
					} catch (NoResultException ex) {
						throw new com.pratikshat.exicom.exception.BadRequestException("SCH does not exist");
					}
				} else {
					try {
						ServiceRecource sch = dbserviceRes.getByEmail(circleManagerEmailId);
						appointment.setOwnerDetails(sch.getUser());
						appointment.setServiceResource(sch);
					} catch (NoResultException ex) {
						throw new com.pratikshat.exicom.exception.BadRequestException("SCH does not exist");
					}
				}

			} else {
				if (caseStageDetails.getCaseStageName().equalsIgnoreCase("Assigned To Engineer")) {
					ServiceRecource serviceRecource = dbserviceRes
							.getServiceResourceWithId(btCase.getEngineer().getServiceResourceId());
					if (serviceRecource != null) {
						appointment.setOwnerDetails(serviceRecource.getUser());
						appointment.setServiceResource(serviceRecource);
					} else {
						try {
							ServiceRecource sch = dbserviceRes.getByEmail(circleManagerEmailId);
							appointment.setOwnerDetails(sch.getUser());
							appointment.setServiceResource(sch);
						} catch (NoResultException ex) {
							throw new com.pratikshat.exicom.exception.BadRequestException("SCH does not exist");
						}
					}

				} else if (caseStageDetails.getCaseStageName().equalsIgnoreCase("Assigned To Dispatcher")) {
					try {
						ServiceRecource sch = dbserviceRes.getByEmail(circleManagerEmailId);
						appointment.setOwnerDetails(sch.getUser());
						appointment.setServiceResource(sch);
					} catch (NoResultException ex) {
						throw new com.pratikshat.exicom.exception.BadRequestException("SCH does not exist");
					}
				} else {
					try {
						ServiceRecource sch = dbserviceRes.getByEmail(circleManagerEmailId);
						appointment.setOwnerDetails(sch.getUser());
						appointment.setServiceResource(sch);
					} catch (NoResultException ex) {
						throw new com.pratikshat.exicom.exception.BadRequestException("SCH does not exist");
					}
				}
			}
			serviceRepo.saveDetails(appointment, user);
		}
		return btCaseRepository.getByCaseId(btCase.getId());
	}

	@Override
	public List<Map<String, Object>> advanceSearch(Map<String, Object> searchRequest, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		return advanceSearch(searchRequest, user, null, 0, 0);
	}


//	@Transactional(rollbackFor = { BadRequestException.class, UnauthoriseException.class,
//			Exception.class }, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
//	@Override
//	public List<Map<String, Object>> podNewUpdate(PodRequest req, List<MultipartFile> file, LoginUser user)
//			throws BadRequestException, UnauthoriseException {
//		List<Long> val = new ArrayList<>();
//
//		for (long i : req.getCaseId()) {
//			val.add(i);
//		}
//		List<BtCase> cases = btCaseRepository.getByIDforPod(val);
//		for (BtCase case1 : cases) {
//			if (case1.isPODUploaded()) {
//				throw new BadRequestException("POD Request already In Pending..");
//			}
//			try {
//				case1.setPODDate(inputSdf.parse(req.getPodDate()));
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//			case1.setPODNumber(req.getPodNumber());
//			case1.setPodShippingMode(req.getPodShippingMode());
//			case1.setPodShippingExpectedDate(req.getPodShippingExpectedDate());
//			case1.setPodShippingToName(req.getPodShippingToName());
//			case1.setPodShippingTrackingNumber(req.getPodShippingTrackingNumber());
//			case1.setJmsNo(req.getJmsNo());
//			case1.setPODUploaded(true);
//
//			case1.setCustomer(req.getCustomer());
//			for (MultipartFile files : file) {
//				PODFileUpload fileUpload = new PODFileUpload();
//				String fileDownloadUri = null;
//				String fileName = null;
//				if (file != null) {
//					if (!file.isEmpty()) {
//						fileName = fileStorageService.storeFile(files);
//
//						fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//								.path("/api/downloadFile/").path(fileName).toUriString();
//						fileUpload.setBtCase(case1);
//						fileUpload.setFileUrl(fileDownloadUri);
//						dbfileUpload.saveDetails(fileUpload, user);
//
//						try {
//							UploadedFiles uploadedFiles = new UploadedFiles();
//							uploadedFiles.setObjectName("BtPODUpdate");
//							uploadedFiles.setObjectId(String.valueOf(case1.getId()));
//							uploadedFiles.setFileName(fileName);
//							uploadedFiles.setFileSize(files.getSize());
//							uploadedFiles.setFileContentType(files.getContentType());
//							uploadedFiles.setFileDownloadUri(fileDownloadUri);
//							uploadedFiles.setCreatedDate(new Date());
//							uploadedFiles.setCreatedBy(new EmployeeDetails(user.getUsername()));
//							btCaseRepository.fileUpload(uploadedFiles, files, user);
//
//						}
//
//						catch (Exception ex) {
//						}
//						btCaseRepository.updateDetails(case1, user);
//					}
//				}
//			}
//
//		}
//		return null;
//	}

	@Override
	public Map<String, Object> updateObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}
	
	 @Override
	    public List<Map<String,Object>> getCasesubReason() {
	        // TODO Auto-generated method stub
	        return btCaseRepository.getCasesubReason();
	    }

//	@Override
//	public ByteArrayInputStream generatePdf(LoginUser user, long id) throws Exception {
//		// TODO Auto-generated method stub
//		System.out.println(id);
//		CasePDF casepdf= new CasePDF(em,fileStorageProperties);
////		 casepdf.createPdf(user, id);
//		return casepdf.createPdf(user, id);
//	}
	

}
