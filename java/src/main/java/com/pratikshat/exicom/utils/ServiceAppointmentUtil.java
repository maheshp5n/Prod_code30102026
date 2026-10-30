package com.pratikshat.exicom.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.pratikshat.exicom.entity.Assets;
import com.pratikshat.exicom.entity.CaseDetails;
import com.pratikshat.exicom.entity.CaseReasonDetails;
import com.pratikshat.exicom.entity.CaseStageDetails;
import com.pratikshat.exicom.entity.ClaimStatus;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.PendingResponsibility;
import com.pratikshat.exicom.entity.ServiceAppointment;
import com.pratikshat.exicom.entity.ServiceRecource;
import com.pratikshat.exicom.exception.BadRequestException;

public class ServiceAppointmentUtil {

public ServiceAppointment createServiceAppointment(long caseId, String userEmailId, EntityManager entityManager)throws BadRequestException {
		
		CaseDetails caseDetails = null;
		try {
			Query selectCaseDetailsQuery = entityManager.createQuery(
					"select ca from CaseDetails ca left join ca.status cs left join ca.caseType ct left join ca.stage cst left join ca.caseReason crs left join ca.businessDomain bd left join ca.assets ast left join ast.account acn left join ca.site st left join st.circle cl left join ca.ownerDetails onr left join ca.createdBy cby left join ca.engineer eng left join eng.contact econ left join ca.serviceCenter scr where ca.caseId=?1",
					CaseDetails.class);
			selectCaseDetailsQuery.setParameter(1, caseId);
			caseDetails = (CaseDetails) selectCaseDetailsQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
		}
		
		List<Long> saStatusList = new ArrayList<>();
		saStatusList.add(1L);	// Unassigned
		saStatusList.add(2L);	// Scheduled
		saStatusList.add(3L);	// Dispatched
		
		List<ServiceAppointment> serviceAppointmentList= null;
		try {
			serviceAppointmentList = entityManager.createQuery(
					"select sa from ServiceAppointment sa join sa.status st join sa.caseDetails cd where cd.caseId=:caseId and st.statusId in :statusList",
					com.pratikshat.exicom.entity.ServiceAppointment.class)
					.setParameter("caseId", caseDetails.getCaseId())
					.setParameter("statusList", saStatusList).getResultList();
		} catch (NoResultException ex) {
			
		}
		
		if(serviceAppointmentList!=null && serviceAppointmentList.size()>0) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Service Appointment already present in Dispatch,Unassigned or Scheduled status");
		}
		
		com.pratikshat.exicom.entity.SiteDetails siteDetails = null;
		
		try {
			Query siteDetailsQuery = entityManager.createQuery(
					"select st from SiteDetails st join st.serviceCenter scr left join st.circle cr left join cr.circleManagerBattry cb left join cr.circleManagerPower cp left join cr.circleManagerEv ev where st.siteId=?1",
					com.pratikshat.exicom.entity.SiteDetails.class);
			siteDetailsQuery.setParameter(1, caseDetails.getSite().getSiteId());
			siteDetails = (com.pratikshat.exicom.entity.SiteDetails) siteDetailsQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist");
		}
		
		com.pratikshat.exicom.entity.EmployeeDetails caseCreatedBy = null;
		try {
			Query getUserDetailsQuery = entityManager.createQuery(
					"select emp from EmployeeDetails emp left join ContactDetails ct on emp.emailId=ct.emailId where emp.emailId=?1");
			getUserDetailsQuery.setParameter(1, caseDetails.getCreatedBy().getEmailId());
			caseCreatedBy = (com.pratikshat.exicom.entity.EmployeeDetails) getUserDetailsQuery
					.getSingleResult();
		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Created by does not exist");
		}
		
		Assets assets = null;
		if(caseDetails.getAssets()!=null) {
			try {
				Query assetsQuery = entityManager.createQuery("select ast from Assets ast left join ast.product prd where ast.serialNumber=?1",Assets.class);
				assetsQuery.setParameter(1, caseDetails.getAssets().getSerialNumber());
				assets = (Assets) assetsQuery.getSingleResult();
			}catch(NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Asset does not exist");
			}
		}
		
		
		com.pratikshat.exicom.entity.ServiceAppointment _serviceAppointment = new com.pratikshat.exicom.entity.ServiceAppointment();

