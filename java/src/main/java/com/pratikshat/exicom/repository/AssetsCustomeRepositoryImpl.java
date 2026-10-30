package com.pratikshat.exicom.repository;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;

import com.pratikshat.exicom.entity.Accounts;
import com.pratikshat.exicom.entity.AssetAmcUpdateLog;
import com.pratikshat.exicom.entity.Assets;
import com.pratikshat.exicom.entity.AssetsStatus;
import com.pratikshat.exicom.entity.CaseDetails;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.PlantDetails;
import com.pratikshat.exicom.entity.Products;
import com.pratikshat.exicom.entity.SSA;
import com.pratikshat.exicom.entity.SiteDetails;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.AmcUpdateRequest;
import com.pratikshat.exicom.model.request.AssetsRequest;
import com.pratikshat.exicom.model.request.SiteDetailsRequest;
import com.pratikshat.exicom.model.response.AssetsResponse;

@Repository
@Transactional
public class AssetsCustomeRepositoryImpl implements AssetsCustomeRepository {

	@PersistenceContext
	EntityManager em;
	
	@Autowired
	com.pratikshat.exicom.interceptor.WorkflowExecuter workflow;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	@org.springframework.transaction.annotation.Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.READ_UNCOMMITTED,rollbackFor = {BadRequestException.class,UnauthoriseException.class,Exception.class,RuntimeException.class})
	public AssetsRequest saveAssets(AssetsRequest assets,LoginUser user) throws Exception {
		
		String assetsSerialNumber="";
		String actSerialNumber="";
		if(assets.getSerialNumber().endsWith("-"+assets.getProductPardCode())) {
			assetsSerialNumber=assets.getSerialNumber();
			actSerialNumber=assetsSerialNumber.replace("-"+assets.getProductPardCode(), "");
		}
			
		else {
			assetsSerialNumber=assets.getSerialNumber()+"-"+assets.getProductPardCode();
			actSerialNumber=assets.getSerialNumber();
		}
			
		
		Assets assetsExist = null;
		try {
			Query assetsQuery=em.createQuery("from Assets ast where ast.serialNumber=?1",Assets.class);
			//assetsQuery.setParameter(1,assets.getSerialNumber());
			assetsQuery.setParameter(1, assetsSerialNumber);
			assetsExist=(Assets)assetsQuery.getSingleResult();	
		}catch(NoResultException ex) {
			
		}
		
		if(assetsExist!=null) {
			throw new BadRequestException("Asset already exist with serial No. "+assets.getSerialNumber());
		}
		
		if(assets.getAssetsStatusId()==2&&assets.getSiteId()==0)
			throw new Exception("Site details not found");
		
		
		
		
		Assets _assets=new Assets();
		_assets.setAccount(new Accounts(assets.getAccountSapNumber()));
		_assets.setCreatedBy(new EmployeeDetails(user.getUsername()));
		_assets.setCreatedDate(new Date());
		_assets.setModifyedBy(new EmployeeDetails(user.getUsername()));
		_assets.setModifyedDate(new Date());
		_assets.setActlSerialNumber(actSerialNumber);
		_assets.setSource("CRM");
		
		if(assets.getPlantCode()==null||assets.getPlantCode().equalsIgnoreCase("")||assets.getPlantCode().equalsIgnoreCase("0")) {
			_assets.setPlant(null);
		}else {
			_assets.setPlant(new PlantDetails(assets.getPlantCode()));
		}
			
		if(assets.getPrimaryAccountSapNumber()==null||assets.getPrimaryAccountSapNumber().equalsIgnoreCase("")||assets.getPrimaryAccountSapNumber().equalsIgnoreCase("0")) {
			_assets.setPrimaryAccount(null);
		}else {
			_assets.setPrimaryAccount(new Accounts(assets.getPrimaryAccountSapNumber()));
		}
			
		if(assets.getProductPardCode()!=null) {
			_assets.setProduct(new Products(assets.getProductPardCode()));
		}else {
			throw new BadRequestException("Please choose part code");
		}
		
		_assets.setRemarks(assets.getRemarks());
		
		//_assets.setSerialNumber(assets.getSerialNumber()+"-"+assets.getProductPardCode());
		
		_assets.setSerialNumber(assetsSerialNumber);
		
		if(assets.getSiteId()==0||assets.getAssetsStatusId()==1||assets.getAssetsStatusId()==7) {
			_assets.setSite(null);
		}else {
			_assets.setSite(new SiteDetails(assets.getSiteId()));
		}
			
		if(assets.getAssetsStatusId()!=0) {
			_assets.setStatus(new AssetsStatus(assets.getAssetsStatusId()));
		}else {
			throw new BadRequestException("Please choose asset status");
		}
		
		_assets.setSynced(false);
		_assets.setPurchaseOrderNumber(assets.getPurchaseOrderNumber());
		
		if(assets.getPurchaseOrderDate()!=null) {
			String purchaseOrderDateString = sdf.format(assets.getPurchaseOrderDate());
			Date purchaseOrderDate = sdf.parse(purchaseOrderDateString);
			_assets.setPurchaseOrderDate(purchaseOrderDate);
		}
		
		
		if(assets.getPurchaseDate()!=null) {
			String purchaseDateString = sdf.format(assets.getPurchaseDate());
			Date purchaseDate = sdf.parse(purchaseDateString);
			_assets.setPurchaseDate(purchaseDate);
		}
		_assets.setWarrantyFromDateOfDeliveryInMonth(assets.getWarrantyFromDateOfDeliveryInMonth());
		
		if(assets.getPurchaseDate()!=null && assets.getInstallDate()!=null) {
			String purchaseDateString = sdf.format(assets.getPurchaseDate());
			Date purchaseDate = sdf.parse(purchaseDateString);
			
			String installDateString = sdf.format(assets.getInstallDate());
			Date installDate = sdf.parse(installDateString);
			
			if(installDate.getTime()-purchaseDate.getTime()<0) {
				throw new BadRequestException("Installation date must be greater than purchase date");
			}
			
			_assets.setInstallDate(installDate);
			
		}
		
		_assets.setWarrantyFromDateOfInstallationInMonth(assets.getWarrantyFromDateOfInstallationInMonth());
		
		Date xpBypDate=null;
		Date xpByInstDate=null;
		if(assets.getPurchaseDate()!=null) {
			Calendar cl=Calendar.getInstance();
			cl.setTime(_assets.getPurchaseDate());
			cl.add(Calendar.MONTH,assets.getWarrantyFromDateOfDeliveryInMonth());
			cl.add(Calendar.HOUR_OF_DAY,-24);
			xpBypDate=cl.getTime();
		}
		
		if(assets.getInstallDate()!=null) {
			Calendar cl=Calendar.getInstance();
			cl.setTime(_assets.getInstallDate());
			cl.add(Calendar.MONTH,assets.getWarrantyFromDateOfInstallationInMonth());
			cl.add(Calendar.HOUR_OF_DAY,-24);
			xpByInstDate=cl.getTime();
		}
		
		if(xpByInstDate==null && xpBypDate!=null) {
			_assets.setWarrantyExpiryDate(xpBypDate);
			_assets.setDateOfDeliveryExpiryDate(xpBypDate);
		}else if(xpBypDate==null && xpByInstDate!=null) {
			_assets.setInstallExpiryDate(xpByInstDate);
			_assets.setWarrantyExpiryDate(xpByInstDate);
		} else if(xpBypDate!=null && xpByInstDate!=null) {
			_assets.setInstallExpiryDate(xpByInstDate);
			_assets.setDateOfDeliveryExpiryDate(xpBypDate);
			if(xpBypDate.getTime()>xpByInstDate.getTime()) {
				_assets.setWarrantyExpiryDate(xpByInstDate);
			}else {
				_assets.setWarrantyExpiryDate(xpBypDate);
			}
		}
		
		_assets.setAmcPurchaseOrderNumber(assets.getAmcPurchaseOrderNumber());
		if(assets.getAmcPurchaseOrderDate()!=null) {
			String amcPurchaseOrderDateString = sdf.format(assets.getAmcPurchaseOrderDate());
			_assets.setAmcPurchaseOrderDate(amcPurchaseOrderDateString);
		}
		
		if(assets.getAmcPurchaseOrderDate()!=null && assets.getAmcStartDate()!=null) {
			String amcPurchaseOrderDateString = sdf.format(assets.getAmcPurchaseOrderDate());
			Date amcPurchaseOrderDate = sdf.parse(amcPurchaseOrderDateString);
			
			String amcStartDateString = sdf.format(assets.getAmcStartDate());
			Date amcStartDate = sdf.parse(amcStartDateString);
			
			if(amcStartDate.getTime()-amcPurchaseOrderDate.getTime()<0) {
				throw new BadRequestException("AMC start date must be greater than AMC purchase date");
			}
			
			_assets.setAmcStartDate(amcStartDate);
		}
		_assets.setAmcPeriod(assets.getAmcPeriod());
		
		if(assets.getAmcStartDate()!=null) {
			Calendar cl=Calendar.getInstance();
			cl.setTime(_assets.getAmcStartDate());
			cl.add(Calendar.MONTH,assets.getAmcPeriod());
			cl.add(Calendar.HOUR_OF_DAY,-24);
			_assets.setAmcExpiryDate(cl.getTime());
		}
		workflow.executeWorkflow(em, _assets, "INSERT", "PRE", user);
		em.persist(_assets);
		workflow.executeWorkflow(em, _assets, "INSERT", "POST", user);
		
		try {
			com.pratikshat.exicom.entity.GlobalSearchIndex _search = new com.pratikshat.exicom.entity.GlobalSearchIndex();
			_search.setKeyName("serialNumber");
			_search.setObjectDetailsUrl("assets/" + _assets.getSerialNumber());
			_search.setObjectIdName("serialNumber");
			_search.setObjectIdValue("" + _assets.getSerialNumber());
			_search.setObjectName("Assets");
			_search.setSearchString(_assets.getSerialNumber());
			em.persist(_search);
			
		} catch (Exception e) {
		}
		
		em.flush();
		em.clear();
		return assets;
	}

	@Override
	@org.springframework.transaction.annotation.Transactional(isolation = Isolation.READ_UNCOMMITTED,propagation = Propagation.REQUIRES_NEW,rollbackFor = {Exception.class,RuntimeException.class,BadRequestException.class,UnauthoriseException.class,SQLException.class})
	public SiteDetailsRequest saveSiteOnAssets(String assetsSerialNumber,SiteDetailsRequest site, LoginUser user) throws Exception {
		
		
		com.pratikshat.exicom.entity.Assets _assets = null;
		try {
			javax.persistence.Query assetsQuery=em.createQuery("from Assets ast where ast.serialNumber=?1",com.pratikshat.exicom.entity.Assets.class);
			assetsQuery.setParameter(1,assetsSerialNumber);
			_assets=(com.pratikshat.exicom.entity.Assets)assetsQuery.getSingleResult();	
		}catch(NoResultException ex) {
			throw new BadRequestException("Asset does not exist with serial No. "+assetsSerialNumber);
		}
		if(_assets.getSite()!=null)
			throw new BadRequestException("Site already exists on this assets ["+assetsSerialNumber+"]");
		
		com.pratikshat.exicom.entity.SiteDetails _site=new com.pratikshat.exicom.entity.SiteDetails();
		
		if(site.getAccountSapNumber()!=null) {
			_site.setAccount(new com.pratikshat.exicom.entity.Accounts(site.getAccountSapNumber()));
		}else {
			throw new BadRequestException("Please select account");
		}
		
		if(site.getCircleName()!=null) {
			_site.setCircle(new com.pratikshat.exicom.entity.CircleDetails(site.getCircleName()));
		}else {
			throw new BadRequestException("Please select circle");
		}
		
		if(site.getPrimaryAccountSapNumber()!=null) {
			_site.setPrimaryAccount(new com.pratikshat.exicom.entity.Accounts(site.getPrimaryAccountSapNumber()));
		}else {
			_site.setPrimaryAccount(null);
		}
		
		if(site.getServiceCenterid()!=0) {
			_site.setServiceCenter(new com.pratikshat.exicom.entity.LegalEntity(site.getServiceCenterid()));	
		}else {
			throw new BadRequestException("Please select service center");
		}
		
		if(site.getServiceResourceId()!=0) {
			_site.setServiceResource(new com.pratikshat.exicom.entity.ServiceRecource(site.getServiceResourceId()));
		}else {
			throw new BadRequestException("Please select service resource");
		}
		
		if(site.getSsaId()!=0) {
			_site.setSsa(new SSA(site.getSsaId()));
		}else {
			throw new BadRequestException("Please select SSA");
		}
		
		
		
		_site.setClusterDistrict(site.getClusterDistrict());
		_site.setCreateBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
		_site.setCreatedDate(new java.util.Date());
		_site.setInfraId(site.getInfraId());
		_site.setManualSiteId(site.getSiteManualId());
		_site.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
		_site.setModifiedDate(new java.util.Date());
		_site.setOwner(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
		_site.setSiteAddress(site.getSiteAddress());
		_site.setSiteDescription(site.getSiteDescription());
		_site.setSiteName(site.getSiteName());
		workflow.executeWorkflow(em, _site, "INSERT", "PRE", user);
		em.persist(_site);
		workflow.executeWorkflow(em, _site, "INSERT", "POST", user);
		
		
		if(_assets!=null) {
			_assets.setSite(_site);
			em.persist(_assets);	
		}
		
		site.setSiteId(_site.getSiteId());
		
		try {
			com.pratikshat.exicom.entity.GlobalSearchIndex _search = new com.pratikshat.exicom.entity.GlobalSearchIndex();
			_search.setKeyName("manualId");
			_search.setObjectDetailsUrl("sites/" + _site.getSiteId());
			_search.setObjectIdName("siteId");
			_search.setObjectIdValue("" + _site.getSiteId());
			_search.setObjectName("Case");
			_search.setSearchString(_site.getManualSiteId());
			em.persist(_search);
			
		} catch (Exception e) {
		}
		em.flush();
		em.clear();
		return site;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AssetsResponse> advanceSearch(Map<String, Object> searchKeys, LoginUser user, List<String> userLists) {
		String _query="select new com.pratikshat.exicom.model.response.AssetsResponse(ast) from Assets ast left join ast.product prd left join prd.subProductFamily sbp left join sbp.productFamily pf left join ast.account acc left join ast.plant plt left join ast.site site left join site.circle circle where 1=1 ";
		StringBuilder queryBuilder=new StringBuilder();
		queryBuilder.append(_query);
		if(searchKeys.get("assets_serial_number")!=null&&!searchKeys.get("assets_serial_number").equals("")) {
			queryBuilder.append(" and ast.serialNumber like :assets_serial_number");
		}
		if(searchKeys.get("site_name")!=null&&!searchKeys.get("site_name").equals("")) {
			queryBuilder.append(" and (site.siteName like :site_name or site.infraId like :site_name or site.manualSiteId like :site_name)");
		}
		if(searchKeys.get("product_part_code")!=null&&!searchKeys.get("product_part_code").equals("")) {
			queryBuilder.append(" and prd.productPartCode like :product_part_code");
		}
		if(searchKeys.get("product_description")!=null&&!searchKeys.get("product_description").equals("")) {
			queryBuilder.append(" and prd.pdoductDescription like :product_description");
		}
		if(searchKeys.get("account_name")!=null&&!searchKeys.get("account_name").equals("")) {
			queryBuilder.append(" and (acc.name like :account_name or acc.sapCustomerNumber like :account_name)");
		}
		if(searchKeys.get("circle")!=null&&!searchKeys.get("circle").equals("")) {
			queryBuilder.append(" and circle.circleName like :circle");
		}
		
		Query query = em.createQuery(queryBuilder.toString(),AssetsResponse.class);
		
		if(searchKeys.get("assets_serial_number")!=null&&!searchKeys.get("assets_serial_number").equals("")) {
			//queryBuilder.append(" and ast.serialNumber like :assets_serial_number");
			query.setParameter("assets_serial_number","%"+searchKeys.get("assets_serial_number")+"%");
		}
		if(searchKeys.get("site_name")!=null&&!searchKeys.get("site_name").equals("")) {
			//queryBuilder.append(" and (site.siteName like :site_name or site.infraId like :site_name or site.manualSiteId like :site_name)");
			query.setParameter("site_name", "%"+searchKeys.get("site_name")+"%");
		}
		if(searchKeys.get("product_part_code")!=null&&!searchKeys.get("product_part_code").equals("")) {
			//queryBuilder.append(" and prd.productPartCode like :product_part_code");
			query.setParameter("product_part_code", "%"+searchKeys.get("product_part_code")+"%");
		}
		if(searchKeys.get("product_description")!=null&&!searchKeys.get("product_description").equals("")) {
			//queryBuilder.append(" and prd.pdoductDescription like :product_description");
			query.setParameter("product_description", "%"+searchKeys.get("product_description")+"%");
		}
		if(searchKeys.get("account_name")!=null&&!searchKeys.get("account_name").equals("")) {
			//queryBuilder.append(" and (acc.name like :account_name or acc.sapCustomerNumber like :account_name)");
			query.setParameter("account_name", "%"+searchKeys.get("account_name")+"%");
		}
		if(searchKeys.get("circle")!=null&&!searchKeys.get("circle").equals("")) {
			//queryBuilder.append(" and circle.circleName like :circle");
			query.setParameter("circle", "%"+searchKeys.get("circle")+"%");
		}
		
		
		return query.getResultList();
	}

	@Override
	@org.springframework.transaction.annotation.Transactional(propagation = Propagation.REQUIRES_NEW,isolation=Isolation.READ_UNCOMMITTED,rollbackFor= {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class})
	public AmcUpdateRequest updateAmcBulk(AmcUpdateRequest req, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		Assets asset = null;
		try {
			String srlNumber=req.getAssetSerialNo().trim()+"-"+req.getAssetPartCode().trim();
			Query assetsQuery=em.createQuery("select ast from Assets ast left join ast.status st where ast.serialNumber=?1",Assets.class);
			assetsQuery.setParameter(1, srlNumber);
			asset=(Assets)assetsQuery.getSingleResult();	
		}catch(NoResultException ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Asset does not exist");
		}catch (NonUniqueResultException e) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Multiple Assets exist");
		}catch(Exception ex) {
			throw new com.pratikshat.exicom.exception.BadRequestException(ex.getMessage());
		}
		
		if(asset==null) {
			throw new com.pratikshat.exicom.exception.BadRequestException("Asset does not exist");
		}
	
		if(asset.getStatus()!=null && asset.getStatus().getAssetsStatusId()==1) {	// Dismantle
			throw new com.pratikshat.exicom.exception.BadRequestException("Asset is in dismantle state");
		}
		
		if(req.getAmcPoNo()!=null && !req.getAmcPoNo().equalsIgnoreCase("")) {
			asset.setAmcPurchaseOrderNumber(req.getAmcPoNo().trim());
		}else {
			throw new com.pratikshat.exicom.exception.BadRequestException("AMC PO No. is null");	
		}
		
		if(req.getAmcPoDate()!=null) {
			try {
				String amcPurchaseOrderDateString = sdf.format(req.getAmcPoDate());
				asset.setAmcPurchaseOrderDate(amcPurchaseOrderDateString);	
			}catch(Exception ex){
				throw new com.pratikshat.exicom.exception.BadRequestException("AMC PO Date format exception");
			}
		}else {
			throw new com.pratikshat.exicom.exception.BadRequestException("AMC PO Date is null");	
		}
		
		if(req.getAmcPeriod()!=null && !req.getAmcPeriod().equalsIgnoreCase("")) {
			try {
				asset.setAmcPeriod(Integer.parseInt(req.getAmcPeriod().trim()));
			}catch(NumberFormatException ex) {
				throw new com.pratikshat.exicom.exception.BadRequestException("AMC Period number format exception");	
			}
		}else {
			throw new com.pratikshat.exicom.exception.BadRequestException("AMC Period is null");	
		}
		
		
		if(req.getAmcStartDate()!=null) {
			try {
				String amcStartDate = sdf.format(req.getAmcStartDate());
				asset.setAmcStartDate(sdf.parse(amcStartDate));	
			}catch(Exception ex){
				throw new com.pratikshat.exicom.exception.BadRequestException("AMC Start Date format exception");
			}
		}else {
			throw new com.pratikshat.exicom.exception.BadRequestException("AMC Start Date is null");	
		}
		
		Calendar cl=Calendar.getInstance();
		cl.setTime(asset.getAmcStartDate());
		cl.add(Calendar.MONTH,asset.getAmcPeriod());
		cl.add(Calendar.HOUR_OF_DAY, -24);
		asset.setAmcExpiryDate(cl.getTime());
			
		asset.setModifyedBy(new EmployeeDetails(user.getUsername()));
		asset.setModifyedDate(new Date());
		
		em.persist(asset);
		
		
		AssetAmcUpdateLog log = new AssetAmcUpdateLog();
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

}
