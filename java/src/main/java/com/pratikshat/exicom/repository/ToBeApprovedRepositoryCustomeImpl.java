package com.pratikshat.exicom.repository;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.config.FileStorageProperties;
import com.pratikshat.exicom.entity.AnnexureLineItem;
import com.pratikshat.exicom.entity.ApplicationNotificationDetails;
import com.pratikshat.exicom.entity.Assets;
import com.pratikshat.exicom.entity.BatteryFSR;
import com.pratikshat.exicom.entity.BusinessDomain;
import com.pratikshat.exicom.entity.CaseDetails;
import com.pratikshat.exicom.entity.CaseStageDetails;
import com.pratikshat.exicom.entity.CaseStatusDetails;
import com.pratikshat.exicom.entity.ClaimStatus;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.ExpenseAccountsOnly;
import com.pratikshat.exicom.entity.ExpenseApproved;
import com.pratikshat.exicom.entity.ExpenseManagement;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.MailerEntity;
import com.pratikshat.exicom.entity.ObjectDefinition;
import com.pratikshat.exicom.entity.ObjectLog;
import com.pratikshat.exicom.entity.Oppertunity;
import com.pratikshat.exicom.entity.OppertunityForecastCategory;
import com.pratikshat.exicom.entity.OppertunityStage;
import com.pratikshat.exicom.entity.PendingResponsibility;
import com.pratikshat.exicom.entity.Quote;
import com.pratikshat.exicom.entity.QuotePdfHeaderAndFooter;
import com.pratikshat.exicom.entity.QuoteStatus;
import com.pratikshat.exicom.entity.ServiceAppointment;
import com.pratikshat.exicom.entity.ServiceRecource;
import com.pratikshat.exicom.entity.TemplateDetails;
import com.pratikshat.exicom.entity.TemplateType;
import com.pratikshat.exicom.entity.UploadedFiles;
import com.pratikshat.exicom.entity.VendorInvoiceDetails;
import com.pratikshat.exicom.entity.VendorInvoiceGrn;
import com.pratikshat.exicom.entity.VendorInvoiceSap;
import com.pratikshat.exicom.entity.WccDetails;
import com.pratikshat.exicom.entity.WccStatus;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.ApprovalRequest;
import com.pratikshat.exicom.model.response.QuoteLineItemResponse;
import com.pratikshat.exicom.utils.AnnexurePDFGeneration;
import com.pratikshat.exicom.utils.ExpenseApprovedAmountCalculation;
import com.pratikshat.exicom.utils.Mailer;
import com.pratikshat.exicom.utils.QuotePDFGeneration;
import com.pratikshat.exicom.utils.ServiceAppointmentUtil;
import com.pratikshat.exicom.utils.SmsNotifier;

@Repository
public class ToBeApprovedRepositoryCustomeImpl implements ToBeApprovedRepositoryCustome {

	@javax.persistence.PersistenceContext
	javax.persistence.EntityManager em;

	@Autowired
	JdbcTemplate jdbc;

	@Autowired
	SimpMessagingTemplate msgTemplate;

	@Autowired
	com.pratikshat.exicom.interceptor.WorkflowExecuter workflow;

	@Autowired
	FileStorageProperties fileStorageProperties;

	@Autowired
	UserProfileRepository dbProfile;

	Logger logger = LoggerFactory.getLogger(ToBeApprovedRepositoryCustomeImpl.class);
	
