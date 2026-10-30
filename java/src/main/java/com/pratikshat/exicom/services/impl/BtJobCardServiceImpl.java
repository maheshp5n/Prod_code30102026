package com.pratikshat.exicom.services.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.BtCustomerInventoryIn;
import com.pratikshat.exicom.entity.BtJobCard;
import com.pratikshat.exicom.entity.BtJobCardProductConsume;
import com.pratikshat.exicom.entity.BtJobCardQc;
import com.pratikshat.exicom.entity.BtProductItem;
import com.pratikshat.exicom.entity.BtProducts;
import com.pratikshat.exicom.entity.BtServiceAppointment;
import com.pratikshat.exicom.entity.BtServiceOrder;
import com.pratikshat.exicom.entity.BtServiceOrderItem;
import com.pratikshat.exicom.entity.BtServiceOrderItemReceive;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.ServiceOrderItem;
import com.pratikshat.exicom.entity.ServiceRecource;
import com.pratikshat.exicom.entity.UserProfile;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.BtCustomerInventoryInRepository;
import com.pratikshat.exicom.repository.BtJobCardProductConsumeRepository;
import com.pratikshat.exicom.repository.BtJobCardQcRepository;
import com.pratikshat.exicom.repository.BtJobCardRepository;
import com.pratikshat.exicom.repository.BtProductItemRepository;
import com.pratikshat.exicom.repository.BtProductsRepository;
import com.pratikshat.exicom.repository.BtServiceOrderItemReceiveRepository;
import com.pratikshat.exicom.repository.BtServiceOrderItemRepository;
import com.pratikshat.exicom.repository.BtServiceOrderRepository;
import com.pratikshat.exicom.repository.ServiceRecourseRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtJobCardService;

