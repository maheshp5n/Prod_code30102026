package com.pratikshat.exicom.repository.custom.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.pratikshat.exicom.entity.BtProductTransferReceive;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;
import com.pratikshat.exicom.repository.custom.BtProductTransferReceiveRepositoryCustom;

import org.springframework.beans.factory.annotation.Autowired;

public class BtProductTransferReceiveRepositoryCustomImpl implements BtProductTransferReceiveRepositoryCustom{

    @PersistenceContext
    EntityManager em;

    @Autowired
    WorkflowExecuter wExecuter;

    @Override
    public BtProductTransferReceive saveDetails(BtProductTransferReceive t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        wExecuter.executeWorkflow(em, t, "INSERT", "PRE", user);
        em.persist(t);
        wExecuter.executeWorkflow(em, t, "INSERT", "POST", user);
        return t;
    }

    @Override
    public BtProductTransferReceive updateDetails(BtProductTransferReceive t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        wExecuter.executeWorkflow(em, t, "UPDATE", "PRE", user);
        em.persist(t);
        wExecuter.executeWorkflow(em, t, "UPDATE", "POST", user);
        return t;
    }

    @Override
    public BtProductTransferReceive deleteDetails(BtProductTransferReceive t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        throw new BadRequestException("this function is currently not working");
    }
    
}
