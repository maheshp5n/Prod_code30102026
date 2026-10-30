package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.model.request.BatteryFSRRequest;

public interface BatteryFSRCustomRepository {

	BatteryFSRRequest saveBatteryFSR(BatteryFSRRequest request,LoginUser user)throws Exception;
	BatteryFSRRequest updateBatteryFSR(BatteryFSRRequest request,LoginUser user)throws Exception;
	
}
