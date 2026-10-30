package com.pratikshat.exicom.repository.custom.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.pratikshat.exicom.entity.BtProductTransferItem;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;
import com.pratikshat.exicom.repository.custom.BtProductTransferItemRepositoryCustom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

public class BtProductTransferItemRepositoryCustomImpl implements BtProductTransferItemRepositoryCustom{


    @PersistenceContext
    EntityManager em;

    @Autowired
    WorkflowExecuter workflow;

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public BtProductTransferItem saveDetails(BtProductTransferItem t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        workflow.executeWorkflow(em, t, "INSERT", "PRE", user);
        em.persist(t);
        workflow.executeWorkflow(em, t, "INSERT", "POST", user);

        return t;
    }

    @Override
    public BtProductTransferItem updateDetails(BtProductTransferItem t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        workflow.executeWorkflow(em, t, "UPDATE", "PRE", user);
        em.merge(t);
        workflow.executeWorkflow( em , t , "UPDATE", "POST", user);
        return t;
    }

    @Override
    public BtProductTransferItem deleteDetails(BtProductTransferItem t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        em.remove(t);
        return t;
    }

    @Override
    public void addTotalIssuedQty(Long btProductTransferItemId, Float qty, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        if(qty==null||qty<0)
            throw new BadRequestException("Invalid quantity");
        BtProductTransferItem item=em.find(BtProductTransferItem.class, btProductTransferItemId);
        item.setTransferQuantity(item.getTransferQuantity()+qty);
        em.merge(item);
        
    }

    @Override
    public void addTotalReceivedQty(Long btProductTransferItemId, Float qty, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        if(qty==null||qty<0)
            throw new BadRequestException("Invalid quantity");
        BtProductTransferItem item=em.find(BtProductTransferItem.class, btProductTransferItemId);

        Float tQty=item.getReceivedQuantity();
        tQty=tQty+qty;
        //if(tQty.floatValue()>item.getTransferQuantity())

        item.setReceivedQuantity(item.getReceivedQuantity()+qty);
        em.merge(item);
    }

    @Override
    public void subTotalIssueQty(Long btProductTransferItemId, Float qty, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        if(qty==null||qty<0)
                throw new BadRequestException("Invalid quantity, Quantity can not have negative value");
        BtProductTransferItem item=em.find(BtProductTransferItem.class, btProductTransferItemId);
        Float tQty=item.getTransferQuantity();
        if(tQty==null||tQty<=0)
                throw new BadRequestException("Invalid quantity, Quantity can not have negative value");
        tQty=tQty-qty;
        if(tQty<0)
            throw new BadRequestException("Invalid quantity, Quantity can not have negative value");
        item.setTransferQuantity(tQty);
        em.merge(item);
        
    }

    @Override
    public void subTotalReceivedQty(Long btProductTransferItemId, Float qty, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        if(qty==null||qty<0){
            throw new BadRequestException("Invalid quantity, Quantity can not have negative value");
        }
        BtProductTransferItem item=em.find(BtProductTransferItem.class, btProductTransferItemId);
        Float tQty=item.getReceivedQuantity();
        if(tQty==null||qty<=0)
            throw new BadRequestException("Invalid quantity, Quantity can not have negative value");
        item.setReceivedQuantity(tQty);
        em.merge(item);
    }

