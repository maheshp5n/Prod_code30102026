package com.pratikshat.exicom.repository.custom.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.BtServiceOrder;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;
import com.pratikshat.exicom.repository.custom.BtServiceOrderCustomRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class BtServiceOrderCustomRepositoryImpl implements BtServiceOrderCustomRepository {
    @PersistenceContext
    EntityManager em;

    @Autowired
    WorkflowExecuter wExecuter;

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public BtServiceOrder saveDetails(BtServiceOrder t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        wExecuter.executeWorkflow(em, t, "INSERT", "PRE", user);
        em.persist(t);
        String manualId="SO-BT-"+MiscellaneousUtils.fillZero(""+t.getId(), 5);
        t.setManualId(manualId);
        em.merge(t);
        wExecuter.executeWorkflow(em, t, "INSERT", "POST", user);
        return t;
    }

    @Override
    public BtServiceOrder updateDetails(BtServiceOrder t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        wExecuter.executeWorkflow(em, t, "UPDATE", "PRE", user);
        em.merge(t);
        wExecuter.executeWorkflow(em, t, "UPDATE", "POST", user);
        return t;
    }

    @Override
    public BtServiceOrder deleteDetails(BtServiceOrder t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        
        throw new BadRequestException("This function is not avilable");
    }

    @Override
    public List<Map<String, Object>> advanceSearch(Map<String, Object> request, List<Long> locationIds, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        StringBuilder sb=new StringBuilder();
        sb.append("SELECT so.id AS serviceOrderId, "
        +" so.`created_date` AS searviceOrderCreatedDate, "
        +" so.`last_modified_date` AS serviceOrderLastModifiedDate, "
        +" so.`manual_id` AS serviceOrderManualId, "
        +" so.`shipping_mode` AS searviceOrderShippingMode, "
        +" so.`shipping_to` AS serviceOrderShippingTo, "
        +" so.`total_number_of_item` AS searviceOrderTotalNumberOfItem, "
        +" so.`total_number_of_item_assigned` AS serviceOrderTotalNumberOfItemAssigned, "
        +" so.`total_number_of_item_assigned_returned` AS serviceOrderTotalNumberOfItemAssignedReturned, "
        +" so.`total_number_of_item_received` AS searviceOrderTotalNumberOfItemReceived, "
        +" so.`total_number_of_item_returned` AS searviceOrderTotalNumberOfItemReturned, "
        +" so.`tracking_number` AS searviceOrderTrackingNumber, "
        +" so.`created_by_email_id` searviceOrderCreatedByEmailId, "
        +" so.`destination_location_location_id` AS serviceOrderRepairCenterLocationId, "
        +" so.`owner_email_id` AS serviceOrderOwnerEmailid, "
        +" so.`source_location_location_id` AS serviceOrderSourceLocationId, "

        +" cemp.`first_name` as createdByFirstName, "
        +" cemp.`middle_name` as createdByMiddleName,"
        +" cemp.`last_name` as createdByLastName, "
    
        +" lemp.`first_name` as lastModifiedByFirstName, "
        +" lemp.`middle_name` as lastModifiedByMiddleName,"
        +" lemp.`last_name` as lastModifiedByLastName, "
    
        +" oemp.`first_name` as ownerFirstName, "
        +" oemp.`middle_name` as ownerMiddleName,"
        +" oemp.`last_name` as ownerByLastName, "
        
        +" dloc.location_name AS serviceOrderRepairCenterLocationName, "
        +" dloc.plant AS serviceOrderRepairCenterLocationPlant, "
        +" sloc.location_name AS serviceOrderSourceLocationName, "
        +" sloc.plant AS serviceOrderSourceLocationPlant "
        
        
        +" FROM `bt_service_order` so  "
        +" JOIN location dloc ON dloc.location_id = so.`destination_location_location_id` "
        +" JOIN location sloc ON sloc.location_id = so.`source_location_location_id` "
        +" JOIN employee_details cemp ON cemp.email_id=so.`created_by_email_id` "
        +" JOIN employee_details oemp ON oemp.email_id=so.`owner_email_id` "
        +" JOIN employee_details lemp ON lemp.email_id=`last_modified_by_email_id` where 1=1");
        if(locationIds!=null){
            StringBuilder lids=new StringBuilder();
            for(int i=0;i<locationIds.size();i++){
                if(i!=0)
                    lids.append(",");
                lids.append(locationIds.get(i));
            }
            sb.append(" and (dloc.location_id in ("+lids.toString()+") or sloc.location_id in ("+lids.toString()+"))");
        }




        List<Object> params=new ArrayList<>();

        if(request.get("serviceOrderManualId")!=null&&!request.get("serviceOrderManualId").toString().equalsIgnoreCase("")){
            sb.append(" and so.`manual_id` like ?");
            params.add(objToLike(request.get("serviceOrderManualId")));
        }
        if(request.get("searviceOrderShippingMode")!=null&&!request.get("searviceOrderShippingMode").toString().equalsIgnoreCase("")){
            sb.append(" and so.`shipping_to` like ? ");
            params.add(objToLike(request.get("searviceOrderShippingMode")));
        }
        if(request.get("searviceOrderTrackingNumber")!=null&&!request.get("searviceOrderTrackingNumber").toString().equalsIgnoreCase("")){
            sb.append(" and so.`tracking_number` like ? ");
            params.add(objToLike(request.get("searviceOrderTrackingNumber")));
        }
        if(request.get("serviceOrderRepairCenterLocationName")!=null&&!request.get("serviceOrderRepairCenterLocationName").toString().equalsIgnoreCase("")){
            sb.append(" and dloc.location_name like ? ");
            params.add(objToLike(request.get("serviceOrderRepairCenterLocationName")));
        }
        if(request.get("serviceOrderSourceLocationName")!=null&&!request.get("serviceOrderSourceLocationName").toString().equalsIgnoreCase("")){
            sb.append(" and sloc.location_name like ? ");
            params.add(objToLike(request.get("serviceOrderSourceLocationName")));
        }

        
        return jdbc.queryForList(sb.toString(),params.toArray());
    }

    @Override
    public void addTotalNumberOfItem(BtServiceOrder order, Float quantity, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        Float tQty=order.getTotalNumberOfItem()==null?0:order.getTotalNumberOfItem();
        tQty+=quantity;
        order.setTotalNumberOfItem(tQty);
        updateDetails(order, user);
    }

    @Override
    public void addTotalNumberOfItemReceived(BtServiceOrder order, Float quantity, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        Float tQty=order.getTotalNumberOfItemReceived();
        tQty+=quantity;
        order.setTotalNumberOfItemReceived(tQty);
        updateDetails(order, user);
    }

    @Override
    public void addTotalNumberOfItemReturned(BtServiceOrder order, Float quantity, LoginUser user)
            throws BadRequestException, UnauthoriseException {
       Float tQty=order.getTotalNumberOfItemReturned();
       tQty+=quantity;
       order.setTotalNumberOfItemReturned(tQty);
       updateDetails(order, user);
    }

    @Override
    public void addTotalNumberOfItemAssigned(BtServiceOrder order, Float quantity, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        Float tQty=order.getTotalNumberOfItemAssigned();
        tQty+=quantity;
        order.setTotalNumberOfItemAssigned(tQty);
        updateDetails(order, user);
        
    }

    @Override
    public void addTotalNumberOfItemAssignedReturned(BtServiceOrder order, Float quantity, LoginUser user)
            throws BadRequestException, UnauthoriseException {
       Float tQty=order.getTotalNumberOfItemAssignedReturned();
       tQty+=quantity;
       order.setTotalNumberOfItemAssignedReturned(tQty);
       updateDetails(order, user);
    }

    @Override
    public void addTotalNumberOfItem(Long orderId, Float quantity, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        BtServiceOrder order=em.find(BtServiceOrder.class, orderId);
        addTotalNumberOfItem(order, quantity, user);
    }

    @Override
    public void addTotalNumberOfItemReceived(Long orderId, Float quantity, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        BtServiceOrder order=em.find(BtServiceOrder.class,orderId);
        addTotalNumberOfItemReceived(order, quantity, user);
        
    }

    @Override
    public void addTotalNumberOfItemReturned(Long orderId, Float quantity, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        BtServiceOrder order=em.find(BtServiceOrder.class, orderId);
        addTotalNumberOfItemReturned(order, quantity, user);    
    }

    @Override
    public void addTotalNumberOfItemAssigned(Long orderId, Float quantity, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        BtServiceOrder order=em.find(BtServiceOrder.class, orderId);
        addTotalNumberOfItemAssigned(order, quantity, user);
        
    }

    @Override
    public void addTotalNumberOfItemAssignedReturned(Long orderId, Float quantity, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        BtServiceOrder order=em.find(BtServiceOrder.class, orderId);
        addTotalNumberOfItemAssignedReturned(order, quantity, user);
        
    }

    @Override
    public void subTotalNumberOfItem(BtServiceOrder order, Float quantity, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        Float tQty=order.getTotalNumberOfItem();
        tQty-=quantity;
        if(tQty<0)
                throw new BadRequestException("Total number of item can not be negative");
        order.setTotalNumberOfItem(tQty);
        updateDetails(order, user);
    }

    @Override
    public void subTotalNumberOfItemReceived(BtServiceOrder order, Float quantity, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        Float tQty=order.getTotalNumberOfItemReceived();
        tQty-=quantity;
        if(tQty<0)
                throw new BadRequestException("Total number of item received can not be negative");
        order.setTotalNumberOfItemReceived(tQty);
        updateDetails(order, user);
    }

    @Override
    public void subTotalNumberOfItemReturned(BtServiceOrder order, Float quantity, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        
        Float tQty=order.getTotalNumberOfItemReturned();
        tQty-=quantity;
        if(tQty<0)
                throw new BadRequestException("Total Number of item returned can not be negative");
        order.setTotalNumberOfItemReturned(tQty);
        updateDetails(order, user);
    }

    @Override
    public void subTotalNumberOfItemAssigned(BtServiceOrder order, Float quantity, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        
        Float tQty=order.getTotalNumberOfItemAssigned();
        tQty-=quantity;
        if(tQty<0)
                throw new BadRequestException("Total number of item assigned can not be negative");
        order.setTotalNumberOfItemAssigned(tQty);
        updateDetails(order, user);
    }

    @Override
    public void subTotalNumberOfItemAssignedReturned(BtServiceOrder order, Float quantity, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        Float tQty=order.getTotalNumberOfItemAssignedReturned();
        tQty-=quantity;
        if(tQty<0)
                throw new BadRequestException("Total numbr of item assigned return can not be negative");
        order.setTotalNumberOfItemAssignedReturned(tQty);
        updateDetails(order, user);
        
    }

    @Override
    public void subTotalNumberOfItem(Long orderId, Float quantity, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        BtServiceOrder order=em.find(BtServiceOrder.class,orderId);
        subTotalNumberOfItem(order, quantity, user);
    }

    @Override
    public void subTotalNumberOfItemReceived(Long orderId, Float quantity, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        BtServiceOrder order=em.find(BtServiceOrder.class, orderId);
        subTotalNumberOfItemReceived(order, quantity, user);
    }

    @Override
    public void subTotalNumberOfItemReturned(Long orderId, Float quantity, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        BtServiceOrder order=em.find(BtServiceOrder.class, orderId);
        subTotalNumberOfItemReturned(order, quantity, user);
    }

    @Override
    public void subTotalNumberOfItemAssigned(Long orderId, Float quantity, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        BtServiceOrder order=em.find(BtServiceOrder.class, orderId);
        subTotalNumberOfItemAssigned(order, quantity, user);
    }

    @Override
    public void subTotalNumberOfItemAssignedReturned(Long orderId, Float quantity, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        BtServiceOrder order = em.find(BtServiceOrder.class, orderId);
        subTotalNumberOfItemAssignedReturned(order, quantity, user);
    }

    String objToLike(Object obj){
        return "%"+obj.toString()+"%";
    }
    
}
