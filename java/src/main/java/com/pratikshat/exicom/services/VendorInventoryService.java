package com.pratikshat.exicom.services;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.response.ServiceAppointmentResponse;

public interface VendorInventoryService {
	
	public Map<String,Object> reAssignServiceAppointment(long id, LoginUser user) throws Exception;
	
	public List<ServiceAppointmentResponse> getAdvanceSearchResult(LoginUser user, Map<String, Object> filterData,
			List<String> users) throws ParseException;
	
	public Map<String,Object> assignToServiceAppointment(Map<String, Object> request, LoginUser user) throws BadRequestException,UnauthoriseException;

	public Map<String,Object> unAssignServiceAppointment(Map<String, Object> request, LoginUser user) throws BadRequestException,UnauthoriseException;
}
