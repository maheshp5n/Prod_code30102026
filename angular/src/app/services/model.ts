import { NumberValueAccessor } from '@angular/forms';
import { ExecSyncOptionsWithStringEncoding } from 'child_process';
import { btSolutionImparted, BtSolutionImparted } from '../battery/bt_model';

export class Login {
  userEmailId: string;
  userPassword: string;
}

export class BusinessDomain {
  businessDomainId: number;
  businessDomainName: string;
  synced: boolean;
}
export class AccountsType {
  accountTypeId: number;
  accountTypeName: string;
  synced: boolean;
}
export class CountryDetails {
  conuntryId: string;
  countryName: string;
  synced: boolean;

}
export class StateProvinceDetails {
  stateId: number;
  stateName: string;
  country: CountryDetails;
  synced: string;
}
export class AccountsForList {
  sapAccountNumber: string = null;
  accountName: string;
  primaryAccountSampNumber: string;
}
export class AccountCreate {
  accountName: string;
  accountOwner: string;
  accountOwnerFirstName: string;
  accountOwnerMiddleName: string;
  accountOwnerLastName: string;

  parentAccountSapNumber: string;
  parentAccountName: string;

  parentCustomerId: number;
  parentCustomerName: string;

  accountTypeName: string;
  accountTypeId: number = 0;

  phone: string;
  description: string = '';

  accountCatagoryName: string;
  accountCategoryId: number = 1;

  emailid: string;
  sampAccountNumber: string;

  billingAddress: string;
  billingStreet: string;
  billingCity: string;
  //String billingState;
  billingPincode: string = '';
  //String billingCountry;

  shippingAddress: string;
  shippingStreet: string;
  shippingCity: string;
  //String shippingState;
  shippingPincode: string = '';
  //String shippingCountry;
  shippingAsBilling: boolean;

  billingStateId: number = 0;
  billingSteateName: string;
  shippingStateId: number = 0;
  shippingStateName: string;
  billingCountryId: string = "In";
  billingcountryName: string;
  shippingCountryId: string = '0';
  shippingCountryName: string;

  createdById: string;
  createdByFirstName: string;
  createdByMiddleName: string;
  createdByLastName: string;
  createdDate: Date;
  modifiyedById: string;
  modifiyedFirstName: string;
  modifiyedMiddleName: string;
  modifiyedLastName: string;
  modifiyedDate: Date;
  catagory: string;
  billingCountry: string;
}


export class Sort {
  sorted: boolean;
  unsorted: boolean;
  empty: boolean;
}
export class Pageable {
  sort: Sort;
  offset: number;
  pageSize: number;
  pageNumber: number;
  unpaged: boolean;
  paged: boolean;
}
export class Page<T> {
  content: Array<T>;
  pageable: Pageable;
  last: boolean;
  totalPages: number;
  totalElements: number;
  size: number;
  number: number;
  sort: Sort;
  numberOfElements: number;
  first: boolean;
  empty: boolean;
}
export class Contacts {
  contactid: number;
  title: string;
  contactOwnerFirstName: string;
  contactOwnerMiddleName: string;
  contactOwnerLastName: string;
  contactOwnerEmailId: string;
  salutation: string = '';
  contactDepartment: string;
  contactFirstName: string;
  contactLastName: string;
  contactMiddleName: string;
  contactSuffix: string;
  accountSAPNumber: string;
  accountName: string;
  contactEmailId: string;
  contactFaxNumber: string;
  contactPhoneNumber: string = '';
  contactMobileNumber: string = '';
  contactMallingAddressId: number;
  contactMallingAddress: string;
  contactMallingStreet: string;
  contactMallingCity: string;
  contactMallingStateName: string;
  contactMallingPincode: string;
  contactMallingCountry: string = '';
  contactMallingCountryName: string = '';
  contactMallingCountryId: string = '';
  contactMallingState: string = '';
  contactMallingStateId: string = '';
  gradeName: string = null;
  legalEntityId: number = null;
  leagalEntityName: string = '';
  contactDesignationId: number = 0;
  contactDesignationName: string = '';
  employeeCode: string;
  contactTypeId: number = 0;
  contactTypeName: string = '';
  contactCreatedDate;
  contactModifyedDate;
  businessPlaceId: number = 0;
  contactCreatedByEmailId: string;
  contactModifyedByEmailId: string;
  contactCreatedByFirstName: string;
  contactCreatedByMiddleName: string;
  contactCreatedByLastName: string;
  contactModifyedByFirstName: string;
  contactModifyedByMiddleName: string;
  contactModifyedByLastName: string;
  

}
export class ContactType {
  typeId: number;
  typeName: string;
}
export class ContactDesignation {
  designationId: number;
  designationName: string;
}


// export class SitesForList {
//     siteIid: string;
//     exchange: string;
//     siteName: string;
//     phone: string;
//     Account: string;
//     circule: string;
//     zone: string;
//     SSA: string;
//     cluster: string;
//     DIS: string;
//     site: string;

// }

// export class LigalEntityModel {
//     legalentityId: string;
//     legalEntityName: string;
//     legalEntityType: string;
//     legalEntiytFixedLayou: string;
//     ownerAccountId: string;
//     ownerAccountFirstName: string;
//     ownerAccountLastName: string;
// }
// export class BoardingLoading {
//     boardingName: string;
//     boardingClassACity: string;
//     boardingClassBCity: string;
//     boardingClassCCity: string;
//     boardingAmountCityA: string;
//     boardingAmountCityB: string;
//     boardingAmountCityC: string;
//     twoWheelerChargesParKilo: string;
//     fourWheelChargesParkilo: string;
// }

export class AddressInformation {
  addressInfoId: string;
  address: string;
  street: string;
  city: string;
  state: string;
  pincode: string;
  latitude: string;
  longitude: string;
}

export class Zonedetails {
  zoneName: string;
  createdBy: EmployeeDetails;
  createdDate: string;
  modifyedBy: EmployeeDetails;
  modifyedDate: string;
}

export class EmployeeDetails {
  emailId: string;
  firstName: string;
  lastName: string;
  emptype: string;
  fcmToken: string;
  deviceId: string;
  fullName?: string;
}


export class WarehousedetailsRequest {
  warehouseId: number;
  warehouseName: string;
  warehouseEmail: string;
  legalEntityId: number = 0;
  warehousePlant: string;
  legalEntityName: string;

  createdByFirstName: string;
  createdByLastName: string;
  createdDateTime: string;
  modifiedByFirstName: string;
  modifiedByLastName: string;
  modifiedDate: string;
  isLegalEntitySelected: string = 'fa fa-search';

}

export class WarehousedetailsResponse {
  warehouseId: number;
  warehouseName: string;
  warehouseEmail: string;
  legalEntityId: number;
  legalEntityName: string;
  warehousePlant: string;

  createdByFirstName: string;
  createdByLastName: string;
  createdByEmailId: string;
  createdDateTime: string;
  modifiedByFirstName: string;
  modifiedByLastName: string;
  modifiedByEmailId: string;
  modifiedDate: string;
  isLegalEntitySelected: string = 'fa fa-search';
}
export class Product {
  partCode: string;
  productDescription: string;
  subProductFamilyId: number = 0;
  type: string = '';
  price: string = null;
  productFamilyId: number = 0;
}

export class ProductsForList {
  productDescription: string;
  productPartCode: string;
  bothvalues?:string='';
}

export class ProductFamily {
  id: number;
  name: string;
}
export class ProductSubFamily {
  productFamilySubProductId: number;
  productFailySubProductName: string;
  productFamilyId: number;
}

export class ProductsResponse {
  partCode: string;
  productDescription: string;
  productFamilyId: number;
  productFamilyName: string;
  productSubFamilyId: number;
  productSubFamilyName: string;
  productType: string;
  productPrice: string;
  createdByEmailId: string;
  createdByFirstName: string;
  createdByMiddleName: string;
  createdByLastName: string;
  createdDate: number;
  modifiedByEmailId: string;
  modifiedByFirstName: string;
  modifiedByMiddleName: string;
  modifiedByLastName: string;
  modifiedDate: number;
  checked: boolean;
}

export class RecentProducts {
  partCode: string;
  productType: string;
  productFamilyName: string;
  productSubFamilyName: string;
  productDescription: string;
}

export class CirclesRequest {
  circleName: string;
  countryCode: string = '';
  circleManagerBattryId: number = null;
  circleManagerPowerId: number = null;
  // circleManagerBattryName: string = '';
  // circleManagerPowerName: string = '';
  circleManagerEv: number = null;
  // circleManagerEvName: string = '';
  zoneName: string = '';
  legalEntity:number;
  // powerLegalEntityName:string;
  // powerLegalEntityId:number;
  // isBatterySelected: string = 'fa fa-search';
  // isPowerSelected: string = 'fa fa-search';
  // isEvSelected: string = 'fa fa-search';
  // circleManagerBattryFirstName: string = '';
  // circleManagerPowerFirstName: string = '';
  // circleManagerEvFirstName: string = '';
}
export class CirclesForList {
  circleName: string;
}
export class CircleZoneForList {
  circleName: string;
  zoneName: string;
}

export class CirclesResponse {
  circleName: string;
  zone: string;
  circleManagerBattryId: number = 0;
  circleManagerBattryFirstName: string = '';
  circleManagerBatteryMiddleName: string = '';
  circleManagerBattryLastName: string = '';
  circleManagerPowerId: number = 0;
  circleManagerPowerFirstName: string = '';
  circleManagerPowerMiddleName: string = '';
  circleManagerPowerLastName: string = '';
  circleManagerEvId: number = 0;
  circleManagerEvFirstName: string = '';
  circleManagerEvMiddleName: string = '';
  circleManagerEvLastName: string = '';
  accountOwnerId: string;
  accountOwnerFirstName: string;
  accountOwnerMiddleName: string;
  accountOwnerLastName: string;
  countryCode: string;
  countryName: string;
  createById: string;
  createByFirstName: string;
  createdByMiddleName: string;
  createByLastName: string;
  createdDate: string;
  modifiedById: string;
  modifiedByFirstName: string;
  modifiedByMiddleName: string;
  modifiedByLastName: string;
  lastModifiedDate: string;
  powerLegalEntityName:string;
  powerLegalEntityId:number;
  // isBatterySelected: string = 'fa fa-search';
  // isPowerSelected: string = 'fa fa-search';
  // isEvSelected: string = 'fa fa-search';
}
// export class cityDetailsReq {
//     catagoryName: string;
//     cityName: string;
// }

// export class CityCatagory {
//     cityCatagoryName: string;
//     synced: boolean;
// }

// export class cityDetailsResponse {
//     cityName: string;
//     ownerId: string;
//     ownerFirstName: string;
//     ownerLastName: string;
//     cityCatagoryName: string;
//     createdById: string;
//     createdByFirstName: string;
//     createdByLastName: string;
//     createdDate: string;
//     modifiedById: string;
//     modifiedByFirstName: string;
//     modifiedByLastName: string;
//     modifiedDate: string;
// }
export class AssetsStatus {
  assetsStatusId: number;
  assetsStatusName: string;
  synced: boolean;
}
export class AssetsResponse {
  serialNumber: string;
  productPartCode: string;
  productDescription: string;
  productSubProductId: string;
  productSubProductName: string;
  productFamailyId: string;
  productFamailyName: string;
  accountSapNumber: string;
  accountName: string;
  plantCode: string = '0';
  plantName: string;
  siteId: number;
  siteName: string;
  siteManualId: string;
  primaryAccountSapNumber: string;
  primaryAccountName: string;
  assetsStatusName: string;
  assetsStatusId: number = 0;
  remarks: string = '';
  purchaseOrderNumber: string;
  purchaseOrderDate: string;
  sapDeliveryDocumentNumber: string;
  sapMaterialDocumentNumber: string;
  purchaseDate: string;
  installDate: string;
  warrantyFromDateOfDeliveryInMonth: any;
  warrantyFromDateOfInstallationInMonth: any;
  warrantyExpiryDate: string;
  dateOfDeliveryExpiryDate: string;
  installExpiryDate: string;
  amcPurchaseOrderNumber: string;
  amcPurchaseOrderDate: string;
  amcStartDate: string;
  amcExpiryDate: string;
  amcPeriod: any;
  createdByEmailId: string;
  createdDate: Date;
  modifyedByEmailId: string;
  modifyedDate: Date;
  createdByFirstName: string;
  createdByLastName: string;
  createdByMiddleName: string;
  modifyedByFirstName: string;
  modifyedByLastName: string;
  modifyedByMiddleName: string;
  circleName: string;
  amcwarrantyExist: boolean;
  warrantyExist: boolean;
  warranty: string;
}
export class AssetsRequest {
  serialNumber: string;
  productPardCode: string;
  accountSapNumber: string;
  plantCode: string;
  siteId: number;
  primaryAccountSapNumber: string;
  assetsStatusId: number;
  remarks: string;
  purchaseOrderNumber: string;
  purchaseOrderDate: string;
  sapDeliveryDocumentNumber: string;
  sapMaterialDocumentNumber: string;
  purchaseDate: string;
  installDate: string;
  warrantyFromDateOfDeliveryInMonth: number;
  warrantyFromDateOfInstallationInMonth: number;
  amcPurchaseOrderNumber: string;
  amcPurchaseOrderDate: string;
  amcStartDate: string;
  amcExpiryDate: string;
  amcPeriod: number;

}

export class PlantDetails {
  plantCode: string;
  plantName: string;
}

export class Notifications {
  notificationId: number;
  nofificationDate: string;
  userEmailId: string;
  notificationMessage: string;
  notificationActionUrl: string;
  notificationRead: string;
}

// export class SiteDetailsForList {
//     siteId: string;
//     siteName: string;
// }
// export class SiteDetailsReq {
//     siteId: number;
//     primaryAccountSapNumber: string;
//     ownerEmailId: string;
//     siteName: string;
//     accountSapNumber: string;
//     serviceCenterid: string;
//     infraId: string;
//     serviceResourceId: string;
//     circleName: string;
//     zoneName: string;
//     clusterDistrict: string;
//     siteDescription: string;
//     siteAddress: string;
// }
// export class SiteDetailsResponse {
//     siteId: number;
//     legalentityId: string;
//     primaryAccountSapNumber: string = '0';
//     primaryAccountName: string;
//     ownerEmailId: string;
//     ownerFirstName: string;
//     ownerLastName: string;
//     siteName: string;
//     accountSapNumber: string = '0';
//     accountName: string;
//     serviceCenterId: string = '0';
//     serviceCenterName: string;
//     infraId: string;
//     serviceResourceId: string = '0';
//     serviceResourceName: string;
//     circleId: string;
//     circleName: string = '0';
//     zoneId: string;
//     zoneName: string = '0';
//     clusterDistrict: string;
//     siteDescription: string;
//     siteAddress: string;
//     createByEmailId: string;
//     createByFirstName: string;
//     createByLastName: string;
//     createdDate: string;
//     modifiedByEmailId: string;
//     modifiedByFirstName: string;
//     modifiedByLastName: string;
//     modifiedDate: string;
// }
// export class ServiceResourseForList {
//     serviceResourceId: number;
//     name: string;
// }
// export class ServiceCenterForList {
//     legalEntityId: number;
//     legalEntityName: string;
// }

// export class UserForList {
//     userEmailId: string;
//     userFirstName: string;
//     userLastName: string;
// }

