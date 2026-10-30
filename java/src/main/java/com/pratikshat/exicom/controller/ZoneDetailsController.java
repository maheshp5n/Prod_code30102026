package com.pratikshat.exicom.controller;



import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.GlobalSearchIndex;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.ZoneDetails;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.GlobalSearchIndexRepository;
import com.pratikshat.exicom.repository.ZoneDetailsRepository;


@RestController
@RequestMapping("api/zone")
public class ZoneDetailsController {
	@Autowired
	private ZoneDetailsRepository dbzone;
	@Autowired
	com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	
	@Autowired
	GlobalSearchIndexRepository dbGlobalSearch;
	
	@GetMapping
	public Page<ZoneDetails> getAllzone(Pageable page, @CurrentUser LoginUser user){
		if(dbProfile.hasObjectAccess(user.getUsername(), "ZoneDetails", "viewAllRights")||dbProfile.hasObjectAccess(user.getUsername(), "ZoneDetails", "viewRights")) {
			return dbzone.findAll(page);	
		}
		return Page.empty();
		
	}
	@GetMapping("forlist")
	public List<ZoneDetails> getforList(){
		return dbzone.findAll();
	}
	@PostMapping
	public ZoneDetails postZone(@RequestBody ZoneDetails details,@com.pratikshat.exicom.annotation.CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse response)throws IOException {
		
		if(!dbProfile.hasObjectAccess(user.getUsername(),"ZoneDetails", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
		
			details.setCreatedBy(new EmployeeDetails(user.getUsername()));
			details.setModifyedBy(new EmployeeDetails(user.getUsername()));
			details.setCreatedDate(new java.util.Date());
			details.setModifyedDate(new java.util.Date());
			dbzone.save(details);
			
			try {
				GlobalSearchIndex _search=new GlobalSearchIndex();
				_search.setSearchString(details.getZoneName());
				_search.setKeyName("zoneName");
				_search.setObjectName("ZoneDetails");
				_search.setObjectDetailsUrl("zone");
				_search.setObjectIdName("zoneName");
				_search.setObjectIdValue(details.getZoneName());
				
				dbGlobalSearch.save(_search);
				
			}catch(Exception e) {}
			
			return dbzone.findByZoneName(details.getZoneName());
			
		}catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
		
	}
//	shagufta
//	
	@GetMapping("forlist/bydomain/{id}")
	public List<String> getforListForBT(@PathVariable("id")long id){
		return dbzone.findByBuisDomainId(id);
	}
}
