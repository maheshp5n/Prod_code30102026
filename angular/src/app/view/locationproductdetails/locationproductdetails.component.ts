import { LocationService } from '../../services/location.service';
import { NgxSpinnerService } from 'ngx-spinner';

import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { LocationPartCodeSerialNumber, ProductType } from '../../services/model';
import { GlobalserviceService } from '../../globalservice.service';
import { ProductsService } from '../../services/products.services';

@Component({
  selector: 'app-locationproductdetails',
  templateUrl: './locationproductdetails.component.html',
  styleUrls: ['./locationproductdetails.component.scss']
})
export class LocationproductdetailsComponent implements OnInit {

  queryparam = {
    page: 0,
    size: 20,
    sort: 'desc',
    sortc: 'product.productPartCode',
    clm: 1
  };
  locationId;
  products;
  location;
  tableHeader = [{
    name: 'Sl.No',
    sortc: 'locationProductItemId'
  },
  {
    name: 'Product Item',
    sortc: 'locationProductItemId'
  },
  {
    name: 'Part Code',
    sortc: 'product.productPartCode'
  },
  {
    name: 'Description',
    sortc: 'product.pdoductDescription'
  },
  {
    name: 'Type',
    sortc: 'product.productType'
  },
  {
    name: 'In-Hand Quantity',
    sortc: 'quantityInOnHand'
  },
  {
    name: 'OK Quantity',
    sortc: 'okQuantity'
  },
  {
    name: 'Faulty Quantity',
    sortc: 'faultyQuantity'
  },
  {
    name: 'Scrap Quantity',
    sortc: 'scrapQty'
  },
  {
    name: 'Rejected Quantity',
    sortc: 'rejectedQty'
  },
  {
    name: 'BER Quantity',
    sortc: 'berQty'
  },
  {
    name: 'Customer Inventory',
    sortc: 'customerInventory'
  },
  {
    name: 'Refurbished',
    sortc: 'refurbished'
  }

  ];
  ProductItemAdvancesearch = {
    productItemNumber: '',
    partcode: '',
    productDescription: '',
    productType: ''
  
  };
  proType:Array<ProductType>=[]
  constructor(

    private _activatedRouter: ActivatedRoute,
    private localservice: LocationService,
    private _ngx: NgxSpinnerService,
    private global:GlobalserviceService,
    private productService:ProductsService

  ) {
    this._activatedRouter.params.subscribe(pram => {
      this.locationId = pram['locationid'];
    });
    this._activatedRouter.queryParams.subscribe(param => {
      if (param['page'] != undefined && param['page'] != null) {
        this.queryparam.page = Number(param['page']);
      }
      if (param['size'] != null && param['size'] != undefined) {
        this.queryparam.size = Number(param['size']);
      }
      if (param['sort'] != null && param['sort'] != undefined) {
        this.queryparam.sort = param['sort'];
      }
      if (param['sortc'] != null && param['sortc'] != undefined) {
        this.queryparam.sortc = param['sortc'];
      }
      if (param['clm'] != null && param['clm'] != undefined) {
        this.queryparam.clm = param['clm'];
      }
      this.getProducts();
    });
  }

  ngOnInit() {
    this.getLocationDetails();
    this.getProductType();
  }
  getProducts() {
    this._ngx.show();
    this.localservice.getProductItemsByLocationId(this.locationId, `page=${this.queryparam.page}&size=${this.queryparam.size}&sort=${this.queryparam.sortc},${this.queryparam.sort}`).subscribe(data => {
      this.products = data;
      this._ngx.hide();
    }, error => this._ngx.hide());
  }
  sortOrder(i) {
    if (i == this.queryparam.clm) {
      this.queryparam.sort = this.queryparam.sort == 'desc' ? 'asc' : 'desc';
    }
    else {
      this.queryparam.clm = i;
      this.queryparam.sortc = this.tableHeader[i].sortc;
    }
    this.getProducts();
  }
  getLocationDetails() {
    this.localservice.getLocationById(this.locationId).subscribe
      (data => {
        this.location = data;
      });
  }
  locationSerialNumberList: Array<LocationPartCodeSerialNumber> = [];
  serialNumberDetaiDisp: boolean = false;
  expendPartcodeItem(partCode) {
    this._ngx.show();
    // this.serialNumberDetaiDisplay.open=!this.serialNumberDetaiDisplay.open;
    this.localservice.getserialNumberByLocationPartCode(this.locationId, partCode).subscribe(data => {
      this._ngx.hide();
      this.locationSerialNumberList = data;
      if (this.locationSerialNumberList != null) {
        this.serialNumberDetaiDisp = true;
      }
      else {
        this.serialNumberDetaiDisp = false;
      }

    })
  }
  searchPrItemLoc(){
    if(this.ProductItemAdvancesearch.productItemNumber== ''&&
    this.ProductItemAdvancesearch.partcode== '' &&
    this.ProductItemAdvancesearch.productDescription=='' &&
    this.ProductItemAdvancesearch.productType==''){
      this.global.alert({ type: 'alert', message:"Please Enter valid Key for Search" });
    }
    else
    {
      this._ngx.show()
    this.localservice.getProductItemLocSearchResult(this.ProductItemAdvancesearch,this.locationId).subscribe(data=>{
   this._ngx.hide();
   this.products.content=data;
    },error=>{
      this._ngx.hide();
      this.global.alert({ type: 'alert', message:"Please Enter valid Key for Search" });
    })
  }
  }
  resetProductItemADVSearch(){
    this.ProductItemAdvancesearch.productItemNumber=''
    this.ProductItemAdvancesearch.partcode=''
    this.ProductItemAdvancesearch.productDescription=''
    this.ProductItemAdvancesearch.productType=''
  }
  getProductType(){
    this.productService.getTypeForAdv().subscribe(data=>{
     this._ngx.hide();
  this.proType=data;
    })
 
  }

}
