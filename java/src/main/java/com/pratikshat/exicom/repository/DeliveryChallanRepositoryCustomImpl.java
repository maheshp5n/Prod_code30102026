package com.pratikshat.exicom.repository;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.DeliveryChallan;
import com.pratikshat.exicom.entity.DeliveryChallanShipmentMode;
import com.pratikshat.exicom.entity.DeliveryChallanStatus;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LProductRequest;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.Requisition;
import com.pratikshat.exicom.model.request.DeliveryChallanRequest;

@Repository
public class DeliveryChallanRepositoryCustomImpl implements DeliveryChallanRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	com.pratikshat.exicom.interceptor.WorkflowExecuter workflow;
	
	@Transactional(isolation = Isolation.SERIALIZABLE)
	@Override
	public DeliveryChallanRequest createDeliveryChallan(DeliveryChallanRequest deliveryChallanRequest, LoginUser user)
			throws Exception {
		if(deliveryChallanRequest!=null) {
			DeliveryChallan _deliveryChallan = new DeliveryChallan();
			
			Query getRequisition = entityManager.createQuery("select pr from LProductRequest pr where pr.locationProductRequestId=?1",LProductRequest.class);
			getRequisition.setParameter(1, deliveryChallanRequest.getProductRequestId());
			LProductRequest productRequest = (LProductRequest) getRequisition.getSingleResult();
			
			_deliveryChallan.setProductRequest(new LProductRequest(deliveryChallanRequest.getProductRequestId()));
			
			_deliveryChallan.setToLocation(productRequest.getDestinationLocation());
			_deliveryChallan.setFromLocation(productRequest.getSourceLocation());
			
			//_deliveryChallan.setQuantityIssued(requisition.get);
			
			_deliveryChallan.setCreatedBy(new EmployeeDetails(user.getUsername()));
			_deliveryChallan.setCreatedDate(new Date());
			
			_deliveryChallan.setModifiedBy(new EmployeeDetails(user.getUsername()));
			_deliveryChallan.setModifiedDate(new Date());
			
			_deliveryChallan.setDeliveryChallanShipmentMode(new DeliveryChallanShipmentMode(deliveryChallanRequest.getId()));
			
			_deliveryChallan.setDeliveryChallanStatus(new DeliveryChallanStatus(deliveryChallanRequest.getId()));
			
			_deliveryChallan.setEmail(deliveryChallanRequest.getEmail());
			_deliveryChallan.setName(deliveryChallanRequest.getName());
			_deliveryChallan.setNumber(deliveryChallanRequest.getNumber());
			
			_deliveryChallan.setReceivedBy(deliveryChallanRequest.getReceivedBy());
			_deliveryChallan.setReceivedQuantity(deliveryChallanRequest.getReceivedQuantity());
			_deliveryChallan.setSendShippingMail(deliveryChallanRequest.isSendShippingMail());
			_deliveryChallan.setShippingDate(deliveryChallanRequest.getShippingDate());
			workflow.executeWorkflow(entityManager, _deliveryChallan, "INSERT", "PRE", user);
			entityManager.persist(_deliveryChallan);
			workflow.executeWorkflow(entityManager, _deliveryChallan, "INSERT", "POST", user);
			
			Query updateManualId = entityManager.createQuery("update DeliveryChallan dc set dc.manualId=?1 where dc.deliveryChallanId=?2");
			SimpleDateFormat dateformatter = new SimpleDateFormat("yyyyMMdd");
			String manualId = "DC-" + dateformatter.format(new java.util.Date()) + "-"
					+ com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(_deliveryChallan.getDeliveryChallanId()),
							5);
			updateManualId.setParameter(1, manualId);
			updateManualId.setParameter(2, _deliveryChallan.getDeliveryChallanId());
			updateManualId.executeUpdate();
			
			deliveryChallanRequest.setId(_deliveryChallan.getDeliveryChallanId());
			
			
			// search index entry
						try {
							com.pratikshat.exicom.entity.GlobalSearchIndex _search = new com.pratikshat.exicom.entity.GlobalSearchIndex();
							_search.setKeyName("manualId");
							_search.setObjectDetailsUrl("deliverychallan/" + _deliveryChallan.getDeliveryChallanId());
							_search.setObjectIdName("deliveryChallanId");
							_search.setObjectIdValue("" + _deliveryChallan.getDeliveryChallanId());
							_search.setObjectName("DeliveryChallan");
							_search.setSearchString(manualId);
							entityManager.persist(_search);
							
						} catch (Exception e) {
						}
						
						
						entityManager.flush();
						entityManager.clear();
						return deliveryChallanRequest;
		}else {
			throw new Exception("No data found to save details");
		}
	}

}
