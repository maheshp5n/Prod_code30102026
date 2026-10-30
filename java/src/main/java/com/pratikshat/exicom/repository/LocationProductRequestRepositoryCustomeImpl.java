package com.pratikshat.exicom.repository;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.ApplicationNotificationDetails;
import com.pratikshat.exicom.entity.BusinessDomain;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LPTransferItem;
import com.pratikshat.exicom.entity.LPTransferLineItem;
import com.pratikshat.exicom.entity.LPTransferReceiveLineItem;
import com.pratikshat.exicom.entity.LProductRequest;
import com.pratikshat.exicom.entity.LProductRequestLineItem;
import com.pratikshat.exicom.entity.Location;
import com.pratikshat.exicom.entity.LocationProductItem;
import com.pratikshat.exicom.entity.LocationProductLineItems;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.LoginUserRoles;
import com.pratikshat.exicom.entity.PartcodeChangeRequest;
import com.pratikshat.exicom.entity.ProductLineItem;
import com.pratikshat.exicom.entity.ProductLineItemProductState;
import com.pratikshat.exicom.entity.ProductLineItemStatus;
import com.pratikshat.exicom.entity.Products;
import com.pratikshat.exicom.entity.ServiceRecource;
import com.pratikshat.exicom.entity.TemplateDetails;
import com.pratikshat.exicom.entity.TemplateType;
import com.pratikshat.exicom.entity.UserRoles;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.LPItemRequest;
import com.pratikshat.exicom.model.request.LPTransferRequest;
import com.pratikshat.exicom.model.request.LocationProductTransferReceiveRequest;
import com.pratikshat.exicom.model.response.ExpenseManagementResponse;
import com.pratikshat.exicom.model.response.LocationProductRequestItemResponse;
import com.pratikshat.exicom.model.response.LocationProductRequestResponse;
import com.pratikshat.exicom.model.response.LocationProductTransferItemResponse;
import com.pratikshat.exicom.model.response.OkItemResponse;
import com.pratikshat.exicom.model.response.ServiceCenterForList;
import com.pratikshat.exicom.model.response.ServiceResourseForList;
import com.pratikshat.exicom.utils.SmsNotifier;

@Repository
public class LocationProductRequestRepositoryCustomeImpl implements LocationProductRequestRepositoryCustome {

	@javax.persistence.PersistenceContext
	javax.persistence.EntityManager em;

	@Autowired
	ProductLineItemRepository dbProductLineItem;

	@Autowired
	SimpMessagingTemplate messageTemplate;

