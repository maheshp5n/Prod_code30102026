package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.BtFsrOdcKitting;
import com.pratikshat.exicom.exception.BadRequestException;

public interface BtFsrOdcKittingRepositoryCustom  extends GenericOperation<BtFsrOdcKitting>{
	
	BtFsrOdcKitting getFsrWithId(long id) throws BadRequestException;
}
