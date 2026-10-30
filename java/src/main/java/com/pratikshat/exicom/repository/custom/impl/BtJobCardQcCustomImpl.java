package com.pratikshat.exicom.repository.custom.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.nimbusds.oauth2.sdk.Request;
import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.BtJobCardQc;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;
import com.pratikshat.exicom.repository.custom.BtJobCardQcCustom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class BtJobCardQcCustomImpl implements BtJobCardQcCustom {

    @PersistenceContext EntityManager em;
    @Autowired WorkflowExecuter wExecuter;
    @Autowired JdbcTemplate jdbc;

    @Override
    public BtJobCardQc saveDetails(BtJobCardQc t, LoginUser user) throws BadRequestException, UnauthoriseException {
        wExecuter.executeWorkflow(em, t, "INSERT", "PRE", user);
        em.persist(t);
        String manualId="JC-QC-BT"+MiscellaneousUtils.fillZero(""+t.getId(), 5);
        t.setManualId(manualId);
        em.merge(t);

        wExecuter.executeWorkflow(em, t, "INSERT", "POST", user);

        return t;
    }

    @Override
    public BtJobCardQc updateDetails(BtJobCardQc t, LoginUser user) throws BadRequestException, UnauthoriseException {
        wExecuter.executeWorkflow(em, t, "UPDATE", "PRE", user);
        em.merge(t);
        wExecuter.executeWorkflow(em, t, "UPDATE", "POST", user);
        return t;
    }

    @Override
    public BtJobCardQc deleteDetails(BtJobCardQc t, LoginUser user) throws BadRequestException, UnauthoriseException {
        throw new BadRequestException("This method is not avilable");
    }

    @Override
    public List<Map<String, Object>> advanceSearch(Map<String, Object> request, List<String> emailids)
            throws BadRequestException, UnauthoriseException {
        StringBuilder sb=new StringBuilder();
        sb.append("SELECT "
        +" qc.`id` AS qcId, "
        +" qc.`created_date` AS qcCreatedDate, "
        +" qc.`last_modified_date` AS qcLastModifiedDate, "
        +" qc.`assigned_date` AS qcAssignedDate, "
        +" qc.`corrective_action_taken` AS qcCorrectiveActionTaken, "
        +" qc.`electrical_check` AS qcElectricalCheck, "
        +" qc.`electrical_remarks` AS qcElectricalRemarks, "
        +" qc.`fault_observed_during_pid` AS qcFaultObservedDuringPID, "
        +" qc.`final_approval` AS qcFinalApproval, "
        +" qc.`manual_id` AS qcManualId, "
        +" qc.`parameters_check` AS qcParameterCheck, "
        +" qc.`physical_check` AS qcPhysicalCheck, "
        +" qc.`physical_remarks` AS qcPhysicalRemarks, "
        +" qc.`software_updation` AS qcSoftWareupdation, "
        +" qc.`soh_updation` AS qcSohUpdation, "
        +" qc.`v_max` AS qcVmax, "
        +" qc.`v_min` AS qcVmin, "
        +" qc.`voltage_gap_lt150mv` AS qcVoltageGap, "
        +" qc.`created_by_email_id` AS qcCreatedByEmailId, "
        +" qc.`last_modified_by_email_id` AS qcLastModifiedByEmailId, "
        +" qc.`assigned_to_email_id` AS qcAssignedToEmailId, "
        +" qc.`job_card_id` AS qcJobCardId, " 
        +" qc.`owner_email_id` AS qcOwnerEmailId, "
        
        +" cemp.`first_name` AS qcCreatedByFirstName, "
        +" cemp.`middle_name` AS qcCreatedByMiddleName, "
        +" cemp.`last_name` AS qcCreatedLastName, "
        
        +" memp.`first_name` AS qcModifiedByFirstName, "
        +" memp.`middle_name` AS qcModifiedByMiddleName, "
        +" memp.`last_name` AS qcModifiedLastName, "
        
        
        +" oemp.`first_name` AS qcOwnerFirstName, "
        +" oemp.`middle_name` AS qcOwnerMiddleName, "
        +" oemp.`last_name` AS qcOwnerLastName, "
        
        +" aemp.`first_name` AS qcAssignedToFirstName, "
        +" aemp.`middle_name` AS qcAssignedToMiddleName, "
        +" aemp.`last_name` AS qcAssignedToLastName, "
        
        +" jc.`manual_id` AS jobCardManualId, "
        +" jc.`qc_done` AS jobCardQcDone, "
        +" jc.`repair_date` AS jobCardRepairDate, "
        +" jc.`repair_product_state` AS jobCardRepariProductState, "
        +" jc.`repared` AS jobCardIsReparied, "
        +" jc.`return_date` AS jobCardReturnDate, "
        +" jc.`returned_to_source` AS jobIsCardReturnedToSource, "
        +" jc.`service_order_item_id` AS jobCardServiceOrderItemId, "
        +" jc.`previous_state` AS jobCardPreviousState, "
        
        +" item.`a_status` AS soItemArriveState, "
        +" item.`assigned` AS soIsItemAssigned, "
        +" item.`assigned_returned` AS soIsItemReturned, "
        +" item.`customer_inventory` AS soIsCustomerInventory, "
        +" item.`manual_id` AS soManualId, "
        +" item.`r_status` AS soItemReturnStatus, " 
        +" item.`returned_to_source` AS soItemRturnToSoruce, "
        +" item.`serial_number` AS soItemSerialNumber, "
        +" item.`in_customer_inventory_id` AS soItemCustomerIvnentoryInId, "
        +" item.`product_id` AS soItemProductId, "
        
        +" prd.`manual_id` AS productPartcode, "
        +" prd.`product_type` AS productType, "
        +" prd.`product_description` AS productDescription "
        
     
        +" FROM `bt_job_card_qc` qc  "
        +" JOIN `bt_job_card` jc ON qc.`job_card_id`=jc.id "
        +" JOIN `bt_service_order_item` item ON item.id=jc.id "
        +" JOIN `bt_products` prd ON prd.id=item.product_id "
        +" JOIN `employee_details` cemp ON cemp.`email_id`=qc.`created_by_email_id` "
        +" JOIN `employee_details` memp ON memp.`email_id`=qc.`last_modified_by_email_id` "
        +" JOIN `employee_details` oemp ON oemp.`email_id`=qc.`owner_email_id` "
        +" JOIN `employee_details` aemp ON aemp.`email_id`=qc.`assigned_to_email_id` where 1=1 ");

        List<Object> param=new ArrayList<>();

        if(emailids!=null){
            
            StringBuilder eb=new StringBuilder();
            for(int i=0;i<emailids.size();i++){
                if(i!=0)
                    eb.append(",");
                eb.append("'"+emailids.get(i)+"'");
            }

            sb.append(" and qc.assigned_to_email_id in ("+eb.toString()+")");
        }

        if(request.get("qcManualId")!=null&&!request.get("qcManualId").toString().equalsIgnoreCase("")){
            sb.append(" and qc.`manual_id` like ?");
            param.add(likeStatement(request.get("qcManualId")));
        }
        if(request.get("qcCorrectiveActionTaken")!=null&&!request.get("qcCorrectiveActionTaken").toString().equalsIgnoreCase("")){
            sb.append(" and qc.`corrective_action_taken like ?");
            param.add(likeStatement(request.get("qcCorrectiveActionTaken")));
        }
        if(request.get("jobCardManualId")!=null&&!request.get("jobCardManualId").toString().equalsIgnoreCase("")){
            sb.append(" and jc.`manual_id` like ?");
            param.add(likeStatement(request.get("jobCardManualId")));
        }
        if(request.get("soItemSerialNumber")!=null&&!request.get("soItemSerialNumber").toString().equalsIgnoreCase("")){
            sb.append(" and item.`serial_number` like ?");
            param.add(likeStatement(request.get("soItemSerialNumber")));
        }
        if(request.get("productPartcode")!=null&&!request.get("productPartcode").toString().equalsIgnoreCase("")){
            sb.append(" and prd.`manual_id` like ?");
            param.add(likeStatement(request.get("productPartcode")));
        }
        if(request.get("productDescription")!=null&&!request.get("productDescription").toString().equalsIgnoreCase("")){
            sb.append(" and prd.`product_description` like ?");
            param.add(likeStatement(request.get("productDescription")));
        }
        if(request.get("assignToQC")!=null&&!request.get("assignToQC").toString().equalsIgnoreCase("")){
            sb.append(" and (aemp.`first_name` like ? or aemp.`middle_name` like ? or aemp.`last_name` like ?)");
            param.add(likeStatement(request.get("assignToQC")));
            param.add(likeStatement(request.get("assignToQC")));
            param.add(likeStatement(request.get("assignToQC")));
        }

        return jdbc.queryForList(sb.toString(),param.toArray());
    }
    
    String likeStatement(Object obj){
        return "%"+obj.toString()+"%";
    }
}
