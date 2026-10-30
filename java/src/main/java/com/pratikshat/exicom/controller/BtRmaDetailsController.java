package com.pratikshat.exicom.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.validator.constraints.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.BtRMADetails;
import com.pratikshat.exicom.entity.BtRmaChangeStatusLog;
import com.pratikshat.exicom.entity.BtRmaRejectLog;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.BtCommissioningFSRRequest;
import com.pratikshat.exicom.model.request.BtCommissioningFSRUpdateRequest;
import com.pratikshat.exicom.model.request.ChangeStatusRmaRequest;
import com.pratikshat.exicom.model.request.RejectRmaRequest;
import com.pratikshat.exicom.model.response.BtCommissioningFSRResponse;
import com.pratikshat.exicom.model.response.BtRmaResponse;
import com.pratikshat.exicom.repository.BtRmaDetailsRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtRmaDetailsService;
import com.pratikshat.exicom.utils.ExcelParser;
import com.pratikshat.exicom.utils.RmaChangeStatusParser;
import com.pratikshat.exicom.utils.RmaRejectParser;

@RestController
@RequestMapping("bt/api/rma")
public class BtRmaDetailsController {

	@Autowired
	BtRmaDetailsService btRmaDetailsService;

	@Autowired
	BtRmaDetailsRepository dbRmaDetailsRepo;

	@Autowired
	UserProfileRepository dbProfile;

	Logger logger = LoggerFactory.getLogger(BtRmaDetailsController.class);

	@PostMapping
	public Map<String, Object> saveRMA(@RequestBody Map<String, Object> req, @CurrentUser LoginUser user)
			throws BadRequestException, UnauthoriseException {
		return null;
	}

	@GetMapping()
	public Page<Map<String, Object>> getPage(@CurrentUser LoginUser user, Pageable page,
			@RequestParam(value = "filtername", required = false) String filtername)
			throws BadRequestException, UnauthoriseException {
		return btRmaDetailsService.getAll(user, page, filtername);
	}

	@PutMapping("{id}")
	public Map<String, Object> updateRMA(@RequestBody Map<String, Object> req, @PathVariable("id") long id,
			@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException {
		return null;
	}

	@GetMapping("/{id}")
	public Map<String, Object> getById(@PathVariable("id") int id, @CurrentUser LoginUser user)
			throws BadRequestException, UnauthoriseException {
		return btRmaDetailsService.getSingle(id, user);
	}

	@GetMapping("/case/{id}")
	public BtRmaResponse getRMAByCaseId(@PathVariable("id") long id, @CurrentUser LoginUser user) {
		System.out.println(id);
		BtRmaResponse response = dbRmaDetailsRepo.getRmaByCaseId(id);
//		System.out.println(response.getManualId());
		return response;

	}

	@PutMapping("/reject")
	public BtRmaRejectLog rejectRMA(@RequestPart("data") Map<String, Object> rejectRmaRequest,@RequestPart("file") MultipartFile file, @CurrentUser LoginUser user)
			throws BadRequestException, UnauthoriseException {
		return btRmaDetailsService.rejectRMAById(rejectRmaRequest,file, user);
	}

	@PutMapping("/updateStatus")
	public BtRmaResponse updateStatus(@RequestPart("data") Map<String, Object> changeStatusRequest,@RequestPart("file") MultipartFile file, @CurrentUser LoginUser user)
			throws BadRequestException, UnauthoriseException {
		return btRmaDetailsService.updateStatus(changeStatusRequest, file, user);
	}

	@GetMapping("/advancesearch")
	public List<Map<String, Object>> advanceSearch(@RequestParam Map<String, Object> reqq, @CurrentUser LoginUser user)
			throws BadRequestException, UnauthoriseException {
		return btRmaDetailsService.advanceSearch(reqq, user);
	}

	@GetMapping("forlist")
	public List<Map<String, Object>> forList(@RequestParam("search") String search, @CurrentUser LoginUser user)
			throws BadRequestException, UnauthoriseException {
		return btRmaDetailsService.searchForList("%"+search+"%",user);
	}

	@GetMapping("/changeStatusTemplate")
	public ResponseEntity<ByteArrayResource> getChangeStatusTemplate(HttpServletResponse httpServletResponse)
			throws IOException {
		HttpHeaders header = new HttpHeaders();
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("RMA Change Status");
		Object[][] bookData = { { "RMA Number", "Remark" }, };

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
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "rma_status_change_template.xlsx");
		httpServletResponse.setContentType("application/xlsx");
		ByteArrayOutputStream bytest = new ByteArrayOutputStream();
		workbook.write(bytest);
		return new ResponseEntity<>(new ByteArrayResource(bytest.toByteArray()), header, HttpStatus.OK);
	}

