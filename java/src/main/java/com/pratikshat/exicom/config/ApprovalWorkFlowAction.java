package com.pratikshat.exicom.config;

import com.pratikshat.exicom.entity.BtApprovalStapes;
import com.pratikshat.exicom.entity.LoginUser;

public interface ApprovalWorkFlowAction {
	
	public void onApproval(BtApprovalStapes approval,Object object,String approverAction,LoginUser user,javax.persistence.EntityManager _em)throws com.pratikshat.exicom.exception.BadRequestException,com.pratikshat.exicom.exception.UnauthoriseException;
	public void onRejected(BtApprovalStapes approval,Object object,String approverAction,LoginUser user,javax.persistence.EntityManager _em)throws com.pratikshat.exicom.exception.BadRequestException,com.pratikshat.exicom.exception.UnauthoriseException;
}
