package com.pratikshat.exicom.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.CircleDetails;
import com.pratikshat.exicom.entity.ContactDetails;
import com.pratikshat.exicom.entity.CountryDetails;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.GlobalSearchIndex;
import com.pratikshat.exicom.entity.LegalEntity;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.ZoneDetails;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.CaseSearchRequest;
import com.pratikshat.exicom.model.request.CircleDetailsRequest;
import com.pratikshat.exicom.model.response.AssetsResponse;
import com.pratikshat.exicom.model.response.CaseResponse;
import com.pratikshat.exicom.model.response.CircleDetailsResponse;

public class CircleDetailsRepositoryCustomeImpl implements CircleDetailsRepositoryCustome {

	@PersistenceContext
	EntityManager entityManager;
	@Autowired
	com.pratikshat.exicom.interceptor.WorkflowExecuter workflow;
	
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = { Exception.class,BadRequestException.class,UnauthoriseException.class,RuntimeException.class }, propagation = Propagation.REQUIRES_NEW)
	@Override
	public CircleDetailsRequest saveDetails(CircleDetailsRequest details,String userId, int businessDomainId) throws Exception {
		
		CircleDetails circle = null;
		try {
			Query selectCircleQuery = entityManager.createQuery("select cr from CircleDetails cr where cr.circleName=?1",CircleDetails.class);
			selectCircleQuery.setParameter(1, details.getCircleName());
			circle = (CircleDetails)selectCircleQuery.getSingleResult();
		}catch(NoResultException ex) {
			
		}
		
		if(circle!=null) {
			throw new BadRequestException("Circle already exist with name "+details.getCircleName());
		}
		
		LegalEntity legalEntity = null;
		try {
			Query selectlegalEntityQuery = entityManager.createQuery("select lg from LegalEntity lg join lg.accountOwner onr join onr.contact cnt where lg.legalEntityId=?1",LegalEntity.class);
			selectlegalEntityQuery.setParameter(1, details.getLegalEntity());
			legalEntity = (LegalEntity)selectlegalEntityQuery.getSingleResult();
		}catch(NoResultException ex) {
			throw new BadRequestException("Legal Entity does not exist");
		}catch(NonUniqueResultException ex) {
			throw new BadRequestException("Legal Entity does not exist");
		}
		
		
		CircleDetails _details=new CircleDetails();
		_details.setAccountOwner(new EmployeeDetails(userId));
		
		if(businessDomainId==1) {
			_details.setCircleManagerBattry(legalEntity.getAccountOwner().getContact());
			_details.setServiceCenterBattery(legalEntity);
			_details.setZoneBattery(new ZoneDetails(details.getZoneName()));
		}
		if(businessDomainId==2) {
			_details.setCircleManagerPower(legalEntity.getAccountOwner().getContact());
			_details.setServiceCenterPower(legalEntity);
			_details.setZone(new ZoneDetails(details.getZoneName()));
		}
		
//		if(details.getCircleManagerBattryId()!=0)
//			_details.setCircleManagerBattry(new ContactDetails(details.getCircleManagerBattryId()));
//		if(details.getCircleManagerPowerId()!=0)
//			_details.setCircleManagerPower(new ContactDetails(details.getCircleManagerPowerId()));
//		if(details.getCircleManagerEv()!=0) {
//			_details.setCircleManagerEv(new ContactDetails(details.getCircleManagerEv()));
//		}
		_details.setCircleName(details.getCircleName());
		_details.setCountry(new CountryDetails(details.getCountryCode()));
		_details.setCreateBy(new EmployeeDetails(userId));
		_details.setCreatedDate(new Date());
		_details.setLastModifyedDate(new Date());
		_details.setModifiedBy(new EmployeeDetails(userId));
		// _details.setZone(new ZoneDetails(details.getZoneName()));
		_details.setSynced(false);
		workflow.executeWorkflow(entityManager, _details, "INSERT", "PRE",new LoginUser(userId) );
		entityManager.persist(_details);
		workflow.executeWorkflow(entityManager, _details, "INSERT", "POST",new LoginUser(userId) );
		
		// Global Search Entry point
		try {
			GlobalSearchIndex _search=new GlobalSearchIndex();
			_search.setSearchString(details.getCircleName());
			_search.setKeyName("circleName");
			_search.setObjectName("CircleDetails");
			_search.setObjectDetailsUrl("circles/"+details.getCircleName());
			_search.setObjectIdName("circleName");
			_search.setObjectIdValue(details.getCircleName());
			
			entityManager.persist(_search);
			
		}catch(Exception e) {}
		
		return details;
	}
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = { Exception.class,BadRequestException.class,UnauthoriseException.class,RuntimeException.class }, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void updateCircleDetails(CircleDetailsRequest circleRequest,String userId, int businessDomainId) throws Exception{
		
		CircleDetails circle = null;
		try {
			Query selectCircleQuery = entityManager.createQuery("select cr from CircleDetails cr where cr.circleName=?1",CircleDetails.class);
			selectCircleQuery.setParameter(1, circleRequest.getCircleName());
			circle = (CircleDetails)selectCircleQuery.getSingleResult();
		}catch(NoResultException ex) {
			throw new BadRequestException("Circle does not exist with name "+circleRequest.getCircleName());
		}
		
		LegalEntity legalEntity = null;
		try {
			Query selectlegalEntityQuery = entityManager.createQuery("select lg from LegalEntity lg join lg.accountOwner onr join onr.contact cnt where lg.legalEntityId=?1",LegalEntity.class);
			selectlegalEntityQuery.setParameter(1, circleRequest.getLegalEntity());
			legalEntity = (LegalEntity)selectlegalEntityQuery.getSingleResult();
		}catch(NoResultException ex) {
			throw new BadRequestException("Legal Entity does not exist");
		}catch(NonUniqueResultException ex) {
			throw new BadRequestException("Legal Entity does not exist");
		}
		
		if(circle!=null) {
			
			circle.setAccountOwner(new EmployeeDetails(userId));
			
//			if(circleRequest.getCircleManagerBattryId()!=0) {
//				circle.setCircleManagerBattry(new ContactDetails(circleRequest.getCircleManagerBattryId()));
//			}
//			
//			if(circleRequest.getCircleManagerPowerId()!=0) {
//				circle.setCircleManagerPower(new ContactDetails(circleRequest.getCircleManagerPowerId()));
//			}
//			
//			if(circleRequest.getCircleManagerEv()!=0) {
//				circle.setCircleManagerEv(new ContactDetails(circleRequest.getCircleManagerEv()));
//			}
			
			if(businessDomainId==1) {
				circle.setCircleManagerBattry(legalEntity.getAccountOwner().getContact());
				circle.setServiceCenterBattery(legalEntity);
				circle.setZoneBattery(new ZoneDetails(circleRequest.getZoneName()));
			}
			if(businessDomainId==2) {
				circle.setCircleManagerPower(legalEntity.getAccountOwner().getContact());
				circle.setServiceCenterPower(legalEntity);
				circle.setZone(new ZoneDetails(circleRequest.getZoneName()));
			}

			circle.setCountry(new CountryDetails(circleRequest.getCountryCode()));
			circle.setLastModifyedDate(new Date());
			circle.setModifiedBy(new EmployeeDetails(userId));
			
			
			circle.setSynced(false);
			workflow.executeWorkflow(entityManager,circle, "UPDATE", "PRE",new LoginUser(userId) );
			entityManager.persist(circle);
			workflow.executeWorkflow(entityManager,circle, "UPDATE", "POST",new LoginUser(userId) );

		}
		
		entityManager.flush();
		entityManager.clear();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CircleDetailsResponse> advanceSearch(Map<String, Object> searchKeys, LoginUser user, List<String> userLists, int businessDomainId) {
		String _query="select new com.pratikshat.exicom.model.response.CircleDetailsResponse(c) from CircleDetails c left join c.serviceCenterBattery batLegal left join c.serviceCenterPower dctLegal left join c.circleManagerBattry btc  left join c.circleManagerPower dctc left join c.circleManagerEv evc  where 1=1 ";
		StringBuilder queryBuilder=new StringBuilder();
		queryBuilder.append(_query);
		if(searchKeys.get("circleName")!=null&&!searchKeys.get("circleName").equals("")) {
			queryBuilder.append(" and c.circleName like :circleName");
		}
		if(businessDomainId==1){
			if(searchKeys.get("zoneName")!=null&&!searchKeys.get("zoneName").equals("")) {
				queryBuilder.append(" and c.zone.zoneName like :zoneName");
			}
		}
		if(businessDomainId==2){
			if(searchKeys.get("zoneName")!=null&&!searchKeys.get("zoneName").equals("")) {
				queryBuilder.append(" and c.zoneBattery.zoneName like :zoneName");
			}
		}
		
		if(searchKeys.get("legalEntityName")!=null&&!searchKeys.get("legalEntityName").equals("")) {
			queryBuilder.append(" and (batLegal.legalEntityName like :legalEntityName or dctLegal.legalEntityName like :legalEntityName)");
		}
		if(searchKeys.get("circleManager")!=null&&!searchKeys.get("circleManager").equals("")) {
			queryBuilder.append(" and (concat(coalesce(btc.firstName,''),' ',coalesce(btc.middleName,''),' ',coalesce(btc.lastName)) like :circleManager"
					+ " or concat(coalesce(dctc.firstName,''),' ',coalesce(dctc.middleName,''),' ',coalesce(dctc.lastName)) like :circleManager"
					+ " or concat(coalesce(evc.firstName,''),' ',coalesce(evc.middleName,''),' ',coalesce(evc.lastName)) like :circleManager)");
		}
		
		queryBuilder.append(" and (batLegal.businessDoamin.businessDomainId=:businessDomainId or dctLegal.businessDoamin.businessDomainId=:businessDomainId)");
		
		Query query = entityManager.createQuery(queryBuilder.toString(),CircleDetailsResponse.class);
		
		if(searchKeys.get("circleName")!=null&&!searchKeys.get("circleName").equals("")) {
			query.setParameter("circleName","%"+searchKeys.get("circleName")+"%");
		}
		if(searchKeys.get("zoneName")!=null&&!searchKeys.get("zoneName").equals("")) {
			query.setParameter("zoneName", "%"+searchKeys.get("zoneName")+"%");
		}
		if(searchKeys.get("legalEntityName")!=null&&!searchKeys.get("legalEntityName").equals("")) {
			query.setParameter("legalEntityName", "%"+searchKeys.get("legalEntityName")+"%");
		}
		if(searchKeys.get("circleManager")!=null&&!searchKeys.get("circleManager").equals("")) {
			query.setParameter("circleManager", "%"+searchKeys.get("circleManager")+"%");
		}
		
		query.setParameter("businessDomainId", businessDomainId);
		
		return query.getResultList();
	}
}
