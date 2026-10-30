package com.pratikshat.exicom.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.services.WebviewLoginService;

@RestController
@RequestMapping("api/switch")
public class WebviewSwitchController {
	@Autowired
	private WebviewLoginService dbSwitch;
	
	@PostMapping("s")
	public String genreateToken(@CurrentUser LoginUser user,@RequestBody Map<String,String> data,HttpServletResponse response) throws IOException{
		return dbSwitch.generateToken(user, data.get("rt"), data.get("url"));
	}
	@PostMapping("login")
	public EmployeeDetails doLogin(@RequestBody Map<String,String> data,HttpServletResponse response) throws IOException{
		try {
			System.out.printf("rt: %s hash: %s url:%s", data.get("rt"),data.get("hash"),data.get("url"));
			return dbSwitch.getRoute(data.get("rt"), data.get("hash"),data.get("url"));
			
		}catch(BadRequestException ex) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You are not authorise");
		}
		catch(UnauthoriseException ex) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You are not authorise");
		}
		return null;
	}
}