export class ChangePassword {
  newPassword: string;
  oldPassword: string;
  confirmPassword: string;
}

export class CustomerForList {
  customerId: number;
  customerName: string;
}

export class Opportunity {
  // customerId: string;
  // customerName: string;
  opportunityName: string;
  closeDate: string;
  accountName: string = null;
  accountSAPNumber: number;
  probability: string;
  case: string;
  siteName: string;
  amount: string;
  expectedRevenue: string;
  oppertunityForecastCategoryId: number;
  oppertunityForecastCategoryName: string = '';
  oppertunityTypeId: number;
  oppertunityTypeName: string = '';
  oppertunityStageId: number;
  oppertunityStageName: string = '';
}

export class OppertunityRequest {
  oppertunityId: number;
  oppertunityNumber: number = 0;
  caseId: number = null;
  caseNumber: string;
  caseManualId: string;
  oppertunityStageId: number = 1;
  oppertunityTypeId: number = 7;
  forcastCategoryId: number = 2;
  accountId: string;
  probability: string = '75';
  ammount: string;
  closeDate: Date;
  accountName: string;
  oppertunityForecastCategoryName: string = '';
  oppertunityTypeName: string = '';
  oppertunityStageName: string = '';
  expectedRevenue: string = '';
  // createdByFirstName: string = '';
  // createdByMiddleName: string = '';
  // createByLastName: string = '';
  // createdDate: string = '';
  // modifiedByFirstName: string = '';
  // modifiedByMiddleName: string = '';
  // modifiedByLastName: string = '';
  // modifiedDate: string = '';
  type: string;
  siteName: string;
}

export class OppertunityResponse {
  oppertunityId: number;
  oppertunityNumber: number;
  oppertunityOwnerFirtName: string;
  oppertunityOwnerMiddleName: string;
  oppertunityOwnerlastName: string;
  oppertunityOwnerEmailId: number;
  accountName: string;
  accountId: string;
  caseId: number = null;
  caseNumber: string;
  siteName: string;
  siteId: number;
  forcastCategory: string;
  forcastCategoryId: number;
  stage: string;
  stageId: number;
  type: string;
  typeId: number;
  closeDate: Date;
  probability: string;
  ammount: string;
  expectedRevenue: string;
  createdByFirstName: string;
  createdByMiddleName: string;
  createByLastName: string;
  createdByEmailId: number;
  createdDate: string;
  modifiedByFirstName: string;
  modifiedByMiddleName: string;
  modifiedByLastName: string;
  modifiedByEmailId: number;
  modifiedDate: string;
  caseManualId: string;
}

export class CaseForList {
  caseId: number;
  caseManualId: string;
}

export class OppertunityStage {
  oppertunityStageId: number;
  oppertunityStageName: string;
}

export class OppertunityType {
  oppertunityTypeId: number;
  oppertunityTypeName: string;
}

export class OppertunityForecastCategory {
  oppertunityForecastCategoryId: number;
  oppertunityForecastCategoryName: string;
}

export class Quote {
  // quoteName: string;
  // accountName: string;
  // quoteNumber: string;
  // opportunityName: string;
  // contactName: string;

  quoteNumber: string;
  quoteApproved: string;
  quoteName: string;
  approvalCheck: string;
  opportunityname: string;
  quoteValidityDays: string;
  accountName: string;
  expirationDate: string;
  syncing: string;
  status: string = '';
  submittedDate: string;
  approvedbyStore: string;
  approvedbyCommercial: string;
  quoteSentDate: string;
  remark: string;

  sgst: string;
  cgst: string;
  igst: string;
  subTotal: string;
  totalAmount: string;

  copyBillingDetails: string;

  billToName: string;
  billTo: string;
  billToCountry: string = '';
  billToStreet: string;
  billToCity: string;
  billToZip: string;
  billToState: string = '';

  shipToName: string;
  shipTo: string;
  shipToCountry: string = '';
  shipToStreet: string;
  shipToCity: string;
  shipToZip: string;
  shipToState: string = '';

  othersTerms: string;
  toEmails: string;
  ccEmails: string;
}

export class ProductLineItemsResponse {
  serialNumber: string;
  productItem: string;
  partCode: string;
  productState: string;
  location: string;
  returnOrderLineItem: string;
  status: string;
  caseNumber: string;
  productRequestLineItem: string;
  repairRemarks: string;
  productTransfer: string;
  scrapDate: string;
  remarks: string;
  customerInventory: string;
  reason: string;
  unique: string;
  markForTransfer: string;
  receivedDate: string;
}
export class ProductLineItemsRequest {
  serialNumber: string;
  productItem: string;
  partCode: string = '';
  productState: string = '';
  productDescription: string = '';
  productType: string = '';
  location: string = '';
  returnOrderLineItem: string = '';
  status: string = '';
  caseNumber: string;
  productRequestLineItem: string;
  repairRemarks: string;
  productTransfer: string;
  scrapDate: string;
  remarks: string;
  customerInventory: string;
  reason: string;
  unique: string;
  markForTransfer: string;
  receivedDate: string;
}

export class ProductLineItemProductState {
  productStateId: number;
  productStateName: string = '';
}

export class ProductLineItemStatus {
  statusId: number;
  statusName: string = '';
}

export class opportunitycaseRequest {
  opportunitycasesName: string = '';
  opportunity: string = '';
  caseNumber: number;
  caseId: number;
  createdByFirstName: string = '';
  createByLastName: string = '';
  createdByEmailId: string = '';
  createdDate: string = '';

  modifiedByFirstName: string = '';
  modifiedByLastName: string = '';
  modifiedByEmailId: string = '';
  modifiedDate: string = '';
}

export class opportunitycaseResponse {
  opportunity: string = '';
  case: string = '';
  opportunitycaseName: string = '';
  createdByFirstName: string = '';
  createByLastName: string = '';
  createdByEmailId: string = '';
  createdDate: string = '';
  modifiedByFirstName: string = '';
  modifiedByLastName: string = '';
  modifiedByEmailId: string = '';
  modifiedDate: string = '';
}

// totalAmount: number;
// contactId: number = null;
// documentDate: string = '';
// tourStartDate: string;
// tourStartTime: string = '';
// tourEndDate: string;
// tourEndTime: string = '';
// statusId: number = 0;
// isPost: boolean;
// isExpenseSynced: boolean;
// advanceAmount: number;
// visibleToFinance: string;
// claimNumber: string;
// employeeCode: string;
// employeeDesignation: string;
// employee: string;
// totalTourDay: string;
// totalBoardingDays: string;
// eligibleDays: string;
// needsMDApproval: string;
// totalTourAmount: string;
// totalLodgingAmount: string;
// totalExpense: string;
// totalBoardingAmount: string;
// totalTourAmountCompanyPaid: string;
// totalBPAmount: string;
// totalLodgingCompanyPaid: string;
// totalLocalConveyance: string;
// totalCompanyPaid: string;
// totalOtherCharges: string;
// netAmountToPay: string;
// contactFirstName: string;
// contactLastName: string;

export class ExpenseManagementRequest {
  businessDomainID: number = 0;
  businessDomainId: number = 0;
  divCostCenters: number = 0;
  expenseManagementId: number;
  expensePurposeId: number = 0;
  expenseTypeId: number = 0;
  costCenterId: number = 0;
  purposeOfVisit: string = '';
  remark: string;
  narration: string;
  businessAreaId: number = 0;
  businessPlaceId: number = 0;
}

export class ExpenseManagementResponse {
  totalAmount: number;
  businessDomainID: number = 0;
  businessDomainId: number = 0;
  // next: boolean = false;
  divCostCenters: number = 0;
  expenseManagementId: number = 0;
  expenseManagementManualId: string;
  contactId: number = null;
  employeeFirstName: string;
  employeeLastName: string;
  employeeFullName: string;
  contactFirstName: string;
  contactMiddleName: string;
  contactLastName: string;
  employeeCode: string;
  document: string;
  expensePurposeId: number = 0;
  expensePurposeName: string;
  expenseTypeId: number = 0;
  expenseTypeName: string;
  tourStart: string;
  tourStartTime: string;
  tourEnd: string;
  tourEndTime: string;
  costCenterId: number = 0;
  costCenterName: string;
  purposeOfVisit: string;
  statusId: number = 0;
  statusName: string;
  remark: string;
  narration: string;
  businessAreaId: number = 0;
  businessAreaName: string;
  businessPlaceId: number = 0;
  businessPlaceName: string;
  isPostL: boolean;
  isExpenseSynced: boolean;
  advanceAmount: number = 0;
  visibleToFinance: string;
  createdByEmailId: string;
  createdByFirstName: string;
  createdByLastName: string;
  created: string;
  modifiedByEmailId: string;
  modifiedByFirstName: string;
  modifiedByLastName: string;
  modified: string;
  ownerEmailId: string;
  ownerFirstName: string;
  ownerMiddleName: string;
  ownerLastName: string;
  claimNumber: string;
  employeeDesignation: string;
  employee: string;
  documentDate: string;
  status: string;
  post: boolean;
  totalTourDay: string;
  totalBoardingDays: string;
  eligibleDays: string;
  needsMDApproval: string;

  totalTourAmount: string;
  totalLodgingAmount: string;
  totalExpense: string;
  totalBoardingAmount: string;
  totalTourAmountCompanyPaid: string;
  totalBPAmount: string;
  totalLodgingCompanyPaid: string;
  totalLocalConveyance: string;
  totalCompanyPaid: string;
  totalOtherCharges: string;
  netAmountToPay: string;
  visibletoFinance: string;
  tourStartDate: string;
  tourEndDate: string;
  isPost: boolean;
  createdDate: string;
  contactEmployeeCode: string;

  totalTourDays: string;
  eligibleTourDays: string;
  totalBpAmount: string;
  totalLocalConveyanceAmount: string;
  totalOtherChargesAmount: string;
  createdByMiddleName: string;
  modifiedByMiddleName: string;
  modifiedDate: string;
  totalLodgingDays: string;
  gradeName: string;
  postPark:boolean;

}

export class ExpenseCreate {
  claimNumber: string;
  documentDate: string;
  employee: string;
  expenseTypeId: string;
  expensePurposeId: string;
  tourEndDate: string;
  tourEnd: Tour;
  tourStartDate: string;
  tourStart: Tour;
  purposeOfVisit: string;
  costCenterId: string;
  serviceAppointment: string;
  approvedbyFinance: string;
  approvedByHod: string;
  approvedByZoneManager: string;
  approvedByRegionalManager: string;
  approvedByAreaManager: string;
  sublittedDate: string;
  accountName: string;
  Date: string;
  accountOwner: string;
  phone: string;
}

export class Tour {
  date: string;
  time: string;
}

export class deliverychallan {
  infoName: string;
  requisition: string;
  receivedQuantity: string;
  issuedQuantity: string; // edit
  fromWarehouseName: string; // new
  status: string;
  username: string;
  toWarehouse: string; // edit
  sendShippingEmail: string;
  shipmentMode: string;
  shipmentDate: string;
  shippingName: string;
  shippingEmail: string;
  shippingNumber: string;
}

export class deliverychallandetailsRequest {
  infoName: string;
  requisition: string;
  receivedQuantity: string;
  issuedQuantity: string; // edit
  fromWarehouseName: string; // new
  status: string = '';
  username: string;
  toWarehouse: string; // edit
  sendShippingEmail: string;

  shipmentMode: string = '';
  shipmentDate: string;
  shippingName: string;
  shippingEmail: string;
  shippingNumber: string;
}

export class AssetsForList {
  assetsSerialNumber: string;
  partCode: string;
  productDescription: string;
}

export class CaseResponse {
  caseId: number = 0;
  caseManualId: string;
  caseRecordTypeId: number = 0;
  caseRecordTypeName: string;
  caseTypeId: number;
  verticalTypeId: number = 0;
  verticalTypeName: string;
  caseTypeName: string;
  caseReasonId: number = 0;
  caseReasonName: string;
  caseReasonVerticalName: string;
  caseReasonVerticalId: number = 0;
  assetsSerialNumber: string;
  assetsPartCode: string;
  assetsName: string;
  caseStatusName: string;
  caseStatusId: number;
  caseStageName: string;
  caseStageId: number;
  caseOriginName: string;
  caseOriginId: number = 0;
  casePriorityName: string;
  casePriorityId: number = 0;
  problemReported: Array<any>;
  contactPersonId: number = null;
  contactPersonFirstName: string;
  contactPersonMiddleName: string;
  contactPersonLastName: string;
  contactPersonEmailId: string;
  noOfSmr: string;
  remarks: string = '';
  iSmsSend:boolean;
  createdByEmailId: string;
  createdByFirstName: string;
  createdByMiddleName: string;
  createdByLastName: string;
  createdDate: Date;
  closedDate: Date;
  modifiedByEmailId: string;
  modifiedByFirstName: string;
  modifiedByMiddleName: string;
  modifiedByLastName: string;
  modifiedDate: Date;
  otherProblemReported: string;
  contactPersonContactNo: string;
  dateOfVisit: Date;
  dateTimeOpened: Date;
  dateTimeClosed: Date;
  rjil_TT01_No: string;
  pendingResponsibilityId: number = 1;
  pendingResponsibility: string;
  SLATime: string;
  podnumber: string;
  poddate: Date;
  ponumber: string;
  podate: Date;
  invoiceNumber: string;
  caseOwnerEmailId: string;
  invoiceDate: Date;
  vendorInvoiceNumber: string;
  vendorInvoiceDate: Date;
  callCenterRepFirstName: string;
  callCenterRepMiddleName: string;
  callCenterRepLastName: string;
  accountSapNumber: string;
  accountName: string;
  siteManualId: string;
  siteName: string;
  siteId: number;
  caseStageVerticalName: string;
  caseStageVerticalId: number = 0;
  serviceResourceId: number = 0;
  serviceResouceContactId: number = 0;
  serviceResourceName: string;
  serviceCenterId: number;
  serviceCenterName: string;
  missingAssets: string;
  isAccountSelected: any;
  caseOwner: string;
  caseOwnerFirstName: string;
  caseOwnerMiddleName: string;
  caseOwnerLastName: string;
  inputFromEng: string;
  internalRemark: string;
  isAssignByAssignmentRule: boolean = true;
  assetDescription: string;
  podShippingExpectedDate: any;
  podShippingMode: string;
  podShippingToName: string;
  podShippingTrackingNumber: string;
  vendorName: string;
  vendorSapCustomerNumber: string;
  siteCustomer:string;
  prcManualId:string;
  prcId:any;

}
export class CaseRequest {
  inputFromEng: string;
  internalRemark: string;
  missingAssets: string;
  caseId: number;
  siteId: number;
  caseManualId: string;
  isAssignByAssignmentRule: boolean;
  caseRecordTypeId: number = 0;
  caseRecordTypeName: string;
  caseTypeId: number;
  caseReasonId: number = 0;
  assetsSerialNumber: string;
  caseStageId: number = 0;
  caseStatusId: number = 0;
  caseOriginName: string;
  caseOriginId: number = 0;
  casePriorityName: string;
  casePriorityId: number;
  caseProblemReported: Array<ProblemReported>;
  contactPersonId: number;
  noOfSmr: string;
  remarks: string;
  ownerEmailId: string;
  legalEntityName: string;
  legalEntityID: string;
  accountSapNumber: string;
  accountName: string;
  pendingResponsibility: number;
  caseTypeName: string;
  caseReasonName: string;
  serviceCenterId: number = 0;
  serviceResourceId: number = 0;
  rjiltt01No: string;
  otherProblemReported: string;
  poNumber: string;
  poDate: Date
  iSmsSend:boolean;
}
export class CaseTypeForList {
  caseTypeId: any;
  caseTypeName: string;
}
export class CaseTypeRecord {
  businessDomainId: number;
  businessDomainName: string;
  synced: boolean;
}
export class CaseStatus {
  caseStatusName: string;
  id: number;
}
export class CaseStageDetails {
  caseStageName: string;
  id: number;
}
export class CaseOriginDetails {
  caseOrignName: string;
  caseOriginId: number;
  synced: boolean;

}
export class CasePriority {
  CasePriorityId: number;
  casePriorityName: string;
  synced: boolean;

}
export class CaseReasonForList {
  caseReasonId: any;
  caseReasonName: string;
}
export class SiteDetailsResponse {
  siteId: number;
  primaryAccountSapNumber: string;
  primaryAccountName: string;
  ownerEmailId: string;
  ownerFirstName: string;
  ownerMiddleName: string;
  ownerLastName: string;
  siteName: string;
  accountSapNumber: string = null;
  accountName: string;
  serviceCenterId: number;
  serviceCenterName: string;
  infraId: string;
  serviceResourceId: number;
  serviceResourceName: string;
  circleId: string;
  circleName: string;
  zoneId: string;
  zoneName: string;
  ssaId: number;
  ssaName: string;
  clusterDistrict: string;
  siteDescription: string;
  siteAddress: string;
  createByEmailId: string;
  createByFirstName: string;
  createdByMiddleName: string;
  createByLastName: string;
  createdDate: string;
  modifiedByEmailId: string;
  modifiedByFirstName: string;
  modifiedByMiddleName: string
  modifiedByLastName: string;
  modifiedDate: string;
  siteManualId: string;
  customer:string;
  indusId:string;
}

