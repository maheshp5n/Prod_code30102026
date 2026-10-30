export class Product {
  id: number;
  partcode: string;
  productDescription: string;
  subProductFamily: number = 0;
  productType: string = '';
  productPrice: string = null;
  productFamilyId: number = 0;
  subProductFamilyId: number = 0;
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
export class ProductDetailResponse {
  createdByEmailId: string;
  productType: string;
  productFamilyId: number;
  active: boolean;
  lastModifiedDate: string
  id: number;
  lastModifiedByEmailId: string;
  ownerEmailId: string;
  subProductFamilyName: string;
  hsnCode: string;
  createdDate: string;
  productDescription: string;
  productFamilyName: string;
  partCode: string;
  subProductFamily: number;
  synced: boolean;
  productPrice: string;

}
export class UpdateProductStatus {
  Id: number;
  active: boolean;
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
export class BTAssetForLIst {
  assetId: string;
  serialNumber: string;
  productDescription: string;
  productPartCode: string;
  productId: number;
}
export class BtAssetsStatus {
  statusname: string;
  statusid: number;
}
export class BTassetResp {
  id: number;
  serialNumber: string;
  productPartCode: any;
  productDescription: string;
  productSubProductId: string;
  productID: number;
  productSubProductName: string;
  productFamilyId: string;
  productFamilyName: string;
  accountSapNumber: string;
  accountName: string;
  plantCode: string;
  plantName: string;
  siteId: number
  siteManualId: string;
  siteName: string;
  primaryAccountSapNumber: string;
  primaryAccountName: string;
  assetsStatusName: string;
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
  warrantyExpiryDate: string;
  installExpiryDate: string;
  dateOfDeliveryExpiryDate: string;
  amcPurchaseOrderNumber: number;
  amcPurchaseOrderDate: string;
  amcStartDate: string;
  amcExpiryDate: string;
  amcPeriod: string;
  createdByEmailId: string;
  createdDate: string;
  modifiedByEmailId: string;
  modifiedDate: string;
  createdByName: string;
  modifiedByName: string;
  circleName: string;
  isWarrantyExist: number;
  isAMCWarrantyExist: number;
  Warranty: string;
  source: string;
  actlSerialNumber: string;
  plant: string;

}
export class BTAssetsForList {
  id: number;
  serialNumber: string;
  productPartCode: string;
  productDescription: string;
  asstPartCode: string;
}
export class BTasseReq {
  assetsId: number;
  serialNumber: string;
  productPartCode: number;
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
  amcPurchaseOrderNumber: number;
  amcPurchaseOrderDate: string;
  amcStartDate: string;
  amcExpiryDate: string;
  amcPeriod: string;
  productId: number
}

export class BatNewProductRequest {
  productRequestId: number;
  sourceLocationId: number;
  destinationLocationId: number;
  needByTime: Date;
  description: string;
  items: Array<ProductRequestItem> = [];
}
export class ProductRequestItem {
  btProductId: number;
  requiredQty: number;
  itemRequiredDescription: string;
  partCode: string;
  productRequestId: number;
}


export class BTsiteCreateReq {
  siteManualId: string;
  accountSapNumber: string;
  customer: string;
  siteName: string;
  serviceCenterid: number;
  serviceResourceId: number;
  circleName: string;
  ssaId: number;
  clusterDistrict: string;
  siteDescription: string;
  siteAddress: string = " ";
  primaryAccountSapNumber: string;
  infraId: string = " ";
  siteId: number;
  customerName: string;
  zoneName: string;
  primaryAccountName: string;
  ssaName: string;
  serviceCenterName: string;
  accountName: string;
  cmp: string = " ";
  rjilJcName = "";


}

export class GetAllBTsiteResp {
  accountName: string;
  siteAddress: string = "";
  lastModifyEmailId: string;
  createdLastName: string;
  createdDate: Date;
  zoneName: string;
  ownerEmailId: string;
  serviceResourceId: number;
  createdEmailId: string;
  infraId: string = "";
  ownerMiddleName: string;
  lastModifiedDate: Date;
  serviceResourceName: string;
  siteId: number;
  circleName: string;
  lastModifyLastName: string;
  lastModifyFirstName: string;
  circleId: string;
  createdMiddleName: string;
  siteManualId: string;
  primaryAccountSapNumber: string;
  ssaName: string;
  ownerLastName: string;
  serviceCenterId: number;
  clusterDistrict: string;
  primaryAccountName: string;
  siteDescription: string;
  customer: string;
  accountSapNumber: string;
  ssaId: number;
  lastModifyMiddleName: string;
  ownerFirstName: string;
  createdFirstName: string;
  siteName: string;
  serviceCenterName: string;
  siteCmp: string = "";
  RjilJcName: string = "";
}
export class ProductReqItem {
  id: number;
  requestQuantity: number;
  partCode: string;
}

export class BtProductRequestResponse {
  productRequestSourceLocationId: number;
  productRequestSourceLocationName: string;
  productRequestDummayStatus: string;
  productRequestCreatedByEmailId: string;
  productRequestDestinationLocationPlant: string;
  productRequestScynced: boolean;
  productRequestNeedByDateTime: any;
  productRequestTotalQuantityIssued: string;
  productRequestCreatedDate: string;
  productRequestStatus: string;
  productRequestId: number;
  productRequestTotalQuantityRequired: number;
  productRequestTotalQuantityReceived: number;
  productRequestDestinationLocationId: number;
  productRequestLastModifiedDate: string;
  productRequestDestinationLocationName: string;
  productRequestDescription: string;
  productRequestSourceLocationPlant: string;
  productRequestManualId: string;
  productRequestLastMdifiedByEmailId: string;
  sourceLocationId: number;
  productRequestSubmittedDate: string;
  lastModifiedByFirstName: string;
  lastModifiedByMiddleName: string;
  lastModifiedByLastName: string;
  productRequestOwnerEmailId: string;
  ownerFirstName: string;
  ownerMiddleName: string;
  ownerLastName: string;
  destinationLocationOwnerEmailId: string;
  createdByFirstName: string;
  createdByMiddleName: string;
  createdByLastName   : string;
  modifiedByEmail : string;
}
export class BatteryProductRequestDetail {
  prId: number;
  prManualId: string;
  destinationLocationId: number;
  destinationLocationName: string;
  sourceLocationId: string;
  sourceLocationName: string;
  createdDate: string;
  description: string;
  needByDateTime: string;
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
  lastModifiedByEmail: string;
  lastModifiedByFirstName: string;
  lastModifiedByMiddleName: string;
  lastModifiedByLastName: string;
  modifiedDate: string;
  sourceLocationOwnerEmailId: string;
  destinationLocationOwnerEmailId: string;

}
export class BtItemListByPrId {
  productRequestItemProductPartcode: string;
  productRequestItemProductType: string;
  productRequestItemLastModifiedByEmailId: string;
  productRequestItemCreatedByEmailId: string;
  productRequestItemId: number;
  productRequestItemLastModifiedDate: string;
  productRequestId: number;
  productRequestItemStatus: string;
  productRequestItemManualId: string;
  productRequestItemIssuedQuantity: number;
  productRequestItemProductPrice: number;
  productRequestItemReceivedQuantity: number;
  productRequestItemDescription: string;
  productRequestItemIsDisabled: boolean;
  productRequestItemProductId: number;
  productRequestItemCreatedDate: string;
  productRequestItemRequiredQuantity: number;
  productRequestItemIsSynced: boolean;
  productRequestItemProductDescription: string;
}
export class BtProductList {
  id: string;
  partCode: string;
  productType: string;
  bothvalues: string;
  productDescription: string;
}
export class AddItemtoPrductRequest {
  productRequestId: Number;
  btProductId: Number;
  itemRequiredDescription: string;
  requiredQty: Number;
}
export class SubmitProductRequest {
  productrequestid: number;
}
export class PendingLocationLIstForTransfer {
  locationId: number;
  plantCode: string;
  locationName: string;
}

export class BtPendingItemLIstASPerLocation {
  productRequestId: number;
  productRequestManualId: string;
  productRequestItemIssuedQty: number;
  productRequestItemRequiredQty: number;
  productManualId: string;
  productRequestItemId: number;
  productId: number;
  productRequestItemManualId: string;
  product_type: string;
  issueingQuantity: number;
  okQuantity: number;
  productDescription: string;
}

export class BtProductTransferItemRequest {
  btTransferItemId: number;
  transferQuantity: number;
}
export class BtProductTransferRequest {
  destinationLocationId: number;
  shippingToName: string;
  trackingNumber: string;
  shippingMode: string;
  actualDeliveryDate: Date;
  items: Array<BtProductTransferItems> = [];
}
export class BtProductTransferItems {
  productRequestItemId: number;
  issueQuantity: number;
}
export class BtProductTransferResponse {
  productTrnsferId: number;
  productRequestDestinationLocationName: string;
  productTransferLastModifiedByEmailId: string;
  productTransferCreatedDate: string;
  productTraansferRemarkG: string;
  productTransferReceivedDate: string;
  productTransferSapDeliv: string;
  productTransferSapMjahrG: string;
  productTransferManualId: string;
  productTransferActualDeliveryDate: string;
  productRequestSourceLocationId: number;
  productTransferSapMblnrg: string;
  productTransferLastModifiedDate: string;
  productRequestDestinationLocationId: number;
  productTransferRemarkI: string;
  productTransferShippingMode: string;
  productRequestSourceLocationName: string;
  productTransferCreatedByEmaiilId: string;
  productTransferSapInvocieNumber: string;
  productTransferShippingToName: string;
  productTranferTrackingNumber: string;
  productTransferRemarkD: string;
  productTransferStatus: string;
}

export class BtProductTransferItemList {
  productDescription: string;
  productPartcode: string;
  productRequestDestinationLocationId: string;
  productRequestDestinationLocationName: string;
  productRequestId: string;
  productRequestManualId: string;
  productRequestSourceLocationId: string;
  productRequestSourceLocationName: string;
  productTraansferRemarkG: string;
  productTranferTrackingNumber: string;
  productTransferActualDeliveryDate: string;
  productTransferCreatedByEmaiilId: string;
  productTransferCreatedByFirstName: string;
  productTransferCreatedByLastName: string;
  productTransferCreatedByMiddleName: string;
  productTransferCreatedDate: string;
  productTransferLastModifiedByEmailId: string;
  productTransferLastModifiedByFirstName: string;
  productTransferLastModifiedByLastName: string;
  productTransferLastModifiedByMiddleName: string;
  productTransferLastModifiedDate: string;
  productTransferManualId: string;
  productTransferOwnerFirstName: string;
  productTransferOwnerLastName: string;
  productTransferOwnerMiddleName: string;
  productTransferReceivedDate: string;
  productTransferRemarkD: string;
  productTransferRemarkI: string;
  productTransferSapDeliv: string;
  productTransferSapInvocieNumber: string;
  productTransferSapMblnrg: string;
  productTransferSapMjahrG: string;
  productTransferShippingMode: string;
  productTransferShippingToName: string;
  productTransferStatus: string;
  productTransferTotalQuantityIssued: string;
  productTransferTotalQuantityReceived: string;
  productTrnsferId: number;
  productType: string;
}

export class GetProductTransferItems {
  transferQuantity: number;
  productType: string;
  productRequestManualId: string;
  productDescription: string;
  transferItemManualId: string;
  productId: number;
  productPartcode: string;
  productRequestItemManualId: string;
  productRequestId: number;
  transferReceivedQuantity: number;
  productRequestItemId: number;
  transferItemReceivedQuantity: number;
  transferItemTransferedQuantity: number;
  receivingQty: number;
}
export class GetPrTransferItemsResponse {
  btpRequestItemId: number;
  btpRequestItemManualId: string;
  partCode: string;
  partDescription: string;
  quantityRequired: number;
  quantityReceived: number;
  quantityIssued: number;
  productType: string;
  itemStatus: string;
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
  disabled: boolean;
  btRequestId: number;
  btRequestManualId: string;
}
export class BtProductReturnAddItemRequest {
  productId: number = 0;
  customerInventory: number;
  exicomIvnentory: number;
}
export class BtProductTransferRecieveRequest {
  transferItemId: number;
  quantity: number;
}
export class BtProductReturnRequest {
  returnOrderDestinationLocationId: number;
  returnOrderDate: Date;
  returnOrderShiptoName: string;
  returnOrderShippingMode: string;
  returnOrderTrackingNumber: string;
  items: Array<ProductReturnItems> = [];
}
export class ProductReturnItems {
  customerInventory: number;
  productId: any;
  exicomIvnentory: number;
}
export class BtProductReturnResponse {
  createdDate: string;
  id: number;
  returnOrderActualSendDate: string;
  returnOrderCreatedByFirstName: string;
  returnOrderCreatedByLastName: string;
  returnOrderCreatedByMiddleName: string;
  returnOrderDestinationLocationId: number;
  returnOrderLastModifiedByFirstName: string;
  returnOrderLastModifiedByLastName: string;
  returnOrderLastModifiedDate: string;
  returnOrderLastModifiedMiddleName: string;
  returnOrderManualId: string;
  returnOrderOwnerFirstName: string;
  returnOrderOwnerMiddleName: string;
  returnOrderShipToName: string;
  returnOrderShippingName: string;
  returnOrderSourceLocationId: number;
  returnOrderSourceLocationName: string;
  returnOrderStatus: string;
  returnOrderTotalQuantityReceived: number;
  returnOrderTotalQuantityReturned: number;
  returnOrderTrackingNumber: string;
  returnOrderownerLastName: string;
  returnOrderDestinationalLocationName: string;
}
export class BtProductReturnReceiveRequest {
  returnOrderItemId: number;
  customerQuantity: number;
  exicomQuantity: number;
}
export class BtProductReturnItemsResponse {
  productId: number;
  productManualId: string;
  returnOrderDestinationLocationId: number;
  returnOrderDestinationLocationName: string;
  returnOrderId: number;
  returnOrderItemCreatedByEmailId: string;
  returnOrderItemCreatedDate: string;
  returnOrderItemCustomerQuantityReceived: string;
  returnOrderItemCustomerQuantityReturned: string;
  returnOrderItemId: number;
  returnOrderItemLastModifiedByEmailId: string;
  returnOrderItemManualId: string;
  returnOrderItemModifiedDate: string;
  returnOrderItemOwnerEmailId: string;
  returnOrderItemQuantityReceived: number;
  returnOrderItemQuantityReturned: number;
  returnOrderManualId: string;
  returnOrderShipToName: string;
  returnOrderShippingMode: string;
  returnOrderSourceLocationId: number;
  returnOrderSourceLocationName: string;
  returnOrderTrackingNumber: string;
}
export class BtMyInventoryResponse {
  customerTotalBerQuantity: number;
  customerTotalFaultyQuantity: number;
  customerTotalOkQuantity: number;
  customerTotalQuantity: number;
  customerTotalRejectedQuantity: number;
  customerTotalScrapQuantity: number;
  exicomTotalBerQuantity: number;
  exicomTotalFaultyQuantity: number;
  exicomTotalOkQuantity: number;
  exicomTotalQuantity: number;
  exicomTotalRejectedQuantity: number;
  exicomTotalScrapQuantity: number;
  locationName: string;
  locationPlant: string;
  productDescription: string;
  productId: string;
  productItemManualId: string;
  productPartcode: string;
  productType: string;
}

export class BatteryExpenseManagementRequest {
  businessDomainID: number = 0;
  businessDomainId: number = 0;
  divCostCenters: number = 0;
  expenseManagementId: number;
  expensePurposeId: number = 0;
  expenseTypeId: number = 0;
  costCenterId: number = 0;
  purposeOfVisit: string = '';
  remark: string = "";
  narration: string = '';
  businessAreaId: number = 0;
  businessPlaceId: number = 0;


}

// export class BatteryExpenseManagementEditRequest{
//   id: string;	
//   contactId: string;	
//   document_date: string;
//   expensePurposeId: string;	
//   expenseTypeId: string;	
//   costCenterId: string;	
//   purposeOfVisit: string;	
//   statusId: string;	
//   remark: string;
//   narration: string;	
//   businessAreaId: string;	
//   businessPlaceId: string;
//   isPost: string;	
//   isExpenseSynced: string;
//   advanceAmount: string;
//   visibleToFinance: string;	
//   isSynced: string;	
//   businessDomainID: string;
// }

export class BatteryExpenseManagementResponse {
  // "businessAreaId":14,
  // "totalLodgingAmount":0.0,
  // "createdByEmailId":"sch@exicom.in",
  // "visibleToFinance":null,
  // "totalBpAmount":0.0,
  // "totalBoardingDays":0.0,
  // "expensePurposeId":null,
  // "businessAreaName":"4500",
  // "remark":"working",
  id: number
  // "isPost":false,
  // "purposeOfVisit":"testing",
  manualId: string;
  // "isExpenseSynced":false,
  // "totalTourAmount":0.0,
  // "tourEndTime":null,
  // "totalLocalConveyanceAmount":0.0,
  createdByName: string;
  // "tourStartDate":null,
  // "businessDomainId":null,
  // "eligibleTourDays":0.0,
  // "totalBoardingAmount":0.0,
  // "statusId":14,
  // "totalLodgingDays":0.0,
  // "totalTourAmountCompanyPaid":0.0,
  // "documentDate":null,
  // "advanceAmount":0.0,
  // "netAmountToPay":0.0,
  // "expensePurposeName":null,
  lastModifiedDate: Date;
  // "ownerEmailId":"sch@exicom.in",
  // "totalTourDays":0.0,
  // "employeeCode":null,
  // contacyId:number;
  // "statusName":"Rejected",
  // "businessPlaceId":14,
  isSynced: boolean;
  // "costCenterName":"EV M-Serv Direct / 3202010103",
  // "totalLodgingCompanyPaid":0.0,
  // "tourEndDate":null,
  // "costCenterId":14,
  contactName: string;
  // "createdDate":"2021-06-16T05:41:36.000+0000",
  // "totalOtherChargesAmount":0.0,
  // "gradeName":"M1",
  // "expenseTypeName":null,
  // "totalCompanyPaid":0.0,
  // "tourStartTime":null,
  ownerName: string;
  // "narration":null,
  modifiedByName: string;
  // "expenseTypeId":null,
  businessDomainName: string;
  // "businessPlaceName":"1300-Maharashtra",
  // "totalExpense":0.0,
  // "modifiedByEmailId":"sch@exicom.in"


  ///////////////////////////////////////////////////////////
  totalAmount: number;
  businessDomainID: number = 0;
  businessDomainId: number = 0;
  // next: boolean = false;
  divCostCenters: number = 0;
  expenseManagementId: number = 0;
  // expenseManagementManualId: string;
  contactId: number = null;
  employeeFirstName: string;
  employeeLastName: string;
  employeeFullName: string;
  // contactFirstName: string;
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
  contactDesignation: string;

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
  postPark: boolean;

}

export class BatteryExpenseCreate {
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

export class btExpenseManagementExpensePurpose {
  expensePurposeId: number;
  expensePurposeName: string;
}

export class btExpenseManagementExpenseType {
  expenseTypeId: number;
  expenseTypeName: string;
}

export class btExpenseManagementStatus {
  statusId: number;
  statusName: string;
}

export class btExpenseManagementBusinessPlace {
  businessPlaceId: number;
  businessPlaceName: string;
}

export class btExpenseManagementBusinessArea {
  businessAreaId: number;
  businessAreaName: string;
}

export class ExpenseManagementCostCenter {
  cost_center_id: number;
  costCenterName: string;
}

export class BTCaseRequest {
  openDate:any;
  ticketNo:string;
  caseReasonId: number;
  assetsSerialNumber: any;
  missingAssets: string;
  siteId: number;
  contactPersonId: number;
  caseTypeId: number;
  caseRecordTypeId: number;
  caseStatusId: number;
  caseStageId: number;
  serviceResourceId: number;
  serviceCenterId: number;
  poDate: string;
  poNumber: string;
  noOfSmr: string;
  caseOriginId: number;
  casePriorityId: number;
  remarks: string;
  otherProblemReported: string;
  isAssignByAssignmentRule: boolean;
  inputFromEng: any;
  internalRemark: string;
  RJILTT01No: string;
  pendingResponsibility: any;
  caseProblemReported: Array<ProblemReported>;
  typeChoice: number;
  id?: any;
  poamount: any;
  lineitem: any;
  assetsId: number;
  btProblemMaster: any;
  rma: boolean = false;
  rol: boolean = false;
  rolRemark: string;
  casesubReason: string='0';

}
export class BTCaseProofOfDeliveryRequest {
  caseId: number;
  podNumber: string;
  podDate: any;
  podShippingMode: string = 'select';
  podShippingToName: string;
  jmsNo: string;
  customer: string;
  podShippingTrackingNumber: string;
  podShippingExpectedDate: any;
}
export class BTCaseResponse {
  createDate: Date;
  openDate:any;
  // openDate:Date = new Date();
  ticketNo:string;
  id: number;
  caseId: any;
  customer: string;
  serviceResourceId: number;
  manualId: string;
  jmsNo: string;
  caseManualId: string;
  // PODDate: any;
  // PODNumber: string;
  podnumber: string;
  poddate: any;
  caseStatusName: string;
  PODate: string;
  PONumber: string;
  ponumber: any;
  poamount: any;
  lineitem: any;
  podate: any;
  caseReasonId: number;
  inputFromEng: string;
  RJILTT01No: any;
  rjil_TT01_No: any;
  missingAssets: string;
  otherProblemReported: any;
  isAssignByAssignmentRule: boolean;
  pendingResponsibility: any = 1;
  internalRemark: string;
  PendingResponsibilityName: string;
  siteId: number;
  siteManualId: string;
  siteName: string;
  serviceCenterId: number;
  serviceCenterName: string;
  caseStageVerticalId: number;
  caseStageVerticalName: string;
  assetsSerialNumber: string; contactPersonEmailId: any;
  contactPersonMiddleName: any;
  assetsId: number;
  accountSapNumber: string;
  accountName: string;
  primaryAccountSapNumber: string;
  primaryAccountName: string;
  createdDate: string;
  createdByEmailId: string;
  createdByFirstName: string;
  createdByMiddleName: string;
  createdByLastName: string;
  modifiedDate: string;
  modifiedByEmailId: string;
  modifiedByFirstName: string;
  modifiedByMiddleName: string;
  modifiedByLastName: string;
  ownerEmailId: string;
  ownerFirstName: string
  caseTypeId: number;
  caseTypeName: string;
  statusName: string;
  serviceId: string;
  serviceName: string;
  caseReasonName: any;
  assetsPartCode: any;
  problemReported: any[];
  assetDescription: any;
  caseStatusId: any;
  caseStageId: number;
  remarks: any;
  noOfSmr: any;
  caseOriginId: number;
  casePriorityId: number;
  contactPersonId: number;
  assetstypeChoiceName: string;
  assetsTypeChoiceId: number;
  typeChoice: any;
  vendorInvoiceNumber: string;
  invoiceNumber: string;
  invoiceDate: any;
  vendorInvoiceDate: any;
  caseLoggedByContactNo: number;
  problemMasterId: number;
  problemmasterType: string;
  //Ayush
  contactPersonContactNo: string;
  contactPersonFirstName: string;
  faultObservedByCustomer: string;
  contactPersonLastName: string;
  circleName: string;
  podShippingExpectedDate: any;
  podShippingMode: string;
  podShippingTrackingNumber: string;
  // pawan for sa
  serviceResourceName: string;
  rmaCreated: boolean;
  fileUploadResponses: Array<FileUploadResponses> = [];

  ///// kalindi
  pendingResponsibilityId: number;
  assignByAssignmentRule: boolean;
  cmp: string;
  // for new cr of ROL
  rolRemark: string;
  isRolCreated: boolean;
  rolCreated: boolean;
  casesubReason: string='0';

}
export class FileUploadResponses {
  fileId: number;
  fileName: string;
}
export class BTCaseForList {
  caseId: number;
  caseManualId: string;
}
export class BTCaseTypeForList {
  caseTypeId: any;
  caseTypeName: string;
}
export class BTCaseTypeRecord {
  businessDomainId: number;
  businessDomainName: string;
  synced: boolean;
}

export class BTCaseStageDetails {
  caseStageName: string;
  id: number;
}

export class BTCaseReasonForList {
  caseReasonId: any;
  caseReasonName: string;
}
export class BTToBeApproved {
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
export class BTApprovalRequest {
  approvalId: number;
  action: string = 'select';
  remark: string;
  reAssigned: string;

}

export class BtTourExpenseRequest {
  timeFlag: boolean;
  id: number;
  btExpenseManagementId: number;
  expenseManagementManualId: string;
  modeOfTravelId: number = 0;
  ownConveyanceId: number = 0;
  departureDate: string;
  departureTime: string = null;
  arrivalDate: string = '';
  arrivalTime: string = null;
  fromPlace: string;
  toPlace: string;
  companyPaid: boolean = false;
  fare: string;
  approvedAmount: string;
  distance: string;
  remark: string;
  manualId: string;
  amountToDisbursed: number;
}
export class BtTourExpenseResponse {
  id: number;
  // manualId: string;
  // expenseManagementId: number;
  // expenseManagementManualId: string;
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
  // createdByFirstName: string;
  // createdByLastName: string;
  // createDate: string;
  // modifiedByEmailId: string;
  // modifiedByFirstName: string;
  // modifiedByLastName: string;
  // modifiedDate: string;
  ownerEmailId: string;
  // ownerFirstName: string;
  // ownerLastName: string;
  // createdByMiddleName: string;
  // modifiedByMiddleName: string;
  noOfDays: number;
  arrivalDateTime: string;
  departuredateTime: string;
  // amountToDisbursed: number;

  amount_to_disbursed: number;
  btExpenseManagementId: number;
  btExpenseManagementManualId: string;
  createdByName: string;
  createdDate: Date;
  lastModifiedByEmailId: string;
  lastModifiedDate: Date
  manual_id: string;
  modifiedByName: string;
  ownerByName: string;

}
export class BtLodgingExpenseRequest {
  id: number;
  // expenseManagementId: number;
  expenseManagementManualId: string;
  btExpenseManagementManualId: string;     //

  fromDate: string = '';             //
  fromTime: string;
  toTime: string;
  toDate: string = '';               //
  cityName: string = null;
  otherCity: string = '';
  selfArrangement: boolean;          //
  comapnyPaid: boolean;              //
  hotelName: string = '';
  billNo: string = '';
  amount: string;
  approvedAmount: string;
  remark: string = '';
  cityCategoryId: number;
  manualId: number;
  amountToDisbursed: number;

  btExpenseManagementId: number;
  from: string = '';
  to: string = '';
  isSelfArrangement: boolean = false;
  isComapnyPaid: boolean = false;
}

export class BtLodgingExpenseResponse {

  id: number;
  manualId: number;
  // expenseManagementId: number;
  // expenseManagementManualId: string = '';
  businessDomainId: number;
  businessDomainName: string = '';
  fromDate: string = '';
  toDate: string = '';
  fromTime: string;
  toTime: string;
  // cityCategoryName: string = '';
  cityCategoryId: number;
  cityName: string = '';
  otherCity: string = '';
  // selfArrangement: boolean;
  // comapnyPaid: boolean;
  hotelName: string = '';
  billNo: string = '';
  amount: string;
  approvedAmount: string;
  remark: string = '';
  createdByEmailId: string = '';
  // createdByFirstName: string = '';
  // createdByLastName: string = '';
  // createDate: string = '';
  modifiedByEmailId: string = '';
  // modifiedByFirstName: string = '';
  // modifiedByLastName: string = '';
  modifiedDate: string = '';
  ownerEmailId: string = '';
  // ownerFirstName: string = '';
  // ownerLastName: string = '';
  // createdByMiddleName: string;
  // modifiedByMiddleName: string;
  // noOfDays: number;
  entitlement: number = 0;
  arrivalDateTime: string;
  DepartureDateTime: string;
  netAmountToPay: string;
  amountToDisbursed: number;

  noOfDayes: number;
  ownerByName: string;
  createdDate: Date;
  isComapnyPaid: boolean;
  createdByName: string;
  isSelfArrangement: boolean;
  cityCategory: string;
  btExpenseManagementManualId: string;
  modifiedByName: string;
  btExpenseManagementId: number;


}

export class BtBoardingExpenseRequest {
  id: number = 0;
  // expenseManagementId: number = 0;
  expenseManagementManualId: string = '';
  fromDate: string = '';      //
  fromTime: string;
  toDate: string = '';        //
  toTime: string
  cityName: string = null;
  otherCity: string = '';
  // noOfDayes: number = 0;
  amount: number = 0;
  remark: string = '';
  cityCategoryId: number;
  entitlementPerDay: number;
  manualId: string = '';
  amountToDisbursed: number;

  btExpenseManagementId: number = 0;
  from: string = '';
  to: string = '';
  NoOfDayes: number = 0;
  btExpenseManagementManualId: string = '';


}

export class BtBoardingExpenseResponse {
  id: number = 0;
  manualId: string = '';
  // expenseManagementId: number = 0;
  // expenseManagementManualId: string = '';
  businessDomainId: number = 0;
  businessDomainName: string = '';
  fromDate: string = '';
  fromTime: string;
  toDate: string = '';
  toTime: string;
  // cityCategoryName: string = '';
  cityCategoryId: number;
  cityName: string = null;
  otherCity: string = '';
  noOfDayes: number = 0;
  amount: number = 0;
  remark: string = '';
  createdByEmailId: string = '';
  // createdByFirstName: string = '';
  // createdByLastName: string = '';
  // createDate: string = '';
  modifiedByEmailId: string = '';
  // modifiedByFirstName: string = '';
  // modifiedByLastName: string = '';
  modifiedDate: string = '';
  ownerEmailId: string = '';
  // ownerFirstName: string = '';
  // ownerLastName: string = '';
  entitlementPerDay: number;
  entitlement: number = 0;
  arrivalDateTime: string;
  DepartureDateTime: string;
  amountToDisbursed: number;


  ownerByName: string = '';
  createdDate: string = '';
  NoOfDayes: number = 0;
  createdByName: string = '';
  cityCategory: string = '';
  btExpenseManagementManualId: string = '';
  modifiedByName: string = '';
  btExpenseManagementId: number = 0;
  noOfDays: number = 0;
}

export class BtLocalConveyanceExpenseRequest {
  amountToDisbursed: number;
  id: number = 0;
  // expenseManagementId: number = 0;
  expenseManagementManualId: string = '';
  modeOfTravelId: number = 0;
  ownConveyanceId: number = 0;
  date: string = '';
  fromPlace: string = '';
  toPlace: string = '';
  amount: string='0';
  distance: string;
  remark: string = '';
  manualId: string = '';

  btExpenseManagementId: number = 0;
}

export class BtLocalConveyanceExpenseResponse {
  id: number = 0;
  manualId: string = '';
  // expenseManagementId: number = 0;
  // expenseManagementManualId: string = '';
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
  // createdByFirstName: string = '';
  // createdByLastName: string = '';
  create: string = '';
  // modifiedByEmailId: string = '';
  // modifiedByFirstName: string = '';
  // modifiedByLastName: string = '';
  modified: string = '';
  ownerEmailId: string = '';
  // ownerFirstName: string = '';
  // ownerLastName: string = '';
  // createDate: string;
  companyPaid: boolean;
  fare: string;
  approvedAmount: string;
  departureDate: string;
  departureTime: string;
  arrivalDate: string;
  arrivalTime: string;
  // createdByMiddleName: string;
  // modifiedByMiddleName: string;
  // modifiedDate: string;
  amountToDisbursed: number;


  createdByName: string = '';
  createdDate: string;
  lastModifiedDate: string;
  lastModifiedByEmailId: string = '';
  ownerByName: string = '';
  btExpenseManagementManualId: string = '';
  modifiedByName: string = '';
  btExpenseManagementId: number = 0;
}

export class BtOtherChargesExpenseRequest {
  id: number = 0;
  // expenseManagementId: number = 0;
  expenseManagementManualId: string = '';
  otherChargesTypeId: number = 0;
  date: string = '';
  billNo: string = '';
  amount: string;
  remark: string = '';
  businessDomainId: number = 0;
  manualId: string = '';
  amountToDisbursed: number;

  btExpenseManagementId: number = 0;
  btExpenseManagementManualId: string = '';

}

export class BtOtherChargesExpenseResponse {
  id: number = 0;
  manualId: string = '';
  // expenseManagementId: number = 0;
  // expenseManagementManualId: string = '';
  businessDomainId: number = 0;
  businessDomainName: string = '';
  otherChargesTypeId: number = 0;
  otherChargesTypeName: string = '';
  date: string = '';
  billNo: string = '';
  amount: string;
  remark: string = '';
  createdByEmailId: string = '';
  // createdByFirstName: string = '';
  // createdByLastName: string = '';
  // createDate: string = '';
  // modifiedByEmailId: string = '';
  // modifiedByFirstName: string = '';
  // modifiedByLastName: string = '';
  modified: string = '';
  ownerEmailId: string = '';
  // ownerFirstName: string = '';
  // ownerLastName: string = '';
  // createdByMiddleName: string;
  // modifiedByMiddleName: string;
  // modifiedDate: string;
  amountToDisbursed: number;

  btExpenseManagementId: number = 0;
  btExpenseManagementManualId: string = '';
  createdByName: string = '';
  createdDate: string = '';
  lastModifiedByEmailId: string = '';
  lastModifiedDate: string;
  modifiedByName: string = '';
  ownerByName: string = '';

}
// export class ExpenseManagementCostCenter {
//   cost_center_id: number;
//   costCenterName: string;
// }
export class BTProductForListResponse {
  id: number;
  partCode: string;
  productType: string;
  bothvalues?: string;
  productDescription?: string

}
export class BT_SiteDetailForList {
  batterySiteId: number;
  manuaSitelId: string;
  siteName: string;
}
export class BT_ProductForListDetail {
  id: number;
  partCode: string;
  productDescription: string;
  productType?: string;
  bothvalues?: string;
  productManualId?: string;
}
export class BT_CaseTypeForList {
  caseTypeId: any;
  caseTypeName: string;
}
export class BtProblemMaster {
  id: number;
  type: string;
}
export class AssetTypeChoice {
  id: number;
  name: string;
}
export class BT_CaseTypeRecord {
  businessDomainId: number;
  businessDomainName: string;
  synced: boolean;
}
export class BT_CaseStatus {
  caseStatusName?: string;
  id: number;
  isDesable: boolean;
  name?: string
}
export class BT_CaseStageDetails {
  caseStageName: string;
  id: number;
}
export class BT_CaseOriginDetails {
  caseOrignName: string;
  caseOriginId: number;
  synced: boolean;

}
export class BT_CasePriority {
  CasePriorityId: number;
  casePriorityName: string;
  synced: boolean;

}
export class BT_CaseReasonForList {
  caseReasonId: any;
  caseReasonName: string;
}
export class AssetForList {
  serialNumber: string;
  id: number;
  productDescription: string;
  productPartCode: string;

}
export class BTServiceAppointmentResponse {
  accountName: string
  accountSapNumber: string;
  acknowledgedByName: string;
  acknowledgedByPhone: string;
  approvedByFinance: string;
  assetDescription: string;
  assetsPartCode: string;
  assetsSapSerialNumber: string;
  businessPlace: string;
  caseId: number;
  caseIdManual: string;
  serviceAppointmentId: string;
  caseReason: number;
  caseReasonName: string;
  caseStatus: string;
  caseType: number;
  caseTypeName: string;
  circleManagerContactEmailId: string;
  circleManagerContactFirstName: string;
  circleManagerContactId: number;
  circleManagerContactLastName: string;
  circleManagerContactMiddleName: string;
  circleName: string;
  claimStatusId: number;
  claimStatusName: string;
  contactFirstName: string;
  contactId: number;
  contactLastName: string;
  contactMiddleName: string;
  contactPhoneNumber: string;
  correctiveActions: string
  costCenter: string;
  createByEmailId: string;
  createDate: string;
  createdByFirstName: string;
  createdByLastName: string;
  createdByMiddleName: string;
  customerRepresentativeContact: string;
  customerRepresentativeName: string;
  dueDate: string;
  dueTime: string
  employeeCode: any
  faultObserved: string;
  fixedPayout: any;
  grade: any;
  isApprovedByFinanceCheck: Boolean;
  manualId: string;
  modifiedByEmailId: string;
  modifiedByFirstName: string;
  modifiedByLastName: string;
  modifiedByMiddleName: string;
  ownerEmailId: string;
  ownerFirstName: string;
  ownerLastName: string;
  ownerMiddleName: string
  pendingResponsibilityId: any;
  pendingResponsibilityName: string;
  punchPointCustomer: string;
  punchPointExiom: string;
  remark: string;
  serviceAppointmentStatusId: any;
  serviceAppointmentStatusName: string;
  serviceCenterId: any;
  serviceCenterManualId: any;
  serviceCenterName: string;
  servicePayoutId: any;
  servicePayoutName: any;
  serviceResourceId: number;
  serviceResourceName: string;
  siteId: any;
  siteIdManual: string
  siteName: string;
  ssaId: any;
  ssaName: string;
  tFlag: boolean;
  visitDate: string;
  wcc: boolean;
  wccId: any;
  wccManualId: any;


}
export class btPendingResponsibility {
  pendingResponsibilityId: number;
  pendingResponsibilityName: string;
}
export class btServiceAppointmentResponseWithSCH {
  appointmentId: number;
  appointmentManualId: number;
  serviceAppointmentId: number;
  serviceAppointmentManulaId: string;
  rmaCreated: boolean;
  billingRequired: boolean = false;
  iscreatefsr: boolean;
  isCommisionFsr: boolean;
  pendingResource: string;
  customerBucketReasonName: string;
  customerBucketReasonId: number;
  ssaId: number;
  fsrCreated: boolean = false;
  // manualId : string;
  customerBillingRate: string;
  customerRate: string;
  siteId: number;
  siteIdManual: string;
  caseId: number;
  caseIdManual: string;
  caseManualId: number;
  caseReason: string;
  siteName: string;
  zoneName: string;
  contactId: number;
  faultObserve: string = '';
  punchPointExicom: string = '';
  punchPointCustomer: string = '';
  visitDate: Date;
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
  createDate: Date;

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
  acknowledgedByName: string;
  acknowledgedByPhone: string;
  latitude: any;
  longitude: any;
  address: string;
  eSignature: any
  closedFromPlatform: any;
  customerRating: any;
  customerRemarks: string;
  rma: boolean = false;
  servicePayoutId: number = 0;
  caseTypeName: string;
  caseReasonName: string;
  productId: number;
  productPartCode: string;
  commFsrId: number;
  serviceFsrId: number;
  assetId: number;
  resolutionMethod: number;
  // btSchOnlyResponses: Array<BtSchOnlyResponses> = [];
  btSolutionImpartedProblemResponses: Array<BtSolutionImpartedSch> = [];
  correctiveAction: string;
  // solutionImparted:string;
  sparePartName: string = '';
  sparePartReplacedFrom: string = '';
  sparePartQuantity: string;
  sparePartReplaced: string;
  resolutionMethodId: number = 0;
  sparePart: string;
  uploadfsrMandatory: boolean;
  fsrMandatory: boolean;
  observation1: string;
  observation2: string;
  observation3: string;
  observation4: string;

}
export class BtSchOnlyResponses {
  resolutionMethodId: number;
  correctiveAction: string;
  resolutionMethodName: string;
  schOnlyId: number;
  btSolutionImpartedSch: Array<BtSolutionImpartedSch> = [];
  sparePartName: string;
  sparePartQuantity: string;
  sparePartReplaced: string;
  sparePartReplacedFrom: string;

}
export class BtSolutionImpartedSch {
  id: number = null;
  solutinName: string = '';
  schId: number = null;

}
export class btServiceAppointmentRequest {
  schFill: boolean = false;
  address: string;
  appointmentId: number;
  customerPendingBucketId: number;
  customerBillingRate: string;
  customerRate: string;
  customerBucketReasonId: number;
  billingRequired: boolean = true;
  customerBucketReasonName: string;
  serviceAppointmentManulaId: string;
  businessDomainId: number;
  serviceAppointmentId: number = null;
  faultObserved: string;
  caseId: number;
  correctiveActions: string;
  punchPointExiom: string;
  punchPointCustomer: string;
  visitDate: String;
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
  acknowledgedByName: string;
  acknowledgedByPhone: string;
  eSignature: any
  closedFromPlatform: any
  rma: boolean = false;
  servicePayout: number = 0;
  assetId: number;
  // SCH Request
  resolutionMethod: number;
  solutionImparted: Array<btSolutionImpartedReq> = [];
  sparePart: string = null;
  sparePartQuantity: number;
  sparePartReplaced: string = null;
  pendingResource: string;
  resolutionMethodId: number;
  sparePartReplacedFrom: string = '';
}


export class BtCirclesRequest {
  circleName: string;
  countryCode: string = '';
  // circleManagerBattryId: number = null;
  // circleManagerPowerId: number = null;
  // // circleManagerBattryName: string = '';
  // // circleManagerPowerName: string = '';
  // circleManagerEv: number = null;
  // circleManagerEvName: string = '';
  zoneName: string = '';
  legalEntity: any;
  // batteryLegalEntityId: number=null;
  // batteryLegalEntityName: String='';
  // isBatterySelected: string = 'fa fa-search';
  // isPowerSelected: string = 'fa fa-search';
  // isEvSelected: string = 'fa fa-search';
  // circleManagerBattryFirstName: string = '';
  // circleManagerPowerFirstName: string = '';
  // circleManagerEvFirstName: string = '';
}

export class BtCirclesResponse {
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
  batteryLegalEntityId: number;
  batteryLegalEntityName: '';
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
  zoneBattery: string;
  // isBatterySelected: string = 'fa fa-search';
  // isPowerSelected: string = 'fa fa-search';
  // isEvSelected: string = 'fa fa-search';
}

export class BtServiceCenterForList {
  batteryLegalEntityId: number;
  batteryLegalEntityName: String = '';
}

export class BtBatteryFSRResponse {
  batterySno_1: number;
  batterySno_2: number;
  batterySno_3: number;
  batterySno_4: number;
  batterySoc_1: number;
  batterySoc_2: number;
  batterySoc_3: number;
  batterySoc_4: number;
  casManualId: string;
  cbmsSerialNo: string;
  currentReading: string;
  customerMobileNo: string;
  customerName: string;
  dateOfVisit: Date
  eNodeType: string;
  id: number;
  macId: string;
  manualId: string;
  nayPhysicalDamage: string;
  odcSerialNo: string;
  odcType: string;
  otherIssue: string;
  otherSiiteManualId: string;
  otherSiteName: string;
  projectType: string;
  rectifierRating: string;
  rjilSmpsMatcode: string;
  serviceManualId: string;
  siteManuald: string;
  siteName: string;
  smpsSerialNo: string;
  smpsType: string;
  softwareVersion: string;
  unitTtpe: string;
  vendorEnfContact: string;
  vendorEngName: string;
  voltageReading: string;
  warehouseName: string;

}
export class BtBatteryFSRRequest {
  serviceAppointmentId: string;
  warehouseId: string;
  siteType: string;
  otherSiteType: string;
  visitDate: Date;
  cbmsSerialNo: string;
  softwareVersionOfBatterystring: string;
  currentReading: string;
  voltageReading: string;
  macID: string;
  smpsSerialNo: string;
  odcType: string;
  odcSerialNo: string;
  smpsType: string;
  eNodeBType: string;
  projectType: string;
  unitType: string;
  rectifierRating: string;
  RjilSmpsMatcode: string;
  batterySNo1: number;
  batterySNo2: number;
  batterySNo3: number;
  batterySNo4: number;
  batterySoc1: number;
  batterySoc2: number;
  batterySoc3: number;
  batterySoc4: number;
  batteryType: number;
  anyPhysicalDamage: string;
  otherIssue: string;
  customerMobileNo: string;
  customerName: string;
}

export class BtPreRegCaseResponse {
  [x: string]: any;
  openDate:any;
  ticketNo:string;
  prcCaseId: number = 0;
  prcCaseManualId: string;
  caseRecordTypeId: number = 0;
  caseRecordTypeName: string;
  // caseTypeId: number;
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
  // accountSapNumber: string;
  accountName: string;
  siteManualId: string;
  siteName: string;
  siteId: number;
  siteCustomer: string;
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
  isAssignByAssignmentRule: boolean;
  assetDescription: string;
  lastAction: string;
  lastActionComment: string;
  caseId: number;
  caseManualId: string;


  RJIL_TT01_No: string;
  caseTypeID: number;
  ownerEmailId: string;
  preCaseId: number;
  preCaseManualId: string;
  primaryAccountName: string;
  primaryAccountSapNumber: string;
  poamount: any;
  lineitem: any;
  assetsId: any;
  assetsTypeChoiceId: number;
  caseTypeId: number;
  problemMasterId: number;
  assetId: number;



}
export class BtPreRegCaseRequest {
  openDate:any;
  ticketNo:string;
  btProblemMaster: number;
  id: number;
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
  RJILTT01No: string;
  otherProblemReported: string;
  poNumber: string;
  poDate: any;
  typeChoice: number;
  poamount: any;
  limeitem: any;
  assetsId: any;

}
export class ProblemReported {
  id: number;
  problemName: string;
}



export class AddBattery {

  Sr_No: number;
  id: number;
  Equipment_Type: string;
  Part_Code: number;
  Model_Number: string = '';
  Equipment_Sr_No: string;
  Software_Version: number = null;
  SOC: number;
  SOH: number;
  Warranty_Status: string = '';
  Installation_Date: any;
  Current_Condition: number = 3;
  problemSince: Date;
  vol: number;
  activeAlarms: number = 0;
  minCellVol: number;
  maxCellVol: number;
  volGap: number;
  batteryTemp: number;
  lastCycleDel: number;
  logStatus: string = "";
  betteryReqBRC: string = "";
  actionbtn: any;
  flag: number = 0;
  assetsSerialNumber: string;
  Equipment_Sr_No_Arr: Array<BtEquipmentSrNO> = [];

}
export class BtserialNoArray {
  eqSerialNo: string;
  indexOfSerialNo: number;
}
export class EquipmentSrNO {
  _equipmentSrNo;
}
export class BtEquipmentSrNO {
  bothvalues: string;

  id: number;
  serialNumber: number;
  warrantyStatus: string;
  productPartCode: string;
  productDescription: string;
  installtionDate: string;
}

export class BtSAServicePayout {
  id: number;
  name: string;
}

export class btExpServiceAppointmentlist {
  btServiceAppointmentId: number;
  btServiceAppointmentManualId: string;
  caseId: number;
  caseManualId: string;
  circleName: string;
  siteId: number;
  siteName: string;
  isChecked?: boolean = false;
  claimAmount?: string;
  totalexp?: number;
}

export class btExpenseCostCenter {
  costCenterAndGlCode: string;
}

export class btExpServiceAppointmentReq {
  btExpId: number;
  costCenter: string = '';
  totalExp: number = 0;
  btServicAppointmentExp: Array<btExpSA>;
  totalClaim: number = 0;
}
export class btExpServiceAppointmentResp {
  btExpenseManagementId: number;
  btServiceAppointmentId: number;
  btServiceAppointmentManualId: string;
  claimAmount: number;
  costCenter: string
  createdByEmailId: string;
  createdDate: Date;
  id: number;
  lastModifiedByEmailId: string;
  lastModifiedDate: string;
  ownerEmailId: string;
  remark: string;
  caseId: number;
  caseManualId: string;
  circleName: string;
  siteId: number;
  siteName: string;
  businessDomainName: string;
  btExpenseManagementManualId: string;
}
export class btExpSA {
  id: number;
  btServicAppointmentId: string = '';
  claimAmount: string = '';
  amountToDisbursed: string = '';
}
export class BtSiteEnvCondition {
  id: any;
  sitevalue: any;
}
export class BtCollingMethod {
  id: any;
  coolingValue: any;
}
export class BtPowerPlantMake {
  id: any;
  powerValue: string;
}
export class BtResolutionMethod {
  id: any;
  resolutionMethodValue: string;
}
export class BtPendingReasonValue {
  id: any;
  pendingReasonValue: string;
}
export class BtSolutionImparted {
  id: number;
  solutionValue: string;

}
export class btSolutionImparted {
  id: number;
  schId: number;
  solutinName: string;

}
export class btSolutionImpartedReq {
  id: number;
  solutionName: string;

}
export class BtSiteType {
  id: any;
  siteTypeName: string;
}
export class BtActiveAlarm {
  id: number;
  activeAlarmValue: string;
}


export class BtAssetMasterUpdate {
  id: number;
  serialNumber: number;
  productPartCode: number;
  productDescription: string;
  installtionDate: Date;
  expiryDate: Date;
  warrantyStatus: string;
  bothvalues: string;
}
// shagufta created boding
export class Bat_BoardingLoading {
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

export class Bat_BoardingLoadingDetailsRequest {
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
export class Bat_BoardingLoadingDetailsResponse {
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
export class BtMSLItemRequestRequest {
  btMslItemRequestId: string;
  btMslRequestId: string;
  btProductId: string;
  mslQty: string;
  hslQty: string;
  remarks: string;
}
export class BtMslItemResponse {
  oldHSL: number;
  productDescription: string;
  remarks: number;
  oldMSL: number;
  newMSL: number;
  newHSL: number;
  btProductManualId: string;
  id: number;
  btProductId: number;
}
//Ayush
export class BtFsrData {

  conatctPersonAtSite: string;
  siteEngContactNo: any;
  serviceFSRId: string;
  ///RESULT 

  appointment: number;
  btSiteEnvironmentalConditions: number = 0;
  btCoolingMethod: number = 0;
  btPowerPlantMake: number = 0;
  btResolutionMethod: number = 0;
  btPendingReasonCustomerBucket: number = 0;
  btSiteType: number = 0;
  btSolutionImparted: number = 0;
  contactPersonAtSite: string;
  contactPersonAtSiteNo: string='';
  dateOfSiteVisit: string;
  earthigStatus: number;
  ambientTemp: number;
  totalEquipmentDcLoad: number;
  activeRectifiers: number;
  smpsToCbmsVolGap: number;
  cbmsToBatVolGap: number;
  dcLoadCurrent: number;
  maxModuleTemp: number;
  correctiveActionInDetails: string;
  faultObservedByExicomEng: string;
  caseStatus: string = "";
  pendingBucket: string = "";
  pendingReasonExicomBucket: string = "";
  complainAttendByEngName: string = "";
  engNumber: string;
  customerRemark: string = "";
  faultyReportByCustomer: string = "";
  observation1: string ="";
  observation2: string ="";
  observation3: string ="";
  observation4: string ="";

  asseRequests: Array<AssetRequest> = [];



  ////////////////Commision/////////////////////
  commissionScope: string;
  installtionStatus: string;
  commStatus: string = "";

  chargeVoltage: string;
  floatVoltage: string;
}
//Class for Case response data
export class BT_CaseGeneralINFO {


  customerName: string;
  caseId: number;
  cMP: string;
  siteId: number;
  siteName: string;
  createdDate: string;
  caseLoggedByContactNo: string;
  ownerFirstName: string;
  dateOfSiteVist: string;
  createdByFirstName: string;
  circle: string;
  pendingBucketIF: any;
  dateOfSiteVisit: Date;
  contactPersonContactNo: string;
  contactPersonFirstName: string;
  dateOfiteVisit: string;
  siteManualId: string;
  assetsSerialNumber: string;
  faultObservedByCustomer: any[];

}
export class AssetRequest {

  srNo: number;
  equipmentName: string;
  equipmentType: string = "";
  btAsset: number;
  equipmentSrNo: string = "";
  flag: number = 0;
  softwareVersion: number = 0;
  // warrantyStatus: string="";
  // installationDate: Date;
  currentCondition: number = 0;
  serialNumber: number;
  voltage: number = 0;
  soc: number;
  soh: number;
  activeAlarms: number = 0;
  problemSince: Date;
  minCellVoltage: number = 0;
  maxCellVoltage: number = 0;
  volGap: number = 0;
  batteryTemp: number = 0;
  lastCycleDel: number = 0;
  logStatus: string = "";
  batteryRequiredAtBRC: string = "";
}


export class batteryCommisionFsr {
  commFSRId: string;
  appointment: string;
  contactPersonAtSite: string;
  contactPersonAtSiteContactNumber: string;
  dateOfSiteVisit: string = '';
  siteType: number = 0;
  btSiteEnvironmentalConditions: number = 0;
  earthigStatus: number;
  btCoolingMethod: number = 0;
  ambientTemp: number;
  commissionScope: string = '';
  totalEquipmentDcLoad: number;
  installtionStatus: string = '';
  smpsToCbmsVolGap: number;
  cbmsToBatVolGap: number;
  dcLoadCurrent: number;
  maxModuleTemp: number;
  chargeVoltage: string;
  floatVoltage: string;
  Voltage: number;
  powerPlantMake: number = 0;
  activeRectifers: number;
  exicomEngRemarks: string;
  customerRemarks: string;
  commStatus: string;
  pendingBucket: string;
  pendingReasonExicomBucket: string;
  pendingReasonCustomerBucket: number;
  complainAttandByEngName: string;
  engNumber: string;
  btCommissioningAssetMasterUpdateRequests: Array<btCommissioningAssetMasterUpdateRequests> = [];
  observation1: string;
  observation2: string;
  observation3: string;
  observation4: string;
}

export class btCommissioningAssetMasterUpdateRequests {
  srNo: number;
  equipmentType: string = '';
  btAsset: number;
  equipmentSrNo: string = '';
  softwareVersion: number = null;
  soc: number = null;
  soh: number = null;
  currentCondition: number = null;
}

export class Btrow {
  id: number;
  equipmentType: string;
}
export class SelectedBattery {
  id: number;
  selectedBt: string;
}

export class BTMslCreateRequest {
  btMslRequestId: string;
  locationId: any;
  StatusId: any;
  btItemRequest: Array<BtItemRequest> = [];
  statusName: string;
}
export class BtItemRequest {
  productPartCode: string
  btMslItemRequestId: string;
  btMslRequestId: string;
  btProductId: string;
  mslQty: string;
  hslQty: string;
  remarks: string
}

export class BtLocationProductItemResponse {
  // locationProductItemId: number;
  // locationProductItemManualId: string;
  // productPartCode: string;
  // productDescription: string;
  // quantityInOnHand: number;
  // quantityUnitOfMasur: string;
  // locationId: number;
  // locationName: string;
  // parentLocationProductItemId: number;
  // parentLocationProductItemManualId: string;
  // hslQuantity: number;
  // hslStatus: string;
  // issuedQunatity: number;
  // mslQuantity: number;
  // mslQuantityStatus: string;
  // okQuantity: number;
  // faultyQuantity: number;
  // selected?: boolean;


  customerTotalBerQuantity: number;
  customerTotalFaultyQuantity: number;
  customerTotalOkQuantity: number;
  customerTotalQuantity: number;
  customerTotalRejectedQuantity: number;
  customerTotalScrapQuantity: number;
  exicomTotalBerQuantity: number;
  exicomTotalFaultyQuantity: number;
  exicomTotalOkQuantity: number;
  exicomTotalQuantity: number;
  exicomTotalRejectedQuantity: number;
  exicomTotalScrapQuantity: number;
  locationName: string;
  locationPlant: string;
  productDescription: string;
  productId: number;
  productItemManualId: string;
  productPartcode: string;
  productType: string;
}

export class BtLocationProductRequestResponse {
  productRequestCreatedByEmailId: string;
  productRequestCreatedDate: Date;
  productRequestDescription: string;
  productRequestDestinationLocationId: number;
  productRequestDestinationLocationName: string;
  productRequestDestinationLocationPlant: string;
  productRequestDummayStatus: string;
  productRequestId: number;
  productRequestLastMdifiedByEmailId: string;
  productRequestLastModifiedDate: Date;
  productRequestManualId: string;
  productRequestNeedByDateTime: Date;
  productRequestOwnerEmailId: string;
  productRequestScynced: boolean;
  productRequestSourceLocationId: number;
  productRequestSourceLocationName: string;
  productRequestSourceLocationPlant: string;
  productRequestStatus: string;
  productRequestTotalQuantityIssued: number;
  productRequestTotalQuantityReceived: number;
  productRequestTotalQuantityRequired: number;

  // prId: number;
  // prManualId: string;
  // destinationLocationId: number;
  // destinationLocationName: string;
  // sourceLocationId: number;
  // sourceLocationName: string;
  // createdDate: Date;
  // description: string;
  // needByDateTime: Date;
  // status: string;
  // ownerEmailid: string;
  // ownerFirstName: string;
  // ownerMiddleName: string;
  // ownerLastName: string;
  // totalQuantityRequired: number;
  // totalQuantityIssued: number;
  // totalQuantityReceived: number;
  // createdByEmail: string;
  // createdByFirstName: string;
  // createdByMiddleName: string;
  // createdByLastName: string;
  // modifiedByEmail: string;
  // modifiedByFirstName: string;
  // modifiedByMiddleName: string;
  // modifiedByLastName: string;
  // modifiedDate: Date;
  // destinationLocationOwnerEmailId: string;
  // sourceLocationOwnerEmailId: string;
  // disabled:boolean;
}
export class BtFsrCommissionDataGetById {


  commFSRId: string;
  serviceAppointmentId: number;
  manualId: string;
  caseNo: string;
  customerName: string;
  createDate: Date;
  circle: string;
  cmp: string;
  siteId: string;
  siteName: string;
  caseLoggedBy: string;
  caseLoggedByContactNo: number;
  contactPersonAtSite: string;
  contactPersonAtSiteContactNumber: string;
  dateOfCaseGenerated: string;
  dateOfSiteVisit: string;
  siteTypeId: number;
  siteType: string;
  siteEnvironmentalConditionsId: number;
  siteEnvironmentalConditions: string;
  solutionImpartedId: number;
  earthingStatus: number;
  coolingMethodId: number;
  coolingMethod: string;
  ambientTemp: number;
  resolutionMethodId: number;
  totalEquipmentDcLoad: number;
  faultObservedByExicomEng: string;
  correctiveActionInDetails: string;
  btResolutionMethod: number;
  btSolutionImparted: number;
  commissionScope: string;
  caseStatus: string;
  totalEquipmentDCLoad: number;
  installationStatus: string;
  smpsToCbmsVolGap: number;
  cbmsToBatVolGap: number;
  dcLoadCurrent: number;
  maxModuleTemp: number;
  chargeVoltage: number;
  floatVoltage: number;
  powerPlantMakeId: number;
  powerPlantMake: string;
  activeRectifers: number;
  asseResponse: Array<batteryAssetResp>;
  exicomEngRemarks: string;
  customerRemarks: string;
  commStatus: string;
  pendingBucket: string;
  pendingReasonExicomBucket: string;
  pendingReasonCustomerBucketId: number;
  pendingReasonCustomerBucket: string;
  complainAttandByEngName: string;
  engineerNumber: number;
  observation1: string;
  observation2: string;
  observation3: string;
  observation4: string;
}

export class batteryAssetResp {
  srNo: number;
  assetId: number;
  equipmentType: string;
  partCode: number;;
  model: string;
  equipmentSrNo: string;
  softwareVersion: number;
  soc: number;
  soh: number;
  warrantyStatus: string;
  installationDate: string;
  currentConditionId: number;
  currentCondition: string;
}

export class BtFsrServiceDataGetById {
  serviceFSRId: number;
  serviceManualId: string;
  caseManualId: string;
  createDate: Date;
  appointment: number;
  appointmentManualId: string;
  siteEnvironmentalConditionsId: number;
  siteEnvironmentalConditions: string;
  coolingMethodId: number;
  coolingMethod: string;
  powerPlantMakeId: number;
  powerPlantMake: string;
  resolutionMethodId: number;
  resolutionMethod: string;
  pendingReasonCustomerBucketId: number;
  pendingReasonCustomerBucket: string;
  siteTypeId: number;
  siteType: string;
  solutionImpartedId: number;
  solutionImparted: string;
  contactPersonAtSite: string;
  contactPersonAtSiteNo: number;
  dateOfsiteVisit: string;
  earthigStatus: number;
  ambientTemp: number;
  totalEquipmentDcLoad: number;
  activeRectifiers: number;
  smpsToCbmsVolGap: number;
  installationStatus: string;
  cbmsToBatVolGap: number;
  dcLoadCurrent: number;
  maxModuleTemp: number;
  faultObservedByExicomEng: string;
  correctiveActionInDetails: string;
  caseStatus: string;
  pendingBucket: string;
  pendingReasonExicomBucket: string;
  complainAttendByEngName: string;
  engNumber: number;
  customerRemark: string;
  observation1: string;
  observation2: string ;
  observation3: string ;
  observation4: string ;
  asseResponse: Array<asseResponseService> = [];

  faultyAsset: Array<FaultyAsset> = [];
}


export class asseResponseService {
  srNo: number;
  assetId: number;
  equipmentType: string;
  productPartCode: number;
  productDescription: string;
  equipmentSrNo: string;
  soc: number;
  soh: number;
  softwareVersion: number;
  warrantyStatus: string;
  installationDate: string;
  currentConditionId: number;
  currentCondition: string;
}

export class FaultyAsset {

  activeAlarms: string;
  activeAlarmsId: number;
  batteryRequiredAtBRC: string;
  batteryTemp: number;
  equipmentName: string;
  lastCycleDel: number;
  logStatus: string;
  maxCellVoltage: number;
  minCellVoltage: number;
  problemSince: Date;
  soc: number;
  soh: number;
  volGap: number;
  voltage: number;

}
export class BTPrcActionRequest {
  action: string;
  comment: string;
  caseId: number;
}

export class RMAResponse {
  caseId: number;
  closeDate: string;
  BRCManualId: string;
  BRCId: number;
  createdDate: Date;
  createdEmailId: string;
  id: number;
  lastModifiedDate: Date;
  manualId: string;
  modifiedEmailId: string;
  ownerEmailId: string;
  rmaStatus: string;
  serviceappointmentId: number;
  BRCReceivedDate: string;
  SLATime: string;
  caseManualId: string;
  caseTypeId: number;
  caseTypeName: string;
  createDate: Date;
  createdByName: string;
  customerId: number;
  customerName: string
  modifiedByName: string;
  ownerName: string;
  serviceManualId: string;
  siteId: number;
  siteManualId: string;
  siteName: string;
  siteTechName: string;
  rmaRemarksA: string;
  rmaRemarksR: string;
  missingAsset: string;

  AssetId: number;
  productID: number;
  circleName: string;
  circle_circle_name: string;
  productPartCode: string;
  AssetSerialNumber: string
}

export class RmaRejectByIdRequest {
  id: number;
  remarks: string = '';
}
export class BrcListByAssetId {
  customerInventoryInChallanNumber: string;
  customerInventoryInStage: string;
  customerInventoryInManualId: string;
  customerInventoryInStatus: string;
  customerInventoryInId: number;
}

export class BtCustomerInventoryInRequest {
  id?: number;
  assetsSerialNumber: string;
  partCode: string;
  customerInventoryStage: string = '0';
  customerInventoryStatus: string = '0';
  contactName: string;
  contactNumber: string;
  accountSapNumber: string;
  challanNumber: string;
  receiveDate: string;
  circle: string;
  customerLrNumber: string;
  customerCurierPartnarName: string;
  customerPlant: string = '0';
  pacingCondation: string = '0';
  physicalDamage: string = '0';
  remarks: string;
  rmaManualId: string;
  dummayWarehouseName: string;
  dummayLocationName: string;
  receiveChallanDate: string;
}
export class BtCustomerInventoryOutRequest {
  inId: number;
  actualOutDate: string;
  outCourierPartner: string = '0';
  outdeleveryChallanNumber: string;
  outGrnNumber: string;
  outGrnDate: string;
  outState: null;
  loatNumber: string;
  trackingNumber: string;
  outLrDocNumber: string;
  bulk: string = 'manual';
}
export class EditGrnRequest {
  grnNumber: string;
  grnDate: string;
}
export class BtInventoryStock {
  customerTotalBerQuantity: number;
  customerTotalFaultyQuantity: number;
  customerTotalOkQuantity: number;
  customerTotalQuantity: number;
  customerTotalRejectedQuantity: number;
  customerTotalScrapQuantity: number;
  exicomTotalBerQuantity: number;
  exicomTotalFaultyQuantity: number;
  exicomTotalOkQuantity: number;
  exicomTotalQuantity: number;
  exicomTotalRejectedQuantity: number;
  exicomTotalScrapQuantity: number;
  locationName: string;
  locationPlant: string;
  productDescription: string;
  productId: number;
  productItemId: number;
  productItemManualId: string;
  productPartcode: string;
  productType: string;
  btinventoryStock?: Array<BtInventoryStockByProductId>;
  globalcustomerinventoryinput?: BtInventoryStockByProductId;
  globalcustoermerinventorycheck: boolean;
  exicomInputCount: Array<any>;
  customerInputCount: any;
  customerInputCountList: Array<any>;
  exicomSerialnumbers: Array<any>;
  expended?: boolean;
  globalexiserialnumber?: string;
}
export class BtInventoryStockByProductId {
  accountName: string;
  brcId: number;
  brcNumber: string;
  customerSapNumber: string;
  productDescription: string;
  productId: number;
  productPartcode: string;
  productType: string;
  serialNumber: string;
  isChecked: boolean;
  isCustome: boolean;
}
export class BtServiceOrderRepairCenterLIst {
  legalEntityId: number;
  legalEntityName: string;
  locationId: number;
  locationName: string = '0';
  locationPlant: string;
}
export class BtSericeOrderRequest {
  destinationLocationId: number;
  shippingMode: string = 'ByHand';
  shippingTo: string;
  trackingNumber: string;
  items: Array<BtServiceOrderItems>;
}
export class BtServiceOrderItems {
  isCustomerInventory: boolean;
  customerInventoryInId: number;
  productId: number;
  serialNumber: string;
}
export class BtServiceOrderRecieveItems {
  isCustomerInventory: boolean;
  productDescription: string;
  productId: number;
  productPartcode: string;
  productType: string;
  serviceOrderId: number;
  serviceOrderItemId: number;
  serviceOrderItemManualId: string;
  serviceOrderItemSerialNumber: string;
  serviceOrderLocationName: string;
  serviceOrderManualId: string;
  serviceOrderShippingMode: string;
  serviceOrderShippingTo: number;
  serviceOrderSourceLocationid: number;
  serviceOrderTrackingNumber: string;
  isChecked: boolean;

}
export class BtSericeOrderReceiveItemReq {
  serviceOrderItemId: number;
}
export class BtServiceOrderAssignReq {
  serviceOrderItemId: number;
  assignedTo: string;

}
export class BtJobCardResponse {
  accountName: string;
  accountSapNumber: string;
  arriableStatus: number;
  assignedToEmailId: string;
  assignedToFirstName: string;
  assignedToLastName: string;
  assignedToMiddleName: string;
  brcCaseId: number;
  brcCaseManualId: string;
  isAssigned: boolean;
  isAssignedReturned: boolean;
  isCustomerInventory: boolean;
  isJobCardRepaired: boolean;
  isReceived: boolean;
  isReturnedToSource: String;
  jobCardAssignedToEmailId: string;
  jobCardCreatedByEmailId: string;
  jobCardId: number;
  jobCardIsQcDone: boolean;
  jobCardIsReturndToSource: boolean;
  jobCardLastModifiedByEmailId: string;
  jobCardManualId: string;
  jobCardRepairProductState: number;
  jobCardRepariDate: string;
  jobCardReturnedDate: string;
  jobCardServiceOrderItemId: number;
  productDescription: string;
  productId: number;
  productPartcode: string;
  productType: string;
  returnedStatus: boolean;
  serviceOrderId: number;
  serviceOrderItemId: number;
  serviceOrderItemManualId: string;
  serviceOrderItemSerialNumber: string;
  serviceOrderManualId: string;
  batteryAhAft: string;
  batteryVoltageAft: string;
  batteryVoltageBef: string;
  berRemarks: string;
  btBalanceAt: string;
  cellBledge: string;
  cellVmax: string;
  cellVmin: string;
  correctiveAction: string;
  engRemarks: string;
  engResolutionMethod: string;
  matSorteg: string;
  currentStatusType: string;
  brcCaseChallanNumber: string;
  fileId: number = undefined;
  filename: string;
  fileCreatedDate: string;
  observation1: string;
  observation2: string;
  observation3: string;
  observation4: string;
  brcCaseStage: string;w
  // observation

}

export class IsCheckEditJobCard {
  isAssignQC: boolean = false;
  isProductConsume: boolean = false;
  showAssignEngineerBox: boolean = true;
  showProductConsumeInput: boolean = false;
  showItemTable: boolean = false;
  isStockConsume: boolean = false;
  isShowAvStock: boolean = false;

}
export class BtUpdateJobCardRequest {
  jobCardId: number;
  repairState: number = 0;
  assignToQc: boolean;
  consumedItems: Array<BtConsumedItemsForRepair>;
  assignToQcEmailId: string = '0';
  batteryVoltageBef: string;
  batteryVoltageAft: string;
  batteryAhAft: string;
  cellVmax: string;
  cellVmin: string;
  correctiveAction: string='0';
  engResolutionMethod: string;
  engRemarks: string;
  btBalanceAt: string;
  cellBledge: string;
  matSorteg: string;
  berRemarks: string = '0';
  filename: string;
  //Observation
  observation1: string;
  observation2: string;
  observation3: string;
  observation4: string;
}
export class BtConsumedItemsForRepair {
  consumedProductId: number;
  quantity: number;
  isFromStock: boolean;
  partCode: string;
  consumeFromStock: string;
}
export class UpdateJobCardQcRequest {
  jobCardQcId: number;
  qcPidDate: string;
  qcCorrectiveActionTaken: string = '0';
  qcElectricalCheck: string = '0';
  qcElectricalRemarks: string = '0';
  qcFaultObservedDuringPID: string;
  qcFinalApproval: string = '0';
  qcParameterCheck: string = '0';
  qcPhysicalCheck: string = '0';
  qcPhysicalRemarks: string = '0';
  qcSoftWareupdation: string = '0';
  qcSohUpdation: string = '0';
  qcVoltageGap: string = '0';
  qcVmax: string;
  qcVmin: string;
  qcObservation1: string;
  qcObservation2: string;
}
export class RepairEngineerList {
  contactEmailId: string;
  contactFirstName: string;
  contactId: number;
  contactLastName: string;
  contactMobileNumber: string;
  cotactMiddleName: string;
  designationId: number;
}
export class ReAssignJobCardReq {
  jobCardId: number;
  reAssignTo: string = '0';
}
export class BtRmaList {
  rmaId: number;
  rmaManualId: string;
}
export class BtAssetForLIst {
  id: number;
  productDescription: string;
  productPartCode: string;
  serialNumber: string;
}
export class anotherSA {
  serviceAppointmentId: number;
}

export class BtServiceAppointmentProductConsumedResponse {
  serviceAppointmentId: number;
  serviceAppointmentManualId: string;
  partCode: string;
  partDescription: string;
  consumedQty: number;
  consumedType: string;
  remark: string;
  productId: number;
}
export class BtServiceAppointmentResponseSchOnly {
  correctiveAction: string;
  resolutionMethodId: number
  resolutionMethodName: string;
  schOnlyId: number;
  sparePartName: string;
  sparePartQuantity: string;
  sparePartReplaced: string;
  sparePartReplacedFrom: string;
  customerRate: string;
  customerBillingRate: string;

  problem: Array<ProblemReportedSch> = [];
}
export class ProblemReportedSch {
  id: number;
  schId: number;
  solutinName: string;
}

export class BtServiceOrderReturnRequest {
  destinationLocationId: number;
  shippingTo: string;
  shippingMode: string = '0';
  trackingNumber: string;
  items: Array<BtServiceOrderReturnItems>
}
export class BtServiceOrderReturnItems {
  serviceOrderItemId: number;
  itemchecked: boolean;
}

export class BtExpenseAccountsOnlyRequest {
  btExpenseManagementId: number;
  businessAreaId: number = 0;
  businessPlaceId: number = 0;
  documentDate: string
  narration: string;
  postPark: boolean;
}
export class BTCASEPODLIST {
  SSA: string;
  circle: string;
  caseManualId: string;
  id: number;
  caseidselected: boolean;
  caseReason: string;
  caseTypes: string;
  first_name: string;
  last_name: string;
  middle_name:string;
}
export class BtCustomerWccResp {
  modifiedDate: Date;
  manualId: string;
  circle: string;
  reasonName: string;
  caseTypeId: number;
  customer: string;
  wccStatusId: number;
  caseTypeName: string;
  id: number;
  createdDate: Date;
  reasonId: number;
  JmsNo: string;
  approvalDate: Date;
  approvalStatus: string;
  caseManualId: string;
  caseid: number;
  createdBy: string;
  createdByEmailId: string;
  invoice_date: Date;
  invoice_number: string;
  lastModifiedBy: string;
  lastModifiedByEmailId: string;
  podate: Date
  ponumber: string;
  wccRejectReason: string;
  remarks: string;
  serviceCenter: string;
  wccStatus: string;
  wccRejectReasonId: number = 0;
  submitted_by_sch_date:Date;
  totalCharges:number;
  approvedByRsh: Date;
  customerBillingRate: number;
  customerPoDate: Date;
  customerPoNumber: string;
  customerRate: number;
  exicom_invoice_date: Date;
  exicom_invoice_number: number;
  grnDate: Date;
  grnNumber: string;
  otherReason: string
  other_rejection_reason: string;
  podShippingExpectedDate: Date;
  podShippingMod: string;
  podShippingName: string;
  podShippingTrackingNumber: string;
  poddate: Date;
  podnumber: string;
  sapInvoiceNumber: null
  servicePeriodFrom: Date;
  servicePeriodTo: Date;
  submittedBySchDate: Date;
  legalEntityName:string;

}
export class BTPODRequest {
  caseId: Array<number>;
  podNumber: string;
  podDate: any;
  podShippingMode: string;
  podShippingToName: string;
  podShippingTrackingNumber: string;
  podShippingExpectedDate: any;
  jmsNo: string;
  fileDetails: string;
  customer: string;
	customerPoNumber: string;
  customerPoDate: any;
	grnNumber: string;
	grnDate: Date;
	servicePeriodFrom: Date;
	servicePeriodTo: Date;
}

export class BtWccCustomerApproveRequest {
  id: number;
  invoiceNumber: string;
  invoiceDate: any;
  sapInvoiceNumber:string;
}

export class BtCustomerWccRejectionList {
  id: number = 0;
  wccCustomerRejectList: string;
}

export class BtCustomerWccRejectReq {
  remarks: string;
  rejectReasonId: number = 0;
  id: number;
}
export class CirclesForList {
  circleName: string;
}

export class AvailableStockInInventory {
  productPartcode: string;
  exicomTotalQuantity: number;
  exicomTotalOkQuantity: number;
  productDescription: string;
}
export class BtContactForlist {
  firstName: string;
  lastName: string;
  middleName: string;
  fullName?: string;
  phoneNumber: string;
  contactId: number;
  emailId: string

}

export class BtWCCResponse {
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
  ///kalindi

  vendorPoGlcode:number;

}

export class BtWccCaseResp{
  caseId: number;
  caseManualId: string;
  sapareCharges: number;
  siteCharge: number;
  totalCharge: number;
}