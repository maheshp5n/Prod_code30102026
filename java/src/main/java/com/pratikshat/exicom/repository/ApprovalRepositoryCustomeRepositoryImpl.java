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

import com.pratikshat.exicom.entity.Approval;
import com.pratikshat.exicom.entity.ApprovalSteps;
import com.pratikshat.exicom.entity.BusinessDomain;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.ObjectDefinition;
import com.pratikshat.exicom.entity.ObjectFieldDefinition;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.ApprovalRuleRequest;
import com.pratikshat.exicom.model.request.ApprovalStepRequest;

@Repository
public class ApprovalRepositoryCustomeRepositoryImpl implements ApprovalRepositoryCustomeRepository{

	@PersistenceContext
	EntityManager entityManager;
	
	
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			Exception.class, RuntimeException.class,BadRequestException.class,UnauthoriseException.class, SQLException.class })
	@Override
	public ApprovalRuleRequest createApprovalRule(ApprovalRuleRequest approvalRuleRequest, LoginUser user)
			throws Exception {

		try {
			
			ObjectDefinition objectDefinition = null;
			try {
				Query selectObjectDefinitionQuery = entityManager.createQuery(
						"select obj from ObjectDefinition obj where obj.objectName=?1", ObjectDefinition.class);
				selectObjectDefinitionQuery.setParameter(1, approvalRuleRequest.getObjectClass());
				objectDefinition = (ObjectDefinition) selectObjectDefinitionQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Object does not exist");
			}

			if (objectDefinition == null) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Object does not exist");
			}
			
			
			ObjectFieldDefinition objectFieldDefinition = null;
			try {
				Query selectObjectFieldQuery = entityManager.createQuery(
						"select fld from ObjectFieldDefinition fld join fld.objectDef obj where obj.objectName=?1 and fld.keyName=?2", ObjectFieldDefinition.class);
				selectObjectFieldQuery.setParameter(1, objectDefinition.getObjectName());
				selectObjectFieldQuery.setParameter(2, "PK");
				objectFieldDefinition = (ObjectFieldDefinition) selectObjectFieldQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Object field does not exist");
			}

			if (objectFieldDefinition == null) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Object field does not exist");
			}
			
			StringBuilder sb = new StringBuilder();
			for(int i=0; i<approvalRuleRequest.getInitialSubmitterList().size(); i++) {
				sb.append(approvalRuleRequest.getInitialSubmitterList().get(i));
				if(approvalRuleRequest.getInitialSubmitterList().size()-i!=1) {
					sb.append(",");
				}
			}
			
			Approval approval = new Approval();
			approval.setObjectName(objectDefinition.getObjectLableName());
			approval.setObjectClassName(objectDefinition.getObjectName());
			approval.setObjectIdFiledName(objectFieldDefinition.getObjectFieldName());
			
			approval.setInitialSubmitters(sb.toString());
			approval.setEntryCreteria(approvalRuleRequest.getEntryCriteria());
			approval.setDescription(approvalRuleRequest.getDescriptions());
			approval.setBusinessDomain(new BusinessDomain(approvalRuleRequest.getBusinessDomainId()));
			approval.setApprovalStages(approvalRuleRequest.getApprovalStages());
			approval.setActive(approvalRuleRequest.isActive());
			approval.setCreatedBy(new EmployeeDetails(user.getUsername()));
			approval.setCreatedDate(new Date());
			approval.setModifiedBy(new EmployeeDetails(user.getUsername()));
			approval.setModifiedDate(new Date());
			
			entityManager.persist(approval);
			
			approvalRuleRequest.setId(approval.getId());
			
			return approvalRuleRequest;	
		}catch(Exception ex) {
			throw new BadRequestException(ex.getMessage());
		}
		
	}

	
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			Exception.class, RuntimeException.class,BadRequestException.class,UnauthoriseException.class, SQLException.class })
	@Override
	public ApprovalRuleRequest updateApprovalRule(ApprovalRuleRequest approvalRuleRequest, LoginUser user)
			throws Exception {
		
		try {
			
			Approval approval = null;
			try {
				Query selectApprovalQuery = entityManager.createQuery(
						"select a from Approval a left join a.businessDomain bd where a.id=?1", Approval.class);
				selectApprovalQuery.setParameter(1, approvalRuleRequest.getId());
				approval = (Approval) selectApprovalQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Approval rule does not exist");
			}

			if (approval == null) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Approval rule does not exist");
			}
			
			
			ObjectDefinition objectDefinition = null;
			try {
				Query selectObjectDefinitionQuery = entityManager.createQuery(
						"select obj from ObjectDefinition obj where obj.objectName=?1", ObjectDefinition.class);
				selectObjectDefinitionQuery.setParameter(1, approvalRuleRequest.getObjectClass());
				objectDefinition = (ObjectDefinition) selectObjectDefinitionQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Object does not exist");
			}

			if (objectDefinition == null) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Object does not exist");
			}
			
			
			ObjectFieldDefinition objectFieldDefinition = null;
			try {
				Query selectObjectFieldQuery = entityManager.createQuery(
						"select fld from ObjectFieldDefinition fld join fld.objectDef obj where obj.objectName=?1 and fld.keyName=?2", ObjectFieldDefinition.class);
				selectObjectFieldQuery.setParameter(1, objectDefinition.getObjectName());
				selectObjectFieldQuery.setParameter(2, "PK");
				objectFieldDefinition = (ObjectFieldDefinition) selectObjectFieldQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Object field does not exist");
			}

			if (objectFieldDefinition == null) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Object field does not exist");
			}
			
			StringBuilder sb = new StringBuilder();
			for(int i=0; i<approvalRuleRequest.getInitialSubmitterList().size(); i++) {
				sb.append(approvalRuleRequest.getInitialSubmitterList().get(i));
				if(approvalRuleRequest.getInitialSubmitterList().size()-i!=1) {
					sb.append(",");
				}
			}
			
			approval.setObjectName(objectDefinition.getObjectLableName());
			approval.setObjectClassName(objectDefinition.getObjectName());
			approval.setObjectIdFiledName(objectFieldDefinition.getObjectFieldName());
			
			approval.setInitialSubmitters(sb.toString());

			approval.setEntryCreteria(approvalRuleRequest.getEntryCriteria());
			approval.setDescription(approvalRuleRequest.getDescriptions());
			approval.setBusinessDomain(new BusinessDomain(approvalRuleRequest.getBusinessDomainId()));
			approval.setApprovalStages(approvalRuleRequest.getApprovalStages());
			approval.setActive(approvalRuleRequest.isActive());
			approval.setModifiedBy(new EmployeeDetails(user.getUsername()));
			approval.setModifiedDate(new Date());
			
			entityManager.persist(approval);
			
			approvalRuleRequest.setId(approval.getId());
			
			return approvalRuleRequest;	
		}catch(Exception ex) {
			throw new BadRequestException(ex.getMessage());
		}
	}

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			Exception.class, RuntimeException.class,BadRequestException.class,UnauthoriseException.class, SQLException.class })
	@Override
	public ApprovalStepRequest createApprovalStep(ApprovalStepRequest approvalStepRequest, LoginUser user)
			throws Exception {
		
		Approval approval = null;
		try {
			Query selectApprovalQuery = entityManager.createQuery(
					"select a from Approval a left join a.businessDomain bd where a.id=?1", Approval.class);
			selectApprovalQuery.setParameter(1, approvalStepRequest.getApprovalId());
			approval = (Approval) selectApprovalQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Approval rule does not exist");
		}

		if (approval == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Approval rule does not exist");
		}
		
		try {
			
			ApprovalSteps approvalSteps = new ApprovalSteps();
			approvalSteps.setApproval(approval);
			approvalSteps.setApprovedBy(approvalStepRequest.getApprovedBy());
			approvalSteps.setApprovedStage(approvalStepRequest.getApprovedStage());
			approvalSteps.setObjectFiledName(approvalStepRequest.getObjectFieldName());
			approvalSteps.setOnAprovedValue(approvalStepRequest.getOnApprovedValue());
			approvalSteps.setOnRejectedValue(approvalStepRequest.getOnRejectedValue());
			approvalSteps.setStepName(approvalStepRequest.getStepName());
			
			approvalSteps.setCreatedBy(new EmployeeDetails(user.getUsername()));
			approvalSteps.setCreatedDate(new Date());
			approvalSteps.setModifiedBy(new EmployeeDetails(user.getUsername()));
			approvalSteps.setModifiedDate(new Date());
			
			entityManager.persist(approvalSteps);
			
			approvalStepRequest.setId(approvalSteps.getId());
			return approvalStepRequest;
		}catch(Exception ex) {
			throw new BadRequestException(ex.getMessage());
		}
		
	}

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			Exception.class, RuntimeException.class,BadRequestException.class,UnauthoriseException.class, SQLException.class })
	@Override
	public ApprovalStepRequest updateApprovalStep(ApprovalStepRequest approvalStepRequest, LoginUser user)
			throws Exception {
		
		Approval approval = null;
		try {
			Query selectApprovalQuery = entityManager.createQuery(
					"select a from Approval a left join a.businessDomain bd where a.id=?1", Approval.class);
			selectApprovalQuery.setParameter(1, approvalStepRequest.getApprovalId());
			approval = (Approval) selectApprovalQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Approval rule does not exist");
		}

		if (approval == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Approval rule does not exist");
		}
		
		ApprovalSteps approvalSteps = null;
		try {
			Query selectApprovalStepsQuery = entityManager.createQuery(
					"select ast from ApprovalSteps ast join ast.approval apr where ast.id=?1", ApprovalSteps.class);
			selectApprovalStepsQuery.setParameter(1, approvalStepRequest.getApprovalId());
			approvalSteps = (ApprovalSteps) selectApprovalStepsQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Approval step does not exist");
		}

		if (approvalSteps == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Approval step does not exist");
		}
		
		try {
			
			approvalSteps.setApprovedBy(approvalStepRequest.getApprovedBy());
			approvalSteps.setApprovedStage(approvalStepRequest.getApprovedStage());
			approvalSteps.setObjectFiledName(approvalStepRequest.getObjectFieldName());
			approvalSteps.setOnAprovedValue(approvalStepRequest.getOnApprovedValue());
			approvalSteps.setOnRejectedValue(approvalStepRequest.getOnRejectedValue());
			approvalSteps.setStepName(approvalStepRequest.getStepName());
			
			approvalSteps.setModifiedBy(new EmployeeDetails(user.getUsername()));
			approvalSteps.setModifiedDate(new Date());
			
			entityManager.persist(approvalSteps);
			
			return approvalStepRequest;
		}catch(Exception ex) {
			throw new BadRequestException(ex.getMessage());
		}
	}
	public void submitForApproval(EntityManager em,Class cls,String id,LoginUser user) {
		
	}
	public void submitForApproval(EntityManager em,Class cls,long id,LoginUser user) {
		
	}
	public void submitForApproval(EntityManager em,Class cls,Long id,LoginUser user) {
		
	}

}
