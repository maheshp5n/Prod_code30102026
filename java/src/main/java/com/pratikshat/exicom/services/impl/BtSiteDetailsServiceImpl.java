package com.pratikshat.exicom.services.impl;

import com.nimbusds.oauth2.sdk.Request;
import com.pratikshat.exicom.entity.Accounts;
import com.pratikshat.exicom.entity.BtSiteDetails;
import com.pratikshat.exicom.entity.BulkUploadAction;
import com.pratikshat.exicom.entity.Bulkupload;
import com.pratikshat.exicom.entity.BulkuploadFileDetails;
import com.pratikshat.exicom.entity.CircleDetails;
import com.pratikshat.exicom.entity.GlobalSearchIndex;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.SSA;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.response.WebMessage;
import com.pratikshat.exicom.repository.AccountsRepository;
import com.pratikshat.exicom.repository.BtSiteDetailsRepository;
import com.pratikshat.exicom.repository.GlobalSearchIndexRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtSiteDetailsService;
import com.pratikshat.exicom.services.BulkuploadService;
import com.pratikshat.exicom.utils.ExcelParser;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.PersistenceContext;

/*Author -Deepak punethaa
 * date- 02-06-2021
*/
@Service
public class BtSiteDetailsServiceImpl implements BtSiteDetailsService {

	@Autowired
	BtSiteDetailsRepository dbBtSite;
	@Autowired
	com.pratikshat.exicom.repository.SiteDetailsRecentVisitRepository dbRecent;
	@Autowired
	com.pratikshat.exicom.repository.CaseDetailsRepository dbCase;
	@Autowired
	com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	@PersistenceContext
	javax.persistence.EntityManager entityManager;
	@Autowired
	JdbcTemplate jdbc;
	@Autowired
	GlobalSearchIndexRepository dbGlobalSearch;
	@Autowired
	BulkuploadService dbBulkUploadService;
	
	@Autowired
	AccountsRepository dbAccount;

	@Value("${app.bulkupload.location}")
	String bulkuploadLocation;

	@Override
	public Map<String, Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException {
		boolean view = dbProfile.hasObjectAccess(user.getUsername(), BtSiteDetails.class, UserProfileRepository.VIEW);
		boolean viewAll = dbProfile.hasObjectAccess(user.getUsername(), BtSiteDetails.class,
				UserProfileRepository.VIEW_ALL);
		if (view || viewAll) {
			Long siteId = Long.parseLong(id.toString());
			return dbBtSite.getBySiteid(siteId);
		} else {
			throw new UnauthoriseException("You are not authorised");
		}
	}

