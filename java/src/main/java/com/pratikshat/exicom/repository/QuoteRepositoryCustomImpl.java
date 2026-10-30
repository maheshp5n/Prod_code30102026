package com.pratikshat.exicom.repository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pratikshat.exicom.config.FileStorageProperties;
import com.pratikshat.exicom.entity.ApplicationNotificationDetails;
import com.pratikshat.exicom.entity.Approval;
import com.pratikshat.exicom.entity.ApprovalSteps;
import com.pratikshat.exicom.entity.Assets;
import com.pratikshat.exicom.entity.BatteryFSR;
import com.pratikshat.exicom.entity.BusinessDomain;
import com.pratikshat.exicom.entity.CaseDetails;
import com.pratikshat.exicom.entity.CaseStageDetails;
import com.pratikshat.exicom.entity.CaseStatusDetails;
import com.pratikshat.exicom.entity.CountryDetails;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.ExpenseManagement;
import com.pratikshat.exicom.entity.ExpenseManagementBusinessArea;
import com.pratikshat.exicom.entity.ExpenseManagementBusinessPlace;
import com.pratikshat.exicom.entity.ExpenseManagementCostCenter;
import com.pratikshat.exicom.entity.ExpenseManagementExpensePurpose;
import com.pratikshat.exicom.entity.ExpenseManagementExpenseType;
import com.pratikshat.exicom.entity.ExpenseManagementStatus;
import com.pratikshat.exicom.entity.IGSTBillingState;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.MailLog;
import com.pratikshat.exicom.entity.MailLogAttachements;
import com.pratikshat.exicom.entity.ObjectDefinition;
import com.pratikshat.exicom.entity.Oppertunity;
import com.pratikshat.exicom.entity.OppertunityForecastCategory;
import com.pratikshat.exicom.entity.OppertunityLineItem;
import com.pratikshat.exicom.entity.OppertunityStage;
import com.pratikshat.exicom.entity.PendingResponsibility;
import com.pratikshat.exicom.entity.PriceBook;
import com.pratikshat.exicom.entity.PriceBookEntries;
import com.pratikshat.exicom.entity.Quote;
import com.pratikshat.exicom.entity.QuoteLineItem;
import com.pratikshat.exicom.entity.QuotePdfHeaderAndFooter;
import com.pratikshat.exicom.entity.QuoteStatus;
import com.pratikshat.exicom.entity.QuoteTermsAndConditions;
import com.pratikshat.exicom.entity.ServiceAppointment;
import com.pratikshat.exicom.entity.ServiceRecource;
import com.pratikshat.exicom.entity.StateProvinceDetails;
import com.pratikshat.exicom.entity.StateWithQuotePdf;
import com.pratikshat.exicom.entity.TaxAndVisitCharges;
import com.pratikshat.exicom.entity.ToBeApproved;
import com.pratikshat.exicom.entity.UploadedFiles;
import com.pratikshat.exicom.event.QuoteHeaderFooterPageEvent;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.FileStorageException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.ExpenseManagementRequest;
import com.pratikshat.exicom.model.request.QuoteLineItemRequest;
import com.pratikshat.exicom.model.request.QuoteRequest;
import com.pratikshat.exicom.model.request.WorkOrderRequest;
import com.pratikshat.exicom.model.response.MailLogResponse;
import com.pratikshat.exicom.model.response.QuoteLineItemResponse;
import com.pratikshat.exicom.model.response.QuoteResponse;
import com.pratikshat.exicom.utils.QuotePDFGeneration;
import com.pratikshat.exicom.utils.ServiceAppointmentUtil;

@Repository
public class QuoteRepositoryCustomImpl implements QuoteRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	FileStorageProperties fileStorageProperties;
	
	@Autowired
	UserProfileRepository dbProfile;

	@Autowired
	com.pratikshat.exicom.interceptor.WorkflowExecuter workflow;

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, RuntimeException.class, Exception.class,
			SQLException.class })
	@Override
	public QuoteRequest saveQuote(QuoteRequest quoteRequest, LoginUser user) throws Exception {
		if (quoteRequest.getQuoteName() != null) {
			if (quoteRequest.getOppertunityId() != 0) {
				if (quoteRequest.getValidityDays() != 0) {
					if (quoteRequest.getToEmailIds() != null && quoteRequest.getToEmailIds().length() > 0) {
						if (quoteRequest.getCcEmailIds() != null && quoteRequest.getCcEmailIds().length() > 0) {

							Quote quoteExist = null;
							try {
								Query selectQuoteQuery = entityManager
										.createQuery("select qt from Quote qt where qt.quoteName=?1", Quote.class);
								selectQuoteQuery.setParameter(1, quoteRequest.getQuoteName());
								quoteExist = (Quote) selectQuoteQuery.getSingleResult();
							} catch (NoResultException ex) {

							}
							if (quoteExist != null) {
								throw new com.pratikshat.exicom.exception.BadRequestException(
										"Quote already exist with quate name " + quoteRequest.getQuoteName());
							}

							Oppertunity oppertunity = null;
							try {
								Query selectOppertunityQuery = entityManager.createQuery(
										"select opt from Oppertunity opt where opt.oppertunityId=?1",
										Oppertunity.class);
								selectOppertunityQuery.setParameter(1, quoteRequest.getOppertunityId());
								oppertunity = (Oppertunity) selectOppertunityQuery.getSingleResult();
							} catch (NoResultException ex) {

							}
							if (oppertunity == null) {
								throw new com.pratikshat.exicom.exception.BadRequestException(
										"Opportunity does not exist");
							}

							List<Integer> quoteStatusList = new ArrayList<Integer>();
							quoteStatusList.add(1); // Draft;
							quoteStatusList.add(2); // Work Order Received
							quoteStatusList.add(4); // Quote Sent
							quoteStatusList.add(6); // Submitted For Approval
							quoteStatusList.add(7); // Approved
							quoteStatusList.add(8); // Invoiced
							quoteStatusList.add(10);// Approved by Store

							Quote quoteForOppertunity = null;
							try {
								Query selectQuoteQuery = entityManager.createQuery(
										"select qt from Quote qt left join qt.quoteStatus qts left join qt.oppertunity opr where opr.oppertunityId=?1 and qts.id in ?2",
										Quote.class);
								selectQuoteQuery.setParameter(1, quoteRequest.getOppertunityId());
								selectQuoteQuery.setParameter(2, quoteStatusList);
								quoteForOppertunity = (Quote) selectQuoteQuery.getSingleResult();
							} catch (NoResultException ex) {

							}
							if (quoteForOppertunity != null) {
								throw new com.pratikshat.exicom.exception.BadRequestException(
										"Quote already exist for the Oppertunity "
												+ oppertunity.getOppertunityNumber());
							}

							Quote quote = new Quote();

							quote.setQuoteName(quoteRequest.getQuoteName());
							quote.setApproved(false);
							quote.setApprovalCheck(0);
							quote.setOppertunity(oppertunity);
							quote.setValidityDays(quoteRequest.getValidityDays());

							Calendar cl = Calendar.getInstance();
							cl.add(Calendar.DATE, quoteRequest.getValidityDays());
							Date expiryDate = cl.getTime();

							if (expiryDate != null) {
								String expiryDateString = sdf.format(expiryDate);
								Date expiryDateFormatted = sdf.parse(expiryDateString);
								quote.setExpiryDate(expiryDateFormatted);
							}

							quote.setSyncing(false);
							quote.setQuoteStatus(new QuoteStatus(1)); // draft
							quote.setRemark(quoteRequest.getRemark());

							quote.setBillingName(quoteRequest.getBillingName());
							quote.setBillingStreet(quoteRequest.getBillingStreet());
							quote.setBillingCity(quoteRequest.getBillingCity());
							if (quoteRequest.getBillingCountryId() != null
									&& quoteRequest.getBillingCountryId().length() > 0) {
								quote.setBillingCountry(new CountryDetails(quoteRequest.getBillingCountryId()));
							} else {
								quote.setBillingCountry(null);
							}
							if (quoteRequest.getBillingStateId() != 0) {
								quote.setBillingState(new StateProvinceDetails(quoteRequest.getBillingStateId()));
							} else {
								quote.setBillingState(null);
							}
							quote.setBillingPostalCode(quoteRequest.getBillingPostalCode());

							quote.setShippingName(quoteRequest.getShippingName());
							quote.setShippingStreet(quoteRequest.getShippingStreet());
							quote.setShippingCity(quoteRequest.getShippingCity());
							if (quoteRequest.getShippingCountryId() != null
									&& quoteRequest.getShippingCountryId().length() > 0) {
								quote.setShippingCountry(new CountryDetails(quoteRequest.getShippingCountryId()));
							} else {
								quote.setShippingCountry(null);
							}
							if (quoteRequest.getShippingStateId() != 0) {
								quote.setShippingState(new StateProvinceDetails(quoteRequest.getShippingStateId()));
							} else {
								quote.setShippingState(null);
							}
							quote.setShippingPostalCode(quoteRequest.getShippingPostalCode());

							quote.setOtherTermsAndConditions(quoteRequest.getOtherTermsAndConditions());
							quote.setToEmailIds(quoteRequest.getToEmailIds());
							quote.setCcEmailIds(quoteRequest.getCcEmailIds());

							quote.setCreatedBy(new EmployeeDetails(user.getUsername()));
							quote.setCreatedDate(new Date());

							quote.setModifiedBy(new EmployeeDetails(user.getUsername()));
							quote.setModifiedDate(new Date());

							quote.setOwner(new EmployeeDetails(user.getUsername()));
							quote.setBusinessDomain(new BusinessDomain(quoteRequest.getBusinessDomainId()));
							entityManager.persist(quote);

							// String[] oppertunityArray = oppertunity.getOppertunityNumber().split("-");

							Query updateManualId = entityManager
									.createQuery("update Quote qt set qt.quoteNumber=?1 where qt.id=?2");
							SimpleDateFormat dateformatter = new SimpleDateFormat("yyyyMMdd");
//							String manualId = "Quote-" + dateformatter.format(new java.util.Date()) + "-"
//									+ com.pratikshat.exicom.config.MiscellaneousUtils
//											.fillZero(String.valueOf(quote.getId()), 5);
							
							String manualId = "QT-"+ com.pratikshat.exicom.config.MiscellaneousUtils
											.fillZero(String.valueOf(quote.getId()), 7);

							// String manualId = "Quote-" + oppertunityArray[1] + "-" + oppertunityArray[2];

							updateManualId.setParameter(1, manualId);
							updateManualId.setParameter(2, quote.getId());
							updateManualId.executeUpdate();

							// search index entry
							try {
								com.pratikshat.exicom.entity.GlobalSearchIndex _search = new com.pratikshat.exicom.entity.GlobalSearchIndex();
								_search.setKeyName("quoteName");
								_search.setObjectDetailsUrl("Quote/" + quote.getId());
								_search.setObjectIdName("id");
								_search.setObjectIdValue("" + quote.getId());
								_search.setObjectName("Quote");
								_search.setSearchString(quote.getQuoteName());
								entityManager.persist(_search);

							} catch (Exception e) {
							}

							quoteRequest.setId(quote.getId());

							entityManager.flush();
							entityManager.clear();
							return quoteRequest;

						} else {
							throw new com.pratikshat.exicom.exception.BadRequestException("CC Email ID is null");
						}
					} else {
						throw new com.pratikshat.exicom.exception.BadRequestException("To Email ID is null");
					}
				} else {
					throw new com.pratikshat.exicom.exception.BadRequestException("Validity days incorrect");
				}
			} else {
				throw new com.pratikshat.exicom.exception.BadRequestException("Opportunity Id incorrect");
			}
		} else {
			throw new com.pratikshat.exicom.exception.BadRequestException("Quote name is null");
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, RuntimeException.class, Exception.class,
			SQLException.class })
	@Override
	public QuoteRequest updateQuote(QuoteRequest quoteRequest, LoginUser user) throws Exception {
		if (quoteRequest.getQuoteName() != null) {
			if (quoteRequest.getOppertunityId() != 0) {
				if (quoteRequest.getValidityDays() != 0) {
					if (quoteRequest.getToEmailIds() != null && quoteRequest.getToEmailIds().length() > 0) {
						if (quoteRequest.getCcEmailIds() != null && quoteRequest.getCcEmailIds().length() > 0) {

							Quote quoteExist = null;
							try {
								Query selectQuoteQuery = entityManager
										.createQuery("select qt from Quote qt where qt.quoteName=?1", Quote.class);
								selectQuoteQuery.setParameter(1, quoteRequest.getQuoteName());
								quoteExist = (Quote) selectQuoteQuery.getSingleResult();
							} catch (NoResultException ex) {

							}
							if (quoteExist != null && quoteExist.getId() != quoteRequest.getId()) {
								throw new com.pratikshat.exicom.exception.BadRequestException(
										"Another Quote already exist with same quate name "
												+ quoteRequest.getQuoteName());
							}

							Quote quote = null;
							try {
								Query selectQuoteQuery = entityManager.createQuery(
										"select qt from Quote qt left join qt.oppertunity opr left join opr.accounts acn left join opr.caseDetails cd left join cd.site st left join st.circle crl left join qt.quoteStatus qtst left join qt.billingCountry bc left join qt.billingState bs left join qt.shippingCountry sc left join qt.shippingState ss left join qt.createdBy createdby left join qt.modifiedBy modified left join qt.owner onr where qt.id=?1",
										Quote.class);
								selectQuoteQuery.setParameter(1, quoteRequest.getId());
								quote = (Quote) selectQuoteQuery.getSingleResult();
							} catch (NoResultException ex) {
								throw new com.pratikshat.exicom.exception.BadRequestException("Quote does not exist");
							}

							if (quote != null) {

								if (quote.getOwner().getEmailId().equalsIgnoreCase(user.getUsername())||dbProfile.samroleUsers(user.getUsername(), quote.getOwner().getEmailId())||dbProfile.getAccountAndRoleGroup(user).get("roleGroup").equalsIgnoreCase("Commercial Executive")) {
									int statusId = quote.getQuoteStatus().getId();
									List<Integer> list = new ArrayList<>();
									list.add(1);	// Draft 
									list.add(3);	// Rejected
									list.add(11);	// Rejected by store 
									list.add(4);	// Quote Sent
									if(!list.contains(statusId)) {
										throw new BadRequestException("You can't edit this record");
									}else {
										
										if (quote != null && quote.getQuoteStatus() != null
												&& quote.getQuoteStatus().getId() == 1) { // Draft

											List<Integer> list1 = new ArrayList<Integer>();
											list1.add(2); // Work Order Received
											list1.add(5); // Work Order Not Received
											list1.add(8); // Invoiced
											list1.add(9); // Withdrawn

											if (list1.contains(quoteRequest.getQuoteStatusId())) {
												throw new com.pratikshat.exicom.exception.BadRequestException(
														"Please send the quote for approval");
											}

										}
										
										quote = updateQuoteWithOwner(quote, quoteRequest, user, entityManager);
										
										CaseDetails caseDetails = null;
										try {
											Query selectCaseQuery=entityManager.createQuery("select cd from Oppertunity opr left join opr.caseDetails cd left join cd.caseType ct left join cd.caseReason cr where opr.isDeleted=false and opr.oppertunityId=?1 ",CaseDetails.class);
											selectCaseQuery.setParameter(1,quote.getOppertunity().getOppertunityId());
											caseDetails=(CaseDetails)selectCaseQuery.getSingleResult();
										}catch(NoResultException ex) {
											
										}
										
										
										Oppertunity oppertunity = null;
										try {
											Query selectOppertunityQuery=entityManager.createQuery("select opr from Oppertunity opr left join opr.oppertunityStage opst left join opr.forcastCategory opfc where opr.isDeleted=false and opr.oppertunityId=?1",Oppertunity.class);
											selectOppertunityQuery.setParameter(1,quote.getOppertunity().getOppertunityId());
											oppertunity=(Oppertunity)selectOppertunityQuery.getSingleResult();
										}catch(NoResultException ex) {
											
										}
										
										if(caseDetails!=null && oppertunity!=null) {
											
											List<Integer> list1 = new ArrayList<Integer>();
											list1.add(5); // Work Order Not Received
											list1.add(9); // Withdrawn
											
											if (list1.contains(quoteRequest.getQuoteStatusId())) {
												
												oppertunity.setForcastCategory(new OppertunityForecastCategory(5));	//Closed
												oppertunity.setOppertunityStage(new OppertunityStage(3));	//Closed Lost
												oppertunity.setCloseDate(new Date());
												entityManager.persist(oppertunity);
												
												
												
												caseDetails.setStatus(new CaseStatusDetails(4));	//Rejected
												caseDetails.setStage(new CaseStageDetails(16));	// Rejected	
												
												entityManager.persist(caseDetails);
											}
											
										}
										
									}
									//throw new UnauthoriseException("You are not authorised to edit this record");
								}else {
									
									ToBeApproved _toBeApproved=null;
									try {
										_toBeApproved=entityManager.createQuery("select apr from ToBeApproved apr join apr.assignedTo asg where apr.ObjectName=?1 and apr.objectId=?2 and apr.status=?3",ToBeApproved.class)
												.setParameter(1,"Quote")
												.setParameter(2,quote.getId())
												.setParameter(3,false)
												.getSingleResult();
									}catch(javax.persistence.NoResultException ex) {
										throw new BadRequestException("You are not authorised");
									}catch(javax.persistence.NonUniqueResultException ex) {
										throw new BadRequestException("You are not authorised");
									}
									if(_toBeApproved.getAssignedTo().getEmailId().equalsIgnoreCase(user.getUsername())){
										quote = updateQuoteOtherThanOwner(quote, quoteRequest, user, entityManager);
									}else {
										if(!dbProfile.samroleUsers(_toBeApproved.getAssignedTo().getEmailId(), user.getUsername())) {
											Map<String,String> rol=dbProfile.getAccountAndRoleGroup(user);
											if(!rol.get("roleGroup").equalsIgnoreCase("Commercial Executive"))
												throw new UnauthoriseException("You are not authorised");
										}
										
									}
								}

								workflow.executeWorkflow(entityManager, quote, "INSERT", "PRE", user);
								entityManager.persist(quote);
								workflow.executeWorkflow(entityManager, quote, "INSERT", "POST", user);

							} else {
								throw new com.pratikshat.exicom.exception.BadRequestException("Quote does not exist");
							}
							entityManager.flush();
							entityManager.clear();

							return quoteRequest;

						} else {
							throw new com.pratikshat.exicom.exception.BadRequestException("CC Email ID is null");
						}
					} else {
						throw new com.pratikshat.exicom.exception.BadRequestException("To Email ID is null");
					}
				} else {
					throw new com.pratikshat.exicom.exception.BadRequestException("Validity days incorrect");
				}
			} else {
				throw new com.pratikshat.exicom.exception.BadRequestException("Opportunity Id incorrect");
			}
		} else {
			throw new com.pratikshat.exicom.exception.BadRequestException("Quote name is null");
		}
	}
	
	