		_serviceAppointment.setCaseDetails(new com.pratikshat.exicom.entity.CaseDetails(caseDetails.getCaseId()));
		_serviceAppointment.setCreateDate(new java.util.Date());
		_serviceAppointment.setCreatedBy(new EmployeeDetails(userEmailId));
		_serviceAppointment.setModifiedBy(new EmployeeDetails(userEmailId));
		_serviceAppointment.setModifiedDate(new java.util.Date());
		_serviceAppointment.setSyncFlag(false);
		_serviceAppointment.setApprovedByFinance(false);
		_serviceAppointment.setCircle(siteDetails.getCircle());
		_serviceAppointment.setFixedPayout(siteDetails.getServiceCenter().getLegalEntityFixedPayout());
		if(caseDetails.getAssets()!=null) {
			_serviceAppointment.setAssets(assets);
			_serviceAppointment.setProduct(assets.getProduct());	
		}
		
		
		
		
//		List<Long> caseTypeList = new ArrayList<>();
//		caseTypeList.add(3L);	// Under Warranty - Battery
//		caseTypeList.add(7L);	// Under Warranty - DCT
//		
//		if(caseTypeList.contains(caseDetails.getCaseType().getCaseTypeId())) {
//			_serviceAppointment.setCostCenter("3002010104");
//		}else {
//			_serviceAppointment.setCostCenter("3002010102");
//		}
		
		
		if(caseDetails.getCaseReason()!=null) {
			try {
				Query caseReasonQuery = entityManager.createQuery("select cr from CaseReasonDetails cr where cr.reasonId=?1",CaseReasonDetails.class);
				caseReasonQuery.setParameter(1, caseDetails.getCaseReason().getReasonId());
				CaseReasonDetails caseReason = (CaseReasonDetails) caseReasonQuery.getSingleResult();
				
				_serviceAppointment.setCostCenter(caseReason.getCostCenter());
			}catch(NoResultException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("Case Reason does not exist");
			}catch (Exception e) {
				throw new com.pratikshat.exicom.exception.BadRequestException(e.getMessage());
			}
		}
		
		
		
		String circleManagerEmailId = null;
		if (caseDetails.getBusinessDomain().getBusinessDomainId()==1) {	// Battery
			if (siteDetails.getCircle() != null) {
				if (siteDetails.getCircle().getCircleManagerBattry() != null) {
					_serviceAppointment.setCircleManager(siteDetails.getCircle().getCircleManagerBattry());
					circleManagerEmailId = siteDetails.getCircle().getCircleManagerBattry().getEmailId();
				}
			}
		}

		if (caseDetails.getBusinessDomain().getBusinessDomainId()==2) {	// DCT
			if (siteDetails.getCircle() != null) {
				if (siteDetails.getCircle().getCircleManagerPower() != null) {
					_serviceAppointment.setCircleManager(siteDetails.getCircle().getCircleManagerPower());
					circleManagerEmailId = siteDetails.getCircle().getCircleManagerPower().getEmailId();
				}
			}
		}
		if (caseDetails.getBusinessDomain().getBusinessDomainId()==3) {	// EV
			if (siteDetails.getCircle() != null) {
				if (siteDetails.getCircle().getCircleManagerEv() != null) {
					_serviceAppointment.setCircleManager(siteDetails.getCircle().getCircleManagerEv());
					circleManagerEmailId = siteDetails.getCircle().getCircleManagerEv().getEmailId();
				}
			}
		}

		_serviceAppointment.setStatus(new com.pratikshat.exicom.entity.ServiceAppointmentStatus(3));	// dispatch
		_serviceAppointment.setPendingResponsibility(new PendingResponsibility(1));
		
		String name = null;
		if(caseCreatedBy.getFirstName()!=null && !caseCreatedBy.getFirstName().equalsIgnoreCase("")) {
			name = caseCreatedBy.getFirstName();
		}
		
