package com.pratikshat.exicom.repository;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.entity.LoginUser;
public interface GenericOperation<T> {
     T saveDetails(T t,LoginUser user) throws BadRequestException,UnauthoriseException;
     T updateDetails(T t,LoginUser user) throws BadRequestException,UnauthoriseException;
     T deleteDetails(T t,LoginUser user) throws BadRequestException,UnauthoriseException;
}
