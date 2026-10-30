import { CounterSerialNo, FaultyCounter, selected } from './../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from './../../globalservice.service';
import { ServiceOrderService } from './../../services/serviceorder.servvices';
import { Component, OnInit, Output, EventEmitter, ViewChild } from '@angular/core';
import { create } from 'domain';
import { ModalDirective } from 'ngx-bootstrap';

@Component({
  selector: 'app-serviceorderdirective',
  templateUrl: './serviceorderdirective.component.html',
  styleUrls: ['./serviceorderdirective.component.scss']
})
export class ServiceorderdirectiveComponent implements OnInit {

  faultyProduct: Array<any> = [];
  repariCenters: Array<any> = [];
  faultyCounter:Array<FaultyCounter> = [];
  counterSerialNo:Array<CounterSerialNo>=  [];
  serviceOrder: any = {};
  isfaultydata: boolean = false;
  isnotFaultydata: boolean = false;
  globalcheck: boolean = false;
  @Output() created: EventEmitter<any> = new EventEmitter();
  @Output() cancled: EventEmitter<any> = new EventEmitter();
  @ViewChild('viewTotalCountModel', { static: false }) viewTotalCountModel: ModalDirective;
  requestTableHeaders = [
    {
      id: 1,
      name: 'Serial Number'
      // sortColumnName: 'name',
      // icon: 'fa fa-angle-down pull-right'
    },

    {
      id: 3,
      name: 'Part code',
      // sortColumnName: 'parentCustomer.customerName',
      // icon: ''
    },
    {
      id: 4,
      name: 'Description'
      // sortColumnName: 'billingInfo.state.stateName',
      // icon: ''
    },

    {
      id: 6,
      name: 'Status',
      // sortColumnName: 'accountOwnerDetails.firstName',
      // icon: ''
    },
    {
      id: 2,
      name: 'Lot No.'
      // sortColumnName: 'sapCustomerNumber',
      // icon: ''
    },
    {
      id: 5,
      name: 'Remark'
      // sortColumnName: 'phoneNumber',
      // icon: ''
    },
    {
      id: 7,
      name: 'Modified Date',
      // sortColumnName: 'createdDate',
      // icon: ''
    },
    {
      id: 8,
      name: 'Customer Inventory'
    }

  ];
 

  constructor(
    private _serviceOrderService: ServiceOrderService,
    private _global: GlobalserviceService,
    private _ngx: NgxSpinnerService) { }

  ngOnInit() {
    this.serviceOrder.repairCenter = 0;
    this.serviceOrder.shippingMode = '0';
    this.getFaultyProducts();
    this.getRepariCenter();
  }

  getRepariCenter() {
    this._serviceOrderService.getRepariCenter().subscribe(data => this.repariCenters = data);
  }
  getFaultyProducts() {
    this._ngx.show();
    this._serviceOrderService.getFaultyDevice().subscribe(data => {
      this.faultyProduct = data;
      this._ngx.hide();
    }, error => {
      this._ngx.hide();
      this._global.alert({ type: 'alert', message: error.error.message });
    })
  }

