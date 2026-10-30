package com.pratikshat.exicom.repository;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.config.FileStorageProperties;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.response.ExpenseManagementResponse;
import com.pratikshat.exicom.model.response.LocationProductRequestResponse;
import com.pratikshat.exicom.model.response.LocationProductTransferItemResponse;
import com.pratikshat.exicom.model.response.LocationProductTransferLineItemResponse;
import com.pratikshat.exicom.utils.ExpensePDFGeneration;
import com.pratikshat.exicom.utils.MaterialTransferPackingList;

public class LpTransferRepositoryCustomeImpl implements LpTransferRepositoryCustome{

	@javax.persistence.PersistenceContext
	javax.persistence.EntityManager em;
	
	@Autowired
	FileStorageProperties fileStorageProperties;
	
	@Autowired
	JdbcTemplate jdbc;
	
	@Override
	public List<LocationProductTransferItemResponse> advanceSearch(Map<String, Object> filterData, LoginUser user, List<Long> childLoc)throws ParseException {
			StringBuilder queryStringBuilder=new StringBuilder();
			queryStringBuilder.append("select new com.pratikshat.exicom.model.response.LocationProductTransferItemResponse(item) from LPTransferItem item join item.product prd join item.lpTranfer trn join trn.createdBy cby left join trn.modifiedBy mby join trn.locationProductRequest preq join preq.sourceLocation sl join preq.destinationLocation dl join item.lpRequestLineItem ritem where 1=1 ");
			if(filterData.get("manualId")!=null&&!filterData.get("manualId").equals(""))
				queryStringBuilder.append(" and item.lpTransferItemIdManualId like :manualId");
			if(filterData.get("partcode")!=null&&!filterData.get("partcode").equals(""))
				queryStringBuilder.append(" and prd.productPartCode like :partcode");
			if(filterData.get("productDescription")!=null&&!filterData.get("productDescription").equals(""))
				queryStringBuilder.append(" and prd.pdoductDescription like :productDescription");
			if(filterData.get("sourceLocation")!=null&&!filterData.get("sourceLocation").equals(""))
				queryStringBuilder.append(" and sl.locationName like :sourceLocation");
			if(filterData.get("destinationLocation")!=null&&!filterData.get("destinationLocation").equals(""))
				queryStringBuilder.append(" and dl.locationName like :destinationLocation");
			if(childLoc!=null)
				queryStringBuilder.append(" and (sl.locationId in :childsLoc or dl.locationId in :childsLoc)");
			
			Query qr=em.createQuery(queryStringBuilder.toString(),LocationProductTransferItemResponse.class);
			
			
			if(filterData.get("manualId")!=null&&!filterData.get("manualId").equals(""))
				qr.setParameter("manualId","%"+ filterData.get("manualId")+"%");
			if(filterData.get("partcode")!=null&&!filterData.get("partcode").equals(""))
				qr.setParameter("partcode", "%"+ filterData.get("partcode")+"%");
			if(filterData.get("productDescription")!=null&&!filterData.get("productDescription").equals(""))
				qr.setParameter("productDescription", "%"+ filterData.get("productDescription")+"%");
			if(filterData.get("sourceLocation")!=null&&!filterData.get("sourceLocation").equals(""))
				qr.setParameter("sourceLocation", "%"+ filterData.get("sourceLocation")+"%");
			if(filterData.get("destinationLocation")!=null&&!filterData.get("destinationLocation").equals(""))
				qr.setParameter("destinationLocation", "%"+ filterData.get("destinationLocation")+"%");
			
			if(childLoc!=null) {
				List<Long> emps=new ArrayList<Long>();
				for(Long loc:childLoc) {
					emps.add(loc);
				}
				qr.setParameter("childsLoc", emps);
			}
			return qr.getResultList();
		}

