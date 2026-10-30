package com.pratikshat.exicom.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
import com.pratikshat.exicom.entity.AssetAmcUpdateLog;
import com.pratikshat.exicom.entity.Assets;
import com.pratikshat.exicom.entity.AssetsStatus;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.AmcUpdateRequest;
import com.pratikshat.exicom.model.request.ChangeCaseTypeRequest;
import com.pratikshat.exicom.model.response.AssetsForList;
import com.pratikshat.exicom.model.response.AssetsResponse;
import com.pratikshat.exicom.repository.AssetsRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtAssetsService;
import com.pratikshat.exicom.utils.AmcUpdateParser;
import com.pratikshat.exicom.utils.ChangeCaseTypeParser;
import com.pratikshat.exicom.utils.ExcelParser;

@RestController
@RequestMapping("api/assets")
public class AssetsController {
	@Autowired
	private AssetsRepository dbAssets;
	@Autowired
	private com.pratikshat.exicom.repository.AssetsRecentVisitRepository dbRecent;
	@Autowired
	private com.pratikshat.exicom.repository.AssetsStatus dbAssetsStatus;
	@Autowired
	private com.pratikshat.exicom.repository.CaseDetailsRepository dbCase;
	@Autowired
	com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	
	@Autowired
	BtAssetsService assetsService;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@GetMapping
	public Page<com.pratikshat.exicom.model.response.AssetsResponse> getAllAssets(Pageable page,@CurrentUser LoginUser user,@RequestParam(value="filtername",required=false)String filtername){

		if(dbProfile.hasObjectAccess(user.getUsername(), "Assets", "viewRights")||dbProfile.hasObjectAccess(user.getUsername(), "Assets", "viewAllRights")) {
			if(filtername!=null&&filtername.equalsIgnoreCase("recent"))
				 return dbAssets.getRecent(user.getUsername(), page);
			return dbAssets.getAllAssets(page);
		}
		return Page.empty();
	}
	
