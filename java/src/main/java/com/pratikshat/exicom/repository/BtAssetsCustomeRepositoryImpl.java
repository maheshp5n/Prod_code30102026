
package com.pratikshat.exicom.repository;


import com.pratikshat.exicom.entity.BtAssetAmcUpdateLog;
import com.pratikshat.exicom.entity.BtAssets;
import com.pratikshat.exicom.entity.BtAssetsAmcdetails;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.AmcUpdateRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;

import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

public class BtAssetsCustomeRepositoryImpl implements BtAssetsCustomeRepository{

    @PersistenceContext
    EntityManager em;

    @Autowired
    com.pratikshat.exicom.interceptor.WorkflowExecuter workflow;
    
    @Autowired
    BtAssetsAmcdetailsRepository  amcdetailsRepository;
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    @Override
    public BtAssets saveDetails(BtAssets btAssets, LoginUser user) throws BadRequestException, UnauthoriseException {
        BtAssets _checck=null;
        try{
            _checck=em.createQuery("select act from BtAssets act join act.products prd where act.manualId=?1 and prd.manualId=?2",BtAssets.class)
                    .setParameter(1,btAssets.getManualId())
                    .setParameter(2,btAssets.getProducts().getManualId())
                    .getSingleResult();
            throw new BadRequestException("Assets with id "+btAssets.getManualId()+" already exists");
        }catch (NoResultException ex){

        }catch (NonUniqueResultException ex){
            throw new BadRequestException("Multiple assets found with Id "+btAssets.getManualId());
        }
        if(btAssets.getProducts()==null)
            throw new BadRequestException("Product missing");
        workflow.executeWorkflow(em,btAssets,"INSERT","PRE",user);
        em.persist(btAssets);
        workflow.executeWorkflow(em,btAssets,"INSERT","POST",user);
        return btAssets;
    }

    @Override
    public BtAssets updateDetails(BtAssets btAssets, LoginUser user) throws BadRequestException, UnauthoriseException {
        BtAssets _exits=null;
        try{
            _exits=em.createQuery("select act from BtAssets act where act.id=?1",BtAssets.class)
                    .setParameter(1,btAssets.getId())
                    .getSingleResult();
        }catch (NoResultException ex){
            throw new BadRequestException("Assets not found");
        }catch (NonUniqueResultException ex){
            throw new BadRequestException("Multiple assets found with Id "+btAssets.getManualId());
        }

        
        _exits.setCustomer(btAssets.getCustomer());
        _exits.setPlant(btAssets.getPlant());
        _exits.setAccount(btAssets.getAccount());
        _exits.setRemarks(btAssets.getRemarks());
         if(btAssets.getProducts()==null)
            throw new BadRequestException("Product cant not be null");
        _exits.setProducts(btAssets.getProducts());
        _exits.setPrimaryAccount(btAssets.getPrimaryAccount());
        _exits.setPlant(btAssets.getPlant());
        _exits.setSiteDetails(btAssets.getSiteDetails());
        _exits.setCustomer(btAssets.getCustomer());
        _exits.setStatus(btAssets.getStatus());
        _exits.setOwner(btAssets.getOwner());
        workflow.executeWorkflow(em,_exits,"UPDATE","PRE",user);
        em.persist(_exits);
        workflow.executeWorkflow(em,_exits,"UPDATE","POST",user);
        return _exits;
    }

    @Override
    public BtAssets deleteDetails(BtAssets btAssets, LoginUser user) throws BadRequestException, UnauthoriseException {
        throw new BadRequestException("Delete record is not supported");
    }

	@Override
	public BtAssets getAssetsForAppointment(long id) throws BadRequestException {
		BtAssets _assets=null;
		try {
			Query assetsQuery = em.createQuery(
					"select ast from BtAssets ast left join ast.account act left join ast.siteDetails st left join ast.status sts left join ast.products prd where ast.id=?1",
					BtAssets.class);
			assetsQuery.setParameter(1, id);
			_assets = (BtAssets) assetsQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Asset does not exist");
		} catch (Exception ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException(ex.getMessage());
		}
		return _assets;
	}
	
		


	@Override
	public BtAssets getWithObject(long id) throws BadRequestException {
		BtAssets _ast = null;
		try {
			_ast = em.createQuery("select ast from BtAssets ast where ast.id=?1", BtAssets.class)
					.setParameter(1, id).getSingleResult();
		} catch (NoResultException ex) {
			throw new BadRequestException("Wrong assets serial number");
		}
		return _ast;
	}

	@Override
	public BtAssets getWithId(long id) throws BadRequestException {
		BtAssets btAssets=null;
		try {
			btAssets = em.createQuery("select bt from BtAssets bt where bt.id=?1", BtAssets.class)
					.setParameter(1,id).getSingleResult();
		} catch (NoResultException ex) {
			throw new BadRequestException("Invalid BtAssets Id");
		} catch (NonUniqueResultException ex) {
			throw new BadRequestException("multiple BtAssets id found with this Id");
		}
		return btAssets;
	}
	