	@GetMapping("/reject_template")
	public ResponseEntity<ByteArrayResource> getRMARejectTemplate(HttpServletResponse httpServletResponse)
			throws IOException {
		HttpHeaders header = new HttpHeaders();
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("RMA Reject");
		Object[][] bookData = { { "RMA Number", "Remark" }, };

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
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "rma_reject_template.xlsx");
		httpServletResponse.setContentType("application/xlsx");
		ByteArrayOutputStream bytest = new ByteArrayOutputStream();
		workbook.write(bytest);
		return new ResponseEntity<>(new ByteArrayResource(bytest.toByteArray()), header, HttpStatus.OK);
	}

	@PutMapping("/changeStatusUpload")
	public ResponseEntity<ByteArrayResource> changeStatusRMA(
			@RequestParam("file") MultipartFile readRmaChangeStatusRmaExcel, @CurrentUser LoginUser user,
			HttpServletResponse servletResponse) throws IOException {

		String fileName = readRmaChangeStatusRmaExcel.getOriginalFilename();

		if (fileName.substring(fileName.lastIndexOf('.') + 1).equals("xlsx")) {

			if (!dbProfile.hasObjectAccess(user.getUsername(), BtRmaChangeStatusLog.class, UserProfileRepository.CREATE)
					|| !dbProfile.hasObjectAccess(user.getUsername(), BtRmaChangeStatusLog.class,
							UserProfileRepository.EDIT)) {
				servletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorized");
				return null;
			}

			List<Map<String, Object>> outData = new ArrayList<Map<String, Object>>();
			ByteArrayOutputStream st = null;
			try {
				List<ChangeStatusRmaRequest> requests = new RmaChangeStatusParser().parseIn(readRmaChangeStatusRmaExcel);

				for (ChangeStatusRmaRequest  changeStatusRmaRequest: requests) {
					Map<String, Object> d = new HashMap<String, Object>();

					d.put("RMA Number", changeStatusRmaRequest.getRmaManualId());
					d.put("Remark", changeStatusRmaRequest.getRemarks());
					try {

						if (changeStatusRmaRequest.getRmaManualId() == null
								|| changeStatusRmaRequest.getRmaManualId().equalsIgnoreCase("")) {
							servletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "RMA manual id is null");
							return null;
						}

						if (changeStatusRmaRequest.getRemarks() == null
								|| changeStatusRmaRequest.getRemarks().equalsIgnoreCase("")) {
							servletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "RMA Remark is null");
						}

						ChangeStatusRmaRequest _caseRequest = btRmaDetailsService.changeStatusRma(changeStatusRmaRequest, user);
						d.put("STATUS", "Success");
					} catch (Exception ex) {
						logger.error("error in RMA change status bulk upload {}", ex);
						d.put("STATUS", "Error " + ex.getMessage());
					}
					outData.add(d);
				}
			} catch (Exception ex) {
				logger.error("error in RMA change status bulk upload {}", ex);
				servletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
				return null;
			}
			try {
				st = new ExcelParser().genrateFile(outData);
			} catch (Exception ex) {
				logger.error("error in RMA change status bulk upload {}", ex);
				servletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
				return null;
			}

			servletResponse.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=BtRMAChangeStatusResult.xlsx");
			servletResponse.setContentType("application/vnd.ms-excel");
			servletResponse.getOutputStream().write(st.toByteArray());
			servletResponse.getOutputStream().flush();
			servletResponse.getOutputStream().close();
			return null;
		} else {
			servletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "file is not excel");
			return null;
		}
	}

	@PutMapping("/rejectUpload")
	public ResponseEntity<ByteArrayResource> rejectRMA(@RequestParam("file") MultipartFile readRmaRejectExcel,
			@CurrentUser LoginUser user, HttpServletResponse servletResponse) throws Exception {
		String fileName = readRmaRejectExcel.getOriginalFilename();

		if (fileName.substring(fileName.lastIndexOf('.') + 1).equals("xlsx")) {

			if (!dbProfile.hasObjectAccess(user.getUsername(), BtRmaRejectLog.class, UserProfileRepository.CREATE)
					|| !dbProfile.hasObjectAccess(user.getUsername(), BtRmaRejectLog.class,
							UserProfileRepository.EDIT)) {
				servletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorized");
				return null;
			}

			List<Map<String, Object>> outData = new ArrayList<Map<String, Object>>();
			ByteArrayOutputStream st = null;
			try {
				List<RejectRmaRequest> requests = new RmaRejectParser().parseIn(readRmaRejectExcel);

				for (RejectRmaRequest rejectRmaRequest : requests) {
					Map<String, Object> d = new HashMap<String, Object>();

					d.put("RMA Number", rejectRmaRequest.getRmaManualId());
					d.put("Remark", rejectRmaRequest.getRemarks());
					try {

						if (rejectRmaRequest.getRmaManualId() == null
								|| rejectRmaRequest.getRmaManualId().equalsIgnoreCase("")) {
							servletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "RMA manual id is null");
							return null;
						}

						if (rejectRmaRequest.getRemarks() == null
								|| rejectRmaRequest.getRemarks().equalsIgnoreCase("")) {
							servletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "RMA Remark is null");
						}

						RejectRmaRequest _caseRequest = btRmaDetailsService.rejectRma(rejectRmaRequest, user);
						d.put("STATUS", "Success");
					} catch (Exception ex) {
						logger.error("error in RMA reject bulk upload {}", ex);
						d.put("STATUS", "Error " + ex.getMessage());
					}
					outData.add(d);
				}
			} catch (Exception ex) {
				logger.error("error in RMA reject bulk upload {}", ex);
				servletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
				return null;
			}
			try {
				st = new ExcelParser().genrateFile(outData);
			} catch (Exception ex) {
				logger.error("error in RMA reject bulk upload {}", ex);
				servletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
				return null;
			}

			servletResponse.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=BtRMARejectResult.xlsx");
			servletResponse.setContentType("application/vnd.ms-excel");
			servletResponse.getOutputStream().write(st.toByteArray());
			servletResponse.getOutputStream().flush();
			servletResponse.getOutputStream().close();
			return null;
		} else {
			servletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "file is not excel");
			return null;
		}
	}
}