	@Value("${feedbackurl}")
	String feedbackurl;

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			Exception.class, RuntimeException.class, BadRequestException.class, UnauthoriseException.class,
			SQLException.class })
	public void submitProductRequest(long prdId, LoginUser user) throws Exception {
		LProductRequest req = em.createQuery(
				"select req from LProductRequest req join req.sourceLocation sloc join req.ownerDetails onr where req.locationProductRequestId=?1 and req.locationProductRequestStatus=?2",
				LProductRequest.class).setParameter(1, prdId).setParameter(2, "Draft").getSingleResult();

		ServiceRecource sr = em.createQuery("select sr from ServiceRecource sr where sr.user=?1", ServiceRecource.class)
				.setParameter(1, new EmployeeDetails(user.getUsername())).getSingleResult();

		Location sourceLocation = req.getSourceLocation();
		boolean isRatanlalWarehouse = sourceLocation.getLocationName().trim().equalsIgnoreCase("NRC-Stock Warehouse");
//		
//		com.pratikshat.exicom.entity.EmployeeDetails emp=null;
//		emp=req.getOwnerDetails();

//		if(emp==null) {
//			throw new RuntimeException("Not a valid request");
//		}

//		if(isRatanlalWarehouse) {
//			if(sourceLocation.getLocationId()!=sr.getLocation().getLocationId())
//				throw new RuntimeException("You are not authorised to sumbit request or already submitted");
//		}
//		
//		else  {
//			if(sr.getLocation().getLocationId()!=req.getDestinationLocation().getLocationId())
//				throw new RuntimeException("You are not authorised to sumbit request or already submitted");
//		}

		if (sr.getLocation().getLocationId() != req.getDestinationLocation().getLocationId()) {
			throw new RuntimeException("You are not authorised to sumbit request or already submitted");
		}
		if (isRatanlalWarehouse)
			req.setLocationProductRequestStatus("Submitted BY SCH");
		else
			req.setLocationProductRequestStatus("Submitted");
		req.setModifiedBy(new EmployeeDetails(user.getUsername()));
		req.setModifiedDate(new Date());
		em.persist(req);

		javax.persistence.Query getProductRequestQuery = em.createQuery(
				"select lr from LProductRequest lr join lr.sourceLocation sl join lr.destinationLocation dl join sl.owner onr left join onr.contact cnt where lr.locationProductRequestId=?1",
				com.pratikshat.exicom.entity.LProductRequest.class).setParameter(1, prdId);
		com.pratikshat.exicom.entity.LProductRequest pr = (com.pratikshat.exicom.entity.LProductRequest) getProductRequestQuery
				.getSingleResult();

		com.pratikshat.exicom.entity.ApplicationNotificationDetails notification = new com.pratikshat.exicom.entity.ApplicationNotificationDetails();
		notification.setNofificationDate(new java.util.Date());
		notification.setNotificationActionUrl("productrequest/" + prdId);
		notification.setNotificationMessage(
				String.format("A new product request %s has been created ", pr.getLocationProductRequestManualId()));
		notification.setNotificationRead(false);
		notification.setUserEmailId(pr.getSourceLocation().getOwner().getEmailId());
		em.persist(notification);
		messageTemplate.convertAndSend("/topic/" + pr.getSourceLocation().getOwner().getEmailId(), notification);

		TemplateDetails templateDetails = null;

		try {
			Query templateQuery = em.createQuery(
					"select tmpd from TemplateDetails tmpd where tmpd.active=?1 and tmpd.templateType=?2 and tmpd.businessDomain=?3 and tmpd.eventName=?4",
					TemplateDetails.class);
			templateQuery.setParameter(1, true);
			templateQuery.setParameter(2, TemplateType.SMS);
			templateQuery.setParameter(3, new BusinessDomain(2));
			templateQuery.setParameter(4, "pr_initiate");

			templateDetails = (TemplateDetails) templateQuery.getSingleResult();
		} catch (NoResultException nex) {
			nex.printStackTrace();
		} catch (NonUniqueResultException e) {
			e.printStackTrace();
		}

		String smsMessage = null;
		if (templateDetails != null) {
			smsMessage = templateDetails.getBody();
			smsMessage = smsMessage.replaceFirst("<var>", req.getLocationProductRequestManualId());
			smsMessage = smsMessage.replaceFirst("<var>", sr.getName());
			smsMessage = smsMessage.replaceFirst("<var>",
					feedbackurl + "/#/productrequest/" + req.getLocationProductRequestId());
		}

		if (smsMessage != null && !smsMessage.equalsIgnoreCase("")) {
			SmsNotifier sNotifier = new SmsNotifier(em, pr.getSourceLocation().getOwner().getContact().getMobile(),
					smsMessage, user.getUsername(), templateDetails.getTemplaeCode());
			sNotifier.sendNotification();
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE, rollbackFor = {
			Exception.class, RuntimeException.class, BadRequestException.class, UnauthoriseException.class,
			SQLException.class })
	public com.pratikshat.exicom.model.request.LPItemRequest addProductItem(LPItemRequest itemRequest, LoginUser user)
			throws Exception {
		long id = itemRequest.getLprId();
		String ownerEmail = user.getUsername();

		ServiceRecource sr = em.createQuery("select sr from ServiceRecource sr where sr.user=?1", ServiceRecource.class)
				.setParameter(1, new EmployeeDetails(user.getUsername())).getSingleResult();

		// javax.persistence.Query productRequestQuery=em.createQuery("select lpr from
		// LProductRequest lpr join lpr.ownerDetails ownr where
		// lpr.locationProductRequestId=?1",com.pratikshat.exicom.entity.LProductRequest.class).setParameter(1,id).getSingleResult();
		com.pratikshat.exicom.entity.LProductRequest lpReq = null;
		try {
			lpReq = em.createQuery(
					"select lpr from LProductRequest lpr join lpr.ownerDetails ownr where lpr.locationProductRequestId=?1",
					com.pratikshat.exicom.entity.LProductRequest.class).setParameter(1, id).getSingleResult();
		} catch (NoResultException ex) {

			throw new com.pratikshat.exicom.exception.BadRequestException("Invalid request id");
		} catch (NonUniqueResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Invalid request id");
		}

		if (lpReq == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Invalid request id");
		}

		if (lpReq.getLocationProductRequestStatus().equalsIgnoreCase("Archived"))
			throw new com.pratikshat.exicom.exception.BadRequestException(
					"Can not add item this request has been Archived");
		if(lpReq.isShortClosed())
			throw new BadRequestException("Can not add item, this product request "+lpReq.getLocationProductRequestManualId()+" has been short closed");

		if (lpReq.getDestinationLocation().getLocationId() != sr.getLocation().getLocationId()) {
			if (lpReq.getSourceLocation().getLocationId() == sr.getLocation().getLocationId()) {
			} else {
				throw new com.pratikshat.exicom.exception.UnauthoriseException("You are not authorised");
			}
		}

//		if (!(lpReq.getLocationProductRequestStatus().equalsIgnoreCase("Draft")||lpReq.getLocationProductRequestStatus().equalsIgnoreCase("Submitted BY SCH"))) {
//			
//			throw new com.pratikshat.exicom.exception.BadRequestException("Already submitted to SAP can not add more items");
//		}
		com.pratikshat.exicom.entity.LProductRequestLineItem _lineitems = null;
		try {
			_lineitems = em.createQuery(
					"select item from LProductRequestLineItem item join item.product prd join item.locationProductRequest rq where rq.locationProductRequestId=?1 and prd.productPartCode=?2 and item.disabledItem=?3",
					com.pratikshat.exicom.entity.LProductRequestLineItem.class).setParameter(1, itemRequest.getLprId())
					.setParameter(2, itemRequest.getProductPartCode())
					.setParameter(3,false)
					.getSingleResult();

		} catch (javax.persistence.NoResultException ex) {

		} catch (javax.persistence.NonUniqueResultException ex) {
			throw new BadRequestException("Multiple item found with same partocde "+itemRequest.getProductPartCode());
		}
		 if(_lineitems==null) {
				// System.out.println( " 260 the line item status="+_lineitems.getLineItemStatus());
				// System.out.println( " 261 the line item status="+_lineitems.getLineItemStatus().equalsIgnoreCase("Short Closed"));
				com.pratikshat.exicom.entity.Products _products = em
						.createQuery("select prd from Products prd where prd.productPartCode=?1",
								com.pratikshat.exicom.entity.Products.class)
						.setParameter(1, itemRequest.getProductPartCode()).getSingleResult();
				com.pratikshat.exicom.entity.LProductRequestLineItem item = new com.pratikshat.exicom.entity.LProductRequestLineItem();
				item.setCreatedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
				item.setCreatedDate(new java.util.Date());
				item.setDescription(_products.getPdoductDescription());
				item.setLocationProductRequest(new com.pratikshat.exicom.entity.LProductRequest(itemRequest.getLprId()));
				item.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
				item.setModifiedDate(new java.util.Date());
				item.setProduct(new com.pratikshat.exicom.entity.Products(itemRequest.getProductPartCode()));
				item.setQuantity(itemRequest.getRequestQuantity());
				item.setQuantityIssued(0);
				item.setQuantityReceived(0);
				item.setLineItemStatus(null);
				item.setDisabledItem(false);
//				item.setLineItemStatus(lineItemStatus);
			em.persist(item);
			String manualId = String.format("PRLI-%s", com.pratikshat.exicom.config.MiscellaneousUtils
					.fillZero(String.valueOf(item.getLocationProductRequestLineItemId()), 5));
			item.setLocationProductRequestLineItemManualId(manualId);
			em.persist(item);
			lpReq.setTotalQuantityRequired(lpReq.getTotalQuantityRequired() + itemRequest.getRequestQuantity());
			// lpReq.setLocationProductRequestStatus("Open");
			if (lpReq.getLocationProductRequestStatus().equalsIgnoreCase("Received")) {
				lpReq.setLocationProductRequestStatus("Open");
			}
			em.persist(lpReq);
		} else {

			System.out.println(" 228 the line item status" + _lineitems.getLineItemStatus());

			// throw new com.pratikshat.exicom.exception.BadRequestException("Item already
			// exists for request");

			if (_lineitems.isDisabledItem())
				throw new BadRequestException("You can not add quantity in disabled item");

			_lineitems.setQuantity(_lineitems.getQuantity() + itemRequest.getRequestQuantity());
			_lineitems.setModifiedBy(new EmployeeDetails(user.getUsername()));
			_lineitems.setModifiedDate(new Date());
			_lineitems.setLineItemStatus(null);
			_lineitems.setDisabledItem(false);

			
			if (_lineitems.getQuantity() <= 0)
				throw new BadRequestException("Please provide valid quantity");
			// if (_lineitems.getQuantity() > 9999)
			// throw new BadRequestException("Please put quantity between 1 to 9999");//
			// 24-09-2020 by Manpreet Madam
			if (_lineitems.getQuantityIssued() > _lineitems.getQuantity())
				throw new BadRequestException("Can not reduce quantity more then issued");

			em.persist(_lineitems);
			lpReq.setTotalQuantityRequired(lpReq.getTotalQuantityRequired() + itemRequest.getRequestQuantity());
			lpReq.setModifiedBy(new EmployeeDetails(user.getUsername()));
			lpReq.setModifiedDate(new Date());
			if (lpReq.getLocationProductRequestStatus().equalsIgnoreCase("Received")) {
				lpReq.setLocationProductRequestStatus("Open");
			}

			em.persist(lpReq);
		}
		return itemRequest;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = {
			Exception.class, RuntimeException.class, BadRequestException.class, UnauthoriseException.class,
			SQLException.class })
	public OkItemResponse getOkItems(long prid, long itemid) {
		com.pratikshat.exicom.entity.LProductRequestLineItem _pitems = em.createQuery(
				"select item from LProductRequestLineItem item join item.product prd join item.locationProductRequest pr join pr.sourceLocation sloc where item.locationProductRequestLineItemId=?1 and pr.locationProductRequestId=?2",
				com.pratikshat.exicom.entity.LProductRequestLineItem.class).setParameter(1, itemid)
				.setParameter(2, prid).getSingleResult();
		com.pratikshat.exicom.entity.Location _location = _pitems.getLocationProductRequest().getSourceLocation();
		com.pratikshat.exicom.entity.Products _product = _pitems.getProduct();
		float okQty = 0;
		java.util.List<String> okProduct = null;
		if (!_product.getProductType().equalsIgnoreCase("Component")) {
			okProduct = em.createQuery(
					"select litem.itemSerialNumber from LocationProductLineItems litem join litem.locationProductItem item join item.product prd join litem.locationProeuctLineItemState st join item.location loc where litem.customerInventory=false and loc.locationId=?1 and prd.productPartCode=?2 and st.productStateName=?3",
					String.class).setParameter(1, _location.getLocationId())
					.setParameter(2, _product.getProductPartCode()).setParameter(3, "Ok").getResultList();
		} else {
			try {
				okQty = em.createQuery(
						"select item.okQuantity from LocationProductItem item join item.product prd join item.location loc where loc.locationId=?1 and prd.productPartCode=?2",
						Float.class).setParameter(1, _location.getLocationId())
						.setParameter(2, _product.getProductPartCode()).getSingleResult();
			} catch (javax.persistence.NoResultException ex) {
				okQty = 0;
			}

		}
		if (okProduct != null) {
			okQty = okProduct.size();
		}
		com.pratikshat.exicom.model.response.OkItemResponse _d = new com.pratikshat.exicom.model.response.OkItemResponse(
				okQty, okProduct);
		return _d;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, RuntimeException.class, Exception.class,
			SQLException.class })
	public LPTransferRequest transferProducts(LPTransferRequest tRequest, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		com.pratikshat.exicom.entity.LProductRequest lpRequest = null;
		ServiceRecource sr = em.createQuery("select sr from ServiceRecource sr where sr.user=?1", ServiceRecource.class)
				.setParameter(1, new EmployeeDetails(user.getUsername())).getSingleResult();
		try {
			lpRequest = em.createQuery(
					"select req from LProductRequest req left join req.sourceLocation sloc left join req.destinationLocation dloc left join sloc.owner sown where req.locationProductRequestId=?1",
					com.pratikshat.exicom.entity.LProductRequest.class)
					.setParameter(1, tRequest.getLocationProductRequestId()).getSingleResult();
		} catch (javax.persistence.NoResultException ex) {
			throw new BadRequestException("Product Request not found");
		}

		if (lpRequest.getLocationProductRequestStatus().equalsIgnoreCase("Archived"))
			throw new BadRequestException("Can not transfer product, This request have been archived");
		if(lpRequest.isShortClosed())
			throw new BadRequestException("Can not transfer product, This request has been short closed");

		if (lpRequest.getSourceLocation().getLocationId() != sr.getLocation().getLocationId())
			throw new UnauthoriseException("You are not authorised to transfer Items");

		if (lpRequest.getTotalQuantityRequired() <= lpRequest.getTotalQuantityIssued()) {

			throw new BadRequestException("Can not issue more then required quantity");
		}
		if (lpRequest.getLocationProductRequestStatus().equalsIgnoreCase("Draft")
				|| lpRequest.getLocationProductRequestStatus().equalsIgnoreCase("Submitted BY SCH")) {
			if (lpRequest.getLocationProductRequestStatus().equalsIgnoreCase("Draft"))
				throw new BadRequestException("Unable to transfer item, Request is in Draft mode");
			if (lpRequest.getLocationProductRequestStatus().equalsIgnoreCase("Submitted BY SCH"))
				throw new BadRequestException("Unable to transfer item, Need to submit PR to SAP");
		}
		com.pratikshat.exicom.entity.LPTrasfer lpTransfer = new com.pratikshat.exicom.entity.LPTrasfer();
		lpTransfer.setActualDeliveryDate(tRequest.getActualDeliveryDate());
		lpTransfer.setCreatedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
		lpTransfer.setCreatedDate(new java.util.Date());
		lpTransfer.setLocationProductRequest(
				new com.pratikshat.exicom.entity.LProductRequest(lpRequest.getLocationProductRequestId()));
		lpTransfer.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
		lpTransfer.setModifiedDate(new Date());
		lpTransfer.setOwner(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
		lpTransfer.setShippingMode(tRequest.getShippingMode());
		lpTransfer.setShippingToName(tRequest.getShippingToName());
		lpTransfer.setTrackingNumber(tRequest.getTrackingNumber());
		em.persist(lpTransfer);
		String manualId = String.format("PT-%s", com.pratikshat.exicom.config.MiscellaneousUtils
				.fillZero(String.valueOf(lpTransfer.getLocationProductTransferId()), 5));
		em.createQuery(
				"update LPTrasfer t set t.locationProductTransferManualId=?1 where t.locationProductTransferId=?2")
				.setParameter(1, manualId).setParameter(2, lpTransfer.getLocationProductTransferId()).executeUpdate();
		tRequest.setLocationProductTransferId(lpTransfer.getLocationProductTransferId());
		float totalItemTransfer = 0;
		if (tRequest.getItems() == null) {
			throw new BadRequestException("No item to transfer");
		}
		for (com.pratikshat.exicom.model.request.LPTransferItemRequest _tItem : tRequest.getItems()) {
			_tItem.getLpRequestLineItemId();
			com.pratikshat.exicom.entity.LProductRequestLineItem _rItem = null;
			try {
				_rItem = em.createQuery(
						"select item from LProductRequestLineItem item left join item.product prd left join item.locationProductRequest pr where pr.locationProductRequestId=?1 and item.locationProductRequestLineItemId=?2",
						com.pratikshat.exicom.entity.LProductRequestLineItem.class)
						.setParameter(1, tRequest.getLocationProductRequestId())
						.setParameter(2, _tItem.getLpRequestLineItemId()).getSingleResult();
			} catch (javax.persistence.NoResultException | javax.persistence.NonUniqueResultException ex) {
				throw new BadRequestException("Product request does not contain");
			}
			
			
			if (_rItem.isDisabledItem())
				throw new BadRequestException("Product part code " + _rItem.getProduct().getProductPartCode()
						+ " has been disabled/Short Closed,Unable to transfer");

			if (_rItem.getQuantity() == _rItem.getQuantityIssued()) {
				throw new BadRequestException(String.format("Product %s has already issued as requested",
						_rItem.getProduct().getProductPartCode()));
			}
			if ((_rItem.getQuantity() - _rItem.getQuantityIssued()) < _tItem.getTransferQuantity()) {
				throw new BadRequestException("Can not issue more quantity then requested");
			}
			if (!_rItem.getProduct().getProductType().equalsIgnoreCase("Component")
					&& _tItem.getSerialNumbers().size() != _tItem.getTransferQuantity()) {
				throw new BadRequestException("Bad request quantity is not as per serialnumbers");
			}
			com.pratikshat.exicom.entity.LocationProductItem _lItem = null;

			try {
				_lItem = em.createQuery(
						"select litem from LocationProductItem litem join litem.location loc join litem.product prd where loc.locationId=?1 and prd.productPartCode=?2 and litem.okQuantity>=?3",
						com.pratikshat.exicom.entity.LocationProductItem.class)
						.setParameter(1, lpRequest.getSourceLocation().getLocationId())
						.setParameter(2, _rItem.getProduct().getProductPartCode())
						.setParameter(3, _tItem.getTransferQuantity()).getSingleResult();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new BadRequestException(
						"Location does not have enough quantity to issue " + _rItem.getProduct().getProductPartCode());
			}
			totalItemTransfer += _tItem.getTransferQuantity();

			float quantityAtLocation = _lItem.getOkQuantity() - _tItem.getTransferQuantity();
			float quantityOnHandAtLocation = _lItem.getQuantityInOnHand() - _tItem.getTransferQuantity();
			float refurbes = _lItem.getRefurbished();
			float totalQuantityAtRequest = _rItem.getQuantityIssued() + _tItem.getTransferQuantity();

			em.createQuery(
					"update LProductRequestLineItem ritem set ritem.quantityIssued=?1,ritem.modifiedBy=?2,ritem.modifiedDate=?3 where ritem.locationProductRequestLineItemId=?4")
					.setParameter(1, totalQuantityAtRequest)
					.setParameter(2, new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()))
					.setParameter(3, new java.util.Date()).setParameter(4, _rItem.getLocationProductRequestLineItemId())
					.executeUpdate();

			com.pratikshat.exicom.entity.LPTransferItem lpTransferItem = new com.pratikshat.exicom.entity.LPTransferItem();
			lpTransferItem.setCreatedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			lpTransferItem.setCreatedDate(new java.util.Date());
			lpTransferItem.setLpRequestLineItem(new com.pratikshat.exicom.entity.LProductRequestLineItem(
					_rItem.getLocationProductRequestLineItemId()));
			lpTransferItem.setLpTranfer(
					new com.pratikshat.exicom.entity.LPTrasfer(lpTransfer.getLocationProductTransferId()));
			lpTransferItem.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			lpTransferItem.setModifiedDate(new java.util.Date());
			lpTransferItem.setOwner(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			lpTransferItem
					.setProduct(new com.pratikshat.exicom.entity.Products(_rItem.getProduct().getProductPartCode()));
			lpTransferItem.setTransferQuantity(_tItem.getTransferQuantity());
			lpTransferItem.setReceivedQuantity(0);

			em.persist(lpTransferItem);

			String lManualId = String.format("PTI-%s", com.pratikshat.exicom.config.MiscellaneousUtils
					.fillZero(String.valueOf(lpTransferItem.getLpTransferItemId()), 5));
			lpTransferItem.setLpTransferItemIdManualId(lManualId);

			em.persist(lpTransferItem);

			_tItem.setLpTransferItemId(lpTransferItem.getLpTransferItemId());

			float customerInventory = 0;

			if (_tItem.getSerialNumbers() != null) {

				for (String serialNumber : _tItem.getSerialNumbers()) {

					PartcodeChangeRequest chRequest = null;
					try {

						chRequest = em.createQuery(
								"select item from PartcodeChangeRequest item where item.faultyProduct=?1 and item.faultyProductSerialNumber=?2 and item.flowType=?3 and item.approvedStatus=?4",
								PartcodeChangeRequest.class).setParameter(1, _rItem.getProduct())
								.setParameter(2, serialNumber)
								.setParameter(3, PartcodeChangeRequestRepository.FLOW_TYPE_NON_SA)
								.setParameter(4, false).getSingleResult();
						throw new BadRequestException(
								"Can not move inventory of part code " + _rItem.getProduct().getProductPartCode()
										+ " with serial number " + serialNumber + " due to pending approval");
					} catch (NoResultException ex) {

					} catch (NonUniqueResultException ex) {
						throw new BadRequestException(
								"Can not move inventory of part code " + _rItem.getProduct().getProductPartCode()
										+ " with serial number " + serialNumber + " due to multiple pending approval");
					}

					com.pratikshat.exicom.entity.LocationProductLineItems _llItem = null;
					try {
						_llItem = em.createQuery(
								"select llitem from LocationProductLineItems llitem join llitem.locationProductItem lpitem left join llitem.locationProductLineItemStatus st left join llitem.locationProeuctLineItemState where llitem.itemSerialNumber=?1 and lpitem.id=?2",
								com.pratikshat.exicom.entity.LocationProductLineItems.class)
								.setParameter(1, serialNumber.trim()).setParameter(2, _lItem.getId()).getSingleResult();
						if (_llItem.getLocationProeuctLineItemState().getProductStateId() != 1)
							throw new BadRequestException("Can not transfer serial number "
									+ _llItem.getItemSerialNumber() + ", serial number is not in OK state");

						// CHECK FOR MULTIPLE SERIAL NUMBER

						com.pratikshat.exicom.entity.LocationProductLineItems checkLineItems = null;
						try {
							checkLineItems = em.createQuery(
									"select litem from LocationProductLineItems litem join litem.locationProductItem item where litem.itemSerialNumber=?1 and item.product=?2",
									com.pratikshat.exicom.entity.LocationProductLineItems.class)
									.setParameter(1, serialNumber.trim())
									.setParameter(2, _llItem.getLocationProductItem().getProduct()).getSingleResult();
						} catch (NoResultException ex) {
							throw new BadRequestException(
									String.format("Bad serial number %s of product part code %s", serialNumber.trim(),
											_llItem.getLocationProductItem().getProduct().getProductPartCode()));
						} catch (NonUniqueResultException ex) {
							throw new BadRequestException(String.format(
									"Multiple serial number %s found for product part code %s", serialNumber.trim(),
									_llItem.getLocationProductItem().getProduct().getProductPartCode()));
						}

					} catch (javax.persistence.NoResultException | javax.persistence.NonUniqueResultException ex) {
						throw new BadRequestException(
								String.format("No Serial Number %s is in Location stock", serialNumber));
					}
					if (!_llItem.getLocationProeuctLineItemState().getProductStateName().equalsIgnoreCase("Ok")) {
						throw new BadRequestException(
								String.format("No Serial Number %s is in Location stock in ok state", serialNumber));
					}
					if (_llItem.isCustomerInventory()) {
						customerInventory++;
					}
					if (_llItem.isRefurbished())
						refurbes = refurbes--;

					com.pratikshat.exicom.entity.LPTransferLineItem lpTlItem = new com.pratikshat.exicom.entity.LPTransferLineItem();
					lpTlItem.setCreatedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
					lpTlItem.setCreatedDate(new java.util.Date());
					lpTlItem.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
					lpTlItem.setModifiedDate(new java.util.Date());
					lpTlItem.setOwner(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
					lpTlItem.setProductSerialNumber(serialNumber.trim());
					lpTlItem.setProductStatus("IN-Transit");
					lpTlItem.setQuantityReceived(0);
					lpTlItem.setQuantityTransfer(1);
					lpTlItem.setReceived(false);
					lpTlItem.setTransferItem(
							new com.pratikshat.exicom.entity.LPTransferItem(lpTransferItem.getLpTransferItemId()));
					lpTlItem.setCustomerInventory(_llItem.isCustomerInventory());
					lpTlItem.setCustomerName(_llItem.getCustomerName());
					lpTlItem.setCustomerSapNumber(_llItem.getCustomerSapNumber());
					lpTlItem.setRefurbished(_llItem.isRefurbished());
					em.persist(lpTlItem);

					ProductLineItem pLineItem = dbProductLineItem.findByProductAndSerialNumber(_rItem.getProduct(),
							serialNumber.trim());

					if (pLineItem != null) {
						pLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
						pLineItem.setModifiedDate(new Date());
						pLineItem.setPosition("PT," + lpTransfer.getLocationProductTransferId());
						pLineItem.setPositionState(ProductLineItemRepository.IN_TRANSIT);
						pLineItem.setState(_llItem.getLocationProeuctLineItemState());
						pLineItem.setStatus(new ProductLineItemStatus(2));
						dbProductLineItem.save(pLineItem);
					} else {
						pLineItem = new ProductLineItem();
						pLineItem.setCreatedBy(new EmployeeDetails(user.getUsername()));
						pLineItem.setCreatedDate(new Date());
						pLineItem.setProduct(_rItem.getProduct());
						pLineItem.setSerialNumber(serialNumber.trim());
						pLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
						pLineItem.setModifiedDate(new Date());
						pLineItem.setPosition("PT," + lpTransfer.getLocationProductTransferId());
						pLineItem.setPositionState(ProductLineItemRepository.IN_TRANSIT);
						pLineItem.setState(_llItem.getLocationProeuctLineItemState());
						pLineItem.setStatus(new ProductLineItemStatus(2));
						dbProductLineItem.save(pLineItem);
					}

					String lpTItemManulaId = String.format("PTLI-%s", com.pratikshat.exicom.config.MiscellaneousUtils
							.fillZero(String.valueOf(lpTlItem.getLpTransferLineitemId()), 5));
					lpTlItem.setLpTransferLineitemIdManualId(lpTItemManulaId);
					// em.createQuery(
					// "update LPTransferLineItem item set item.lpTransferLineitemIdManualId=?1
					// where item.lpTransferLineitemId=?2")
					// .setParameter(1, lpTItemManulaId).setParameter(2,
					// lpTlItem.getLpTransferLineitemId())
					// .executeUpdate();
					em.remove(_llItem);
					// em.createQuery("delete from LocationProductLineItems llitem where id=?1")
					// .setParameter(1, _llItem.getId()).executeUpdate();
				}

			}

			// UPDATING FINAL STOCK OF PART CODE ON LOCATION

			_lItem.setOkQuantity(quantityAtLocation);
			_lItem.setQuantityInOnHand(quantityOnHandAtLocation);
			_lItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
			_lItem.setModifiedDate(new Date());
			_lItem.setCustomerInventory(_lItem.getCustomerInventory() - customerInventory);
			_lItem.setRefurbished(refurbes);

			em.persist(_lItem);

		}
		float tQ = lpRequest.getTotalQuantityIssued() + totalItemTransfer;
		String status = "Open";
		if (tQ >= lpRequest.getTotalQuantityRequired()) {
			status = "Receipt Pending";
		}
		em.createQuery(
				"update LProductRequest lo set lo.totalQuantityIssued=?1,lo.locationProductRequestStatus=?2 where lo.locationProductRequestId=?3")
				.setParameter(1, tQ).setParameter(2, status).setParameter(3, lpRequest.getLocationProductRequestId())
				.executeUpdate();

		try {
			Location loc = em.createQuery(
					"select loc from Location loc join loc.owner onr left join onr.contact cnt where loc.locationId=?1",
					Location.class).setParameter(1, lpRequest.getDestinationLocation().getLocationId())
					.getSingleResult();
			com.pratikshat.exicom.entity.ApplicationNotificationDetails _notification = new com.pratikshat.exicom.entity.ApplicationNotificationDetails();
			_notification.setNofificationDate(new java.util.Date());
			_notification.setNotificationActionUrl("/productrequest/" + lpRequest.getLocationProductRequestId());
			_notification.setNotificationMessage(
					"A product transfer against " + lpRequest.getLocationProductRequestManualId() + " has been done");
			_notification.setNotificationRead(false);
			_notification.setUserEmailId(loc.getOwner().getEmailId());
			em.persist(_notification);
			try {
				messageTemplate.convertAndSend("/topic/" + loc.getOwner().getEmailId(), _notification);
			} catch (Exception ex) {
			}

			TemplateDetails templateDetails = null;

			try {
				Query templateQuery = em.createQuery(
						"select tmpd from TemplateDetails tmpd where tmpd.active=?1 and tmpd.templateType=?2 and tmpd.businessDomain=?3 and tmpd.eventName=?4",
						TemplateDetails.class);
				templateQuery.setParameter(1, true);
				templateQuery.setParameter(2, TemplateType.SMS);
				templateQuery.setParameter(3, new BusinessDomain(2));
				templateQuery.setParameter(4, "pt_initiate");

				templateDetails = (TemplateDetails) templateQuery.getSingleResult();
			} catch (NoResultException nex) {
				nex.printStackTrace();
			} catch (NonUniqueResultException e) {
				e.printStackTrace();
			}

			String smsMessage = null;
			if (templateDetails != null) {
				smsMessage = templateDetails.getBody();
				smsMessage = smsMessage.replaceFirst("<var>", manualId);
				smsMessage = smsMessage.replaceFirst("<var>", lpRequest.getLocationProductRequestManualId());
				smsMessage = smsMessage.replaceFirst("<var>", sr.getName());
				smsMessage = smsMessage.replaceFirst("<var>",
						feedbackurl + "/#/productrequest/" + lpRequest.getLocationProductRequestId());
			}

			if (smsMessage != null && !smsMessage.equalsIgnoreCase("")) {
				SmsNotifier sNotifier = new SmsNotifier(em, loc.getOwner().getContact().getMobile(), smsMessage,
						user.getUsername(), templateDetails.getTemplaeCode());
				sNotifier.sendNotification();
			}

		} catch (Exception ex) {
		}
		return tRequest;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, Exception.class, RuntimeException.class,
			SQLException.class })
	public void receiveTransfredItem(long productRequestId, List<LocationProductTransferReceiveRequest> items,
			EmployeeDetails user) throws BadRequestException, UnauthoriseException {
		com.pratikshat.exicom.entity.LProductRequest request = null;
		com.pratikshat.exicom.entity.Location destLocation = null;
		com.pratikshat.exicom.entity.EmployeeDetails owner = null;
		com.pratikshat.exicom.entity.Location sourceLocation = null;
		ServiceRecource sr = em.createQuery("select sr from ServiceRecource sr where sr.user=?1", ServiceRecource.class)
				.setParameter(1, user).getSingleResult();
		try {
			System.out.println("request id" + productRequestId);
			request = em.createQuery(
					"select req from LProductRequest req join req.sourceLocation sl join req.destinationLocation dl join dl.owner own where req.locationProductRequestId=?1",
					com.pratikshat.exicom.entity.LProductRequest.class).setParameter(1, productRequestId)
					.getSingleResult();
			destLocation = request.getDestinationLocation();
			owner = destLocation.getOwner();
			sourceLocation = request.getSourceLocation();

		} catch (javax.persistence.NoResultException ex) {
			throw new BadRequestException("Invalid Request");
		}
		if (destLocation.getLocationId() != sr.getLocation().getLocationId()) {
			throw new UnauthoriseException("You are not authorise to receive Items");
		}

		if (request.getLocationProductRequestStatus().equalsIgnoreCase("Archived"))
			throw new BadRequestException("Can not receive product, This request has been archived ");

		if (items == null || items.size() == 0) {
			throw new BadRequestException("No item to receive");
		}
		com.pratikshat.exicom.entity.LPTransferReceive _receive = new com.pratikshat.exicom.entity.LPTransferReceive();
		_receive.setCreatedBy(user);
		_receive.setCreatedDate(new java.util.Date());
		_receive.setLocation(destLocation);
		_receive.setModifiedBy(user);
		_receive.setModifiedDate(new java.util.Date());
		_receive.setOwner(user);
		_receive.setReceiveDate(new java.util.Date());
		// _receive.setTotalQuantityReceived(items.size()); //need to update on last
		em.persist(_receive);
		float totalItemReceived = 0;

		for (LocationProductTransferReceiveRequest item : items) {
			long itemTranferId = item.getLocationProductTransferItemId();
			com.pratikshat.exicom.entity.LPTransferItem _tItem = null;
			com.pratikshat.exicom.entity.LPTrasfer transfer = null;
			com.pratikshat.exicom.entity.Products products = null;
			com.pratikshat.exicom.entity.LProductRequestLineItem requestLineItem = null;
			try {
				_tItem = em.createQuery(
						"select titem from LPTransferItem titem join titem.lpRequestLineItem litem join titem.product prd join titem.lpTranfer trns join trns.locationProductRequest req  where titem.lpTransferItemId=?1 and req.locationProductRequestId=?2",
						com.pratikshat.exicom.entity.LPTransferItem.class).setParameter(1, itemTranferId)
						.setParameter(2, productRequestId).getSingleResult();
				transfer = _tItem.getLpTranfer();
				products = _tItem.getProduct();
				requestLineItem = _tItem.getLpRequestLineItem();
				// _receive.setProductTransfer(transfer);
				transfer.setReceivedDate(new Date());
			} catch (javax.persistence.NoResultException noResult) {
				throw new BadRequestException("Invalid request");
			}

			if (_tItem == null)
				throw new BadRequestException("Invalid request");

			if (!_tItem.getProduct().getProductType().equalsIgnoreCase("Component")) {
				if (item.getReceivedSerialNumber().size() != item.getQuantityReceive()) {
					throw new BadRequestException("Quantity mismatch");
				}
			}
			if (_tItem.getProduct().getProductType().equalsIgnoreCase("Component")) {
				if (item.getReceivedSerialNumber() != null && item.getReceivedSerialNumber().size() > 0)
					throw new BadRequestException("Component can not have serial number");
			}
			totalItemReceived = totalItemReceived + item.getQuantityReceive();

//			em.createQuery("update LPTransferItem item set item.receivedQuantity=?1,item.modifiedBy=?2,item.modifiedDate=?3 where item.lpTransferItemId=?4")
//				.setParameter(1,(_tItem.getReceivedQuantity()+item.getQuantityReceive()))
//				.setParameter(2,user)
//				.setParameter(3,new java.util.Date())
//				.setParameter(4, _tItem.getLpTransferItemId())
//				.executeUpdate();
			_tItem.setReceivedQuantity(_tItem.getReceivedQuantity() + item.getQuantityReceive());

			_tItem.setModifiedBy(user);
			_tItem.setModifiedDate(new Date());

			if (_tItem.getReceivedQuantity() > _tItem.getTransferQuantity())
				throw new BadRequestException("Can not receive more then transfer quantity");

			em.persist(_tItem);

//			em.createQuery("update LProductRequestLineItem item set item.quantityReceived=?1,item.modifiedBy=?2,item.modifiedDate=?3 where item.locationProductRequestLineItemId=?4")
//				.setParameter(1,(requestLineItem.getQuantityReceived()+item.getQuantityReceive()))
//				.setParameter(2, user)
//				.setParameter(3, new java.util.Date())
//				.setParameter(4, requestLineItem.getLocationProductRequestLineItemId())
//				.executeUpdate();
			requestLineItem.setQuantityReceived(requestLineItem.getQuantityReceived() + item.getQuantityReceive());
			requestLineItem.setModifiedBy(user);
			requestLineItem.setModifiedDate(new Date());

			if (requestLineItem.getQuantityIssued() < requestLineItem.getQuantityReceived())
				throw new BadRequestException("Can not receive quantity more then issued");

			em.persist(requestLineItem);

			com.pratikshat.exicom.entity.LPTransferReceiveItem rItem = new com.pratikshat.exicom.entity.LPTransferReceiveItem();
			rItem.setCreatedby(user);
			rItem.setCreatedDate(new java.util.Date());
			rItem.setModifiedBy(user);
			rItem.setModifiedDate(new java.util.Date());
			rItem.setOwner(user);
			rItem.setProduct(products);
			rItem.setReceiveDate(new java.util.Date());
			rItem.setTotalQuantityReceived(item.getQuantityReceive());
			rItem.setTransferItem(_tItem);
			rItem.setTransferReceive(_receive);
			em.persist(rItem);
			com.pratikshat.exicom.entity.LocationProductItem lItem = null;
			try {
				lItem = em.createQuery(
						"select lItem from LocationProductItem lItem where lItem.product=?1 and lItem.location=?2",
						com.pratikshat.exicom.entity.LocationProductItem.class).setParameter(1, products)
						.setParameter(2, destLocation).getSingleResult();
			} catch (javax.persistence.NoResultException ex) {

			} catch (javax.persistence.NonUniqueResultException ex) {
				throw new BadRequestException("Invalid Request");
			}
			if (lItem != null) {
//				em.createQuery("update LocationProductItem litem set litem.okQuantity=?1,litem.quantityInOnHand=?2,litem.modifiedBy=?3,litem.modifiedDate=?4 where litem.id=?5")
//					.setParameter(1, (lItem.getOkQuantity()+item.getQuantityReceive()))
//					.setParameter(2, (lItem.getQuantityInOnHand()+item.getQuantityReceive()))
//					.setParameter(3, user)
//					.setParameter(4, new java.util.Date())
//					.setParameter(5, lItem.getId())
//					.executeUpdate();

				lItem.setOkQuantity(lItem.getOkQuantity() + item.getQuantityReceive());
				lItem.setQuantityInOnHand(lItem.getQuantityInOnHand() + item.getQuantityReceive());
				lItem.setModifiedBy(user);
				lItem.setModifiedDate(new Date());
				em.persist(lItem);

			} else {

				com.pratikshat.exicom.entity.LocationProductItem parentLocationItem = null;
				try {
					em.createQuery(
							"select sItem from LocationProductItem sItem where sItem.product=?1 and sItem.location=?2")
							.setParameter(1, products).setParameter(2, sourceLocation).getSingleResult();
				} catch (javax.persistence.NoResultException ex) {
					// throw new BadRequestException("Invalid Request");
				}

				lItem = new com.pratikshat.exicom.entity.LocationProductItem();
				lItem.setCreatedBy(user);
				lItem.setCreatedDate(new java.util.Date());
				lItem.setCustomerInventory(0);
				lItem.setFaultyQuantity(0);
				lItem.setHslQuantity(0);
				lItem.setHslStatus("L");
				lItem.setIssuedQunatity(0);
				lItem.setLocation(destLocation);
				lItem.setModifiedBy(user);
				lItem.setModifiedDate(new java.util.Date());
				lItem.setMslQuantity(0);
				lItem.setMslQuantityStatus("L");
				lItem.setOkQuantity(item.getQuantityReceive());
				lItem.setProduct(products);
				lItem.setQuantityInOnHand(item.getQuantityReceive());
				lItem.setQuantityUnitOfMasur("");
				lItem.setParentLocationProductItem(parentLocationItem);
				lItem.setRefurbished(0);

				em.persist(lItem);
				String manualId = String.format("PI-%s",
						com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(lItem.getId()), 5));
				lItem.setLocationProductItemId(manualId);
				em.persist(lItem);
			}
			float customerInventory = 0;
			float refurbished = 0;

			if (item.getReceivedSerialNumber() != null) {
				// lItem

				for (String serialNumber : item.getReceivedSerialNumber()) {
					LocationProductLineItems checkExists = null;

					try {
						em.createQuery(
								"select ct from LocationProductLineItems ct join ct.locationProductItem itemm where ct.itemSerialNumber=?1 and itemm.product=?2",
								LocationProductLineItems.class).setParameter(1, serialNumber.trim())
								.setParameter(2, products).getSingleResult();
						throw new BadRequestException(
								String.format("Product %s with serial number %s already exists,Wrong serial number",
										products.getProductPartCode(), serialNumber));
					} catch (NoResultException ex) {

					}

					catch (NonUniqueResultException ex) {
						throw new BadRequestException("Invalid serial number of product, multiple serial number "
								+ serialNumber + " found for part code " + products.getProductPartCode());
					}
					if (checkExists != null)
						throw new BadRequestException(
								String.format("Product %s with serial number %s already exists,Wrong serial number",
										products.getProductPartCode(), serialNumber));

					LPTransferLineItem transferLineItem = null;
					try {
						transferLineItem = em.createQuery(
								"select item from LPTransferLineItem item where item.transferItem=?1 and item.productSerialNumber=?2",
								LPTransferLineItem.class).setParameter(1, _tItem).setParameter(2, serialNumber.trim())
								.getSingleResult();

					} catch (NoResultException ex) {
						throw new BadRequestException(String.format("Invalid serial number %s", serialNumber.trim()));
					} catch (NonUniqueResultException ex) {
						throw new BadRequestException(String.format("Invalid serial number %s", serialNumber.trim()));
					}
					if (transferLineItem.isCustomerInventory()) {
						customerInventory++;
					}
					if (transferLineItem.isRefurbished())
						refurbished++;

					com.pratikshat.exicom.entity.LocationProductLineItems _lineItems = new com.pratikshat.exicom.entity.LocationProductLineItems();
					_lineItems.setCreatedBy(user);
					_lineItems.setCreatedDate(new java.util.Date());
					_lineItems.setCustomerInventory(false);
					_lineItems.setInStock(true);
					_lineItems.setItemSerialNumber(serialNumber.trim());
					_lineItems.setLocationProductItem(lItem);
					_lineItems.setLocationProductLineItemStatus(
							new com.pratikshat.exicom.entity.ProductLineItemStatus(1));
					_lineItems.setLocationProeuctLineItemState(
							new com.pratikshat.exicom.entity.ProductLineItemProductState(1));
					_lineItems.setModifiedBy(user);
					_lineItems.setModifiedDate(new java.util.Date());
					_lineItems.setReplaced(false);
					_lineItems.setReturned(false);
					_lineItems.setCustomerInventory(transferLineItem.isCustomerInventory());
					_lineItems.setCustomerName(transferLineItem.getCustomerName());
					_lineItems.setCustomerSapNumber(transferLineItem.getCustomerSapNumber());
					_lineItems.setRefurbished(transferLineItem.isRefurbished());

					em.persist(_lineItems);
					String lmid = String.format("PIL-%s",
							MiscellaneousUtils.fillZero(String.valueOf(_lineItems.getId()), 5));
					_lineItems.setLocationProductLineItemId(lmid);
					em.persist(_lineItems);
					transferLineItem.setQuantityReceived(1F);
					transferLineItem.setReceived(true);
					transferLineItem.setModifiedBy(user);
					transferLineItem.setModifiedDate(new Date());
					em.persist(transferLineItem);

					// PUSHING DATA TO LOCATION LPTransferReceiveLineItems

					LPTransferReceiveLineItem lpTransferReceiveLineItem = new LPTransferReceiveLineItem();
					lpTransferReceiveLineItem.setCreatedBy(user);
					lpTransferReceiveLineItem.setCreatedDate(new Date());
					lpTransferReceiveLineItem.setModifiedBy(user);
					lpTransferReceiveLineItem.setModifiedDate(new Date());
					lpTransferReceiveLineItem.setOwner(user);
					lpTransferReceiveLineItem.setProductSerialNumber(serialNumber);
					lpTransferReceiveLineItem.setQuantityReceived(1);
					lpTransferReceiveLineItem.setReceiveDate(new Date());
					lpTransferReceiveLineItem.setReceiveItem(rItem);
					lpTransferReceiveLineItem.setTransferLineItem(transferLineItem);
					lpTransferReceiveLineItem.setRefurbished(transferLineItem.isRefurbished());

					em.persist(lpTransferReceiveLineItem);
					ProductLineItem pLineItem = null;
					try {
						pLineItem = dbProductLineItem.findByProductAndSerialNumber(lItem.getProduct(),
								serialNumber.trim());
					} catch (IncorrectResultSizeDataAccessException ex) {
						throw new com.pratikshat.exicom.exception.BadRequestException("Duplicate serial number exist");
					}
					if (pLineItem != null) {
						pLineItem.setModifiedBy(user);
						pLineItem.setModifiedDate(new Date());
						pLineItem.setPosition(String.valueOf(lItem.getLocation().getLocationId()));
						pLineItem.setPositionState(ProductLineItemRepository.AT_LOCATION);
						pLineItem.setState(new ProductLineItemProductState(1));
						pLineItem.setStatus(new ProductLineItemStatus(1));
						dbProductLineItem.save(pLineItem);
					} else {
						pLineItem = new ProductLineItem();
						pLineItem.setCreatedBy(user);
						pLineItem.setCreatedDate(new Date());
						pLineItem.setProduct(lItem.getProduct());
						pLineItem.setSerialNumber(serialNumber.trim());
						pLineItem.setModifiedBy(user);
						pLineItem.setModifiedDate(new Date());
						pLineItem.setPosition(String.valueOf(lItem.getLocation().getLocationId()));
						pLineItem.setPositionState(ProductLineItemRepository.AT_LOCATION);
						pLineItem.setState(new ProductLineItemProductState(1));
						pLineItem.setStatus(new ProductLineItemStatus(1));
						dbProductLineItem.save(pLineItem);
					}

					System.out.println("Location Receive Line Item owner => " + user.getEmailId());
				}
			}

			lItem.setCustomerInventory((lItem.getCustomerInventory() + customerInventory));
			lItem.setRefurbished(lItem.getRefurbished() + refurbished);

			em.persist(lItem);
		}
//		em.createQuery("update LPTransferReceive receive set receive.totalQuantityReceived=?1 where receive.receiveId=?2")
//			.setParameter(1, totalItemReceived)
//			.setParameter(2,_receive.getReceiveId())
//			.executeUpdate();

		_receive.setTotalQuantityReceived(totalItemReceived);
		em.persist(_receive);

//		em.createQuery("update LProductRequest req set req.totalQuantityReceived=?1,req.locationProductRequestStatus=?2 where req.locationProductRequestId=?3")
//			.setParameter(1, (request.getTotalQuantityReceived())+totalItemReceived)
//			.setParameter(2,(request.getTotalQuantityRequired()<=(request.getTotalQuantityReceived()+totalItemReceived))?"Received":"Partial Received")
//			.setParameter(3, request.getLocationProductRequestId())
//			.executeUpdate();
		request.setTotalQuantityReceived(request.getTotalQuantityReceived() + totalItemReceived);

		if (request.getLocationProductRequestStatus().equals("Short Closed")) {
			System.out.println("I am here at 1004 ");
			request.setLocationProductRequestStatus("Short Closed");

		} else {
			System.out.println("I am here at 1009 ");
			request.setLocationProductRequestStatus(
					request.getTotalQuantityRequired() <= request.getTotalQuantityReceived() ? "Received"
							: "Partial Received");
		}
		em.persist(request);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, Exception.class, RuntimeException.class,
			SQLException.class })
	public void removeProductRequestItem(long id, LoginUser user) throws BadRequestException, UnauthoriseException {
		LProductRequestLineItem item = null;
		ServiceRecource sr = em.createQuery("select sr from ServiceRecource sr where sr.user=?1", ServiceRecource.class)
				.setParameter(1, new EmployeeDetails(user.getUsername())).getSingleResult();
		try {
			item = em.createQuery(
					"select item from LProductRequestLineItem item join item.locationProductRequest preq join preq.destinationLocation deloc where item=?1",
					LProductRequestLineItem.class).setParameter(1, new LProductRequestLineItem(id)).getSingleResult();
		} catch (NoResultException ex) {
			throw new BadRequestException("Invalid item id");
		} catch (NonUniqueResultException ex) {
			throw new BadRequestException("Invalid item id");
		}
		if (!item.getLocationProductRequest().getLocationProductRequestStatus().equalsIgnoreCase("Draft"))
			throw new BadRequestException("Product request not in Draft");
		if (item.getLocationProductRequest().getDestinationLocation().getLocationId() != sr.getLocation()
				.getLocationId())
			throw new UnauthoriseException("You are not authorised");

		LProductRequest rq = item.getLocationProductRequest();
		rq.setTotalQuantityRequired(rq.getTotalQuantityRequired() - item.getQuantity());
		rq.setModifiedBy(new EmployeeDetails(user.getUsername()));
		rq.setModifiedDate(new Date());
		em.persist(rq);
		em.createQuery("delete from LProductRequestLineItem item where item=?1").setParameter(1, item).executeUpdate();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, Exception.class, RuntimeException.class,
			SQLException.class })
	public void updateSourceLocation(long productRequestId, long locationId, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		LProductRequest pr = null;
		try {
			pr = em.createQuery("select pr from LProductRequest pr where pr=?1", LProductRequest.class)
					.setParameter(1, new LProductRequest(productRequestId)).getSingleResult();
		} catch (NoResultException ex) {
			throw new BadRequestException("Invalid Id");
		}
		if (!pr.getLocationProductRequestStatus().equalsIgnoreCase("Draft"))
			throw new BadRequestException("Product Request not in Draft mode");

		if (!pr.getOwnerDetails().getEmailId().equalsIgnoreCase(user.getUsername()))
			throw new UnauthoriseException("You are not authorised");

		pr.setModifiedDate(new Date());
		pr.setModifiedBy(new EmployeeDetails(user.getUsername()));
		pr.setSourceLocation(new Location(locationId));
		if (pr.getSourceLocation().getLocationId() == pr.getDestinationLocation().getLocationId())
			throw new BadRequestException("Destination location can not be same as source location");
		em.persist(pr);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, Exception.class, RuntimeException.class,
			SQLException.class })
	public void updateProductPartCode(long productRequestItemId, String nPartCode, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		LProductRequestLineItem item = null;
		ServiceRecource sr = null;
		try {
			item = em.createQuery(
					"select item from LProductRequestLineItem item join item.locationProductRequest rq join rq.sourceLocation sloc where item=?1",
					LProductRequestLineItem.class).setParameter(1, new LProductRequestLineItem(productRequestItemId))
					.getSingleResult();
		} catch (NoResultException ex) {
			throw new BadRequestException("Invalid Items");
		}
		try {
			sr = em.createQuery("select sr from ServiceRecource sr where sr.user=?1", ServiceRecource.class)
					.setParameter(1, new EmployeeDetails(user.getUsername())).getSingleResult();
		} catch (NoResultException ex) {
			throw new BadRequestException("Service Resource not found");
		}

		// if(!item.getLocationProductRequest().getSourceLocation().getOwner().getEmailId().equalsIgnoreCase(user.getUsername()))
		// throw new UnauthoriseException("You are not authorise");

		if (sr.getLocation().getLocationId() != item.getLocationProductRequest().getSourceLocation().getLocationId())
			throw new UnauthoriseException("You are not authorise");

		LPTransferItem _testTransfer = null;
		try {
			_testTransfer = em
					.createQuery("select pt from LPTransferItem pt where pt.lpRequestLineItem=?1", LPTransferItem.class)
					.setParameter(1, item).getSingleResult();
		} catch (NoResultException ex) {

		} catch (NonUniqueResultException ex) {
			throw new BadRequestException(
					"Can not change product part code, transfer already begin for request part code");
		}
		item.setModifiedBy(new EmployeeDetails(user.getUsername()));
		item.setModifiedDate(new Date());
		item.setProduct(new Products(nPartCode));
		em.persist(item);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, Exception.class, RuntimeException.class,
			SQLException.class })
	public void submitProductRequestToSAP(long prdId, LoginUser user) throws Exception {
		ServiceRecource sr = null;
		LProductRequest lpr = null;

		try {
			sr = em.createQuery("select sr from ServiceRecource sr where sr.user=?1", ServiceRecource.class)
					.setParameter(1, new EmployeeDetails(user.getUsername())).getSingleResult();
		} catch (NoResultException ex) {
			throw new Exception("Invalid resource");
		} catch (NonUniqueResultException ex) {
			throw new Exception("Invalid resource");
		}

		try {
			lpr = em.createQuery("select lpr from LProductRequest lpr where lpr=?1", LProductRequest.class)
					.setParameter(1, new LProductRequest(prdId)).getSingleResult();
		} catch (NoResultException ex) {
			throw new Exception("Invalid resource");
		} catch (NonUniqueResultException ex) {
			throw new Exception("Invalid resource");
		}

		if (lpr.getSourceLocation().getLocationId() == sr.getLocation().getLocationId()) {
			if (lpr.getLocationProductRequestStatus().equalsIgnoreCase("Submitted BY SCH")) {
				lpr.setLocationProductRequestStatus("Submitted");
				lpr.setModifiedBy(new EmployeeDetails(user.getUsername()));
				lpr.setModifiedDate(new Date());
				em.persist(lpr);
			} else {
				throw new Exception("Already submitted to SAP");
			}
		} else {
			throw new Exception("You are not authorised");
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, Exception.class, RuntimeException.class,
			SQLException.class })
	public void disableProductLineItems(long productRequestLineItemId, boolean flag, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		ServiceRecource sr = null;
		try {

			sr = em.createQuery("select sr from ServiceRecource sr where sr.user=?1", ServiceRecource.class)
					.setParameter(1, new EmployeeDetails(user.getUsername())).getSingleResult();

		} catch (NoResultException ex) {
			throw new BadRequestException("Not authorised");
		} catch (NonUniqueResultException ex) {
			throw new BadRequestException("Multiple resource found");
		}
		LProductRequestLineItem lprItem = null;
		LProductRequest lpr = null;
		try {
			lprItem = em.createQuery(
					"select lpr from LProductRequestLineItem lpr join lpr.locationProductRequest lp join lp.sourceLocation sloc join lp.destinationLocation dloc where lpr=?1",
					LProductRequestLineItem.class)
					.setParameter(1, new LProductRequestLineItem(productRequestLineItemId)).getSingleResult();
			lpr = lprItem.getLocationProductRequest();
		} catch (NoResultException ex) {
			throw new BadRequestException("Product request item not found");
		} catch (NonUniqueResultException ex) {
			throw new BadRequestException("Product request item not found");
		}
		if (lprItem.getLocationProductRequest().getSourceLocation().getLocationId() == sr.getLocation().getLocationId()
				|| lprItem.getLocationProductRequest().getDestinationLocation().getLocationId() == sr.getLocation()
						.getLocationId()) {
			lprItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
			lprItem.setModifiedDate(new Date());
//			lprItem.setLineItemStatus("Short Closed");
			lprItem.setDisabledItem(flag); // change by shagufta for new shortclose function on 05-07-2022
			lpr.setTotalQuantityRequired(lpr.getTotalQuantityRequired() - lprItem.getQuantity());
			lpr.setModifiedBy(new EmployeeDetails(user.getUsername()));
			lpr.setModifiedDate(new Date());
			if ((lpr.getTotalQuantityRequired() - lprItem.getQuantity()) == lpr.getTotalQuantityReceived()) {
				lpr.setLocationProductRequestStatus("Received");
			}
			em.persist(lprItem);
			em.persist(lpr);
		} else {
			throw new UnauthoriseException("You are not authorised");
		}
	}

	@Override
	public List<LocationProductRequestResponse> advanceSearch(Map<String, Object> filterData, LoginUser user,
			List<Long> childLoc) throws ParseException {
		StringBuilder queryStringBuilder = new StringBuilder();
		queryStringBuilder.append(
				"select new com.pratikshat.exicom.model.response.LocationProductRequestResponse(lpr) from LProductRequest lpr left join lpr.sourceLocation sl join sl.locationType sltyp left join lpr.destinationLocation dl join dl.locationType dltyp left join lpr.createdBy cby left join lpr.modifiedBy mby left join lpr.ownerDetails onr where lpr.locationProductRequestStatus !='Archived' and 1=1 ");
		if (filterData.get("manualId") != null && !filterData.get("manualId").equals(""))
			queryStringBuilder.append(" and lpr.locationProductRequestManualId like :locationProductRequestManualId");
		if (filterData.get("sourceLocation") != null && !filterData.get("sourceLocation").equals(""))
			queryStringBuilder.append(" and sl.locationName like :sourceLocation");
		if (filterData.get("destinationLocation") != null && !filterData.get("destinationLocation").equals(""))
			queryStringBuilder.append(" and dl.locationName like :destinationLocation");
		if (filterData.get("status") != null && !filterData.get("status").equals(""))
			queryStringBuilder.append(" and lpr.locationProductRequestStatus like :status");

//			if(filterData.get("Warehouse")!=null&&!filterData.get("Warehouse").equals("")) {
//				
//			}
//			
//			if(filterData.get("Van")!=null&&!filterData.get("Van").equals("")) {
//				
//			}
//			
//			if(filterData.get("Plant")!=null&&!filterData.get("Plant").equals("")) {
//				
//			}
//			
//			if(filterData.get("Repair Engineer")!=null&&!filterData.get("Repair Engineer").equals("")) {
//				
//			}

		if (childLoc != null)
			queryStringBuilder.append(" and (sl.locationId in :childsLoc or dl.locationId in :childsLoc)");

		Query qr = em.createQuery(queryStringBuilder.toString(), LocationProductRequestResponse.class);

		if (filterData.get("manualId") != null && !filterData.get("manualId").equals(""))
			qr.setParameter("locationProductRequestManualId", "%" + filterData.get("manualId") + "%");
		if (filterData.get("sourceLocation") != null && !filterData.get("sourceLocation").equals(""))
			qr.setParameter("sourceLocation", "%" + filterData.get("sourceLocation") + "%");
		if (filterData.get("destinationLocation") != null && !filterData.get("destinationLocation").equals(""))
			qr.setParameter("destinationLocation", "%" + filterData.get("destinationLocation") + "%");
		if (filterData.get("status") != null && !filterData.get("status").equals(""))
			qr.setParameter("status", "%" + filterData.get("status") + "%");

		if (childLoc != null) {
			List<Long> emps = new ArrayList<Long>();
			for (Long loc : childLoc) {
				emps.add(loc);
			}
			qr.setParameter("childsLoc", emps);
		}
		return qr.getResultList();
	}

	@Override
	public List<LocationProductRequestItemResponse> getProductRequestItemsAdvanceSearch(long productrequestid,
			Map<String, Object> request) throws ParseException {

		StringBuilder queryStringBuilder = new StringBuilder();
		queryStringBuilder.append(
				"select new com.pratikshat.exicom.model.response.LocationProductRequestItemResponse(item) from LProductRequestLineItem item join item.locationProductRequest prd join item.product pd where prd.locationProductRequestStatus !='Archived' and prd.locationProductRequestId=:productRequestId");

		if (request.get("name") != null && !request.get("name").equals(""))
			queryStringBuilder.append(
					" and item.locationProductRequestLineItemManualId like :locationProductRequestLineItemManualId");
		if (request.get("product") != null && !request.get("product").equals(""))
			queryStringBuilder.append(" and pd.productPartCode like :productPartCode");
		if (request.get("productDescription") != null && !request.get("productDescription").equals(""))
			queryStringBuilder.append(" and pd.pdoductDescription like :productDescription");

		Query qr = em.createQuery(queryStringBuilder.toString(), LocationProductRequestItemResponse.class);

		qr.setParameter("productRequestId", productrequestid);

		if (request.get("name") != null && !request.get("name").equals(""))
			qr.setParameter("locationProductRequestLineItemManualId", "%" + request.get("name") + "%");
		if (request.get("product") != null && !request.get("product").equals(""))
			qr.setParameter("productPartCode", "%" + request.get("product") + "%");
		if (request.get("productDescription") != null && !request.get("productDescription").equals(""))
			qr.setParameter("productDescription", "%" + request.get("productDescription") + "%");

		return qr.getResultList();
	}

	@Override
	public List<LocationProductTransferItemResponse> getLocationProductTransferByProductAdvanceSearch(
			long productrequestid, Map<String, Object> request) throws ParseException {

		StringBuilder queryStringBuilder = new StringBuilder();
		queryStringBuilder.append(
				"select new com.pratikshat.exicom.model.response.LocationProductTransferItemResponse(item) from LPTransferItem item join item.product prd join item.lpTranfer trn join trn.locationProductRequest preq where preq.locationProductRequestStatus !='Archived' and preq.locationProductRequestId=:productRequestId");

		if (request.get("name") != null && !request.get("name").equals(""))
			queryStringBuilder.append(" and item.lpTransferItemIdManualId like :lpTransferItemIdManualId");
		if (request.get("product") != null && !request.get("product").equals(""))
			queryStringBuilder.append(" and prd.productPartCode like :productPartCode");
		if (request.get("productDescription") != null && !request.get("productDescription").equals(""))
			queryStringBuilder.append(" and prd.pdoductDescription like :productDescription");

		Query qr = em.createQuery(queryStringBuilder.toString(), LocationProductTransferItemResponse.class);

		qr.setParameter("productRequestId", productrequestid);

		if (request.get("name") != null && !request.get("name").equals(""))
			qr.setParameter("lpTransferItemIdManualId", "%" + request.get("name") + "%");
		if (request.get("product") != null && !request.get("product").equals(""))
			qr.setParameter("productPartCode", "%" + request.get("product") + "%");
		if (request.get("productDescription") != null && !request.get("productDescription").equals(""))
			qr.setParameter("productDescription", "%" + request.get("productDescription") + "%");

		return qr.getResultList();
	}

