package com.pratikshat.exicom.repository;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.ServiceRecource;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.response.SSAResponse;
import com.pratikshat.exicom.model.response.ServiceResourseResponse;

public interface ServiceRecourseRepositoryCustome {
	com.pratikshat.exicom.model.request.ServiceResourseRequest saveServiceResource(com.pratikshat.exicom.model.request.ServiceResourseRequest serviceRequest,LoginUser user, int businessDomainId) throws Exception;
	com.pratikshat.exicom.model.request.ServiceResourseRequest editServiceResource(com.pratikshat.exicom.model.request.ServiceResourseRequest serviceRequest,LoginUser user, int businessDomainId) throws BadRequestException,UnauthoriseException;
	
	public List<ServiceResourseResponse> advanceSearch(Map<String,Object> searchKeys,LoginUser user, int businessDomainId)throws ParseException;
	
	public ServiceRecource getServiceResourceWithCaseId(long id) throws BadRequestException;
	
	public ServiceRecource getServiceResourceWithId(long id) throws BadRequestException;
	
	public ServiceRecource getServiceResourceWithIdAndServiceid(long serviceId,long resourceid) throws BadRequestException;
	
	public ServiceRecource getServiceResourceWithUser(EmployeeDetails user)throws BadRequestException;
	
	public List<Map<String,Object>> getServiceResourceRatingAdvanceSearch(Map<String,Object> data, int businessDomainId);
	
}
