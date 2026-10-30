package com.pratikshat.exicom.repository;

import java.sql.SQLException;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.BusinessDomain;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.PriceBookEntries;
import com.pratikshat.exicom.entity.QuotePdfHeaderAndFooter;
import com.pratikshat.exicom.entity.StateProvinceDetails;
import com.pratikshat.exicom.entity.StateWithQuotePdf;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.QuotePdfHeaderAndFooterRequest;

@Repository
public class QuotePdfHeaderAndFooterRepositoryCustomImpl implements QuotePdfHeaderAndFooterRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, RuntimeException.class, Exception.class,
			SQLException.class })
	@Override
	public QuotePdfHeaderAndFooterRequest createTemplate(QuotePdfHeaderAndFooterRequest request, LoginUser user)
			throws Exception {

		QuotePdfHeaderAndFooter templateExist = null;
		try {
			Query selectQuoteTemplateQuery = entityManager.createQuery(
					"select qtm from QuotePdfHeaderAndFooter qtm where qtm.templateName=?1",
					QuotePdfHeaderAndFooter.class);
			selectQuoteTemplateQuery.setParameter(1, request.getTemplateName());
			templateExist = (QuotePdfHeaderAndFooter) selectQuoteTemplateQuery.getSingleResult();
		} catch (NoResultException ex) {

		}

		if (templateExist != null) {
			throw new com.pratikshat.exicom.exception.BadRequestException(
					"Quote template already exist with name " + request.getTemplateName());
		}

		QuotePdfHeaderAndFooter template = new QuotePdfHeaderAndFooter();
		template.setTemplateName(request.getTemplateName());
		template.setLogo(request.getLogo());
		template.setSignature(request.getSignature());
		template.setCompanyName(request.getCompanyName());
		template.setAddressLine1(request.getAddressLine1());
		template.setAddressLine2(request.getAddressLine2());
		template.setCity(request.getCity());
		template.setPincode(request.getPincode());
		template.setStateName(request.getStateName());
		template.setCin(request.getCin());
		template.setDocumentNumber(request.getDocumentNumber());
		template.setTelephone(request.getTelephone());
		template.setGstNumber(request.getGstNumber());
		template.setWebsite(request.getWebsite());
		template.setFooterline1(request.getFooterline1());
		template.setFooterline2(request.getFooterline2());
		template.setFooterline3(request.getFooterline3());
		template.setFooterline4(request.getFooterline4());
		template.setFooterline5(request.getFooterline5());
		template.setCreatedBy(new EmployeeDetails(user.getUsername()));
		template.setCreatedDate(new Date());
		template.setModifiedBy(new EmployeeDetails(user.getUsername()));
		template.setModifiedDate(new Date());
		template.setActive(request.isActive());
		template.setBusinessDomain(new BusinessDomain(request.getBusinessDomainId()));
		entityManager.persist(template);
		
		request.setId(template.getId());

		if (request.getStateList() != null && !request.getStateList().isEmpty()) {

			for (int i = 0; i < request.getStateList().size(); i++) {

				StateWithQuotePdf stateWithQuotePdf = null;
				try {
					Query selectState = entityManager.createQuery("select qtf from StateWithQuotePdf qtf join qtf.state st join qtf.businessDomain bd where st.stateId=?1 and bd.businessDomainId=?2");
					selectState.setParameter(1, request.getStateList().get(i));
					selectState.setParameter(2, request.getBusinessDomainId());
					stateWithQuotePdf = (StateWithQuotePdf) selectState.getSingleResult();	
				}catch(Exception ex) {
					
				}
				
				if(stateWithQuotePdf!=null) {
					stateWithQuotePdf.setQuotePdfHeaderAndFooter(new QuotePdfHeaderAndFooter(template.getId()));
					stateWithQuotePdf.setModifiedBy(new EmployeeDetails(user.getUsername()));
					stateWithQuotePdf.setModifiedDate(new Date());
					entityManager.persist(stateWithQuotePdf);
				}else {
				
					StateWithQuotePdf state = new StateWithQuotePdf();
					state.setQuotePdfHeaderAndFooter(new QuotePdfHeaderAndFooter(template.getId()));
					state.setState(new StateProvinceDetails(request.getStateList().get(i)));
					state.setCreatedBy(new EmployeeDetails(user.getUsername()));
					state.setCreatedDate(new Date());
					state.setModifiedBy(new EmployeeDetails(user.getUsername()));
					state.setModifiedDate(new Date());
					state.setBusinessDomain(new BusinessDomain(request.getBusinessDomainId()));
					entityManager.persist(state);
				}
			}
		}
		
		entityManager.flush();
		entityManager.clear();

		return request;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, RuntimeException.class, Exception.class,
			SQLException.class })
	@Override
	public QuotePdfHeaderAndFooterRequest updateTemplate(QuotePdfHeaderAndFooterRequest request, LoginUser user)
			throws BadRequestException,UnauthoriseException {

		QuotePdfHeaderAndFooter template = null;
		try {
			Query selectQuoteTemplateQuery = entityManager.createQuery(
					"select qtm from QuotePdfHeaderAndFooter qtm where qtm.id=?1",
					QuotePdfHeaderAndFooter.class);
			selectQuoteTemplateQuery.setParameter(1, request.getId());
			template = (QuotePdfHeaderAndFooter) selectQuoteTemplateQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new BadRequestException("Quote template does not exists");
		}

		if (template == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException(
					"Quote template does not exist");
		}

		
		template.setTemplateName(request.getTemplateName());
		template.setLogo(request.getLogo());
		template.setSignature(request.getSignature());
		template.setCompanyName(request.getCompanyName());
		template.setAddressLine1(request.getAddressLine1());
		template.setAddressLine2(request.getAddressLine2());
		template.setCity(request.getCity());
		template.setPincode(request.getPincode());
		template.setStateName(request.getStateName());
		template.setCin(request.getCin());
		template.setDocumentNumber(request.getDocumentNumber());
		template.setTelephone(request.getTelephone());
		template.setGstNumber(request.getGstNumber());
		template.setWebsite(request.getWebsite());
		template.setFooterline1(request.getFooterline1());
		template.setFooterline2(request.getFooterline2());
		template.setFooterline3(request.getFooterline3());
		template.setFooterline4(request.getFooterline4());
		template.setFooterline5(request.getFooterline5());
		template.setCreatedBy(new EmployeeDetails(user.getUsername()));
		template.setCreatedDate(new Date());
		template.setModifiedBy(new EmployeeDetails(user.getUsername()));
		template.setModifiedDate(new Date());
		template.setActive(request.isActive());
		template.setBusinessDomain(new BusinessDomain(request.getBusinessDomainId()));
		entityManager.persist(template);

		request.setId(template.getId());
		
		try {
			Query deleteStates = entityManager.createQuery("delete StateWithQuotePdf qtf where qtf.quotePdfHeaderAndFooter.id=?1");
			deleteStates.setParameter(1, request.getId());
			deleteStates.executeUpdate();	
		}catch(Exception ex) {
			
		}
		

		if (request.getStateList() != null && !request.getStateList().isEmpty()) {

			for (int i = 0; i < request.getStateList().size(); i++) {

				StateWithQuotePdf stateWithQuotePdf = null;
				try {
					Query selectState = entityManager.createQuery("select qtf from StateWithQuotePdf qtf join qtf.state st join qtf.businessDomain bd where st.stateId=?1 and bd.businessDomainId=?2");
					selectState.setParameter(1, request.getStateList().get(i));
					selectState.setParameter(2, request.getBusinessDomainId());
					stateWithQuotePdf = (StateWithQuotePdf) selectState.getSingleResult();	
				}catch(Exception ex) {
					
				}
				
				if(stateWithQuotePdf!=null) {
					stateWithQuotePdf.setQuotePdfHeaderAndFooter(new QuotePdfHeaderAndFooter(template.getId()));
					stateWithQuotePdf.setModifiedBy(new EmployeeDetails(user.getUsername()));
					stateWithQuotePdf.setModifiedDate(new Date());
					entityManager.persist(stateWithQuotePdf);
				}else {
				
					StateWithQuotePdf state = new StateWithQuotePdf();
					state.setQuotePdfHeaderAndFooter(new QuotePdfHeaderAndFooter(template.getId()));
					state.setState(new StateProvinceDetails(request.getStateList().get(i)));
					state.setCreatedBy(new EmployeeDetails(user.getUsername()));
					state.setCreatedDate(new Date());
					state.setModifiedBy(new EmployeeDetails(user.getUsername()));
					state.setModifiedDate(new Date());
					state.setBusinessDomain(new BusinessDomain(request.getBusinessDomainId()));
					entityManager.persist(state);
				}
			}
		}

	//	entityManager.flush();
	//	entityManager.clear();
		
		return request;
	}

}