//	short close of items by Tahseen
	@Autowired
	private ServiceRecourseRepository dbService;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, Exception.class, RuntimeException.class,
			SQLException.class })
	public void shortCloseProductRequest(long prdId, LoginUser user) throws Exception {

		ServiceRecource sr = null;
		LProductRequest pr=null;
		List<LProductRequestLineItem> litems=null;
		
		
		try {
			sr = em.createQuery("select sr from ServiceRecource sr join sr.location loc where sr.user=?1",ServiceRecource.class)
					.setParameter(1, new EmployeeDetails(user.getUsername()))
					.getSingleResult();
		}
		catch(NoResultException ex) {
			throw new UnauthoriseException("You are not authorised");
		}
		catch(NonUniqueResultException ex) {
			throw new BadRequestException("Multiple resource found, Please contact to IT admin");
		}
		
		try {
			litems = em.createQuery("select litem from LProductRequestLineItem litem join litem.locationProductRequest pr where pr.locationProductRequestId =?1",LProductRequestLineItem.class)
					.setParameter(1, prdId)
					.getResultList();
			
		}catch(NoResultException ex) {
			throw new BadRequestException("Invalid request line item id");
		}
		
		if(litems.size()<=0)
			throw new BadRequestException("Invalid id no line item found for short close");
		
		pr = litems.get(0).getLocationProductRequest();
		
		if(pr.getSourceLocation().getLocationId()!=sr.getLocation().getLocationId())
			throw new UnauthoriseException("You are not authorised");
		
		if(sr.getLocation().getLocationId()==pr.getSourceLocation().getLocationId()||sr.getLocation().getLocationId()== pr.getDestinationLocation().getLocationId()) {
			
			for(LProductRequestLineItem litem:litems) {
				float sorCloseQty = litem.getQuantity()-litem.getQuantityIssued();
				
				if(sorCloseQty<=0) {
					continue;
				}
				if(litem.isDisabledItem())
					continue;
				
				litem.setDisabledItem(true);
				litem.setLineItemStatus("Short Closed");
				litem.setShortCloseQty(sorCloseQty);
				litem.setModifiedBy(new EmployeeDetails(user.getUsername()));
				litem.setModifiedDate(new Date());
				em.persist(litem);
				
				pr.setTotalShortCloseQty(pr.getTotalShortCloseQty()+sorCloseQty);
				
				
			}
			pr.setModifiedBy(new EmployeeDetails(user.getUsername()));
			pr.setModifiedDate(new Date());
			pr.setShortClosed(true);
			pr.setLocationProductRequestStatus("Short Closed");
			em.persist(pr);
			
		}
		else {
			throw new UnauthoriseException("You are not authorised");
		}
	}

