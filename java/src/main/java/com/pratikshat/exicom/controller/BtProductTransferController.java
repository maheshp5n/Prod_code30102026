package com.pratikshat.exicom.controller;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pratikshat.exicom.annotation.CurrentUser;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.ProductTransferEwaybill;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.response.ResponseProductTransfer;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtProductTransferItemService;
import com.pratikshat.exicom.services.BtProductTransferService;
import com.pratikshat.exicom.services.ProductTransferEwaybillService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("bt/api/producttransfer")
public class BtProductTransferController {

	@Autowired
	BtProductTransferService dbProductTransfer;

	@Autowired
	BtProductTransferItemService dbProductTransferItem;

	@Autowired
	UserProfileRepository dbProfile;
	
	@Autowired
	private ProductTransferEwaybillService productTransferEwaybillService;


	@GetMapping
	public Page<Map<String,Object>> getPageDetails(Pageable page,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
		return dbProductTransfer.getPage(page, user);
	}
	@GetMapping("{id}")
	public Map<String,Object> getSingleDetails(@PathVariable("id")Long productTransferId,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
		return dbProductTransfer.getSingle(productTransferId, user);
	}

	@GetMapping("{id}/items")
	public ResponseEntity<?> getItems(@PathVariable("id")Long productTransferId,@CurrentUser LoginUser user,Pageable page,@RequestParam(value="listview",required = false)String listview) throws BadRequestException,UnauthoriseException{
		if(listview!=null&&listview.equalsIgnoreCase("yes"))
			return ResponseEntity.ok(dbProductTransferItem.getTransferItemDetails(productTransferId, user));

		return ResponseEntity.ok(dbProductTransferItem.getTransferItemDetails(productTransferId, user,page));
	}

	@GetMapping("pendinglocationforitemtransfer")
	public List<Map<String,Object>> getPendingLocationList(@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException {
		return dbProductTransfer.getPendingLocationForProductTransfer(user);
	}

	@GetMapping("pendingitemfortransfer/{locationid}")
	public List<Map<String,Object>> getPendingItemList(@CurrentUser LoginUser user,@PathVariable("locationid")Long locationId) throws BadRequestException,UnauthoriseException{
		return dbProductTransfer.getPendingItemToTransfer(locationId, user);
	}	

	@PostMapping
	public Map<String,Object> doTransferItems(@RequestBody Map<String,Object> request,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
		
		return dbProductTransfer.saveObject(request, user);
	}

	@GetMapping("pendingitemtoreceive/v1")
	public List<Map<String,Object>> getPendingItemToreceive(@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
		
		return dbProductTransfer.getPendingTransferItemToReceive(user);
	}

	@PostMapping("pendingitemtoreceive/v1")
	public String doReceivePendingItems(@RequestBody List<Map<String,Object>> lstItemReceived,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
		 dbProductTransfer.receiveTransferdItem(lstItemReceived, user);
		 return "{}";
	}

	@GetMapping("advancesearch")
	public List<Map<String,Object>> getAdvanceSearch(@RequestParam Map<String,Object> request,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
		return dbProductTransfer.advanceSearch(request, user);
	}

	@GetMapping("{id}/items/advancesearch")
	public List<Map<String,Object>> getItemAdvanceSearch(@PathVariable("id")Long productTransferId,@RequestParam Map<String,Object> request,@CurrentUser LoginUser user) throws BadRequestException,UnauthoriseException{
		return dbProductTransferItem.advanceSearchOfItemByproduct(productTransferId,request,user);
	}
	
	@PostMapping("{ptId}/ewaybill")
	public ResponseEntity<?> uploadEwaybill(
	        @PathVariable("ptId") Long ptId,
	        @RequestParam("ptNumber") String ptNumber,
	        @RequestParam("ewaybillNumber") String ewaybillNumber,
	        @RequestParam("ewaybillDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate ewaybillDate,
	        @RequestParam("file") MultipartFile file,
	        @CurrentUser LoginUser loginUser
	) {
	    try {
	        ProductTransferEwaybill saved = productTransferEwaybillService.saveEwaybill(
	                ptId,
	                ptNumber,
	                ewaybillNumber,
	                ewaybillDate,
	                file,
	                loginUser.getUsername()
	        );

	        return ResponseEntity.ok(saved);

	    } catch (Exception ex) {
	        return ResponseEntity
	                .status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Error: " + ex.getMessage());
	    }
	}
	
	@GetMapping("/getby/{ptnum}")
	public ResponseProductTransfer getByProdId(@PathVariable("ptnum")Long ptNum) {
		return productTransferEwaybillService.getByProdId(ptNum);
		
	}

}
