import { Component, OnInit, Testability, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../globalservice.service';
import { LocationProductRequestService } from '../../services/locationproductrequest.service';
import { Loginservices } from '../../services/login.services';
import { AssetsResponse, CaseResponse, LocationProductRequestItemResponse, LocationProductRequestResponse, LocationProductTransferItemResponse, LocationProductTransferLineItemResponse, LpReturnOrderItemResponse, Page, PreRegCaseResponse, PriceBookEntriesResponse, ProductsForList, ROlineItems, ServiceOrderReturnItemResponse, TableHeader } from '../../services/model';
import { PreRegCaseService } from '../../services/preregcaseservice.services';
import { PriceBookService } from '../../services/pricebook.service';
import { ProductReturnService } from '../../services/productreturnservice';
import { ProductsService } from '../../services/products.services';
import { ProductTransferServices } from '../../services/producttransfer.service';
import { ServiceOrderService } from '../../services/serviceorder.servvices';
import { ServiceorderreturnService } from '../../services/serviceorderreturn.service';
import { siteService } from '../../services/siteService.services';
import { ProductreturnorderDetailsDataTransfer } from '../productreturnorderdetails/productreturnorderdetails.datatransfer';

@Component({
  selector: 'app-detailsofall',
  templateUrl: './detailsofall.component.html',
  styleUrls: ['./detailsofall.component.css']
})
export class DetailsofallComponent implements OnInit {
  objectId:any;
  objectName:any;
  _cFlag:any;
  page:any=0;
  dReceived: boolean = false;
  casedetails: Page<CaseResponse> = new Page<CaseResponse>();
  pageProductTranferDetails: Page<LocationProductTransferItemResponse> = new Page<LocationProductTransferItemResponse>();
  AllDataList:Page<any>=new Page<any>();
  serialNumber:any;
  message:string;
  column:string='createDate';
  order:string='desc';
  pageName:string='';
  locationProductTransferLineItems:Array<LocationProductTransferLineItemResponse>=[];
  products: Array<ProductsForList> = [];
  popupSuccessModel
  colmnsCase:Array<CaseResponse>=[];
  logedInUser:any;
  data: ProductreturnorderDetailsDataTransfer=new ProductreturnorderDetailsDataTransfer();
  colmnsLineItemPR:Array<any>=[];
  colmnsPRC:Array<any>=[]
  locationProductRequestResponse: LocationProductRequestResponse = new LocationProductRequestResponse();
  compom:string='';
  isCaseModelOpen:boolean;
  pageItemResponse: Page<LpReturnOrderItemResponse> = new Page<LpReturnOrderItemResponse>();
  d:Page<ROlineItems>=new Page<ROlineItems>();
  heading:string
  productPrices:number;
  allTotalElement:number=0;
  isassetPRC:boolean=false;
  prcdata:Page<PreRegCaseResponse>=new Page<PreRegCaseResponse>();
  astprcdata:Page<PreRegCaseResponse>=new Page<PreRegCaseResponse>();
  priceEntrieResponse: PriceBookEntriesResponse=new PriceBookEntriesResponse();
  prliAdvance:boolean=false;
  PTofPRAdvance:boolean=false;
  PTLineItmsAdvanc:boolean=false;
  SOItmsAdv:boolean=false;
  ROLineItemsAdv:boolean=false;
  SORItemAdv:boolean=false;
  soritems:Page<ServiceOrderReturnItemResponse>=new Page<ServiceOrderReturnItemResponse>()
  caseId:string='CaseId';
  sortArrSiteOfcase=[];
  rolGroup:string;

  advancesearchPT = {
    name: '',
    product: '',
    productDescription: ''
    
  };
  advancesearchPRitems = {
    name: '',
    product: '',
    productDescription: ''
    
  };
  forSorting:[{
    name:'',
    sortColumnName: '',
     icon: 'fa fa-angle-down pull-right'
  }]
    casesortcolumns =  [
     {
     name:"S.No",
    sortColumnName: 'caseId',
    icon: 'fa fa-angle-down pull-right'
   },
     {
       name:"Case ID",
      sortColumnName: 'caseId',
      icon: 'fa fa-angle-down pull-right'
     } ,{
       name:'Case Type',
      sortColumnName: 'caseId',
      icon: 'fa fa-angle-down pull-right'
     },{
      name:"Case Reason",
      sortColumnName: 'caseId',
      icon: 'fa fa-angle-down pull-right',
     },{
       name:"Status",
       sortColumnName: 'caseId',
       icon: 'fa fa-angle-down pull-right'

     }
   ]
   PTItemAdv:boolean=false;
   PTItem:string;
   tbalehead:Array<TableHeader>=new Array<TableHeader>()
  @ViewChild('selectedItemModel') selectedItemModel:ModalDirective;
  constructor( private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute,private siteservice:siteService,
    private _productRequestService: LocationProductRequestService,private _user: Loginservices,
    private _productService: ProductsService,private _serviceOrderService: ServiceOrderService,private pricebooservice:PriceBookService,
    private _productreturnservice: ProductReturnService,private _global: GlobalserviceService,private _producttransferService:ProductTransferServices,
    private prcservice:PreRegCaseService,
    private sorservice:ServiceorderreturnService ) {
    this._activatedRoute.params.subscribe(pram => {
      this.objectId = pram['id'];
      this.objectName=pram['cmponent'];
      this.objectName=pram['cmponentsiteast'];
      this.pageName=pram['pagename']
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
      if(pram['pagename']=='assetcase'){
        this.pageName="Asset Case";
      }
      else{
        this.pageName=''
      }
     
     
    });
    this._activatedRoute.queryParams.subscribe(pram => {
    this.colmnsCase=pram['case']
    
   
   
    if(pram['cmponent']==null){
      this.colmnsLineItemPR=pram['cmponentprc'];
    }
    else if(pram['pagename']=='asset'){
      
    this.colmnsLineItemPR=pram['cmponentsiteast']
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
        tblhd.sortColumnName='';
        tblhd.sortOrder='';
        tblhd.id=i;
        tblhd.icon='fa fa-angle-down pull-right'
        TemHead.push(tblhd);
      
      }
      this.tbalehead=TemHead;
    }
    else{
  this.sortArrSiteOfcase=pram['sortArr'];
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
   this.tbalehead=TemHead;
  
  }
 }
  this.page =pram['page'];
   this.allTotalElement=pram['totalElement'];

        this._cFlag = pram['_c'];
        this.getDetails();
    });
    this.logedInUser = _user.getLoggedInUser();
    this.rolGroup = this.logedInUser.roleGroupAndAccount['roleGroup']
   }

  ngOnInit(): void {
    this.locationProductRequestResponse.status=" ";
    // this.getDetails();
    
  }
  pageSize:number=1;
  getDetails(){
    if(this.pageName=="Case"){
     this.getCase(this.column,this.order,this.page);
    }
    else if(this.pageName=="Product Request Line Items"){
      this.getOwners(this.objectId)
      this.getProductRequestDetails(this.objectId);
      this.getProducts();
      this.getItems(this.objectId,'createdDate',this.order);
      
    }
    else if( this.pageName=="Product Transfer"){
      this.PTofPRAdvance=true;
      this.getProductTransferResponse(this.objectId);
    }
    else if(this.pageName=="Product Transfer Item"){
      this.getPTItemDetails(this.objectId,this.page)
    }
  
    else if(this.pageName=="Return Order"){
      this.ROLineItemsAdv=true;
       this.getProductReturnItems('prd.product_part_code',this.order)
       
    }
    else if( this.pageName=="Product Tranfer Line Items"){
      this.PTLineItmsAdvanc=true;
      this.getLineItemDetails(this.objectId);
    }
    else if(this.pageName=="Service Order Items"){
     this.SOItmsAdv=true
     this.getItemsSO();
    }
    else if(this.pageName=="Price Book Entries"){
      this.getEntries();
    }
    else if(this.pageName=="Pre Registration Case"){
     this.getPrcBysite('createDate',this.order);
    }
    else if(this.pageName=='Asset of Site'){
      this.getAssetBySiteid('createdDate',this.order);
    }
    else if( this.pageName=="Service Order Return Items"){
      this.SORItemAdv=true;
      this.getSORItems(this.objectId,'item.service_order_id',this.order,this.page);
         
    }
    
    else if(this.pageName="Asset Pre Registration Case"){
      this.isassetPRC=true;
     this.getPrcByAsset();

    }
  
    else
    {
      this.pageProductRequestItems.content=[];
      this.AllDataList.content=[];
      this.casedetails.content=[];
      this.products=[];
    }
  }
  getCase(column: string, order: string,page?:any) {
    const size=20;
    this.page=page;
    this.spinner.show();
    this.AllDataList.content=[];
    this.casedetails.content=[];
    this.siteservice.getCaseBySiteId(this.objectId, this.page, column,size, order).subscribe(data => {
      this.spinner.hide();
      this.casedetails = data;
      this.AllDataList=this.casedetails;
      this.serialNumber = (((this.page) * data.size));
    },error=>{
      this.spinner.hide()
      this.spinner.hide();
      this._global.alert({ type: 'alert', message:error.error.message });
    
    })
  }
  pageProductRequestItems: Page<LocationProductRequestItemResponse> = new Page<LocationProductRequestItemResponse>();
 
  getItems(requestid,colmn?,order?) {
    this.spinner.show();
    this.pageSize=20;
   this.pagedisp=false;
    //  this.order=order;
     this.column=colmn
    this.AllDataList.content=[];
    this.pageProductRequestItems.content=[];
    this._productRequestService.getItems(requestid,`sort=${this.column},${order}`,`page=${this.page}&size=${this.pageSize}`).subscribe(data => {
      this.pageProductRequestItems = data;
      this.AllDataList=data;
      this.pageProductRequestItems.content.forEach(d=>d.edit=false);
      this.serialNumber = (((this.page) * data.size));
      this.AllDataList=this.pageProductRequestItems;
      data.content.forEach(e=>{
      
      })
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
               this._global.alert({ type: 'alert', message:err.error.message });
    });
  }
  getProducts() {
    this._productService.getProductForList().subscribe(data=>{
      this.products = data
      data.forEach(e=>{
        let pro=new ProductsForList();
        pro.productPartCode=e.productPartCode;
        pro.productDescription=e.productDescription;
        pro.bothvalues=e.productPartCode+' '+e.productDescription;
        this.products.push(pro);
      })
    },error=>{
      this.spinner.hide();
      this._global.alert({ type: 'alert', message: error.error.message});
    })
  }
  changePartCode(prdouctRequestItemId,partCode){
    if(prdouctRequestItemId!==null && partCode!==null){
     this.spinner.show();
     this._productRequestService.updatePartcode(prdouctRequestItemId,partCode).subscribe(data=>{
       this.spinner.hide();
       this.getItems(this.objectId);
     },error=>{
       this.spinner.hide();
       this._global.alert({type:'alert',message:error.error.message});
     });
    }else{
     this._global.alert({type:'alert',message:'Please Pick one PartCode'});
      
    }
   }
   deleteItem(id) {
    this.spinner.show();
    this._productRequestService.deleteProductLineItemsById(id).subscribe(data => {
      this.spinner.hide();
      this._global.alert({ type: '', message: 'Item deleted' });
      this.getItems(this.objectId);
    }, error => {
      this.spinner.hide();
      this._global.alert({ type: 'alert', message: error.error.message });
    });
  }
  issSortclose 
  getProductRequestDetails(requestId: number) {
    this.getOwners(requestId);
    this.spinner.show();
    this.prliAdvance=true;
    this._productRequestService.getLocationProductRequest(requestId).subscribe(data => {
      this.locationProductRequestResponse = data;
      this.spinner.hide();
      if(this.locationProductRequestResponse.status=='Short Closed'){
        this.issSortclose=false;
      }
      else if(this.PROwnerRole=='DCT_MAIN_STORE' && this.rolGroup=='DCT_MAIN_STORE'){
     
          this.issSortclose=true
      
      }
     else if(this.PROwnerRole=='SCH' && this.rolGroup=='DCT_MAIN_STORE'){
   
        this.issSortclose=true
    
    }
      else if(this.PROwnerRole=='FIELD_SERVICE_ENGINEER' && this.rolGroup=='SCH'){
        this.issSortclose=true;

      }


      else{
        this.issSortclose=false
      }
    }, error => {
      this.spinner.hide();
      this._global.alert({ type: 'alert', message: error.error.message});
    });
  }
 
  PROwnerRole:string='';
  getOwners(productRequestId:number){
    this.spinner.show();
    this._productRequestService.getOwnerdetails(productRequestId).subscribe(data=>{
    if(data!=null)
      {this.PROwnerRole=data.role;}
      else{
        this._global.alert({ type: 'alert', message:'This owner role group is missing'});
      }
    },error=>{
      this.spinner.hide();
      this._global.alert({ type: 'alert', message:error.error.message });
  
    })
  }
  cancel(){
    this.spinner.show();
    this._productRequestService.getItems(this.objectId,'page='+this.page+'&size='+this.pageSize).subscribe(data => {
      this.pageProductRequestItems = data;
      this.pageProductRequestItems.content.forEach(d=>d.edit=false);
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this._global.alert({ type: 'alert', message: err.error.message});
    });
      }
   
