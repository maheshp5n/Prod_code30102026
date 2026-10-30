package com.pratikshat.exicom.repository.custom.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.DctRepairBulkConsumption;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;
import com.pratikshat.exicom.repository.custom.DctRepairBulkConsumptionRepositoryCustom;

import org.springframework.beans.factory.annotation.Autowired;

public class DctRepairBulkConsumptionRepositoryCustomImpl implements DctRepairBulkConsumptionRepositoryCustom {

    @PersistenceContext
    EntityManager em;

    @Autowired
    WorkflowExecuter wExecuter;

    @Override
    public DctRepairBulkConsumption saveDetails(DctRepairBulkConsumption t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        wExecuter.executeWorkflow(em, t, "INSERT", "PRE", user);
        em.persist(t);
        String manualId = "JCPC" + MiscellaneousUtils.fillZero("" + t.getId(), 5);
        t.setManualId(manualId);
        em.persist(t);
        wExecuter.executeWorkflow(em, t, "INSERT", "POST", user);
        return t;
    }

    @Override
    public DctRepairBulkConsumption updateDetails(DctRepairBulkConsumption t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        wExecuter.executeWorkflow(em, t, "UPDATE", "PRE", user);
        em.persist(t);

        wExecuter.executeWorkflow(em, t, "UPDATE", "POST", user);
        return t;
    }

    @Override
    public DctRepairBulkConsumption deleteDetails(DctRepairBulkConsumption t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
                throw new BadRequestException("This function is not supported");
    }

}
