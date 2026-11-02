import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { ExpenseManagementService } from '../../../services/expenses-management.service';
import { Loginservices } from '../../../services/login.services';
import { CaseResponse, Contacts, ExpenseManagementRequest, ExpenseManagementResponse, SiteDetailsResponse, SSAResponse, TableHeader } from '../../../services/model';
import { FieldValidation } from '../../../services/validation.services';
import { BatproducttransferService } from '../../bat_services/batproducttransfer.service';
import { BtCaseserviceService } from '../../bat_services/bt-caseservice.service';
import { BtPreRegistrationCaseService } from '../../bat_services/bt-pre-registration-case.service';
import { BtaccountService } from '../../bat_services/btaccount.service';
import { BTassetserviceService } from '../../bat_services/btassetservice.service';
import { BtcircleService } from '../../bat_services/btcircle.service';
import { BtcontactsService } from '../../bat_services/btcontacts.service';
import { BtexpensemanagementService } from '../../bat_services/btexpensemanagement.service';
import { BtproductrequestService } from '../../bat_services/btproductrequest.service';
import { BtsiteserviceService } from '../../bat_services/btsiteservice.service';
import { BatteryExpenseManagementRequest, BatteryExpenseManagementResponse, BrcListByAssetId, BTCaseResponse, BtItemListByPrId, BtPreRegCaseResponse, Page } from '../../bt_model';

@Component({
  selector: 'app-bat-all-view-page',
  templateUrl: './bat-all-view-page.component.html',
  styleUrls: ['./bat-all-view-page.component.scss']
})
export class BatAllViewPageComponent implements OnInit {
  objectId:number;
  objectName:string;
  compom:string;
  allTotalElement:number=0;
  colmnsLineItemPR:Array<any>=[];
  sitesList: Page<SiteDetailsResponse>;
  prcdata:Page<BtPreRegCaseResponse>=new Page<BtPreRegCaseResponse>();
  contactsList: Page<Contacts>;
  casesAccount: Page<CaseResponse>;
  productConsumedList: Page<any>;
  casesList: Page<CaseResponse>;
  SSAListByCircleName: Page<SSAResponse>;
  expenseListByContactId: Page<ExpenseManagementResponse>;
  newexpensesmanagement: BatteryExpenseManagementRequest = new BatteryExpenseManagementRequest();
  isEditModeExpenseManagement: boolean = false;
  headingExpenseManagement: string;
  EDIT_expensesmanagement_HEADING: string = 'Edit Expenses';
  isExpensesmanagementOpen: boolean;

  btProductTransferLineItems:Page<any>;

  contactPage: number = 0;
  contactFirst: boolean;
  contactLast: boolean;
  contactSize: number;
  totalContacts: number;
  contactSerial: number;
  totalPages:any;
  serialNumber:any;

  totalSites:number;
  siteFirst:boolean;
  siteLast:boolean;
  siteSize:number;
  siteSerial:number;

  totalCases:number;
  caseFirst:boolean; 
  caseLast:boolean;
  caseSize:number;
  caseSerial:number;

  first:any;
  last:any;
  size:number;
  message:string;
  pageName: any;

  column:any='created_date';
  forSorting:[{
    name:'',
    sortColumnName: '',
     icon: 'fa fa-angle-down pull-right'
  }]
    casesortcolumns =  [
     {
     name:"S.No",
    sortColumnName: 'id',
    icon: 'fa fa-angle-down pull-right'
   },
     {
       name:"Case ID",
      sortColumnName: 'id',
      icon: 'fa fa-angle-down pull-right'
     } 
     ,{
       name:'Case Type',
      sortColumnName: 'caseType.case_type_id',
      icon: 'fa fa-angle-down pull-right'
     },
     {
      name:"Case Reason",
      sortColumnName: 'reason.case_reason_details_name',
      icon: 'fa fa-angle-down pull-right',
     },{
       name:"Status",
       sortColumnName: 'st.case_status_name',
       icon: 'fa fa-angle-down pull-right'

     }
   ]
   PTItemAdv:boolean=false;
   PTItem:string;
   tbalehead:Array<TableHeader>=new Array<TableHeader>()
   colmnsCase:Array<BTCaseResponse>=[];
   sortArrSiteOfcase=[];
  page: any;
  private _cFlag: any;
  logedInUser:any;
  order: string='desc';
  casedetails: Page<BTCaseResponse>=new Page<BTCaseResponse>()
  contactDetails: Page<BTCaseResponse>=new Page<BTCaseResponse>()
  casesListByAsset: Page<BTCaseResponse> =new Page<BTCaseResponse>();
  totalCase:any;
  AllDataList:Page<any>=new Page<any>()
  
