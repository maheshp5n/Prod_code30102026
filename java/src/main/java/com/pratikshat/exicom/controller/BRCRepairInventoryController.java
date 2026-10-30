package com.pratikshat.exicom.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFRow;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.AccountRecentVisit;
import com.pratikshat.exicom.entity.BRCRepairInventoryDispatch;
import com.pratikshat.exicom.entity.BRCRepairInventoryReceive;
import com.pratikshat.exicom.entity.BRCRepairInventoryReceiveRecentVisit;
import com.pratikshat.exicom.entity.BRCRepairInventoryReceiveRepairItem;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.AccountCreate;
import com.pratikshat.exicom.model.request.BRCRepairInventoryDispatchRequest;
import com.pratikshat.exicom.model.request.BRCRepairInventoryReceiveRequest;
import com.pratikshat.exicom.model.response.BRCInventoryReceiveResponse;
import com.pratikshat.exicom.model.response.BRCRepariInventoryProductConsumedResponse;
import com.pratikshat.exicom.repository.BRCRepiarResolutionMethodChoosenRepository;

@org.springframework.web.bind.annotation.RestController
@org.springframework.web.bind.annotation.RequestMapping("api/brcrepair")
public class BRCRepairInventoryController {
	@Autowired
	com.pratikshat.exicom.repository.BRCRepairBERRemarkRepository dbberRemarks;
	@Autowired
	com.pratikshat.exicom.repository.BRCRepairInventoryReceiveRepairItemRepository dbItemsRepair;
	@Autowired
	com.pratikshat.exicom.repository.BRCRepairInventoryReceiveRepository dbItemReceive;
	@Autowired
	com.pratikshat.exicom.repository.BRCRepairTypeRepository dbRepairType;
	@Autowired
	com.pratikshat.exicom.repository.BRCRepiarBalancedAtRepository dbBalanceAt;
	@Autowired
	com.pratikshat.exicom.repository.BRCRepiarResolutionMethodRepository dbResolutionMethod;
	@Autowired
	com.pratikshat.exicom.repository.BRCRepiarStatusRepository dbRepairStatus;
	@Autowired
	com.pratikshat.exicom.repository.PackingConditionRepository dbPacking;
	@Autowired
	com.pratikshat.exicom.repository.FaultObservedRepository dbFaultObserved;
	@Autowired
	BRCRepiarResolutionMethodChoosenRepository dbBRCRepiarResolutionMethodChoosen;

	@Autowired
	com.pratikshat.exicom.repository.UserProfileRepository dbProfile;

	// shyamu
	@Autowired
	com.pratikshat.exicom.repository.BRCRepairInventoryReceiveRecentVisitRepository dbItemReceiveRecentVisit;

	@GetMapping("dropdown/berremarks")
	public java.util.List<com.pratikshat.exicom.entity.BRCRepairBERRemark> getRemarks() {
		return dbberRemarks.findAll();
	}

	@GetMapping("dropdown/packingcondition")
	public java.util.List<com.pratikshat.exicom.entity.PackingCondition> getPackingCondation() {
		return dbPacking.findAll();
	}

	@GetMapping("dropdown/physicaldamage")
	public ResponseEntity<?> getPhysicalDamage() {
		return ResponseEntity.ok("[\"Yes\",\"No\"]");
	}

	@GetMapping("dropdown/faultobserved")
	public java.util.List<com.pratikshat.exicom.model.response.BrcfaultObservedResponse> getFaultObserved() {
		return dbFaultObserved.getAll();
	}

	@GetMapping("dropdown/status")
	public java.util.List<com.pratikshat.exicom.entity.BRCRepiarStatus> getStatus() {
		return dbRepairStatus.findAll();
	}

	@GetMapping("dropdown/types")
	public java.util.List<com.pratikshat.exicom.entity.BRCRepairType> getTypes() {
		return dbRepairType.findAll();
	}

	@GetMapping("dropdown/balanceat")
	public java.util.List<com.pratikshat.exicom.entity.BRCRepiarBalancedAt> getBalanceAt() {
		return dbBalanceAt.findAll();
	}

