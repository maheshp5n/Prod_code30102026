import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { Page } from '../../../services/model';
import { PartcodechangeService } from '../../../services/partcodechange.service';

@Component({
  selector: 'app-approvedlist',
  templateUrl: './approvedlist.component.html',
  styleUrls: ['./approvedlist.component.scss']
})
export class ApprovedlistComponent implements OnInit {

  data:Page<any> =new Page<any>();
  requestTableHeaders:Array<any>=[];
  size:number=20;
  search={
    partcode:'',
    serialnumber:'',
    refid:''
  };
 

  constructor(
    private _partchangeservice: PartcodechangeService,
    private _global: GlobalserviceService,
    private _ngx: NgxSpinnerService,
    private _activatedRoute: ActivatedRoute) {
      this._activatedRoute.queryParams.subscribe(param => {
        if (param['page'] != undefined && param['page'] != null) {
          // this.dropdownSelected.querypram = true;
          this.page = Number(param['page']);
        }
        if (param['size'] != undefined && param['size'] != null) {
          // this.dropdownSelected.querypram = true;
          this.size = Number(param['size']);
        }
        this.getList();
      })
     }

  ngOnInit() {
    this.getList();
    this.requestTableHeaders = [
      {
        id: 1,
        name: 'Ref. ID',
        sortColumnName: 'ownerDetails.firstName',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id: 2,
        name: 'Date',
        sortColumnName: 'accounts.name',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id: 3,
        name: 'Service Appointment',
        sortColumnName: 'oppertunityStage.oppertunityStageName',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id: 4,
        name: 'Circle',
        sortColumnName: '',
        icon: ''
      },
      {
        id: 5,
        name: 'Location',
        sortColumnName: '',
        icon: ''
      },
      {
        id: 6,
        name: 'Legal Entity',
        sortColumnName: '',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id: 7,
        name: 'Consumed Part code',
        sortColumnName: 'ammount',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id: 8,
        name: 'Description',
        sortColumnName: 'createDate',
        icon: 'fa fa-angle-down pull-right'
      },{
        id:9,
        name:'Consumed Serial Number',
        sortColumnName:'',
        icon:''
      },
      {
        id:10,
        name:'Faulty Part code',
        sortColumnName:'',
        icon:''
  
      },
      {
        id:11,
        name:'Description',
        sortColumnName:'',
        icon:''
  
      },
      {id:12,
      name:'Faulty Serial Number',
      sortColumnName:'',
      icon:''
       },{
         id:13,
         name:'Price difference',
         sortColumnName:'',
         icon:''
       },{
         id:14,
         name:' New Part code',
         sortColumnName:'',
         icon:''
       },{
         id:15,
         name:'New Serial Number',
         sortColumnName:'',
         icon:''
       },
       {
        id:16,
        name:'MBLNR',
        sortColumnName:'',
        icon:''
      },
      {
        id:17,
        name:'MBLNR_C',
        sortColumnName:'',
        icon:''
      },
      {
        id:17,
        name:'MBLNR_U',
        sortColumnName:'',
        icon:''
      },
      {
        id:18,
        name:'MJAHR',
        sortColumnName:'',
        icon:''
      },{
        id:19,
        name:'MJAHR_C',
        sortColumnName:'',
        icon:''
  
      },{
        id:20,
        name:'MJAHR_U',
        sortColumnName:'',
        icon:''
      },
      {
        id:21,
        name:'REMARK_C',
        sortColumnName:'',
        icon:''
  
      },{
        id:22,
        name:'REMARK_U',
        sortColumnName:'',
        icon:''
      }
  
     
    ];
  }

  getList() {

    this._ngx.show();
  const size=5;
  this.data.content=[];
    this._partchangeservice.getApprovedListPageable(this.page,this.size).subscribe(data => {
      this._ngx.hide();
      this.data = data;
    }, error => {
      this._ngx.hide();
      this._global.alert({ type: 'alert', message: error.error.message });
    });
  }

  getSearchResult(){
    this._ngx.show();
    this.data.content=[];
    this._partchangeservice.searchApproved(this.search).subscribe(data=>{
      this._ngx.hide();
      this.data.content=data;
    },error=>{
      this._ngx.hide();
      this._global.alert({type:'alert',message:error.error.message});
    });
  }
  reset(){
    this.search.partcode=''
    this.search.serialnumber='',
    this.search.refid=''
  }
  page:number=0;
  pagenumber:any=null;
  jumpPage(pagenumber){
  if(this.data.totalPages< pagenumber){
    // this.message="Enter Valid input";
    this._global.alert({type:'alert',message:"Enter Valid input"});
  }
else{
    this.page=pagenumber-1;
    this.getList();
}
}
}
