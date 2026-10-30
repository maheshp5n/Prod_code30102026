package com.pratikshat.exicom.repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.GlobalSearchIndex;
import com.pratikshat.exicom.entity.Location;
import com.pratikshat.exicom.entity.LocationProductItem;
import com.pratikshat.exicom.entity.LocationProductLineItems;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.ProductLineItem;
import com.pratikshat.exicom.entity.Products;
import com.pratikshat.exicom.entity.ServiceOrderItem;
import com.pratikshat.exicom.entity.ServiceOrderItemReceive;
import com.pratikshat.exicom.entity.ServiceOrderJobCard;
import com.pratikshat.exicom.entity.ServiceRecource;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

@Repository
public class ServiceOrderJobCardRepositoryCustomeImpl implements ServiceOrderJobCardRepositoryCustome {
	@PersistenceContext
	EntityManager em;
	
	@Autowired
	ProductLineItemRepository dbProductLineItem; 
	
	@Autowired
	JdbcTemplate jdbc;
	
	private static final Logger logger=LoggerFactory.getLogger(ServiceOrderJobCardRepositoryCustomeImpl.class);

	@Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			Exception.class, RuntimeException.class,BadRequestException.class,UnauthoriseException.class, SQLException.class })
	@Override
	public void createJobCard(List<Long> items, long serviceresourceId, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		EmployeeDetails emp=new EmployeeDetails(user.getUsername());
		Date today=new Date();
		
		Location sourceLoation=null;
		Location destinationLocation=null;
		ServiceRecource serviceResource=null;
		ServiceRecource sourceResource=null;
		
		try {
			serviceResource=em.createQuery("select sr from ServiceRecource sr join sr.location loc join sr.user usr where sr.serviceResourceId=?1",ServiceRecource.class)
					.setParameter(1,serviceresourceId)
					.getSingleResult();
					
		}catch(NoResultException ex) {
			throw new BadRequestException("Invalid service resource id");
		}
		catch(NonUniqueResultException ex) {
			throw new BadRequestException("Invalid request");
		}
		
		destinationLocation=serviceResource.getLocation();
		
		try {
			sourceResource=em.createQuery("select sr from ServiceRecource sr join sr.location loc where sr.user=?1",ServiceRecource.class).setParameter(1, new EmployeeDetails(user.getUsername())).getSingleResult();
			sourceLoation=sourceResource.getLocation();
//			sourceLoation = em.createQuery("select sl from Location sl where sl.owner=?1",Location.class)
//					.setParameter(1,new EmployeeDetails(user.getUsername()))
//					.getSingleResult();
		}catch(NoResultException ex) {
			throw new BadRequestException("Repaircenter warehouse not found");
		}catch(NonUniqueResultException ex) {
			throw new BadRequestException("Multiple reparicenter warehouse found");
		}
		
		for(Long item:items) {
			ServiceOrderItemReceive _receive=null;
			try {
				
				_receive=em.createQuery("select rcv from ServiceOrderItemReceive rcv join rcv.item itm join itm.serviceOrder ord where rcv=?1",ServiceOrderItemReceive.class)
						.setParameter(1, new ServiceOrderItemReceive(new ServiceOrderItem(item)))
						.getSingleResult();
				
			}catch(NoResultException ex) {
				
				throw new BadRequestException("Invalid ID");
				
			}catch(NonUniqueResultException ex) {
				
				throw new BadRequestException("Invalid ID");
			}
			
			if(_receive.getItem().getServiceOrder().getSoStatus().equalsIgnoreCase("Archived"))
				throw new BadRequestException("Service Order is archived, cant not change records");
			
			if(_receive.getItem().getServiceOrder().getRepariCenter().getLocationId()!=sourceLoation.getLocationId()) {
				throw new UnauthoriseException("You are not authorise 1");
			}
			
			LocationProductLineItems sourceLocationProductLineItem=null;
			try {
				sourceLocationProductLineItem =em.createQuery("select litem from LocationProductLineItems litem join litem.locationProductItem item where litem.itemSerialNumber=?1 and item.product=?2",LocationProductLineItems.class)
						.setParameter(1, _receive.getItem().getSerialNumber())
						.setParameter(2, _receive.getItem().getProduct())
						.getSingleResult();
			}catch(NoResultException ex) {
				throw new BadRequestException(String.format("Product %s with serial number %s not found",_receive.getItem().getProduct().getProductPartCode() ,_receive.getItem().getSerialNumber()));
			}catch(NonUniqueResultException ex) {
				throw new BadRequestException(String.format("Product %s with serial number %s having multiple times",_receive.getItem().getProduct().getProductPartCode(),_receive.getItem().getSerialNumber()));
			}
			
			if(sourceLocationProductLineItem.getLocationProductItem().getLocation().getLocationId()!=sourceLoation.getLocationId()) {
				throw new UnauthoriseException("You are not authorise 2");
			}
			
			// DECREASE STOCK FROM SOURCE LOCATION
			
			LocationProductItem sourceLocationProductITem=sourceLocationProductLineItem.getLocationProductItem();
			
			long statuscode=sourceLocationProductLineItem.getLocationProeuctLineItemState().getProductStateId();
			
			if(statuscode==1)
				sourceLocationProductITem.setOkQuantity(sourceLocationProductITem.getOkQuantity()-1);
			if(statuscode==2)
				sourceLocationProductITem.setFaultyQuantity(sourceLocationProductITem.getFaultyQuantity()-1);
			if(statuscode==3)
				sourceLocationProductITem.setScrapQty(sourceLocationProductITem.getScrapQty()-1);
			if(statuscode==4)
				sourceLocationProductITem.setRejectedQty(sourceLocationProductITem.getRejectedQty()-1);
			if(statuscode==5)
				sourceLocationProductITem.setBerQty(sourceLocationProductITem.getBerQty()-1);

			if(sourceLocationProductLineItem.isCustomerInventory())
				sourceLocationProductITem.setCustomerInventory(sourceLocationProductITem.getCustomerInventory()-1);
				
			sourceLocationProductITem.setQuantityInOnHand(sourceLocationProductITem.getQuantityInOnHand()-1);
			
			em.persist(sourceLocationProductITem);
			
			//INCREASE TO DESTINATION LOCATION
			
			LocationProductItem destLocationProductItem=null;
			try {
				destLocationProductItem=em.createQuery("select item from LocationProductItem item where item.product=?1 and item.location=?2",LocationProductItem.class)
						.setParameter(1, sourceLocationProductITem.getProduct())
						.setParameter(2, destinationLocation)
						.getSingleResult();
			}catch(NoResultException ex) {
				
				destLocationProductItem=new LocationProductItem();
				destLocationProductItem.setCreatedBy(emp);
				destLocationProductItem.setCreatedDate(today);
				destLocationProductItem.setHslQuantity(0);
				destLocationProductItem.setHslStatus("L");
				destLocationProductItem.setIssuedQunatity(0);
				destLocationProductItem.setLocation(destinationLocation);
				destLocationProductItem.setModifiedBy(emp);
				destLocationProductItem.setModifiedDate(today);
				destLocationProductItem.setMslQuantity(0);
				destLocationProductItem.setMslQuantityStatus("L");
				destLocationProductItem.setProduct(sourceLocationProductITem.getProduct());
				destLocationProductItem.setQuantityUnitOfMasur("NO");
				//QTY INIT FOR NEW LOCATION PRODUCT ITEMS WITH 0
				
				destLocationProductItem.setOkQuantity(0);
				destLocationProductItem.setFaultyQuantity(0);
				destLocationProductItem.setScrapQty(0);
				destLocationProductItem.setRejectedQty(0);
				destLocationProductItem.setBerQty(0);
				destLocationProductItem.setCustomerInventory(0);
				destLocationProductItem.setQuantityInOnHand(0);
				
			}catch(NonUniqueResultException ex) {
				new BadRequestException("Invalid request");//KUCH TO GADBAR HAI DYA
			}
			
			if(statuscode==1)
				destLocationProductItem.setOkQuantity(destLocationProductItem.getOkQuantity()+1);
			if(statuscode==2)
				destLocationProductItem.setFaultyQuantity(destLocationProductItem.getFaultyQuantity()+1);
			if(statuscode==3)
				destLocationProductItem.setScrapQty(destLocationProductItem.getScrapQty()+1);
			if(statuscode==4)
				destLocationProductItem.setRejectedQty(destLocationProductItem.getRejectedQty()+1);
			if(statuscode==5)
				destLocationProductItem.setBerQty(destLocationProductItem.getBerQty()+1);


			if(sourceLocationProductLineItem.isCustomerInventory())
				destLocationProductItem.setCustomerInventory(destLocationProductItem.getCustomerInventory()+1);

			destLocationProductItem.setQuantityInOnHand(destLocationProductItem.getQuantityInOnHand()+1);
			
			destLocationProductItem.setModifiedBy(emp);
			destLocationProductItem.setModifiedDate(today);
			
			em.persist(destLocationProductItem);
			
			if(destLocationProductItem.getLocationProductItemId()==null) {
				String manualId=String.format("PI-%s",MiscellaneousUtils.fillZero(String.valueOf(destLocationProductItem.getId()), 5));
				destLocationProductItem.setLocationProductItemId(manualId);
				em.persist(destLocationProductItem);
			}
			
			sourceLocationProductLineItem.setLocationProductItem(destLocationProductItem);
			em.persist(sourceLocationProductLineItem);
			
			_receive.setAssignedTo(serviceResource);
			_receive.setLastModifiedBy(emp);
			_receive.setLastModifiedDate(today);
			em.persist(_receive);
			
			
			
			//SAVE JOB CARD
			
			ServiceOrderJobCard jobCard=new ServiceOrderJobCard();
			jobCard.setAssignedDate(today);
			jobCard.setCreatedBy(emp);
			jobCard.setItemId(_receive);
			jobCard.setLastModifiedBy(emp);
			jobCard.setLastModifiedDate(today);
			jobCard.setOwner(serviceResource.getUser());
			jobCard.setRepairEng(serviceResource);
			jobCard.setReparied(false);
			jobCard.setJobCardStatus("Submitted");
			em.persist(jobCard);
			
			String manualIdJobCard=String.format("JC-%s",MiscellaneousUtils.fillZero(String.valueOf(jobCard.getId()),5));
			jobCard.setManualId(manualIdJobCard);
			em.persist(jobCard);
			
			ProductLineItem pLineItem=dbProductLineItem.findByProductAndSerialNumber(destLocationProductItem.getProduct(), sourceLocationProductLineItem.getItemSerialNumber());
			if(pLineItem!=null) {
				pLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
				pLineItem.setModifiedDate(new Date());
				pLineItem.setPosition("JC,"+jobCard.getId());
				pLineItem.setPositionState(ProductLineItemRepository.ASSIGNED_TO_ENGINEER_FOR_REPAIR);
				dbProductLineItem.save(pLineItem);
			}
			else {
				pLineItem=new ProductLineItem();
				pLineItem.setCreatedBy(new EmployeeDetails(user.getUsername()));
				pLineItem.setCreatedDate(new Date());
				pLineItem.setProduct(destLocationProductItem.getProduct());
				pLineItem.setSerialNumber(sourceLocationProductLineItem.getItemSerialNumber());
				pLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
				pLineItem.setModifiedDate(new Date());
				pLineItem.setPosition("JC,"+jobCard.getId());
				pLineItem.setPositionState(ProductLineItemRepository.ASSIGNED_TO_ENGINEER_FOR_REPAIR);
				dbProductLineItem.save(pLineItem);
			}
			
			// Global Search Entry Point
			try {
				GlobalSearchIndex _search=new GlobalSearchIndex();
				_search.setSearchString(manualIdJobCard);
				_search.setKeyName("manualId");
				_search.setObjectName("ServiceOrderJobCard");
				_search.setObjectDetailsUrl("repairdct/"+jobCard.getId());
				_search.setObjectIdName("id");
				_search.setObjectIdValue(String.valueOf(jobCard.getId()));
				
				em.persist(_search);
				
			}catch(Exception e) {}
		}
		
	}

	@Override
	public List<Map<String, Object>> advanceSearch(Map<String, Object> searchkey, LoginUser user,List<String>lstUsers,boolean viewAll)
			throws BadRequestException, UnauthoriseException {
		String genQuery="SELECT \r\n" + 
				"	jcard.`assigned_date` AS assignedDate,\r\n" + 
				"	jcard.`item_id_item_id` AS itemId,\r\n" + 
				"	jcard.`last_modified_by_email_id` AS lastMdifiedByEmailId,\r\n" + 
				"	jcard.`last_modified_date` AS lastModifiedDate,\r\n" + 
				"	jcard.`manual_id` AS jobCardManualId,\r\n" + 
				"	jcard.`id` AS jobCardId,\r\n" + 
				"	jcard.`owner_email_id` AS jobCardOwnerEmailId,\r\n" + 
				"	jcard.`repair_eng_service_resource_id` AS resourcId,\r\n" + 
				"	jcard.`reparied` AS reparied,\r\n" + 
				"	jcard.`returned` as returned,\r\n"+
				"	item.`circle` AS itemCircle,\r\n" + 
				"	item.`customer_inventory` AS customerInventory,\r\n" + 
				"	item.`customer_name` AS customerName,\r\n" + 
				"	item.`customer_sap` AS customerSapName,\r\n" + 
				"	item.`id` AS serviceOrderItemId,\r\n" + 
				"	item.`product_product_part_code` AS partCode,\r\n" + 
				"	item.`serial_number` AS partCodeSerialNumber,\r\n" + 
				"	item.`service_order_id` AS serviceOrderId,\r\n" + 
				"	res.`service_resource_id` AS engId,\r\n" + 
				"	res.`name` AS engName,\r\n" + 
				"	prd.`pdoduct_description` as productDescription,\r\n"+
				"	prd.`product_type` as productType,\r\n"+
				"	pstate.`product_state_name` AS state,\r\n"+
				"	item.`remark` AS `remarks`,\r\n"+
				"	item.`lot_no` AS `lotNo`\r\n"+
				"\r\n" + 
				"	FROM `service_order_job_card` jcard\r\n" + 
				"	JOIN `service_order_item_receive` sitem ON sitem.`item_id`=jcard.`item_id_item_id`\r\n" + 
				"	JOIN `service_order_item` item ON item.`id`=sitem.`item_id`\r\n" + 
				"	JOIN `service_recource` res ON res.`service_resource_id`=jcard.`repair_eng_service_resource_id`\r\n" + 
				" 	JOIN `products` prd ON prd.`product_part_code`=item.`product_product_part_code`\r\n"+
				"	JOIN `product_line_item_product_state` pstate ON pstate.`product_state_id`=item.`state_product_state_id`"+
				"	\r\n" + 
				"	WHERE jcard.job_card_status <>'Archived'\r\n";
		if(lstUsers!=null) {
			StringBuilder usbu=new StringBuilder();
			int i=0;
			for(String email:lstUsers) {
				if(i!=0)
					usbu.append(",");
				usbu.append("'"+email+"'");
				i++;
			}
			genQuery+=" and jcard.`owner_email_id` IN ("+usbu+")";
		}
		else {
			if(!viewAll)
				genQuery+=" and jcard.owner_email_id like '"+user.getUsername()+"'";
		}
		List<Object> paramArray=new ArrayList<Object>();
		if(searchkey!=null) {
			
			//logger.info("raw data",searchkey);
			
			if(searchkey.get("job_card_number")!=null&&!searchkey.get("job_card_number").toString().equals("")) {
				//logger.info("job card number ");
				//logger.info(searchkey.get("job_card_number").toString());
				genQuery+=" and jcard.`manual_id` like ?";
				paramArray.add(("%"+searchkey.get("job_card_number").toString()+"%"));
			}
			if(searchkey.get("product_serial_number")!=null&&!searchkey.get("product_serial_number").toString().equals("")) {
				genQuery+=" and item.`serial_number` like ?";
				paramArray.add("%"+searchkey.get("product_serial_number").toString()+"%");
			}
			if(searchkey.get("product_part_code")!=null&&!searchkey.get("product_part_code").toString().equals("")) {
				genQuery+=" and item.`product_product_part_code` like ?";
				paramArray.add("%"+searchkey.get("product_part_code").toString()+"%");
			}
			if(searchkey.get("product_description")!=null&&!searchkey.get("product_description").toString().equals("")) {
				
				//logger.info("product description");
				//logger.info(searchkey.get("product_description").toString());
				genQuery+=" and prd.`pdoduct_description` like ?";
				paramArray.add("%"+searchkey.get("product_description").toString()+"%");
			}
			if(searchkey.get("assigned_date_from")!=null
					&&!searchkey.get("assigned_date_from").toString().equals("")
					&&searchkey.get("assigned_date_to")!=null
					&&!searchkey.get("assigned_date_to").toString().equals("")) {
				genQuery+=" and (jcard.`assigned_date` between ? and ?)";
				paramArray.add(searchkey.get("assigned_date_from").toString());
				paramArray.add(searchkey.get("assigned_date_to").toString());
			}
			if(searchkey.get("isRepair")!=null&&!searchkey.get("isRepair").toString().equalsIgnoreCase("")) {
				genQuery+=" and jcard.reparied="+(searchkey.get("isRepair").toString().equalsIgnoreCase("true")?"true":"false");
				//paramArray.add(searchkey.get("isRepair"));
			}
			if(searchkey.get("isReturn")!=null&&!searchkey.get("isReturn").toString().equalsIgnoreCase("")) {
				genQuery+=" and jcard.returned="+(searchkey.get("isReturn").toString().equalsIgnoreCase("true")?"true":"false");
				//paramArray.add(searchkey.get("isReturn"));
			}
			
			//logger.info(genQuery);
			//logger.info("parameter ",paramArray);
			
			if(paramArray.size()>0)
			{
				for(Object ob:paramArray) {
					//logger.info("Obj {}",ob.toString());
				}
				return jdbc.queryForList(genQuery,paramArray.toArray());
			}
				
			else
				return jdbc.queryForList(genQuery);
		}
		
		
			
		return jdbc.queryForList(genQuery);
	}
}
