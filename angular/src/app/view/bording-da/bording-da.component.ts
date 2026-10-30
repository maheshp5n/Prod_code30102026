import { Component, OnInit, ViewChild } from '@angular/core';
import { BoardingLoading, Page, Login, BoardingLoadingDetailsResponse } from '../../services/model';
import { ModalDirective } from 'ngx-bootstrap';
import { Loginservices } from '../../services/login.services';
import { OrderPipe } from 'ngx-order-pipe';
import { BoardingLoadingService } from '../../services/boarding-loading.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute } from '@angular/router';
import { PagefilterService } from '../../services/pagefilterservice';

@Component({
  selector: 'app-bording-da',
  templateUrl: './bording-da.component.html',
  styleUrls: ['./bording-da.component.scss']
})
export class BordingDaComponent implements OnInit {
  @ViewChild('createBoardingModel',{static:false}) createBoardingModel: ModalDirective;
  @ViewChild('popupSuccessModel',{static:false}) popupSuccessModel:ModalDirective;
res :BoardingLoadingDetailsResponse= new BoardingLoadingDetailsResponse()
message:string="";
pagenumber:any=null;
pindtselection = false;
  recentlyView: boolean = true;
  isSelected = false;
  dataStyle: string;
  // ngClassAll: string;
  All: boolean = true;
  table: boolean = true;
  ngClassKanban: string;
  ngClassTable: string = 'fa fa-check';
  modalMsg:string;
  ngClassAll: string = 'dropdown-item dropdown-item-checked';
  inboundClick: boolean = true;
  showHide: boolean = false;
  showFilter: boolean = false;
  isEditMode:boolean=false;
  heading : string;
  edit:boolean=false;
  CREATE_ACCOUNT_HEADING:string= 'Create Boarding/Lodging & DA';
  EDIT_ACCOUNT_HEADING:string= 'Edit Boarding/Lodging & DA';
  
  inputrow: boolean = false;
  legalentityId:number;
  _login: Login = new Login();
  values: string;
  boadingData:Page<BoardingLoadingDetailsResponse>= new Page<BoardingLoadingDetailsResponse>();
  // req: LegalEntityResponse = new LegalEntityResponse();
  // res: LegalEntityResponse = new LegalEntityResponse();
  contactPage: Page<BoardingLoading> = new Page<BoardingLoading>();
  page: number = 0;
  searchitem:string
  totalPages:any;
  first: boolean;
  last: boolean;
  serialNumber: number;
  size:number;
  loginData:any;
  order: string = 'legalENtityName';
  reverse: boolean = false;
  boardingName:string;
  i: number;
  _cFlag:string;
      //filter
      dropdownsList = [{
        sqId: 1,
        name: 'All',
        clickEventParam: 'all',
        class: 'dropdown-item dropdown-item-checked',
        pined: false
      },
      {
        sqId: 2,
        name: 'Recently Viewed',
        clickEventParam: 'recentvisit',
        class: 'dropdown-item',
        pined: false
      },
      
     
     ];
    
    
      dropdownSelected = {
        sqId: 1,
        name: 'All',
        filtreparam: 'all',
        sortname: 'createdDate',
        sortorder: 'desc',
        page: 0,
        size: 20,
        querypram: false
      };
      requestTableHeaders = [
        {
          id: 1,
          name: 'Name',
          sortColumnName: 'boardingName',
          icon: 'fa fa-angle-down pull-right'
        },
        {
          id: 2,
          name: 'Created By',
          sortColumnName: 'createdBy.firstName',
          icon: 'fa fa-angle-down pull-right'
        },
        {
          id: 3,
          name: 'Created Date',
          sortColumnName: 'createdDate',
          icon: 'fa fa-angle-down pull-right'
        },
       
      ];
    
      //done
  constructor(private pagefilterservice:PagefilterService,private loginservice:Loginservices,private bordingservice:BoardingLoadingService,private spinner:NgxSpinnerService, private orderPipe: OrderPipe, private _activatedRoute: ActivatedRoute){
    this._activatedRoute.params.subscribe(pram => {
      this.boardingName = pram['boardingName'];
    
    });
    this._activatedRoute.queryParams.subscribe(param => {
      this._cFlag = param['_c'];
      if (param['sqId'] != undefined && param['sqId'] != null) {
        this.dropdownSelected.querypram = true;
        this.dropdownSelected.sqId = Number(param['sqId']);

      }
      if (param['name'] != undefined && param['name'] != null) {
        this.dropdownSelected.querypram = true;
        this.dropdownSelected.name = param['name'];
      }
      if (param['filtreparam'] != undefined && param['filtreparam'] != null) {
        this.dropdownSelected.querypram = true;
        this.dropdownSelected.filtreparam = param['filtreparam'];
      }
      if (param['sortname'] != undefined && param['sortname'] != null) {
        this.dropdownSelected.querypram = true;
        this.dropdownSelected.sortname = param['sortname'];
      }
      if (param['sortorder'] != undefined && param['sortorder'] != null) {
        this.dropdownSelected.querypram = true;
        this.dropdownSelected.sortorder = param['sortorder'];
      }
      if (param['page'] != undefined && param['page'] != null) {
        this.dropdownSelected.querypram = true;
        this.dropdownSelected.page = Number(param['page']);
      }
      if (param['size'] != undefined && param['size'] != null) {
        this.dropdownSelected.querypram = true;
        this.dropdownSelected.size = Number(param['size']);
      }
      this.getFilterList();

    });
    this.boadingData = orderPipe.transform(this.boadingData, this.order);
   }
   rights(object,right:string):boolean{
    return this.loginservice.isUserValidFor(object,right);
  }
  dropdownChange(sqId) {
    this.dropdownsList.forEach(item => {
      if (item.sqId == sqId) {
        item.class = 'dropdown-item dropdown-item-checked';
        this.dropdownSelected.sqId = sqId;
        this.dropdownSelected.name = item.name;
        this.dropdownSelected.filtreparam = item.clickEventParam;
        this.dropdownSelected.page = 0;
        this.getBoadingtable();
        if (item.pined) {
          this.pindtselection = true;
        } else {
          this.pindtselection = false;
        }
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
        tbl.icon = this.dropdownSelected.sortorder == 'desc' ? 'fa fa-angle-down pull-right' : 'fa fa-angle-up pull-right';
        this.dropdownSelected.page = 0;
        this.getBoadingtable();
      } else {
        tbl.icon = '';
      }
    });
  }
  showCreateRequestView(){
    // this.req=new LegalEntityResponse();
    this.isEditMode = false;
    this.heading =this.CREATE_ACCOUNT_HEADING;
    this.createBoardingModel.show();
    } 
  cancelData() {
    this.res=new BoardingLoadingDetailsResponse();
    this.isEditMode=false;
     this.createBoardingModel.hide();
  
     }
 

