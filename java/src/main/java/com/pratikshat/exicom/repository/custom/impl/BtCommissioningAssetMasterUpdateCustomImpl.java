package com.pratikshat.exicom.repository.custom.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.config.FileStorageProperties;
import com.pratikshat.exicom.entity.BtCommissioningAssetMasterUpdate;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;
import com.pratikshat.exicom.repository.custom.BtCommissioningAssetMasterUpdateCustom;

public class BtCommissioningAssetMasterUpdateCustomImpl  implements BtCommissioningAssetMasterUpdateCustom{
	
	@PersistenceContext
	EntityManager em;
	
	@Autowired
	WorkflowExecuter workflow;
	
	@Autowired
	FileStorageProperties fileStorageProperties;


	@Override
	public BtCommissioningAssetMasterUpdate saveDetails(BtCommissioningAssetMasterUpdate btCommAsset, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		workflow.executeWorkflow(em,btCommAsset , "INSERT", "PRE", user);
		em.persist(btCommAsset);
		workflow.executeWorkflow(em, btCommAsset, "INSERT","POST", user);
		return null;
	}

	@Override
	public BtCommissioningAssetMasterUpdate updateDetails(BtCommissioningAssetMasterUpdate t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		workflow.executeWorkflow(em, t, "UPDATE", "PRE", user);
        em.merge(t);
        workflow.executeWorkflow(em, t, "UPDATE", "POST", user);
		return null;
	}

	@Override
	public BtCommissioningAssetMasterUpdate deleteDetails(BtCommissioningAssetMasterUpdate t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

}
