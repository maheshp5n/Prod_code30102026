package com.pratikshat.exicom.repository;

import java.util.List;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.model.request.PriceBookEntriesRequest;

public interface PriceBookEntriesRepositoryCustom {

	List<PriceBookEntriesRequest> insertEntries(List<PriceBookEntriesRequest> entries, LoginUser user) throws Exception;
	PriceBookEntriesRequest updateEntry(PriceBookEntriesRequest entry, LoginUser user) throws Exception;
	
	PriceBookEntriesRequest insertEntry(PriceBookEntriesRequest entry, LoginUser user) throws Exception;
	
}
