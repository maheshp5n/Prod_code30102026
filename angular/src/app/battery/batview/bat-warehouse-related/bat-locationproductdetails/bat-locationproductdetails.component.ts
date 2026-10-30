import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../../globalservice.service';
import { LocationPartCodeSerialNumber, ProductType } from '../../../../services/model';
import { ProductsService } from '../../../../services/products.services';
import { BtwarehouseService } from '../../../bat_services/btwarehouse.service';

@Component({
  selector: 'app-bat-locationproductdetails',
  templateUrl: './bat-locationproductdetails.component.html',
  styleUrls: ['./bat-locationproductdetails.component.css']
})
export class BatLocationproductdetailsComponent implements OnInit {

  queryparam = {
    page: 0,
    size: 20,
    sort: 'desc',
    sortc: 'product_id',
    clm: 1
  };
  locationId;
  products;
  location;
  tableHeader = [{
    name: 'S.No.',
    sortc: 'product_id'
  },
  {
    name: 'Product Item',
    sortc: 'product_id'
  },
  {
    name: 'Part Code',
    sortc: 'btp.manual_id'
  },
  {
    name: 'Description',
    sortc: 'btp.product_description'
  },
  {
    name: 'Type',
    sortc: 'btp.product_type'
  },
  {
    name: 'Customer Total Quantity',
    sortc: 'customer_total_quantity'
  },
  {
    name: 'Customer Total Ok Quantity',
    sortc: 'customer_total_ok_quantity'
  },
  {
    name: 'Customer Total Faulty Quantity',
    sortc: 'customer_total_faulty_quantity'
  },
  {
    name: 'Exicom Total Quantity',
    sortc: 'exicom_total_quantity'
  },
  {
    name: 'Exicom Total Ok Quantity',
    sortc: 'exicom_total_ok_quantity'
  },
  {
    name: 'Exicom Total Faulty Quantity',
    sortc: 'exicom_total_faulty_quantity'
  },
  {
    name: 'Location Name',
    sortc: 'loc.location_name'
  },
  {
    name: 'Location Plant',
    sortc: 'loc.plant'
  }

  ];
  ProductItemAdvancesearch = {
    locationName:'',
    locationPlant:'',
    productPartcode:'',
    productDescription:'',
    productType:''
  
  };
  proType:Array<ProductType>=[]
  constructor(

    private _activatedRouter: ActivatedRoute,
    private localservice: BtwarehouseService,
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
    if(this.ProductItemAdvancesearch.locationName== ''&&
    this.ProductItemAdvancesearch.locationPlant== '' &&
    this.ProductItemAdvancesearch.productDescription=='' &&
    this.ProductItemAdvancesearch.productPartcode=='' &&
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
      this.global.alert({ type: 'alert', message:"Please Enter valid Key For Search" });
    })
  }
  }
  resetProductItemADVSearch(){
    this.ProductItemAdvancesearch.locationName=''
    this.ProductItemAdvancesearch.locationPlant=''
    this.ProductItemAdvancesearch.productDescription=''
    this.ProductItemAdvancesearch.productType=''
    this.ProductItemAdvancesearch.productPartcode=''
  }
  getProductType(){
    this.productService.getTypeForAdv().subscribe(data=>{
     this._ngx.hide();
  this.proType=data;
    })
 
  }

}
