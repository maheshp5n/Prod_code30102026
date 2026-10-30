package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.BtPreRegistrationCases;
import com.pratikshat.exicom.exception.BadRequestException;

public interface BtPreRegistrationCasesRepositoryCustom extends GenericOperation<BtPreRegistrationCases> {
	
	BtPreRegistrationCases getCasesWithId(long id) throws BadRequestException;
}
