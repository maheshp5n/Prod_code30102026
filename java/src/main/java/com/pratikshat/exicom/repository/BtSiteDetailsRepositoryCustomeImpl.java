
package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.BtSiteDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

import java.util.Map;

import javax.persistence.EntityManager;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;
import org.springframework.beans.factory.annotation.Autowired;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import javax.persistence.PersistenceContext;
import javax.persistence.Query;

 class BtSiteDetailsRepositoryCustomeImpl implements BtSiteDetailsRepositoryCustome{

    @PersistenceContext
    EntityManager em;

    @Autowired
    WorkflowExecuter workFlow;

    @Override
    public BtSiteDetails saveDetails(BtSiteDetails btSiteDetails, LoginUser user) throws BadRequestException, UnauthoriseException {
        workFlow.executeWorkflow(em,btSiteDetails,"INSERT","PRE",user);
        em.persist(btSiteDetails);
        workFlow.executeWorkflow(em,btSiteDetails,"INSERT","POST",user);
        return btSiteDetails;
    }

    @Override
    public BtSiteDetails updateDetails(BtSiteDetails btSiteDetails, LoginUser user) throws BadRequestException, UnauthoriseException {
        BtSiteDetails _exists=null;
        try{
            _exists=em.createQuery("select st from BtSiteDetails st where st=?1",BtSiteDetails.class)
                    .setParameter(1,new BtSiteDetails(btSiteDetails.getId()))
                    .getSingleResult();
        }catch (NoResultException ex){
            throw new BadRequestException("Site details not found");
        }catch (NonUniqueResultException ex){
            throw new BadRequestException("Multiple site details found");
        }
        _exists.setCustomer(btSiteDetails.getCustomer());
        _exists.setAccount(btSiteDetails.getAccount());
        _exists.setOwner(btSiteDetails.getOwner());
        _exists.setPrimaryAccount(btSiteDetails.getPrimaryAccount());
        _exists.setAccount(btSiteDetails.getAccount());
        _exists.setCircle(btSiteDetails.getCircle());
        _exists.setClusterDistrict(btSiteDetails.getClusterDistrict());
        _exists.setInfraId(btSiteDetails.getInfraId());
        _exists.setServiceCenter(btSiteDetails.getServiceCenter());
        _exists.setServiceResource(btSiteDetails.getServiceResource());
        _exists.setSiteAddress(btSiteDetails.getSiteAddress());
        _exists.setSiteDescription(btSiteDetails.getSiteDescription());
        _exists.setSiteName(btSiteDetails.getSiteName());
        _exists.setSsa(btSiteDetails.getSsa());
        _exists.setRjilJcName(btSiteDetails.getRjilJcName());
        _exists.setZoneZoneName(btSiteDetails.getZoneZoneName());
        _exists.setCmp(btSiteDetails.getCmp());

        workFlow.executeWorkflow(em,btSiteDetails,"UPDATE","PRE",user);
        em.persist(_exists);
        workFlow.executeWorkflow(em,btSiteDetails,"UPDATE","POST",user);
        return btSiteDetails;
    }

    @Override
    public BtSiteDetails deleteDetails(BtSiteDetails btSiteDetails, LoginUser user) throws BadRequestException, UnauthoriseException {
        throw new BadRequestException("Option not available");
    }

	@Override
	public Map<String, Object> siteDetailsSaveExcelData(Map<String, Object> req, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public BtSiteDetails getSiteWithId(long id) throws BadRequestException {
		BtSiteDetails site = null;
			try {
				Query siteQuery = em.createQuery(
						"select sti from BtSiteDetails sti join sti.account act left join sti.circle cr left join cr.circleManagerBattry cb left join cr.circleManagerPower cp left join cr.circleManagerEv ev where sti.id=?1",
						BtSiteDetails.class);
				siteQuery.setParameter(1, id);
				site = (BtSiteDetails) siteQuery.getSingleResult();
			} catch(NoResultException ex) {
    			throw new BadRequestException("Site does not exist");
    		}catch(NonUniqueResultException ex) {
    			throw new BadRequestException("multiple Site exist with this site Id");
    		}
			
		return site;
	}
}
