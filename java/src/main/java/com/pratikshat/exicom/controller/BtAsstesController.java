
package com.pratikshat.exicom.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.AssetAmcUpdateLog;
import com.pratikshat.exicom.entity.BtAssetAmcUpdateLog;
import com.pratikshat.exicom.entity.BtAssetsAmcdetails;
import com.pratikshat.exicom.entity.BtCase;
import com.pratikshat.exicom.entity.BtCustomerInventoryIn;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.AmcUpdateRequest;
import com.pratikshat.exicom.model.response.BtPrcResponse;
import com.pratikshat.exicom.repository.AssetsStatus;
import com.pratikshat.exicom.repository.BtAssetsRepository;
import com.pratikshat.exicom.repository.BtCaseRepository;
import com.pratikshat.exicom.repository.BtCustomerInventoryInRepository;
import com.pratikshat.exicom.repository.BtPreRegistrationCasesRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtAssetsService;
import com.pratikshat.exicom.utils.AmcUpdateParser;
import com.pratikshat.exicom.utils.ExcelParser;

@RestController
@RequestMapping("bt/api/assets")
public class BtAsstesController {

	@Autowired
	BtAssetsService assetsService;
	@Autowired
	BtAssetsRepository dbAssets;
	@Autowired
	AssetsStatus dbStatus;
	@Autowired
	BtCaseRepository dbCase;
	@Autowired
	com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	@Autowired
	BtPreRegistrationCasesRepository dbPreRegistrationCasesRepository;
	@Autowired
	BtCustomerInventoryInRepository dbBRCCase;

	@GetMapping("/{id}")
	private Map<String, Object> getById(@PathVariable("id") int id, @CurrentUser LoginUser user)
			throws BadRequestException, UnauthoriseException {
		return assetsService.getSingle(id, user);
	}

	@GetMapping()
	private Page<Map<String, Object>> getPage(@CurrentUser LoginUser user, Pageable page)
			throws BadRequestException, UnauthoriseException {
		return assetsService.getPage(page, user);
	}
	@GetMapping("warranty/{id}")
	public Map<String, Object> getWarrantyAssetsSerialNumber(@PathVariable("id") String id, @CurrentUser LoginUser user) 
		throws BadRequestException, UnauthoriseException {
		return assetsService.getByAssetsSerialNumber(id, user);
	}

	@GetMapping("case/{assetsserialnumber}")
	public Page<Map<String, Object>> getCaseByAssetsSerialNumber(
			@PathVariable("assetsserialnumber") Long assetsserialnumber, Pageable page) {
		return dbCase.getByAssetsSerialNumber(assetsserialnumber, page);
	}
	
	@GetMapping("prc/{assetsserialnumber}")
	public Page<BtPrcResponse> getPrcCaseByAssetsSerialNumber(
			@PathVariable("assetsserialnumber") Long assetsserialnumber, Pageable page) {
		return dbPreRegistrationCasesRepository.getByAssetsSerialNumber(assetsserialnumber, page);
	}
	
	@GetMapping("brccase/{assetsserialnumber}")
	public Page<Map<String, Object>> getBRCCaseByAssetsSerialNumber(
			@PathVariable("assetsserialnumber") Long assetsserialnumber, Pageable page) {
		return dbBRCCase.getByAssetsSerialNumber(assetsserialnumber, page);
	}

	@PostMapping
	private Map<String, Object> saveBtAssets(@RequestBody Map<String, Object> req, @CurrentUser LoginUser user)
			throws BadRequestException, UnauthoriseException {
		return assetsService.saveObject(req, user);
	}

	@PutMapping("{id}")
	private Map<String, Object> updateAssets(@RequestBody Map<String, Object> reqq, @PathVariable("id") long id,
			@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException {
		System.out.println("In");
		return assetsService.updateObject(reqq, user);
	}

	@GetMapping("/advancesearch")
	private List<Map<String, Object>> advanceSearch(@RequestParam Map<String, Object> searchKey,
			@CurrentUser LoginUser user) throws BadRequestException, UnauthoriseException {
		return assetsService.advanceSearch(searchKey, user);
	}

	@GetMapping("forlist")
	private List<Map<String, Object>> forList(@RequestParam("search") String search, @CurrentUser LoginUser user,Pageable pageable)
			throws BadRequestException, UnauthoriseException {
		return assetsService.searchForLists(search,pageable);
	}

	@GetMapping("status/forlist")
	public List<Map<String, Object>> getCaseStatusList() {
		return dbStatus.getallStatus();
	}

	@GetMapping("forlist/forinstallation/small")
	public Page<Map<String, Object>> getListForInstallation(@RequestParam("searchkey") String searchKey,
			Pageable page) {
		return dbAssets.getAssetsForListForInstallation("%" + searchKey + "%", page);
	}

	// Template for AMC Update
	@GetMapping("/templateAMCUpdate")
	public ResponseEntity<ByteArrayResource> getTemplateAMC(HttpServletResponse httpResponse) throws IOException {
		HttpHeaders header = new HttpHeaders();
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("AMCUpdate");
		Object[][] bookData = { { "Asset_Serial_No.", "Asset_Part_Code", "AMC_PO_No", "AMC_PO_Date (YYYY-MM-DD)",
				"AMC_Start_Date (YYYY-MM-DD)", "AMC_Period (months)" }, };

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
		FileOutputStream fileOut = new FileOutputStream("BtAMCUpdateTemplate.xlsx");
		return new ResponseEntity<>(new ByteArrayResource(bytest.toByteArray()), header, HttpStatus.OK);
	}

	
	@PutMapping("amcUpdate")
	public ResponseEntity<?> amcBulkUdpated(@RequestParam(value = "file") MultipartFile file, @CurrentUser LoginUser user,
			HttpServletResponse response) throws IOException {
			if(!dbProfile.hasObjectAccess(user.getUsername(), BtAssetsAmcdetails.class, UserProfileRepository.CREATE)) {
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