	@Override
	@org.springframework.transaction.annotation.Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class})
	public AmcUpdateRequest updateAmcBulk(AmcUpdateRequest req, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		BtAssetsAmcdetails assetAMCDetails = null;
		BtAssets asset = null;
		try {
			Query assetsQuery=em.createQuery("select ast from BtAssets ast left join ast.products pr left join ast.status st where ast.manualId=?1 and pr.manualId=?2",BtAssets.class);
			assetsQuery.setParameter(1, req.getAssetSerialNo().trim());
			assetsQuery.setParameter(2, req.getAssetPartCode().trim());
			asset=(BtAssets)assetsQuery.getSingleResult();	

		}catch(NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Asset does not exist");
		}catch (NonUniqueResultException e) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Multiple assets exist");
		}catch(Exception ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException(ex.getMessage());
		}
		
		try {
			Query assetsQuery=em.createQuery("select ast from BtAssetsAmcdetails ast left join ast.btAssets st where ast.id=?1",BtAssetsAmcdetails.class);

			assetsQuery.setParameter(1, asset.getId());
			assetAMCDetails=(BtAssetsAmcdetails)assetsQuery.getSingleResult();	
		}catch(NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Asset does not exist");
		}catch (NonUniqueResultException e) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Multiple assets exist");
		}catch(Exception ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException(ex.getMessage());
		}
	
		if(asset.getStatus()!=null && asset.getStatus().getAssetsStatusId()==1) {	// Dismantle
			throw new com.pratikshat.exicom.exception.BadRequestException("Asset is in dismantle state");
		}
		
		if(req.getAmcPoNo()!=null && !req.getAmcPoNo().equalsIgnoreCase("")) {
			assetAMCDetails.setAmcPurchaseOrderNumber(req.getAmcPoNo().trim());
		}else {
			throw new com.pratikshat.exicom.exception.BadRequestException("AMC PO no. is null");	
		}
		
		if(req.getAmcPoDate()!=null) {
			try {
				String amcPurchaseOrderDateString = sdf.format(req.getAmcPoDate());
				assetAMCDetails.setAmcPurchaseOrderDate(amcPurchaseOrderDateString);	
			}catch(Exception ex){
				throw new com.pratikshat.exicom.exception.BadRequestException("AMC PO date format exception");
			}
		}else {
			throw new com.pratikshat.exicom.exception.BadRequestException("AMC PO date is null");	
		}
		
		if(req.getAmcPeriod()!=null && !req.getAmcPeriod().equalsIgnoreCase("")) {
			try {
				assetAMCDetails.setAmcPeriod(Integer.parseInt(req.getAmcPeriod().trim()));
			}catch(NumberFormatException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("AMC Period number format exception");	
			}
		}else {
			throw new com.pratikshat.exicom.exception.BadRequestException("AMC period is null");	
		}
		
		
		if(req.getAmcStartDate()!=null) {
			try {
				String amcStartDate = sdf.format(req.getAmcStartDate());
				assetAMCDetails.setAmcStartDate(sdf.parse(amcStartDate));	
			}catch(Exception ex){
				throw new com.pratikshat.exicom.exception.BadRequestException("AMC Start Date format exception");
			}
		}else {
			throw new com.pratikshat.exicom.exception.BadRequestException("AMC Start Date is null");	
		}
		
		Calendar cl=Calendar.getInstance();
		cl.setTime(assetAMCDetails.getAmcStartDate());
		cl.add(Calendar.MONTH,assetAMCDetails.getAmcPeriod());
		cl.add(Calendar.HOUR_OF_DAY, -24);
		assetAMCDetails.setAmcExpiryDate(cl.getTime());
			

		amcdetailsRepository.updateDetails(assetAMCDetails,user);
		
		
		BtAssetAmcUpdateLog log = new BtAssetAmcUpdateLog();
		log.setAssetSerialNo(req.getAssetSerialNo().trim());
		log.setAssetPartCode(req.getAssetPartCode().trim());
		log.setAmcPoNo(req.getAmcPoNo().trim());
		log.setAmcPoDate(sdf.format(req.getAmcPoDate()));
		log.setAmcPeriod(req.getAmcPeriod().trim());
		log.setAmcStartDate(sdf.format(req.getAmcStartDate()));
		
		log.setCreatedBy(new EmployeeDetails(user.getUsername()));
		log.setCreatedDate(new Date());
		
		em.persist(log);
		
		return req;
	}

	@Override
	public BtAssets getAssestBySerialNoAndPartCode(String assetsSerialNumber, String assetsPartCode) throws BadRequestException {
		BtAssets _assets=null;
		try {
			Query assetsQuery = em.createQuery(
					"select ast from BtAssets ast left join ast.account act left join ast.siteDetails st left join ast.status sts left join ast.products prd where ast.manualId=?1 and prd.manualId=?2 ",
					BtAssets.class);
			assetsQuery.setParameter(1, assetsSerialNumber);
			assetsQuery.setParameter(2, assetsPartCode);
			_assets = (BtAssets) assetsQuery.getSingleResult();
		} catch (NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Asset does not exist");
		} catch (Exception ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException(ex.getMessage());
		}
		return _assets;
	}

}
