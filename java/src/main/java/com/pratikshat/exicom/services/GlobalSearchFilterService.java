package com.pratikshat.exicom.services;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.LoginUser;

public interface GlobalSearchFilterService {
	Map<String,List> getSearchFilter(String searchKey,LoginUser user);
	List<Map<String,Object>> oldsearch(String searchKey,LoginUser user);
}
