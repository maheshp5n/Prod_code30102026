import { Component, OnInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap';
import { AssetsResponse, BatteryFSRResponse, FileUploadResponse } from '../../services/model';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute } from '@angular/router';
import { AssetsService } from '../../services/assets.services';
import { BatrryFSRService } from '../../services/BattryFSRservice.services';
import { Loginservices } from '../../services/login.services';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, FormGroup } from '@angular/forms';
import * as fileSaver from 'file-saver';

@Component({
  selector: 'app-battery-fsrcbmsli-lonbatterydetails',
  templateUrl: './battery-fsrcbmsli-lonbatterydetails.component.html',
  styleUrls: ['./battery-fsrcbmsli-lonbatterydetails.component.scss']
})
export class BatteryFSRCBMSLiLonbatterydetailsComponent implements OnInit {
  @ViewChild('createBFSRModel', { static: false }) createBFSRModel: ModalDirective;
  @ViewChild('createBatteryInventoryConsumption', { static: false }) createBatteryInventoryConsumption: ModalDirective;
  @ViewChild('choosenFiles', { static: false }) choosenFiles: ModalDirective;
  @ViewChild('choosenFilesShow', { static: false }) choosenFilesShow: ModalDirective;
  @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;

  @Output()
  onAccountCreated: EventEmitter<AssetsResponse> = new EventEmitter<AssetsResponse>();
  @Output()
  onCancle: EventEmitter<string> = new EventEmitter<string>();

  message: string
  ngClassDetails: string = 'active';
  ngClassReleated: string = '';
  vDetails: boolean = true;
  id: number;
  fileuploadResponse: FileUploadResponse = new FileUploadResponse();
  isEditMode: boolean = false;
  heading: string;
  edit: boolean = false;
  EDIT_ACCOUNT_HEADING: string = 'Edit Battery FSR CBMS & Li-lon Battery';
  assetResponse: AssetsResponse = new AssetsResponse();
  assetList: Array<BatteryFSRResponse> = [];
  BTRresponse: BatteryFSRResponse = new BatteryFSRResponse();
  BFSRres: BatteryFSRResponse = new BatteryFSRResponse();
  fileLists: Array<FileUploadResponse> = [];
  serialNumber: number;
  page: number = 0;
  size: number;
  fileList: string[] = [];
  imgsrc: any
  imageToShow: any;
  isImageLoading: any;
  uploadForm: FormGroup;
  formData = new FormData();
  _cFlag:string;
  popupNgClass: string = 'alert alert-danger center center-block';
  constructor(private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute, private formBuilder: FormBuilder,
    private _http: HttpClient, private BFSservice: BatrryFSRService, private loginservice: Loginservices) {
    this._activatedRoute.params.subscribe(pram => {
      this.id = pram['id'];
    });
    this._activatedRoute.queryParams.subscribe(pram => {
      this._cFlag = pram['_c'];
    });

  }
  rights(object, right: string): boolean {
    return this.loginservice.isUserValidFor(object, right);
  }
  ngOnInit() {
    this.getBattry();
    
    this.uploadForm = this.formBuilder.group({
      profile: ['']
    });
  }
  onFileSelect(event) {
    if (event.target.files.length > 0) {
      this.choosenFiles.show();
      for (let i = 0; i < event.target.files.length; i++) {
        this.fileList.push(event.target.files[i]);
      }
    }
  }

  remove(unselectReason) {
    if (unselectReason !== undefined) {
      const index = this.fileList.indexOf(unselectReason);
      this.fileList.splice(index, 1);
    }
  }

  createImageFromBlob(image: Blob) {

    let reader = new FileReader();
    reader.addEventListener("load", () => {
      this.imageToShow = reader.result;
    }, false);

    if (image) {
      reader.readAsDataURL(image);
    } else {
      //  this.downloadFile(this.fileName)
    }
  }
  fileName: any
  opneImage(file: FileUploadResponse) {
    this.isImageLoading = true;
    // this.choosenFilesShow.show();
    this.spinner.show();
    this.BFSservice.filedownload(file.fileName).subscribe(data => {
      this.spinner.hide();
      this.createImageFromBlob(data);
      this.fileName = file.fileName
     
      // if (data.type == 'image/jpeg' || data.type == 'image/png') {
        this.choosenFilesShow.show();
        this.isImageLoading = false;
    },error=>{
      this.spinner.hide();
      this.message=error.message;
      this.popupSuccessModel.show()
    })
  }

