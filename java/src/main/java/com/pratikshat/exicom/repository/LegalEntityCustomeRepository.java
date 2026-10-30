package com.pratikshat.exicom.repository;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.model.request.LegalEntityResponse;

public interface LegalEntityCustomeRepository {

	public List<LegalEntityResponse> advanceSearch(Map<String,Object> searchKeys,LoginUser user, int businessDommain)throws ParseException;
}
