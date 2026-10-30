package com.pratikshat.exicom.services;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.LoginUser;

public interface LocationNativeSerivce {
	List<Map<String,Object>> getLocationParentAndSiblings(LoginUser User);
}