  brcCaseList: Page<BrcListByAssetId> =new Page<BrcListByAssetId>();
  totalBrcCases : number;
  @ViewChild('createexpensesmanagementModel') createexpensesmanagementModel: ModalDirective;
  constructor( private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute,private _user: Loginservices,private _assetsService: BTassetserviceService,private prcservice:BtPreRegistrationCaseService,
    private _global: GlobalserviceService,private btSiteservice:BtsiteserviceService,private _btAccountService :BtaccountService,private _contactService: BtcontactsService,
    private _btProductReqService: BtproductrequestService,
    private siteservice:BtsiteserviceService, private _btProductService: BatproducttransferService,private caseservice: BtCaseserviceService,  private loginService: Loginservices,private _circleService: BtcircleService,private _expenseManagementService: BtexpensemanagementService ) {
    this._activatedRoute.params.subscribe(pram => {
      this.objectId = pram['id'];
      console.log(this.objectId)
      this.objectName=pram['cmponent'];
      this.objectName=pram['cmponentsiteast'];
      this.pageName=pram['pagename'];
      
    this.compom=pram['pagename'];
    console.log(pram['pagename']) 
      if(pram['pagename']=='case'){
        this.pageName="Case";

        return true;
      }
       if(pram['pagename']=='soritems'){
        this.pageName="Service Order Return Items";
        return true;
      
      }
       if(pram['pagename']=='proTranferItem'){
        this. pageName="Product Transfer Item";
        this.PTItemAdv=true;
        return true;
       
      }
       if(pram['pagename']=='asset'){
        this.pageName="Asset of Site";
        this.PTItemAdv=false;
        return true;
      }
       if(pram['pagename']=='prc'){
        this.pageName="Pre Registration Case";
        this.PTItemAdv=false;
        return true;
      }
      if(pram['pagename']=='siteprc'){
        this.pageName="Site Pre Registration Case";
        this.PTItemAdv=false;
        return true;
      }
       if(pram['pagename']=='proLi'){
        this.PTItemAdv=false;
        this.pageName="Product Request Line Items";
        return true;
      }
       if(pram['pagename']=='proTransfer'){
        this.pageName="Product Transfer"
        this.PTItemAdv=false;
        return true;
      }
       if(pram['pagename']=='proTranferLI'){
        this.pageName="Product Tranfer Line Items";
        this.PTItemAdv=false;
        return true;
      }
       if(pram['pagename']=='proRO'){
        this.pageName="Return Order";
        this.PTItemAdv=false;
        return true;
      }
       if(pram['pagename']=='serviceOR'){
        this.pageName="Service Order Items";
        return true;
        
      }
       if(pram['pagename']=='pricebook'){
        this.pageName="Price Book Entries";
        return true;
      }
       if(pram['pagename']=='assetprc'){
        this.pageName="Asset Pre Registration Case";
        return true;
       

      }
      if(pram['pagename']=='assetbrc'){
        this.pageName="BRC Case";
        return true;
       

      }
      if(pram['pagename']=='caseAcc'){
        this.pageName="Case By Account";
        return true;
       

      }
      if(pram['pagename']=='assetcase'){
        this.pageName="Asset Case";
      }
      if(pram['pagename']=='contact'){
        this.pageName="Contacts";
        return true;
      }
      if(pram['pagename']=='siteacc'){
        this.pageName="Sites";
        return true;
      }
      if(pram['pagename']=='assetCase'){
        this.pageName="Case By Asset";
        return true;
      }
      if(pram['pagename']=='productconsume'){
        this.pageName="Product Consumed";
        return true;
      }
      if(pram['pagename']=='ssaByCircle'){
        this.pageName="SSA By Circle";
        return true;
      }
      if(pram['pagename']=='expanseByContact'){
        this.pageName="Expanse By Contact";
        return true;
      }
      if(pram['pagename']=='productTransfer'){
        this.pageName="Transfer Item By Product Transfer";
        return true;
      }
      // if(pram['pagename']=='siteprc'){
      //   console.log('here is my code')
      //   this.pageName='Site Pre Registration Case'
      // }
      
      
      else{
        this.pageName=''
      }
     
     
    });
    this._activatedRoute.queryParams.subscribe(pram => {
    this.colmnsCase=pram['case'];
     console.log(pram['cmponentsiteprc']);
    if(pram['cmponent']==null){
      this.colmnsLineItemPR=pram['cmponentprc'];
    }
    else if(pram['pagename']=='asset'){
      
    this.colmnsLineItemPR=pram['cmponentsiteast']
    }
    if(pram['cmponentsiteprc']!==null){
      console.log("no ")

      this.colmnsLineItemPR=pram['cmponentsiteprc'];
    }
    else{
      this.colmnsLineItemPR=pram['cmponent'];


    }
    if(this.colmnsLineItemPR!=null){
      if(pram['sortArr']==undefined){
      const TemHead=[];
      for(let i=0;i<this.colmnsLineItemPR.length;i++){
        let tblhd =new TableHeader();
        tblhd.name=this.colmnsLineItemPR[i];
        tblhd.sortColumnName=this.colmnsLineItemPR[i];
        tblhd.sortOrder='desc' ? 'asc' : 'desc';
        // this.casesortcolumns.forEach(e=>{
        //   tblhd.sortColumnName=e.sortColumnName
        //   tblhd.sortOrder=this.order;
        // })
        tblhd.id=i;
        tblhd.icon='fa fa-angle-down pull-right'
        TemHead.push(tblhd);
      
      }
      this.tbalehead=TemHead;
      console.log(this.tbalehead);
    }
    else{
  const TemHead=[];
  for(let i=0;i<this.colmnsLineItemPR.length;i++){
    let tblhd =new TableHeader();
    tblhd.name=this.colmnsLineItemPR[i];
    tblhd.sortColumnName=this.sortArrSiteOfcase[i];
    tblhd.sortOrder=this.order;
    tblhd.id=i;
    tblhd.icon='fa fa-angle-down pull-right'
    TemHead.push(tblhd);
  
  }
  //  this.tbalehead=TemHead;
   console.log(this.tbalehead);
  
  }
 }
  this.page =pram['page'];
   this.allTotalElement=pram['totalElement'];

        this._cFlag = pram['_c'];
        this.getDetails();
    });
    this.logedInUser = _user.getLoggedInUser();
   }

