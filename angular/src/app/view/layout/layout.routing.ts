import { AnnexureLineItemdetailsModule } from './../annexureLineItemdetails/annexureLineItemdetails.module';
import { LocalConveyancedetailsModule } from './../expensesmanagementRelated/localConveyancedetails/localConveyancedetails.module';
import { LayoutComponent } from "./layout.component";
import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { LayoutgaurdGuard } from '../../gaurd/layoutgaurd.guard';
import { P403Component } from '../../p403/p403.component';
import { loadAsync } from 'jszip';
const routes: Routes = [
  {
    path: '',
    component: LayoutComponent, canActivate: [LayoutgaurdGuard], canActivateChild: [LayoutgaurdGuard], children: [
      {
        path: 'accounts',
        loadChildren: () => import('../accounts/accounts.module').then(m => m.AccountsModule)
      },
      {
        path: 'accounts/:id',
        loadChildren: () => import('../accountsindetails/accountsindetails.module').then(m => m.AccountsindetailsModule)
      },
      {
        path: 'expense/:id',
        loadChildren: () => import('../expensesmanagementdetails/expensesmanagementdetails.module').then(m => m.ExpensesmanagementdetailsModule)
      },

      {
        path: 'home',
        loadChildren: () => import('../home/home.module').then(m => m.HomeModule)
      },
      {
        path: 'contacts',
        loadChildren: () => import('../contacts/contacts.module').then(m => m.ContactsModule)
      },
      {
        path: 'contacts/:id',
        loadChildren: () => import('../contactsdetails/contactsdetails.module').then(m => m.ContactsdetailsModule)
      },
      {
        path: 'assets',
        loadChildren: () => import('../assets/assets.module').then(m => m.AssetsModule)
      },
      {
        path: 'assets/:id',
        loadChildren: () => import('../assetsdetails/assetsdetails.module').then(m => m.AssetsdetailsModule)
      },

      {
        path: 'zone',
        loadChildren: () => import('../zone/zone.module').then(m => m.ZoneModule)
      }
      ,
      {
        path: 'warehouse',
        loadChildren: () => import('../warehouse/warehouse.module').then(m => m.WarehouseModule)
      },

      {
        path: 'userprofile',
        loadChildren: () => import('../userprofile/userprofile.module').then(m => m.UserprofileModule)
      },
      {
        path: 'warehouse/:id',
        loadChildren: () => import('../warehousedetails/warehousedetails.module').then(m => m.WarehousedetailsModule)
      },
      {
        path: 'products',
        loadChildren: () => import('../products/products.module').then(m => m.ProductsModule)
      },
      {
        path: 'circles',
        loadChildren: () => import('../Circles/circles.module').then(m => m.CirclesModule)
      },
      {
        path: 'products/:id',
        loadChildren: () => import('../productsdetails/productsdetails.module').then(m => m.ProductsdetailsModule)
      },
      {
        path: 'circles/:id',
        loadChildren: () => import('../circlesindetails/circlesindetails.module').then(m => m.CirclesindetailsModule)
      },
      {
        path: 'layout',
        loadChildren: () => import('../layout/layout.module').then(m => m.LayoutModule)
      },
      {
        path: 'Opportunities',
        loadChildren: () => import('../opportunities/opportunities.module').then(m => m.OpportunitiesModule)
      },
      {
        path: 'Opportunities/:id',
        loadChildren: () => import('../opportunitiesdetails/opportunitiesdetails.module').then(m => m.OpportunitiesdetailsModule)
      }
      ,
      {
        path: 'Quote',
        loadChildren: () => import('../quote/quote.module').then(m => m.QuoteModule)
      },
      {
        path: 'Quote/:id',
        loadChildren: () => import('../quotedetails/quotedetails.module').then(m => m.QuotedetailsModule)
      },
      {
        path: 'productLineItems',
        loadChildren: () => import('../ProductLineItems/ProductLineItems.module').then(m => m.ProductLineItemsModule)
      },
      {
        path: 'productLineItems/:id',
        loadChildren: () => import('../ProductLineItemsdetails/productLineItemsdetails.module').then(m => m.ProductLineItemsdetailsModule)
      },
      // {
      //     path: 'opportunitycase',
      //     loadChildren: () => import('../opportunitycase/opportunitycase.module').then(m => m.opportunitycaseModule)
      // },
      // {
      //     path: 'opportunitycase/:id',
      //     loadChildren: () => import('../opportunitycasedetails/opportunitycasedetails.module').then(m => m.opportunitycasedetailsModule)
      // },
      {
        path: 'expensesmanagement',
        loadChildren: () => import('../expensesmanagement/expensesmanagement.module').then(m => m.ExpensesmanagementModule)
      },
      {
        path: 'expensesmanagement/:id',
        loadChildren: () => import('../expensesmanagementdetails/expensesmanagementdetails.module').then(m => m.ExpensesmanagementdetailsModule)
      },

      {
        path: 'expensesmanagement/:id/tour/:id2',
        loadChildren: () => import('../expensesmanagementRelated/tourdetails/tourdetails.module').
          then(m => m.TourdetailsModule)
      },
      {
        path: 'expensesmanagement/:id/lodging/:id2',
        loadChildren: () => import('../expensesmanagementRelated/lodgingdetails/lodgingdetails.module').
          then(m => m.LodgingdetailsModule)
      },
      {
        path: 'expensesmanagement/:id/boarding/:id2',
        loadChildren: () => import('../expensesmanagementRelated/boardingdetails/boardingdetails.module').
          then(m => m.BoardingdetailsModule)
      },
      {
        path: 'expensesmanagement/:id/localConveyance/:id2',
        loadChildren: () => import('../expensesmanagementRelated/localConveyancedetails/localConveyancedetails.module').
          then(m => m.LocalConveyancedetailsModule)
      },
      {
        path: 'expensesmanagement/:id/otherChargesdetails/:id2',
        loadChildren: () => import('../expensesmanagementRelated/otherChargesdetails/otherChargesdetails.module').
          then(m => m.OtherChargesdetailsModule)
      },

      {
        path: 'deliverychallan',
        loadChildren: () => import('../deliverychallan/deliverychallan.module').then(m => m.deliverychallanModule)
      },
      {
        path: 'deliverychallan/:id',
        loadChildren: () => import('../deliverychallandetails/deliverychallandetails.module').then(m => m.deliverychallandetailsModule)
      },

      {
        path: 'serviceappointment',
        loadChildren: () => import('../serviceappointment/serviceappointment.module').then(m => m.ServiceappointmentModule)
      },
      {
        path: 'serviceappointment/:id',
        loadChildren: () => import('../serviceappointmentindetails/serviceappointmentindetails.module').then(m => m.ServiceappointmentindetailsModule)
      },
      {
        path: 'workOrder',
        loadChildren: () => import('../workorder/workorder.module').then(m => m.WorkorderModule)
      },
      {
        path: 'workOrder/:id',
        loadChildren: () => import('../workorderindetails/workorderindetails.module').then(m => m.WorkorderindetailsModule)
      },
      {
        path: 'batteryinventoryconsumption',
        loadChildren: () => import('../batteryinventoryconsumption/batteryinventoryconsumption.module').then(m => m.BatteryinventoryconsumptionModule)
      },
      {
        path: 'batteryinventoryconsumption/:id',
        loadChildren: () => import('../batteryinventoryconsumptionindetails/batteryinventoryconsumptionindetails.module').then(m => m.BatteryinventoryconsumptionindetailsModule)
      },
      {
        path: 'serviceReport',
        loadChildren: () => import('../servicereport/servicereport.module').then(m => m.ServicereportModule)
      },
      {
        path: 'serviceReport/:id',
        loadChildren: () => import('../servicereportindetails/servicereportindetails.module').then(m => m.ServicereportindetailsModule)
      },
      {
        path: 'requisition',
        loadChildren: () => import('../requisition/requisition.module').then(m => m.RequisitionModule)
      },
      {
        path: 'requisition/:id',
        loadChildren: () => import('../requisitionindetails/requisitionindetails.module').then(m => m.RequisitionindetailsModule)
      },
      {
        path: 'systemConfigurationParameters',
        loadChildren: () => import('../systemconfigurationparameters/systemconfigurationparameters.module').then(m => m.SystemconfigurationparametersModule)
      },
      {
        path: 'systemConfigurationParameters/:id',
        loadChildren: () => import('../systemconfigurationparameters-details/systemconfigurationparameters-details.module').then(m => m.SystemconfigurationparametersDetailsModule)
      },
      {
        path: 'issuedComponent',
        loadChildren: () => import('../issuedcomponents/issuedcomponents.module').then(m => m.IssuedcomponentsModule)
      },
      {
        path: 'issuedComponent/:id',
        loadChildren: () => import('../issuedcomponentsindetails/issuedcomponentsindetails.module').then(m => m.IssuedcomponentsindetailsModule)
      },
      {
        path: 'manageComponent',
        loadChildren: () => import('../managecomponent/managecomponent.module').then(m => m.ManagecomponentModule)
      },
      {
        path: 'manageComponent/:id',
        loadChildren: () => import('../managecomponentindetails/managecomponentindetails.module').then(m => m.ManagecomponentindetailsModule)
      },
      {
        path: 'subProductInventory',
        loadChildren: () => import('../subproductinventory/subproductinventory.module').then(m => m.SubproductinventoryModule)
      },
      {
        path: 'subProductInventory/:id',
        loadChildren: () => import('../subproductinventorydetails/subproductinventorydetails.module').then(m => m.SubproductinventorydetailsModule)
      },
      {
        path: 'jobWarranty',
        loadChildren: () => import('../jobwarranty/jobwarranty.module').then(m => m.JobwarrantyModule)
      },
      {
        path: 'jobWarranty/:id',
        loadChildren: () => import('../jobwarrantydetails/jobwarrantydetails.module').then(m => m.JobwarrantydetailsModule)
      },
      {
        path: 'manageInventory',
        loadChildren: () => import('../manageinventory/manageinventory.module').then(m => m.ManageinventoryModule)
      },
      {
        path: 'manageInventory/:id',
        loadChildren: () => import('../manageinventorydetails/manageinventorydetails.module').then(m => m.ManageinventorydetailsModule)
      },
      {
        path: 'serviceResource',
        loadChildren: () => import('../serviceresource/serviceresource.module').then(m => m.ServiceresourceModule)
      },
      {
        path: 'serviceResource/:id',
        loadChildren: () => import('../serviceresourcedetails/serviceresourcedetails.module').then(m => m.ServiceresourcedetailsModule)
      },
      {
        path: 'batteryFSRCBMS',
        loadChildren: () => import('../battery-fsrcbmsli-lonbattery/battery-fsrcbmsli-lonbattery.module').then(m => m.BatteryFSRCBMSLiLonbatteryModule)
      },
      {
        path: 'batteryFSRCBMS/:id',
        loadChildren: () => import('../battery-fsrcbmsli-lonbatterydetails/battery-fsrcbmsli-lonbatterydetails.module').then(m => m.BatteryFSRCBMSLiLonbatterydetailsModule)
      },
      {
        path: 'sites',
        loadChildren: () => import('../sites/sites.module').then(m => m.SitesModule)
      },
      {
        path: 'sites/:siteId',
        loadChildren: () => import('../sitedetails/sitedetails.module').then(m => m.SitedetailsModule)
      },
      {
        path: 'legalentity',
        loadChildren: () => import('../ligalentity/ligalentity.module').then(m => m.LigalEntity)
      },
      {
        path: 'city',
        loadChildren: () => import('../city/city.module').then(m => m.cityModule)
      },
      {
        path: 'city/:cityname',
        loadChildren: () => import('../cityview/cityview.module').then(m => m.CityViewModule)
      },
      {
        path: 'legalentity/:id',
        loadChildren: () => import('../legalentityview/legalentityview.module').then(m => m.LigalEntityViewModule)
      },
      {
        path: 'cases',
        loadChildren: () => import('../case/case.module').then(m => m.caseModule)
      },
      {
        path: 'cases/:caseid',
        loadChildren: () => import('../case-details/caseDetails.module').then(m => m.caseDetailsModule)
      },
      {
        path: 'Mslrequest',
        loadChildren: () => import('../mslrequest/mslrequest.module').then(m => m.MslrequestModule)
      },
      {
        path: 'Mslrequest/:mslId',
        loadChildren: () => import('../mslrequestdetails/mslrequestdetails.module').then(m => m.MslreqestDetailsModule)
      },
      {
        path: 'Bording_lodging_DA',
        loadChildren: () => import('../bording-da/bording-da.module').then(m => m.BordingDAModule)

      },
      {
        path: 'Bording_lodging_DA/:boardingName',
        loadChildren: () => import('../boading-da-details/boadingDaDetails.module').then(m => m.boadingDetailsModule)
      },
      {
        path: 'location',
        loadChildren: () => import('../location/location.module').then(m => m.LocationModule)
      },
      {
        path: 'location/:locationId',
        loadChildren: () => import('../locationdetails/locationdetails.module').then(m => m.locationDetailsModule)
      },
      {
        path: 'createuser',
        loadChildren: () => import('../userregistration/userregistration.module').then(m => m.UserregistrationModule)
      },
      {
        path: 'createuser/:userName',
        loadChildren: () => import('../userprofile-details/userprofile-details.module').then(m => m.UserDetailsModule)
      },
      {
        path: 'productrequest/:id',
        loadChildren: () => import('../productrequestdetails/productrequestdetails.module').then(m => m.ProductrequestdetailsModule)
      },
      {
        path: 'pricebook',
        loadChildren: () => import('../pricebook/pricebook.module').then(m => m.pricebookModule)
      },
      {
        path: 'pricebook/:id',
        loadChildren: () => import('../pricebookdetails/pricebookdetails.module').then(m => m.PricebookdetailsModule)
      },
      {
        path: 'pricebookproducts/:id',
        loadChildren: () => import('../pricebookproductsdetails/pricebookproductsdetails.module').then(m => m.pricebookproductsDetailsModule)

      },
      {
        path: 'productrequest',
        loadChildren: () => import('../productrequest/productrequest.module').then(m => m.ProductrequestModule)
      },
      {
        path: 'producttransfer/:transferitemid',
        loadChildren: () => import('../producttransferdetails/producttransferdetails.module').then(m => m.ProducttransferdetailsModule)
      },
      {
        path: 'producttransferLineItemDetail/:productTransferItemId',
        loadChildren: () => import('../product-transfer-line-item-details/product-transfer-line-item-details.module').then(m => m.ProductTransferLineItemDetailModule)
      },
      {
        path: 'producttransfer/ptitemdetails/:ptItemid',
        loadChildren: () => import('../producttransferitemdetails/producttransferitemdetails.module').then(m => m.PTIDetailsModule)
      },
      {
        path: 'productreturnorder',
        loadChildren: () => import('../productreturnorder/productreturnorder.module').then(m => m.ProductreturnorderModule)
      },
      {
        path: 'productreturnorder/:returnorderid',
        loadChildren: () => import('../productreturnorderdetails/productreturnorderdetails.module').then(m => m.ProductreturnorderdetailsModule)
      },
      {
        path: 'productreturnorder/lineItem/:lineItemId',
        loadChildren: () => import('../product-return-line-item-detail/product-return-line-item-detail.module').then(m => m.ProductReturnLineItemDetailModule)
      },
      {
        path: 'annexure',
        loadChildren: () => import('../annexure/annexure.module').then(m => m.AnnexureModule)
      },
      {
        path: 'producttransfer',
        loadChildren: () => import('../producttransfer/producttransfer.module').then(m => m.ProducttransferModule)
      },
      {
        path: 'itemtobeapproved',
        loadChildren: () => import('../itemtobeapproved/itemtobeapproved.module').then(m => m.ItemtobeapprovedModule)
      },
      {
        path: 'repairengineer',
        loadChildren: () => import('../repair-engineer/repair-engineer.module').then(m => m.RepairEngineerModule)
      },
      {
        path: 'repairengineer/:id',
        loadChildren: () => import('../repair-engineer-details/repair-engineer-details.module').then(m => m.RepairEngineerDetailsModule)

      },
      {
        path: 'annexure/:id',
        loadChildren: () => import('../annexuredetails/annexuredetails.module').then(m => m.AnnexuredetailsModule)
      },
      {
        path: 'annexure/:id/annexureLineItem/:id2',
        loadChildren: () => import('../annexureLineItemdetails/annexureLineItemdetails.module').then(m => m.AnnexureLineItemdetailsModule)
      },
      {
        path: 'roles',
        loadChildren: () => import('../roledetails/roledetails.module').then(m => m.RoledetailsModule)
      },
      {
        path: 'ssa',
        loadChildren: () => import('../ssa/ssa.module').then(m => m.SSAModule)
      },
      {
        path: 'ssa/:id',
        loadChildren: () => import('../ssadetails/ssadetails.module').then(m => m.SSADetailsModule)
      },
      {
        path: 'report',
        loadChildren: () => import('../report/report.module').then(m => m.ReportModule)
      },
      {
        path: 'inventorymonitoringandplanning',
        loadChildren: () => import('../inventorymonitoring/inventorymonitoring.module').then(m => m.InventorymonitoringModule)
      },
      {
        path: 'workflow',
        loadChildren: () => import('../workflows/workflows.module').then(m => m.WorkflowsModule)
      },
      {
        path: 'productrequest/lineItems/:id',
        loadChildren: () => import('../product-request-line-items-detail/product-request-line-items-detail.module').then(m => m.ProductRequestLineItemsDetailModule)
      },
      {
        path: 'quetetemplatetc',
        loadChildren: () => import('../quete-template-tand-c/quete-template-tand-c.module').then(m => m.QueteTemplateTCModule)
      },
      {
        path: 'quetetemplatetc/:id',
        loadChildren: () => import('../quete-template-tand-cdetails/quete-template-tand-cdetails.module').then(m => m.QueteTemplateTandCDetailsModule)
      },
      {
        path: 'commercial',
        loadChildren: () => import('../commercial-page/commercial-page.module').then(m => m.CommercialPageModule)
      },
      {
        path: 'commercial/:id',
        loadChildren: () => import('../commercial-page-detail/commercial-page-detail.module').then(m => m.commercialPageDetailModule)
      }, {
        path: 'bulkupload',
        loadChildren: () => import('../bulkupload/bulkupload.module').then(m => m.BulkUploadModule)
      }, {
        path: 'approval',
        loadChildren: () => import('../approval/approval.module').then(m => m.ApprovalModule)
      }, {
        path: 'approval/:id',
        loadChildren: () => import('../approvalDetails/approvaldetails.module').then(m => m.ApprovaldetailsModule)
      }, {
        path: 'repairdct',
        loadChildren: () => import('../repair-dct/repair-dct.module').then(m => m.RepairDCTModule)
      }, {
        path: 'repairdct/:id',
        loadChildren: () => import('../repair-dctdetails/rpair-dctdetails.module').then(m => m.RpairDetailsModule)
      }, {
        path: 'wcc',
        loadChildren: () => import('../wcc/wcc.module').then(m => m.WCCModule)
      }, {
        path: 'wcc/:id',
        loadChildren: () => import('../wc-details/wc-details.module').then(m => m.WCDetailsModule)
      }, {
        path: 'invoice',
        loadChildren: () => import('../invoice/invoice.module').then(m => m.InvoiceModule)
      }, {
        path: 'invoice/:id',
        loadChildren: () => import('../invoice-details/invoice-details.module').then(m => m.InvoicesDetailsModule)
      }, {
        path: 'serviceorder',
        loadChildren: () => import('../serviceorder/serviceorder.module').then(m => m.ServiceorderModule)
      },
      {
        path: 'serviceorder/newso',
        loadChildren: () => import('../newserviceorder/newserviceorder.module').then(m => m.NewserviceOrderModule)
      },

      {
        path: 'serviceorder/:id',
        loadChildren: () => import('../serviceorderdetails/serviceorderdetails.module').then(m => m.ServiceorderdetailsModule)
      },
      {
        path: 'coustomerinventory',
        loadChildren: () => import('../customerinventory/customerinventory.module').then(m => m.CustomerinventoryModule)
      },
      {
        path: 'location/:locationid/productdetails',
        loadChildren: () => import('../locationproductdetails/locationproductdetails.module').then(m => m.LocationproductdetailsModule)
      },
      {
        path: 'location/:locationid/childlocations',
        loadChildren: () => import('../locationchilddetails/locationchilddetails.module').then(m => m.LocationchilddetailsModule)
      }, {
        path: 'location/:locationid/requisition',
        loadChildren: () => import('../locationrequesationdetails/locationrequesationdetails.module')
          .then(m => m.LocationrequesationdetailsModule)
      },
      {
        path: 'prc',
        loadChildren: () => import('../case-pre-regis/case-pre-regis.module').then(m => m.CasePreRegisModule)
      },
      {
        path: 'prc/:id',
        loadChildren: () => import('../case-pre-regis-detail/case-pre-regis-detail.module').then(m => m.PreRegisCaseDetailModul)
      },
      {
        path: 'qbrowser',
        loadChildren: () => import('../qbrowser/qbrowser.module').then(m => m.QbrowserModule)
      },
      {
        path: 'accounts/:accountsap/case',
        loadChildren: () => import('../accountscasedetails/accountscasedetails.module').then(m => m.AccountscasedetailsModule)

      },
      {
        path: 'myinventory',
        loadChildren: () => import('../myinventorydetail/myinventorydetail.module').then(m => m.MyinventorydetailModule)
      },
      {
        path: '403',
        component: P403Component,
        data: {
          title: 'Forbidden'
        }
      },
      {
        path: 'objectactionhistory',
        loadChildren: () => import('../objecthistoryfull/objecthistoryfull.module').then(m => m.ObjecthistoryfullModule)
      },
      {
        path: 'location/:locationid/newpr',
        loadChildren: () => import('../newpr/newpr.module').then(m => m.NewprModule)
      },
      {
        path: 'location/:locationid/newro',
        loadChildren: () => import('../newro/newro.module').then(m => m.NewroModule)
      },
      {
        path: 'productrequest/:id/newpt',
        loadChildren: () => import('../newpt/newpt.module').then(m => m.NewptModule)
      },
      {
        path: 'productrequest/:id/newptr',
        loadChildren: () => import('../newptr/newptr.module').then(m => m.NewptrModule)
      },
      {
        path: 'productreturnorder/:id/newror',
        loadChildren: () => import('../newror/newror.module').then(m => m.NewrorModule)
      }, {
        path: 'helpguide',
        loadChildren: () => import('../helpguide/helpguide.module').then(m => m.helpguideModule)
      },
      {
        path: 'details/:id/:component/:pagename',
        loadChildren: () => import('../detailsofall/detailsofall.module').then(m => m.DetailofallModule)
      },
      {
        path: 'partcodechange',
        loadChildren: () => import('../partcodechangelist/partcodechangelist.module').then(m => m.PartcodechangelistModule)
      },

      {
        path: 'reportdetails/:id',
        loadChildren: () => import('../reportwizard-details/reportwizard-details.module').then(m => m.ReportwizardModule)
      },
      {
        path: 'bulkutils',
        loadChildren: () => import('../bulkutils/bulkutils.module').then(m => m.BulkutilsModule)
      },
      {
        path: 'bulkutils/:id',
        loadChildren: () => import('../bulkutilsdetails/bulkutilsdetails.module').then(m => m.BulkutilsdetailsModule)
      },
      {
        path: 'bat_account',
        loadChildren: () => import('../../battery/batview/bat-accounts/bat-accounts.module').then(m => m.BatAccountsModule)
      },
      {
        path: 'bat_account/:id',
        loadChildren: () => import('../../battery/batview/bat-accountsdetail/bat-accountsdetail.module').then(m => m.BatAccountsdetailsModule)
      },
      {
        path: 'bat_product',
        loadChildren: () => import('../../battery/batview/bat-product/bat-product.module').then(m => m.BatProductModule)
      },
      {
        path: 'bat_product/:id',
        loadChildren: () => import('../../battery/batview/bat-productdetail/bat-productdetail.module').then(m => m.ProductsdetailsModule)
      },

      {
        path: 'bat_site',
        loadChildren: () => import('../../battery/batview/bat-site/bat-site.module').then(m => m.batSiteModule)
      }, {
        path: 'bat_asset',
        loadChildren: () => import('../../battery/batview/batt-asset/batt-asset.module').then(m => m.BattAssetModule)
      },
      {
        path: 'bat_site/:siteId',
        loadChildren: () => import('../../battery/batview/bat-site-detail/bat-site-detail.module').then(m => m.batSiteDetailModule)
      },
      {
        path: 'bat_product_request',
        loadChildren: () => import('../../battery/batview/bat-productrequest/bat-productrequest.module').then(m => m.BatProductrequestModule)
      },
      {
        path: 'bat_product_request/:id',
        loadChildren: () => import('../../battery/batview/bat-productrequestdetail/bat-productrequestdetail.module').then(m => m.BatProductrequestdetailsModule)
      },
      {
        path: 'bat_product_request/:id/bt_newpr',
        loadChildren: () => import('../../battery/batview/new-productrequest/new-productreqest.module').then(m => m.BtNewProductReqModule)
      },
      {
        path: 'bat_product_request_items/:itemId',
        loadChildren: () => import('../../battery/batview/bat-product-request-items-details/bat-product-request-items-details.module').then(m => m.BatProductRequestItemsDetailModule)
      },
      {
        path: 'bat_product_transfer',
        loadChildren: () => import('../../battery/batview/bat-product-transfer/bat-product-transfer.module').then(m => m.BatProductTransferModule)
      },
      {
        path: 'bat_product_transfer/:transferitemid',
        loadChildren: () => import('../../battery/batview/bat-product-transferdetail/bat-product-transferdetail.module').then(m => m.BatProducttransferdetailsModule)
      },
      {
        path: 'bat_product_transfer/:id/newtransfer',
        loadChildren: () => import('../../battery/batview/newbatproducttransfer/newbatproducttransfer.module').then(m => m.NewBatProductTransferModule)
      },
      {
        path: 'bat_product_transferLineItemsDetails:/id',
        loadChildren: () => import('../../battery/batview/bat-product-transfer-line-items-detail/bat-product-transfer-line-items-detail.module').then(m => m.BatProductTransferLineItemsDetailModule)
      },
      {

        path: 'bat_expense',
        loadChildren: () => import('../../battery/batview/bat-expensesmanagement/bat-expensesmanagement.module').then(m => m.BatExpensesmanagementModule)
      },
      {
        path: 'bat_expense/:id',
        loadChildren: () => import('../../battery/batview/bat-expensesmanagement-detail/bat-expensesmanagement-detail.module').then(m => m.BatExpensesmanagementDetailModule)
      },
      {
        path: 'bat_expense/:id/battour/:id2',
        loadChildren: () => import('../../battery/batview/bat-expensesmanagement-related/bat-tourdetails/bat-tourdetails.module').
          then(m => m.BatTourdetailsModule)
      },
      {
        path: 'bat_expense/:id/batlodging/:id2',
        loadChildren: () => import('../../battery/batview/bat-expensesmanagement-related/bat-lodgingdetails/bat-lodgingdetails.module').
          then(m => m.BatLodgingdetailsModule)
      },
      {
        path: 'bat_expense/:id/batboarding/:id2',
        loadChildren: () => import('../../battery/batview/bat-expensesmanagement-related/bat-boardingdetails/bat-boardingdetails.module').
          then(m => m.BatBoardingdetailsModule)
      },
      {
        path: 'bat_expense/:id/batlocalConveyance/:id2',
        loadChildren: () => import('../../battery/batview/bat-expensesmanagement-related/bat-local-conveyancedetails/bat-local-conveyancedetails.module').
          then(m => m.BatLocalConveyancedetailsModule)
      },
      {
        path: 'bat_expense/:id/batotherChargesdetails/:id2',
        loadChildren: () => import('../../battery/batview/bat-expensesmanagement-related/bat-other-chargesdetails/bat-other-chargesdetails.module').
          then(m => m.BatOtherChargesdetailsModule)
      },
      {
        path: 'bat_expense/:id/batSAExpensedetails/:id2',
        loadChildren: () => import('../../battery/batview/bat-expensesmanagement-related/bat-saexpensedetails/bat-saexpensedetails.module').
          then(m => m.BatSAExpensedetailsModule)
      },
      {
        path: 'bat_asset/:id',
        loadChildren: () => import('../../battery/batview/btasset-details/btasset-details.module').then(m => m.BTAssetDetailsModules)
      },
      {
        path: 'bat_contacts',
        loadChildren: () => import('../../battery/batview/bat-contacts/bat-contacts.module').then(m => m.BatContactsModule)
      },
      {
        path: 'bat_contacts/:id',
        loadChildren: () => import('../../battery/batview/bat-contacts-details/bat-contacts-details.module').then(m => m.BatContactsDetailsModule)
      },
      {
        path: 'bat_warehouse',
        loadChildren: () => import('../../battery/batview/bat-warehouse/bat-warehouse.module').then(m => m.BatWarehouseModule)
      },
      {
        path: 'bat_warehouse/:locationId',
        loadChildren: () => import('../../battery/batview/bat-warehouse-details/bat-warehouse-details.module').then(m => m.BatWarehouseDetailsModule)
      },
      {
        path: 'bat_warehouse/:locationid/batchildlocations/:battery',
        loadChildren: () => import('../locationchilddetails/locationchilddetails.module').then(m => m.LocationchilddetailsModule)
      },
      {
        path: 'bat_warehouse/:locationid/requisition',
        loadChildren: () => import('../../battery/batview/bat-warehouse-related/bat-locationrequestdetails/bat-locationrequestdetails.module').then(m => m.BatLocationrequestdetailsModule)
      },
      {
        path: 'bat_warehouse/:locationid/productdetails',
        loadChildren: () => import('../../battery/batview/bat-warehouse-related/bat-locationproductdetails/bat-locationproductdetails.module').then(m => m.BatLocationproductdetailsModule)
      },
      {
        path: 'bat_warehouse/:locationId/newpr',
        loadChildren: () => import('../../battery/batview/new-productrequest/new-productreqest.module').then(m => m.BtNewProductReqModule)
      },
      {
        path: 'bat_warehouse/:locationId/newro',
        loadChildren: () => import('../../battery/batview/new-productrequest/new-productreqest.module').then(m => m.BtNewProductReqModule)
      },
      {
        path: 'bat_userRole',
        loadChildren: () => import('../../battery/batview/bat-userrole/bat-userrole.module').then(m => m.BatUserroleModule)
      },
      {
        path: 'bat_serviceResources',
        loadChildren: () => import('../../battery/batview/bat-serviceresource/bat-serviceresource.module').then(m => m.BatServiceresourceModule)
      },
      {
        path: 'bat_serviceResources/:id',
        loadChildren: () => import('../../battery/batview/bat-serviceresource-details/bat-serviceresource-details.module').then(m => m.BatServiceresourceDetailsModule)
      },
      {
        path: 'bat_helpguide',
        loadChildren: () => import('../../battery/batview/bat-helpguide/bat-helpguide.module').then(m => m.BatHelpguideModule)
      },

      {
        path: 'soreturn',
        loadChildren: () => import('../service-order-return/service-order-return.module').then(m => m.ServiceOrderReturnModule)
      },
      {

        path: 'soreturn/:id',
        loadChildren: () => import('../service-order-return-detail/service-order-return-detail.module').then(m => m.ServiceOrderReturnDetailModule)
      }, {
        path: 'bat_case',
        loadChildren: () => import('../../battery/batview/bat-case/bat-case.module').then(m => m.BatCaseModule)
      }, {
        path: 'bat_case/:id',
        loadChildren: () => import('../../battery/batview/bat-case-detail/bat-case-detail.module').then(m => m.BatCaseDetailModule)
      }, {
        path: 'rating',
        loadChildren: () => import('../engineer-rating/engineer-rating.module').then(m => m.EngineerRatingModule)
      },
      {

        path: 'bat_legalentity',
        loadChildren: () => import('../../battery/batview/bat-legalentity/bat-legalentity.module').then(m => m.BatLegalEntityModule)
      },
      {

        path: 'bat_legalentity/:id',
        loadChildren: () => import('../../battery/batview/bat-legalentitydetail/bat-legalentitydetail.module').then(m => m.BatLegalEntityDetailModule)
      },
      {

        path: 'bat_serviceorder',
        loadChildren: () => import('../../battery/batview/bat-serviceorder/bat-serviceorder.module').then(m => m.BatServiceorderModule)
      },
      {
        path: 'bat_serviceorder/newso',
        loadChildren: () => import('../../battery/batview/bat-new-serviceorder/bat-new-serviceorder.module').then(m => m.BatNewserviceOrderModule)
      },
      {

        path: 'bat_serviceorder/:id',
        loadChildren: () => import('../../battery/batview/bat-serviceorderdetails/bat-serviceorderdetails.module').then(m => m.BatServiceorderdetailsModule)
      },
      {

        path: 'bat_user',
        loadChildren: () => import('../../battery/batview/bat-userregistration/bat-userregistration.module').then(m => m.BatUserregistrationModule)
      },
      {

        path: 'bat_user/:userName',
        loadChildren: () => import('../../battery/batview/bat-userregistrationdetails/bat-userregistrationdetails.module').then(m => m.BatUserRegistrationDetailsModule)
      },
      {

        path: 'bat_serviceappointment',
        loadChildren: () => import('../../battery/batview/bat-serviceappointment/bat-serviceappointment.module').then(m => m.BatServiceappointmentModule)
      },
      {

        path: 'bat_serviceappointment/:id',
        loadChildren: () => import('../../battery/batview/bat-serviceappointmentdetails/bat-serviceappointmentdetails.module').then(m => m.BatServiceappointmentindetailsModule)
      },
      {
        path: 'bat_circle',
        loadChildren: () => import('../../battery/batview/bat-circle/bat-circle.module').then(m => m.BatCircleModule)
      },
      {
        path: 'bat_circle/:id',
        loadChildren: () => import('../../battery/batview/bat-circle-detail/bat-circle-detail.module').then(m => m.BatCircleDetailModule)
      },
      {
        path: 'batapprovalflow',
        loadChildren: () => import('../../battery/batview/bat-approvalviewlist/bat-approvalviewlist.module').then(m => m.BatApprovalviewlistModule)
      },
      {
        path: 'batapprovalflow/:id',
        loadChildren: () => import('../../battery/batview/bat-approvalflow/bat-approvalflow.module').then(m => m.BatApprovalflowModule)
      },
      {
        path: 'bat_itemtobeapproved',
        loadChildren: () => import('../../battery/batview/bat-tobe-approved/bat-tobe-approved.module').then(m => m.BatTobeApprovedModule)
      }, {


        path: 'bat_wcc',
        loadChildren: () => import('../../battery/batview/bat-wcc/bat-wcc.module').then(m => m.BatWCCModule)
      },
      {
        path: 'bat_wcc/:id',
        loadChildren: () => import('../../battery/batview/bat-wcc-detail/bat-wcc-detail.module').then(m => m.BatWCCDetailsModule)
      },
      {
        path: 'bat_invoice',
        loadChildren: () => import('../../battery/batview/bat-invoice/bat-invoice.module').then(m => m.BatInvoiceModule)
      },
      {
        path: 'bat_invoice/:id',
        loadChildren: () => import('../../battery/batview/bat-invoice-detail/bat-invoice-detail.module').then(m => m.BatInvoicesDetailsModule)
      },
      {
        path: 'bat_commercial',
        loadChildren: () => import('../../battery/batview/bat-commercial/bat-commercial.module').then(m => m.BatCommercialPageModule)
      },
      {
        path: 'bat_commercial/:id',
        loadChildren: () => import('../../battery/batview/bat-commercial-detail/bat-commercial-detail.module').then(m => m.BatcommercialDetailModule)
      },
      // {
      //   path: 'bat_fsr_service',
      //   loadChildren: () => import('../../battery/batview/bat-fsr-service/bat-fsr-service.module').then(m => m.BatFsrServiceModule)
      // },
      {
        path: 'bat_prc',
        loadChildren: () => import('../../battery/batview/bat-pre-registration-case/bat-pre-registration-case.module').then(m => m.BatPreRegistrationCaseModule)
      },
      {
        path: 'bat_prc/:id',
        loadChildren: () => import('../../battery/batview/bat-pre-registration-case-detail/bat-pre-registration-case-detail.module').then(m => m.BatPreRegistrationCaseDetailModule)
      },
      {
        path: 'bat_mslrequest',
        loadChildren: () => import('../../battery/batview/bat-mslrequest/bat-mslrequest.module').then(m => m.BatMslrequestModule)
      },
      {
        path: 'bat_mslrequest/:mslId',
        loadChildren: () => import('../../battery/batview/bat-mslrequest-detail/bat-mslrequest-detail.module').then(m => m.BatMslrequestDetailModule)
      },
      // {
      //   path: 'bat_report',
      //   loadChildren: () => import('../../battery/batview/bat-report/bat-report.module').then(m => m.BatReportModule)
      // },
      {
        path: 'bat_userprofile',
        loadChildren: () => import('../../battery/batview/bat-user-profile/bat-user-profile.module').then(m => m.BatUserProfileModule)
      },
      {
        path: 'bat_boding_lodgin_DA',
        loadChildren: () => import('../../battery/batview/bt-boading-loadgin-da/bt-boading-loadgin-da.module').then(m => m.BTBodingLodginDAModule)
      },
      {
        path: 'bat_boding_lodgin_DA/:boardingName',
        loadChildren: () => import('../../battery/batview/bt-boading-loadgin-da-details/bt-boading-loading-da-details.module').then(m => m.btBoadingLoadginDetailsModule)
      },
      {
        path: 'bat_myinventory',
        loadChildren: () => import('../../battery/batview/bat-my-inventory/bat-my-inventory.module').then(m => m.BatMyInventoryModule)
      },
      {
        path: 'bat_product_returnOrder',
        loadChildren: () => import('../../battery/batview/bat-product-return/bat-product-return.module').then(m => m.BatProductReturnModule)
      },
      {
        path: 'bat_product_returnOrder/:returnorderid',
        loadChildren: () => import('../../battery/batview/bat-product-return-detail/bat-product-return-detail.module').then(m => m.BatProductReturnDetailModule)
      },
      {
        path: 'bat_rma',
        loadChildren: () => import('../../battery/batview/bat-rma/bat-rma.module').then(m => m.BatRmaModule)
      },
      {
        path: 'bat_rma/:id',
        loadChildren: () => import('../../battery/batview/bat-rma-detail/bat-rma-detail.module').then(m => m.BatRmaDetailModule)
      },
      {
        path: 'bat_brc_case',
        loadChildren: () => import('../../battery/batview/bat-customer-inventory/bat-customer-inventory.module').then(m => m.BatCustomerInventoryModule)
      },
      {
        path: 'bat_brc_case/:id',
        loadChildren: () => import('../../battery/batview/bat-customer-inventory-in-detail/bat-cutomer-inventory-in-detail.module').then(m => m.BatCustomerInventoryInDetailModule)
      },
      {
        path: 'details_of_all_view/:id/:component/:pagename',
        loadChildren: () => import('../../battery/batview/bat-all-view-page/bat-all-view-page.module').then(m => m.BatAllViewModule)
      },
      {
        path: 'bat_serviceappointment/anotherSA/:id',
        loadChildren: () => import('../../battery/batview/bat-another-service-appointment/bat-another-service-appointment.module').then(m => m.BatAnotherServiceAppointmentModule)
      },
      {
        path: 'bat_job_card',
        loadChildren: () => import('../../battery/batview/bat-job-card/bat-job-card.module').then(m => m.BatJobCardModule)
      },
      {
        path: 'bat_job_card/:id',
        loadChildren: () => import('../../battery/batview/bat-job-card-detail/bat-job-card-detail.module').then(m => m.BatJobCardDetailModule)
      },
      {
        path: 'bat_job_card_qc',
        loadChildren: () => import('../../battery/batview/bat-job-card-qc/bat-job-card-qc.module').then(m => m.BatJobCardQcModule)
      },
      {
        path: 'bat_job_card_qc/:id',
        loadChildren: () => import('../../battery/batview/bat-job-card-qc-detail/bat-job-card-qc-detail.module').then(m => m.BatJobCardQCDetailModule)
      },
      {
        path: 'bat_wcc',
        loadChildren: () => import('../../battery/batview/bat-wcc/bat-wcc.module').then(m => m.BatWCCModule)
      },
      {
        path: 'bat_wcc/:id',
        loadChildren: () => import('../../battery/batview/bat-wcc-detail/bat-wcc-detail.module').then(m => m.BatWCCDetailsModule)
      },
      {
        path: 'bat_invoice',
        loadChildren: () => import('../../battery/batview/bat-invoice/bat-invoice.module').then(m => m.BatInvoiceModule)
      },
      {
        path: 'bat_invoice/:id',
        loadChildren: () => import('../../battery/batview/bat-invoice-detail/bat-invoice-detail.module').then(m => m.BatInvoicesDetailsModule)
      },
      {
        path: 'bat_commercial',
        loadChildren: () => import('../../battery/batview/bat-commercial/bat-commercial.module').then(m => m.BatCommercialPageModule)
      },
      {
        path: 'bat_commercial/:id',
        loadChildren: () => import('../../battery/batview/bat-commercial-detail/bat-commercial-detail.module').then(m => m.BatcommercialDetailModule)
      },
      // {
      //   path: 'bat_fsr_service',
      //   loadChildren: () => import('../../battery/batview/bat-fsr-service/bat-fsr-service.module').then(m => m.BatFsrServiceModule)
      // },
      {
        path: 'bat_prc',
        loadChildren: () => import('../../battery/batview/bat-pre-registration-case/bat-pre-registration-case.module').then(m => m.BatPreRegistrationCaseModule)
      },
      {
        path: 'bat_prc/:id',
        loadChildren: () => import('../../battery/batview/bat-pre-registration-case-detail/bat-pre-registration-case-detail.module').then(m => m.BatPreRegistrationCaseDetailModule)
      },
      {
        path: 'bat_mslrequest',
        loadChildren: () => import('../../battery/batview/bat-mslrequest/bat-mslrequest.module').then(m => m.BatMslrequestModule)
      },
      {
        path: 'bat_mslrequest/:mslId',
        loadChildren: () => import('../../battery/batview/bat-mslrequest-detail/bat-mslrequest-detail.module').then(m => m.BatMslrequestDetailModule)
      },
      // {
      //   path: 'bat_report',
      //   loadChildren: () => import('../../battery/batview/bat-report/bat-report.module').then(m => m.BatReportModule)
      // },
      {
        path: 'bat_report',
        loadChildren: () => import('../report/report.module').then(m => m.ReportModule)
      },
      {
        path: 'bat_userprofile',
        loadChildren: () => import('../../battery/batview/bat-user-profile/bat-user-profile.module').then(m => m.BatUserProfileModule)
      },
      {
        path: 'bat_boding_lodgin_DA',
        loadChildren: () => import('../../battery/batview/bt-boading-loadgin-da/bt-boading-loadgin-da.module').then(m => m.BTBodingLodginDAModule)
      },
      {
        path: 'bat_boding_lodgin_DA/:boardingName',
        loadChildren: () => import('../../battery/batview/bt-boading-loadgin-da-details/bt-boading-loading-da-details.module').then(m => m.btBoadingLoadginDetailsModule)
      },
      {
        path: 'bat_myinventory',
        loadChildren: () => import('../../battery/batview/bat-my-inventory/bat-my-inventory.module').then(m => m.BatMyInventoryModule)
      },
      {
        path: 'bat_product_returnOrder',
        loadChildren: () => import('../../battery/batview/bat-product-return/bat-product-return.module').then(m => m.BatProductReturnModule)
      },
      {
        path: 'bat_product_returnOrder/:returnorderid',
        loadChildren: () => import('../../battery/batview/bat-product-return-detail/bat-product-return-detail.module').then(m => m.BatProductReturnDetailModule)
      },
      {
        path: 'bat_rma',
        loadChildren: () => import('../../battery/batview/bat-rma/bat-rma.module').then(m => m.BatRmaModule)
      },
      {
        path: 'bat_rma/:id',
        loadChildren: () => import('../../battery/batview/bat-rma-detail/bat-rma-detail.module').then(m => m.BatRmaDetailModule)
      },
      {
        path: 'bat_customer_inventory',
        loadChildren: () => import('../../battery/batview/bat-customer-inventory/bat-customer-inventory.module').then(m => m.BatCustomerInventoryModule)
      },
      {
        path: 'details_of_all_view/:id/:component/:pagename',
        loadChildren: () => import('../../battery/batview/bat-all-view-page/bat-all-view-page.module').then(m => m.BatAllViewModule)
      },
      {
        path: 'batAnotherServiceAppointment/:id',
        loadChildren: () => import('../../battery/batview/bat-another-service-appointment/bat-another-service-appointment.module').then(m => m.BatAnotherServiceAppointmentModule)
      },
      {
        path: 'bat_job_card',
        loadChildren: () => import('../../battery/batview/bat-job-card/bat-job-card.module').then(m => m.BatJobCardModule)
      },
      {
        path: 'bat_job_card/:id',
        loadChildren: () => import('../../battery/batview/bat-job-card-detail/bat-job-card-detail.module').then(m => m.BatJobCardDetailModule)
      },
      {
        path: 'bat_job_card_qc',
        loadChildren: () => import('../../battery/batview/bat-job-card-qc/bat-job-card-qc.module').then(m => m.BatJobCardQcModule)
      },
      {
        path: 'bat_job_card_qc/:id',
        loadChildren: () => import('../../battery/batview/bat-job-card-qc-detail/bat-job-card-qc-detail.module').then(m => m.BatJobCardQCDetailModule)
      },
      {
        path: 'serviceAppointmentVendor',
        loadChildren: () => import('../vendor-service-appointment/vendor-service-appointment.module').then(m => m.VendorServiceAppointmentModule)
      },
      {
        path: 'serviceAppointmentVendor/:id',
        loadChildren: () => import('../vendor-service-appointment-detail/vendor-service-appointment-detail.module').then(m => m.VendorServiceAppointmentDetailModule)
      },
      {
        path: 'bat_serviceappointment/:id/sch',
        loadChildren: () => import('../../battery/batview/bat-view-all-sch-details/bat-view-all-sch-details.module').then(m => m.BatViewAllSchDetailsModule)
      },
      {
        path: 'batsoreturn',
        loadChildren: () => import('../../battery/batview/bat-service-order-return/bat-service-order-return.module').then(m => m.BatServiceOrderReturnModule)
      },
      {
        path: 'batsoreturn/:id',
        loadChildren: () => import('../../battery/batview/bat-service-order-return-details/bat-service-order-return-details.module').then(m => m.BatServiceOrderReturnDetailsModule)
      },
      {
        path: 'bat_wcc_customer',
        loadChildren: () => import('../../battery/batview/bat-wcc-customer/bat-wcc-customer.module').then(m => m.BatWccCustomerModule)
      },
      {
        path: 'bat_wcc_customer/:id',
        loadChildren: () => import('../../battery/batview/bat-wcc-customer-detail/bat-wcc-customer-detail.module').then(m => m.BatWccCustomerDetailModule)
      },
      {
        path: 'bat_zone',
        loadChildren: () => import('../zone/zone.module').then(m => m.ZoneModule)
      }

    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LayoutroutingModule { }
