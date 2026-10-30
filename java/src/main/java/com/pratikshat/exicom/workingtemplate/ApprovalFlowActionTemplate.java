package com.pratikshat.exicom.workingtemplate;

import javax.persistence.EntityManager;

import com.pratikshat.exicom.config.ApprovalWorkFlowAction;
import com.pratikshat.exicom.entity.BtApprovalStapes;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

public class ApprovalFlowActionTemplate implements ApprovalWorkFlowAction {

    @Override
    public void onApproval(BtApprovalStapes approval, Object object, String approverAction, LoginUser user,
            EntityManager _em) throws BadRequestException, UnauthoriseException {
        
    }

    @Override
    public void onRejected(BtApprovalStapes approval, Object object, String approverAction, LoginUser user,
            EntityManager _em) throws BadRequestException, UnauthoriseException {
        
    }
    
}
