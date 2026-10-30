package com.pratikshat.exicom.repository.custom.impl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.BtProductRequest;

import com.pratikshat.exicom.entity.LoginUser;

import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;
import com.pratikshat.exicom.repository.custom.BtProductRequestRepositoryCustome;
import com.pratikshat.exicom.utils.DateConverter;


@Repository
public class BtProductRequestRepositoryCustomeImpl implements BtProductRequestRepositoryCustome {

	@PersistenceContext
	EntityManager em;
	
	@Autowired
	WorkflowExecuter workflow;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public BtProductRequest saveDetails(BtProductRequest t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		t.setTotalQuantityIssued(0);
		t.setTotalQuantityReceived(0);
		
		workflow.executeWorkflow(em, t, "INSERT", "PRE", user);
		em.persist(t);
		
		String manulaid="PR-BT-"+MiscellaneousUtils.fillZero(String.valueOf(t.getId()), 5);
		
		t.setManualId(manulaid);
		
		em.persist(t);
		
		workflow.executeWorkflow(em, t, "INSERT", "POST", user);
		
		return t;
	}

	@Override
	public BtProductRequest updateDetails(BtProductRequest t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if(t.getId()==null||t.getId().equals(0))
			throw new BadRequestException("Product request "+t.getManualId()+" not found");
		workflow.executeWorkflow(em, t, "UPDATE", "PRE", user);
		em.merge(t);
		workflow.executeWorkflow(em, t, "UPDATE","POST", user);
		return null;
	}

	@Override
	public BtProductRequest deleteDetails(BtProductRequest t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		return null;
	}

	@Override
	public List<Map<String, Object>> advanceSearch(Map<String, Object> request, List<Long> locationIds,
			LoginUser users) {
		List<Object> params=new ArrayList<Object>();
		String locationIdsMaps="";
		for(int i=0;i<locationIds.size();i++) {
			if(i!=0) {
				locationIdsMaps+=",";
			}
			locationIdsMaps+=locationIds.get(i).toString();
		}
		SimpleDateFormat formatOfDate=new SimpleDateFormat("dd-MM-yyyyy");
		
		
		StringBuilder query=new StringBuilder("SELECT pr.id AS productRequestId, \r\n" + 
				"pr.`created_date` AS productRequestCreatedDate,\r\n" + 
				"pr.`last_modified_date` AS productRequestLastModifiedDate,\r\n" + 
				"pr.`bt_product_request_status` AS productRequestStatus,\r\n" + 
				"pr.`description` AS productRequestDescription,\r\n" + 
				"pr.`dummy_status` AS productRequestDummayStatus,\r\n" + 
				"pr.`manual_id` AS productRequestManualId,\r\n" + 
				"pr.`need_by_date_time` AS productRequestNeedByDateTime,\r\n" + 
				"pr.`synced` AS productRequestScynced,\r\n" + 
				"pr.`total_quantity_issued` AS productRequestTotalQuantityIssued,\r\n" + 
				"pr.`total_quantity_received` AS productRequestTotalQuantityReceived,\r\n" + 
				"pr.`total_quantity_required` AS productRequestTotalQuantityRequired,\r\n" + 
				"pr.`created_by_email_id` AS productRequestCreatedByEmailId,\r\n" + 
				"pr.`last_modified_by_email_id` AS productRequestLastMdifiedByEmailId,\r\n" + 
				"pr.`destination_location_location_id` AS productRequestDestinationLocationId,\r\n" + 
				"pr.`owner_details_email_id` AS productRequestOwnerEmailId,\r\n" + 
				"pr.`source_location_location_id` AS productRequestSourceLocationId,\r\n" + 
				"sloc.location_name AS productRequestSourceLocationName,\r\n" + 
				"dloc.location_name AS productRequestDestinationLocationName,\r\n" + 
				"sloc.plant AS productRequestSourceLocationPlant,\r\n" + 
				"dloc.plant AS productRequestDestinationLocationPlant\r\n" + 
				"\r\n" + 
				"FROM `bt_product_request` pr \r\n" + 
				"JOIN `location` sloc ON sloc.location_id =pr.`source_location_location_id` \r\n" + 
				"JOIN location dloc ON pr.`destination_location_location_id`=dloc.location_id where 1=1");
		
		if(request.get("productRequestManualId")!=null&&!request.get("productRequestManualId").toString().equalsIgnoreCase("")) {
			query.append(" and pr.manual_id like ?");
			params.add("%"+request.get("productRequestManualId").toString()+"%");
		}
		if(request.get("productRequestStatus")!=null&&!request.get("productRequestStatus").toString().equalsIgnoreCase("")) {
			query.append(" and pr.bt_product_request_status like ? ");
			params.add("%"+request.get("productRequestStatus").toString()+"%");
		}
		if(request.get("productRequestFromDate")!=null&&request.get("productRequestToDate")!=null) {
			query.append(" and (pr.need_by_date_time between ? and ?)");
			params.add(DateConverter.convertToDate(request.get("productRequestFromDate").toString()));
			params.add(DateConverter.convertToDate(request.get("productRequestToDate").toString()));
		}
		if(request.get("productRequestSoruceLocationName")!=null&&!request.get("productRequestSoruceLocationName").toString().equalsIgnoreCase("")) {
			query.append(" and sloc.location_name like ?");
			params.add("%"+request.get("productRequestSoruceLocationName")+"%");
		}
		if(request.get("productRequestDestinationLocationName")!=null&&!request.get("productRequestDestinationLocationName").toString().equalsIgnoreCase("")) {
			query.append(" and dloc.location_name like ?");
			params.add("%"+request.get("productRequestDestinationLocationName")+"%");
		}
		
		query.append("and (sloc.location_id in (?) or dloc.location_id in(?))");
		params.add(locationIdsMaps);
		params.add(locationIdsMaps);
		
		return jdbcTemplate.queryForList(query.toString(), params.toArray());
	}