	@Override
	public Page<Map<String, Object>> getPage(Pageable page, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		boolean view = dbProfile.hasObjectAccess(user.getUsername(), BtSiteDetails.class, UserProfileRepository.VIEW);
		boolean viewAll = dbProfile.hasObjectAccess(user.getUsername(), BtSiteDetails.class,
				UserProfileRepository.VIEW_ALL);
		if (view || viewAll) {
			return dbBtSite.getAllPage(page);
		} else
			throw new UnauthoriseException("You are not authorised");
	}

	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, RuntimeException.class, Exception.class,
			SQLException.class })
	public Map<String, Object> saveObject(Map<String, Object> siteDetails, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		boolean createPermission = dbProfile.hasObjectAccess(user.getUsername(), BtSiteDetails.class,
				UserProfileRepository.CREATE);
		if (!createPermission) {
			throw new UnauthoriseException("You are not authorised");
		}

		System.out.println("map data-- " + siteDetails);
		
		System.out.println("map data-- " + siteDetails.get("ssaId"));
		if (siteDetails.get("siteManualId") == null
				|| siteDetails.get("siteManualId").toString().trim().equalsIgnoreCase("")) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Site id is blank");
		}

		if (siteDetails.get("accountSapNumber") == null
				|| siteDetails.get("accountSapNumber").toString().trim().equalsIgnoreCase("")) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Account is blank");
		}

		if (siteDetails.get("customer") == null || siteDetails.get("customer").toString().trim().equalsIgnoreCase("")) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Customer is blank");
		}

		if (siteDetails.get("siteName") == null || siteDetails.get("siteName").toString().trim().equalsIgnoreCase("")) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Site name is blank");
		}

		if (Long.parseLong(siteDetails.get("serviceCenterid").toString()) == 0) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Service Center is blank");
		}

		if (Long.parseLong(siteDetails.get("serviceResourceId").toString()) == 0) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Service Resource is blank");
		}
		if (siteDetails.get("circleName") == null
				|| siteDetails.get("circleName").toString().trim().equalsIgnoreCase("")) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Circle is blank");
		}
		if (siteDetails.get("ssaId") == null || siteDetails.get("ssaId").toString().trim().equalsIgnoreCase("")) {
			throw new com.pratikshat.exicom.exception.BadRequestException("SSA is blank");
		}
		if (siteDetails.get("clusterDistrict") == null
				|| siteDetails.get("clusterDistrict").toString().trim().equalsIgnoreCase("")) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Cluster/District is blank");
		}

		if (siteDetails.get("siteDescription") == null
				|| siteDetails.get("siteDescription").toString().trim().equalsIgnoreCase("")) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Description is blank");
		}
		////////// Check Given Battery Site Details already Exist In Data Base
		////////// /////////////////
		BtSiteDetails bsd = dbBtSite.getExistBatterySite(siteDetails.get("siteManualId").toString());

		Map<String, Object> zone = dbBtSite.getZonDetails(siteDetails.get("circleName").toString());

		if (zone.get("zoneName") == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Zone not found");
		}

		Map<String, Object> customerExist = dbBtSite.getCustomerDetails(siteDetails.get("customer").toString());
		if (customerExist.get("customerName") == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Customer not found");
		}
		List<CircleDetails> circleList = dbBtSite
				.getCiculeList(Long.parseLong(siteDetails.get("serviceCenterid").toString()));

		if (circleList == null || circleList.size() == 0) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Circle not found");
		}
		if (circleList == null || circleList.isEmpty()) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Circle not found");
		}
		if (bsd == null) {
			com.pratikshat.exicom.entity.BtSiteDetails _btsiteDetails = new com.pratikshat.exicom.entity.BtSiteDetails();
			
			Optional<Accounts> optionalAccount=dbAccount.findById(siteDetails.get("accountSapNumber").toString());
			if(!optionalAccount.isPresent())
				throw new BadRequestException("Invalid account sap number");
			
			_btsiteDetails.setAccount(optionalAccount.get());
			_btsiteDetails.setCircle(
					new com.pratikshat.exicom.entity.CircleDetails(siteDetails.get("circleName").toString()));
			_btsiteDetails.setClusterDistrict(siteDetails.get("clusterDistrict").toString());
			_btsiteDetails.setCreatedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			_btsiteDetails.setCreatedDate(new java.util.Date());
			_btsiteDetails.setInfraId(siteDetails.get("infraId").toString());
			_btsiteDetails.setCmp(siteDetails.get("cmp").toString());
			if (siteDetails.get("rjilJcName") != null) {
				_btsiteDetails.setRjilJcName(siteDetails.get("rjilJcName").toString());
			}
			_btsiteDetails.setLastModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			_btsiteDetails.setLastModifiedDate(new java.util.Date());
			_btsiteDetails.setOwner(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			if (siteDetails.get("primaryAccountSapNumber") != null
					&& !siteDetails.get("primaryAccountSapNumber").toString().isEmpty()) {
				_btsiteDetails.setPrimaryAccount(new com.pratikshat.exicom.entity.Accounts(
						siteDetails.get("primaryAccountSapNumber").toString()));
			} else {
				_btsiteDetails.setPrimaryAccount(null);
			}

			_btsiteDetails.setServiceCenter(new com.pratikshat.exicom.entity.LegalEntity(
					Long.parseLong(siteDetails.get("serviceCenterid").toString())));
			if (Long.parseLong(siteDetails.get("serviceResourceId").toString()) != 0) {
				_btsiteDetails.setServiceResource(new com.pratikshat.exicom.entity.ServiceRecource(
						Long.parseLong(siteDetails.get("serviceResourceId").toString())));

			} else {

				_btsiteDetails.setServiceResource(null);
			}

			_btsiteDetails.setSiteAddress(siteDetails.get("siteAddress").toString());
			_btsiteDetails.setSiteDescription(siteDetails.get("siteDescription").toString());
			_btsiteDetails.setSiteName(siteDetails.get("siteName").toString());
			_btsiteDetails.setManualId(siteDetails.get("siteManualId").toString());
			_btsiteDetails.setSsa(new SSA(Long.parseLong(siteDetails.get("ssaId").toString())));
			_btsiteDetails.setZoneZoneName(zone.get("zoneName").toString());
			_btsiteDetails.setCustomer(siteDetails.get("customer").toString());
			
			dbBtSite.saveDetails(_btsiteDetails, user);
	
			// entityManager.persist(_btsiteDetails);

			// siteDetails.setSiteId(_btsiteDetails.getId());

			// Global Search Entry Point
			try {
				GlobalSearchIndex _search = new GlobalSearchIndex();
				_search.setSearchString(_btsiteDetails.getSiteName());
				_search.setKeyName("siteName");
				_search.setObjectName("SiteDetails");
				_search.setObjectDetailsUrl("sites/" + _btsiteDetails.getId());
				_search.setObjectIdName("siteId");
				_search.setObjectIdValue(String.valueOf(_btsiteDetails.getId()));

				// entityManager.persist(_search);
				dbGlobalSearch.save(_search);
				siteDetails.put("siteId", _btsiteDetails.getId());
			} catch (Exception e) {
			}

			// Global Search Entry Point
			try {
				GlobalSearchIndex _search = new GlobalSearchIndex();
				_search.setSearchString(_btsiteDetails.getManualId());
				_search.setKeyName("manualSiteId");
				_search.setObjectName("SiteDetails");
				_search.setObjectDetailsUrl("sites/" + _btsiteDetails.getId());
				_search.setObjectIdName("siteId");
				_search.setObjectIdValue(String.valueOf(_btsiteDetails.getId()));
				dbGlobalSearch.save(_search);
				// entityManager.persist(_search);

			} catch (Exception e) {
			}

			return dbBtSite.getBySiteid(_btsiteDetails.getId());
		} else {
			throw new com.pratikshat.exicom.exception.BadRequestException("Battery Site Already Exist");
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, RuntimeException.class, Exception.class,
			SQLException.class })
	@Override
	public Map<String, Object> updateObject(Map<String, Object> siteDetails, LoginUser user)
			throws BadRequestException, UnauthoriseException {

		if (dbBtSite.countById(Long.parseLong(siteDetails.get("siteId").toString())) == 0) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Site not exist");
		}
		if (siteDetails.get("siteManualId") == null
				|| siteDetails.get("siteManualId").toString().trim().equalsIgnoreCase("")) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Site id is blank");
		}

		if (siteDetails.get("accountSapNumber") == null
				|| siteDetails.get("accountSapNumber").toString().trim().equalsIgnoreCase("")) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Account is blank");
		}

		if (siteDetails.get("customer") == null || siteDetails.get("customer").toString().trim().equalsIgnoreCase("")) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Customer is blank");
		}

		if (siteDetails.get("siteName") == null || siteDetails.get("siteName").toString().trim().equalsIgnoreCase("")) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Site name is blank");
		}

		if (Long.parseLong(siteDetails.get("serviceCenterid").toString()) == 0) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Service Center is blank");
		}

		if (Long.parseLong(siteDetails.get("serviceResourceId").toString()) == 0) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Service Resource is blank");
		}

		if (siteDetails.get("circleName") == null
				|| siteDetails.get("circleName").toString().trim().equalsIgnoreCase("")) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Circle is blank");
		}

		if (Long.parseLong(siteDetails.get("ssaId").toString()) == 0) {
			throw new com.pratikshat.exicom.exception.BadRequestException("SSA is blank");
		}

		if (siteDetails.get("clusterDistrict") == null
				|| siteDetails.get("clusterDistrict").toString().trim().equalsIgnoreCase("")) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Cluster/District is blank");
		}

		if (siteDetails.get("siteDescription") == null
				|| siteDetails.get("siteDescription").toString().trim().equalsIgnoreCase("")) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Description is blank");
		}

		BtSiteDetails bsd =  dbBtSite.getExistBatterySite(siteDetails.get("siteManualId").toString()); 
		
				
