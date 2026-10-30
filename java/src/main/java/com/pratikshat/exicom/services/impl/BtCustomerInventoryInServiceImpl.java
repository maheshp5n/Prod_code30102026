package com.pratikshat.exicom.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.NoResultException;


import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.BtAssets;
import com.pratikshat.exicom.entity.BtAssetsWarrantyDetails;
import com.pratikshat.exicom.entity.BtCase;
import com.pratikshat.exicom.entity.BtCustomerInventoryIn;
import com.pratikshat.exicom.entity.BtCustomerInventoryOut;
import com.pratikshat.exicom.entity.BtProducts;
import com.pratikshat.exicom.entity.BtRMADetails;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.ServiceRecource;

import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.BtAssetsRepository;
import com.pratikshat.exicom.repository.BtAssetsWarrantyDetailsRepository;
import com.pratikshat.exicom.repository.BtCaseRepository;
import com.pratikshat.exicom.repository.BtCustomerInventoryInRepository;
import com.pratikshat.exicom.repository.BtCustomerInventoryOutRepository;
import com.pratikshat.exicom.repository.BtProductItemRepository;
import com.pratikshat.exicom.repository.BtProductsRepository;
import com.pratikshat.exicom.repository.BtRmaDetailsRepository;
import com.pratikshat.exicom.repository.ServiceRecourseRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtCustomerInventoryInService;
import com.pratikshat.exicom.utils.DateConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BtCustomerInventoryInServiceImpl implements BtCustomerInventoryInService {

        @Autowired
        BtCustomerInventoryInRepository dbIn;

        @Autowired
        BtCustomerInventoryOutRepository dbOut;

        @Autowired
        UserProfileRepository dbUserprofile;

        @Autowired
        ServiceRecourseRepository dbServiceResource;

        @Autowired
        BtRmaDetailsRepository dbRma;

        @Autowired
        BtProductItemRepository dbStock;

        @Autowired
        BtProductsRepository dbProduct;

        @Autowired
        BtAssetsRepository dbAssets;

        @Autowired
        BtAssetsWarrantyDetailsRepository dbAssetsWarranty;

        @Autowired
        BtCaseRepository dbCase;

        private static final String UNAUTHORISED_REQUEST = "You are not authorised";
        private static final Logger LOGGER=LoggerFactory.getLogger(BtCustomerInventoryInServiceImpl.class);

        @Override
        public Map<String, Object> getSingle(Object id, LoginUser user)
                        throws BadRequestException, UnauthoriseException {
                if (dbUserprofile.hasObjectAccess(user.getUsername(), BtCustomerInventoryIn.class,
                                UserProfileRepository.VIEW_ALL))
                        return dbIn.getSingle((Long) id);
                else if (dbUserprofile.hasObjectAccess(user.getUsername(), BtCustomerInventoryIn.class,
                                UserProfileRepository.VIEW))
                        return dbIn.getSingle((Long) id, dbUserprofile.getLocationAndChildLocation(user.getUsername()));
                else
                        throw new UnauthoriseException(BtCustomerInventoryInServiceImpl.UNAUTHORISED_REQUEST);
        }

        @Override
        public Page<Map<String, Object>> getPage(Pageable page, LoginUser user)
                        throws BadRequestException, UnauthoriseException {
                if (dbUserprofile.hasObjectAccess(user.getUsername(), BtCustomerInventoryIn.class,
                                UserProfileRepository.VIEW_ALL))
                        return dbIn.getPage(page);
                else if (dbUserprofile.hasObjectAccess(user.getUsername(), BtCustomerInventoryIn.class,
                                UserProfileRepository.VIEW))
                        return dbIn.getPage(page, dbUserprofile.getLocationAndChildLocation(user.getUsername()));
                else
                        throw new UnauthoriseException(BtCustomerInventoryInServiceImpl.UNAUTHORISED_REQUEST);
        }

        @Override
        public Page<Map<String,Object>> getPageByStatus(LoginUser user,Pageable page,String status) throws BadRequestException,UnauthoriseException{
                if (dbUserprofile.hasObjectAccess(user.getUsername(), BtCustomerInventoryIn.class,
                UserProfileRepository.VIEW_ALL))
        return dbIn.getPageByPaseStatus(status,page);
else if (dbUserprofile.hasObjectAccess(user.getUsername(), BtCustomerInventoryIn.class,
                UserProfileRepository.VIEW))
        return dbIn.getPageByPaseStatus(status,page, dbUserprofile.getLocationAndChildLocation(user.getUsername()));
else
        throw new UnauthoriseException(BtCustomerInventoryInServiceImpl.UNAUTHORISED_REQUEST);
        }

        @Override
        @Transactional(rollbackFor = { BadRequestException.class, UnauthoriseException.class, RuntimeException.class,
                        Exception.class }, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
        public Map<String, Object> saveObject(Map<String, Object> request, LoginUser user)
                        throws BadRequestException, UnauthoriseException {
                if (!dbUserprofile.hasObjectAccess(user.getUsername(), BtCustomerInventoryIn.class,
                                UserProfileRepository.CREATE))
                        throw new UnauthoriseException(BtCustomerInventoryInServiceImpl.UNAUTHORISED_REQUEST);
                BtCustomerInventoryIn _check = null;
                ServiceRecource sr = dbServiceResource.getByEmail(user.getUsername());
                try {
                        _check = dbIn.getByProductPartcodeAndSerialNumber(request.get("partCode").toString(),
                                        request.get("assetsSerialNumber").toString());
                } catch (NoResultException e) {

                }

                if (_check != null && !_check.getReturnedToCustomer())
                        throw new BadRequestException("Part code " + request.get("partCode").toString()
                                        + " and serial number " + request.get("assetsSerialNumber").toString()
                                        + " is already in stock, please provide different serial number or part code");
                BtProducts product = dbProduct.findByManualId(request.get("partCode").toString().trim());

                if (product == null)
                        throw new BadRequestException("Product " + request.get("partCode") + " not found");

                _check = new BtCustomerInventoryIn();
                _check.setAssetsSerialNumber(request.get("assetsSerialNumber").toString());

                _check.setInDate(new Date());
                _check.setInLocation(sr.getLocation());
                _check.setCustomerInventoryStage(request.get("customerInventoryStage") == null ? ""
                                : request.get("customerInventoryStage").toString());
                _check.setCustomerInventoryStatus(request.get("customerInventoryStatus") == null ? ""
                                : request.get("customerInventoryStatus").toString());
                _check.setContactName(request.get("contactName") == null ? "" : request.get("contactName").toString());
                _check.setContactNumber(
                                request.get("contactNumber") == null ? "" : request.get("contactNumber").toString());
                _check.setCustomerLRNumber(request.get("customerLrNumber") == null ? ""
                                : request.get("customerLrNumber").toString());
                _check.setCustomerCourierPartnarName(request.get("customerCurierPartnarName") == null ? ""
                                : request.get("customerCurierPartnarName").toString());
                _check.setCustomerPlant(
                                request.get("customerPlant") == null ? "" : request.get("customerPlant").toString());
                _check.setPackingCondation(request.get("pacingCondation") == null ? ""
                                : request.get("pacingCondation").toString());
                _check.setPhysicalDamage(
                                request.get("physicalDamage") == null ? "" : request.get("physicalDamage").toString());
                _check.setRemarks(request.get("remarks") == null ? "" : request.get("remarks").toString());

                _check.setOwner(new EmployeeDetails(user.getUsername()));
                _check.setProductPartcode(request.get("partCode").toString());
                _check.setReturnedToCustomer(false);
                _check.setBrcNumber(request.get("brcNumber") == null ? "" : request.get("brcNumber").toString());
                _check.setServiceOrderDone(false);
                _check.setServiceOrderReturn(false);
                _check.setInStock(true);
                _check.setDummayWarehouse(MiscellaneousUtils.objToString(request.get("dummayWarehouseName")));
                _check.setDummayLocation(MiscellaneousUtils.objToString(request.get("dummayLocationName")));
                LOGGER.info("reiveChallanDate from Request is :"+request.get("receiveChallanDate"));
     
                _check.setReceiveChallanDate(MiscellaneousUtils.objToString(request.get("receiveChallanDate")));
                
                LOGGER.info("reiveChallanDate After Formating is :"+_check.getReceiveChallanDate());
                
                _check.setReceiveDate(request.get("receiveDate") == null ? "" : request.get("receiveDate").toString());
                

                Date receiveDate=null;
                try {
                	Object _date=request.get("receiveDate");
                	if(_date instanceof java.util.Date )
                		receiveDate=(Date)_date;
                	else
                        receiveDate=DateConverter.convertToDate(_check.getReceiveDate());
                } catch (Exception e) {
                        LOGGER.error("ERROR IN DATE CONVERSION OF BRC CASE RECEIVE DATE {} ",_check.getReceiveDate());
                        receiveDate=new Date();
                }
                _check.setReceiveDate(DateConverter.changeDateToISOString(receiveDate));
                
                LOGGER.info("receiveDate from Request is :"+request.get("receiveDate"));
                LOGGER.info("receiveDate After Formating is :"+_check.getReceiveDate());

                BtAssets assets = dbAssets.findByManualIdAndProducts(_check.getAssetsSerialNumber(), product);

                if (assets == null)
                        throw new BadRequestException("Invalid assets serial number " + _check.getAssetsSerialNumber()
                                        + " with partcode " + product.getManualId());
                _check.setAssets(assets);

                Optional<BtAssetsWarrantyDetails> assetsWarrantyDetailsOptional = dbAssetsWarranty
                                .findById(assets.getId());
                if (assetsWarrantyDetailsOptional.isPresent()) {
                        BtAssetsWarrantyDetails assetsWarrantyDetails = assetsWarrantyDetailsOptional.get();
                        if (assetsWarrantyDetails.getWarrantyExpiryDate() != null) {
                                if (MiscellaneousUtils.daydiffrence(assetsWarrantyDetails.getWarrantyExpiryDate(),
                                                receiveDate) < 0)
                                        _check.setCaseWarranty("Under Warranty");
                                else
                                        _check.setCaseWarranty("Out of Warranty");
                        } else
                                _check.setCaseWarranty("Out of Warranty");

                }
                if (request.get("rmaManualId") == null || request.get("rmaManualId").toString().equalsIgnoreCase("")) {
                        List<BtRMADetails> rmaDetails = dbRma.getByAssetsSerialnumberAndProduct(
                                        _check.getAssetsSerialNumber(), product, "Open");
                        if (rmaDetails != null && rmaDetails.size() > 0)
                                request.put("rmaManualId", rmaDetails.get(0).getManualId());
                }

                if (request.get("rmaManualId") != null && !request.get("rmaManualId").toString().equalsIgnoreCase("")) {
                        BtRMADetails rmaDetails = dbRma.findByManualId(request.get("rmaManualId").toString());
                        if (rmaDetails == null)
                                throw new BadRequestException("Invalid RMA number " + request.get("rmaManualId"));
                        if (rmaDetails.getRmaStatus().equalsIgnoreCase("Closed"))
                                throw new BadRequestException(
                                                "RMA " + request.get("rmaManualId") + " is already closed");
                        rmaDetails.setRmaStatus("Closed");
                        rmaDetails.setLastModifiedBy(new EmployeeDetails(user.getUsername()));
                        rmaDetails.setLastModifiedDate(new Date());
                        rmaDetails.setCloseDate(new Date());
                        dbRma.updateDetails(rmaDetails, user);
                        Optional<BtCase> optionalCase = dbCase.findById(rmaDetails.getCaseDetails().getId());
                        if (optionalCase.isPresent()) {
                                BtCase btCase = optionalCase.get();
                                long rmsla = MiscellaneousUtils.daydiffrence(btCase.getCreatedDate(), new Date());
                                if (rmsla <= 30) {
                                        if (assetsWarrantyDetailsOptional.isPresent()) {
                                                BtAssetsWarrantyDetails assetsWarrantyDetails = assetsWarrantyDetailsOptional
                                                                .get();
                                                _check.setDynamicWarranty(assetsWarrantyDetails.getWarrantyExpiryDate()
                                                                .getTime() < receiveDate.getTime() ? "Out of Warranty"
                                                                                : "Under Warranty");
                                        }
                                } else {
                                        _check.setDynamicWarranty(_check.getCaseWarranty());
                                }
                        }
                        else{
                                throw new BadRequestException("Invalid RMA ID "+ request.get("rmaManualId")+", Case not found");
                        }
                        

                        _check.setRma(rmaDetails);

                }
                else{
                        _check.setDynamicWarranty(_check.getCaseWarranty());
                }

                _check.setProduct(product);
                if (request.get("accountName") != null)
                        _check.setAccountName(request.get("accountName").toString());
                if (request.get("accountSapNumber") != null)
                        _check.setAccountSapNumber(request.get("accountSapNumber").toString());

                _check.setCircleName(request.get("circle") == null ? "" : request.get("circle").toString());
                _check.setLoatNumber(request.get("lotNumber") == null ? "" : request.get("lotNumber").toString());
                _check.setTrackingNumber(
                                request.get("trackingNumber") == null ? "" : request.get("trackingNumber").toString());
                _check.setDocketNumber(
                                request.get("docketNumber") == null ? "" : request.get("docketNumber").toString());
                _check.setDocketDate(request.get("docketDate") == null ? "" : request.get("docketDate").toString());
                _check.setChallanNumber(
                                request.get("challanNumber") == null ? "" : request.get("challanNumber").toString());
                
                _check.setInStock(true);

                dbIn.saveDetails(_check, user);
                dbStock.addQuantity(product, sr.getLocation(), 1F, true, 2, user);

                request.put("customerInventoryInId", _check.getId());
                request.put("customerInventoryInManualId", _check.getManualId());

                return request;
        }

        @Override
        @Transactional(rollbackFor = { BadRequestException.class, UnauthoriseException.class, RuntimeException.class,
                        Exception.class }, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
        public Map<String, Object> updateObject(Map<String, Object> request, LoginUser user)
                        throws BadRequestException, UnauthoriseException {
                if (!dbUserprofile.hasObjectAccess(user.getUsername(), BtCustomerInventoryIn.class,
                                UserProfileRepository.EDIT))
                        throw new BadRequestException(UNAUTHORISED_REQUEST);
                Long id = Long.parseLong(request.get("id").toString());
                Optional<BtCustomerInventoryIn> inAcc = dbIn.findById(id);
                if (!inAcc.isPresent())
                        throw new BadRequestException("Invalid Customer inventory Id");
                if (inAcc.get().getReturnedToCustomer())
                        throw new BadRequestException(
                                        "Can not change to provided customer inventory, Inventory is returned to customer ");

                BtCustomerInventoryIn inCustomerIn = inAcc.get();
                inCustomerIn.setDummayLocation(MiscellaneousUtils.objToString(request.get("dummayLocationName")));
                inCustomerIn.setChallanNumber(MiscellaneousUtils.objToString(request.get("challanNumber")));
                inCustomerIn.setReceiveChallanDate(MiscellaneousUtils.objToString(request.get("receiveChallanDate")));
                inCustomerIn.setCustomerLRNumber(MiscellaneousUtils.objToString(request.get("customerLrNumber")));
                inCustomerIn.setCustomerCourierPartnarName(
                                MiscellaneousUtils.objToString(request.get("customerCurierPartnarName")));
                inCustomerIn.setCustomerPlant(MiscellaneousUtils.objToString(request.get("customerPlant")));
                inCustomerIn.setPackingCondation(MiscellaneousUtils.objToString(request.get("pacingCondation")));
                inCustomerIn.setRemarks(MiscellaneousUtils.objToString(request.get("remarks")));
                inCustomerIn.setPhysicalDamage(MiscellaneousUtils.objToString(request.get("physicalDamage")));

                dbIn.updateDetails(inCustomerIn, user);
                return request;
        }

        @Override
        public List<Map<String, Object>> searchForList(String search, LoginUser user)
                        throws BadRequestException, UnauthoriseException {
                if (dbUserprofile.hasObjectAccess(user.getUsername(), BtCustomerInventoryIn.class,
                                UserProfileRepository.VIEW_ALL))
                        return dbIn.searchForListPage(search, PageRequest.of(0, 20)).getContent();
                else if (dbUserprofile.hasObjectAccess(user.getUsername(), BtCustomerInventoryIn.class,
                                UserProfileRepository.VIEW))
                        return dbIn.searchForListPage(search,
                                        dbUserprofile.getLocationAndChildLocation(user.getUsername()),
                                        PageRequest.of(0, 20)).getContent();
                else
                        throw new UnauthoriseException(BtCustomerInventoryInServiceImpl.UNAUTHORISED_REQUEST);
        }

        @Override
        public List<Map<String, Object>> advanceSearch(Map<String, Object> searchRequest, LoginUser user)
                        throws BadRequestException, UnauthoriseException {
                if (dbUserprofile.hasObjectAccess(user.getUsername(), BtCustomerInventoryIn.class,
                                UserProfileRepository.VIEW_ALL))
                        return dbIn.getAdvanceSearchResult(searchRequest, null, user);
                else if (dbUserprofile.hasObjectAccess(user.getUsername(), BtCustomerInventoryIn.class,
                                UserProfileRepository.VIEW))
                        return dbIn.getAdvanceSearchResult(searchRequest,
                                        dbUserprofile.getLocationAndChildLocation(user.getUsername()), user);
                else
                        throw new UnauthoriseException(BtCustomerInventoryInServiceImpl.UNAUTHORISED_REQUEST);
        }

        @Override
        @Transactional(rollbackFor = { BadRequestException.class, UnauthoriseException.class, RuntimeException.class,
                        Exception.class }, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
        public Map<String, Object> out(Map<String, Object> request, LoginUser user)
                        throws BadRequestException, UnauthoriseException {
        	BtCustomerInventoryIn customerInventoryIn =null;
        	
        		if(request.get("bulk").toString().equalsIgnoreCase("bulkUpload")) {
        			if(request.get("manualid")==null || request.get("manualid").toString().equalsIgnoreCase(""))
        				throw new BadRequestException("Customer inventory in id not found");
        			
        			 customerInventoryIn = dbIn.getByManualId(request.get("manualid").toString());
        			if(customerInventoryIn==null)
        				throw new BadRequestException(request.get("manualid").toString()+" not found");
        		}
        		
        		if(!request.get("bulk").toString().equalsIgnoreCase("bulkUpload")) {
                Long id = Long.parseLong(request.get("inId").toString());
                Optional<BtCustomerInventoryIn> optionCustomerIn = dbIn.findById(id);
                if (!optionCustomerIn.isPresent())
                        throw new BadRequestException("Invalid id");
                customerInventoryIn = optionCustomerIn.get();
        		}
        		
                if (!customerInventoryIn.getInStock())
                        throw new BadRequestException("Requested Inventory is not in your stock");
                if (customerInventoryIn.getReturnedToCustomer())
                        throw new BadRequestException("Product part code " + customerInventoryIn.getProductPartcode()
                                        + " with serial number " + customerInventoryIn.getAssetsSerialNumber()
                                        + " is already returned to customer");
                BtCustomerInventoryOut out = new BtCustomerInventoryOut();

                ServiceRecource sr = dbServiceResource.getByEmail(user.getUsername());
                out.setId(customerInventoryIn.getId());
                out.setActualOutDate(converttoString(request.get("actualOutDate")));
                out.setCustomerInventoryIn(customerInventoryIn);
                out.setLoatNumber(converttoString(request.get("loatNumber")));
                out.setOutDate(new Date());
                out.setOutState(customerInventoryIn.getFinalStateCode());
                out.setOwner(new EmployeeDetails(user.getUsername()));
                out.setTrackingNumber(converttoString(request.get("trackingNumber")));

                out.setOutLrDocNumber(converttoString(request.get("outLrDocNumber")));
                out.setOutCourierPartner(converttoString(request.get("outCourierPartner")));
                out.setOutdeleveryChallanNumber(converttoString(request.get("outdeleveryChallanNumber")));
                out.setOutGrnNumber(converttoString(request.get("outGrnNumber")));
                out.setOutGrnDate(converttoString(request.get("outGrnDate")));

                if (customerInventoryIn.getFinalStateCode() == null)
                        throw new BadRequestException("Customer Inventory has not been repaired yet");

                dbOut.saveDetails(out, user);
                dbStock.subQuantity(customerInventoryIn.getProduct(), sr.getLocation(), 1F, true,
                                customerInventoryIn.getFinalStateCode(),
                                user);
                customerInventoryIn.setReturnedToCustomer(true);
                customerInventoryIn.setInStock(false);
                //Ruchit-Stage Change to dispatch when customer Inventory Out
                customerInventoryIn.setCustomerInventoryStage("Dispatched");
                dbIn.updateDetails(customerInventoryIn, user);
                request.put("outId", out.getId());
                request.put("outManualId", out.getManualId());

                return request;

        }

        private String converttoString(Object obj) {
                if (obj != null)
                        return obj.toString();
                else
                        return "";
        }

        @Override
        @Transactional(rollbackFor = {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class},propagation = Propagation.REQUIRES_NEW,isolation = Isolation.SERIALIZABLE)
        public Map<String, Object> updateOutGrn(Long outId, Map<String, Object> request, LoginUser user)
                        throws BadRequestException, UnauthoriseException {
                if(!dbUserprofile.hasObjectAccess(user.getUsername(), BtCustomerInventoryOut.class, UserProfileRepository.EDIT))
                        throw new UnauthoriseException("You are not authorised");
                
                Optional<BtCustomerInventoryOut> optionalOut =dbOut.findById(outId);
                if(!optionalOut.isPresent())
                        throw new BadRequestException("Invalid out details");
                BtCustomerInventoryOut outDetails=optionalOut.get();
                BtCustomerInventoryIn inDetails=dbIn.findById(outDetails.getCustomerInventoryIn().getId()).get();
                ServiceRecource sr=dbServiceResource.getByEmail(user.getUsername());
                
                if(sr.getLocation().getLocationId()!=inDetails.getInLocation().getLocationId())
                        throw new BadRequestException("You are not authorised");
                
                outDetails.setOutGrnNumber(MiscellaneousUtils.objToString(request.get("grnNumber")));
                outDetails.setOutGrnDate(MiscellaneousUtils.objToString(request.get("grnDate")));
                outDetails.setSysGrnDate(new Date());
              //  outDetails.setSysGrnDate(MiscellaneousUtils.objToString(request.get("sysGrnDate")));
                dbOut.updateDetails(outDetails, user);
                request.put("updateStatus", "done");
                return request;
        }

}
