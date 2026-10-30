package com.pratikshat.exicom.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.BillOfMaterial;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.BillOfMaterialRequest;
import com.pratikshat.exicom.repository.BillOfMaterialRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;

@RestController
@RequestMapping("api/bom")
public class BillOfMaterialController {
	@Autowired
	BillOfMaterialRepository dbBom;
	@Autowired
	UserProfileRepository dbProfile;
	
	@GetMapping("{partcode}")
	List<Map<String,Object>> getBuillOfMaterial(@PathVariable("partcode")String partCode,HttpServletResponse response,@CurrentUser LoginUser user) throws java.io.IOException{
		if(dbProfile.hasObjectAccess(user.getUsername(), com.pratikshat.exicom.entity.BillOfMaterial.class, UserProfileRepository.VIEW)) {
			//return dbBom.getBillOfMaterialOfproduct(partCode);
			return getBom(partCode);
		}
		return new ArrayList();
	}
	@PostMapping("{partcode}")
	List<Map<String,Object>> saveBom(@PathVariable("partcode")String partCode,@RequestBody List<BillOfMaterialRequest> req,HttpServletResponse response,@CurrentUser LoginUser user) throws java.io.IOException{
		if(!dbProfile.hasObjectAccess(user.getUsername(), BillOfMaterial.class, UserProfileRepository.CREATE)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN,"You are not authorised");
			return null;
		}
		try {
			dbBom.saveBillOfMaterial(partCode, req, user);
			return dbBom.getBillOfMaterialOfproduct(partCode);
			
		}catch(BadRequestException ex) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,ex.getMessage());
		}
		catch(UnauthoriseException ex) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN,ex.getMessage());
		}
		return null;
	}
	@PostMapping("/additems/{partcode}")
	List<Map<String,Object>> addItemToBom(@PathVariable("partcode")String partCode,@RequestBody BillOfMaterialRequest req,HttpServletResponse response,@CurrentUser LoginUser user)throws java.io.IOException{
		if(!dbProfile.hasObjectAccess(user.getUsername(), BillOfMaterial.class, UserProfileRepository.CREATE)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN,"You are not authorised");
			return null;
		}
		try {
			dbBom.addProductToBillOfMaterial(partCode, req, user);
			return dbBom.getBillOfMaterialOfproduct(partCode);
		}catch(BadRequestException ex) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,ex.getMessage());
		}
		catch(UnauthoriseException ex) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN,ex.getMessage());
		}
		return null;
	}
	
	@PostMapping("bom/bulk/{productcode}")
	public void updateBillOfMaterial(@RequestParam("file")MultipartFile file,HttpServletResponse response,@CurrentUser LoginUser user)throws IOException{
		if(!dbProfile.hasObjectAccess(user.getUsername(), BillOfMaterial.class, UserProfileRepository.CREATE)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN,"You are not authorise");
			return;
		}
	}
	
	List<Map<String,Object>> getBom(String partcode){
		List<Map<String,Object>> mp=new ArrayList<Map<String,Object>>();//dbBom.getBillOfMaterialOfproduct(partcode);
		List<Map<String,Object>> _ch=new ArrayList<Map<String,Object>>();
		mp=dbBom.getBillOfMaterialOfproduct(partcode);
		
		if(mp!=null&&mp.size()>0) {
			for(Map<String,Object> m:mp) {
				
				_ch.add(m);
				List<Map<String,Object>> _m=getBom(m.get("rawProductPartCode").toString());
				if(_m!=null&&_m.size()>0) {
//					for(Map<String,Object> __m:_m) {
//						__m.put("parent_part_code_partcode", m.get("rawProductPartCode").toString());
//						_ch.add(__m);
//					}
					_ch.addAll(_m);
				}
			}
		}
		return _ch;
	}
	
}
