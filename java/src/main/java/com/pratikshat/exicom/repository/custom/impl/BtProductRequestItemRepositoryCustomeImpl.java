package com.pratikshat.exicom.repository.custom.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.BtProductRequestItem;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;
import com.pratikshat.exicom.repository.custom.BtProductRequestItemRepositoryCustome;

public class BtProductRequestItemRepositoryCustomeImpl implements BtProductRequestItemRepositoryCustome {

	@PersistenceContext
	EntityManager em;
	
	@Autowired
	WorkflowExecuter workFlow;
	
	@Autowired
	JdbcTemplate jdbc;
	
	@Override
	public BtProductRequestItem saveDetails(BtProductRequestItem t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		workFlow.executeWorkflow(em, t, "INSERT", "PRE", user);
		t.setQuantityIssued(0);
		t.setQuantityReceived(0);
		t.setOwner(new EmployeeDetails(user.getUsername()));
		em.persist(t);
		String manualId="PRI-BT-"+ MiscellaneousUtils.fillZero(String.valueOf(t.getId()),5);
		t.setManualId(manualId);
		em.merge(t);
		workFlow.executeWorkflow(em, t, "INSERT", "POST", user);
		
		return t;
	}

	@Override
	public BtProductRequestItem updateDetails(BtProductRequestItem t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		workFlow.executeWorkflow(em, t, "UPDATE","PRE", user);
		t.setOwner(new EmployeeDetails(user.getUsername()));
		em.merge(t);
		workFlow.executeWorkflow(em, t, "UPDATE", "POST", user);
		return t;
	}

	@Override
	public BtProductRequestItem deleteDetails(BtProductRequestItem t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if(t.getQuantityIssued()==0)
			em.remove(t);
		else
			throw new BadRequestException("Can not remote item from PR,Quantity already issued");
		
		workFlow.executeWorkflow(em, t, "UPDATE", "POST", user);
		return t;
	}

	@Override
	public void addRequiredQty(Long itemId, Float qty, LoginUser user)  throws BadRequestException,UnauthoriseException{
		BtProductRequestItem item=em.find(BtProductRequestItem.class,itemId);
		item.setQuantity(item.getQuantity()+qty);
		em.merge(item);
		workFlow.executeWorkflow(em, item, "UPDATE", "POST", user);
	}

	@Override
	public void addIssueQty(Long itemId, Float qty, LoginUser user)  throws BadRequestException,UnauthoriseException{
		BtProductRequestItem item=em.find(BtProductRequestItem.class, itemId);
		item.setQuantityIssued(item.getQuantityIssued()+qty);
		em.merge(item);
		workFlow.executeWorkflow(em, item, "UPDATE", "POST", user);
	}

	@Override
	public void addReceivedQty(Long itemId, Float qty, LoginUser user)  throws BadRequestException,UnauthoriseException{
		BtProductRequestItem item=em.find(BtProductRequestItem.class, itemId);
		item.setQuantityReceived(item.getQuantityReceived()+qty);
		
		em.merge(item);
		workFlow.executeWorkflow(em, item, "UPDATE", "POST", user);
	}

	@Override
	public void subtractRequiredQty(Long itemId, Float qty, LoginUser user)  throws BadRequestException,UnauthoriseException{
		BtProductRequestItem item=em.find(BtProductRequestItem.class, itemId);
		item.setQuantity(item.getQuantity()-qty);
		em.merge(item);
		workFlow.executeWorkflow(em, item, "UPDATE", "POST", user);
	}

	@Override
	public void subtractIssueQty(Long itemId, Float qty, LoginUser user)  throws BadRequestException,UnauthoriseException{
		BtProductRequestItem item=em.find(BtProductRequestItem.class, itemId);
		item.setQuantityIssued(item.getQuantityIssued()-qty);
		em.merge(item);
		workFlow.executeWorkflow(em, item, "UPDATE", "POST", user);
	}

	@Override
	public void subtractReceivedQty(Long itemId, Float qty, LoginUser user) throws BadRequestException,UnauthoriseException {
		BtProductRequestItem item=em.find(BtProductRequestItem.class, itemId);
		item.setQuantityReceived(item.getQuantityReceived()-qty);
		em.merge(item);
		workFlow.executeWorkflow(em, item, "UPDATE", "POST", user);
	}

