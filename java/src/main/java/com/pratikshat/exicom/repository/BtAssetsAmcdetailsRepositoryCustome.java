package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.BtAssetsAmcdetails;
import com.pratikshat.exicom.exception.BadRequestException;

public interface BtAssetsAmcdetailsRepositoryCustome extends GenericOperation<BtAssetsAmcdetails> {
	public BtAssetsAmcdetails getWithId(long id) throws BadRequestException;
}