  ngOnInit() {
    this.AllDataList.content = [];
    
  }
  // For Check Rights
  rights(object, right: string): boolean {
    return this.loginService.isUserValidFor(object, right);
  }

  // For case
  getDetails(){
    console.log("hey")
    if(this.pageName=='Case')
   { 
     this.getCase('created_date','desc',this.page);
    }else if(this.pageName=='Contacts')
    {
      
      this.getContact('created_date','desc');
    }
    else if(this.pageName=='Sites')
    {
      
      this.getSiteByAccount('created_date','desc');
    }
    else if(this.pageName=='Case By Account')
    {
      
      this.getCaseByAccount('created_date','desc');
      console.log(this.AllDataList);
    
    }
    else if(this.pageName=='Asset Pre Registration Case')
    {
      
      this.getPrcByAsset('created_date','desc');
      console.log(this.AllDataList);
    
    }
    else if(this.pageName=='Case By Asset')
    {
      
      this.getCaseByAsset('created_date','desc');
      console.log(this.AllDataList);
    
    }
    else if(this.pageName=='Product Consumed')
    {
      this.getProductConsumeByCase('created_date','desc');
      console.log(this.AllDataList);
    }
    else if(this.pageName=='SSA By Circle')
    {
      this.getSsaByCircle('created_date','desc');
      console.log(this.AllDataList);
    }
    else if(this.pageName=='Expanse By Contact')
    {
      this.getExpanseByContact('created_date','desc');
      console.log(this.AllDataList);
    }
    else if(this.pageName=='Transfer Item By Product Transfer')
    {
      this.getItemsByProduct('created_date','desc');
      console.log(this.AllDataList);
    }
    else if(this.pageName=='BRC Case')
    {
       this.getBrcCaseList(this.objectId,0);
      console.log(this.AllDataList);
    }
    else if(this.pageName=="Site Pre Registration Case"){
      this.getPrcBysitess();
    }
   

  }
    // brc case (add by pawan)
    getBrcCaseList(assetId,page){
      this.spinner.show();
      this.brcCaseList.content = [];
      this._assetsService.btBrcList(assetId,page).subscribe(data=>{
      this.spinner.hide();
      this.brcCaseList = data;
      this.AllDataList = data;
      this.totalBrcCases = data.totalElements;
      },err=>{
      this.spinner.hide();
      })
    }
  // Get Transfer Item By Product Transfer
  getItemsByProduct(column: string,order:string,page?:number){
    this.spinner.show();
    this._btProductService.getAllProuctTransferItems(this.objectId).subscribe(data => {
      this.btProductTransferLineItems = data;
      this.AllDataList = data;
      this.spinner.hide();
    }, error => {
      this.spinner.hide();
    })
  }
// Get Expanse By Contact
  getExpanseByContact(column: string,order:string,page?:number){
    this.spinner.show();
    this._contactService.getAllExpenseManagementListByExpenseId(this.objectId).subscribe(
      data => {
        this.expenseListByContactId = data;
        this.AllDataList = data;
        this.first = data.first;
        this.last = data.last;
        this.size = data.size;
        this.allTotalElement = data.totalElements;
        this.serialNumber = ((this.page) * this.size);
        if (data !== null) {
          this.spinner.hide();
        }
      }, err => {
        this.spinner.hide();
        this.message = err.error.message;
        this._global.alert({ type: 'alert', message: this.message });
      });


  }
  // Action Function For Expanse
  onClickEditExpenses(id) {
    this.spinner.show();
    console.log(id);
    
    this._expenseManagementService.getexpensesmanagementById(id).subscribe(data => {
      this.newexpensesmanagement = data;
      console.log(this.newexpensesmanagement);
      
      // this.newexpensesmanagement.contactId = this.contactid;
      this.spinner.hide();
    },error=>{
      this.spinner.hide();
      this.message=error.error.message;
      this._global.alert({ type: 'alert', message: this.message });
    });
    this.isEditModeExpenseManagement = true;
    this.headingExpenseManagement = this.EDIT_expensesmanagement_HEADING;
    this.createexpensesmanagementModel.show();
  }

