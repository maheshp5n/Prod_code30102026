package com.pratikshat.exicom.services.impl;

import com.pratikshat.exicom.entity.*;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.AccountsRepository;
import com.pratikshat.exicom.repository.BtAssetsAmcdetailsRepository;
import com.pratikshat.exicom.repository.BtAssetsPurchaseDetailsRepository;
import com.pratikshat.exicom.repository.BtAssetsRepository;
import com.pratikshat.exicom.repository.BtAssetsWarrantyDetailsRepository;
import com.pratikshat.exicom.repository.BtProductsRepository;
import com.pratikshat.exicom.repository.BtSiteDetailsRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtAssetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Service
public class BtAssetsServiceImpl implements BtAssetsService {

	@Autowired
	BtAssetsRepository dbAssets;
	@Autowired
	BtAssetsPurchaseDetailsRepository dbAssetspurchase;
	@Autowired
	BtAssetsAmcdetailsRepository dbAssetsamc;
	@Autowired
	BtAssetsWarrantyDetailsRepository dbAssetswarranty;
	@Autowired
	UserProfileRepository dbProfile;
	@PersistenceContext
	EntityManager entityManager;
	@Autowired
	BtSiteDetailsRepository dbSite;
	@Autowired
	JdbcTemplate jdbc;
	@Autowired
	BtProductsRepository dbProduct;
	@Autowired
	AccountsRepository dbAccount;
	@Autowired
	com.pratikshat.exicom.repository.AssetsStatus dbassetSt;

	@Override
	public Map<String, Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException {
		if (id == null)
			throw new BadRequestException("Invalid product id");
		boolean view = dbProfile.hasObjectAccess(user.getUsername(), BtAssets.class, UserProfileRepository.VIEW);
		boolean viewAll = dbProfile.hasObjectAccess(user.getUsername(), BtAssets.class, UserProfileRepository.VIEW_ALL);
		if (view || viewAll) {
			Long _id = Long.parseLong(id.toString());
			return dbAssets.getSingleAssets(_id);
		} else
			throw new UnauthoriseException("You are not authorised");
	}
	
	@Override
	public Map<String, Object> getByAssetsSerialNumber(String id, LoginUser user) throws BadRequestException, UnauthoriseException {
//		if (id == null)
//			throw new BadRequestException("Invalid product id");
//		boolean view = dbProfile.hasObjectAccess(user.getUsername(), BtSales.class, UserProfileRepository.VIEW);
//		boolean viewAll = dbProfile.hasObjectAccess(user.getUsername(), BtSales.class, UserProfileRepository.VIEW_ALL);
//		if (view || viewAll) {
			return dbAssets.getByAssetsSerialNumber(id);
//		} else
//			throw new UnauthoriseException("You are not authorised");
	}

