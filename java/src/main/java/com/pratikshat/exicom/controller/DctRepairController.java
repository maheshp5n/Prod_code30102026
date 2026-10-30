package com.pratikshat.exicom.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.DctRepairRequest;
import com.pratikshat.exicom.model.response.DctRepairItemConsumedResponse;
import com.pratikshat.exicom.model.response.DctRepairResponse;
import com.pratikshat.exicom.repository.DctRepairProblemMasterRepository;
import com.pratikshat.exicom.repository.LocationRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;

@RestController
@RequestMapping("api/dctrepair")
public class DctRepairController {

	@Autowired
	private LocationRepository dbLocation;

	@Autowired
	com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	@Autowired
	com.pratikshat.exicom.repository.DctRepairRepository dbRepair;
	
	@Autowired
	DctRepairProblemMasterRepository dbProblemMaster;
	
	int businessDomainId =2 ;

	@GetMapping("productstatus")
	public List<Map<String,Object>> getProductStatus(){
		return dbRepair.getProductStatus();
	}
	@GetMapping("itemforrepair")
	public Page<com.pratikshat.exicom.model.response.LocationFaultyLineItemResponse> getFaultyItems(Pageable page,
			@CurrentUser LoginUser user) {

		if (dbProfile.hasObjectAccess(user.getUsername(), "DctRepair", "viewAllRights")
				|| dbProfile.hasObjectAccess(user.getUsername(), "DctRepair", "viewRights")) {
			
			return dbLocation.getFaultyLineItemByLocationOwner(user.getUsername(), page, businessDomainId);
		}
		return Page.empty();
	}
	
	@PostMapping
	public ResponseEntity<?> saveDetails(@CurrentUser LoginUser user,@RequestBody DctRepairRequest request  ,HttpServletResponse response) throws java.io.IOException{
		if(!dbProfile.hasObjectAccess(user.getUsername(),com.pratikshat.exicom.entity.DctRepair.class, UserProfileRepository.CREATE)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		try {
			dbRepair.repairItems(request, user);
			return ResponseEntity.ok("{}");
		}catch(BadRequestException bd) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,bd.getMessage());
		}catch(UnauthoriseException ex) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN,ex.getMessage());
		}
		return null;
	}

	
	@GetMapping("{id}")
	public com.pratikshat.exicom.model.response.LocationFaultyLineItemResponse getDetails(@CurrentUser LoginUser user,@PathVariable("id") long id  ,HttpServletResponse response) throws java.io.IOException{
		if(!dbProfile.hasObjectAccess(user.getUsername(),com.pratikshat.exicom.entity.DctRepair.class, UserProfileRepository.VIEW) || !dbProfile.hasObjectAccess(user.getUsername(),com.pratikshat.exicom.entity.DctRepair.class, UserProfileRepository.VIEW_ALL)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		try {
			return dbLocation.getFaultyLineItemById(id);
		}catch(Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
		}
		return null;
	}
	
	@GetMapping
	public Page<DctRepairResponse> getDctRepairBySerialNoAndPartcode(@RequestParam("serialNo")String serialNo, @RequestParam("partcode")String partcode, Pageable pageable, @CurrentUser LoginUser user, HttpServletResponse response) throws IOException{
		if(!dbProfile.hasObjectAccess(user.getUsername(),com.pratikshat.exicom.entity.DctRepair.class, UserProfileRepository.VIEW) || !dbProfile.hasObjectAccess(user.getUsername(),com.pratikshat.exicom.entity.DctRepair.class, UserProfileRepository.VIEW_ALL)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			return dbRepair.getDctRepairByPartcodeAndSerialNo(partcode, serialNo, pageable);
		}catch(Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
			return null;
		}
	}
	
	@GetMapping("related/{repairId}")
	public Page<DctRepairItemConsumedResponse> getRepairItemByDctRepairId(@PathVariable("repairId")long repairId, Pageable pageable, @CurrentUser LoginUser user, HttpServletResponse response) throws IOException{
		if(!dbProfile.hasObjectAccess(user.getUsername(),com.pratikshat.exicom.entity.DctRepair.class, UserProfileRepository.VIEW) || !dbProfile.hasObjectAccess(user.getUsername(),com.pratikshat.exicom.entity.DctRepair.class, UserProfileRepository.VIEW_ALL)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			return dbRepair.getRepairItemConsumedByRepairId(repairId, pageable);
		}catch(Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
			return null;
		}
	}
	@GetMapping("{partcode}/okitemsserialnumber")
	public List<String> getSerialnumber(@PathVariable("partcode")String partCode, @CurrentUser LoginUser user,HttpServletResponse response)throws java.io.IOException{
		try {
			return dbRepair.getOkSerialNumbers(partCode, user);
		}catch(BadRequestException ex) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,ex.getMessage());
		}
		catch(UnauthoriseException ex) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN,ex.getMessage());
		}
		return null;
	}
	@GetMapping("problemmaster/{partcode}")
	public List<Map<String,Object>> getProblemMaster(@PathVariable("partcode")String producctPartcode){
		//return dbProblemMaster.getProblemAndResultionByProductPartcode(producctPartcode);
		return dbProblemMaster.getProblemAndResultion();
	}
	
	
}