	@GetMapping("astdetails")
	public com.pratikshat.exicom.model.response.AssetsResponse getAssetsBySerialNumberd(@RequestParam("assetsserialnumber") String assetsSerialNumber,@CurrentUser LoginUser user, HttpServletResponse response)throws IOException{
		
		if(dbProfile.hasObjectAccess(user.getUsername(), "Assets", "viewRights")||dbProfile.hasObjectAccess(user.getUsername(), "Assets", "viewAllRights")) {
			try {
				com.pratikshat.exicom.entity.AssetsRecentVisit _recent=dbRecent.findByAssetsSerialIdAndVisitorId(assetsSerialNumber, user.getUsername());
				if(_recent!=null) {
					_recent.setVisitDate(new java.util.Date());
				}
				else {
					_recent=new com.pratikshat.exicom.entity.AssetsRecentVisit(assetsSerialNumber, user.getUsername());
				}
				dbRecent.save(_recent);
				
				return dbAssets.getAssetsBySerialNumber(assetsSerialNumber);
			} catch (Exception ex) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
				return null;
			}
			
		}
		return new com.pratikshat.exicom.model.response.AssetsResponse();
	}
	
	@Transactional
	@GetMapping("{assetsserialnumber}")
	public com.pratikshat.exicom.model.response.AssetsResponse getAssetsBySerialNumber(@PathVariable("assetsserialnumber")String assetsSerialNumber,@CurrentUser LoginUser user, HttpServletResponse response)throws IOException{
		
		if(dbProfile.hasObjectAccess(user.getUsername(), "Assets", "viewRights")||dbProfile.hasObjectAccess(user.getUsername(), "Assets", "viewAllRights")) {
			try {
				com.pratikshat.exicom.entity.AssetsRecentVisit _recent=dbRecent.findByAssetsSerialIdAndVisitorId(assetsSerialNumber, user.getUsername());
				if(_recent!=null) {
					_recent.setVisitDate(new java.util.Date());
				}
				else {
					_recent=new com.pratikshat.exicom.entity.AssetsRecentVisit(assetsSerialNumber, user.getUsername());
				}
				dbRecent.save(_recent);
				
				return dbAssets.getAssetsBySerialNumber(assetsSerialNumber);
			} catch (Exception ex) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
				return null;
			}
			
		}
		return new com.pratikshat.exicom.model.response.AssetsResponse();
	}
	@GetMapping("recent")
	public Page<com.pratikshat.exicom.model.response.AssetsResponse> getRecent(@CurrentUser LoginUser user,Pageable page, HttpServletResponse response)throws IOException{
		if(dbProfile.hasObjectAccess(user.getUsername(), "Assets", "viewRights")||dbProfile.hasObjectAccess(user.getUsername(), "Assets", "viewAllRights")) {
			try {
				return dbAssets.getRecent(user.getUsername(), page);
			}catch(Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
				return null;
			}
			
		}
		return Page.empty();
	}
	@PostMapping
	public com.pratikshat.exicom.model.response.AssetsResponse postAssets(@Valid @RequestBody com.pratikshat.exicom.model.request.AssetsRequest assets,@CurrentUser LoginUser user,HttpServletResponse response) throws Exception{
		if(!dbProfile.hasObjectAccess(user.getUsername(),"Assets", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		assets.setAccountSapNumber(assets.getAccountSapNumber().trim());
		
		try {
			if(dbAssets.countBySerialNumber(assets.getSerialNumber())>0) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Assets Already Exist");
				return null;
			}
			
			dbAssets.saveAssets(assets, user);
			String srlNumber=assets.getSerialNumber();
			if(!srlNumber.endsWith("-"+assets.getProductPardCode()))
				srlNumber=assets.getSerialNumber()+"-"+assets.getProductPardCode();
			 return dbAssets.getAssetsBySerialNumber(srlNumber);
		}catch(BadRequestException ex) {
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
	public com.pratikshat.exicom.model.response.AssetsResponse editAssets(@Valid @RequestBody com.pratikshat.exicom.model.request.AssetsRequest assets,@RequestParam("assetsserialnumber")String assetsSerialNumber,@CurrentUser LoginUser user,HttpServletResponse response) throws Exception{

		if(!dbProfile.hasObjectAccess(user.getUsername(), "Assets", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			if(dbAssets.countBySerialNumber(assetsSerialNumber)==0) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Assets does not exist");
				return null;
			}

			if(!assetsSerialNumber.equalsIgnoreCase(assets.getSerialNumber())) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Assets does not exist");
				return null;
			}
			Map<String,String> roleGroup=dbProfile.getAccountAndRoleGroup(user);
			boolean isAdmin=roleGroup.get("roleGroup").equalsIgnoreCase("ADMIN");
			boolean customerCare=roleGroup.get("roleGroup").equalsIgnoreCase("CUSTOMERCARE");
			boolean commercial=false;
			if(roleGroup.get("roleGroup").equalsIgnoreCase("COMMERCIAL") || roleGroup.get("roleGroup").equalsIgnoreCase("Commercial Executive") ) {
				commercial=true;
			}else {
				commercial=false;
			}
			
			if(assets.getAssetsStatusId()==2&&assets.getSiteId()==0) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Site details not found");
				return null;
			}
				
			
			com.pratikshat.exicom.entity.Assets _assets=dbAssets.findBySerialNumber(assetsSerialNumber);
			_assets.setAccount(new com.pratikshat.exicom.entity.Accounts(assets.getAccountSapNumber()));
			_assets.setModifyedBy(new com.pratikshat.exicom.entity.EmployeeDetails(user.getUsername()));
			_assets.setModifyedDate(new java.util.Date());
			if(assets.getPlantCode()==null|| assets.getPlantCode().equalsIgnoreCase("")||assets.getPlantCode().equalsIgnoreCase("0")) {
				
					_assets.setPlant(null);
			}
			else {
				
					_assets.setPlant(new com.pratikshat.exicom.entity.PlantDetails(assets.getPlantCode()));
			}
			if(assets.getPrimaryAccountSapNumber()==null||assets.getPrimaryAccountSapNumber().equalsIgnoreCase("")||assets.getPrimaryAccountSapNumber().equalsIgnoreCase("0"))
				_assets.setPrimaryAccount(null);
			else
				_assets.setPrimaryAccount(new com.pratikshat.exicom.entity.Accounts(assets.getPrimaryAccountSapNumber()));
			//_assets.setProduct(new com.pratikshat.exicom.entity.Products(assets.getProductPardCode()));
			_assets.setRemarks(assets.getRemarks());
			if(assets.getSiteId()==0||assets.getAssetsStatusId()==1||assets.getAssetsStatusId()==7)
				_assets.setSite(null);
			else
				_assets.setSite(new com.pratikshat.exicom.entity.SiteDetails(assets.getSiteId()));
			_assets.setStatus(new AssetsStatus(assets.getAssetsStatusId()));
			_assets.setSynced(false);
			
			_assets.setPurchaseOrderNumber(assets.getPurchaseOrderNumber());
			if(assets.getPurchaseOrderDate()!=null) {
				String purchaseOrderDateString = sdf.format(assets.getPurchaseOrderDate());
				Date purchaseOrderDate = sdf.parse(purchaseOrderDateString);
				_assets.setPurchaseOrderDate(purchaseOrderDate);
			}
			_assets.setSapDeliveryDocumentNumber(assets.getSapDeliveryDocumentNumber());
			_assets.setSapMaterialDocumentNumber(assets.getSapMaterialDocumentNumber());
			
			
			if(assets.getPurchaseDate()!=null) {
				String purchaseDateString = sdf.format(assets.getPurchaseDate());
				Date purchaseDate = sdf.parse(purchaseDateString);
				_assets.setPurchaseDate(purchaseDate);
			}
			_assets.setWarrantyFromDateOfDeliveryInMonth(assets.getWarrantyFromDateOfDeliveryInMonth());
			
			if(assets.getPurchaseDate()!=null && assets.getInstallDate()!=null) {
				String purchaseDateString = sdf.format(assets.getPurchaseDate());
				Date purchaseDate = sdf.parse(purchaseDateString);
				
				String installDateString = sdf.format(assets.getInstallDate());
				Date installDate = sdf.parse(installDateString);
				
				if(installDate.getTime()-purchaseDate.getTime()<0) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Installation date must be greater than purchase date");
					return null;
				}
				
				_assets.setInstallDate(installDate);
				
			}
			_assets.setWarrantyFromDateOfInstallationInMonth(assets.getWarrantyFromDateOfInstallationInMonth());
			
			Date xpBypDate=null;
			Date xpByInstDate=null;
			if(assets.getPurchaseDate()!=null) {
				Calendar cl=Calendar.getInstance();
				cl.setTime(_assets.getPurchaseDate());
				cl.add(Calendar.MONTH,assets.getWarrantyFromDateOfDeliveryInMonth());
				cl.add(Calendar.HOUR_OF_DAY,-24);
				xpBypDate=cl.getTime();
			}
			
			if(assets.getInstallDate()!=null) {
				Calendar cl=Calendar.getInstance();
				cl.setTime(_assets.getInstallDate());
				cl.add(Calendar.MONTH,assets.getWarrantyFromDateOfInstallationInMonth());
				cl.add(Calendar.HOUR_OF_DAY,-24);
				xpByInstDate=cl.getTime();
			}
			
			if(xpByInstDate==null && xpBypDate!=null) {
				_assets.setWarrantyExpiryDate(xpBypDate);
				_assets.setDateOfDeliveryExpiryDate(xpBypDate);
			}else if(xpBypDate==null && xpByInstDate!=null) {
				_assets.setInstallExpiryDate(xpByInstDate);
				_assets.setWarrantyExpiryDate(xpByInstDate);
			} else if(xpBypDate!=null && xpByInstDate!=null) {
				_assets.setInstallExpiryDate(xpByInstDate);
				_assets.setDateOfDeliveryExpiryDate(xpBypDate);
				if(xpBypDate.getTime()>xpByInstDate.getTime()) {
					_assets.setWarrantyExpiryDate(xpByInstDate);
				}else {
					_assets.setWarrantyExpiryDate(xpBypDate);
				}
			}
			if(commercial)
				_assets.setAmcPurchaseOrderNumber(assets.getAmcPurchaseOrderNumber());
			if(assets.getAmcPurchaseOrderDate()!=null) {
				String amcPurchaseOrderDateString = sdf.format(assets.getAmcPurchaseOrderDate());
				if(commercial)
					_assets.setAmcPurchaseOrderDate(amcPurchaseOrderDateString);
			}
			
			if(assets.getAmcPurchaseOrderDate()!=null && assets.getAmcStartDate()!=null) {
				String amcPurchaseOrderDateString = sdf.format(assets.getAmcPurchaseOrderDate());
				Date amcPurchaseOrderDate = sdf.parse(amcPurchaseOrderDateString);
				
				String amcStartDateString = sdf.format(assets.getAmcStartDate());
				Date amcStartDate = sdf.parse(amcStartDateString);
				
//				if(amcStartDate.getTime()-amcPurchaseOrderDate.getTime()<0) {
//					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "AMC start date must be greater than AMC purchase date");
//					return null;
//				}
				if(commercial)
					_assets.setAmcStartDate(amcStartDate);
			}
			if(commercial)
				_assets.setAmcPeriod(assets.getAmcPeriod());
			
			if(assets.getAmcStartDate()!=null) {
				Calendar cl=Calendar.getInstance();
				cl.setTime(_assets.getAmcStartDate());
				cl.add(Calendar.MONTH,assets.getAmcPeriod());
				cl.add(Calendar.HOUR_OF_DAY, -24);
				if(commercial)
					_assets.setAmcExpiryDate(cl.getTime());
			}
			_assets.setModifyedBy(new EmployeeDetails(user.getUsername()));
			_assets.setModifyedDate(new Date());
			dbAssets.save(_assets);
			return dbAssets.getAssetsBySerialNumber(_assets.getSerialNumber());
		} catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
		
		
	}
	@GetMapping("status")
	public java.util.List<com.pratikshat.exicom.entity.AssetsStatus> getAssetsStatus(){
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(7);
		return dbAssetsStatus.getStatus(list);
	}
	@GetMapping("forlist")
	public java.util.List<com.pratikshat.exicom.model.response.AssetsForList> getList(@RequestParam(value="status",required=false)Integer type){
		if(type!=null&&type!=0)
			return dbAssets.getAssetsForList(new AssetsStatus(type));
		return dbAssets.getAssetsForList();
	}
	
	
	@GetMapping("forlist/small")
	public Page<AssetsForList> getSmallForList(@RequestParam("search")String search,Pageable page){
		
		return dbAssets.getAssetsForList("%"+search+"%", page);
	}
	
	@GetMapping("forlist/forinstallation")
	public java.util.List<com.pratikshat.exicom.model.response.AssetsForList> getListForInstallation(){
		
		return dbAssets.getAssetsForListForInstallation();
	}
	@GetMapping("forlist/forinstallation/small")
	public Page<com.pratikshat.exicom.model.response.AssetsForList> getListForInstallation(@RequestParam("searchkey")String searchKey,Pageable page){
		
		return dbAssets.getAssetsForListForInstallation("%"+searchKey+"%",page);
	}
	
	@GetMapping("case/details")
	public Page<com.pratikshat.exicom.model.response.CaseResponse> getCaseByAssetsSerialNumberd(@RequestParam("assetsserialnumber")String assetsserialnumber,Pageable page){
		return dbCase.getByAssetsSerialNumber(assetsserialnumber, page);
	}
	
	@GetMapping("case/{assetsserialnumber}")
	public Page<com.pratikshat.exicom.model.response.CaseResponse> getCaseByAssetsSerialNumber(@PathVariable("assetsserialnumber")String assetsserialnumber,Pageable page){
		return dbCase.getByAssetsSerialNumber(assetsserialnumber, page);
	}
	
	
	@GetMapping("case")
	public Page<com.pratikshat.exicom.model.response.CaseResponse> getCaseByAssetsSerialNumberNew(@RequestParam("assetsserialnumber")String assetsserialnumber,Pageable page){
		return dbCase.getByAssetsSerialNumber(assetsserialnumber, page);
	}
	
	@PostMapping("assetsserialnumber/sites")
	public com.pratikshat.exicom.model.response.AssetsResponse getAssetsd(@RequestParam("assetsserialnumber")String assetsserialnumber,@RequestBody com.pratikshat.exicom.model.request.SiteDetailsRequest siteRequest,@CurrentUser LoginUser user,HttpServletResponse response) throws Exception{
		if(!dbProfile.hasObjectAccess(user.getUsername(), "Assets", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		try {
			dbAssets.saveSiteOnAssets(assetsserialnumber, siteRequest, user);
			return dbAssets.getAssetsBySerialNumber(assetsserialnumber);
		}catch(BadRequestException ex) {
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
	@PostMapping("{assetsserialnumber}/sites")
	public com.pratikshat.exicom.model.response.AssetsResponse getAssets(@PathVariable("assetsserialnumber")String assetsserialnumber,@RequestBody com.pratikshat.exicom.model.request.SiteDetailsRequest siteRequest,@CurrentUser LoginUser user,HttpServletResponse response) throws Exception{
		if(!dbProfile.hasObjectAccess(user.getUsername(), "Assets", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		try {
			dbAssets.saveSiteOnAssets(assetsserialnumber, siteRequest, user);
			return dbAssets.getAssetsBySerialNumber(assetsserialnumber);
		}catch(BadRequestException ex) {
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
	@GetMapping("advancesearch")
	public List<AssetsResponse> getAdvanceSearchresult(@RequestParam Map<String,Object> request,@CurrentUser LoginUser user,HttpServletResponse  response) throws IOException{
		if(dbProfile.hasObjectAccess(user.getUsername(), Assets.class, UserProfileRepository.VIEW)) {
			return dbAssets.advanceSearch(request, user, null);
		}
		response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
		return null;
		
	}
	
	
	// Template for AMC Update
	@GetMapping("/templateAMCUpdate")
	public ResponseEntity<ByteArrayResource> getTemplateAMC(HttpServletResponse httpResponse)throws IOException {
		HttpHeaders header = new HttpHeaders();
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("AMCUpdate");
		Object[][] bookData = { { "Asset_Serial_No." , "Asset_Part_Code" ,"AMC_PO_No", "AMC_PO_Date (YYYY-MM-DD)", 
			"AMC_Start_Date (YYYY-MM-DD)", "AMC_Period (months)" },};

		int rowCount = 0;
		
		CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setColor(Font.COLOR_RED);
        style.setFont(font);

		for (Object[] aBook : bookData) {
			Row row = sheet.createRow(rowCount);

			int columnCount = 0;

			for (Object field : aBook) {
				Cell cell = row.createCell(columnCount++);
				sheet.autoSizeColumn(columnCount);
				if (field instanceof String) {
					cell.setCellValue((String) field);
					cell.setCellStyle(style);
				} else if (field instanceof Integer) {
					cell.setCellValue((Integer) field);
					cell.setCellStyle(style);
				}
			}

		}

		header.setContentType(new MediaType("application", "force-download"));
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "AMCUpdateTemplate.xlsx");
		httpResponse.setContentType("application/xlsx");
		ByteArrayOutputStream bytest = new ByteArrayOutputStream();
		workbook.write(bytest);
		FileOutputStream fileOut = new FileOutputStream("AMCUpdateTemplate.xlsx");
		return new ResponseEntity<>(new ByteArrayResource(bytest.toByteArray()), header, HttpStatus.OK);
	}

	@PutMapping("amcUpdate")
	public ResponseEntity<?> amcBulkUdpated(@RequestParam(value = "file") MultipartFile file, @CurrentUser LoginUser user,
			HttpServletResponse response) throws IOException {
			if(!dbProfile.hasObjectAccess(user.getUsername(), AssetAmcUpdateLog.class, UserProfileRepository.CREATE)) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN,"You are not authorise");
				return null;
			}
		List<Map<String, Object>> outData = new ArrayList<Map<String, Object>>();
		ByteArrayOutputStream st = null;
		try {
			List<AmcUpdateRequest> requests = new AmcUpdateParser().parseIn(file);
			for (AmcUpdateRequest re : requests) {
				Map<String, Object> d = new HashMap<String, Object>();
				d.put("Asset_Serial_No.", re.getAssetSerialNo());
				d.put("Asset_Part_Code", re.getAssetPartCode());
				d.put("AMC_PO_No", re.getAmcPoNo());
				d.put("AMC_PO_Date (YYYY-MM-DD)", re.getAmcPoDate());
				d.put("AMC_Start_Date (YYYY-MM-DD)", re.getAmcStartDate());
				d.put("AMC_Period (months)", re.getAmcPeriod());
				try {
					dbAssets.updateAmcBulk(re, user);
					d.put("Status", "Success");
				} catch (Exception ex) {
					d.put("Status", "Error : " + ex.getMessage());
				}
				outData.add(d);
			}

		} catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		}
		try {
			st = new ExcelParser().genrateFile(outData);
		} catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}

		response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=amcUpdateResult.xlsx");
		response.setContentType("application/vnd.ms-excel");
		response.getOutputStream().write(st.toByteArray());
		response.getOutputStream().flush();
		response.getOutputStream().close();
		return null;
	}
	
}
