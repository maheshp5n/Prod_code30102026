package com.pratikshat.exicom.repository.custom.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.BtServiceOrderItemReturn;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;
import com.pratikshat.exicom.repository.custom.BtServiceOrderItemReturnRepositoryCustom;

import org.springframework.beans.factory.annotation.Autowired;

public class BtServiceOrderItemReturnRepositoryCustomImpl implements BtServiceOrderItemReturnRepositoryCustom {

    @PersistenceContext
    EntityManager em;

    @Autowired
    WorkflowExecuter wExecuter;

    @Override
    public BtServiceOrderItemReturn saveDetails(BtServiceOrderItemReturn t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        wExecuter.executeWorkflow(em, t, "INSERT", "PRE", user);
        em.persist(t);

        String manualId = "SORI-BT-" + MiscellaneousUtils.fillZero("" + t.getId(), 5);
        t.setManualId(manualId);
        em.merge(t);
        return t;
    }

    @Override
    public BtServiceOrderItemReturn updateDetails(BtServiceOrderItemReturn t, LoginUser user)
            throws BadRequestException, UnauthoriseException {

        wExecuter.executeWorkflow(em, t, "UPDATE", "PRE", user);

        em.merge(t);
        wExecuter.executeWorkflow(em, t, "UPDATE", "POST", user);
        return t;
    }

    @Override
    public BtServiceOrderItemReturn deleteDetails(BtServiceOrderItemReturn t, LoginUser user)
            throws BadRequestException, UnauthoriseException {

        throw new BadRequestException("This function is not supported");
    }

}