	@Override
	public void changeRequestStatus(Long productRequestId, String status,LoginUser user) throws BadRequestException,UnauthoriseException {
		BtProductRequest request=em.find(BtProductRequest.class, productRequestId);
		request.setBtProductRequestStatus(status);
		updateDetails(request, user);
	}

	@Override
	public void addTotalRequiredQty(Long productRequestId, Float qty, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		if(qty!=null&&qty>=0) {
			BtProductRequest req=em.find(BtProductRequest.class, productRequestId);
			req.setTotalQuantityRequired(req.getTotalQuantityRequired()+qty);
			em.merge(req);
		}
		else
			throw new BadRequestException("Invalid quantity");
		
	}

	@Override
	public void addTotalIssuedQty(Long productRequestId, Float qty, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		if(qty!=null&&qty>=0) {
			BtProductRequest req=em.find(BtProductRequest.class, productRequestId);
			Float tqty=req.getTotalQuantityIssued();
			tqty+=qty;
			req.setTotalQuantityIssued(tqty);
			if(req.getTotalQuantityRequired()<=tqty)
				req.setBtProductRequestStatus("Closed");
			em.merge(req);
		}
		else
			throw new BadRequestException("Invalid quantity");
		
	}

	@Override
	public void addTotalReceiedQty(Long productRequestId, Float qty, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		if(qty!=null&&qty>=0) {
			BtProductRequest req=em.find(BtProductRequest.class, productRequestId);
			req.setTotalQuantityReceived(req.getTotalQuantityReceived()+qty);
			em.merge(req);
		}
		else
			throw new BadRequestException("Invalid quantity");
		
	}

	@Override
	public void subtractTotalRequiredQty(Long productRequestId, Float qty, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		if(qty!=null&&qty>=0) {
			BtProductRequest req=em.find(BtProductRequest.class, productRequestId);

			Float tQty=req.getTotalQuantityRequired();
			if(tQty==null||tQty<=0)
				throw new BadRequestException("Invalid quantity, Quantity can not have negative value");
			tQty=tQty-qty;

			if(tQty<0)
				throw new BadRequestException("Invalid quantity, Quantity can not have negative value");

			req.setTotalQuantityRequired(tQty);
			
			em.merge(req);
		}
		else
			throw new BadRequestException("Invalid quantity");
		
	}

	@Override
	public void subtractTotalIssuedQty(Long productRequestId, Float qty, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		
		if(qty!=null&&qty>=0) {
			BtProductRequest req=em.find(BtProductRequest.class, productRequestId);

			Float tQty=req.getTotalQuantityIssued();
			if(tQty==null||tQty<=0)
				throw new BadRequestException("invalid quantity, Quantity can not have negative value");
			tQty=tQty-qty;

			if(tQty<0)
				throw new BadRequestException("invalid quantity, Quantity can not have negative value");

			req.setTotalQuantityIssued(tQty);
			
			em.merge(req);
		}
		else
			throw new BadRequestException("Invalid quantity");
		
	}

	@Override
	public void subtractTotalReceiedQty(Long productRequestId, Float qty, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if(qty!=null&&qty>=0) {
			BtProductRequest req=em.find(BtProductRequest.class, productRequestId);

			Float tQty=req.getTotalQuantityReceived();
			if(tQty==null||tQty<=0)
				throw new BadRequestException("Invalid quantity, Quantity can not have negative value");
			
			tQty=tQty-qty;
			
			if(tQty<0)
				throw new BadRequestException("Invalie quantity, Quantity can not have negative value");

			req.setTotalQuantityReceived(tQty);
			
			em.merge(req);
		}
		else
			throw new BadRequestException("Invalid quantity");
		
	}

