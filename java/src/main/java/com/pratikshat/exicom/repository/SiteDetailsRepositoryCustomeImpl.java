package com.pratikshat.exicom.repository;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.CaseDetails;
import com.pratikshat.exicom.entity.CircleDetails;
import com.pratikshat.exicom.entity.CustomerDeatils;
import com.pratikshat.exicom.entity.GlobalSearchIndex;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.SSA;
import com.pratikshat.exicom.entity.ServiceRecource;
import com.pratikshat.exicom.entity.SiteDetails;
import com.pratikshat.exicom.entity.ZoneDetails;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;
import com.pratikshat.exicom.model.request.SiteDetailsRequest;
import com.pratikshat.exicom.model.response.SiteDetailsResponse;
@Repository
public class SiteDetailsRepositoryCustomeImpl implements SiteDetailsRepositoryCustome {

	@PersistenceContext
	javax.persistence.EntityManager entityManager;

	@Autowired WorkflowExecuter workflowExecuter;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	@Override
	public SiteDetailsRequest saveDetails(SiteDetailsRequest siteDetails, LoginUser loginUser) throws Exception {
		if(siteDetails.getSiteManualId()==null || siteDetails.getSiteManualId().trim().equalsIgnoreCase("")) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Site id is blank");
		}
		
		if(siteDetails.getAccountSapNumber()==null || siteDetails.getAccountSapNumber().trim().equalsIgnoreCase("")) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Account is blank");
		}
		
		if(siteDetails.getCustomer()== null || siteDetails.getCustomer().trim().equalsIgnoreCase("")) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Customer is blank");
		}
		
		if(siteDetails.getSiteName()==null || siteDetails.getSiteName().trim().equalsIgnoreCase("")) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Site name is blank");
		}
		
		if(siteDetails.getServiceCenterid()==0) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Service Center is blank");
		}
		
		if(siteDetails.getServiceResourceId()==0) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Service Resource is blank");
		}
		
		if(siteDetails.getCircleName()==null || siteDetails.getCircleName().trim().equalsIgnoreCase("")) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Circle is blank");
		}
		
		if(siteDetails.getSsaId()==0) {
			throw new com.pratikshat.exicom.exception.BadRequestException("SSA is blank");
		}
		
		if(siteDetails.getClusterDistrict()==null || siteDetails.getClusterDistrict().trim().equalsIgnoreCase("")) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Cluster/District is blank");
		}
		
		if(siteDetails.getSiteDescription()==null || siteDetails.getSiteDescription().trim().equalsIgnoreCase("")) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Description is blank");
		}
		
		SiteDetails siteid =null;
		try {
			Query selectSiteDetailsQuery=entityManager.createQuery("select sa from SiteDetails sa join sa.circle crl join sa.account act where sa.manualSiteId=?1",SiteDetails.class);
			selectSiteDetailsQuery.setParameter(1,siteDetails.getSiteManualId());
			siteid=(SiteDetails)selectSiteDetailsQuery.getSingleResult();
		}catch(NoResultException ex) {
			
			
		}
		if(siteid!=null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("SiteID is not exits");	
		}
		