	@Override
	public Page<Map<String, Object>> getPage(Pageable page, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		boolean view = dbProfile.hasObjectAccess(user.getUsername(), BtAssets.class, UserProfileRepository.VIEW);
		boolean viewAll = dbProfile.hasObjectAccess(user.getUsername(), BtAssets.class, UserProfileRepository.VIEW_ALL);
		if (view || viewAll) {
			return dbAssets.getPage(page);
		} else
			throw new UnauthoriseException("You are not authorised");

	}

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = { RuntimeException.class,BadRequestException.class,UnauthoriseException.class,Exception.class}, propagation = Propagation.REQUIRES_NEW)
	public Map<String, Object> saveObject(Map<String, Object> assets, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		boolean assetsC = dbProfile.hasObjectAccess(user.getUsername(), BtAssets.class, UserProfileRepository.CREATE);
		boolean asstW = dbProfile.hasObjectAccess(user.getUsername(), BtAssetsWarrantyDetails.class,
				UserProfileRepository.CREATE);
		boolean asstP = dbProfile.hasObjectAccess(user.getUsername(), BtAssetsPurchaseDetails.class,
				UserProfileRepository.CREATE);
		boolean asstA = dbProfile.hasObjectAccess(user.getUsername(), BtAssetsAmcdetails.class,
				UserProfileRepository.CREATE);
		BtAssets btAssets = null;
		if (assetsC || asstW || asstP || asstA) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (assetsC) {
				if (assets.get("serialNumber").toString() == null
						|| assets.get("serialNumber").toString().equalsIgnoreCase("")) {
					throw new BadRequestException("Serial Number is blank");
				}

				if (assets.get("productId") == null) {
					throw new BadRequestException("Part code id is blank");
				}

				if (assets.get("accountSapNumber").toString() == null
						|| assets.get("accountSapNumber").toString().equalsIgnoreCase("")) {
					throw new BadRequestException("Account is blank");
				}

				if ((int) assets.get("assetsStatusId") == 0) {
					throw new BadRequestException("Status is blank");
				}

				btAssets = new BtAssets();

				if ( assets.get("siteId") != null) {
					int num = (Integer) assets.get("siteId");
					BtSiteDetails btSiteDetails = dbSite.getSiteWithId(Long.valueOf(num));
					if (btSiteDetails == null) {
						throw new BadRequestException("Site does not exist");
					}
					btAssets.setSiteDetails(btSiteDetails);
				}

				int num = (Integer) assets.get("productId");
				BtProducts btProducts = dbProduct.getProductWithId(Long.valueOf(num));
				if (btProducts == null) {
					throw new BadRequestException("Product does not exist");
				}
				btAssets.setProducts(btProducts);

				Accounts accounts = dbAccount.getWithId(assets.get("accountSapNumber").toString());

				if (accounts == null) {
					throw new BadRequestException("Account does not exist");
				}

				btAssets.setAccount(accounts);
				if (accounts.getParentCustomer() != null) {
					btAssets.setCustomer(accounts.getParentCustomer().getCustomerName());
				}

				if (assets.get("primaryAccountSapNumber") != null
						&& !assets.get("primaryAccountSapNumber").toString().equalsIgnoreCase("")) {
					Accounts priamryAccounts = dbAccount.getWithId(assets.get("primaryAccountSapNumber").toString());
					if (priamryAccounts == null) {
						throw new BadRequestException("Primary account does not exist");
					}
					btAssets.setPrimaryAccount(priamryAccounts);
				}

				AssetsStatus assetsStatus = dbassetSt.getWithId((Integer) assets.get("assetsStatusId"));

				if (assetsStatus == null) {
					throw new BadRequestException("Status does not exist");
				}

				btAssets.setStatus(assetsStatus);
				if (assets.get("plantCode") != null && !assets.get("plantCode").toString().equalsIgnoreCase("")) {
					btAssets.setPlant(assets.get("plantCode").toString());
				}
				if (assets.get("remarks") != null && !assets.get("remarks").toString().equalsIgnoreCase("")) {
					btAssets.setRemarks(assets.get("remarks").toString());
				}

				btAssets.setManualId(assets.get("serialNumber").toString());
				btAssets.setOwner(new EmployeeDetails(user.getUsername()));
				btAssets.setSource("CRM");
				dbAssets.saveDetails(btAssets, user);
			}
			if (asstW) {
				if (btAssets != null) {
					BtAssetsWarrantyDetails assetsWarrantyDetails = new BtAssetsWarrantyDetails();
					if (assets.get("warrantyFromDateOfDeliveryInMonth") != null
							&& !assets.get("warrantyFromDateOfDeliveryInMonth").toString().equalsIgnoreCase("")) {
						assetsWarrantyDetails.setWarrantyFromDateOfDeliveryInMonth(
								(int) assets.get("warrantyFromDateOfDeliveryInMonth"));
					}
					if (assets.get("warrantyFromDateOfInstallationInMonth") != null
							&& !assets.get("warrantyFromDateOfInstallationInMonth").toString().equalsIgnoreCase("")) {
						assetsWarrantyDetails.setWarrantyFromDateOfInstallationInMonth(
								(int) assets.get("warrantyFromDateOfInstallationInMonth"));
					}
					if(assets.get("purchaseDate")!=null && assets.get("installDate")!=null) {
						String purchaseDateString =assets.get("purchaseDate").toString();
						Date purchaseDate;
						try {
							purchaseDate = sdf.parse(purchaseDateString);
							String installDateString = assets.get("installDate").toString();
							Date installDate = sdf.parse(installDateString);
							if(installDate.getTime()-purchaseDate.getTime()<0) {
								throw new BadRequestException("Installation date must be greater than purchase date");
							}
							assetsWarrantyDetails.setInstallDate(installDate);
						} catch (ParseException e) {
							e.printStackTrace();
						}	
					}
					try {
						Date xpBypDate=null;
						Date xpByInstDate=null;
						if (assets.get("installDate") != null) {
							Calendar cl=Calendar.getInstance();
							cl.setTime(sdf.parse(assets.get("installDate").toString()));
							cl.add(Calendar.MONTH,(int)assets.get("warrantyFromDateOfInstallationInMonth"));
							cl.add(Calendar.HOUR_OF_DAY,-24);
							xpByInstDate=cl.getTime();
						}
						if (assets.get("purchaseDate") != null) {
							Calendar cl=Calendar.getInstance();
							cl.setTime(sdf.parse(assets.get("purchaseDate").toString()));
							cl.add(Calendar.MONTH,(int)assets.get("warrantyFromDateOfDeliveryInMonth"));
							cl.add(Calendar.HOUR_OF_DAY,-24);
							xpBypDate=cl.getTime();
							
							assetsWarrantyDetails.setPurchaseDate(sdf.parse(assets.get("purchaseDate").toString()));
							
						}
						
						if(xpByInstDate==null && xpBypDate!=null) {
							assetsWarrantyDetails.setWarrantyExpiryDate(xpBypDate);
							assetsWarrantyDetails.setDateOfDeliveryExpiryDate(xpBypDate);
						}else if(xpBypDate==null && xpByInstDate!=null) {
							assetsWarrantyDetails.setInstallExpiryDate(xpByInstDate);
							assetsWarrantyDetails.setWarrantyExpiryDate(xpByInstDate);
						} else if(xpBypDate!=null && xpByInstDate!=null) {
							assetsWarrantyDetails.setInstallExpiryDate(xpByInstDate);
							assetsWarrantyDetails.setDateOfDeliveryExpiryDate(xpBypDate);
							if(xpBypDate.getTime()>xpByInstDate.getTime()) {
								assetsWarrantyDetails.setWarrantyExpiryDate(xpByInstDate);
							}else {
								assetsWarrantyDetails.setWarrantyExpiryDate(xpBypDate);
							}
						}
						if (assets.get("dateOfDeliveryExpiryDate") != null) {
							assetsWarrantyDetails.setDateOfDeliveryExpiryDate(
									sdf.parse(assets.get("dateOfDeliveryExpiryDate").toString()));
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
					assetsWarrantyDetails.setBtAssets(btAssets);
					assetsWarrantyDetails.setOwner(new EmployeeDetails(user.getUsername()));
					dbAssetswarranty.saveDetails(assetsWarrantyDetails, user);
				}
			}
			if (asstP) {
				if (btAssets != null) {
					BtAssetsPurchaseDetails assetsPurchaseDetails = new BtAssetsPurchaseDetails();
					if (assets.get("purchaseOrderNumber") != null
							&& !assets.get("purchaseOrderNumber").toString().equalsIgnoreCase("")) {
						assetsPurchaseDetails.setPurchaseOrderNumber(assets.get("purchaseOrderNumber").toString());
					}
					try {
						if(assets.get("purchaseOrderDate")!=null) {
						assetsPurchaseDetails
								.setPurchaseOrderDate(sdf.parse(assets.get("purchaseOrderDate").toString()));}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (assets.get("sapDeliveryDocumentNumber") != null
							&& !assets.get("sapDeliveryDocumentNumber").toString().equalsIgnoreCase("")) {
						assetsPurchaseDetails
								.setSapDeliveryDocumentNumber(assets.get("sapDeliveryDocumentNumber").toString());
					} 
					if (assets.get("sapMaterialDocumentNumber") != null
							&& !assets.get("sapMaterialDocumentNumber").toString().equalsIgnoreCase("")) {
						assetsPurchaseDetails
								.setSapMaterialDocumentNumber(assets.get("sapMaterialDocumentNumber").toString());
					} 
					if (assets.get("salesDocNumber") != null
							&& !assets.get("salesDocNumber").toString().equalsIgnoreCase("")) {
						assetsPurchaseDetails.setSalesDocNumber(assets.get("salesDocNumber").toString());
					} 
					assetsPurchaseDetails.setOwner(new EmployeeDetails(user.getUsername()));
					assetsPurchaseDetails.setAssets(btAssets);
					dbAssetspurchase.saveDetails(assetsPurchaseDetails, user);
				}
			}
			if (asstA) {
				if (btAssets != null) {
					BtAssetsAmcdetails amcdetails = new BtAssetsAmcdetails();
					if (assets.get("amcPeriod") != null && !assets.get("amcPeriod").toString().equalsIgnoreCase("")) {
						amcdetails.setAmcPeriod((int) assets.get("amcPeriod"));
					}
					try {
						
						if(assets.get("amcStartDate") != null) {
							Calendar cl=Calendar.getInstance();
							cl.setTime(sdf.parse(assets.get("amcStartDate").toString()));
							cl.add(Calendar.MONTH,(int)assets.get("amcPeriod"));
							cl.add(Calendar.HOUR_OF_DAY,-24);
							amcdetails.setAmcExpiryDate(cl.getTime());
						}
						if(assets.get("amcStartDate") != null && assets.get("amcPurchaseOrderDate") != null ) {
							String amcPurchaseOrderDateString = assets.get("amcPurchaseOrderDate").toString();
							Date amcPurchaseOrderDate = sdf.parse(amcPurchaseOrderDateString);
							
							String amcStartDateString = assets.get("amcStartDate").toString();
							Date amcStartDate = sdf.parse(amcStartDateString);
							if(amcStartDate.getTime()-amcPurchaseOrderDate.getTime()<0) {
								throw new BadRequestException("AMC start date must be greater than AMC purchase date");
							}
							
						amcdetails.setAmcStartDate(amcStartDate);
						}
					} catch (ParseException e) {

						e.printStackTrace();
					}
					if(assets.get("amcPurchaseOrderDate") != null && !assets.get("amcPurchaseOrderDate").toString().equalsIgnoreCase("")) {
					amcdetails.setAmcPurchaseOrderDate((String) assets.get("amcPurchaseOrderDate"));
					}
					if (assets.get("amcPurchaseOrderNumber") != null
							&& !assets.get("amcPurchaseOrderNumber").toString().equalsIgnoreCase("")) {
						amcdetails.setAmcPurchaseOrderNumber(assets.get("amcPurchaseOrderNumber").toString());
					} 
					amcdetails.setBtAssets(btAssets);
					amcdetails.setOwner(new EmployeeDetails(user.getUsername()));
					dbAssetsamc.saveDetails(amcdetails, user);
				}
			}
		} else
			throw new UnauthoriseException("You are not authorise");
		return dbAssets.getSingleAssets(btAssets.getId());
	}

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = { RuntimeException.class,BadRequestException.class,UnauthoriseException.class,Exception.class}, propagation = Propagation.REQUIRES_NEW)
	public Map<String, Object> updateObject(Map<String, Object> assets, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		boolean assetsC = dbProfile.hasObjectAccess(user.getUsername(), BtAssets.class, UserProfileRepository.EDIT);
		boolean asstW = dbProfile.hasObjectAccess(user.getUsername(), BtAssetsWarrantyDetails.class,
				UserProfileRepository.EDIT);
		boolean asstP = dbProfile.hasObjectAccess(user.getUsername(), BtAssetsPurchaseDetails.class,
				UserProfileRepository.EDIT);
		boolean asstA = dbProfile.hasObjectAccess(user.getUsername(), BtAssetsAmcdetails.class,
				UserProfileRepository.EDIT);
		BtAssets btAssets = null;
		if (assetsC || asstW || asstP || asstA) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (assetsC) {
				System.out.println("Assets C");
				long num = Long.parseLong(assets.get("assetsId").toString());
				btAssets = dbAssets.getWithId(num);



				if (assets.get("siteId") != null) {
					int num1 = (Integer) assets.get("siteId");
					BtSiteDetails btSiteDetails = dbSite.getSiteWithId(Long.valueOf(num1));
					btAssets.setSiteDetails(btSiteDetails);
					if (btSiteDetails == null) {
						throw new BadRequestException("Site does not exist");
					}

					btAssets.setSiteDetails(btSiteDetails);
				}

				if (assets.get("productId") != null
						&& !assets.get("productId").toString().equalsIgnoreCase("")) {
					BtProducts btProducts = null;
					int num2 = (Integer) assets.get("productId");
					btProducts = dbProduct.getProductWithId(Long.valueOf(num2));
					if (btProducts == null) {
						throw new BadRequestException("Product does not exist");
					}
					btAssets.setProducts(btProducts);
				}
				if (assets.get("accountSapNumber") != null
						&& !assets.get("accountSapNumber").toString().equalsIgnoreCase("")) {
					Accounts accounts = null;
					accounts = dbAccount.getWithId(assets.get("accountSapNumber").toString());
					if (accounts == null) {
						throw new BadRequestException("Account does not exist");
					}
					btAssets.setAccount(accounts);

					if (accounts.getParentCustomer() != null) {
						btAssets.setCustomer(accounts.getParentCustomer().getCustomerName());
					}
				}
				if (assets.get("primaryAccountSapNumber") != null
						&& !assets.get("primaryAccountSapNumber").toString().equalsIgnoreCase("")) {
					Accounts priamryAccounts = null;
					priamryAccounts = dbAccount.getWithId(assets.get("primaryAccountSapNumber").toString());
					if (priamryAccounts == null) {
						throw new BadRequestException("Primary account does not exist");
					}
					btAssets.setPrimaryAccount(priamryAccounts);
				}

				if (assets.get("assetsStatusId") != null
						&& !assets.get("assetsStatusId").toString().equalsIgnoreCase("")) {
					AssetsStatus assetsStatus = null;
					assetsStatus = dbassetSt.getWithId((int) assets.get("assetsStatusId"));
					if (assetsStatus == null) {
						throw new BadRequestException("Status does not exist");
					}
					btAssets.setStatus(assetsStatus);
				}
				if (assets.get("plantCode") != null && !assets.get("plantCode").toString().equalsIgnoreCase("")) {
					btAssets.setPlant(assets.get("plantCode").toString());
				}
				if (assets.get("remarks") != null && !assets.get("remarks").toString().equalsIgnoreCase("")) {
					btAssets.setRemarks(assets.get("remarks").toString());
				}
				if (assets.get("serialNumber") != null && !assets.get("serialNumber").toString().equalsIgnoreCase("")) {
					btAssets.setManualId(assets.get("serialNumber").toString());
				}
				btAssets.setOwner(new EmployeeDetails(user.getUsername()));
				btAssets.setSource("CRM");

				dbAssets.updateDetails(btAssets, user);
			}
			if (asstW) {
				System.out.println("Assets W");
				BtAssetsWarrantyDetails assetsWarrantyDetails = null;
				Number num = (Number) assets.get("assetsId");
				assetsWarrantyDetails = dbAssetswarranty.getWithAssetsId(num.longValue());
				if (assets.get("warrantyFromDateOfDeliveryInMonth") != null
						&& !assets.get("warrantyFromDateOfDeliveryInMonth").toString().equalsIgnoreCase("")) {
					assetsWarrantyDetails.setWarrantyFromDateOfDeliveryInMonth(
							(int) assets.get("warrantyFromDateOfDeliveryInMonth"));
				}
				if (assets.get("warrantyFromDateOfInstallationInMonth") != null
						&& !assets.get("warrantyFromDateOfInstallationInMonth").toString().equalsIgnoreCase("")) {
					assetsWarrantyDetails.setWarrantyFromDateOfInstallationInMonth(
							(int) assets.get("warrantyFromDateOfInstallationInMonth"));
				}
				try {
					if (assets.get("installDate") != null) {
						assetsWarrantyDetails.setInstallDate(sdf.parse(assets.get("installDate").toString()));
					}
					if (assets.get("purchaseDate") != null) {
						assetsWarrantyDetails.setPurchaseDate(sdf.parse(assets.get("purchaseDate").toString()));
					}
					
					
					
					Date xpBypDate=null;
					Date xpByInstDate=null;
					if (assets.get("installDate") != null) {
						Calendar cl=Calendar.getInstance();
						cl.setTime(sdf.parse(assets.get("installDate").toString()));
						cl.add(Calendar.MONTH,(int)assets.get("warrantyFromDateOfInstallationInMonth"));
						cl.add(Calendar.HOUR_OF_DAY,-24);
						xpByInstDate=cl.getTime();
					}
					if (assets.get("purchaseDate") != null) {
						Calendar cl=Calendar.getInstance();
						cl.setTime(sdf.parse(assets.get("purchaseDate").toString()));
						cl.add(Calendar.MONTH,(int)assets.get("warrantyFromDateOfDeliveryInMonth"));
						cl.add(Calendar.HOUR_OF_DAY,-24);
						xpBypDate=cl.getTime();
					
					}
					
					if(xpByInstDate==null && xpBypDate!=null) {
						assetsWarrantyDetails.setWarrantyExpiryDate(xpBypDate);
						assetsWarrantyDetails.setDateOfDeliveryExpiryDate(xpBypDate);
					}else if(xpBypDate==null && xpByInstDate!=null) {
						assetsWarrantyDetails.setInstallExpiryDate(xpByInstDate);
						assetsWarrantyDetails.setWarrantyExpiryDate(xpByInstDate);
					} else if(xpBypDate!=null && xpByInstDate!=null) {
						assetsWarrantyDetails.setInstallExpiryDate(xpByInstDate);
						assetsWarrantyDetails.setDateOfDeliveryExpiryDate(xpBypDate);
						if(xpBypDate.getTime()>xpByInstDate.getTime()) {
							assetsWarrantyDetails.setWarrantyExpiryDate(xpByInstDate);
						}else {
							assetsWarrantyDetails.setWarrantyExpiryDate(xpBypDate);
						}
					}
					
					
				

					if (assets.get("dateOfDeliveryExpiryDate") != null) {
						assetsWarrantyDetails.setDateOfDeliveryExpiryDate(
								sdf.parse(assets.get("dateOfDeliveryExpiryDate").toString()));	
					}
				
					if (assets.get("warrantyExpiryDate") != null) {
						assetsWarrantyDetails
								.setWarrantyExpiryDate(sdf.parse(assets.get("warrantyExpiryDate").toString()));
					}
					
					if (assets.get("installExpiryDate") != null) {
						assetsWarrantyDetails
								.setInstallExpiryDate(sdf.parse(assets.get("installExpiryDate").toString()));
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
				assetsWarrantyDetails.setBtAssets(btAssets);
				assetsWarrantyDetails.setOwner(new EmployeeDetails(user.getUsername()));
				dbAssetswarranty.updateDetails(assetsWarrantyDetails, user);
			}
			if (asstP) {
				System.out.println("Assets P");
				Number num = (Number) assets.get("assetsId");
				BtAssetsPurchaseDetails assetsPurchaseDetails = dbAssetspurchase.getWithId(num.longValue());
				if (assets.get("purchaseOrderNumber") != null
						&& !assets.get("purchaseOrderNumber").toString().equalsIgnoreCase("")) {
					
					assetsPurchaseDetails.setPurchaseOrderNumber(assets.get("purchaseOrderNumber").toString());
				}
				try {
					if(assets.get("purchaseOrderDate")!=null) {
					assetsPurchaseDetails
							.setPurchaseOrderDate(sdf.parse(assets.get("purchaseOrderDate").toString()));}
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if (assets.get("sapDeliveryDocumentNumber") != null
						&& !assets.get("sapDeliveryDocumentNumber").toString().equalsIgnoreCase("")) {
					assetsPurchaseDetails
							.setSapDeliveryDocumentNumber(assets.get("sapDeliveryDocumentNumber").toString());
				}
				if (assets.get("sapMaterialDocumentNumber") != null
						&& !assets.get("sapMaterialDocumentNumber").toString().equalsIgnoreCase("")) {
					assetsPurchaseDetails
							.setSapMaterialDocumentNumber(assets.get("sapMaterialDocumentNumber").toString());
				}
				if (assets.get("salesDocNumber") != null
						&& !assets.get("salesDocNumber").toString().equalsIgnoreCase("")) {
					assetsPurchaseDetails.setSalesDocNumber(assets.get("salesDocNumber").toString());
				}

				assetsPurchaseDetails.setOwner(new EmployeeDetails(user.getUsername()));
				assetsPurchaseDetails.setAssets(btAssets);
				dbAssetspurchase.updateDetails(assetsPurchaseDetails, user);

			}
			if (asstA) {
				System.out.println("In");
//				Number num = (Number) assets.get("assetsId");
				System.out.println(assets.get("assetsId")+"Id");
				BtAssetsAmcdetails amcdetails = dbAssetsamc.getWithId(Long.parseLong(assets.get("assetsId").toString()));
				if (assets.get("amcPeriod") != null && !assets.get("amcPeriod").toString().equalsIgnoreCase("")) {
					System.out.println(assets.get("amcPeriod")+"amc");
					amcdetails.setAmcPeriod(Integer.parseInt(assets.get("amcPeriod").toString()));
				}
				
				try {
					if(assets.get("amcExpiryDate") != null && !assets.get("amcExpiryDate").toString().equalsIgnoreCase("")) {
					amcdetails.setAmcExpiryDate(sdf.parse(assets.get("amcExpiryDate").toString()));
					}
					if(assets.get("amcStartDate") != null && !assets.get("amcStartDate").toString().equalsIgnoreCase("")) {
					amcdetails.setAmcStartDate(sdf.parse(assets.get("amcStartDate").toString()));
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if(assets.get("amcPurchaseOrderDate") != null && !assets.get("amcPurchaseOrderDate").toString().equalsIgnoreCase("")) {
				amcdetails.setAmcPurchaseOrderDate((String) assets.get("amcPurchaseOrderDate"));
				}
				if (assets.get("amcPurchaseOrderNumber") != null
						&& !assets.get("amcPurchaseOrderNumber").toString().equalsIgnoreCase("")) {
					amcdetails.setAmcPurchaseOrderNumber(assets.get("amcPurchaseOrderNumber").toString());
				}
				
				amcdetails.setBtAssets(btAssets);
				amcdetails.setOwner(new EmployeeDetails(user.getUsername()));
				dbAssetsamc.updateDetails(amcdetails, user);
			}
		} else
			throw new UnauthoriseException("You are not authorise");

		return dbAssets.getSingleAssets(btAssets.getId());
	}

	@Override
	public List<Map<String, Object>> searchForLists(String search, Pageable pageable)
			throws BadRequestException, UnauthoriseException {
					Page<Map<String, Object>> list=  dbAssets.getBtAssetsForListSmall("%" +search+ "%",pageable==null?PageRequest.of(0, 20):pageable);
					return list.getContent();
	}
	
	@Override
	public Map<String, Object> searchForPartcode(long search,LoginUser user) {
		List<Object> value=new ArrayList<>();
		String _query = "SELECT btAssets.`id` AS id,"
				+ "				btAssets.`manual_id` AS serialNumber,\r\n"
				+ "				btP.`manual_id` AS productPartCode, "
				+ "				btP.`product_description` AS productDescription,\r\n"
				+ "				btAssetsWar.`install_date` AS installtionDate ,btAssetsWar.`install_expiry_date` AS expiryDate,\r\n"
				+ "				(CASE\r\n"
				+ "				WHEN btAssetsWar.`install_expiry_date` > NOW()  THEN 'Under Warranty'\r\n"
				+ "				ELSE 'Without Warranty'\r\n"
				+ "				END)AS warrantyStatus \r\n"
				+ "				FROM `bt_assets` btAssets \r\n"
				+ "				JOIN `bt_products` btP ON btAssets.`products_id`=btP.`id`\r\n"
				+ "				JOIN `bt_assets_warranty_details` btAssetsWar ON btAssetsWar.`id` =btAssets.`id`";
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(_query);
		stringBuilder.append("	where btAssets.`id`= ? ");
		value.add(search);
		return jdbc.queryForMap(stringBuilder.toString(),value.toArray());
	}

	@Override
	public List<Map<String, Object>> advanceSearch(Map<String, Object> searchKeys, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		SimpleDateFormat dformatter = new SimpleDateFormat("yyyy-mm-dd");
		List<Object> value = new ArrayList<>();
		String _query = "SELECT btAssets.id,btAssets.`manual_id` AS serialNumber,btAssets.`remarks` AS remarks,btAssets.`last_modified_by_email_id` AS modifiedByEmailId,\r\n"
				+ "				btAssets.`last_modified_date` AS modifiedDate,btAssets.`created_date` AS createdDate,cby.`email_id` AS createdByEmailId,onr.`email_id` AS ownerEmailId,\r\n"
				+ "				subProduct.`product_faily_sub_product_name` AS productSubProductName ,subProduct.`product_family_sub_product_id` AS productSubProductId,\r\n"
				+ "				pFamily.`product_family_id` AS productFamilyId,pFamily.`product_family_name` AS productFamilyName,\r\n"
				+ "				btP.`manual_id` AS productPartCode,btP.`id` AS productID,btP.`product_type` AS productType, btP.`product_description` AS productDescription,\r\n"
				+ "			 	btSite.`id`AS siteId,btSite.`site_name`AS siteName ,btSite.`manual_id`AS siteManualId,\r\n"
				+ "				acc.`name` AS accountName,acc.`sap_customer_number` AS accountSapNumber,\r\n"
				+ "				pacc.`name` AS primaryAccountName, pacc.`sap_customer_number` AS primaryAccountSapNumber,\r\n"
				+ "				st.`assets_status_name` AS assetsStatusName,st.`assets_status_id` AS assetsStatusId,\r\n"
				+ "				amc.`amc_purchase_order_number`AS amcPurchaseOrderNumber,amc.`amc_purchase_order_date` AS amcPurchaseOrderDate,\r\n"
				+ "				amc.`amc_expiry_date` AS amcExpiryDate ,amc.`amc_start_date`AS amcStartDate,amc.`amc_period` AS amcPeriod,\r\n"
				+ "				warr.`install_date` AS installDate, warr.`warranty_from_date_of_delivery_in_month` AS warrantyFromDateOfDeliveryInMonth,\r\n"
				+ "				warr.`warranty_from_date_of_installation_in_month` AS warrantyFromDateOfInstallationInMonth,\r\n"
				+ "				warr.`install_expiry_date` AS installExpiryDate,warr.`date_of_delivery_expiry_date`AS dateOfDeliveryExpiryDate,\r\n"
				+ "				warr.`warranty_expiry_date`AS warrantyExpiryDate,warr.`purchase_date` AS purchaseDate,\r\n"
				+ "				purchase.`purchase_order_number` AS purchaseOrderNumber,purchase.`purchase_order_date` AS purchaseOrderDate,\r\n"
				+ "				purchase.`sap_delivery_document_number` AS sapDeliveryDocumentNumber,\r\n"
				+ "				purchase.`sap_material_document_number` AS sapMaterialDocumentNumber,\r\n"
				+ "				circle.`circle_name` AS circleName,\r\n" + "				(CASE \r\n"
				+ "					WHEN warr.`warranty_expiry_date`>NOW() THEN TRUE\r\n"
				+ "					ELSE FALSE\r\n" + "				END) AS `isWarrantyExist`,\r\n"
				+ "				(CASE \r\n" + "			 		WHEN amc.`amc_expiry_date`>NOW() THEN TRUE\r\n"
				+ "				ELSE FALSE\r\n" + "				END) AS `isAMCWarrantyExist`,\r\n"
				+ "				(CASE \r\n"
				+ "					WHEN warr.`warranty_expiry_date` IS NOT NULL AND warr.`warranty_expiry_date`>NOW() THEN 'Under Warranty'\r\n"
				+ "					WHEN amc.`amc_expiry_date` IS NOT NULL AND amc.`amc_expiry_date`>NOW() THEN 'Under AMC'\r\n"
				+ "					WHEN warr.`warranty_expiry_date` IS NOT NULL AND amc.`amc_expiry_date` IS NOT NULL AND warr.`warranty_expiry_date`>NOW() AND amc.`amc_expiry_date`>NOW() THEN 'Under Warranty'\r\n"
				+ "					ELSE 'Without Warranty/AMC'\r\n" + "				END) AS `Warranty`,\r\n"
				+ "				CONCAT(IFNULL(cby.`first_name`,''),IFNULL(cby.`middle_name`,''),' ',IFNULL(cby.`last_name`,'')) AS `creatrdByName`,\r\n"
				+ "				CONCAT(IFNULL(mby.`first_name`,''),IFNULL(mby.`middle_name`,''),' ',IFNULL(mby.`last_name`,'')) AS `modifiedByName`,\r\n"
				+ "				CONCAT(IFNULL(onr.`first_name`,''),IFNULL(onr.`middle_name`,''),' ',IFNULL(onr.`last_name`,'')) AS `onwerName`\r\n"
				+ "				\r\n"
				+ "			FROM `bt_assets` btAssets JOIN `bt_products` btP ON btAssets.`products_id`=btP.`id`\r\n"
				+ "			LEFT JOIN `product_family_sub_product` subProduct ON btP.`sub_product_family_product_family_sub_product_id`=subProduct.`product_family_sub_product_id`\r\n"
				+ "			LEFT JOIN `product_family` pFamily ON subProduct.`product_family_product_family_id` =pFamily.`product_family_id`\r\n"
				+ "			LEFT JOIN `bt_site_details` btSite ON btAssets.`site_details_id` =btSite.`id`\r\n"
				+ "			LEFT JOIN `circle_details` circle ON btSite.`circle_circle_name`=circle.`circle_name`\r\n"
				+ "			JOIN `accounts` acc ON btAssets.`account_sap_customer_number` =acc.`sap_customer_number`\r\n"
				+ "			LEFT JOIN `accounts` pacc ON btAssets.`primary_account_sap_customer_number`=pacc.`sap_customer_number`\r\n"
				+ "			LEFT JOIN `assets_status` st ON btAssets.`status_assets_status_id` =st.`assets_status_id`\r\n"
				+ "			JOIN `employee_details` onr ON btAssets.`owner_email_id`=onr.`email_id`\r\n"
				+ "			JOIN `employee_details` cby ON btAssets.`created_by_email_id`=cby.`email_id`\r\n"
				+ "			JOIN `employee_details` mby ON btAssets.`last_modified_by_email_id`=mby.`email_id`\r\n"
				+ "			LEFT JOIN `bt_assets_amcdetails` amc ON amc.`id`=btAssets.`id`\r\n"
				+ "			LEFT JOIN `bt_assets_purchase_details`  purchase ON purchase.`id`=btAssets.`id`\r\n"
				+ "			LEFT JOIN `bt_assets_warranty_details` warr ON warr.`id`=btAssets.`id`\r\n"
				+ "	        WHERE 1=1";
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(_query);
		if (searchKeys.get("serialNumber") != null && !searchKeys.get("serialNumber").equals("")) {
			queryBuilder.append(" and btAssets.`manual_id` like ?");
			value.add("%" + searchKeys.get("serialNumber") + "%");

		}
		if (searchKeys.get("siteName") != null && !searchKeys.get("siteName").equals("")) {
			queryBuilder.append(" and btSite.`site_name` like ?");
			value.add("%" + searchKeys.get("siteName") + "%");
		}
		if (searchKeys.get("productPartCode") != null && !searchKeys.get("productPartCode").equals("")) {
			queryBuilder.append(" and btP.`manual_id` like?");
			value.add("%" + searchKeys.get("productPartCode") + "%");
		}
		if (searchKeys.get("productDescription") != null && !searchKeys.get("productDescription").equals("")) {
			queryBuilder.append(" and btP.`product_description` like ?");
			value.add("%" + searchKeys.get("productDescription") + "%");
		}

		if (searchKeys.get("circleName") != null && !searchKeys.get("circleName").equals("")) {
			queryBuilder.append(" and circle.`circle_name` like ?");
			value.add("%" + searchKeys.get("circleName") + "%");
		}
		if (searchKeys.get("accountName") != null && !searchKeys.get("accountName").equals("")) {
			queryBuilder.append(" and acc.`name` like ?");
			value.add("%" + searchKeys.get("accountName") + "%");
		}

		return jdbc.queryForList(queryBuilder.toString(), value.toArray());
	}

	public List<Map<String, Object>> searchPartCode(String searchkey, LoginUser user) {
		 Page<Map<String, Object>> list= dbAssets.getBtProductsForListData(searchkey, null);
		 return list.getContent();
	}

	@Override
	public List<Map<String, Object>> searchForPartcodes(String productManualId, String assetManualId, LoginUser user) {
		List<Object> value=new ArrayList<>();
		String _query = "SELECT btAssets.`id` AS id,"
				+ "				btAssets.`manual_id` AS serialNumber,\r\n"
				+ "				btP.`manual_id` AS productPartCode, "
				+ "				btP.`product_description` AS productDescription,\r\n"
				+ "				btAssetsWar.`install_date` AS installtionDate ,btAssetsWar.`install_expiry_date` AS expiryDate,\r\n"
				+ "				(CASE\r\n"
				+ "				WHEN btAssetsWar.`install_expiry_date` > NOW()  THEN 'Under Warranty'\r\n"
				+ "				ELSE 'Without Warranty'\r\n"
				+ "				END)AS warrantyStatus \r\n"
				+ "				FROM `bt_assets` btAssets \r\n"
				+ "				JOIN `bt_products` btP ON btAssets.`products_id`=btP.`id`\r\n"
				+ "				LEFT JOIN `bt_assets_warranty_details` btAssetsWar ON btAssetsWar.`id` =btAssets.`id`";
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(_query);
		stringBuilder.append("	WHERE btP.`manual_id` LIKE  '"+productManualId+"'");
		System.out.println(assetManualId+" manual asset");
		if(assetManualId !=null && !assetManualId.equalsIgnoreCase("")) {
		stringBuilder.append(" AND btAssets.`manual_id` LIKE "+" '%"+assetManualId+"%'");
		}
		System.out.println(stringBuilder);
		return jdbc.queryForList(stringBuilder.toString());
	}

	@Override
	public List<Map<String, Object>> searchForList(String search, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	
}
