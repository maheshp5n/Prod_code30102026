package com.pratikshat.exicom.repository.custom.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.BtProductReturnOrder;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;
import com.pratikshat.exicom.repository.custom.BtProductReturnOrderRepositoryCustom;
import com.pratikshat.exicom.utils.DateConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class BtProductReturnOrderRepositoryCustomImpl implements BtProductReturnOrderRepositoryCustom {

    @PersistenceContext
    EntityManager em;

    @Autowired
    WorkflowExecuter wExecuter;

    @Autowired
    JdbcTemplate jdbc;

    private static final String INVALID_QUANTITY="Invalid quantity";
    private static final String INVALID_RETURN_ORDER_ID ="Invalid return order id, Id not found";

    @Override
    public BtProductReturnOrder saveDetails(BtProductReturnOrder t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        wExecuter.executeWorkflow(em, t, "INSERT", "PRE", user);
        em.persist(t);

        String manualId="RO-BT-"+MiscellaneousUtils.fillZero(""+t.getId(), 5);
        t.setManualId(manualId);

        wExecuter.executeWorkflow(em, t, "INSERT", "POST", user);
        return t;
    }

    @Override
    public BtProductReturnOrder updateDetails(BtProductReturnOrder t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        wExecuter.executeWorkflow(em, t, "UPDATE", "PRE", user);
        em.merge(t);
        wExecuter.executeWorkflow(em, t, "UPDATE", "POST", user);
        return t;
    }

    @Override
    public BtProductReturnOrder deleteDetails(BtProductReturnOrder t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        em.remove(t);
        return t;
    }

    @Override
    public void addReturnedQuantity(BtProductReturnOrder returnOrder, Float quantity, Boolean customerInventory,
            LoginUser user) throws BadRequestException, UnauthoriseException {
        if(quantity<0)
            throw new BadRequestException(BtProductReturnOrderRepositoryCustomImpl.INVALID_QUANTITY);
        if(returnOrder.getId()==null||returnOrder.getId().equals(0L))
            throw new BadRequestException(BtProductReturnOrderRepositoryCustomImpl.INVALID_RETURN_ORDER_ID);
        
        Float tQty=returnOrder.getTotalQuantityReturn();
        tQty+=quantity;
        returnOrder.setTotalQuantityReturn(tQty);
        updateDetails(returnOrder, user);
    }

    @Override
    public void addReturnedQuantity(Long returnOrderId, Float quantity, Boolean customerInventory, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        BtProductReturnOrder returnOrder=em.find(BtProductReturnOrder.class, returnOrderId);
        addReturnReceivedQuantity(returnOrder, quantity, customerInventory, user);
    }

    @Override
    public void subReturnedQuantity(BtProductReturnOrder returnOrder, Float quantity, Boolean customerInventory,
            LoginUser user) throws BadRequestException, UnauthoriseException {
        if(quantity<0)
                throw new BadRequestException(BtProductReturnOrderRepositoryCustomImpl.INVALID_QUANTITY);
        if(returnOrder.getId()==null||returnOrder.getId().equals(0L))
                throw new BadRequestException(BtProductReturnOrderRepositoryCustomImpl.INVALID_RETURN_ORDER_ID);
        Float tQty=returnOrder.getTotalQuantityReturn();
        tQty-=quantity;
        
        if(tQty<0)
                throw new BadRequestException(BtProductReturnOrderRepositoryCustomImpl.INVALID_QUANTITY);
        returnOrder.setTotalQuantityReturn(tQty);

        updateDetails(returnOrder, user);
    }

    @Override
    public void subReturnedQuantity(Long returnOrderId, Float quantity, Boolean customerIvnentory, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        BtProductReturnOrder returnOrder=em.find(BtProductReturnOrder.class, returnOrderId);
        subReturnReceivedQuantity(returnOrder, quantity, customerIvnentory, user);
    }

    @Override
    public void addReturnReceivedQuantity(BtProductReturnOrder returnOrder, Float quantity, Boolean customerInventory,
            LoginUser user) throws BadRequestException, UnauthoriseException {
        if(quantity<0)
                throw new BadRequestException(BtProductReturnOrderRepositoryCustomImpl.INVALID_QUANTITY);
        if(returnOrder.getId()==null||returnOrder.getId().equals(0L))
                throw new BadRequestException(BtProductReturnOrderRepositoryCustomImpl.INVALID_RETURN_ORDER_ID);
        Float tQty=returnOrder.getTotalQuantityReceived();
        tQty+=quantity;
        returnOrder.setTotalQuantityReceived(tQty);
        updateDetails(returnOrder, user);
    }

    @Override
    public void addReturnReceivedQuantity(Long returnOrderId, Float quantity, Boolean customerInventory, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        BtProductReturnOrder returnOrder =em.find(BtProductReturnOrder.class, returnOrderId);
        addReturnReceivedQuantity(returnOrder, quantity, customerInventory, user);

    }

    @Override
    public void subReturnReceivedQuantity(BtProductReturnOrder returnOrder, Float quantity, Boolean customerInventory,
            LoginUser user) throws BadRequestException, UnauthoriseException {
         if(quantity<0)
                throw new BadRequestException(BtProductReturnOrderRepositoryCustomImpl.INVALID_QUANTITY);
        if(returnOrder.getId()==null||returnOrder.getId().equals(0L))
                throw new BadRequestException(BtProductReturnOrderRepositoryCustomImpl.INVALID_RETURN_ORDER_ID);
        Float tQty=returnOrder.getTotalQuantityReceived();
        tQty-=tQty;
        if(tQty<0)
                throw new BadRequestException(BtProductReturnOrderRepositoryCustomImpl.INVALID_QUANTITY);
        returnOrder.setTotalQuantityReceived(tQty);
        updateDetails(returnOrder, user);
    }

    @Override
    public void subReturnReceivedQuantity(Long returnOrderId, Float quantity, Boolean customerInventory, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        BtProductReturnOrder returnOrder=em.find(BtProductReturnOrder.class, returnOrderId);
        subReturnReceivedQuantity(returnOrder, quantity, customerInventory, user);
    }

    @Override
    public List<Map<String, Object>> advanceSearch(List<Long> locationIds, Map<String, Object> searchparam,
            LoginUser user) throws BadRequestException, UnauthoriseException {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT "
        +" ro.`id` AS id, "
        +" ro.`created_date` AS createdDate, "
        +" ro.`last_modified_date` AS returnOrderLastModifiedDate, "
        +" ro.`actual_send_date` AS returnOrderActualSendDate, "
        +" ro.`manual_id` AS returnOrderManualId, "
        +" ro.`return_order_status` AS returnOrderStatus, "
        +" ro.`ship_to_name` AS returnOrderShipToName, "
        +" ro.`shipping_mode` AS returnOrderShippingMode, "
        +" ro.`total_quantity_received` AS returnOrderTotalQuantityReceived, "
        +" ro.`total_quantity_return` AS returnOrderTotalQuantityReturned, "
        +" ro.`tracking_number` AS returnOrderTrackingNumber, "
    
        +" sloc.location_id AS returnOrderSourceLocationId, "
        +" sloc.location_name AS returnOrderSourceLocationName, "
        
        +" dloc.location_id AS returnOrderDestinationLocationId, "
        +" dloc.location_name AS returnOrderDestinationalLocationName, "
        
        +" cremp.`first_name` AS returnOrderCreatedByFirstName, "
        +" cremp.`last_name` AS returnOrderCreatedByLastName, "
        +" cremp.`middle_name` AS returnOrderCreatedByMiddleName, "
        
        +" lsemp.`first_name` AS returnOrderLastModifiedByFirstName, "
        +" lsemp.`last_name` AS returnOrderLastModifiedByLastName, "
        +" lsemp.`middle_name` AS returnOrderLastModifiedMiddleName, "
        
        +" oemp.first_name AS returnOrderOwnerFirstName, "
        +" oemp.last_name AS returnOrderownerLastName, "
        +" oemp.middle_name AS returnOrderOwnerMiddleName "
        
        
        +" FROM `bt_product_return_order` ro "
        +" JOIN location sloc ON ro.`source_location_id` = sloc.location_id "
        +" JOIN location dloc ON ro.`destination_location_id` =dloc.location_id "
        +" JOIN employee_details cremp ON cremp.email_id=ro.`created_by_email_id` "
        +" JOIN employee_details lsemp ON lsemp.email_id=ro.`last_modified_by_email_id` "
        +" JOIN employee_details oemp ON oemp.email_id=ro.`owner_email_id` where 1=1");
        
        List<Object> param=new ArrayList<Object>();

        if(locationIds!=null){
            String lo="";
            int lent=locationIds.size();
            for(int i=0;i<lent;i++){
                if(i!=0)
                    lo+=",";
                lo+=locationIds.get(i);
            }
            sb.append(" and (sloc.location_id in ("+lo+") or dloc.location_id in ("+lo+"))");
        }
        if(searchparam.get("returnOrderManualId")!=null&&!searchparam.get("returnOrderManualId").toString().equalsIgnoreCase("")){
            sb.append(" and ro.manual_id like ? ");
            param.add("%"+searchparam.get("returnOrderManualId")+"%");
        }
        if(searchparam.get("returnOrderStatus")!=null&&!searchparam.get("returnOrderStatus").toString().equalsIgnoreCase("")){
            sb.append(" and ro.return_order_status like ? ");
            param.add("%"+searchparam.get("returnOrderStatus")+"%");
        }
        if(searchparam.get("returnOrderShipToName")!=null&&!searchparam.get("returnOrderShipToName").toString().equalsIgnoreCase("")){
            sb.append("and ro.ship_to_name like ? ");
            param.add("%"+searchparam.get("returnOrderShipToName")+"%");
        }

        if(searchparam.get("returnOrderShippingMode")!=null&&!searchparam.get("returnOrderShippingMode").toString().equalsIgnoreCase("")){
            sb.append(" and ro.shipping_mode like ? ");
            param.add("%"+searchparam.get("returnOrderShippingMode")+"%");
        }
        if(searchparam.get("returnOrderTrackingNumber")!=null&&!searchparam.get("returnOrderTrackingNumber").toString().equalsIgnoreCase("")){
            sb.append(" and ro.tracking_number like ? ");
            param.add("%"+searchparam.get("returnOrderTrackingNumber")+"%");
        }
        if(searchparam.get("returnOrderSourceLocationName")!=null&&!searchparam.get("returnOrderSourceLocationName").toString().equalsIgnoreCase("")){
            sb.append(" and sloc.location_name like ? ");
            param.add("%"+searchparam.get("returnOrderSourceLocationName")+"%");
        }
        if(searchparam.get("returnOrderDestinationalLocationName")!=null&&!searchparam.get("returnOrderDestinationalLocationName").toString().equalsIgnoreCase("")){
            sb.append(" and dloc.location_name like ? ");
            param.add("%"+searchparam.get("returnOrderDestinationalLocationName")+"%");
        }
        if(searchparam.get("fromDate")!=null&&!searchparam.get("fromDate").toString().equalsIgnoreCase("")&&searchparam.get("toDate")!=null&&!searchparam.get("toDate").toString().equalsIgnoreCase("")){
            sb.append("and (ro.`created_date` between ? and ?)");
            param.add(DateConverter.convertToDate(searchparam.get("fromDate").toString()));
            param.add(DateConverter.convertToDate(searchparam.get("toDate").toString()));
        }

        return jdbc.queryForList(sb.toString(),param.toArray());
    }
    
}
