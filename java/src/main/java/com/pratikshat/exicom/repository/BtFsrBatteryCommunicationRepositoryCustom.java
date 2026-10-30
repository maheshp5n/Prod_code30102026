package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.BtFsrBatteryCommunication;
import com.pratikshat.exicom.exception.BadRequestException;

public interface BtFsrBatteryCommunicationRepositoryCustom extends GenericOperation<BtFsrBatteryCommunication>{

	BtFsrBatteryCommunication getFsrWithId(long id) throws BadRequestException;
}
