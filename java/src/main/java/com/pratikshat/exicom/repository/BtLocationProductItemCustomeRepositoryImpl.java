package com.pratikshat.exicom.repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.pratikshat.exicom.entity.BtLocationProductItem;
import com.pratikshat.exicom.entity.BtServiceAppointment;
import com.pratikshat.exicom.entity.Location;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;

public class BtLocationProductItemCustomeRepositoryImpl implements BtLocationProductItemCustomeRepository{
	
	@PersistenceContext
	EntityManager em;
	@Autowired
	WorkflowExecuter workflow;
	
	@Override
	public BtLocationProductItem saveDetails(BtLocationProductItem t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
	
		return null;
	}

	@Override
	public BtLocationProductItem updateDetails(BtLocationProductItem btLocationProductItem, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		BtLocationProductItem _check = null;
		try {
			_check=em.createQuery("select btlp from BtLocationProductItem btlp where btlp.id=?1",
					BtLocationProductItem.class)
					                   .setParameter(1,btLocationProductItem.getId()).getSingleResult();
			
		}catch (NoResultException ex){
            throw new BadRequestException("Assets details not found");
        }catch (NonUniqueResultException ex){
            throw new BadRequestException("Multiple assets details found");
        }
		_check.setBerQty(btLocationProductItem.getBerQty());
		_check.setBtProducts(btLocationProductItem.getProduct());
		_check.setLocation(btLocationProductItem.getLocation());
		_check.setBtProducts(btLocationProductItem.getProduct());
		_check.setParentLocationProductItem(btLocationProductItem.getParentLocationProductItem());
		_check.setQuantityInOnHand(btLocationProductItem.getQuantityInOnHand());
		_check.setQuantityUnitOfMasur(btLocationProductItem.getQuantityUnitOfMasur());
		_check.setHslQuantity(btLocationProductItem.getHslQuantity());
		_check.setHslStatus(btLocationProductItem.getHslStatus());
		_check.setIssuedQuantity(btLocationProductItem.getIssuedQuantity());
		_check.setMslQuantity(btLocationProductItem.getMslQuantity());
		_check.setMslQuantityStatus(btLocationProductItem.getMslQuantityStatus());
		_check.setOkQuantity(btLocationProductItem.getOkQuantity());
		_check.setFaultyQuantity(btLocationProductItem.getFaultyQuantity());
		_check.setCustomerInventory(btLocationProductItem.getCustomerInventory());
		_check.setScrapQty(btLocationProductItem.getScrapQty());
		_check.setRejectedQty(btLocationProductItem.getRejectedQty());
		_check.setRefurbished(btLocationProductItem.getRefurbished());
		_check.setDoaQuantity(btLocationProductItem.getDoaQuantity());
		 workflow.executeWorkflow(em,_check,"UPDATE","PRE",user);
	     em.persist(_check);
	     workflow.executeWorkflow(em,_check,"UPDATE","POST",user);
		return _check;
		
		
		
	}

	@Override
	public BtLocationProductItem deleteDetails(BtLocationProductItem t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public BtLocationProductItem getProductItemByLocationAndProduct(Location location,long productId) throws BadRequestException{
		BtLocationProductItem items = null;
		try {
			javax.persistence.Query locationProductItemQuery=em.createQuery("select litem from BtLocationProductItem litem join litem.product pr join litem.location loc where loc=?1 and pr.id=?2",BtLocationProductItem.class);
			locationProductItemQuery.setParameter(1,location);
			locationProductItemQuery.setParameter(2,productId);
			items=(BtLocationProductItem)locationProductItemQuery.getSingleResult();
		}catch(NoResultException ex) {
		}
		if(items == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Service Resource does not have product ");
		}
		
		return items;
	}

}
