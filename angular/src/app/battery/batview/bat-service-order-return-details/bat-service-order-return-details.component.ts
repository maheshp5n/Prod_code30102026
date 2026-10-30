import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { BtServiceOrderReturnService } from '../../bat_services/bt-service-order-return.service';
import { Page } from '../../bt_model';

@Component({
  selector: 'app-bat-service-order-return-details',
  templateUrl: './bat-service-order-return-details.component.html',
  styleUrls: ['./bat-service-order-return-details.component.scss']
})
export class BatServiceOrderReturnDetailsComponent implements OnInit {

  soreturndetails;
  serviceorderreturnid;

  
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


  pagenumber = 1;
  pageinfo = {
    page: 0,
    size: 8,
    order: 'id',
    orderBy: 'asc',
    filterparam:'',
    filtername:'All',
    sqId:1,
    querypram:false
  };

  tableHeader = [{
    id: 1,
    titleName: 'So Return Item Number',
    orderby: 'id',
    sort: 'asc',
    icon: 'fa fa-angle-double-up',
    propertiesBinding: 'returnManualId',
   
  },{
    id:2,
    titleName: 'Serial Number',
    orderby: 'iitem.`serial_number`',
    sort: '',
    icon: '',
    propertiesBinding: 'itemSerialNumber',
   
  },{
    id:3,
    titleName: 'Part code',
    orderby: 'prd.`manual_id`',
    sort: '',
    icon: '',
    type:'link',
    linkurl:'bat_product',
    linkid:'productId',
    propertiesBinding: 'productPartcode'
  },{
    id:4,
    titleName: 'Produc Type',
    orderby: 'prd.`product_type`',
    sort: '',
    icon: '',
    propertiesBinding: 'productType'
  },
  {
    id:5,
    titleName: 'Description',
    orderby: 'prd.`product_description`',
    sort: '',
    icon: '',
    propertiesBinding: 'productDescription'
  },
  {
    id:5,
    titleName: 'Received',
    orderby: 'received',
    sort: '',
    icon: '',
    propertiesBinding: 'returnIsReceived',
    type:'bool'
  },
  {
    id:7,
    titleName: 'Is Customer Inventory',
    orderby: 'item.`customer_inventory`',
    sort: '',
    icon: '',
    propertiesBinding: 'isCustomerInventory',
    type:'bool'
  },{
    id:8,
    titleName: 'Return Date',
    orderby: 'item.`created_date`',
    sort: '',
    icon: '',
    propertiesBinding: 'returnCreatedDate',
    type:'date',
    dateformat:'dd-MMM-yyyy H:mm a'
  }];

  detailssigment=[
    {
    sigId:1,
    name:'Shipping Details',
    expended:true,
    icon:'fa fa-angle-down',
    itemstodisplay:[{
      name:'Shipping To',
      propertieBinding:'sorShippingTo',
      type:'text'
    },
    {
      name:'Shipping Mode',
      propertieBinding:'sorShippingMode',
      type:'text'
    },
    {
      name:'Tracking Number',
      propertieBinding:'sorTrackingNumber',
      type:'text'
    },{
      name:'Shipping Date',
      propertieBinding:'sorCreatedDate',
      type:'date',
      dateFormat:'dd-MMM-yy h:mm a'
    }]
  },
  {
    sigId:2,
    name:'System Information',
    expended:false,
    icon:'fa fa-angle-right',
    itemstodisplay:[{
      name:'Created By',
      propertieBinding:'',
      type:'merge_link',
      delimeter:' ',
      link:'createuser',
      linkid:'sorCreatedByEmailId',
      mergeproperty:[{binding:'createdByfirstName'},{binding:'createdBymiddleName'},{binding:'createdBylastName'},{binding:'sorCreatedDate',type:'date',dateFormat:'dd MMM yy h:mm a'}]
    },{
      name:'Modified By',
      propertieBinding:'',
      type:'merge_link',
      delimeter:' ',
      link:'createuser',
      linkid:'sorLastModifiedByEmailId',
      mergeproperty:[{binding:'lastModifiedByfirstName'},{binding:'lastModifiedBymiddleName'},{binding:'lastModifiedBylastName'},{binding:'lastModifiedBylastName'},{binding:'sorLastModifiedDate',type:'date',dateFormat:'dd MMM yy h:mm a'}]
    }]
  }

];

