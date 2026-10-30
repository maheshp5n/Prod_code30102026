package com.pratikshat.exicom.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.protobuf.DescriptorProtos.SourceCodeInfo.Location;
import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LProductRequest;
import com.pratikshat.exicom.entity.LocationProductItem;
import com.pratikshat.exicom.entity.LocationProductLineItems;
import com.pratikshat.exicom.entity.LocationRecentVisit;
import com.pratikshat.exicom.entity.LocationType;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.LPItemRequest;
import com.pratikshat.exicom.model.request.LPRequest;
import com.pratikshat.exicom.model.request.LocationRequest;
import com.pratikshat.exicom.model.response.LocationProductRequestResponse;
import com.pratikshat.exicom.model.response.LocationResponse;
import com.pratikshat.exicom.repository.LocationRecentVisitRepository;
import com.pratikshat.exicom.repository.LocationRepository;
import com.pratikshat.exicom.repository.LocationTypeRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.LocationNativeSerivce;

@RestController
@RequestMapping("api/location")
public class LocationController {
	@Autowired
	private LocationRepository dbLocation;

	@Autowired
	private LocationTypeRepository dbLocationType;

	@Autowired
	private LocationRecentVisitRepository dbLocationRecentVisit;
	@Autowired
	private com.pratikshat.exicom.repository.LocationProductRequestRepository dbProductRequest;
	@Autowired
	com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	
	@Autowired
	LocationNativeSerivce dbNativeLocation;
	
	int businessDomainId=2;

	@GetMapping
	public Page<com.pratikshat.exicom.model.response.LocationResponse> getLocationAll(Pageable page,
			@RequestParam(value = "filter", required = false) String filter, @CurrentUser LoginUser user,@RequestParam(value="filtername",required=false)String filtername) {
		if(filtername!=null) {
			filter=filtername;
		}
		if(dbProfile.hasObjectAccess(user.getUsername(),"Location", "viewAllRights")) {
			if (filter != null && filter.equalsIgnoreCase("mylocation")) {
				return dbLocation.getMyLocation(user.getUsername(), page, businessDomainId);
			}
			if(filter!=null&&filter.equalsIgnoreCase("related")) {
				return dbLocation.getRecentVisit(user.getUsername(), page, businessDomainId);
			}
		
			return dbLocation.getByAll(page, businessDomainId);
		}
		if(dbProfile.hasObjectAccess(user.getUsername(), "Location", "viewRights")) {
			if (filter != null && filter.equalsIgnoreCase("mylocation")) {
				return dbLocation.getMyLocation(user.getUsername(), page, businessDomainId);
			}
			if(filter!=null&&filter.equalsIgnoreCase("related")) {
				return dbLocation.getRecentVisit(user.getUsername(), page, businessDomainId);
			}
		
//			List<String> u=new ArrayList<String>();
//			u=dbProfile.getListOfUserAndChildByUser(user.getUsername());
//			List<com.pratikshat.exicom.entity.EmployeeDetails> emps=new ArrayList<com.pratikshat.exicom.entity.EmployeeDetails>();
//			for(String us:u) {
//				emps.add(new com.pratikshat.exicom.entity.EmployeeDetails(us));
//			}
			
			List<Long> childs=dbProfile.getLocationAndChildLocation(user.getUsername());
			
			return dbLocation.getByAllWithChilds(childs, page);
		}
		return Page.empty();
	}

	@GetMapping("forlist")
	public java.util.List<com.pratikshat.exicom.model.response.LocationForList> getForList(@RequestParam(value="type",required=false)String type) {
		if(type!=null) {
			return dbLocation.getLocationForList(type, businessDomainId);
		}
		return dbLocation.getLocationForList(businessDomainId);
	}

