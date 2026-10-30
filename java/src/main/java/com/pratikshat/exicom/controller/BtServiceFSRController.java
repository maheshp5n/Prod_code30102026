package com.pratikshat.exicom.controller;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
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
import com.pratikshat.exicom.entity.BtFsr;
import com.pratikshat.exicom.entity.BtPowerPlantMake;
import com.pratikshat.exicom.entity.BtProducts;
import com.pratikshat.exicom.entity.BtServiceAppointment;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.BtAssetMasterUpdateRequest;
import com.pratikshat.exicom.model.request.BtCommissioningAssetMasterUpdateRequest;
import com.pratikshat.exicom.model.request.BtServiceFSRRequest;
import com.pratikshat.exicom.model.request.BtServiceFSRUpdateRequest;
import com.pratikshat.exicom.model.response.BtServiceFSRResponse;
import com.pratikshat.exicom.model.response.ServiceAppointmentResponse;
import com.pratikshat.exicom.repository.ActiveAlarmsRepository;
import com.pratikshat.exicom.repository.BtAssetMasterUpdateRepository;
import com.pratikshat.exicom.repository.BtAssetsRepository;
import com.pratikshat.exicom.repository.BtCoolingMethodRepository;
import com.pratikshat.exicom.repository.BtFaultyAssetParametersRepository;
import com.pratikshat.exicom.repository.BtPendingReasonCustomerBucketRepository;
import com.pratikshat.exicom.repository.BtPowerPlantMakeRepository;
import com.pratikshat.exicom.repository.BtResolutionMethodRepository;
import com.pratikshat.exicom.repository.BtServiceAppointmentRepository;
import com.pratikshat.exicom.repository.BtServiceFSRRepository;
import com.pratikshat.exicom.repository.BtSiteEnvironmentalConditionsRepository;
import com.pratikshat.exicom.repository.BtSiteTypeRepository;
import com.pratikshat.exicom.repository.BtSolutionImpartedRepository;
import com.pratikshat.exicom.services.BtAssetsService;
import com.pratikshat.exicom.services.BtProductService;
import com.pratikshat.exicom.services.BtServiceFSRService;
import com.pratikshat.exicom.services.impl.BtAssetsServiceImpl;
import com.pratikshat.exicom.services.impl.BtProductServiceImpl;

@RestController
@RequestMapping("/bt/api/fsr/ser")
public class BtServiceFSRController {
	
	@Autowired
	BtServiceFSRService serFsr;
	
	@Autowired
	BtServiceAppointmentRepository dbserviceAppointmentRepo;
	
	@Autowired
	BtSiteEnvironmentalConditionsRepository sitEnCon;
	
	@Autowired
	BtCoolingMethodRepository coolMeth;
	
	@Autowired
	BtPowerPlantMakeRepository pwrplmk;
	
	@Autowired
	BtResolutionMethodRepository resMeth;
	
	@Autowired
	BtPendingReasonCustomerBucketRepository penrsn;
	
	@Autowired
	BtSiteTypeRepository siteType;
	
	@Autowired
	BtSolutionImpartedRepository solImp;
	
	@Autowired
	ActiveAlarmsRepository actAlarm;
	
	@Autowired
	BtAssetsService asset;
	
	@Autowired
	BtAssetsRepository dbAssetsRepo;
	
	@Autowired
	BtServiceAppointmentRepository dbAppointmentRepo;
	
	@Autowired
	BtServiceFSRRepository  dbServiceFSRRepo;
	
	@Autowired
	BtAssetMasterUpdateRepository dbAssetMasterUpdateRepo;
	
	@Autowired
	BtFaultyAssetParametersRepository assetParametersRepository;
	
	@Autowired
	BtProductService productService;  
	
