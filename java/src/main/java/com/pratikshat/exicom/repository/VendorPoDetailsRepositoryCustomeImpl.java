package com.pratikshat.exicom.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.controller.ItemToBeApprovedController;
import com.pratikshat.exicom.entity.CaseDetails;
import com.pratikshat.exicom.entity.ContactDetails;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.GlobalSearchIndex;
import com.pratikshat.exicom.entity.InternalServiceCode;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.ToBeApproved;
import com.pratikshat.exicom.entity.UploadedFiles;
import com.pratikshat.exicom.entity.VendorInvoiceDetails;
import com.pratikshat.exicom.entity.VendorInvoiceGrn;
import com.pratikshat.exicom.entity.VendorInvoiceSap;
import com.pratikshat.exicom.entity.VendorInvoiceStatus;
import com.pratikshat.exicom.entity.VendorPoDetails;
import com.pratikshat.exicom.entity.WccDetails;
import com.pratikshat.exicom.entity.WccServiceAppointment;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.VendorInvoiceCommercialRequest;
import com.pratikshat.exicom.model.request.VendorInvoiceRequest;
import com.pratikshat.exicom.model.request.VendorInvoiceSapRequest;
import com.pratikshat.exicom.model.response.CircleDetailsResponse;
import com.pratikshat.exicom.services.FileStorageService;

public class VendorPoDetailsRepositoryCustomeImpl implements VendorPoDetailsRepositoryCustome {
	@PersistenceContext
	EntityManager em;
	
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	@Autowired
	com.pratikshat.exicom.interceptor.WorkflowExecuter workflow;
	
	@Autowired
	UserProfileRepository dbProfile;
	
	@Autowired
    private FileStorageService fileStorageService;
	
	@Autowired 
	JdbcTemplate jdbc;
	
