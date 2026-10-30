package com.pratikshat.exicom.controller;

import java.io.IOException;
import java.text.ParseException;
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
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.GlobalSearchIndex;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.ProductType;
import com.pratikshat.exicom.entity.ProductsRecentViews;
import com.pratikshat.exicom.entity.ServiceOrder;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.GlobalSearchIndexRepository;
import com.pratikshat.exicom.repository.ProductRepository;
import com.pratikshat.exicom.repository.ProductTypeRepository;
import com.pratikshat.exicom.repository.ProductsRecentVisitRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;

@RestController
@RequestMapping("api/products")
public class ProductsController {
	@Autowired
	ProductRepository dbProducts;
	@Autowired
	ProductsRecentVisitRepository dbRecent;
	@Autowired
	ProductTypeRepository dbProductType;
	@Autowired
	private com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	
	@Autowired
	GlobalSearchIndexRepository dbGlobalSearch;
	
	@GetMapping
	public Page<com.pratikshat.exicom.model.response.Products> getAll(Pageable page,@RequestParam(value="type",required=false)String type,@RequestParam(value="filtername",required=false)String filtername ,@CurrentUser LoginUser user, HttpServletResponse response)throws IOException{
		if(dbProfile.hasObjectAccess(user.getUsername(), "Products", "viewRights")||dbProfile.hasObjectAccess(user.getUsername(), "Products", "viewAllRights")) {
			try {
				if(type!=null) {
					return dbProducts.getAll(type, page);
				}
				if(filtername!=null&&filtername.equalsIgnoreCase("All"))
					return dbProducts.getAll(page);
				if(filtername!=null&&filtername.equalsIgnoreCase("related"))
					return dbProducts.getAllRecentViews(user.getUsername(),page);
				
				return dbProducts.getAll(page);
			}catch(Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				return null;
			}
			
		}
		return Page.empty();
		
		
	}
	@GetMapping("{id}")
	public com.pratikshat.exicom.model.response.Products getProductsById(@PathVariable("id")String partCode,@CurrentUser LoginUser user, HttpServletResponse response)throws IOException{
		
		if(dbProfile.hasObjectAccess(user.getUsername(), "Products", "viewRights")||dbProfile.hasObjectAccess(user.getUsername(), "Products", "viewAllRights")) {
			try {
				ProductsRecentViews recent=dbRecent.findByVisitedPartCodeAndUserId(partCode, user.getUsername());
				if(recent!=null) {
					recent.setDateOfVisits(new Date());
				}
				else {
					recent=new ProductsRecentViews(partCode,user.getUsername());
				}
				try {
					dbRecent.save(recent);
					
				}catch(Exception e) {}
				return dbProducts.getByProductPartCode(partCode);
			}catch(Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				return null;
			}
			
		}
		
		return new com.pratikshat.exicom.model.response.Products();
		
	}
	@GetMapping("recent")
	public Page<com.pratikshat.exicom.model.response.Products> getRecentVisits(@CurrentUser LoginUser user,Pageable page, HttpServletResponse response) throws IOException{
		
		if(dbProfile.hasObjectAccess(user.getUsername(), "Products", "viewRights")||dbProfile.hasObjectAccess(user.getUsername(), "Products", "viewAllRights")) {
			try {
				return dbProducts.getAllRecentViews(user.getUsername(),page);
			}catch(Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				return null;
			}
			
		}
		return Page.empty();
	}
	
	
	@PostMapping
	public com.pratikshat.exicom.model.response.Products post(@RequestBody com.pratikshat.exicom.model.request.Products products,@CurrentUser LoginUser user, HttpServletResponse response) throws IOException{
		products.setPartCode(products.getPartCode().trim());
		
		if(!dbProfile.hasObjectAccess(user.getUsername(),"Products", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			if(dbProducts.countByProductPartCode(products.getPartCode())>0) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Already exists");
				return null;
			}
			com.pratikshat.exicom.entity.Products _products=new com.pratikshat.exicom.entity.Products();
			_products.setCreatedBy(new EmployeeDetails(user.getUsername()));
			_products.setCreateddate(new Date());
			_products.setModifiedBy(new EmployeeDetails(user.getUsername()));
			_products.setModifiedDate(new Date());
			_products.setPdoductDescription(products.getProductDescription());
			_products.setProductPartCode(products.getPartCode());
			_products.setProductPrice(products.getPrice());
			_products.setProductType(products.getType());
			_products.setSubProductFamilyId(products.getSubProductFamilyId());
			_products.setSynced(false);
			_products.setActive(true);
			dbProducts.saveAndFlush(_products);
			
			try {
				GlobalSearchIndex _search=new GlobalSearchIndex();
				_search.setSearchString(products.getPartCode());
				_search.setKeyName("productPartCode");
				_search.setObjectName("Products");
				_search.setObjectDetailsUrl("products/"+products.getPartCode());
				_search.setObjectIdName("productPartCode");
				_search.setObjectIdValue(products.getPartCode());
				
				dbGlobalSearch.save(_search);
				
			}catch(Exception e) {}
			
			return dbProducts.getByProductPartCode(_products.getProductPartCode());
		} catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
	}
	@PutMapping("{partCode}")
	public com.pratikshat.exicom.model.response.Products put(@PathVariable("partCode")String partCode, @RequestBody com.pratikshat.exicom.model.request.Products products,@CurrentUser LoginUser user, HttpServletResponse response) throws IOException{
		
		try {
			if(dbProducts.countByProductPartCode(partCode)==0) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Part code does not exist");
				return null;
			}
			if(!partCode.equalsIgnoreCase(products.getPartCode())) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Wrong part code");
				return null;
			}
			com.pratikshat.exicom.entity.Products _products=dbProducts.findByProductPartCode(partCode);
			_products.setModifiedBy(new EmployeeDetails(user.getUsername()));
			_products.setModifiedDate(new Date());
			_products.setPdoductDescription(products.getProductDescription());
			_products.setProductPartCode(products.getPartCode());
			_products.setProductPrice(products.getPrice());
			_products.setProductType(products.getType());
			_products.setSubProductFamilyId(products.getSubProductFamilyId());
			_products.setSynced(false);
			dbProducts.saveAndFlush(_products);
			return dbProducts.getByProductPartCode(partCode);
		}catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
	}
	
	@GetMapping("type")
	public List<ProductType> getProductType(){
		return dbProductType.findAll();
	}
	@GetMapping("forlist")
	public List<com.pratikshat.exicom.model.response.ProductsForList> getProductForList(@RequestParam(value="type",required=false)String type){
		if(type!=null) {
			return dbProducts.getProductsForList(type);
		}
		return dbProducts.getProductsForList();
	}
	@GetMapping("panindiastock")
	public Page<Map<String,Object>> getPanIndiaStock(Pageable page){
		return dbProducts.getPanIndiaStockSummary(page);
	}
	
	@GetMapping("forlist/small")
	public List<com.pratikshat.exicom.model.response.ProductsForList> getProductForListSmall(@RequestParam(value="searchkey")String type,Pageable page){

		Page<com.pratikshat.exicom.model.response.ProductsForList> lst= dbProducts.getProductsForListSmall("%"+type+"%",page);
		
		return lst.getContent();
	}
	
	@GetMapping("advancesearch")
	public List<com.pratikshat.exicom.model.response.Products> getAdvanceSearchresult(@RequestParam Map<String,Object> request,@CurrentUser LoginUser user,HttpServletResponse  response) throws IOException, ParseException{
		
		if(dbProfile.hasObjectAccess(user.getUsername(), com.pratikshat.exicom.entity.Products.class, UserProfileRepository.VIEW_ALL)) {
			return 	dbProducts.advanceSearch(request, null);
		}
		
		if(dbProfile.hasObjectAccess(user.getUsername(), com.pratikshat.exicom.entity.Products.class, UserProfileRepository.VIEW)) {
			return 	dbProducts.advanceSearch(request,null);
		}
		
		response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
		return null;
		
	}
	
	@GetMapping("family/forlist")
	public java.util.List<String> getProductFamilyList() {
		return dbProducts.getProductFamilyList();
	}
	
	@GetMapping("subfamily/forlist")
	public java.util.List<String> getProductSubFamilyList() {
		return dbProducts.getProductSubFamilyList();
	}
}
