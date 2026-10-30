package com.pratikshat.exicom.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.model.response.ServiceAppointmentResponse;
import com.pratikshat.exicom.repository.AccountsRepository;
import com.pratikshat.exicom.repository.ServiceAppointmentRepository;
import com.pratikshat.exicom.repository.ServiceRecourseRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.VendorInventoryService;

@RestController
@RequestMapping("api/vendor")
public class VendorInventoryController {
	
	@Autowired
	ServiceAppointmentRepository dbServiceAppoitment;
	
	@Autowired
	UserProfileRepository dbProfile;
	
	@Autowired
	VendorInventoryService appointmentRepositoryCustom;
	
	@Autowired
	ServiceRecourseRepository dbServiceResource;
	
	@Autowired
	AccountsRepository dbAccount;
	
	@GetMapping
	public Page<ServiceAppointmentResponse> getVendorInventory(@CurrentUser LoginUser user,Pageable page){
		Map<String,String> result=	dbProfile.getAccountAndRoleGroup(user);
		String s = result.get("accountSapNumber");
		return dbServiceAppoitment.getVendorServiceAppointment(s,page);
	}
	
	@PutMapping("assign/{id}")
	public Map<String, Object> selfAssign(@CurrentUser LoginUser user,@PathVariable("id") long id) throws Exception{
		return appointmentRepositoryCustom.reAssignServiceAppointment(id, user);
	}
	
	@GetMapping("forlist")
	public List<Map<String, Object>> getServiceResource(@CurrentUser LoginUser user){
		Map<String,String> result=	dbProfile.getAccountAndRoleGroup(user);
		String s = result.get("accountSapNumber");
		List<String> list=dbAccount.getAccountsUser(s);
		return dbServiceResource.getServiceResource(list);
	}
	
	@PutMapping("assignto")
	public Map<String, Object> assignTo(@CurrentUser LoginUser user,@RequestBody Map<String, Object> req) throws Exception{
		return appointmentRepositoryCustom.assignToServiceAppointment(req, user);
	}
	
	@PutMapping("unassign")
	public Map<String, Object> unAssign(@CurrentUser LoginUser user,@RequestBody Map<String, Object> req) throws Exception{
		return appointmentRepositoryCustom.unAssignServiceAppointment(req, user);
	}

}