//		SiteDetails sitename =null;
//		try {
//			Query selectSiteDetailsQuery=entityManager.createQuery("select sa from SiteDetails sa join sa.circle crl join sa.account act where sa.siteName=?1",SiteDetails.class);
//			selectSiteDetailsQuery.setParameter(1,siteDetails.getSiteName());
//			sitename=(SiteDetails)selectSiteDetailsQuery.getSingleResult();
//		}catch(NoResultException ex) {
//			
//		}
//		if(sitename!=null) {
//		throw new com.pratikshat.exicom.exception.BadRequestException("SiteName already exits");	
//		}
		
		
		SiteDetails sd =null;
		try {
			Query selectSiteDetailsQuery=entityManager.createQuery("select sa from SiteDetails sa join sa.circle crl join sa.account act where sa.manualSiteId=?1",SiteDetails.class);
			selectSiteDetailsQuery.setParameter(1,siteDetails.getSiteManualId());
			sd=(SiteDetails)selectSiteDetailsQuery.getSingleResult();
		}catch(NoResultException ex) {
			
		}
		
		ZoneDetails zone =null;
		try {
			Query selectZoneQuery=entityManager.createQuery("select zn from CircleDetails cd join cd.zone zn where cd.circleName=?1",ZoneDetails.class);
			selectZoneQuery.setParameter(1,siteDetails.getCircleName());
			zone=(ZoneDetails)selectZoneQuery.getSingleResult();
		}catch(NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Zone not found");	
		}
		
		if(zone==null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Zone not found");
		}
		
		CustomerDeatils customer =null;
		try {
			Query selectCustomerQuery=entityManager.createQuery("select cd from CustomerDeatils cd where cd.customerName=?1",CustomerDeatils.class);
			selectCustomerQuery.setParameter(1,siteDetails.getCustomer().trim());
			customer=(CustomerDeatils)selectCustomerQuery.getSingleResult();
		}catch(NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Customer not found");	
		}
		
		List<CircleDetails> circleList =null;
		try {
			Query selectCircleQuery=entityManager.createQuery("select cd from CircleDetails cd where cd.serviceCenterBattery.legalEntityId=?1 or cd.serviceCenterPower.legalEntityId=?2 or cd.serviceCenterEv.legalEntityId=?3",CircleDetails.class);
			selectCircleQuery.setParameter(1,siteDetails.getServiceCenterid());
			selectCircleQuery.setParameter(2,siteDetails.getServiceCenterid());
			selectCircleQuery.setParameter(3,siteDetails.getServiceCenterid());
			circleList=selectCircleQuery.getResultList();
		}catch(NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Circle not found");	
		}
		
		if(circleList==null || circleList.isEmpty()) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Circle not found");
		}
		
		
		if(sd==null) {
			com.pratikshat.exicom.entity.SiteDetails _siteDetails=new com.pratikshat.exicom.entity.SiteDetails();
			_siteDetails.setAccount(new com.pratikshat.exicom.entity.Accounts(siteDetails.getAccountSapNumber()));
			_siteDetails.setCircle(new com.pratikshat.exicom.entity.CircleDetails(siteDetails.getCircleName()));
			_siteDetails.setClusterDistrict(siteDetails.getClusterDistrict());
			_siteDetails.setCreateBy(new com.pratikshat.exicom.entity.EmployeeDetails(loginUser.getUsername()));
			_siteDetails.setCreatedDate(new java.util.Date());
			_siteDetails.setInfraId(siteDetails.getInfraId());
			_siteDetails.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(loginUser.getUsername()));
			_siteDetails.setModifiedDate(new java.util.Date());
			_siteDetails.setOwner(new com.pratikshat.exicom.entity.EmployeeDetails(loginUser.getUsername()));
			if(siteDetails.getPrimaryAccountSapNumber()!=null&&!siteDetails.getPrimaryAccountSapNumber().isEmpty()) {
				_siteDetails.setPrimaryAccount(new com.pratikshat.exicom.entity.Accounts(siteDetails.getPrimaryAccountSapNumber()));
			}
			else {
				_siteDetails.setPrimaryAccount(null);
			}
			
			_siteDetails.setServiceCenter(new com.pratikshat.exicom.entity.LegalEntity(siteDetails.getServiceCenterid()));
			if(siteDetails.getServiceResourceId()!=0) {
				_siteDetails.setServiceResource(new com.pratikshat.exicom.entity.ServiceRecource(siteDetails.getServiceResourceId()));	
				
			}
			else {
				
				_siteDetails.setServiceResource(null);
			}
			
			_siteDetails.setSiteAddress(siteDetails.getSiteAddress());
			_siteDetails.setSiteDescription(siteDetails.getSiteDescription());
			_siteDetails.setSiteName(siteDetails.getSiteName());
			_siteDetails.setManualSiteId(siteDetails.getSiteManualId());
			_siteDetails.setSsa(new SSA(siteDetails.getSsaId()));
			_siteDetails.setZoneZoneName(zone.getZoneName());
			_siteDetails.setCustomer(siteDetails.getCustomer());
			_siteDetails.setIndusId(siteDetails.getIndusId());
			
			workflowExecuter.executeWorkflow(entityManager, _siteDetails, "INSERT", "PRE", loginUser);

			entityManager.persist(_siteDetails);
			siteDetails.setSiteId(_siteDetails.getSiteId());
			workflowExecuter.executeWorkflow(entityManager, _siteDetails, "INSERT", "POST", loginUser);
			// Global Search Entry Point
			try {
				GlobalSearchIndex _search=new GlobalSearchIndex();
				_search.setSearchString(_siteDetails.getSiteName());
				_search.setKeyName("siteName");
				_search.setObjectName("SiteDetails");
				_search.setObjectDetailsUrl("sites/"+_siteDetails.getSiteId());
				_search.setObjectIdName("siteId");
				_search.setObjectIdValue(String.valueOf(_siteDetails.getSiteId()));
				
				entityManager.persist(_search);
				
			}catch(Exception e) {}
			
			
			// Global Search Entry Point
						try {
							GlobalSearchIndex _search=new GlobalSearchIndex();
							_search.setSearchString(_siteDetails.getManualSiteId());
							_search.setKeyName("manualSiteId");
							_search.setObjectName("SiteDetails");
							_search.setObjectDetailsUrl("sites/"+_siteDetails.getSiteId());
							_search.setObjectIdName("siteId");
							_search.setObjectIdValue(String.valueOf(_siteDetails.getSiteId()));
							
							entityManager.persist(_search);
							
						}catch(Exception e) {}
			
			return siteDetails;
		}else {
			throw new com.pratikshat.exicom.exception.BadRequestException("Site Already Exist");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SiteDetailsResponse> getAdvanceSearchReslt(Map<String, Object> keys, LoginUser user,
			List<String> userList) {
		String _q="select new com.pratikshat.exicom.model.response.SiteDetailsResponse(site) from SiteDetails site left join site.primaryAccount paccount left join site.owner owner left join site.account acou left join site.serviceCenter serv left join site.serviceResource res left join site.circle circ left join site.ssa zn where 1=1";
		
		StringBuilder builder=new StringBuilder();
		
		if(keys.get("site_id")!=null&&!keys.get("site_id").equals("")) {
			builder.append(" and site.manualSiteId like :site_id");
		}
		if(keys.get("site_name")!=null&&!keys.get("site_name").equals("")) {
			builder.append(" and site.siteName like :site_name");
		}
		if(keys.get("infra_id")!=null&&!keys.get("infra_id").equals("")) {
			builder.append(" and site.infraId like :infra_id");
		}
		if(keys.get("account")!=null&&!keys.get("account").equals("")) {
			builder.append(" and (acou.sapCustomerNumber like :account or acou.name like :account)");
		}
		if(keys.get("circle")!=null&&!keys.get("circle").equals("")) {
			builder.append(" and circ.circleName like :circle");
		}
		if(keys.get("service_center")!=null && !keys.get("service_center").equals("")) {
			builder.append(" and serv.legalEntityName like :service_center");
		}
		
		if(keys.get("customer")!=null && !keys.get("customer").equals("")) {
			builder.append(" and site.customer like :customer");
		}
		
		if(keys.get("indusId")!=null && !keys.get("indusId").equals("")) {
			builder.append(" and site.indusId like :indusId");
		}
		
		Query query=entityManager.createQuery(_q+builder.toString(),SiteDetailsResponse.class);
		
		if(keys.get("site_id")!=null&&!keys.get("site_id").equals("")) {
			//builder.append(" and site.manualSiteId like :site_id");
			query.setParameter("site_id", "%"+keys.get("site_id")+"%");
		}
		if(keys.get("site_name")!=null&&!keys.get("site_name").equals("")) {
			//builder.append(" and site.siteName like :site_name");
			query.setParameter("site_name","%"+ keys.get("site_name")+"%");
		}
		if(keys.get("infra_id")!=null&&!keys.get("infra_id").equals("")) {
			//builder.append(" and site.infraId like :infra_id");
			query.setParameter("infra_id","%"+ keys.get("infra_id")+"%");
		}
		if(keys.get("account")!=null&&!keys.get("account").equals("")) {
			//builder.append(" and (acou.sapCustomerNumber like :account or acou.name like :account)");
			query.setParameter("account","%"+ keys.get("account")+"%");
		}
		if(keys.get("circle")!=null&&!keys.get("circle").equals("")) {
			//builder.append(" and circ.circleName like :circle");
			query.setParameter("circle","%"+ keys.get("circle")+"%");
		}
		if(keys.get("service_center")!=null && !keys.get("service_center").equals("")) {
			//builder.append("and serv.legalEntityName like :service_center");
			query.setParameter("service_center","%"+ keys.get("service_center")+"%");
		}
		
		if(keys.get("customer")!=null && !keys.get("customer").equals("")) {
			query.setParameter("customer","%"+ keys.get("customer")+"%");
		}
		
		if(keys.get("indusId")!=null && !keys.get("indusId").equals("")) {
			query.setParameter("indusId","%"+ keys.get("indusId")+"%");
		}
		
		return query.getResultList();
	}
	
	
	
	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	@Override
	public SiteDetailsRequest updateSiteDetails(SiteDetailsRequest siteDetails, LoginUser loginUser) throws Exception {
		
		if(siteDetails.getSiteManualId()==null || siteDetails.getSiteManualId().trim().equalsIgnoreCase("")) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Site id is blank");
		}
		
		if(siteDetails.getAccountSapNumber()==null || siteDetails.getAccountSapNumber().trim().equalsIgnoreCase("")) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Account is blank");
		}
		
		if(siteDetails.getCustomer()== null || siteDetails.getCustomer().trim().equalsIgnoreCase("")) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Customer is blank");
		}
		
		if(siteDetails.getSiteName()==null || siteDetails.getSiteName().trim().equalsIgnoreCase("")) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Site name is blank");
		}
		
		if(siteDetails.getServiceCenterid()==0) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Service Center is blank");
		}
		
		if(siteDetails.getServiceResourceId()==0) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Service Resource is blank");
		}
		
		if(siteDetails.getCircleName()==null || siteDetails.getCircleName().trim().equalsIgnoreCase("")) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Circle is blank");
		}
		
		if(siteDetails.getSsaId()==0) {
			throw new com.pratikshat.exicom.exception.BadRequestException("SSA is blank");
		}
		
		if(siteDetails.getClusterDistrict()==null || siteDetails.getClusterDistrict().trim().equalsIgnoreCase("")) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Cluster/District is blank");
		}
		
		if(siteDetails.getSiteDescription()==null || siteDetails.getSiteDescription().trim().equalsIgnoreCase("")) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Description is blank");
		}
		
		SiteDetails sd =null;
		try {
			Query selectSiteDetailsQuery=entityManager.createQuery("select sa from SiteDetails sa join sa.circle crl join sa.account act where sa.manualSiteId=?1",SiteDetails.class);
			selectSiteDetailsQuery.setParameter(1,siteDetails.getSiteManualId());
			sd=(SiteDetails)selectSiteDetailsQuery.getSingleResult();
		}catch(NoResultException ex) {
			//dar
			ex.printStackTrace();
		}
		
		if(sd!=null && sd.getSiteId()!=siteDetails.getSiteId()) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Site with Site ID : "+siteDetails.getSiteManualId()+", Site Name : "+siteDetails.getSiteName()+", Circle : "+siteDetails.getCircleName()+", Account : "+siteDetails.getAccountSapNumber()+" Already exist");	
		}
		
		ZoneDetails zone =null;
		try {
			Query selectZoneQuery=entityManager.createQuery("select zn from CircleDetails cd left join cd.zone zn where cd.circleName=?1",ZoneDetails.class);
			selectZoneQuery.setParameter(1,siteDetails.getCircleName());
			zone=(ZoneDetails)selectZoneQuery.getSingleResult();
		}catch(NoResultException ex) {
			//dar
			ex.printStackTrace();
			throw new com.pratikshat.exicom.exception.BadRequestException("Zone not found");	
		}
		
		if(zone==null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Zone not found");
		}
		
		CustomerDeatils customer =null;
		try {
			Query selectCustomerQuery=entityManager.createQuery("select cd from CustomerDeatils cd where cd.customerName=?1",CustomerDeatils.class);
			selectCustomerQuery.setParameter(1,siteDetails.getCustomer().trim());
			customer=(CustomerDeatils)selectCustomerQuery.getSingleResult();
		}catch(NoResultException ex) {		
			ex.printStackTrace();
			throw new com.pratikshat.exicom.exception.BadRequestException("Customer not found");	
		}
		
		
		List<CircleDetails> circleList =null;
		try {
			Query selectCircleQuery=entityManager.createQuery("select cd from CircleDetails cd where cd.serviceCenterBattery.legalEntityId=?1 or cd.serviceCenterPower.legalEntityId=?2 or cd.serviceCenterEv.legalEntityId=?3",CircleDetails.class);
			selectCircleQuery.setParameter(1,siteDetails.getServiceCenterid());
			selectCircleQuery.setParameter(2,siteDetails.getServiceCenterid());
			selectCircleQuery.setParameter(3,siteDetails.getServiceCenterid());
			circleList=selectCircleQuery.getResultList();
		}catch(NoResultException ex) {
			//dar
			ex.printStackTrace();
			throw new com.pratikshat.exicom.exception.BadRequestException("Circle not found");	
		}
		
		if(circleList==null || circleList.isEmpty()) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Circle not found");
		}
		
		SiteDetails site =null;
		try {
			Query selectSiteDetailsQuery=entityManager.createQuery("select sa from SiteDetails sa join sa.circle crl join sa.account act where sa.siteId=?1",SiteDetails.class);
			selectSiteDetailsQuery.setParameter(1,siteDetails.getSiteId());
			site=(SiteDetails)selectSiteDetailsQuery.getSingleResult();
		}catch(NoResultException ex) {
			//dar
			ex.printStackTrace();
			throw new com.pratikshat.exicom.exception.BadRequestException("Site not found");
		}
		
		if(site!=null) {
			site.setAccount(new com.pratikshat.exicom.entity.Accounts(siteDetails.getAccountSapNumber()));
			site.setCircle(new com.pratikshat.exicom.entity.CircleDetails(siteDetails.getCircleName()));
			site.setClusterDistrict(siteDetails.getClusterDistrict());
			site.setInfraId(siteDetails.getInfraId());
			site.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(loginUser.getUsername()));
			site.setModifiedDate(new java.util.Date());
			site.setOwner(new com.pratikshat.exicom.entity.EmployeeDetails(loginUser.getUsername()));
			if(siteDetails.getPrimaryAccountSapNumber()!=null&&!siteDetails.getPrimaryAccountSapNumber().isEmpty()) {
				site.setPrimaryAccount(new com.pratikshat.exicom.entity.Accounts(siteDetails.getPrimaryAccountSapNumber()));
			}
			else {
				site.setPrimaryAccount(null);
			}
			
			site.setServiceCenter(new com.pratikshat.exicom.entity.LegalEntity(siteDetails.getServiceCenterid()));
			site.setServiceResource(new com.pratikshat.exicom.entity.ServiceRecource(siteDetails.getServiceResourceId()));	
			
			site.setSiteAddress(siteDetails.getSiteAddress());
			site.setSiteDescription(siteDetails.getSiteDescription());
			site.setSiteName(siteDetails.getSiteName());
			site.setManualSiteId(siteDetails.getSiteManualId());
			site.setSsa(new SSA(siteDetails.getSsaId()));
			site.setZoneZoneName(zone.getZoneName());
			site.setCustomer(siteDetails.getCustomer());
			site.setIndusId(siteDetails.getIndusId());
			workflowExecuter.executeWorkflow(entityManager, site, "UPDATE", "PRE", loginUser);
			entityManager.persist(site);
			siteDetails.setSiteId(site.getSiteId());
			

			workflowExecuter.executeWorkflow(entityManager, site, "UPDATE", "POST", loginUser);
				
			return siteDetails;
		}else {
			throw new com.pratikshat.exicom.exception.BadRequestException("Site not exist");
		}
	}

}