		if(caseCreatedBy.getMiddleName()!=null && !caseCreatedBy.getMiddleName().equalsIgnoreCase("")) {
			name = name +" "+  caseCreatedBy.getMiddleName();
		}
		
		if(caseCreatedBy.getLastName()!=null && !caseCreatedBy.getLastName().equalsIgnoreCase("")) {
			name = name +" "+  caseCreatedBy.getLastName();
		}
		_serviceAppointment.setCustomerRepresentativeName(name);
		
		if (caseCreatedBy.getContact() != null) {
			_serviceAppointment.setCustomerRepresentativeContact(caseCreatedBy.getContact().getMobile());
		}
		
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
		_serviceAppointment.setBusinessDomain(caseDetails.getBusinessDomain());
		_serviceAppointment.setClaimStatus(new ClaimStatus(2L)); // To be Claimed
		_serviceAppointment.setApprovedByFinance(false);
		
		CaseStageDetails caseStageDetails = null;
		try {
			Query caseStageDetailsQuery = entityManager.createQuery(
					"select csd from CaseStageDetails csd where csd.id=?1",
					CaseStageDetails.class);
			caseStageDetailsQuery.setParameter(1, caseDetails.getStage().getId());
			caseStageDetails = (CaseStageDetails) caseStageDetailsQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Case Stage does not exist");
		}
		
		
		if (caseDetails.isAssignByAssignmentRule()) {

			if (caseStageDetails.getCaseStageName().equalsIgnoreCase("Assigned To Engineer")) {
				ServiceRecource serviceRecource = null;
				try {
					Query serviceAppointmentOwnerQuery = entityManager.createQuery(
							"select sr from CaseDetails cd left join cd.site st left join st.serviceResource sr left join sr.user ur where cd.caseId=?1",
							ServiceRecource.class);
					serviceAppointmentOwnerQuery.setParameter(1,
							caseDetails.getCaseId());
					serviceRecource = (ServiceRecource) serviceAppointmentOwnerQuery.getSingleResult();
				} catch (NoResultException ex) {

				}

				if (serviceRecource != null) {
					_serviceAppointment.setOwnerDetails(serviceRecource.getUser());
					_serviceAppointment.setServiceResource(serviceRecource);
				} else {
					try {
						Query schQuery = entityManager.createQuery(
								"select sr from ServiceRecource sr left join sr.user ur where ur.emailId=?1",
								ServiceRecource.class);
						schQuery.setParameter(1, circleManagerEmailId);
						ServiceRecource sch = (ServiceRecource) schQuery.getSingleResult();

						_serviceAppointment.setOwnerDetails(sch.getUser());
						_serviceAppointment.setServiceResource(sch);

					} catch (NoResultException ex) {
						throw new com.pratikshat.exicom.exception.BadRequestException(
								"SCH deos not exist");
					}
				}

			} else if (caseStageDetails.getCaseStageName().equalsIgnoreCase("Assigned To Dispatcher")) {

				try {
					Query schQuery = entityManager.createQuery(
							"select sr from ServiceRecource sr left join sr.user ur where ur.emailId=?1",
							ServiceRecource.class);
					schQuery.setParameter(1, circleManagerEmailId);
					ServiceRecource sch = (ServiceRecource) schQuery.getSingleResult();

					_serviceAppointment.setOwnerDetails(sch.getUser());
					_serviceAppointment.setServiceResource(sch);

				} catch (NoResultException ex) {
					throw new com.pratikshat.exicom.exception.BadRequestException("SCH deos not exist");
				}
			}else {
				try {
					Query schQuery = entityManager.createQuery(
							"select sr from ServiceRecource sr left join sr.user ur where ur.emailId=?1",
							ServiceRecource.class);
					schQuery.setParameter(1, circleManagerEmailId);
					ServiceRecource sch = (ServiceRecource) schQuery.getSingleResult();

					_serviceAppointment.setOwnerDetails(sch.getUser());
					_serviceAppointment.setServiceResource(sch);

				} catch (NoResultException ex) {
					throw new com.pratikshat.exicom.exception.BadRequestException("SCH deos not exist");
				}
			}
		} else {

			if (caseStageDetails.getCaseStageName().equalsIgnoreCase("Assigned To Engineer")) {
				ServiceRecource serviceRecource = null;
				if(caseDetails.getEngineer()!=null) {
					try {
						Query serviceAppointmentOwnerQuery = entityManager.createQuery(
								"select sr from ServiceRecource sr left join sr.user ur where sr.serviceResourceId=?1",
								ServiceRecource.class);
						serviceAppointmentOwnerQuery.setParameter(1, caseDetails.getEngineer().getServiceResourceId());
						serviceRecource = (ServiceRecource) serviceAppointmentOwnerQuery.getSingleResult();
					} catch (NoResultException ex) {

					}
				}
				

				if (serviceRecource != null) {
					_serviceAppointment.setOwnerDetails(serviceRecource.getUser());
					_serviceAppointment.setServiceResource(serviceRecource);
				} else {
					try {
						Query schQuery = entityManager.createQuery(
								"select sr from ServiceRecource sr left join sr.user ur where ur.emailId=?1",
								ServiceRecource.class);
						schQuery.setParameter(1, circleManagerEmailId);
						ServiceRecource sch = (ServiceRecource) schQuery.getSingleResult();

						_serviceAppointment.setOwnerDetails(sch.getUser());
						_serviceAppointment.setServiceResource(sch);

					} catch (NoResultException ex) {
						throw new com.pratikshat.exicom.exception.BadRequestException(
								"SCH deos not exist");
					}
				}

			} else if (caseStageDetails.getCaseStageName().equalsIgnoreCase("Assigned To Dispatcher")) {

				try {
					Query schQuery = entityManager.createQuery(
							"select sr from ServiceRecource sr left join sr.user ur where ur.emailId=?1",
							ServiceRecource.class);
					schQuery.setParameter(1, circleManagerEmailId);
					ServiceRecource sch = (ServiceRecource) schQuery.getSingleResult();

					_serviceAppointment.setOwnerDetails(sch.getUser());
					_serviceAppointment.setServiceResource(sch);

				} catch (NoResultException ex) {
					throw new com.pratikshat.exicom.exception.BadRequestException("SCH deos not exist");
				}
			}else {
				try {
					Query schQuery = entityManager.createQuery(
							"select sr from ServiceRecource sr left join sr.user ur where ur.emailId=?1",
							ServiceRecource.class);
					schQuery.setParameter(1, circleManagerEmailId);
					ServiceRecource sch = (ServiceRecource) schQuery.getSingleResult();

					_serviceAppointment.setOwnerDetails(sch.getUser());
					_serviceAppointment.setServiceResource(sch);

				} catch (NoResultException ex) {
					throw new com.pratikshat.exicom.exception.BadRequestException("SCH deos not exist");
				}
			}
		}
		
