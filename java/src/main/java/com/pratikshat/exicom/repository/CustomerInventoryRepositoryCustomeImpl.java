package com.pratikshat.exicom.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.Accounts;
import com.pratikshat.exicom.entity.Assets;
import com.pratikshat.exicom.entity.CircleDetails;
import com.pratikshat.exicom.entity.CustomerInventory;
import com.pratikshat.exicom.entity.CustomerInventoryOut;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.Location;
import com.pratikshat.exicom.entity.LocationProductItem;
import com.pratikshat.exicom.entity.LocationProductLineItems;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.ProductLineItem;
import com.pratikshat.exicom.entity.ProductLineItemProductState;
import com.pratikshat.exicom.entity.ProductLineItemStatus;
import com.pratikshat.exicom.entity.Products;
import com.pratikshat.exicom.entity.ServiceRecource;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.CustomerInventoryOutWardRequest;
import com.pratikshat.exicom.model.request.CustomerInventoryRequest;

@Repository
public class CustomerInventoryRepositoryCustomeImpl implements CustomerInventoryRepositoryCustome {
	@PersistenceContext
	EntityManager em;
	
	@Autowired
	ProductLineItemRepository dbProductLineItem;

	@Autowired 
	JdbcTemplate jdbc;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class})
	public CustomerInventoryRequest doInward(CustomerInventoryRequest req, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		ProductLineItem pLineItem=null;
		/*
		 * INITIAL VALIDATION
		 * */
		if(req.getCircle()==null||req.getCircle().equalsIgnoreCase(""))
			throw new BadRequestException("Invalid Circle Details");
		if(req.getAccountSapNumber()==null||req.getAccountSapNumber().trim().equalsIgnoreCase(""))
			throw new BadRequestException("Account not found");
		if(req.getProductPartCode()==null||req.getProductPartCode().trim().equalsIgnoreCase(""))
			throw new BadRequestException("Product part code not found");
		if(req.getProductSerialNumber()==null||req.getProductSerialNumber().trim().equalsIgnoreCase(""))
			throw new BadRequestException("Invalid product serial number "+req.getProductSerialNumber()+" for product"+req.getProductPartCode());
		
		CircleDetails circle=null;
		try {
			circle=em.createQuery("select cr from CircleDetails cr where cr.circleName=?1",CircleDetails.class).setParameter(1, req.getCircle()).getSingleResult();
		}catch(Exception ex) {
			throw new BadRequestException("Invalid Circle Details");
		}
		Accounts act=null;
		try {
			act=em.createQuery("select act from Accounts act where act=?1",Accounts.class).setParameter(1,new Accounts(req.getAccountSapNumber())).getSingleResult();
		}catch(Exception ex) {
			throw new BadRequestException("Account not found");
		}
		Products prd=null;
		try {
			prd=em.createQuery("select prd from Products prd where prd=?1",Products.class).setParameter(1, new Products(req.getProductPartCode())).getSingleResult();
		}catch(Exception ex) {
			throw new BadRequestException("Product part code not found");
		}
		
		pLineItem=dbProductLineItem.findByProductAndSerialNumber(prd, req.getProductSerialNumber().trim());
		if(pLineItem!=null) {
			if(pLineItem.getPositionState().equalsIgnoreCase(ProductLineItemRepository.ATTACHED_TO_ASSETS)||pLineItem.getPositionState().equalsIgnoreCase(ProductLineItemRepository.ASSETS_OUT)) {}
			else
				throw new BadRequestException("Invalid product serial number "+req.getProductSerialNumber()+" for product"+req.getProductPartCode());
		}
		
		//END VALIDATION
		
		Location location=null;
		ServiceRecource sr=null;
		EmployeeDetails emp=new EmployeeDetails(user.getUsername());
		
		Date today=new Date();
		/*
		//ADDITIONAL VALIDATION ON DEMAND BY MANPREET MADAM ON MAIL DATED 02-DEC-2020
		//CHECK FOR ASSETS IS VALID
		
		Assets ast=null;
		try {
			
			ast=em.createQuery("select ast from Assets ast join ast.product prd join ast.account act left join ast.primaryAccount pact where ast.actlSerialNumber=?1",Assets.class).setParameter(1, req.getProductSerialNumber()).getSingleResult();
		
		}catch(NoResultException ex) {
			
			throw new BadRequestException("Assets not found ");
		
		}catch(NonUniqueResultException ex) {
			
		}
		
		if(!ast.getAccount().getSapCustomerNumber().equalsIgnoreCase(req.getAccountSapNumber())) {
			
			throw new BadRequestException("Wrong account for assets serial number "+req.getProductSerialNumber());
		}
		if(!ast.getProduct().getProductPartCode().equalsIgnoreCase(req.getProductPartCode())) {
			
			throw new BadRequestException("Wrong part code for assets serial number "+req.getProductSerialNumber());
		}
		//END OF SPACIAL VALIDATION DEMAND
		 * 
		 */
		//ADDITONAL VALIDATION REMOVED ON DEMAND OF MANPREET MADAM ON 08-JAN-2021
		
		CustomerInventory _checkCustomerInventory=null;
		try {
			_checkCustomerInventory=em.createQuery("select ct from CustomerInventory ct where ct.serialNumber=?1 and ct.product=?2 and ct.returned=?3",CustomerInventory.class)
					.setParameter(1,req.getProductSerialNumber())
					.setParameter(2, new Products( req.getProductPartCode()))
					.setParameter(3, false)
					.getSingleResult();
			throw new BadRequestException(String.format("Part code %s with serial number %s alrady received from customer which has not been returned", req.getProductPartCode(),req.getProductSerialNumber()));
		}catch(NoResultException ex) {
			
		}
		catch(NonUniqueResultException ex) {
			throw new BadRequestException("Invalid request multiple entries for same part code and serial number which has not been returned");
		}
		
		try {
			sr=em.createQuery("select sr from ServiceRecource sr join sr.location lc where sr.user=?1",ServiceRecource.class)
					.setParameter(1, new EmployeeDetails(user.getUsername())).getSingleResult();
		}catch(NoResultException ex) {
			throw new BadRequestException("Service Resource not found or Location not found with your user id");
		}
		catch(NonUniqueResultException ex) {
			throw new BadRequestException("Multiple Service resource attached with this user, Please contact with system admin");
		}
		location=sr.getLocation();
		
		String ownerLocation=location.getOwner().getEmailId();
//		if(!ownerLocation.equalsIgnoreCase(user.getUsername())) {
//			throw new UnauthoriseException("You are not authorised, Your taged Location does not have your ownership");
//		}
		
		LocationProductLineItems litems=null;
		try {
			litems=em.createQuery("select litem from LocationProductLineItems litem join litem.locationProductItem item where item.product=?1 and litem.itemSerialNumber=?2",LocationProductLineItems.class)
					.setParameter(1, new Products(req.getProductPartCode()))
					.setParameter(2,req.getProductSerialNumber().trim())
					.getSingleResult();
		}catch(NoResultException ex) {
			
		}catch(NonUniqueResultException ex) {
			throw new BadRequestException(String.format("No Unique result found for Product %s with Serial number %s",req.getProductPartCode(),req.getProductSerialNumber()));
		}
		
		if(litems!=null) {
			throw new BadRequestException(String.format("Product %s with serial number %s already in stock", req.getProductPartCode(),req.getProductSerialNumber()));
		}
		
		LocationProductItem item=null;
		try {
			item=em.createQuery("select item from LocationProductItem item where item.product=?1 and item.location=?2",LocationProductItem.class)
					.setParameter(1, new Products(req.getProductPartCode()))
					.setParameter(2, location)
					.getSingleResult();
			float faltyQty=item.getFaultyQuantity()+1;
			float customerInv=item.getCustomerInventory()+1;
			float totalInhand=item.getQuantityInOnHand()+1;
			item.setFaultyQuantity(faltyQty);
			item.setCustomerInventory(customerInv);
			item.setQuantityInOnHand(totalInhand);
			item.setModifiedBy(emp);
			item.setModifiedDate(today);
			
			em.persist(item);
		}catch(NoResultException ex) {
			item=new LocationProductItem();
			item.setBerQty(0);
			item.setCreatedBy(emp);
			item.setCreatedDate(today);
			item.setCustomerInventory(1);
			item.setFaultyQuantity(1);
			item.setHslQuantity(0);
			item.setHslStatus("L");
			item.setIssuedQunatity(0);
			item.setLocation(location);
			item.setModifiedBy(emp);
			item.setModifiedDate(today);
			item.setMslQuantity(0);
			item.setMslQuantityStatus("L");
			item.setOkQuantity(0);
			item.setProduct(new Products(req.getProductPartCode()));
			item.setQuantityInOnHand(1);
			item.setQuantityUnitOfMasur("NO");
			item.setRejectedQty(0);
			item.setScrapQty(0);
			em.persist(item);
			String mId=String.format("PI-%s",MiscellaneousUtils.fillZero(String.valueOf(item.getId()), 5));
			item.setLocationProductItemId(mId);
			em.persist(item);
			
		}catch(NonUniqueResultException ex) {
			throw new BadRequestException(String.format("Some thing went wrong with this Product %s and Location %s",req.getProductPartCode(),location.getLocationName()));
		}
		if(item==null) throw new BadRequestException("Some thing went wrong ");
		
		LocationProductLineItems _lineitem=null;
		
		try {
			_lineitem=em.createQuery("select lineItem from LocationProductLineItems lineItem join lineItem.locationProductItem item where item.product=?1 and lineItem.itemSerialNumber=?2",LocationProductLineItems.class)
					.setParameter(1,new Products(req.getProductPartCode()))
					.setParameter(2, req.getProductSerialNumber().trim())
					.getSingleResult();
			throw new BadRequestException(String.format("Invalid Serial Number %s with of product part code %s",req.getProductSerialNumber(),req.getProductPartCode()));
		}catch(NoResultException ex) {
			_lineitem=new LocationProductLineItems();
		}
		catch(NonUniqueResultException ex) {
			throw new BadRequestException(String.format("Multiple Serial Number %s found with product part code %s",req.getProductSerialNumber(),req.getProductPartCode()));
		}
		
		Accounts customer=null;
		try {
			customer=em.createQuery("select act from Accounts act where act=?1",Accounts.class).setParameter(1, new Accounts(req.getAccountSapNumber())).getSingleResult();
		}catch(NoResultException ex) {
			throw new BadRequestException("Customer not found ");
		}
		
		_lineitem.setCreatedBy(emp);
		_lineitem.setCreatedDate(today);
		_lineitem.setCustomerInventory(true);
		_lineitem.setInStock(true);
		_lineitem.setItemSerialNumber(req.getProductSerialNumber().trim());
		_lineitem.setLocationProductLineItemStatus(new ProductLineItemStatus(1));
		_lineitem.setLocationProeuctLineItemState(new ProductLineItemProductState(2));
		_lineitem.setModifiedBy(emp);
		_lineitem.setModifiedDate(today);
		_lineitem.setReplaced(false);
		_lineitem.setReturned(false);
		_lineitem.setCustomerName(customer.getName());
		_lineitem.setCustomerSapNumber(customer.getSapCustomerNumber());
		_lineitem.setRemarks(req.getRedmrks());
		_lineitem.setLotNo(req.getLotNo());
		_lineitem.setLocationProductItem(item);
		
		em.persist(_lineitem);
		
		String _lineItemManualId=String.format("PIL-%s",MiscellaneousUtils.fillZero(String.valueOf(_lineitem.getId()), 5));
		_lineitem.setLocationProductLineItemId(_lineItemManualId);
		em.persist(_lineitem);
		
		if(pLineItem!=null) {
			pLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
			pLineItem.setModifiedDate(new Date());
			pLineItem.setPositionState(ProductLineItemRepository.AT_LOCATION);
			pLineItem.setPosition(String.valueOf(location.getLocationId()));
			pLineItem.setState(new ProductLineItemProductState(2));
			pLineItem.setStatus(new ProductLineItemStatus(1));
			dbProductLineItem.save(pLineItem);
		}
		else {
			pLineItem=new ProductLineItem();
			pLineItem.setCreatedBy(new EmployeeDetails(user.getUsername()));
			pLineItem.setCreatedDate(new Date());
			pLineItem.setProduct(prd);
			pLineItem.setSerialNumber(req.getProductSerialNumber().trim());
			pLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
			pLineItem.setModifiedDate(new Date());
			pLineItem.setPositionState(ProductLineItemRepository.AT_LOCATION);
			pLineItem.setPosition(String.valueOf(location.getLocationId()));
			pLineItem.setState(new ProductLineItemProductState(2));
			pLineItem.setStatus(new ProductLineItemStatus(1));
			pLineItem.setCustomerInventory(true);
			pLineItem.setAccountName(act.getName());
			pLineItem.setAccountSapNumber(act.getSapCustomerNumber());
			pLineItem.setRemarks(req.getRedmrks());
			pLineItem.setLotNo(req.getLotNo());
			dbProductLineItem.save(pLineItem);
		}
		
		CustomerInventory _inv=new CustomerInventory();
		_inv.setClient(act);
		_inv.setPreviousState(new ProductLineItemProductState(2));
		_inv.setProduct(prd);
		_inv.setReceiveDate(req.getReceiveDate());
		_inv.setSerialNumber(req.getProductSerialNumber());
		_inv.setCircle(circle);
		_inv.setCreatedBy(emp);
		_inv.setCustomerChallanDate(req.getCustomerChallanDate());
		_inv.setCustomerChallanNumber(req.getCustomerChallanNumber());
		_inv.setCustomerDocketNumber(req.getCustomerDocketNumber());
		_inv.setModifiedBy(emp);
		_inv.setOwner(emp);
		_inv.setRedmrks(req.getRedmrks());
		_inv.setLotNo(req.getLotNo());
		_inv.setReturned(false);
		_inv.setWarrantyStatus(req.getWarrantyStatus());
		em.persist(_inv);
		req.setId(_inv.getId());
		return req;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class})
	public CustomerInventoryOutWardRequest doOutward(CustomerInventoryOutWardRequest req, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		//CHECK CUSTOMER INVENTORY IS VALID IN CUSTOMER INVENTORY INWARD
		ProductLineItem pLineItem=null;
		CustomerInventory in=null;
		try {
			in=em.createQuery("select inven from CustomerInventory inven where inven.product=?1 and inven.serialNumber=?2 and inven.returned=?3",CustomerInventory.class)
					.setParameter(1, new Products(req.getProductPartcode()))
					.setParameter(2, req.getProductSerialNumber())
					.setParameter(3, false)
					.getSingleResult();
		}
		catch(NoResultException ex) {
			throw new BadRequestException(String.format("Part code %s with serial number %s not found",req.getProductPartcode(),req.getProductSerialNumber()));
		}
		catch(NonUniqueResultException ex) {
			throw new BadRequestException("Invalid request");
		}
		
		//GETING LOCATION PRODUCT LINE ITEMS
		
		LocationProductLineItems lineItem=null;
		LocationProductItem item=null;
		Location location=null;
		
		ServiceRecource sr=null;
		try {
			sr=em.createQuery("select sr from ServiceRecource sr where sr.user=?1",ServiceRecource.class)
					.setParameter(1, new EmployeeDetails(user.getUsername()))
					.getSingleResult();
		}catch(NoResultException ex) {
			
			throw new BadRequestException("Location not found");
		}catch(NonUniqueResultException ex) {
			
			throw new BadRequestException("Multiple location found");
		}
		
		try {
			lineItem=em.createQuery("select lineitem from LocationProductLineItems lineitem join lineitem.locationProductItem item join item.location loc where item.product=?1 and lineitem.itemSerialNumber=?2",LocationProductLineItems.class)
					.setParameter(1, new Products(req.getProductPartcode()))
					.setParameter(2,req.getProductSerialNumber())
					.getSingleResult();
		}catch(NoResultException ex) {
			throw new BadRequestException(String.format("Stock for Part code %s with serial number %s not found in your stock",req.getProductPartcode(),req.getProductSerialNumber()));
		}catch(NonUniqueResultException ex) {
			throw new BadRequestException(String.format("Multiple stock for Part code %s with serial number %s found",req.getProductPartcode(),req.getProductSerialNumber()));
		}
		item=lineItem.getLocationProductItem();
		location=item.getLocation();
		
		//CHECK STOCK OWNER
//		if(!location.getOwner().getEmailId().equalsIgnoreCase(user.getUsername()))
//			throw new UnauthoriseException("You are not authorise");
		
		if(location.getLocationId()!=sr.getLocation().getLocationId())
			throw new UnauthoriseException("You are not authorise");
		
		//CHECK IS IT CUSTOMER INVENTORY
		if(!lineItem.isCustomerInventory())
			throw new BadRequestException(String.format("Part code %s with serial number %s is not customer inventory", req.getProductPartcode(),req.getDispatchChallanNumber()));
		
		long statusId=lineItem.getLocationProeuctLineItemState().getProductStateId();
		ProductLineItemProductState state= lineItem.getLocationProeuctLineItemState();
		
		//SET STOCK BALANCE 
		
		if(statusId==1)
			item.setOkQuantity(item.getOkQuantity()-1);
		if(statusId==2)
			item.setFaultyQuantity(item.getFaultyQuantity()-1);
		if(statusId==3)
			item.setScrapQty(item.getScrapQty()-1);
		if(statusId==4)
			item.setRejectedQty(item.getRejectedQty()-1);
		if(statusId==5)
			item.setBerQty(item.getBerQty()-1);
		
		item.setCustomerInventory(item.getCustomerInventory()-1);
		item.setQuantityInOnHand(item.getQuantityInOnHand()-1);
		
		em.persist(item);
		
		pLineItem=dbProductLineItem.findByProductAndSerialNumber(lineItem.getLocationProductItem().getProduct(), lineItem.getItemSerialNumber());
		if(pLineItem!=null) {
			pLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
			pLineItem.setModifiedDate(new Date());
			pLineItem.setPositionState(ProductLineItemRepository.ASSETS_OUT);
			pLineItem.setPosition(pLineItem.getAccountSapNumber());
			pLineItem.setStatus(new ProductLineItemStatus(3));
			pLineItem.setState(lineItem.getLocationProeuctLineItemState());
		}
		else {
			pLineItem=new ProductLineItem();
			pLineItem.setCreatedBy(new EmployeeDetails(user.getUsername()));
			pLineItem.setCreatedDate(new Date());
			pLineItem.setAccountName(in.getClient().getName());
			pLineItem.setAccountSapNumber(in.getClient().getSapCustomerNumber());
			pLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
			pLineItem.setModifiedDate(new Date());
			pLineItem.setPositionState(ProductLineItemRepository.ASSETS_OUT);
			pLineItem.setPosition(pLineItem.getAccountSapNumber());
			pLineItem.setStatus(new ProductLineItemStatus(3));
			pLineItem.setState(lineItem.getLocationProeuctLineItemState());
			pLineItem.setCustomerInventory(true);
			pLineItem.setProduct(item.getProduct());
			pLineItem.setSerialNumber(lineItem.getItemSerialNumber().trim());
			pLineItem.setLotNo(req.getLotNo());
			pLineItem.setRemarks(req.getRemark());
			dbProductLineItem.save(pLineItem);
		}
		
		em.createQuery("delete from LocationProductLineItems item where item=?1").setParameter(1, lineItem).executeUpdate();
		
		CustomerInventoryOut out=new CustomerInventoryOut();
		out.setCreatedBy(new EmployeeDetails(user.getUsername()));
		out.setDispatchChallanNumber(req.getDispatchChallanNumber());
		out.setDispatchDate(new Date());
		out.setDispatchDocketNumber(req.getDispatchDocketNumber());
		out.setInventory(in);
		out.setModifiedBy(new EmployeeDetails(user.getUsername()));
		out.setOwner(new EmployeeDetails(user.getUsername()));
		out.setPreviousState(state);
		out.setProduct(new Products(req.getProductPartcode()));
		out.setSerialNumber(req.getProductSerialNumber());
		out.setLotNo(req.getLotNo());
		out.setRemark(req.getRemark());
		in.setReturned(true);
		
		em.persist(in);
		em.persist(out);
		
		req.setId(in.getId());
		
		return req;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> advanceSearch(Map<String, Object> searchKeys, LoginUser user, List<String> userLists) throws ParseException {
		
		List<Object> value = new ArrayList<>();
		String _query="SELECT \r\n" + 
				"	custin.`circle_circle_name` AS circle,\r\n" + 
				"	custin.`client_sap_customer_number` customerSapNumber,\r\n" + 
				"	acc.`name` AS customerName,\r\n" + 
				"	custin.`customer_challan_date` AS inChallanDate,\r\n" + 
				"	custin.`customer_challan_number` AS inChallNumber,\r\n" + 
				"	custin.`customer_docket_number` AS inDocketNumber,\r\n" + 
				"	custin.`id` AS inId,\r\n" + 
				"	custin.`previous_state_product_state_id` AS inStateCode,\r\n" + 
				"	instate.`product_state_name` AS inStateName,\r\n" + 
				"	custin.`product_product_part_code` AS productPartcode,\r\n" + 
				"	prd.`pdoduct_description` AS productDescription,\r\n" + 
				"	prd.`product_type` AS productType,\r\n" + 
				"	custin.`receive_date` AS receiveDate,\r\n" + 
				"	custin.`redmrks` AS remarks,\r\n" + 
				"	custin.`returned` AS returnd,\r\n" + 
				"	custin.`return_date` AS returnDate,\r\n" + 
				"	custin.`serial_number` AS productSerialNumebr,\r\n" + 
				"	custin.`warranty_status` AS warrantyStatus,\r\n" + 
				"	custout.`dispatch_challan_number` AS dispatchChallanNumber,\r\n" + 
				"	custout.`dispatch_date` AS dispatchDate,\r\n" + 
				"	custout.`dispatch_docket_number` AS dispatchDocketNumber,\r\n" + 
				"	custout.`previous_state_product_state_id` AS dispatchProductStatus,\r\n" +
				"	custin.`lot_no` AS `lotNo`\r\n"+
				" FROM `customer_inventory` custin \r\n" + 
				"	LEFT JOIN `customer_inventory_out` custout ON custin.`id`=custout.`inventory_id`\r\n" + 
				"	JOIN `accounts` acc ON acc.`sap_customer_number`=custin.`client_sap_customer_number`\r\n" + 
				"	JOIN `products` prd ON custin.`product_product_part_code`=prd.`product_part_code`\r\n" + 
				"	JOIN `product_line_item_product_state` instate ON instate.`product_state_id`=custin.`previous_state_product_state_id`\r\n" + 
				"	LEFT JOIN `product_line_item_product_state` outstate ON outstate.`product_state_id`=custout.`previous_state_product_state_id`"+
				"	WHERE 1=1";
		StringBuilder queryBuilder=new StringBuilder();
		queryBuilder.append(_query);
		if(searchKeys.get("partCode")!=null&&!searchKeys.get("partCode").equals("")) {
			queryBuilder.append(" and custin.`product_product_part_code` like ?");
			value.add("%"+searchKeys.get("partCode")+"%");
			
		}
		if(searchKeys.get("serialNumber")!=null&&!searchKeys.get("serialNumber").equals("")) {
			queryBuilder.append(" and custin.`serial_number` like ?");
			value.add("%"+searchKeys.get("serialNumber")+"%");
		}
		if(searchKeys.get("customerName")!=null&&!searchKeys.get("customerName").equals("")) {
			queryBuilder.append(" and acc.`name` like ?");
			value.add("%"+searchKeys.get("customerName")+"%");
		}
		if(searchKeys.get("circle_name")!=null&&!searchKeys.get("circle_name").equals("")) {
			queryBuilder.append(" and custin.`circle_circle_name` like ?");
			value.add("%"+searchKeys.get("circle_name")+"%");
		}
		
		if(searchKeys.get("remarks")!=null&&!searchKeys.get("remarks").equals("")) {
			queryBuilder.append(" and custin.`redmrks` like ?");
			value.add("%"+searchKeys.get("remarks")+"%");
		}
		if(searchKeys.get("lotNo")!=null&&!searchKeys.get("lotNo").equals("")) {
			queryBuilder.append(" and custin.`lot_no` like ?");
			value.add("%"+searchKeys.get("lotNo")+"%");
		}
		
		if(userLists!=null) {
			queryBuilder.append(" and custin.`owner_email_id` IN ");
			StringBuilder em=new StringBuilder();
			int i=0;
			for(String usr:userLists) {
				if(i!=0)
					em.append(",");
				em.append("'"+usr+"'");
				i++;
			}
			queryBuilder.append("("+em.toString()+")");
		}
		
		return jdbc.queryForList(queryBuilder.toString(),value.toArray());
	}
	
}
