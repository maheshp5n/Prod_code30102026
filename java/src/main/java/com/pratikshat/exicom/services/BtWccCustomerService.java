package com.pratikshat.exicom.services;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

 import com.pratikshat.exicom.entity.BtCustomerWcc;
import com.pratikshat.exicom.entity.BtCustomerWccReject;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
 import com.pratikshat.exicom.model.request.PodRequest;


public interface BtWccCustomerService extends GenricService{

	public Map<String, Object> customerWccApproval(Map<String, Object> request, List<MultipartFile> files, LoginUser user) throws BadRequestException, UnauthoriseException, ParseException;

	public Map<String, Object> rejectCustomerWCC(Map<String, Object> req, LoginUser user) throws BadRequestException, UnauthoriseException;

	public List<BtCustomerWccReject> getReasonList();

	 public BtCustomerWcc postWccCustomer(PodRequest request, List<MultipartFile> files, LoginUser user) throws BadRequestException, UnauthoriseException;

	public List<Map<String, Object>> getWccData(long wccid);

	public List<Map<String, Object>> getAllCase(long id, LoginUser user);

	

}
