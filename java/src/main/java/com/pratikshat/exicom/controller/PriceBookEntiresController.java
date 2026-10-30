package com.pratikshat.exicom.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.PriceBookEntriesRequest;
import com.pratikshat.exicom.model.response.PriceBookEntriesResponse;
import com.pratikshat.exicom.repository.PriceBookEntriesRepository;

@RestController
@RequestMapping("api/pricebookentry")
public class PriceBookEntiresController {

	@Autowired
	PriceBookEntriesRepository dbPriceBookEntries;
	@Autowired
	private com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	
	@GetMapping("pricebook/{priceBookId}")
	public Page<PriceBookEntriesResponse> getAllEntries(@PathVariable("priceBookId")long priceBookId, Pageable pageable, @CurrentUser LoginUser user, HttpServletResponse response) throws IOException {
		if(dbProfile.hasObjectAccess(user.getUsername(), "PriceBook", "viewRights")||dbProfile.hasObjectAccess(user.getUsername(), "PriceBook", "viewAllRights")) {
			try {
				return dbPriceBookEntries.getByPriceBookId(priceBookId,pageable);
			}catch(Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				return null;
			}
			
		}
		return Page.empty();
		
	}
	@GetMapping("pricebook/list/{priceBookId}")
	public List<PriceBookEntriesResponse> getAllEntries(@PathVariable("priceBookId")long priceBookId, @CurrentUser LoginUser user, HttpServletResponse response) throws IOException {
		if(dbProfile.hasObjectAccess(user.getUsername(), "PriceBook", "viewRights")||dbProfile.hasObjectAccess(user.getUsername(), "PriceBook", "viewAllRights")) {
			try {
				return dbPriceBookEntries.getByPriceBookId(priceBookId);
			}catch(Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				return null;
			}
			
		}
		return new ArrayList<PriceBookEntriesResponse>();
		
	}
	
	
	@GetMapping("{id}")
	public PriceBookEntriesResponse getById(@PathVariable("id")long id, @CurrentUser LoginUser user, HttpServletResponse response)throws IOException {
		if(dbProfile.hasObjectAccess(user.getUsername(), "PriceBook", "viewRights")||dbProfile.hasObjectAccess(user.getUsername(), "PriceBook", "viewAllRights")) {
			try {
				return dbPriceBookEntries.getById(id);
			}catch(Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				return null;
			}
			
		}
		return new PriceBookEntriesResponse();
		
	}
	
	@PostMapping("{priceBookId}")
	public Page<PriceBookEntriesResponse> savePriceBookEntries(@PathVariable("priceBookId")long priceBookId, @RequestBody List<PriceBookEntriesRequest> entries,@CurrentUser LoginUser user,  HttpServletResponse response)throws IOException{
		
		if(!dbProfile.hasObjectAccess(user.getUsername(),"PriceBook", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			List<PriceBookEntriesRequest> _pricBookEntryRequestList = dbPriceBookEntries.insertEntries(entries, user);
			 return dbPriceBookEntries.getByPriceBookId(priceBookId, PageRequest.of(0, 20,Sort.by("modifiedDate").descending()));
		} catch(BadRequestException ex) {
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
	
	@PutMapping
	public PriceBookEntriesResponse updatePriceBookEntries(@RequestBody PriceBookEntriesRequest entry, @CurrentUser LoginUser user, HttpServletResponse response) throws IOException {
		
		if(!dbProfile.hasObjectAccess(user.getUsername(),"PriceBook", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			PriceBookEntriesRequest _pricBookEntryRequest = dbPriceBookEntries.updateEntry(entry, user);
			 return dbPriceBookEntries.getById(_pricBookEntryRequest.getId());
		} catch(BadRequestException ex) {
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
	
	
	@GetMapping("pricebookByPartCode")
	public Page<PriceBookEntriesResponse> getPriceBookByProductPartCode(@RequestParam("partCode")String partCode, Pageable pageable, @CurrentUser LoginUser user) {
	return dbPriceBookEntries.getPriceBookByProductPartCode(partCode,pageable);
	}
	
	
	@PostMapping("entry/{priceBookId}")
	public Page<PriceBookEntriesResponse> savePriceBookEntry(@PathVariable("priceBookId")long priceBookId, @RequestBody PriceBookEntriesRequest entry,@CurrentUser LoginUser user,  HttpServletResponse response) throws IOException{
		
		if(!dbProfile.hasObjectAccess(user.getUsername(),"PriceBook", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			PriceBookEntriesRequest _pricBookEntryRequestList = dbPriceBookEntries.insertEntry(entry, user);
			 return dbPriceBookEntries.getByPriceBookId(priceBookId, PageRequest.of(0, 20,Sort.by("modifiedDate").descending()));
		} catch(BadRequestException ex) {
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
}
