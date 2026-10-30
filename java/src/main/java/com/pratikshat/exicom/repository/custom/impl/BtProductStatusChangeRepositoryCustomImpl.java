package com.pratikshat.exicom.repository.custom.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.BtProductStatusChange;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;
import com.pratikshat.exicom.repository.custom.BtProductStatusChangeRepositoryCustom;

import org.springframework.beans.factory.annotation.Autowired;

public class BtProductStatusChangeRepositoryCustomImpl implements BtProductStatusChangeRepositoryCustom {

    @PersistenceContext EntityManager em;
    @Autowired WorkflowExecuter wExecuter;

    @Override
    public BtProductStatusChange saveDetails(BtProductStatusChange t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
                wExecuter.executeWorkflow(em, t, "INSERT", "PRE", user);
                em.persist(t);
                String manualId="PRSC-BT-"+MiscellaneousUtils.fillZero(""+t.getId(), 5);
                t.setManualId(manualId);
                em.persist(t);
                wExecuter.executeWorkflow(em, t, "INSERT", "POST", user);
                return t;
    }

    @Override
    public BtProductStatusChange updateDetails(BtProductStatusChange t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
                throw new BadRequestException("Function not supported");
    }

    @Override
    public BtProductStatusChange deleteDetails(BtProductStatusChange t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
                throw new BadRequestException("Function not supported");
    }
    
}
