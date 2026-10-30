package com.pratikshat.exicom.repository;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.BtVendorInvoiceDetails;
import com.pratikshat.exicom.entity.BtVendorInvoiceGrn;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.GlobalSearchIndex;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.UploadedFiles;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.interceptor.WorkflowExecuter;
import com.pratikshat.exicom.services.FileStorageService;

public class BtVendorInvoiceDetailsRepositoryCustomImpl implements BtVendorInvoiceDetailsRepositoryCustom{

	@Autowired
	WorkflowExecuter workflow;
	
	@PersistenceContext
	EntityManager em;
	
	@Autowired
    private FileStorageService fileStorageService;
	
	@Override
	@Transactional
	public BtVendorInvoiceDetails saveDetails(BtVendorInvoiceDetails btVendorInvoiceDetails, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		workflow.executeWorkflow(em, btVendorInvoiceDetails, "INSERT", "PRE", user);
		em.persist(btVendorInvoiceDetails);
		SimpleDateFormat sdf= new SimpleDateFormat("yyyymmdd");
		String manualId="INV-BT-" +MiscellaneousUtils.fillZero(""+btVendorInvoiceDetails.getId(), 5);
		btVendorInvoiceDetails.setManualId(manualId);
		em.persist(btVendorInvoiceDetails);
		workflow.executeWorkflow(em, btVendorInvoiceDetails, "INSERT", "POST", user);
		try {
			GlobalSearchIndex _search = new GlobalSearchIndex();
			_search.setKeyName("manualId");
			_search.setObjectDetailsUrl("bt/inv/" + btVendorInvoiceDetails.getId());
			_search.setObjectIdName("id");
			_search.setObjectIdValue("" + btVendorInvoiceDetails.getId());
			_search.setObjectName("BtVendorInvoiceDetails");
			_search.setSearchString(manualId);
			em.persist(_search);
		} catch (Exception e) {
	  }
		return null;
	}

	@Override
	@Transactional
	public BtVendorInvoiceDetails updateDetails(BtVendorInvoiceDetails details, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		BtVendorInvoiceDetails _check=null;
		try {
			_check=em.createQuery("select inv from BtVendorInvoiceDetails inv join inv.vendorPo vp join inv.wccDetails wcc where inv.id=?1",BtVendorInvoiceDetails.class)
					.setParameter(1,details.getId())
					.getSingleResult();
		}catch(NoResultException ex) {
			throw new BadRequestException("Invoice details not found");
		}
		catch(NonUniqueResultException ex) {
			throw new BadRequestException("Invalid Id");
		}
		_check.setInvoiceAmount(details.getInvoiceAmount());
		_check.setInvoiceStatus(details.getInvoiceStatus());
		_check.setRemarks(details.getRemarks());
		_check.setOwner(details.getOwner());
		_check.setVendorId(details.getVendorId());
		_check.setVendorInvoiceDate(details.getVendorInvoiceDate());
		_check.setVendorInvoiceNumber(details.getVendorInvoiceNumber());
		_check.setVendorName(details.getVendorName());
		_check.setVendorPo(details.getVendorPo());
		_check.setPoDate(details.getPoDate());
		_check.setInvoiceTaxAmount(details.getInvoiceTaxAmount());
		_check.setInvoiceTotalAmount(details.getInvoiceTotalAmount());
		_check.setWccDetails(details.getWccDetails());
		workflow.executeWorkflow(em, _check, "UPDATE", "PRE", user);
		em.persist(_check);
		workflow.executeWorkflow(em, _check, "UPDATE", "POST", user);
		return null;
	}

	@Override
	public BtVendorInvoiceDetails deleteDetails(BtVendorInvoiceDetails t, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		return null;
	}

	@Override
	public BtVendorInvoiceDetails getDetailsWithId(long id) throws BadRequestException {
		BtVendorInvoiceDetails _invoice=null;
		try {
			_invoice=em.createQuery("select inv from BtVendorInvoiceDetails inv join inv.vendorPo vp join inv.wccDetails wcc join inv.invoiceStatus st where inv.id=?1",BtVendorInvoiceDetails.class)
					.setParameter(1, id)
					.getSingleResult();
		}catch(NoResultException ex) {
			throw new BadRequestException("Invoice details not found");
		}
		catch(NonUniqueResultException ex) {
			throw new BadRequestException("Invalid Id");
		}
		return _invoice;
	}