  onexpensesmanagementCreated($event) {
    let column;
    let order;
   let page;

    this.getExpanseByContact(column,order,page) 
       if (!this.isEditModeExpenseManagement) {
      this.expenseListByContactId.content.unshift($event);
      this.allTotalElement = this.allTotalElement + 1;
    } else {
      let index = 0;
      this.expenseListByContactId.content.forEach(element => {
        if (element.expenseManagementId === $event.expenseManagementId) {
          this.expenseListByContactId[index] = $event;
        } else {
          index++;
        }
      });
    }
  }
  cancelDataExpense() {
    this.createexpensesmanagementModel.hide();
    this.isEditModeExpenseManagement = false;
    // this.heading = this.CREATE_expensesmanagement_HEADING;
    this.newexpensesmanagement = new BatteryExpenseManagementResponse();
    // this.newexpensesmanagement.next = false;
  }


  // End Expanse Methods


  
  // Get SSA By Circle
  getSsaByCircle(column: string,order:string,page?:number)
  {
    this.spinner.show();
    this._circleService.getAllSSAListByCircleName(this.objectId.toString()).subscribe(
      data => {
        this.AllDataList = data;
        this.SSAListByCircleName = data;
        this.first = data.first;
        this.last = data.last;
        this.size = data.size;
        this.allTotalElement = data.totalElements;
        this.serialNumber = ((this.page) * this.size);
        this.spinner.hide();
      }, err => {
        this.spinner.hide();
        this.message = err.error.message;
        this._global.alert({ type: 'alert', message: this.message });
      });
  }
  // Get Product By Case
  getProductConsumeByCase(column: string,order:string,page?:number){

    this.spinner.show();
    this.caseservice.getAllProductConsuedBycaseId(this.objectId).subscribe(data => {
      this.spinner.hide()
      this.AllDataList = data;
      this.productConsumedList = data;
      this.allTotalElement = data.totalElements;
      this.first = data.first;
      this.last = data.last;
      this.size = data.size;
      this.serialNumber = (((this.page) * this.size));

    }, error => {
      this.spinner.hide()
      this.message = error.error.message;
      this._global.alert({type:'alert',message:this.message});

    })
  }
  // Get Case By Asset
  getCaseByAsset(column: string,order:string,page?:number){
    this.spinner.show();
    page=0;
    this.casesListByAsset.content=[];
    this._assetsService.getAllCaseList(this.objectId.toString()).subscribe(data => {
      this.casesListByAsset = data;
      this.AllDataList = data;
      this.totalCases = data.totalElements;
      this.caseFirst = data.first;
      this.caseLast = data.last;
      this.caseSize = data.size;
      this.caseSerial = (((page) * this.caseSize));
      if (data != null) {
        this.spinner.hide();
      }
    }, err => {
      this.spinner.hide();
      this._global.alert({type:'alert',message:err.error.message});
    });
  }

