package com.pratikshat.exicom.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.BtAssetMasterUpdate;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

public class BtAssetMasterUpdateCustomRepositoryImpl implements BtAssetMasterUpdateCustomRepository{

	@PersistenceContext
    EntityManager em;

    @Autowired
    com.pratikshat.exicom.interceptor.WorkflowExecuter workflow;
	
	@Override
	public BtAssetMasterUpdate saveDetails(BtAssetMasterUpdate btAssetMasterUpdate, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		workflow.executeWorkflow(em, btAssetMasterUpdate, "INSERT", "PRE", user);
		em.persist(btAssetMasterUpdate);
		workflow.executeWorkflow(em, btAssetMasterUpdate, "INSERT", "POST", user);
		return null;
	}

	@Override
	public BtAssetMasterUpdate updateDetails(BtAssetMasterUpdate t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		workflow.executeWorkflow(em, t, "UPDATE", "PRE", user);
        em.merge(t);
        workflow.executeWorkflow(em, t, "UPDATE", "POST", user);
		return null;	}

	@Override
	public BtAssetMasterUpdate deleteDetails(BtAssetMasterUpdate t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

}
