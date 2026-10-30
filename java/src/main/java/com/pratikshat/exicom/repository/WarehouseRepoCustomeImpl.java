package com.pratikshat.exicom.repository;



import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LegalEntity;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.Warehouse;
import com.pratikshat.exicom.model.request.WarehouseRequest;

@Repository
@Transactional 
public class WarehouseRepoCustomeImpl implements WarehouseRepoCustome {

	@PersistenceContext EntityManager entityManager;
	@Override
	 public WarehouseRequest saveWarehouse(WarehouseRequest request,LoginUser user) throws Exception {
		Warehouse warehouse=new Warehouse();
		warehouse.setCreatedBy(new EmployeeDetails(user.getUsername()));
		warehouse.setCreatedDateTime(new Date());
		warehouse.setWarehouseEmail(request.getWarehouseEmail());
		warehouse.setWarehouseName(request.getWarehouseName());
		warehouse.setWarehousePlant(request.getWarehousePlant());
		warehouse.setLegalEntity(new LegalEntity(request.getLegalEntityId()));
		warehouse.setModifiedBy(new EmployeeDetails(user.getUsername()));
		warehouse.setModifyiedDate(new Date());
		warehouse.setSynced(false);
		entityManager.persist(warehouse);
		request.setWarehouseId(warehouse.getWarehouseId());
		return request;
	}

}
