package com.pratikshat.exicom.repository.custom.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.BtServiceOrderItem;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;
import com.pratikshat.exicom.repository.custom.BtServiceOrderItemRepositoryCustom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;


public class BtServiceOrderItemRepositoryCustomImpl implements BtServiceOrderItemRepositoryCustom{

    @PersistenceContext
    EntityManager em;

    @Autowired
    WorkflowExecuter wExecuter;

    @Autowired JdbcTemplate jdbc;

    @Override
    public BtServiceOrderItem saveDetails(BtServiceOrderItem t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        wExecuter.executeWorkflow(em, t, "INSERT", "PRE", user);
        em.persist(t);
        String manualId="SOI-BT-"+MiscellaneousUtils.fillZero(""+t.getId(), 5);
        t.setManualId(manualId);
        em.merge(t);
        wExecuter.executeWorkflow(em, t, "INSERT", "POST", user);
        return t;
    }

    @Override
    public BtServiceOrderItem updateDetails(BtServiceOrderItem t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        wExecuter.executeWorkflow(em, t, "UPDATE", "PRE", user);
        em.merge(t);
        wExecuter.executeWorkflow(em, t, "UPDATE", "POST", user);
        return t;
    }

    @Override
    public BtServiceOrderItem deleteDetails(BtServiceOrderItem t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        throw new BadRequestException("This function is not supported");
    }

    @Override
    public List<Map<String, Object>> advanceSearch(Long serviceOrderId,Map<String, Object> request, List<Long> locationIds,
            LoginUser user) {
        StringBuilder sb=new StringBuilder();
        sb.append("SELECT "
        +" bts.`id` AS serviceOrderId, "
        +" bts.`manual_id` AS serviceOrderManualId, "
        
        +" prd.`manual_id` AS productPartcode, "
        +" prd.`id` AS productId, "
        +" prd.`product_type` AS productType, "
        +" prd.`product_description` AS productDescription, "
        +" titem.`id` AS serviceOrderItemId, "
        +" titem.`manual_id` AS serviceOrderItemManualId, "
        +" titem.`customer_inventory` AS isCustomerInventory, "
        +" titem.`serial_number` serviceOrderItemSerialNumber, "
        +" titem.`assigned` AS isAssigned, "
        +" titem.`assigned_returned` AS isAssignedReturned, "
        +" titem.`r_status` AS returnedStatus, "
        +" titem.`a_status` AS arriableStatus, "
        +" titem.`received` AS isReceived, "
        +" titem.`returned_to_source` AS isReturnedToSource, "
        +" titem.`assigned_to_email_id` AS assignedToEmailId, "
    
        +" aemp.`first_name` AS assignedToFirstName, "
        +" aemp.`middle_name` AS assignedToMiddleName, "
        +" aemp.`last_name` AS assignedToLastName, "
        +" btin.`id` AS brcCaseId, "
        +" btin.`manual_id` AS brcCaseManualId, "
        +" btin.`account_name` AS accountName, "
        +" btin.`account_sap_number` AS accountSapNumber "
         
        +" FROM `bt_service_order` bts  "
        +" JOIN `bt_service_order_item` titem ON titem.`service_order_id`=bts.id  "
        +" JOIN bt_products prd ON prd.id=titem.product_id "
        +" JOIN location sloc ON sloc.location_id = bts.`source_location_location_id`  "
        +" LEFT JOIN employee_details aemp ON aemp.email_id=titem.`assigned_to_email_id` "
        +" LEFT JOIN `bt_customer_inventory_in` btin ON btin.id=titem.`in_customer_inventory_id`"
        +" where bts.id=?");

        List<Object> param=new ArrayList<>();
        param.add(serviceOrderId);

        if(locationIds!=null){
            StringBuilder locations=new StringBuilder();
            for(int i=0;i<locationIds.size();i++){
                if(i!=0)
                    locations.append(",");
                locations.append(locationIds.get(i));
            }
            sb.append(" and (bts.source_location_location_id in ("+locations.toString()+") or bts.destination_location_location_id in ("+locations.toString()+")) ");
        }

        if(request.get("productPartcode")!=null&&!request.get("productPartcode").toString().equalsIgnoreCase("")){
            sb.append(" and prd.manual_id like ?");
            param.add(prepareLike(request.get("productPartcode")));
        }
        if(request.get("productType")!=null&&!request.get("productType").toString().equalsIgnoreCase("")){
            sb.append( " and prd.`product_type` like ?");
            param.add(prepareLike(request.get("productType")));
        }
        if(request.get("productDescription")!=null&&!request.get("productDescription").toString().equalsIgnoreCase("")){
            sb.append(" and prd.`product_description` like ?");
            param.add(prepareLike(request.get("productDescription")));
        }
        if(request.get("serviceOrderItemManualId")!=null&&!request.get("serviceOrderItemManualId").toString().equalsIgnoreCase("")){
            sb.append(" and titem.`manual_id` like ?");
            param.add(prepareLike(request.get("serviceOrderItemManualId")));
        }
        if(request.get("serviceOrderItemSerialNumber")!=null&&!request.get("serviceOrderItemSerialNumber").toString().equalsIgnoreCase("")){
            sb.append(" and titem.`serial_number` like ?");
            param.add(prepareLike(request.get("serviceOrderItemSerialNumber")));
        }
        if(request.get("assignedToFirstName")!=null&&!request.get("assignedToFirstName").toString().equalsIgnoreCase("")){
            sb.append(" and aemp.`first_name` like ?");
            param.add(request.get("assignedToFirstName"));
        }
        if(request.get("brcCaseManualId")!=null&&!request.get("brcCaseManualId").toString().equalsIgnoreCase("")){
            sb.append(" and btin.`manual_id`  like ?");
            param.add(request.get("brcCaseManualId"));
        }
        if(request.get("accountName")!=null&&!request.get("accountName").toString().equalsIgnoreCase("")){
            sb.append(" and btin.`account_name` like ? ");
            param.add(request.get("accountName"));
        }
        if(request.get("accountSapNumber")!=null&&!request.get("accountSapNumber").toString().equalsIgnoreCase("")){
            sb.append(" and btin.`account_sap_number` like ?");
            param.add(request.get("accountSapNumber"));
        }


        return jdbc.queryForList(sb.toString(),param.toArray());
    }

    String prepareLike(Object obj){
        return "%"+obj+"%";
    }
    
}
