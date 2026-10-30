package com.pratikshat.exicom.controller;

import java.io.IOException;
import java.util.List;

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
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.PriceBookRequest;
import com.pratikshat.exicom.model.response.PriceBookForList;
import com.pratikshat.exicom.model.response.PriceBookResponse;
import com.pratikshat.exicom.repository.PriceBookRepository;

@RestController
@RequestMapping("api/pricebook")
public class PriceBookController {

	@Autowired
	PriceBookRepository dbPriceBook;
	@Autowired
	private com.pratikshat.exicom.repository.UserProfileRepository dbProfile;

	@GetMapping
	public Page<PriceBookResponse> getAll(@CurrentUser LoginUser user, Pageable pageable, HttpServletResponse response)throws IOException {
		if(dbProfile.hasObjectAccess(user.getUsername(), "PriceBook", "viewRights")||dbProfile.hasObjectAccess(user.getUsername(), "PriceBook", "viewAllRights")) {
			try {
				return dbPriceBook.getAll(pageable);
			}catch(Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				return null;
			}
			
		}
		return Page.empty();
		
	}

	@GetMapping("forlist")
	public List<PriceBookForList> getAllPriceBookList(@CurrentUser LoginUser user) {
		return dbPriceBook.getAllPriceBookList();
	}

	@GetMapping("{id}")
	public PriceBookResponse getById(@CurrentUser LoginUser user, @PathVariable("id") long id, HttpServletResponse response)throws IOException {
		if(dbProfile.hasObjectAccess(user.getUsername(), "PriceBook", "viewRights")||dbProfile.hasObjectAccess(user.getUsername(), "PriceBook", "viewAllRights")) {
			try {
				return dbPriceBook.getById(id);
			}catch(Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				return null;
			}
			
		}
		return new PriceBookResponse();
		
	}

	@PostMapping
	public PriceBookResponse createPriceBook(@RequestBody PriceBookRequest priceBookRequest,
			@CurrentUser LoginUser user, HttpServletResponse response)throws IOException {
		
		if(!dbProfile.hasObjectAccess(user.getUsername(),"PriceBook", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			PriceBookRequest _pricBookRequest = dbPriceBook.createPriceBook(priceBookRequest, user);
			return dbPriceBook.getById(_pricBookRequest.getId());
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
	public PriceBookResponse updatePriceBook(@RequestBody PriceBookRequest priceBookRequest,
			@CurrentUser LoginUser user, HttpServletResponse response) throws IOException {
		
		if(!dbProfile.hasObjectAccess(user.getUsername(),"PriceBook", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			PriceBookRequest _pricBookRequest = dbPriceBook.updatePriceBook(priceBookRequest, user);
			return dbPriceBook.getById(_pricBookRequest.getId());
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