private Quote updateQuoteWithOwner(Quote quote, QuoteRequest quoteRequest, LoginUser user, EntityManager entityManager)throws Exception {
		
	quote.setQuoteName(quoteRequest.getQuoteName());
	quote.setValidityDays(quoteRequest.getValidityDays());

	Calendar cl = Calendar.getInstance();
	cl.add(Calendar.DATE, quoteRequest.getValidityDays());
	Date expiryDate = cl.getTime();

	if (expiryDate != null) {
		String expiryDateString = sdf.format(expiryDate);
		Date expiryDateFormatted = sdf.parse(expiryDateString);
		quote.setExpiryDate(expiryDateFormatted);
	}

	quote.setSyncing(quoteRequest.isSyncing());
	if (quoteRequest.getQuoteStatusId() != 0) {
		quote.setQuoteStatus(new QuoteStatus(quoteRequest.getQuoteStatusId()));
	} else {
		quote.setQuoteStatus(null);
	}

	quote.setSubmittedDate(quoteRequest.getSubmittedDate());

	quote.setRemark(quoteRequest.getRemark());

	quote.setBillingName(quoteRequest.getBillingName());
	quote.setBillingStreet(quoteRequest.getBillingStreet());
	quote.setBillingCity(quoteRequest.getBillingCity());
	if (quoteRequest.getBillingCountryId() != null) {
		quote.setBillingCountry(new CountryDetails(quoteRequest.getBillingCountryId()));
	} else {
		quote.setBillingCountry(null);
	}
	if (quoteRequest.getBillingStateId() != 0) {
		quote.setBillingState(new StateProvinceDetails(quoteRequest.getBillingStateId()));
	} else {
		quote.setBillingState(null);
	}
	quote.setBillingPostalCode(quoteRequest.getBillingPostalCode());

	quote.setShippingName(quoteRequest.getShippingName());
	quote.setShippingStreet(quoteRequest.getShippingStreet());
	quote.setShippingCity(quoteRequest.getShippingCity());
	if (quoteRequest.getShippingCountryId() != null) {
		quote.setShippingCountry(new CountryDetails(quoteRequest.getShippingCountryId()));
	} else {
		quote.setShippingCountry(null);
	}
	if (quoteRequest.getShippingStateId() != 0) {
		quote.setShippingState(new StateProvinceDetails(quoteRequest.getShippingStateId()));
	} else {
		quote.setShippingState(null);
	}
	quote.setShippingPostalCode(quoteRequest.getShippingPostalCode());

	quote.setOtherTermsAndConditions(quoteRequest.getOtherTermsAndConditions());
	quote.setToEmailIds(quoteRequest.getToEmailIds());
	quote.setCcEmailIds(quoteRequest.getCcEmailIds());

	quote.setModifiedBy(new EmployeeDetails(user.getUsername()));
	quote.setModifiedDate(new Date());
		return quote;
	}
	
	private Quote updateQuoteOtherThanOwner(Quote quote, QuoteRequest quoteRequest, LoginUser user, EntityManager entityManager)throws Exception {
		
		
		quote.setQuoteName(quoteRequest.getQuoteName());
		quote.setValidityDays(quoteRequest.getValidityDays());

		Calendar cl = Calendar.getInstance();
		cl.add(Calendar.DATE, quoteRequest.getValidityDays());
		Date expiryDate = cl.getTime();

		if (expiryDate != null) {
			String expiryDateString = sdf.format(expiryDate);
			Date expiryDateFormatted = sdf.parse(expiryDateString);
			quote.setExpiryDate(expiryDateFormatted);
		}

		quote.setSyncing(quoteRequest.isSyncing());
		if (quoteRequest.getQuoteStatusId() != 0) {
			quote.setQuoteStatus(new QuoteStatus(quoteRequest.getQuoteStatusId()));
		} else {
			quote.setQuoteStatus(null);
		}

		quote.setSubmittedDate(quoteRequest.getSubmittedDate());

		quote.setRemark(quoteRequest.getRemark());

		quote.setBillingName(quoteRequest.getBillingName());
		quote.setBillingStreet(quoteRequest.getBillingStreet());
		quote.setBillingCity(quoteRequest.getBillingCity());
		if (quoteRequest.getBillingCountryId() != null) {
			quote.setBillingCountry(new CountryDetails(quoteRequest.getBillingCountryId()));
		} else {
			quote.setBillingCountry(null);
		}
		if (quoteRequest.getBillingStateId() != 0) {
			quote.setBillingState(new StateProvinceDetails(quoteRequest.getBillingStateId()));
		} else {
			quote.setBillingState(null);
		}
		quote.setBillingPostalCode(quoteRequest.getBillingPostalCode());

		quote.setShippingName(quoteRequest.getShippingName());
		quote.setShippingStreet(quoteRequest.getShippingStreet());
		quote.setShippingCity(quoteRequest.getShippingCity());
		if (quoteRequest.getShippingCountryId() != null) {
			quote.setShippingCountry(new CountryDetails(quoteRequest.getShippingCountryId()));
		} else {
			quote.setShippingCountry(null);
		}
		if (quoteRequest.getShippingStateId() != 0) {
			quote.setShippingState(new StateProvinceDetails(quoteRequest.getShippingStateId()));
		} else {
			quote.setShippingState(null);
		}
		quote.setShippingPostalCode(quoteRequest.getShippingPostalCode());

		quote.setOtherTermsAndConditions(quoteRequest.getOtherTermsAndConditions());
		quote.setToEmailIds(quoteRequest.getToEmailIds());
		quote.setCcEmailIds(quoteRequest.getCcEmailIds());

		quote.setModifiedBy(new EmployeeDetails(user.getUsername()));
		quote.setModifiedDate(new Date());
			return quote;
	}
	
	
	
	
	
	

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, RuntimeException.class, Exception.class,
			SQLException.class })
	@Override
	public List<QuoteLineItemRequest> addLineItem(List<QuoteLineItemRequest> lineItemList, long priceBookId,
			long quoteId, int businessDomainId, LoginUser user) throws Exception {
		Quote quoteExist = null;
		try {
			Query selectQuoteQuery = entityManager.createQuery(
					"select qt from Quote qt left join qt.quoteStatus qts left join qt.oppertunity opr left join qt.shippingState st where qt.id=?1",
					Quote.class);

			selectQuoteQuery.setParameter(1, quoteId);
			quoteExist = (Quote) selectQuoteQuery.getSingleResult();
		} catch (NoResultException ex) {

		}

		if (quoteExist == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Quote does not exist");
		}

		List<Integer> quoteStatusList = new ArrayList<Integer>();
		quoteStatusList.add(2); // Work Order Received
		quoteStatusList.add(8); // Invoiced

		if (quoteExist != null && quoteExist.getQuoteStatus() != null) {
			if (quoteStatusList.contains(quoteExist.getQuoteStatus().getId())) {
				throw new com.pratikshat.exicom.exception.BadRequestException(
						"You can't able to edit this record");
			}
		}
		
		
		
		if (quoteExist.getOwner().getEmailId().equalsIgnoreCase(user.getUsername())) {
			int statusId = quoteExist.getQuoteStatus().getId();
			List<Integer> list = new ArrayList<>();
			list.add(1);	// Draft 
			list.add(3);	// Rejected
			list.add(11);	// Rejected by store 
			
			if(!list.contains(statusId)) {
				throw new BadRequestException("You can't able to edit this record");
			}
			//throw new UnauthoriseException("You are not authorised to edit this record");
		}else {
			
			ToBeApproved _toBeApproved=null;
			try {
				_toBeApproved=entityManager.createQuery("select apr from ToBeApproved apr join apr.assignedTo asg where apr.ObjectName=?1 and apr.objectId=?2 and apr.status=?3",ToBeApproved.class)
						.setParameter(1,"Quote")
						.setParameter(2,quoteExist.getId())
						.setParameter(3,false)
						.getSingleResult();
			}catch(javax.persistence.NoResultException ex) {
				throw new BadRequestException("You are not authorised");
			}catch(javax.persistence.NonUniqueResultException ex) {
				throw new BadRequestException("You are not authorised");
			}
			if(!_toBeApproved.getAssignedTo().getEmailId().equalsIgnoreCase(user.getUsername())){
				if(!dbProfile.samroleUsers(user.getUsername(), _toBeApproved.getAssignedTo().getEmailId())) {
					Map<String,String> rol=dbProfile.getAccountAndRoleGroup(user);
					if(!rol.get("roleGroup").equalsIgnoreCase("Commercial Executive"))
						throw new UnauthoriseException("You are not authorised");
				}
				//throw new BadRequestException("You are not authorised");
			}else {
				
			}
		}
		
		Oppertunity oppertunity = null;
		try {
			Query selectOppertunityQuery = entityManager
					.createQuery("select opr from Oppertunity opr where opr.oppertunityId=?1", Oppertunity.class);
			selectOppertunityQuery.setParameter(1, quoteExist.getOppertunity().getOppertunityId());
			oppertunity = (Oppertunity) selectOppertunityQuery.getSingleResult();
		} catch (NoResultException ex) {

		}
		if (oppertunity == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Opportunity does not exist");
		}

		PriceBook priceBook = null;
		try {
			Query selectPriceBookQuery = entityManager.createQuery("select pb from PriceBook pb where pb.id=?1",
					PriceBook.class);
			selectPriceBookQuery.setParameter(1, priceBookId);
			priceBook = (PriceBook) selectPriceBookQuery.getSingleResult();
		} catch (NoResultException ex) {

		}
		if (priceBook == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Price Book does not exist");
		}

		if (quoteExist.isSyncing()) {
			TaxAndVisitCharges taxAndVisitCharges = null;
			try {
				Query selectTaxAndVisitChargesQuery = entityManager.createQuery(
						"select ts from TaxAndVisitCharges ts left join ts.businessDomain bd where bd.businessDomainId=?1",
						TaxAndVisitCharges.class);
				selectTaxAndVisitChargesQuery.setParameter(1, businessDomainId);
				taxAndVisitCharges = (TaxAndVisitCharges) selectTaxAndVisitChargesQuery.getSingleResult();
			} catch (NoResultException ex) {

			}
			if (taxAndVisitCharges == null) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Taxes for this business does not exist");
			}

			IGSTBillingState igstBillingState = null;
			try {

				Query selectTaxAndVisitChargesQuery = entityManager.createQuery(
						"select bs from IGSTBillingState bs where bs.stateName=?1", IGSTBillingState.class);
				selectTaxAndVisitChargesQuery.setParameter(1, quoteExist.getBillingState().getStateName());
				igstBillingState = (IGSTBillingState) selectTaxAndVisitChargesQuery.getSingleResult();

			} catch (NoResultException ex) {

			}

			for (int i = 0; i < lineItemList.size(); i++) {
				QuoteLineItemRequest request = lineItemList.get(i);

				PriceBookEntries priceBookEntries = null;
				try {
					Query selectPriceBookEntriesQuery = entityManager.createQuery(
							"select pbe from PriceBookEntries pbe where pbe.id=?1", PriceBookEntries.class);
					selectPriceBookEntriesQuery.setParameter(1, request.getPriceBookEntryId());
					priceBookEntries = (PriceBookEntries) selectPriceBookEntriesQuery.getSingleResult();
				} catch (NoResultException ex) {

				}
				if (priceBookEntries == null) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Price Book Product does not exist");
				}

				QuoteLineItem quoteLineItem = null;
				try {
					Query selectQuoteLineItemQuery = entityManager.createQuery(
							"select qli from QuoteLineItem qli left join qli.quote qt left join qli.priceBookEntries pbe left join pbe.product prd where qt.id=?1 and prd.productPartCode=?2 and qli.isDeleted=?3",
							QuoteLineItem.class);
					selectQuoteLineItemQuery.setParameter(1, quoteExist.getId());
					selectQuoteLineItemQuery.setParameter(2, request.getPartCode());
					selectQuoteLineItemQuery.setParameter(3,false);
					quoteLineItem = (QuoteLineItem) selectQuoteLineItemQuery.getSingleResult();
				} catch (NoResultException ex) {

				}
				if (quoteLineItem != null) {
					throw new com.pratikshat.exicom.exception.BadRequestException(
							"Line Item " + request.getPartCode() + " already exist in this Quote");
				}

				double price = request.getPrice();
				double quantity = request.getQuantity();
				double totalPrice = price * quantity;

				OppertunityLineItem item = createOppertunityLineItem(oppertunity, priceBookEntries, price, quantity,
						totalPrice, user);
				// totalAmount = totalAmount+totalPrice;

				entityManager.persist(item);

				QuoteLineItem quoteItem = createQuoteLineItem(quoteExist, priceBookEntries, price, quantity, totalPrice,
						taxAndVisitCharges, igstBillingState, user);

				entityManager.persist(quoteItem);
			}

			Object[] amount = null;
			try {
				Query sumOppertunityQuery = entityManager.createQuery(
						"select coalesce(sum(qli.totalPrice),0) as totalPrice, coalesce(sum(qli.cgstAmount),0) as cgstAmount, coalesce(sum(qli.sgstAmount),0) as sgstAmount, coalesce(sum(qli.igstAmount),0) as igstAmount, coalesce(sum(qli.totalPriceWithTax),0) as totalPriceWithTax from QuoteLineItem qli left join qli.quote qt where qli.isDeleted=false and qt.id=?1");
				sumOppertunityQuery.setParameter(1, quoteExist.getId());
				amount = (Object[]) sumOppertunityQuery.getSingleResult();
			} catch (NoResultException ex) {

			}

			if (amount != null) {
				quoteExist.setSubTotal((double) amount[0]);
				quoteExist.setCgstAmount((double) amount[1]);
				quoteExist.setSgstAmount((double) amount[2]);
				quoteExist.setIgstAmount((double) amount[3]);
				quoteExist.setDiscount(0);
				quoteExist.setTotalAmountWithTax((double) amount[4]);
				quoteExist.setTotalAmountAfterDiscount((double) amount[4]);

				quoteExist.setModifiedBy(new EmployeeDetails(user.getUsername()));
				quoteExist.setModifiedDate(new Date());

				entityManager.persist(quoteExist);
			} else {

			}

			try {
				Query sumOppertunityQuery = entityManager.createQuery(
						"select coalesce(sum(oli.totalPrice),0) from OppertunityLineItem oli left join oli.oppertunity opr where opr.oppertunityId=?1 and oli.isDeleted=?2",
						Double.class);
				sumOppertunityQuery.setParameter(1, quoteExist.getOppertunity().getOppertunityId());
				sumOppertunityQuery.setParameter(2,false);
				Double totalAmount = (Double) sumOppertunityQuery.getSingleResult();

				oppertunity.setAmmount(totalAmount.floatValue());
				oppertunity.setExpectedRevenue(totalAmount.floatValue());
				oppertunity.setModifiedBy(new EmployeeDetails(user.getUsername()));
				oppertunity.setModifiedDate(new Date());

				entityManager.persist(oppertunity);
			} catch (NoResultException ex) {

			}
		} else {

			for (int i = 0; i < lineItemList.size(); i++) {
				QuoteLineItemRequest request = lineItemList.get(i);

				QuoteLineItem quoteLineItem = null;
				try {
					Query selectQuoteLineItemQuery = entityManager.createQuery(
							"select qli from QuoteLineItem qli left join qli.quote qt left join qli.priceBookEntries pbe left join pbe.product prd where qt.id=?1 and prd.productPartCode=?2 and qli.isDeleted=?3",
							QuoteLineItem.class);
					selectQuoteLineItemQuery.setParameter(1, quoteExist.getId());
					selectQuoteLineItemQuery.setParameter(2, request.getPartCode());
					selectQuoteLineItemQuery.setParameter(3,false);
					quoteLineItem = (QuoteLineItem) selectQuoteLineItemQuery.getSingleResult();
				} catch (NoResultException ex) {

				}
				if (quoteLineItem != null) {
					throw new com.pratikshat.exicom.exception.BadRequestException(
							"Line Item " + request.getPartCode() + " already exist in this Quote");
				}

				PriceBookEntries priceBookEntries = null;
				try {
					Query selectPriceBookEntriesQuery = entityManager.createQuery(
							"select pbe from PriceBookEntries pbe where pbe.id=?1", PriceBookEntries.class);
					selectPriceBookEntriesQuery.setParameter(1, request.getPriceBookEntryId());
					priceBookEntries = (PriceBookEntries) selectPriceBookEntriesQuery.getSingleResult();
				} catch (NoResultException ex) {

				}
				if (priceBookEntries == null) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Price Book Product does not exist");
				}

				TaxAndVisitCharges taxAndVisitCharges = null;
				try {
					Query selectTaxAndVisitChargesQuery = entityManager.createQuery(
							"select ts from TaxAndVisitCharges ts left join ts.businessDomain bd where bd.businessDomainId=?1",
							TaxAndVisitCharges.class);
					selectTaxAndVisitChargesQuery.setParameter(1, businessDomainId);
					taxAndVisitCharges = (TaxAndVisitCharges) selectTaxAndVisitChargesQuery.getSingleResult();
				} catch (NoResultException ex) {

				}
				if (taxAndVisitCharges == null) {
					throw new com.pratikshat.exicom.exception.BadRequestException(
							"Taxes for this business does not exist");
				}

				IGSTBillingState igstBillingState = null;
				try {
					Query selectTaxAndVisitChargesQuery = entityManager.createQuery(
							"select bs from IGSTBillingState bs where bs.stateName=?1", IGSTBillingState.class);
					selectTaxAndVisitChargesQuery.setParameter(1, quoteExist.getBillingState().getStateName());
					igstBillingState = (IGSTBillingState) selectTaxAndVisitChargesQuery.getSingleResult();
				} catch (NoResultException ex) {

				}

				double price = request.getPrice();
				double quantity = request.getQuantity();

				double totalPrice = price * quantity;

				QuoteLineItem quoteItem = createQuoteLineItem(quoteExist, priceBookEntries, price, quantity, totalPrice,
						taxAndVisitCharges, igstBillingState, user);

				entityManager.persist(quoteItem);

				Object[] amount = null;
				try {
					Query sumOppertunityQuery = entityManager.createQuery(
							"select coalesce(sum(qli.totalPrice),0) as totalPrice, coalesce(sum(qli.cgstAmount),0) as cgstAmount, coalesce(sum(qli.sgstAmount),0) as sgstAmount, coalesce(sum(qli.igstAmount),0) as igstAmount, coalesce(sum(qli.totalPriceWithTax),0) as totalPriceWithTax from QuoteLineItem qli left join qli.quote qt where qli.isDeleted=false and qt.id=?1");
					sumOppertunityQuery.setParameter(1, quoteExist.getId());
					amount = (Object[]) sumOppertunityQuery.getSingleResult();
				} catch (NoResultException ex) {

				}

				if (amount != null) {
					quoteExist.setSubTotal((double) amount[0]);
					quoteExist.setCgstAmount((double) amount[1]);
					quoteExist.setSgstAmount((double) amount[2]);
					quoteExist.setIgstAmount((double) amount[3]);
					quoteExist.setDiscount(0);
					quoteExist.setTotalAmountWithTax((double) amount[4]);
					quoteExist.setTotalAmountAfterDiscount((double) amount[4]);

					quoteExist.setModifiedBy(new EmployeeDetails(user.getUsername()));
					quoteExist.setModifiedDate(new Date());

					entityManager.persist(quoteExist);
				} else {

				}
			}
		}

		entityManager.flush();
		entityManager.clear();

		return lineItemList;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, RuntimeException.class, Exception.class,
			SQLException.class })
	@Override
	public QuoteLineItemRequest updateLineItem(QuoteLineItemRequest quoteLineItem, long priceBookId, long quoteId,
			int businessDomainId, LoginUser user) throws Exception {

		Quote quoteExist = null;
		try {
			Query selectQuoteQuery = entityManager.createQuery(
					"select qt from Quote qt left join qt.quoteStatus qts left join qt.oppertunity opr left join qt.shippingState st where qt.id=?1",
					Quote.class);
			selectQuoteQuery.setParameter(1, quoteId);
			quoteExist = (Quote) selectQuoteQuery.getSingleResult();
		} catch (NoResultException ex) {

		}

		if (quoteExist == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Quote does not exist");
		}

		List<Integer> quoteStatusList = new ArrayList<Integer>();
		quoteStatusList.add(2); // Work Order Received
		quoteStatusList.add(8); // Invoiced

		if (quoteExist != null && quoteExist.getQuoteStatus() != null) {
			if (quoteStatusList.contains(quoteExist.getQuoteStatus().getId())) {
				throw new com.pratikshat.exicom.exception.BadRequestException(
						"You can't able to edit this record");
			}
		}
		
		
		
		if (quoteExist.getOwner().getEmailId().equalsIgnoreCase(user.getUsername())) {
			int statusId = quoteExist.getQuoteStatus().getId();
			List<Integer> list = new ArrayList<>();
			list.add(1);	// Draft 
			list.add(3);	// Rejected
			list.add(11);	// Rejected by store 
			
			if(!list.contains(statusId)) {
				throw new BadRequestException("You can't able to edit this record");
			}
			//throw new UnauthoriseException("You are not authorised to edit this record");
		}else {
			
			ToBeApproved _toBeApproved=null;
			try {
				_toBeApproved=entityManager.createQuery("select apr from ToBeApproved apr join apr.assignedTo asg where apr.ObjectName=?1 and apr.objectId=?2 and apr.status=?3",ToBeApproved.class)
						.setParameter(1,"Quote")
						.setParameter(2,quoteExist.getId())
						.setParameter(3,false)
						.getSingleResult();
			}catch(javax.persistence.NoResultException ex) {
				throw new BadRequestException("You are not authorised");
			}catch(javax.persistence.NonUniqueResultException ex) {
				throw new BadRequestException("You are not authorised");
			}
			if(!_toBeApproved.getAssignedTo().getEmailId().equalsIgnoreCase(user.getUsername())){
				if(!dbProfile.samroleUsers(user.getUsername(), _toBeApproved.getAssignedTo().getEmailId()))
				{
					Map<String,String> rol=dbProfile.getAccountAndRoleGroup(user);
					if(!rol.get("roleGroup").equalsIgnoreCase("Commercial Executive"))
						throw new UnauthoriseException("You are not authorised");
				}
				//throw new BadRequestException("You are not authorised");
			}else {
				
			}
		}

		PriceBook priceBook = null;
		try {
			Query selectPriceBookQuery = entityManager.createQuery("select pb from PriceBook pb where pb.id=?1",
					PriceBook.class);
			selectPriceBookQuery.setParameter(1, priceBookId);
			priceBook = (PriceBook) selectPriceBookQuery.getSingleResult();
		} catch (NoResultException ex) {

		}
		if (priceBook == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Price Book does not exist");
		}

		Oppertunity oppertunity = null;
		try {
			Query selectOppertunityQuery = entityManager.createQuery(
					"select opr from Oppertunity opr where opr.isDeleted=false and opr.oppertunityId=?1",
					Oppertunity.class);
			selectOppertunityQuery.setParameter(1, quoteExist.getOppertunity().getOppertunityId());
			oppertunity = (Oppertunity) selectOppertunityQuery.getSingleResult();
		} catch (NoResultException ex) {

		}

		if (oppertunity == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Opportunity does not exist");
		}

		TaxAndVisitCharges taxAndVisitCharges = null;
		try {
			Query selectTaxAndVisitChargesQuery = entityManager.createQuery(
					"select ts from TaxAndVisitCharges ts left join ts.businessDomain bd where bd.businessDomainId=?1",
					TaxAndVisitCharges.class);
			selectTaxAndVisitChargesQuery.setParameter(1, businessDomainId);
			taxAndVisitCharges = (TaxAndVisitCharges) selectTaxAndVisitChargesQuery.getSingleResult();
		} catch (NoResultException ex) {

		}

		if (taxAndVisitCharges == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Taxes for this business does not exist");
		}

		IGSTBillingState igstBillingState = null;
		try {
			Query selectTaxAndVisitChargesQuery = entityManager
					.createQuery("select bs from IGSTBillingState bs where bs.stateName=?1", IGSTBillingState.class);
			selectTaxAndVisitChargesQuery.setParameter(1, quoteExist.getBillingState().getStateName());
			igstBillingState = (IGSTBillingState) selectTaxAndVisitChargesQuery.getSingleResult();
		} catch (NoResultException ex) {

		}

		if (quoteExist.isSyncing()) {

			OppertunityLineItem oppertunityLineItem = null;
			try {
				Query selectOppertunityLineItemQuery = entityManager.createQuery(
						"select oli from OppertunityLineItem oli left join oli.oppertunity opr left join oli.priceBookEntries pb left join pb.product prd where opr.oppertunityId=?1 and prd.productPartCode=?2 and oli.isDeleted=?3",
						OppertunityLineItem.class);
				selectOppertunityLineItemQuery.setParameter(1, oppertunity.getOppertunityId());
				selectOppertunityLineItemQuery.setParameter(2, quoteLineItem.getPartCode());
				selectOppertunityLineItemQuery.setParameter(3,false);
				oppertunityLineItem = (OppertunityLineItem) selectOppertunityLineItemQuery.getSingleResult();
			} catch (NoResultException ex) {

			}
			if (oppertunityLineItem == null) {
				throw new com.pratikshat.exicom.exception.BadRequestException(
						"Line Item does not exist in oppertunity");
			}

			float price = quoteLineItem.getPrice();
			float quantity = quoteLineItem.getQuantity();

			float totalPrice = price * quantity;

			OppertunityLineItem item = updateOppertunityLineItem(oppertunityLineItem, price, quantity, totalPrice,
					user);

			entityManager.persist(item);

			try {
				Query sumOppertunityQuery = entityManager.createQuery(
						"select sum(oli.totalPrice) from OppertunityLineItem oli left join oli.oppertunity opr where oli.isDeleted=false and opr.oppertunityId=?1 and oli.isDeleted=?2",
						Double.class);
				sumOppertunityQuery.setParameter(1, oppertunity.getOppertunityId());
				sumOppertunityQuery.setParameter(2,false);
				Double totalAmount = (Double) sumOppertunityQuery.getSingleResult();

				oppertunity.setAmmount(totalAmount.floatValue());
				oppertunity.setExpectedRevenue(totalAmount.floatValue());

				oppertunity.setModifiedBy(new EmployeeDetails(user.getUsername()));
				oppertunity.setModifiedDate(new Date());

				entityManager.persist(oppertunity);
			} catch (NoResultException ex) {

			}
		}

		QuoteLineItem lineItem = null;
		try {
			Query selectQuoteLineItemQuery = entityManager.createQuery(
					"select qli from QuoteLineItem qli left join qli.quote qt left join qli.priceBookEntries pb left join pb.product prd where qli.id=?1 and qli.isDeleted=?2",
					QuoteLineItem.class);
			selectQuoteLineItemQuery.setParameter(1, quoteLineItem.getId());
			selectQuoteLineItemQuery.setParameter(2, false);
			lineItem = (QuoteLineItem) selectQuoteLineItemQuery.getSingleResult();
		} catch (NoResultException ex) {

		}
		if (lineItem == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Line Item does not exist in Quote");
		}

		float price = quoteLineItem.getPrice();
		float quantity = quoteLineItem.getQuantity();

		float totalPrice = price * quantity;

		QuoteLineItem Item = updateQuoteLineItem(lineItem, price, quantity, totalPrice, taxAndVisitCharges,
				igstBillingState, user);

		entityManager.persist(Item);

		Object[] amount = null;
		try {
			Query sumOppertunityQuery = entityManager.createQuery(
					"select coalesce(sum(qli.totalPrice),0) as totalPrice, coalesce(sum(qli.cgstAmount),0) as cgstAmount, coalesce(sum(qli.sgstAmount),0) as sgstAmount, coalesce(sum(qli.igstAmount),0) as igstAmount, coalesce(sum(qli.totalPriceWithTax),0) as totalPriceWithTax from QuoteLineItem qli left join qli.quote qt where qt.id=?1");
			sumOppertunityQuery.setParameter(1, quoteExist.getId());
			amount = (Object[]) sumOppertunityQuery.getSingleResult();
		} catch (NoResultException ex) {

		}

		if (amount != null) {
			quoteExist.setSubTotal((double) amount[0]);
			quoteExist.setCgstAmount((double) amount[1]);
			quoteExist.setIgstAmount((double) amount[3]);
			quoteExist.setSgstAmount((double) amount[2]);

			quoteExist.setDiscount(0);
			quoteExist.setTotalAmountWithTax((double) amount[4]);
			quoteExist.setTotalAmountAfterDiscount((double) amount[4]);

			quoteExist.setModifiedBy(new EmployeeDetails(user.getUsername()));
			quoteExist.setModifiedDate(new Date());
			entityManager.persist(quoteExist);
		}

		entityManager.flush();
		entityManager.clear();

		return quoteLineItem;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, RuntimeException.class, Exception.class,
			SQLException.class })
	@Override
	public List<QuoteLineItemRequest> updateLineItemList(List<QuoteLineItemRequest> lineItemList, long priceBookId,
			long quoteId, int businessDomainId, LoginUser user) throws Exception {

		Quote quoteExist = null;
		try {
			Query selectQuoteQuery = entityManager.createQuery(
					"select qt from Quote qt left join qt.quoteStatus qts left join qt.oppertunity opr left join qt.shippingState st where qt.id=?1",
					Quote.class);
			selectQuoteQuery.setParameter(1, quoteId);
			quoteExist = (Quote) selectQuoteQuery.getSingleResult();
		} catch (NoResultException ex) {

		}

		if (quoteExist == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Quote does not exist");
		}

		List<Integer> quoteStatusList = new ArrayList<Integer>();
		quoteStatusList.add(2); // Work Order Received
		quoteStatusList.add(8); // Invoiced

		if (quoteExist != null && quoteExist.getQuoteStatus() != null) {
			if (quoteStatusList.contains(quoteExist.getQuoteStatus().getId())) {
				throw new com.pratikshat.exicom.exception.BadRequestException(
						"You can't able to edit this record");
			}
		}
		
		
		
		if (quoteExist.getOwner().getEmailId().equalsIgnoreCase(user.getUsername())) {
			int statusId = quoteExist.getQuoteStatus().getId();
			List<Integer> list = new ArrayList<>();
			list.add(1);	// Draft 
			list.add(3);	// Rejected
			list.add(11);	// Rejected by store 
			
			if(!list.contains(statusId)) {
				throw new BadRequestException("You can't able to edit this record");
			}
			//throw new UnauthoriseException("You are not authorised to edit this record");
		}else {
			
			ToBeApproved _toBeApproved=null;
			try {
				_toBeApproved=entityManager.createQuery("select apr from ToBeApproved apr join apr.assignedTo asg where apr.ObjectName=?1 and apr.objectId=?2 and apr.status=?3",ToBeApproved.class)
						.setParameter(1,"Quote")
						.setParameter(2,quoteExist.getId())
						.setParameter(3,false)
						.getSingleResult();
			}catch(javax.persistence.NoResultException ex) {
				throw new BadRequestException("You are not authorised");
			}catch(javax.persistence.NonUniqueResultException ex) {
				throw new BadRequestException("You are not authorised");
			}
			if(!_toBeApproved.getAssignedTo().getEmailId().equalsIgnoreCase(user.getUsername())){
				if(!dbProfile.samroleUsers(user.getUsername(), _toBeApproved.getAssignedTo().getEmailId())) {
					Map<String,String> rol=dbProfile.getAccountAndRoleGroup(user);
					if(!rol.get("roleGroup").equalsIgnoreCase("Commercial Executive"))
						throw new UnauthoriseException("You are not authorised");
				}
				//throw new BadRequestException("You are not authorised");
			}else {
				
			}
		}

		PriceBook priceBook = null;
		try {
			Query selectPriceBookQuery = entityManager.createQuery("select pb from PriceBook pb where pb.id=?1",
					PriceBook.class);
			selectPriceBookQuery.setParameter(1, priceBookId);
			priceBook = (PriceBook) selectPriceBookQuery.getSingleResult();
		} catch (NoResultException ex) {

		}
		if (priceBook == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Price Book does not exist");
		}

		Oppertunity oppertunity = null;
		try {
			Query selectOppertunityQuery = entityManager.createQuery(
					"select opr from Oppertunity opr where opr.isDeleted=false and opr.oppertunityId=?1",
					Oppertunity.class);
			selectOppertunityQuery.setParameter(1, quoteExist.getOppertunity().getOppertunityId());
			oppertunity = (Oppertunity) selectOppertunityQuery.getSingleResult();
		} catch (NoResultException ex) {

		}

		if (oppertunity == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Opportunity does not exist");
		}

		TaxAndVisitCharges taxAndVisitCharges = null;
		try {
			Query selectTaxAndVisitChargesQuery = entityManager.createQuery(
					"select ts from TaxAndVisitCharges ts left join ts.businessDomain bd where bd.businessDomainId=?1",
					TaxAndVisitCharges.class);
			selectTaxAndVisitChargesQuery.setParameter(1, businessDomainId);
			taxAndVisitCharges = (TaxAndVisitCharges) selectTaxAndVisitChargesQuery.getSingleResult();
		} catch (NoResultException ex) {

		}

		if (taxAndVisitCharges == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Taxes for this business does not exist");
		}

		IGSTBillingState igstBillingState = null;
		try {
			Query selectTaxAndVisitChargesQuery = entityManager
					.createQuery("select bs from IGSTBillingState bs where bs.stateName=?1", IGSTBillingState.class);
			selectTaxAndVisitChargesQuery.setParameter(1, quoteExist.getBillingState().getStateName());
			igstBillingState = (IGSTBillingState) selectTaxAndVisitChargesQuery.getSingleResult();
		} catch (NoResultException ex) {

		}

		if (quoteExist.isSyncing()) {

			for (int i = 0; i < lineItemList.size(); i++) {
				QuoteLineItemRequest lineItemRequest = lineItemList.get(i);

				OppertunityLineItem oppertunityLineItem = null;
				try {
					Query selectOppertunityLineItemQuery = entityManager.createQuery(
							"select oli from OppertunityLineItem oli left join oli.oppertunity opr left join oli.priceBookEntries pb left join pb.product prd where opr.oppertunityId=?1 and prd.productPartCode=?2 and oli.isDeleted=?3",
							OppertunityLineItem.class);
					selectOppertunityLineItemQuery.setParameter(1, oppertunity.getOppertunityId());
					selectOppertunityLineItemQuery.setParameter(2, lineItemRequest.getPartCode());
					selectOppertunityLineItemQuery.setParameter(3,false);
					oppertunityLineItem = (OppertunityLineItem) selectOppertunityLineItemQuery.getSingleResult();
				} catch (NoResultException ex) {

				}
				if (oppertunityLineItem == null) {
					throw new com.pratikshat.exicom.exception.BadRequestException(
							"Line Item does not exist in oppertunity");
				}

				float price = lineItemRequest.getPrice();
				float quantity = lineItemRequest.getQuantity();

				float totalPrice = price * quantity;

				OppertunityLineItem item = updateOppertunityLineItem(oppertunityLineItem, price, quantity, totalPrice,
						user);

				entityManager.persist(item);
			}

			try {
				Query sumOppertunityQuery = entityManager.createQuery(
						"select coalesce(sum(oli.totalPrice),0) from OppertunityLineItem oli left join oli.oppertunity opr where oli.isDeleted=false and opr.oppertunityId=?1 and oli.isDeleted=?2",
						Double.class);
				sumOppertunityQuery.setParameter(1, oppertunity.getOppertunityId());
				sumOppertunityQuery.setParameter(3,false);
				Double totalAmount = (Double) sumOppertunityQuery.getSingleResult();

				oppertunity.setAmmount(totalAmount.floatValue());
				oppertunity.setExpectedRevenue(totalAmount.floatValue());

				oppertunity.setModifiedBy(new EmployeeDetails(user.getUsername()));
				oppertunity.setModifiedDate(new Date());

				entityManager.persist(oppertunity);
			} catch (NoResultException ex) {

			}

		}

		for (int i = 0; i < lineItemList.size(); i++) {
			QuoteLineItemRequest lineItemRequest = lineItemList.get(i);

			QuoteLineItem lineItem = null;
			try {
				Query selectQuoteLineItemQuery = entityManager.createQuery(
						"select qli from QuoteLineItem qli left join qli.quote qt left join qli.priceBookEntries pb left join pb.product prd where qt.id=?1 and prd.productPartCode=?2 and qli.isDeleted=?3",
						QuoteLineItem.class);
				selectQuoteLineItemQuery.setParameter(1, quoteExist.getId());
				selectQuoteLineItemQuery.setParameter(2, lineItemRequest.getPartCode());
				selectQuoteLineItemQuery.setParameter(3,false);
				lineItem = (QuoteLineItem) selectQuoteLineItemQuery.getSingleResult();
			} catch (NoResultException ex) {

			}
			if (lineItem == null) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Line Item does not exist in Quote");
			}

			float price = lineItemRequest.getPrice();
			float quantity = lineItemRequest.getQuantity();

			float totalPrice = price * quantity;

			QuoteLineItem item = updateQuoteLineItem(lineItem, price, quantity, totalPrice, taxAndVisitCharges,
					igstBillingState, user);

			entityManager.persist(item);
		}

		Object[] amount = null;
		try {
			Query sumOppertunityQuery = entityManager.createQuery(
					"select coalesce(sum(qli.totalPrice),0) as totalPrice, coalesce(sum(qli.cgstAmount),0) as cgstAmount, coalesce(sum(qli.sgstAmount),0) as sgstAmount, coalesce(sum(qli.igstAmount),0) as igstAmount, coalesce(sum(qli.totalPriceWithTax),0) as totalPriceWithTax from QuoteLineItem qli left join qli.quote qt where qli.isDeleted=false and qt.id=?1");
			sumOppertunityQuery.setParameter(1, quoteExist.getId());
			amount = (Object[]) sumOppertunityQuery.getSingleResult();
		} catch (NoResultException ex) {

		}

		if (amount != null) {
			quoteExist.setSubTotal((double) amount[0]);
			quoteExist.setCgstAmount((double) amount[1]);
			quoteExist.setSgstAmount((double) amount[2]);
			quoteExist.setIgstAmount((double) amount[3]);
			quoteExist.setDiscount(0);
			quoteExist.setTotalAmountWithTax((double) amount[4]);
			quoteExist.setTotalAmountAfterDiscount((double) amount[4]);

			quoteExist.setModifiedBy(new EmployeeDetails(user.getUsername()));
			quoteExist.setModifiedDate(new Date());

			entityManager.persist(quoteExist);
		} else {

		}

		entityManager.flush();
		entityManager.clear();

		return lineItemList;

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, RuntimeException.class, Exception.class,
			SQLException.class })
	@Override
	public void deleteQuote(long quoteId, LoginUser user) throws Exception {

		Quote quoteExist = null;
		try {
			Query selectQuoteQuery = entityManager.createQuery(
					"select qt from Quote qt left join qt.quoteStatus qts left join qt.oppertunity opr left join qt.shippingState st where qt.id=?1",
					Quote.class);
			selectQuoteQuery.setParameter(1, quoteId);
			quoteExist = (Quote) selectQuoteQuery.getSingleResult();
		} catch (NoResultException ex) {

		}

		if (quoteExist == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Quote does not exist");
		}
		
		List<Integer> quoteStatusList = new ArrayList<Integer>();
		quoteStatusList.add(2); // Work Order Received
		quoteStatusList.add(8); // Invoiced

		if (quoteExist != null && quoteExist.getQuoteStatus() != null) {
			if (quoteStatusList.contains(quoteExist.getQuoteStatus().getId())) {
				throw new com.pratikshat.exicom.exception.BadRequestException(
						"You can't able to edit this record");
			}
		}
		
		
		
		if (quoteExist.getOwner().getEmailId().equalsIgnoreCase(user.getUsername())) {
			int statusId = quoteExist.getQuoteStatus().getId();
			List<Integer> list = new ArrayList<>();
			list.add(1);	// Draft 
			list.add(3);	// Rejected
			list.add(11);	// Rejected by store 
			
			if(!list.contains(statusId)) {
				throw new BadRequestException("You can't able to edit this record");
			}
			//throw new UnauthoriseException("You are not authorised to edit this record");
		}else {
			
			ToBeApproved _toBeApproved=null;
			try {
				_toBeApproved=entityManager.createQuery("select apr from ToBeApproved apr join apr.assignedTo asg where apr.ObjectName=?1 and apr.objectId=?2 and apr.status=?3",ToBeApproved.class)
						.setParameter(1,"Quote")
						.setParameter(2,quoteExist.getId())
						.setParameter(3,false)
						.getSingleResult();
			}catch(javax.persistence.NoResultException ex) {
				throw new BadRequestException("You are not authorised");
			}catch(javax.persistence.NonUniqueResultException ex) {
				throw new BadRequestException("You are not authorised");
			}
			if(!_toBeApproved.getAssignedTo().getEmailId().equalsIgnoreCase(user.getUsername())){
				if(!dbProfile.samroleUsers(user.getUsername(), _toBeApproved.getAssignedTo().getEmailId())) {
					Map<String,String> rol=dbProfile.getAccountAndRoleGroup(user);
					if(!rol.get("roleGroup").equalsIgnoreCase("COMMERCIAL"))
						throw new UnauthoriseException("You are not authorised");
				}
				//throw new BadRequestException("You are not authorised");
			}else {
				
			}
		}

		quoteExist.setDeleted(true);
		quoteExist.setModifiedBy(new EmployeeDetails(user.getUsername()));
		quoteExist.setModifiedDate(new Date());
		entityManager.persist(quoteExist);

		if (quoteExist.isSyncing()) {

			Oppertunity oppertunity = null;
			try {
				Query selectOppertunityQuery = entityManager.createQuery(
						"select opr from Oppertunity opr where opr.isDeleted=false and opr.oppertunityId=?1",
						Oppertunity.class);
				selectOppertunityQuery.setParameter(1, quoteExist.getOppertunity().getOppertunityId());
				oppertunity = (Oppertunity) selectOppertunityQuery.getSingleResult();
			} catch (NoResultException ex) {

			}

			if (oppertunity != null) {
				oppertunity.setDeleted(true);
				oppertunity.setModifiedBy(new EmployeeDetails(user.getUsername()));
				oppertunity.setModifiedDate(new Date());
				entityManager.persist(oppertunity);
			}

			List<OppertunityLineItem> oppertunityLineItemList = null;
			try {
				Query selectLineItemList = entityManager.createQuery(
						"select oli from OppertunityLineItem oli where oli.oppertunity.oppertunityId=?1",
						OppertunityLineItem.class);
				selectLineItemList.setParameter(1, oppertunity.getOppertunityId());
				oppertunityLineItemList = selectLineItemList.getResultList();
			} catch (NoResultException e) {

			}

			if (oppertunityLineItemList != null && oppertunityLineItemList.size() > 0) {
				for (int i = 0; i < oppertunityLineItemList.size(); i++) {
					OppertunityLineItem oppertunityLineItem = oppertunityLineItemList.get(i);
					oppertunityLineItem.setDeleted(true);
					oppertunityLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
					oppertunityLineItem.setModifiedDate(new Date());
					entityManager.persist(oppertunityLineItem);
				}
			}
		}

		List<QuoteLineItem> quoteLineItemList = null;
		try {
			Query selectQuoteLineItemQuery = entityManager.createQuery(
					"select qli from QuoteLineItem qli left join qli.quote qt where qt.id=?1", QuoteLineItem.class);
			selectQuoteLineItemQuery.setParameter(1, quoteExist.getId());
			quoteLineItemList = (List<QuoteLineItem>) selectQuoteLineItemQuery.getResultList();
		} catch (NoResultException ex) {

		}

		if (quoteLineItemList != null && quoteLineItemList.size() > 0) {
			for (int i = 0; i < quoteLineItemList.size(); i++) {
				QuoteLineItem quoteLineItem = quoteLineItemList.get(i);
				quoteLineItem.setDeleted(true);
				quoteLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
				quoteLineItem.setModifiedDate(new Date());
				entityManager.persist(quoteLineItem);
			}
		}

		entityManager.flush();
		entityManager.clear();

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, RuntimeException.class, Exception.class,
			SQLException.class })
	@Override
	public void deleteQuoteLineItem(long quoteLineItemId, LoginUser user) throws Exception {

		QuoteLineItem quoteLineItem = null;
		try {
			Query selectQuoteLineItemQuery = entityManager.createQuery(
					"select qli from QuoteLineItem qli left join qli.quote qt left join qt.oppertunity opr left join qli.priceBookEntries pb left join pb.product prd where qli.id=?1",
					QuoteLineItem.class);
			selectQuoteLineItemQuery.setParameter(1, quoteLineItemId);
			quoteLineItem = (QuoteLineItem) selectQuoteLineItemQuery.getSingleResult();
		} catch (NoResultException ex) {

		}

		if (quoteLineItem == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Line Item does not exist");
		}

		Quote quote = null;
		try {
			Query selectQuoteLineItemQuery = entityManager.createQuery(
					"select qt from QuoteLineItem qli left join qli.quote qt left join qt.quoteStatus qts where qli.id=?1", Quote.class);
			selectQuoteLineItemQuery.setParameter(1, quoteLineItemId);
			quote = (Quote) selectQuoteLineItemQuery.getSingleResult();
		} catch (NoResultException ex) {

		}

		if (quote == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Quote does not exist");
		}
		
		List<Integer> quoteStatusList = new ArrayList<Integer>();
		quoteStatusList.add(2); // Work Order Received
		quoteStatusList.add(8); // Invoiced

		if (quote != null && quote.getQuoteStatus() != null) {
			if (quoteStatusList.contains(quote.getQuoteStatus().getId())) {
				throw new com.pratikshat.exicom.exception.BadRequestException(
						"You can't able to edit this record");
			}
		}
		
		
		
		if (quote.getOwner().getEmailId().equalsIgnoreCase(user.getUsername())) {
			int statusId = quote.getQuoteStatus().getId();
			List<Integer> list = new ArrayList<>();
			list.add(1);	// Draft 
			list.add(3);	// Rejected
			list.add(11);	// Rejected by store 
			
			if(!list.contains(statusId)) {
				throw new BadRequestException("You can't able to edit this record");
			}
			//throw new UnauthoriseException("You are not authorised to edit this record");
		}else {
			
			ToBeApproved _toBeApproved=null;
			try {
				_toBeApproved=entityManager.createQuery("select apr from ToBeApproved apr join apr.assignedTo asg where apr.ObjectName=?1 and apr.objectId=?2 and apr.status=?3",ToBeApproved.class)
						.setParameter(1,"Quote")
						.setParameter(2,quote.getId())
						.setParameter(3,false)
						.getSingleResult();
			}catch(javax.persistence.NoResultException ex) {
				throw new BadRequestException("You are not authorised");
			}catch(javax.persistence.NonUniqueResultException ex) {
				throw new BadRequestException("You are not authorised");
			}
			if(!_toBeApproved.getAssignedTo().getEmailId().equalsIgnoreCase(user.getUsername())){
				if(!dbProfile.samroleUsers(user.getUsername(), _toBeApproved.getAssignedTo().getEmailId())) {
					Map<String,String> rol=dbProfile.getAccountAndRoleGroup(user);
					if(!rol.get("roleGroup").equalsIgnoreCase("COMMERCIAL"))
						throw new UnauthoriseException("You are not authorised");
				}
				//throw new BadRequestException("You are not authorised");
			}else {
				
			}
		}

		Oppertunity oppertunity = null;
		try {
			Query selectOppertunityQuery = entityManager.createQuery(
					"select opr from Oppertunity opr where opr.isDeleted=false and opr.oppertunityId=?1",
					Oppertunity.class);
			selectOppertunityQuery.setParameter(1, quoteLineItem.getQuote().getOppertunity().getOppertunityId());
			oppertunity = (Oppertunity) selectOppertunityQuery.getSingleResult();
		} catch (NoResultException ex) {

		}
		if (oppertunity == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Opportunity does not exist");
		}

		if (quoteLineItem.getQuote().isSyncing()) {

			OppertunityLineItem oppertunityLineItem = null;
			try {
				Query selectOppertunityLineItemQuery = entityManager.createQuery(
						"select oli from OppertunityLineItem oli left join oli.oppertunity opr left join oli.priceBookEntries pbe left join pbe.product prd where oli.isDeleted=false and opr.oppertunityId=?1 and prd.productPartCode=?2",
						OppertunityLineItem.class);
				selectOppertunityLineItemQuery.setParameter(1, oppertunity.getOppertunityId());
				selectOppertunityLineItemQuery.setParameter(2,
						quoteLineItem.getPriceBookEntries().getProduct().getProductPartCode());
				oppertunityLineItem = (OppertunityLineItem) selectOppertunityLineItemQuery.getSingleResult();
			} catch (NoResultException ex) {

			}

			oppertunityLineItem.setDeleted(true);
			oppertunityLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
			oppertunityLineItem.setModifiedDate(new Date());
			entityManager.persist(oppertunityLineItem);

			try {
				Query sumOppertunityQuery = entityManager.createQuery(
						"select coalesce(sum(oli.totalPrice),0) from OppertunityLineItem oli left join oli.oppertunity opr where oli.isDeleted=false and opr.oppertunityId=?1",
						Double.class);
				sumOppertunityQuery.setParameter(1, oppertunityLineItem.getOppertunity().getOppertunityId());
				Double totalAmount = (Double) sumOppertunityQuery.getSingleResult();

				oppertunity.setAmmount(totalAmount.floatValue());
				oppertunity.setExpectedRevenue(totalAmount.floatValue());

				oppertunity.setModifiedBy(new EmployeeDetails(user.getUsername()));
				oppertunity.setModifiedDate(new Date());

				entityManager.persist(oppertunity);
			} catch (NoResultException ex) {

			}

		}

		quoteLineItem.setDeleted(true);
		quoteLineItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
		quoteLineItem.setModifiedDate(new Date());

		entityManager.persist(quoteLineItem);

		Object[] amount = null;
		try {
			Query sumOppertunityQuery = entityManager.createQuery(
					"select coalesce(sum(qli.totalPrice),0) as totalPrice, coalesce(sum(qli.cgstAmount),0) as cgstAmount, coalesce(sum(qli.sgstAmount),0) as sgstAmount, coalesce(sum(qli.igstAmount),0) as igstAmount, coalesce(sum(qli.totalPriceWithTax),0) as totalPriceWithTax from QuoteLineItem qli left join qli.quote qt where qli.isDeleted=false and qt.id=?1");
			sumOppertunityQuery.setParameter(1, quote.getId());
			amount = (Object[]) sumOppertunityQuery.getSingleResult();
		} catch (NoResultException ex) {

		}

		if (amount != null) {
			quote.setSubTotal((double) amount[0]);
			quote.setCgstAmount((double) amount[1]);
			quote.setSgstAmount((double) amount[2]);
			quote.setIgstAmount((double) amount[3]);
			quote.setDiscount(0);
			quote.setTotalAmountWithTax((double) amount[4]);
			quote.setTotalAmountAfterDiscount((double) amount[4]);

			quote.setModifiedBy(new EmployeeDetails(user.getUsername()));
			quote.setModifiedDate(new Date());

			entityManager.persist(quote);
		}

		entityManager.flush();
		entityManager.clear();

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, RuntimeException.class, Exception.class,
			SQLException.class })
	@Override
	public boolean syncQuoteWithOppertunity(long quoteId, int businessDomainId, LoginUser user) throws Exception {

		Quote quote = null;
		try {
			Query selectQuoteLineItemQuery = entityManager.createQuery(
					"select qt from Quote qt left join qt.quoteStatus qts left join qt.oppertunity opr left join qt.shippingState st where qt.isDeleted=false and qt.id=?1",
					Quote.class);
			selectQuoteLineItemQuery.setParameter(1, quoteId);
			quote = (Quote) selectQuoteLineItemQuery.getSingleResult();
		} catch (NoResultException ex) {

		}

		if (quote == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Quote does not exist");
		}

		List<Integer> quoteStatusList = new ArrayList<Integer>();
		quoteStatusList.add(2); // Work Order Received
		quoteStatusList.add(8); // Invoiced

		if (quote != null && quote.getQuoteStatus() != null) {
			if (quoteStatusList.contains(quote.getQuoteStatus().getId())) {
				throw new com.pratikshat.exicom.exception.BadRequestException(
						"You can't able to edit this record");
			}
		}
		
		
		
		if (quote.getOwner().getEmailId().equalsIgnoreCase(user.getUsername())) {
			int statusId = quote.getQuoteStatus().getId();
			List<Integer> list = new ArrayList<>();
			list.add(1);	// Draft 
			list.add(3);	// Rejected
			list.add(11);	// Rejected by store 
			
			if(!list.contains(statusId)) {
				throw new BadRequestException("You can't able to edit this record");
			}
			//throw new UnauthoriseException("You are not authorised to edit this record");
		}else {
			
			ToBeApproved _toBeApproved=null;
			try {
				_toBeApproved=entityManager.createQuery("select apr from ToBeApproved apr join apr.assignedTo asg where apr.ObjectName=?1 and apr.objectId=?2 and apr.status=?3",ToBeApproved.class)
						.setParameter(1,"Quote")
						.setParameter(2,quote.getId())
						.setParameter(3,false)
						.getSingleResult();
			}catch(javax.persistence.NoResultException ex) {
				throw new BadRequestException("You are not authorised");
			}catch(javax.persistence.NonUniqueResultException ex) {
				throw new BadRequestException("You are not authorised");
			}
			if(!_toBeApproved.getAssignedTo().getEmailId().equalsIgnoreCase(user.getUsername())){
				if(!dbProfile.samroleUsers(user.getUsername(), _toBeApproved.getAssignedTo().getEmailId())) {
					Map<String,String> rol=dbProfile.getAccountAndRoleGroup(user);
					if(!rol.get("roleGroup").equalsIgnoreCase("COMMERCIAL"))
						throw new UnauthoriseException("You are not authorised");
				}
				//throw new BadRequestException("You are not authorised");
			}else {
				
			}
		}

		Oppertunity oppertunity = null;
		try {
			Query selectOppertunityQuery = entityManager.createQuery(
					"select opr from Oppertunity opr where opr.isDeleted=false and opr.oppertunityId=?1",
					Oppertunity.class);
			selectOppertunityQuery.setParameter(1, quote.getOppertunity().getOppertunityId());
			oppertunity = (Oppertunity) selectOppertunityQuery.getSingleResult();
		} catch (NoResultException ex) {

		}

		if (oppertunity == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Opportunity does not exist");
		}

		TaxAndVisitCharges taxAndVisitCharges = null;
		try {
			Query selectTaxAndVisitChargesQuery = entityManager.createQuery(
					"select ts from TaxAndVisitCharges ts left join ts.businessDomain bd where bd.businessDomainId=?1",
					TaxAndVisitCharges.class);
			selectTaxAndVisitChargesQuery.setParameter(1, businessDomainId);
			taxAndVisitCharges = (TaxAndVisitCharges) selectTaxAndVisitChargesQuery.getSingleResult();
		} catch (NoResultException ex) {

		}

		if (taxAndVisitCharges == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Taxes for this business does not exist");
		}

		IGSTBillingState igstBillingState = null;
		try {
			Query selectTaxAndVisitChargesQuery = entityManager
					.createQuery("select bs from IGSTBillingState bs where bs.stateName=?1", IGSTBillingState.class);
			selectTaxAndVisitChargesQuery.setParameter(1, quote.getBillingState().getStateName());
			igstBillingState = (IGSTBillingState) selectTaxAndVisitChargesQuery.getSingleResult();
		} catch (NoResultException ex) {

		}

		List<OppertunityLineItem> oppertunityLineItemList = null;
		try {
			Query selectLineItemList = entityManager.createQuery(
					"select oli from OppertunityLineItem oli left join oli.oppertunity opr left join oli.priceBookEntries pbe left join pbe.product prd left join pbe.priceBook pb where opr.oppertunityId=?1 and oli.isDeleted=?2",
					OppertunityLineItem.class);
			selectLineItemList.setParameter(1, oppertunity.getOppertunityId());
			selectLineItemList.setParameter(2,false);
			oppertunityLineItemList = selectLineItemList.getResultList();
		} catch (NoResultException e) {

		}

		List<QuoteLineItem> quoteLineItemList = null;
		try {
			Query selectQuoteLineItemQuery = entityManager.createQuery(
					"select qli from QuoteLineItem qli left join qli.quote qt left join qli.priceBookEntries pbe left join pbe.product prd left join pbe.priceBook pb left join qt.oppertunity opr where qt.id=?1 and qli.isDeleted=?2",
					QuoteLineItem.class);
			selectQuoteLineItemQuery.setParameter(1, quote.getId());
			selectQuoteLineItemQuery.setParameter(2,false);
			quoteLineItemList = (List<QuoteLineItem>) selectQuoteLineItemQuery.getResultList();
		} catch (NoResultException ex) {

		}

		if (oppertunityLineItemList != null && oppertunityLineItemList.size() > 0) {

			for (int i = 0; i < oppertunityLineItemList.size(); i++) {
				OppertunityLineItem lineItemRequest = oppertunityLineItemList.get(i);

				QuoteLineItem lineItem = null;
				try {
					Query selectQuoteLineItemQuery = entityManager.createQuery(
							"select qli from QuoteLineItem qli left join qli.quote qt left join qli.priceBookEntries pb left join pb.product prd where qt.id=?1 and prd.productPartCode=?2 and qli.isDeleted=?3",
							QuoteLineItem.class);
					selectQuoteLineItemQuery.setParameter(1, quote.getId());
					selectQuoteLineItemQuery.setParameter(2,
							lineItemRequest.getPriceBookEntries().getProduct().getProductPartCode());
					selectQuoteLineItemQuery.setParameter(3,false);
					lineItem = (QuoteLineItem) selectQuoteLineItemQuery.getSingleResult();
				} catch (NoResultException ex) {

				}
				if (lineItem != null) {
					continue;
					// throw new com.pratikshat.exicom.exception.BadRequestException("Line Item
					// "+lineItemRequest.getPriceBookEntries().getProduct().getProductPartCode()+"
					// Already exist in Quote");
				}

				double price = lineItemRequest.getPrice();
				double quantity = lineItemRequest.getQuantity();

				double totalPrice = price * quantity;

				PriceBookEntries priceBookEntries = lineItemRequest.getPriceBookEntries();

				QuoteLineItem qlineItem = createQuoteLineItem(quote, priceBookEntries, price, quantity, totalPrice,
						taxAndVisitCharges, igstBillingState, user);

				entityManager.persist(qlineItem);
			}
		}

		if (quoteLineItemList != null && quoteLineItemList.size() > 0) {

			for (int i = 0; i < quoteLineItemList.size(); i++) {
				QuoteLineItem lineItemRequest = quoteLineItemList.get(i);

				OppertunityLineItem oppertunityLineItem = null;
				try {
					Query selectOppertunityLineItemQuery = entityManager.createQuery(
							"select oli from OppertunityLineItem oli left join oli.oppertunity opr left join oli.priceBookEntries pb left join pb.product prd where opr.oppertunityId=?1 and prd.productPartCode=?2 and oli.isDeleted=?3",
							OppertunityLineItem.class);
					selectOppertunityLineItemQuery.setParameter(1, oppertunity.getOppertunityId());
					selectOppertunityLineItemQuery.setParameter(2,
							lineItemRequest.getPriceBookEntries().getProduct().getProductPartCode());
					selectOppertunityLineItemQuery.setParameter(3,false);
					oppertunityLineItem = (OppertunityLineItem) selectOppertunityLineItemQuery.getSingleResult();
				} catch (NoResultException ex) {

				}
				if (oppertunityLineItem != null) {
					continue;
					// throw new com.pratikshat.exicom.exception.BadRequestException("Line Item
					// "+lineItemRequest.getPriceBookEntries().getProduct().getProductPartCode()+"
					// Already exist in Oppertunity");
				}

				double price = lineItemRequest.getPrice();
				double quantity = lineItemRequest.getQuantity();

				double totalPrice = price * quantity;
				PriceBookEntries priceBookEntries = lineItemRequest.getPriceBookEntries();
				OppertunityLineItem lineItem = createOppertunityLineItem(oppertunity, priceBookEntries, price, quantity,
						totalPrice, user);

				entityManager.persist(lineItem);
			}
		}

		try {
			Query sumOppertunityQuery = entityManager.createQuery(
					"select coalesce(sum(oli.totalPrice),0) from OppertunityLineItem oli left join oli.oppertunity opr where oli.isDeleted=false and opr.oppertunityId=?1 and oli.isDeleted=?2",
					Double.class);
			sumOppertunityQuery.setParameter(1, oppertunity.getOppertunityId());
			sumOppertunityQuery.setParameter(2, false);
			Double totalAmount = (Double) sumOppertunityQuery.getSingleResult();

			oppertunity.setAmmount(totalAmount.floatValue());
			oppertunity.setExpectedRevenue(totalAmount.floatValue());

			oppertunity.setModifiedBy(new EmployeeDetails(user.getUsername()));
			oppertunity.setModifiedDate(new Date());
			oppertunity.setSyncFlag(true);
			entityManager.persist(oppertunity);
		} catch (NoResultException ex) {

		}

		Object[] amount = null;
		try {
			Query sumOppertunityQuery = entityManager.createQuery(
					"select coalesce(sum(qli.totalPrice),0) as totalPrice, coalesce(sum(qli.cgstAmount),0) as cgstAmount, coalesce(sum(qli.sgstAmount),0) as sgstAmount, coalesce(sum(qli.igstAmount),0) as igstAmount, coalesce(sum(qli.totalPriceWithTax),0) as totalPriceWithTax from QuoteLineItem qli left join qli.quote qt where qli.isDeleted=false and qt.id=?1");
			sumOppertunityQuery.setParameter(1, quote.getId());
			amount = (Object[]) sumOppertunityQuery.getSingleResult();
		} catch (NoResultException ex) {

		}

		if (amount != null) {
			quote.setSubTotal((double) amount[0]);
			quote.setCgstAmount((double) amount[1]);
			quote.setSgstAmount((double) amount[2]);
			quote.setIgstAmount((double) amount[3]);
			quote.setDiscount(0);
			quote.setTotalAmountWithTax((double) amount[4]);
			quote.setTotalAmountAfterDiscount((double) amount[4]);

			quote.setModifiedBy(new EmployeeDetails(user.getUsername()));
			quote.setModifiedDate(new Date());
			quote.setSyncing(true);
			entityManager.persist(quote);
		}

		entityManager.flush();
		entityManager.clear();
		return quote.isSyncing();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, RuntimeException.class, Exception.class,
			SQLException.class })
	@Override
	public boolean stopSyncQuoteWithOppertunity(long quoteId, int businessDomainId, LoginUser user) throws Exception {

		Quote quote = null;
		try {
			Query selectQuoteLineItemQuery = entityManager.createQuery(
					"select qt from Quote qt left join qt.oppertunity opr left join qt.shippingState st where qt.isDeleted=false and qt.id=?1",
					Quote.class);
			selectQuoteLineItemQuery.setParameter(1, quoteId);
			quote = (Quote) selectQuoteLineItemQuery.getSingleResult();
		} catch (NoResultException ex) {

		}

		if (quote == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Quote does not exist");
		}
		
		List<Integer> quoteStatusList = new ArrayList<Integer>();
		quoteStatusList.add(2); // Work Order Received
		quoteStatusList.add(8); // Invoiced

		if (quote != null && quote.getQuoteStatus() != null) {
			if (quoteStatusList.contains(quote.getQuoteStatus().getId())) {
				throw new com.pratikshat.exicom.exception.BadRequestException(
						"You can't able to edit this record");
			}
		}
		
		
		
		if (quote.getOwner().getEmailId().equalsIgnoreCase(user.getUsername())) {
			int statusId = quote.getQuoteStatus().getId();
			List<Integer> list = new ArrayList<>();
			list.add(1);	// Draft 
			list.add(3);	// Rejected
			list.add(11);	// Rejected by store 
			
			if(!list.contains(statusId)) {
				throw new BadRequestException("You can't able to edit this record");
			}
			//throw new UnauthoriseException("You are not authorised to edit this record");
		}else {
			
			ToBeApproved _toBeApproved=null;
			try {
				_toBeApproved=entityManager.createQuery("select apr from ToBeApproved apr join apr.assignedTo asg where apr.ObjectName=?1 and apr.objectId=?2 and apr.status=?3",ToBeApproved.class)
						.setParameter(1,"Quote")
						.setParameter(2,quote.getId())
						.setParameter(3,false)
						.getSingleResult();
			}catch(javax.persistence.NoResultException ex) {
				throw new BadRequestException("You are not authorised");
			}catch(javax.persistence.NonUniqueResultException ex) {
				throw new BadRequestException("You are not authorised");
			}
			if(!_toBeApproved.getAssignedTo().getEmailId().equalsIgnoreCase(user.getUsername())){
				if(!dbProfile.samroleUsers(user.getUsername(), _toBeApproved.getAssignedTo().getEmailId())) {
					Map<String,String> rol=dbProfile.getAccountAndRoleGroup(user);
					if(!rol.get("roleGroup").equalsIgnoreCase("COMMERCIAL"))
						throw new UnauthoriseException("You are not authorised");
				}
				//throw new BadRequestException("You are not authorised");
			}else {
				
			}
		}
		

		Oppertunity oppertunity = null;
		try {
			Query selectOppertunityQuery = entityManager.createQuery(
					"select opr from Oppertunity opr where opr.isDeleted=false and opr.oppertunityId=?1",
					Oppertunity.class);
			selectOppertunityQuery.setParameter(1, quote.getOppertunity().getOppertunityId());
			oppertunity = (Oppertunity) selectOppertunityQuery.getSingleResult();
		} catch (NoResultException ex) {

		}

		if (oppertunity == null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Opportunity does not exist");
		}

		oppertunity.setSyncFlag(false);
		entityManager.persist(oppertunity);

		quote.setSyncing(false);
		entityManager.persist(quote);

		quote.isSyncing();
		entityManager.flush();
		entityManager.clear();
		return quote.isSyncing();
	}

	OppertunityLineItem createOppertunityLineItem(Oppertunity oppertunity, PriceBookEntries priceBookEntries,
			double price, double quantity, double totalPrice, LoginUser user) {

		OppertunityLineItem item = new OppertunityLineItem();
		item.setOppertunity(oppertunity);
		item.setPriceBookEntries(priceBookEntries);

		item.setPrice(price);
		item.setQuantity(quantity);
		item.setTotalPrice(totalPrice);
		item.setCreatedBy(new EmployeeDetails(user.getUsername()));
		item.setCreatedDate(new Date());
		item.setModifiedBy(new EmployeeDetails(user.getUsername()));
		item.setModifiedDate(new Date());

		return item;
	}

	OppertunityLineItem updateOppertunityLineItem(OppertunityLineItem item, double price, double quantity,
			double totalPrice, LoginUser user) {

		item.setPrice(price);
		item.setQuantity(quantity);
		item.setTotalPrice(totalPrice);
		item.setModifiedBy(new EmployeeDetails(user.getUsername()));
		item.setModifiedDate(new Date());

		return item;
	}

	QuoteLineItem createQuoteLineItem(Quote quote, PriceBookEntries priceBookEntries, double price, double quantity,
			double totalPrice, TaxAndVisitCharges taxAndVisitCharges, IGSTBillingState igstBillingState,
			LoginUser user) {

		QuoteLineItem quoteItem = new QuoteLineItem();
		quoteItem.setQuote(quote);
		quoteItem.setPriceBookEntries(priceBookEntries);

		quoteItem.setPrice(price);
		quoteItem.setQuantity(quantity);
		quoteItem.setTotalPrice(totalPrice);

		// quoteTotalAmount = quoteTotalAmount+totalPrice;

		if (igstBillingState == null) {
			double cgstAmount = (totalPrice * taxAndVisitCharges.getCgst()) / 100;
			double sgstAmount = (totalPrice * taxAndVisitCharges.getSgst()) / 100;

			// quoteCGSTAmount = quoteCGSTAmount+cgstAmount;
			// quoteSGSTAmount = quoteSGSTAmount+sgstAmount;

			quoteItem.setCgstAmount(cgstAmount);
			quoteItem.setSgstAmount(sgstAmount);

			double amountWithTax = totalPrice + cgstAmount + sgstAmount;
			quoteItem.setTotalPriceWithTax(amountWithTax);
			quoteItem.setTotalRepairPrice(amountWithTax);
		} else {
			double igstAmount = (totalPrice * taxAndVisitCharges.getIgst()) / 100;

			// quoteIGSTAmount = quoteIGSTAmount + igstAmount;
			// quoteItem.setIgstAmount(igstAmount);
			double amountWithTax = totalPrice + igstAmount;
			// quoteTotalAmountWithTax = quoteTotalAmountWithTax+amountWithTax;
			quoteItem.setIgstAmount(igstAmount);
			quoteItem.setTotalPriceWithTax(amountWithTax);
			quoteItem.setTotalRepairPrice(amountWithTax);
		}
		quoteItem.setCreatedBy(new EmployeeDetails(user.getUsername()));
		quoteItem.setCreatedDate(new Date());
		quoteItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
		quoteItem.setModifiedDate(new Date());

		return quoteItem;
	}

	QuoteLineItem updateQuoteLineItem(QuoteLineItem quoteItem, double price, double quantity, double totalPrice,
			TaxAndVisitCharges taxAndVisitCharges, IGSTBillingState igstBillingState, LoginUser user) {

		quoteItem.setPrice(price);
		quoteItem.setQuantity(quantity);
		quoteItem.setTotalPrice(totalPrice);

		// quoteTotalAmount = quoteTotalAmount+totalPrice;

		if (igstBillingState == null) {
			double cgstAmount = (totalPrice * taxAndVisitCharges.getCgst()) / 100;
			double sgstAmount = (totalPrice * taxAndVisitCharges.getSgst()) / 100;

			// quoteCGSTAmount = quoteCGSTAmount+cgstAmount;
			// quoteSGSTAmount = quoteSGSTAmount+sgstAmount;

			quoteItem.setCgstAmount(cgstAmount);
			quoteItem.setSgstAmount(sgstAmount);

			double amountWithTax = totalPrice + cgstAmount + sgstAmount;
			quoteItem.setTotalPriceWithTax(amountWithTax);
			quoteItem.setTotalRepairPrice(amountWithTax);
		} else {
			double igstAmount = (totalPrice * taxAndVisitCharges.getIgst()) / 100;

			// quoteIGSTAmount = quoteIGSTAmount + igstAmount;
			// quoteItem.setIgstAmount(igstAmount);
			double amountWithTax = totalPrice + igstAmount;
			// quoteTotalAmountWithTax = quoteTotalAmountWithTax+amountWithTax;
			quoteItem.setIgstAmount(igstAmount);
			quoteItem.setTotalPriceWithTax(amountWithTax);
			quoteItem.setTotalRepairPrice(amountWithTax);
		}
		quoteItem.setModifiedBy(new EmployeeDetails(user.getUsername()));
		quoteItem.setModifiedDate(new Date());

		return quoteItem;
	}

	private String getApproverEmail(String role, String userMailId, javax.persistence.EntityManager _em) {
		com.pratikshat.exicom.entity.EmployeeDetails emp = null;
		emp = _em
				.createQuery("select mng from EmployeeDetails emp join emp.manager mng where emp.emailId=?1",
						com.pratikshat.exicom.entity.EmployeeDetails.class)
				.setParameter(1, userMailId).getSingleResult();

		java.util.List<String> roles = _em.createQuery(
				"select rol.authority from LoginUserRoles rol join rol.loginUser usr where usr.username=?1",
				String.class).setParameter(1, emp.getEmailId()).getResultList();

		for (String _rl : roles) {
			if (_rl.equalsIgnoreCase(role)) {
				return emp.getEmailId();
			}
		}
		return getApproverEmail(role, emp.getEmailId(), _em);

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, RuntimeException.class, Exception.class,
			SQLException.class })
	@Override
	public ByteArrayInputStream createPdf(long quoteId, LoginUser user) throws Exception {

		QuotePDFGeneration quotePDFGeneration = new QuotePDFGeneration(entityManager, fileStorageProperties);

		return quotePDFGeneration.createPdf(quoteId, user);

	}

	public void createServiceAppointment(long objectId, String userEmaiid,
			javax.persistence.EntityManager entityManager) throws Exception {

		CaseDetails _caseDetails = null;
		try {
			Query selectCaseDetailsQuery = entityManager.createQuery(
					"select ca from CaseDetails ca left join ca.status cs left join ca.assets ast left join ast.account acn left join ast.site st left join ca.engineer eng left join eng.contact econ left join ca.serviceCenter scr left join ca.businessDomain bd left join ca.caseType cty where ca.caseId=?1",
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
		statusList.add(1L);	// Unassigned
		statusList.add(2L);	// Scheduled
		statusList.add(3L);	// Dispatched
		
		List<ServiceAppointment> serviceAppointmentList= null;
		try {
			serviceAppointmentList = entityManager.createQuery(
					"select sa from ServiceAppointment sa join sa.status st join sa.caseDetails cd where cd.caseId=:caseId and st.statusId in :statusList",
					com.pratikshat.exicom.entity.ServiceAppointment.class)
					.setParameter("caseId", objectId)
					.setParameter("statusList", statusList).getResultList();
		} catch (NoResultException ex) {
			
		}
		
		if(serviceAppointmentList!=null && serviceAppointmentList.size()>0) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Service Appointment already present in Dispatch,Unassigned or Scheduled status");
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

		com.pratikshat.exicom.entity.ServiceAppointment _serviceAppointment = new com.pratikshat.exicom.entity.ServiceAppointment();

		_serviceAppointment.setCaseDetails(_caseDetails);
		_serviceAppointment.setCreateDate(new java.util.Date());
		_serviceAppointment.setCreatedBy(_emp);
		_serviceAppointment.setModifiedBy(_emp);
		_serviceAppointment.setModifiedDate(new java.util.Date());
		_serviceAppointment.setSyncFlag(false);
		_serviceAppointment.setCircle(siteDetails.getCircle());

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
		if (_caseDetails.getBusinessDomain().getBusinessDomainId()==1) {	// Battery
			if (siteDetails.getCircle() != null) {
				if (siteDetails.getCircle().getCircleManagerBattry() != null) {
					_serviceAppointment.setCircleManager(siteDetails.getCircle().getCircleManagerBattry());
				}
			}
		}

		if (_caseDetails.getBusinessDomain().getBusinessDomainId()==2) {	// DCT
			if (siteDetails.getCircle() != null) {
				if (siteDetails.getCircle().getCircleManagerPower() != null) {
					_serviceAppointment.setCircleManager(siteDetails.getCircle().getCircleManagerPower());
				}
			}
		}
		if (_caseDetails.getBusinessDomain().getBusinessDomainId()==3) {	// EV
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
			messagingTemplate.convertAndSend(notification);
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

		if (_caseDetails.getBusinessDomain().getBusinessDomainName().equalsIgnoreCase("Battery")) {

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

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			Exception.class, BadRequestException.class, UnauthoriseException.class, RuntimeException.class,
			SQLException.class })
	@Override
	public void submitForApproval(long quoteId, LoginUser user) throws Exception {

		Quote quote = null;
		try {
			Query selectQuoteQuery = entityManager.createQuery(
					"select qt from Quote qt left join qt.oppertunity opr left join opr.accounts acn left join opr.caseDetails cd left join cd.site st left join st.circle crl left join qt.quoteStatus qtst left join qt.billingCountry bc left join qt.billingState bs left join qt.shippingCountry sc left join qt.shippingState ss left join qt.createdBy createdby left join qt.modifiedBy modified left join qt.owner onr where qt.id=?1",
					Quote.class);
			selectQuoteQuery.setParameter(1, quoteId);
			quote = (Quote) selectQuoteQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Quote does not exist");
		}

		if (quote != null) {

			if (!quote.getOwner().getEmailId().equalsIgnoreCase(user.getUsername())) {
				if(!dbProfile.samroleUsers(quote.getOwner().getEmailId(), user.getUsername())) {
					Map<String,String> rol=dbProfile.getAccountAndRoleGroup(user);
					if(!(rol.get("roleGroup").equalsIgnoreCase("CUSTOMERCARE")))
						throw new UnauthoriseException("You are not authorised");
				}
				//throw new UnauthoriseException("You are not authorised to edit this record");
			}

			List<Integer> list = new ArrayList<Integer>();
			list.add(1); // Draft
			list.add(3); // Rejected
			list.add(11); // Rejected by Store

			if (!list.contains(quote.getQuoteStatus().getId())) {
				throw new com.pratikshat.exicom.exception.BadRequestException(
						"Quote already approved or pending for approval");
			}

			
			List<QuoteLineItem> quoteLineItemList = null;
			try {
				Query selectQuoteLineItemQuery = entityManager.createQuery(
						"select qli from QuoteLineItem qli where qli.quote.id=?1",
						QuoteLineItem.class);
				selectQuoteLineItemQuery.setParameter(1, quoteId);
				quoteLineItemList = (List<QuoteLineItem>) selectQuoteLineItemQuery.getResultList();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Quote does not have line item");
			}
			
			if(quoteLineItemList== null) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Quote does not have line item");
			}
			
			if(quoteLineItemList!=null && quoteLineItemList.size()==0) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Quote does not have line item");
			}
			
			quote.setSubmittedDate(new Date());
			quote.setQuoteStatus(new QuoteStatus(6));
			quote.setModifiedBy(new EmployeeDetails(user.getUsername()));
			quote.setModifiedDate(new Date());

			entityManager.persist(quote);

			// approval process entry point
			try {

				java.util.Set<String> _roles = new java.util.HashSet<String>();
				java.util.Iterator ite = user.getAuthorities().iterator();
				while (ite.hasNext()) {
					com.pratikshat.exicom.entity.LoginUserRoles _rr = (com.pratikshat.exicom.entity.LoginUserRoles) ite
							.next();
					_roles.add(_rr.getAuthority());
				}

				boolean roleFound = false;
				boolean userFound = false;
				com.pratikshat.exicom.entity.Approval _appliedRule = new com.pratikshat.exicom.entity.Approval();

				java.util.List<com.pratikshat.exicom.entity.Approval> _rulesLst = new java.util.ArrayList<com.pratikshat.exicom.entity.Approval>();

				_rulesLst = entityManager.createQuery(
						"select apr from Approval apr where apr.objectClassName=?1 and apr.active=?2 and apr.businessDomain.businessDomainId=?3",
						com.pratikshat.exicom.entity.Approval.class).setParameter(1, "Quote").setParameter(2, true)
						.setParameter(3, quote.getBusinessDomain().getBusinessDomainId()).getResultList();

				for (com.pratikshat.exicom.entity.Approval _appr : _rulesLst) {
					String submitters[] = _appr.getInitialSubmitters().split(",");
					for (String submi : submitters) {
						String _subs[] = submi.split(":");
						if (_subs[0].equalsIgnoreCase("ROLE")) {
							if (_roles.contains(_subs[1])) {
								roleFound = true;
								break;
							}
						}
						if (_subs[0].equalsIgnoreCase("USER")) {
							if (user.getUsername().equalsIgnoreCase(_subs[1])) {
								userFound = true;
								break;
							}
						}
					}
					if (roleFound || userFound) {
						_appliedRule = _appr;
						break;
					}
				}

				com.pratikshat.exicom.entity.ApprovalSteps _stapes = entityManager
						.createQuery("select stp from ApprovalSteps stp where stp.approval=?1 and stp.approvedStage=?2",
								com.pratikshat.exicom.entity.ApprovalSteps.class)
						.setParameter(1, _appliedRule).setParameter(2, 1).getSingleResult();

				String assigned[] = _stapes.getApprovedBy().split(":");

				String assigneUserEmailId = "";

				if (assigned[0].equalsIgnoreCase("USER")) {
					assigneUserEmailId = assigned[1];
				}
				if (assigned[0].equalsIgnoreCase("APPROVAL-MANAGER")) {
					assigneUserEmailId = entityManager.createQuery(
							"select apm.emailId from EmployeeDetails emp join emp.approvalManager apm where emp.emailId=?1",
							String.class).setParameter(1, user.getUsername()).getSingleResult();
				}

				if (assigned[0].equalsIgnoreCase("MANAGER")) {
					assigneUserEmailId = entityManager.createQuery(
							"select apm.emailId from EmployeeDetails emp join emp.manager apm where emp.emailId=?1",
							String.class).setParameter(1, user.getUsername()).getSingleResult();
				}

				if (assigned[0].equalsIgnoreCase("ROLE")) {
					assigneUserEmailId = getApproverEmail(assigned[1], user.getUsername(), entityManager);
				}

				com.pratikshat.exicom.entity.ToBeApproved _toBeApproved = new com.pratikshat.exicom.entity.ToBeApproved();
				_toBeApproved.setAssignedTo(new com.pratikshat.exicom.entity.EmployeeDetails(assigneUserEmailId));
				_toBeApproved.setAssignedDate(new java.util.Date());
				_toBeApproved.setObjectId(quote.getId());
				_toBeApproved.setObjectName("Quote");
				_toBeApproved.setObjectId(quote.getId());
				_toBeApproved.setObjectManualId(quote.getQuoteNumber());
				_toBeApproved.setObjectUrl("/Quote/" + quote.getId());
				_toBeApproved.setRemark("");
				_toBeApproved.setApprovalRuleId(_appliedRule.getId());
				_toBeApproved.setStage(1);
				_toBeApproved.setStatus(false);
				_toBeApproved.setPreviousRemarks(null);
				entityManager.persist(_toBeApproved);
				
				String empName="";
				if(quote.getOwner()!=null&&quote.getOwner().getFirstName()!=null) empName=quote.getOwner().getFirstName();
				if(quote.getOwner()!=null&&quote.getOwner().getMiddleName()!=null) empName=empName+" "+quote.getOwner().getMiddleName();
				if(quote.getOwner()!=null&&quote.getOwner().getLastName()!=null) empName=empName+" "+quote.getOwner().getLastName();
				
				ObjectDefinition _obj=null;
				try {
					_obj=entityManager.createQuery("select obj from ObjectDefinition obj where obj=?1",ObjectDefinition.class)
							.setParameter(1, new ObjectDefinition(_toBeApproved.getObjectName()))
							.getSingleResult();
				}catch(Exception ex) {
					
				}
				
				com.pratikshat.exicom.entity.ApplicationNotificationDetails notificationDetails=new com.pratikshat.exicom.entity.ApplicationNotificationDetails();
				notificationDetails.setNofificationDate(new java.util.Date());
				notificationDetails.setNotificationActionUrl("/itemtobeapproved");
				notificationDetails.setNotificationMessage(String.format("%s %s initiated by %s received for your approval",_obj==null?_toBeApproved.getObjectName():_obj.getObjectLableName(),_toBeApproved.getObjectManualId(),empName));
				notificationDetails.setNotificationRead(false);
				notificationDetails.setUserEmailId(assigneUserEmailId);

				entityManager.persist(notificationDetails);

				try {
					messagingTemplate.convertAndSend("/topic/" + assigneUserEmailId, notificationDetails);
				} catch (Exception ex) {
				}

			} catch (Exception ex) {
				ex.printStackTrace();
				throw new BadRequestException("Unable to submit request for approval");

			}
		} else {
			throw new BadRequestException("Quote does not exist");
		}
	}
	
	
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			Exception.class, BadRequestException.class, UnauthoriseException.class, RuntimeException.class,
			SQLException.class })
	@Override
	public void workOrderUpdate(WorkOrderRequest request, LoginUser user) throws Exception {

		Quote quote = null;
		try {
			Query selectQuoteQuery = entityManager.createQuery(
					"select qt from Quote qt left join qt.oppertunity opr left join opr.accounts acn left join opr.caseDetails cd left join cd.site st left join st.circle crl left join qt.quoteStatus qtst left join qt.billingCountry bc left join qt.billingState bs left join qt.shippingCountry sc left join qt.shippingState ss left join qt.createdBy createdby left join qt.modifiedBy modified left join qt.owner onr where qt.id=?1",
					Quote.class);
			selectQuoteQuery.setParameter(1, request.getQuoteId());
			quote = (Quote) selectQuoteQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Quote does not exist");
		}
		
		Map<String,String> roleGroupName=dbProfile.getAccountAndRoleGroup(user);
		
		if (quote != null) {

			if (!quote.getOwner().getEmailId().equalsIgnoreCase(user.getUsername())) {
				
				if(!dbProfile.samroleUsers(quote.getOwner().getEmailId(), user.getUsername()))
					if(!roleGroupName.get("roleGroup").equalsIgnoreCase("COMMERCIAL"))
						throw new UnauthoriseException("You are not authorised to edit this record");
			}

			List<Integer> list = new ArrayList<Integer>();
			list.add(4); // Quote Sent
			
			if (!list.contains(quote.getQuoteStatus().getId())) {
				throw new com.pratikshat.exicom.exception.BadRequestException(
						"You can't update the work order");
			}

			quote.setWorkOrderDate(request.getWorkOrderDate());
			quote.setWorkOrderNumber(request.getWorkOrderNumber());
			quote.setModifiedBy(new EmployeeDetails(user.getUsername()));
			quote.setModifiedDate(new Date());
			quote.setQuoteStatus(new QuoteStatus(2)); // Work Order Received
			entityManager.persist(quote);
			
			
			Oppertunity oppertunity = null;
			try {
				Query selectOppertunityQuery = entityManager.createQuery(
						"select opr from Quote qt left join qt.oppertunity opr left join opr.caseDetails cd left join opr.oppertunityStage oprst left join opr.forcastCategory oprctg where qt.id=?1",
						Oppertunity.class);
				selectOppertunityQuery.setParameter(1, quote.getId());
				oppertunity = (Oppertunity) selectOppertunityQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Oppertunity does not exist");
			}
			
			if(oppertunity==null) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Oppertunity does not found");
			}
			
			oppertunity.setOppertunityStage(new OppertunityStage(2)); // Closed Won
			oppertunity.setForcastCategory(new OppertunityForecastCategory(5)); // Closed
			oppertunity.setCloseDate(new Date());
			oppertunity.setProbability(100);
			entityManager.persist(oppertunity);
			

			CaseDetails _caseDetails = null;
			try {
				Query selectCaseDetailsQuery = entityManager.createQuery(
						"select ca from CaseDetails ca left join ca.status cs left join ca.caseType ct left join ca.stage cst left join ca.caseReason crs left join ca.businessDomain bd left join ca.assets ast left join ast.account acn left join ca.site st left join st.circle cl left join ca.ownerDetails onr left join ca.createdBy cby left join ca.engineer eng left join eng.contact econ left join ca.serviceCenter scr where ca.caseId=?1",
						CaseDetails.class);
				selectCaseDetailsQuery.setParameter(1, quote.getOppertunity().getCaseDetails().getCaseId());
				_caseDetails = (CaseDetails) selectCaseDetailsQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
			}
			
			
			ServiceRecource serviceRecource = null;
			try {
				Query serviceAppointmentOwnerQuery = entityManager.createQuery(
						"select sr from CaseDetails cd left join cd.site st left join st.serviceResource sr left join sr.user ur where cd.caseId=?1",
						ServiceRecource.class);
				serviceAppointmentOwnerQuery.setParameter(1,
						_caseDetails.getCaseId());
				serviceRecource = (ServiceRecource) serviceAppointmentOwnerQuery.getSingleResult();
			} catch (NoResultException ex) {

			}
			
			com.pratikshat.exicom.entity.SiteDetails siteDetails = null;
			try {
				Query siteDetailsQuery = entityManager.createQuery(
						"select st from SiteDetails st  left join st.circle cr left join cr.circleManagerBattry cb left join cr.circleManagerPower cp left join cr.circleManagerEv ev where st.siteId=?1",
						com.pratikshat.exicom.entity.SiteDetails.class);
				siteDetailsQuery.setParameter(1, _caseDetails.getSite().getSiteId());
				siteDetails = (com.pratikshat.exicom.entity.SiteDetails) siteDetailsQuery.getSingleResult();
			} catch (NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist");
			}
			
			
			if (serviceRecource != null) {
				if (_caseDetails.getBusinessDomain().getBusinessDomainId()==1) {	// Battery
					_caseDetails.setStatus(new CaseStatusDetails(3)); // In-Process
					_caseDetails.setStage(new CaseStageDetails(5));	// Assign to engineer
					
					if (siteDetails.getCircle() != null) {
						if (siteDetails.getCircle().getCircleManagerBattry() != null) {
							_caseDetails.setOwnerDetails(new EmployeeDetails(siteDetails.getCircle().getCircleManagerBattry().getEmailId()));
						}
					}
				}
				
				if (_caseDetails.getBusinessDomain().getBusinessDomainId()==2) {	// DCT
					_caseDetails.setStatus(new CaseStatusDetails(3)); // In-Process
					_caseDetails.setStage(new CaseStageDetails(11));	// Assign to engineer
					
					if (siteDetails.getCircle() != null) {
						if (siteDetails.getCircle().getCircleManagerPower() != null) {
							_caseDetails.setOwnerDetails(new EmployeeDetails(siteDetails.getCircle().getCircleManagerPower().getEmailId()));
						}
					}
				}
				
				if (_caseDetails.getBusinessDomain().getBusinessDomainId()==1) {	// EV
					
					
					if (siteDetails.getCircle() != null) {
						if (siteDetails.getCircle().getCircleManagerEv() != null) {
							_caseDetails.setOwnerDetails(new EmployeeDetails(siteDetails.getCircle().getCircleManagerEv().getEmailId()));
						}
					}
				}
				
			} else {
				try {
					
					if (_caseDetails.getBusinessDomain().getBusinessDomainId()==1) {	// Battery
						_caseDetails.setStatus(new CaseStatusDetails(3)); // In-Process
						_caseDetails.setStage(new CaseStageDetails(6));	// Assign to dispatcher
						
						if (siteDetails.getCircle() != null) {
							if (siteDetails.getCircle().getCircleManagerBattry() != null) {
								_caseDetails.setOwnerDetails(new EmployeeDetails(siteDetails.getCircle().getCircleManagerBattry().getEmailId()));
							}
						}
					}
					
					if (_caseDetails.getBusinessDomain().getBusinessDomainId()==2) {	// DCT
						_caseDetails.setStatus(new CaseStatusDetails(3)); // In-Process
						_caseDetails.setStage(new CaseStageDetails(12));	// Assign to dispatcher
						
						if (siteDetails.getCircle() != null) {
							if (siteDetails.getCircle().getCircleManagerPower() != null) {
								_caseDetails.setOwnerDetails(new EmployeeDetails(siteDetails.getCircle().getCircleManagerPower().getEmailId()));
							}
						}
					}
					
					if (_caseDetails.getBusinessDomain().getBusinessDomainId()==1) {	// EV
					
						
						if (siteDetails.getCircle() != null) {
							if (siteDetails.getCircle().getCircleManagerEv() != null) {
								_caseDetails.setOwnerDetails(new EmployeeDetails(siteDetails.getCircle().getCircleManagerEv().getEmailId()));
							}
						}
					}
					
				} catch (NoResultException ex) {
					throw new com.pratikshat.exicom.exception.BadRequestException(
							"SCH deos not exist");
				}
			}
			
			entityManager.persist(_caseDetails);

			
			
			
			// Service Appointment generation code
			ServiceAppointment _serviceAppointment = new ServiceAppointmentUtil().createServiceAppointment(_caseDetails.getCaseId(), user.getUsername(), entityManager);
			
			workflow.executeWorkflow(entityManager, _serviceAppointment, "INSERT", "PRE", user);
			entityManager.persist(_serviceAppointment);
			workflow.executeWorkflow(entityManager, _serviceAppointment, "INSERT", "POST", user);
			
			
			
			Query updateServiceAppointmentManualId = entityManager.createQuery(
					"update ServiceAppointment sa set sa.appointmentNumber=?1 where sa.serviceAppointmentId=?2");
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
//			String serviceAppointmentManualId = "SA-" + df.format(new java.util.Date()) + "-"
//					+ com.pratikshat.exicom.config.MiscellaneousUtils
//							.fillZero(String.valueOf(_serviceAppointment.getServiceAppointmentId()), 5);
			
			String serviceAppointmentManualId = "SA-"+ com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(_serviceAppointment.getServiceAppointmentId()), 7);
			updateServiceAppointmentManualId.setParameter(1, serviceAppointmentManualId);
			updateServiceAppointmentManualId.setParameter(2, _serviceAppointment.getServiceAppointmentId());
			updateServiceAppointmentManualId.executeUpdate();
			// notification to service appointment Eng
			try {
				ApplicationNotificationDetails notification = new ApplicationNotificationDetails();
				notification.setNofificationDate(new java.util.Date());
				notification.setNotificationActionUrl(
						"/serviceappointment/" + _serviceAppointment.getServiceAppointmentId());
				notification.setNotificationMessage(
						String.format("Service Appointment %s assigned for your action",
								serviceAppointmentManualId));
				notification.setNotificationRead(false);
				notification
						.setUserEmailId(_serviceAppointment.getServiceResource().getUser().getEmailId());
				entityManager.persist(notification);
				messagingTemplate.convertAndSend("/topci/"+_serviceAppointment.getServiceResource().getUser().getEmailId(),notification);
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
				entityManager.persist(_search);

			} catch (Exception e) {
			}
			
			
			if(_caseDetails.getBusinessDomain().getBusinessDomainId()==1) { // Battery
				
				BatteryFSR batteryFSR = new BatteryFSR();
				batteryFSR.setServiceAppointment(_serviceAppointment);
				entityManager.persist(batteryFSR);
				
				Query updateFSRManualId=entityManager.createQuery("update BatteryFSR fsr set fsr.manualId=?1 where fsr.id=?2");
				//String fsrManualId= "FSR-CBMS-LI-"+df.format(new java.util.Date())+"-"+com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(batteryFSR.getId()),5);
				
				String fsrManualId= "FSR-CBMS-LI-"+com.pratikshat.exicom.config.MiscellaneousUtils.fillZero(String.valueOf(batteryFSR.getId()),7);
				updateFSRManualId.setParameter(1,fsrManualId);
				updateFSRManualId.setParameter(2, batteryFSR.getId());
				updateFSRManualId.executeUpdate();
				
				//search index entry
				try {
					com.pratikshat.exicom.entity.GlobalSearchIndex _search=new com.pratikshat.exicom.entity.GlobalSearchIndex();
					_search.setKeyName("manualId");
					_search.setObjectDetailsUrl("Batteryfsr/"+batteryFSR.getId());
					_search.setObjectIdName("id");
					_search.setObjectIdValue(""+batteryFSR.getId());
					_search.setObjectName("BatteryFSR");
					_search.setSearchString(fsrManualId);
					entityManager.persist(_search);
					
				}catch(Exception e) {}
			}

		} else {
			throw new BadRequestException("Quote does not exist");
		}
	}
	
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			Exception.class, BadRequestException.class, UnauthoriseException.class, RuntimeException.class,
			SQLException.class })
	@Override
	public List<MailLogResponse> getMailLog(long quoteId, LoginUser user) throws Exception{
		List<MailLogResponse> mailLogResponse = new ArrayList<>();
		Quote quote = null;
		try {
			Query selectQuoteQuery = entityManager.createQuery("select qt from Quote qt where qt.id=?1", Quote.class);
			selectQuoteQuery.setParameter(1, quoteId);
			quote = (Quote) selectQuoteQuery.getSingleResult();
		} catch (NoResultException ex) {
			
		}
		
		if(quote==null) {
			return mailLogResponse;
		}
		
		List<MailLog> mailLogList = null;
		try {
			Query selectMailQuery = entityManager.createQuery("select ml from MailLog ml where ml.objectName=?1 and ml.objectId=?2",MailLog.class);
			selectMailQuery.setParameter(1, "Quote");
			selectMailQuery.setParameter(2, String.valueOf(quote.getId()));
			mailLogList = (List<MailLog>)selectMailQuery.getResultList();
		} catch (NoResultException ex) {
			
		}
		
		if(mailLogList!=null && mailLogList.size()>0) {
			for(int i=0; i<mailLogList.size(); i++) {
				MailLog log = mailLogList.get(i);
				List<MailLogAttachements> attachment = null;
				try {
					Query selectMailAttachmentQuery = entityManager.createQuery("select mla from MailLogAttachements mla left join mla.mailLog ml where ml.id=?1",MailLogAttachements.class);
					selectMailAttachmentQuery.setParameter(1, log.getId());
					attachment = (List<MailLogAttachements>)selectMailAttachmentQuery.getResultList();
				} catch (NoResultException ex) {
					
				}
				
				MailLogResponse response = new MailLogResponse();
				response.setId(log.getId());
				response.setLogBy(log.getLogBy());
				response.setLogDate(log.getLogDate());
				response.setMailContent(log.getMailContent());
				response.setObjectId(log.getObjectId());
				response.setObjectName(log.getObjectName());
				response.setSendCc(log.getSendCc());
				response.setSendFrom(log.getSendFrom());
				response.setSendTo(log.getSendTo());
				response.setSubject(log.getSubject());
				
				if(attachment!=null && attachment.size()>0) {
					List<String> attachedFile = new ArrayList<>();
					for(int j=0; j<attachment.size(); j++) {
						attachedFile.add(attachment.get(j).getFilepath());
					}
					response.setAttachemt(attachedFile);
				}
				
				mailLogResponse.add(response);
			}	
		}
		
		
		return mailLogResponse;
	}
	
	
	@Override
	@Transactional
	public List<QuoteResponse> advanceSearch( Map<String,Object> request,List<String> users) {
		
		String queryString ="select new com.pratikshat.exicom.model.response.QuoteResponse(qt) from Quote qt left join qt.oppertunity opr left join opr.caseDetails cd left join opr.accounts acn left join qt.quoteStatus qtst where 1=1";
		
		
		if(request.get("quoteNumber")!=null&&!request.get("quoteNumber").equals("")) {
			queryString+=" and qt.quoteNumber like :quoteNumber";
		}
		if(request.get("quoteName")!=null&&!request.get("quoteName").equals("")) {
			queryString+=" and qt.quoteName like :quoteName";
		}
		if(request.get("accountName")!=null&&!request.get("accountName").equals("")) {
			queryString+=" and acn.name like :accountName";
		}
		if(request.get("oppertunityNumber")!=null&&!request.get("oppertunityNumber").equals("")) {
			queryString+=" and opr.oppertunityNumber like :oppertunityNumber";
		}
		if(request.get("expiryDate")!=null&&!request.get("expiryDate").equals("")) {
			queryString+=" and function('date_format', qt.expiryDate, '%Y-%m-%d')=function('date_format', :expiryDate, '%Y-%m-%d')";
		}
		if(request.get("status")!=null&&!request.get("status").equals("")) {
			queryString+=" and qtst.name like :status";
		}
		
		Query qry=entityManager.createQuery(queryString,QuoteResponse.class);
		
		
		if(request.get("quoteNumber")!=null&&!request.get("quoteNumber").equals("")) {
			qry.setParameter("quoteNumber","%"+ request.get("quoteNumber")+"%");
		}
		if(request.get("quoteName")!=null&&!request.get("quoteName").equals("")) {
			qry.setParameter("quoteName","%"+ request.get("quoteName")+"%");
		}
		if(request.get("accountName")!=null&&!request.get("accountName").equals("")) {
			qry.setParameter("accountName","%"+ request.get("accountName")+"%");
		}
		if(request.get("oppertunityNumber")!=null&&!request.get("oppertunityNumber").equals("")) {
			qry.setParameter("oppertunityNumber","%"+ request.get("oppertunityNumber")+"%");
		}
		if(request.get("expiryDate")!=null&&!request.get("expiryDate").equals("")) {
			qry.setParameter("expiryDate", request.get("expiryDate"));
		}
		if(request.get("status")!=null&&!request.get("status").equals("")) {
			qry.setParameter("status","%"+ request.get("status")+"%");
		}
		
		List<QuoteResponse> response= qry.getResultList();
																										
		 return response;
	}
}
