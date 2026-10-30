package com.pratikshat.exicom.controller;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.ProductType;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.BtProductsRepository;
import com.pratikshat.exicom.repository.ProductTypeRepository;
import com.pratikshat.exicom.services.BtProductService;

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

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("bt/api/products")
public class BtProductController {
    @Autowired
    BtProductService product;
    @Autowired
    BtProductsRepository dbProduct;
	@Autowired
	ProductTypeRepository dbProductType;
    @GetMapping
    Page<Map<String,Object>> getProducts(@RequestParam(value="filterParam",required=false)String filterParam,Pageable page, @CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        
    	if(filterParam.equalsIgnoreCase("") || filterParam.equalsIgnoreCase("All")) {
        	return product.getPage(page, user);
        }
    	if(filterParam!=null) {
            return product.getPageFilter(filterParam,page, user);
    	}
		return null;
    }
    @GetMapping("all")
    Page<Map<String,Object>> getAllProducts(Pageable page, @CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
        
  
        	return product.getPage(page, user);
       
    }
    @GetMapping("{id}")
    Map<String,Object> getSingleProduct(@PathVariable("id") long id, @CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{

            return product.getSingle(id, user);

    }
    
    
    @PostMapping
	public Map<String, Object> saveBtProduct(@RequestBody Map<String,Object> request,@CurrentUser LoginUser user,HttpServletResponse response) throws IOException {

		try {
				
			product.saveObject(request, user);
						
		} catch (BadRequestException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,e.getMessage());
		} catch (UnauthoriseException e) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN,e.getMessage());
		}
		return dbProduct.getProductByPartcode(request.get("partcode").toString());
	}
	
	@PutMapping
	public Map<String, Object> updateBtProduct(@RequestBody Map<String,Object> request,@CurrentUser LoginUser user,HttpServletResponse response) throws IOException {

		try {
				
			product.updateObject(request, user);
			return dbProduct.getSingleProducts(Long.valueOf(request.get("id").toString()));
		} catch (BadRequestException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,e.getMessage());
		} catch (UnauthoriseException e) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN,e.getMessage());
		}
		return null;
	}
	
	@PutMapping("status")
	public Map<String, Object> updateBtProductStatus(@RequestParam("productId") long id,@RequestParam("active") boolean active, LoginUser user,HttpServletResponse response) throws IOException {

		try {
				
			product.updateProductStatus(id, active, user);
			return dbProduct.getSingleProducts(id);
		} catch (BadRequestException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,e.getMessage());
		} catch (UnauthoriseException e) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN,e.getMessage());
		}
		return null;
	}
	
	@GetMapping("forlist")
	public List<Map<String, Object>> getProductForListSmall(@RequestParam("searchkey") String searchkey,@CurrentUser LoginUser user,Pageable p) throws BadRequestException, UnauthoriseException{

		return product.searchForList("%"+searchkey+"%",user,p);
	}
	
	
	@GetMapping("advancesearch")
	public List<Map<String,Object>> getAdvanceSearchresult(@RequestParam Map<String,Object> searchRequest,@CurrentUser LoginUser user) throws IOException, ParseException, BadRequestException, UnauthoriseException{
		
		return 	product.advanceSearch(searchRequest,user);
		
	}
	
	@GetMapping("type")
	public List<ProductType> getProductType(){
		return dbProductType.findAll();
	}
}
