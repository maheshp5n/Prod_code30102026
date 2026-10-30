//package com.pratikshat.exicom.repository.custom.impl;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.pratikshat.exicom.entity.BtCustomerWccFilesUpload;
//import com.pratikshat.exicom.entity.LoginUser;
//import com.pratikshat.exicom.exception.BadRequestException;
//import com.pratikshat.exicom.exception.UnauthoriseException;
//import com.pratikshat.exicom.interceptor.WorkflowExecuter;
//import com.pratikshat.exicom.repository.custom.BtCustomerWccFilesUploadRepositoryCustom;
//
//public class BtCustomerWccFilesUploadRepositoryCustomImpl implements BtCustomerWccFilesUploadRepositoryCustom{
//
//	@PersistenceContext
//	EntityManager em;
//	
//	@Autowired
//	WorkflowExecuter executer;
//	
//	
//	@Override
//	public BtCustomerWccFilesUpload saveDetails(BtCustomerWccFilesUpload t, LoginUser user)
//			throws BadRequestException, UnauthoriseException {
//		executer.executeWorkflow(em, t, "INSERT", "PRE", user);
//		em.persist(t);
//		executer.executeWorkflow(em, t, "INSERT", "POST", user);
//		// TODO Auto-generated method stub
//		return t;
//	}
//
//	@Override
//	public BtCustomerWccFilesUpload updateDetails(BtCustomerWccFilesUpload t, LoginUser user)
//			throws BadRequestException, UnauthoriseException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public BtCustomerWccFilesUpload deleteDetails(BtCustomerWccFilesUpload t, LoginUser user)
//			throws BadRequestException, UnauthoriseException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//
//}