  // Get PRC By Asset
  getPrcByAsset(column: string,order:string,page?:number){
    this.spinner.show();
    const size=5;
    this.prcdata.content=[];
    this._assetsService.getAllPrcListbyAsset(this.objectId.toString()).subscribe(data=>{
    this.spinner.hide();
    this.AllDataList = data;  
    this.prcdata=data;
    console.log(this.prcdata);
    
    })
  }

  // For Case By Account
  getCaseByAccount(column: string,order:string,page?:number){
    this.spinner.show();
    page =0;
    this._btAccountService.getAllBtCaseList(this.objectId.toString()).subscribe(data => {
      this.casesList = data;
      this.AllDataList = data;
      console.log(this.casesList);
      
      this.totalCases = data.totalElements;
      this.caseFirst = data.first;
      this.caseLast = data.last;
      this.caseSize = data.size;
      this.caseSerial = (((page) * this.caseSize));
      if (data != null) {
        this.spinner.hide();
      }
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;    
      this._global.alert({ type: 'alert', message: this.message });
    });
    
  }

  // For Site By Account 
  getSiteByAccount(column: string,order:string,page?:number){
      this.spinner.show();
      this._btAccountService.getAllBtSitesByAccount(this.objectId.toString()).subscribe(data => {
        this.sitesList = data;
        this.AllDataList =  data;
        this.totalSites = data.totalElements;
        this.siteFirst = data.first;
        this.siteLast = data.last;
        this.siteSize = data.size;
        this.siteSerial = (((this.page) * this.siteSize));
        if (data != null) {
          this.spinner.hide();
        }
      }, err => {
        this.spinner.hide();
        this.message = err.error.message;     
        this._global.alert({ type: 'alert', message: this.message });
      });
      


  }
  // For Contact
  getContact(column: string,order:string,page?:number){
    this.spinner.show();
    this.AllDataList.content=[];
    console.log(this.objectId.toString());
    console.log(this.page);
    console.log(this.tbalehead);
    
    this._btAccountService.getBtContactsByAccountId(this.objectId.toString(), this.page).subscribe(data => {
      this.spinner.hide()
      this.contactsList = data;
      this.AllDataList =  data;
      this.totalContacts = data.totalElements;
      this.contactFirst = data.first;
      this.contactLast = data.last;
      this.contactSize = data.size;
      this.serialNumber = (((this.page) * data.size));
      console.log(this.AllDataList);
      
      // this.contactSerial = (((contactPage) * this.contactSize));
      if (data != null) {
        this.spinner.hide();
      }
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;     
      this._global.alert({ type: 'alert', message: this.message });
    });
  }
    
  // For Case

  getCase(column: string, order: string,page?:number) {
    
    this.spinner.show();
    const size=20;
    this.page=page;
    this.spinner.show();
    this.AllDataList.content=[];
    this.casedetails.content=[];
    console.log(column,order,this.page);
    console.log(this.tbalehead);
    
    this.btSiteservice.getCaseBySiteId(this.objectId, this.page, column, order).subscribe(data => {
      this.spinner.hide();
      this.casedetails = data;
      this.AllDataList=this.casedetails;
      this.serialNumber = (((this.page) * data.size));
      this.totalCase = data.totalElements;
      this.totalPages = data.totalPages;
      this.first = data.first;
      this.last = data.last;
      this.size = data.size;
      this.serialNumber = (((this.page) * this.size));
    },error=>{
      this.spinner.hide()
      this.message=error.error.message;
      this._global.alert({type: 'alert',message: this.message})

    })
  }

  ///// for bt product request items
  
