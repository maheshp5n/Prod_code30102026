package com.pratikshat.exicom.repository.custom.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import com.google.api.gax.rpc.UnauthenticatedException;
import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.BtCustomerInventoryIn;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;
import com.pratikshat.exicom.repository.custom.BtCustomerInventoryInCustomRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class BtCustomerInventoryInCustomRepositoryImpl implements BtCustomerInventoryInCustomRepository {

    @PersistenceContext
    EntityManager em;

    @Autowired
    WorkflowExecuter wExecuter;

    @Autowired
    JdbcTemplate jdbc;

    SimpleDateFormat format=new SimpleDateFormat("yyMMdd");

    @Override
    public BtCustomerInventoryIn saveDetails(BtCustomerInventoryIn t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        wExecuter.executeWorkflow(em, t, "INSERT", "PRE", user);
        em.persist(t);
        String manualId="CS-BRC-"+format.format(new Date())+MiscellaneousUtils.fillZero(""+t.getId(), 5);
        t.setManualId(manualId);
        em.persist(t);
        wExecuter.executeWorkflow(em, t, "INSERT", "POST", user);
        return t;
    }

    @Override
    public BtCustomerInventoryIn updateDetails(BtCustomerInventoryIn t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        wExecuter.executeWorkflow(em, t, "UPDATE", "PRE", user);
        em.persist(t);
        wExecuter.executeWorkflow(em, t, "UPDATE", "POST", user);
        return t;
    }

    @Override
    public BtCustomerInventoryIn deleteDetails(BtCustomerInventoryIn t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        throw new RuntimeException("This function is not avilable right now");
    }

    @Override
    public List<Map<String, Object>> getAdvanceSearchResult(Map<String, Object> request, List<Long> locationIds,
            LoginUser user) throws BadRequestException, UnauthenticatedException {
        StringBuilder sb=new StringBuilder();

        List<Object> param=new ArrayList<Object>();
        
        sb.append("SELECT   "
        +" cus.`id` AS customerInventoryInId,  "
        +" cus.`created_date` AS customerIvnentoryCreatedDate,  "
        +" cus.`last_modified_date` AS customerInventoryModifiedDate,  "
        +" cus.`assets_serial_number` AS customerInventoryAssetsSerialNumber,  "
        +" cus.`account_name` AS customerAccountName, "
        +" cus.`account_sap_number` AS customerAccountSapNumber, "
        +" cus.`challan_number` AS customerChallanNumber, "
        +" cus.`circle_name` AS customerCircleName, "
        +" cus.`docket_date` AS docketDate, "
        +" cus.`docket_number` AS customerInventoryDocketNumber, "
        +" cus.`loat_number` AS customerInventoryLoatNumber, "
        +" cus.`tracking_number` AS trackingNumber, "
        +" cus.brc_number as brcNumber,"
        +" cus.`in_date` AS customerInventoryInDate,  "
        +" cus.`manual_id` AS customerInventoryManualId,  "
        +" cus.`product_partcode` AS customerInventoryProductPartcode,  "
        +" cus.`returned_to_customer` AS customerInventoryReturnedToCustomer,  "
        +" cus.`created_by_email_id` AS customerInventoryCreatedByEmailId,  "
        +" cus.`last_modified_by_email_id` AS customerInventoryLastModifiedByEmailId,  "
        +" cus.`in_location_location_id` AS customerInventoryInLocationId,  "
        +" cus.`owner_email_id` AS customerInventoryOwnerEmailId,  "
        +" cus.`rma_id` AS customerInventoryRmaId,  "
        +" cus.`state_code` AS customerIvnentoryInProductStateCode,  "
        +" cus.`product_id` AS customerInventoryProductId,  "

        +" cus.remarks as cRemarks, "
        +" cus.dispatch_init_date as dispatchInitDate, "
        +" cus.brc_case_close_date as brcCaseCloseDate, "

        +" cus.case_warranty as btcCaseWarranty, "
        +" cus.dynamic_warranty as btcCaseDynamicWarranty, "

        +" cus.`contact_name`  AS contactName, "
        +" cus.`contact_number` AS contactNumber, "
        +" cus.`customer_courier_partnar_name` AS customerCourierpartnerName, "
        +" cus.`customer_inventory_stage` AS customerInventoryStage, "
        +" cus.`customer_inventory_status` AS customerInventoryStatu, "
        +" cus.`customerlrnumber` AS customerLRNumber, "
        +" cus.`customer_plant` AS customerPlant, "
        +" cus.`packing_condation` AS packingCondation, "
        +" cus.`physical_damage` AS physicalDamage, "
        +" cus.`receive_challan_date` AS receivedChallanDate, "

        +" cemp.`first_name` AS createdByFirstName,"
        +" cemp.`last_name` AS createdByLastName,"
        +" cemp.`middle_name` AS createdByMiddleName,"
    
        +" oemp.`first_name` AS ownerFirstName,"
        +" oemp.`last_name` AS ownerLastName,"
        +" oemp.`middle_name` AS ownerMiddleName,"
        
        +" lemp.`first_name` AS lastModifiedByFirstName,"
        +" lemp.`last_name` AS lastModifiedByLastName,"
        +" lemp.`middle_name` AS lastModifiedByMiddleName,"
        
        +" cout.`created_date` customerInventoryOutCreatedDate, "
        +" cout.`last_modified_date` AS customerInventoryOutLastModifiedDate, "
        +" cout.`actual_out_date` AS customerInventoryOutActualDate, "
        +" cout.`loat_number` AS customerInventoryOutLoatNumber, "
        +" cout.`manual_id` AS customerInventoryOutManualId, "
        +" cout.`out_date` AS customerInventoryOutOutDate, "
        +" cout.`out_state` AS customerInventoryOutStateId, "
        +" cout.`tracking_number` AS customerInventoryOutTrackingNumber, "
        +" cout.`created_by_email_id` AS customerInventoryOutCreatedByEmailId, "
        +" cout.`last_modified_by_email_id` AS customerInventoryOutLastModifiedBy, "
        +" cout.`owner_email_id` AS customerInventoryOutOwnerEmailId, "

        +" btprd.`product_type` AS productType, "
        +" btprd.`product_description` AS productDescription,"
    
        +" cout.out_courier_partner as outCourierPartner, "
        +" cout.out_grn_date as outGrnDate, "
        +" cout.out_grn_number as outGrnNumber, "
        +" cout.out_lr_doc_number as outLrDocNumber, "
        +" cout.outdelevery_challan_number as outDeliveryChallanNumber, "
        
        +" rma.`id` AS rmaId,  "
        +" rma.`created_date` AS rmaCreatedDate,  "
        +" rma.`battery_grn_id` AS rmaBatteryGrnId, " 
        +" rma.`manual_id` AS rmaManualId,  "
        +" rma.`rma_status` AS rmaStatus,  "
        +" rma.`case_details_id` AS rmaCaseId,  "
        +" rma.`service_appointment_service_appointment_id` AS rmaSaId,  "
        +" bcs.manual_id AS caseManualId,  "
        +" btsa.manual_id AS serviceAppointmentManualId,  "
        +" loc.location_name AS locationName,  "
        +" ( CASE WHEN jc.`repair_product_state`=1 THEN 'OK' "
        +"   WHEN jc.`repair_product_state`=5 THEN 'BER' "
        +"   ELSE jc.`repair_product_state` END) AS `jobCardCurrentState`"
        +" FROM `bt_customer_inventory_in` cus  " 
        +" JOIN `employee_details` cemp ON cemp.`email_id`=cus.created_by_email_id "
        +" JOIN `employee_details` oemp ON oemp.`email_id`=cus.owner_email_id "
        +" JOIN `employee_details` lemp ON lemp.`email_id`=cus.owner_email_id "
        +" LEFT JOIN `btrmadetails` rma ON rma.id=cus.`rma_id`  "
        +" JOIN `bt_products` btprd ON btprd.id=cus.product_id  "
        +" JOIN `location` loc ON loc.location_id=cus.`in_location_location_id`  "
        +" LEFT JOIN `bt_service_appointment` btsa ON btsa.`service_appointment_id`=rma.service_appointment_service_appointment_id "
        +" LEFT JOIN `bt_customer_inventory_out` cout ON cout.id=cus.id "
        +"     LEFT JOIN `bt_service_order_item` soi ON soi.`in_customer_inventory_id`=cus.id "
    	+"     LEFT JOIN `bt_job_card` jc ON jc.`service_order_item_id`= soi.id "
        +" LEFT JOIN `bt_case` bcs ON bcs.id=rma.case_details_id where 1=1");

        if(locationIds!=null){
            if(locationIds.size()>0){
                String loc="";
                for(int i=0;i<locationIds.size();i++){
                    if(i>0)
                        loc+=",";
                    loc+=locationIds.get(i);
                }
                sb.append(" and (loc.location_id in ("+loc+"))");
            }
        }

        if(request.get("caseManualId")!=null&&!request.get("caseManualId").toString().equalsIgnoreCase("")){
            sb.append(" and (bcs.manual_id like ? or cus.manual_id like ?)");
            param.add(prepareLikeStatement(request.get("caseManualId")));
            param.add(prepareLikeStatement(request.get("caseManualId")));
        }
        if(request.get("serviceAppointmentManualId")!=null&&!request.get("serviceAppointmentManualId").toString().equalsIgnoreCase("")){
            sb.append(" and btsa.manual_id like ?");
            param.add(prepareLikeStatement(request.get("serviceAppointmentManualId")));
        }
        if(request.get("customerInventoryAssetsSerialNumber")!=null&&!request.get("customerInventoryAssetsSerialNumber").toString().equalsIgnoreCase("")){
            sb.append(" and cus.assets_serial_number like ?");
            param.add(prepareLikeStatement(request.get("customerInventoryAssetsSerialNumber")));
        }
        if(request.get("customerInventoryProductPartcode")!=null&&!request.get("customerInventoryProductPartcode").toString().equalsIgnoreCase("")){
            sb.append(" and cus.product_partcode like ?");
            param.add(prepareLikeStatement(request.get("customerInventoryProductPartcode")));
        }
        if(request.get("locationName")!=null&&!request.get("locationName").toString().equalsIgnoreCase("")){
            sb.append(" and loc.location_name like ?");
            param.add(prepareLikeStatement(request.get("locationName")));
        }
        if(request.get("rmaManualId")!=null&&!request.get("rmaManualId").toString().equalsIgnoreCase("")){
            sb.append(" and rma.manual_id like ?");
            param.add(prepareLikeStatement(request.get("rmaManualId")));
        }

        if(request.get("productDescription")!=null&&!request.get("productDescription").toString().equalsIgnoreCase("")){
            sb.append(" and btprd.product_description like ?");
            param.add(prepareLikeStatement(request.get("productDescription")));
        }

        if(request.get("contactName")!=null&&!request.get("contactName").toString().equalsIgnoreCase("")){
            sb.append(" and cus.`contact_name` like ?");
            param.add(prepareLikeStatement(request.get("contactName")));
        }

        if(request.get("contactNumber")!=null&&!request.get("contactNumber").toString().equalsIgnoreCase("")){
            sb.append(" and cus.`contact_number` like ? ");
            param.add(prepareLikeStatement(request.get("contactNumber")));
        }

        if(request.get("customerCourierpartnerName")!=null&&request.get("customerCourierpartnerName").toString().equalsIgnoreCase("")){
            sb.append(" and cus.customer_courier_partnar_name like ? ");
            param.add(prepareLikeStatement(request.get("customerCourierpartnerName")));
        }

        if(request.get("customerInventoryStage")!=null&&request.get("customerInventoryStage").toString().equalsIgnoreCase("")){
            sb.append(" and cus.customer_inventory_stage like ? ");
            param.add(prepareLikeStatement(request.get("customerInventoryStage")));
        }
        if(request.get("customerInventoryStatu")!=null&&request.get("customerInventoryStatu").toString().equalsIgnoreCase("")){
            sb.append(" and cus.customer_inventory_status like ? ");
            param.add(prepareLikeStatement(request.get("customerInventoryStatu")));
        }

        if(request.get("customerlrnumber")!=null&&!request.get("customerlrnumber").toString().equalsIgnoreCase("")){
            sb.append(" and cus.customerlrnumber like ? ");
            param.add(prepareLikeStatement(request.get("customerlrnumber")));
        }

        if(request.get("customerPlant")!=null && !request.get("customerPlant").toString().equalsIgnoreCase("")){
            sb.append("and cus.customer_plant like ? ");
            param.add(prepareLikeStatement(request.get("customerPlant")));
        }

        if(request.get("packingCondation")!=null && !request.get("packingCondation").toString().equalsIgnoreCase("")){
            sb.append(" and cus.packing_condation like ? ");
            param.add(prepareLikeStatement(request.get("packingCondation")));
        }
        if(request.get("physicalDamage")!=null && !request.get("physicalDamage").toString().equalsIgnoreCase("")){
            sb.append(" and cus.physical_damage like ? ");
            param.add(prepareLikeStatement(request.get("physicalDamage")));
        }

        
        System.out.println(sb.toString());
        return jdbc.queryForList(sb.toString(),param.toArray());
    }

    String prepareLikeStatement(Object obj){
        return "%"+obj+"%";
    }
    
}