//		System.out.println("siteId" + siteDetails.get("siteId") + "  ,dbsiteId" + bsd.getId());
		if (bsd != null && bsd.getId() != Long.parseLong(siteDetails.get("siteId").toString())) {
			throw new com.pratikshat.exicom.exception.BadRequestException(
					"Site with Site ID : " + siteDetails.get("siteManualId") + ", Site Name : "
							+ siteDetails.get("siteName") + ", Circle : " + siteDetails.get("circleName")
							+ ", Account : " + siteDetails.get("accountSapNumber") + " Already exist");
		}
		Map<String, Object> zone = dbBtSite.getZonDetails(siteDetails.get("circleName").toString());

		if (zone == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Zone not found");
		}

		Map<String, Object> customer = dbBtSite.getCustomerDetails(siteDetails.get("customer").toString());
		if (customer == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Customer not found");
		}

		List<CircleDetails> circleList = dbBtSite
				.getCiculeList(Long.parseLong(siteDetails.get("serviceCenterid").toString()));

		if (circleList == null || circleList.isEmpty()) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Circle not found");
		}

		if (siteDetails.get("siteId")!= null) {
			com.pratikshat.exicom.entity.BtSiteDetails _btsiteDetails =  dbBtSite.getBySiteID(siteDetails.get("siteId").toString());
			_btsiteDetails.setAccount(
					new com.pratikshat.exicom.entity.Accounts(siteDetails.get("accountSapNumber").toString()));
			_btsiteDetails.setCircle(
					new com.pratikshat.exicom.entity.CircleDetails(siteDetails.get("circleName").toString()));
			_btsiteDetails.setClusterDistrict(siteDetails.get("clusterDistrict").toString());
			_btsiteDetails.setCreatedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			_btsiteDetails.setCreatedDate(new java.util.Date());
			_btsiteDetails.setInfraId(siteDetails.get("infraId").toString());
			_btsiteDetails.setCmp(siteDetails.get("cmp").toString());
			if (siteDetails.get("rjilJcName") != null) {
				_btsiteDetails.setRjilJcName(siteDetails.get("rjilJcName").toString());
			}
			_btsiteDetails.setLastModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			_btsiteDetails.setLastModifiedDate(new java.util.Date());
			_btsiteDetails.setOwner(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			if (siteDetails.get("primaryAccountSapNumber") != null
					&& !siteDetails.get("primaryAccountSapNumber").toString().isEmpty()) {
				_btsiteDetails.setPrimaryAccount(new com.pratikshat.exicom.entity.Accounts(
						siteDetails.get("primaryAccountSapNumber").toString()));
			} else {
				_btsiteDetails.setPrimaryAccount(null);
			}

			_btsiteDetails.setServiceCenter(new com.pratikshat.exicom.entity.LegalEntity(
					Long.parseLong(siteDetails.get("serviceCenterid").toString())));
			if (Long.parseLong(siteDetails.get("serviceResourceId").toString()) != 0) {
				_btsiteDetails.setServiceResource(new com.pratikshat.exicom.entity.ServiceRecource(
						Long.parseLong(siteDetails.get("serviceResourceId").toString())));
			} else {

				_btsiteDetails.setServiceResource(null);
			}
			_btsiteDetails.setSiteAddress(siteDetails.get("siteAddress").toString());
			_btsiteDetails.setSiteDescription(siteDetails.get("siteDescription").toString());
			_btsiteDetails.setSiteName(siteDetails.get("siteName").toString());
			_btsiteDetails.setManualId(siteDetails.get("siteManualId").toString());
			_btsiteDetails.setSsa(new SSA(Long.parseLong(siteDetails.get("ssaId").toString())));
			_btsiteDetails.setZoneZoneName(zone.get("zoneName").toString());
			_btsiteDetails.setCustomer(siteDetails.get("customer").toString());
			_btsiteDetails.setId(Long.parseLong(siteDetails.get("siteId").toString()));
			// entityManager.persist(_btsiteDetails);
			dbBtSite.updateDetails(_btsiteDetails, user);
			siteDetails.put("siteId", _btsiteDetails.getId());
			return dbBtSite.getBySiteid(_btsiteDetails.getId());
		} else {
			throw new com.pratikshat.exicom.exception.BadRequestException("Battery Site not exist");
		}
	}

	@Override
	public List<Map<String, Object>> searchForList(String search, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if (search != null && !search.equalsIgnoreCase("")) {

			Page<Map<String, Object>> list = dbBtSite.getSiteBySiteNameOrSiteManualId("%" + search + "%", PageRequest.of(0, 20));

			return list.getContent();

		} else {
			return new ArrayList<Map<String, Object>>();
		}
	}

	@Override
	public List<Map<String, Object>> advanceSearch(Map<String, Object> searchKey, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		List<Object> value = new ArrayList<>();
		long serviceCenterId;
		String _query = "SELECT btsite.id AS siteId,btsite.manual_id AS siteManualId,btsite.site_name AS siteName,btsite.infra_id AS infraId,btsite.cluster_district AS clusterDistrict,btsite.site_description AS siteDescription,btsite.site_address AS siteAddress,\r\n"
				+ "							btsite.zone_zone_name AS zoneName,btsite.customer AS customer,btsite.last_modified_date AS lastModifiedDate,\r\n"
				+ "							btsite.created_date AS createdDate,\r\n"
				+ "							paccount.sap_customer_number AS primaryAccountSapNumber ,paccount.name AS primaryAccountName,\r\n"
				+ "							owner.email_id AS ownerEmailId,owner.first_name AS ownerFirstName,owner.middle_name AS ownerMiddleName,\r\n"
				+ "							owner.last_name AS ownerLastName,account.sap_customer_number AS accountSapNumber ,\r\n"
				+ "							account.name AS accountName,serv.legal_entity_id AS serviceCenterId,serv.legal_entity_name AS serviceCenterName,ser.service_resource_id AS serviceResourceId,\r\n"
				+ "							ser.name AS serviceResourceName ,circ.circle_name AS circleName,circ.circle_name AS circleId,ssa.id AS ssaId ,\r\n"
				+ "							ssa.name AS ssaName,crdby.email_id AS createdEmailId,crdby.first_name AS createdFirstName,crdby.middle_name AS createdMiddleName,\r\n"
				+ "							crdby.last_name AS createdLastName,mody.first_name AS lastModifyFirstName,mody.middle_name AS lastModifyMiddleName,\r\n"
				+ "							mody.last_name AS lastModifyLastName,mody.email_id AS lastModifyEmailId  FROM bt_site_details btsite LEFT JOIN\r\n"
				+ "							accounts paccount ON paccount.sap_customer_number=btsite.primary_account_sap_customer_number  JOIN employee_details `owner` ON owner.email_id=btsite.owner_email_id\r\n"
				+ "							JOIN accounts `account` ON account.sap_customer_number=btsite.account_sap_customer_number JOIN legal_entity serv ON serv.legal_entity_id=btsite.service_center_legal_entity_id  \r\n"
				+ "							JOIN service_recource ser ON ser.service_resource_id= btsite.service_resource_service_resource_id  JOIN circle_details circ\r\n"
				+ "							ON circ.circle_name=btsite.circle_circle_name JOIN ssa ssa ON ssa.id=btsite.ssa_id JOIN employee_details crdby ON crdby.email_id=btsite.created_by_email_id\r\n"
				+ "							JOIN employee_details mody ON mody.email_id=btsite.last_modified_by_email_id where 1=1";

		StringBuilder builder = new StringBuilder();
		builder.append(_query);

		if (searchKey.get("manualId") != null && !searchKey.get("manualId").equals("")) {
			builder.append(" and btsite.`manual_id` like ?");
			value.add("%" + searchKey.get("manualId") + "%");
		}
		if (searchKey.get("siteName") != null && !searchKey.get("siteName").equals("")) {
			builder.append(" and btsite.site_name like ?");
			value.add("%" + searchKey.get("siteName") + "%");

			builder.append(" and btsite.`site_name` like ?");
			value.add("%" + searchKey.get("siteName") + "%");
		}
		if (searchKey.get("infraId") != null && !searchKey.get("infraId").equals("")) {
			builder.append(" and btsite.`infra_id` like ?");
			value.add("%" + searchKey.get("infraId") + "%");
		}
		if (searchKey.get("account") != null && !searchKey.get("account").equals("")) {
			builder.append(" and account.`name` like ?");
			value.add("%" + searchKey.get("account") + "%");
		}
		if (searchKey.get("circle") != null && !searchKey.get("circle").equals("")) {
			builder.append(" and circ.`circle_name` like ?");
			value.add("%" + searchKey.get("circle") + "%");
		}
		if (searchKey.get("serviceCenter") != null && searchKey.get("serviceCenter").equals("")) {
			builder.append(" and serv.`legal_entity_name` like ?");
			value.add("%" + searchKey.get("serviceCenter") + "%");
		}
		return jdbc.queryForList(builder.toString(), value.toArray());
	}

}
