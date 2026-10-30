package com.pratikshat.exicom.repository.custom;

import com.pratikshat.exicom.entity.BtRmaChangeStatusLog;
import com.pratikshat.exicom.entity.BtRmaRejectLog;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.UploadedFiles;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.GenericOperation;

public interface BtRmaChangeStatusLogCustom extends GenericOperation<BtRmaChangeStatusLog> {

	BtRmaChangeStatusLog saveDetails(BtRmaChangeStatusLog changeLog, UploadedFiles uploadedFile, LoginUser user) throws BadRequestException,UnauthoriseException;
}
