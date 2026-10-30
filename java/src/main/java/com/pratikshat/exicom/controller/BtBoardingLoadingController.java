package com.pratikshat.exicom.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.BoardingLoadingDetails;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.GlobalSearchIndex;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.model.request.BoardingLoadingDetailsRequest;
import com.pratikshat.exicom.model.response.BoardingLoadingDetailsResponse;
import com.pratikshat.exicom.model.response.BoardingLoadingForListResponse;
import com.pratikshat.exicom.repository.BoardingLoadingRepository;
import com.pratikshat.exicom.repository.GlobalSearchIndexRepository;

@RestController
@RequestMapping("bt/api/boardingloading")
public class BtBoardingLoadingController {
	
	@Autowired
	private BoardingLoadingRepository dbBoarding;
	
	@Autowired
	com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	
	@Autowired
	GlobalSearchIndexRepository dbGlobalSearch;
	
	@GetMapping
	public Page<BoardingLoadingDetailsResponse> getBoardingDetails(@CurrentUser LoginUser user,Pageable page)
	{
		if(dbProfile.hasObjectAccess(user.getUsername(), BoardingLoadingDetails.class, "viewRights") || dbProfile.hasObjectAccess(user.getUsername(), BoardingLoadingDetails.class, "viewAllRights"))
		{
			return dbBoarding.getAll(page);
		}
		return Page.empty();
	}
	
	@GetMapping("{boardingName}")
	public BoardingLoadingDetailsResponse getBoardingLoadingDetail(@CurrentUser LoginUser user, @PathVariable("boardingName") String boardingName)
	{
		if(dbProfile.hasObjectAccess(user.getUsername(), BoardingLoadingDetails.class, "viewRights") || dbProfile.hasObjectAccess(user.getUsername(), BoardingLoadingDetails.class, "viewAllRights"))
		{
			return dbBoarding.getByBoardingName(boardingName);
		}
		
		return new BoardingLoadingDetailsResponse();
	}
	
