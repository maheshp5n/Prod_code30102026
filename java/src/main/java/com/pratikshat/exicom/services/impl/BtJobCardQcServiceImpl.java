package com.pratikshat.exicom.services.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.BtCustomerInventoryIn;
import com.pratikshat.exicom.entity.BtJobCard;
import com.pratikshat.exicom.entity.BtJobCardQc;
import com.pratikshat.exicom.entity.BtServiceOrder;
import com.pratikshat.exicom.entity.BtServiceOrderItem;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.ServiceRecource;
import com.pratikshat.exicom.entity.UserProfile;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.BtCustomerInventoryInRepository;
import com.pratikshat.exicom.repository.BtJobCardQcRepository;
import com.pratikshat.exicom.repository.BtJobCardRepository;
import com.pratikshat.exicom.repository.BtProductItemRepository;
import com.pratikshat.exicom.repository.BtServiceOrderItemRepository;
import com.pratikshat.exicom.repository.BtServiceOrderRepository;
import com.pratikshat.exicom.repository.ServiceRecourseRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtJobCardQcService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BtJobCardQcServiceImpl implements BtJobCardQcService {

    @Autowired
    BtServiceOrderItemRepository dbServiceOrderItem;
    @Autowired
    BtJobCardRepository dbJobcard;
    @Autowired
    BtProductItemRepository dbStock;
    @Autowired
    BtJobCardQcRepository dbQc;
    @Autowired
    UserProfileRepository dbUserProfile;

    @Autowired BtServiceOrderRepository dbServiceOrder;

    @Autowired BtCustomerInventoryInRepository dbCustomerInventoryIn;
    @Autowired ServiceRecourseRepository dbServiceRecourse;

    @Override
    public Map<String, Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException {
        if (dbUserProfile.hasObjectAccess(user.getUsername(), BtJobCardQc.class, UserProfileRepository.VIEW_ALL))
            return dbQc.getSingle(Long.parseLong(id.toString()));
        else if (dbUserProfile.hasObjectAccess(user.getUsername(), BtJobCardQc.class, UserProfileRepository.VIEW))
            return dbQc.getSingle(Long.parseLong(id.toString()),
                    dbUserProfile.getListOfUserAndChildByUser(user.getUsername()));
        else
            throw new UnauthoriseException("You are not authorised");
    }

    @Override
    public Page<Map<String, Object>> getPage(Pageable page, LoginUser user)
            throws BadRequestException, UnauthoriseException {

        if (dbUserProfile.hasObjectAccess(user.getUsername(), BtJobCardQc.class, UserProfileRepository.VIEW_ALL))
            return dbQc.getPage(page);
        else if (dbUserProfile.hasObjectAccess(user.getUsername(), BtJobCardQc.class, UserProfileRepository.VIEW))
            return dbQc.getPage(page, dbUserProfile.getListOfUserAndChildByUser(user.getUsername()));
        else
            throw new UnauthoriseException("You are not authorised");
    }

    @Deprecated
    @Override
    public Map<String, Object> saveObject(Map<String, Object> request, LoginUser user)
            throws BadRequestException, UnauthoriseException {
            
        return null;
    }

    @Override
    @Transactional(rollbackFor = {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class},propagation = Propagation.REQUIRES_NEW,isolation = Isolation.SERIALIZABLE)
    public Map<String, Object> updateObject(Map<String, Object> request, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        if(!dbUserProfile.hasObjectAccess(user.getUsername(), BtJobCardQc.class, UserProfileRepository.EDIT))
            throw new UnauthoriseException("You are not authorised");    
        Long jqcid=Long.parseLong(request.get("jobCardQcId").toString());
        Optional<BtJobCardQc> optionalBtJobCardQc=dbQc.findById(jqcid);
        
        if(!optionalBtJobCardQc.isPresent())
                throw new BadRequestException("Invalid qc id");
        
                BtJobCardQc jobCardQc=optionalBtJobCardQc.get();
        if(!jobCardQc.getAssignedTo().getEmailId().equalsIgnoreCase(user.getUsername()))
                throw new UnauthoriseException("You are not authorised");
        if(jobCardQc.getFinalApproval()!=null)
                throw new BadRequestException("Can not update status, Qc already done");
        
        BtJobCard jobCard=dbJobcard.findById(jobCardQc.getJobCard().getId()).get();
        BtServiceOrderItem soItem=dbServiceOrderItem.findById(jobCard.getId()).get();
        
        jobCardQc.setCorrectiveActionTaken(MiscellaneousUtils.objToString(request.get("qcCorrectiveActionTaken")));
        jobCardQc.setElectricalCheck(MiscellaneousUtils.objToString(request.get("qcElectricalCheck")));
        jobCardQc.setElectricalRemarks(MiscellaneousUtils.objToString(request.get("qcElectricalRemarks")));
        jobCardQc.setPidDate(MiscellaneousUtils.objToString(request.get("qcPidDate")));
        jobCardQc.setFaultObservedDuringPid(MiscellaneousUtils.objToString(request.get("qcFaultObservedDuringPID")));
        jobCardQc.setFinalApproval(MiscellaneousUtils.objToString(request.get("qcFinalApproval")));
        jobCardQc.setParametersCheck(MiscellaneousUtils.objToString(request.get("qcParameterCheck")));
        jobCardQc.setPhysicalCheck(MiscellaneousUtils.objToString(request.get("qcPhysicalCheck")));
        jobCardQc.setPhysicalRemarks(MiscellaneousUtils.objToString(request.get("qcPhysicalRemarks")));
        jobCardQc.setSoftwareUpdation(MiscellaneousUtils.objToString(request.get("qcSoftWareupdation")));
        jobCardQc.setSohUpdation(MiscellaneousUtils.objToString(request.get("qcSohUpdation")));
        jobCardQc.setVoltageGapLt150mv(MiscellaneousUtils.objToString(request.get("qcVoltageGap")));
        jobCardQc.setvMax(MiscellaneousUtils.objToString(request.get("qcVmax")));
        jobCardQc.setvMin(MiscellaneousUtils.objToString(request.get("qcVmin")));
        jobCardQc.setQcobservation1(MiscellaneousUtils.objToString(request.get("qcObservation1")));
        jobCardQc.setQcobservation2(MiscellaneousUtils.objToString(request.get("qcObservation2")));

        // if(jobCardQc.getCorrectiveActionTaken().equalsIgnoreCase("")||jobCardQc.getCorrectiveActionTaken().equalsIgnoreCase("0"))
        //         throw new BadRequestException("Please provide Corrective action taken");
        // if(jobCardQc.getElectricalCheck().equalsIgnoreCase("")||jobCardQc.getElectricalCheck().equalsIgnoreCase("0"))
        //         throw new BadRequestException("Please provide electrical check");
        // if(jobCardQc.getElectricalRemarks().equalsIgnoreCase("")||jobCardQc.getElectricalRemarks().equalsIgnoreCase("0"))
        //         throw new BadRequestException("Please provide electrical remarks");
        // if(jobCardQc.getPidDate().equalsIgnoreCase(""))
        //         throw new BadRequestException("Please provide PID date");
        // if(jobCardQc.getFaultObservedDuringPid().equalsIgnoreCase("")||jobCardQc.getFaultObservedDuringPid().equalsIgnoreCase("0"))
        //         throw new BadRequestException("Please provide fault observed during PID");
        // if(jobCardQc.getParametersCheck().equalsIgnoreCase("")||jobCardQc.getParametersCheck().equalsIgnoreCase("0"))
        //         throw new BadRequestException("Please provide parameters check");
        // if(jobCardQc.getPhysicalCheck().equalsIgnoreCase("")||jobCardQc.getPhysicalCheck().equalsIgnoreCase("0"))
        //         throw new BadRequestException("Please provide physical check");

        // // if(jobCardQc.getPhysicalRemarks().equalsIgnoreCase("")||jobCardQc.getPhysicalRemarks().equalsIgnoreCase("0"))
        // //         throw new BadRequestException("Please provide pysical remarks");
        
        // if(jobCardQc.getSoftwareUpdation().equalsIgnoreCase("")||jobCardQc.getSoftwareUpdation().equalsIgnoreCase("0"))
        //         throw new BadRequestException("Please provide sofware update");
        // if(jobCardQc.getSohUpdation().equalsIgnoreCase("")||jobCardQc.getSohUpdation().equalsIgnoreCase("0"))
        //         throw new BadRequestException("Please provide soh updation");
        // if(jobCardQc.getVoltageGapLt150mv().equalsIgnoreCase("")||jobCardQc.getVoltageGapLt150mv().equalsIgnoreCase("0"))
        //         throw new BadRequestException("Please provide Voltage Gap Less than 150 mv");
        // if(jobCardQc.getvMax().equalsIgnoreCase("")||jobCardQc.getvMax().equalsIgnoreCase("0"))
        //         throw new BadRequestException("Please provide  vMax");
        // if(jobCardQc.getvMin().equalsIgnoreCase("")||jobCardQc.getvMin().equalsIgnoreCase("0"))
        //         throw new BadRequestException("Please provide vMin");

        // if(jobCardQc.getFinalApproval().equalsIgnoreCase("")||jobCardQc.getFinalApproval().equalsIgnoreCase("0"))
        //         throw new BadRequestException("Please provide final approval");

        //AMENDMENT ON QC FIELD MENDATORY


        
        dbQc.updateDetails(jobCardQc, user);

        if(jobCardQc.getFinalApproval().equalsIgnoreCase("yes")){
            soItem.setAssignedReturned(true);
            soItem.setrStatus(jobCard.getRepairProductState());
            dbServiceOrderItem.updateDetails(soItem, user);
            dbStock.subQuantity(soItem.getProduct(), 1F, soItem.getCustomerInventory(), jobCard.getRepairProductState(), user);
            BtServiceOrder sorder=dbServiceOrder.findById(soItem.getServiceOrder().getId()).get();
            dbStock.addQuantity(soItem.getProduct(), sorder.getDestinationLocation(), 1F, soItem.getCustomerInventory(), jobCard.getRepairProductState(), user);

            if(soItem.getInCustomerInventory()!=null){
                BtCustomerInventoryIn _in=dbCustomerInventoryIn.findById(soItem.getInCustomerInventory().getId()).get();
                _in.setCustomerInventoryStage("QC Approved");
                _in.setFinalStateCode(jobCard.getRepairProductState());
                
                Calendar c = Calendar.getInstance();
                c.setTime(new Date()); // Using today's date
                c.add(Calendar.DATE, 1); // Adding 5 days
                _in.setDispatchInitDate(c.getTime());
              
                dbCustomerInventoryIn.updateDetails(_in, user);
            }
            jobCard.setReturnedToSource(true);
            jobCard.setQcDone(true);
            jobCard.setRepared(true);
            jobCard.setReturnDate(new Date());
            dbJobcard.updateDetails(jobCard, user);
        }
        else{
            dbStock.subQuantity(soItem.getProduct(), 1F, soItem.getCustomerInventory(), jobCard.getRepairProductState(), user);
            ServiceRecource engRe=dbServiceRecourse.getByEmail(jobCard.getAssignedTo().getEmailId());
            
            dbStock.addQuantity(soItem.getProduct(), engRe.getLocation(), 1F, soItem.getCustomerInventory(), jobCard.getRepairProductState(), user);
           
            jobCard.setPreviousState(jobCard.getRepairProductState());
            jobCard.setRepared(false);
            jobCard.setQcDone(false);
            jobCard.setAssignedForQc(false);
            dbJobcard.updateDetails(jobCard, user);
            if(soItem.getInCustomerInventory()!=null){
                BtCustomerInventoryIn _in=dbCustomerInventoryIn.findById(soItem.getInCustomerInventory().getId()).get();
                _in.setCustomerInventoryStage("QC Rejected");
                dbCustomerInventoryIn.updateDetails(_in, user);
            }
        }

        return request;
    }

    @Override
    public List<Map<String, Object>> searchForList(String search, LoginUser user)
            throws BadRequestException, UnauthoriseException {

        return null;
    }

    @Override
    public List<Map<String, Object>> advanceSearch(Map<String, Object> searchRequest, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        if (dbUserProfile.hasObjectAccess(user.getUsername(), BtJobCardQc.class, UserProfileRepository.VIEW_ALL))
            return dbQc.advanceSearch(searchRequest, null);
        else if (dbUserProfile.hasObjectAccess(user.getUsername(), BtJobCardQc.class, UserProfileRepository.VIEW))
            return dbQc.advanceSearch(searchRequest, dbUserProfile.getListOfUserAndChildByUser(user.getUsername()));
        else
            throw new UnauthoriseException("You are not authorised");
    }

    @Override
    public List<Map<String, Object>> getQcListByJobcardid(Long id, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        if (dbUserProfile.hasObjectAccess(user.getUsername(), BtJobCardQc.class, UserProfileRepository.VIEW_ALL))
            return dbQc.getListOfQcByJobcardId(id);
        else if (dbUserProfile.hasObjectAccess(user.getUsername(), BtJobCardQc.class, UserProfileRepository.VIEW))
            return dbQc.getListOfQcByJobcardId(id, dbUserProfile.getListOfUserAndChildByUser(user.getUsername()));
        else
            throw new UnauthoriseException("You are not authorised");
    }

    @Override
    public Page<Map<String, Object>> getQcPageByJobcardId(Long id, Pageable page, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        if (dbUserProfile.hasObjectAccess(user.getUsername(), BtJobCardQc.class, UserProfileRepository.VIEW_ALL))
            return dbQc.getPageOfQcByJobcardId(id, page);
        else if (dbUserProfile.hasObjectAccess(user.getUsername(), BtJobCardQc.class, UserProfileRepository.VIEW))
            return dbQc.getPageOfQcByJobcardId(id, dbUserProfile.getListOfUserAndChildByUser(user.getUsername()), page);
        else
            throw new UnauthoriseException("You are not authorised");
    }

}
