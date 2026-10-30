package com.pratikshat.exicom.repository.custom.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.BtServiceOrderReturn;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;
import com.pratikshat.exicom.repository.custom.BtServiceOrderReturnRepositoryCustom;

import org.springframework.beans.factory.annotation.Autowired;

public class BtServiceOrderReturnRepositoryCustomImpl implements BtServiceOrderReturnRepositoryCustom {

    @PersistenceContext
    EntityManager em;
    @Autowired
    WorkflowExecuter wExecuter;

    @Override
    public BtServiceOrderReturn saveDetails(BtServiceOrderReturn t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        wExecuter.executeWorkflow(em, t, "INSERT", "PRE", user);
        em.persist(t);
        String manualid = "SOR-BT-" + MiscellaneousUtils.fillZero("" + t.getId(), 5);
        t.setManualId(manualid);
        em.merge(t);
        wExecuter.executeWorkflow(em, t, "INSERT", "POST", user);
        return t;
    }

    @Override
    public BtServiceOrderReturn updateDetails(BtServiceOrderReturn t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        wExecuter.executeWorkflow(em, t, "UPDATE", "PRE", user);
        em.merge(t);
        wExecuter.executeWorkflow(em, t, "UPDATE", "POST", user);
        return t;
    }

    @Override
    public BtServiceOrderReturn deleteDetails(BtServiceOrderReturn t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        throw new BadRequestException("Function is not supported");
    }

    @Override
    public void addTotalReturndQuantity(Long returnOrderId, Float quantity, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        BtServiceOrderReturn _return = em.find(BtServiceOrderReturn.class, returnOrderId);
        Float qty = _return.getTotalNumberOfQuantity();
        qty += quantity;
        _return.setTotalNumberOfQuantity(qty);
        updateDetails(_return, user);

    }

    @Override
    public void addTotalReturndReceivedQuantity(Long returnOrderId, Float quantity, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        BtServiceOrderReturn _return = em.find(BtServiceOrderReturn.class, returnOrderId);
        Float qty = _return.getTotalNumberOfQuantityReceived();
        qty += quantity;
        _return.setTotalNumberOfQuantityReceived(qty);
        updateDetails(_return, user);
    }

}
