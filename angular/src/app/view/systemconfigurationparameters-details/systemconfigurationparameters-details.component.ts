import { Component, OnInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { AssetsResponse } from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute } from '@angular/router';
import { AssetsService } from '../../services/assets.services';

@Component({
  selector: 'app-systemconfigurationparameters-details',
  templateUrl: './systemconfigurationparameters-details.component.html',
  styleUrls: ['./systemconfigurationparameters-details.component.scss']
})
export class SystemconfigurationparametersDetailsComponent implements OnInit {
  @ViewChild('createAcountModel') createAcountModel: ModalDirective;
  
  @Output()
  onAccountCreated: EventEmitter<AssetsResponse> = new EventEmitter<AssetsResponse>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();


  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  vDetails: boolean = true;
  sapId: string = '';
 
  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  EDIT_ACCOUNT_HEADING: string = 'Edit System Configuration Parameter';

  assetResponse: AssetsResponse = new AssetsResponse();
  assetList: Array<AssetsResponse> = [];
  asset: AssetsResponse = new AssetsResponse();
  constructor(private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute,
    private _assetsService: AssetsService) {
    this._activatedRoute.params.subscribe(pram => {
      this.sapId = pram['id'];      
    });

  }

  ngOnInit() {
    this.getAssets();
  }
  getAssets() {
    this.spinner.show();
    this._assetsService.getAssetsBySerialNumber(this.sapId).subscribe(data => {
      if(data!=null){
        this.spinner.hide();
      }    
      this.asset = data;    
    });
  }

  addressDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  infoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  }
  purchaseDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  }
  warrantyDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  }
  amcDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  }
  systemDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
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
  changePurchaseDisplay() {
    this.purchaseDisplay.open = !this.purchaseDisplay.open
    if (this.purchaseDisplay.open) {
      this.purchaseDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.purchaseDisplay.angleIcon = 'fa fa-angle-right';
    }
  }
  changeWarrantyDisplay() {
    this.warrantyDisplay.open = !this.warrantyDisplay.open
    if (this.warrantyDisplay.open) {
      this.warrantyDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.warrantyDisplay.angleIcon = 'fa fa-angle-right';
    }
  }

  changeAmcDisplay() {
    this.amcDisplay.open = !this.amcDisplay.open
    if (this.amcDisplay.open) {
      this.amcDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.amcDisplay.angleIcon = 'fa fa-angle-right';
    }
  }

  changeSystemDisplay() {
    this.systemDisplay.open = !this.systemDisplay.open
    if (this.systemDisplay.open) {
      this.systemDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.systemDisplay.angleIcon = 'fa fa-angle-right';
    }
  }

  subTabChange(tabName: string) {
    this.spinner.show();
    if (tabName == 'details') {
      this.spinner.hide();
      this.ngClassDetails = 'active';
      this.ngClassReleated = '';
      this.vDetails = true;
      this.getAssets();
    }
    else {
      this.spinner.show();
      this.ngClassDetails = '';
      this.ngClassReleated = 'active';
      this.vDetails = false;
      //this.getContacts();
    }
    this.spinner.hide();
    return false;
  }

  createAcountModelhide() {
    //this.assetResponse=new AssetsResponse();
    this.onCancle.emit('cancled');
    this.createAcountModel.hide();
  }
  /* showCreateAccountView(){
    this.assetResponse=new AssetsResponse();
    this.isEditMode = false;
    this.heading =this.CREATE_ACCOUNT_HEADING;
    this.createAcountModel.show();
  } */

  createEditModelshow(asset: AssetsResponse) {
    this.spinner.show();
    this._assetsService.getAssetsBySerialNumber(asset.serialNumber).subscribe(data => {
      this.assetResponse = data
      if (data != null) {
        this.spinner.hide();
      }
      
    });
    this.isEditMode = true;
    this.heading = this.EDIT_ACCOUNT_HEADING;
    this.createAcountModel.show();
  }

  cancelData() {
    //this.assetResponse=new AssetsResponse();
    this.createAcountModel.hide();
  }

  onAssetCreated($event: AssetsResponse) {
    //console.log('event',$event)
    let assetEvent = new AssetsResponse();
    this.spinner.show();
    this._assetsService.getAssetsBySerialNumber($event.serialNumber).subscribe(data => {      
      this.spinner.hide()
      assetEvent = data;
    });
    this.assetList.push(assetEvent);
    this.asset = $event;
  }
}