  ngOnInit() {
  }
 
  getBoadingtable(){
    this.spinner.show()
    this.boadingData.content=[];
    this.bordingservice.geBoadingTable(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data=>{
      this.spinner.hide()
      this.boadingData=data
      this.totalPages = data.totalPages;
      this.first = data.first;
      this.last = data.last;
      this.size = data.size;
     this.serialNumber = ((this.page) * this.size);
      
      
    },error=>{
      this.spinner.hide()
      this.message=error.error.message;
      this.popupSuccessModel.show()
    })
  }
  editBoadingshow(res:BoardingLoadingDetailsResponse){
    this.res=res
    this.isEditMode=true;
    this.heading=this.EDIT_ACCOUNT_HEADING
    this.bordingservice.getDetailByboadingName(this.res.boardingName).subscribe(data=>{
      this.spinner.hide();
      this.res=data;
    },error=>{
      this.spinner.hide();
      this.message=error.error.message;
      this.popupSuccessModel.show();
    });
    this.createBoardingModel.show()
    

  }

  onBoadingCreated($event){
      
    if(!this.isEditMode){
      this.boadingData.content.unshift($event);
    }
    else{
      let index = 0;
      this.boadingData.content.forEach(element => {
       
        if(element.boardingName == $event.boardingName){
          this.boadingData[index]=$event;
        }else{
          index++;
        }
      });
    }
  }
 
  getFilterList() {
    this.pagefilterservice.getPagefilter('Bording_lodging_DA').subscribe(data =>{
      this.dropdownsList = [];
      let dfound = false;
      data.forEach(d => {
        let cls = 'dropdown-item';
        if (d.pined != null) {
          dfound = true;
          if (this.dropdownSelected.querypram == false) {
            cls = cls + ' dropdown-item-checked';
            this.dropdownSelected.name = d.filterName;
            this.dropdownSelected.filtreparam = d.filterParam;
            this.dropdownSelected.sqId = d.userSummaryPageFilterId;
            this.pindtselection = true;
            this.dropdownSelected.page = 0;
          }

          else {
            if (d.sqId == this.dropdownSelected.sqId) {
              this.pindtselection = true;
            }
          }

        }
        this.dropdownsList.push({
          sqId: d.userSummaryPageFilterId,
          name: d.filterName,
          clickEventParam: d.filterParam,
          class: cls,
          pined: d.pined != null ? true : false
        });
      });
      if (!dfound && this.dropdownsList.length > 0 && this.dropdownSelected.querypram == false) {
        this.dropdownSelected.name = this.dropdownsList[0].name;
        this.dropdownSelected.filtreparam = this.dropdownsList[0].clickEventParam;
        this.dropdownsList[0].class = 'dropdown-item dropdown-item-checked';
        this.dropdownSelected.sqId = this.dropdownsList[0].sqId;
        this.pindtselection = true;
        this.dropdownSelected.page = 0;
      }

      this.getBoadingtable();
    });
  }
  pinFilter() {
    this.spinner.show();
    this.pagefilterservice.pinExisting(this.dropdownSelected.sqId).subscribe(data => {
      this.pindtselection = true;
      this.dropdownsList.forEach(d => {
        if (d.sqId == this.dropdownSelected.sqId) {
          d.pined = true;
        } else {
          d.pined = false;
        }
      });
      this.spinner.hide();
    }, error => this.spinner.hide());
  }
  jumpPage(pagenumber){
    // console.log(this.assetList.pa)
    if(pagenumber!=null)

   {
    this.boadingData.number=this.pagenumber-1;
  }
else{

}
    this.dropdownSelected.page=pagenumber-1;
    this.spinner.show();
    this.boadingData.content=[];
    this.bordingservice.geBoadingTable(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data=>{
      this.spinner.hide()
      this.boadingData=data
      this.totalPages = data.totalPages;
      this.first = data.first;
      this.last = data.last;
      this.size = data.size;
     this.serialNumber = ((this.page) * this.size);
      
      
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this.popupSuccessModel.show();
    });

  }

}
