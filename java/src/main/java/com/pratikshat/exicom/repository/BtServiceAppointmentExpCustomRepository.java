package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.BtServiceAppointmentExp;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.BtServiceAppointmentExpRequest;
import com.pratikshat.exicom.model.response.StatusMessageResponse;

public interface BtServiceAppointmentExpCustomRepository extends GenericOperation<BtServiceAppointmentExpRequest> {

	StatusMessageResponse deleteBtServiceAppointmentExp(long id, LoginUser user) throws BadRequestException, UnauthoriseException;
}
