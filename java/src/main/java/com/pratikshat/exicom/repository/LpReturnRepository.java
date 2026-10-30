package com.pratikshat.exicom.repository;



import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pratikshat.exicom.entity.LProductReturnOrder;
import com.pratikshat.exicom.entity.LProductReturnOrderItem;
import com.pratikshat.exicom.entity.LProductReturnOrderLineItem;
import com.pratikshat.exicom.entity.Products;

public interface LpReturnRepository extends JpaRepository<com.pratikshat.exicom.entity.LProductReturnOrder, Long>,LpReturnRepositoryCustome {
	@Query("select new com.pratikshat.exicom.model.response.LpReturnOrderResponse(rt) from LProductReturnOrder rt join rt.sourceLocation slo join rt.destinationLocation dslo where rt.locationReturnOrderStatus !='Archived'")
	Page<com.pratikshat.exicom.model.response.LpReturnOrderResponse> getReturnOrders(Pageable page);
	
//	@Query("select new com.pratikshat.exicom.model.response.LpReturnOrderResponse(rt) from LProductReturnOrder rt join rt.sourceLocation slo join rt.destinationLocation dslo where slo.owner in :userlist or dslo.owner in :userlist")
//	Page<com.pratikshat.exicom.model.response.LpReturnOrderResponse> getReturnOrders(@Param("userlist")java.util.List<com.pratikshat.exicom.entity.EmployeeDetails> userList,Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.LpReturnOrderResponse(rt) from LProductReturnOrder rt join rt.sourceLocation slo join rt.destinationLocation dslo where slo.locationId in :ids or dslo.locationId in :ids and rt.locationReturnOrderStatus !='Archived'")
	Page<com.pratikshat.exicom.model.response.LpReturnOrderResponse> getReturnOrders(@Param("ids")java.util.List<Long> ids,Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.LpReturnOrderResponse(rt) from LProductReturnOrder rt join rt.sourceLocation slo join rt.destinationLocation dslo where rt.totalQuantityReturned>rt.totalQuantityReceived and rt.locationReturnOrderStatus !='Archived'")
	Page<com.pratikshat.exicom.model.response.LpReturnOrderResponse> getPendingToReceive(Pageable pge);
	
//	@Query("select new com.pratikshat.exicom.model.response.LpReturnOrderResponse(rt) from LProductReturnOrder rt join rt.sourceLocation slo join rt.destinationLocation dslo where rt.totalQuantityReturned>rt.totalQuantityReceived and (slo.owner in :userlist or dslo.owner in :userlist)")
//	Page<com.pratikshat.exicom.model.response.LpReturnOrderResponse> getPendingToReceive(@Param("userlist")java.util.List<com.pratikshat.exicom.entity.EmployeeDetails> userList,Pageable pge);
	
	@Query("select new com.pratikshat.exicom.model.response.LpReturnOrderResponse(rt) from LProductReturnOrder rt join rt.sourceLocation slo join rt.destinationLocation dslo where  rt.locationReturnOrderStatus !='Archived' and rt.totalQuantityReturned>rt.totalQuantityReceived and (slo.locationId in :ids or dslo.locationId in :ids)")
	Page<com.pratikshat.exicom.model.response.LpReturnOrderResponse> getPendingToReceive(@Param("ids")java.util.List<Long> ids,Pageable pge);
	
	@Query("select new com.pratikshat.exicom.model.response.LpReturnOrderResponse(rt) from LProductReturnOrder rt join rt.sourceLocation slo join rt.destinationLocation dslo join ServiceRecource sr on sr.location=dslo where rt.locationReturnOrderStatus !='Archived' and rt.totalQuantityReturned>rt.totalQuantityReceived and sr.user=:owner")
	Page<com.pratikshat.exicom.model.response.LpReturnOrderResponse> getMyPendingToReceive(@Param("owner")com.pratikshat.exicom.entity.EmployeeDetails user,Pageable apge);
	
	@Query("select new com.pratikshat.exicom.model.response.LpReturnOrderResponse(rt) from LProductReturnOrder rt join rt.sourceLocation slo join rt.destinationLocation dslo join ServiceRecource sr on sr.location=slo where rt.locationReturnOrderStatus !='Archived' and sr.user=:owner")
	Page<com.pratikshat.exicom.model.response.LpReturnOrderResponse> getSendByMe(@Param("owner")com.pratikshat.exicom.entity.EmployeeDetails user,Pageable apge);
	
	@Query("select new com.pratikshat.exicom.model.response.LpReturnOrderResponse(rt) from LProductReturnOrder rt join rt.sourceLocation slo join rt.destinationLocation dslo join ServiceRecource sr on sr.location=dslo where rt.locationReturnOrderStatus !='Archived' and sr.user=:owner")
	Page<com.pratikshat.exicom.model.response.LpReturnOrderResponse> getSendForMe(@Param("owner")com.pratikshat.exicom.entity.EmployeeDetails user,Pageable apge);
	
	@Query("select new com.pratikshat.exicom.model.response.LpReturnOrderResponse(rt) from LProductReturnOrder rt join rt.sourceLocation slo join rt.destinationLocation dslo left join rt.owner orn left join rt.createdBy cr left join rt.modifiedBy md where rt.locationReturnOrderStatus !='Archived' and rt.lprOrderid=:returnOrderId")
	com.pratikshat.exicom.model.response.LpReturnOrderResponse getReturnOrder(@Param("returnOrderId")long returnOrderId);
	@Query("select new com.pratikshat.exicom.model.response.LpReturnOrderItemResponse(item.lproItemid,item.lproItemManualId,item.totalReturnQuantity,item.totalReceiveQuantity,litem.id,litem.locationProductItemId,rord.lprOrderid,prd.productPartCode,prd.pdoductDescription) from LProductReturnOrderItem item join item.locationProductReturnOrder rord join item.productItem litem join item.product prd where rord.locationReturnOrderStatus !='Archived' and item.totalReturnQuantity>item.totalReceiveQuantity and rord.id=:returnOrderId")
	java.util.List<com.pratikshat.exicom.model.response.LpReturnOrderItemResponse> getPendingItemToReceive(@Param("returnOrderId")long returnOrderId);
	@Query("select new com.pratikshat.exicom.model.response.LpReturnLineItemResponse(litem.lproLineitemId,litem.lproLineitemManualId,item.lproItemid,item.lproItemManualId,litem.productLineItemSerialNumber,false) from LProductReturnOrderLineItem litem join litem.returnOrderItem item where litem.quantityReceived!=1 and item.lproItemid=:lproItemid")
	java.util.List<com.pratikshat.exicom.model.response.LpReturnLineItemResponse> getPendingLineItemToReceive(@Param("lproItemid")long returnItemId);
	
	@Query("select new com.pratikshat.exicom.model.response.LpReturnOrderItemResponse(item.lproItemid,item.lproItemManualId,item.totalReturnQuantity,item.totalReceiveQuantity,litem.id,litem.locationProductItemId,rord.lprOrderid,prd.productPartCode,prd.pdoductDescription) from LProductReturnOrderItem item join item.locationProductReturnOrder rord join item.productItem litem join item.product prd where rord.locationReturnOrderStatus !='Archived' and rord.id=:returnOrderId")
	Page<com.pratikshat.exicom.model.response.LpReturnOrderItemResponse> getItem(@Param("returnOrderId")long returnOrderId,Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.LpReturnLineItemResponse(litem.lproLineitemId,litem.lproLineitemManualId,item.lproItemid,item.lproItemManualId,litem.productLineItemSerialNumber,litem.quantityReceived=1) from LProductReturnOrderLineItem litem join litem.returnOrderItem item where item.lproItemid=:lproItemid")
	java.util.List<com.pratikshat.exicom.model.response.LpReturnLineItemResponse> getLineItem(@Param("lproItemid")long returnItemId);
	
	@Query("select new com.pratikshat.exicom.model.response.LpReturnLineItemResponse(litem.lproLineitemId,"
			+ "litem.lproLineitemManualId,"
			+ "item.lproItemid,"
			+ "item.lproItemManualId,"
			+ "litem.productLineItemSerialNumber,"
			+ "litem.quantityReceived=1,"
			+ "cby.firstName,"
			+ "cby.middleName,"
			+ "cby.lastName,"
			+ "cby.emailId,"
			+ "litem.createdDate"
			+ ",mby.firstName,"
			+ "mby.middleName,"
			+ "mby.lastName,"
			+ "mby.emailId,"
			+ "litem.modifiedDate) from LProductReturnOrderLineItem litem join litem.returnOrderItem item left join litem.createdBy cby left join litem.modifiedBy mby where litem.lproLineitemId=:lineItemId")
	com.pratikshat.exicom.model.response.LpReturnLineItemResponse getLineItemById(@Param("lineItemId")long lineItemId);
	
	
	@Query(value="SELECT litem.lpro_lineitem_id AS returnOrderLineItemId,\r\n" + 
			"litem.lpro_lineitem_manual_id AS returnOrderLineItemManualId, \r\n" + 
			"prd.product_part_code AS returnOrderProductPartcode,\r\n" + 
			"prd.pdoduct_description AS returnOrderProductDescription,\r\n" + 
			"litem.product_line_item_serial_number AS returnOrderProductSerialNumber,\r\n" + 
			"CASE WHEN litem.quantity_received>=litem.quantity_returned THEN TRUE ELSE FALSE END AS received\r\n" + 
			"FROM `lproduct_return_order_line_item` litem JOIN `lproduct_return_order_item` item ON litem.`return_order_item_lpro_itemid`=item.`lpro_itemid` JOIN `products` prd ON prd.`product_part_code`=item.`product_product_part_code` WHERE item.`location_product_return_order_lpr_orderid`=:returnOrderId",
			countQuery="SELECT count(*) \r\n"+ 
					"FROM `lproduct_return_order_line_item` litem JOIN `lproduct_return_order_item` item ON litem.`return_order_item_lpro_itemid`=item.`lpro_itemid` JOIN `products` prd ON prd.`product_part_code`=item.`product_product_part_code` WHERE item.`location_product_return_order_lpr_orderid`=:returnOrderId",nativeQuery = true)
	Page<Map<String,Object>> getItemNative(@Param("returnOrderId")long returnOrderId,Pageable page);
	
	@Query("select litem from LProductReturnOrderLineItem litem join litem.returnOrderItem item join item.locationProductReturnOrder ro where ro.locationReturnOrderStatus !='Archived' and item.product=:product and litem.productLineItemSerialNumber=:serialnumber and litem.quantityReceived=:quantityReceived")
	public LProductReturnOrderLineItem findByProductLineItemSerialNumberAndLocationProductReturnOrder_productAndQuantityReceived(@Param("product")Products product,@Param("serialnumber")String itemSerialNumber,@Param("quantityReceived")float quantityReceived);
	
	
	@Query("select litem from LProductReturnOrderLineItem litem join litem.returnOrderItem item join item.locationProductReturnOrder ro where ro.locationReturnOrderStatus !='Archived' and item.product=:product and litem.productLineItemSerialNumber=:serialnumber and litem.quantityReceived=:quantityReceived")
	public List<LProductReturnOrderLineItem> findByProductLineItemSerialNumberAndLocationProductReturnOrder_productAndQuantityReceivedReceived(@Param("product")Products product,@Param("serialnumber")String itemSerialNumber,@Param("quantityReceived")float quantityReceived);
	
	@Query("select item from LProductReturnOrderItem item join item.locationProductReturnOrder ro where ro.locationReturnOrderStatus !='Archived' and item.locationProductReturnOrder=:ro and item.product=:product")
	public LProductReturnOrderItem findByLocationProductReturnOrderAndProduct(@Param("ro")LProductReturnOrder ro,@Param("product")Products product);
}