export class SiteDetailsForList {
  siteId: number;
  siteName: string;
  siteManualId: string;
}
export class SiteDetailsReq {
  siteId: number;
  ssaId: number;
  siteManualId: string;
  primaryAccountSapNumber: string;
  ownerEmailId: string;
  siteName: string;
  accountSapNumber: string;
  serviceCenterid: number;
  infraId: string;
  serviceResourceId: number;
  circleName: string;
  zoneName: string;
  clusterDistrict: string;
  siteDescription: string;
  siteAddress: string;
  customer:string;
  indusId:string;
}

export class ServiceCenterForList {
  legalEntityId: number;
  legalEntityName: string;
}
export class LegaLEntityType {
  typeId: number;
  typeName: string;
  synced: string;
}

export class UserForList {
  userEmailId: string = '';
  userFirstName: string = '';
  userMiddleName: string = '';
  userLastName: string = '';
  fullName?: string;
}
export class cityDetailsReq {
  catagoryName: string;
  cityName: string = null;
  cityCategoryId: number;
}
export class CityCatagory {
  cityCategoryId: number;
  cityCatagoryName: string;
  synced: boolean;
}

export class cityDetailsResponse {
  cityCategoryId: number = 0;
  cityName: string;
  ownerId: string;
  ownerFirstName: string;
  ownerLastName: string;
  cityCatagoryName: string;
  createdById: string;
  createdByFirstName: string;
  createdByMiddleName: string
  createdByLastName: string;
  createdDate: string;
  modifiedById: string;
  modifiedByFirstName: string;
  modifiedByMiddleName: string;
  modifiedByLastName: string;
  modifiedDate: string;
}


export class SitesForList {
  siteIid: string;
  exchange: string;
  siteName: string;
  phone: string;
  Account: string;
  circule: string;
  zone: string;
  SSA: string;
  cluster: string;
  DIS: string;
  site: string;
}

export class LigalEntityModel {
  legalentityId: string;
  legalEntityName: string;
  legalEntityType: string;
  legalEntiytFixedLayou: string;
  ownerAccountId: string;
  ownerAccountFirstName: string;
  ownerAccountLastName: string;
}
export class BoardingLoading {
  boardingName: string;
  boardigName: string;
  boardingClassACity: string;
  boardingClassBCity: string;
  boardingClassCCity: string;
  boardingAmountCityA: string;
  boardingAmountCityB: string;
  boardingAmountCityC: string;
  twoWheelerChargesParKilo: string;
  fourWheelChargesParkilo: string;
}
export class BoardingLoadingDetailsRequest {
  boardingName: string;
  boardingClassACity: number;
  boardingClassBCity: number;
  boardingClassCCity: number;
  boardingAmountCityA: number;
  boardingAmountCityB: number;
  boardingAmountCityC: number;
  twoWheelerChargesParKilo: number;
  fourWheelChargesParkilo: number;
  ownerFirstName: string;
  ownerLastName: string;
  ownerEmailId: string;
  createdByFirstName: string;
  createdByLastName: string;
  createdByEmailId: string;
  createdDate: string;
  modifiedByFirstName: string;
  modifiedByLastName: string;
  modifiedByEmailId: string;
  modifiedDate: string;
  twoWheelerMaxDistance: string
  fourWheelerMaxDistance: string;
  lodgingClassACitySelf: any;
  lodgingClassBCitySelf: any;
  lodgingClassCCitySelf: any;

}
export class BoardingLoadingDetailsResponse {
  boardingName: string;
  boardingClassACity: any;
  boardingClassBCity: any;
  boardingClassCCity: any;
  boardingAmountCityA: any;
  boardingAmountCityB: any;
  boardingAmountCityC: any;
  twoWheelerChargesParKilo: any;
  fourWheelChargesParkilo: any;
  ownerFirstName: string;
  ownerMiddleName: string;
  ownerLastName: string;
  ownerEmailId: string;
  createdByFirstName: string;
  createdByMiddleName: string;
  createdByLastName: string;
  createdByEmailId: string;
  createdDate: string;
  modifiedByFirstName: string;
  modifiedByMiddleName: string;
  modifiedByLastName: string;
  modifiedByEmailId: string;
  modifiedDate: string;
  twoWheelerMaxDistance: string
  fourWheelMaxDistance: string;
  amountToDisbursed: number;
  lodgingClassACitySelf: any;
  lodgingClassBCitySelf: any;
  lodgingClassCCitySelf: any;

}
export class LegalEntityResponse {
  // [x: string]: any;
  firstName: string;
  lastName: string;
  managerId:any;
  name: string;
  legalentityId: number;
  legalEntityName: string;
  legalEntityTypeName: string;
  legalEntiytFixedLayou: string;
  ownerAccountId:any;
  legalEntityTypeId: number = 0;
  legalEntityFixedPayout: number;
  ownerAccountFirstName: string;
  ownerAccountLastName: string;
  ownerAccountMiddleName: string
  createdByEmailId: string;
  createdByFirstName: string;
  createdByMiddleName: string;
  createdByLastName: string;
  createdDate: Date;
  modifiedByEmailid: string;
  modifiedByFirstName: string;
  modifiedByLastName: string;
  modifiedByMiddleName: string;
  modifiedDate: Date;
}
export class ProblemReported {
  id: number;
  problemName: string;
}
export class selected {
  selectValue: string;
}
export class MSLRequestStatus {
  statusId: any;
  statusName: string;
  synced: boolean;
}
export class MSLRequest {
  mslRequestId: any
  locationId: any
  statusId: any
  itemRequest: Array<MSLItemRequestRequest>
  remarks: string;
}
export class MSLItemRequestRequest {
  mslItmeRequestId: number;
  mslRequestId: number;
  productPartCode: string;
  mslQty: number;
  hslQty: number;
  remarks: number;
  locationProductId: number;

}
export class MSLRequestResponse {
  mslId: any;
  id : number;
  manualId: string;
  mslRequestOwnerFirtName: string;
  mslRequestOwnerMiddleName: string;
  mslRequestOwnerlastName: string;
  mslRequestOwnerEmailId: string;
  locationName: string;
  locationId: number;
  statusId: any;
  statusName: any;
  productDescription: string;
  productPartCode: string;
  quantity: any;
  createdByFirstName: string;
  createdByMiddleName: string;
  createByLastName: string;
  createdByEmailId: string;
  createdDate: Date;
  modifiedByFirstName: string;
  modifiedByMiddleName: string;
  modifiedByLastName: string;
  modifiedByEmailId: string;
  modifiedDate: Date;
}
export class ServiceAppointmentStatus {
  statusId: string;
  statusName: string;
}
export class PendingResponsibility {
  pendingResponsibilityId: number;
  pendingResponsibilityName: string;
}
export class ClaimStatus {
  claimStatusId: string;
  claimStatusName: string;
  synced:boolean;
}

export class ServiceReportStatus {
  statusId: string;
  statusName: string;
  synced: boolean;
}
export class ServiceAppointmentResponse {
  serviceAppointmentId: number = null;
  serviceAppointmentManulaId: string;
  manualId : string;
  siteId: number;
  engAssign:boolean=false;
  siteIdManual: string;
  caseId: number;
  caseIdManual: string;
  caseReason: string;
  siteName: string;
  zoneName: string;
  contactId: number;
  faultObserved: string = '';
  correctiveActions: string = '';
  punchPointExiom: string = '';
  punchPointCustomer: string = '';
  visitDate: any;
  customerRepresentativeName: string;
  customerRepresentativeContact: string;
  remark: string = '';
  pendingResponsibilityName: string;
  pendingResponsibilityId: number = 1;
  earliestStartPermittedDate: string;
  earliestStartPermittedTime: string;
  dueDate: string;
  dueTime: string;
  tFlag: string = 'N';
  contactFirstName: string;
  contactMiddleName: string;
  contactLastName: string;
  contactEmailId: string;
  contactPhoneNumber: string;
  caseType: string;
  assetsSapSerialNumber: string;
  caseStatus: string;
  serviceAppointmentStatusName: string;
  serviceAppointmentStatusId: number = 1;
  createdByFirstName: string;
  createdByMiddleName: string;
  createdByLastName: string;
  createdDate: Date;
  createByEmailId: string;
  ownerFirstName: string;
  ownerMiddleName: string;
  ownerLastName: string;
  ownerEmailId: string;
  modifiedByFirstName: string;
  modifiedByMiddleName: string
  modifiedByLastName: string;
  modifiedByEmailId: string;
  modifiedDate: Date;
  circleName: string;
  caseCloserDate: string;
  accountSapNumber: string;
  accountName: string;
  assetsPartCode: string;
  serviceResourceName: string;
  serviceResourceId: number;
  circleManagerContactId: number;
  circleManagerContactFirstName: string;
  circleManagerContactMiddleName: string;
  circleManagerContactLastName: string;
  serviceCenterId: number;
  serviceCenterManualId: string;
  serviceCenterName: string;
  claimStatusId: number = 0;
  claimStatusName: string;
  createDate : string;
  
  employeeCode: string;
  businessPlace: string;
  costCenter: string;
  isApprovedByFinance: boolean;
  grade: string;
  fixedPayout: number;
  approvedByFinance: Date;
  sstatus: boolean;
  sastatus: string;
  approvedByFinanceCheck: boolean;
  assetDescription: string;
  acknowledgedByName:string;
  acknowledgedByPhone:string;
  latitude:any;
  longitude:any;
  address:string;
  eSignature:any
  closedFromPlatform:any;
  customerRating:any;
  customerRemarks:string;
  rma : boolean = false;
  servicePayout : number = 0;
  caseTypeName : string;
  caseReasonName : string;
  productId : number;
  productPartCode : string; 
  commFsrId:number;
  serviceFsrId: number;
  assetId : number;
  resolutionMethod:number;
  solutionImparted:Array<btSolutionImparted>=[];
  // solutionImparted:string;
  sparePartName:string;
  sparePartQuantity:string;
  sparePartReplaced:string;
  resolutionMethodId:number;
  sparePart:string;
  dateOfVisit?:string;
  
}
export class ServiceAppointmentRequest {
  serviceAppointmentManulaId: string;
  businessDomainId: number;
  serviceAppointmentId: number = null;
  faultObserved: string;
  caseId: number;
  correctiveActions: string;
  punchPointExiom: string;
  punchPointCustomer: string;
  visitDate: Date;
  customerRepresentativeName: string;
  customerRepresentativeContact: string;
  remark: string;
  statusId: number;
  pendingResponsibilityId: number;

  earliestStartPermittedDate: string;
  earliestStartPermittedTime: string;
  dueDate: string;
  dueTime: string;
  tFlag: string;
  ownerId: string;

  assetsSerialNumber: any;
  partCode: any;
  acknowledgedByName:string;
  acknowledgedByPhone:string;
  eSignature:any
  closedFromPlatform:any
  rma : boolean = false;
  servicePayout : number;
  assetId : number;
  // SCH Request
  resolutionMethod:number;
  solutionImparted:Array<btSolutionImparted>=[];
  sparePart:string;
  sparePartQuantity:number;
  sparePartReplaced:string;
  pendingResource:string;
  resolutionMethodId:number;
}
export class SecondServiceAppointmentRequest {
  caseId: number;
  serviceResourceId: number;
  isAssignmentRule: boolean = true;

  caseManaualId: string;
  serviceResourceName: string;
  acknowledgedByName:string;
  acknowledgedByPhone:string;
}

export class ProductConsumptionType {
  consumptionTypeName: string;
  synced: boolean;
}
export class ServiceAppointmentProductConsumedResponse {
  serviceAppointmentId: number;
  serviceAppointmentManualId: string;
  partCode: string;
  partDescription: string;
  consumedQty: number;
  consumedType: string;
  remark: string;
}
export class ServiceAppointmentProductConsumedLineitemRequest {
  id: number;
  consumedItemSerialNumber: string;
  replacedItemSerialNumber: string;
  faultyPartcode:string;
  qty: number;
}

export class ServiceAppointmentProductConsumedRequest {
  serviceAppointmentNumber: number;
  serviceAppointmentManualId: string;
  productConsumedId: number;
  partCode: string;
  consumedQty: number;
  consumeType: string;
  remark: string;

  productLineItem: Array<ServiceAppointmentProductConsumedLineitemRequest> = [];
  okId: number;
  serviceAppointmentId : number;
  productConsumption : string;
  consumedProducts : string;
  quantity : number;
  replacedProducts : string;
}
// Ayush
export class FaultyCounter{
  srNo:number;
  faultypartCode:string;
  faultySerialNo:string;
  totalSerialCount?:number;
}
export class CounterSerialNo{
  // srNo:number;
  faultypartCode:string;
  // faultySerialNo?:string;
  totalSerialCount?:number;
}

export class ServiceAppointmentReAssignRequest {
  serviceAppointmentId: number;
  caseId: number;
  serviceCenterId: number;
  serviceResourceId: number;
  businessDomainId: number;

  serviceCenterName: string;
  serviceResourceName: string
}


export class ExpenseManagementExpensePurpose {
  expensePurposeId: number;
  expensePurposeName: string;
  division: string;
}

export class ExpenseManagementExpenseType {
  expenseTypeId: number;
  expenseTypeName: string;
  division: string;
}

export class ExpenseManagementStatus {
  statusId: number;
  statusName: string;
  division: string;
}

export class ExpenseManagementBusinessPlace {
  businessPlaceId: number;
  businessPlaceName: string;
  division: string;
}

