package com.pratikshat.exicom.workingtemplate;



import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;


import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.BeanUtil;
import com.pratikshat.exicom.entity.*;
import com.pratikshat.exicom.utils.*;
public class workflowTemplate {

    void executeWorkflow(EntityManager em,Object object,LoginUser user) throws BadRequestException,UnauthoriseException{
       BtCase btCase=(BtCase)object;
       if(btCase.isRma()){
           try{
                BtRMADetails _check=em.createQuery("from BtRMADetails rma where rma.caseDetails=?1",BtRMADetails.class).setParameter(1, btCase).getSingleResult();
           }catch(javax.persistence.NoResultException ex){
            com.pratikshat.exicom.repository.BtRmaDetailsRepository dbRma= com.pratikshat.exicom.interceptor.BeanUtil.getBean(com.pratikshat.exicom.repository.BtRmaDetailsRepository.class);
            BtRMADetails _rma=new BtRMADetails();

            _rma.setCaseDetails(btCase);
            _rma.setOwner(new EmployeeDetails(user.getUsername()));
            _rma.setRmaStatus("Approved");
            dbRma.saveDetails(_rma, user);
            
            

           }catch(javax.persistence.NonUniqueResultException ex){

           }
          
       } 

    }
}
