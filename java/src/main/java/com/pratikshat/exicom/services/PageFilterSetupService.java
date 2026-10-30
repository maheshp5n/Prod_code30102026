package com.pratikshat.exicom.services;

import java.util.List;
import java.util.Map;

public interface PageFilterSetupService {
	List<Map<String,Object>> getNaviationModules();
	List<Map<String,Object>> getProfiles();
	List<Map<String,Object>> getFilters(long navId,long profileId);
	void saveProfileFilters(List<Map<String,Object>> idsFilter);
}
