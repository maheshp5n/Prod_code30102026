package com.pratikshat.exicom.repository.custom.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.pratikshat.exicom.entity.BtJobCardFile;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;
import com.pratikshat.exicom.repository.custom.BtJobCardFileRepositoryCustom;

import org.springframework.beans.factory.annotation.Autowired;

public class BtJobCardFileRepositoryCustomImpl implements BtJobCardFileRepositoryCustom {

    @PersistenceContext
    EntityManager em;
    @Autowired
    WorkflowExecuter wExecuter;

    @Override
    public BtJobCardFile saveDetails(BtJobCardFile t, LoginUser user) throws BadRequestException, UnauthoriseException {
        wExecuter.executeWorkflow(em, t, "INSERT", "PRE", user);
        em.persist(t);
        wExecuter.executeWorkflow(em, t, "INSERT", "POST", user);
        return t;
    }

    @Override
    public BtJobCardFile updateDetails(BtJobCardFile t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        wExecuter.executeWorkflow(em, t, "UPDATE", "PRE", user);
        em.persist(t);
        wExecuter.executeWorkflow(em, t, "UPDATE", "POST", user);
        return t;

    }

    @Override
    public BtJobCardFile deleteDetails(BtJobCardFile t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
                throw new BadRequestException("Function not supported");
    }

}
