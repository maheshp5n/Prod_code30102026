package com.pratikshat.exicom.controller;



import java.io.IOException;

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
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.Warehouse;
import com.pratikshat.exicom.entity.WarehouseRecentVisit;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.WarehouseRequest;
import com.pratikshat.exicom.model.response.WarehouseResponse;
import com.pratikshat.exicom.repository.WarehouseRecentVisitRepository;
import com.pratikshat.exicom.repository.WarehouseRepo;

@RestController
@RequestMapping("api/warehouse")
public class WarehouseController {
	@Autowired
	private WarehouseRepo dbWarehouse;
	@Autowired
	private WarehouseRecentVisitRepository dbRecent;
	@Autowired
	com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	
	@GetMapping
	public Page<WarehouseResponse> getAll(Pageable page, @CurrentUser LoginUser user){
		if(dbProfile.hasObjectAccess(user.getUsername(), "Warehouse", "viewAllRights")||dbProfile.hasObjectAccess(user.getUsername(), "Warehouse", "viewRights")) {
			return dbWarehouse.getByAll(page);
		}
		return Page.empty();
	}
	
	@GetMapping("{id}")
	public WarehouseResponse getWarehouse(@PathVariable("id")long id,@CurrentUser LoginUser user) {
		
		if(dbProfile.hasObjectAccess(user.getUsername(), "Warehouse", "viewAllRights")||dbProfile.hasObjectAccess(user.getUsername(), "Warehouse", "viewRights")) {
			try {
				WarehouseRecentVisit _recent=dbRecent.findByWarehouseIdAndVisitorId(id, user.getUsername());
				if(_recent==null) {
					_recent=new WarehouseRecentVisit(id,user.getUsername());
				}
				else {
					_recent.setVisitDate(new java.util.Date());
				}
				dbRecent.save(_recent);
			}catch(Exception e) {}
			return dbWarehouse.getByWarehouseId(id);
		}
		return new WarehouseResponse();
		
		
	}
	@GetMapping("recentvisit")
	public Page<WarehouseResponse> getRecentVisist(@CurrentUser LoginUser user,Pageable page){
		
		if(dbProfile.hasObjectAccess(user.getUsername(), "Warehouse", "viewAllRights")||dbProfile.hasObjectAccess(user.getUsername(), "Warehouse", "viewRights")) {
			return dbWarehouse.getRecentVisit(user.getUsername(), page);
		}
		return Page.empty();
		
	}
	@PostMapping
	public WarehouseResponse postDetails(@RequestBody WarehouseRequest request,@CurrentUser LoginUser user, HttpServletResponse response) throws IOException {
		
		if(!dbProfile.hasObjectAccess(user.getUsername(),"Warehouse", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			if(dbWarehouse.countByWarehouseId(request.getWarehouseId())>0) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Warehouse Already Exists");
				return null;
			}
			 dbWarehouse.saveWarehouse(request, user);
			 return dbWarehouse.getByWarehouseId(request.getWarehouseId());	
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
	@PutMapping("{warehouseid}")
	public WarehouseResponse putDetails(@PathVariable("warehouseid")long warehouseId,@RequestBody WarehouseRequest request,@CurrentUser LoginUser user, HttpServletResponse response)throws IOException{
		
		if(!dbProfile.hasObjectAccess(user.getUsername(),"Warehouse", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
		
			if(dbWarehouse.countByWarehouseId(warehouseId)==0) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Warehouse does not exists");
				return null;
			}

			if(warehouseId!=request.getWarehouseId()) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Warehouse does not exists");
				return null;
			}

			Warehouse _warehouse=dbWarehouse.findByWarehouseId(warehouseId);
			_warehouse.setLegalEntity(new com.pratikshat.exicom.entity.LegalEntity(request.getLegalEntityId()));
			_warehouse.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			_warehouse.setModifyiedDate(new java.util.Date());
			_warehouse.setSynced(false);
			_warehouse.setWarehouseEmail(request.getWarehouseEmail());
			_warehouse.setWarehouseName(request.getWarehouseName());
			_warehouse.setWarehousePlant(request.getWarehousePlant());
			dbWarehouse.save(_warehouse);
			return dbWarehouse.getByWarehouseId(warehouseId);
			
		}catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
		
	}
	@GetMapping("forlist")
	java.util.List<com.pratikshat.exicom.model.response.WarehouseForList> getWarehouseForList(){
		return dbWarehouse.getWarehouseForList();
	}
}
