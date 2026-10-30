package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.model.request.WarehouseRequest;

public interface WarehouseRepoCustome {
	WarehouseRequest saveWarehouse(WarehouseRequest request,LoginUser user) throws Exception;
}
