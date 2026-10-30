package com.pratikshat.exicom.services;

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

public interface BtJobCardQcDropdownService {
    public List<BtJobCardQcPhysicalcheck> getBtJobCArdQcPhysicalcheck();
    public List<BtJobCardQcPhysicalremarks> getBtJobCardQcPhysicalremarks();
    public List<BtJobCardQcElectricalremarks> getBtJobCardQcElectricalremarks();
    public List<BtJobCardQcCorrectiveActiontaken> getBtJobCardQcCorrectiveActiontaken();
    public List<BtJobCardQcSoftwareupdation> getBtJobCardQcSoftwareupdation();
    public List<BtJobCardQcParametercheck> getBtJobCardQcParametercheck();
    public List<BtJobCardQcSohupdation> getBtJobCardQcSohupdation();
    public List<BtJobCardQcVoltagegap> getBtJobCardQcVoltagegap();
    public List<BtJobCardQcFinalapproval>  getBtJobCardQcFinalapproval();
    public List<BtJobCardQcElectricalCheck>  getBtJobCardQcElectricalCheck();
    
	public List<Map<String, Object>> getBtJobCardQcFaultOnserved();
}