export class ExpenseManagementBusinessArea {
  businessAreaId: number;
  businessAreaName: string;
  division: string;
}

export class ExpenseManagementCostCenter {
  costCenterId: number;
  costCenterName: string;
  division: string;
}
export class RequisitionStatus {
  statusId: number;
  statusName: string;
  synced: boolean;
}

export class RequisitionProductType {
  productTypeId: number;
  productTypeName: string;
  synced: boolean;
}
export class RequisitionApprovalStatus {
  approvalStatusId: number;
  approvalStatusName: string;
  synced: boolean;
}
export class RequisitionProductDetailsResponse {
  productPartCode: string;
  proudctDescription: string;
  quantity: number;
}
export class RequisitionProductDetailsRequest {
  productPartCode: string;
  quantity: number;
}

export class RequisitionResponse {
  id: number;
  manualId: string;
  productList: Array<RequisitionProductDetailsResponse> = [];

  fromWarehouseId: number;
  fromWarehouseEmailId: string;
  fromWarehouseName: string;
  requstingEntity: string;

  toWarehouseId: number;
  toWarehouseEmailId: string;
  toWarehouseName: string;
  requestingTo: string;

  requisitionerFirtName: string;
  requisitionerlastName: string;
  requisitionerEmailId: string;

  requiredDate: Date;

  requisitionStatusId: number = 0;
  requisitionStatusName: string;

  productTypeId: number = 0;
  productTypeName: string;

  approvalStatusId: number = 0;
  approvalStatusName: string;

  receivedStatus: boolean;
  sentStatus: boolean;

  ownerFirtName: string;
  ownerlastName: string;
  ownerEmailId: string;

  createdByFirstName: string;
  createByLastName: string;
  createdByEmailId: string;
  createdDate: Date;

  modifiedByFirstName: string;
  modifiedByLastName: string;
  modifiedByEmailId: string;
  modifiedDate: Date;

  costCenter: string;
  plant: string;

  sentStatusMessage: string;
  pendingForIssue: number;

  totalQuantityRequired: number;
  totalInTransit: number;
  totalQuantityReceived: number;
  totalQuantityIssued: number;
  locationProductRequestResponse

  legalEnetityType: string;
}

export class RequisitionRequest {
  id: number;
  manualId: string;

  requisitionId: number;
  requisitionStatusId: number;
  productList: Array<RequisitionProductDetailsRequest> = [];
  fromWarehouseId: number;
  toWarehouseId: number;
  requiredDate: Date;
  productTypeId: number;
  approvalStatusId: number;
}

export class WareHouseForList {
  warehouseId: number;
  warehouseName: string;
}

export class WarehouseForList {
  warehouseId: number;
  warehouseName: string;
}

export class ServiceResourseForList {
  serviceResourceId: number;
  name: string;
  accountName: string;
  emailId:string   //new add var
  bothvalue?:string;
}
export class ServiceResourseVendor{
  Id:number;
  Name:string;
}
export class ServiceAppointmentAssignToRequest{
  serviceResourceId:number;
  id:number;
}
export class ServiceAppointmentUnAssignToRequest{
  // serviceResourceId:number;
  id:number;
}


export class ServiceResourseRequest {
  serviceResourceId: number;
  name: string;
  userEmailId: string = null;
  locationId: number = null;
  locationName: string = '';
  recourceTypeId: number = null;
  contactId: number = null;
  active: boolean;
  createdByFirstName: string;
  createdByMiddleName: string;
  createdByLastName: string;
  createdDate: string; //
  modifiedByEmailId: string;
  modifiedByFirstName: string;
  modifiedByMiddleName: string;
  modifiedByLastName: string;
  modifiedDate: string; // date
  owner: string;
  isLocationSelected: string = 'fa fa-search';
}

export class ServiceResourseResponse {
  serviceResourceId: number;
  name: string;
  userEmailId: string = null;
  userFirstName: string;
  userLastName: string;
  resourceTypeId: number;
  resourceTypeName: string;
  locationId: number;
  locationName: string;
  createdByEmailId: string;
  createdByFirstName: string;
  createdByMiddleName: string;
  createdByLastName: string;
  createdDate: string; //
  modifiedByEmailId: string;
  modifiedByFirstName: string;
  modifiedByMiddleName: string;
  modifiedByLastName: string;
  modifiedDate: string; // date
  contactFirstName: string;
  contactLastName: string;
  contactEmailId: string;
  active: boolean;
  contactId: number = 0;
  isActive: boolean;
  employeeCode: string;
  userMiddleName: string;
  contactMiddleName: string;
}

export class LocationRequest {
  locationId: number;
  recordType: string;
  locationName: string;
  ownerEmailId: string;
  parentLocationId: number;
  mobileLocation: boolean;
  locationTypeId: number;
  legalEntityId: number;
  inventoryLocation: boolean;
  plant: string;
}
export class LocationResponse {
  locationId: number;
  recordType: string;
  locationName: string;
  ownerEmailId: string;
  ownerFirstName: string;
  ownerMiddleName: string;
  ownerLastName: string;
  parentLocationId: number;
  parentLocationName: string;
  mobileLocation: boolean;
  locationTypeId: number;
  locationTypeName: string;
  inventoryLocation: boolean;
  legalEntityId: number;
  legalEntityName: string;
  createdByEmailId: string;
  createdByFirstName: string;
  createdByMiddleName: string;
  createdByLastName: string;
  createdDate: string;
  modifiedByEmailId: string;
  modifiedByFirstName: string;
  modifiedByMiddleName: string;
  modifiedByLastName: string;
  modifiedDate: string;
  selectionChecked: false;
  rootLocation?: string;
  plant: string;
}
export class MSLItemRequestResponse {
  mslRequestItemId: number;
  partCode: string;
  partDescription: string;
  newMSL: any;
  newHSL: any;
}

export class ResourceType {
  resourceTypeId: number;
  resourceTypeName: string = '';
}

export class LocationForList {
  locationId: number;
  locationName: string = '';
}

export class BoardingLoadingForListResponse {

  boardigName: string;
}

export class ContactForList {
  contactId: number = 0;

  firstName: string = '';
  middleName: string = '';
  lastName: string = '';
  emailId: string = '';
  phoneNumber: string = '';
  accountSapNumber: string;
  primaryAccNumber: string;
  fullName?: string;
  contactMobileNumber?:string;
}

export class userRole {
  authorities: string;
}

export class CreateLoginRequest {
  contactId: number;
  password: string;
  confirmPassword: string;
  authorities: string;
  accountNonExpired: boolean;
  accountNonLocked: boolean;
  credentialsNonExpired: boolean;
  enabled: boolean;
  locationId: number;
  resourceTypeId: number = 0;
  approvarManagerEmailId: string;
  managerEmailId: string;
  userProfileId:any;
  accountSapNumber?: string;
}
export class LocationType {
  locationTypeId: number;
  locationTypeName: string;
  synced: boolean;
}
export class LocationProductItemForList {
  id: number;
  locationProductItemId: string;
  productPartCode: string;
  productTypeName: string;
  productDescription:string;
  bothvalues?:string;
}
export class ChangeOwnerRequest {
  ids: Array<any>;
  stringIds: Array<any>;
  userEmailId: string;
}
export class LocationProductItemResponse {
  locationProductItemId: number;
  locationProductItemManualId: string;
  productPartCode: string;
  productDescription: string;
  quantityInOnHand: number;
  quantityUnitOfMasur: string;
  locationId: number;
  locationName: string;
  parentLocationProductItemId: number;
  parentLocationProductItemManualId: string;
  hslQuantity: number;
  hslStatus: string;
  issuedQunatity: number;
  mslQuantity: number;
  mslQuantityStatus: string;
  okQuantity: number;
  faultyQuantity: number;
  selected?: boolean;
}

export class TourExpenseRequest {
  timeFlag: boolean;
  id: number;
  expenseManagementId: number;
  expenseManagementManualId: string;
  modeOfTravelId: number = 0;
  ownConveyanceId: number = 0;
  departureDate: string;
  departureTime: string = null;
  arrivalDate: string = '';
  arrivalTime: string = null;
  fromPlace: string;
  toPlace: string;
  companyPaid: boolean;
  fare: string;
  approvedAmount: string;
  distance: string;
  remark: string;
  manualId: string;
  amountToDisbursed: number;
}

export class TourExpenseResponse {
  id: number;
  manualId: string;

  expenseManagementId: number;
  expenseManagementManualId: string;

  businessDomainId: number;
  businessDomainName: string;

  modeOfTravelId: number = 0;
  modeOfTravelName: string = '';
  ownConveyanceId: number = 0;
  ownConveyanceName: string = '';
  departureDate: string;
  departureTime: string;
  arrivalDate: string;
  arrivalTime: string;
  fromPlace: string;
  toPlace: string;
  companyPaid: boolean;
  fare: string;
  approvedAmount: string;
  distance: string;
  remark: string;
  createdByEmailId: string;
  createdByFirstName: string;
  createdByLastName: string;
  createDate: string;
  modifiedByEmailId: string;
  modifiedByFirstName: string;
  modifiedByLastName: string;
  modifiedDate: string;
  ownerEmailId: string;
  ownerFirstName: string;
  ownerLastName: string;
  createdByMiddleName: string;
  modifiedByMiddleName: string;
  noOfDays: number;
  arrivalDateTime: string;
  departuredateTime: string;
  amountToDisbursed: number;
}

export class OwnConveyance {
  id: number;
  name: string;
  modeOfTravel: ModeOfTravel;
}

export class ModeOfTravel {
  id: number;
  name: string;
}
export class LPRequest {
  id: number;
  manualId: string;
  descirption: string;
  needByDateTime: Date;
  sourceLocationId: number;
  destinationLocationId: number;
  locationProductRequestStatus: string;
  lileItems: Array<LPItemRequest>;
}
export class LPItemRequest {
  id: number;
  manualId: string;
  productPartCode: string;
  lprId: number;
  requestQuantity: string;
}
export class LocationProductRequestResponse {
  prId: number;
  prManualId: string;
  destinationLocationId: number;
  destinationLocationName: string;
  sourceLocationId: number;
  sourceLocationName: string;
  createdDate: Date;
  description: string;
  needByDateTime: Date;
  status: string;
  ownerEmailid: string;
  ownerFirstName: string;
  ownerMiddleName: string;
  ownerLastName: string;
  totalQuantityRequired: number;
  totalQuantityIssued: number;
  totalQuantityReceived: number;
  createdByEmail: string;
  createdByFirstName: string;
  createdByMiddleName: string;
  createdByLastName: string;
  modifiedByEmail: string;
  modifiedByFirstName: string;
  modifiedByMiddleName: string;
  modifiedByLastName: string;
  modifiedDate: Date;
  destinationLocationOwnerEmailId: string;
  sourceLocationOwnerEmailId: string;
  disabled:boolean;
  isShortclosed?:boolean=false;
  totalShortCloseQty:number;
}
export class LocationProductRequestItemResponse {
  lpRequestItemId: number;
  lpRequestItemManualId: string;
  lpRequestId: number;
  lpRequestManualId: string;
  partCode: string;
  partDescription: string;
  quantityRequired: number;
  quantityReceived: number;
  quantityIssued: number;
  productType: string;
  okItems?: OkItemResponse;
  allselect?: any;
  quantityToBeIssued?: number;
  search: string
  edit:boolean;
  disabled:boolean;
  totalSelected:number;
  createdByEmailId: string;
  createdByFirstName: string;
  createdByMiddleName: string;
  createdByLastName: string;
  createdDate: Date;
  modifiedByEmailId: string;
  modifiedByFirstName: string;
  modifiedByMiddleName:string;
  modifiedByLastName:string;
  modifiedDate: Date;
  shortClosedQuantity?:number;
  lineItemStatus?:string;

}
export class OkItemResponse {
  okQuantityOnHand: number;
  serialNumbers: Array<any>;
}

export class BRCRepairBERRemark {
  berRemarkId: number;
  berRemarkName: string;
  synced: boolean;
}

export class PackingCondition {
  id: number;
  name: string;
  synced: boolean;
}

export class FaultObserved {
  id: number;
  domain: BusinessDomain;
  faultObserved: string;
  synced: boolean;
}

export class BRCRepiarStatus {
  statusId: number;
  statusName: string;
  synced: boolean;
}

export class BRCRepairType {
  typeId: number;
  typeName: string;
  synced: boolean;
}

export class BRCRepiarBalancedAt {
  balancedAtId: number;
  balancedAtName: string;
  synced: boolean;
}

export class BRCRepiarResolutionMethod {
  resolutionMethodId: number;
  resolutionMethodName: string;
  synced: boolean;
}

export class BRCRepairInventoryReceiveRequest {
  receivingName: string;

  receivingId: number;
  caseDetailsId: number;
  assetsSerialNumber: string;
  invoiceNumber: string;
  productPartCode: string;
  legalEntityId: number;
  siteId: number;
  contactId: number;
  invoiceDate: string;
  alterSerialNumber: string;
  challanNumber: string;
  rjilChallanDate: string;
  locationStateOrCircle: string;
  packingConditionId: number = 0;
  receivedDate: string;
  physicalDamageIfAny: string = '0';
  faultObservedId: number = 0;
  batteryVoltageBefoureRepair: string;
  remarks: string;
  brcRepariStatus: number = 1;

}
export class BRCInventoryReceiveResponse {
  receivingId: number = 0;
  receivingName: string;
  caseId: number;
  caseDetailsManualId: string;
  assetsSerialNumber: string;
  invoiceNumber: string;
  productPartCode: string;
  productDescription: string;
  legalEntityId: number;
  legalEntityName: string;
  siteId: number;
  siteManualId: string;
  siteName: string;
  contactId: number;
  contactFirstName: string;
  contactLastName: string;
  contactmiddleName: string;
  contactEmailid: string;
  invoiceDate: Date;
  alterSerialNumber: string;
  challanNumber: string;
  rjilChallanDate: string;
  locationStateOrCircle: string;
  packingConditionId: number = 0;
  packingConditionName: string;
  receivedDate: string;
  physicalDamageIfAny: string = '0';
  faultObservedId: number = 0;
  faultObservedName: string;
  batteryVoltageBefoureRepair: string;
  remarks: string;
  brcRepariStatus: number = 1;

  ownerDetailsEmailId: string;
  ownerDetailsFirstName: string;
  ownerDetailsLastName: string;
  ownerDetailsMiddleName: string;
  createdByEmailId: string;
  createdByFirstName: string;
  createdByLastName: string;
  createdByMiddleName: string;
  modifiedByEmailid: string;
  modifiedByFirstName: string;
  modifiedByLastName: string;
  modifiedByMiddleName: string;
  createdDate: Date;
  modifiedDate: Date;

  repairId: number;
  engineerId: number;
  engineerContactId: number;
  engineerName: string;
  repairTypeId: number = 1;
  repairTypeName: string;
  engineerIssuedDate: Date;
  repariDate: string;
  battryVoltageAfterRepair: string;
  battryAHAfterRepair: string;
  battryEngCorrectiveAction: string;
  battryEngRemarks: string;
  battryBalanceAtId: number = 0;
  battryBalanceAtName: string;
  brcBERRemarksId: number = 0;
  brcBERRemarksName: string;
  materialSortage: string;
  cellBulged: string;