	@PostMapping
	public BoardingLoadingDetailsResponse postDetails(@CurrentUser LoginUser user,@Valid @RequestBody BoardingLoadingDetailsRequest details,HttpServletResponse response)throws java.io.IOException, BadRequestException {
		if(!dbProfile.hasObjectAccess(user.getUsername(), "BoardingLoadingDetails", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			if(details.getBoardingName()==null || details.getBoardingName().equalsIgnoreCase("")) {
				throw new BadRequestException("Name is null");
			}else if(details.getBoardingClassACity()==0) {
				throw new BadRequestException("Lodging amount of class A city required");
			}else if(details.getBoardingClassBCity()==0) {
				throw new BadRequestException("Lodging amount of class B city required");
			}else if(details.getBoardingClassCCity()==0) {
				throw new BadRequestException("Lodging amount of class C city required");
			}else if(details.getBoardingAmountCityA()==0) {
				throw new BadRequestException("Boarding amount of class A city required");
			}else if(details.getBoardingAmountCityB()==0) {
				throw new BadRequestException("Boarding amount of class B city required");
			}else if(details.getBoardingAmountCityC()==0) {
				throw new BadRequestException("Boarding amount of class C city required");
			}else if(details.getTwoWheelerChargesParKilo()==0) {
				throw new BadRequestException("Two wheeler charge/km required");
			}else if(details.getFourWheelChargesParkilo()==0) {
				throw new BadRequestException("Four wheeler charge/km required");
			}else if(details.getLodgingClassACitySelf()==0) {
				throw new BadRequestException("Lodging self arrangement amount of class A city required");
			}else if(details.getLodgingClassBCitySelf()==0) {
				throw new BadRequestException("Lodging self arrangement amount of class B city required");
			}else if(details.getLodgingClassCCitySelf()==0) {
				throw new BadRequestException("Lodging self arrangement amount of class C city required");
			}else {
				BoardingLoadingDetails boardingLoadingDetails = new BoardingLoadingDetails();
				
				boardingLoadingDetails.setBoardingName(details.getBoardingName());
				
				boardingLoadingDetails.setLodgingClassACity(details.getBoardingClassACity());
				boardingLoadingDetails.setLodgingClassBCity(details.getBoardingClassBCity());
				boardingLoadingDetails.setLodgingClassCCity(details.getBoardingClassCCity());
				
				boardingLoadingDetails.setLodgingClassACitySelf(details.getLodgingClassACitySelf());
				boardingLoadingDetails.setLodgingClassBCitySelf(details.getLodgingClassBCitySelf());
				boardingLoadingDetails.setLodgingClassCCitySelf(details.getLodgingClassCCitySelf());
				
				boardingLoadingDetails.setBoardingAmountCityA(details.getBoardingAmountCityA());
				boardingLoadingDetails.setBoardingAmountCityB(details.getBoardingAmountCityB());
				boardingLoadingDetails.setBoardingAmountCityC(details.getBoardingAmountCityC());
				
				boardingLoadingDetails.setTwoWheelerChargesParKilo(details.getTwoWheelerChargesParKilo());
				boardingLoadingDetails.setTwoWheelerMaxDistance(details.getTwoWheelerMaxDistance());
				
				boardingLoadingDetails.setFourWheelChargesParkilo(details.getFourWheelChargesParkilo());
				boardingLoadingDetails.setFourWheelerMaxDistance(details.getFourWheelerMaxDistance());
				
				boardingLoadingDetails.setAccountOwner(new EmployeeDetails(user.getUsername()));
				
				boardingLoadingDetails.setCreatedBy(new EmployeeDetails(user.getUsername()));
				boardingLoadingDetails.setCreatedDate(new Date());
				
				boardingLoadingDetails.setModifiedBy(new EmployeeDetails(user.getUsername()));
				boardingLoadingDetails.setModifiedDate(new Date());
				
				boardingLoadingDetails.setSynced(false);
				
				dbBoarding.save(boardingLoadingDetails);
				
				try {
					GlobalSearchIndex _search=new GlobalSearchIndex();
					_search.setSearchString(details.getBoardingName());
					_search.setKeyName("boardingName");
					_search.setObjectName("BoardingLoadingDetails");
					_search.setObjectDetailsUrl("Bording_lodging_DA/"+details.getBoardingName());
					_search.setObjectIdName("boardingName");
					_search.setObjectIdValue(details.getBoardingName());
					
					dbGlobalSearch.save(_search);
					
				}catch(Exception e) {}
				
				return dbBoarding.getByBoardingName(boardingLoadingDetails.getBoardingName());
			}
			
		} catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
		
	}
	
	
	@PutMapping
	public BoardingLoadingDetailsResponse updateDetails(@CurrentUser LoginUser user,@Valid @RequestBody BoardingLoadingDetailsRequest details,HttpServletResponse httpResponse) throws java.io.IOException {
		if(!dbProfile.hasObjectAccess(user.getUsername(), "BoardingLoadingDetails", "editRights")) {
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			
			if(details.getBoardingName()==null || details.getBoardingName().equalsIgnoreCase("")) {
				throw new BadRequestException("Name is null");
			}else if(details.getBoardingClassACity()==0) {
				throw new BadRequestException("Lodging amount of class A city required");
			}else if(details.getBoardingClassBCity()==0) {
				throw new BadRequestException("Lodging amount of class B city required");
			}else if(details.getBoardingClassCCity()==0) {
				throw new BadRequestException("Lodging amount of class C city required");
			}else if(details.getBoardingAmountCityA()==0) {
				throw new BadRequestException("Boarding amount of class A city required");
			}else if(details.getBoardingAmountCityB()==0) {
				throw new BadRequestException("Boarding amount of class B city required");
			}else if(details.getBoardingAmountCityC()==0) {
				throw new BadRequestException("Boarding amount of class C city required");
			}else if(details.getTwoWheelerChargesParKilo()==0) {
				throw new BadRequestException("Two wheeler charge/km required");
			}else if(details.getFourWheelChargesParkilo()==0) {
				throw new BadRequestException("Four wheeler charge/km required");
			}else if(details.getLodgingClassACitySelf()==0) {
				throw new BadRequestException("Lodging self arrangement amount of class A city required");
			}else if(details.getLodgingClassBCitySelf()==0) {
				throw new BadRequestException("Lodging self arrangement amount of class B city required");
			}else if(details.getLodgingClassCCitySelf()==0) {
				throw new BadRequestException("Lodging self arrangement amount of class C city required");
			}else {
				BoardingLoadingDetails boardingLodging = dbBoarding.findByBoardingName(details.getBoardingName());
				if(boardingLodging!=null) {
					boardingLodging.setBoardingAmountCityA(details.getBoardingAmountCityA());
					boardingLodging.setBoardingAmountCityB(details.getBoardingAmountCityB());
					boardingLodging.setBoardingAmountCityC(details.getBoardingAmountCityC());
					
					boardingLodging.setLodgingClassACity(details.getBoardingClassACity());
					boardingLodging.setLodgingClassBCity(details.getBoardingClassBCity());
					boardingLodging.setLodgingClassCCity(details.getBoardingClassCCity());
					
					boardingLodging.setLodgingClassACitySelf(details.getLodgingClassACitySelf());
					boardingLodging.setLodgingClassBCitySelf(details.getLodgingClassBCitySelf());
					boardingLodging.setLodgingClassCCitySelf(details.getLodgingClassCCitySelf());
					
					boardingLodging.setTwoWheelerChargesParKilo(details.getTwoWheelerChargesParKilo());
					boardingLodging.setTwoWheelerMaxDistance(details.getTwoWheelerMaxDistance());
					
					boardingLodging.setFourWheelChargesParkilo(details.getFourWheelChargesParkilo());
					boardingLodging.setFourWheelerMaxDistance(details.getFourWheelerMaxDistance());
					
					boardingLodging.setSynced(false);
					
					boardingLodging.setModifiedBy(new EmployeeDetails(user.getUsername()));
					boardingLodging.setModifiedDate(new Date());
					
					dbBoarding.save(boardingLodging);
					
					return dbBoarding.getByBoardingName(boardingLodging.getBoardingName());
			}else {
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,"No Record found for this Boarding Name");
				return null;
			}
			}
		}catch(Exception ex) {
			httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
	}
	
	@GetMapping("boardingforlist")
	public List<BoardingLoadingForListResponse> getBoardingForList(){
		return dbBoarding.getBoardingForList();
	}
	
	

}
