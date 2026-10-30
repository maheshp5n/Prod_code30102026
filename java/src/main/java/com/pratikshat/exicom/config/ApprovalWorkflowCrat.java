package com.pratikshat.exicom.config;

import javax.persistence.EntityManager;

import com.pratikshat.exicom.entity.BtApprovalStapes;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

public interface ApprovalWorkflowCrat {
	public boolean isValid(Object obj,String dmlCommand,LoginUser user,EntityManager em,BtApprovalStapes stape) throws BadRequestException,UnauthoriseException;
}