	@Override
	public List<LocationProductTransferLineItemResponse> getLineItemsByTransferItemIdAdvanceSearch(long transferId,
			Map<String, Object> request) throws ParseException {
		
		StringBuilder queryStringBuilder=new StringBuilder();
		queryStringBuilder.append("select new com.pratikshat.exicom.model.response.LocationProductTransferLineItemResponse(item.lpTransferItemId,lineItem.lpTransferLineitemId,lineItem.lpTransferLineitemIdManualId,prd.productPartCode,prd.pdoductDescription,prd.productType,lineItem.productSerialNumber,lineItem.received) from LPTransferLineItem lineItem join lineItem.transferItem item join item.product prd where 1=1");
		
		queryStringBuilder.append(" and item.lpTransferItemId=:transferId");
		
		if(request.get("serialNumber")!=null&&!request.get("serialNumber").equals(""))
			queryStringBuilder.append(" and lineItem.productSerialNumber like :serialNumber");
		
		if(request.get("partcode")!=null&&!request.get("partcode").equals(""))
			queryStringBuilder.append(" and prd.productPartCode like :partcode");
		
		if(request.get("productDescription")!=null&&!request.get("productDescription").equals(""))
			queryStringBuilder.append(" and prd.pdoductDescription like :productDescription");
		
		if(request.get("productType")!=null&&!request.get("productType").equals(""))
			queryStringBuilder.append(" and prd.productType like :productType");
		
		
		Query qr=em.createQuery(queryStringBuilder.toString(),LocationProductTransferLineItemResponse.class);
		
		qr.setParameter("transferId",transferId);
		
		if(request.get("serialNumber")!=null&&!request.get("serialNumber").equals(""))
			qr.setParameter("serialNumber","%"+ request.get("serialNumber")+"%");
		
		if(request.get("partcode")!=null&&!request.get("partcode").equals(""))
			qr.setParameter("partcode", "%"+ request.get("partcode")+"%");
		
		if(request.get("productDescription")!=null&&!request.get("productDescription").equals(""))
			qr.setParameter("productDescription", "%"+ request.get("productDescription")+"%");
		
		if(request.get("productType")!=null&&!request.get("productType").equals(""))
			qr.setParameter("productType", "%"+ request.get("productType")+"%");
		
		
		return qr.getResultList();
	}
	
	
	@Transactional(isolation=Isolation.READ_UNCOMMITTED,rollbackFor = {Exception.class,BadRequestException.class,UnauthoriseException.class,RuntimeException.class},propagation = Propagation.REQUIRES_NEW)
	@Override
	public ByteArrayInputStream generatePackingListPdf(LoginUser user, long id, String type) throws Exception {
		
		MaterialTransferPackingList packingListPdf = new MaterialTransferPackingList(em, fileStorageProperties);
		return packingListPdf.createPdf(user, id, type);
	}

