import { CustomerInventoryServices } from '../../services/customerinventory.service';
import { Page } from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../services/login.services';
import { GlobalserviceService } from '../../globalservice.service';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { DatepickerDateCustomClasses } from 'ngx-bootstrap/datepicker';
import * as fileSaver from 'file-saver'

@Component({
  selector: 'app-customerinventory',
  templateUrl: './customerinventory.component.html',
  styleUrls: ['./customerinventory.component.scss']
})
export class CustomerinventoryComponent implements OnInit {
 
  searchitem: string;
  dropdownsList = [{
    sqId: 1,
    name: 'All',
    clickEventParam: 'all',
    class: 'dropdown-item dropdown-item-checked'
  }];
  dropdownSelected = {
    name: 'All',
    filtreparam: 'all',
    sortname: 'id',
    sortorder: 'desc',
    page:0
  };
  requestTableHeaders = [
    {
      id: 1,
      name: 'S.No',
      sortColumnName: '',
      icon: ''

    },
    {
      id: 2,
      name: 'Customer',
      sortColumnName: 'acc.name',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 14,
      name: 'Product Serial Number',
      sortColumnName: 'serial_number',
      icon:'fa fa-angle-down pull-right'
    },
    {
      id: 7,
      name: 'Product Part code',
      sortColumnName: 'product_product_part_code',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 8,
      name: 'Product Description',
      sortColumnName: 'prd.pdoduct_description',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 9,
      name: 'Product Type',
      sortColumnName: 'prd.product_type',
      icon:'fa fa-angle-down pull-right'
    },
    {
      id: 1,
      name: 'Circle',
      sortColumnName: 'circle_circle_name',
      icon: 'fa fa-angle-down pull-right'
    },

    {
      id: 3,
      name: 'Challan Date',
      sortColumnName: 'customer_challan_date',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 4,
      name: 'Challan Number',
      sortColumnName: 'customer_challan_number',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 5,
      name: 'Docket Number',
      sortColumnName: 'customer_docket_number',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 6,
      name: 'Product State',
      sortColumnName: 'instate.product_state_name',
      icon: 'fa fa-angle-down pull-right'
    },

    {
      id: 10,
      name: 'Receive Date',
      sortColumnName: 'receive_date',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 11,
      name: 'Remarks',
      sortColumnName: 'redmrks',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id:12,
      name:'Lot No',
      sortColumnName:'lot_no',
      icon:'fa fa-angle-down pull-right'
    },
    {
      id: 13,
      name: 'Returned',
      sortColumnName: 'returned',
      icon:'fa fa-angle-down pull-right'
    },
    {
      id: 14,
      name: 'Return Date',
      sortColumnName: 'return_date',
      icon: 'fa fa-angle-down pull-right'
    },

    {
      id: 15,
      name: 'Warranty Status',
      sortColumnName: 'warranty_status',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 16,
      name: 'Dispatch Challan Number',
      sortColumnName: 'custout.dispatch_challan_number',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 17,
      name: 'Dispatch Date',
      sortColumnName: 'custout.dispatch_date',
      icon:'fa fa-angle-down pull-right'
    },
    {
      id: 18,
      name: 'Dispatch Docket Number',
      sortColumnName: 'custout.dispatch_docket_number',
      icon: 'fa fa-angle-down pull-right'
    },
    {
      id: 19,
      name: 'Dispatch Product Status',
      sortColumnName: 'custout.previous_state_product_state_id',
      icon: 'fa fa-angle-down pull-right'
    }
  ];

  @ViewChild('inputfile')inputfile:ElementRef;
  @ViewChild('outputFile')outputfile:ElementRef;

  pagedata: Page<any> = new Page<any>();

  constructor(private _customerInventory: CustomerInventoryServices,
    private spinner: NgxSpinnerService,
    private loginservice: Loginservices,
    private _global: GlobalserviceService,
  ) { }
    resetFileValue(){
      this.inputfile.nativeElement.value='';
      this.outputfile.nativeElement.value='';
    }
  ngOnInit() {
    this.pagedata.content=[];
    this.getCustomerInventory(`filtername=${this.dropdownSelected.filtreparam}&page=0&size=20&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}`);
  }
  rights(object, right: string): boolean {
    return this.loginservice.isUserValidFor(object, right);
  }

  getCustomerInventory(filter?) {
    this.spinner.show();
    this.pagedata.content=[];
    this._customerInventory.getCustomerInventory(filter).subscribe(data => {
      this.spinner.hide();
      this.pagedata = data;
      this.serialNumber = (((this.dropdownSelected.page) * this.size));
    }, error => this.spinner.hide());
  }