  dispatchId: number;
  podNumber: string;
  dispatchMode: string;
  dispatcherName: string;
  sapDeleveryChallanNumber: string;
  dispatchDate: Date;
  dispatchShipmentNo: string;
  dispatchRemark: string;
  dispatchCreatedbyEmailId: string;
  dispatchCreatedByFirstName: string;
  dispatchCreatedByLastName: string;
  dispatchCreatedDate: Date;
  dispatchModifieByEmailId: string;
  dispatchModifiedByFirstName: string;
  dispatchModifiedByLastName: string;
  dispatchModifiedDate: Date;

  brcRepairStatusId: number = 1
  brcRepairStatusName: string;

  warehouse: string;
  customerAccountId: string;
  customerAccountName: string;
  warrantyStatus: boolean;
  complaintStatus: string;
  sla: string;
  brcRepairResolutionMethod: Array<BRCRepiarResolutionMethodChoosen>;
}
export class BRCRepiarResolutionMethodChoosen {
  id: number;
  resolutionMethods: BRCRepiarResolutionMethod;
}

export class BRCRepairInventoryForList {
  receivingId: number;
  receivingName: string;
}

export class BRCRepairInventoryReceiveItemRequest {
  repairId: number;
  receiveDetailsId: number;
  serviceResourceId: number;
  brcRepariType: number = 1;
  engineerIssuedDate: Date;
  repariDate: string;
  battryVoltageAfterRepair: string;
  battryAHAfterRepair: string;
  battryEngCorrectiveAction: string;
  battryEngRemarks: string;
  battryBalanceAtId: number = 0;
  brcBERRemarksId: number = 0;
  materialSortage: string;
  cellBulged: string;
  methodChoosen: Array<BRCRepiarResolutionMethodChoosenRequest>;

  receivingName: string;
  engineerName: string;
  legalEntityId: number
}

export class BRCRepiarResolutionMethodChoosenRequest {
  id: number;
  repariItemId: number;
  resolutionMethodsId: number;
}
export class BRCRepairInventoryDispatchRequest {
  dispatchId: number;
  podNumber: string;
  dispatchMode: string = '0';
  dispatcherName: string;
  sapDeleveryChallanNumber: string;
  dispatchDate: string;
  repariItemId: number;
  dispatchShipmentNo: string;
  dispatchRemark: string;
}
export class BRCRepariInventoryProductConsumedRequest {
  miCode: number;
  productPartCode: string;
  quantity: number;
  materialIssueDate: Date;
  faultySubmissionDate: Date;
  remarks: string;
  consumeid: number;

  receivingName: string;
  repairId: number
}

export class BRCRepariInventoryProductConsumedResponse {
  miCode: number;
  miManualId: string;
  productPartCode: string;
  quantity: number;
  materialIssueDate: Date;
  faultySubmissionDate: Date;
  remarks: string;
  consumeid: number;
  repairId: number
  consumedDate: Date;
  productDescription: string;
  caseId: number;
  caseDetailsManualId: string;
}

export class LocalConveyanceExpenseRequest {
  amountToDisbursed: number;
  id: number = 0;
  expenseManagementId: number = 0;
  expenseManagementManualId: string = '';
  modeOfTravelId: number = 0;
  ownConveyanceId: number = 0;
  date: string = '';
  fromPlace: string = '';
  toPlace: string = '';
  amount: string;
  distance: string;
  remark: string = '';
  manualId: string = '';
}

export class LocalConveyanceExpenseResponse {
  id: number = 0;
  manualId: string = '';
  expenseManagementId: number = 0;
  expenseManagementManualId: string = '';
  businessDomainId: number = 0;
  businessDomainName: string = '';
  modeOfTravelId: number = 0;
  modeOfTravelName: string = '';
  ownConveyanceId: number = 0;
  ownConveyanceName: string = '';
  date: string = '';
  fromPlace: string = '';
  toPlace: string = '';
  amount: string;
  distance: string;
  remark: string = '';
  createdByEmailId: string = '';
  createdByFirstName: string = '';
  createdByLastName: string = '';
  create: string = '';
  modifiedByEmailId: string = '';
  modifiedByFirstName: string = '';
  modifiedByLastName: string = '';
  modified: string = '';
  ownerEmailId: string = '';
  ownerFirstName: string = '';
  ownerLastName: string = '';
  createDate: string;
  companyPaid: boolean;
  fare: string;
  approvedAmount: string;
  departureDate: string;
  departureTime: string;
  arrivalDate: string;
  arrivalTime: string;
  createdByMiddleName: string;
  modifiedByMiddleName: string;
  modifiedDate: string;
  amountToDisbursed: number;
}

export class OtherChargesExpenseRequest {
  id: number = 0;
  expenseManagementId: number = 0;
  expenseManagementManualId: string = '';
  otherChargesTypeId: number = 0;
  date: string = '';
  billNo: string = '';
  amount: string;
  remark: string = '';
  businessDomainId: number = 0;
  manualId: string = '';
  amountToDisbursed: number;
}

export class OtherChargesExpenseResponse {
  id: number = 0;
  manualId: string = '';
  expenseManagementId: number = 0;
  expenseManagementManualId: string = '';
  businessDomainId: number = 0;
  businessDomainName: string = '';
  otherChargesTypeId: number = 0;
  otherChargesTypeName: string = '';
  date: string = '';
  billNo: string = '';
  amount: string;
  remark: string = '';
  createdByEmailId: string = '';
  createdByFirstName: string = '';
  createdByLastName: string = '';
  createDate: string = '';
  modifiedByEmailId: string = '';
  modifiedByFirstName: string = '';
  modifiedByLastName: string = '';
  modified: string = '';
  ownerEmailId: string = '';
  ownerFirstName: string = '';
  ownerLastName: string = '';
  createdByMiddleName: string;
  modifiedByMiddleName: string;
  modifiedDate: string;
  amountToDisbursed: number;
}


// export class BusinessDomain {
//   businessDomainId: number;
//   businessDomainName: string;
//   synced: boolean;
// }

export class PriceBookRequestinProduct {
  id: number;
  priceBookId: number;
  partCode: string;
  name: string;
  description: string;
  active: boolean;
  standardPriceBook: boolean;
  listprice: number;
}

export class PriceBookRequest {
  id: number = 0;
  name: string;
  listprice: string;
  active: boolean;
  description: string;
  standardPriceBook: boolean;
}

export class PriceBookResponse {
  id: number;
  name: string;
  description: string;
  active: boolean = true;
  standardPriceBook: boolean;
  createdByFirstName: string;
  createdByMiddleName: string;
  createdByLastName: string;
  createdByEmailId: string;
  createdDate: Date;
  modifiedByFirstName: string;
  modifiedByMiddleName: string;
  modifiedByLastName: string;
  modifiedByEmailId: string;
  modifiedDate: Date;
  listPrice: any;
  partCode: string;
  priceBookName: string;
  productFamily: string;
}

export class PriceBookEntriesRequest {
  id: number;
  priceBookId: number;
  partCode: string;
  active: boolean = true;
  listPrice: any;
}





// pendingResponsibility: PendingResponsibility;
// SLATime: string;
// PODNumber: string;
// PODDate: string;
// PONumber: string;
// PODate: string;
// invoiceNumber: string;
// invoiceDate: string;
// vendorInvoiceNumber: string;
// vendorInvoiceDate: string;
// callCenterRepFirstName: string;
// callCenterRepLastName: string;

// accountSapNumber: string;
// accountName: string;
// siteManualId: string;
// siteName: string;
//  siteId: number;
// caseStageVerticalName: string;
//  caseStageVerticalId: number;
//  serviceResourceId: number;
//  serviceResouceContactId: number;
// serviceResourceName: string;
//  serviceCenterId: number;
// serviceCenterName: string;
// missingAssets: string;

// export class PendingResponsibility {
//   PendingResponsibilityId: number;
//   PendingResponsibilityName: string;
// }

export class QuoteRequest {
  id: number = 0;
  quoteName: string = '';
  isApproved: boolean;
  approvalCheck: number = 0;
  oppertunityId: number = 0;
  validityDays: string = "45";
  expiryDate: string = '';
  quoteStatusId: number = 0;
  isSyncing: boolean;
  submittedDate: string = '';
  approvedByStoreDate: string = '';
  approvedByCommercialDate: string = '';
  quoteSentDate: string = '';
  remark: string = '';
  sgst: string = '';
  igst: string = '';
  cgst: string = '';
  sgstAmount: string = '';
  igstAmount: string = '';
  cgstAmount: string = '';
  visitCharge: string = '';
  subTotal: string = '';
  totalAmountWithTax: string = '';
  discount: string = '';
  totalAmountAfterDiscount: string = '';
  billingName: string = '';
  billingStreet: string = '';
  billingCity: string = '';
  billingCountryId: string = null;
  billingStateId: number = 0;
  billingPostalCode: string = '';
  shippingName: string = '';
  shippingStreet: string = '';
  shippingCity: string = '';
  shippingCountryId: string = null;
  shippingStateId: number = 0;
  shippingPostalCode: string = '';
  otherTermsAndConditions: string = '';
  toEmailIds: string = '';
  ccEmailIds: string = '';

  quoteNumber: string = '';
  accountName: string = '';
  oppertunityManualId: number;
  copyBillingDetails: boolean;
  syncing: boolean;
  approved: boolean;
  businessDomainId: number;

  // createdByFirstName: string = '';
  // createdByLastName: string = '';
  // createdByEmailId: string = '';
  // createdDate: string = '';
  // modifiedByFirstName: string = '';
  // modifiedByLastName: string = '';
  // modifiedByEmailId: string = '';
  // modifiedDate: string = '';
}

export class QuoteResponse {
  id: number = 0;
  quoteNumber: string = '';
  quoteName: string = '';
  approved: boolean;
  approvalCheck: number = 0;
  oppertunityId: number = 0;
  oppertunityManualId: number;
  sapCustomerNumber: string = '';
  accountName: string = '';
  validityDays: string = null;
  expiryDate: string = '';
  quoteStatusId: number = 0;
  quoteStatusName: string = '';
  syncing: boolean;
  submittedDate: string = '';
  approvedByStoreDate: string = '';
  approvedByCommercialDate: string = '';
  quoteSentDate: string = '';
  remark: string = '';
  sgstAmount: string = '';
  igstAmount: string = '';
  cgstAmount: string = '';
  subTotal: string = '';
  totalAmountWithTax: string = '';
  discount: string = '';
  totalAmountAfterDiscount: string = '';
  billingName: string = '';
  billingStreet: string = '';
  billingCity: string = '';
  billingCountryId: string = null;
  billingCountryName: string = '';
  billingStateId: number = 0;
  billingStateName: string = '';
  billingPostalCode: string = '';
  shippingName: string = '';
  shippingStreet: string = '';
  shippingCity: string = '';
  shippingCountryId: string = null;
  shippingCountryName: string = '';
  shippingStateId: number = 0;
  shippingStateName: string = '';
  shippingPostalCode: string = '';
  otherTermsAndConditions: string = '';
  toEmailIds: string = '';
  ccEmailIds: string = '';
  createdByFirstName: string = '';
  createdByLastName: string = '';
  createdByEmailId: string = '';
  createdDate: string = '';
  modifiedByFirstName: string = '';
  modifiedByLastName: string = '';
  modifiedByEmailId: string = '';
  modifiedDate: string = '';
  ownerFirstName: string = '';
  ownerLastName: string = '';
  ownerEmailId: string = '';

  sgst: string = '';
  igst: string = '';
  cgst: string = '';
  visitCharge: string = '';
  copyBillingDetails: boolean;
  isSyncing: boolean;
  isApproved: boolean;
  businessDomainId: number;
  workOrderNumber: string;
  workOrderDate: Date;
  caseId: number;
  caseNumber: string;
}

export class QuoteStatus {
  id: number;
  name: string;
}


export class CityForListReponse {
  cityName: string;
}

export class BoardingExpenseRequest {
  id: number = 0;
  expenseManagementId: number = 0;
  expenseManagementManualId: string = '';
  fromDate: string = '';
  fromTime: string;
  toDate: string = '';
  toTime: string
  cityName: string = null;
  otherCity: string = '';
  noOfDayes: number = 0;
  amount: number = 0;
  remark: string = '';
  // isCitySelected: string = 'fa fa-search';
  cityCategoryId: number;
  entitlementPerDay: number;
  manualId: string = '';
  amountToDisbursed: number;
}

export class BoardingExpenseResponse {
  id: number = 0;
  manualId: string = '';
  expenseManagementId: number = 0;
  expenseManagementManualId: string = '';
  businessDomainId: number = 0;
  businessDomainName: string = '';
  fromDate: string = '';
  fromTime: string;
  toDate: string = '';
  toTime: string;
  cityCategoryName: string = '';
  cityCategoryId: number;
  cityName: string = null;
  otherCity: string = '';
  noOfDayes: number = 0;
  amount: number = 0;
  remark: string = '';
  createdByEmailId: string = '';
  createdByFirstName: string = '';
  createdByLastName: string = '';
  createDate: string = '';
  modifiedByEmailId: string = '';
  modifiedByFirstName: string = '';
  modifiedByLastName: string = '';
  modifiedDate: string = '';
  ownerEmailId: string = '';
  ownerFirstName: string = '';
  ownerLastName: string = '';
  // isCitySelected: string = 'fa fa-search';
  entitlementPerDay: number;
  entitlement: number = 0;
  arrivalDateTime: string;
  DepartureDateTime: string;
  amountToDisbursed: number;
}
export class CityDetails {
  cityName: string = '';
  cityCategoryId: number = 0;
}

export class LodgingExpenseRequest {
  id: number;
  expenseManagementId: number;
  expenseManagementManualId: string;
  fromDate: string = '';
  fromTime: string;
  toTime: string;
  toDate: string = '';
  cityName: string = null;
  otherCity: string = '';
  selfArrangement: boolean;
  comapnyPaid: boolean;
  hotelName: string = '';
  billNo: string = '';
  amount: string;
  approvedAmount: string;
  remark: string = '';
  cityCategoryId: number;
  manualId: number;
  amountToDisbursed: number;
  // isCitySelected: string = 'fa fa-search';
  // fromDateShow: string = '';
  // toDateShow: string = '';

}

export class LodgingExpenseResponse {

  id: number;
  manualId: number;
  expenseManagementId: number;
  expenseManagementManualId: string = '';
  businessDomainId: number;
  businessDomainName: string = '';
  fromDate: string = '';
  toDate: string = '';
  fromTime: string;
  toTime: string;
  cityCategoryName: string = '';
  cityCategoryId: number;
  cityName: string = '';
  otherCity: string = '';
  selfArrangement: boolean;
  comapnyPaid: boolean;
  hotelName: string = '';
  billNo: string = '';
  amount: string;
  approvedAmount: string;
  remark: string = '';
  createdByEmailId: string = '';
  createdByFirstName: string = '';
  createdByLastName: string = '';
  createDate: string = '';
  modifiedByEmailId: string = '';
  modifiedByFirstName: string = '';
  modifiedByLastName: string = '';
  modifiedDate: string = '';
  ownerEmailId: string = '';
  ownerFirstName: string = '';
  ownerLastName: string = '';
  // isCitySelected: string = 'fa fa-search';
  createdByMiddleName: string;
  modifiedByMiddleName: string;
  // fromDateShow: string = '';
  // toDateShow: string = '';
  noOfDays: number;
  entitlement: number = 0;
  arrivalDateTime: string;
  DepartureDateTime: string;
  netAmountToPay: string;
  amountToDisbursed: number;
}

