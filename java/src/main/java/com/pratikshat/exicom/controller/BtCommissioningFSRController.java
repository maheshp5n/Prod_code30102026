package com.pratikshat.exicom.controller;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import com.pratikshat.exicom.model.request.BtCommissioningAssetMasterUpdateRequest;
import com.pratikshat.exicom.model.request.BtCommissioningFSRRequest;
import com.pratikshat.exicom.model.request.BtCommissioningFSRUpdateRequest;
import com.pratikshat.exicom.model.response.BtCommissioningFSRResponse;
import com.pratikshat.exicom.model.response.BtServiceFSRResponse;
import com.pratikshat.exicom.repository.BtCommissioningAssetMasterUpdateRepository;
import com.pratikshat.exicom.repository.BtCommissioningFSRRepository;
import com.pratikshat.exicom.repository.BtCommissioningPendingReasonCustomerBucketRepository;
import com.pratikshat.exicom.services.BtCommissioningFSRService;

@RestController
@RequestMapping("/bt/api/fsr/com")
public class BtCommissioningFSRController {

	@Autowired
	BtCommissioningFSRRepository dbCommissioningFSRRepo;

	@Autowired
	BtCommissioningFSRService btCommFSRService;
	
	@Autowired
	BtCommissioningPendingReasonCustomerBucketRepository penrsn;
	
	@Autowired
	BtCommissioningAssetMasterUpdateRepository dbCommAssetMasterUpdateRepo;

	@PostMapping
	public BtCommissioningFSRResponse saveCommFsr(@RequestBody BtCommissioningFSRRequest req, @CurrentUser LoginUser user)
			throws BadRequestException, UnauthoriseException {
		
		if(req.getBtCommissioningAssetMasterUpdateRequests().size()>9)
			throw new BadRequestException("Asset master update size should be equal or less than 9 ");
		for (BtCommissioningAssetMasterUpdateRequest ass : req.getBtCommissioningAssetMasterUpdateRequests()) {

		    boolean isCbmsNA = ass.getEquipmentType().contains("CBMS") 
		                       && "NA".equalsIgnoreCase(ass.getEquipmentSrNo());

		    // NA CBMS à¤•à¥‡ à¤²à¤¿à¤ à¤¸à¤¿à¤°à¥à¤« srNo check à¤•à¤°à¥‹, à¤¬à¤¾à¤•à¥€ à¤¸à¤¬ skip
		    if (ass.getSrNo() == null)
		        throw new BadRequestException(ass.getEquipmentType() + " Sr no. is null");

		    if (!isCbmsNA) {
		        if (ass.getBtAsset() == null)
		            throw new BadRequestException("Asset not found against " + ass.getEquipmentType());

		        if (ass.getEquipmentType().contains("CBMS")) {
		            if (ass.getEquipmentSrNo().length() == 8 || ass.getEquipmentSrNo().length() == 9
		                    || ass.getEquipmentSrNo().length() == 15) {
		                // valid
		            } else {
		                throw new BadRequestException(ass.getEquipmentType() + " length should be 8, 9 & 15");
		            }
		        }
		        if (ass.getEquipmentType().startsWith("BATTERY")) {
		            if (ass.getEquipmentSrNo().length() != 20)
		                throw new BadRequestException(ass.getEquipmentType() + " length should be 20");
		        }
		        if (ass.getSoftwareVersion() == null)
		            throw new BadRequestException(ass.getEquipmentType() + " Software version is blank");
		        if (ass.getSoc() == null)
		            throw new BadRequestException(ass.getEquipmentType() + " SOC is blank");
		        if (ass.getSoh() == null)
		            throw new BadRequestException(ass.getEquipmentType() + " SOH is blank");
		        if (ass.getCurrentCondition() == 3)
		            throw new BadRequestException("Please Select Current Condition");
		    }
		}
		return btCommFSRService.saveObject(req, user);
	}

	@GetMapping()
	public Page<Map<String, Object>> getPage(@CurrentUser LoginUser user, Pageable page)
			throws BadRequestException, UnauthoriseException {
		return btCommFSRService.getPage(page, user);
	}