  downloadFile(fileName: any) {
    this.spinner.show();
    this.BFSservice.getDownloadFile(fileName).subscribe(data => {
      fileSaver.saveAs(data, fileName);
      this.spinner.hide();
    },
      err => {
        this.spinner.hide();
        this.message = err.error.message;
        this.popupSuccessModel.show();
      });
  }
  cancelChoosen() {
    this.fileList = [];
    this.formData = new FormData();
    this.choosenFiles.hide();
  }

  onSubmit() {

    this.spinner.show();
    const formData = new FormData();
    for (let i = 0; i < this.fileList.length; i++) {
      formData.append('files', this.fileList[i]);
    }
    this.BFSservice.uploadFiles(this.BTRresponse.id, formData).subscribe(data => {
      this.spinner.hide();
      this.choosenFiles.hide();
      this.fileList = [];
      this.getFileUploadList();
    }, error => {
      this.spinner.hide();
      this.message = error.error.message;
      this.popupSuccessModel.show();
    })

  }

  getFileUploadList() {
    this.fileList = [];
    this.spinner.show();
    this.BFSservice.getFileList(this.id).subscribe(data => {
      this.spinner.hide();
      this.fileLists = data;
    }, error => {
      this.spinner.hide();
      this.message = error.error.message;
      this.popupSuccessModel.show();
    })
  }
  getBattry() {
    this.spinner.show();
    this.BFSservice.getDetailById(this.id).subscribe(data => {
      if (data != null) {
        this.spinner.hide();
      }
      this.BTRresponse = data;
    }, error => {
      this.spinner.hide();
      this.message = error.error.message;
      this.popupSuccessModel.show();
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
  sysinfoDisplay = {
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
  sysInfoDisplay() {
    this.sysinfoDisplay.open = !this.sysinfoDisplay.open
    if (this.sysinfoDisplay.open) {
      this.sysinfoDisplay.angleIcon = 'fa fa-angle-down';
    }
    else {
      this.sysinfoDisplay.angleIcon = 'fa fa-angle-right';
    }

  }
  subTabChange(tabName: string) {
    this.spinner.show();
    if (tabName == 'details') {
      this.spinner.hide();
      this.ngClassDetails = 'active';
      this.ngClassReleated = '';
      this.vDetails = true;
      this.getBattry();
    }
    else {
      this.spinner.show();
      this.ngClassDetails = '';
      this.ngClassReleated = 'active';
      this.vDetails = false;
      this.getFileUploadList();
      //this.getContacts();
    }
    this.spinner.hide();
    return false;
  }
  cancelData() {

  }
  onAssetCreated($event) {

  }

  cancelBFSRModelhide() {
    //this.assetResponse=new AssetsResponse();
    this.BFSRres = new BatteryFSRResponse();
    this.onCancle.emit('cancled');
    this.createBFSRModel.hide();
  }
  /* showCreateAccountView(){
    this.assetResponse=new AssetsResponse();
    this.isEditMode = false;
    this.heading =this.CREATE_ACCOUNT_HEADING;
    this.createAcountModel.show();
  } */

  createEditModelshow(BFSRres: BatteryFSRResponse) {
    this.isEditMode=true
    this.spinner.show();
    this.BFSservice.getDetailById(BFSRres.id).subscribe(data => {
      this.spinner.hide();
      this.BFSRres = data
   
    }, error => {
      this.spinner.hide();
      this.message = error.error.message;
      this.popupSuccessModel.show();
    });
    this.heading = this.EDIT_ACCOUNT_HEADING;
    this.createBFSRModel.show();
  }

  onBFSRCreated($event: BatteryFSRResponse) {
    this.BTRresponse = $event;
    this.getBattry();
    this.getFileUploadList();
  }
  handleFileInput(files: FileList) {
    // this.fileToUpload = files.item(0);
  }



}
