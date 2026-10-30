package com.pratikshat.exicom.services.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.TourExpenseRequest;
import com.pratikshat.exicom.model.response.StatusMessageResponse;
import com.pratikshat.exicom.repository.BtTourExpenseCustomeRepositoryImpl;
import com.pratikshat.exicom.repository.BtTourExpenseRepository;
import com.pratikshat.exicom.services.BtTourExpenseService;

@Service
public class BtTourExpenseServiceImpl implements BtTourExpenseService {

	@Autowired
	BtTourExpenseCustomeRepositoryImpl BtTourExpenseDBImpl;
	@Autowired
	BtTourExpenseRepository btTourExpenseDB; 
	
	SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public Map<String, Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException {
		if(id!=null && Long.parseLong(id.toString())!=0 && !id.toString().equalsIgnoreCase("") && !id.toString().equalsIgnoreCase("null"))
		{
			return btTourExpenseDB.getById(Long.parseLong(id.toString()));
		}
		else
		{
			throw new BadRequestException("BtTour Expense Id is null");	
		}
	}

	@Override
	public Page<Map<String, Object>> getPage(Pageable page, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = { Exception.class,BadRequestException.class,UnauthoriseException.class,
			RuntimeException.class }, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Map<String, Object> saveObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		if(request.get("btExpenseManagementId")==null && Long.parseLong(request.get("btExpenseManagementId").toString())==0 &&  request.get("btExpenseManagementId").toString().equalsIgnoreCase("") && request.get("btExpenseManagementId").toString().equalsIgnoreCase("null")) {
			throw new BadRequestException("Expense id is null");
		}else if(request.get("departureDate")==null && request.get("departureDate").toString().equalsIgnoreCase("") && request.get("departureDate").toString().equalsIgnoreCase("null")) {
			throw new BadRequestException("Departure date is null");
		}else if(request.get("departureTime")==null && request.get("departureTime").toString().equalsIgnoreCase("") && request.get("departureTime").toString().equalsIgnoreCase("null")) {
			throw new BadRequestException("Departure time is null");
		}else if(request.get("arrivalDate")==null && request.get("arrivalDate").toString().equalsIgnoreCase("") && request.get("arrivalDate").toString().equalsIgnoreCase("null")) {
			throw new BadRequestException("Arrival date is null");
		}else if(request.get("arrivalTime")==null && request.get("arrivalTime").toString().equalsIgnoreCase("") && request.get("arrivalTime").toString().equalsIgnoreCase("null")) {
			throw new BadRequestException("Arrival time is null");
		}else if(request.get("fromPlace")==null && request.get("fromPlace").toString().equalsIgnoreCase("") && request.get("fromPlace").toString().equalsIgnoreCase("null")) {
			throw new BadRequestException("From place is null");
		}else if(request.get("toPlace")==null && request.get("toPlace").toString().equalsIgnoreCase("") && request.get("toPlace").toString().equalsIgnoreCase("null")) {
			throw new BadRequestException("To place is null");
		}else if(request.get("fromPlace").toString().equalsIgnoreCase(request.get("toPlace").toString())){
			throw new BadRequestException("From Place And To Place are same");
		}else if(request.get("modeOfTravelId")==null && Integer.parseInt(request.get("modeOfTravelId").toString())==0 && request.get("modeOfTravelId").toString().equalsIgnoreCase("") && request.get("modeOfTravelId").toString().equalsIgnoreCase("null")) {
			throw new BadRequestException("Mode of travel Id is null");
		}else {
			if(Long.parseLong(request.get("modeOfTravelId").toString())==8) {
				if(request.get("ownConveyanceId")==null && Integer.parseInt(request.get("ownConveyanceId").toString())==0 && request.get("ownConveyanceId").toString().equalsIgnoreCase("") && request.get("ownConveyanceId").toString().equalsIgnoreCase("null")) {
					throw new BadRequestException("Own conveyance Id is null");
				}
				if(request.get("distance")==null && Double.parseDouble(request.get("distance").toString())==0 && request.get("distance").toString().equalsIgnoreCase("") && request.get("distance").toString().equalsIgnoreCase("null") && Long.parseLong(request.get("distance").toString())==0) {
					throw new BadRequestException("Distance is 0");
				}
			}else {
				if(!Boolean.parseBoolean(request.get("companyPaid").toString())) {
					if(request.get("fare")==null && Double.parseDouble(request.get("fare").toString())==0 && request.get("fare").toString().equalsIgnoreCase("") && request.get("fare").toString().equalsIgnoreCase("null")  && Long.parseLong(request.get("fare").toString())==0) {
						throw new BadRequestException("Fare is 0");
					}
				}
				
			}
		}
		
		TourExpenseRequest req=new TourExpenseRequest();
		if(request.get("btExpenseManagementId")!=null  && Long.parseLong(request.get("btExpenseManagementId").toString())!=0 && !request.get("btExpenseManagementId").toString().equalsIgnoreCase("") && !request.get("btExpenseManagementId").toString().equalsIgnoreCase("null")) {
			req.setExpenseManagementId(Long.parseLong(request.get("btExpenseManagementId").toString()));
		}
		if(request.get("departureDate")!=null && !request.get("departureDate").toString().equalsIgnoreCase("") && !request.get("departureDate").toString().equalsIgnoreCase("null")) {
			try {
				req.setDepartureDate(sdf.parse(request.get("departureDate").toString()));
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
		} 
		if(request.get("departureTime")!=null && !request.get("departureTime").toString().equalsIgnoreCase("") && !request.get("departureTime").toString().equalsIgnoreCase("null")) {
			req.setDepartureTime(request.get("departureTime").toString());
		} 
		if(request.get("arrivalDate")!=null && !request.get("arrivalDate").toString().equalsIgnoreCase("") && !request.get("arrivalDate").toString().equalsIgnoreCase("null")) {
			try {
				req.setArrivalDate(sdf.parse(request.get("arrivalDate").toString()));
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
		} 
		if(request.get("arrivalTime")!=null && !request.get("arrivalTime").toString().equalsIgnoreCase("") && !request.get("arrivalTime").toString().equalsIgnoreCase("null")) {
			req.setArrivalTime(request.get("arrivalTime").toString());
		}
		if(request.get("fromPlace")!=null && !request.get("fromPlace").toString().equalsIgnoreCase("") && !request.get("fromPlace").toString().equalsIgnoreCase("null")) {
			req.setFromPlace(request.get("fromPlace").toString());
		} 
		if(request.get("toPlace")!=null && !request.get("toPlace").toString().equalsIgnoreCase("") && !request.get("toPlace").toString().equalsIgnoreCase("null")) {
			req.setToPlace(request.get("toPlace").toString());
		}
		if(request.get("modeOfTravelId")!=null && !request.get("modeOfTravelId").toString().equalsIgnoreCase("") && !request.get("modeOfTravelId").toString().equalsIgnoreCase("null")) {
			req.setModeOfTravelId(Integer.parseInt(request.get("modeOfTravelId").toString()));
		}
		if(request.get("ownConveyanceId")!=null && !request.get("ownConveyanceId").toString().equalsIgnoreCase("") && !request.get("ownConveyanceId").toString().equalsIgnoreCase("null")) {
			req.setOwnConveyanceId(Integer.parseInt(request.get("ownConveyanceId").toString()));
		}
		if(request.get("companyPaid")!=null && !request.get("companyPaid").toString().equalsIgnoreCase("") && !request.get("companyPaid").toString().equalsIgnoreCase("null")) {
			req.setCompanyPaid(Boolean.parseBoolean(request.get("companyPaid").toString()));
		}
		if(request.get("fare")!=null && !request.get("fare").toString().equalsIgnoreCase("") && !request.get("fare").toString().equalsIgnoreCase("null")) {
			req.setFare(Double.parseDouble(request.get("fare").toString()));
		}
		if(request.get("approvedAmount")!=null && !request.get("approvedAmount").toString().equalsIgnoreCase("") && !request.get("approvedAmount").toString().equalsIgnoreCase("null")) {
			req.setApprovedAmount(Double.parseDouble(request.get("approvedAmount").toString()));
		}
		if(request.get("distance")!=null && !request.get("distance").toString().equalsIgnoreCase("") && !request.get("distance").toString().equalsIgnoreCase("null")) {
			req.setDistance(Double.parseDouble(request.get("distance").toString()));
		}
		if(request.get("remark")!=null && !request.get("remark").toString().equalsIgnoreCase("") && !request.get("remark").toString().equalsIgnoreCase("null")) {
			req.setRemark(request.get("remark").toString());
		}
		if(request.get("amountToDisbursed")!=null && !request.get("amountToDisbursed").toString().equalsIgnoreCase("") && !request.get("amountToDisbursed").toString().equalsIgnoreCase("null")) {
			req.setAmountToDisbursed(Double.parseDouble(request.get("amountToDisbursed").toString()));
		}
		TourExpenseRequest reqs=BtTourExpenseDBImpl.saveDetails(req, user);
		return btTourExpenseDB.getById(reqs.getId());
	}

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = { Exception.class,BadRequestException.class,UnauthoriseException.class,
			RuntimeException.class }, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Map<String, Object> updateObject(Map<String, Object> request, LoginUser user)
			throws BadRequestException, UnauthoriseException {

		if(request.get("id")==null && Long.parseLong(request.get("id").toString())==0 &&  request.get("id").toString().equalsIgnoreCase("") && request.get("id").toString().equalsIgnoreCase("null")) {
			throw new BadRequestException("Tour Expense id is null");
		}
		if(request.get("btExpenseManagementId")==null && Long.parseLong(request.get("btExpenseManagementId").toString())==0 &&  request.get("btExpenseManagementId").toString().equalsIgnoreCase("") && request.get("btExpenseManagementId").toString().equalsIgnoreCase("null")) {
			throw new BadRequestException("Expense id is null");
		}else if(request.get("departureDate")==null && request.get("departureDate").toString().equalsIgnoreCase("") && request.get("departureDate").toString().equalsIgnoreCase("null")) {
			throw new BadRequestException("Departure date is null");
		}else if(request.get("departureTime")==null && request.get("departureTime").toString().equalsIgnoreCase("") && request.get("departureTime").toString().equalsIgnoreCase("null")) {
			throw new BadRequestException("Departure time is null");
		}else if(request.get("arrivalDate")==null && request.get("arrivalDate").toString().equalsIgnoreCase("") && request.get("arrivalDate").toString().equalsIgnoreCase("null")) {
			throw new BadRequestException("Arrival date is null");
		}else if(request.get("arrivalTime")==null && request.get("arrivalTime").toString().equalsIgnoreCase("") && request.get("arrivalTime").toString().equalsIgnoreCase("null")) {
			throw new BadRequestException("Arrival time is null");
		}else if(request.get("fromPlace")==null && request.get("fromPlace").toString().equalsIgnoreCase("") && request.get("fromPlace").toString().equalsIgnoreCase("null")) {
			throw new BadRequestException("From place is null");
		}else if(request.get("toPlace")==null && request.get("toPlace").toString().equalsIgnoreCase("") && request.get("toPlace").toString().equalsIgnoreCase("null")) {
			throw new BadRequestException("To place is null");
		}else if(request.get("fromPlace").toString().equalsIgnoreCase(request.get("toPlace").toString())){
			throw new BadRequestException("From Place And To Place are same");
		}else if(request.get("modeOfTravelId")==null && Integer.parseInt(request.get("modeOfTravelId").toString())==0 && request.get("modeOfTravelId").toString().equalsIgnoreCase("") && request.get("modeOfTravelId").toString().equalsIgnoreCase("null")) {
			throw new BadRequestException("Mode of travel Id is null");
		}else {
			if(Long.parseLong(request.get("modeOfTravelId").toString())==8) {
				if(request.get("ownConveyanceId")==null && Integer.parseInt(request.get("ownConveyanceId").toString())==0 && request.get("ownConveyanceId").toString().equalsIgnoreCase("") && request.get("ownConveyanceId").toString().equalsIgnoreCase("null")) {
					throw new BadRequestException("Own conveyance Id is null");
				}
				if(request.get("distance")==null && Double.parseDouble(request.get("distance").toString())==0 && request.get("distance").toString().equalsIgnoreCase("") && request.get("distance").toString().equalsIgnoreCase("null") ) {
					throw new BadRequestException("Distance is 0");
				}
			}else {
				if(!Boolean.parseBoolean(request.get("companyPaid").toString())) {
					if(request.get("fare")==null && Double.parseDouble(request.get("fare").toString())==0 && request.get("fare").toString().equalsIgnoreCase("") && request.get("fare").toString().equalsIgnoreCase("null") ) {
						throw new BadRequestException("Fare is 0");
					}
				}
				
			}
		}
		
		TourExpenseRequest req=new TourExpenseRequest();
		
		if(request.get("id")!=null && Long.parseLong(request.get("id").toString())!=0 && !request.get("id").toString().equalsIgnoreCase("") && !request.get("id").toString().equalsIgnoreCase("null")) {
			req.setId(Long.parseLong(request.get("id").toString()));
		}
		if(request.get("btExpenseManagementId")!=null && Long.parseLong(request.get("btExpenseManagementId").toString())!=0 && !request.get("btExpenseManagementId").toString().equalsIgnoreCase("") && !request.get("btExpenseManagementId").toString().equalsIgnoreCase("null")) {
			req.setExpenseManagementId(Long.parseLong(request.get("btExpenseManagementId").toString()));
		}
		if(request.get("departureDate")!=null && !request.get("departureDate").toString().equalsIgnoreCase("") && !request.get("departureDate").toString().equalsIgnoreCase("null")) {
			try {
				req.setDepartureDate(sdf.parse(request.get("departureDate").toString()));
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
		} 
		if(request.get("departureTime")!=null && !request.get("departureTime").toString().equalsIgnoreCase("") && !request.get("departureTime").toString().equalsIgnoreCase("null")) {
			req.setDepartureTime(request.get("departureTime").toString());
		} 
		if(request.get("arrivalDate")!=null && !request.get("arrivalDate").toString().equalsIgnoreCase("") && !request.get("arrivalDate").toString().equalsIgnoreCase("null")) {
			try {
				req.setArrivalDate(sdf.parse(request.get("arrivalDate").toString()));
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
		} 
		if(request.get("arrivalTime")!=null && !request.get("arrivalTime").toString().equalsIgnoreCase("") && !request.get("arrivalTime").toString().equalsIgnoreCase("null")) {
			req.setArrivalTime(request.get("arrivalTime").toString());
		}
		if(request.get("fromPlace")!=null && !request.get("fromPlace").toString().equalsIgnoreCase("") && !request.get("fromPlace").toString().equalsIgnoreCase("null")) {
			req.setFromPlace(request.get("fromPlace").toString());
		} 
		if(request.get("toPlace")!=null && !request.get("toPlace").toString().equalsIgnoreCase("") && !request.get("toPlace").toString().equalsIgnoreCase("null")) {
			req.setToPlace(request.get("toPlace").toString());
		}
		if(request.get("modeOfTravelId")!=null && !request.get("modeOfTravelId").toString().equalsIgnoreCase("") && !request.get("modeOfTravelId").toString().equalsIgnoreCase("null")) {
			req.setModeOfTravelId(Integer.parseInt(request.get("modeOfTravelId").toString()));
		}
		if(request.get("ownConveyanceId")!=null && !request.get("ownConveyanceId").toString().equalsIgnoreCase("") && !request.get("ownConveyanceId").toString().equalsIgnoreCase("null")) {
			req.setOwnConveyanceId(Integer.parseInt(request.get("ownConveyanceId").toString()));
		}
		if(request.get("companyPaid")!=null && !request.get("companyPaid").toString().equalsIgnoreCase("") && !request.get("companyPaid").toString().equalsIgnoreCase("null")) {
			req.setCompanyPaid(Boolean.parseBoolean(request.get("companyPaid").toString()));
		}
		if(request.get("fare")!=null && !request.get("fare").toString().equalsIgnoreCase("") && !request.get("fare").toString().equalsIgnoreCase("null")) {
			req.setFare(Double.parseDouble(request.get("fare").toString()));
		}
		if(request.get("approvedAmount")!=null && !request.get("approvedAmount").toString().equalsIgnoreCase("") && !request.get("approvedAmount").toString().equalsIgnoreCase("null")) {
			req.setApprovedAmount(Double.parseDouble(request.get("approvedAmount").toString()));
		}
		if(request.get("distance")!=null && !request.get("distance").toString().equalsIgnoreCase("") && !request.get("distance").toString().equalsIgnoreCase("null")) {
			req.setDistance(Double.parseDouble(request.get("distance").toString()));
		}
		if(request.get("remark")!=null && !request.get("remark").toString().equalsIgnoreCase("") && !request.get("remark").toString().equalsIgnoreCase("null")) {
			req.setRemark(request.get("remark").toString());
		}
		if(request.get("amountToDisbursed")!=null && !request.get("amountToDisbursed").toString().equalsIgnoreCase("") && !request.get("amountToDisbursed").toString().equalsIgnoreCase("null")) {
			req.setAmountToDisbursed(Double.parseDouble(request.get("amountToDisbursed").toString()));
		}
		TourExpenseRequest reqs=BtTourExpenseDBImpl.updateDetails(req, user);
		return btTourExpenseDB.getById(reqs.getId());
	}

	@Override
	public List<Map<String, Object>> searchForList(String search, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> advanceSearch(Map<String, Object> searchRequest, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Map<String, Object>> getList(long btExpenseId, Pageable page) throws BadRequestException {
		if(btExpenseId!=0)
		{
			return btTourExpenseDB.getByBtExpenseManagmentId(btExpenseId, page);
		}
		else
		{
			throw new BadRequestException("BtExpense Id is null");
		}
	}

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = { Exception.class,BadRequestException.class,UnauthoriseException.class,
			RuntimeException.class }, propagation = Propagation.REQUIRES_NEW)
	@Override
	public StatusMessageResponse deleteTourExpense(long id, LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		return BtTourExpenseDBImpl.deleteTourExpense(id, user);
	}

}
