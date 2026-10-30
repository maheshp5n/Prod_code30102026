package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.LoginUser;

public interface ObjectDeffinitionRepoCustome {
	List<Map<String,Object>> executeQuery(String query,LoginUser user);
}