export class LPTransferRequest {
  locationProductTransferId: number;
  locationProductRequestId: number;
  shippingToName: string;
  trackingNumber: string;
  shippingMode: string = 'select';
  actualDeliveryDate: Date;
  items: Array<LPTransferItemRequest>
}
export class LPTransferItemRequest {
  lpTransferItemId: number;
  lpRequestLineItemId: number;
  transferQuantity: number;
  serialNumbers: Array<string>;
}
export class LocationProductRequestPendingToReceive {
  productRequestId: number;
  productRequestManualId: string;
  productRequestItemId: number;
  productRequestItemManualId: string;
  productTransferId: number
  productTransferManualId: string;
  productTransferItemId: number;
  productTransferItemManualId: string;
  productPartCode: string;
  productDescription: string;
  productType: string;
  sourceLocationId: number;
  sourceLocationName: string;
  destinationLocationId: number;
  destinationLocationName: string;
  totalQuantityTransfred: number;
  totalQuantityReceived: number;
  actualReceive?: number;
  transferDate: Date;
  serialNumbers: Array<any>;
  invoiceNumber:string;
}
export class LocationProductTransferReceiveRequest {
  locationProductTransferItemId: number;
  quantityReceive: number;
  receivedSerialNumber: Array<string>;
}
export class LocationProductTransferItemResponse {
  locationProductTransferItemId: number;
  locationProductTransferItemManualId: string;
  locationProductTransferId: number;
  locationProductTransferManualId: string;
  locationProductRequestId: number;
  locationProductRequestManualId: string;
  productPartCode: string;
  partDescription: string;
  quantitySent: number;
  quantityReceived: number;
  receivedAll: boolean;
  sourceLocationId: number;
  sourceLocationName: string;
  destinationLocationId: number;
  destinationLocationName: string;
  locationProductRequestLineItemId: number;
  locationProductRequestLineItemManualId: string;
  ownerFirstName: string;
  ownerMiddleName: string;
  ownerLastName: string;
  ownerEmailId: string;
  createdByEmailId: string;
  createdByFirstName: string;
  createdByMiddleName: string;
  createdByLastName: string;
  modifiedByEmailId: string;
  modifiedByFirstName: string;
  modifiedByMiddleName: string;
  modifiedByLastName: string;
  createdDate: Date;
  modifiedDate: Date;
  trackingNumber: string;
  shipToName: string;
  shippingMode: string;
  expectedDate: Date;
}
export class LPReturnOrderRequest {
  lprOrderid: number;
  sourceLocationId: number;
  destinationLocationId: number;
  totalQuantityReturned: number;
  shipToName: string;
  shippingMode: string;
  trackingNumber: string;
  expectedArrivalDate: Date;
  items: Array<LPReturnOrderItemRequest>;
}
export class LPReturnOrderItemRequest {
  returnOrderItemId: number;
  lpReturnOderId: number;
  productItemId: number;
  productPartCode: string;
  returnDate: Date;
  totalReturnQuantity: number;
  lineItems: Array<LPReturnOrderLineItemRequest>;
}
export class LPReturnOrderLineItemRequest {
  lproLineitemId: number;
  returnOrderItemId: number;
  productLineItemSerialNumber: string;
  quantityReturned: number = 1;
}
export class LocationFaultyItemResponse {
  locationId: number;
  locationItemId: number;
  locationItemManualId: string;
  partCode: string;
  partDescription: string;
  faultyQuantity: string;
  checked: boolean;
  checkedlen?:any;
  lineItems: Array<LocationFaultyLineItemResponse>;
}
export class LocationFaultyLineItemResponse {
  checkedlen?:number=0;
  locationItemId: number;
  locationLineItemId: number;
  locationLineItemManualId: string;
  serialNumber: string;
  isSelected?: boolean;
  partCode: string;
  pdoductDescription: string;
  productStateId: string;
  productStateName: string;
}
export class LPrStatusForList{
  name:string;
  id:number;
}
export class LpReturnOrderResponse {
  returnOrderId: number;
  returnOrderManualId: string;
  destinationLocationId: number;
  destinationLocationName: string;
  sourceLocationId: number;
  sourceLocationName: string;
  returnDate: Date;
  shippingToName: string;
  shippingModel: string;
  trackingNumber: string;
  expectedDateOf: string;
  totalQuantityReturned: number;
  totalQuantityReceived: number;
  ownerFirstName: string;
  ownerMiddleName: string;
  ownerLastName: string;
  ownerEmailId: string;
  createdByFirstName: string;
  createdByMiddleName: string;
  createdByLastName: string;
  createdByEmailId: string;
  createdDate: Date;
  modifiedDate: Date;
  modifiedByFirstName: string;
  modifiedByMiddleName: string;
  modifiedByLastName: string;
  modifiedByEmailId: string;
  status: string;
  received: boolean;
  destinationLocationOwnerEmailId: string;
  sourceLocationOwnerEmailId: string;
}
export class ROlineItems{
received:boolean;
returnOrderLineItemId: number;
returnOrderLineItemManualId:string;
returnOrderProductDescription:string;
returnOrderProductPartcode:string;
returnOrderProductSerialNumber:string;

}
export class LpReturnOrderItemResponse {
  returnOrderItemId: number;
  returnOrderItemManualId: string;
  returnOrderQuantity: number;
  returnOrderReceivedQuantity: number;
  locationItemId: number;
  locationItemIdManual: string;
  returnOrderId: number;
  partCode: string;
  partDescription: string;
  gcheck: boolean;
  lineItems: Array<LpReturnLineItemResponse>;

}

export class LpReturnLineItemResponse {
  returnLineItemId: number;
  returnLineItemIdManual: string;
  returnItemId: number;
  returnItemIdManual: string;
  serialNumber: string;
  received: boolean;
  acutalReceive?: boolean;
  modifiedByEmailId: string;
  modifiedByFirstName: string;
  modifiedByMiddleName: string;
  modifiedByLastName: string;
  modifiedDate: Date;
  createdByEmailId: string;
  createdByFirstName: string;
  createdByMiddleName: string;
  createdByLastName: string;
  createdDate: Date;
}
export class BatteryFSRResponse {
  id: number;
  manualId: string;
  serviceAppointmentId: number;
  serviceAppointmentManualId: string;
  caseId: number;
  caseManualId: string;
  technicianFirstName: string;
  technicianMiddleName: string;
  technicianLastName: string;
  technicianEmailId: string;
  technicianContact: string;
  dateOfVisit: Date;
  problemReported: Array<ProblemReported>;
  vendorEngName: string;
  vendorEngContact: string;
  faultReportedByCustomer: string;
  faultObservedByEngineer: string;
  siteTypeId: number = 0;
  siteTypeName: string;
  siteEnvCond: string;
  ebavailability: string;
  dgrun: string;
  // dgrating: string;
  earthingStatus: string;
  dcload: string;
  powerPlantMakeAndCapacity: string;
  ambientTemp: string;
  softVersionCBMS: string;
  batteryVoltageBeforRepair: string;
  batteryVoltageAfterRepair: string;
  correctiveAction: string;
  sparePartReplaced: string = '';
  remark: string;
  pendingResponsibilityId: number;
  pendingResponsibilityName: string;
  problemCustomerOrExicom: string;
  customerOrSiteProblem: string = '0';
  complaintStatusId: number = 0;
  complaintStatusName: string;
  otherPendingIssue: string;
  createdByFirstName: string;
  createdByMiddleName: string;
  createdByLastName: string;
  createdByEmailId: string;
  createdDate: Date;
  mdifiedByFirstName: string;
  modifiedByLastName: string;
  modifiedByMiddleName: string;
  modifiedByEmailId: string;
  modifiedDate: Date;
  dgrating: string;

}
export class BatteryFSRRequest {
  manualId: string;
  id: number;
  serviceAppointmentId: number;
  technicianContact: string;
  dateOfVisit: Date;
  vendorEngName: string;
  vendorEngContact: string;
  faultReportedByCustomer: string;
  faultObservedByEngineer: string;
  siteTypeId: number;
  siteEnvCond: string;
  ebavailability: string;
  dgrun: string;
  dgrating: string;
  earthingStatus: string;
  dcload: string;
  powerPlantMakeAndCapacity: string;
  ambientTemp: string;
  softVersionCBMS: string;
  batteryVoltageBeforRepair: string;
  batteryVoltageAfterRepair: string;
  correctiveAction: string;
  sparePartReplaced: string;
  remark: string;
  pendingResponsibilityId: number;
  problemCustomerOrExicom: string;
  customerOrSiteProblem: string;
  complaintStatusId: number;
  otherPendingIssue: string;
}
export class BatterySiteType {
  id: number;
  name: string;
  synced: boolean;
}
export class BatteryComplaintStatus {
  id: number;
  name: string;
  synced: boolean;
}

export class PriceBookForList {
  id: number = 0;
  name: string = '';
}

export class OpportunityPriceBook {
  id: number = 0;
  name: string;
}

export class QuotePriceBook {
  id: number = 0;
  name: string;
}

export class PriceBookEntriesResponse {
  id: number;
  active: boolean;
  listPrice: number;
  partCode: string = '';
  description: string = '';
  partCodePrice: number;
  productType: string;
  priceBookName: string = '';
  priceBookId: number;
  createdByFirstName: string = '';
  createdByLastName: string = '';
  createdByEmailId: string = '';
  createdDate: string = '';
  modifiedByFirstName: string = '';
  modifiedByLastName: string = '';
  modifiedByEmailId: string = '';
  modifiedDate: string = '';
  isActive: boolean = true;
  standardPriceBook: boolean;
}

export class OppertunityLineItemRequest {
  id: number;
  listPrice: number;
  salePrice: number;
  price: number = 0;
  quantity: number = 1;
  partCode: string = '';
  priceBookEntryId: number;
  priceBookId: number;

  oppertunityNumber: string = '';
  totalPrice: number;
  pdoductDescription: string = '';
  unique: string = '';
}

export class OppertunityLineItemResponse {
  id: number;
  productPartCode: string = '';
  pdoductDescription: string = '';
  productPrice: number;
  oppertunityId: number;
  oppertunityNumber: string = '';
  priceBookId: number;
  priceBookName: string = '';
  listPrice: number;
  salePrice: number;
  quantity: number;
  totalPrice: number;
  priceBookEntryId: number;
  createdByFirstName: string = '';
  createdByLastName: string = '';
  createdByEmailId: string = '';
  createdDate: string = '';
  modifiedByFirstName: string = '';
  modifiedByLastName: string = '';
  modifiedByEmailId: string = '';
  modifiedDate: string = '';
}

export class OppertunityLineItemRequestArrayEdit {
  oppertunityLineItemRequestArrayEdit: Array<OppertunityLineItemRequest> = [];
}

export class QuoteLineItemRequest {
  id: number = 0;
  priceBookEntryId: number;
  quoteId: number;
  listPrice: number;
  salePrice: number;
  price: number;
  quantity: number = 1;
  partCode: string = '';
  priceBookId: number;
  totalPrice: number;
  pdoductDescription: string = '';
  quoteName: string = '';
}

export class QuoteLineItemResponse {
  id: number;
  productPartCode: string = '';
  pdoductDescription: string = '';
  productPrice: number;
  priceBookEntryId: number;
  quoteId: number;
  quoteNumber: string = '';
  quoteName: string = '';
  businessDomainId: number;
  priceBookId: number;
  priceBookName: string = '';
  price: number;
  quantity: number;
  totalPrice: number;
  listPrice: number;
  salePrice: number;
  totalRepairPrice: number;
  cgstAmount: number;
  sgstAmount: number;
  igstAmount: number;
  totalPriceWithTax: number;
  createdByFirstName: string = '';
  createdByLastName: string = '';
  createdByEmailId: string = '';
  createdDate: string = '';
  modifiedByFirstName: string = '';
  modifiedByLastName: string = '';
  modifiedByEmailId: string = '';
  modifiedDate: string = '';
}

export class AnnexureRequest {
  id: number;
  invoiceNumber: string = '';
  invoiceDate: string = '';
  submittedDate: string;
  isInvoiceReceived: boolean;
  approvalStatusId: number = 0;
  businessDomainId: number;
  vendorCode: string;
  poNumber: string;
  poDate: string;
  invoiceReceived: boolean;
}

export class AnnexureResponse {
  id: number;
  manualId: string = '';
  vendorFirstName: string = '';
  vendorMiddleName: string = '';
  vendorLastName: string = '';
  vendorEmailId: string = '';
  vendorCode: string = '';
  serviceCenterId: number;
  serviceCenterName: string = '';
  circleManagerFirstName: string = '';
  circleManagerMiddleName: string = '';
  circleManagerLastName: string = '';
  circleManagerEmailId: string = '';
  invoiceNumber: string = '';
  invoiceDate: string = '';
  submittedDate: string = '';
  approvedByCommercialDate: string = '';
  approvedByCircleDate: string = '';
  isInvoiceReceived: boolean;
  invoiceReceived: boolean;
  lineItemCount: number;
  totalAmount: number;
  approvalStatusId: number = null;
  approvalStatusName: string = '';
  poNumber: string;
  poDate: string;
  owner: string;
  createdByFirstName: string = '';
  createdByMiddleName: string = '';
  createdByLastName: string = '';
  createdByEmailId: string = '';
  createdDate: string = '';
  modifiedByFirstName: string = '';
  modifiedByMiddleName: string = '';
  modifiedByLastName: string = '';
  modifiedByEmailId: string = '';
  modifiedDate: string = '';
  ownerFirstName: string = '';
  ownerMiddleName: string = '';
  ownerLastName: string = '';
  ownerEmailId: string = '';


}

export class AnnexureApprovalStatus {
  id: number;
  name: string;
}
export class LpReturnOrderReceiveRequest {
  lpReturnOderReceiveId: number;
  lpReturnOrderId: number;
  quantityReceived: number;
  items: Array<LpReturnOrderReceiveItem>;
}
export class LpReturnOrderReceiveItem {
  lpReturnOrderReceiveItemId: number;
  lpReturnOrderReceiveId: number;
  lpReturnOrderItemId: number;
  productPartCode: string;
  serialNumber: Array<string>;
}
export interface DisplayTableHeaders {
  id: number;
  name: string;
  sortColumnName?: string;
  icon?: string;
}
export interface DisplayDropdownList {
  sqId: number;
  name: string;
  clickEventParam: string;
  class: string;
}
export interface SelectedDropdown {
  sqId: number;
  name: string;
  filtreparam: string;
  sortname?: string;
  sortorder?: string;
  page?:number;
}

export class LocationProductTransferLineItemResponse {
  productTransferItemId: number;
  productTransferLineItemId: number;
  productTransferLineItemManualId: string;
  productPartCode: string;
  productPartDescription: string;
  productType: string;
  productLineItemSerialNumber: string;
  received: boolean;
  createdByEmailId: string;
  createdByFirstName: string;
  createdByMiddleName: string;
  createdByLastName: string;
  createdDate: string;
  modifiedByEmailId: string
  modifiedByFirstName: string;
  modifiedByMiddleName: string;
  modifiedByLastName: string;
  modifiedDate: string;
}

export class ToBeApproved {
  id: number;
  objectName: string;
  objectId: string;
  objectManualId: string;
  objectUrl: string;
  status: boolean;
  stage: string;
  action: string;
  remark: string;
  approvedDate: Date;
  assignedDate: Date;
  reAssigned?: string;
  selected: boolean;
}