	@Override
	public List<Map<String, Object>> advanceSearchByLocationId(Long locationId,Map<String,Object> request, List<Long> locationIds, LoginUser user,
			Boolean toMe) throws BadRequestException, UnauthoriseException {
				List<Object> params=new ArrayList<Object>();
				String locationIdsMaps="";
				for(int i=0;i<locationIds.size();i++) {
					if(i!=0) {
						locationIdsMaps+=",";
					}
					locationIdsMaps+=locationIds.get(i).toString();
				}
				SimpleDateFormat formatOfDate=new SimpleDateFormat("dd-MM-yyyyy");
				
				
				StringBuilder query=new StringBuilder("SELECT pr.id AS productRequestId, \r\n" + 
						"pr.`created_date` AS productRequestCreatedDate,\r\n" + 
						"pr.`last_modified_date` AS productRequestLastModifiedDate,\r\n" + 
						"pr.`bt_product_request_status` AS productRequestStatus,\r\n" + 
						"pr.`description` AS productRequestDescription,\r\n" + 
						"pr.`dummy_status` AS productRequestDummayStatus,\r\n" + 
						"pr.`manual_id` AS productRequestManualId,\r\n" + 
						"pr.`need_by_date_time` AS productRequestNeedByDateTime,\r\n" + 
						"pr.`synced` AS productRequestScynced,\r\n" + 
						"pr.`total_quantity_issued` AS productRequestTotalQuantityIssued,\r\n" + 
						"pr.`total_quantity_received` AS productRequestTotalQuantityReceived,\r\n" + 
						"pr.`total_quantity_required` AS productRequestTotalQuantityRequired,\r\n" + 
						"pr.`created_by_email_id` AS productRequestCreatedByEmailId,\r\n" + 
						"pr.`last_modified_by_email_id` AS productRequestLastMdifiedByEmailId,\r\n" + 
						"pr.`destination_location_location_id` AS productRequestDestinationLocationId,\r\n" + 
						"pr.`owner_details_email_id` AS productRequestOwnerEmailId,\r\n" + 
						"pr.`source_location_location_id` AS productRequestSourceLocationId,\r\n" + 
						"sloc.location_name AS productRequestSourceLocationName,\r\n" + 
						"dloc.location_name AS productRequestDestinationLocationName,\r\n" + 
						"sloc.plant AS productRequestSourceLocationPlant,\r\n" + 
						"dloc.plant AS productRequestDestinationLocationPlant\r\n" + 
						"\r\n" + 
						"FROM `bt_product_request` pr \r\n" + 
						"JOIN `location` sloc ON sloc.location_id =pr.`source_location_location_id` \r\n" + 
						"JOIN location dloc ON pr.`destination_location_location_id`=dloc.location_id where 1=1");

				if(toMe){
					query.append(" and sloc.location_id =? ");
					params.add(locationId);
				}
				else{
					query.append(" and dloc.location_id =?");
					params.add(locationId);
				}
				
				if(request.get("productRequestManualId")!=null&&!request.get("productRequestManualId").toString().equalsIgnoreCase("")) {
					query.append(" and pr.manual_id like ?");
					params.add("%"+request.get("productRequestManualId").toString()+"%");
				}
				if(request.get("productRequestStatus")!=null&&!request.get("productRequestStatus").toString().equalsIgnoreCase("")) {
					query.append(" and pr.dummy_status like ? ");
					params.add("%"+request.get("productRequestStatus").toString()+"%");
				}
				if(request.get("productRequestFromDate")!=null&&request.get("productRequestToDate")!=null) {
					query.append(" and (pr.need_by_date_time between ? and ?)");
					params.add(DateConverter.convertToDate(request.get("productRequestFromDate").toString()));
					params.add(DateConverter.convertToDate(request.get("productRequestToDate").toString()));
				}
				if(request.get("productRequestSoruceLocationName")!=null&&!request.get("productRequestSoruceLocationName").toString().equalsIgnoreCase("")) {
					query.append(" and sloc.location_name like ?");
					params.add("%"+request.get("productRequestSoruceLocationName")+"%");
				}
				if(request.get("productRequestDestinationLocationName")!=null&&!request.get("productRequestDestinationLocationName").toString().equalsIgnoreCase("")) {
					query.append(" and dloc.location_name like ?");
					params.add("%"+request.get("productRequestDestinationLocationName")+"%");
				}
				
				query.append("and (sloc.location_id in (?) or dloc.location_id in(?))");
				params.add(locationIdsMaps);
				params.add(locationIdsMaps);
				
				return jdbcTemplate.queryForList(query.toString(), params.toArray());
	}

}