	@Value("${feedbackurl}")
	String feedbackurl;

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			Exception.class, RuntimeException.class, BadRequestException.class, UnauthoriseException.class })
	public ApprovalRequest approveReject(ApprovalRequest appRequest, EmployeeDetails emp, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		ObjectDefinition _obj = null;

		if (appRequest.getRemark() == null || appRequest.getRemark().equalsIgnoreCase("")) {
			throw new BadRequestException("Please write remark");
		}

		if (appRequest.getAction() == null || appRequest.getAction().equalsIgnoreCase("")) {
			throw new BadRequestException("Please choose action");
		}

		String userName = "";
		EmployeeDetails currentUser = em
				.createQuery("select usr from EmployeeDetails usr where usr.emailId=?1", EmployeeDetails.class)
				.setParameter(1, emp.getEmailId()).getSingleResult();

		if (currentUser != null && currentUser.getFirstName() != null)
			userName = currentUser.getFirstName();
		if (currentUser != null && currentUser.getMiddleName() != null)
			userName = userName + " " + currentUser.getMiddleName();
		if (currentUser != null && currentUser.getLastName() != null)
			userName = userName + " " + currentUser.getLastName();

		String __assignedUser;
		com.pratikshat.exicom.entity.ToBeApproved _toBeApproved = null;
		try {
			_toBeApproved = em
					.createQuery("select apr from ToBeApproved apr join apr.assignedTo asg where apr.id=?1",
							com.pratikshat.exicom.entity.ToBeApproved.class)
					.setParameter(1, appRequest.getApprovalId()).getSingleResult();
		} catch (javax.persistence.NoResultException ex) {
			throw new BadRequestException("No data found");
		}
		__assignedUser = _toBeApproved.getAssignedTo().getEmailId();
		_toBeApproved.setActionBy(new EmployeeDetails(user.getUsername()));
		//validation block to prevant self expense approval
		if (_toBeApproved.getObjectName().equalsIgnoreCase("ExpenseManagement")) {

			try {
				Field[] fields = Class.forName("com.pratikshat.exicom.entity." + _toBeApproved.getObjectName())
						.getDeclaredFields();
				String fieldName = null;

				for (Field field : fields) {
					if (field.getName().contains("owner")) {
						fieldName = field.getName();
						break;
					}
				}

				com.pratikshat.exicom.entity.ApprovalSteps _stapes = null;
				try {
					System.out.printf("Approval Rule Id =%d and approval stage =%d\n",
							_toBeApproved.getApprovalRuleId(), _toBeApproved.getStage());

					_stapes = em.createQuery(
							"select stps from ApprovalSteps stps join stps.approval apr where apr=?1 and stps.approvedStage=?2",
							com.pratikshat.exicom.entity.ApprovalSteps.class)
							.setParameter(1,
									new com.pratikshat.exicom.entity.Approval(_toBeApproved.getApprovalRuleId()))
							.setParameter(2, _toBeApproved.getStage()).getSingleResult();
				} catch (javax.persistence.NoResultException ex) {
					throw new BadRequestException("Invalid Request stapes not match");
				}
				EmployeeDetails employeeDetails = null;
				if (fieldName != null) {
					String selectOwnerQuery = String.format(
							"Select onr from %s obj join obj.%s onr left join onr.contact ct where obj.%s = ?1",
							_toBeApproved.getObjectName(), fieldName, _stapes.getApproval().getObjectIdFiledName());
					employeeDetails = em.createQuery(selectOwnerQuery, EmployeeDetails.class)
							.setParameter(1, _toBeApproved.getObjectId()).getSingleResult();
					if (employeeDetails != null) {
						if (employeeDetails.getEmailId().equalsIgnoreCase(user.getUsername()))
							throw new BadRequestException("You can not approve/reject self request");
					}
				}
				
				if (!__assignedUser.equalsIgnoreCase(emp.getEmailId())) {

					if (!dbProfile.samroleUsers(emp.getEmailId(), __assignedUser)) {
						Map<String, String> assingRole = dbProfile.getAccountAndRoleGroup(new LoginUser(__assignedUser));
						String __usrgrp = assingRole.get("roleGroup");
						if (__usrgrp.equalsIgnoreCase("COMMERCIAL") || __usrgrp.equalsIgnoreCase("CUSTOMERCARE")
								|| __usrgrp.equalsIgnoreCase("FINANCE") || __usrgrp.equalsIgnoreCase("DCT_MAIN_STORE")) {
							Map<String, String> loginuserRole = dbProfile.getAccountAndRoleGroup(user);
							if (!loginuserRole.get("roleGroup").equalsIgnoreCase(__usrgrp)) {
								throw new UnauthoriseException("You are not authorised");
							}
							
							// If User and Initiator is in same role group then throw Unauthorized error
							Map<String, String> initiatorRole = dbProfile.getAccountAndRoleGroup(new LoginUser(employeeDetails.getEmailId()));
							String initiatorRoleGroup = initiatorRole.get("roleGroup");
							if(loginuserRole.get("roleGroup").equalsIgnoreCase(initiatorRoleGroup)) {
								throw new UnauthoriseException("You are not authorised");
							}
							
						} else
							throw new UnauthoriseException("You are not authorised");
					}

				}

			}

			catch (Exception ex) {
				throw new BadRequestException(ex.getMessage());
			}

		}
		
		

		//end
		
		//INVOICE REJECTION
		if(_toBeApproved.getObjectName().equalsIgnoreCase("VendorInvoiceDetails")) {
			
			VendorInvoiceDetails vd=null;
			try {
				vd=em.createQuery("select vd from VendorInvoiceDetails vd join vd.wccDetails wcc where vd=?1",VendorInvoiceDetails.class)
						.setParameter(1,new VendorInvoiceDetails(_toBeApproved.getObjectId()))
						.getSingleResult();
				
				if(appRequest.getAction().trim().equalsIgnoreCase("Rejected")
				|| appRequest.getAction().trim().equalsIgnoreCase("Reject")) {
					WccDetails wcc=vd.getWccDetails();
					wcc.setWccStatus(new WccStatus(3));
					em.persist(wcc);
				}
				
			}catch(NoResultException ex) {}
			catch(NonUniqueResultException ex) {}
		}
		
		
		//
		

		if (!__assignedUser.equalsIgnoreCase(emp.getEmailId())) {

			if (!dbProfile.samroleUsers(emp.getEmailId(), __assignedUser)) {
				Map<String, String> assingRole = dbProfile.getAccountAndRoleGroup(new LoginUser(__assignedUser));
				String __usrgrp = assingRole.get("roleGroup");
				if (__usrgrp.equalsIgnoreCase("COMMERCIAL") || __usrgrp.equalsIgnoreCase("CUSTOMERCARE")
						|| __usrgrp.equalsIgnoreCase("FINANCE") || __usrgrp.equalsIgnoreCase("DCT_MAIN_STORE")) {
					Map<String, String> loginuserRole = dbProfile.getAccountAndRoleGroup(user);
					if (!loginuserRole.get("roleGroup").equalsIgnoreCase(__usrgrp)) {
						throw new UnauthoriseException("You are not authorised");
					}
				} else
					throw new UnauthoriseException("You are not authorised");
			}

		}
		if (_toBeApproved.isStatus()) {
			throw new BadRequestException("Already submitted");
		}

		_toBeApproved.setAction(appRequest.getAction());
		_toBeApproved.setApprovedDate(new java.util.Date());
		_toBeApproved.setRemark(appRequest.getRemark());
		_toBeApproved.setStatus(true);
		_toBeApproved.setActionBy(new EmployeeDetails(user.getUsername()));
		em.persist(_toBeApproved);

		try {
			_obj = em.createQuery("select obj from ObjectDefinition obj where obj=?1", ObjectDefinition.class)
					.setParameter(1, new ObjectDefinition(_toBeApproved.getObjectName())).getSingleResult();
		} catch (Exception ex) {

		}

		if (appRequest.getAction().trim().equalsIgnoreCase("Re-Assign")) {
			if (appRequest.getReAssigned() == null || appRequest.getReAssigned().equalsIgnoreCase("")) {
				throw new BadRequestException("Re assigned data not found");
			}
			com.pratikshat.exicom.entity.ToBeApproved _toBeReAssign = new com.pratikshat.exicom.entity.ToBeApproved();
			_toBeReAssign.setAssignedDate(new java.util.Date());
			_toBeReAssign.setAssignedTo(new com.pratikshat.exicom.entity.EmployeeDetails(appRequest.getReAssigned()));
			_toBeReAssign.setObjectId(_toBeApproved.getObjectId());
			_toBeReAssign.setObjectManualId(_toBeApproved.getObjectManualId());
			_toBeReAssign.setObjectName(_toBeApproved.getObjectName());
			_toBeReAssign.setObjectUrl(_toBeApproved.getObjectUrl());
			_toBeReAssign.setStage(_toBeApproved.getStage());
			_toBeReAssign.setStatus(false);
			em.persist(_toBeReAssign);
		} else if (appRequest.getAction().trim().equalsIgnoreCase("Rejected")
				|| appRequest.getAction().trim().equalsIgnoreCase("Reject")) {

			com.pratikshat.exicom.entity.ApprovalSteps _stapes = null;
			try {
				System.out.printf("Approval Rule Id =%d and approval stage =%d\n", _toBeApproved.getApprovalRuleId(),
						_toBeApproved.getStage());

				_stapes = em.createQuery(
						"select stps from ApprovalSteps stps join stps.approval apr where apr=?1 and stps.approvedStage=?2",
						com.pratikshat.exicom.entity.ApprovalSteps.class)
						.setParameter(1, new com.pratikshat.exicom.entity.Approval(_toBeApproved.getApprovalRuleId()))
						.setParameter(2, _toBeApproved.getStage()).getSingleResult();
			} catch (javax.persistence.NoResultException ex) {
				throw new BadRequestException("Invalid Request stapes not match");
			}

			String idOldValue = "";
			String lastModifiedByColumnName = "";
			String lastModifiedDateColumnName = "";

			// FIND LAST MODIFIER COLUMN NAME
			try {
				Field[] _fields = Class.forName("com.pratikshat.exicom.entity." + _toBeApproved.getObjectName())
						.getDeclaredFields();
				for (Field fld : _fields) {
					if (fld.getName().toLowerCase().contains("modified")
							&& fld.getType().getName().contains("EmployeeDetails")) {
						lastModifiedByColumnName = fld.getName();
					}
					if (fld.getName().toLowerCase().contains("modified") && fld.getType().getName().contains("Date"))
						lastModifiedDateColumnName = fld.getName();
				}

				EmployeeDetails lastMofifier = null;
				Date lastMofiedDate = null;
				String lastMofifierQuery = String.format("select obj.%s from %s obj where obj.%s=?1",
						lastModifiedByColumnName, _stapes.getApproval().getObjectClassName(),
						_stapes.getApproval().getObjectIdFiledName());
				String lastModifierDateQuery = String.format("select obj.%s from %s obj where obj.%s=?1",
						lastModifiedDateColumnName, _stapes.getApproval().getObjectClassName(),
						_stapes.getApproval().getObjectIdFiledName());

				lastMofifier = em.createQuery(lastMofifierQuery, EmployeeDetails.class)
						.setParameter(1, _toBeApproved.getObjectId()).getSingleResult();
				lastMofiedDate = em.createQuery(lastModifierDateQuery, Date.class)
						.setParameter(1, _toBeApproved.getObjectId()).getSingleResult();
				logger.info(lastModifierDateQuery);
				logger.info(lastMofifierQuery);

				if (!emp.getEmailId().equalsIgnoreCase(lastMofifier.getEmailId())) {
					ObjectLog dLog = new ObjectLog();
					dLog.setCreatedDate(new Date());
					dLog.setKeyName(lastModifiedByColumnName);
					dLog.setLoggedBy(emp);
					dLog.setObjectId(String.valueOf(_toBeApproved.getObjectId()));
					dLog.setObjectName(_stapes.getApproval().getObjectClassName());
					dLog.setOldValue(lastMofifier.getEmailId());
					dLog.setValue(emp.getEmailId());
					em.persist(dLog);
				}
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				ObjectLog sLog = new ObjectLog();
				sLog.setCreatedDate(new Date());
				sLog.setKeyName(lastModifiedDateColumnName);
				sLog.setLoggedBy(emp);
				sLog.setObjectId(String.valueOf(_toBeApproved.getObjectId()));
				sLog.setObjectName(_stapes.getApproval().getObjectClassName());
				sLog.setOldValue(formatter.format(lastMofiedDate));
				sLog.setValue(formatter.format(new Date()));
				em.persist(sLog);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			String queryOldValue = String.format("select COALESCE(obj.%s,'') from %s obj where obj.%s=?1",
					_stapes.getObjectFiledName(), _stapes.getApproval().getObjectClassName(),
					_stapes.getApproval().getObjectIdFiledName());
			String updateQueryhql = String.format("update %s obj set obj.%s=?1,obj.%s=?2,obj.%s=?3 where obj.%s=?4",
					_stapes.getApproval().getObjectClassName(), _stapes.getObjectFiledName(), lastModifiedByColumnName,
					lastModifiedDateColumnName, _stapes.getApproval().getObjectIdFiledName());

			logger.info(queryOldValue);
			logger.info(updateQueryhql);

			String objectName = _toBeApproved.getAction().equalsIgnoreCase("Approved") ? _stapes.getOnAprovedValue()
					: _stapes.getOnRejectedValue();

			ObjectLog oLog = new ObjectLog();
			oLog.setCreatedDate(new Date());
			oLog.setKeyName(_stapes.getObjectFiledName().split("\\.")[0]);
			oLog.setLoggedBy(currentUser);
			oLog.setObjectId(String.valueOf(_toBeApproved.getObjectId()));
			oLog.setObjectName(_stapes.getApproval().getObjectClassName());
			oLog.setValue(objectName);
			// oLog.setOldValue(oldValue);

			try {
				long objparam = Long.valueOf(objectName);
				idOldValue = em.createQuery(queryOldValue).setParameter(1, _toBeApproved.getObjectId())
						.getSingleResult().toString();
				em.createQuery(updateQueryhql).setParameter(1, objparam).setParameter(2, emp)
						.setParameter(3, new Date()).setParameter(4, _toBeApproved.getObjectId()).executeUpdate();

			} catch (Exception ex) {
				try {
					int objparam = Integer.valueOf(objectName);
					idOldValue = em.createQuery(queryOldValue).setParameter(1, _toBeApproved.getObjectId())
							.getSingleResult().toString();
					em.createQuery(updateQueryhql).setParameter(1, objparam).setParameter(2, emp)
							.setParameter(3, new Date()).setParameter(4, _toBeApproved.getObjectId()).executeUpdate();
				} catch (Exception _ei) {
					idOldValue = em.createQuery(queryOldValue).setParameter(1, _toBeApproved.getObjectId())
							.getSingleResult().toString();
					em.createQuery(updateQueryhql).setParameter(1, objectName).setParameter(2, emp)
							.setParameter(3, new Date()).setParameter(4, _toBeApproved.getObjectId()).executeUpdate();
				}
			}
			oLog.setOldValue(idOldValue);
			em.persist(oLog);

			try {
				Field[] fields = Class.forName("com.pratikshat.exicom.entity." + _toBeApproved.getObjectName())
						.getDeclaredFields();

				String fieldName = null;

				for (Field field : fields) {
					if (field.getName().contains("owner")) {
						fieldName = field.getName();
						break;
					}
				}

				if (fieldName != null && _stapes != null) {

					String selectOwnerQuery = String.format(
							"Select onr from %s obj join obj.%s onr left join onr.contact ct where obj.%s = ?1",
							_toBeApproved.getObjectName(), fieldName, _stapes.getApproval().getObjectIdFiledName());
					EmployeeDetails employeeDetails = em.createQuery(selectOwnerQuery, EmployeeDetails.class)
							.setParameter(1, _toBeApproved.getObjectId()).getSingleResult();
					if (employeeDetails != null) {

						com.pratikshat.exicom.entity.ApplicationNotificationDetails notificationDetails = new com.pratikshat.exicom.entity.ApplicationNotificationDetails();
						notificationDetails.setNofificationDate(new java.util.Date());
						notificationDetails.setNotificationActionUrl(_toBeApproved.getObjectUrl());

						notificationDetails.setNotificationMessage(String.format("%s Request %s rejected by %s",
								_obj == null ? _toBeApproved.getObjectName() : _obj.getObjectLableName(),
								_toBeApproved.getObjectManualId(), userName));
						notificationDetails.setNotificationRead(false);
						notificationDetails.setUserEmailId(employeeDetails.getEmailId());
						try {
							em.persist(notificationDetails);
						} catch (Exception ex) {
						}
						try {

							msgTemplate.convertAndSend("/topic/" + employeeDetails.getEmailId(), notificationDetails);

						} catch (Exception ex) {
							ex.printStackTrace();
						}
						
						// Rejection SMS notification
						
						try {
							if (employeeDetails.getContact().getMobile() != null) {
								
								switch (_stapes.getApproval().getObjectClassName()) {
								
								case "ServiceAppointment" : {
									TemplateDetails	templateDetails = null;
									
									try {
										Query templateQuery=em.createQuery("select tmpd from TemplateDetails tmpd where tmpd.active=?1 and tmpd.templateType=?2 and tmpd.businessDomain=?3 and tmpd.eventName=?4",TemplateDetails.class);
										templateQuery.setParameter(1,true);
										templateQuery.setParameter(2,TemplateType.SMS);
										templateQuery.setParameter(3,new BusinessDomain(2));
										templateQuery.setParameter(4,"sa_expense_reject");

										templateDetails = (TemplateDetails) templateQuery.getSingleResult();
									}catch(NoResultException nex) {
										nex.printStackTrace();
									}catch (NonUniqueResultException e) {
										e.printStackTrace();
									}
									String smsMessage=null;
									if(templateDetails!=null) {
										smsMessage = templateDetails.getBody();
										smsMessage = smsMessage.replaceFirst("<var>", _toBeApproved.getObjectManualId());
										smsMessage = smsMessage.replaceFirst("<var>", userName);
									}
									
									if(smsMessage!=null && !smsMessage.equalsIgnoreCase("")) {
										SmsNotifier sNotifier = new SmsNotifier(em, employeeDetails.getContact().getMobile(),smsMessage, emp.getEmailId(),templateDetails.getTemplaeCode());
										sNotifier.sendNotification();
									}
									
									break;
								}
								case "ExpenseManagement" : {
									TemplateDetails	templateDetails = null;
									
									try {
										Query templateQuery=em.createQuery("select tmpd from TemplateDetails tmpd where tmpd.active=?1 and tmpd.templateType=?2 and tmpd.businessDomain=?3 and tmpd.eventName=?4",TemplateDetails.class);
										templateQuery.setParameter(1,true);
										templateQuery.setParameter(2,TemplateType.SMS);
										templateQuery.setParameter(3,new BusinessDomain(2));
										templateQuery.setParameter(4,"expense_reject");

										templateDetails = (TemplateDetails) templateQuery.getSingleResult();
									}catch(NoResultException nex) {
										nex.printStackTrace();
									}catch (NonUniqueResultException e) {
										e.printStackTrace();
									}
									String smsMessage=null;
									if(templateDetails!=null) {
										smsMessage = templateDetails.getBody();
										smsMessage = smsMessage.replaceFirst("<var>", _toBeApproved.getObjectManualId());
										smsMessage = smsMessage.replaceFirst("<var>", userName);
										smsMessage = smsMessage.replaceFirst("<var>", feedbackurl+"/#/expensesmanagement/"+_toBeApproved.getObjectId());
									}
									
									if(smsMessage!=null && !smsMessage.equalsIgnoreCase("")) {
										SmsNotifier sNotifier = new SmsNotifier(em, employeeDetails.getContact().getMobile(),smsMessage, emp.getEmailId(),templateDetails.getTemplaeCode());
										sNotifier.sendNotification();
									}
									break;
								}
								case "CaseDetails" : {
									
									String selectSAOwnerQuery = "Select onr from ServiceAppointment sa join sa.caseDetails cd join sa.ownerDetails onr left join onr.contact ct where cd.caseId = ?1 ORDER BY sa.createDate DESC";
									EmployeeDetails saOwner = em.createQuery(selectSAOwnerQuery, EmployeeDetails.class)
											.setParameter(1, _toBeApproved.getObjectId()).getResultList().get(0);
									
									TemplateDetails	templateDetails = null;
									
									try {
										Query templateQuery=em.createQuery("select tmpd from TemplateDetails tmpd where tmpd.active=?1 and tmpd.templateType=?2 and tmpd.businessDomain=?3 and tmpd.eventName=?4",TemplateDetails.class);
										templateQuery.setParameter(1,true);
										templateQuery.setParameter(2,TemplateType.SMS);
										templateQuery.setParameter(3,new BusinessDomain(2));
										templateQuery.setParameter(4,"request_for_sa_reject");

										templateDetails = (TemplateDetails) templateQuery.getSingleResult();
									}catch(NoResultException nex) {
										nex.printStackTrace();
									}catch (NonUniqueResultException e) {
										e.printStackTrace();
									}
									
									String smsMessage = null;
									if(templateDetails!=null) {
										smsMessage = templateDetails.getBody();
										smsMessage = smsMessage.replaceFirst("<var>", _toBeApproved.getObjectManualId());
										smsMessage = smsMessage.replaceFirst("<var>", userName);
										smsMessage = smsMessage.replaceFirst("<var>", feedbackurl+"/#/cases/"+_toBeApproved.getObjectId());
									}
									
									if(smsMessage!=null && !smsMessage.equalsIgnoreCase("")) {
										SmsNotifier sNotifier = new SmsNotifier(em, saOwner.getContact().getMobile(),smsMessage, emp.getEmailId(),templateDetails.getTemplaeCode());
										sNotifier.sendNotification();
									}
									
									break;
								}
								case "Quote": {
									
									TemplateDetails	templateDetails = null;
									
									try {
										Query templateQuery=em.createQuery("select tmpd from TemplateDetails tmpd where tmpd.active=?1 and tmpd.templateType=?2 and tmpd.businessDomain=?3 and tmpd.eventName=?4",TemplateDetails.class);
										templateQuery.setParameter(1,true);
										templateQuery.setParameter(2,TemplateType.SMS);
										templateQuery.setParameter(3,new BusinessDomain(2));
										templateQuery.setParameter(4,"quote_reject");

										templateDetails = (TemplateDetails) templateQuery.getSingleResult();
									}catch(NoResultException nex) {
										nex.printStackTrace();
									}catch (NonUniqueResultException e) {
										e.printStackTrace();
									}
									String smsMessage=null;
									if(templateDetails!=null) {
										smsMessage = templateDetails.getBody();
										smsMessage = smsMessage.replaceFirst("<var>", _toBeApproved.getObjectManualId());
										smsMessage = smsMessage.replaceFirst("<var>", userName);
										smsMessage = smsMessage.replaceFirst("<var>", feedbackurl+"/#/Quote/"+_toBeApproved.getObjectId());
									}
									
									if(smsMessage!=null && !smsMessage.equalsIgnoreCase("")) {
										SmsNotifier sNotifier = new SmsNotifier(em, employeeDetails.getContact().getMobile(),smsMessage, emp.getEmailId(),templateDetails.getTemplaeCode());
										sNotifier.sendNotification();
									}
									
									break;
								}
								case "WccDetails" : {
									
									TemplateDetails	templateDetails = null;
									
									try {
										Query templateQuery=em.createQuery("select tmpd from TemplateDetails tmpd where tmpd.active=?1 and tmpd.templateType=?2 and tmpd.businessDomain=?3 and tmpd.eventName=?4",TemplateDetails.class);
										templateQuery.setParameter(1,true);
										templateQuery.setParameter(2,TemplateType.SMS);
										templateQuery.setParameter(3,new BusinessDomain(2));
										templateQuery.setParameter(4,"wcc_reject");

										templateDetails = (TemplateDetails) templateQuery.getSingleResult();
									}catch(NoResultException nex) {
										nex.printStackTrace();
									}catch (NonUniqueResultException e) {
										e.printStackTrace();
									}
									String smsMessage=null;
									if(templateDetails!=null) {
										smsMessage = templateDetails.getBody();
										smsMessage = smsMessage.replaceFirst("<var>", _toBeApproved.getObjectManualId());
										smsMessage = smsMessage.replaceFirst("<var>", userName);
										smsMessage = smsMessage.replaceFirst("<var>", feedbackurl+"/#/wcc/"+_toBeApproved.getObjectId());
									}
									
									if(smsMessage!=null && !smsMessage.equalsIgnoreCase("")) {
										SmsNotifier sNotifier = new SmsNotifier(em, employeeDetails.getContact().getMobile(),smsMessage, emp.getEmailId(),templateDetails.getTemplaeCode());
										sNotifier.sendNotification();
									}
									
									break;
								}
								case "VendorInvoiceDetails" : {
									
									TemplateDetails	templateDetails = null;
									
									try {
										Query templateQuery=em.createQuery("select tmpd from TemplateDetails tmpd where tmpd.active=?1 and tmpd.templateType=?2 and tmpd.businessDomain=?3 and tmpd.eventName=?4",TemplateDetails.class);
										templateQuery.setParameter(1,true);
										templateQuery.setParameter(2,TemplateType.SMS);
										templateQuery.setParameter(3,new BusinessDomain(2));
										templateQuery.setParameter(4,"invoice_reject");

										templateDetails = (TemplateDetails) templateQuery.getSingleResult();
									}catch(NoResultException nex) {
										nex.printStackTrace();
									}catch (NonUniqueResultException e) {
										e.printStackTrace();
									}
									String smsMessage=null;
									if(templateDetails!=null) {
										smsMessage = templateDetails.getBody();
										smsMessage = smsMessage.replaceFirst("<var>", _toBeApproved.getObjectManualId());
										smsMessage = smsMessage.replaceFirst("<var>", userName);
										smsMessage = smsMessage.replaceFirst("<var>", feedbackurl+"/#/invoice/"+_toBeApproved.getObjectId());
									}
									
									if(smsMessage!=null && !smsMessage.equalsIgnoreCase("")) {
										SmsNotifier sNotifier = new SmsNotifier(em, employeeDetails.getContact().getMobile(),smsMessage, emp.getEmailId(),templateDetails.getTemplaeCode());
										sNotifier.sendNotification();
									}
									
									break;
								}
							}
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}

			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			switch (_stapes.getApproval().getObjectClassName()) {
			case "Quote": {
//				if(_stapes.getApproval().getApprovalStages()>_toBeApproved.getStage()) {
//					
//				}else {
//					
//				}

				Quote quote = null;
				try {
					Query selectQuoteQuery = em.createQuery(
							"select qt from Quote qt left join qt.oppertunity opr left join opr.accounts acn left join opr.caseDetails cd left join cd.site st left join st.circle crl left join st.serviceCenter lgl where qt.id=?1",
							Quote.class);
					selectQuoteQuery.setParameter(1, _toBeApproved.getObjectId());
					quote = (Quote) selectQuoteQuery.getSingleResult();
				} catch (NoResultException ex) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Quote does not exist");
				}

				if (quote == null) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Quote does not found");
				}
				sendMailToCustomerCareTeamOnRejection(quote, emp, em);
			}
				break;
			}
		} else if (appRequest.getAction().trim().equalsIgnoreCase("Approved")
				|| appRequest.getAction().trim().equalsIgnoreCase("Approve")) {
			com.pratikshat.exicom.entity.ApprovalSteps _stapes = null;

			// SPACIAL DEMAND OF MANPREET JUGAD INJECTION IN DEFAULT FLOW OF APPROVAL

			if (_toBeApproved.getObjectName().equalsIgnoreCase("VendorInvoiceDetails")) {
				if (_toBeApproved.getStage() == 1 || _toBeApproved.getStage()==2 )
					if (!checkVendorInvoiceGrn(em, _toBeApproved.getObjectId()))
						throw new BadRequestException("Please update GRN");
				if (_toBeApproved.getStage() == 4)
					if (!checckVendorInvocieSap(em, _toBeApproved.getObjectId()))
						throw new BadRequestException("Please update SAP");
			}

			// END

			try {
				System.out.printf("Approval Rule Id =%d and approval stage =%d\n", _toBeApproved.getApprovalRuleId(),
						_toBeApproved.getStage());
				_stapes = em.createQuery(
						"select stps from ApprovalSteps stps join stps.approval apr where apr=?1 and stps.approvedStage=?2",
						com.pratikshat.exicom.entity.ApprovalSteps.class)
						.setParameter(1, new com.pratikshat.exicom.entity.Approval(_toBeApproved.getApprovalRuleId()))
						.setParameter(2, _toBeApproved.getStage()).getSingleResult();
			} catch (javax.persistence.NoResultException ex) {
				throw new BadRequestException("Invalid Request steps not match");
			}
			String idOldValue = "";
			String lastModifiedByColumnName = "";
			String lastModifiedDateColumnName = "";

			// FIND LAST MODIFIER COLUMN NAME
			try {
				Field[] _fields = Class.forName("com.pratikshat.exicom.entity." + _toBeApproved.getObjectName())
						.getDeclaredFields();
				for (Field fld : _fields) {
					if (fld.getName().toLowerCase().contains("modified")
							&& fld.getType().getName().contains("EmployeeDetails")) {
						lastModifiedByColumnName = fld.getName();
					}
					if (fld.getName().toLowerCase().contains("modified") && fld.getType().getName().contains("Date"))
						lastModifiedDateColumnName = fld.getName();
				}

				EmployeeDetails lastMofifier = null;
				Date lastMofiedDate = null;
				String lastMofifierQuery = String.format("select obj.%s from %s obj where obj.%s=?1",
						lastModifiedByColumnName, _stapes.getApproval().getObjectClassName(),
						_stapes.getApproval().getObjectIdFiledName());
				String lastModifierDateQuery = String.format("select obj.%s from %s obj where obj.%s=?1",
						lastModifiedDateColumnName, _stapes.getApproval().getObjectClassName(),
						_stapes.getApproval().getObjectIdFiledName());

				logger.info(lastMofifierQuery);
				logger.info(lastModifierDateQuery);

				lastMofifier = em.createQuery(lastMofifierQuery, EmployeeDetails.class)
						.setParameter(1, _toBeApproved.getObjectId()).getSingleResult();
				lastMofiedDate = em.createQuery(lastModifierDateQuery, Date.class)
						.setParameter(1, _toBeApproved.getObjectId()).getSingleResult();

				if (!emp.getEmailId().equalsIgnoreCase(lastMofifier.getEmailId())) {
					ObjectLog dLog = new ObjectLog();
					dLog.setCreatedDate(new Date());
					dLog.setKeyName(lastModifiedByColumnName);
					dLog.setLoggedBy(emp);
					dLog.setObjectId(String.valueOf(_toBeApproved.getObjectId()));
					dLog.setObjectName(_stapes.getApproval().getObjectClassName());
					dLog.setOldValue(lastMofifier.getEmailId());
					dLog.setValue(emp.getEmailId());
					em.persist(dLog);
				}
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				ObjectLog sLog = new ObjectLog();
				sLog.setCreatedDate(new Date());
				sLog.setKeyName(lastModifiedDateColumnName);
				sLog.setLoggedBy(emp);
				sLog.setObjectId(String.valueOf(_toBeApproved.getObjectId()));
				sLog.setObjectName(_stapes.getApproval().getObjectClassName());
				sLog.setOldValue(formatter.format(lastMofiedDate));
				sLog.setValue(formatter.format(new Date()));
				em.persist(sLog);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			String queryOldValue = String.format("select COALESCE(obj.%s,'') from %s obj where obj.%s=?1",
					_stapes.getObjectFiledName(), _stapes.getApproval().getObjectClassName(),
					_stapes.getApproval().getObjectIdFiledName());

			String updateQueryhql = String.format("update %s obj set obj.%s=?1,obj.%s=?2,obj.%s=?3 where obj.%s=?4",
					_stapes.getApproval().getObjectClassName(),

					_stapes.getObjectFiledName(), lastModifiedByColumnName, lastModifiedDateColumnName,
					_stapes.getApproval().getObjectIdFiledName());
			logger.info(queryOldValue);
			logger.info(updateQueryhql);
			// System.out.println("HQL Query: "+updateQueryhql);
			String objectName = _toBeApproved.getAction().equalsIgnoreCase("Approved") ? _stapes.getOnAprovedValue()
					: _stapes.getOnRejectedValue();
			// System.out.println("Value of params status "+objectName);

			ObjectLog oLog = new ObjectLog();
			oLog.setCreatedDate(new Date());

			oLog.setKeyName(_stapes.getObjectFiledName().split("\\.")[0]);
			oLog.setLoggedBy(currentUser);
			oLog.setObjectId(String.valueOf(_toBeApproved.getObjectId()));
			oLog.setObjectName(_stapes.getApproval().getObjectClassName());
			oLog.setValue(objectName);

			try {
				long objparam = Long.valueOf(objectName);
//				System.out.println(queryOldValue);
//				System.out.println("Parameter "+objparam);

				Object tmp = em.createQuery(queryOldValue).setParameter(1, _toBeApproved.getObjectId())
						.getSingleResult();

				idOldValue = tmp == null ? "" : tmp.toString();
				em.createQuery(updateQueryhql).setParameter(1, objparam).setParameter(2, emp)
						.setParameter(3, new Date()).setParameter(4, _toBeApproved.getObjectId()).executeUpdate();
			} catch (Exception ex) {
				// ex.printStackTrace();
				try {
					int objparam = Integer.valueOf(objectName);
					Object tmp = em.createQuery(queryOldValue).setParameter(1, _toBeApproved.getObjectId())
							.getSingleResult();
					idOldValue = tmp == null ? "" : tmp.toString();
					em.createQuery(updateQueryhql).setParameter(1, objparam).setParameter(2, emp)
							.setParameter(3, new Date()).setParameter(4, _toBeApproved.getObjectId()).executeUpdate();
				} catch (Exception _ei) {
					// _ei.printStackTrace();
					Object tmp = em.createQuery(queryOldValue).setParameter(1, _toBeApproved.getObjectId())
							.getSingleResult();
					idOldValue = tmp == null ? "" : tmp.toString();
					em.createQuery(updateQueryhql).setParameter(1, objectName).setParameter(2, emp)
							.setParameter(3, new Date()).setParameter(4, _toBeApproved.getObjectId()).executeUpdate();
				}

			}

			oLog.setOldValue(idOldValue);
			em.persist(oLog);

			if (_stapes.getApproval().getApprovalStages() > _toBeApproved.getStage()) {
				com.pratikshat.exicom.entity.ApprovalSteps _next = null;
				try {
					_next = em.createQuery(
							"select nst from ApprovalSteps nst join nst.approval apr where apr=?1 and nst.approvedStage=?2",
							com.pratikshat.exicom.entity.ApprovalSteps.class)
							.setParameter(1, new com.pratikshat.exicom.entity.Approval(_stapes.getApproval().getId()))
							.setParameter(2, (_toBeApproved.getStage() + 1)).getSingleResult();
					String[] approvar = _next.getApprovedBy().split(":");
					String approvarEmailId = "";
					if (approvar[0].equalsIgnoreCase("USER")) {
						approvarEmailId = approvar[1];
					}
					if (approvar[0].equalsIgnoreCase("ROLE")) {
						approvarEmailId = getApproverEmail(approvar[1], emp.getEmailId(), em);
					}
					if (approvar[0].equalsIgnoreCase("APPROVAL-MANAGER")) {
						approvarEmailId = getApprovalManager(emp.getEmailId(), em);
					}
					if (approvar[0].equalsIgnoreCase("MANAGER")) {
						approvarEmailId = getManager(emp.getEmailId(), em);
					}
					com.pratikshat.exicom.entity.ToBeApproved _nextApproval = new com.pratikshat.exicom.entity.ToBeApproved();
					_nextApproval.setAssignedDate(new java.util.Date());
					_nextApproval.setAssignedTo(new com.pratikshat.exicom.entity.EmployeeDetails(approvarEmailId));
					_nextApproval.setObjectId(_toBeApproved.getObjectId());
					_nextApproval.setObjectManualId(_toBeApproved.getObjectManualId());
					_nextApproval.setObjectName(_toBeApproved.getObjectName());
					_nextApproval.setObjectUrl(_toBeApproved.getObjectUrl());
					_nextApproval.setStage(_next.getApprovedStage());
					_nextApproval.setApprovalRuleId(_toBeApproved.getApprovalRuleId());
					_nextApproval.setStatus(false);
					em.persist(_nextApproval);

					// APPROVAL NOTIFICATION TO NEXT APPROVER
					EmployeeDetails employeeDetails = null;
					Field[] fields = Class.forName("com.pratikshat.exicom.entity." + _toBeApproved.getObjectName())
							.getDeclaredFields();

					String fieldName = null;

					for (Field field : fields) {
						if (field.getName().contains("owner")) {
							fieldName = field.getName();
							break;
						}
					}

					if (fieldName != null && _stapes != null) {

						String selectOwnerQuery = String.format(
								"Select onr from %s obj join obj.%s onr left join onr.contact ct where obj.%s = ?1",
								_toBeApproved.getObjectName(), fieldName, _stapes.getApproval().getObjectIdFiledName());

						try {
							employeeDetails = em.createQuery(selectOwnerQuery, EmployeeDetails.class)
									.setParameter(1, _toBeApproved.getObjectId()).getSingleResult();
						} catch (Exception ex) {

						}

					}
					String empName = "";
					if (employeeDetails != null && employeeDetails.getFirstName() != null)
						empName = employeeDetails.getFirstName();
					if (employeeDetails != null && employeeDetails.getMiddleName() != null)
						empName = empName + " " + employeeDetails.getMiddleName();
					if (employeeDetails != null && employeeDetails.getLastName() != null)
						empName = empName + " " + employeeDetails.getLastName();

					com.pratikshat.exicom.entity.ApplicationNotificationDetails notificationDetails = new com.pratikshat.exicom.entity.ApplicationNotificationDetails();
					notificationDetails.setNofificationDate(new java.util.Date());
					notificationDetails.setNotificationActionUrl("/itemtobeapproved");
					notificationDetails.setNotificationMessage(String.format(
							"%s %s initiated by %s received for your approval",
							_obj == null ? _toBeApproved.getObjectName() : _obj.getObjectLableName(),
							_toBeApproved.getObjectManualId(), empName.equalsIgnoreCase("") ? userName : empName));
					notificationDetails.setNotificationRead(false);
					notificationDetails.setUserEmailId(approvarEmailId);
					try {
						em.persist(notificationDetails);
					} catch (Exception ex) {
					}
					try {

						msgTemplate.convertAndSend("/topic/" + approvarEmailId, notificationDetails);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} else {
				switch (_stapes.getApproval().getObjectClassName()) {
				case "MSLRequest": {
					if (appRequest.getAction().equalsIgnoreCase("Approved"))
						updateMSLOnFinalApproval(_toBeApproved.getObjectId(), emp.getEmailId(), em);
					break;
				}
				case "ExpenseManagement": {
					if (appRequest.getAction().equalsIgnoreCase("Approved")) {
						try {
							 addToArchiveExpenseAfterApproval(_toBeApproved.getObjectId(), emp.getEmailId(), em);
						} catch (Exception ex) {
							throw new BadRequestException(ex.getMessage());
						}

					}
					break;
				}
				case "CaseDetails": {
					if (appRequest.getAction().equalsIgnoreCase("Approved")) {
						try {
							// createServiceAppointment(_toBeApproved.getObjectId(),emp.getEmailId(),em);

							CaseDetails _caseDetails = null;
							try {
								Query selectCaseDetailsQuery = em.createQuery(
										"select ca from CaseDetails ca left join ca.status cs left join ca.caseType ct left join ca.stage cst left join ca.caseReason crs left join ca.businessDomain bd left join ca.assets ast left join ast.account acn left join ca.site st left join st.circle cl left join ca.ownerDetails onr left join ca.createdBy cby left join ca.engineer eng left join eng.contact econ left join ca.serviceCenter scr where ca.caseId=?1",
										CaseDetails.class);
								selectCaseDetailsQuery.setParameter(1, _toBeApproved.getObjectId());
								_caseDetails = (CaseDetails) selectCaseDetailsQuery.getSingleResult();
							} catch (NoResultException ex) {
								throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
							}

							ServiceAppointment _serviceAppointment = new ServiceAppointmentUtil()
									.createServiceAppointment(_caseDetails.getCaseId(), emp.getEmailId(), em);

							workflow.executeWorkflow(em, _serviceAppointment, "INSERT", "PRE",
									new LoginUser(emp.getEmailId()));
							em.persist(_serviceAppointment);
							workflow.executeWorkflow(em, _serviceAppointment, "INSERT", "POST",
									new LoginUser(emp.getEmailId()));

							Query updateServiceAppointmentManualId = em.createQuery(
									"update ServiceAppointment sa set sa.appointmentNumber=?1 where sa.serviceAppointmentId=?2");
							SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
//							String serviceAppointmentManualId = "SA-" + df.format(new java.util.Date()) + "-"
//									+ com.pratikshat.exicom.config.MiscellaneousUtils
//											.fillZero(String.valueOf(_serviceAppointment.getServiceAppointmentId()), 5);

							String serviceAppointmentManualId = "SA-" + com.pratikshat.exicom.config.MiscellaneousUtils
									.fillZero(String.valueOf(_serviceAppointment.getServiceAppointmentId()), 7);
							updateServiceAppointmentManualId.setParameter(1, serviceAppointmentManualId);
							updateServiceAppointmentManualId.setParameter(2,
									_serviceAppointment.getServiceAppointmentId());
							updateServiceAppointmentManualId.executeUpdate();
							// notification to service appointment Eng
							try {
								ApplicationNotificationDetails notification = new ApplicationNotificationDetails();
								notification.setNofificationDate(new java.util.Date());
								notification.setNotificationActionUrl(
										"/serviceappointment/" + _serviceAppointment.getServiceAppointmentId());
								notification.setNotificationMessage(String.format(
										"Service Appointment %s assigned for your action", serviceAppointmentManualId));
								notification.setNotificationRead(false);
								notification.setUserEmailId(
										_serviceAppointment.getServiceResource().getUser().getEmailId());
								em.persist(notification);
								msgTemplate.convertAndSend(
										"/topci/" + _serviceAppointment.getServiceResource().getUser().getEmailId(),
										notification);
							} catch (Exception ex) {

							}
							try {
								com.pratikshat.exicom.entity.GlobalSearchIndex _search = new com.pratikshat.exicom.entity.GlobalSearchIndex();
								_search.setKeyName("appointmentNumber");
								_search.setObjectDetailsUrl(
										"serviceappointment/" + _serviceAppointment.getServiceAppointmentId());
								_search.setObjectIdName("serviceAppointmentId");
								_search.setObjectIdValue("" + _serviceAppointment.getServiceAppointmentId());
								_search.setObjectName("ServiceAppointment");
								_search.setSearchString(serviceAppointmentManualId);
								em.persist(_search);

							} catch (Exception e) {
							}

							if (_caseDetails.getBusinessDomain().getBusinessDomainId() == 1) { // Battery

								BatteryFSR batteryFSR = new BatteryFSR();
								batteryFSR.setServiceAppointment(_serviceAppointment);
								em.persist(batteryFSR);

								Query updateFSRManualId = em
										.createQuery("update BatteryFSR fsr set fsr.manualId=?1 where fsr.id=?2");
								// String fsrManualId= "FSR-CBMS-LI-"+df.format(new
								// java.util.Date())+"-"+com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(batteryFSR.getId()),5);

								String fsrManualId = "FSR-CBMS-LI-" + com.pratikshat.exicom.config.MiscellaneousUtils
										.fillZero(String.valueOf(batteryFSR.getId()), 7);
								updateFSRManualId.setParameter(1, fsrManualId);
								updateFSRManualId.setParameter(2, batteryFSR.getId());
								updateFSRManualId.executeUpdate();

								// search index entry
								try {
									com.pratikshat.exicom.entity.GlobalSearchIndex _search = new com.pratikshat.exicom.entity.GlobalSearchIndex();
									_search.setKeyName("manualId");
									_search.setObjectDetailsUrl("Batteryfsr/" + batteryFSR.getId());
									_search.setObjectIdName("id");
									_search.setObjectIdValue("" + batteryFSR.getId());
									_search.setObjectName("BatteryFSR");
									_search.setSearchString(fsrManualId);
									em.persist(_search);

								} catch (Exception e) {
								}
							}
						} catch (Exception ex) {
							new BadRequestException(ex.getMessage());
						}

					}
					break;
				}

				case "Quote": {
					if (appRequest.getAction().equalsIgnoreCase("Approved")) {

						// workflow.executeWorkflow(em, _serviceAppointment, "INSERT", "POST",new
						// LoginUser(_emp.getEmailId()));
						// System.out.println("Quote final approval process");
						try {
							QuotePDFGeneration quotePDFGeneration = new QuotePDFGeneration(em, fileStorageProperties);
							ByteArrayInputStream bis = quotePDFGeneration.createPdf(_toBeApproved.getObjectId(),
									new LoginUser(emp.getEmailId()));
							// System.out.println("Pdf creation done");

							UploadedFiles uploadedFile = null;
							try {
								Query selectuploadedFileQuery = em.createQuery(
										"select uf from UploadedFiles uf where uf.objectName='Quote' and uf.objectId=?1 order by uf.createdDate desc",
										UploadedFiles.class);
								selectuploadedFileQuery.setParameter(1, "" + _toBeApproved.getObjectId());
								uploadedFile = (UploadedFiles) selectuploadedFileQuery.getResultList().get(0);

							} catch (NoResultException ex) {
								throw new com.pratikshat.exicom.exception.BadRequestException(
										"Quote PDF does not found");
							} catch (IndexOutOfBoundsException e) {
								throw new com.pratikshat.exicom.exception.BadRequestException(
										"Quote PDF does not found");
							}

							if (uploadedFile == null) {
								throw new com.pratikshat.exicom.exception.BadRequestException(
										"Quote PDF does not found");
							}

							Quote quote = null;
							try {
								Query selectQuoteQuery = em.createQuery(
										"select qt from Quote qt left join qt.oppertunity opr left join opr.accounts acn left join opr.caseDetails cd left join cd.site st left join st.circle crl left join st.serviceCenter lgl where qt.id=?1",
										Quote.class);
								selectQuoteQuery.setParameter(1, _toBeApproved.getObjectId());
								quote = (Quote) selectQuoteQuery.getSingleResult();
							} catch (NoResultException ex) {
								throw new com.pratikshat.exicom.exception.BadRequestException("Quote does not exist");
							}

							if (quote == null) {
								throw new com.pratikshat.exicom.exception.BadRequestException("Quote does not found");
							}

							quote.setQuoteSentDate(new Date());
							quote.setApproved(true);
							quote.setOwner(emp);
							quote.setApprovalCheck(_stapes.getApprovedStage());
							quote.setQuoteStatus(new QuoteStatus(4)); // Quote Sent
							quote.setModifiedBy(emp);
							quote.setModifiedDate(new Date());
							em.persist(quote);

							sendMailToCustomer(quote, uploadedFile, emp);
							sendMailToCustomerCareTeamOnApproval(quote, emp, em);

							CaseDetails _caseDetails = null;
							try {
								Query selectCaseDetailsQuery = em.createQuery(
										"select ca from CaseDetails ca left join ca.status cs left join ca.stage stg left join ca.ownerDetails onr left join ca.modifiedBy modified left join ca.businessDomain bd where ca.caseId=?1",
										CaseDetails.class);
								selectCaseDetailsQuery.setParameter(1,
										quote.getOppertunity().getCaseDetails().getCaseId());
								_caseDetails = (CaseDetails) selectCaseDetailsQuery.getSingleResult();
							} catch (NoResultException ex) {
								throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
							}

							if (_caseDetails != null) {
								if (_caseDetails.getBusinessDomain().getBusinessDomainId() == 1) { // Battery
									_caseDetails.setStage(new CaseStageDetails(19));
								} else if (_caseDetails.getBusinessDomain().getBusinessDomainId() == 2) { // DCT
									_caseDetails.setStage(new CaseStageDetails(13));
								} else if (_caseDetails.getBusinessDomain().getBusinessDomainId() == 3) { // EV

								}
								_caseDetails.setStatus(new CaseStatusDetails(3)); // In-Process
								_caseDetails.setModifiedBy(emp);
								_caseDetails.setModifiedDate(new Date());

								em.persist(_caseDetails);
							}
						} catch (Exception ex) {
							ex.printStackTrace();
							throw new BadRequestException(ex.getMessage());
						}

					}
					break;
				}

				case "ServiceAppointment": {
					if (appRequest.getAction().equalsIgnoreCase("Approved")) {
						if (_toBeApproved.getStage() != 1) {
							try {
								com.pratikshat.exicom.entity.ServiceAppointment serviceAppointment = null;
								try {
									serviceAppointment = em.createQuery(
											"select sa from ServiceAppointment sa join sa.status st left join sa.claimStatus cs join sa.ownerDetails owner where sa.serviceAppointmentId=?1",
											com.pratikshat.exicom.entity.ServiceAppointment.class)
											.setParameter(1, _toBeApproved.getObjectId()).getSingleResult();
								} catch (NoResultException ex) {
									throw new BadRequestException("Service Appointment does not exist");
								}

								if (serviceAppointment == null) {
									throw new BadRequestException("Service Appointment does not exist");
								}

								serviceAppointment.setApprovedByFinance(true);
								serviceAppointment.setApprovedByFinance(new Date());
								serviceAppointment.setModifiedBy(emp);
								serviceAppointment.setModifiedDate(new Date());
								em.persist(serviceAppointment);

							} catch (Exception ex) {
								new BadRequestException(ex.getMessage());
							}
						}
					}
					break;

				}

				case "Annexure": {
					try {
						List<ServiceAppointment> serviceAppointmentList = null;
						try {
							Query selectServiceAppointmentQuery = em.createQuery(
									"select sa from AnnexureLineItem ali join ali.annexure anx join ali.serviceAppointment sa join sa.claimStatus clm where anx.id=?1",
									ServiceAppointment.class);
							selectServiceAppointmentQuery.setParameter(1, _toBeApproved.getObjectId());
							serviceAppointmentList = (List<ServiceAppointment>) selectServiceAppointmentQuery
									.getResultList();
						} catch (NoResultException ex) {
							throw new BadRequestException("Service Appointment does not exist");
						}

						if (serviceAppointmentList == null) {
							throw new BadRequestException("Service Appointment does not exist");
						}
						if (serviceAppointmentList != null && serviceAppointmentList.size() == 0) {
							throw new BadRequestException("Service Appointment does not exist");
						}

						for (int i = 0; i < serviceAppointmentList.size(); i++) {
							ServiceAppointment serviceAppointment = serviceAppointmentList.get(i);

							serviceAppointment.setApprovedByFinance(true);
							serviceAppointment.setApprovedByFinance(new Date());
							serviceAppointment.setClaimStatus(new ClaimStatus(5));
							serviceAppointment.setModifiedBy(emp);
							serviceAppointment.setModifiedDate(new Date());
							em.persist(serviceAppointment);
						}

					} catch (Exception ex) {
						new BadRequestException(ex.getMessage());
					}

					break;
				}

				}
			}
			
			// Approval SMS Notification
			
			try {
				Field[] fields = Class.forName("com.pratikshat.exicom.entity." + _toBeApproved.getObjectName())
						.getDeclaredFields();

				String fieldName = null;

				for (Field field : fields) {
					if (field.getName().contains("owner")) {
						fieldName = field.getName();
						break;
					}
				}

				if (fieldName != null) {

					String selectOwnerQuery = String.format(
							"Select onr from %s obj join obj.%s onr left join onr.contact ct where obj.%s = ?1",
							_toBeApproved.getObjectName(), fieldName, _stapes.getApproval().getObjectIdFiledName());
					EmployeeDetails employeeDetails = em.createQuery(selectOwnerQuery, EmployeeDetails.class)
							.setParameter(1, _toBeApproved.getObjectId()).getSingleResult();

					if (employeeDetails.getContact().getMobile() != null) {

						switch (_stapes.getApproval().getObjectClassName()) {

						case "ServiceAppointment": {
							TemplateDetails templateDetails = null;
							
							try {
								Query templateQuery=em.createQuery("select tmpd from TemplateDetails tmpd where tmpd.active=?1 and tmpd.templateType=?2 and tmpd.businessDomain=?3 and tmpd.eventName=?4",TemplateDetails.class);
								templateQuery.setParameter(1,true);
								templateQuery.setParameter(2,TemplateType.SMS);
								templateQuery.setParameter(3,new BusinessDomain(2));
								templateQuery.setParameter(4,"sa_expense_approve");

								templateDetails = (TemplateDetails) templateQuery.getSingleResult();
							} catch (NoResultException nex) {
								nex.printStackTrace();
							} catch (NonUniqueResultException e) {
								e.printStackTrace();
							}
							String smsMessage = null;
							if (templateDetails != null) {
								smsMessage = templateDetails.getBody();
								smsMessage = smsMessage.replaceFirst("<var>", _toBeApproved.getObjectManualId());
								smsMessage = smsMessage.replaceFirst("<var>", userName);
							}

							if (smsMessage != null && !smsMessage.equalsIgnoreCase("")) {
								SmsNotifier sNotifier = new SmsNotifier(em, employeeDetails.getContact().getMobile(),
										smsMessage, emp.getEmailId(), templateDetails.getTemplaeCode());
								sNotifier.sendNotification();
							}

							break;
						}
						case "ExpenseManagement": {
							TemplateDetails templateDetails = null;
							
							try {
								Query templateQuery=em.createQuery("select tmpd from TemplateDetails tmpd where tmpd.active=?1 and tmpd.templateType=?2 and tmpd.businessDomain=?3 and tmpd.eventName=?4",TemplateDetails.class);
								templateQuery.setParameter(1,true);
								templateQuery.setParameter(2,TemplateType.SMS);
								templateQuery.setParameter(3,new BusinessDomain(2));
								templateQuery.setParameter(4,"expense_approve");

								templateDetails = (TemplateDetails) templateQuery.getSingleResult();
							} catch (NoResultException nex) {
								nex.printStackTrace();
							} catch (NonUniqueResultException e) {
								e.printStackTrace();
							}
							String smsMessage = null;
							if (templateDetails != null) {
								smsMessage = templateDetails.getBody();
								smsMessage = smsMessage.replaceFirst("<var>", _toBeApproved.getObjectManualId());
								smsMessage = smsMessage.replaceFirst("<var>", userName);
								smsMessage = smsMessage.replaceFirst("<var>",
										feedbackurl+"/#/expensesmanagement/" + _toBeApproved.getObjectId());
							}

							if (smsMessage != null && !smsMessage.equalsIgnoreCase("")) {
								SmsNotifier sNotifier = new SmsNotifier(em, employeeDetails.getContact().getMobile(),
										smsMessage, emp.getEmailId(), templateDetails.getTemplaeCode());
								sNotifier.sendNotification();
							}
							break;
						}
						case "CaseDetails": {

							String selectSAOwnerQuery = "Select onr from ServiceAppointment sa join sa.caseDetails cd join sa.ownerDetails onr left join onr.contact ct where cd.caseId = ?1 ORDER BY sa.createDate DESC";
							EmployeeDetails saOwner = em.createQuery(selectSAOwnerQuery, EmployeeDetails.class)
									.setParameter(1, _toBeApproved.getObjectId()).getResultList().get(0);

							TemplateDetails templateDetails = null;
							
							try {
								Query templateQuery=em.createQuery("select tmpd from TemplateDetails tmpd where tmpd.active=?1 and tmpd.templateType=?2 and tmpd.businessDomain=?3 and tmpd.eventName=?4",TemplateDetails.class);
								templateQuery.setParameter(1,true);
								templateQuery.setParameter(2,TemplateType.SMS);
								templateQuery.setParameter(3,new BusinessDomain(2));
								templateQuery.setParameter(4,"request_for_sa_approve");

								templateDetails = (TemplateDetails) templateQuery.getSingleResult();
							} catch (NoResultException nex) {
								nex.printStackTrace();
							} catch (NonUniqueResultException e) {
								e.printStackTrace();
							}

							String smsMessage = null;
							if (templateDetails != null) {
								smsMessage = templateDetails.getBody();
								smsMessage = smsMessage.replaceFirst("<var>", _toBeApproved.getObjectManualId());
								smsMessage = smsMessage.replaceFirst("<var>", userName);
								smsMessage = smsMessage.replaceFirst("<var>",
										feedbackurl+"/#/cases/" + _toBeApproved.getObjectId());
							}

							if (smsMessage != null && !smsMessage.equalsIgnoreCase("")) {
								SmsNotifier sNotifier = new SmsNotifier(em, saOwner.getContact().getMobile(),
										smsMessage, emp.getEmailId(), templateDetails.getTemplaeCode());
								sNotifier.sendNotification();
							}

							break;
						}
						case "Quote": {
							
							TemplateDetails templateDetails = null;
						
							try {
								Query templateQuery=em.createQuery("select tmpd from TemplateDetails tmpd where tmpd.active=?1 and tmpd.templateType=?2 and tmpd.businessDomain=?3 and tmpd.eventName=?4",TemplateDetails.class);
								templateQuery.setParameter(1,true);
								templateQuery.setParameter(2,TemplateType.SMS);
								templateQuery.setParameter(3,new BusinessDomain(2));
								templateQuery.setParameter(4,"quote_approve");

								templateDetails = (TemplateDetails) templateQuery.getSingleResult();
							} catch (NoResultException nex) {
								nex.printStackTrace();
							} catch (NonUniqueResultException e) {
								e.printStackTrace();
							}
							String smsMessage = null;
							if (templateDetails != null) {
								smsMessage = templateDetails.getBody();
								smsMessage = smsMessage.replaceFirst("<var>", _toBeApproved.getObjectManualId());
								smsMessage = smsMessage.replaceFirst("<var>", userName);
								smsMessage = smsMessage.replaceFirst("<var>",
										feedbackurl+"/#/Quote/" + _toBeApproved.getObjectId());
							}

							if (smsMessage != null && !smsMessage.equalsIgnoreCase("")) {
								SmsNotifier sNotifier = new SmsNotifier(em, employeeDetails.getContact().getMobile(),
										smsMessage, emp.getEmailId(), templateDetails.getTemplaeCode());
								sNotifier.sendNotification();
							}
							
							break;
						}
						case "WccDetails": {
							
							TemplateDetails templateDetails = null;
							
							try {
								Query templateQuery=em.createQuery("select tmpd from TemplateDetails tmpd where tmpd.active=?1 and tmpd.templateType=?2 and tmpd.businessDomain=?3 and tmpd.eventName=?4",TemplateDetails.class);
								templateQuery.setParameter(1,true);
								templateQuery.setParameter(2,TemplateType.SMS);
								templateQuery.setParameter(3,new BusinessDomain(2));
								templateQuery.setParameter(4,"wcc_approve");

								templateDetails = (TemplateDetails) templateQuery.getSingleResult();
							} catch (NoResultException nex) {
								nex.printStackTrace();
							} catch (NonUniqueResultException e) {
								e.printStackTrace();
							}
							String smsMessage = null;
							if (templateDetails != null) {
								smsMessage = templateDetails.getBody();
								smsMessage = smsMessage.replaceFirst("<var>", _toBeApproved.getObjectManualId());
								smsMessage = smsMessage.replaceFirst("<var>", userName);
								smsMessage = smsMessage.replaceFirst("<var>",
										feedbackurl+"/#/wcc/" + _toBeApproved.getObjectId());
							}

							if (smsMessage != null && !smsMessage.equalsIgnoreCase("")) {
								SmsNotifier sNotifier = new SmsNotifier(em, employeeDetails.getContact().getMobile(),
										smsMessage, emp.getEmailId(), templateDetails.getTemplaeCode());
								sNotifier.sendNotification();
							}
							
							break;
						}
						case "VendorInvoiceDetails": {
							
							TemplateDetails templateDetails = null;
							
							try {
								Query templateQuery=em.createQuery("select tmpd from TemplateDetails tmpd where tmpd.active=?1 and tmpd.templateType=?2 and tmpd.businessDomain=?3 and tmpd.eventName=?4",TemplateDetails.class);
								templateQuery.setParameter(1,true);
								templateQuery.setParameter(2,TemplateType.SMS);
								templateQuery.setParameter(3,new BusinessDomain(2));
								templateQuery.setParameter(4,"invoice_approve");

								templateDetails = (TemplateDetails) templateQuery.getSingleResult();
							} catch (NoResultException nex) {
								nex.printStackTrace();
							} catch (NonUniqueResultException e) {
								e.printStackTrace();
							}
							String smsMessage = null;
							if (templateDetails != null) {
								smsMessage = templateDetails.getBody();
								smsMessage = smsMessage.replaceFirst("<var>", _toBeApproved.getObjectManualId());
								smsMessage = smsMessage.replaceFirst("<var>", userName);
								smsMessage = smsMessage.replaceFirst("<var>",
										feedbackurl+"/#/invoice/" + _toBeApproved.getObjectId());
							}

							if (smsMessage != null && !smsMessage.equalsIgnoreCase("")) {
								SmsNotifier sNotifier = new SmsNotifier(em, employeeDetails.getContact().getMobile(),
										smsMessage, emp.getEmailId(), templateDetails.getTemplaeCode());
								sNotifier.sendNotification();
							}
							
							break;
						}
						}
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
		} else {

		}

		return appRequest;
	}

	private String getApproverEmail(String role, String userMailId, javax.persistence.EntityManager _em) {

		com.pratikshat.exicom.entity.EmployeeDetails emp = null;
		emp = _em
				.createQuery("select mng from EmployeeDetails emp join emp.manager mng where emp.emailId=?1",
						com.pratikshat.exicom.entity.EmployeeDetails.class)
				.setParameter(1, userMailId).getSingleResult();
		java.util.List<String> roles = em.createQuery(
				"select rol.authority from LoginUserRoles rol join rol.loginUser usr where usr.username=?1",
				String.class).setParameter(1, emp.getEmailId()).getResultList();
		for (String _rl : roles) {
			// System.out.println(_rl+"\t"+role);
			if (_rl.equalsIgnoreCase(role)) {
				return emp.getEmailId();
			}
		}
		return getApproverEmail(role, emp.getEmailId(), _em);
	}

	private String getApprovalManager(String currentUser, javax.persistence.EntityManager _em) {
		String empManager = _em.createQuery(
				"select mng.emailId from EmployeeDetails emp join emp.approvalManager mng where emp.emailId=?1",
				String.class).setParameter(1, currentUser).getSingleResult();
		return empManager;
	}

	private String getManager(String currentUser, javax.persistence.EntityManager _em) {
		String manager = _em
				.createQuery("select mng.emailId from EmployeeDetails emp join emp.manager mng where emp.emailId=?1",
						String.class)
				.setParameter(1, currentUser).getSingleResult();
		return manager;
	}

	private void updateMSLOnFinalApproval(long objectId, String userEmaiid, javax.persistence.EntityManager _em) {
		java.util.List<com.pratikshat.exicom.entity.MSLRequestItem> items = _em.createQuery(
				"select item from MSLRequestItem item join item.mslRequest rq join rq.warehouse wr join item.products prd where rq=?1",
				com.pratikshat.exicom.entity.MSLRequestItem.class)
				.setParameter(1, new com.pratikshat.exicom.entity.MSLRequest(objectId)).getResultList();
		for (com.pratikshat.exicom.entity.MSLRequestItem _item : items) {
			com.pratikshat.exicom.entity.LocationProductItem locationItem = null;
			try {

				locationItem = _em.createQuery(
						"select litem from LocationProductItem litem where litem.product=?1 and litem.location=?2",
						com.pratikshat.exicom.entity.LocationProductItem.class).setParameter(1, _item.getProducts())
						.setParameter(2, _item.getMslRequest().getWarehouse()).getSingleResult();

				String hqlUpate = "update LocationProductItem item set item.hslQuantity=?1,item.mslQuantity=?2,item.modifiedBy=?3,item.modifiedDate=?4 where item.id=?5";
				_em.createQuery(hqlUpate).setParameter(1, _item.getNewHSL()).setParameter(2, _item.getNewMSL())
						.setParameter(3, new com.pratikshat.exicom.entity.EmployeeDetails(userEmaiid))
						.setParameter(4, new java.util.Date()).setParameter(5, locationItem.getId()).executeUpdate();

			} catch (javax.persistence.NoResultException ex) {
				locationItem = new com.pratikshat.exicom.entity.LocationProductItem();
				locationItem.setCreatedBy(new com.pratikshat.exicom.entity.EmployeeDetails(userEmaiid));
				locationItem.setCreatedDate(new java.util.Date());
				locationItem.setCustomerInventory(0);
				locationItem.setFaultyQuantity(0);
				locationItem.setHslQuantity(_item.getNewHSL());
				locationItem.setHslStatus("L");
				locationItem.setIssuedQunatity(0);
				locationItem.setLocation(_item.getMslRequest().getWarehouse());
				locationItem.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(userEmaiid));
				locationItem.setModifiedDate(new Date());
				locationItem.setMslQuantity(_item.getNewMSL());
				locationItem.setOkQuantity(0);
				locationItem.setProduct(_item.getProducts());
				locationItem.setQuantityInOnHand(0);
				locationItem.setQuantityUnitOfMasur("NO");
				_em.persist(locationItem);
			}

		}
	}

	public void createServiceAppointment(long objectId, String userEmaiid,
			javax.persistence.EntityManager entityManager) throws Exception {

		CaseDetails _caseDetails = null;
		try {
			Query selectCaseDetailsQuery = entityManager.createQuery(
					"select ca from CaseDetails ca left join ca.status cs left join ca.assets ast left join ast.account acn left join ast.site st left join ca.engineer eng left join eng.contact econ left join ca.serviceCenter scr left join ca.businessDomain bd left join ca.caseType cty left join ca.ownerDetails onr where ca.caseId=?1",
					CaseDetails.class);
			selectCaseDetailsQuery.setParameter(1, objectId);
			_caseDetails = (CaseDetails) selectCaseDetailsQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
		}

		if (_caseDetails == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
		}

		List<Long> statusList = new ArrayList<>();
		statusList.add(1L); // Unassigned
		statusList.add(2L); // Scheduled
		statusList.add(3L); // Dispatched

		List<ServiceAppointment> serviceAppointmentList = null;
		try {
			serviceAppointmentList = entityManager.createQuery(
					"select sa from ServiceAppointment sa join sa.status st join sa.caseDetails cd where cd.caseId=:caseId and st.statusId in :statusList",
					com.pratikshat.exicom.entity.ServiceAppointment.class).setParameter("caseId", objectId)
					.setParameter("statusList", statusList).getResultList();
		} catch (NoResultException ex) {

		}

		if (serviceAppointmentList != null && serviceAppointmentList.size() > 0) {
			throw new com.pratikshat.exicom.exception.BadRequestException(
					"Service Appointment already present in Dispatch,Unassigned or Scheduled status");
		}

		Query getUserDetailsQuery = entityManager.createQuery(
				"select emp from EmployeeDetails emp left join ContactDetails ct on emp.emailId=ct.emailId where emp.emailId=?1");
		getUserDetailsQuery.setParameter(1, userEmaiid);
		com.pratikshat.exicom.entity.EmployeeDetails _emp = (com.pratikshat.exicom.entity.EmployeeDetails) getUserDetailsQuery
				.getSingleResult();

		Query siteDetailsQuery = entityManager.createQuery(
				"select st from SiteDetails st  left join st.circle cr left join cr.circleManagerBattry cb left join cr.circleManagerPower cp left join cr.circleManagerEv ev where st.siteId=?1",
				com.pratikshat.exicom.entity.SiteDetails.class);
		com.pratikshat.exicom.entity.SiteDetails siteDetails = null;
		try {
			siteDetailsQuery.setParameter(1, _caseDetails.getSite().getSiteId());
			siteDetails = (com.pratikshat.exicom.entity.SiteDetails) siteDetailsQuery.getSingleResult();
		} catch (NoResultException ex) {

		}

		ServiceRecource serviceRecource = null;
		try {
			Query serviceAppointmentOwnerQuery = entityManager.createQuery(
					"select sr from CaseDetails cd left join cd.site st left join st.serviceResource sr left join sr.user ur where cd.caseId=?1",
					ServiceRecource.class);
			serviceAppointmentOwnerQuery.setParameter(1, _caseDetails.getCaseId());
			serviceRecource = (ServiceRecource) serviceAppointmentOwnerQuery.getSingleResult();
		} catch (NoResultException ex) {

		}

		com.pratikshat.exicom.entity.ServiceAppointment _serviceAppointment = new com.pratikshat.exicom.entity.ServiceAppointment();

		_serviceAppointment.setCaseDetails(_caseDetails);
		_serviceAppointment.setCreateDate(new java.util.Date());
		_serviceAppointment.setCreatedBy(_emp);
		_serviceAppointment.setModifiedBy(_emp);
		_serviceAppointment.setModifiedDate(new java.util.Date());
		_serviceAppointment.setSyncFlag(false);
		_serviceAppointment.setCircle(siteDetails.getCircle());
		_serviceAppointment.setReassign(false);

		if (_caseDetails.getAssets() != null) {
			try {
				Query assetsQuery = entityManager.createQuery(
						"select ast from Assets ast left join ast.product prd where ast.serialNumber=?1", Assets.class);
				assetsQuery.setParameter(1, _caseDetails.getAssets().getSerialNumber());
				Assets assets = (Assets) assetsQuery.getSingleResult();

				_serviceAppointment.setAssets(assets);
				_serviceAppointment.setProduct(assets.getProduct());
			} catch (NoResultException ex) {

			}
		}

		if (_caseDetails.getCaseType().getCaseTypesName().equalsIgnoreCase("Under Warranty")) {
			_serviceAppointment.setCostCenter("3002010104");
		} else {
			_serviceAppointment.setCostCenter("3002010102");
		}
		if (_caseDetails.getBusinessDomain().getBusinessDomainId() == 1) { // Battery
			if (siteDetails.getCircle() != null) {
				if (siteDetails.getCircle().getCircleManagerBattry() != null) {
					_serviceAppointment.setCircleManager(siteDetails.getCircle().getCircleManagerBattry());
				}
			}
		}

		if (_caseDetails.getBusinessDomain().getBusinessDomainId() == 2) { // DCT
			if (siteDetails.getCircle() != null) {
				if (siteDetails.getCircle().getCircleManagerPower() != null) {
					_serviceAppointment.setCircleManager(siteDetails.getCircle().getCircleManagerPower());
				}
			}
		}
		if (_caseDetails.getBusinessDomain().getBusinessDomainId() == 3) { // EV
			if (siteDetails.getCircle() != null) {
				if (siteDetails.getCircle().getCircleManagerEv() != null) {
					_serviceAppointment.setCircleManager(siteDetails.getCircle().getCircleManagerEv());
				}
			}
		}

		_serviceAppointment.setStatus(new com.pratikshat.exicom.entity.ServiceAppointmentStatus(3)); // dispatch
		_serviceAppointment.setPendingResponsibility(new PendingResponsibility(1));

//		if(_emp.getMiddleName()!=null) {
//			String name = String.format("%s %s %s", _emp.getFirstName(),_emp.getMiddleName(), _emp.getLastName());
//			_serviceAppointment.setCustomerRepresentativeName(name);
//		}else {
//			String name = String.format("%s %s", _emp.getFirstName(), _emp.getLastName());
//			_serviceAppointment.setCustomerRepresentativeName(name);
//		}

//		if (_emp.getContact() != null) {
//			_serviceAppointment.setCustomerRepresentativeContact(_emp.getContact().getMobile());
//		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

		Calendar cal = Calendar.getInstance();
		String time = timeFormat.format(cal.getTime());

		_serviceAppointment.setEarliestStartPermittedDate(dateFormat.format(cal.getTime()));
		_serviceAppointment.setEarliestStartPermittedTime(time);

		cal.add(Calendar.DATE, 30);

		_serviceAppointment.setDueDate(dateFormat.format(cal.getTime()));
		_serviceAppointment.setDueTime(time);
		_serviceAppointment.settFlag("N");
		_serviceAppointment.setBusinessDomain(_caseDetails.getBusinessDomain());

		if (serviceRecource != null) {
			_serviceAppointment.setOwnerDetails(serviceRecource.getUser());
			_serviceAppointment.setServiceResource(serviceRecource);
		} else {
			try {
				Query schQuery = entityManager.createQuery(
						"select sr from ServiceRecource sr left join sr.user ur where ur.emailId=?1",
						ServiceRecource.class);
				schQuery.setParameter(1, _caseDetails.getOwnerDetails().getEmailId());
				ServiceRecource sch = (ServiceRecource) schQuery.getSingleResult();

				_serviceAppointment.setOwnerDetails(sch.getUser());
				_serviceAppointment.setServiceResource(sch);

			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("SCH deos not exist");
			}
		}

		workflow.executeWorkflow(entityManager, _serviceAppointment, "INSERT", "PRE", new LoginUser(_emp.getEmailId()));
		entityManager.persist(_serviceAppointment);
		workflow.executeWorkflow(entityManager, _serviceAppointment, "INSERT", "POST",
				new LoginUser(_emp.getEmailId()));

		Query updateServiceAppointmentManualId = entityManager.createQuery(
				"update ServiceAppointment sa set sa.appointmentNumber=?1 where sa.serviceAppointmentId=?2");
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String serviceAppointmentManualId = "SA-" + df.format(new java.util.Date()) + "-"
				+ com.pratikshat.exicom.config.MiscellaneousUtils
						.fillZero(String.valueOf(_serviceAppointment.getServiceAppointmentId()), 5);
		updateServiceAppointmentManualId.setParameter(1, serviceAppointmentManualId);
		updateServiceAppointmentManualId.setParameter(2, _serviceAppointment.getServiceAppointmentId());
		updateServiceAppointmentManualId.executeUpdate();
		// notification to service appointment Eng
		try {
			ApplicationNotificationDetails notification = new ApplicationNotificationDetails();
			notification.setNofificationDate(new java.util.Date());
			notification
					.setNotificationActionUrl("/serviceappointment/" + _serviceAppointment.getServiceAppointmentId());
			notification.setNotificationMessage(
					String.format("Service Appointment %s assigned for your action", serviceAppointmentManualId));
			notification.setNotificationRead(false);
			notification.setUserEmailId(_serviceAppointment.getServiceResource().getUser().getEmailId());
			entityManager.persist(notification);
			msgTemplate.convertAndSend("/topic/" + notification.getUserEmailId(), notification);
		} catch (Exception ex) {

		}
		try {
			com.pratikshat.exicom.entity.GlobalSearchIndex _search = new com.pratikshat.exicom.entity.GlobalSearchIndex();
			_search.setKeyName("appointmentNumber");
			_search.setObjectDetailsUrl("serviceappointment/" + _serviceAppointment.getServiceAppointmentId());
			_search.setObjectIdName("serviceAppointmentId");
			_search.setObjectIdValue("" + _serviceAppointment.getServiceAppointmentId());
			_search.setObjectName("ServiceAppointment");
			_search.setSearchString(serviceAppointmentManualId);
			entityManager.persist(_search);

		} catch (Exception e) {
		}

		if (_caseDetails.getBusinessDomain().getBusinessDomainId() == 1) { // Battery

			BatteryFSR batteryFSR = new BatteryFSR();
			batteryFSR.setServiceAppointment(_serviceAppointment);
			entityManager.persist(batteryFSR);

			Query updateFSRManualId = entityManager
					.createQuery("update BatteryFSR fsr set fsr.manualId=?1 where fsr.id=?2");
			SimpleDateFormat dateformatter = new SimpleDateFormat("yyyyMMdd");
			String fsrManualId = "FSR-CBMS-LI-" + dateformatter.format(new java.util.Date()) + "-"
					+ com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(batteryFSR.getId()), 5);
			updateFSRManualId.setParameter(1, fsrManualId);
			updateFSRManualId.setParameter(2, batteryFSR.getId());
			updateFSRManualId.executeUpdate();

			// search index entry
			try {
				com.pratikshat.exicom.entity.GlobalSearchIndex _search = new com.pratikshat.exicom.entity.GlobalSearchIndex();
				_search.setKeyName("manualId");
				_search.setObjectDetailsUrl("Batteryfsr/" + batteryFSR.getId());
				_search.setObjectIdName("id");
				_search.setObjectIdValue("" + batteryFSR.getId());
				_search.setObjectName("BatteryFSR");
				_search.setSearchString(fsrManualId);
				entityManager.persist(_search);

			} catch (Exception e) {
			}
		}
	}

	public void addToArchiveExpenseAfterApproval(long objectId, String userEmaiid,
			javax.persistence.EntityManager entityManager) throws Exception {

		new ExpenseApprovedAmountCalculation(objectId, userEmaiid, entityManager).calculate();

		ExpenseManagement expenseManagement = null;

		try {
			Query getQuery = entityManager.createQuery(
					"select em from ExpenseManagement em left join em.businessDomain bd left join em.contact cnt left join em.expensePurpose empur left join em.expenseType emty left join em.costCenter cc left join em.status st left join em.businessArea barea left join em.businessPlace bplc left join em.createdBy cby left join em.modifiedBy mby left join em.owner own where em.expenseManagementId=?1",
					ExpenseManagement.class);
			getQuery.setParameter(1, objectId);
			expenseManagement = (ExpenseManagement) getQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Expense does not exist");
		}

		if (expenseManagement == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Expense does not exist");
		}

		ExpenseAccountsOnly expenseAccountsOnly = null;
		try {
			Query getQuery = entityManager.createQuery(
					"select eao from ExpenseAccountsOnly eao left join eao.expense em left join eao.businessArea ba left join eao.businessPlace bp where em.expenseManagementId=?1",
					ExpenseAccountsOnly.class);
			getQuery.setParameter(1, objectId);
			expenseAccountsOnly = (ExpenseAccountsOnly) getQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Account department data not found");
		}

		if (expenseAccountsOnly == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Account department data not found");
		}

		ExpenseApproved expenseApproved = new ExpenseApproved();
		expenseApproved.setExpenseManagementId(expenseManagement.getExpenseManagementId());
		expenseApproved.setExpenseManagementManualId(expenseManagement.getExpenseManagementManualId());
		expenseApproved.setBusinessDomain(expenseManagement.getBusinessDomain());
		expenseApproved.setContact(expenseManagement.getContact());
		expenseApproved.setExpensePurpose(expenseManagement.getExpensePurpose());
		expenseApproved.setExpenseType(expenseManagement.getExpenseType());
		expenseApproved.setTourStartDate(expenseManagement.getTourStartDate());
		expenseApproved.setTourStartTime(expenseManagement.getTourStartTime());
		expenseApproved.setTourEndDate(expenseManagement.getTourEndDate());
		expenseApproved.setTourEndTime(expenseManagement.getTourEndTime());
		expenseApproved.setCostCenter(expenseManagement.getCostCenter());
		expenseApproved.setPurposeOfVisit(expenseManagement.getPurposeOfVisit());
		expenseApproved.setStatus(expenseManagement.getStatus());
		expenseApproved.setRemark(expenseManagement.getRemark());

		expenseApproved.setDocumentDate(expenseAccountsOnly.getDocumentDate());
		expenseApproved.setNarration(expenseAccountsOnly.getNarration());
		expenseApproved.setBusinessArea(expenseAccountsOnly.getBusinessArea());
		expenseApproved.setBusinessPlace(expenseAccountsOnly.getBusinessPlace());

		expenseApproved.setPost(expenseManagement.isPost());
		expenseApproved.setExpenseSynced(expenseManagement.isExpenseSynced());
		expenseApproved.setAdvanceAmount(expenseManagement.getAdvanceAmount());
		expenseApproved.setVisibleToFinance(expenseManagement.getVisibleToFinance());
		expenseApproved.setTotalTourAmount(expenseManagement.getTotalTourAmount());
		expenseApproved.setTotalLodgingAmount(expenseManagement.getTotalLodgingAmount());
		expenseApproved.setTotalBoardingAmount(expenseManagement.getTotalBoardingAmount());
		expenseApproved.setTotalBpAmount(expenseManagement.getTotalBpAmount());
		expenseApproved.setTotalLocalConveyanceAmount(expenseManagement.getTotalLocalConveyanceAmount());
		expenseApproved.setTotalOtherChargesAmount(expenseManagement.getTotalOtherChargesAmount());
		expenseApproved.setTotalExpense(expenseManagement.getTotalExpense());
		expenseApproved.setTotalTourAmountCompanyPaid(expenseManagement.getTotalTourAmountCompanyPaid());
		expenseApproved.setTotalLodgingCompanyPaid(expenseManagement.getTotalLodgingCompanyPaid());
		expenseApproved.setTotalCompanyPaid(expenseManagement.getTotalCompanyPaid());
		expenseApproved.setNetAmountToPay(expenseManagement.getNetAmountToPay());
		expenseApproved.setTotalTourDays(expenseManagement.getTotalTourDays());
		expenseApproved.setEligibleTourDays(expenseManagement.getEligibleTourDays());
		expenseApproved.setTotalBoardingDays(expenseManagement.getTotalBoardingDays());
		expenseApproved.setCreatedBy(expenseManagement.getCreatedBy());
		expenseApproved.setCreatedDate(expenseManagement.getCreatedDate());
		expenseApproved.setModifiedBy(expenseManagement.getModifiedBy());
		expenseApproved.setModifiedDate(expenseManagement.getModifiedDate());
		expenseApproved.setOwner(expenseManagement.getOwner());
		expenseApproved.setSynced(expenseManagement.isSynced());
		expenseApproved.setTotalLodgingDays(expenseManagement.getTotalLodgingDays());
		entityManager.persist(expenseApproved);

		if (expenseApproved.getContact() != null) {
			if (expenseApproved.getContact().getMobile() != null) {
				String message = String.format("Expense claimed for %s Approved",
						expenseApproved.getExpenseManagementManualId());
				SmsNotifier smsNofifier = new SmsNotifier(entityManager, expenseApproved.getContact().getMobile(),
						message, userEmaiid);
				smsNofifier.sendNotification();

				ApplicationNotificationDetails notifi = new ApplicationNotificationDetails();
				notifi.setNofificationDate(new Date());
				notifi.setNotificationActionUrl("#");
				notifi.setNotificationMessage(String.format("Expense claimed for %s Approved",
						expenseApproved.getExpenseManagementManualId()));
				notifi.setNotificationRead(false);
				notifi.setUserEmailId(expenseApproved.getContact().getEmailId());
				try {
					entityManager.persist(notifi);
					msgTemplate.convertAndSend("/topic/" + notifi.getUserEmailId(), notifi);
				} catch (Exception ex) {

				}
			}
		}
	}

	// VENDOR INVOICE PRE-APPROVAL CHECK GRN APPROVAL_STAPE 1 FOR VENDORINVOICE
	// OBJECT

	private boolean checkVendorInvoiceGrn(EntityManager em, Object id) throws BadRequestException {
		Long _id = (Long) id;
		VendorInvoiceGrn grn = null;
		try {
			grn = em.createQuery("select grn from VendorInvoiceGrn grn where grn=?1", VendorInvoiceGrn.class)
					.setParameter(1, new VendorInvoiceGrn(new VendorInvoiceDetails(_id))).getSingleResult();
			return true;
		} catch (NoResultException ex) {
			throw new BadRequestException("Please update GRN details");
		} catch (NonUniqueResultException ex) {

		}

		return false;
	}

	// VENDOR INVOICE PRE-APPROVAL CHECK SAP APPROVAL_STATE 2 FOR VENDORINVOICE
	// OBJECT

	private boolean checckVendorInvocieSap(EntityManager em, Long id) throws BadRequestException {
		VendorInvoiceSap sap = null;
		try {
			sap = em.createQuery("select sap from VendorInvoiceSap sap where sap=?1", VendorInvoiceSap.class)
					.setParameter(1, new VendorInvoiceSap(new VendorInvoiceDetails(id))).getSingleResult();
			return true;
		} catch (NoResultException ex) {
			throw new BadRequestException("Please update SAP details");
		} catch (NonUniqueResultException ex) {
		}
		return false;
	}

	@Override
	public EmployeeDetails getInitiator(String objectClassName, long objectid) {
		switch (objectClassName) {
		case "ServiceAppointment":
			return em.createQuery(
					"select owner from ServiceAppointment obj join obj.ownerDetails owner where obj.serviceAppointmentId=?1",
					EmployeeDetails.class).setParameter(1, objectid).getSingleResult();

		case "Quote":
			return em.createQuery("select owner from Quote obj join obj.owner owner where obj.id=?1",
					EmployeeDetails.class).setParameter(1, objectid).getSingleResult();

		case "MSLRequest":
			return em.createQuery("select owner from MSLRequest obj join obj.owner owner where obj.id=?1",
					EmployeeDetails.class).setParameter(1, objectid).getSingleResult();

		case "ExpenseManagement":
			return em.createQuery(
					"select owner from ExpenseManagement obj join obj.owner owner where obj.expenseManagementId=?1",
					EmployeeDetails.class).setParameter(1, objectid).getSingleResult();

		case "Annexure":
			return em.createQuery("select owner from Annexure obj join obj.owner owner where obj.id=?1",
					EmployeeDetails.class).setParameter(1, objectid).getSingleResult();

		case "CaseDetails":
			return em.createQuery("select owner from CaseDetails obj join obj.ownerDetails owner where obj.caseId=?1",
					EmployeeDetails.class).setParameter(1, objectid).getSingleResult();

		case "WccDetails":
			return em.createQuery("select owner from WccDetails obj join obj.owner owner where obj.id=?1",
					EmployeeDetails.class).setParameter(1, objectid).getSingleResult();

		case "VendorInvoiceDetails":
			System.out.println("the invoice id is ==== "+objectid);
			return em.createQuery("select owner from VendorInvoiceDetails obj join obj.owner owner where obj.id=?1",
					EmployeeDetails.class).setParameter(1, objectid).getSingleResult();
		}
		return null;
	}

	public void sendMailToCustomer(Quote quote, UploadedFiles uploadedFile, EmployeeDetails emp) {

		com.pratikshat.exicom.entity.TemplateDetails template = null;
		// System.out.println("mail sending process open");
		try {
			template = em
					.createQuery("select tplt from TemplateDetails tplt where tplt.templaeCode=?1",
							com.pratikshat.exicom.entity.TemplateDetails.class)
					.setParameter(1, "quote_approved").getSingleResult();
			String body = template.getBody().replace("{{quoteNumber}}", quote.getQuoteName());
			StringBuilder s = new StringBuilder();
			s.append(
					"<table border=\"1\"><tr><th>Customer</th><th>Service Center</th><th>Site ID</th><th>Site Name</th><th>Indus Id</th><th>Case Number</th><th>Basic Amount</th></tr>");
			s.append("<tr>");
			s.append("<td>" + quote.getOppertunity().getAccounts().getName() + "</td>");
			s.append("<td>" + quote.getOppertunity().getCaseDetails().getSite().getServiceCenter().getLegalEntityName()
					+ "</td>");
			s.append("<td>" + quote.getOppertunity().getCaseDetails().getSite().getManualSiteId() + "</td>");
			s.append("<td>" + quote.getOppertunity().getCaseDetails().getSite().getSiteName() + "</td>");
			if(quote.getOppertunity().getCaseDetails().getSite().getCustomer()!=null && quote.getOppertunity().getCaseDetails().getSite().getCustomer().contains("INDUS") ) {
				s.append("<td>" + quote.getOppertunity().getCaseDetails().getSite().getIndusId()+ "</td>");
			}
			else {
				s.append("<td>" +""+ "</td>");
			}
			
			s.append("<td>" + quote.getOppertunity().getCaseDetails().getManualId() + "</td>");
			s.append("<td>" + String.format("%.2f", quote.getSubTotal()) + "</td>");
			s.append("</tr></table>");

			body = body.replace("{{htmlcontent}}", s.toString());
			body = "<html><body>" + body + "</body></html>";
			List<String> mailto = new ArrayList<String>();
			List<String> ccList = new ArrayList<String>();
			List<File> fileList = new ArrayList<File>();
			String[] _cc = quote.getCcEmailIds().split(";");
			for (int i = 0; i < _cc.length; i++) {
				ccList.add(_cc[i]);
			}
			String[] _to = quote.getToEmailIds().split(";");
			for (int i = 0; i < _to.length; i++) {
				mailto.add(_to[i]);
			}
			File path = new File(fileStorageProperties.getUploadDir() + File.separator + uploadedFile.getFileName());
			fileList.add(path);
			Mailer _mailer = new Mailer(em, mailto, body, "Work order request for " + quote.getQuoteName(), null,
					emp.getEmailId(), fileList, ccList, "Quote", String.valueOf(quote.getId()));

		} catch (NoResultException | NonUniqueResultException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void sendMailToCustomerCareTeamOnApproval(Quote quote, EmployeeDetails emp, EntityManager em) {

		List<QuoteLineItemResponse> quoteLineItemList = null;
		try {
			Query selectQuoteLineItemQuery = em.createQuery(
					"select new com.pratikshat.exicom.model.response.QuoteLineItemResponse(qli) from QuoteLineItem qli left join qli.quote qt left join qli.priceBookEntries pbe left join pbe.product prd left join pbe.priceBook where qli.isDeleted=false and qt.id=?1",
					QuoteLineItemResponse.class);
			selectQuoteLineItemQuery.setParameter(1, quote.getId());
			quoteLineItemList = selectQuoteLineItemQuery.getResultList();

		} catch (NoResultException ex) {

		}

		MailerEntity mailerEntity = em.createQuery("select m from MailerEntity m where m.active=?1", MailerEntity.class)
				.setParameter(1, true).getSingleResult();

		com.pratikshat.exicom.entity.TemplateDetails template = null;
		// System.out.println("mail sending process open");
		try {
			template = em
					.createQuery("select tplt from TemplateDetails tplt where tplt.templaeCode=?1",
							com.pratikshat.exicom.entity.TemplateDetails.class)
					.setParameter(1, "quote_approved_cc").getSingleResult();
			String content = template.getBody().replace("{{quoteNumber}}", quote.getQuoteName());
			String body = content.replace("{{quoteId}}", String.valueOf(quote.getId()));
			StringBuilder s = new StringBuilder();
			s.append(
					"<table border=\"1\"><tr><th>Sr.No</th><th>Part Code</th><th>List Price</th><th>Sales Price</th><th>Quantity</th><th>Total Price</th></tr>");
			if (quoteLineItemList != null && !quoteLineItemList.isEmpty()) {

				for (int i = 0; i < quoteLineItemList.size(); i++) {

					s.append("<tr>");
					s.append("<td>" + (i + 1) + "</td>");
					s.append("<td>" + quoteLineItemList.get(i).getProductPartCode() + "</td>");
					s.append("<td>" + String.format("%.2f", quoteLineItemList.get(i).getListPrice()) + "</td>");
					s.append("<td>" + String.format("%.2f", quoteLineItemList.get(i).getPrice()) + "</td>");
					s.append("<td>" + quoteLineItemList.get(i).getQuantity() + "</td>");
					s.append("<td>" + String.format("%.2f", quoteLineItemList.get(i).getTotalPrice()) + "</td>");
					s.append("</tr>");
				}

			}
			s.append("</table>");
			body = body.replace("{{htmlcontent}}", s.toString());
			body = "<html><body>" + body + "</body></html>";

			List<String> mailto = new ArrayList<String>();
			mailto.add(mailerEntity.getDefaultUserName());

			List<String> ccList = new ArrayList<String>();
			String[] _cc = quote.getCcEmailIds().split(";");
			for (int i = 0; i < _cc.length; i++) {
				ccList.add(_cc[i]);
			}

			List<File> fileList = new ArrayList<File>();
			Mailer _mailer = new Mailer(em, mailto, body, "Quote Approved # " + quote.getQuoteName(), null,
					emp.getEmailId(), fileList, ccList, "Quote", String.valueOf(quote.getId()));

		} catch (NoResultException | NonUniqueResultException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void sendMailToCustomerCareTeamOnRejection(Quote quote, EmployeeDetails emp, EntityManager em) {

		List<QuoteLineItemResponse> quoteLineItemList = null;
		try {
			Query selectQuoteLineItemQuery = em.createQuery(
					"select new com.pratikshat.exicom.model.response.QuoteLineItemResponse(qli) from QuoteLineItem qli left join qli.quote qt left join qli.priceBookEntries pbe left join pbe.product prd left join pbe.priceBook where qli.isDeleted=false and qt.id=?1",
					QuoteLineItemResponse.class);
			selectQuoteLineItemQuery.setParameter(1, quote.getId());
			quoteLineItemList = selectQuoteLineItemQuery.getResultList();

		} catch (NoResultException ex) {

		}

		MailerEntity mailerEntity = em.createQuery("select m from MailerEntity m where m.active=?1", MailerEntity.class)
				.setParameter(1, true).getSingleResult();

		com.pratikshat.exicom.entity.TemplateDetails template = null;
		// System.out.println("mail sending process open");
		try {
			template = em
					.createQuery("select tplt from TemplateDetails tplt where tplt.templaeCode=?1",
							com.pratikshat.exicom.entity.TemplateDetails.class)
					.setParameter(1, "quote_rejected_cc").getSingleResult();
			String content = template.getBody().replace("{{quoteNumber}}", quote.getQuoteName());
			String body = content.replace("{{quoteId}}", String.valueOf(quote.getId()));
			StringBuilder s = new StringBuilder();
			s.append(
					"<table border=\"1\"><tr><th>Sr.No</th><th>Part Code</th><th>List Price</th><th>Sales Price</th><th>Quantity</th><th>Total Price</th></tr>");
			if (quoteLineItemList != null && !quoteLineItemList.isEmpty()) {

				for (int i = 0; i < quoteLineItemList.size(); i++) {

					s.append("<tr>");
					s.append("<td>" + (i + 1) + "</td>");
					s.append("<td>" + quoteLineItemList.get(i).getProductPartCode() + "</td>");
					s.append("<td>" + String.format("%.2f", quoteLineItemList.get(i).getListPrice()) + "</td>");
					s.append("<td>" + String.format("%.2f", quoteLineItemList.get(i).getPrice()) + "</td>");
					s.append("<td>" + quoteLineItemList.get(i).getQuantity() + "</td>");
					s.append("<td>" + String.format("%.2f", quoteLineItemList.get(i).getTotalPrice()) + "</td>");
					s.append("</tr>");
				}

			}
			s.append("</table>");
			body = body.replace("{{htmlcontent}}", s.toString());
			body = "<html><body>" + body + "</body></html>";

			List<String> mailto = new ArrayList<String>();
			mailto.add(mailerEntity.getDefaultUserName());

			List<String> ccList = new ArrayList<String>();
			String[] _cc = quote.getCcEmailIds().split(";");
			for (int i = 0; i < _cc.length; i++) {
				ccList.add(_cc[i]);
			}

			List<File> fileList = new ArrayList<File>();
			Mailer _mailer = new Mailer(em, mailto, body, "Quote Rejected # " + quote.getQuoteName(), null,
					emp.getEmailId(), fileList, ccList, "Quote", String.valueOf(quote.getId()));

		} catch (NoResultException | NonUniqueResultException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public List<Map<String, Object>> getAdvanceSearchResult(Map<String, Object> search, LoginUser user)
			throws BadRequestException {
		String stQuery = "SELECT tobe.id AS `id`,\r\n" + "	tobe.object_name AS `objectName`,\r\n"
				+ "	tobe.`assigned_date` AS `assignedDate`,\r\n" + "	tobe.object_id AS `objectId`,\r\n"
				+ "	tobe.object_manual_id AS `objectManualId`,\r\n" + "	obj.object_lable_name,\r\n"
				+ "	tobe.`object_url` AS `objectUrl`,\r\n" + "	tobe.`status` AS `status`,\r\n"
				+ "	tobe.stage AS `stage`,\r\n" + "	tobe.action AS `action`,\r\n" + "	tobe.remark AS `remark`,\r\n"
				+ "	tobe.approved_date AS `approvedDate`,\r\n" + "	tobe.`previous_remarks` AS `previousRemarks`\r\n"
				+ "	FROM to_be_approved tobe JOIN `object_definition` obj ON obj.object_name =tobe.object_name";
		Map<String, String> rlGrp = dbProfile.getAccountAndRoleGroup(user);
		String _rl = rlGrp.get("roleGroup").toString();
		List<String> usrs = null;
		if (_rl.equalsIgnoreCase("COMMERCIAL") || _rl.equalsIgnoreCase("FINANCE")
				|| _rl.equalsIgnoreCase("CUSTOMERCARE") || _rl.equalsIgnoreCase("DCT_MAIN_STORE")) {
			usrs = dbProfile.getUsersInMainRoleGroup(_rl);
		}
		else {
			usrs = dbProfile.getSameroleUsers(user);
		}

		StringBuilder _usrs = new StringBuilder();
		if (usrs != null && usrs.size() > 0) {
			int i = 0;
			for (String u : usrs) {
				if (i != 0)
					_usrs.append(",");
				_usrs.append("'" + u + "'");
				i++;
			}
		}
		stQuery += " where tobe.assigned_to_email_id in (" + _usrs.toString() + ")";
		List<String> params=new ArrayList<String>();
		if(search.get("request_number")!=null&&!search.get("request_number").toString().equalsIgnoreCase("")) {
			stQuery+=" and tobe.object_manual_id like ?";
			params.add("%"+ search.get("request_number").toString()+"%");
		}
		if(search.get("type_name")!=null&&!search.get("type_name").toString().equalsIgnoreCase("")) {
			stQuery+=" and obj.object_lable_name like ?";
			params.add("%"+search.get("type_name").toString()+"%");
		}
		if(search.get("date_from")!=null&&!search.get("date_from").toString().equalsIgnoreCase("")&&search.get("date_to")!=null&&!search.get("date_to").toString().equalsIgnoreCase("")) {
			stQuery+=" and date_format(tobe.assigned_date, '%Y-%m-%d') between date_format(?, '%Y-%m-%d') and date_format(?, '%Y-%m-%d')";
			params.add(search.get("date_from").toString());
			params.add(search.get("date_to").toString());
		}
		if(search.get("status")!=null&&!search.get("status").toString().equalsIgnoreCase("")) {
			stQuery+=" and tobe.status =?";
			params.add(search.get("status").toString());
		}
		if(search.get("action")!=null&&!search.get("action").toString().equalsIgnoreCase("")) {
			if(search.get("action").toString().equalsIgnoreCase("Pending")) {
				stQuery+=" and tobe.action IS NULL";
			}else {
				stQuery+=" and tobe.action like ?";
				params.add("%"+search.get("action").toString()+"%");	
			}
			
		}
		List<Map<String,Object>> fresult=null;
		if(params!=null&&params.size()>0) {
			fresult= jdbc.queryForList(stQuery,params.toArray());
		}
		else {
			fresult= jdbc.queryForList(stQuery);
		}
		
		if(fresult!=null && !fresult.isEmpty()) {
			for(Map<String,Object> f:fresult) {
				f.put("initiator", getInitiator(f.get("objectName").toString(),Long.parseLong(f.get("objectId").toString())));
			}
		}
		
		
		return fresult;
	}
}
