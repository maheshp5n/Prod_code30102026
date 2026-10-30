package com.pratikshat.exicom.repository.custom.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.PmSiteAssets;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;
import com.pratikshat.exicom.repository.custom.PmSiteAssetsRepositoryCustom;

import org.springframework.beans.factory.annotation.Autowired;

public class PmSiteAssetsRepositoryCustomImpl implements PmSiteAssetsRepositoryCustom{

    @PersistenceContext EntityManager em;

    @Autowired WorkflowExecuter wExecuter;

    @Override
    public PmSiteAssets saveDetails(PmSiteAssets t, LoginUser user) throws BadRequestException, UnauthoriseException {
        wExecuter.executeWorkflow(em, t, "INSERT", "PRE", user);
        em.persist(t);
        String manualId="PM-ST-AST-"+MiscellaneousUtils.fillZero(""+t.getId(), 5);
        t.setManualId(manualId);
        em.merge(t);

        wExecuter.executeWorkflow(em, t, "INSERT", "POST", user);
        return t;
    }

    @Override
    public PmSiteAssets updateDetails(PmSiteAssets t, LoginUser user) throws BadRequestException, UnauthoriseException {
        wExecuter.executeWorkflow(em, t, "UPDATE", "PRE", user);
        em.merge(t);
        wExecuter.executeWorkflow(em, t, "UPDATE", "POST", user);
        return t;
    }

    @Override
    public PmSiteAssets deleteDetails(PmSiteAssets t, LoginUser user) throws BadRequestException, UnauthoriseException {
        em.remove(t);
        return null;
    }
    
}
