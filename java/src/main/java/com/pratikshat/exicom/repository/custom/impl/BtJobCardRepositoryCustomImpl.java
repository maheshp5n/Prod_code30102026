package com.pratikshat.exicom.repository.custom.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.BtJobCard;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;
import com.pratikshat.exicom.repository.custom.BtJobCardRepositoryCustom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class BtJobCardRepositoryCustomImpl implements BtJobCardRepositoryCustom{

    @PersistenceContext
    EntityManager em;

    @Autowired JdbcTemplate jdbc;
    @Autowired WorkflowExecuter wExecuter;

    @Override
    public BtJobCard saveDetails(BtJobCard t, LoginUser user) throws BadRequestException, UnauthoriseException {
        wExecuter.executeWorkflow(em, t, "INSERT", "PRE", user);
        em.persist(t);
        String manualId="JC-BT-"+MiscellaneousUtils.fillZero(""+t.getId(), 5);
        t.setManualId(manualId);
        em.persist(t);
        wExecuter.executeWorkflow(em, t, "INSERT", "POST", user);
        return t;
    }

    @Override
    public BtJobCard updateDetails(BtJobCard t, LoginUser user) throws BadRequestException, UnauthoriseException {
        wExecuter.executeWorkflow(em, t, "UPDATE", "PRE", user);
        em.persist(t);
        wExecuter.executeWorkflow(em, t, "UPDATE", "POST", user);
        return t;
    }

    @Override
    public BtJobCard deleteDetails(BtJobCard t, LoginUser user) throws BadRequestException, UnauthoriseException {
       throw new BadRequestException("This function not supported");
    }

    @Override
    public List<Map<String, Object>> advanceSearch(Map<String, Object> request, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        return advanceSearch(request, null, user);
    }

    @Override
    public List<Map<String, Object>> advanceSearch(Map<String, Object> request, List<String> ownerList, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        
        StringBuilder sb =new StringBuilder();
        List<Object> param=new ArrayList<>();

        return jdbc.queryForList(sb.toString(),param.toArray());
    }
    
}
