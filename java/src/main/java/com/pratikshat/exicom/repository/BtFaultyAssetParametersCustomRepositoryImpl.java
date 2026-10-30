package com.pratikshat.exicom.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.BtFaultyAssetParameters;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;

public class BtFaultyAssetParametersCustomRepositoryImpl implements BtFaultyAssetParametersCustomRepository {

	@PersistenceContext
    EntityManager em;

    @Autowired
    WorkflowExecuter workflow;
    
	@Override
	public BtFaultyAssetParameters saveDetails(BtFaultyAssetParameters btFaultyAssetParameters, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		workflow.executeWorkflow(em, btFaultyAssetParameters, "INSERT","PRE", user);
		em.persist(btFaultyAssetParameters);
		workflow.executeWorkflow(em, btFaultyAssetParameters, "INSERT", "POST", user);
		return null;
	}

	@Override
	public BtFaultyAssetParameters updateDetails(BtFaultyAssetParameters t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		workflow.executeWorkflow(em, t, "UPDATE", "PRE", user);
        em.merge(t);
        workflow.executeWorkflow(em, t, "UPDATE", "POST", user);
		return null;
	}

	@Override
	public BtFaultyAssetParameters deleteDetails(BtFaultyAssetParameters t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

}