	@Override
	public List<Map<String, Object>> productTransferAdvanceSearch(Map<String, Object> request, LoginUser user,
			List<Long> locationIds) throws ParseException {
		
		StringBuilder queryStringBuilder=new StringBuilder();
		List<Object> value = new ArrayList<>();
		queryStringBuilder.append("SELECT \r\n" + 
				"trf.`location_product_transfer_id` AS `id`,\r\n" + 
				"trf.`location_product_transfer_manual_id` AS `manualId`,\r\n" + 
				"sl.`location_id` AS `sourceLocationId`,\r\n" + 
				"sl.`location_name` AS `sourceLocationName`,\r\n" + 
				"dl.`location_id` AS `destinationLocationId`,\r\n" + 
				"dl.`location_name` AS `destinationLocationName`,\r\n" + 
				"trf.`actual_delivery_date` AS `deliveryDate`,\r\n" + 
				"trf.`shipping_mode` AS `shippingMode`,\r\n" + 
				"trf.`shipping_to_name` AS `shippingToName`,\r\n" + 
				"trf.`tracking_number` AS `trackingNumber`,\r\n" + 
				"trf.`received_date` AS `receivedDate`,\r\n" + 
				"trf.`location_product_request_location_product_request_id` AS `productRequestId`,\r\n" + 
				"pr.`location_product_request_manual_id` AS `productRequestManualId`,\r\n" + 
				"qty.transferQty,\r\n" + 
				"qty.receivedQty,\r\n" + 
				"(CASE\r\n" + 
				"	WHEN qty.transferQty!=0 AND qty.receivedQty!=0 AND qty.transferQty=qty.receivedQty THEN 'Received'\r\n" + 
				"	WHEN qty.transferQty!=0 AND qty.receivedQty!=0 AND qty.transferQty>qty.receivedQty THEN 'Partially Received'\r\n" + 
				"	WHEN qty.receivedQty=0 THEN 'Pendig to Received'\r\n" + 
				"END) AS `status`,\r\n" + 
				"trf.`created_date` AS `createdDate`,\r\n" + 
				"trf.`modified_date` AS `modifiedDate`,\r\n" + 
				"trf.`created_by_email_id` AS `createdByEmailId`,\r\n" + 
				"CONCAT(CONCAT(cby.`first_name`,' '),IFNULL(CONCAT(cby.`middle_name`,' '),''),cby.`last_name`) AS `createdByName`,\r\n" + 
				"trf.`modified_by_email_id` AS `modifiedByEmailId`,\r\n" + 
				"CONCAT(CONCAT(mby.`first_name`,' '),IFNULL(CONCAT(mby.`middle_name`,' '),''),mby.`last_name`) AS `modifiedByName`,\r\n" + 
				"trf.`owner_email_id` AS `ownerEmailId`,\r\n" + 
				"CONCAT(CONCAT(onr.`first_name`,' '),IFNULL(CONCAT(onr.`middle_name`,' '),''),onr.`last_name`) AS `ownerName`,\r\n" + 
				"trf.`deliv`,\r\n" + 
				"trf.`invoice`,\r\n" + 
				"trf.`mblnr_g`,\r\n" + 
				"trf.`mjahr_g`,\r\n" + 
				"trf.`remark_d`,\r\n" + 
				"trf.`remark_g`,\r\n" + 
				"trf.`remark_i`\r\n" + 
				"FROM `lptrasfer` trf\r\n" + 
				"JOIN `lproduct_request` pr ON pr.`location_product_request_id`=trf.`location_product_request_location_product_request_id`\r\n" + 
				"JOIN `employee_details` cby ON cby.`email_id`=trf.`created_by_email_id`\r\n" + 
				"JOIN `employee_details` mby ON mby.`email_id`=trf.`modified_by_email_id`\r\n" + 
				"JOIN `employee_details` onr ON onr.`email_id`=trf.`owner_email_id`\r\n" + 
				"JOIN `location` sl ON sl.`location_id`=pr.`source_location_location_id`\r\n" + 
				"JOIN `location` dl ON dl.`location_id`=pr.`destination_location_location_id`\r\n" +
				"LEFT JOIN \r\n" + 
				"(SELECT\r\n" + 
				"	SUM(item.`transfer_quantity`) AS `transferQty`,\r\n" + 
				"	SUM(item.`received_quantity`) AS `receivedQty`,\r\n" + 
				"	item.`lp_tranfer_location_product_transfer_id`\r\n" + 
				"	FROM `lptransfer_item` item\r\n" + 
				"	GROUP BY  item.`lp_tranfer_location_product_transfer_id`\r\n" + 
				") AS `qty`\r\n" + 
				"ON qty.`lp_tranfer_location_product_transfer_id`=trf.`location_product_transfer_id`\r\n" + 
				"WHERE pr.`location_product_request_status`!='Archived'\r\n");
		
		if(request.get("manualId")!=null&&!request.get("manualId").equals("")) {
			queryStringBuilder.append(" and trf.`location_product_transfer_manual_id` like ?");
			value.add("%"+request.get("manualId")+"%");
		}
		if(request.get("sourceLocation")!=null&&!request.get("sourceLocation").equals("")) {
			queryStringBuilder.append(" and sl.`location_name` like ?");
			value.add("%"+request.get("sourceLocation")+"%");
		}
			
		if(request.get("destinationLocation")!=null&&!request.get("destinationLocation").equals("")) {
			queryStringBuilder.append(" and dl.`location_name` like ?");
			value.add("%"+request.get("destinationLocation")+"%");
		}
		
		if(request.get("deliveryDate")!=null&&!request.get("deliveryDate").equals("")) {
			queryStringBuilder.append(" and  date_format(trf.`actual_delivery_date`,'%Y-%m-%d')= date_format(?,'%Y-%m-%d')");
			value.add(request.get("deliveryDate"));
		}
		
		if(request.get("receivedDate")!=null&&!request.get("receivedDate").equals("")) {
			queryStringBuilder.append(" and date_format(trf.`received_date`,'%Y-%m-%d')= date_format(?,'%Y-%m-%d')");
			value.add(request.get("receivedDate"));
			
		}
		
		if(request.get("productRequestManualId")!=null&&!request.get("productRequestManualId").equals("")) {
			queryStringBuilder.append(" and pr.`location_product_request_manual_id` like ?");
			value.add("%"+request.get("productRequestManualId")+"%");
		}
			
		if(request.get("status")!=null&&!request.get("status").equals("")) {
			if(request.get("status").toString().equalsIgnoreCase("Received")) {
				queryStringBuilder.append(" and qty.transferQty!=0 AND qty.receivedQty!=0 AND qty.transferQty=qty.receivedQty");
			}else if(request.get("status").toString().equalsIgnoreCase("Partially Received")) {
				queryStringBuilder.append(" and qty.transferQty!=0 AND qty.receivedQty!=0 AND qty.transferQty>qty.receivedQty");
			}else if(request.get("status").toString().equalsIgnoreCase("Pendig to Received")) {
				queryStringBuilder.append(" and qty.receivedQty=0");
			}
		}
			
		if(locationIds!=null) {
			queryStringBuilder.append(" AND (sl.`location_id` IN ");
			
			StringBuilder locations=new StringBuilder();
			int i=0;
			for(Long loc:locationIds) {
				if(i!=0)
					locations.append(",");
				locations.append("'"+loc+"'");
				i++;
			}
			queryStringBuilder.append("("+locations.toString()+")");
			
			queryStringBuilder.append(" OR dl.`location_id` IN ");
			
			queryStringBuilder.append("("+locations.toString()+"))");
		}
					
		return jdbc.queryForList(queryStringBuilder.toString(),value.toArray());
	}
}
