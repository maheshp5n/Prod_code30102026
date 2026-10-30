import { Loginservices } from './../../../services/login.services';
import { ServiceOrderService } from '../../../services/serviceorder.servvices';
import { NgxSpinnerService } from 'ngx-spinner';
import { ModalDirective } from 'ngx-bootstrap';
import { GlobalserviceService } from '../../../globalservice.service';
import { ServiceOrderJobCardService } from '../../../services/serviceorderjobcardservice';
import { ViewChild, Component, OnInit, Input } from '@angular/core';
import { ActionHistoryObjectAndId } from '../../../services/model';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-serviceorderreleatedview',
  templateUrl: './serviceorder.releatedview.component.html',
  styleUrls: ['./serviceorder.releatedview.component.scss']
})
export class ServiceorderReleatedviewComponent implements OnInit {

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
      this.actionHistoryObjectAndId.objectName = 'ServiceOrder';
      this.getItems();
    }

  }

  @ViewChild('assignToEng', { static: false }) assignToEng: ModalDirective;
 objectId:any;
  constructor(private _serviceOrderService: ServiceOrderService,
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
    this.getRepariEng();
  }

  getItems() {
    this._ngx.show();
    this.items=[];
    this._serviceOrderService.getServiceOrderItems(this._serviceOrderId).subscribe(data => {
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

  getRepariEng() {
    this._serviceOrderService.getRepariEng().subscribe(data => this.serviceEngs = data);
  }
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
      this._ngx.show();
    this._serviceOrderService.getSearchResultforItems(this.advancesearchSOItem,this._serviceOrderId).subscribe(data => {
      this._ngx.hide();
      this.items = data;
    
    },
    error => {this._ngx.hide()
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
}