	@PutMapping("{id}")
	public Map<String, Object> updateBtCommFsr(@RequestBody BtCommissioningFSRUpdateRequest req, @PathVariable("id") long id,
			@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException {
		
		if(req.getBtCommissioningAssetMasterUpdateRequests().size()>9)
			throw new BadRequestException("Asset master update size should be equal or less than 9 ");
		for (BtCommissioningAssetMasterUpdateRequest ass : req.getBtCommissioningAssetMasterUpdateRequests()) {

		    boolean isCbmsNA = ass.getEquipmentType().contains("CBMS") 
		                       && "NA".equalsIgnoreCase(ass.getEquipmentSrNo());

		    
		    if (ass.getSrNo() == null)
		        throw new BadRequestException(ass.getEquipmentType() + " Sr no. is null");

		    if (!isCbmsNA) {
		        if (ass.getBtAsset() == null)
		            throw new BadRequestException("Asset not found against " + ass.getEquipmentType());

		        if (ass.getEquipmentType().contains("CBMS")) {
		            if (ass.getEquipmentSrNo().length() == 8 || ass.getEquipmentSrNo().length() == 9
		                    || ass.getEquipmentSrNo().length() == 15) {
		                // valid
		            } else {
		                throw new BadRequestException(ass.getEquipmentType() + " length should be 8, 9 & 15");
		            }
		        }
		        if (ass.getEquipmentType().startsWith("BATTERY")) {
		            if (ass.getEquipmentSrNo().length() != 20)
		                throw new BadRequestException(ass.getEquipmentType() + " length should be 20");
		        }
		        if (ass.getSoftwareVersion() == null)
		            throw new BadRequestException(ass.getEquipmentType() + " Software version is blank");
		        if (ass.getSoc() == null)
		            throw new BadRequestException(ass.getEquipmentType() + " SOC is blank");
		        if (ass.getSoh() == null)
		            throw new BadRequestException(ass.getEquipmentType() + " SOH is blank");
		        if (ass.getCurrentCondition() == 3)
		            throw new BadRequestException("Please Select Current Condition");
		    }
		}
		return btCommFSRService.updateObject(req, user);
	}

//	@GetMapping("/{id}")
//	private Map<String, Object> getById(@PathVariable( "id" )int id,@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException{
//		return  serFsr.getSingle(id, user);
//	}

	@GetMapping("{id}")
	public BtCommissioningFSRResponse getByCommFSRId(@CurrentUser LoginUser user,@PathVariable("id") long id) {
		BtCommissioningFSRResponse cfsrResponse=dbCommissioningFSRRepo.getByCommissioningFSRId(id);
		if(cfsrResponse!=null) {
			cfsrResponse.setAsseResponse(dbCommAssetMasterUpdateRepo.getByComAssetMasterId(cfsrResponse.getCommFSRId()));
		}
		return cfsrResponse;
	}
	
	@GetMapping("/advancesearch")
	public List<Map<String, Object>> advanceSearch(@RequestParam Map<String, Object> reqq, @CurrentUser LoginUser user)
			throws BadRequestException, UnauthoriseException {
		return btCommFSRService.advanceSearch(reqq, user);
	}

	@GetMapping("forlist")
	public List<Map<String, Object>> forList(@RequestParam("search") String search, @CurrentUser LoginUser user)
			throws BadRequestException, UnauthoriseException {
		return btCommFSRService.searchForList(search, user);
	}
	
	@GetMapping("forlist/penrsn")
	public List<Map<String,Object>> getBtCommissioningPendingReasonCustomerBucket(@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException{
		return	penrsn.getBtCommissioningPendingReasonCustomerBucket();
	}

	@GetMapping(value = "/pdf")
	public ResponseEntity<?> createPdf(@RequestParam("commFSRId")long commFSRId,HttpServletResponse httpServletResponse,@CurrentUser LoginUser user) throws Exception {
		try {
			BtCommissioningFSRResponse _cbtFsr=dbCommissioningFSRRepo.getByCommissioningFSRId(commFSRId);
			
			if(_cbtFsr ==null) {
				httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,"Field Service Report does not exist");
			}
			
			ByteArrayInputStream bis=dbCommissioningFSRRepo.generatePdf(user,commFSRId);			
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "inline; filename="+_cbtFsr.getManualId()+".pdf");
			return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(bis));
		} catch(BadRequestException ex) {
			ex.printStackTrace();
			httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		}catch(UnauthoriseException ex) {
			ex.printStackTrace();
			httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		}
	}

}
