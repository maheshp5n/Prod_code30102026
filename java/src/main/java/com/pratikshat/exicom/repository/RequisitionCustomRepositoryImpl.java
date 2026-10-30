package com.pratikshat.exicom.repository;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.Accounts;
import com.pratikshat.exicom.entity.BusinessDomain;
import com.pratikshat.exicom.entity.CaseDetails;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.Oppertunity;
import com.pratikshat.exicom.entity.OppertunityForecastCategory;
import com.pratikshat.exicom.entity.OppertunityStage;
import com.pratikshat.exicom.entity.OppertunityType;
import com.pratikshat.exicom.entity.ProductType;
import com.pratikshat.exicom.entity.Products;
import com.pratikshat.exicom.entity.Requisition;
import com.pratikshat.exicom.entity.RequisitionApprovalStatus;
import com.pratikshat.exicom.entity.RequisitionProductDetails;
import com.pratikshat.exicom.entity.RequisitionProductType;
import com.pratikshat.exicom.entity.RequisitionStatus;
import com.pratikshat.exicom.entity.Warehouse;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.RequisitionProductDetailsRequest;
import com.pratikshat.exicom.model.request.RequisitionRequest;

public class RequisitionCustomRepositoryImpl implements RequisitionCustomRepository {

	@PersistenceContext
	EntityManager entityManager;

	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	@Override
	public RequisitionRequest createRequisition(RequisitionRequest requisitionRequest, LoginUser user)
			throws Exception {
		if (requisitionRequest != null) {
			Requisition _requisition = new Requisition();

			_requisition.setFromWarehouse(new Warehouse(requisitionRequest.getFromWarehouseId()));
			_requisition.setToWarehouse(new Warehouse(requisitionRequest.getToWarehouseId()));
			_requisition.setReceivedStatus(false);
			_requisition.setRequiredDate(requisitionRequest.getRequiredDate());
			_requisition.setRequisitionApprovalStatus(new RequisitionApprovalStatus(requisitionRequest.getApprovalStatusId()));
			_requisition.setRequisitionProductType(new RequisitionProductType(requisitionRequest.getProductTypeId()));
			_requisition.setRequisitionStatus(new RequisitionStatus(requisitionRequest.getRequisitionStatusId()));
			_requisition.setSentStatus(false);
			_requisition.setCreatedBy(new EmployeeDetails(user.getUsername()));
			_requisition.setCreatedDate(new Date());
			_requisition.setModifiedBy(new EmployeeDetails(user.getUsername()));
			_requisition.setModifiedDate(new Date());
			_requisition.setOwner(new EmployeeDetails(user.getUsername()));

			/*
			 * if(requisitionRequest.getProductList()!=null) {
			 * Set<RequisitionProductDetails> productSet = new
			 * HashSet<RequisitionProductDetails>();
			 * 
			 * Set<RequisitionProductDetailsRequest> productRequest =
			 * requisitionRequest.getProductList();
			 * Iterator<RequisitionProductDetailsRequest> iterator =
			 * productRequest.iterator(); while(iterator.hasNext()) {
			 * RequisitionProductDetailsRequest request = iterator.next();
			 * RequisitionProductDetails requisitionProductDetails = new
			 * RequisitionProductDetails(); requisitionProductDetails.setProducts(new
			 * Products(request.getProductPartCode()));
			 * requisitionProductDetails.setQuantitty(request.getQuantity());
			 * 
			 * productSet.add(requisitionProductDetails); }
			 * _requisition.setRequisitionProductDetails(productSet); }
			 */

			entityManager.persist(_requisition);
			

			Query updateManualId = entityManager.createQuery("update Requisition rq set rq.manualId=?1 where rq.id=?2");
			SimpleDateFormat dateformatter = new SimpleDateFormat("yyyyMMdd");
			String manualId = "REQ-" + dateformatter.format(new java.util.Date()) + "-"
					+ com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(_requisition.getId()), 5);
			updateManualId.setParameter(1, manualId);
			updateManualId.setParameter(2, _requisition.getId());
			updateManualId.executeUpdate();

			if (requisitionRequest.getProductList() != null) {
				Set<RequisitionProductDetailsRequest> productRequest = requisitionRequest.getProductList();
				Iterator<RequisitionProductDetailsRequest> iterator = productRequest.iterator();
				while (iterator.hasNext()) {
					RequisitionProductDetailsRequest request = iterator.next();
					RequisitionProductDetails requisitionProductDetails = new RequisitionProductDetails();
					requisitionProductDetails.setProducts(new Products(request.getProductPartCode()));
					requisitionProductDetails.setQuantitty(request.getQuantity());
					requisitionProductDetails.setRequisition(_requisition);

					entityManager.persist(requisitionProductDetails);
					
				}

			}

			requisitionRequest.setRequisitionId(_requisition.getId());

			// search index entry
			try {
				com.pratikshat.exicom.entity.GlobalSearchIndex _search = new com.pratikshat.exicom.entity.GlobalSearchIndex();
				_search.setKeyName("manualId");
				_search.setObjectDetailsUrl("requisition/" + _requisition.getId());
				_search.setObjectIdName("id");
				_search.setObjectIdValue("" + _requisition.getId());
				_search.setObjectName("Requisition");
				_search.setSearchString(manualId);
				entityManager.persist(_search);
				
			} catch (Exception e) {
			}
			entityManager.flush();
			entityManager.clear();
			return requisitionRequest;
		} else {
			throw new Exception("No data found to save details");
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class,SQLException.class})
	@Override
	public RequisitionRequest updateRequisition(RequisitionRequest requisitionRequest, LoginUser user)
			throws Exception {
		if(requisitionRequest!=null) {
			Query selectRequisitionQuery=entityManager.createQuery("select rq from Requisition rq where rq.id=?1",Requisition.class);
			selectRequisitionQuery.setParameter(1,requisitionRequest.getRequisitionId());
			Requisition _requisition=(Requisition)selectRequisitionQuery.getSingleResult();
			if(_requisition!=null) {
				
				Query deleteRequisitionProductDetailsQuery = entityManager.createQuery("delete RequisitionProductDetails rpd  where rpd.requisition.id=?1");
				deleteRequisitionProductDetailsQuery.setParameter(1, requisitionRequest.getRequisitionId());
				deleteRequisitionProductDetailsQuery.executeUpdate();
				
				_requisition.setFromWarehouse(new Warehouse(requisitionRequest.getFromWarehouseId()));
				_requisition.setToWarehouse(new Warehouse(requisitionRequest.getToWarehouseId()));
				_requisition.setReceivedStatus(false);
				_requisition.setRequiredDate(requisitionRequest.getRequiredDate());
				_requisition.setRequisitionApprovalStatus(new RequisitionApprovalStatus(requisitionRequest.getApprovalStatusId()));
				_requisition.setRequisitionProductType(new RequisitionProductType(requisitionRequest.getProductTypeId()));
				_requisition.setRequisitionStatus(new RequisitionStatus(requisitionRequest.getRequisitionStatusId()));
				_requisition.setSentStatus(false);
				_requisition.setModifiedBy(new EmployeeDetails(user.getUsername()));
				_requisition.setModifiedDate(new Date());
				
				entityManager.persist(_requisition);
				
				if (requisitionRequest.getProductList() != null) {
					Set<RequisitionProductDetailsRequest> productRequest = requisitionRequest.getProductList();
					Iterator<RequisitionProductDetailsRequest> iterator = productRequest.iterator();
					while (iterator.hasNext()) {
						RequisitionProductDetailsRequest request = iterator.next();
						RequisitionProductDetails requisitionProductDetails = new RequisitionProductDetails();
						requisitionProductDetails.setProducts(new Products(request.getProductPartCode()));
						requisitionProductDetails.setQuantitty(request.getQuantity());
						requisitionProductDetails.setRequisition(_requisition);

						entityManager.persist(requisitionProductDetails);
					}

				}
				entityManager.flush();
				entityManager.clear();
				return requisitionRequest;
			}else {
				throw new Exception("No data found to save details");
			}
		}else {
			throw new Exception("Request Parameter not found");
		}
	}

}
