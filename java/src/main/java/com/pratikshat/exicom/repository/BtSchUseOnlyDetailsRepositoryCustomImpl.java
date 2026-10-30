package com.pratikshat.exicom.repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import com.pratikshat.exicom.entity.BtAssets;
import com.pratikshat.exicom.entity.BtSchUseOnlyDetails;
import com.pratikshat.exicom.entity.BtSolutionImpartedProblem;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;

public class BtSchUseOnlyDetailsRepositoryCustomImpl implements BtSchUseOnlyDetailsRepositoryCustom{
	
	@PersistenceContext
	EntityManager em;
	
	@Autowired
	WorkflowExecuter workflow;

	@Override
	public BtSchUseOnlyDetails saveDetails(BtSchUseOnlyDetails btSchUseOnlyDetails, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		workflow.executeWorkflow(em, btSchUseOnlyDetails,"INSERT", "PRE", user);
		System.out.println("PRE");
		em.persist(btSchUseOnlyDetails);
		System.out.println("POST");
		workflow.executeWorkflow(em, btSchUseOnlyDetails,"INSERT", "POST", user);
		return null;
	}

	@Override
	public BtSchUseOnlyDetails updateDetails(BtSchUseOnlyDetails btSchUseOnlyDetails, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		BtSchUseOnlyDetails _exits=null; 
		
		try{
	            _exits=em.createQuery("select act from BtSchUseOnlySDetails act where act.id=?1",BtSchUseOnlyDetails.class)
	                    .setParameter(1,btSchUseOnlyDetails.getId())
	                    .getSingleResult();
	        }catch (NoResultException ex){
	            throw new BadRequestException("Assets not found");
	        }catch (NonUniqueResultException ex){
	            throw new BadRequestException("Multiple assets found with Id ");
	        }
		
		_exits.setResolutionMethod(btSchUseOnlyDetails.getResolutionMethod());
		_exits.setSparePartName(btSchUseOnlyDetails.getSparePartName());
		_exits.setSparePartQuantity(btSchUseOnlyDetails.getSparePartQuantity());
		_exits.setSparePartReplaced(btSchUseOnlyDetails.getSparePartReplaced());
		
		workflow.executeWorkflow(em, btSchUseOnlyDetails,"UPDATE", "PRE", user);
		em.persist(btSchUseOnlyDetails);
		workflow.executeWorkflow(em, btSchUseOnlyDetails,"UPDATE", "POST", user);
		return null;
	}

	@Override
	public BtSchUseOnlyDetails deleteDetails(BtSchUseOnlyDetails t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BtSolutionImpartedProblem saveDetail(BtSolutionImpartedProblem btSolutionImpartedRepository,
			LoginUser user) throws BadRequestException, UnauthoriseException {
		workflow.executeWorkflow(em, btSolutionImpartedRepository, "INSERT", "PRE", user);
		em.persist(btSolutionImpartedRepository);
		workflow.executeWorkflow(em, btSolutionImpartedRepository, "INSERT", "POST", user);
		return null;
	}

	@Override
	public BtSchUseOnlyDetails getObject(long id) {
		BtSchUseOnlyDetails btSchUseOnlyDetails=em.createQuery("select bt from BtSchUseOnlyDetails bt where bt.id=?1",BtSchUseOnlyDetails.class)
				.setParameter(1, id).getSingleResult();
		return btSchUseOnlyDetails;
	}

	@Override
	public void deleteSolutionImparted(long id) {
		Query deleteProblemId =em.createQuery("delete BtSolutionImpartedProblem pr where pr.btSchUseOnlyDetails.id=?1");
		deleteProblemId.setParameter(1,id);
				deleteProblemId.executeUpdate();
		
	}

}
