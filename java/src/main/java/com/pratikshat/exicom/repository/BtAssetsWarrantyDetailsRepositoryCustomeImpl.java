package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.BtAssets;
import com.pratikshat.exicom.entity.BtAssetsWarrantyDetails;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.Date;

public class BtAssetsWarrantyDetailsRepositoryCustomeImpl implements BtAssetsWarrantyDetailsRepositoryCustome{
    @PersistenceContext
    EntityManager em;

    @Autowired
    WorkflowExecuter workflow;

    @Override
    public BtAssetsWarrantyDetails saveDetails(BtAssetsWarrantyDetails btAssetsWarrantyDetails, LoginUser user) throws BadRequestException, UnauthoriseException {
        BtAssets _check=null;
        try{
        	 _check=em.createQuery("select ast from BtAssets ast where ast=?1",BtAssets.class)
                     .setParameter(1,btAssetsWarrantyDetails.getBtAssets())
                     .getSingleResult();
        }catch (NoResultException ex){
            throw new BadRequestException("Assets not found");
        }catch (NonUniqueResultException ex){
            throw new BadRequestException("Multiple assets not found");
        }

        _check.setLastModifiedBy(new EmployeeDetails(user.getUsername()));
        _check.setLastModifiedDate(new Date());
        btAssetsWarrantyDetails.setId(_check.getId());
        btAssetsWarrantyDetails.setBtAssets(_check);
        workflow.executeWorkflow(em,btAssetsWarrantyDetails,"INSERT","PRE",user);
        em.persist(btAssetsWarrantyDetails);
        em.persist(_check);
        workflow.executeWorkflow(em,btAssetsWarrantyDetails,"INSERT","POST",user);

        return btAssetsWarrantyDetails;
    }

    @Override
    public BtAssetsWarrantyDetails updateDetails(BtAssetsWarrantyDetails btAssetsWarrantyDetails, LoginUser user) throws BadRequestException, UnauthoriseException {
        BtAssets _check=null;
        try{
        	 _check=em.createQuery("select ast from BtAssets ast where ast=?1",BtAssets.class)
                     .setParameter(1,btAssetsWarrantyDetails.getBtAssets())
                     .getSingleResult();

        }catch (NoResultException ex){
            throw new BadRequestException("Assets not found");
        }catch (NonUniqueResultException ex){
            throw new BadRequestException("Multiple assets not found");
        }

        _check.setLastModifiedBy(new EmployeeDetails(user.getUsername()));
        _check.setLastModifiedDate(new Date());
        btAssetsWarrantyDetails.setId(_check.getId());
        btAssetsWarrantyDetails.setBtAssets(_check);
        workflow.executeWorkflow(em,btAssetsWarrantyDetails,"UPDATE","PRE",user);
        em.persist(btAssetsWarrantyDetails);
        em.persist(_check);
        workflow.executeWorkflow(em,btAssetsWarrantyDetails,"UPDATE","POST",user);

        return btAssetsWarrantyDetails;
    }

    @Override
    public BtAssetsWarrantyDetails deleteDetails(BtAssetsWarrantyDetails btAssetsWarrantyDetails, LoginUser user) throws BadRequestException, UnauthoriseException {
        throw new BadRequestException("Option not available");
    }

	@Override
	public BtAssetsWarrantyDetails getWithAssetsId(long id) throws BadRequestException {
		BtAssetsWarrantyDetails assetsWarrantyDetails=null;
		try {
			Query assetsQuery = em.createQuery(
					"select ast from BtAssetsWarrantyDetails ast left join ast.btAssets act where ast.id=?1",
					BtAssetsWarrantyDetails.class);
			assetsQuery.setParameter(1, id);
			assetsWarrantyDetails = (BtAssetsWarrantyDetails) assetsQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Asset does not exist");
		} catch (Exception ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException(ex.getMessage());
		}
		return assetsWarrantyDetails;
	}
}