	@GetMapping("dropdown/resolutionmethods")
	public java.util.List<com.pratikshat.exicom.entity.BRCRepiarResolutionMethod> getResulutionMethods() {
		return dbResolutionMethod.findAll();
	}

	@PostMapping
	public com.pratikshat.exicom.model.response.BRCInventoryReceiveResponse saveRequest(
			@RequestBody com.pratikshat.exicom.model.request.BRCRepairInventoryReceiveRequest request,
			@com.pratikshat.exicom.annotation.CurrentUser com.pratikshat.exicom.entity.LoginUser user,
			HttpServletResponse response) throws IOException {
		if (!dbProfile.hasObjectAccess(user.getUsername(), "BRCRepairInventoryReceive", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}

		try {
			BRCRepairInventoryReceiveRequest item = dbItemReceive.saveRequest(request, user);

			return dbItemReceive.getMIByMiId(item.getReceivingId());
		} catch (BadRequestException ex) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		} catch (UnauthoriseException ex) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		} catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}

	}

	@PostMapping("jobcard/assigntoeng")
	public BRCInventoryReceiveResponse assignToEng(@RequestParam("receivingId") long receivingId,
			@RequestParam("serviceResourceId") long serviceResourceId, @CurrentUser LoginUser user,
			HttpServletResponse response) throws IOException {
		if (!dbProfile.hasObjectAccess(user.getUsername(), "BRCRepairInventoryReceive", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		try {
			BRCRepairInventoryReceiveRepairItem item = dbItemReceive.assignToEng(receivingId, serviceResourceId, user);

			BRCInventoryReceiveResponse BRCresponse = dbItemReceive.getMIByMiId(receivingId);
			BRCresponse.setBrcRepairResolutionMethod(
					dbBRCRepiarResolutionMethodChoosen.getResolutionMethodByRepairId(item.getRepairId()));
			return BRCresponse;
		} catch (BadRequestException ex) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		} catch (UnauthoriseException ex) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		} catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
	}

	@PostMapping("jobcard/{repairid}/receivefromeng")
	public BRCInventoryReceiveResponse saveRquest(@PathVariable("repairid") long receivingid,
			@RequestBody com.pratikshat.exicom.model.request.BRCRepairInventoryReceiveItemRequest itemRequest,
			@com.pratikshat.exicom.annotation.CurrentUser com.pratikshat.exicom.entity.LoginUser user,
			HttpServletResponse response) throws IOException {
		if (!dbProfile.hasObjectAccess(user.getUsername(), "BRCRepairInventoryReceive", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		if (receivingid != itemRequest.getRepairId()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Receiving not found");
			return null;
		}

		try {
			dbItemReceive.receiveFromEng(itemRequest, user);
			BRCInventoryReceiveResponse BRCresponse = dbItemReceive.getMIByMiId(itemRequest.getReceiveDetailsId());
			BRCresponse.setBrcRepairResolutionMethod(
					dbBRCRepiarResolutionMethodChoosen.getResolutionMethodByRepairId(itemRequest.getRepairId()));
			return BRCresponse;
		} catch (BadRequestException ex) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		} catch (UnauthoriseException ex) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		} catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}

	}

	@PostMapping("{repairitemid}")
	public BRCInventoryReceiveResponse saveDispatch(@PathVariable("repairitemid") long repairitemid,
			@RequestBody com.pratikshat.exicom.model.request.BRCRepairInventoryDispatchRequest dispatchRequest,
			@com.pratikshat.exicom.annotation.CurrentUser com.pratikshat.exicom.entity.LoginUser user,
			HttpServletResponse response) throws IOException {
		if (!dbProfile.hasObjectAccess(user.getUsername(), "BRCRepairInventoryReceive", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}

		try {
			BRCRepairInventoryReceive receive = dbItemReceive.saveDispatch(dispatchRequest, user);

			BRCInventoryReceiveResponse BRCresponse = dbItemReceive.getMIByMiId(receive.getReceivingId());
			BRCresponse.setBrcRepairResolutionMethod(dbBRCRepiarResolutionMethodChoosen
					.getResolutionMethodByRepairId(receive.getRepariItem().getRepairId()));
			return BRCresponse;
		} catch (BadRequestException ex) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		} catch (UnauthoriseException ex) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		} catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}

	}

	@PostMapping("{receivingid}/productconsumed")
	public com.pratikshat.exicom.model.request.BRCRepariInventoryProductConsumedRequest consumeProduct(
			@PathVariable("receivingid") long receiveid,
			@RequestBody com.pratikshat.exicom.model.request.BRCRepariInventoryProductConsumedRequest request,
			@com.pratikshat.exicom.annotation.CurrentUser com.pratikshat.exicom.entity.LoginUser user,
			HttpServletResponse response) throws IOException {
		if (!dbProfile.hasObjectAccess(user.getUsername(), "BRCRepairInventoryReceive", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		try {
			return dbItemReceive.consumeProduct(request, user);
		} catch (BadRequestException ex) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		} catch (UnauthoriseException ex) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		} catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
	}

	// created by shyamu
	@PutMapping
	public com.pratikshat.exicom.model.response.BRCInventoryReceiveResponse updateRequest(
			@RequestBody com.pratikshat.exicom.model.request.BRCRepairInventoryReceiveRequest request,
			@com.pratikshat.exicom.annotation.CurrentUser com.pratikshat.exicom.entity.LoginUser user,
			HttpServletResponse response) throws IOException {

		if (!dbProfile.hasObjectAccess(user.getUsername(), "BRCRepairInventoryReceive", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}

		if (dbItemReceive.countByReceivingId(request.getReceivingId()) == 0) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Receiving id not exists");
			return null;
		}
		try {
			BRCRepairInventoryReceiveRequest receive = dbItemReceive.updateRequest(request, user);

			BRCInventoryReceiveResponse BRCresponse = dbItemReceive.getMIByMiId(receive.getReceivingId());
			BRCresponse.setBrcRepairResolutionMethod(
					dbBRCRepiarResolutionMethodChoosen.getResolutionMethodByRepairId(BRCresponse.getRepairId()));
			return BRCresponse;

		} catch (BadRequestException ex) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		} catch (UnauthoriseException ex) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		} catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
	}

	@GetMapping
	public Page<BRCInventoryReceiveResponse> getMIDetails(Pageable page, @CurrentUser LoginUser user,
			HttpServletResponse response) {
		if (dbProfile.hasObjectAccess(user.getUsername(), "BRCRepairInventoryReceive", "viewAllRights")) {
			return dbItemReceive.getMIDetails(page);
		}
		if (dbProfile.hasObjectAccess(user.getUsername(), "BRCRepairInventoryReceive", "viewRights")) {
			List<String> ulist = new ArrayList<String>();
			ulist = dbProfile.getListOfUserAndChildByUser(user.getUsername());
			List<com.pratikshat.exicom.entity.EmployeeDetails> emps = new ArrayList<com.pratikshat.exicom.entity.EmployeeDetails>();
			for (String u : ulist) {
				emps.add(new com.pratikshat.exicom.entity.EmployeeDetails(u));
			}
			System.out.println(ulist);
			return dbItemReceive.getMIDetails(emps, page);
		}
		return Page.empty();
	}

	@GetMapping("recentVisit")
	public Page<BRCInventoryReceiveResponse> getRecentVisit(@CurrentUser LoginUser user, Pageable page) {
		return dbItemReceive.getRecentVisit(user.getUsername(), page);
	}

	@GetMapping("{receivingId}")
	public BRCInventoryReceiveResponse getMIByMiId(@CurrentUser LoginUser user,
			@PathVariable("receivingId") long receivingId) {
		try {

			dbItemReceiveRecentVisit
					.save(new BRCRepairInventoryReceiveRecentVisit(receivingId, user.getUsername(), new Date()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		BRCInventoryReceiveResponse response = dbItemReceive.getMIByMiId(receivingId);
		response.setBrcRepairResolutionMethod(
				dbBRCRepiarResolutionMethodChoosen.getResolutionMethodByRepairId(response.getRepairId()));
		return response;
	}

	@GetMapping("forList")
	public java.util.List<com.pratikshat.exicom.model.response.BRCRepairInventoryForList> getBRCRepairInventoryForList() {
		return dbItemReceive.getBRCRepairInventoryForList();
	}
	// close by shyamu

	@GetMapping("getbyuser")
	public Page<BRCInventoryReceiveResponse> getByUser(@CurrentUser LoginUser user, Pageable page) {
		return dbItemReceive.getMIByEngineer(user.getUsername(), page);
	}

	@GetMapping("getbytype")
	public Page<BRCInventoryReceiveResponse> getByTyper(@RequestParam("type") String type, @CurrentUser LoginUser user,
			Pageable page) {
		if (dbProfile.hasObjectAccess(user.getUsername(), "BRCRepairInventoryReceive", "viewAllRights")) {
			return dbItemReceive.getMIByType(type, page);
		}
		if (dbProfile.hasObjectAccess(user.getUsername(), "BRCRepairInventoryReceive", "viewRights")) {
			List<String> lst = new ArrayList<String>();
			List<com.pratikshat.exicom.entity.EmployeeDetails> emsp = new ArrayList<com.pratikshat.exicom.entity.EmployeeDetails>();
			lst = dbProfile.getListOfUserAndChildByUser(user.getUsername());
			for (String u : lst) {
				emsp.add(new com.pratikshat.exicom.entity.EmployeeDetails(u));
			}
			return dbItemReceive.getMIByType(emsp, type, page);
		}
		return Page.empty();
	}

	@GetMapping("getbyuserandtype")
	public Page<BRCInventoryReceiveResponse> getByUserAndType(@RequestParam("type") String type,
			@CurrentUser LoginUser user, Pageable page) {
		return dbItemReceive.getMIByEngineerAndType(user.getUsername(), type, page);
	}

	@GetMapping("productconsumed/related/{receivingId}")
	public Page<BRCRepariInventoryProductConsumedResponse> getProductConsumedByMI(
			@PathVariable("receivingId") long receivingId, Pageable page) {
		return dbItemReceive.getProductConsumedByMI(receivingId, page);
	}

	@GetMapping("productconsumed")
	public Page<BRCRepariInventoryProductConsumedResponse> getProductConsumed(Pageable page) {
		return dbItemReceive.getProductConsumed(page);
	}

	// BRC Bulk Upload by shagufta
	@PostMapping("/bulkuploadMI")
	public ResponseEntity<ByteArrayResource> mapReapExcelDatatoDB(@RequestParam("file") MultipartFile reapExcelDataFile,
			@CurrentUser com.pratikshat.exicom.entity.LoginUser user, HttpServletResponse httpResponse)
			throws IOException {
		// System.out.println(reapExcelDataFile.getOriginalFilename());
		String fileName = reapExcelDataFile.getOriginalFilename();
		int i;
		com.pratikshat.exicom.model.request.BRCRepairInventoryReceiveRequest request = new BRCRepairInventoryReceiveRequest();
		if (fileName.substring(fileName.lastIndexOf('.') + 1).equals("xlsx")) {
			HttpHeaders header = new HttpHeaders();
			// FileOutputStream output_file =new FileOutputStream(new
			// File(fileName)); // for write file
			XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
			XSSFSheet worksheet = workbook.getSheetAt(0);
			Cell cell = null;
			i = 0;
			int fault = worksheet.getFirstRowNum();
			XSSFRow row = worksheet.getRow(i);
			// Iterate over data and write to sheet
			CellStyle style = workbook.createCellStyle();

			if (!dbProfile.hasObjectAccess(user.getUsername(), "BRCRepairInventoryReceive", "createRights")) {
				httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
				return null;
			}

			for (i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
				try {
					row = worksheet.getRow(i);
					if (row.getCell(i) == null || row.getCell(i).equals(" ") || row.getCell(i).equals("")) {
						row.getCell(i, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

					}
					DataFormatter dataFormatter = new DataFormatter();

					request.setProductPartCode(dataFormatter.formatCellValue(row.getCell(0)));
					request.setCaseDetailsId(Long.parseLong(dataFormatter.formatCellValue(row.getCell(1))));
					// casedetail.setCaseId(Long.parseLong(dataFormatter.formatCellValue(row.getCell(1))));
					request.setLegalEntityId(Long.parseLong(dataFormatter.formatCellValue(row.getCell(2))));
					request.setInvoiceNumber(dataFormatter.formatCellValue(row.getCell(3)));
					request.setInvoiceDate(row.getCell(4).getDateCellValue());
					request.setAlterSerialNumber(dataFormatter.formatCellValue(row.getCell(5)));
					request.setChallanNumber(dataFormatter.formatCellValue(row.getCell(6)));
					request.setRjilChallanDate(row.getCell(7).getDateCellValue());
					request.setLocationStateOrCircle(dataFormatter.formatCellValue(row.getCell(8)));
					request.setPackingConditionId(Integer.parseInt(dataFormatter.formatCellValue(row.getCell(9))));
					request.setReceivedDate(row.getCell(10).getDateCellValue());
					request.setPhysicalDamageIfAny(dataFormatter.formatCellValue(row.getCell(11)));
					request.setFaultObservedId(Integer.parseInt(dataFormatter.formatCellValue(row.getCell(12))));
					request.setBatteryVoltageBefoureRepair(dataFormatter.formatCellValue(row.getCell(13)));
					request.setBrcRepariStatus(Integer.parseInt(dataFormatter.formatCellValue(row.getCell(14))));
					request.setRemarks(dataFormatter.formatCellValue(row.getCell(15)));
					request.setSiteId(Long.parseLong(dataFormatter.formatCellValue(row.getCell(16))));

					dbItemReceive.saveRequest(request, user);

					Cell createdCell = row.createCell(17);
					createdCell.setCellValue("success");
					style.setFillBackgroundColor(IndexedColors.RED.getIndex());
					style.setFillPattern(FillPatternType.LESS_DOTS);
					createdCell.setCellStyle(style);
				} catch (Exception e) {
					e.printStackTrace();
					++fault;
					Cell createdCell = row.createCell(17);
					createdCell.setCellValue(e.getMessage());
					style.setFillBackgroundColor(IndexedColors.RED.getIndex());
					style.setFillPattern(FillPatternType.LESS_DOTS);
					createdCell.setCellStyle(style);

				}

			}
			if (fault == worksheet.getPhysicalNumberOfRows()) {

				header.setContentType(new MediaType("application", "force-download"));
				header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
				httpResponse.setContentType("application/xlsx");
				ByteArrayOutputStream bytest = new ByteArrayOutputStream();
				FileOutputStream fileOut = new FileOutputStream(fileName);
				workbook.write(fileOut);
				workbook.write(bytest);
				bytest.close();
				fileOut.close();
				return new ResponseEntity<>(new ByteArrayResource(bytest.toByteArray()), header,
						HttpStatus.BAD_REQUEST);

			} else {

				header.setContentType(new MediaType("application", "force-download"));
				header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
				httpResponse.setContentType("application/xlsx");
				ByteArrayOutputStream bytest = new ByteArrayOutputStream();
				FileOutputStream fileOut = new FileOutputStream(fileName);
				workbook.write(fileOut);
				workbook.write(bytest);
				bytest.close();
				fileOut.close();
				return new ResponseEntity<>(new ByteArrayResource(bytest.toByteArray()), header, HttpStatus.OK);
			}

		} else {
			System.out.println("file is not excel, file=" + reapExcelDataFile.getContentType());
		}

		return null;
	}

}
