package com.pratikshat.exicom.repository.custom.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

import com.itextpdf.text.log.LoggerFactory;
import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.BtJobCardProductConsume;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.ServiceRecource;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;
import com.pratikshat.exicom.repository.BRCCasePdfGen;
import com.pratikshat.exicom.repository.BtProductItemRepository;
import com.pratikshat.exicom.repository.custom.BtJobCardProductConsumeRepositoryCustom;


import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class BtJobCardProductConsumeRepositoryCustomImpl implements BtJobCardProductConsumeRepositoryCustom {

    @PersistenceContext
    EntityManager em;

    @Autowired
    WorkflowExecuter wExecuter;

    @Autowired
    JdbcTemplate jdbc;

    @Autowired BtProductItemRepository dbStock;
    
   
    @Override
    public BtJobCardProductConsume saveDetails(BtJobCardProductConsume t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        wExecuter.executeWorkflow(em, t, "INSERT", "PRE", user);
        em.persist(t);
        String manualId = "JC-PC-BT-" + MiscellaneousUtils.fillZero("" + t.getId(), 5);
        t.setManualId(manualId);
        em.merge(t);
        wExecuter.executeWorkflow(em, t, "INSERT", "POST", user);
        return t;
    }

    @Override
    public BtJobCardProductConsume updateDetails(BtJobCardProductConsume t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        wExecuter.executeWorkflow(em, t, "UPDATE", "PRE", user);
        em.merge(t);
        wExecuter.executeWorkflow(em, t, "UPDATE", "POST", user);
        return t;
    }

    @Override
    public BtJobCardProductConsume deleteDetails(BtJobCardProductConsume t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        em.remove(t);
        return null;
    }

    @Override
    public void addQuantity(BtJobCardProductConsume jobCardConsume, Float quantity, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        
        BtJobCardProductConsume _jobCard =null;
        try {
            _jobCard=em.createQuery("select prdc from BtJobCardProductConsume prdc join prdc.product prd join prdc.jobCard jcd where prd=?1 and jcd=?2 and prdc.consumeFromStock=?3",BtJobCardProductConsume.class)
                    .setParameter(1, jobCardConsume.getProduct())
                    .setParameter(2, jobCardConsume.getJobCard())
                    .setParameter(3, jobCardConsume.getConsumeFromStock())
                    .getSingleResult();
        } catch (NoResultException e) {
            
        } catch (NonUniqueResultException ex){
            throw new BadRequestException("Multiple consumption record found");
        }

        if (_jobCard!=null) {
            Float qty = _jobCard.getQuantity() == null ? 0 : _jobCard.getQuantity();
            qty += quantity;
            _jobCard.setQuantity(qty);
            
            if(_jobCard.getConsumeFromStock()==null?false:_jobCard.getConsumeFromStock())
            {
                dbStock.subQuantity(_jobCard.getProduct(), quantity, false, 1, user);
//            	dbStock.addQuantity(_jobCard.getProduct(), quantity, false, 2, user);
            }
            updateDetails(_jobCard, user);
            
        } else {
             //_jobCard = new BtJobCardProductConsume();

            _jobCard = new BtJobCardProductConsume();
            _jobCard.setConsumeFromStock(jobCardConsume.getConsumeFromStock()==null?false:jobCardConsume.getConsumeFromStock());
            _jobCard.setConsumeDate(jobCardConsume.getConsumeDate());
            _jobCard.setConsumeFromStock(jobCardConsume.getConsumeFromStock());
            _jobCard.setJobCard(jobCardConsume.getJobCard());
            _jobCard.setOwner(new EmployeeDetails(user.getUsername()));
            _jobCard.setProduct(jobCardConsume.getProduct());
            _jobCard.setQuantity(quantity);
            if(_jobCard.getConsumeFromStock())
            {
            	dbStock.subQuantity(_jobCard.getProduct(), quantity, false, 1, user);
//            	dbStock.addQuantity(_jobCard.getProduct(), quantity, false, 2, user);
            }
          
            saveDetails(_jobCard, user);
        }

    }

    @Override
    public void addQuantity(Long jobCardConsumeId, Float quantity, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        BtJobCardProductConsume _jobCard = em.find(BtJobCardProductConsume.class, jobCardConsumeId);
        addQuantity(_jobCard, quantity, user);

    }

    @Override
    public void subQuantity(BtJobCardProductConsume jobCardConsume, Float quantity, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        subQuantity(jobCardConsume.getId(), quantity, user);
    }

    @Override
    public void subQuantity(Long btJobcardId, Float quantity, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        BtJobCardProductConsume jobCard = em.find(BtJobCardProductConsume.class, btJobcardId);
        Float qty = jobCard.getQuantity();
        qty -= quantity;
        if (qty < 0)
            throw new BadRequestException("Invalid quantity, value can not be negative");
        jobCard.setQuantity(qty);
        if(jobCard.getConsumeFromStock()==null?false:true)
            dbStock.addQuantity(jobCard.getProduct(), quantity, false, 1, user);
        updateDetails(jobCard, user);
    }

}
