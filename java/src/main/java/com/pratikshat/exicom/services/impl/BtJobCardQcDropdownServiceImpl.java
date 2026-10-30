package com.pratikshat.exicom.services.impl;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.BtJobCardQcCorrectiveActiontaken;
import com.pratikshat.exicom.entity.BtJobCardQcElectricalCheck;
import com.pratikshat.exicom.entity.BtJobCardQcElectricalremarks;
import com.pratikshat.exicom.entity.BtJobCardQcFinalapproval;
import com.pratikshat.exicom.entity.BtJobCardQcParametercheck;
import com.pratikshat.exicom.entity.BtJobCardQcPhysicalcheck;
import com.pratikshat.exicom.entity.BtJobCardQcPhysicalremarks;
import com.pratikshat.exicom.entity.BtJobCardQcSoftwareupdation;
import com.pratikshat.exicom.entity.BtJobCardQcSohupdation;
import com.pratikshat.exicom.entity.BtJobCardQcVoltagegap;
import com.pratikshat.exicom.repository.BtJobCardQcCorrectiveActiontakenRepository;
import com.pratikshat.exicom.repository.BtJobCardQcElectricalCheckRepository;
import com.pratikshat.exicom.repository.BtJobCardQcElectricalremarksRepository;
import com.pratikshat.exicom.repository.BtJobCardQcFinalapprovalRepository;
import com.pratikshat.exicom.repository.BtJobCardQcParametercheckRepository;
import com.pratikshat.exicom.repository.BtJobCardQcPhysicalcheckRepository;
import com.pratikshat.exicom.repository.BtJobCardQcPhysicalremarksRepository;
import com.pratikshat.exicom.repository.BtJobCardQcSoftwareupdationRepository;
import com.pratikshat.exicom.repository.BtJobCardQcSohupdationRepository;
import com.pratikshat.exicom.repository.BtJobCardQcVoltagegapRepository;
import com.pratikshat.exicom.services.BtJobCardQcDropdownService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BtJobCardQcDropdownServiceImpl implements BtJobCardQcDropdownService {

    @Autowired BtJobCardQcPhysicalcheckRepository dbPhysicalcheck;
    @Autowired BtJobCardQcPhysicalremarksRepository dbPhysicalRemarks;
    @Autowired BtJobCardQcElectricalremarksRepository dbElectricalRemarks;
    @Autowired BtJobCardQcCorrectiveActiontakenRepository dbCorrectiveAction;
    @Autowired BtJobCardQcSoftwareupdationRepository dbSoftwareUpdation;
    @Autowired BtJobCardQcParametercheckRepository dbParameterCheck;
    @Autowired BtJobCardQcSohupdationRepository dbSohupdation;
    @Autowired BtJobCardQcVoltagegapRepository dbVoltageGap;
    @Autowired BtJobCardQcFinalapprovalRepository dbFinalApproval;
    @Autowired BtJobCardQcElectricalCheckRepository dbElectricalCheck;

    @Override
    public List<BtJobCardQcPhysicalcheck> getBtJobCArdQcPhysicalcheck() {
        // TODO Auto-generated method stub
        return dbPhysicalcheck.findAll();
    }

    @Override
    public List<BtJobCardQcPhysicalremarks> getBtJobCardQcPhysicalremarks() {
        // TODO Auto-generated method stub
        return dbPhysicalRemarks.findAll();
    }

    @Override
    public List<BtJobCardQcElectricalremarks> getBtJobCardQcElectricalremarks() {
        // TODO Auto-generated method stub
        return dbElectricalRemarks.findAll();
    }

    @Override
    public List<BtJobCardQcCorrectiveActiontaken> getBtJobCardQcCorrectiveActiontaken() {
        // TODO Auto-generated method stub
        return dbCorrectiveAction.findAll();
    }

    @Override
    public List<BtJobCardQcSoftwareupdation> getBtJobCardQcSoftwareupdation() {
        // TODO Auto-generated method stub
        return dbSoftwareUpdation.findAll();
    }

    @Override
    public List<BtJobCardQcParametercheck> getBtJobCardQcParametercheck() {
        // TODO Auto-generated method stub
        return dbParameterCheck.findAll();
    }

    @Override
    public List<BtJobCardQcSohupdation> getBtJobCardQcSohupdation() {
        // TODO Auto-generated method stub
        return dbSohupdation.findAll();
    }

    @Override
    public List<BtJobCardQcVoltagegap> getBtJobCardQcVoltagegap() {
        // TODO Auto-generated method stub
        return dbVoltageGap.findAll();
    }

    @Override
    public List<BtJobCardQcFinalapproval> getBtJobCardQcFinalapproval() {
        // TODO Auto-generated method stub
        return dbFinalApproval.findAll();
    }

    @Override
    public List<BtJobCardQcElectricalCheck> getBtJobCardQcElectricalCheck() {
        // TODO Auto-generated method stub
        return dbElectricalCheck.findAll();
    }
    
    @Override
    public List<Map<String,Object>> getBtJobCardQcFaultOnserved() {
        // TODO Auto-generated method stub
        return dbElectricalCheck.getBtJobCardQcFaultObserved();
    }
    
}
