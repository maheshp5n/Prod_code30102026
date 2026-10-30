package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.model.request.PriceBookRequest;

public interface PriceBookRepositoryCustom {

	public PriceBookRequest createPriceBook(PriceBookRequest priceBookRequest, LoginUser user) throws Exception;
	public PriceBookRequest updatePriceBook(PriceBookRequest priceBookRequest, LoginUser user) throws Exception;
}