	@GetMapping("{locationid}")
	public com.pratikshat.exicom.model.response.LocationResponse getByLocationId(
			@PathVariable("locationid") long locationId, @CurrentUser LoginUser user) {
		LocationResponse location = dbLocation.getByLocationId(locationId);
		try {
			LocationRecentVisit _recent = dbLocationRecentVisit.findByLocationIdAndVisitorId(locationId,
					user.getUsername());
			if (_recent == null)
				_recent = new LocationRecentVisit(locationId, user.getUsername());
			else
				_recent.setVisitDate(new java.util.Date());
			dbLocationRecentVisit.save(_recent);
			dbLocationRecentVisit.flush();
		} catch (Exception e) {
		}

		return location;
	}

	@PostMapping
	public LocationResponse postLocation(
			@RequestBody com.pratikshat.exicom.model.request.LocationRequest locationRequest,
			@CurrentUser LoginUser user, HttpServletResponse response) throws IOException {
		
		if(!dbProfile.hasObjectAccess(user.getUsername(), "Location", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			if (dbLocation.countByLocationId(locationRequest.getLocationId()) > 0) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Warehouse already exists");
				return null;
			}
			
			LocationRequest request = dbLocation.saveLocation(locationRequest, user, businessDomainId);
			return dbLocation.getByLocationId(request.getLocationId());
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

	@PutMapping("{locationid}")
	public LocationResponse putMapping(@RequestBody com.pratikshat.exicom.model.request.LocationRequest locationRequest,
			@CurrentUser LoginUser user, @PathVariable("locationid") long locationId, HttpServletResponse response)
			throws IOException {
		if(!dbProfile.hasObjectAccess(user.getUsername(), "Location", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			dbLocation.editLocation(locationRequest, user, businessDomainId);
			return dbLocation.getByLocationId(locationId);
		} catch (Exception ex) {
			ex.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
	}

	@GetMapping("{locationid}/product/forlist")
	public java.util.List<com.pratikshat.exicom.model.response.LocationProductItemForList> getProducctLocationForlist(
			@PathVariable("locationid") long locationid) {
		return dbLocation.getLocationProductItemByLocationId(locationid);
	}

	@GetMapping("{locationid}/rootlocation")
	public com.pratikshat.exicom.model.response.LocationResponse getRootLocation(
			@PathVariable("locationid") long locationId) {
		com.pratikshat.exicom.entity.Location pLocation = dbLocation.getParentLocation(locationId);
		while (pLocation != null) {
			com.pratikshat.exicom.entity.Location _pLocation = dbLocation.getParentLocation(pLocation.getLocationId());
			if (_pLocation == null)
				return dbLocation.getByLocationId(pLocation.getLocationId());
			else
				pLocation = _pLocation;
		}
		return dbLocation.getByLocationId(locationId);
	}

	@GetMapping("type")
	public List<LocationType> getLocationTypeList() {
		return dbLocationType.findAll();
	}

	@GetMapping("recentvisit")
	public Page<com.pratikshat.exicom.model.response.LocationResponse> getReceintVisist(@CurrentUser LoginUser user,
			Pageable page) {
		return dbLocation.getRecentVisit(user.getUsername(), page, businessDomainId);
	}

	@PostMapping("{locationid}/productrequest")
	public com.pratikshat.exicom.model.request.LPRequest requestProduct(
			@PathVariable("locationid") long destinationLocationId,
			@RequestBody com.pratikshat.exicom.model.request.LPRequest lperRequest, @CurrentUser LoginUser user,
			HttpServletResponse response) throws java.io.IOException {
		
		
		if(!dbProfile.hasObjectAccess(user.getUsername(), "LProductRequest", "createRights")) {
			
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}

		try {
			if (lperRequest.getDestinationLocationId() != destinationLocationId) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Destination Warehouse missmatch");
				return null;
			}
//			com.pratikshat.exicom.entity.EmployeeDetails emp = dbLocation.getOwnerOfLocation(destinationLocationId);
//			if (emp == null || !emp.getEmailId().equalsIgnoreCase(user.getUsername())) {
//				response.sendError(HttpServletResponse.SC_FORBIDDEN,
//						"You are not authorised to create or change product request to this warehouse");
//				return null;
//			}
			
			return dbLocation.saveProductRequest(lperRequest, user);
		} catch (BadRequestException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
			return null;
		} catch (UnauthoriseException e) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
			return null;
		}catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
		
	}

	@PostMapping("{locationid}/returnorder")
	public com.pratikshat.exicom.model.request.LPReturnOrderRequest returnOrderRequest(
			@PathVariable("locationid") long locationid,
			@RequestBody com.pratikshat.exicom.model.request.LPReturnOrderRequest returnOrder,
			@CurrentUser LoginUser user, HttpServletResponse response) throws java.io.IOException {
		if(!dbProfile.hasObjectAccess(user.getUsername(),"LProductReturnOrder", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		try {
			if (returnOrder.getSourceLocationId() != locationid) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Source Warehouse mismatch");
				return null;
			}
//			com.pratikshat.exicom.entity.EmployeeDetails emp = dbLocation.getOwnerOfLocation(locationid);
//			if (emp == null || !emp.getEmailId().equalsIgnoreCase(user.getUsername())) {
//				response.sendError(HttpServletResponse.SC_FORBIDDEN,
//						"You are not authorised to create product return order for this location");
//				return null;
//			}
			
			return dbLocation.saveReturnOrder(returnOrder, user);
		} catch (com.pratikshat.exicom.exception.BadRequestException bd) {
			bd.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, bd.getMessage());
			return null;
		} catch (com.pratikshat.exicom.exception.UnauthoriseException ex) {
			ex.printStackTrace();
			response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		}catch (Exception ex) {
			ex.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
		
	}

	@PutMapping("changeowner")
	public ResponseEntity<?> changeOwner(
			@RequestBody com.pratikshat.exicom.model.request.ChangeOwnerRequest locationIds,
			@CurrentUser LoginUser user) {
		if (locationIds.getIds() == null || locationIds.getIds().size() == 0
				|| locationIds.getIds().size() > 1) {
			return ResponseEntity.badRequest().body("Please select single location");
		}
		java.util.Iterator ite = user.getAuthorities().iterator();
		while (ite.hasNext()) {
			com.pratikshat.exicom.entity.LoginUserRoles roles = (com.pratikshat.exicom.entity.LoginUserRoles) ite
					.next();
			if (roles.getAuthority().equalsIgnoreCase("ADMIN")) {
				dbLocation.changeOwner(locationIds.getIds(), locationIds.getUserEmailId(), user, businessDomainId);
				return ResponseEntity.ok("{\"ok\":\"ok\"}");
			}
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorised");
	}

	@PutMapping("changeowner/{locationids}")
	public ResponseEntity<?> changeOwner(@PathVariable("locationids") long locationIds,
			@RequestBody com.pratikshat.exicom.model.request.ChangeOwnerRequest locationOwnerIds,
			@CurrentUser LoginUser user) {
		if (locationOwnerIds.getIds() == null || locationOwnerIds.getIds().size() == 0
				|| locationOwnerIds.getIds().size() > 1) {
			return ResponseEntity.badRequest().body("Bad request");
		}
		java.util.Iterator ite = user.getAuthorities().iterator();
		while (ite.hasNext()) {
			com.pratikshat.exicom.entity.LoginUserRoles roles = (com.pratikshat.exicom.entity.LoginUserRoles) ite
					.next();
			if (roles.getAuthority().equalsIgnoreCase("ADMIN")) {
				dbLocation.changeOwner(locationOwnerIds.getIds(), locationOwnerIds.getUserEmailId(), user, businessDomainId);
				return ResponseEntity.ok("{\"ok\":\"ok\"}");
			}
		}

		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorised");
	}

	@GetMapping("{locationid}/childlocation")
	public Page<com.pratikshat.exicom.model.response.LocationResponse> getChildLocation(
			@PathVariable("locationid") long locationid, Pageable page) {
		return dbLocation.getChildLocation(locationid,page);
	}

	@GetMapping("{locationid}/productitem")
	public Page<com.pratikshat.exicom.model.response.LocationProductItemResponse> getLocationProductItems(
			@PathVariable("locationid") long locationId,@RequestParam(value="typefilter",required=false)String typefilter, Pageable page) {
		if(typefilter!=null) {
			if(typefilter.equalsIgnoreCase("all")) {
				return dbLocation.getLocationProductItem(locationId, page);
			}
			if(typefilter.equalsIgnoreCase("serialiezed")) {
				List<String> types=new ArrayList<String>();
				types.add("Product");
				types.add("Sub-Product");
				types.add("Sub Product");
				return dbLocation.getLocationProductItem(locationId, types, page);
			}
			if(typefilter.equalsIgnoreCase("nonserialiezed")) {
				List<String> types=new ArrayList<String>();
				types.add("Component");
				return dbLocation.getLocationProductItem(locationId, types, page);
			}
		}
		return dbLocation.getLocationProductItem(locationId,page);
	}
	
	@GetMapping("{locationid}/sentproductrequest")
	public Page<com.pratikshat.exicom.model.response.LocationProductRequestResponse> getSendProductRequest(
			@PathVariable("locationid") long locationId, Pageable page) {
		return dbProductRequest.getByDestinationLocation(locationId, page);
	}

	@GetMapping("{locationid}/receivedrequest")
	public Page<com.pratikshat.exicom.model.response.LocationProductRequestResponse> getReceiveRequest(
			@PathVariable("locationid") long locationId, Pageable page) {
		return dbProductRequest.getBySourceLocation(locationId, "Draft", page);
	}

	@GetMapping("{locationid}/itemforreturn")
	public List<com.pratikshat.exicom.model.response.LocationFaultyItemResponse> getFaultyItems(
			@PathVariable("locationid") long locationid) {
		List<com.pratikshat.exicom.model.response.LocationFaultyItemResponse> items = dbLocation
				.getFaultyItemByLocationId(locationid);
		for (int i = 0; i < items.size(); i++) {

			items.get(i).setLineItems(dbLocation.getFaultyLineItemByLocationItemId(items.get(i).getLocationItemId()));
		}
		return items;
	}
	@GetMapping("productitem")
	public Page<com.pratikshat.exicom.model.response.LocationProductItemResponse> getLocationProductItems(
			@RequestParam( value="producttype",required=false) String producttype, @RequestParam(value="locationtype",required=false) String locationType,
			Pageable page) {
		List<String> locationTypes=new ArrayList<String>();
		if(locationType==null||locationType.equalsIgnoreCase("all")) {
			locationTypes.add("Warehouse");
			locationTypes.add("Van");
			locationTypes.add("Site");
			locationTypes.add("Plant");
			locationTypes.add("Repair Engineer");
		}
		else {
			locationTypes.add(locationType);
		}
		if(producttype!=null) {
			if(producttype.equalsIgnoreCase("all")) {
				return dbLocation.getLocationProductItem(locationTypes, page, businessDomainId);
			}
			if(producttype.equalsIgnoreCase("serialiezed")) {
				List<String> types=new ArrayList<String>();
				types.add("Product");
				types.add("Sub-Product");
				types.add("Sub Product");
				return dbLocation.getLocationProductItem(locationTypes,types, page, businessDomainId);
			}
			if(producttype.equalsIgnoreCase("nonserialiezed")) {
				List<String> types=new ArrayList<String>();
				types.add("Component");
				return dbLocation.getLocationProductItem(locationTypes,types, page, businessDomainId);
			}
		}
			
		return dbLocation.getLocationProductItem(locationTypes, page, businessDomainId);
	}
	@PostMapping("/customereorder")
	public List<Map<String,Object>> inventoryMonitoringReorder(@RequestBody List<Map<String,Object>> data,@CurrentUser LoginUser user,HttpServletResponse response) throws java.io.IOException {
		try {
			return dbLocation.customeProductRequest(data, user);	
		}
		catch(BadRequestException bd) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,bd.getMessage());
			return null;
		}
		catch(UnauthoriseException ex) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		}catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
	}
	@GetMapping("threshold")
	public Page<Map<String,Object>> getLocationMsl(Pageable page,@RequestParam(value="producttype",required = false)String producttype,@RequestParam(value="locationType",required =false)String locationType){
		List<String> locationTypes=new ArrayList<String>();
		if(locationType==null||locationType.equalsIgnoreCase("all")) {
			locationTypes.add("Warehouse");
			locationTypes.add("Van");
			locationTypes.add("Site");
			locationTypes.add("Plant");
			locationTypes.add("Repair Engineer");
		}
		else {
			locationTypes.add(locationType);
		}
		if(producttype!=null) {
			List<String> producttypes=new ArrayList<String>();
			if(producttype.equalsIgnoreCase("all")) return dbLocation.getLocationMsl(locationTypes,page, businessDomainId);
			if(producttype.equalsIgnoreCase("serial")) {
				
				producttypes.add("Product");
				producttypes.add("Sub-Product");
				producttypes.add("Sub Product");
				return dbLocation.getLocationMsl(locationTypes,producttypes, page, businessDomainId);
			}
			if(producttype.equalsIgnoreCase("nonserial")) {
				producttypes.add("Component");
				return dbLocation.getLocationMsl(locationTypes,producttypes, page, businessDomainId);
			}
		}
		return dbLocation.getLocationMsl(locationTypes,page, businessDomainId);
	}
	@GetMapping("threshold/{locationid}")
	public Page<Map<String,Object>> getLocationMslByLocationId(@PathVariable("locationid")long locationId,@RequestParam(value="producttype",required = false)String producttype,Pageable page){
		if(producttype!=null) {
			List<String> producttypes=new ArrayList<String>();
			if(producttype.equalsIgnoreCase("all")) return dbLocation.getLocationMslByLocationId(locationId, page);
			if(producttype.equalsIgnoreCase("serial")) {
				
				producttypes.add("Product");
				producttypes.add("Sub-Product");
				producttypes.add("Sub Product");
				return dbLocation.getLocationMslByLocationId(locationId,producttypes, page);
			}
			if(producttype.equalsIgnoreCase("nonserial")) {
				producttypes.add("Component");
				return dbLocation.getLocationMslByLocationId(locationId,producttypes, page);
			}
			
		}
		return dbLocation.getLocationMslByLocationId(locationId, page);
	}
	@PostMapping("threshold")
	public List<Map<String,Object>> postRestMSL(@RequestBody List<Map<String,Object>> objects,@CurrentUser LoginUser user,HttpServletResponse response)throws java.io.IOException{
		try {
			return dbLocation.resetMSL(objects,user);
		} catch (BadRequestException  e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
			return null;
		}
		catch(UnauthoriseException ex) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		}catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
		
	}
	
