package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.BtAssetsPurchaseDetails;
import com.pratikshat.exicom.exception.BadRequestException;

public interface BtAssetsPurchaseDetailsRepositoryCustome extends GenericOperation<BtAssetsPurchaseDetails> {
	
	public BtAssetsPurchaseDetails getWithId(long id) throws BadRequestException;
}
