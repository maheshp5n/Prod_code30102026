package com.pratikshat.exicom.workingtemplate;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.pratikshat.exicom.config.ApprovalWorkflowCrat;
import com.pratikshat.exicom.entity.BtApprovalStapes;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

import com.pratikshat.exicom.entity.*;
import com.pratikshat.exicom.utils.*;

import org.springframework.messaging.simp.SimpMessagingTemplate;

public class ApprovalFlowTemplate implements ApprovalWorkflowCrat {

    @Override
    public boolean isValid(Object obj, String dmlCommand, LoginUser user, EntityManager em, BtApprovalStapes stape)
            throws BadRequestException, UnauthoriseException {
        if(dmlCommand.contains("UPDATE")){
            BtExpenseManagement exp=(BtExpenseManagement)obj;
            if(exp.getStatus().getStatusId()==2){

                java.util.List<LoginUserRoles> userRoles=em.createQuery("select usr from LoginUserRoles usr join usr.loginUser lg where usr.authority=?1",LoginUserRoles.class)
                    .setParameter(1, "SCH_Battery")
                    .getResultList();
                if(userRoles!=null&&!userRoles.isEmpty()){
                    for(LoginUserRoles ur:userRoles){
                        ApplicationNotificationDetails details=new ApplicationNotificationDetails();
                        details.setNofificationDate(new java.util.Date());
                        details.setNotificationActionUrl("bat_expense/"+exp.getId());
                        details.setNotificationMessage("You have a new Expense to ");
                        details.setNotificationRead(false);
                        details.setUserEmailId(ur.getLoginUser().getUsername());
                        em.persist(details);
                        org.springframework.messaging.simp.SimpMessagingTemplate template= com.pratikshat.exicom.interceptor.BeanUtil.getBean(SimpMessagingTemplate.class);
                        template.convertAndSend( "/topic/"+ur.getLoginUser().getUsername(), details);
                    }
                    
    
                }
                
                return true;
            }
            else{
                return false;
            }
                
        }
		return false;
        
      
            
    }
    
}