  btProductReqItemResponse: Page<BtItemListByPrId> = new Page<BtItemListByPrId>();
  getItemByPrId(PrId: number,) {
    this.spinner.show();
    this._btProductReqService.itemByPrId(PrId).subscribe(data => {
      this.btProductReqItemResponse.content = data.content;
      this.btProductReqItemResponse.totalElements = data.totalElements;
      this.spinner.hide();
    }, error => {
      this.spinner.hide();
      this._global.alert({ type: 'alert', message: error.error.message });
    })

  }
// site prc

// prcdata:Page<BtPreRegCaseResponse>=new Page<BtPreRegCaseResponse>()
prcserialNumber:any;
getPrcBysitess(){
  console.log("heyyyyyyy")
  this.spinner.show();
  this.prcdata.content=[];
  const size=20;
  this.prcservice.getPRCBySite(this.objectId,`page=`+this.page+`&size=`+size).subscribe(data=>{
  this.spinner.hide();
  this.prcdata=data;
  this.AllDataList=this.prcdata;
  this.serialNumber = (((this.page) * data.size));
  this.totalCase = data.totalElements;
  this.totalPages = data.totalPages;
  this.first = data.first;
  this.last = data.last;
  this.size = data.size;
  this.serialNumber = (((this.page) * this.size));
  console.log(this.prcdata)
  this.prcserialNumber=(((this.page)*data.size));
  },error=>{
    this.spinner.hide();
    this._global.alert({ type: 'alert', message: error.error.message });

  })
}
  // sort table

  getSortOrder(tblId) {
    this.tbalehead.forEach((tbl) => {
      console.log(tbl.id, tbl.sortColumnName);
      if (tbl.id == tblId) {
        this.column = tbl.sortColumnName;

        console.log(this.tbalehead);
      if(this.order=='desc'){
        this.order='asc'
      }else{
        this.order='desc'
      }
    //  this.order=tbl.sortOrder;
    if(this.pageName=="Product Request Line Items"){
      this.getItemByPrId(this.objectId);
    }
    else if(this.pageName=="Pre Registration Case"){
      // this.getPrcBysite(this.column,this.order);
     }
     else if(this.pageName=="Return Order"){
      // this.ROLineItemsAdv=true;
      //  this.getProductReturnItems(this.column,this.order);
       
    }
    else if( this.pageName=="Service Order Return Items"){
      // this.getSORItems(this.objectId,'item.service_order_id',this.order,this.page);
        }
        else if(this.pageName=="Product Transfer Item"){
          // this.getPTItemDetails(this.objectId,this.page)
        }

       else if(this.pageName=="Case")
          {
            this.getCase(this.column,this.order,this.page);
          }
          else if(this.pageName=="Contacts")
          {
            console.log(this.tbalehead);
            
            this.getContact(this.column,this.order,this.page);
          }
          else if(this.pageName=="siteacc")
          {
            console.log(this.tbalehead);
            
            this.getSiteByAccount(this.column,this.order,this.page);
          }
          else if(this.pageName=="Case By Asset")
          {
            console.log(this.tbalehead);
            
            this.getCaseByAsset(this.column,this.order,this.page);
          }
          else if(this.pageName=="Asset Pre Registration Case")
          {
            console.log(this.tbalehead);
            
            this.getCaseByAsset(this.column,this.order,this.page);
          }
          else if(this.pageName=="Site Pre Registration Case"){
            this.getPrcBysitess();
          }
          // else if(this.pageName=="Site Pre Registration Case"){
          //   this.getPrcBysite();
          // }
         

      
      
      }
     
    });
  }
  pagenumber:any=null;
  jumpPage(pagenumber){
    if(pagenumber!=null && pagenumber!==''){
      if(pagenumber>this.AllDataList.totalPages || pagenumber==0){
        this._global.alert({type:'alert',message:'Page Count Should Be Less Then Total Page'});
      }
else{
  this.AllDataList.number=this.pagenumber-1;
    this.page.page=pagenumber-1;
    if(this.pageName=='Case')
    { this.getCase('created_date','desc',this.page);}
}

}
else if(FieldValidation.isLetter(pagenumber))
  {
    this._global.alert({ type: 'alert', message: "Please Enter A Valid Input" });
    pagenumber=null;
  }
else{
  this._global.alert({type:'alert',message:'Please Input A Valid Number'});
 }
}

}
