package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.BtAssets;
import com.pratikshat.exicom.entity.BtAssetsAmcdetails;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.NoResultException;
import java.util.Date;

public class BtAssetsAmcdetailsRepositoryCustomeImpl implements BtAssetsAmcdetailsRepositoryCustome{

    @PersistenceContext
    EntityManager em;

    @Autowired
    com.pratikshat.exicom.interceptor.WorkflowExecuter workflow;


    @Override
    public BtAssetsAmcdetails saveDetails(BtAssetsAmcdetails btAssetsAmcdetails, LoginUser user) throws BadRequestException, UnauthoriseException {
        BtAssets _check=null;
        try{
            _check=em.createQuery("select ast from BtAssets ast where ast=?1",BtAssets.class)
                    .setParameter(1,btAssetsAmcdetails.getBtAssets())
                    .getSingleResult();
        }catch (NoResultException ex){
            throw new BadRequestException("Assets details not found");
        }catch (NonUniqueResultException ex){
            throw new BadRequestException("Multiple assets details found");
        }
        _check.setLastModifiedBy(new EmployeeDetails(user.getUsername()));
        _check.setLastModifiedDate(new Date());
        btAssetsAmcdetails.setId(_check.getId());
        workflow.executeWorkflow(em,btAssetsAmcdetails,"INSERT","PRE",user);
        em.persist(btAssetsAmcdetails);
        em.persist(_check);
        workflow.executeWorkflow(em,btAssetsAmcdetails,"INSERT","POST",user);
        return btAssetsAmcdetails;
    }

    @Override
    public BtAssetsAmcdetails updateDetails(BtAssetsAmcdetails btAssetsAmcdetails, LoginUser user) throws BadRequestException, UnauthoriseException {
        BtAssets _check=null;
        try{
            _check=em.createQuery("select ast from BtAssets ast where ast=?1",BtAssets.class)
                    .setParameter(1,btAssetsAmcdetails.getBtAssets())
                    .getSingleResult();
        }catch (NoResultException ex){
            throw new BadRequestException("Assets details not found");
        }catch (NonUniqueResultException ex){
            throw new BadRequestException("Multiple assets details found");
        }
        _check.setLastModifiedBy(new EmployeeDetails(user.getUsername()));
        _check.setLastModifiedDate(new Date());
        btAssetsAmcdetails.setId(_check.getId());
        workflow.executeWorkflow(em,btAssetsAmcdetails,"UPDATE","PRE",user);
        em.persist(btAssetsAmcdetails);
        em.persist(_check);
        workflow.executeWorkflow(em,btAssetsAmcdetails,"UPDATE","POST",user);
        return btAssetsAmcdetails;
    }

    @Override
    public BtAssetsAmcdetails deleteDetails(BtAssetsAmcdetails btAssetsAmcdetails, LoginUser user) throws BadRequestException, UnauthoriseException {
        throw new BadRequestException("Option not available");
    }

	@Override
	public BtAssetsAmcdetails getWithId(long id) throws BadRequestException {
		BtAssetsAmcdetails amcdetails = null;
		try {
		
			amcdetails = em.createQuery("select amc from BtAssetsAmcdetails amc left join BtAssets btAssets on btAssets.id =amc.id where amc.id=?1",
					BtAssetsAmcdetails.class).setParameter(1, id).getSingleResult();
		} catch (NoResultException ex) {
			throw new BadRequestException("No BtAssetsAmcdetails Found with this Id");
		} catch (NonUniqueResultException ex) {
			throw new BadRequestException("Dupliate value found with this key");
		}
		return amcdetails;
	}
}