export class ApprovalRequest {
  approvalId: number;
  action: string = 'select';
  remark: string;
  reAssigned: string;

}

export class ApprovalResponse {
  id: number;
  objectName: string;
  objectClassName: string;
  objectIdFiledName: string;
  approvalStages: number;
  description: string;
  active: boolean;
  entryCreteria: string;
  initialSubmitters: Array<string>;
  createByFirstName: string;
  createdByMiddleName: string;
  createByLastName: string;
  createdByEmailId: string;
  createdDate: string;
  lastModifiedDate: string;
  modifiedByFirstName: string;
  modifiedByMiddleName: string;
  modifiedByLastName: string;
  modifiedByEmailId: string;
  businessDomainId: number;
  businessDomainName: string;
}
export class ApprovalStepsResponse {
  id: number;
  approvalId: number;
  stepName: string;
  approvedBy: string;
  approvedStage: number;
  objectFiledName: string;
  onAprovedValue: string;
  onRejectedValue: string;
  createdByFirstName: string;
  createdByMiddleName: string;
  createdByLastName: string;
  createdByEmailId: string;
  createdDate: Date;
  modifiedByFirstName: string;
  modifiedByMiddleName: string;
  modifiedByLastName: string;
  modifiedByEmailId: string;
  modifiedDate: Date;
}

export class EditDates {
  createdData: string = '';
  modifiedData: string = '';
}

export class AnnexureLineItemRequest {
  businessDomainId: number;
  id: number;
  annexureId: number;
  serviceAppointmentId: number = null;
  serviceAppointmentManulaId: string;
  glCodeId: number = 0;
  workCompletionDate: string;
  activity: string = '';
  workStatus: string = '';
  exicomInvoiceDate: string;
  exicomInvoiceNumber: string = '';
  remark: string = '';
  annexureManualId: string = '';
  serviceAppointmentName: string = '';
  glCodeName: string = '';
  circleName: string = '';
  siteId: number = 0;
  siteIdManual: string = '';
  siteName: string = '';
  caseId: number = 0;
  caseIdManual: string = '';
  amountWithTax: string = '';
  amount: number = 0;
  approvalStatusId: number = 0;
  isInvoiceReceived: boolean;
  // createdByFirstName: string = '';
  // createdByLastName: string = '';
  // createdDate: string;
  // modifiedByFirstName: string = '';
  // modifiedByLastName: string = '';
  // modifiedDate: string; rate: number = 0;
}

export class AnnexureLineItemResponse {
  businessDomainId: number;
  id: number = 0;
  manualId: string = '';
  annexureId: number = 0;
  annexureManualId: string = '';
  serviceAppointmentId: number = 0;
  serviceAppointmentManulaId: string;
  serviceAppointmentName: string = '';
  circleName: string = '';
  serviceCenterId: number = 0;
  serviceCenterManualId: string = '';
  serviceCenterName: string = '';
  siteId: number = 0;
  siteIdManual: string = '';
  siteName: string = '';
  caseId: number = 0;
  caseIdManual: string = '';
  accountSapNumber: string = '';
  accountName: string = '';
  glCodeId: number = 0;
  glCodeName: string = '';
  workCompletionDate: string;
  activity: string = '';
  workStatus: string = '';
  amount: number = 0;
  amountWithTax: string = '';
  exicomInvoiceDate: string;
  exicomInvoiceNumber: string = '';
  remark: string = '';
  createdByFirstName: string = '';
  createdByMiddleName: string = '';
  createdByLastName: string = '';
  createdDate: string;
  createByEmailId: string = '';
  modifiedByFirstName: string = '';
  modifiedByMiddleName: string = '';
  modifiedByLastName: string = '';
  modifiedByEmailId: string = '';
  modifiedDate: string; rate: number = 0;
  approvalStatusId: number = 0;
  isInvoiceReceived: boolean;
}

export class AnnexureGLCode {
  id: number = 0;
  name: string;
  amount: number;
  taxRate: string;
}
export class UserResponse {
  userName: string;
  accountName: string;
  contactId: number;
  contactFirstName: string;
  contactLastName: string;
  contactMiddleName: string;
  accountSapNumber: string;
  contactDesignation: string;
  contactEmailid: string;
  contactDetailsEmployeeCode: string;
  buisnessDomain: number;
  buisnessDomainName: string;
  accountNotExpired: boolean;
  accountNonLocked: boolean;
  creadentialNonExpired: boolean;
  enabled: boolean;
  profileName: string;
  managerEmailId: string;
  managerFirstName: string;
  managerMiddleName: string;
  managerLastName: string;
  approverManagerEmailId: string;
  approverManagerFirstName: string;
  approverManagerMiddleName: string;
  approverManagerLastName: string;
  userRole: string;
  resourceTypeId: number;
  resourceTypeName: string;
  createdByFirstName: string;
  createdByMiddleName: string;
  createdByLastName: string;
  modifiedByFirstName: string;
  modifiedByMiddleName: string;
  modifiedByLastName: string;
  modifiedDate: string;
  createdDate: string;

}
export class UserProfile {
  id: number;
  profileName: string;
  profileDescription: string;
  userProfileObjectAccess: Array<UserProfileObjectAccess>;
  expended?: boolean;
  objectAccessResponse?: Array<ObjectAccessResponse>
  uiNav?: Array<UINavigationModuleResponse>
  defaultPage: string;
  
}
export class UserProfileObjectAccess {
  id: number;
  userProfile: UserProfile;
  viewRights: boolean;
  viewAllRights: boolean;
  createRights: boolean;
  editRights: boolean;
  deleteRights: boolean;
  object: ObjectDefinition;
}
export class ObjectDefinition {
  objectName: string;
  packageName: string;
  objectLableName: string;
  bojectLabelDbName: string;
  objectUrl: string;
}
export class ObjectAccessResponse {
  objectAccessId: number;
  profileId: number;
  objectName: string;
  userProfileName: string;
  objectFullUrl: string;
  userProfileDescription: string;
  viewRights: boolean;
  viewAllRights: boolean;
  createRights: boolean;
  editRights: boolean;
  deleteRights: boolean;
  objectLableName?:string;
}
export class ResetpasswordRequest {
  userName: string;
  newPassword: string;
  confirmPassword: string;
}
export class ProfileRequest {
  profileId: number;
  profileName: string;
  profileDescription: string;
  objectAccessRights: Array<ProfileObjectAccessRequest>;
  uiId: Array<number>;
  defaultpage: number;
}
export class ProfileObjectAccessRequest {
  objectAccessId: number;
  profileId: number;
  objectName: string;
  createRights: boolean;
  editRights: boolean;
  viewRights: boolean;
  viewAllRights: boolean;
  deleteRights: boolean;
}
export class UINavigationModuleResponse {
  uiModulesId: number;
  name: string;
  url: string;
  icon: string;
  title: boolean;
  variant: string;
  parentId: number;
  hasPrevilage?: boolean;
}
export class UserRoleResponse {
  authorities: string;
  parentUserAuthorities: string;
  roleGroup: any;
}

export class PriceBookEntriesRequestInProducts {
  // isEditModeAddProduct: boolean;
  id: number;
  priceBookId: number;
  priceBookName: string;
  partCode: string;
  listPrice: number;
  isActive: boolean = true;
  active: boolean = true;
  standardPriceBook: boolean;
}
export class SSAResponse {
  id: number;
  name: string;
  circleName: string;

  createdByEmailId: string;
  createdByFirstName: string;
  createdByMiddleName: string;
  createdByLastName: string;
  createdDate: Date;
  modifiedByEmailId: string;
  modifiedByFirstName: string;
  modifiedByMiddleName: string;
  modifiedByLastName: string;
  modifiedDate: Date;
  ownerByEmailId: string;
  ownerByFirstName: string;
  ownerByMiddleName: string;
  ownerByLastName: string;

}
export class SSARequest {
  ssaid: number;
  circleName: string;
  ssaname: string;
}
export class SSAForlist {
  id: number;
  name: number;
}

export class PanIndiaProductSummary {
  productFamilyName: string;
  productType: string;
  productPrice: number;
  productFailySubProductName: string;
  pdoductDescription: string;
  qtyOnHand: number;
  productPartCode: string;
  qtyOk: number;
  qtyFaulty: number;
}

export class FileUploadResponse {
  fileDownloadUri: string;
  createdByEmailId: string;
  createdByFirstName: string;
  fileSize: string;
  createdByMiddleName: string;
  createdDate: string;
  fileContentType: string;
  fileName: string;
  createdByLastName: string;
}
export class FileUploadResponseCase {
  case_details_case_id:number;
  created_by_email_id:string;
  created_date:any;
  id:number;
  last_modified_by_email_id:string;
  last_modified_date:any;
  manual_id:any;
  owner_email_id:string;
  upload_physical_location:string;
  uploaded_file_name:string;
}

export class ToBeApprovedHistoryResponse {
  stageName: string;
  action: string;
  previousRemarks: string;
  stage: number;
  assignedToLastName: string;
  assignedToFirstName: string;
  assignedToEmailId: string;
  remark: string;
  approvedDate: string;
  assignedToMiddleName: string;
  assignedDate: string;
  actionByFirstName:string;
  actionByLastName:string;
  actionByMiddleName:string;
}
export class CaseInvoiceRequest {
  caseId: number;
  poNumber: string;
  poDate: any;
  invoiceNumber: string;
  invoiceDate: any;
  vendorInvoiceNumber: string;
  vendorInvoiceDate: Date;
}
export class CaseProofOfDeliveryRequest {
  caseId: number;
  podNumber: string;
  podDate: any;
  jmsNo:string;
  customer:string;
  podShippingMode: string = 'select';
  podShippingToName: string;
  podShippingTrackingNumber: string;
  podShippingExpectedDate: any;
}


export class QuotePdfHeaderAndFooterResponse {
  id: number;
  templateName: string;
  logo: string;
  signature: string;
  companyName: string;
  addressLine1: string;
  addressLine2: string;
  city: string;
  pincode: string;
  stateName: string = '0';
  cin: string;
  documentNumber: string;
  telephone: string;
  gstNumber: string;
  website: string;
  footerline1: string;
  footerline2: string;
  footerline3: string;
  footerline4: string;
  footerline5: string;
  createdByFirstName: string;
  createdByMiddleName: string;
  createdByLastName: string;
  createdByEmailId: string;
  createdDate: Date;
  modifiedByFirstName: string;
  modifiedByMiddleName: string;
  modifiedByLastName: string;
  modifiedByEmailId: string;
  modifiedDate: Date;
  active: boolean = true;
  enabled: boolean;
  termsAndConditions: Array<QuoteTermsAndConditions>;
  conuntryId: number;
  countryName: string;
  businessDomainId: number = 0;
  stateList1: Array<number>
  stateList: Array<StateWithQuotePdf>
  stateList2:number;
}

export class QuotePdfHeaderAndFooterRequest {
  id: number;
  templateName: string;
  logo: string;
  signature: string;
  companyName: string;
  addressLine1: string;
  addressLine2: string;
  city: string;
  pincode: string;
  stateName: string;
  cin: string;
  documentNumber: string;
  telephone: string;
  gstNumber: string;
  website: string;
  footerline1: string;
  footerline2: string;
  footerline3: string;
  footerline4: string;
  footerline5: string;
  active: boolean;
  conuntryId: number = 0;
  countryName: string;
  businessDomainId: number = 0;
  // stateList: Array<any>;
  stateList:Array<StateWithQuotePdf>
  // statusId:number;
  stateList2:number;
}
export class QuoteTemplateEnableAndDisableRequest {
  id: number;
  active: boolean;
}

export class QuoteTermsAndConditions {
  id: number;
  rule: string;
  serialNumber: string;
  objectName: string;
  isActive: boolean;
  createdBy: EmployeeDetails;
  createdDate: Date;
  modifiedBy: EmployeeDetails;
  modifiedDate: Date;
}

export class TermsAndConditionsResponse {
  id: number;
  rule: string;
  serialNumber: number;
  objectName: string;
  isActive: boolean;
  createdByFirstName: string;
  createdByMiddleName: string;
  createdByLastName: string;
  createdDate: Date;
  modifiedByFirstName: string;
  modifiedByMiddleName: string;
  modifiedByLastName: string;
  modifiedDate: Date;
}


export class ApprovalWorkflowRequest {
  id: number;
  // objectName: string = null;
  objectClass: string;
  // objectIdFiledName: string;
  approvalStages: number = 0;
  descriptions: string;
  active: boolean;
  entryCriteria: string;
  businessDomainId: number = 0;

  initialSubmitterList: Array<any>
}
export class SimpleResponseMessage {
  messgae: string;
}
export class WorkOrderRequest {
  workOrderNumber: string;
  workOrderDate: Date
  quoteId: number;
}
export class StateWithQuotePdf {
  id: number;
  state: StateProvinceDetails;
  quotePdfHeaderAndFooter: QuotePdfHeaderAndFooterResponse;
  createdBy: EmployeeDetails;
  createdDate: Date;
  modifiedDate: Date
  BusinessDomain: BusinessDomain;


}
export class RoleClass {

