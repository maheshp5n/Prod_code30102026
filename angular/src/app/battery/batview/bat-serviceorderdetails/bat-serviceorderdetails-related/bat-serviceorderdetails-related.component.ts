import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../../globalservice.service';
import { Loginservices } from '../../../../services/login.services';
import { ActionHistoryObjectAndId } from '../../../../services/model';
import { ServiceOrderJobCardService } from '../../../../services/serviceorderjobcardservice';
import { BtServiceOrderService } from '../../../bat_services/bt-service-order.service';

@Component({
  selector: 'app-bat-serviceorderdetails-related',
  templateUrl: './bat-serviceorderdetails-related.component.html',
  styleUrls: ['./bat-serviceorderdetails-related.component.scss']
})
export class BatServiceorderdetailsRelatedComponent implements OnInit {


  _serviceOrderId: any;
  items = [];

  gcheckbox = false;

  serviceEngs = [];
  _serviceEng;

  openModal = {
    assign: false
  };

  actionHistoryObjectAndId: ActionHistoryObjectAndId = new ActionHistoryObjectAndId();
  serviceRoCol=[];
  page:number=0;
  serviceRoColItem=["S.No","Item Serial Number","Part Code","Desription","Status","Customer Inventory","Received"]
  @Input()
  set serviceOrderId(id) {
    if (id) {
      this._serviceOrderId = id;
      this.actionHistoryObjectAndId.objectId = this._serviceOrderId;
      this.actionHistoryObjectAndId.objectName = 'BtServiceOrder';
      this.getItems();
    }

  }

  @ViewChild('assignToEng') assignToEng: ModalDirective;
 objectId:any;
  constructor(private _btserviceOrderService: BtServiceOrderService,
    private _ngx: NgxSpinnerService, private _global: GlobalserviceService,
    private _jobcard: ServiceOrderJobCardService,
    private loginservice: Loginservices,private _activatedroute:ActivatedRoute
  ) {
    this._activatedroute.queryParams.subscribe(pram => {
      this.serviceRoCol=this.serviceRoColItem;

    });
    this._activatedroute.params.subscribe(pram=>{
       this.objectId=this._serviceOrderId;
          })
   }

  ngOnInit() {
    // this.getRepariEng();
  }

  getItems() {
    this._ngx.show();
    this.items=[];
    this._btserviceOrderService.btItemsForServiceOrder(this._serviceOrderId,'yes').subscribe(data => {
      this._ngx.hide();
      this.items = data;
    });
  }

  changeGlobal() {
    const counter = this.items.length;
    for (let i = 0; i < counter; i++) {
      if(this.items[i].assignedResourceId==null){
        this.items[i].checked = this.gcheckbox;
      }
      else{
        this.items[i].checked=false;
      }
      
    }
  }
// pawan comment
  // getRepariEng() {
  //   this._btserviceOrderService.getRepariEng().subscribe(data => this.serviceEngs = data);
  // }
  assign() {
    const temp = [];
    const counter = this.items.length;
    for (let i = 0; i < counter; i++) {
      if (this.items[i].checked) {
        temp.push(this.items[i].serviceOrderItemId);
      }
    }

    if (temp.length === 0) {
      this._global.alert({ type: 'alert', message: 'Please select product to assign' });
      return;
    }

    if (this._serviceEng === undefined || this._serviceEng === null || this._serviceEng === '') {
      this._global.alert({ type: 'alert', message: 'Please select Repari Eng. to assign' });
      return;
    }
   
    this._jobcard.createJobCard(this._serviceEng, temp).subscribe(data => {
      this.assignToEng.hide();
      this._global.alert({ type: '', message: 'Assigned Successfully' });
      this.getItems();
    }, error => {
      this._global.alert({ type: 'alert', message: error.error.message });
    });

    this.assignToEng.hide();

  }

  rights(object: string, right: string): boolean {
    return this.loginservice.isUserValidFor(object, right);
  }
  advancesearchSOItem={
    serviceOrderItemSerialNumber:'',
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
    if(this.advancesearchSOItem.serviceOrderItemSerialNumber==''&&
    this.advancesearchSOItem.productPartcode==''&&
    this.advancesearchSOItem.productDescription==''&&
    this.advancesearchSOItem.inhouseInventory==''&&
    this.advancesearchSOItem.customerInventory==''&&
    this.advancesearchSOItem.assignedTo==''&&
    this.advancesearchSOItem.received==''&&
    this.advancesearchSOItem.notReceived==''&&
    this.advancesearchSOItem.lotNo==''&&
    this.advancesearchSOItem.remarks==''){
      this._global.alert({ type: 'alert', message:"Please Enter Valid Key For Search" });
    }
    else{
      this._ngx.show();
    this._btserviceOrderService.btSeriveOrderItemsAdvancesearch(this._serviceOrderId,this.advancesearchSOItem).subscribe(data => {
      this._ngx.hide();
      this.items = data;   
    },
    error => {this._ngx.hide()
      this._global.alert({ type: 'alert', message: error.error.message });
    }); 
    }  
  }
  resetSOItems(){
    this.advancesearchSOItem.serviceOrderItemSerialNumber='',
    this.advancesearchSOItem.productPartcode='',
    this.advancesearchSOItem.productDescription='',
    this.advancesearchSOItem.inhouseInventory=='',
    this.advancesearchSOItem.customerInventory='',
    this.advancesearchSOItem.assignedTo='',
    this.advancesearchSOItem.received='',
    this.advancesearchSOItem.notReceived='',
    this.advancesearchSOItem.lotNo='',
    this.advancesearchSOItem.remarks=''
    this.getItems(); 
  }

}