// product tranfer of PR related
getProductTransferResponse(productRequestid) {
  this.spinner.show();
  this.AllDataList.content=[];
  this.pageProductTranferDetails.content=[];
  // this.pageSize=20;
  this._productRequestService.getTransferItems(productRequestid, '?page='+this.page+'&size=20').subscribe(data => {
    this.spinner.hide();
    this.pageProductTranferDetails = data;
    this.serialNumber = (((this.page) * data.size));
    this.AllDataList=this.pageProductTranferDetails;
  }, error => {
    this.spinner.hide();
        this._global.alert({ type: 'alert', message: error.error.message});
  });
}
lenthofLineitm:any;
pagedisp:boolean=true;
getProductReturnItems(column?,order?) {
  this.pagedisp=false;
    this.pageItemResponse.content=[];
    this.d.content=[];
    this.AllDataList.content=[];
    this._productreturnservice.getROItems(this.objectId,`?sort=${column},${order}`,`&page=${this.page}&size=${this.allTotalElement}`).subscribe(data => {
      this.d = data;
      // console.log(data);
      this.serialNumber=(this.page*data.size);
      this.AllDataList=this.d;

      
    },error=>{
      this.spinner.hide();
      this._global.alert({ type: 'alert', message: error.error.message});
    });
  
   }
   getLineItemDetails(transferId){
     this.locationProductTransferLineItems=[];
     this.AllDataList.content=[];
    this._producttransferService.getLineItems(transferId).subscribe(data=>{
      this.locationProductTransferLineItems=data;
      this.AllDataList.content=this.locationProductTransferLineItems;
    },error=>{
      this.spinner.hide();
      this._global.alert({ type: 'alert', message: error.error.message});
    });
  }
  items:Array<any>=[];
  getItemsSO() {
    this.spinner.show();
    this.AllDataList.content=[];
    this.items=[];
    this._serviceOrderService.getServiceOrderItems(this.objectId).subscribe(data => {
      this.spinner.hide();
      this.items = data;
      this.AllDataList.content=this.items;
    },error=>{
      this.spinner.hide();
      this._global.alert({ type: 'alert', message: error.error.message});
    });
  }
  // totalEntries:any
  entrieTable:Page<PriceBookEntriesResponse>=new Page<PriceBookEntriesResponse>()
  getEntries(){
    this.spinner.show();
    this.entrieTable.content=[];
    this.AllDataList.content=[];
    this.pageSize=20;
    this.pricebooservice.getAllEntries(this.page,this.objectId,this.pageSize).subscribe(data=>{
    this.spinner.hide();
    this.entrieTable=data;
    this.AllDataList=this.entrieTable;
    this.serialNumber=((this.page) * data.size);
  
    },error=>{
      this.spinner.hide();
      this.spinner.hide();
        this._global.alert({ type: 'alert', message: error.error.message});

    })
  }
  editpriceEntriesModelshow(partCode:PriceBookEntriesResponse){
   this.spinner.show()
   this.heading="Edit Products";
   this.selectedItemModel.show();
    this.pricebooservice.getDetailByentriesId(partCode.id).subscribe(data=>{
    this.spinner.hide();
    this.priceEntrieResponse=data;
    this.productPrices= data.partCodePrice;
     
    },error=>{
      this.spinner.hide();
      this._global.alert({ type: 'alert', message: error.error.message});
    })
   

  }
  onProductUpdated($event:PriceBookEntriesResponse){
    this.entrieTable.content.unshift($event)
    this.getEntries();
  }
  cancelselectedData(){
    this.selectedItemModel.hide();
  }
  removeItem(id) {
  this.spinner.show();
    this._productreturnservice.deleteReturnOrderLineItem(this.objectId, id).subscribe(data => {
      this.spinner.hide();
      this.getProductReturnItems('prd.product_part_code',this.order);
      this._global.alert({ alert: '', message: 'Item deleted' });
      // this._activatedRoute.navigate(['/productreturnorder', this.data.productReturnOrderResponse.returnOrderId]);
    }, error => 
   {
    this.spinner.hide();
    this._global.alert({ type: 'alert', message: error.error.message});
   }
   
    );
    this.spinner.hide();
  }

  disableLineItem(lineItemId){
    this.spinner.show();
    this._productRequestService.disableProductRequestLineItem(lineItemId).subscribe(data=>{
      this.spinner.hide();
      this.getProductRequestDetails(this.objectId);
      this.getItems(this.objectId,'createdDate',this.order);
      this._global.alert({type:'',message:'Product request line item has been disabled'});
    },error=>{
      this.spinner.hide();
      this._global.alert({type:'alert',message:error.error.message});
      
    });
  }

  getPrcBysite(colmn?,order?){
    this.pagedisp=false;
    this.spinner.show();
    this.prcdata.content=[];
    this.AllDataList.content=[];
    const size=20;
    this.prcservice.getPRCBySite(this.objectId,`sort=${colmn},${order}`,`page=${this.page}&size=${size}`).subscribe(data=>{
    this.spinner.hide();
    this.prcdata=data;
    this.AllDataList=data;
    })
  }
 
    getPrcByAsset(){
      this.spinner.show();
      this.prcdata.content=[];
      this.AllDataList.content=[];
      const size=20
      this.prcservice.getPRCByAsset(this.objectId,this.page,size).subscribe(data=>{
      this.spinner.hide();
      this.astprcdata=data;
      this.AllDataList=data;
      },error=>{
        this.spinner.hide();
        this._global.alert({ type: 'alert', message: error.error.message});
      })
    
  }
  resetpreqitem(){
    this.advancesearchPRitems.name=''
    this.advancesearchPRitems.product=''
    this.advancesearchPRitems.productDescription=''
      }
      searchPReqitems(){
        if(this.advancesearchPRitems.name=='' &&
        this.advancesearchPRitems.product=='' &&
        this.advancesearchPRitems.productDescription==''){
          this._global.alert({ type: 'alert', message:"Please Enter valid Key for Search" });
        }
        else{
          this.spinner.show();
          this._productRequestService.getSearchResultPRLineitems(this.advancesearchPRitems,this.objectId).subscribe(data=>{
            this.spinner.hide();
            this.pageProductRequestItems.content = data;
          },error=>{
            this.spinner.hide();
            this._global.alert({ type: 'alert', message:error.error.message });
          })
  
        }
       
  
      }
      resetPT(){
        this.advancesearchPT.name=''
        this.advancesearchPT.product=''
        this.advancesearchPT.productDescription=''
         }
         searchPT(){
           if(this.advancesearchPT.name=='' &&
           this.advancesearchPT.product=='' &&
           this.advancesearchPT.productDescription==''){
             this._global.alert({ type: 'alert', message:"Please Enter valid Key for Search" });
           }
           else{
             this.spinner.show();
             this.pageProductTranferDetails.content=[];
             this._productRequestService.getSearchPT(this.advancesearchPT,this.objectId).subscribe(data=>{
               this.spinner.hide();
               this.pageProductTranferDetails.content = data;
               this.AllDataList=this.pageProductTranferDetails;
             },error=>{
               this.spinner.hide();
               this._global.alert({ type: 'alert', message:error.error.message });
             })
     
     
         }
       }
       advancesearchPTitems = {
        serialNumber: '',
        partcode: '',
        productDescription: '',
        productType:''
        
      };
      resetPTItems(){
    this.advancesearchPTitems.serialNumber='';
    this.advancesearchPTitems.partcode='';
    this.advancesearchPTitems.productDescription='';
    this.advancesearchPTitems.productType='';
      }
      searchPTitems(){
        if(this.advancesearchPTitems.serialNumber=='' &&
        this.advancesearchPTitems.partcode=='' &&
        this.advancesearchPTitems.productDescription=='' &&
        this.advancesearchPTitems.productType==''){
          this._global.alert({ type: 'alert', message:"Please Enter valid Key for Search" });
        }
        else{
          this.spinner.show();
          this.locationProductTransferLineItems=[];
          this._producttransferService.getPTItemsSearchResult(this.advancesearchPTitems,this.objectId).subscribe(data=>{
            this.spinner.hide();
            this.locationProductTransferLineItems = data;
            this.AllDataList.content=this.locationProductTransferLineItems
          },error=>{
            this.spinner.hide();
            this._global.alert({ type: 'alert', message:error.error.message });
          })
    
        }
       
    
      }

      // SO Items 
      advancesearchSOItem={
        serialNumber:'',
        productPartcode:'',
        productDescription:'',
        inhouseInventory:'',
        customerInventory:'',
        assignedTo:'',
        received:'',
        notReceived:'',
        lotNo:'',
        remarks:''
    
    
    
      }
      searchSOItems() {
        if(this.advancesearchSOItem.serialNumber==''&&
        this.advancesearchSOItem.productPartcode==''&&
        this.advancesearchSOItem.productDescription==''&&
        this.advancesearchSOItem.inhouseInventory==''&&
        this.advancesearchSOItem.customerInventory==''&&
        this.advancesearchSOItem.assignedTo==''&&
        this.advancesearchSOItem.received==''&&
        this.advancesearchSOItem.notReceived==''&&
        this.advancesearchSOItem.lotNo==''&&
        this.advancesearchSOItem.remarks==''){
          this._global.alert({ type: 'alert', message:"Please Enter valid Key for Search" });
        }
        else{
          this.spinner.show();
        this._serviceOrderService.getSearchResultforItems(this.advancesearchSOItem,this.objectId).subscribe(data => {
          this.spinner.hide();
          this.items = data;
        
        },
        error => {this.spinner.hide()
          // this.message=error.error.message;
          this._global.alert({ type: 'alert', message: error.error.message });
        });
      
        }
        
      }
      resetSOItems(){
        this.advancesearchSOItem.serialNumber='',
        this.advancesearchSOItem.productPartcode='',
        this.advancesearchSOItem.productDescription='',
        this.advancesearchSOItem.inhouseInventory=='',
        this.advancesearchSOItem.customerInventory='',
        this.advancesearchSOItem.assignedTo='',
        this.advancesearchSOItem.received='',
        this.advancesearchSOItem.notReceived='',
        this.advancesearchSOItem.lotNo='',
        this.advancesearchSOItem.remarks=''
      
      }
      advancesearch = {
        returnOrderLineItemManualId: '',
        productPartcode: '',
        productDescription: '',
        serialNumber: ''
      
      };
      
      searchROItems() {
         
        if(this.advancesearch.returnOrderLineItemManualId=='' && this.advancesearch.serialNumber==''
          && this.advancesearch.productPartcode=='' &&this.advancesearch.productDescription==''){
        //  this.message="Please Enter valid Key for Search";
         this._global.alert({type:'alert',message:"Please Enter valid Key for Search"});
    
        }
        else{
          this.spinner.show();
          this.d.content=[];
          this._productreturnservice.getSearchResultROItems(this.advancesearch,this.objectId).subscribe(data => {
            this.spinner.hide();
            this.d.content = data;
          },error=>{
            this.spinner.hide();
            // this.message=error.error.message;
            // this.popupSuccessModel.show();
            this._global.alert({type:'alert',message:error.error.message});
          });
        }
      
      }
      resetROItems(){
        this.advancesearch.returnOrderLineItemManualId='';
        this.advancesearch.productPartcode= '';
        this.advancesearch.productDescription= '';
        this.advancesearch.serialNumber= '';
      
      }

      pagenumber:any=null;
      jumpPage(pagenumber){
      if(this.AllDataList.totalPages< pagenumber){
        this.message="Enter Valid Input";
        this.popupSuccessModel.show();
      }
    else{
    
        this.page=pagenumber-1;
        if(this.pageName=="Case"){
          this.getCase(this.order,this.column,this.page);
         }
         else if(this.pageName=="Product Request Line Items"){
           this.getProductRequestDetails(this.objectId);
           this.getProducts();
           this.getItems(this.objectId);
           
         }
         else if( this.pageName=="Product Transfer"){
           this.PTofPRAdvance=true;
           this.getProductTransferResponse(this.objectId);
         }
         else if(this.pageName=="Product Transfer Item"){
          this.getPTItemDetails(this.objectId,this.page)
        }
         else if(this.pageName=="Return Order"){
           this.ROLineItemsAdv=true;
            this.getProductReturnItems('prd.product_part_code',this.order)
            
         }
         else if( this.pageName=="Product Tranfer Line Items"){
           this.PTLineItmsAdvanc=true;
           this.getLineItemDetails(this.objectId);
         }
         else if(this.pageName=="Service Order Items"){
          this.SOItmsAdv=true
          this.getItemsSO();
         }
         else if(this.pageName=="Price Book Entries"){
           this.getEntries();
         }
         else if(this.pageName=="Pre Registration Case"){
          this.getPrcBysite();
         }
         else if( this.pageName=="Service Order Return Items"){
          this.getSORItems(this.objectId,'item.service_order_id',this.order,this.page);
         
            }
        
            else if(this.pageName="Asset Pre Registration Case"){
              this.isassetPRC=true;
             this.getPrcByAsset();
        
            }
         else
         {
           this.pageProductRequestItems.content=[];
           this.AllDataList.content=[];
           this.casedetails.content=[];
           this.products=[];
         }
      
    
    }
  }
  asset: Array<AssetsResponse> =[];
  getAssetBySiteid(column: string, order: string) {
    this.spinner.show();
    this.asset=[];
    this.AllDataList.content=[];
    this.siteservice.getAssetBySiteId(this.objectId,`sort=${column},${order}`,`page=${this.page}&size=${this.pageSize}`).subscribe(data => {
     this.spinner.hide();
      this.asset = data.content;
      this.AllDataList.content=this.asset;
      // this.totalasset=data.totalElements;
      // this.firstserv=data.first;
      // this.lastserv=data.last;
      // this.serialNumberAst=(((this.pageAsset)*data.size));
    })
  }


    getSortOrder(tblId) {
      this.tbalehead.forEach((tbl) => {
        if (tbl.id == tblId) {
          this.column = tbl.sortColumnName;
        
           this.order = tbl.sortOrder == 'desc' ? 'asc' : 'desc';
      if(this.pageName=="Product Request Line Items"){
        this.getItems(this.objectId,this.column,this.order);
      }
      else if(this.pageName=="Pre Registration Case"){
        this.getPrcBysite(this.column,this.order);
       }
       else if(this.pageName=="Return Order"){
        this.ROLineItemsAdv=true;
         this.getProductReturnItems(this.column,this.order);
         
      }
      else if( this.pageName=="Service Order Return Items"){
        this.getSORItems(this.objectId,'item.service_order_id',this.order,this.page);
          }
          else if(this.pageName=="Product Transfer Item"){
            this.getPTItemDetails(this.objectId,this.page)
          }

         else if(this.pageName=="Case")
            {
              this.getCase(this.column,this.order,this.page);
            }
        
        
        }
       
      });
    }
    getSORItems(id,column: string, order: string,page?:any){
      this.spinner.show();
      this.AllDataList.content=[];
      this.soritems.content=[];
    this.sorservice.getSORItemsbysorId(id, `page=${page}&sort=${column},${order}&size=20`).subscribe(data=>{
      this.spinner.hide();
      this.soritems=data;
      this.AllDataList=this.soritems;
    },error=>{
      this.spinner.hide();
      this._global.alert({ type: 'alert', message:error.error.message });
  
    })
    }
    advancesearchSORItm = {
      serialNumber: '',
      productPartcode:'',
      sourceLocation: '',
      destinationLocation: '',
      productDescription:'',
      received:'',
      customerInventory:'',
      inhouseInventory:'',
      notReceived:''
     
      
    };
      searchSROItems() {
        if( this.advancesearchSORItm.serialNumber== '' &&
        this.advancesearchSORItm. productPartcode=='' &&
        this.advancesearchSORItm.sourceLocation== '' &&
        this.advancesearchSORItm.destinationLocation== '' &&
        this.advancesearchSORItm.productDescription=='' &&
        this.advancesearchSORItm.customerInventory=='' &&
        this.advancesearchSORItm.notReceived==''&&
        this.advancesearchSORItm.received==''&&
        this.advancesearchSORItm.inhouseInventory==''
        ){
          this._global.alert({ type: 'alert', message:"Please Enter valid Key for Search" });
      
        }
        else{
          this.spinner.show();
        this.sorservice.getSORItemsSearchResult(this.objectId,this.advancesearchSORItm).subscribe(data => {
          this.spinner.hide();
          this.soritems.content = data;
        
        },error=>{
          this.spinner.hide();
          this._global.alert({type:'alert',message:error.error.message});
      
        });
      
        }
        
      }
      resetSROItems(){
        this.advancesearchSORItm.serialNumber=''
        this.advancesearchSORItm. productPartcode=''
        this.advancesearchSORItm.sourceLocation=''
        this.advancesearchSORItm.destinationLocation=''
        this.advancesearchSORItm.productDescription=''
        this.advancesearchSORItm.received=''
        this.advancesearchSORItm.customerInventory=''
        this.advancesearchSORItm.inhouseInventory=''
        this.advancesearchSORItm.notReceived=''
    
      }

      LPTItems:Page<LocationProductTransferItemResponse>=new Page<LocationProductTransferItemResponse>()
      getPTItemDetails(transferId,page){
        this.spinner.show();
        this.LPTItems.content=[];
        this.AllDataList.content=[];
        this._producttransferService.getPTItems(transferId, `page=${page}&size=20`).subscribe(data=>{
          this.spinner.hide();
          this.LPTItems=data;
          this.AllDataList=this.LPTItems;
    
        },error=>{
          this.spinner.hide();
          this._global.alert({ type: 'alert', message:error.error.message });
        })
      }
    advancesearchPTItm = {
      manualId: '',
      partcode:'',
      sourceLocation: '',
      destinationLocation: '',
      productDescription:''
     
      
    };
    searchPTItm() {
        if( this.advancesearchPTItm.manualId== '' &&
        this.advancesearchPTItm. partcode=='' &&
        this.advancesearchPTItm.sourceLocation== '' &&
        this.advancesearchPTItm.destinationLocation== '' &&
        this.advancesearchPTItm.productDescription==''){
          this._global.alert({ type: 'alert', message:"Please Enter valid Key for Search" });
      
        }
        else{
          this.spinner.show();
          this.AllDataList.content=[];
        this._producttransferService.getSearchResult(this.advancesearchPTItm).subscribe(data => {
          this.spinner.hide();
          this.LPTItems.content = data;
          this.AllDataList.content=data;
        
        },error=>{
          this.spinner.hide();
          this._global.alert({type:'alert',message:error.error.message});
      
        });
      
        }
        
      }
      resetPTItm(){
        this.advancesearchPTItm.manualId=''
        this.advancesearchPTItm. partcode=''
        this.advancesearchPTItm.sourceLocation=''
        this.advancesearchPTItm.destinationLocation=''
        this.advancesearchPTItm.productDescription=''
      }
      selecteditemid:number;
      forPR:string;
      shortcloseHeading:string='';
      messageShort:string='';
      @ViewChild('sureModel')sureModel:ModalDirective

      onshortCloseItem( id?:any){
        console.log(id);
        this.shortcloseHeading=id.lpRequestItemManualId;
        this.forPR='onlyItem';
        this.messageShort='Do you want to Short Close this item'
        this.selecteditemid=id.lpRequestItemId;
        console.log("item id="+ this.selecteditemid);
          
        this.sureModel.show();
      }

      sureModelhide(){
        this.sureModel.hide();
        // this.prId=null;
        // this.prManualId=null;
      }


      yesRequest(){
        console.log(this.forPR);
        this.spinner.show();
        this._productRequestService.shortClose(this.objectId,this.selecteditemid,this.forPR).subscribe(data=>{
          this.spinner.hide();
          this.sureModel.hide();
          if(this.forPR=="allPR")
          {
            this._global.alert({ type: '', message:" Product Request has been Short Closed" });
          }
          else
          {
            this._global.alert({ type: '', message:" Product Request Line Item has been Short Closed" });
          }
          
          this.getProductRequestDetails(this.objectId);
          this.getItems(this.objectId,this.column,this.order);
       
        },error=>{
          this.spinner.hide();
          this._global.alert({ type: 'alert', message:error.error.message });
        })
      }
}