	@Override
	public void changeItemsStatus(Long itemId, String status, LoginUser user)  throws BadRequestException,UnauthoriseException {
		BtProductRequestItem item=em.find(BtProductRequestItem.class, itemId);
		item.setItemStatus(status);
		em.merge(item);
		workFlow.executeWorkflow(em, item, "UPDATE", "POST", user);
	}

	@Override
	public void addItem(BtProductRequestItem item, LoginUser user) throws BadRequestException, UnauthoriseException {
		BtProductRequestItem _item=null;
		try {
			_item=em.createQuery("select item from BtProductRequestItem item where item.btProduct=?1 and item.btProductRequest=?2",BtProductRequestItem.class)
				.setParameter(1,item.getBtProduct())
				.setParameter(2,item.getBtProductRequest())
				.getSingleResult();
			addRequiredQty(_item.getId(), item.getQuantity(), user);
			
		}catch(NoResultException ex) {
			
			saveDetails(item, user);
			
		}catch(NonUniqueResultException ex) {
			throw new BadRequestException("Multiple item found for same partcode");
		}
	}

	@Override
	public List<Map<String, Object>> advanceSearch(Long productRequestId, Map<String, Object> search, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		List<Object> param=new ArrayList<Object>();
		
		StringBuilder sb=new StringBuilder();
		sb.append("SELECT \r\n" + 
			"item.id AS productRequestItemId,\r\n" + 
			"item.`created_date` AS productRequestItemCreatedDate,\r\n" + 
			"item.`last_modified_date` AS productRequestItemLastModifiedDate,\r\n" + 
			"item.`item_status` AS productRequestItemStatus,\r\n" + 
			"item.`description` AS productRequestItemDescription,\r\n" + 
			"item.`disabled_item` AS productRequestItemIsDisabled,\r\n" + 
			"item.`manual_id` AS productRequestItemManualId,\r\n" + 
			"item.`quantity` AS productRequestItemRequiredQuantity,\r\n" + 
			"item.`quantity_issued` AS productRequestItemIssuedQuantity,\r\n" + 
			"item.`quantity_received` AS productRequestItemReceivedQuantity,\r\n" + 
			"item.`synced` AS productRequestItemIsSynced,\r\n" + 
			"item.`created_by_email_id` AS productRequestItemCreatedByEmailId,\r\n" + 
			"item.`last_modified_by_email_id` AS productRequestItemLastModifiedByEmailId,\r\n" + 
			"item.`bt_product_id` AS productRequestItemProductId,\r\n" + 
			"item.`bt_product_request_id` AS productRequestId,\r\n" + 
			"prd.`manual_id` AS productRequestItemProductPartcode,\r\n" + 
			"prd.`product_price` AS productRequestItemProductPrice,\r\n" + 
			"prd.`product_type` AS productRequestItemProductType,\r\n" + 
			"prd.`product_description` AS productRequestItemProductDescription\r\n" + 
			"\r\n" + 
			"FROM `bt_product_request_item` item JOIN `bt_products` prd ON prd.id=item.bt_product_id where item.bt_product_request_id=?");
		param.add(productRequestId);
		if(search!=null) {
			if(search.get("productRequestItemStatus")!=null&&!search.get("productRequestItemStatus").toString().equalsIgnoreCase("")) {
				sb.append(" and item.item_status like ? ");
				param.add("%"+search.get("productRequestItemStatus").toString()+"%");
			}
			if(search.get("productRequestItemManualId")!=null&&!search.get("productRequestItemManualId").toString().equalsIgnoreCase("")) {
				sb.append(" and item.manual_id like ? ");
				param.add("%"+search.get("productRequestItemManualId")+"%");
			}
			if(search.get("productRequestItemProductPartcode")!=null&&!search.get("productRequestItemProductPartcode").toString().equalsIgnoreCase("")) {
				sb.append(" and prd.manual_id like ? ");
				param.add("%"+search.get("productRequestItemProductPartcode")+"%");
			}
		}
		
		return jdbc.queryForList(sb.toString(),param.toArray());
		
	}

}