  createServiceOrder() {

    this.serviceOrder.items = [];

    const counter = this.faultyProduct.length;
    for (let i = 0; i < counter; i++) {
      if (this.faultyProduct[i].selected) {
        this.serviceOrder.items.push(this.faultyProduct[i].lineItemId.toString());
      }
    }

    // VALIDATION
    if (this.serviceOrder.repairCenter == undefined || this.serviceOrder.repairCenter == null || this.serviceOrder.repairCenter == '') {
      this._global.alert({ type: 'alert', message: 'Please select Repari center' });
      return;
    }
    if (this.serviceOrder.items.length == 0) {
      this._global.alert({ type: 'alert', message: 'Item item list not found' });
      return;
    }
    if (this.serviceOrder.shippingMode == '0') {
      this._global.alert({ type: 'alert', message: 'Please select shipping mode' });
      return;
    }
    if (this.serviceOrder.shipToName == undefined || this.serviceOrder.shipToName == null || this.serviceOrder.shipToName == '') {
      this._global.alert({ type: 'alert', message: 'Please provide shipping to name' });
      return;
    }
    // VALIDATION END
    this._ngx.show();
    this._serviceOrderService.saveWorkOrder(this.serviceOrder).subscribe(data => {
      this._ngx.hide();
      this.created.emit(data);
    }, error => {
      this._ngx.hide();
      this._global.alert({ type: 'alert', message: error.error.message });
    });
  }
  doCancel() {
    this.cancled.emit();
  }
  chckGlobalCheck() {
    const counter = this.faultyProduct.length;
    for (let i = 0; i < counter; i++) {
      this.faultyProduct[i].selected = this.globalcheck;
      // this.countfaultyProduct(i);
    }
    
  }
  faultySearch = {
    inhouseInventory: '',
    lotNo: '',
    customerInventory: '',
    partCode:''
  };
  getAdvanceSearchResult() {
    if (this.faultySearch.inhouseInventory == '' &&
      this.faultySearch.lotNo == '' &&
      this.faultySearch.customerInventory == '' && 
      this.faultySearch.partCode == '') {
      this._global.alert({ type: 'alert', message: "Enter Valid input" });
    }

    else {
      this._ngx.show();
      this._serviceOrderService.getSearchResultforFaultyProd(this.faultySearch).subscribe(data => {
        this.faultyProduct = data;
        this._ngx.hide();
      }, error => {
        this._ngx.hide();
        this._global.alert({ type: 'alert', message: error.error.message });
      })
    }
  }
  totalSerialCount:number=1;
  isDataNull:boolean=false;
  newUniqueList:any;
  totalitemCount:number=0;
  countfaultyProduct(index:number){
    this.totalitemCount=0;
     this.faultyCounter=[];
    for(let i=0;i<this.faultyProduct.length;i++)
    {   
      if(this.faultyProduct[i].selected)
      {
            this.faultyCounter.push({srNo:index,faultypartCode:this.faultyProduct[i].partCode,faultySerialNo:this.faultyProduct[i].itemSerialNumber,totalSerialCount:this.totalSerialCount});  
            this.totalitemCount++;
            // this.newUniqueList = this.faultyCounter;
      }
  }
  this.newUniqueList =  this.faultyCounter.filter((v,i) => this.faultyCounter.findIndex(item => item.faultypartCode == v.faultypartCode) === i);
  // this.faultyCounter = newFormulalist;
  console.log(this.newUniqueList);
  this.viewTotalCount();
}

  faltyserialno:string;
  viewTotalCount(){
    
    let serialNoCount=0;
    let serialNumber;
    let partCode;
    let falultyCounting =this.faultyCounter; 
    for(let i = 0;i<this.newUniqueList.length;i++)
    {
     
      serialNoCount = 0;
      serialNumber = this.newUniqueList[i].faultySerialNo;
      partCode = this.newUniqueList[i].faultypartCode;
      console.log(partCode);
      for(let j = 0;j<falultyCounting.length;j++)
      {
        if(this.faultyCounter[j].faultypartCode == partCode)
        {
          console.log(this.faultyCounter[j].faultypartCode);
          serialNoCount  = serialNoCount+1;
        }
      }
      let  objIndex = this.faultyCounter.findIndex((obj => obj.faultypartCode == partCode));
      console.log("index is "+objIndex);
      this.faultyCounter[objIndex].totalSerialCount = serialNoCount;
      console.log(this.faultyCounter);
    }
    // this.viewTotalCountModel.show();
  }
  resetsearch() {
    this.faultySearch.inhouseInventory = ''
    this.faultySearch.lotNo = ''
    this.faultySearch.customerInventory = ''
    this.faultySearch.partCode = ''
  }
}
