package com.pratikshat.exicom.repository;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.ServiceAppointment;
import com.pratikshat.exicom.model.request.SecondServiceAppointmentRequest;
import com.pratikshat.exicom.model.request.ServiceAppointmentReAssignRequest;
import com.pratikshat.exicom.model.response.ServiceAppointmentResponse;

public interface ServiceAppointmentRepositoryCustom {
	public com.pratikshat.exicom.model.response.ServiceAppointmentResponse updateServiceAppointmentDetails(com.pratikshat.exicom.model.request.ServiceAppointmentRequest serviceAppointmentRequest,com.pratikshat.exicom.entity.LoginUser user) throws Exception;
	//public com.pratikshat.exicom.model.response.ServiceAppointmentResponse createServiceAppointmentDetails(com.pratikshat.exicom.model.request.CaseRequest caseDetails,com.pratikshat.exicom.entity.LoginUser user) throws Exception;
	public ServiceAppointment createServiceAppointmentDetails(SecondServiceAppointmentRequest serviceRequest,com.pratikshat.exicom.entity.LoginUser user) throws Exception;
	public java.util.List<String> getOkSerialNumber(long locationProductItemId,com.pratikshat.exicom.entity.LoginUser user) throws Exception;
	
	public void requestForAnotherServiceAppointment(long caseId, String remark, LoginUser user)throws Exception;
	
	public ServiceAppointmentReAssignRequest reAssignServiceAppointment(ServiceAppointmentReAssignRequest request, LoginUser user) throws Exception;
	
	public List<ServiceAppointmentResponse> getAdvanceSearchResult(LoginUser user,Map<String,Object> filterData,List<String>users) throws ParseException;

	public ByteArrayInputStream generatePdf(LoginUser user, long id) throws Exception;
	
	public boolean verifyCode(long id,String vfCode);
}