import org.bouncycastle.asn1.ocsp.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BtJobCardServiceImpl implements BtJobCardService {

	@Autowired
	BtJobCardRepository dbJobCard;
	@Autowired
	BtServiceOrderItemRepository dbServiceOrderItem;
	@Autowired
	BtServiceOrderRepository dbServiceOrder;
	@Autowired
	BtServiceOrderItemReceiveRepository dbServiceOrderItemReceive;
	@Autowired
	UserProfileRepository dbUserProfile;
	@Autowired
	BtProductItemRepository dbStock;
	@Autowired
	ServiceRecourseRepository dbResource;
	@Autowired
	BtJobCardProductConsumeRepository dbProductConsume;
	@Autowired
	BtProductsRepository dbProduct;
	@Autowired
	BtJobCardQcRepository dbQc;

	@Autowired
	BtCustomerInventoryInRepository dbCustomerInventoryIn;
	@Autowired
	UserProfileRepository dbProfileRepo;
	@Autowired
	JdbcTemplate jdbc;

	@Override
	public Map<String, Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException {
		if (dbUserProfile.hasObjectAccess(user.getUsername(), BtJobCard.class, UserProfileRepository.VIEW_ALL))
			return dbJobCard.getSingle(Long.parseLong(id.toString()));
		else if (dbUserProfile.hasObjectAccess(user.getUsername(), BtJobCard.class, UserProfileRepository.VIEW)) {
			return dbJobCard.getSingle(Long.parseLong(id.toString()),
					dbUserProfile.getListOfUserAndChildByUser(user.getUsername()));
		} else
			throw new UnauthoriseException("You are not authorised");
	}

	@Override
	public Page<Map<String, Object>> getPage(Pageable page, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if (dbUserProfile.hasObjectAccess(user.getUsername(), BtJobCard.class, UserProfileRepository.VIEW_ALL)) {
			return dbJobCard.getPage(page);
		} else if (dbUserProfile.hasObjectAccess(user.getUsername(), BtJobCard.class, UserProfileRepository.VIEW))
			return dbJobCard.getPage(dbUserProfile.getListOfUserAndChildByUser(user.getUsername()), page);
		else
			throw new UnauthoriseException("You are not authorised");
	}

	@Override
	@Transactional(rollbackFor = { BadRequestException.class, UnauthoriseException.class, RuntimeException.class,
			Exception.class }, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
	public Map<String, Object> saveObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if (!dbUserProfile.hasObjectAccess(user.getUsername(), BtJobCard.class, UserProfileRepository.CREATE))
			throw new UnauthoriseException("You are not authorised");
		Long serviceOrderItemId = Long.parseLong(request.get("serviceOrderItemId").toString());
		Optional<BtServiceOrderItem> optionalServiceOrderItem = dbServiceOrderItem.findById(serviceOrderItemId);
		if (!optionalServiceOrderItem.isPresent())
			throw new BadRequestException("Invalid service order item id");
		BtServiceOrderItem serviceOrderItem = optionalServiceOrderItem.get();
		BtServiceOrder serviceOrder = dbServiceOrder.findById(serviceOrderItem.getServiceOrder().getId()).get();
		ServiceRecource userRe = dbResource.getByEmail(user.getUsername());

		if (userRe.getLocation().getLocationId() != serviceOrder.getDestinationLocation().getLocationId())
			throw new UnauthoriseException("You are not authorised");

		Optional<BtServiceOrderItemReceive> optionalServiceOrderItemReceive = dbServiceOrderItemReceive
				.findById(serviceOrderItemId);
		if (!optionalServiceOrderItemReceive.isPresent())
			throw new BadRequestException("Invalid service order item id, has not been received yet");
		if (serviceOrderItem.getAssigned())
			throw new BadRequestException("Invlaid service order item id, Already assigned");
		Optional<BtJobCard> cchecck = dbJobCard.findById(serviceOrderItemId);
		if (cchecck.isPresent())
			throw new BadRequestException("Invalid service order item id, Already assigned");

		String assignedTo = request.get("assignedTo") == null ? null : request.get("assignedTo").toString();
		if (assignedTo == null)
			throw new BadRequestException("Assigned engineer not found");
		ServiceRecource engRe = dbResource.getByEmail(assignedTo);

		if (serviceOrderItem.getInCustomerInventory() != null) {
			BtCustomerInventoryIn _instatus = dbCustomerInventoryIn
					.findById(serviceOrderItem.getInCustomerInventory().getId()).get();
			_instatus.setCustomerInventoryStatus("Under Repaired");
			_instatus.setCustomerInventoryStage("Assigned To Engineer");
			dbCustomerInventoryIn.updateDetails(_instatus, user);
		}

		BtJobCard jobCard = new BtJobCard();
		jobCard.setAssignedDate(new Date());
		jobCard.setId(serviceOrderItem.getId());

		jobCard.setAssignedTo(new EmployeeDetails(assignedTo));
		jobCard.setOwner(new EmployeeDetails(assignedTo));
		jobCard.setQcDone(false);
		jobCard.setRepared(false);
		jobCard.setReturnedToSource(false);
		jobCard.setServiceOrderItem(serviceOrderItem);
		jobCard.setPreviousState(serviceOrderItem.getaStatus());
		dbJobCard.saveDetails(jobCard, user);

		dbStock.subQuantity(serviceOrderItem.getProduct(), serviceOrder.getDestinationLocation(), 1F,
				serviceOrderItem.getCustomerInventory(), serviceOrderItem.getaStatus(), user);

		dbStock.addQuantity(serviceOrderItem.getProduct(), engRe.getLocation(), 1F,
				serviceOrderItem.getCustomerInventory(), serviceOrderItem.getaStatus(), user);
				
		request.put("jobCardId", jobCard.getId());
		request.put("jobCardManualId", jobCard.getManualId());

		serviceOrderItem.setAssigned(true);
		serviceOrderItem.setAssignedTo(jobCard.getAssignedTo());

		dbServiceOrder.addTotalNumberOfItemAssigned(serviceOrder.getId(), 1F, user);

		return request;
	}

	@Override
	@Transactional(rollbackFor = { BadRequestException.class, UnauthoriseException.class, RuntimeException.class,
			Exception.class }, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
	public Map<String, Object> updateObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		Long jobCardId = Long.parseLong(request.get("jobCardId").toString());
		Integer reparState = Integer.parseInt(request.get("repairState").toString());
		boolean assignToQc = Boolean.parseBoolean(request.get("assignToQc").toString());
		String qcToEmailid = null;
		if (assignToQc)
			qcToEmailid = request.get("assignToQcEmailId").toString();

		Optional<BtJobCard> optionalJobCard = dbJobCard.findById(jobCardId);
		if (!optionalJobCard.isPresent())
			throw new BadRequestException("Invalid job card id");
		BtJobCard jobCard = optionalJobCard.get();

		if (jobCard.getAssignedForQc() != null && jobCard.getAssignedForQc())
			throw new BadRequestException("Product is assigned for QC can not edit record");
		jobCard.setBatteryVoltageBef(MiscellaneousUtils.objToString(request.get("batteryVoltageBef")));
		jobCard.setBatteryVoltageAft(MiscellaneousUtils.objToString(request.get("batteryVoltageAft")));
		jobCard.setBatteryAhAft(MiscellaneousUtils.objToString(request.get("batteryAhAft")));
		jobCard.setCellVmax(MiscellaneousUtils.objToString(request.get("cellVmax")));
		jobCard.setCellVmin(MiscellaneousUtils.objToString(request.get("cellVmin")));
		jobCard.setCorrectiveAction(MiscellaneousUtils.objToString(request.get("correctiveAction")));
		jobCard.setEngResolutionMethod(MiscellaneousUtils.objToString(request.get("engResolutionMethod")));
		jobCard.setEngRemarks(MiscellaneousUtils.objToString(request.get("engRemarks")));
		jobCard.setBtBalanceAt(MiscellaneousUtils.objToString(request.get("btBalanceAt")));
		jobCard.setCellBledge(MiscellaneousUtils.objToString(request.get("cellBledge")));
		jobCard.setMatSorteg(MiscellaneousUtils.objToString(request.get("matSorteg")));
		jobCard.setBerRemarks(MiscellaneousUtils.objToString(request.get("berRemarks")));
		//set observation
		jobCard.setObservation1(MiscellaneousUtils.objToString(request.get("observation1")));
		jobCard.setObservation2(MiscellaneousUtils.objToString(request.get("observation2")));
		jobCard.setObservation3(MiscellaneousUtils.objToString(request.get("observation3")));
		jobCard.setObservation4(MiscellaneousUtils.objToString(request.get("observation4")));

		// if(jobCard.getBatteryVoltageBef().equalsIgnoreCase("")||jobCard.getBatteryVoltageBef().equalsIgnoreCase("0"))
		// throw new BadRequestException("Please provide voltage before ");
		// if(jobCard.getBatteryVoltageAft().equalsIgnoreCase("")||jobCard.getBatteryVoltageAft().equalsIgnoreCase("0"))
		// throw new BadRequestException("Please provide voltage after");
		// if(jobCard.getBatteryAhAft().equalsIgnoreCase("")||jobCard.getBatteryAhAft().equalsIgnoreCase("0"))
		// throw new BadRequestException("Please provide battery AH after");
		// if(jobCard.getCellVmax().equalsIgnoreCase("")||jobCard.getCellVmax().equalsIgnoreCase("0"))
		// throw new BadRequestException("Please provide Cell Vmax");
		// if(jobCard.getCellVmin().equalsIgnoreCase("")||jobCard.getCellVmin().equalsIgnoreCase("0"))
		// throw new BadRequestException("Please provide Cell Vmin");
		// if(jobCard.getCorrectiveAction().equalsIgnoreCase("")||jobCard.getCorrectiveAction().equalsIgnoreCase("0"))
		// throw new BadRequestException("Please provide corrective action");
		// if(jobCard.getEngResolutionMethod().equalsIgnoreCase("")||jobCard.getEngResolutionMethod().equalsIgnoreCase("0"))
		// throw new BadRequestException("Please provide Resolution method");
		// if(jobCard.getEngRemarks().equalsIgnoreCase("")||jobCard.getEngRemarks().equalsIgnoreCase("0"))
		// throw new BadRequestException("Please provide Remarks");
		// if(jobCard.getBtBalanceAt().equalsIgnoreCase("")||jobCard.getBtBalanceAt().equalsIgnoreCase("0"))
		// throw new BadRequestException("Please provide ballance at");
		// if(jobCard.getCellBledge().equalsIgnoreCase(""))
		// throw new BadRequestException("Please provide cell bledge");
		// if(jobCard.getMatSorteg().equalsIgnoreCase("")||jobCard.getMatSorteg().equalsIgnoreCase("0"))
		// throw new BadRequestException("Please provide Mat Sortage");
		// if(jobCard.getBerRemarks().equalsIgnoreCase("")||jobCard.getBerRemarks().equalsIgnoreCase("0"))
		// throw new BadRequestException("Please provide BER Remarks");

		BtServiceOrderItem serviceOrderItem = dbServiceOrderItem.findById(jobCard.getServiceOrderItem().getId()).get();

		if (!assignToQc) {
			if (reparState != jobCard.getPreviousState()) {
				dbStock.subQuantity(serviceOrderItem.getProduct(), 1F, serviceOrderItem.getCustomerInventory(),
						jobCard.getPreviousState(), user);
				dbStock.addQuantity(serviceOrderItem.getProduct(), 1F, serviceOrderItem.getCustomerInventory(),
						reparState, user);
			}
		} else {
			ServiceRecource qcRe = dbResource.getByEmail(qcToEmailid);
			//Ruchit
			if(qcRe.getLocation()==null) {
				throw new BadRequestException("QC service resource location not found");
			}
			//
			dbStock.subQuantity(serviceOrderItem.getProduct(), 1F, serviceOrderItem.getCustomerInventory(),
					jobCard.getPreviousState(), user);
			dbStock.addQuantity(serviceOrderItem.getProduct(), qcRe.getLocation(), 1F,
					serviceOrderItem.getCustomerInventory(), reparState, user);
			jobCard.setAssignedForQc(true);
			jobCard.setRepared(true);
			jobCard.setRepairDate(new Date());
			jobCard.setRepairProductState(reparState);

			BtJobCardQc qc = new BtJobCardQc();
			qc.setAssignedDate(new Date());
			qc.setAssignedTo(new EmployeeDetails(qcToEmailid));
			qc.setJobCard(jobCard);
			qc.setOwner(new EmployeeDetails(qcToEmailid));
			dbQc.saveDetails(qc, user);

			if (serviceOrderItem.getInCustomerInventory() != null) {
				BtCustomerInventoryIn _in = dbCustomerInventoryIn
						.findById(serviceOrderItem.getInCustomerInventory().getId()).get();
				_in.setCustomerInventoryStage("Assigned to QC");
			}
		}
		dbJobCard.updateDetails(jobCard, user);
		// item consumptions

		List<Map<String, Object>> consumedItems = (List<Map<String, Object>>) request.get("consumedItems");
		if (consumedItems != null && !consumedItems.isEmpty()) {

			for (Map<String, Object> item : consumedItems) {
				Long prdId = Long.parseLong(item.get("consumedProductId").toString());
				Float qty = Float.parseFloat(item.get("quantity").toString());
				boolean fromStock = Boolean.parseBoolean(item.get("isFromStock").toString());

				Optional<BtProducts> opProuct = dbProduct.findById(prdId);
				if (!opProuct.isPresent())
					throw new BadRequestException("Invalid id for product consumed");

				BtJobCardProductConsume consume = new BtJobCardProductConsume();
				consume.setProduct(opProuct.get());
				consume.setConsumeFromStock(fromStock);
				consume.setJobCard(jobCard);
				dbProductConsume.addQuantity(consume, qty, user);
			}

		}

		return request;
	}

	@Override
	public List<Map<String, Object>> searchForList(String search, LoginUser user)
			throws BadRequestException, UnauthoriseException {

		return null;
	}

	@Override
	public List<Map<String, Object>> advanceSearch(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		StringBuilder sb = new StringBuilder();

		List<Object> param = new ArrayList<Object>();
		sb.append("SELECT \r\n" + "	 bts.`id` AS serviceOrderId, \r\n"
				+ "	 bts.`manual_id` AS serviceOrderManualId, \r\n" + "	\r\n"
				+ "	 prd.`manual_id` AS productPartcode, \r\n" + "	 prd.`id` AS productId, \r\n"
				+ "	 prd.`product_type` AS productType, \r\n"
				+ "	 prd.`product_description` AS productDescription, \r\n" + "	 titem.`id` AS serviceOrderItemId, \r\n"
				+ "	 titem.`manual_id` AS serviceOrderItemManualId, \r\n"
				+ "	 titem.`customer_inventory` AS isCustomerInventory, \r\n"
				+ "	 titem.`serial_number` serviceOrderItemSerialNumber, \r\n"
				+ "	 titem.`assigned` AS isAssigned, \r\n" + "	 titem.`assigned_returned` AS isAssignedReturned, \r\n"
				+ "	 titem.`r_status` AS returnedStatus, \r\n" + "	 titem.`a_status` AS arriableStatus, \r\n"
				+ "	 titem.`received` AS isReceived, \r\n" + "	 titem.`returned_to_source` AS isReturnedToSource, \r\n"
				+ "	 titem.`assigned_to_email_id` AS assignedToEmailId, \r\n" + "\r\n"
				+ "     aemp.`first_name` AS assignedToFirstName, \r\n"
				+ "	 aemp.`middle_name` AS assignedToMiddleName, \r\n"
				+ "	 aemp.`last_name` AS assignedToLastName, \r\n" + "	 btin.`id` AS brcCaseId, \r\n"
				+ "	 btin.`manual_id` AS brcCaseManualId, \r\n" + "	 btin.`account_name` AS accountName, \r\n" + "\r\n"
				+ "    \r\n" + "	 btin.`account_sap_number` AS accountSapNumber, \r\n" + "\r\n"
				+ "	 crd.`id` AS jobCardId, \r\n" + "	 crd.`manual_id` AS jobCardManualId, \r\n"
				+ "	 crd.`qc_done` AS jobCardIsQcDone, \r\n" + "	 crd.`repair_date` AS jobCardRepariDate, \r\n"
				+ "	 crd.`repair_product_state` AS jobCardRepairProductState, \r\n"
				+ "	 crd.`repared` AS isJobCardRepaired, \r\n" + "	 crd.`return_date` AS jobCardReturnedDate, \r\n"
				+ "	 crd.`returned_to_source` AS jobCardIsReturndToSource, \r\n"
				+ "	 crd.`created_by_email_id` AS jobCardCreatedByEmailId, \r\n"
				+ "	 crd.`last_modified_by_email_id` AS jobCardLastModifiedByEmailId, \r\n"
				+ "	 crd.`assigned_to_email_id` AS jobCardAssignedToEmailId, \r\n"
				+ "	 crd.`service_order_item_id` AS jobCardServiceOrderItemId, \r\n"
				+ "	 crd.battery_ah_aft as batteryAhAft, \r\n" + "	 crd.battery_voltage_aft as batteryVoltageAft,\r\n"
				+ "	 crd.battery_voltage_bef  as batteryVoltageBef, \r\n" + "	 crd.bt_balance_at as btBalanceAt, \r\n"
				+ "	 crd.cell_bledge as cellBledge, \r\n" + "	 crd.cell_vmax as cellVmax, \r\n"
				+ "	 crd.cell_vmin as cellVmin, \r\n" + "	 crd.corrective_action as correctiveAction, \r\n"
				+ "	 crd.eng_remarks as engRemarks, \r\n" + "	 crd.eng_resolution_method as engResolutionMethod, \r\n"
				+ "	 crd.ber_remarks as berRemarks, \r\n" + "	 crd.mat_sorteg as matSorteg, \r\n" + "\r\n"
				+ "	 crd.created_date as jobCardCreatedDate, \r\n"
				+ "	 crd.last_modified_date as jobCardLastModifiedDate, \r\n"
				+ "	 crd.assigned_date as jobCardAssignedDate, \r\n"
				+ "	 cemp.`first_name` AS createdByfirstName, \r\n" + "	 cemp.`last_name` AS createdBylastName, \r\n"
				+ "	 cemp.`middle_name` AS createdBymiddleName, \r\n" + "\r\n"
				+ "	 oemp.`first_name` AS ownerFirstName, \r\n" + "	 oemp.`last_name` AS ownerLastName, \r\n"
				+ "	 oemp.`middle_name` AS ownerMiddleName, \r\n" + "\r\n"
				+ "	 lemp.`first_name` AS lastModifiedByfirstName, \r\n"
				+ "	 lemp.`last_name` AS lastModifiedBylastName, \r\n"
				+ "	 lemp.`middle_name` AS lastModifiedByMiddleName, \r\n"
				+ "	 btin.`challan_number` AS brcCaseChallanNumber,\r\n"
				+ "	 btin.`customer_inventory_stage` AS brcCaseStage, \r\n"
				+ "	 btin.`customer_inventory_status` AS brcCaseStatus \r\n" + "	 FROM `bt_service_order` bts  \r\n"
				+ "	 JOIN `bt_service_order_item` titem ON titem.`service_order_id`=bts.id  \r\n"
				+ "	 JOIN bt_products prd ON prd.id=titem.product_id \r\n"
				+ "	 JOIN location sloc ON sloc.location_id = bts.`source_location_location_id`  \r\n"
				+ "	 JOIN employee_details aemp ON aemp.email_id=titem.`assigned_to_email_id` \r\n"
				+ "     JOIN `bt_customer_inventory_in` btin ON btin.id=titem.`in_customer_inventory_id`\r\n"
				+ "     JOIN bt_job_card crd on crd.id=titem.id \r\n"
				+ "	 JOIN employee_details cemp on cemp.email_id = crd.created_by_email_id \r\n"
				+ "	 JOIN employee_details oemp on oemp.email_id = crd.owner_email_id \r\n"
				+ "	 JOIN employee_details lemp on lemp.email_id = crd.last_modified_by_email_id " 
				+ " where 1=1 ");

		if (!dbProfileRepo.hasObjectAccess(user.getUsername(), BtJobCard.class,
				UserProfileRepository.VIEW_ALL)) {
			if (dbProfileRepo.hasObjectAccess(user.getUsername(), BtJobCard.class,
					UserProfileRepository.VIEW)) {
				List<String> userList = dbUserProfile.getListOfUserAndChildByUser(user.getUsername());
				String users = "";
				for (int i = 0; i < userList.size(); i++) {
					if (i > 0) {
						users += ",";
					}
					users += "'" + userList.get(i) + "'";
					System.out.println(users);
				}
				sb.append(" and (crd.assigned_to_email_id in (" + users + "))");
			}
		}

		if (request.get("jobCardManualId") != null && !request.get("jobCardManualId").toString().equalsIgnoreCase("")) {
			sb.append(" and crd.manual_id like ? ");
			param.add(prepareLikeStatement(request.get("jobCardManualId")));
		}
		if (request.get("serviceOrderManualId") != null
				&& !request.get("serviceOrderManualId").toString().equalsIgnoreCase("")) {
			sb.append(" and bts.manual_id like ? ");
			param.add(prepareLikeStatement(request.get("serviceOrderManualId")));
		}
		if (request.get("assignTo") != null && !request.get("assignTo").toString().equalsIgnoreCase("")) {
			sb.append(" AND CONCAT(COALESCE(aemp.first_name,''),COALESCE(aemp.middle_name,''),' ',COALESCE(aemp.last_name,'')) LIKE ? ");
			param.add(prepareLikeStatement(request.get("assignTo")));
		}
		if (request.get("customerChallanNumber") != null
				&& !request.get("customerChallanNumber").toString().equalsIgnoreCase("")) {
			sb.append(" and btin.challan_number like ? ");
			param.add(prepareLikeStatement(request.get("customerChallanNumber")));
		}
		if (request.get("serailNo") != null
				&& !request.get("serailNo").toString().equalsIgnoreCase("")) {
			sb.append(" and titem.`serial_number` like ? ");
			param.add(prepareLikeStatement(request.get("serailNo")));
		}

		return jdbc.queryForList(sb.toString(), param.toArray());
	}

	@Override
	public List<Map<String, Object>> getRepariEngineerList(LoginUser user)
			throws BadRequestException, UnauthoriseException {

		return dbJobCard.getRepariEngineerList(user.getUsername());
	}

	@Override
	@Transactional(rollbackFor = { BadRequestException.class, UnauthoriseException.class, RuntimeException.class,
			Exception.class }, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
	public List<Map<String, Object>> saveObject(List<Map<String, Object>> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if (request != null && !request.isEmpty()) {
			for (Map<String, Object> req : request) {
				saveObject(req, user);
			}
		}
		return null;
	}

	@Override
	public Page<Map<String, Object>> getProductConsumed(Long jobCardId, Pageable page, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if (dbUserProfile.hasObjectAccess(user.getUsername(), BtJobCardProductConsume.class,
				UserProfileRepository.VIEW_ALL))
			return dbJobCard.getProductConsumedPageByJobcardid(jobCardId, page);
		else if (dbUserProfile.hasObjectAccess(user.getUsername(), BtJobCardProductConsume.class,
				UserProfileRepository.VIEW))
			return dbJobCard.getProductConsumedPageByJobcardid(jobCardId, page);
		else
			throw new UnauthoriseException("You are not authorised");
	}

	@Override
	public List<Map<String, Object>> getProductConsumedList(Long jobCardId, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if (dbUserProfile.hasObjectAccess(user.getUsername(), BtJobCardProductConsume.class,
				UserProfileRepository.VIEW_ALL))
			return dbJobCard.getProductConsumedListByJobcardid(jobCardId);
		else if (dbUserProfile.hasObjectAccess(user.getUsername(), BtJobCardProductConsume.class,
				UserProfileRepository.VIEW))
			return dbJobCard.getProductConsumedListByJobcardid(jobCardId);
		else
			throw new UnauthoriseException("You are not authorised");
	}

	@Override
	public Page<Map<String, Object>> getJobCardQcDetails(Long jobCardId, LoginUser user, Pageable page)
			throws BadRequestException, UnauthoriseException {
		if (dbUserProfile.hasObjectAccess(user.getUsername(), BtJobCardQc.class, UserProfileRepository.VIEW_ALL))
			return dbJobCard.getQcPageByJobCardId(jobCardId, page);
		else if (dbUserProfile.hasObjectAccess(user.getUsername(), BtJobCardQc.class, UserProfileRepository.VIEW))
			return dbJobCard.getQcPageByJobCardId(jobCardId, page);
		else
			throw new UnauthoriseException("You are not authorised");
	}

	@Override
	public List<Map<String, Object>> getJobCardQcDetailsList(Long jobCardId, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if (dbUserProfile.hasObjectAccess(user.getUsername(), BtJobCardQc.class, UserProfileRepository.VIEW_ALL))
			return dbJobCard.getQcListByJobCardId(jobCardId);
		else if (dbUserProfile.hasObjectAccess(user.getUsername(), BtJobCardQc.class, UserProfileRepository.VIEW))
			return dbJobCard.getQcListByJobCardId(jobCardId);
		else
			throw new UnauthoriseException("You are not authorised");
	}

	@Override
	public List<Map<String, Object>> getResolutionMethod() {
		// TODO Auto-generated method stub
		return dbJobCard.getResolutionMethod();
	}

	@Override
	public List<Map<String, Object>> getBatteryBalanceAt() {
		// TODO Auto-generated method stub
		return dbJobCard.getBatteryBalanceAt();
	}

	@Override
	public List<Map<String, Object>> getBerRemarks() {
		// TODO Auto-generated method stub
		return dbJobCard.getBerRemarks();
	}
	
	@Override
	public List<Map<String, Object>> getCorrectiveAction() {
		// TODO Auto-generated method stub
		return dbJobCard.getCorrectiveAction();
	}
	
	@Override
	public List<Map<String, Object>> getEngRemarks() {
		// TODO Auto-generated method stub
		return dbJobCard.getEngRemarks();
	}

	@Override
	public Page<Map<String, Object>> getAssignedToMeJobCard(LoginUser user, Pageable page)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return dbJobCard.getAllPageByUserId(user.getUsername(), page);
	}

	@Override
	public Page<Map<String, Object>> getAssignedToMeJobCardPending(LoginUser user, Pageable page)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return dbJobCard.getAllPageByUserIdPending(user.getUsername(), page);
	}

	@Override
	public Page<Map<String, Object>> getAssignedToMeRejected(LoginUser user, Pageable page)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return dbJobCard.getAllPageByUserIdPendingRejected(user.getUsername(), page);
	}

	String prepareLikeStatement(Object obj) {
		return "%" + obj + "%";
	}

	@Override
	public Page<Map<String, Object>> getSubmitToQc(LoginUser user, Pageable page) {
		// TODO Auto-generated method stub
		return dbJobCard.getAllPageByUserIdSubmitToQc(user.getUsername(),page);
	}

	@Override
	@Transactional(rollbackFor = { BadRequestException.class, UnauthoriseException.class, RuntimeException.class,
			Exception.class }, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
	public Map<String, Object> jobCardReassign(Long jobCardId,Map<String, Object> req, LoginUser user) throws BadRequestException, UnauthoriseException {
		
		if (!dbUserProfile.hasObjectAccess(user.getUsername(), BtJobCard.class, UserProfileRepository.EDIT))
			throw new UnauthoriseException("You are not authorised");
		
		BtJobCard btJobCard=null;
		if(jobCardId==null || jobCardId.toString().equalsIgnoreCase(""))
			throw new BadRequestException("Job card not exist");
		
		 Optional<BtJobCard> jobCardpresent= dbJobCard.findById(jobCardId);
		if(!jobCardpresent.isPresent())
			throw new BadRequestException("Job card not exist");
		
		btJobCard= jobCardpresent.get();
		
		if(!user.getUsername().equalsIgnoreCase(btJobCard.getAssignedTo().toString()))
			throw new BadRequestException("Only "+btJobCard.getAssignedTo().toString()+" can reassign");
		
			System.out.println(req.get("reAssignTo"));
		if(req.get("reAssignTo")==null || req.get("reAssignTo").toString().equalsIgnoreCase("")) 
			throw new BadRequestException("Repair engineer not found");
		
		if(btJobCard.getRepared())
			throw new BadRequestException("Job Card already submitted for QC");
		
		if(req.get("reAssignTo").toString().equalsIgnoreCase(btJobCard.getAssignedTo().toString()))
			throw new BadRequestException("You can't reassign yourself");
		
		Optional<BtServiceOrderItem> optionalServiceOrderItem= dbServiceOrderItem.findById(Long.valueOf(btJobCard.getServiceOrderItem().toString()));
		if (!optionalServiceOrderItem.isPresent())
			throw new BadRequestException("Invalid service order item id");
		
		BtServiceOrderItem serviceOrderItem = optionalServiceOrderItem.get();
		serviceOrderItem.setAssignedTo(new EmployeeDetails(req.get("reAssignTo").toString()));
		dbServiceOrderItem.updateDetails(serviceOrderItem, user);
		
		ServiceRecource presentServiceRes=  dbResource.getByEmail(btJobCard.getAssignedTo().toString());
		if(presentServiceRes==null)
			throw new BadRequestException(btJobCard.getAssignedTo().toString()+" service resource not found");
			
		ServiceRecource assignedServiceRes= dbResource.getByEmail(req.get("reAssignTo").toString());
		if(assignedServiceRes==null)
			throw new BadRequestException(req.get("reAssignTo").toString()+" service resource not found");
		
		btJobCard.setAssignedTo(new EmployeeDetails(req.get("reAssignTo").toString()));
		btJobCard.setOwner(new EmployeeDetails(req.get("reAssignTo").toString()));
		
		dbJobCard.updateDetails(btJobCard, user);
		
		
		dbStock.subQuantity(serviceOrderItem.getProduct(), presentServiceRes.getLocation(), 1F,
				serviceOrderItem.getCustomerInventory(), serviceOrderItem.getaStatus(), user);
		dbStock.addQuantity(serviceOrderItem.getProduct(), assignedServiceRes.getLocation(), 1F,
				serviceOrderItem.getCustomerInventory(), serviceOrderItem.getaStatus(), user);
		
		return null;
	}
}
