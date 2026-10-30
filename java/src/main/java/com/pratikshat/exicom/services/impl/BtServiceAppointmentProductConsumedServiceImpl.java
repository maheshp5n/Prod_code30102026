package com.pratikshat.exicom.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.BtLocationProductItem;
import com.pratikshat.exicom.entity.BtProductItem;
import com.pratikshat.exicom.entity.BtProducts;
import com.pratikshat.exicom.entity.BtServiceAppoinmentProductConsumed;
import com.pratikshat.exicom.entity.BtServiceAppointment;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.ProductConsumptionType;
import com.pratikshat.exicom.entity.ServiceRecource;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.BtLocationProductItemRepository;
import com.pratikshat.exicom.repository.BtProductItemRepository;
import com.pratikshat.exicom.repository.BtProductsRepository;
import com.pratikshat.exicom.repository.BtServiceAppoinmentProductConsumedRepository;
import com.pratikshat.exicom.repository.BtServiceAppointmentRepository;
import com.pratikshat.exicom.repository.ProductConsumptionTypeRepository;
import com.pratikshat.exicom.repository.ServiceRecourseRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtServiceAppointmentProductConsumedService;

@Service
public class BtServiceAppointmentProductConsumedServiceImpl implements BtServiceAppointmentProductConsumedService {

	@Autowired
	UserProfileRepository dbProfile;
	@Autowired
	BtServiceAppoinmentProductConsumedRepository dbProductConsumed;
	@Autowired
	BtServiceAppointmentRepository dbService;
	@Autowired
	ProductConsumptionTypeRepository dbProductConsumption;
	@Autowired
	BtProductsRepository dbProduct;
	@PersistenceContext
	EntityManager em;
	//@Autowired
	//BtLocationProductItemRepository dblp;
	@Autowired
	BtServiceAppoinmentProductConsumedRepository dbBtSAPC;
	
	@Autowired
	BtProductItemRepository dbStock;
	
	@Autowired
	ServiceRecourseRepository dbSr;

	@Override
	public Map<String, Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException {

		return null;
	}

