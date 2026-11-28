import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../globalservice.service';
import { Loginservices } from '../../services/login.services';
import { Page, ServiceOrderReturnItemResponse, ServiceOrderReturnResponse } from '../../services/model';
import { PdfServiceService } from '../../services/pdf-service.service';
import { ServiceorderreturnService } from '../../services/serviceorderreturn.service';

@Component({
  selector: 'app-service-order-return-detail',
  templateUrl: './service-order-return-detail.component.html',
  styleUrls: ['./service-order-return-detail.component.css']
})
export class ServiceOrderReturnDetailComponent implements OnInit {
  @ViewChild('confirmationPopup') confirmationPopup:ModalDirective;

  navigationMenue = {
    details: {
      ngclass: '',
      view: false
    },
    releted: {
      ngclass: '',
      view: false
    }
  };
  addressDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  infoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  }
  sysinfoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  }
  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  _cFlag:string;
  sorId:number;
  caseCoulms=[];
  sortCaseColumnsBlnk=[];
  sortArr:string;
  page:number=0;
  columns=["S.No","Service Order","Item Serial Number","Product Part Code","Product Description","Previous Product Final State Name","Final Product State Name"];
  sortCaseColumns=["","item.service_order_id","item.serial_number","item.product_product_part_code","prd.pdoduct_description","prestate.product_state_name","stfinal.product_state_name"]
  resp:ServiceOrderReturnResponse=new ServiceOrderReturnResponse();
  soritems:Page<ServiceOrderReturnItemResponse>=new Page<ServiceOrderReturnItemResponse>()
  actionHistoryObjectAndId: any;
  constructor( 
    private sorservice:ServiceorderreturnService,
    private _gloabl: GlobalserviceService,
    private _activatedRoute:ActivatedRoute,
    private _ngxspinner: NgxSpinnerService,
    private loginservice: Loginservices,private pdfservice:PdfServiceService
  ) { 
    this._activatedRoute.params.subscribe(param => {
      this.sorId = param['id'];
      this.actionHistoryObjectAndId.objectId=this.sorId;
      // this.getServiceOrderDetails(this.serviceOrderId);
    });
    this._activatedRoute.queryParams.subscribe(pram=>{
      this.caseCoulms=this.columns;
      this._cFlag = pram['_c'];
      this.sortCaseColumnsBlnk=this.sortCaseColumns;
    })
    this.getDetailsById(this.sorId);
  }

  ngOnInit() {
  }
  getDetailsById(id){
    this._ngxspinner.show();
    this.sorservice.getSORbyid(id).subscribe(data=>{
      this._ngxspinner.hide();
      this.resp=data;
      this.resp.manualId
    },error=>{
      this._ngxspinner.hide();
      this._gloabl.alert({ type: 'alert', message:error.error.message });

    })
  }
  vDetails:boolean=true;
  subTabChange(tabName: string) {
    if (tabName == 'details') {
      this.ngClassDetails = 'active';
      this.ngClassReleated = '';
      this.vDetails = true;
      this.getDetailsById(this.sorId);
    }
    else {
      this.ngClassDetails = '';
      this.ngClassReleated = 'active';
      this.vDetails = false;
      this.getSORItems(this.sorId);
 
    }
    return false;
  }
  changeAddressDisplay() {
    this.addressDisplay.open = !this.addressDisplay.open
    if (this.addressDisplay.open) {
      this.addressDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.addressDisplay.angleIcon = 'fa fa-angle-right';
    }
  }
  changeInfoDisplay() {
    this.infoDisplay.open = !this.infoDisplay.open
    if (this.infoDisplay.open) {
      this.infoDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.infoDisplay.angleIcon = 'fa fa-angle-right';
    }

  }
  sysInfoDisplay() {
    this.sysinfoDisplay.open = !this.sysinfoDisplay.open
    if (this.sysinfoDisplay.open) {
      this.sysinfoDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.sysinfoDisplay.angleIcon = 'fa fa-angle-right';
    }

  }
  getSORItems(id){
    this._ngxspinner.show();
  this.sorservice.getSORItemsbysorId(id, `page=0&size=5`).subscribe(data=>{
    this._ngxspinner.hide();
    this.soritems=data;
  },error=>{
    this._ngxspinner.hide();
    this._gloabl.alert({ type: 'alert', message:error.error.message });

  })
  }
  createPDF() {
    this._ngxspinner.show();
    this.pdfservice.createPDF('api/serviceorderreturn', 'serivceOrderReturnId',
    this.sorId).subscribe(data => {
      this._ngxspinner.hide();
        const file = new Blob([data], { type: 'application/pdf' });
     
        if (window.navigator && window.navigator.msSaveOrOpenBlob) {
          window.navigator.msSaveOrOpenBlob(file);
        } else {
          const fileURL = URL.createObjectURL(file);
          window.open(fileURL);
        }
        // this.getFileList('Quote', this.expensesmanagement.expenseManagementId);

        this.confirmationPopup.hide();
        this._gloabl.alert({ type: '', message: "Created successfully"});
      }, err => {
        this._ngxspinner.hide();
        this.confirmationPopup.hide();
        this._gloabl.alert({ type: 'alert', message: err.error.message });
      });
  }
  // search itms

  
advancesearch = {
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
    if( this.advancesearch.serialNumber== '' &&
    this.advancesearch. productPartcode=='' &&
    this.advancesearch.sourceLocation== '' &&
    this.advancesearch.destinationLocation== '' &&
    this.advancesearch.productDescription=='' &&
    this.advancesearch.customerInventory=='' &&
    this.advancesearch.received=='' &&
    this.advancesearch.notReceived==''&&
    this.advancesearch.inhouseInventory==''
    ){
      this._gloabl.alert({ type: 'alert', message:"Please Enter valid Key for Search" });
  
    }
    else{
      this._ngxspinner.show();
    this.sorservice.getSORItemsSearchResult(this.sorId,this.advancesearch).subscribe(data => {
      this._ngxspinner.hide();
      this.soritems.content = data;
    
    },error=>{
      this._ngxspinner.hide();
      this._gloabl.alert({type:'alert',message:error.error.message});
  
    });
  
    }
    
  }
  resetSROItems(){
    this.advancesearch.serialNumber=''
    this.advancesearch. productPartcode=''
    this.advancesearch.sourceLocation=''
    this.advancesearch.destinationLocation=''
    this.advancesearch.productDescription=''
    this.advancesearch.received=''
    this.advancesearch.customerInventory=''
    this.advancesearch.inhouseInventory=''
    this.advancesearch.notReceived=''

  }

}
