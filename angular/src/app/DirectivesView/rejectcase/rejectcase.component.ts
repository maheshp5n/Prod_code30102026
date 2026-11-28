import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup } from '@angular/forms';
import { HtmlAttributesDirective } from '@coreui/angular/lib/shared/layout/layout.directive';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { caseService } from '../../services/caseService.service';
import { RejectCaseRequest } from '../../services/model';

@Component({
  selector: 'app-rejectcase',
  templateUrl: './rejectcase.component.html',
  styleUrls: ['./rejectcase.component.css']
})
export class RejectcaseComponent implements OnInit {
  @ViewChild('popupSuccessModel') popupSuccessModel:ModalDirective;
  @ViewChild('choosenFiles') choosenFiles: ModalDirective
  constructor(private caseservice:caseService,private spinner:NgxSpinnerService,private formBuilder: UntypedFormBuilder) { }
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
      this.popupSuccessModel.show();
    }
    else if( this.rejectcase.remark==null && this.rejectcase.remark==undefined){
      this.message="Remarks Cannot be null";
      this.popupSuccessModel.show();
    }
    else{
      this.spinner.show();
      this.rejectcase.file=this.fileurl;
      this.caseservice.rejectcase(this.rejectcase).subscribe(data=>{
    this.spinner.hide();
    this.onSuccess.emit(data);
    this.popupNgClass='alert alert-success center center-block'
    this.message="Submitted Successfully"
    this.popupSuccessModel.show();
  },error=>{
    this.spinner.hide();
    this.message=error.error.message;
    this.popupSuccessModel.show();
  });

    }
 
  }
  onFileSelect(event) {
    if (event.target.files.length > 0) {
      this.choosenFiles.show();
      for (let i = 0; i < event.target.files.length; i++) {
        this.fileList.push(event.target.files[i]);
      }
    }
  }
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
      this.popupSuccessModel.show();
    })

  }

  cancelChoosen() {
    this.fileList = [];
    this.formData = new FormData();
    this.choosenFiles.hide();
  }
  popupClose(){
    this.popupSuccessModel.hide();
    if (this.popupNgClass === 'alert alert-success center center-block') {
      this.onCancel.emit('cancel');
    }
  }
  removefile(){
    this.filename=null;
    this.rejectcase.file=null;
  }
}