//	short close of items of PR by Tahseen

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, Exception.class, RuntimeException.class,
			SQLException.class })
	public void shortCloseProductRequestItems(long prdId, LoginUser user, long productRequestLineItemId)
			throws BadRequestException,UnauthoriseException {
		ServiceRecource sr = null;
		LProductRequest pr=null;
		LProductRequestLineItem litem=null;
		
		
		try {
			sr = em.createQuery("select sr from ServiceRecource sr join sr.location loc where sr.user=?1",ServiceRecource.class)
					.setParameter(1, new EmployeeDetails(user.getUsername()))
					.getSingleResult();
		}
		catch(NoResultException ex) {
			throw new UnauthoriseException("You are not authorised");
		}
		catch(NonUniqueResultException ex) {
			throw new BadRequestException("Multiple resource found, Please contact to IT admin");
		}
		
		try {
			litem = em.createQuery("select litem from LProductRequestLineItem litem join litem.locationProductRequest pr where litem.locationProductRequestLineItemId =?1",LProductRequestLineItem.class)
					.setParameter(1, productRequestLineItemId)
					.getSingleResult();
			
		}catch(NoResultException ex) {
			throw new BadRequestException("Invalid request line item id");
		}
		
		pr = litem.getLocationProductRequest();
		
		if(pr.getSourceLocation().getLocationId()!=sr.getLocation().getLocationId())
			throw new UnauthoriseException("You are not authorised");
		
		
		if(sr.getLocation().getLocationId()==pr.getSourceLocation().getLocationId()||sr.getLocation().getLocationId()== pr.getDestinationLocation().getLocationId()) {
			float sorCloseQty = litem.getQuantity()-litem.getQuantityIssued();
			
			if(sorCloseQty<=0) {
				throw new BadRequestException("You can not short close this item");
			}
			
			litem.setDisabledItem(true);
			litem.setLineItemStatus("Short Closed");
			litem.setShortCloseQty(sorCloseQty);
			litem.setModifiedBy(new EmployeeDetails(user.getUsername()));
			litem.setModifiedDate(new Date());
			em.persist(litem);
			
			pr.setTotalShortCloseQty(pr.getTotalShortCloseQty()+sorCloseQty);
			pr.setModifiedBy(new EmployeeDetails(user.getUsername()));
			pr.setModifiedDate(new Date());
			
			em.persist(pr);
			
		}
		else {
			throw new UnauthoriseException("You are not authorised");
		}
				
	}

}
