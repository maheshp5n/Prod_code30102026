package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.BtAssets;
import com.pratikshat.exicom.entity.BtAssetsPurchaseDetails;
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

public class BtAssetsPurchaseDetailsRepositoryCustomeImpl implements BtAssetsPurchaseDetailsRepositoryCustome{

    @PersistenceContext
    EntityManager em;

    @Autowired
    com.pratikshat.exicom.interceptor.WorkflowExecuter workFlow;

    @Override
    public BtAssetsPurchaseDetails saveDetails(BtAssetsPurchaseDetails btAssetsPurchaseDetails, LoginUser user) throws BadRequestException, UnauthoriseException {
        BtAssets _check=null;
        try{
            _check=em.createQuery("select ast from BtAssets ast where ast=?1",BtAssets.class)
                    .setParameter(1,btAssetsPurchaseDetails.getAssets())
                    .getSingleResult();
        }
        catch (NoResultException ex){
            throw new BadRequestException("Assets not found");
        }
        catch (NonUniqueResultException ex){
            throw new BadRequestException("Multiple assets found");
        }
        _check.setLastModifiedDate(new Date());
        _check.setLastModifiedBy(new EmployeeDetails(user.getUsername()));
        btAssetsPurchaseDetails.setId(_check.getId());
        btAssetsPurchaseDetails.setAssets(_check);
        workFlow.executeWorkflow(em,btAssetsPurchaseDetails,"INSERT","PRE",user);
        em.persist(btAssetsPurchaseDetails);
        em.persist(_check);
        workFlow.executeWorkflow(em,btAssetsPurchaseDetails,"INSERT","POST",user);
        return btAssetsPurchaseDetails;
    }

    @Override
    public BtAssetsPurchaseDetails updateDetails(BtAssetsPurchaseDetails btAssetsPurchaseDetails, LoginUser user) throws BadRequestException, UnauthoriseException {
        BtAssets _check=null;
        try{
            _check=em.createQuery("select ast from BtAssets ast where ast=?1",BtAssets.class)
                    .setParameter(1,btAssetsPurchaseDetails.getAssets())
                    .getSingleResult();
        }
        catch (NoResultException ex){
            throw new BadRequestException("Assets not found");
        }
        catch (NonUniqueResultException ex){
            throw new BadRequestException("Multiple assets found");
        }
        _check.setLastModifiedDate(new Date());
        _check.setLastModifiedBy(new EmployeeDetails(user.getUsername()));
        btAssetsPurchaseDetails.setId(_check.getId());
        btAssetsPurchaseDetails.setAssets(_check);
        workFlow.executeWorkflow(em,btAssetsPurchaseDetails,"UPDATE","PRE",user);
        em.persist(btAssetsPurchaseDetails);
        em.persist(_check);
        workFlow.executeWorkflow(em,btAssetsPurchaseDetails,"UPDATE","POST",user);
        return btAssetsPurchaseDetails;
    }

    @Override
    public BtAssetsPurchaseDetails deleteDetails(BtAssetsPurchaseDetails btAssetsPurchaseDetails, LoginUser user) throws BadRequestException, UnauthoriseException {
        throw new BadRequestException("Option not available");
    }

	@Override
	public BtAssetsPurchaseDetails getWithId(long id) throws BadRequestException {
		BtAssetsPurchaseDetails assetsPurchaseDetails = null;
		try {
			assetsPurchaseDetails = em
					.createQuery("select purchase from BtAssetsPurchaseDetails purchase where purchase.id=?1",
							BtAssetsPurchaseDetails.class)
					.setParameter(1,id).getSingleResult();
		} catch (NoResultException ex) {
			throw new BadRequestException("No BtAssetsPurchaseDetails Found with this Id");
		} catch (NonUniqueResultException ex) {
			throw new BadRequestException("Dupliate value found with this key");
		}
		return assetsPurchaseDetails;
	}
}
