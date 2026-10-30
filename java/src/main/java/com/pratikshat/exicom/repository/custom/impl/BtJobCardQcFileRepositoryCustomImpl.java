package com.pratikshat.exicom.repository.custom.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.pratikshat.exicom.entity.BtJobCardQcFile;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;
import com.pratikshat.exicom.repository.custom.BtJobCardQcFileRepositoryCustom;

import org.springframework.beans.factory.annotation.Autowired;

public class BtJobCardQcFileRepositoryCustomImpl implements BtJobCardQcFileRepositoryCustom {

    @PersistenceContext
    EntityManager em;
    @Autowired
    WorkflowExecuter wExecuter;

    @Override
    public BtJobCardQcFile saveDetails(BtJobCardQcFile t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        wExecuter.executeWorkflow(em, t, "INSERT", "PRE", user);
        em.persist(t);
        wExecuter.executeWorkflow(em, t, "INSERT", "POST", user);
        return t;
    }

    @Override
    public BtJobCardQcFile updateDetails(BtJobCardQcFile t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        wExecuter.executeWorkflow(em, t, "UPDATE", "PRE", user);
        em.persist(t);
        wExecuter.executeWorkflow(em, t, "UPDATE", "POST", user);
        return t;
    }

    @Override
    public BtJobCardQcFile deleteDetails(BtJobCardQcFile t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
                em.remove(t);
        return null;
    }

}
