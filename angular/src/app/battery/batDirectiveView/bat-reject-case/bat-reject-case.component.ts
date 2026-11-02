import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup } from '@angular/forms';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { RejectCaseRequest } from '../../../services/model';
import { BtCaseserviceService } from '../../bat_services/bt-caseservice.service';

@Component({
  selector: 'app-bat-reject-case',
  templateUrl: './bat-reject-case.component.html',
  styleUrls: ['./bat-reject-case.component.scss']
})
export class BatRejectCaseComponent implements OnInit {
  
  @ViewChild('choosenFiles') choosenFiles: ModalDirective
  constructor(private caseservice:BtCaseserviceService,private spinner:NgxSpinnerService,private formBuilder: UntypedFormBuilder,
    private _global : GlobalserviceService) { }
  rejectcase:RejectCaseRequest=new RejectCaseRequest();
  _isEditMode:boolean;
  fileList:any=[];
  getFile;
  uploadForm: UntypedFormGroup;
  formData = new FormData();
  message:string='';
  caseid:number;
  popupNgClass: string = 'alert alert-danger center center-block';
  @Output()
  onCancel: EventEmitter<string> = new EventEmitter<string>();
  @Output()
  onSuccess: EventEmitter<Array<any>> = new EventEmitter<Array<any>>();

  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }
  @Input()
  set caseManualID(caseid:string){
    this.rejectcase.caseManualId=caseid;
  }
  get caseManualID():string{
    return this.rejectcase.caseManualId;
  }
  @Input()
  set caseID(caseid:number){
    this.caseid=caseid;
  }
  get caseID():number{
    return this.caseid;
  }
    
  ngOnInit() {
    this.uploadForm = this.formBuilder.group({
      profile: ['']
    });
  }
  RejectSubmit(){
    if(this.rejectcase.caseManualId==null && this.rejectcase.caseManualId==undefined ){
      this.message="Case ID Cannot be null";
      this._global.alert({ type: 'alert', message: this.message});
    }
    else if( this.rejectcase.remark==null && this.rejectcase.remark==undefined){
      this.message="Remarks Cannot be null";
      this._global.alert({ type: 'alert', message: this.message});
    }
    else if(this.filename==null || this.filename==''){
      this.message="Please Select File";
      this._global.alert({ type: 'alert', message: this.message});
    }
    else{
      this.spinner.show();
      this.rejectcase.file=this.fileurl;
      this.caseservice.rejectcase(this.rejectcase).subscribe(data=>{
    this.spinner.hide();
    this.onSuccess.emit(data);
    this.popupNgClass='alert alert-success center center-block'
    this.message="Submitted Successfully"
    this._global.alert({ type: '', message: this.message});
  },error=>{
    this.spinner.hide();
    this.message=error.error.message;
    this._global.alert({ type: 'alert', message: this.message});
  });

    }
 
  }
  fileDetails: File;
  onFileSelect($event) {    
    if ($event.target.files.length > 0) {
      this.choosenFiles.show();
      for (let i = 0; i < $event.target.files.length; i++) {
        this.fileList.push($event.target.files[i]);
      }
    }
    this.uploadForm.get('profile').setValue(this.fileDetails);
  }
  // fileToUpload:File;
  // fileName:any;
  // onFileSelect($event) {
  //   console.log($event);
  //   for (let i = 0; i < $event.target.files.length; i++) {
  //     this.fileList.push($event.target.files[i]);
  //     this.fileToUpload = $event.target.files[i];
  //   }
  //   this.fileDetails = $event.target.files[0];
  //   this.fileName = this.fileDetails.name;
  //   this.uploadForm.get('profile').setValue(this.fileDetails);

  // }
  rejectcasemodelCancel(){
    this.rejectcase.remark=null;
    this.fileurl=null;
    this.onCancel.emit('cancled');
  }
  filedata:any=[];
  fileurl:string;
  filename:any=null;
  onSubmit() {
    this.spinner.show();
    const formData = new FormData();
    for (let i = 0; i < this.fileList.length; i++) {
      formData.append('files', this.fileList[i]);
    }
    this.caseservice.uploadImageFile(this.caseid, formData).subscribe(data => {
      this.spinner.hide();
      this.choosenFiles.hide();
      this.filedata=data;
      this.filedata.forEach(e => {
        this.filename=e.fileName;
        this.fileurl=e.fileDownloadUri;
      });
      this.rejectcase.file=this.fileurl;
      this.fileList = [];
      
      // this.getFileUploadList();
    }, error => {
      this.spinner.hide();
      this.message = error.error.message;
      this._global.alert({ type: 'alert', message: this.message});
    })

  }

  cancelChoosen() {
    this.fileList = [];
    this.formData = new FormData();
    this.choosenFiles.hide();
  }

  removefile(){
    this.filename=null;
    this.rejectcase.file=null;
  }

}