    @Override
    public List<Map<String, Object>> getAdvanceSearchByProductRequestId(Long productRequestId,List<Long> locationIds,
            Map<String, Object> request, LoginUser user) throws BadRequestException, UnauthoriseException {
        StringBuilder sb=new StringBuilder();
        sb.append("SELECT trn.`id` AS productTrnsferId, "
        +" trn.`created_date` AS productTransferCreatedDate, "
        +" trn.`last_modified_date` AS productTransferLastModifiedDate, "
        +" trn.`deliv` AS productTransferSapDeliv, "
        +" trn.`invoice` AS productTransferSapInvocieNumber, "
        +"  trn.`mblnr_g` AS productTransferSapMblnrg, "
        +" trn.`mjahr_g` AS productTransferSapMjahrG, "
        +" trn.`remark_d` AS productTransferRemarkD, "
        +" trn.`remark_g` AS productTraansferRemarkG, "
        +" trn.`remark_i` AS productTransferRemarkI, "
        +" trn.`actual_delivery_date` AS productTransferActualDeliveryDate, "
        +" trn.`manual_id` AS productTransferManualId, "
        +" trn.`received_date` AS productTransferReceivedDate, "
        +" trn.`shipping_mode` AS productTransferShippingMode, "
        +" trn.`shipping_to_name` AS productTransferShippingToName, "
        +" trn.`tracking_number` AS productTranferTrackingNumber, "
        +" trn.`created_by_email_id` AS productTransferCreatedByEmaiilId, "
        +" trn.`last_modified_by_email_id` AS productTransferLastModifiedByEmailId, "
        +" trn.transfer_status AS productTransferStatus, "
        +" trn.`total_quantity_issued` AS productTransferTotalQuantityIssued, "
        +" trn.`total_quantity_received` AS productTransferTotalQuantityReceived,  "
        +" cemp.first_name AS productTransferCreatedByFirstName, "
        +" cemp.`middle_name` AS productTransferCreatedByMiddleName, "
        +" cemp.`last_name` AS productTransferCreatedByLastName, "
        +" lemp.first_name AS productTransferLastModifiedByFirstName, "
        +" lemp.`middle_name` AS productTransferLastModifiedByMiddleName, "
        +" lemp.`last_name` AS productTransferLastModifiedByLastName,  "
        
        +" oemp.first_name AS productTransferOwnerFirstName, "
        +" oemp.`middle_name` AS productTransferOwnerMiddleName, "
        +" oemp.`last_name` AS productTransferOwnerLastName,  "
        +" btp.`manual_id` AS productPartcode, "
        +" btp.`product_type` AS productType, "
        +" btp.`product_description` AS productDescription, "
        +" req.`id` AS productRequestId,   req.`manual_id` AS productRequestManualId, "
        +" sloc.location_name AS productRequestSourceLocationName, "
        +" dloc.location_name AS productRequestDestinationLocationName, "
        +" sloc.location_id AS productRequestSourceLocationId, "
        +" dloc.location_id AS productRequestDestinationLocationId    "
        +" FROM `bt_product_transfer` trn  "
        +" JOIN bt_product_transfer_item trn_item ON trn.id=trn_item.btp_transfer_id "
        +" JOIN bt_product_request_item req_item ON req_item.id=trn_item.btp_request_item_id "
        +" JOIN `bt_product_request` req ON req_item.`bt_product_request_id`=req.`id` "
        +" JOIN `location` sloc ON sloc.location_id=trn.`source_location_location_id` "
        +" JOIN employee_details cemp ON cemp.email_id=trn.`created_by_email_id`  "
        +" JOIN employee_details lemp ON lemp.email_id=trn.`last_modified_by_email_id`  "
        +" JOIN employee_details oemp ON oemp.email_id=trn.`owner_email_id`  "
        +" JOIN location dloc ON dloc.location_id=trn.`destination_location_location_id` "
        +" JOIN bt_products btp ON btp.id=req_item.`bt_product_id`");

        List<Object> param=new ArrayList();
        if(locationIds!=null){
            String locations="";
            for(int i=0;i<locationIds.size();i++){
                if(i>0)
                    locations+=",";
                locations+=locationIds.get(i);
            }
            sb.append(" and dloc.location_id in ("+locations+") or sloc.location_id in ("+locations+")");
        }
        if(request.get("productTransferManualId")!=null&&!request.get("productTransferManualId").toString().equalsIgnoreCase("")){
            sb.append("and trn.`manual_id` like ?");
            param.add(prepareLikeStateMent(request.get("productTransferManualId")));
        }
        if(request.get("productTransferShippingMode")!=null&&!request.get("productTransferShippingMode").toString().equalsIgnoreCase("")){
            sb.append(" and trn.`shipping_mode` like ?");
            param.add(prepareLikeStateMent(request.get("productTransferShippingMode")));
        }
        if(request.get("productTransferShippingToName")!=null&&!request.get("productTransferShippingToName").toString().equalsIgnoreCase("")){
            sb.append(" and trn.`shipping_to_name` like ?");
            param.add(prepareLikeStateMent(request.get("productTransferShippingToName")));
        }
        if(request.get("productTranferTrackingNumber")!=null&&!request.get("productTranferTrackingNumber").toString().equalsIgnoreCase("")){
            sb.append(" and trn.`tracking_number` like ?");
            param.add(prepareLikeStateMent(request.get("productTranferTrackingNumber")));
        }
        if(request.get("productPartcode")!=null&&!request.get("productPartcode").toString().equalsIgnoreCase("")){
            sb.append(" and btp.`manual_id` like ?");
            param.add(prepareLikeStateMent(request.get("productPartcode")));
        }
        if(request.get("productDescription")!=null&&!request.get("productDescription").toString().equalsIgnoreCase("")){
            sb.append(" and btp.`product_description` like ?");
            param.add(prepareLikeStateMent(request.get("productDescription")));
        }

        return jdbc.queryForList(sb.toString(),param.toArray());
    }