  labelsValue: string;
  valuesValue: string;
}
export class ApprovalStepRequest {
  id: number
  approvedBy: string
  approvedStage: number = 0;
  approvalId: number
  objectFieldName: string
  onApprovedValue: string
  onRejectedValue: string
  stepName: string;
  //  userrole:string;
  //  authorities:string;
  //  userEmailId:string;
}
export class BillOfMaterialRequest {
  id: number;
  productPartCode: string;
  requiredQuantity: number;
  locationName: Array<string>;
  drawaingRef: Array<string>;
}
export class BillOfMaterialResponse {
  locationName: string;
  requiredQuantity: number;
  rawProductDescription: string;
  rawProductType: string;
  locId: number
  rawProductPartCode: string;
}
export class combinedList {
  drawingRef: string;
  location: string;
}
export class RepairDCTResponse {
  dctRepairId: number;
  serialNumber: string;
  productPartcode: string;
  productDescription: string;
  repariDate: Date;
  repairByFirstName: string;
  repairByMiddleName: string;
  repairByLastName: string;
  repairByEmailId: string;
  createdByFirstName: string;
  createdByMiddleName: string;
  createdByLastName: string;
  createdByEmailId: string;
  modifiedByFirstName: string;
  modifiedByMiddleName: string;
  modifiedByLastName: string;
  modifiedByEmailId: string;
  ownerFirstName: string;
  ownerMiddleName: string;
  ownerLastName: string;
  ownerEmailId: string;
  createdDate: Date;
  lastModifiedDate: Date;
  previousStateId: number;
  previousStateName: string;
  currentStateId: string;
  currentStateName: string;
  totalRepairAmount: number;
}
export class DCTRepairRequest {
  partCode: string;
  serialNumber: string;
  productStatus: number = 0;
  items: Array<DctRepairConsumedItemsRequest>;
  remarks: string;
  jobCardId: Number;
  problemmaster: Array<any>;
  heatRunStatus: string='OK';
  faultObservedInHeatRun: string='NA';
  reworkAfterFail: string='NA';
  finalStatus: string='OK';
  repariEng: string;

}
export class DctRepairConsumedItemsRequest {
  locationId: number;
  qty: number;
  serialNumber: Array<string>;
}
export class DctRepairConsumedItemsResponse {
  id: number;
  partcode: string;
  productDescription: string;
  location: string;
  quantity: number;
  repairAmount: number;
}
export class dctRepairItemList {
  rawParcode: string;
  quantity: number;
  serialNO: Array<string>;
  description: string;
  location: string;
  locationid: number;
}
export class WccRequest {
  id: number
  circleName: string
  wccTypeId: number = 0;
  startDate: string;
  toDate: string;
  saIds: Array<wccSAReq>;
}
export class WCCResponse {
  caseReasontails: string;
  caseType: string;
  circleName: string;
  createdDate: string;
  createdByEmailId: string;
  createdbyMiddleName: string;
  createdbyLastname: string;
  createdbyFirstName: string
  id: number;
  lastModifiedByEmailId: string;
  lastModifiedDate: string;
  modifiedByLastName: string;
  modifiedByFirstName: string;
  modifiedByMiddleName: string;
  manulId: string;
  serviceCenter: string;
  wccStatus: string;
  vendorName: string;
  vendorSapNumber: string;
  vendorUserCode: string;
  vendorUserFirstName: string;
  vendorUserLastName: string;
  vendorUserMiddleName: string;
  wccType: string;
  wccTypeId: number;
  toDate: string;
  fromDate: string;
  invoiced:boolean;

}
export class LocationPartCodeSerialNumber {
  productPartCode: string;
  productStateId: number;
  productStateName: string;
  serialNumber: string;
}
export class WccTypes {
  id: number;
  wccTypeName: string;
}
export class WccSA {
  appointmentNumbr: string;
  caseId: number;
  caseReason: string;
  caseType: string;
  circleName: string
  customerName: string;
  customrSAPNumber: string;
  serviceRsource: string;
  service_appointment_id: number;
  SAtrue: boolean;
  caseCloseDate: string;
  poDetailsId: any;
  poNumber: any;
  appointmentNumber: string;
  amount: any;
  taxRate: any;
  taxAmount: any;
  totalAmount: any;
  id : number;
}
export class POType {
  poTypeName: string;
  poTypeId: number;
}
export class VendorPOcategory {
  VendorPoCategoryId: number;
  vendorPoCattegoryName: string;
}
export class VendorInvoiceRequest {
  id: number;
  manualId: string;
  wccId: number;
  vendorInvoiceNumber: string;
  vendorInvoiceDate: string;
  vendorPoId: number;
  remarks: string;

}
export class vendorInvoiceResponse {
  entrySheetNumber: string;
  wccManualId: string;
  glCode: string
  grnDate: string
  grnNumber: string;
  invoiceAmount: number;
  invoiceCreatedByEmailId: string;
  invoiceCreatedDate: string;
  invoiceId: number;
  invoiceLastModifiedDate: string;
  invoiceLastModifiedEmailId: string;
  invoiceManualId: string;
  invoiceOnwerEmailId: string;
  invoicePoDate: string;
  invoiceRemarks: string;
  invoiceStatus: string;
  invoiceStatusId: number;
  invoiceVendorId: string;
  poBusineesArea: string;
  poCategoryId: number;
  poCircleName: string;
  poCodeForInternalRef: string;
  poCostCentre: string;
  poDate: string;
  poExpiryDate: string;
  poNumber: string;
  poRemainingQuantity: number;
  poTotalQuantity: number
  povendorCode: string;
  povendorName: string;
  vendorInvoiceDate: string;
  vendorInvoiceNumber: string;
  vendorName: string;
  vendorPoID: number;
  wccDetailsId: number;
  sapNumber: number;
  sapDate: string;
  invoiceCopyUrl: string;
  internalCodeId: number;
  internalServiceCode: string;
  ModifiedDate : string;
  CreatedDate : string;
}
export class WccPendingInvoice {
  wccId: number;
  wccTypeId: number;
  wccManualId: string;
}
export class PODetails {
  businessArea: string;
  categoryId: number
  categoryName: string;
  costCentre: string;
  expiryDate: string;
  glCode: string;
  id: number;
  internalRefCode: string;
  poActive: boolean;
  poActivityPrice: number;
  poCircleName: string;
  poDate: string;
  poNumber: string
  poStartDate: string;
  poTypeId: number;
  poTypeName: string;
  remainingQuantity: number;
  totalQuantity: number;
  vendorCode: string;
  vendorName: string;
}
export class VendorCommRequest {
  grnNumber: string;
  grnDate: string;
  entrySheetNumber: string;
  vendorInvoiceId: number;
  internalServiceCode: any;
}
export class PODetailsbywcctypeID {
  active: boolean;
  activityNumber: number
  activityPrice: number
  activityPriceListDescription: string;
  busineesArea: string;
  codeForInternalRef: string;
  costCenterDescription: string;
  costCentre: string;
  exipirtyDate: string;
  glCode: string;
  glCodeDescription: string
  id: number
  poNumber: string;
  poStartDate: string;
  poTypeId: string;
  remainingQuantity: string;
  serviceActivityCode: string;
  serviceActivityDescription: string;
  totalQuantity: number;
  vendorCode: string;
  vendorName: string;
}
export class wccSAReq {
  saId: number;
  poId: number;
}
export class ActivityDesc {
  activityPriceListDescription: string;
  id: number;
}
export class VendorInvSAPreq {
  vendorInvoiceId: number;
  sapNumber: string;
  sapDate: string;
}
export class PendingSAIDs {
  id: number;
}
export class GLCodeResponse {
  id: number;
  name: string;
  totalAmount: number;
}
export class ExpenseAccountsOnlyRequest {
  expenseManagementId: number;
  businessAreaId: number = 0;
  businessPlaceId: number = 0;
  documentDate: string
  narration: string;
  postPark:boolean;
}
export class MailResponse {
  id: number;
  logBy: string;
  logDate: string;
  mailContent: string;
  sendFrom: string;
  sendTo: string;
  sendCc: string;
  subject: string;
  objectName: string;
  objectId: string
  attachemt: Array<string>;
}

export class ActionHistoryObjectAndId {
  objectName: string;
  objectId: any;
}

export class ActionHistoryResponse {
  object_id: string;
  key_name: string;
  object_name: string;
  fieldLabelName: string;
  old_value: string;
  value: string;
  logged_by_email_id: string;
  first_name: string;
  middle_name: string;
  last_name: string;
  created_date: string;
}
export class PreRegCaseResponse {
  prcCaseId: number = 0;
  prcCaseManualId: string;
  caseRecordTypeId: number = 0;
  caseRecordTypeName: string;
  caseTypeId: number;
  verticalTypeId: number = 0;
  verticalTypeName: string;
  caseTypeName: string;
  caseReasonId: number = 0;
  caseReasonName: string;
  caseReasonVerticalName: string;
  caseReasonVerticalId: number = 0;
  assetsSerialNumber: string;
  assetsPartCode: string;
  assetsName: string;
  caseStatusName: string;
  caseStatusId: number;
  caseStageName: string;
  caseStageId: number;
  caseOriginName: string;
  caseOriginId: number = 0;
  casePriorityName: string;
  casePriorityId: number = 0;
  problemReported: Array<any>;
  contactPersonId: number = null;
  contactPersonFirstName: string;
  contactPersonMiddleName: string;
  contactPersonLastName: string;
  contactPersonEmailId: string;
  noOfSmr: string;
  remarks: string = '';
  createdByEmailId: string;
  createdByFirstName: string;
  createdByMiddleName: string;
  createdByLastName: string;
  createdDate: Date;
  closedDate: Date;
  modifiedByEmailId: string;
  modifiedByFirstName: string;
  modifiedByMiddleName: string;
  modifiedByLastName: string;
  modifiedDate: Date;
  otherProblemReported: string;
  contactPersonContactNo: string;
  dateOfVisit: Date;
  dateTimeOpened: Date;
  dateTimeClosed: Date;
  rjil_TT01_No: string;
  pendingResponsibilityId: number = 1;
  pendingResponsibility: string;
  SLATime: string;
  podnumber: string;
  poddate: Date;
  ponumber: string;
  podate: Date;
  invoiceNumber: string;
  invoiceDate: Date;
  vendorInvoiceNumber: string;
  vendorInvoiceDate: Date;
  callCenterRepFirstName: string;
  callCenterRepMiddleName: string;
  callCenterRepLastName: string;
  accountSapNumber: string;
  accountName: string;
  siteManualId: string;
  siteName: string;
  siteId: number;
  caseStageVerticalName: string;
  caseStageVerticalId: number = 0;
  serviceResourceId: number = 0;
  serviceResouceContactId: number = 0;
  serviceResourceName: string;
  serviceCenterId: number;
  serviceCenterName: string;
  missingAssets: string;
  isAccountSelected: any;
  caseOwner: string;
  caseOwnerFirstName: string;
  caseOwnerMiddleName: string;
  caseOwnerLastName: string;
  inputFromEng: string;
  internalRemark: string;
  isAssignByAssignmentRule: boolean = true;
  assetDescription: string;
  lastAction: string;
  lastActionComment:string;
  caseId: number;
  caseManualId: string;
  iSmsSend:boolean

}
export class PreRegCaseRequest {
  inputFromEng: string;
  internalRemark: string;
  missingAssets: string;
  caseId: number;
  siteId: number;
  caseManualId: string;
  isAssignByAssignmentRule: boolean;
  caseRecordTypeId: number = 0;
  caseRecordTypeName: string;
  caseTypeId: number;
  caseReasonId: number = 0;
  assetsSerialNumber: string;
  caseStageId: number = 0;
  caseStatusId: number = 0;
  caseOriginName: string;
  caseOriginId: number = 0;
  casePriorityName: string;
  casePriorityId: number;
  caseProblemReported: Array<ProblemReported>;
  contactPersonId: number;
  noOfSmr: string;
  remarks: string;
  ownerEmailId: string;
  legalEntityName: string;
  legalEntityID: string;
  accountSapNumber: string;
  accountName: string;
  pendingResponsibility: number;
  caseTypeName: string;
  caseReasonName: string;
  serviceCenterId: number = 0;
  serviceResourceId: number = 0;
  rjiltt01No: string;
  otherProblemReported: string;
  poNumber: string;
  poDate: Date;
  iSmsSend:boolean;
}
export class Repaireng {
  emailId: string;
  firstName: string;
  lastName: string;
  middleName: string;
  fullName?: string
}
export class PrcActionRequest {
  action: string;
  comment: string;
  caseid: number;
}
export class SAAdvanceSearchData {
  sanumber: string;
  casenumber: string;
  site: string;
  ctype: string;
  creason: string='';
  circle: string;
  assets: string;
  status: string;
  cstatus: string='';
  srs: string;
  crdf: string;
  crdt: string
  show: boolean;
}
export class HelpTutorial{
   id:number;
   topic:string;
	 platForm:string;
	 stackHolder:string;
	 lanuguage:string;
	 url:string;
}
export class RejectCaseRequest{
  caseManualId:string;
  remark:string;
  file:string;
}

export class PartcodeChangeRequest{
  approved_status:boolean;
  assigned_to:string;
  circle:string;
  consumed_product_description:string;
  consumed_product_price:string;
  consumed_product_product_part_code:string;
  consumed_product_serial_number:string;
  create_date:string;
  created_by_email_id:string;
  engineer_name:string;
  faulty_product_description:string;
  faulty_product_price:number;
  faulty_product_product_part_code:string;
  faulty_product_serial_number:string;
  flow_type:string;
  id:number;
  initiated_by_email_id:string;
  modified_by_email_id:string;
  modified_date:string;
  price_variance:string;
  request_date:string;
  sa_id:number;
  sa_manual_id:string;
  open:boolean;
  remarks:string;
}
export class caseLogReject{
  id:number;
	caseManualId:string;
	 remark:string;
	 fileName:string;
	 fileUrl:string;
	 fileContentType:string;
	 fileSize:number;
	 createdByFirstName:string;
	 createdByLastName:string;
	 createdByMiddleName:string;
	 createdByEmailId:string;
	 createdDate:any;
	
}
export class ChangecaseTypeRequest{
   caseManualId:string;
	 caseType:string="";
	 caseReason:string="";
	 poNo:string;
	 poDate:any;
}
export class ChangeCase_caseTypeList{
  caseType:string;
}
export class ReportWizard{
createdBy:string;
createdDate:string;
id:number
lastModifiedBy: string;
lastModifiedDate:string;
presentationType:string;
reportDescription: string;
reportName: string;
reportQuery:string
reportType:string;
report_name:string;
report_description:string;
bothvalues:string;
params: Array<any>
executers: Array<any>
reportLinkId:any;
}
export class ReportLink{
  id:number;
  report_description:string;
  report_name:string;
  bothvalues?:string;
}
export class reportRequest{
  
    id:number;
    reportName: string;
    reportQuery: string
    reportDescription:string;
    reportType: string;
    presentationType: string;
    params: Array<any>=[];
    executers:Array<any>=[];
    reportLinkId:any;
    
    // report_name:string;
    // report_description:string;
    // bothvalues?:string;

}
export class ExpenseStatus{
  statusName:string;
  statusId:number;
}
export class LastActionForList{
  action:string;
  id:number;
}
export class ProductType{
  typeName:string;
  typeId:number;
  synced:boolean;
}
export class QueteStatus{
  statusName:string;
  id:number;
}
export class SAStatus{
  statusname:string;
  statusId:number;
}
export class LPRStatus{
  name:string;
  id:number;
}
export class InvoiceStatus{
  statusname:string;
  statusid:number;
}
 export class TableHeader{
  id:number;
  name:string;
  sortColumnName?:string;
  sortOrder?:string;
  icon?:string
}
export class ServiceOrderReturnResponse{
  createdByEmailId: string;
createdByName: string;
createdDate: string;
destinationLocationId: number;
destinationLocationName: string;
id: number;
manualId: string;
modifiedByEmailId:string;
modifiedByName:string;
modifiedDate: string;
ownerEmailId:string;
ownerName: string;
receivedQty: number;
returnedQty: number;
shipToName: string;
shippingMode:string;
sourceLocationId: number;
sourceLocationName:string;
status:string;
trackingNumber:string;

}
export class ServiceOrderReturnItemResponse{
  circleName: string;
customerName: string;
customerSAP: string;
finalProductStateName:string;
isCustomerInventory: boolean;
itemSerialNumber: string;
lotNo:string;
previousProductFinalStateName:string;
previousStatteId: number;
productDescription: string;
productFinalStateId: number;
productPartCode: string;
received: boolean;
remarks:string;
returnIdServiceOrder:number;
reurnItemId:number;
serviceOrderId:number;
serviceOrderManualId:string;
}
export class PTResponse{
  createdByEmailId:string;
createdByName:string;
createdDate: string;
deliv: any;
deliveryDate:string;
destinationLocationId:number;
destinationLocationName: string;
id:number;
invoice: null
manualId: string;
mblnr_g: any;
mjahr_g: any;
modifiedByEmailId:string;
modifiedByName: string;
modifiedDate: string;
ownerEmailId: string;
ownerName:string;
productRequestId:number;
productRequestManualId: string;
receivedDate:any;
receivedQty: number;
remark_d: number;
remark_g: number;
remark_i: number;
shippingMode:string;
shippingToName:string;
sourceLocationId:number;
sourceLocationName:string;
status: string;
trackingNumber:string;
transferQty: number;
}
export class LptransferOkSRN_details{
ModifiedDate: string;
OwnerEmail: string;
Serial:string;
role:string;
}