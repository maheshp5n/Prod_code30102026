package com.pratikshat.exicom.controller;

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
import com.pratikshat.exicom.services.BtJobCardQcDropdownService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("bt/api/jobcard/qc/dropdown")
public class BtJobCardQcDropdownController {
    @Autowired BtJobCardQcDropdownService dBtJobCardQcDropdownService;

    @GetMapping("qcphysicalcheck")
    public List<BtJobCardQcPhysicalcheck> getList(){
        return  dBtJobCardQcDropdownService.getBtJobCArdQcPhysicalcheck();
    }
    @GetMapping("qcphysicalremarks")
    public List<BtJobCardQcPhysicalremarks> getQcphysicalremarks(){
        return dBtJobCardQcDropdownService.getBtJobCardQcPhysicalremarks();
    }

    @GetMapping("qcelectricalremarks")
    public List<BtJobCardQcElectricalremarks> getQcelectricalremarks(){
        return dBtJobCardQcDropdownService.getBtJobCardQcElectricalremarks();
    }
    @GetMapping("qccorrectiveactiontaken")
    public List<BtJobCardQcCorrectiveActiontaken> getQccorrectiveactiontaken(){
        return dBtJobCardQcDropdownService.getBtJobCardQcCorrectiveActiontaken();
    }

    @GetMapping("qcsoftwareupdation")
    public List<BtJobCardQcSoftwareupdation> getQcsoftwareupdation(){
        return dBtJobCardQcDropdownService.getBtJobCardQcSoftwareupdation();
    }

    @GetMapping("qcparametercheck")
    public List<BtJobCardQcParametercheck> getQcparametercheck(){
        return dBtJobCardQcDropdownService.getBtJobCardQcParametercheck();
    }
    @GetMapping("qcsohupation")
    public List<BtJobCardQcSohupdation> getQcsohupation(){
        return dBtJobCardQcDropdownService.getBtJobCardQcSohupdation();
    }
    @GetMapping("qcvoltagap")
    public List<BtJobCardQcVoltagegap> getQcvoltagap(){
        return dBtJobCardQcDropdownService.getBtJobCardQcVoltagegap();
    }
    @GetMapping("qcfinalapproval")
    public List<BtJobCardQcFinalapproval> getQcfinalapproval(){
        return dBtJobCardQcDropdownService.getBtJobCardQcFinalapproval();
    }

    @GetMapping("qcelectricalcheck")
    public List<BtJobCardQcElectricalCheck> getBtJobCardQcElectricalCheck(){
        return dBtJobCardQcDropdownService.getBtJobCardQcElectricalCheck();
    }
    
    @GetMapping("qcfaultobserved")
    public List<Map<String,Object>> getBtJobCardQcFaultObserved(){
        return dBtJobCardQcDropdownService.getBtJobCardQcFaultOnserved();
    }

}
