package com.pratikshat.exicom.repository.custom.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import com.pratikshat.exicom.config.MiscellaneousUtils;

import com.pratikshat.exicom.entity.BtServiceOrderItemReceive;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;
import com.pratikshat.exicom.repository.custom.BtServiceOrderItemReceiveRepositoryCustom;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class BtServiceOrderItemReceiveRepositoryCustomImpl implements BtServiceOrderItemReceiveRepositoryCustom{

    @PersistenceContext
    EntityManager em;

    @Autowired
    WorkflowExecuter wExecuter;

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public BtServiceOrderItemReceive saveDetails(BtServiceOrderItemReceive t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        BtServiceOrderItemReceive item=null;
        try {
            item=em.createQuery("from BtServiceOrderItemReceive item where item.id=?1",BtServiceOrderItemReceive.class).setParameter(1, t.getId()).getSingleResult();
            throw new BadRequestException("Invalid item id, Already received");
        } catch (NoResultException e) {
            wExecuter.executeWorkflow(em, t, "INSERT", "PRE", user);
            em.persist(t);
            String maunualId="SOIR-BT-"+MiscellaneousUtils.fillZero(""+t.getId(), 5);
            t.setManualId(maunualId);
            em.persist(t);

            wExecuter.executeWorkflow(em, t, "INSERT", "POST", user);
        }
        return t;
    }

    @Override
    public BtServiceOrderItemReceive updateDetails(BtServiceOrderItemReceive t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        wExecuter.executeWorkflow(em, t, "UPDATE", "PRE", user);
        em.merge(t);
        wExecuter.executeWorkflow(em, t, "UPDATE", "POST", user);
        return t;
    }

    @Override
    public BtServiceOrderItemReceive deleteDetails(BtServiceOrderItemReceive t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        throw new BadRequestException("This method is supported");
    }
    
}