	@PostMapping
	private String saveFsr(@RequestBody BtServiceFSRRequest req,
	                       @CurrentUser LoginUser user)
	        throws BadRequestException, UnauthoriseException {

	    if (req.getAsseRequests() == null || req.getAsseRequests().size() > 9) {
	        throw new BadRequestException("Asset master update size should be equal or less than 9");
	    }

	    for (BtAssetMasterUpdateRequest ass : req.getAsseRequests()) {

	        String equipmentType = ass.getEquipmentType();
	        String equipmentSrNo = ass.getEquipmentSrNo();

	        if (equipmentType == null) {
	            throw new BadRequestException("Equipment type is null");
	        }

	     
	        if (equipmentType.contains("CBMS")) {

	           
	            if ("NA".equalsIgnoreCase(equipmentSrNo)) {
	                continue;
	            }

	            if (ass.getBtAsset() == null) {
	                throw new BadRequestException("Asset not found against " + equipmentType);
	            }

	            if (ass.getSrNo() == null) {
	                throw new BadRequestException(equipmentType + " Sr no. is null");
	            }

	            if (equipmentSrNo == null ||
	                !(equipmentSrNo.length() == 8 ||
	                  equipmentSrNo.length() == 9 ||
	                  equipmentSrNo.length() == 15)) {

	                throw new BadRequestException(equipmentType + " length should be 8, 9 & 15");
	            }
	        }

	     
	        else if (equipmentType.startsWith("Battery")) {

	            if (ass.getBtAsset() == null) {
	                throw new BadRequestException("Asset not found against " + equipmentType);
	            }

	            if (ass.getSrNo() == null) {
	                throw new BadRequestException(equipmentType + " Sr no. is null");
	            }

	            if (equipmentSrNo == null || equipmentSrNo.length() != 20) {
	                throw new BadRequestException(equipmentType + " length should be 20");
	            }
	        }

	       
	        if (ass.getSoftwareVersion() == null) {
	            throw new BadRequestException(equipmentType + " Software version is blank");
	        }

	        if ("3".equals(String.valueOf(ass.getCurrentCondition()))) {
	            throw new BadRequestException("Please Select Current Condition");
	        }
	    }

	    return serFsr.saveObject(req, user);
	}
	
	@GetMapping()
	public Page<Map<String, Object>> getPage(@CurrentUser LoginUser user,Pageable page) throws BadRequestException, UnauthoriseException{
		return serFsr.getPage(page, user);
	}
	
