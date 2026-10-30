
package com.pratikshat.exicom.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.AccountRecentVisit;
import com.pratikshat.exicom.entity.AddressInformation;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.AccountCreate;
import com.pratikshat.exicom.model.request.Contacts;
import com.pratikshat.exicom.model.response.AccountsForList;
import com.pratikshat.exicom.repository.AccountRecentVisistRepository;
import com.pratikshat.exicom.repository.AccountsRepository;
import com.pratikshat.exicom.repository.AddressInformationRepository;
import com.pratikshat.exicom.repository.BtCaseRepository;
import com.pratikshat.exicom.repository.BtSiteDetailsRepository;

@RestController
@RequestMapping("bt/api/accounts")
public class BtAccountsController {
	@Autowired
	AccountsRepository dbAccounts;
	@Autowired
	private AccountRecentVisistRepository dbAccountVisit;

	@Autowired
	private AddressInformationRepository dbAddressInfo;

	@Autowired
	private BtSiteDetailsRepository dbSiteDetails;

	@Autowired
	private BtCaseRepository dbCase;
	@Autowired
	private com.pratikshat.exicom.repository.UserProfileRepository dbUserProfile;
	
	int businessDomainId=1;

	@GetMapping
	public Page<AccountCreate> getAccounts(Pageable page,
			@com.pratikshat.exicom.annotation.CurrentUser com.pratikshat.exicom.entity.LoginUser user,@RequestParam(value="filtername",required=false)String filterName) {

		if (dbUserProfile.hasObjectAccess(user.getUsername(), "Accounts", "viewAllRights")
				|| dbUserProfile.hasObjectAccess(user.getUsername(), "Accounts", "viewRights")) {
			if(filterName!=null&&filterName.equalsIgnoreCase("recentvisit"))
				 return dbAccounts.getRecentVisist(user.getUsername(), page, businessDomainId);
			if(filterName!=null&&filterName.equalsIgnoreCase("myaccount"))
				return dbAccounts.getMyAccount(user.getUsername(), page, businessDomainId);
			
			return dbAccounts.getAll(page, businessDomainId);
		}

		return Page.empty();
	}

	@GetMapping("forlist")
	public List<AccountsForList> getAccountsForList() {
		return dbAccounts.getAccountForList(businessDomainId);
	}

	@GetMapping("recentvisit")
	public Page<AccountCreate> getReceintVisist(@CurrentUser LoginUser user, Pageable page) {

		if (dbUserProfile.hasObjectAccess(user.getUsername(), "Accounts", "viewRights")
				|| dbUserProfile.hasObjectAccess(user.getUsername(), "Accounts", "viewAllRights")) {

			return dbAccounts.getRecentVisist(user.getUsername(), page, businessDomainId);
		}
		return Page.empty();
	}

	@GetMapping("myaccount")
	public Page<AccountCreate> getMyAccount(@CurrentUser LoginUser user, Pageable page) {

		return dbAccounts.getMyAccount(user.getUsername(), page, businessDomainId);
	}