  items=new Page();
  constructor(private _global: GlobalserviceService, private _btsoreturnService: BtServiceOrderReturnService, private _activeRoute: ActivatedRoute,
    private _spinner: NgxSpinnerService,) {
      this._activeRoute.params.subscribe(data=>{
        this.serviceorderreturnid=Number(data['id']);
        this.getServiceOrderReturnDetails(this.serviceorderreturnid);
        this.getItems(this.serviceorderreturnid);
      });

      this._activeRoute.queryParams.subscribe(v => {
        const _view = v['view'];
        if (_view === 'details' || _view === undefined || _view === null || _view === '') {
          this.subTabChange('details');
        }
        if (_view === 'related') {
          this.subTabChange('related');
        }
      });
  }


  ngOnInit() {
  }

  getServiceOrderReturnDetails(id){
    this._spinner.show();
    this._btsoreturnService.getSingle(id).subscribe(data=>{
      try { 
        this.soreturndetails=data;
      } catch (error) {
        
      }
      this._spinner.hide();
    },error=>this._spinner.hide());
  }

  getItems(id){
    this._spinner.show();
    this._btsoreturnService.getItems(id,`page=${this.pageinfo.page}&size=${this.pageinfo.size}&sort=${this.pageinfo.order},${this.pageinfo.orderBy}`).subscribe(data=>{
      try {
        this.items=data;  
      } catch (error) {
        
      }
      this._spinner.hide();
    },error=>this._spinner.hide());
  }

  subTabChange(tabname) {
    if (tabname === 'details') {
      this.navigationMenue.details.ngclass = 'active';
      this.navigationMenue.releted.ngclass = '';
      this.navigationMenue.details.view = true;
      this.navigationMenue.releted.view = false;
      this.getServiceOrderReturnDetails(this.serviceorderreturnid);
    } else {
      this.navigationMenue.details.ngclass = '';
      this.navigationMenue.releted.ngclass = 'active';
      this.navigationMenue.details.view = false;
      this.navigationMenue.releted.view = true;
      this.getItems(this.serviceorderreturnid);
    }
  }

  changeOrder(item) {
    const lt = this.tableHeader.length;
    for (let i = 0; i < lt; i++) {
      if (this.tableHeader[i].id == item.id) {

        this.pageinfo.order = this.tableHeader[i].orderby;
        this.tableHeader[i].sort = this.tableHeader[i].sort === 'asc' ? 'desc' : 'asc';
        this.tableHeader[i].icon = this.tableHeader[i].sort === 'asc' ? 'fa fa-angle-double-up' : 'fa fa-angle-double-down';
        this.pageinfo.order = this.tableHeader[i].sort;

      }
      else {
        this.tableHeader[i].sort = '';
        this.tableHeader[i].icon = '';
      }
    }
    this.getItems(this.serviceorderreturnid);
  }
  LimteExceed: boolean;
  onlyNumbers(key): boolean {
    const charCode = (key.which) ? key.which : key.keyCode;

    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    else if (this.LimteExceed) {
      return false;
    }
    return true;

  }

  // pagenumber : any;
  jumpPage(pagenumber) {
    if (pagenumber == null || pagenumber == 0 || pagenumber == '') {
      this._global.alert({ type: 'alert', message: 'Please Fill A Valid Input' });

    }
    else if (this.items.totalPages < pagenumber) {
      this._global.alert({ type: 'alert', message: "Page Count Should Be Less Than Total Page Count" });
    }
    else {
      this.pageinfo.page = pagenumber - 1;
      this.getItems(this.serviceorderreturnid);
    }

  }

  changeButtonDisplay(sigment){
    const lent=this.detailssigment.length;
    for(let i=0;i<lent;i++){
      if(sigment.sigId==this.detailssigment[i].sigId){
        this.detailssigment[i].expended=!this.detailssigment[i].expended;
        if(this.detailssigment[i].expended){
          this.detailssigment[i].icon='fa fa-angle-down';
        }
        else{
          this.detailssigment[i].icon='fa fa-angle-right';
        }
        break;
      }
    }
  }

}
