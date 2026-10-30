package com.pratikshat.exicom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.repository.ProductFamilyRepo;
import com.pratikshat.exicom.repository.SubProductFamily;

@RestController
@RequestMapping("api/productfamily")
public class ProductFamilyController {
	@Autowired
	ProductFamilyRepo dbProduct;
	@Autowired
	SubProductFamily dbSubProuct;
	
	@GetMapping
	public List<com.pratikshat.exicom.model.request.ProductFamily> getProductList(){
		return dbProduct.getByAll();
	}
	@GetMapping("{id}")
	public com.pratikshat.exicom.model.request.ProductFamily getProduct(@PathVariable("id")long id){
		return dbProduct.getByProductFamilyId(id);
	}
	@GetMapping("{id}/subproduct")
	public List<com.pratikshat.exicom.model.request.ProductFamily> getSubProducts(@PathVariable("id")long id){
		return dbProduct.getSubProductByProductFamilyId(id);
	}
	@PostMapping
	public com.pratikshat.exicom.model.request.ProductFamily postProduct(@RequestBody com.pratikshat.exicom.model.request.ProductFamily productFamily){
		com.pratikshat.exicom.entity.ProductFamily _productFamily=new com.pratikshat.exicom.entity.ProductFamily();
		_productFamily.setProductFamilyName(productFamily.getName());
		_productFamily.setSynced(false);
		dbProduct.save(_productFamily);
		productFamily.setId(_productFamily.getProductFamilyId());
		return productFamily;
	}
	@PostMapping("{id}/subproduct")
	public com.pratikshat.exicom.model.request.ProductFamily postSubProduct(@PathVariable("id")long id,@RequestBody com.pratikshat.exicom.model.request.ProductFamily subProductFamily){
		com.pratikshat.exicom.entity.ProductFamilySubProduct subProduct=new com.pratikshat.exicom.entity.ProductFamilySubProduct(id,subProductFamily.getName());
		subProduct.setSynced(false);
		dbSubProuct.save(subProduct);
		subProductFamily.setId(subProduct.getProductFamilySubProductId());
		return subProductFamily;
	}	
}
