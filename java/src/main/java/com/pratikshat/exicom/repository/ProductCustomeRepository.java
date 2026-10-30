package com.pratikshat.exicom.repository;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface ProductCustomeRepository {
	
	List<com.pratikshat.exicom.model.response.Products> advanceSearch( Map<String,Object> request,List<String> users)throws ParseException;

}
