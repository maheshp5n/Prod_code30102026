import { Component, OnInit, ViewChild, Input, Output, EventEmitter } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { MSLRequestStatus,  MSLRequest,MSLRequestResponse, ProductsForList,MSLItemRequestRequest,LocationResponse} from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../services/login.services';
import { mslRequestService } from '../../services/mslRequest.services';
import { AssetsService } from '../../services/assets.services';
import { ServiceResourceService } from '../../services/serviceresource.services';

@Component({
  selector: 'app-create-mslrequest',
  templateUrl: './create-mslrequest.component.html',
  styleUrls: ['./create-mslrequest.component.scss']
})
export class CreateMslrequestComponent implements OnInit {
  
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;
  @ViewChild('MSLItemModel') MSLItemModel: ModalDirective;
  loc:LocationResponse=new LocationResponse();
  isEditMode:boolean=false;
  @Input()
  set location(loc:LocationResponse) {
    if(loc){
      this.loc=loc;
      this._mslRequest.locationId=loc.locationId;
    }
    else{
      this.loc=new LocationResponse();
    }
  }
  get location():LocationResponse{
    return this.loc;
  }
  @Input()
  set editMode(mode:boolean){
    this.isEditMode=mode;
  }
  get editMode():boolean{
    return this.isEditMode;
  }
  alertmessage={class:'alert alert-success center center-block',message:''};
  loginData;
  products:Array<ProductsForList>=[];
  mslStatus:Array<MSLRequestStatus>=[];
  _mslRequest:MSLRequest=new MSLRequest();
  _mslItemRequest:MSLItemRequestRequest=new MSLItemRequestRequest();
  

  @Output()
  onMSLCreated: EventEmitter<MSLRequestResponse> = new EventEmitter<MSLRequestResponse>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();
  constructor(private spinner: NgxSpinnerService, private loginservice: Loginservices, private mslreqservice: mslRequestService, private assetserive: AssetsService, private serviceResourceservice: ServiceResourceService) { }

  ngOnInit() {
    if(this._mslRequest.itemRequest==null||this._mslRequest.itemRequest==undefined){
      this._mslRequest.itemRequest=[];
    }
    this.getLoggedInUser()
    this.getStatus();
    this.getProductsList();
    this._mslRequest.statusId=1;
  }
  getLoggedInUser() {
    this.loginData = this.loginservice.getLoggedInUser();
  }
  cancelData() {
      this._mslRequest = new MSLRequest();
      this._mslRequest.itemRequest=[];
      this._mslRequest.statusId=1;
      this._mslRequest.locationId=this.loc.locationId;
      this.onCancle.emit('cancled');
  }
  saveMSLData(){
    if(this._mslRequest.locationId==null||this._mslRequest.locationId==undefined){
      this.showAlert('Warehouse not found');
      return;
    }
    if(this._mslRequest.statusId==null||this._mslRequest.statusId==undefined){
      this.showAlert('Status not found');
      return;
    }
    this.mslreqservice.saveMsl(this._mslRequest).subscribe(data=>{
      this.onMSLCreated.emit(data);
    },err=>this.showAlert(err.error.message));
  }
  getStatus() {
    this.spinner.show()
    this.mslreqservice.getMSLRequestForList().subscribe(data => {
      this.spinner.hide();
      this.mslStatus = data
    },err=>this.spinner.hide());
  }
 
  getProductsList() {
    this.assetserive.getProductForList().subscribe(data => {
      this.products = data
    })
  }
  addProductList(){
    if(this._mslItemRequest.productPartCode==null||this._mslItemRequest.productPartCode==undefined){
      this.showAlert('Please select part code');
      return;
    }
    if(this._mslItemRequest.hslQty==null||this._mslItemRequest.hslQty==undefined){
      this._mslItemRequest.hslQty=0;
    }
    if(this._mslItemRequest.mslQty==null||this._mslItemRequest.mslQty==undefined){
      this._mslItemRequest.mslQty=0;
    }
    if(this._mslItemRequest.hslQty<this._mslItemRequest.mslQty){
      this.showAlert('HSL Quantity can not be less then MSL Quantity');
      return;
    }
    let alreadyExists:boolean=false;
    this._mslRequest.itemRequest.forEach((item)=>{
      if(item.productPartCode==this._mslItemRequest.productPartCode){
        alreadyExists=true;
      }
    });
    if(alreadyExists){
      this.showAlert(`Please choose different part code, ${this._mslItemRequest.productPartCode} already selected`);
      return;
    }
    let _item=new MSLItemRequestRequest();
    _item.productPartCode=this._mslItemRequest.productPartCode;
    _item.hslQty=this._mslItemRequest.hslQty;
    _item.mslQty=this._mslItemRequest.mslQty;
    this._mslRequest.itemRequest.push(_item);
    
    this._mslItemRequest.productPartCode = null;
    this._mslItemRequest.hslQty = null;
    this._mslItemRequest.mslQty = null;
  }
  removeItems(index:number){
    let temp=[];
    for(let i=0;i<this._mslRequest.itemRequest.length;i++){
      if(i!=index){
        temp.push(this._mslRequest.itemRequest[i]);
      }
    }
    this._mslRequest.itemRequest=temp;
  }
  showAlert(message:string){
    this.alertmessage.class='alert alert-danger center center-block';
    this.alertmessage.message=message;
    this.popupSuccessModel.show();
  }
  showMessage(message:string){
    this.alertmessage.class='alert alert-success center center-block';
    this.alertmessage.message=message;
    this.popupSuccessModel.show();
  }
}