	@PutMapping("{id}")
	public String updateBtServiceFsr(@RequestBody BtServiceFSRUpdateRequest req,@PathVariable("id") long id, @CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException {
		
		if (req.getAsseRequests() == null || req.getAsseRequests().size() > 9) {
		    throw new BadRequestException("Asset master update size should be equal or less than 9");
		}

		for (BtAssetMasterUpdateRequest ass : req.getAsseRequests()) {

		    String equipmentType = ass.getEquipmentType();
		    String equipmentSrNo = ass.getEquipmentSrNo();

		    if (equipmentType == null) {
		        throw new BadRequestException("Equipment type is null");
		    }

		   
		    if (equipmentType.contains("CBMS")) {

		       
		        if ("NA".equalsIgnoreCase(equipmentSrNo)) {
		            continue;
		        }

		        if (ass.getBtAsset() == null) {
		            throw new BadRequestException("Asset not found against " + equipmentType);
		        }

		        if (ass.getSrNo() == null) {
		            throw new BadRequestException(equipmentType + " Sr no. is null");
		        }

		        if (equipmentSrNo == null ||
		            !(equipmentSrNo.length() == 8 ||
		              equipmentSrNo.length() == 9 ||
		              equipmentSrNo.length() == 15)) {

		            throw new BadRequestException(equipmentType + " length should be 8, 9 & 15");
		        }
		    }

		    else if (equipmentType.startsWith("Battery")) {

		        if (ass.getBtAsset() == null) {
		            throw new BadRequestException("Asset not found against " + equipmentType);
		        }

		        if (ass.getSrNo() == null) {
		            throw new BadRequestException(equipmentType + " Sr no. is null");
		        }

		        if (equipmentSrNo == null || equipmentSrNo.length() != 20) {
		            throw new BadRequestException(equipmentType + " length should be 20");
		        }
		    }

		 
		    if (ass.getSoftwareVersion() == null) {
		        throw new BadRequestException(equipmentType + " Software version is blank");
		    }

		    if ("3".equals(String.valueOf(ass.getCurrentCondition()))) {
		        throw new BadRequestException("Please Select Current Condition");
		    }
		}
		return serFsr.updateObject(req, user);
	}
	
//	@GetMapping("/{id}")
//	private Map<String, Object> getById(@PathVariable( "id" )int id,@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException{
//		return  serFsr.getSingle(id, user);
//	}
	
	@GetMapping("{id}")
	public BtServiceFSRResponse getByServiceFSRId(@CurrentUser LoginUser user,@PathVariable("id") long id) {
		BtServiceFSRResponse fsrResponse=dbServiceFSRRepo.getByServiceFSRId(id);
		if(fsrResponse!=null) {
			System.out.println(fsrResponse.getServiceFSRId());
			System.out.println(dbAssetMasterUpdateRepo.getByAssetMasterId(fsrResponse.getServiceFSRId()));
			fsrResponse.setAsseResponse(dbAssetMasterUpdateRepo.getByAssetMasterId(fsrResponse.getServiceFSRId()));
			fsrResponse.setFaultyAsset(assetParametersRepository.getAssetsById(fsrResponse.getServiceFSRId()));
		}
		return fsrResponse;
	}
	
	@GetMapping("/advancesearch")
	public List<Map<String, Object>> advanceSearch(@RequestParam Map<String, Object> reqq,@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException{
		return serFsr.advanceSearch(reqq, user);
	}
	
	@GetMapping("forlist")
	public List<Map<String, Object>> forList(@RequestParam("search") String search,@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException{
		return	serFsr.searchForList(search, user);
	}
	
	@GetMapping("forlist/siteType")
	public List<Map<String,Object>> getSiteType(@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException{
		return	siteType.getBtSiteType();
	}
	
	@GetMapping("forlist/siteEnCon")
	public List<Map<String,Object>> getBtSiteEnvironmentalConditions(@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException{
		return	sitEnCon.getBtSiteEnvironmentalConditions();
	}
	
	@GetMapping("forlist/coolMeth")
	public List<Map<String,Object>> getCoolMeth(@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException{
		return	coolMeth.getBtCoolingMethod();
	}
	
	@GetMapping("forlist/pwrplmk")
	public List<Map<String,Object>> getPowerPlant(@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException{
		return	pwrplmk.getBtPowerPlantMake();
	}
	
	@GetMapping("forlist/resMeth")
	public List<Map<String,Object>> getResolutionMethod(@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException{
		return	resMeth.getBtResolutionMethod();
	}
	
	@GetMapping("forlist/penrsn")
	public List<Map<String,Object>> getPendingReasonCustomerBucket(@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException{
		return	penrsn.getBtPendingReasonCustomerBucket();
	}
	
	@GetMapping("forlist/solImp")
	public List<Map<String,Object>> getSolutionImparted(@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException{
		return	solImp.getBtSolutionImparted();
	}
	
	@GetMapping("forlist/actAlarm")
	public List<Map<String,Object>> getActiveAlarms(@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException{
		return	actAlarm.getActiveAlarms();
	}
	
	@GetMapping("forlist/partcode")
	public List<Map<String, Object>> getProductForListSmall(@RequestParam("searchkey") String searchkey,@CurrentUser LoginUser user,Pageable pa) throws BadRequestException, UnauthoriseException{
		
		return  productService.searchPartCode("%"+searchkey+"%",user,pa);
	}
	
	@GetMapping("forlist/asset")
	public List<Map<String, Object>> getProduct(@CurrentUser LoginUser user,@RequestParam("partcode") String productManualId,@RequestParam("equipmentSrNo") String assetManualId ) throws BadRequestException, UnauthoriseException{
		return	asset.searchForPartcodes(productManualId, assetManualId, user);
	}
	
	@GetMapping(value = "/pdf")
	public ResponseEntity<?> createPdf(@RequestParam("serviceFSRId")long serviceFSRId,HttpServletResponse httpServletResponse,@CurrentUser LoginUser user) throws Exception {
		try {
			BtServiceFSRResponse _btFsr=dbServiceFSRRepo.getByServiceFSRId(serviceFSRId);
			
			if(_btFsr ==null) {
				httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,"Field Service Report does not exist");
			}
			
			ByteArrayInputStream bis=dbServiceFSRRepo.generatePdf(user,serviceFSRId);			
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "inline; filename="+_btFsr.getServiceManualId()+".pdf");
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