	@GetMapping("serialnumber/{locationid}/{partcode}")
	public List<Map<String,Object>> getLocationProductSerialNumber(@PathVariable("locationid")long locationid,@PathVariable("partcode")String partCode){
		
		return dbLocation.getProductSerialNumberByLoationAndProduct(partCode,locationid);
	}
	@GetMapping("reparicenter")
	public List<Map<String,Object>> getRepariCenterWarehouse(){
		return dbLocation.getRepariCenter(businessDomainId);
	}
	@GetMapping("stock/{locationid}/{partcode}")
	public Map<String,Object> getStockByLocationIdAndPartCode(@PathVariable("locationid")long locationId,@PathVariable("partcode")String partcode){
		return dbLocation.getStockByLocationIdAndProductPartCode(partcode, locationId);
	}
	
	@GetMapping("myinventory")
	public Page<Map<String,Object>> getMyInventory(Pageable page,@CurrentUser LoginUser user){
		return dbLocation.getMyInventory(user.getUsername(), page);
	}
	
	@PostMapping("searchmyinventory")
	public List<Map<String,Object>> searchMyInventory(@RequestBody Map<String,String> request,@CurrentUser LoginUser user){
		return dbLocation.searchMyInventory(request, user, businessDomainId);
	}
	
	@PutMapping("updatestate/{lineitemid}")
	public String updateLineItemState(@PathVariable("lineitemid")long lineItemid,@RequestBody Map<String,Object> state,@CurrentUser LoginUser user,HttpServletResponse response) throws IOException{
		long id=Long.parseLong(state.get("state").toString());
		try {
			dbLocation.changeProductSerialNumberStatus(user, lineItemid, id,state.get("remarks").toString());
			return "{}";
		} catch (BadRequestException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,e.getMessage());
			e.printStackTrace();
		} catch (UnauthoriseException e) {
			// TODO Auto-generated catch block
			response.sendError(HttpServletResponse.SC_FORBIDDEN,e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}
	@GetMapping("parentandsiblings")
	public List<Map<String,Object>> getParentAndSiblings(@CurrentUser LoginUser user){
		return dbNativeLocation.getLocationParentAndSiblings(user);
	}
	
	@PostMapping("bulkpr")
	public LPRequest uploadBulk(@RequestParam MultipartFile bulkfile,@CurrentUser LoginUser user,HttpServletResponse response) throws IOException {
		LPRequest lpRequest=new LPRequest();
		List<LPItemRequest> ltItem=new ArrayList<LPItemRequest>();
		
		if(bulkfile.isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Wrong file");
			return null;
		}
		Workbook workBook=null;
		
		InputStream in=bulkfile.getInputStream();
		try {
			workBook =new XSSFWorkbook(in);
		}catch(Exception ex) {
			
		}
		try {
			workBook= new HSSFWorkbook(in);
		}catch(Exception ex) {
			
		}
		if(workBook==null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Please provide valid file format");
			return null;
		}
		
		Sheet sheet=workBook.getSheetAt(0);
		
		//SKIPING HEADER ROW
		Row headerRow=sheet.getRow(0);
		
		java.util.Iterator<Row> roIte=sheet.iterator();
		int i=0;
		Set<String> partcodes=new HashSet<String>();
		while(roIte.hasNext()) {
			Row rc=roIte.next();
			Cell sourceLocationCell=rc.getCell(0);
			Cell destinationLocationCell=rc.getCell(1);
			Cell descriptionCell=rc.getCell(2);
			Cell needDateTimeCell=rc.getCell(3);
			Cell partCodeCell=rc.getCell(4);
			Cell qtyCell=rc.getCell(5);
			
			if(i==0) {
				
				if(sourceLocationCell!=null && sourceLocationCell.getCellTypeEnum()!=CellType.NUMERIC) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Source Location Id not found");
					return null;
				}
				if(destinationLocationCell!=null &&destinationLocationCell.getCellTypeEnum()!=CellType.NUMERIC) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Destination Location Not found");
					return null;
				}
				lpRequest.setSourceLocationId((long)(sourceLocationCell.getNumericCellValue()));
				lpRequest.setDestinationLocationId((long)destinationLocationCell.getNumericCellValue());
				
				lpRequest.setDescirption(descriptionCell.getStringCellValue());
				lpRequest.setLocationProductRequestStatus("Draft");
				try {
					lpRequest.setNeedByDateTime(needDateTimeCell.getDateCellValue());
				}catch(Exception ex) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Wrong date input");
					return null;
				}
				
				
				
				//lpRequest.setSourceLocationId(rc.getCell(0).);
			}
			else {
				
				if(sourceLocationCell!=null && sourceLocationCell.getCellTypeEnum()!=CellType.NUMERIC) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Source Location Id not found");
					return null;
				}
				if(destinationLocationCell!=null &&destinationLocationCell.getCellTypeEnum()!=CellType.NUMERIC) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Destination Location Not found");
					return null;
				}
				long dlo=(long) destinationLocationCell.getNumericCellValue();
				long slo=(long) sourceLocationCell.getNumericCellValue();
				if(dlo!=lpRequest.getDestinationLocationId()) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Multiple Destination Location");
					return null;
				}
				if(slo!=lpRequest.getSourceLocationId()) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Multiple Source Location");
					return null;
				}
					
			}
			LPItemRequest lpItem=new LPItemRequest();
			String prt=partCodeCell.getStringCellValue();
			
			if(partcodes.contains(prt)) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Multiple part code");
				return null;
			}
			
			partcodes.add(prt);
			
			lpItem.setProductPartCode(prt);
			lpItem.setRequestQuantity((float)qtyCell.getNumericCellValue());
			ltItem.add(lpItem);
		}
		requestProduct(lpRequest.getDestinationLocationId(),lpRequest,user,response);			
		return lpRequest;
	}
	
	
	@GetMapping("advancesearch")
	public List<LocationResponse> getAdvanceSearchresult(@RequestParam Map<String,Object> request,@CurrentUser LoginUser user,HttpServletResponse  response) throws IOException, ParseException{
		
		if(dbProfile.hasObjectAccess(user.getUsername(), com.pratikshat.exicom.entity.Location.class, UserProfileRepository.VIEW_ALL)) {
			return 	dbLocation.advanceSearch(request, user, null, businessDomainId);
		}
		
		if(dbProfile.hasObjectAccess(user.getUsername(), com.pratikshat.exicom.entity.Location.class, UserProfileRepository.VIEW)) {
			return 	dbLocation.advanceSearch(request, user, dbProfile.getLocationAndChildLocation(user.getUsername()), businessDomainId);
		}
		
		response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
		return null;
		
	}
	
	
	@GetMapping("{locationid}/childlocation/advancesearch")
	public List<com.pratikshat.exicom.model.response.LocationResponse> getChildLocationAdvanceSearch(@PathVariable("locationid") long locationid, @RequestParam Map<String,Object> request,@CurrentUser LoginUser user,HttpServletResponse  response) throws IOException, ParseException{
		return dbLocation.getChildLocationAdvanceSearch(locationid,request, user, businessDomainId);
	}
	
	
	@GetMapping("{locationid}/productitem/advancesearch")
	public List<com.pratikshat.exicom.model.response.LocationProductItemResponse> getLocationProductItemsAdvanceSearch(
			@PathVariable("locationid") long locationId, @RequestParam Map<String,Object> request,@CurrentUser LoginUser user,HttpServletResponse  response) throws IOException, ParseException{

		return dbLocation.getLocationProductItemsAdvanceSearch(locationId, request, user, businessDomainId);
	}
	
	
	@GetMapping("{locationid}/sentproductrequest/advancesearch")
	public List<com.pratikshat.exicom.model.response.LocationProductRequestResponse> getSendProductRequestAdvanceSearch(
			@PathVariable("locationid") long locationId, @RequestParam Map<String,Object> request,@CurrentUser LoginUser user,HttpServletResponse  response) throws IOException, ParseException{
		return dbLocation.getSendProductRequestAdvanceSearch(locationId, request, user, businessDomainId);
	}
	
	@GetMapping("{locationid}/receivedrequest/advancesearch")
	public List<com.pratikshat.exicom.model.response.LocationProductRequestResponse> getReceivedProductRequestAdvanceSearch(
			@PathVariable("locationid") long locationId, @RequestParam Map<String,Object> request,@CurrentUser LoginUser user,HttpServletResponse  response) throws IOException, ParseException{
		return dbLocation.getReceivedProductRequestAdvanceSearch(locationId, request, user, businessDomainId);
	}

	@PutMapping("partcodechangemyinventory/{id}")
	public String changeLocationPartcode(@PathVariable("id")long id,@RequestBody Map<String,Object> data,HttpServletResponse response,@CurrentUser LoginUser user)throws IOException {
		if(dbProfile.hasObjectAccess(user.getUsername(), LocationProductLineItems.class, UserProfileRepository.EDIT)&&dbProfile.hasObjectAccess(user.getUsername(), LocationProductItem.class, UserProfileRepository.EDIT)) {
			try {
				dbLocation.changeProductPartcodeAndState(id, data, user);
				return "{}";
			}catch(BadRequestException ex) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST,ex.getMessage());
			}catch(UnauthoriseException ex) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
			}
		}
		else {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"You are not authorised");
		}
		return null;
	}
	
	
}