  dropdownChange(sqId) {
    this.dropdownsList.forEach(item => {
      if (item.sqId == sqId) {
        item.class = 'dropdown-item dropdown-item-checked';
        this.dropdownSelected.name = item.name;
        this.dropdownSelected.filtreparam = item.clickEventParam;
        this.getCustomerInventory(`filtername=${this.dropdownSelected.filtreparam}&page=0&size=20&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}`);
      } else {
        item.class = 'dropdown-item';
      }
    });
  }
  getSortOrder(tblId) {
    this.requestTableHeaders.forEach((tbl) => {
      if (tbl.id == tblId) {
        this.dropdownSelected.sortname = tbl.sortColumnName;
        this.dropdownSelected.sortorder = this.dropdownSelected.sortorder == 'desc' ? 'asc' : 'desc';
        tbl.icon = this.dropdownSelected.sortorder == 'desc' ? 'fa fa-caret-down pull-right' : 'fa fa-caret-up pull-right';
        this.getCustomerInventory(`filtername=${this.dropdownSelected.filtreparam}&page=0&size=20&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}`);
      } else {
        tbl.icon = '';
      }
    });
  }
  goToNext(pageNumber) {
    this.getCustomerInventory(`filtername=${this.dropdownSelected.filtreparam}&page=${pageNumber + 1}&size=20&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}`);
  }
  goToPrevious(pageNumber) {
    this.getCustomerInventory(`filtername=${this.dropdownSelected.filtreparam}&page=${pageNumber - 1}&size=20&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}`);
  }
  customerInventoryINUpload($event) {
    const file = $event.target.files[0];
    const fd = new FormData;
    fd.append('file', file, file.name);
    this.spinner.show();
    this._customerInventory.postCustomerInventoryIn(fd).subscribe(data => {
      this.spinner.hide();
      this.resetFileValue();
      this.getCustomerInventory(`filtername=${this.dropdownSelected.filtreparam}&page=0&size=20&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}`);
      this.downLoadFile(data);
    }, error => {
      this.spinner.hide();
      this.resetFileValue();
      this._global.alert({ type: 'alert', message: error.message });
    });

  }
  downLoadFile(data: any) {
    let blob = new Blob([data], { type: data.type });
    let url = window.URL.createObjectURL(blob);
    let pwa = window.open(url);
    if (!pwa || pwa.closed || typeof pwa.closed == 'undefined') {
      alert('Please disable your Pop-up blocker and try again.');
    }
  }

  customerInventoryOUt($event) {
    const file = $event.target.files[0];
    const fd = new FormData;
    fd.append('file', file, file.name);
    this.spinner.show();
    this._customerInventory.postOutInventory(fd).subscribe(data => {
      this.spinner.hide();
      this.resetFileValue();
      this.getCustomerInventory(`filtername=${this.dropdownSelected.filtreparam}&page=0&size=20&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}`);
      this.downLoadFile(data);
    }, error => {
      this.spinner.hide();
      this.resetFileValue();
      this._global.alert({ type: 'alert', message: error.message });
    });

  }

  templateDownload(type) {
    console.log(type);
    
    this._customerInventory.getTempalte(type).subscribe(data => {
      var file = new Blob([data], {
        type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
      });
      if(type=='in'){
      fileSaver.saveAs(file, "customer_Inventory_in" + ".xlsx");
    }else{
      fileSaver.saveAs(file, "customer_Inventory_out" + ".xlsx");
    }
      // this.downLoadFile(data);
    });
  }
  contactSearch={
    partCode:'',
    serialNumber:'',
    customerName:'',
    circle_name:'',
    contact_type:'',
    remarks:'',
    lotNo:''
  };
  advanceSearch(){
  
    if(this.contactSearch.circle_name=='' && this.contactSearch.serialNumber==''
     && this.contactSearch.partCode=='' &&
    this.contactSearch.customerName=='' && this.contactSearch.contact_type==''
     && this.contactSearch.remarks== '' && this.contactSearch.lotNo==''){
      this._global.alert({ type: 'alert', message: "Please Enter valid Input to Search"});
    }
 
    else {
      this.spinner.show();
      this.pagedata.content=[];
      this._customerInventory.getAdvanceSearch(this.contactSearch).subscribe(data=>{
        this.spinner.hide();
        this.pagedata.content = data;
      },error=>{
        this.spinner.hide();
        this._global.alert({ type: 'alert', message: error.error.message});
 
      });
    }

  }
  serialNumber:number;
  size:number=20;
  pagenumber:any=null;
  jumpPage(pagenumber){
  if(this.pagedata.totalPages< pagenumber){
   this._global.alert({type:'alert',message:"Enter Valid input"});
  }
else{
    this.dropdownSelected.page=pagenumber-1;
    this.getCustomerInventory(`filtername=${this.dropdownSelected.filtreparam}&page=${this.dropdownSelected.page}&size=${this.size}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}`);

    this.serialNumber = (((this.dropdownSelected.page) * this.size));
  }
}

}
