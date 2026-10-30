package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.BtAssetsWarrantyDetails;
import com.pratikshat.exicom.exception.BadRequestException;

public interface BtAssetsWarrantyDetailsRepositoryCustome extends GenericOperation<BtAssetsWarrantyDetails> {
	
	public BtAssetsWarrantyDetails getWithAssetsId(long id) throws BadRequestException;
}
