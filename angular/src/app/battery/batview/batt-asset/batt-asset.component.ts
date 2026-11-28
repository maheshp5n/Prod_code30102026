import { Component, OnInit, ViewChild } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../../services/login.services';
import {Page } from '../../../services/model';
import { PagefilterService } from '../../../services/pagefilterservice';
import { BTassetserviceService } from '../../bat_services/btassetservice.service';
import { BTasseReq, BTassetResp } from '../../bt_model';
import * as fileSaver from 'file-saver';
import { GlobalserviceService } from '../../../globalservice.service';
import { FieldValidation } from '../../../services/validation.services';
@Component({
  selector: 'app-batt-asset',
  templateUrl: './batt-asset.component.html',
  styleUrls: ['./batt-asset.component.css']
})

export class BattAssetComponent implements OnInit{
  @ViewChild('createAssetModel') createAssetModel: ModalDirective;
  @ViewChild('choosenFiles') choosenFiles: ModalDirective;
  assetRequest: BTasseReq = new BTasseReq();
  assetResponse: BTassetResp = new BTassetResp();
  assetList: Page<BTassetResp> = new Page<BTassetResp>();
  searchitem: string = '';
  getFile:any;
  filyType:any;
  fileList:any=[];
  fileToUpload: File;
  formData = new FormData();
  uploadForm: UntypedFormGroup;
  related: boolean = false;
  all: boolean = true;
  message: string;
  public page: number = 0;
  searchContent={
    serialNumber:'',
    siteName:'',
    productPartCode:'',
    productDescription:'',
    accountName:'',
    circleName:'',
    
  };
 

  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  CREATE_ACCOUNT_HEADING: string = 'New Asset';
  EDIT_ACCOUNT_HEADING: string = 'Edit Asset';
  popupNgClass: string = 'alert alert-danger center center-block';
  isModelShow = false;
  _cFlag: string;
  pindtselection = false;
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
      sortname: 'created_date',
      sortorder: 'desc',
      page: 0,
      size: 20,
      querypram: false
    };
    requestTableHeaders = [
      {
        id: 1,
        name: 'Asset Serial Number',
        sortColumnName: 'manual_id',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id: 2,
        name: 'Site Id',
        sortColumnName: 'btSite.manual_id',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id: 3,
        name: 'Account Name',
        sortColumnName: 'acc.name',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id: 4,
        name: 'Part Code',
        sortColumnName: 'btP.manual_id',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id: 5,
        name: 'Circle',
        sortColumnName: 'circle.circle_name',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id: 6,
        name: 'Product Family',
        sortColumnName: 'pFamily.product_family_name',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id: 7,
        name: 'Asset Description',
        sortColumnName: 'btP.pdoduct_description',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id: 8,
        name: 'Warranty Status',
        sortColumnName: '',
        icon: 'fa fa-angle-down pull-right'
      },
      {
        id: 9,
        name: 'AMC Status',
        sortColumnName: '',
        icon: 'fa fa-angle-down pull-right'
      }
      // {
      //   id: 9,
      //   name: 'Created Date',
      //   sortColumnName: 'createDate',
      //   icon: 'fa fa-angle-down pull-right'
      // },
     
    ];
  
    //done

  constructor(private pagefilterservice:PagefilterService,private spinner: NgxSpinnerService,private _activatedRoute: ActivatedRoute,
    private formBuilder: UntypedFormBuilder, private _assetsService: BTassetserviceService,private loginService: Loginservices,private _global:GlobalserviceService) {
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
  }
  rights(object,right:string):boolean{
    return this.loginService.isUserValidFor(object,right);
  }
  ngOnInit() {
    this.uploadForm = this.formBuilder.group({
      profile: ['']
    });
  }

  allAseets() {
    this.spinner.show();
    this.assetList.content=[];
    this._assetsService.getAllAssets(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.assetList = data;
      // this.assetList.number+1;
      
      if (data != null) {
        this.spinner.hide();
      }
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this._global.alert({type:'alert',message:err.error.message});
    });
  }

  
  dropdownChange(sqId) {
    this.dropdownsList.forEach(item => {
      if (item.sqId == sqId) {
        item.class = 'dropdown-item dropdown-item-checked';
        this.dropdownSelected.sqId = sqId;
        this.dropdownSelected.name = item.name;
        this.dropdownSelected.filtreparam = item.clickEventParam;
        this.dropdownSelected.page = 0;
        this.allAseets();
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
        this.allAseets();
      } else {
        tbl.icon = '';
      }
    });
  }
  createAssetModelhide() {
    this.assetResponse = new BTassetResp();
    this.createAssetModel.hide();
  }
  showCreateAccountView() {
    this.assetResponse = new BTassetResp();
    this.isEditMode = false;
    this.heading = this.CREATE_ACCOUNT_HEADING;
    this.createAssetModel.show();
  }

  createEditModelshow(a: BTassetResp) {
    this.spinner.show();
    this._assetsService.getAssetsBySerialNumber(a.id).subscribe(data => {
      this.assetResponse = data;      
      this.assetResponse.productDescription=data.productDescription;
        this.spinner.hide();
      
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this._global.alert({type:'alert',message:'Please Enter Valid Number'});
    });

    this.isEditMode = true;
    this.heading = this.EDIT_ACCOUNT_HEADING;
    this.createAssetModel.show();
  }

  cancelData() {
    this.assetResponse = new BTassetResp();
    this.createAssetModel.hide();
  }


  onAssetCreated($event: BTassetResp) {
    if (!this.isEditMode) {
      this.assetList.content.unshift($event);
    } else {
      let index = 0;
      this.assetList.content.forEach(element => {
        if (element.serialNumber === $event.serialNumber) {
          this.assetList[index] = $event;
        } else {
          index++;
        }
      });
    }
    this.createAssetModel.hide();
  }

  // popupClose() {
  //   this.popupSuccessModel.hide();
  // }

  getFilterList() {
    this.pagefilterservice.getPagefilter('bat_asset').subscribe(data =>{
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

      this.allAseets();
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

  advanceSearch(){
    if(this.searchContent.accountName=='' && this.searchContent.serialNumber=='' &&
      this.searchContent.circleName=='' && this.searchContent.productDescription=='' &&
      this.searchContent.productPartCode=='' && this.searchContent.siteName==''){
      this.message="Enter Valid Key For Search";
      this._global.alert({type:'alert',message:this.message});
    }
    else{
       
    this.spinner.show();
    this._assetsService.getSearchResult(this.searchContent).subscribe(data=>{
      this.spinner.hide();
      this.assetList.content=data;
      this.assetList.first=true;
      this.assetList.last=true;
    },error=>{
      this.spinner.hide();
    });
    }
    
   
  }

  sencodeURL(urld):string{
    return encodeURIComponent(urld);
  }
  pagenumber:any=null;
  jumpPage(pagenumber){
    if(pagenumber!=null && pagenumber!==''){
      if(pagenumber>this.assetList.totalPages || pagenumber==0){
        this._global.alert({type:'alert',message:'Page Count Should Be Less Then Total Page Count'});
      }
      else if(FieldValidation.isLetter(pagenumber))
  {
    this._global.alert({ type: 'alert', message: "Please Enter A Valid Input" });
    pagenumber=null;
  }
   else{ 
     this.assetList.number=this.pagenumber-1;
    this.dropdownSelected.page=pagenumber-1;
    this.spinner.show();
    this.assetList.content=[];
    this._assetsService.getAllAssets(`filtername=${this.dropdownSelected.filtreparam}`, `page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe(data => {
      this.assetList = data;
      this.assetList.number=this.pagenumber-1;
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
      this.message = err.error.message;
      this._global.alert({type:'alert',message:this.message})
    });}

  }
  else{
       this._global.alert({type:'alert',message:'Please Input Number'});
      }
}

  // bulk upload
  
  onFileSelect(event) {
    // console.log((<HTMLInputElement>document.getElementById('getFile')).value)
    if(event!=null){
      
    this.getFile = event.target.files.id;
    this.filyType = event.target.id;
    if (event.target.files.length > 0) {
      this.choosenFiles.show();
      for (let i = 0; i < event.target.files.length; i++) {
        this.fileList.push(event.target.files[i]);
        this.fileToUpload = event.target.files[i];
        // console.log(this.fileToUpload);

      }
    }
    }
    else{
      console.log(event)
    }
   
  }

  // submit file choosen
  onSubmit() {
    this.spinner.show();
    for (let i = 0; i < this.fileList.length; i++) {
      this.formData.append('files', this.fileList[i]);
      // this.fileToUpload = event.target.fileList[i];

    }
    if (this.filyType == "getFile") {
      this._assetsService.uploadFiles(this.fileToUpload).subscribe(data => {
        this.spinner.hide();
        this.choosenFiles.hide();
        this.downloadFile(data);
        this.popupNgClass = 'alert alert-success center center-block';
        this.fileList = []; 
       (<HTMLInputElement>document.getElementById('getFile')).value=null
        this.message = "File Uploaded Successfully";
        this._global.alert({type:'',message:this.message});
      }, error => {
        this.spinner.hide();
        this.message = error.message;
        this._global.alert({type:'alert',message:this.message});
      });
      this.formData = new FormData();
      this.getFile = "";
    }
  }
  // get response file downloaded
  cancelChoosen() {
    (<HTMLInputElement>document.getElementById('getFile')).value=null
    this.choosenFiles.hide();
    this.formData = new FormData();
    this.fileList = [];
    this.getFile = "";
  }
  
  downloadFile(data) {
    //  var blob = new Blob([<any>data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;'});
    //  var url= window.URL.createObjectURL(blob);
    //  fileSaver.saveAs(blob,data.fileName);
    //  window.open(url);
    // 
    var file = new Blob([data], {
      type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
    });
    fileSaver.saveAs(file, "Report" + ".xlsx");



  }
  getTemplateExcel() {
    this._assetsService.getExcelTemplate().subscribe(data => {
      // this.downloadFile(data);
      var file = new Blob([data], {
        type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
      });
      fileSaver.saveAs(file, "Asset" + ".xlsx");
    }, error => {
      this.spinner.hide();
      this.message = error.message;
      this._global.alert({type:'alert',message:this.message});
    })
  }
  restsearch(){
    
     
   this.searchContent.serialNumber=''
   this.searchContent.siteName=''
   this.searchContent.productPartCode=''
   this.searchContent.productDescription=''
   this.searchContent.accountName=''
   this.searchContent.circleName=''
   this.getFilterList();
   
   //Ayush //get all list on click reset button
  }
  // shagufta
  onlyNumbersForRs(key): any {
    console.log(key.target.value+1);

    const charCode = (key.which) ? key.which : key.keyCode;
    console.log("Only Number For Rs. Function");
    if(charCode>31 && charCode==46){
      return true; 
     }
    else if (charCode > 31 &&(charCode <48 || charCode > 57)) {
      return false;
    }
  //   else if(this.pagenumber>this.assetList.totalPages){
  //     this.message="Please Enter Under "+ this.assetList.totalPages+" "+"Pages";
  //     this._global.alert({type:'alert',message:this.message});
  //    return false;
  //  }
   
  }
}