package com.pratikshat.exicom.repository.custom;

import com.pratikshat.exicom.entity.BtJobCard;
import com.pratikshat.exicom.entity.BtJobCardProductConsume;
import com.pratikshat.exicom.entity.BtProducts;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.GenericOperation;

public interface BtJobCardProductConsumeRepositoryCustom extends GenericOperation<BtJobCardProductConsume> {
    public void addQuantity(BtJobCardProductConsume jobCardConsume,Float quantity,LoginUser user) throws BadRequestException,UnauthoriseException;
    public void addQuantity(Long jobCardConsumeId,Float quantity,LoginUser user) throws BadRequestException,UnauthoriseException;
    public void subQuantity(BtJobCardProductConsume jobCardConsume,Float quantity,LoginUser user) throws BadRequestException,UnauthoriseException;
    public void subQuantity(Long btJobcardId,Float quantity,LoginUser user) throws BadRequestException,UnauthoriseException;
}