		return _serviceAppointment;
	}


public List<ServiceAppointment> updateServiceAppointment(long caseId, String userEmailId, EntityManager entityManager)throws BadRequestException {
	
	CaseDetails caseDetails = null;
	try {
		Query selectCaseDetailsQuery = entityManager.createQuery(
				"select ca from CaseDetails ca left join ca.status cs left join ca.caseType ct left join ca.stage cst left join ca.caseReason crs left join ca.businessDomain bd left join ca.assets ast left join ast.account acn left join ca.site st left join st.circle cl left join ca.ownerDetails onr left join ca.createdBy cby left join ca.engineer eng left join eng.contact econ left join ca.serviceCenter scr where ca.caseId=?1",
				CaseDetails.class);
		selectCaseDetailsQuery.setParameter(1, caseId);
		caseDetails = (CaseDetails) selectCaseDetailsQuery.getSingleResult();
	} catch (NoResultException ex) {
		throw new com.pratikshat.exicom.exception.BadRequestException("Case does not exist");
	}
	
	List<ServiceAppointment> serviceAppointmentList= null;
	try {
		serviceAppointmentList = entityManager.createQuery(
				"select sa from ServiceAppointment sa join sa.status st join sa.caseDetails cd where cd.caseId=:caseId",
				com.pratikshat.exicom.entity.ServiceAppointment.class)
				.setParameter("caseId", caseDetails.getCaseId()).getResultList();
	} catch (NoResultException ex) {
		
	}
	
	com.pratikshat.exicom.entity.SiteDetails siteDetails = null;
	
	try {
		Query siteDetailsQuery = entityManager.createQuery(
				"select st from SiteDetails st  left join st.circle cr left join cr.circleManagerBattry cb left join cr.circleManagerPower cp left join cr.circleManagerEv ev where st.siteId=?1",
				com.pratikshat.exicom.entity.SiteDetails.class);
		siteDetailsQuery.setParameter(1, caseDetails.getSite().getSiteId());
		siteDetails = (com.pratikshat.exicom.entity.SiteDetails) siteDetailsQuery.getSingleResult();
	} catch (NoResultException ex) {
		throw new com.pratikshat.exicom.exception.BadRequestException("Site does not exist");
	}
	
	com.pratikshat.exicom.entity.EmployeeDetails caseCreatedBy = null;
	try {
		Query getUserDetailsQuery = entityManager.createQuery(
				"select emp from EmployeeDetails emp left join ContactDetails ct on emp.emailId=ct.emailId where emp.emailId=?1");
		getUserDetailsQuery.setParameter(1, caseDetails.getCreatedBy().getEmailId());
		caseCreatedBy = (com.pratikshat.exicom.entity.EmployeeDetails) getUserDetailsQuery
				.getSingleResult();
	} catch (NoResultException ex) {
		throw new com.pratikshat.exicom.exception.BadRequestException("Created by does not exist");
	}
	
	Assets assets = null;
	if(caseDetails.getAssets()!=null) {
		try {
			Query assetsQuery = entityManager.createQuery("select ast from Assets ast left join ast.product prd where ast.serialNumber=?1",Assets.class);
			assetsQuery.setParameter(1, caseDetails.getAssets().getSerialNumber());
			assets = (Assets) assetsQuery.getSingleResult();
		}catch(NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Asset does not exist");
		}
	}
	
	List<Long> saStatusList = new ArrayList<>();
	saStatusList.add(1L);	// Unassigned
	saStatusList.add(2L);	// Scheduled
	saStatusList.add(3L);	// Dispatched
	
	if(serviceAppointmentList!=null) {
		for(int i=0; i<serviceAppointmentList.size(); i++) {
			ServiceAppointment _serviceAppointment = serviceAppointmentList.get(i);
			if(saStatusList.contains(_serviceAppointment.getStatus().getStatusId())) {
				
				_serviceAppointment.setModifiedBy(new EmployeeDetails(userEmailId));
				_serviceAppointment.setModifiedDate(new java.util.Date());

				_serviceAppointment.setCircle(siteDetails.getCircle());
				if(caseDetails.getAssets()!=null) {
					_serviceAppointment.setAssets(assets);
					_serviceAppointment.setProduct(assets.getProduct());	
				}
				
				if(caseDetails.getCaseReason()!=null) {
					try {
						Query caseReasonQuery = entityManager.createQuery("select cr from CaseReasonDetails cr where cr.reasonId=?1",CaseReasonDetails.class);
						caseReasonQuery.setParameter(1, caseDetails.getCaseReason().getReasonId());
						CaseReasonDetails caseReason = (CaseReasonDetails) caseReasonQuery.getSingleResult();
						
						_serviceAppointment.setCostCenter(caseReason.getCostCenter());
					}catch(NoResultException ex) {
						throw new com.pratikshat.exicom.exception.BadRequestException("Case Reason does not exist");
					}catch (Exception e) {
						throw new com.pratikshat.exicom.exception.BadRequestException(e.getMessage());
					}
				}
				
				String circleManagerEmailId = null;
				if (caseDetails.getBusinessDomain().getBusinessDomainId()==1) {	// Battery
					if (siteDetails.getCircle() != null) {
						if (siteDetails.getCircle().getCircleManagerBattry() != null) {
							_serviceAppointment.setCircleManager(siteDetails.getCircle().getCircleManagerBattry());
							circleManagerEmailId = siteDetails.getCircle().getCircleManagerBattry().getEmailId();
						}
					}
				}

				if (caseDetails.getBusinessDomain().getBusinessDomainId()==2) {	// DCT
					if (siteDetails.getCircle() != null) {
						if (siteDetails.getCircle().getCircleManagerPower() != null) {
							_serviceAppointment.setCircleManager(siteDetails.getCircle().getCircleManagerPower());
							circleManagerEmailId = siteDetails.getCircle().getCircleManagerPower().getEmailId();
						}
					}
				}
				if (caseDetails.getBusinessDomain().getBusinessDomainId()==3) {	// EV
					if (siteDetails.getCircle() != null) {
						if (siteDetails.getCircle().getCircleManagerEv() != null) {
							_serviceAppointment.setCircleManager(siteDetails.getCircle().getCircleManagerEv());
							circleManagerEmailId = siteDetails.getCircle().getCircleManagerEv().getEmailId();
						}
					}
				}
				
				CaseStageDetails caseStageDetails = null;
				try {
					Query caseStageDetailsQuery = entityManager.createQuery(
							"select csd from CaseStageDetails csd where csd.id=?1",
							CaseStageDetails.class);
					caseStageDetailsQuery.setParameter(1, caseDetails.getStage().getId());
					caseStageDetails = (CaseStageDetails) caseStageDetailsQuery.getSingleResult();
				} catch (NoResultException ex) {
					throw new com.pratikshat.exicom.exception.BadRequestException("Case Stage does not exist");
				}
				
				
				if (caseDetails.isAssignByAssignmentRule()) {

					if (caseStageDetails.getCaseStageName().equalsIgnoreCase("Assigned To Engineer")) {
						ServiceRecource serviceRecource = null;
						try {
							Query serviceAppointmentOwnerQuery = entityManager.createQuery(
									"select sr from CaseDetails cd left join cd.site st left join st.serviceResource sr left join sr.user ur where cd.caseId=?1",
									ServiceRecource.class);
							serviceAppointmentOwnerQuery.setParameter(1,
									caseDetails.getCaseId());
							serviceRecource = (ServiceRecource) serviceAppointmentOwnerQuery.getSingleResult();
						} catch (NoResultException ex) {

						}

						if (serviceRecource != null) {
							_serviceAppointment.setOwnerDetails(serviceRecource.getUser());
							_serviceAppointment.setServiceResource(serviceRecource);
						} else {
							try {
								Query schQuery = entityManager.createQuery(
										"select sr from ServiceRecource sr left join sr.user ur where ur.emailId=?1",
										ServiceRecource.class);
								schQuery.setParameter(1, circleManagerEmailId);
								ServiceRecource sch = (ServiceRecource) schQuery.getSingleResult();

								_serviceAppointment.setOwnerDetails(sch.getUser());
								_serviceAppointment.setServiceResource(sch);

							} catch (NoResultException ex) {
								throw new com.pratikshat.exicom.exception.BadRequestException(
										"SCH deos not exist");
							}
						}

					} else if (caseStageDetails.getCaseStageName().equalsIgnoreCase("Assigned To Dispatcher")) {

						try {
							Query schQuery = entityManager.createQuery(
									"select sr from ServiceRecource sr left join sr.user ur where ur.emailId=?1",
									ServiceRecource.class);
							schQuery.setParameter(1, circleManagerEmailId);
							ServiceRecource sch = (ServiceRecource) schQuery.getSingleResult();

							_serviceAppointment.setOwnerDetails(sch.getUser());
							_serviceAppointment.setServiceResource(sch);

						} catch (NoResultException ex) {
							throw new com.pratikshat.exicom.exception.BadRequestException("SCH deos not exist");
						}
					}else {
						try {
							Query schQuery = entityManager.createQuery(
									"select sr from ServiceRecource sr left join sr.user ur where ur.emailId=?1",
									ServiceRecource.class);
							schQuery.setParameter(1, circleManagerEmailId);
							ServiceRecource sch = (ServiceRecource) schQuery.getSingleResult();

							_serviceAppointment.setOwnerDetails(sch.getUser());
							_serviceAppointment.setServiceResource(sch);

						} catch (NoResultException ex) {
							throw new com.pratikshat.exicom.exception.BadRequestException("SCH deos not exist");
						}
					}
				} else {

					if (caseStageDetails.getCaseStageName().equalsIgnoreCase("Assigned To Engineer")) {
						ServiceRecource serviceRecource = null;
						try {
							Query serviceAppointmentOwnerQuery = entityManager.createQuery(
									"select sr from ServiceRecource sr left join sr.user ur where sr.serviceResourceId=?1",
									ServiceRecource.class);
							serviceAppointmentOwnerQuery.setParameter(1, caseDetails.getEngineer().getServiceResourceId());
							serviceRecource = (ServiceRecource) serviceAppointmentOwnerQuery.getSingleResult();
						} catch (NoResultException ex) {

						}

						if (serviceRecource != null) {
							_serviceAppointment.setOwnerDetails(serviceRecource.getUser());
							_serviceAppointment.setServiceResource(serviceRecource);
						} else {
							try {
								Query schQuery = entityManager.createQuery(
										"select sr from ServiceRecource sr left join sr.user ur where ur.emailId=?1",
										ServiceRecource.class);
								schQuery.setParameter(1, circleManagerEmailId);
								ServiceRecource sch = (ServiceRecource) schQuery.getSingleResult();

								_serviceAppointment.setOwnerDetails(sch.getUser());
								_serviceAppointment.setServiceResource(sch);

							} catch (NoResultException ex) {
								throw new com.pratikshat.exicom.exception.BadRequestException(
										"SCH deos not exist");
							}
						}

					} else if (caseStageDetails.getCaseStageName().equalsIgnoreCase("Assigned To Dispatcher")) {

						try {
							Query schQuery = entityManager.createQuery(
									"select sr from ServiceRecource sr left join sr.user ur where ur.emailId=?1",
									ServiceRecource.class);
							schQuery.setParameter(1, circleManagerEmailId);
							ServiceRecource sch = (ServiceRecource) schQuery.getSingleResult();

							_serviceAppointment.setOwnerDetails(sch.getUser());
							_serviceAppointment.setServiceResource(sch);

						} catch (NoResultException ex) {
							throw new com.pratikshat.exicom.exception.BadRequestException("SCH deos not exist");
						}
					}else {
						try {
							Query schQuery = entityManager.createQuery(
									"select sr from ServiceRecource sr left join sr.user ur where ur.emailId=?1",
									ServiceRecource.class);
							schQuery.setParameter(1, circleManagerEmailId);
							ServiceRecource sch = (ServiceRecource) schQuery.getSingleResult();

							_serviceAppointment.setOwnerDetails(sch.getUser());
							_serviceAppointment.setServiceResource(sch);

						} catch (NoResultException ex) {
							throw new com.pratikshat.exicom.exception.BadRequestException("SCH deos not exist");
						}
					}
				}
				
			}else {
				if(caseDetails.getCaseReason()!=null) {
					try {
						Query caseReasonQuery = entityManager.createQuery("select cr from CaseReasonDetails cr where cr.reasonId=?1",CaseReasonDetails.class);
						caseReasonQuery.setParameter(1, caseDetails.getCaseReason().getReasonId());
						CaseReasonDetails caseReason = (CaseReasonDetails) caseReasonQuery.getSingleResult();
						
						_serviceAppointment.setCostCenter(caseReason.getCostCenter());
					}catch(NoResultException ex) {
						throw new com.pratikshat.exicom.exception.BadRequestException("Case Reason does not exist");
					}catch (Exception e) {
						throw new com.pratikshat.exicom.exception.BadRequestException(e.getMessage());
					}
				}
				
				_serviceAppointment.setModifiedBy(new EmployeeDetails(userEmailId));
				_serviceAppointment.setModifiedDate(new java.util.Date());
				
			}
		}
	}
	return serviceAppointmentList;
}

}
