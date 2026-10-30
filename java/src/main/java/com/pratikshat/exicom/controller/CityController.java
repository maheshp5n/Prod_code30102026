package com.pratikshat.exicom.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.CircleDetails;
import com.pratikshat.exicom.entity.CityDetails;
import com.pratikshat.exicom.entity.CityRecentVisit;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.response.CircleDetailsResponse;
import com.pratikshat.exicom.model.response.CityDetailsResponse;
import com.pratikshat.exicom.model.response.CityForListReponse;
import com.pratikshat.exicom.repository.CityCatagoryRepository;
import com.pratikshat.exicom.repository.CityDetailsRepository;
import com.pratikshat.exicom.repository.CityRecentVisitRepo;
import com.pratikshat.exicom.repository.UserProfileRepository;

@RestController
@RequestMapping("api/city")
public class CityController {
	@Autowired
	CityDetailsRepository dbCity;
	@Autowired
	CityRecentVisitRepo dbRecent;
	@Autowired
	CityCatagoryRepository dbCatagory;
	@Autowired
	private com.pratikshat.exicom.repository.UserProfileRepository dbUserProfile;
	
	@GetMapping
	public Page<com.pratikshat.exicom.model.response.CityDetailsResponse> getAll(Pageable page, @CurrentUser LoginUser user,@RequestParam(value="filtername",required=false)String filtername){
		if(dbUserProfile.hasObjectAccess(user.getUsername(), "CityDetails", "viewAllRights") || dbUserProfile.hasObjectAccess(user.getUsername(), "CityDetails", "viewRights")) {
			if(filtername!=null&&filtername.equalsIgnoreCase("recentvisit"))
				return dbCity.getRecentAll(user.getUsername(),page);
			
			return dbCity.getAll(page);
		}
		return Page.empty();
		
	}
	@GetMapping("catagory")
	public List<com.pratikshat.exicom.entity.CityCatagory> getCatagory(){
		return dbCatagory.findAll();
	}
	@GetMapping("{cityname}")
	public com.pratikshat.exicom.model.response.CityDetailsResponse getByCityName(@PathVariable("cityname")String cityName,@CurrentUser LoginUser user){
		
		if(dbUserProfile.hasObjectAccess(user.getUsername(), "CityDetails", "viewAllRights") || dbUserProfile.hasObjectAccess(user.getUsername(), "CityDetails", "viewRights")) {
			
			try {
				CityRecentVisit recent=dbRecent.findByCityNameAndVisitoryId(cityName, user.getUsername());
				if(recent!=null) {
					recent.setVisitDate(new Date());
				}
				else {
					recent=new CityRecentVisit(cityName, user.getUsername(),new Date());
				}
				dbRecent.save(recent);
			}catch(Exception e) {}
			return dbCity.getByCityName(cityName);
		}
		
		return new com.pratikshat.exicom.model.response.CityDetailsResponse();
		
	}
	@GetMapping("recentvisit")
	public Page<com.pratikshat.exicom.model.response.CityDetailsResponse> getCityRecent(@CurrentUser LoginUser user,Pageable page){
		return dbCity.getRecentAll(user.getUsername(),page);
	}
	@PostMapping
	public com.pratikshat.exicom.model.response.CityDetailsResponse postCityDetails(@CurrentUser LoginUser user,@RequestBody com.pratikshat.exicom.model.request.CityDetails cityDetails, HttpServletResponse response) throws IOException{
		
		if(!dbUserProfile.hasObjectAccess(user.getUsername(), "CityDetails", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You don't have permission");
			return null;
		}
		
		try {
			
			if(dbCity.countByCityName(cityDetails.getCityName())>0) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "City Already Exist");
				return null;
			}
			
			dbCity.saveCityDetails(cityDetails,user.getUsername());
			
			return dbCity.getByCityName(cityDetails.getCityName());
			
		}catch(BadRequestException ex) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		}catch(UnauthoriseException ex) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		} catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
		
	}
	@PutMapping("{cityname}")
	public com.pratikshat.exicom.model.response.CityDetailsResponse udpate(@CurrentUser LoginUser user,@RequestBody com.pratikshat.exicom.model.request.CityDetails cityDetails,@PathVariable("cityname")String cityName, HttpServletResponse response)throws IOException{
		
		if(!dbUserProfile.hasObjectAccess(user.getUsername(), "CityDetails", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You don't have permission");
			return null;
		}
		
		try {
			if(dbCity.countByCityName(cityName)==0) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "City does not exist");
			}
			
			dbCity.updateCityCatagory_cityCatagoryNameAndCityNameByCityName(cityDetails.getCityCategoryId(),cityName,new Date(), user.getUsername(),cityDetails.getCityName(),false);
			
			return dbCity.getByCityName(cityName);
		} catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
		
	}
	
	
	@GetMapping("forlist")
	public List<CityForListReponse> getCityForList(){
		return dbCity.getCityForList();
	}
	
	
	@GetMapping("advancesearch")
	public List<CityDetailsResponse> getAdvanceSearchresult(@RequestParam Map<String,Object> request,@CurrentUser LoginUser user,HttpServletResponse  response) throws IOException{
		if(dbUserProfile.hasObjectAccess(user.getUsername(), CityDetails.class, UserProfileRepository.VIEW) || 
				dbUserProfile.hasObjectAccess(user.getUsername(), CityDetails.class, UserProfileRepository.VIEW_ALL)) {
			return dbCity.advanceSearch(request, user, null);
		}
		response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
		return null;
		
	}
}
