package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.BtAssets;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.AmcUpdateRequest;

public interface BtAssetsCustomeRepository extends GenericOperation<BtAssets> {

	public BtAssets getAssestBySerialNoAndPartCode(String assetsSerialNumber, String assetsPartCode) throws BadRequestException;
	public BtAssets getAssetsForAppointment(long id) throws BadRequestException;
	public BtAssets getWithObject(long id) throws BadRequestException;
	public BtAssets getWithId(long id) throws BadRequestException;
	
	public AmcUpdateRequest updateAmcBulk(AmcUpdateRequest req, LoginUser user)throws BadRequestException, UnauthoriseException;

}