    String prepareLikeStateMent(Object data){
        return "%"+data+"%";
    }

	@Override
	public List<Map<String, Object>> getAdvanceSerachByProductTransferId(Long productTransferId, List<Long> locationIds,
			Map<String, Object> request, LoginUser user) throws BadRequestException, UnauthoriseException {
		StringBuilder sb=new StringBuilder();
        sb.append("SELECT titem.`manual_id` AS transferItemManualId,"
        +" titem.`transfer_quantity` AS transferQuantity,"
        +" titem.`received_quantity` AS transferReceivedQuantity,"
        
        +" btp.`manual_id` AS productPartcode,"
        +" btp.`id` AS productId,"
        +" btp.`product_type` AS productType,"
        +" btp.`product_description` AS productDescription,"
        
        +" reqitem.`manual_id` AS productRequestItemManualId,"
        +" reqitem.id AS productRequestItemId,"
        +" req.`id` AS productRequestId,"
        +" req.`manual_id` AS productRequestManualId"
        
        +" FROM `bt_product_transfer_item` titem "
        +" JOIN `bt_product_transfer` trn ON trn.id=titem.`btp_transfer_id`"
        +" JOIN `bt_product_request_item` reqitem ON reqitem.id=titem.`btp_request_item_id`"
        +" JOIN `bt_products` btp ON btp.`id`=reqitem.`bt_product_id`"
        +" JOIN `bt_product_request` req ON req.id=reqitem.`bt_product_request_id` WHERE trn.id=? ");
        List<Object> param=new ArrayList<Object>();

        param.add(productTransferId);

        if(locationIds!=null){
            String locations="";
            for(int i=0;i<locationIds.size();i++){
                if(i>0)
                    locations+=",";
                locations+=locationIds.get(i);
            }
            sb.append(" and (trn.source_location_location_id in ("+locations+") or trn.destination_location_location_id in ("+locations+")) ");
        }
        if(request.get("transferItemManualId")!=null&&!request.get("transferItemManualId").toString().equalsIgnoreCase("")){
            sb.append(" and titem.`manual_id` like ?");
            param.add(prepareLikeStateMent(request.get("transferItemManualId")));
        }
        if(request.get("productPartcode")!=null&&!request.get("productPartcode").toString().equalsIgnoreCase("")){
            sb.append(" and btp.`manual_id` like ?");
            param.add(prepareLikeStateMent(request.get("productPartcode")));
        }
        if(request.get("productType")!=null&&!request.get("productType").toString().equalsIgnoreCase("")){
            sb.append(" and btp.`product_type` like ? ");
            param.add(prepareLikeStateMent(request.get("productType")));
        }
        if(request.get("productDescription")!=null&&!request.get("productDescription").toString().equalsIgnoreCase("")){
            sb.append(" and btp.`product_description` like ?");
            param.add(prepareLikeStateMent(request.get("productDescription")));
        }
        if(request.get("productRequestItemManualId")!=null&&request.get("productRequestItemManualId").toString().equalsIgnoreCase("")){
            sb.append(" and reqitem.`manual_id` like ?");
            param.add(prepareLikeStateMent(request.get("productRequestItemManualId")));
        }
        if(request.get("productRequestManualId")!=null&&request.get("productRequestManualId").toString().equalsIgnoreCase("")){
            sb.append(" and req.`manual_id` like ? ");
            param.add(prepareLikeStateMent(request.get("productRequestManualId")));
        }

		return jdbc.queryForList(sb.toString(),param.toArray());
	}
    
}
