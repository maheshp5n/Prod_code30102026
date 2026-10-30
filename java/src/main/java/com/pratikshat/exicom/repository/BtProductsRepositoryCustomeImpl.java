package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.BtProducts;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import javax.persistence.EntityManager;

import com.pratikshat.exicom.interceptor.WorkflowExecuter;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

public class BtProductsRepositoryCustomeImpl implements BtProductsRepositoryCustome {

	@PersistenceContext
	EntityManager em;

	@Autowired
	WorkflowExecuter workFlow;

	@Override
	public BtProducts saveDetails(BtProducts btProducts, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if (btProducts.getManualId() == null || btProducts.getManualId().equalsIgnoreCase(""))
			throw new BadRequestException("Partcode not found");
		BtProducts _exits = null;
		try {
			_exits = em.createQuery("select prd from BtProducts prd where prd.manualId=?1", BtProducts.class)
					.setParameter(1, btProducts.getManualId()).getSingleResult();
			throw new BadRequestException("Product with part code " + btProducts.getManualId() + " already exists");
		} catch (NoResultException ex) {

		} catch (NonUniqueResultException ex) {
			throw new BadRequestException("Multiple product found with same partcode " + btProducts.getManualId());
		}
		workFlow.executeWorkflow(em, btProducts, "INSERT", "PRE", user);
		em.persist(btProducts);
		workFlow.executeWorkflow(em, btProducts, "INSERT", "POST", user);
		return btProducts;
	}

	@Override
	public BtProducts updateDetails(BtProducts btProducts, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if (btProducts.getManualId() == null || btProducts.getManualId().equalsIgnoreCase(""))
			throw new BadRequestException("Partcode not found");
		BtProducts _exits = null;
		try {
			_exits = em.createQuery("select prd from BtProducts prd where prd=?1", BtProducts.class)
					.setParameter(1, btProducts).getSingleResult();

		} catch (NoResultException ex) {
			throw new BadRequestException("Product not found");
		} catch (NonUniqueResultException ex) {
			throw new BadRequestException("Multiple product found with same partcode " + btProducts.getManualId());
		}
		_exits.setHsnDetails(btProducts.getHsnDetails());
		_exits.setOwner(btProducts.getOwner());
		_exits.setActive(btProducts.isActive());
		_exits.setProductPrice(btProducts.getProductPrice());
		_exits.setProductType(btProducts.getProductType());
		_exits.setProductDescription(btProducts.getProductDescription());
		_exits.setSubProductFamily(btProducts.getSubProductFamily());
		workFlow.executeWorkflow(em, _exits, "UPDATE", "PRE", user);
		em.persist(_exits);
		workFlow.executeWorkflow(em, _exits, "UPDATE", "POST", user);
		return btProducts;
	}

	@Override
	public BtProducts deleteDetails(BtProducts btProducts, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		throw new BadRequestException("Function not supported yet");
	}

	@Override
	public String updateStatus(long productId, boolean status, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if (productId == 0)
			throw new BadRequestException("Product not found");
		BtProducts _exits = null;
		try {
			_exits = em.createQuery("select prd from BtProducts prd where prd.id=?1", BtProducts.class)
					.setParameter(1, productId).getSingleResult();

		} catch (NoResultException ex) {
			throw new BadRequestException("Product not found");
		} catch (NonUniqueResultException ex) {
			throw new BadRequestException("Multiple product found with same productId " + productId);
		}

		_exits.setActive(status);

		workFlow.executeWorkflow(em, _exits, "UPDATE", "PRE", user);
		em.persist(_exits);
		workFlow.executeWorkflow(em, _exits, "UPDATE", "POST", user);
		return "Product Status updated";
		// TODO Auto-generated method stub

	}

	@Override
	public BtProducts getProductWithId(long id) throws BadRequestException {
		BtProducts btProducts=null;
    	try {
    		
    		Query selectProductQuery=em.createQuery("select btproduct from BtProducts btproduct where btproduct.id=?1",BtProducts.class);
        	selectProductQuery.setParameter(1, id);
        	btProducts=(BtProducts) selectProductQuery.getSingleResult();
		}catch(NoResultException ex) {
			throw new BadRequestException("Product does not exist");
		}catch(NonUniqueResultException ex) {
			throw new BadRequestException("multiple Product exist with this Product Id");
		}
		return btProducts;
	}

}
