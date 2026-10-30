package com.pratikshat.exicom.repository.custom.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

import com.nimbusds.oauth2.sdk.Request;
import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.BtProductReturnOrderItem;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;
import com.pratikshat.exicom.repository.custom.BtProductReturnOrderItemRepositoryCustom;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class BtProductReturnOrderItemRepositoryCustomImpl implements BtProductReturnOrderItemRepositoryCustom{

    @PersistenceContext
    EntityManager em;

    @Autowired
    WorkflowExecuter wExecuter;

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public BtProductReturnOrderItem saveDetails(BtProductReturnOrderItem t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        wExecuter.executeWorkflow(em, t, "INSERT", "PRE", user);
        em.persist(t);
        String manulId=MiscellaneousUtils.fillZero("BT-ROI-"+t.getId(), 5);
        em.merge(t);
        wExecuter.executeWorkflow(em, t, "INSERT", "POST", user);
        return t;
    }

    @Override
    public BtProductReturnOrderItem updateDetails(BtProductReturnOrderItem t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        wExecuter.executeWorkflow(em, t, "UPDATE", "PRE", user);
        em.merge(t);
        wExecuter.executeWorkflow(em, t, "UPDATE", "POST", user);
        return t;
    }

    @Override
    public BtProductReturnOrderItem deleteDetails(BtProductReturnOrderItem t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        
        em.remove(t);
        
        return null;
    }

    @Override
    public void addReturnedQuantity(BtProductReturnOrderItem item, Float quantity, Boolean customerInventory,
            LoginUser user) throws BadRequestException, UnauthoriseException {
        if(quantity<0)
            throw new BadRequestException("Invalid quantity, value can not be negative");
        if(item.getId()==null||item.getId().equals(0L))
            throw new BadRequestException("Invalid return order item id");
        if(customerInventory){
            Float tQty=item.getCustomerQuantityReturned();
            tQty+=quantity;
            item.setCustomerQuantityReturned(tQty);
        }else{
            Float tQty=item.getQuantityReturned();
            tQty+=quantity;
            item.setQuantityReturned(tQty);
        }
        updateDetails(item, user);
    }

    @Override
    public void addReturnedQuantity(Long btProductReturnOrderItemId, Float quantity, Boolean customerInventory,
            LoginUser user) throws BadRequestException, UnauthoriseException {
        BtProductReturnOrderItem item=em.find(BtProductReturnOrderItem.class, btProductReturnOrderItemId);
        addReturnedQuantity(item, quantity, customerInventory, user);
    }

    @Override
    public void subReturnedQuantity(BtProductReturnOrderItem item, Float quantity, Boolean customerInventory,
            LoginUser user) throws BadRequestException, UnauthoriseException {
            if(quantity<0)
                throw new BadRequestException("Invalid quantity, value can not be negative");
            if(item.getId()==null||item.getId().equals(0L))
                throw new BadRequestException("Invalid return order item id");
            if(customerInventory){
                Float tQty=item.getCustomerQuantityReturned();
                tQty-=quantity;
                if(tQty<0)
                    throw new BadRequestException("Invalid quantity, value can not be negative");
                item.setCustomerQuantityReturned(tQty);
            }else{
                Float tQty=item.getQuantityReturned();
                tQty-=quantity;
                if(tQty<0)
                    throw new BadRequestException("Invalid quantity, value can not be negative");
                item.setQuantityReturned(tQty);
            }
            updateDetails(item, user);
    }

    @Override
    public void subReturnedQuantity(Long btProductReturnOrderItemId, Float quantity, Boolean customerInventory,
            LoginUser user) throws BadRequestException, UnauthoriseException {
        BtProductReturnOrderItem item=em.find(BtProductReturnOrderItem.class, btProductReturnOrderItemId);
        subReturnedQuantity(item, quantity, customerInventory, user);
    }

    @Override
    public void addReturnOrderReceivedQuantity(BtProductReturnOrderItem item, Float quantity, Boolean customerIvnentory,
            LoginUser user) throws BadRequestException, UnauthoriseException {
        if(quantity<0)
            throw new BadRequestException("Invalid quantity, value can not be negative");

        if(item.getId()==null||item.getId().equals(0L))
            throw new BadRequestException("Invalid product return order item id");

        if(customerIvnentory){
            Float tQty=item.getCustomerQuantityReceived();
            tQty+=quantity;
            item.setCustomerQuantityReceived(tQty);
        }
        else{
            Float tQty=item.getQuantityReceived();
            tQty+=quantity;
            item.setQuantityReceived(tQty);
        }
        updateDetails(item, user);
        
    }

    @Override
    public void addReturnOrderReceivedQuantity(Long btProductReturnOrderItemId, Float quantity,
            Boolean customerIvnentory, LoginUser user) throws BadRequestException, UnauthoriseException {
        BtProductReturnOrderItem item=em.find(BtProductReturnOrderItem.class, btProductReturnOrderItemId);
        addReturnOrderReceivedQuantity(item, quantity, customerIvnentory, user);
        
    }

    @Override
    public void subReturnorderReceivedQuantity(BtProductReturnOrderItem item, Float quantity, Boolean customerInventory,
            LoginUser user) throws BadRequestException, UnauthoriseException {
        if(quantity<0)
            throw new BadRequestException("Invalid quantity, Value can not be negative");

        if(item.getId()==null||item.getId().equals(0L))
            throw new BadRequestException("Invalid product return order item id");
            
        if(customerInventory){
            Float tQty=item.getCustomerQuantityReceived();
            tQty-=quantity;
            if(tQty<0)
                throw new BadRequestException("Invalid quantity, Value can not be negative");
            item.setCustomerQuantityReceived(tQty);
        }
        else{
            Float tQty=item.getQuantityReceived();
            tQty-=quantity;
            item.setQuantityReceived(tQty);
        }
        updateDetails(item, user);
    }

    @Override
    public void subReturnOrderReceivedQuantity(Long btProductReturnOrderItemId, Float quantity,
            Boolean customerInventory, LoginUser user) throws BadRequestException, UnauthoriseException {
        BtProductReturnOrderItem item=em.find(BtProductReturnOrderItem.class, btProductReturnOrderItemId);
        subReturnorderReceivedQuantity(item, quantity, customerInventory, user);
        
    }

    @Override
    public void addItem(BtProductReturnOrderItem item, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        BtProductReturnOrderItem _item=null;
        try {
            _item=em.createQuery("select item from BtProductReturnOrderItem item where item.products=?1 and item.returnOrder=?2",BtProductReturnOrderItem.class)
                .setParameter(1, item.getProducts())
                .setParameter(2, item.getReturnOrder())
                .getSingleResult();
            
            addReturnedQuantity(_item, item.getCustomerQuantityReturned(), true, user);
            addReturnedQuantity(_item, item.getQuantityReturned(), false, user);
            
        } catch (NoResultException e) {
            saveDetails(item, user);
        } catch (NonUniqueResultException e){
            throw new BadRequestException("Multiple record for same product found in request");
        }

    }


    @Override
    public List<Map<String, Object>> advanceSearchOfItemsByReturnOrderId(Long returnOrderId, List<Long> locationIds,Map<String,Object> request,
            LoginUser user) throws BadRequestException, UnauthoriseException {
        StringBuilder sb=new StringBuilder();
        List<Object> param=new ArrayList<Object>();
        sb.append("SELECT "
        +" item.`id` AS returnOrderItemId, "
        +" item.`created_date` AS returnOrderItemCreatedDate, "
        +" item.`last_modified_date` AS returnOrderItemModifiedDate, "
        +" item.`manual_id` AS returnOrderItemManualId, "
        +" item.`quantity_received` AS returnOrderItemQuantityReceived, "
        +" item.`quantity_returned` AS returnOrderItemQuantityReturned, "
        +" item.`created_by_email_id` AS returnOrderItemCreatedByEmailId, "
        +" item.`last_modified_by_email_id` AS returnOrderItemLastModifiedByEmailId, "
        +" item.`owner_email_id` AS returnOrderItemOwnerEmailId, "
        +" prd.id AS productId, "
        +" prd.manual_id AS productManualId, "
        +" item.`customer_quantity_received` AS returnOrderItemCustomerQuantityReceived, "
        +" item.`customer_quantity_returned` AS returnOrderItemCustomerQuantityReturned, "
        +" ro.id AS returnOrderId, "
        +" ro.manual_id AS returnOrderManualId "
        
        +" FROM `bt_product_return_order_item` item "
        +" JOIN `bt_product_return_order` ro ON ro.id=item.`return_order_id` "
        +" JOIN `bt_products` prd ON prd.id=item.`products_id` where ro.id=? ");
        param.add(returnOrderId);

        if(locationIds!=null){
            String locations="";
            for(int i=0;i<locationIds.size();i++){
                if(i>0)
                    locations+=",";
                locations+=locationIds.get(i);
            }
            sb.append(" and (ro.source_location_id in ("+locations+") or ro.destination_location_id in ("+locations+")) ");
        }

        if(request.get("returnOrderItemManualId")!=null&&!request.get("returnOrderItemManualId").toString().equalsIgnoreCase("")){

            sb.append(" and item.`manual_id` like ?");
            param.add(prepareForLike(request.get("returnOrderItemManualId")));
        }
        if(request.get("productManualId")!=null&&!request.get("productManualId").toString().equalsIgnoreCase("")){
           
            sb.append(" and prd.manual_id like ?");
            param.add(prepareForLike(request.get("productManualId")));
        }
        if(request.get("returnOrderManualId")!=null&&!request.get("returnOrderManualId").toString().equalsIgnoreCase("")){
            sb.append(" and ro.manual_id like ?");
            param.add(prepareForLike(request.get("returnOrderManualId")));
        }
        
        return jdbc.queryForList(sb.toString(),param.toArray());
    }

    String prepareForLike(Object obj){
        return "%"+obj+"%";
    }
    
}
