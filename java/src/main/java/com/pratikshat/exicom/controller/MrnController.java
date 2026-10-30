package com.pratikshat.exicom.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.MrnRequest;
import com.pratikshat.exicom.services.MrnService;

@RestController
@RequestMapping("api/mrn")
public class MrnController {
	@Autowired
	MrnService dbMrn;
	
	@PostMapping
	MrnRequest saveMrnRequest(@RequestBody MrnRequest request,@CurrentUser LoginUser user,HttpServletResponse response) throws IOException {
		try {
			
			dbMrn.saveMrn(request, user);
			
			return request;
			
		}catch(BadRequestException db) {
			
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, db.getMessage());
		}
		catch(UnauthoriseException ex) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
		}
		return null;
	}
}
