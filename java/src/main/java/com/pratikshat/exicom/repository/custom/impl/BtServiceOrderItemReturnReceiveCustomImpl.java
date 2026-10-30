package com.pratikshat.exicom.repository.custom.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.BtServiceOrderItemReturnReceive;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;
import com.pratikshat.exicom.repository.custom.BtServiceOrderItemReturnReceiveCustom;


import org.springframework.beans.factory.annotation.Autowired;


public class BtServiceOrderItemReturnReceiveCustomImpl implements BtServiceOrderItemReturnReceiveCustom{

    @PersistenceContext
    EntityManager em;
    @Autowired WorkflowExecuter wExecuter;

    @Override
    public BtServiceOrderItemReturnReceive saveDetails(BtServiceOrderItemReturnReceive t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        em.persist(t);
        String manualId="SOR-IR-"+ MiscellaneousUtils.fillZero(""+t.getId(), 5);
        t.setManualId(manualId);
        em.persist(t);
        return t;
    }

    @Override
    public BtServiceOrderItemReturnReceive updateDetails(BtServiceOrderItemReturnReceive t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        throw new BadRequestException("This function is not supported ");
    }

    @Override
    public BtServiceOrderItemReturnReceive deleteDetails(BtServiceOrderItemReturnReceive t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
                throw new BadRequestException("This function is not supported ");
    }
    
}