	@Override
	@Transactional(rollbackFor= {BadRequestException.class,UnauthoriseException.class,Exception.class,RuntimeException.class},propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED)
	public void saveVendorInvoice(VendorInvoiceRequest req, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		//BASIC INFO
		EmployeeDetails emp=new EmployeeDetails(user.getUsername());
		Date today=new Date();
		
		//VALIDATION
		if(req.getRemarks()!=null&&req.getRemarks().length()>500) throw new BadRequestException("Remarks lenght can not be greater then 500");
		WccDetails _wcc=null;
		try {
			_wcc=em.createQuery("select wcc from WccDetails wcc where wcc=?1",WccDetails.class).setParameter(1,new WccDetails(req.getWccId())).getSingleResult();
		}catch(NoResultException ex) {
			throw new BadRequestException("Wcc Details not found");
		}catch(NonUniqueResultException ex) {
			throw new BadRequestException("Multiple Record found");
		}
		if(!_wcc.getOwner().getEmailId().equalsIgnoreCase(user.getUsername())) throw new UnauthoriseException("You are not authorised");
		
		if(_wcc.getWccStatus().getId()!=2) {
			throw new BadRequestException(String.format("Invalid request %s is not approved",_wcc.getManualId()));
		}
		
		if(_wcc.isInvoiced()) throw new BadRequestException("Invoice already genreated for this Wcc");
		
		ContactDetails _vendorContact=null;
		try {
			_vendorContact=em.createQuery("select ct from ContactDetails ct join ct.account act join act.accountType actt where ct.emailId=?1",ContactDetails.class).setParameter(1, user.getUsername()).getSingleResult();
		}catch(NoResultException ex) {
			throw new UnauthoriseException("You are not authorised");
		}
		catch(NonUniqueResultException ex) {
			throw new UnauthoriseException("You are not authorised");
		}
		
		if(_vendorContact.getAccount().getAccountType()==null||_vendorContact.getAccount().getAccountType().getAccountTypeId()!=1) {
			
			throw new UnauthoriseException("Vendor can only genreate Invoice");
		}
		
		
		List<WccServiceAppointment> lstService=new ArrayList<WccServiceAppointment>();
		lstService=em.createQuery("select ws from WccServiceAppointment ws join ws.serviceAppointment sr join ws.poDetails po join sr.serviceResource srr join srr.contact ct join ct.legalEntity lg where ws.wcc=?1",WccServiceAppointment.class).setParameter(1, _wcc).getResultList();
		float totalAmount=0;
		int totalNumber=0;
		VendorPoDetails po=null;
		if(lstService!=null&&lstService.size()>0) {
			totalNumber=lstService.size();
			for(WccServiceAppointment ap:lstService) {
				//totalAmount+=(float)ap.getServiceAppointment().getServiceResource().getContact().getLegalEntity().getLegalEntityFixedPayout();
				totalAmount+=(float)ap.getPoDetails().getActivityPrice();
				po=ap.getPoDetails();
			}
		}
		VendorInvoiceDetails _invoice=new VendorInvoiceDetails();
		_invoice.setCreatedBy(emp);
		_invoice.setCreatedDate(today);
		_invoice.setInvoiceAmount(_wcc.getWccAmount());
		_invoice.setInvoiceStatus(new VendorInvoiceStatus(6));
		_invoice.setLastModifiedBy(emp);
		_invoice.setLastModifiedDate(today);
		_invoice.setOwner(emp);
		_invoice.setVendorId(_vendorContact.getEmployeeCode());
		_invoice.setVendorInvoiceDate(req.getVendorInvoiceDate());
		_invoice.setVendorInvoiceNumber(req.getVendorInvoiceNumber());
		_invoice.setVendorName(_vendorContact.getAccount().getName());
		_invoice.setVendorPo(po);
		_invoice.setWccDetails(_wcc);
		_invoice.setPoDate(po.getPoDate());
		_invoice.setRemarks(req.getRemarks());
		_invoice.setInvoiceTaxAmount(_wcc.getWccTaxAmount());
		_invoice.setInvoiceTotalAmount(_wcc.getWccTotalAmount());
		
		workflow.executeWorkflow(em, _invoice, "INSERT", "PRE", user);
		em.persist(_invoice);
		_wcc.setInvoiced(true);
		em.persist(_wcc);
		String manualId="INV-"+MiscellaneousUtils.fillZero(""+_invoice.getId(), 5);
		_invoice.setManualId(manualId);
		em.persist(_invoice);
		workflow.executeWorkflow(em, _invoice, "INSERT", "POST", user);
		
		//UPDATE INVOICE DETAILS IIN CASE
		
				List<CaseDetails> listCaseDetails=null;
				try {
					listCaseDetails=em.createQuery("select csd from WccServiceAppointment wca join wca.serviceAppointment sa join sa.caseDetails csd where wca.wcc=?1",CaseDetails.class)
							.setParameter(1, _invoice.getWccDetails())
							.getResultList();
				}catch(Exception ex) {
					throw new BadRequestException("Unable to get case details");
				}
				for(CaseDetails cds:listCaseDetails) {
					cds.setVendorInvoiceNumber(_invoice.getVendorInvoiceNumber());
					cds.setVendorInvoiceDate(_invoice.getVendorInvoiceDate());
					cds.setModifiedBy(new EmployeeDetails(user.getUsername()));
					cds.setModifiedDate(new Date());
					em.persist(cds);
				}
				//UPDATE INVOICE END
		
		req.setId(_invoice.getId());
		req.setManualId(manualId);
		try {
			GlobalSearchIndex id=new GlobalSearchIndex();
			id.setKeyName("manualId");
			id.setObjectDetailsUrl("invoice/"+_invoice.getId());
			id.setObjectIdName("id");
			id.setObjectIdValue(""+_invoice.getId());
			id.setObjectName("VendorInvoiceDetails");
			id.setSearchString(manualId);
			em.persist(id);
		}catch(Exception ex) {}
		
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

	@Override
	@Transactional(rollbackFor= {BadRequestException.class,UnauthoriseException.class,Exception.class,RuntimeException.class},propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED)
	public void updateGrnDetails(VendorInvoiceCommercialRequest req, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		EmployeeDetails emp=new EmployeeDetails(user.getUsername());
		Date today=new Date();
			VendorInvoiceDetails _invoice=null;
			try {
				_invoice=em.createQuery("select inv from VendorInvoiceDetails inv join inv.invoiceStatus st where inv=?1",VendorInvoiceDetails.class).setParameter(1, new VendorInvoiceDetails(req.getVendorInvoiceId())).getSingleResult();
			}catch(NoResultException ex) {
				throw new BadRequestException("Invoice not found");
			}
			catch(NonUniqueResultException ex) {
				throw new BadRequestException("Invalid invoice id");
			}
			
			List<ToBeApproved> itemApp=new ArrayList<ToBeApproved>();
			try {
				itemApp=em.createQuery("select item from ToBeApproved item where item.ObjectName=?1 and objectId=?2 and stage=?3",ToBeApproved.class)
						.setParameter(1,"VendorInvoiceDetails")
						.setParameter(2,req.getVendorInvoiceId())
						.setParameter(3, 1).getResultList();
			}
			catch(Exception ex) {
				throw new UnauthoriseException("You are not authorised");
			}
			boolean found=false;
			for(ToBeApproved t:itemApp) {
				if(t.getAssignedTo().getEmailId().equalsIgnoreCase(user.getUsername())) {
					//throw new UnauthoriseException("You are not authorised");
					found=true;
				}
				else {
					if(dbProfile.samroleUsers(t.getAssignedTo().getEmailId(), user.getUsername())) {
						found=true;
					}
					else {
						Map<String,String> uRlrl=dbProfile.getAccountAndRoleGroup(user);
						Map<String,String> arl=dbProfile.getAccountAndRoleGroup(new LoginUser(t.getAssignedTo().getEmailId()));
						if(uRlrl.get("roleGroup").toString().equalsIgnoreCase(arl.get("roleGroup").toString()))
							found=true;
					}
				}
			}
			if(!found) {
				throw new UnauthoriseException("You are not authorised");
			}
//			change here status by shagufta for new commercial executive status
			if(_invoice.getInvoiceStatus().getId()==1||_invoice.getInvoiceStatus().getId()==9) {
				SimpleDateFormat frm=new SimpleDateFormat("yyyy-MM-dd");
				
				VendorInvoiceGrn grn=null;
				
				try {
					grn=em.createQuery("select grn from VendorInvoiceGrn grn where grn=?1",VendorInvoiceGrn.class).setParameter(1, new VendorInvoiceGrn(_invoice)).getSingleResult();
				}catch(NoResultException ex) {
					grn=new VendorInvoiceGrn();
					grn.setInvoice(_invoice);
					grn.setCreatedBy(emp);
					grn.setCreatedDate(today);
				}
				catch(NonUniqueResultException ex) {
					throw new BadRequestException("Multiple GRN entry found");
				}
				grn.setEntrySheetNumber(req.getEntrySheetNumber());
				grn.setGrnDate(frm.format(req.getGrnDate()));
				grn.setGrnNumber(req.getGrnNumber());
				grn.setInvoice(_invoice);
				grn.setLastModifiedBy(emp);
				grn.setLastModifiedDate(today);
				grn.setOwner(emp);
				grn.setInternalServiceCode(new InternalServiceCode(req.getInternalServiceCode()));
				em.persist(grn);
			}
			else {
				throw new UnauthoriseException("You are not authorise");
			}
	}

	@Override
	public void udpateVendorInvoice(VendorInvoiceRequest req, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		VendorInvoiceDetails _invoice=null;
		try {
			_invoice=em.createQuery("select inv from VendorInvoiceDetails inv join inv.vendorPo vp join inv.wccDetails wcc where inv=?1",VendorInvoiceDetails.class)
					.setParameter(1, new VendorInvoiceDetails(req.getId()))
					.getSingleResult();
		}catch(NoResultException ex) {
			throw new BadRequestException("Invoice details not found");
		}
		catch(NonUniqueResultException ex) {
			throw new BadRequestException("Invalid Id");
		}
		if(_invoice.getInvoiceStatus().getId()==3||_invoice.getInvoiceStatus().getId()==5) {
			
		}
		else {
			throw new UnauthoriseException("You are not authorised");
		}
		if(!_invoice.getOwner().getEmailId().equalsIgnoreCase(user.getUsername())) {
			throw new UnauthoriseException("You are not authorised");
		}
		EmployeeDetails emp=new EmployeeDetails(user.getUsername());
		Date today=new Date();
		
		//VALIDATION
		if(req.getRemarks()!=null&&req.getRemarks().length()>500) throw new BadRequestException("Remarks lenght can not be greater then 500");
		WccDetails _wcc=null;
		try {
			_wcc=em.createQuery("select wcc from WccDetails wcc where wcc=?1",WccDetails.class).setParameter(1,new WccDetails(req.getWccId())).getSingleResult();
		}catch(NoResultException ex) {
			throw new BadRequestException("Wcc Details not found");
		}catch(NonUniqueResultException ex) {
			throw new BadRequestException("Multiple Record found");
		}
		if(!_wcc.getOwner().getEmailId().equalsIgnoreCase(user.getUsername())) throw new UnauthoriseException("You are not authorised");
		if(_wcc.isInvoiced()) throw new BadRequestException("Invoice already genreated for this Wcc");
		if(_wcc.getWccStatus().getId()!=2)
			throw new BadRequestException(String.format("WCC %s not approved",_wcc.getManualId()));
		
		ContactDetails _vendorContact=null;
		try {
			_vendorContact=em.createQuery("select ct from ContactDetails ct join ct.account act join act.accountType actt where ct.emailId=?1",ContactDetails.class).setParameter(1, user.getUsername()).getSingleResult();
		}catch(NoResultException ex) {
			throw new UnauthoriseException("You are not authorised");
		}
		catch(NonUniqueResultException ex) {
			throw new UnauthoriseException("You are not authorised");
		}
		
		if(_vendorContact.getAccount().getAccountType()==null||_vendorContact.getAccount().getAccountType().getAccountTypeId()!=1) {
			
			throw new UnauthoriseException("Vendor can only genreate Invoice");
		}
		
		
		List<WccServiceAppointment> lstService=new ArrayList<WccServiceAppointment>();
		lstService=em.createQuery("select ws from WccServiceAppointment ws join ws.serviceAppointment sr join ws.poDetails po join sr.serviceResource srr join srr.contact ct join ct.legalEntity lg where ws.wcc=?1",WccServiceAppointment.class).setParameter(1, _wcc).getResultList();
	//	float totalAmount=0;
	//	int totalNumber=0;
		VendorPoDetails po=null;
		if(lstService!=null&&lstService.size()>0) {
			//totalNumber=lstService.size();
			for(WccServiceAppointment ap:lstService) {
				//totalAmount+=(float)ap.getServiceAppointment().getServiceResource().getContact().getLegalEntity().getLegalEntityFixedPayout();
				//totalAmount+=(float)ap.getPoDetails().getActivityPrice();
				po=ap.getPoDetails();
			}
		}
		_invoice.setCreatedBy(emp);
		_invoice.setCreatedDate(today);
		_invoice.setInvoiceAmount(_wcc.getWccAmount());
		_invoice.setInvoiceStatus(new VendorInvoiceStatus(1));
		_invoice.setLastModifiedBy(emp);
		_invoice.setLastModifiedDate(today);
		_invoice.setOwner(emp);
		_invoice.setVendorId(_vendorContact.getEmployeeCode());
		_invoice.setVendorInvoiceDate(req.getVendorInvoiceDate());
		_invoice.setVendorInvoiceNumber(req.getVendorInvoiceNumber());
		_invoice.setVendorName(_vendorContact.getAccount().getName());
		_invoice.setVendorPo(po);
		_invoice.setWccDetails(_wcc);
		_invoice.setPoDate(po.getPoDate());
		_invoice.setRemarks(req.getRemarks());
		_invoice.setInvoiceStatus(new VendorInvoiceStatus(6));
		_invoice.setInvoiceTaxAmount(_wcc.getWccTaxAmount());
		_invoice.setInvoiceTotalAmount(_wcc.getWccTotalAmount());
				
//		WccDetails wcc=_invoice.getWccDetails();
//		
//		if(wcc.getWccAmount()!=_invoice.getInvoiceAmount()) {
//			_invoice.setInvoiceAmount(wcc.getWccAmount());
//			_invoice.setInvoiceTaxAmount(wcc.getWccTaxAmount());
//			_invoice.setInvoiceTotalAmount(wcc.getWccTotalAmount());
//		}
		
		workflow.executeWorkflow(em, _invoice, "UPDATE", "PRE", user);
		em.persist(_invoice);
		_wcc.setInvoiced(true);
		em.persist(_wcc);
		em.persist(_invoice);
		workflow.executeWorkflow(em, _invoice, "UPDATE", "POST", user);
		
		//UPDATE INVOICE DETAILS IIN CASE
		
				List<CaseDetails> listCaseDetails=null;
				try {
					listCaseDetails=em.createQuery("select csd from WccServiceAppointment wca join wca.serviceAppointment sa join sa.caseDetails csd where wca.wcc=?1",CaseDetails.class)
							.setParameter(1, _invoice.getWccDetails())
							.getResultList();
				}catch(Exception ex) {
					throw new BadRequestException("Unable to get case details");
				}
				for(CaseDetails cds:listCaseDetails) {
					cds.setVendorInvoiceNumber(_invoice.getVendorInvoiceNumber());
					cds.setVendorInvoiceDate(_invoice.getVendorInvoiceDate());
					cds.setModifiedBy(new EmployeeDetails(user.getUsername()));
					cds.setModifiedDate(new Date());
					em.persist(cds);
				}
				//UPDATE INVOICE END
		
		req.setId(_invoice.getId());
		
	}

	@Override
	@Transactional(rollbackFor= {BadRequestException.class,UnauthoriseException.class,Exception.class,RuntimeException.class},propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED)
	public void saveVendorInvoiceWithCopy(VendorInvoiceRequest req, LoginUser user, MultipartFile invoice)
			throws BadRequestException, UnauthoriseException {
		//BASIC INFO
				EmployeeDetails emp=new EmployeeDetails(user.getUsername());
				Date today=new Date();
				
				if(invoice.isEmpty())
					throw new BadRequestException("Please provide invoice soft copy");
				
				
				//VALIDATION
				if(req.getRemarks()!=null&&req.getRemarks().length()>500) throw new BadRequestException("Remarks lenght can not be greater then 500");
				WccDetails _wcc=null;
				try {
					_wcc=em.createQuery("select wcc from WccDetails wcc where wcc=?1",WccDetails.class).setParameter(1,new WccDetails(req.getWccId())).getSingleResult();
				}catch(NoResultException ex) {
					throw new BadRequestException("Wcc Details not found");
				}catch(NonUniqueResultException ex) {
					throw new BadRequestException("Multiple Record found");
				}
				if(!_wcc.getOwner().getEmailId().equalsIgnoreCase(user.getUsername())) throw new UnauthoriseException("You are not authorised");
				if(_wcc.isInvoiced()) throw new BadRequestException("Invoice already genreated for this Wcc");
				
				
				ContactDetails _vendorContact=null;
				try {
					_vendorContact=em.createQuery("select ct from ContactDetails ct join ct.account act join act.accountType actt where ct.emailId=?1",ContactDetails.class).setParameter(1, user.getUsername()).getSingleResult();
				}catch(NoResultException ex) {
					throw new UnauthoriseException("You are not authorised");
				}
				catch(NonUniqueResultException ex) {
					throw new UnauthoriseException("You are not authorised");
				}
			
				if(_vendorContact.getAccount().getAccountType()==null||_vendorContact.getAccount().getAccountType().getAccountTypeId()!=1) {
					
					throw new UnauthoriseException("Vendor can only genreate Invoice");
				}
							
				List<WccServiceAppointment> lstService=new ArrayList<WccServiceAppointment>();
				lstService=em.createQuery("select ws from WccServiceAppointment ws join ws.serviceAppointment sr join ws.poDetails po join sr.serviceResource srr join srr.contact ct join ct.legalEntity lg where ws.wcc=?1",WccServiceAppointment.class).setParameter(1, _wcc).getResultList();
				float totalAmount=0;
				int totalNumber=0;
				VendorPoDetails po=new VendorPoDetails();
				if(lstService!=null&&lstService.size()>0) {
					totalNumber=lstService.size();
					for(WccServiceAppointment ap:lstService) {
						//totalAmount+=(float)ap.getServiceAppointment().getServiceResource().getContact().getLegalEntity().getLegalEntityFixedPayout();
						totalAmount+=ap.getPoDetails().getActivityPrice();
						po=ap.getPoDetails();
					}
				}
				VendorInvoiceDetails _invoice=new VendorInvoiceDetails();
				_invoice.setCreatedBy(emp);
				_invoice.setCreatedDate(today);
				_invoice.setInvoiceAmount(_wcc.getWccAmount());
				_invoice.setInvoiceStatus(new VendorInvoiceStatus(6));
				_invoice.setLastModifiedBy(emp);
				_invoice.setLastModifiedDate(today);
				_invoice.setOwner(emp);
				_invoice.setVendorId(_vendorContact.getEmployeeCode());
				_invoice.setVendorInvoiceDate(req.getVendorInvoiceDate());
				_invoice.setVendorInvoiceNumber(req.getVendorInvoiceNumber());
				_invoice.setVendorName(_vendorContact.getAccount().getName());
				_invoice.setVendorPo(po);
				_invoice.setWccDetails(_wcc);
				_invoice.setPoDate(po.getPoDate());
				_invoice.setRemarks(req.getRemarks());
				_invoice.setInvoiceTaxAmount(_wcc.getWccTaxAmount());
				_invoice.setInvoiceTotalAmount(_wcc.getWccTotalAmount());
				
				workflow.executeWorkflow(em, _invoice, "INSERT", "PRE", user);
				em.persist(_invoice);
				
				//file upload process
				if(invoice!=null&&!invoice.isEmpty()) {
					String fileName = fileStorageService.storeFile(invoice);

			        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
			                .path("/api/downloadFile/")
			                .path(fileName)
			                .toUriString();
			        try {
			        	UploadedFiles uploadedFiles = new UploadedFiles();
			            uploadedFiles.setObjectName("VendorInvoiceDetails");
			            uploadedFiles.setObjectId(String.valueOf(_invoice.getId()));
			            uploadedFiles.setFileName(fileName);
			            uploadedFiles.setFileSize(invoice.getSize());
			            uploadedFiles.setFileContentType(invoice.getContentType());
			            uploadedFiles.setFileDownloadUri(fileDownloadUri);
			            uploadedFiles.setCreatedDate(new Date());
			            uploadedFiles.setCreatedBy(new EmployeeDetails(user.getUsername()));
			            em.persist(uploadedFiles);
			        }
			        catch(Exception ex) {}
					_invoice.setInvoiceCopyUrl(fileDownloadUri);
				}
				
				
				//end file upload process
				
				_wcc.setInvoiced(true);
				em.persist(_wcc);
				String manualId="INV-"+MiscellaneousUtils.fillZero(""+_invoice.getId(), 5);
				_invoice.setManualId(manualId);
				em.persist(_invoice);
				workflow.executeWorkflow(em, _invoice, "INSERT", "POST", user);
				
				
				//UPDATE INVOICE DETAILS IIN CASE
				
				List<CaseDetails> listCaseDetails=null;
				try {
					listCaseDetails=em.createQuery("select csd from WccServiceAppointment wca join wca.serviceAppointment sa join sa.caseDetails csd where wca.wcc=?1",CaseDetails.class)
							.setParameter(1, _invoice.getWccDetails())
							.getResultList();
				}catch(Exception ex) {
					throw new BadRequestException("Unable to get case details");
				}
				for(CaseDetails cds:listCaseDetails) {
					cds.setVendorInvoiceNumber(_invoice.getVendorInvoiceNumber());
					cds.setVendorInvoiceDate(_invoice.getVendorInvoiceDate());
					cds.setModifiedBy(new EmployeeDetails(user.getUsername()));
					cds.setModifiedDate(new Date());
					em.persist(cds);
				}
				//UPDATE INVOICE END
			
				req.setId(_invoice.getId());
				req.setManualId(manualId);
				try {
					GlobalSearchIndex id=new GlobalSearchIndex();
					id.setKeyName("manualId");
					id.setObjectDetailsUrl("invoice/"+_invoice.getId());
					id.setObjectIdName("id");
					id.setObjectIdValue(""+_invoice.getId());
					id.setObjectName("VendorInvoiceDetails");
					id.setSearchString(manualId);
					em.persist(id);
				}catch(Exception ex) {}
				
				
				
	}

	@Override
	@Transactional(rollbackFor= {BadRequestException.class,UnauthoriseException.class,Exception.class,RuntimeException.class},propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED)
	public void updateVendorSapDetails(VendorInvoiceSapRequest req, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		VendorInvoiceDetails _invoice=null;
		EmployeeDetails emp=new EmployeeDetails(user.getUsername());
		Date today=new Date();
		try {
			_invoice=em.createQuery("select inv from VendorInvoiceDetails inv where inv=?1",VendorInvoiceDetails.class).setParameter(1, new VendorInvoiceDetails(req.getVendorInvoiceId())).getSingleResult();
		}catch(NoResultException ex) {
			throw new BadRequestException("Invoice not found");
		}
		catch(NonUniqueResultException ex) {
			throw new BadRequestException("Invalid Invoice id");
		}
//		if(_invoice.getInvoiceStatus().getId()==1||_invoice.getInvoiceStatus().getId()==4) {
		if(_invoice.getInvoiceStatus().getId()==7) {

		}
		else {
			throw new UnauthoriseException("You are not authorise");
		}
		VendorInvoiceSap sap=null;
		try {
			sap=em.createQuery("select sap from VendorInvoiceSap sap where sap=?1",VendorInvoiceSap.class).setParameter(1, new VendorInvoiceSap(_invoice)).getSingleResult();
		}catch(NoResultException ex) {
			sap=new VendorInvoiceSap();
			sap.setCreatedBy(emp);
			sap.setCreatedDate(today);
			sap.setInvoice(_invoice);
			sap.setOwner(emp);
		}
		SimpleDateFormat fr=new SimpleDateFormat("yyyy-dd-MM");
		String ddate=fr.format(_invoice.getCreatedDate());
		
		
		try {
			if(req.getSapDate().getTime()<fr.parse(ddate).getTime())
				throw new BadRequestException("SAP posting date can not be less then Invoice created date");
		} catch (ParseException e) {
			throw new BadRequestException(e.getMessage());
		} catch (BadRequestException e) {
			// TODO Auto-generated catch block
			throw e;
		}
		
		sap.setSapDate(req.getSapDate());
		sap.setSapNumber(req.getSapNumber());
		sap.setLastModifiedBy(emp);
		sap.setLastModifiedDate(today);
		em.persist(sap);
	}

	@Override
	@Transactional(rollbackFor= {BadRequestException.class,UnauthoriseException.class,Exception.class,RuntimeException.class},propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED)
	public void updateVendorInvoiceWithCopy(VendorInvoiceRequest req, LoginUser user, MultipartFile invoiceCopy)
			throws BadRequestException, UnauthoriseException {
		VendorInvoiceDetails _invoice=null;
		try {
			_invoice=em.createQuery("select inv from VendorInvoiceDetails inv join inv.wccDetails wcc where inv=?1",VendorInvoiceDetails.class).setParameter(1,new VendorInvoiceDetails(req.getId())).getSingleResult();
		}catch(NoResultException ex) {
			throw new BadRequestException("Invalid Invoice Id");
		}
		catch(NonUniqueResultException ex) {
			throw new BadRequestException("Invalid Invoice Id");
		}
		
		if(!_invoice.getOwner().getEmailId().equalsIgnoreCase(user.getUsername()))
			throw new UnauthoriseException("You are not authorise");
		
		if(!_invoice.getWccDetails().getId().equals(req.getWccId()))
			throw new BadRequestException("Invalid wcc id");
		
		if(_invoice.getInvoiceStatus().getId()==2||_invoice.getInvoiceStatus().getId()==4||_invoice.getInvoiceStatus().getId()==1)
			throw new BadRequestException("Can not edit record");
		
		
		WccDetails wcc=_invoice.getWccDetails();
		
		if(wcc.getWccAmount()!=_invoice.getInvoiceAmount()) {
			_invoice.setInvoiceAmount(wcc.getWccAmount());
			_invoice.setInvoiceTaxAmount(wcc.getWccTaxAmount());
			_invoice.setInvoiceTotalAmount(wcc.getWccTotalAmount());
		}
		
		_invoice.setInvoiceStatus(new VendorInvoiceStatus(6));
		_invoice.setLastModifiedBy(new EmployeeDetails(user.getUsername()));
		_invoice.setLastModifiedDate(new Date());
		_invoice.setVendorInvoiceDate(req.getVendorInvoiceDate());
		_invoice.setVendorInvoiceNumber(req.getVendorInvoiceNumber());
		_invoice.setRemarks(req.getRemarks());
	
		if(invoiceCopy!=null) {
			if(!invoiceCopy.isEmpty()) {
				String fileName = fileStorageService.storeFile(invoiceCopy);

		        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
		                .path("/api/downloadFile/")
		                .path(fileName)
		                .toUriString();
		        try {
		        	UploadedFiles uploadedFiles = new UploadedFiles();
		            uploadedFiles.setObjectName("VendorInvoiceDetails");
		            uploadedFiles.setObjectId(String.valueOf(_invoice.getId()));
		            uploadedFiles.setFileName(fileName);
		            uploadedFiles.setFileSize(invoiceCopy.getSize());
		            uploadedFiles.setFileContentType(invoiceCopy.getContentType());
		            uploadedFiles.setFileDownloadUri(fileDownloadUri);
		            uploadedFiles.setCreatedDate(new Date());
		            uploadedFiles.setCreatedBy(new EmployeeDetails(user.getUsername()));
		            em.persist(uploadedFiles);
		        }
		        catch(Exception ex) {}
				_invoice.setInvoiceCopyUrl(fileDownloadUri);
			}
		}
		
		//UPDATE INVOICE DETAILS IIN CASE
		
		List<CaseDetails> listCaseDetails=null;
		try {
			listCaseDetails=em.createQuery("select csd from WccServiceAppointment wca join wca.serviceAppointment sa join sa.caseDetails csd where wca.wcc=?1",CaseDetails.class)
					.setParameter(1, _invoice.getWccDetails())
					.getResultList();
		}catch(Exception ex) {
			throw new BadRequestException("Unable to get case details");
		}
		for(CaseDetails cds:listCaseDetails) {
			cds.setVendorInvoiceNumber(_invoice.getVendorInvoiceNumber());
			cds.setVendorInvoiceDate(_invoice.getVendorInvoiceDate());
			cds.setModifiedBy(new EmployeeDetails(user.getUsername()));
			cds.setModifiedDate(new Date());
			em.persist(cds);
		}
		//UPDATE INVOICE END
		
		workflow.executeWorkflow(em, _invoice, "UPDATE", "PRE", user);
		em.persist(_invoice);
		workflow.executeWorkflow(em, _invoice, "UPDATE", "POST", user);
		
	}

	@Override
	@Transactional(rollbackFor= {BadRequestException.class,UnauthoriseException.class,Exception.class,RuntimeException.class},propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED)
	public void submitforApproval(Long invoiceid, LoginUser user) throws BadRequestException, UnauthoriseException {
		
		VendorInvoiceDetails _invoice=null;
		
		try {
			_invoice=em.createQuery("select inv from VendorInvoiceDetails inv join inv.wccDetails wcc where inv=?1",VendorInvoiceDetails.class)
					.setParameter(1,new VendorInvoiceDetails(invoiceid))
					.getSingleResult();
			
		}catch(NoResultException ex) {
			throw new BadRequestException("Invalid invoid id");
		}
		catch(NonUniqueResultException ex) {
			throw new BadRequestException("Invalid invoid id");
		}
		
		if(!_invoice.getOwner().getEmailId().equalsIgnoreCase(user.getUsername()))
			throw new UnauthoriseException("You are not authorise");
		Integer st=_invoice.getInvoiceStatus().getId();
		if(st==1||st==2||st==4) {
			
			throw new BadRequestException("Can not submit for approval");
		}
		
		WccDetails wcc=_invoice.getWccDetails();
		
		if(wcc.getWccStatus().getId()!=2)
			
			throw new BadRequestException("Your Wcc is not approved");
		
		if(wcc.getWccAmount()!=_invoice.getInvoiceAmount()) {
			_invoice.setInvoiceAmount(wcc.getWccAmount());
			_invoice.setInvoiceTaxAmount(wcc.getWccTaxAmount());
			_invoice.setInvoiceTotalAmount(wcc.getWccTotalAmount());
		}
		

		_invoice.setLastModifiedBy(new EmployeeDetails(user.getUsername()));
		_invoice.setLastModifiedDate(new Date());
		_invoice.setInvoiceStatus(new VendorInvoiceStatus(1));
		em.persist(_invoice);
		//approval process
		
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

			_rulesLst = em.createQuery(
					"select apr from Approval apr where apr.objectClassName=?1 and apr.active=?2",
					com.pratikshat.exicom.entity.Approval.class).setParameter(1, "VendorInvoiceDetails")
					.setParameter(2, true)
					.getResultList();

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

			com.pratikshat.exicom.entity.ApprovalSteps _stapes = em
					.createQuery("select stp from ApprovalSteps stp where stp.approval=?1 and stp.approvedStage=?2",
							com.pratikshat.exicom.entity.ApprovalSteps.class)
					.setParameter(1, _appliedRule).setParameter(2, 1).getSingleResult();

			String assigned[] = _stapes.getApprovedBy().split(":");

			String assigneUserEmailId = "";

			if (assigned[0].equalsIgnoreCase("USER")) {
				assigneUserEmailId = assigned[1];
			}
			if (assigned[0].equalsIgnoreCase("APPROVAL-MANAGER")) {
				assigneUserEmailId = em.createQuery(
						"select apm.emailId from EmployeeDetails emp join emp.approvalManager apm where emp.emailId=?1",
						String.class).setParameter(1, user.getUsername()).getSingleResult();
			}

			if (assigned[0].equalsIgnoreCase("MANAGER")) {
				assigneUserEmailId = em.createQuery(
						"select apm.emailId from EmployeeDetails emp join emp.manager apm where emp.emailId=?1",
						String.class).setParameter(1, user.getUsername()).getSingleResult();
			}

			if (assigned[0].equalsIgnoreCase("ROLE")) {
				assigneUserEmailId = getApproverEmail(assigned[1], user.getUsername(), em);
			}

			com.pratikshat.exicom.entity.ToBeApproved _toBeApproved = new com.pratikshat.exicom.entity.ToBeApproved();
			_toBeApproved.setAssignedTo(new com.pratikshat.exicom.entity.EmployeeDetails(assigneUserEmailId));
			_toBeApproved.setAssignedDate(new java.util.Date());
			_toBeApproved.setObjectId(_invoice.getId());
			_toBeApproved.setObjectName("VendorInvoiceDetails");
			_toBeApproved.setObjectId(_invoice.getId());
			_toBeApproved.setObjectManualId(_invoice.getManualId());
			_toBeApproved
					.setObjectUrl("/invoice/" + _invoice.getId());
			_toBeApproved.setRemark("");
			_toBeApproved.setApprovalRuleId(_appliedRule.getId());
			_toBeApproved.setStage(1);
			_toBeApproved.setStatus(false);
			_toBeApproved.setPreviousRemarks(null);
			em.persist(_toBeApproved);

			com.pratikshat.exicom.entity.ApplicationNotificationDetails notificationDetails = new com.pratikshat.exicom.entity.ApplicationNotificationDetails();
			notificationDetails.setNofificationDate(new java.util.Date());
			notificationDetails.setNotificationActionUrl("/itemtobeapproved");
			notificationDetails.setNotificationMessage(
					String.format("Vendor Invoice %s received for your appoval",
							_invoice.getManualId()));
			notificationDetails.setNotificationRead(false);
			notificationDetails.setUserEmailId(assigneUserEmailId);

			em.persist(notificationDetails);

			try {
				messagingTemplate.convertAndSend("/topic/" + assigneUserEmailId, notificationDetails);
			} catch (Exception ex) {
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BadRequestException("Unable to submit request for approval");

		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> advanceSearch(Map<String, Object> searchKeys, LoginUser user, List<String> userLists) throws ParseException {
		SimpleDateFormat dformatter=new SimpleDateFormat("yyyy-mm-dd");
		List<Object> value = new ArrayList<>();
		String _query="SELECT \r\n" + 
				"	inv.`created_by_email_id` AS invoiceCreatedByEmailId,\r\n" + 
				"	inv.`created_date` AS invoiceCreatedDate,\r\n" + 
				"	grn.`entry_sheet_number` AS entrySheetNumber,\r\n" + 
				"	grn.`grn_date` AS grnDate,\r\n" + 
				"	grn.`grn_number` AS grnNumber,\r\n" + 
				"	sap.sap_number as sapNumber,\r\n"+
				" 	sap.sap_date as sapDate,\r\n"+
				"	inv.`id` AS invoiceId,\r\n" + 
				"	inv.`invoice_amount` AS invoiceAmount,\r\n" + 
				"	inv.`invoice_status_id` AS invoiceStatusId,\r\n" + 
				"	inv.`last_modified_by_email_id` AS invoiceLastModifiedEmailId,\r\n" + 
				"	inv.`last_modified_date` AS invoiceLastModifiedDate,\r\n" + 
				"	inv.`manual_id` AS invoiceManualId,\r\n" + 
				"	inv.`owner_email_id` AS invoiceOnwerEmailId,\r\n" + 
				"	inv.`po_date` AS invoicePoDate,\r\n" + 
				"	inv.`remarks` AS invoiceRemarks,\r\n" + 
				"	inv.`vendor_id` AS invoiceVendorId,\r\n" + 
				"	inv.`vendor_invoice_date` AS vendorInvoiceDate,\r\n" + 
				"	inv.`vendor_invoice_number` AS vendorInvoiceNumber,\r\n" + 
				"	inv.`vendor_name` AS vendorName,\r\n" + 
				"	inv.`vendor_po_id` AS vendorPoID,\r\n" + 
				"	inv.`wcc_details_id` AS wccDetailsId,\r\n" + 
				" 	inv.invoice_copy_url as invoiceCopyUrl,\r\n"+
				"	inv.invoice_tax_amount as invoiceTaxAmount,\r\n"+
				" 	inv.invoice_total_amount as invoiceTotalAmount,\r\n"+
				"	vis.`name` AS invoiceStatus,\r\n" + 
				"	po.`busineed_area` AS poBusineesArea,\r\n" + 
				"	po.`circle_circle_name` AS poCircleName,\r\n" + 
				"	po.`code_for_internal_ref` AS poCodeForInternalRef,\r\n" + 
				"	po.`cost_centre` AS poCostCentre,\r\n" + 
				"	po.`gl_code` AS glCode,\r\n" + 
				"	po.`po_category_id` AS poCategoryId,\r\n" + 
				"	po.`po_date` AS poDate,\r\n" + 
				"	po.`po_expiry_date` AS poExpiryDate,\r\n" + 
				"	po.`po_number` AS poNumber,\r\n" + 
				"	po.`remaining_quantity` AS poRemainingQuantity,\r\n" + 
				"	po.`total_quantity` AS poTotalQuantity,\r\n" + 
				"	po.`vendor_code` AS povendorCode,\r\n" + 
				"	po.`vendor_name` AS povendorName,\r\n" + 
				"	wcc.`manual_id` AS wccManualId,\r\n" + 
				"	wcc.`service_center_legal_entity_id` AS serviceCenterId,\r\n" + 
				"	lgl.`legal_entity_name` AS serviceCenterName,\r\n" + 
				"	crb.`first_name` AS createdByFirstName,\r\n" + 
				"	crb.`last_name` AS createdByLastName,\r\n" + 
				"	crb.`middle_name` AS createdByMiddleName,\r\n" + 
				"	mdb.`first_name` AS lastModifiedByFirstName,\r\n" + 
				"	mdb.`last_name` AS lastModifiedByLastName,\r\n" + 
				"	mdb.`middle_name` AS lastModifiedByMiddleName,\r\n"+
				" 	cts.`contact_id` as vendorContactId,\r\n"+
				"	cts.`account_id` as vendorAccountSapCode,\r\n"+
				"	invs.`name` AS vendorInvocieStatus\r\n"+
				"	\r\n" + 
				"	FROM `vendor_invoice_details` inv \r\n" + 
				"	JOIN `vendor_po_details` po ON inv.`vendor_po_id`=po.`id`\r\n" + 
				"	JOIN `wcc_details` wcc ON inv.`wcc_details_id`=wcc.`id`\r\n" + 
				"	JOIN `vendor_invoice_status` vis ON inv.`invoice_status_id`=vis.`id` \r\n" + 
				"	JOIN `employee_details` crb ON crb.`email_id`=inv.`created_by_email_id`\r\n" + 
				"	JOIN `employee_details` mdb ON mdb.`email_id`=inv.`last_modified_by_email_id`\r\n" + 
				"	JOIN `legal_entity` lgl ON lgl.`legal_entity_id`=wcc.`service_center_legal_entity_id`\r\n"+
				"	JOIN `vendor_invoice_status` invs ON invs.`id`=inv.`invoice_status_id`\r\n"+
				"	JOIN `contact_details` cts on cts.`email_id`=inv.`created_by_email_id`\r\n"+
				" 	LEFT JOIN vendor_invoice_grn grn on grn.invoice_id=inv.id\r\n"+
				" 	LEFT JOIN vendor_invoice_sap sap on sap.invoice_id=inv.id\r\n"+
				"	WHERE 1=1";
		StringBuilder queryBuilder=new StringBuilder();
		queryBuilder.append(_query);
		if(searchKeys.get("invoice_number")!=null&&!searchKeys.get("invoice_number").equals("")) {
			queryBuilder.append(" and inv.`manual_id` like ?");
			value.add("%"+searchKeys.get("invoice_number")+"%");
			
		}
		if(searchKeys.get("vendor_name")!=null&&!searchKeys.get("vendor_name").equals("")) {
			queryBuilder.append(" and inv.`vendor_name` like ?");
			value.add("%"+searchKeys.get("vendor_name")+"%");
		}
		if(searchKeys.get("vendor_code")!=null&&!searchKeys.get("vendor_code").equals("")) {
			queryBuilder.append(" and po.`vendor_code` like ?");
			value.add("%"+searchKeys.get("vendor_code")+"%");
		}
		if(searchKeys.get("circle_name")!=null&&!searchKeys.get("circle_name").equals("")) {
			queryBuilder.append(" and po.`circle_circle_name` like ?");
			value.add("%"+searchKeys.get("circle_name")+"%");
		}
		
		if(searchKeys.get("po_number")!=null&&!searchKeys.get("po_number").equals("")) {
			queryBuilder.append(" and po.`po_number` like ?");
			value.add("%"+searchKeys.get("po_number")+"%");
		}
		if(searchKeys.get("po_date")!=null&&!searchKeys.get("po_date").equals("")) {
			queryBuilder.append(" and date_format(po.`po_date`,'%Y-%m-%d')=date_format(?,'%Y-%m-%d')");
			value.add(searchKeys.get("po_date"));	
		}
		if(searchKeys.get("vendor_invoice_number")!=null&&!searchKeys.get("vendor_invoice_number").equals("")) {
			queryBuilder.append(" and inv.`vendor_invoice_number` like ?");
			value.add("%"+searchKeys.get("vendor_invoice_number")+"%");
		}
		if(searchKeys.get("vendor_invoice_date")!=null&&!searchKeys.get("vendor_invoice_date").equals("")) {
			queryBuilder.append(" and date_format(inv.`vendor_invoice_date`,'%Y-%m-%d')= date_format(?,'%Y-%m-%d')");
			value.add(searchKeys.get("vendor_invoice_date"));	
		}
		
		if(searchKeys.get("invoice_status")!=null&&!searchKeys.get("invoice_status").equals("")) {
			queryBuilder.append(" and invs.`name` like ?");
			value.add("%"+searchKeys.get("invoice_status")+"%");
		}
		
		if(userLists!=null) {
			queryBuilder.append(" and inv.`owner_email_id` in ");
			StringBuilder em=new StringBuilder();
			int i=0;
			for(String usr:userLists) {
				if(i!=0)
					em.append(",");
				em.append("'"+usr+"'");
				i++;
			}
			queryBuilder.append("("+em.toString()+")");
		}
		
		return jdbc.queryForList(queryBuilder.toString(),value.toArray());
	}
	
}
