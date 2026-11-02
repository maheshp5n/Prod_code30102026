import { Component, OnInit, ViewChild } from '@angular/core';
import { ProductLineItemsRequest, ProductLineItemsResponse } from '../../services/model';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute } from '@angular/router';
import { ProductLineItemsService } from '../../services/product-line-items.service';

@Component({
  selector: 'app-productLineItemsdetails',
  templateUrl: './productLineItemsdetails.component.html',
  styleUrls: ['./productLineItemsdetails.component.css']
})
export class ProductLineItemsdetailsComponent implements OnInit {

  constructor(private _productlineitemsService: ProductLineItemsService, private spinner: NgxSpinnerService,
    private _activatedRoute: ActivatedRoute) {
    this._activatedRoute.params.subscribe(pram => {
      this.productlineitemsName = pram['id'];
    });
  }
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;
  @ViewChild('createProductLineItemsModel') createProductLineItemsModel: ModalDirective;

  productlineitemsName: string;
  productlineitems: ProductLineItemsResponse = new ProductLineItemsResponse();
  productlineitemsList: Array<ProductLineItemsResponse> = [];
  isEditMode: boolean = false;
  heading: string;
  // popupNgClass:boolean;
  CREATE_PRODUCTLINEITEMS_HEADING: string = 'New Product';
  EDIT_PRODUCTLINEITEMS_HEADING: string = 'Edit Product';
  newproductLineItems: ProductLineItemsRequest = new ProductLineItemsRequest();
  userforlist;
  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  message: string;
  vDetails: boolean = true;
  popupNgClass: string = 'alert alert-danger center center-block';

  addressDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  infoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };
  sysinfoDisplay = {
    angleIcon: 'fa fa-angle-down',
    open: true
  };

  ngOnInit() {
    // this.getProductLineItemsDetails();
  }

  changeAddressDisplay() {
    this.addressDisplay.open = !this.addressDisplay.open;
    if (this.addressDisplay.open) {
      this.addressDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.addressDisplay.angleIcon = 'fa fa-angle-right';
    }
  }
  changeInfoDisplay() {
    this.infoDisplay.open = !this.infoDisplay.open;
    if (this.infoDisplay.open) {
      this.infoDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.infoDisplay.angleIcon = 'fa fa-angle-right';
    }

  }
  sysInfoDisplay() {
    this.sysinfoDisplay.open = !this.sysinfoDisplay.open;
    if (this.sysinfoDisplay.open) {
      this.sysinfoDisplay.angleIcon = 'fa fa-angle-down';
    } else {
      this.sysinfoDisplay.angleIcon = 'fa fa-angle-right';
    }
  }

  subTabChange(tabName: string) {
    if (tabName === 'details') {
      this.ngClassDetails = 'active';
      this.ngClassReleated = '';
      this.vDetails = true;
    } else {
      this.ngClassDetails = '';
      this.ngClassReleated = 'active';
      this.vDetails = false;
    }
    return false;
  }

  getProductLineItemsDetails() {
    this.spinner.show();
    this._productlineitemsService.getproductLineItemsById(5).subscribe(data => {
      if (data != null) {
        this.spinner.hide();
      }
      this.productlineitems = data;
    });
  }

  cancelData() {
    // this.createProductLineItemsModel.hide();
    // this.isEditMode = false;
    // this.heading = this.CREATE_PRODUCTLINEITEMS_HEADING;
    // this.newproductLineItems = new ProductLineItemsRequest();
  }

  onClickEdit() {
    // this.spinner.show();
    // this.newproductLineItems = y;
    // this._productlineitemsService.getproductLineItemsById(i).subscribe(data => {
    // this.newproductLineItems.productlineitemsName = data.productlineitemsName;
    // this.newproductLineItems.zoneName = data.zone;
    // this.newproductLineItems.countryCode = data.countryCode;
    // this.newproductLineItems.productlineitemsManagerBattryId = data.productlineitemsManagerBattryId;
    // this.newproductLineItems.productlineitemsManagerPowerId = data.productlineitemsManagerPowerId;
    //   if (data != null) {
    //     this.spinner.hide();
    //   }
    // });
    this.isEditMode = true;
    this.heading = this.EDIT_PRODUCTLINEITEMS_HEADING;
    this.createProductLineItemsModel.show();
  }

  onProductLineItemsCreated($event: ProductLineItemsResponse) {
    this.productlineitemsList.push($event);
    this.productlineitems = $event;
    
  }
  popClose(){
    this.popupSuccessModel.hide();
  }

  popupClose() {
    this.popupSuccessModel.hide();   
  }

  
}
