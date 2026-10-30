package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

public interface ServiceAppointmentProductConsumedCustomeRepo {
	void consumeProducts(com.pratikshat.exicom.model.request.ServiceAppointmentProductConsumedRequest consumedProduct,com.pratikshat.exicom.entity.LoginUser user) throws Exception;
	void deleteProducts(long consumeid,com.pratikshat.exicom.entity.LoginUser user) throws BadRequestException,UnauthoriseException;
	void consumeProductsWithFaultyProduct(com.pratikshat.exicom.model.request.ServiceAppointmentProductConsumedRequest consumedProduct,com.pratikshat.exicom.entity.LoginUser user) throws Exception;
}