	@GetMapping("{sapnumber}")
	public AccountCreate getAccounts(@CurrentUser LoginUser user, @PathVariable("sapnumber") String sapNumber,
			HttpServletResponse response) throws IOException {
		if (dbUserProfile.hasObjectAccess(user.getUsername(), "Accounts", "viewRights")
				|| dbUserProfile.hasObjectAccess(user.getUsername(), "Accounts", "viewAllRights")) {
			try {

				dbAccountVisit.save(new AccountRecentVisit(sapNumber, user.getUsername(), new Date()));
				return dbAccounts.getBySapCustomerNumber(sapNumber);
			} catch (Exception ex) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
				return null;
			}
		}
		return new com.pratikshat.exicom.model.request.AccountCreate();
	}

	@GetMapping("{sapnumber}/contacts")
	public List<Contacts> getAccountsContacts(@PathVariable("sapnumber") String sapNumber) {

		return dbAccounts.getAccountsContacts(sapNumber);
	}

	@GetMapping("{sapnumber}/sites")
	public List<Map<String, Object>> getSiteByAccountSapNumber(
			@PathVariable("sapnumber") String sapnumber) {
		return dbSiteDetails.getSiteDetailsByAccountSapNumber(sapnumber);
	}

	@GetMapping("{sapnumber}/assets/forlist")
	public List<com.pratikshat.exicom.model.response.AssetsForList> getAssetsListBy(
			@PathVariable("sapnumber") String sapNumber) {
		return dbAccounts.getAssetsForListBySapNumber(sapNumber);
	}

	@PostMapping
	public AccountCreate postAccount(@CurrentUser LoginUser user, @Valid @RequestBody AccountCreate accountDetails,
			HttpServletResponse response) throws Exception,IOException {
		accountDetails.setSampAccountNumber(accountDetails.getSampAccountNumber().trim());
		
		if (!dbUserProfile.hasObjectAccess(user.getUsername(), "Accounts", "createRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You don't have permission");
			return null;
		}
		
		try {
			if (dbAccounts.countBySapCustomerNumber(accountDetails.getSampAccountNumber()) > 0) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Account already exists");
				return null;
				
			}
			dbAccounts.createAccount(accountDetails, new EmployeeDetails(user.getUsername()),businessDomainId);
			return dbAccounts.getBySapCustomerNumber(accountDetails.getSampAccountNumber());
		}catch(BadRequestException ex) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		}catch(UnauthoriseException ex) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		}
		
	}

	@PutMapping("{sapnumber}")
	public AccountCreate updateAccountDetails(@CurrentUser LoginUser user,
			@Valid @RequestBody AccountCreate accountDetails, @PathVariable("sapnumber") String sapnumber,
			HttpServletResponse response) throws IOException {
		if (!dbUserProfile.hasObjectAccess(user.getUsername(), "Accounts", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You don't have permission");
			return null;
		}

		try {
			if (dbAccounts.countBySapCustomerNumber(sapnumber) == 0) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Account not exists");
				return null;
			}

			if (!sapnumber.equalsIgnoreCase(accountDetails.getSampAccountNumber())) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Account not exists");
				return null;
			}
			
			dbAccounts.updateAccounsDetails(accountDetails, user, null, null, businessDomainId);
			return dbAccounts.getBySapCustomerNumber(sapnumber);
		} catch(BadRequestException ex) {
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

	@PutMapping("address/{id}")
	public AddressInformation updateAddressInfo(@CurrentUser LoginUser user, @PathVariable("id") long addressInfoId,
			@RequestBody AddressInformation addressInfo, HttpServletResponse response)
			throws IOException, BadRequestException {
		if (!dbUserProfile.hasObjectAccess(user.getUsername(), "Accounts", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You don't have permission");
			return null;
		}

		try {
			
			if (dbAddressInfo.countByAddressInfoId(addressInfoId) == 0) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Address Information not exists");
				return null;
			}
			if (addressInfoId != addressInfo.getAddressInfoId()) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Address Information not exists");
				return null;
			}
			
			dbAddressInfo.save(addressInfo);
			return dbAddressInfo.findByAddressInfoId(addressInfoId);
		} catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
		
	}

	@PostMapping("address/shipping/{sapnumber}")
	public AddressInformation addShippingAddressToAccount(@CurrentUser LoginUser user,
			@PathVariable("sapnumber") String sapNumber, @RequestBody AddressInformation addressInfo,
			HttpServletResponse response) throws IOException {
		if (!dbUserProfile.hasObjectAccess(user.getUsername(), "Accounts", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You don't have permission");
			return null;
		}
		
		try {
			dbAddressInfo.save(addressInfo);
			com.pratikshat.exicom.entity.Accounts _account = dbAccounts.findBySapCustomerNumber(sapNumber);
			_account.setShipingInfo(addressInfo);
			dbAccounts.save(_account);
			dbAccounts.flush();

			return dbAddressInfo.findByAddressInfoId(addressInfo.getAddressInfoId());
		}catch(Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
		
	}

	@PostMapping("address/billing/{sapnumber}")
	public AddressInformation addBillingAddressToAccount(@CurrentUser LoginUser user,
			@PathVariable("sapnumber") String sapNumber, @RequestBody AddressInformation addressInfo,
			HttpServletResponse response) throws IOException {
		if (!dbUserProfile.hasObjectAccess(user.getUsername(), "Accounts", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You don't have permission");
			return null;
		}
		
		try {
			dbAddressInfo.save(addressInfo);
			com.pratikshat.exicom.entity.Accounts _account = dbAccounts.findBySapCustomerNumber(sapNumber);
			_account.setBillingInfo(addressInfo);
			dbAccounts.save(_account);
			return dbAddressInfo.findByAddressInfoId(addressInfo.getAddressInfoId());	
		}catch(Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;	
		}
		
	}

	@GetMapping("case/{accountsapnumber}")
	public Page<Map<String, Object>> getCaseBySapNumber(
			@PathVariable("accountsapnumber") String sapnumber, Pageable page) {
		return dbCase.getByAccountSapNumber(sapnumber, page);
	}

	@GetMapping("{sapnumber}/contacts/forlist")
	public List<com.pratikshat.exicom.model.response.ContactForList> getContactForListByAcctoun(
			@PathVariable("sapnumber") String sapnumber) {
		return dbAccounts.getContactForListByAccountId(sapnumber);
	}
	
	@GetMapping("{sapnumber}/contacts/notuser/forlist")
	public List<com.pratikshat.exicom.model.response.ContactForList> getContactForListNotUserByAccount(
			@PathVariable("sapnumber") String sapnumber) {
		return dbAccounts.getContactNotUserForListByAccountId(sapnumber);
	}
	

	// created by shyamu
	@GetMapping("{sapnumber}/contacts/related")
	public Page<com.pratikshat.exicom.model.request.Contacts> getContactByAccountId(
			@PathVariable("sapnumber") String sapnumber, Pageable page) {
		return dbAccounts.getContactByAccountId(sapnumber, page);
	}

	@GetMapping("{sapnumber}/sites/related")
	public Page<Map<String, Object>> getSiteByAccountId(
			@PathVariable("sapnumber") String sapnumber, Pageable page) {
		return dbSiteDetails.getSiteDetailsByAccountId(sapnumber, page);
	}
	// close by shyamu
	
	
	@GetMapping("address/{sapnumber}")
	public AccountCreate getAccountAddress(@CurrentUser LoginUser user, @PathVariable("sapnumber") String sapNumber,
			HttpServletResponse response) throws IOException {
			try {
				return dbAccounts.getBySapCustomerNumber(sapNumber);
			} catch (Exception ex) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
				return null;
			}
	}
	
	
	@GetMapping("advanceSearch")
	public List<AccountCreate> advanceSearch(@CurrentUser LoginUser user,@RequestParam Map<String, Object> searchParameters,
			HttpServletResponse response) throws IOException {
			try {
				return dbAccounts.advanceSearch(searchParameters,user, null, businessDomainId);
			} catch (Exception ex) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
				return null;
			}
	}

	@GetMapping("{sapnumber}/contacts/forlist/sml/{contact}")
	public List<Contacts> getAccountsContactsforlist(@PathVariable("sapnumber") String sapNumber,@PathVariable("contact")String contact,Pageable page) {
 
		return dbAccounts.getBtAccountsContacts(sapNumber,"%"+contact+"%", page).getContent();
	}
}