	@Override
	public Page<Map<String, Object>> getPage(Pageable page, LoginUser user)
			throws BadRequestException, UnauthoriseException {

		return null;
	}

	
	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = { Exception.class, BadRequestException.class,
			UnauthoriseException.class, RuntimeException.class }, propagation = Propagation.REQUIRES_NEW)
	public Map<String, Object> saveObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		boolean create = dbProfile.hasObjectAccess(user.getUsername(), BtServiceAppoinmentProductConsumed.class,
				UserProfileRepository.CREATE);
		BtServiceAppoinmentProductConsumed appoinmentProductConsumed = new BtServiceAppoinmentProductConsumed();
		if (create) {
			if (request.get("serviceAppointmentId") == null
					|| request.get("serviceAppointmentId").toString().equalsIgnoreCase(""))
				throw new BadRequestException("Service Appointment is Null");

			if (request.get("productConsumption") == null
					|| request.get("productConsumption").toString().equalsIgnoreCase(""))
				throw new BadRequestException("No product ConsumptionType Found");
			BtServiceAppointment serviceAppointment = dbService
					.getServiceAppointment(Long.parseLong(request.get("serviceAppointmentId").toString()));
			if (serviceAppointment == null) {
				throw new BadRequestException("Service Apointment is not found");
			}
			appoinmentProductConsumed.setServiceAppointment(serviceAppointment);

			List<Long> claimStatusList = new ArrayList<Long>();
			claimStatusList.add(1L); // Submitted
			claimStatusList.add(3L); // Approved By SCH
			claimStatusList.add(5L); // Approved By Finance
			claimStatusList.add(7L); // Approved By RSH
			claimStatusList.add(9L); // Posted in SAP

			if (claimStatusList.contains(serviceAppointment.getClaimStatus().getClaimStatusId())) {
				throw new BadRequestException("You can not edit this record.");
			}

			if (!serviceAppointment.getOwnerDetails().getEmailId().equalsIgnoreCase(user.getUsername())) {
				throw new com.pratikshat.exicom.exception.BadRequestException("You are not authorised");
			}

			ProductConsumptionType productType = dbProductConsumption
					.getProductConsumptionTypeBtId(request.get("productConsumption").toString());
			ServiceRecource _serviceResource = null;
			try {
				javax.persistence.Query serviceResourceQuery = em.createQuery(
						"select sr from BtServiceAppointment srp join srp.serviceResource sr join sr.location lco where srp.serviceAppointmentId=?1",
						com.pratikshat.exicom.entity.ServiceRecource.class);
				serviceResourceQuery.setParameter(1, Long.parseLong(request.get("serviceAppointmentId").toString()));
				_serviceResource = (ServiceRecource) serviceResourceQuery.getSingleResult();
			} catch (NoResultException ex) {
			}

			if (_serviceResource == null) {
				throw new com.pratikshat.exicom.exception.BadRequestException(
						"Service Resource not assigned in this service appointment");
			}
			
			ServiceRecource _c=dbSr.getByEmail(user.getUsername());
			if(_serviceResource.getServiceResourceId()!=_c.getServiceResourceId())
				throw new UnauthoriseException("You are not authorised");
			
			
			
			BtProducts consumedProduct=dbProduct.findById(Long.parseLong(request.get("consumedProducts").toString())).get();
			
//			BtLocationProductItem items = dblp.getProductItemByLocationAndProduct(_serviceResource.getLocation(),
//					Long.parseLong(request.get("consumedProducts").toString()));

			if (productType.getConsumptionTypeName().contains("Consumed")) {
//				if (items.getOkQuantity() < Float.parseFloat(request.get("quantity").toString())) {
//					throw new com.pratikshat.exicom.exception.BadRequestException("Quantity not avilable");
//				}
//				float okQuantity = items.getOkQuantity();
//				float faultyQuantity = items.getFaultyQuantity();
//				okQuantity = okQuantity - Float.parseFloat(request.get("quantity").toString());
				appoinmentProductConsumed.setQuantity(Float.parseFloat(request.get("quantity").toString()));
				BtProducts consumedProducts = dbProduct
						.getById(Long.parseLong(request.get("consumedProducts").toString()));
				appoinmentProductConsumed.setConsumed(consumedProducts);
				appoinmentProductConsumed.setConsume(true);
				
				dbStock.subQuantity(consumedProducts, _serviceResource.getLocation(), Float.parseFloat(request.get("quantity").toString()), false, 1, user);
//				
//				items.setOkQuantity(okQuantity);
//				items.setFaultyQuantity(faultyQuantity);
//				items.setQuantityInOnHand(okQuantity + faultyQuantity);
//				dblp.updateDetails(items, user);
			}
			if (productType.getConsumptionTypeName().contains("Replaced")) {
//				if (items.getOkQuantity() < 1) {
//					throw new com.pratikshat.exicom.exception.BadRequestException("Quantity not avilable");
//				}
//				float okQuantity = items.getOkQuantity();
//				float faultyQuantity = items.getFaultyQuantity();
				//okQuantity = okQuantity - 1;
				appoinmentProductConsumed.setQuantity(1);
				//faultyQuantity = faultyQuantity + 1;
				BtProducts replacedProducts = dbProduct
						.getById(Long.parseLong(request.get("replacedProducts").toString()));
				BtProducts consumedProducts = dbProduct
						.getById(Long.parseLong(request.get("consumedProducts").toString()));
				appoinmentProductConsumed.setReplaced(replacedProducts);
				appoinmentProductConsumed.setConsumed(consumedProducts);
				appoinmentProductConsumed.setConsume(false);
//				items.setOkQuantity(okQuantity);
//				items.setFaultyQuantity(faultyQuantity);
//				items.setQuantityInOnHand(okQuantity + faultyQuantity);
//				dblp.updateDetails(items, user);
				dbStock.addQuantity(replacedProducts, _serviceResource.getLocation(), 1F, false, 2, user);
				dbStock.subQuantity(consumedProducts, _serviceResource.getLocation(), 1F, false, 1, user);
			}
			appoinmentProductConsumed.setConsumptionType(productType);
			//appoinmentProductConsumed.setItems(items);
			if(request.get("remark")!=null) {
				appoinmentProductConsumed.setRemark(request.get("remark").toString());
			}
			dbProductConsumed.saveDetails(appoinmentProductConsumed, user);
		} else {
			throw new UnauthoriseException("You are not authorise");
		}
		return request;
	}

	
	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = { Exception.class, BadRequestException.class,
			UnauthoriseException.class, RuntimeException.class }, propagation = Propagation.REQUIRES_NEW)
	public Map<String, Object> updateObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		return null;
	}


	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = { Exception.class, BadRequestException.class,
			UnauthoriseException.class, RuntimeException.class }, propagation = Propagation.REQUIRES_NEW)
	public void deleteProduct(long id, LoginUser user) throws BadRequestException, UnauthoriseException {
		boolean update = dbProfile.hasObjectAccess(user.getUsername(), BtServiceAppoinmentProductConsumed.class,
				UserProfileRepository.EDIT);
		BtServiceAppoinmentProductConsumed appoinmentProductConsumed = null;
		if (update) {
			BtServiceAppointment sa = null;
			appoinmentProductConsumed = dbBtSAPC.getproductConsumed(id);
			sa = dbService.findByServiceAppointmentId(appoinmentProductConsumed.getServiceAppointment().getId()) ;
			List<Long> claimStatusList = new ArrayList<Long>();
			claimStatusList.add(1L); // Submitted
			claimStatusList.add(3L); // Approved By SCH
			claimStatusList.add(5L); // Approved By Finance
			claimStatusList.add(7L); // Approved By RSH
			claimStatusList.add(9L); // Posted in SAP
			ServiceRecource sr=dbSr.findByServiceResourceId(sa.getServiceResource().getServiceResourceId());
			ServiceRecource _c=dbSr.getByEmail(user.getUsername());
			System.out.println(sa.getServiceResource().getServiceResourceId()+ " "+_c.getServiceResourceId());
			if(sr.getServiceResourceId()!=_c.getServiceResourceId())
				throw new UnauthoriseException("You are not authorised");
			
			if (claimStatusList.contains(sa.getClaimStatus().getClaimStatusId())) {
				throw new BadRequestException("Can not remove item");
			}
			
			
			//BtLocationProductItem items = appoinmentProductConsumed.getItems();
//			float okQuantity = items.getOkQuantity();
//			float faultyQuantity = items.getFaultyQuantity();
			if (appoinmentProductConsumed.getConsumptionType().getConsumptionTypeName().contains("Replaced")) {
//				faultyQuantity = faultyQuantity - 1;
				dbStock.addQuantity(appoinmentProductConsumed.getConsumed(),sr.getLocation(), 1F, false, 1, user);
				dbStock.subQuantity(appoinmentProductConsumed.getReplaced(), sr.getLocation(), 1F, false, 2, user);
			}
			else {
				dbStock.addQuantity(appoinmentProductConsumed.getConsumed(),sr.getLocation(), appoinmentProductConsumed.getQuantity(), false, 1, user);
			}
//			okQuantity = okQuantity + appoinmentProductConsumed.getQuantity();
//			items.setOkQuantity(okQuantity);
//			items.setFaultyQuantity(faultyQuantity);
//			items.setQuantityInOnHand(okQuantity + faultyQuantity);
//			dblp.updateDetails(items, user);
			dbProductConsumed.deleteItem(appoinmentProductConsumed);
		} else {
			throw new UnauthoriseException("You are not authorise");
		}
	}

	@Override
	public List<Map<String, Object>> searchForList(String search, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> advanceSearch(Map<String, Object> searchRequest, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

}
