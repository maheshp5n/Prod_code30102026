package com.pratikshat.exicom.repository.custom.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.BtCustomerInventoryOut;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;
import com.pratikshat.exicom.repository.custom.BtCustomerInventoryOutRepositoryCustom;

import org.springframework.beans.factory.annotation.Autowired;

public class BtCustomerInventoryOutRepositoryCustomImpl implements BtCustomerInventoryOutRepositoryCustom{

    @PersistenceContext EntityManager em;

    @Autowired WorkflowExecuter executer;

    @Override
    public BtCustomerInventoryOut saveDetails(BtCustomerInventoryOut t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        executer.executeWorkflow(em, t, "INSERT", "PRE", user);
        em.persist(t);
        String manulaId="BT-CI-OUT-"+MiscellaneousUtils.fillZero(""+t.getId(), 5);
        t.setManualId(manulaId);
        em.persist(t);
        executer.executeWorkflow(em, t, "INSERT", "POST", user);
        return t;
    }

    @Override
    public BtCustomerInventoryOut updateDetails(BtCustomerInventoryOut t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        executer.executeWorkflow(em, t, "UPDATE", "PRE", user);
        em.persist(t);
        executer.executeWorkflow(em, t, "UPDATE", "POST", user);
        return t;
    }

    @Override
    public BtCustomerInventoryOut deleteDetails(BtCustomerInventoryOut t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        throw new BadRequestException("This function is not applicable");
        
    }
    
}