	@Override
	@Transactional
	public BtVendorInvoiceDetails saveVendorInvoiceWithCopy(BtVendorInvoiceDetails btVendorInvoiceDetails,
			LoginUser user, MultipartFile invoice) throws BadRequestException, UnauthoriseException {
		workflow.executeWorkflow(em, btVendorInvoiceDetails, "INSERT", "PRE", user);
		em.persist(btVendorInvoiceDetails);
//		for Uploading File or invoice   
		
		if(invoice!=null&&!invoice.isEmpty()) {
			String fileName = fileStorageService.storeFile(invoice);

	        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
	                .path("/api/downloadFile/")
	                .path(fileName)
	                .toUriString();
	        try {
	        	UploadedFiles uploadedFiles = new UploadedFiles();
	            uploadedFiles.setObjectName("BtVendorInvoiceDetails");
	            uploadedFiles.setObjectId(String.valueOf(btVendorInvoiceDetails.getId()));
	            uploadedFiles.setFileName(fileName);
	            uploadedFiles.setFileSize(invoice.getSize());
	            uploadedFiles.setFileContentType(invoice.getContentType());
	            uploadedFiles.setFileDownloadUri(fileDownloadUri);
	            uploadedFiles.setCreatedDate(new Date());
	            uploadedFiles.setCreatedBy(new EmployeeDetails(user.getUsername()));
	            em.persist(uploadedFiles);
	        }
	        catch(Exception ex) {}
	        btVendorInvoiceDetails.setInvoiceCopyUrl(fileDownloadUri);
		}
		SimpleDateFormat sdf= new SimpleDateFormat("yyyymmdd");
		String manualId="BT-INV-"+MiscellaneousUtils.fillZero(""+btVendorInvoiceDetails.getId(), 5);
		btVendorInvoiceDetails.setManualId(manualId);
		em.persist(btVendorInvoiceDetails);
		workflow.executeWorkflow(em, btVendorInvoiceDetails, "INSERT", "POST", user);
		try {
			GlobalSearchIndex _search = new GlobalSearchIndex();
			_search.setKeyName("manualId");
			_search.setObjectDetailsUrl("bt/inv/" + btVendorInvoiceDetails.getId());
			_search.setObjectIdName("id");
			_search.setObjectIdValue("" + btVendorInvoiceDetails.getId());
			_search.setObjectName("BtVendorInvoiceDetails");
			_search.setSearchString(manualId);
			em.persist(_search);
		} catch (Exception e) {
	  }
		return null;
	}

	@Override
	public BtVendorInvoiceDetails updateVendorInvoiceWithCopy(BtVendorInvoiceDetails details,
			LoginUser user, MultipartFile invoice) throws BadRequestException, UnauthoriseException {
		BtVendorInvoiceDetails _check=null;
		try {
			_check=em.createQuery("select inv from BtVendorInvoiceDetails inv join inv.vendorPo vp join inv.wccDetails wcc where inv.id=?1",BtVendorInvoiceDetails.class)
					.setParameter(1,details.getId())
					.getSingleResult();
		}catch(NoResultException ex) {
			throw new BadRequestException("Invoice details not found");
		}
		catch(NonUniqueResultException ex) {
			throw new BadRequestException("Invalid Id");
		}
			_check.setInvoiceAmount(details.getInvoiceAmount());
			_check.setInvoiceStatus(details.getInvoiceStatus());
			_check.setRemarks(details.getRemarks());
			_check.setOwner(details.getOwner());
			_check.setVendorId(details.getVendorId());
			_check.setVendorInvoiceDate(details.getVendorInvoiceDate());
			_check.setVendorInvoiceNumber(details.getVendorInvoiceNumber());
			_check.setVendorName(details.getVendorName());
			_check.setVendorPo(details.getVendorPo());
			_check.setPoDate(details.getPoDate());
			_check.setInvoiceTaxAmount(details.getInvoiceTaxAmount());
			_check.setInvoiceTotalAmount(details.getInvoiceTotalAmount());
			_check.setWccDetails(details.getWccDetails());
			workflow.executeWorkflow(em, _check, "INSERT", "PRE", user);
			em.persist(_check);
			if(invoice!=null) {
				if(!invoice.isEmpty()) {
					String fileName = fileStorageService.storeFile(invoice);

			        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
			                .path("/api/downloadFile/")
			                .path(fileName)
			                .toUriString();
			        try {
			        	UploadedFiles uploadedFiles = new UploadedFiles();
			            uploadedFiles.setObjectName("BtVendorInvoiceDetails");
			            uploadedFiles.setObjectId(String.valueOf(details.getId()));
			            uploadedFiles.setFileName(fileName);
			            uploadedFiles.setFileSize(invoice.getSize());
			            uploadedFiles.setFileContentType(invoice.getContentType());
			            uploadedFiles.setFileDownloadUri(fileDownloadUri);
			            uploadedFiles.setCreatedDate(new Date());
			            uploadedFiles.setCreatedBy(new EmployeeDetails(user.getUsername()));
			            em.persist(uploadedFiles);
			        }
			        catch(Exception ex) {}
					_check.setInvoiceCopyUrl(fileDownloadUri);
				}
			}
			em.persist(_check);
			workflow.executeWorkflow(em, _check, "INSERT", "POST", user);
		return null;
	}

	@Override
	public BtVendorInvoiceGrn updateDetails(BtVendorInvoiceGrn btVendorInvoiceGrn, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		BtVendorInvoiceGrn grn = null;
//		try {
//			grn = em.createQuery("select grn from BtVendorInvoiceGrn grn where grn=?1", BtVendorInvoiceGrn.class)
//					.setParameter(1, new BtVendorInvoiceGrn(btVendorInvoiceGrn.getInvoice()).getSingleResult();
//		} catch (NoResultException ex) {
//			grn = new BtVendorInvoiceGrn();
//			grn.setInvoice(btVendorInvoiceGrn.getInvoice());
		return null;
//	}

}
}
