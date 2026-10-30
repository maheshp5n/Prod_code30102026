package com.pratikshat.exicom.repository.custom.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.nimbusds.oauth2.sdk.Request;
import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.BtPartcodeChange;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.repository.custom.BtPartcodeChangeRepositoryCustom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class BtPartcodeChangeRepositoryCustomImpl implements BtPartcodeChangeRepositoryCustom {

    @PersistenceContext EntityManager em;
    @Autowired WorkflowExecuter wExecuter;
    @Autowired JdbcTemplate jdbc;
    @Autowired UserProfileRepository dbProfile;

    @Override
    public BtPartcodeChange saveDetails(BtPartcodeChange t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        wExecuter.executeWorkflow(em, t, "INSERT", "PRE", user);
        em.persist(t);
        String manualId="PRC-BT-"+MiscellaneousUtils.fillZero(""+t.getId(), 5);
        t.setManualId(manualId);
        em.persist(t);
        wExecuter.executeWorkflow(em, t, "INSERT", "POST", user);
        return t;
    }

    @Override
    public BtPartcodeChange updateDetails(BtPartcodeChange t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        throw new BadRequestException("Function not supported");
    }

    @Override
    public BtPartcodeChange deleteDetails(BtPartcodeChange t, LoginUser user)
            throws BadRequestException, UnauthoriseException {
                throw new BadRequestException("Function not supported");
    }

    @Override
    public List<Map<String, Object>> advanceSearch(Map<String, Object> searchRequest, LoginUser user)
            throws BadRequestException, UnauthoriseException {
                StringBuilder sb=new StringBuilder();
                sb.append("SELECT "
                +" ch.`id` AS partCodeChangeId, "
                +" ch.`created_date` AS partCodeChangeCreatedDate, "
                +" ch.`last_modified_date` AS partCodeChnageLastModifiedDate, "
                +" ch.`manual_id` AS partCodeChangeManualId, "
                +" ch.`product_state` AS partCodeState, "
                +" ch.`quantity` AS partCodeChageQty, "
                +" ch.`created_by_email_id` AS partCodeCreatedBy, "
                +" ch.`last_modified_by_email_id` AS partCodeLastModifiedBy, "
                +" ch.`from_product_id` AS fromProductId, "
                +" ch.`location_location_id` AS locationId, "
                +" ch.`owner_email_id` AS partCodeOwnerEmailId, "
                +" ch.`to_product_id` AS toProductId, "
                +" toPrd.`manual_id` AS toPartCode, "
                +" toPrd.`product_type` AS toProductType, "
                +" toPrd.`product_description` AS toProductDescription, "
                +" fromPrd.`manual_id` AS fromPartCode, "
                +" fromPrd.`product_type` AS fromProductType, "
                +" fromPrd.`product_description` AS fromProductDescription, "
                +" loc.`location_name` AS locationName, "
                +" loc.plant AS locationPlant, "
                +" cemp.`first_name` AS createdByFirstName, "
                +" cemp.`middle_name` AS createdByMiddleName, "
                +" cemp.`last_name` AS createdByLastName, "
                
                +" oemp.`first_name` AS ownerFirstName, "
                +" oemp.`middle_name` AS ownerMiddleName, "
                +" oemp.`last_name` AS ownerLastName, "
                
                +" lemp.`first_name` AS lastModifiedByFirstName, "
                +" lemp.`middle_name` AS lastModifiedByMiddleName, "
                +" lemp.`last_name` AS lastModifiedByLastName "
                
            
                +" FROM `bt_partcode_change` ch  "
                +" JOIN bt_products toPrd ON toPrd.id=ch.`to_product_id` "
                +" JOIN bt_products fromPrd ON fromPrd.id=ch.`from_product_id` "
                +" JOIN location loc ON loc.location_id=ch.`location_location_id` "
                +" JOIN employee_details cemp ON cemp.`email_id`=ch.`created_by_email_id` "
                +" JOIN employee_details oemp ON oemp.`email_id`=ch.`owner_email_id` "
                +" JOIN employee_details lemp ON lemp.`email_id`=ch.`last_modified_by_email_id` where 1=1 ");
        List<Object> param=new ArrayList<Object>();
        if(dbProfile.hasObjectAccess(user.getUsername(), BtPartcodeChange.class, UserProfileRepository.VIEW_ALL)){
            if(!MiscellaneousUtils.objToString(searchRequest.get("toPartCode")).equalsIgnoreCase("")){
                sb.append(" and toPrd.`manual_id` like ?");
                param.add(MiscellaneousUtils.likeStatement(searchRequest.get("toPartCode")));
            }
            if(!MiscellaneousUtils.objToString(searchRequest.get("fromPartCode")).equalsIgnoreCase("")){
                sb.append(" and fromPrd.`manual_id` like ? ");
                param.add(MiscellaneousUtils.likeStatement(searchRequest.get("fromPartCode")));
            }

        }
        else if (dbProfile.hasObjectAccess(user.getUsername(), BtPartcodeChange.class, UserProfileRepository.VIEW)){
            

            if(!MiscellaneousUtils.objToString(searchRequest.get("toPartCode")).equalsIgnoreCase("")){
                sb.append(" and toPrd.`manual_id` like ?");
                param.add(MiscellaneousUtils.likeStatement(searchRequest.get("toPartCode")));
            }
            if(!MiscellaneousUtils.objToString(searchRequest.get("fromPartCode")).equalsIgnoreCase("")){
                sb.append(" and fromPrd.`manual_id` like ? ");
                param.add(MiscellaneousUtils.likeStatement(searchRequest.get("fromPartCode")));
            }
        }
        return jdbc.queryForList(sb.toString(),param.toArray());
    }
    
}
