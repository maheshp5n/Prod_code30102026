import { Component, OnInit, Output, ViewChild,EventEmitter, Input } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup } from '@angular/forms';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { caseService } from '../../../services/caseService.service';
import { RejectCaseRequest } from '../../../services/model';
import { BtCaseserviceService } from '../../bat_services/bt-caseservice.service';

@Component({
  selector: 'app-bt-reject-case',
  templateUrl: './bt-reject-case.component.html',
  styleUrls: ['./bt-reject-case.component.css']
})
export class BtRejectCaseComponent implements OnInit {
@ViewChild('popupSuccessModel') popupSuccessModel:ModalDirective;
@ViewChild('choosenFiles') choosenFiles: ModalDirective
constructor(private _btcaseservice:BtCaseserviceService,private spinner:NgxSpinnerService,private formBuilder: UntypedFormBuilder,
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
    this._global.alert({ type: 'alert', message: 'Case ID Can not Be Null' });
  }
  else if( this.rejectcase.remark==null && this.rejectcase.remark==undefined){
    this._global.alert({ type: 'alert', message: 'Remarks Can not Be Null' });
  }
  else{
    this.spinner.show();
    this.rejectcase.file=this.fileurl;
    this._btcaseservice.rejectcase(this.rejectcase).subscribe(data=>{
  this.spinner.hide();
  this.onSuccess.emit(data);
  this.onCancel.emit('cancled');
  this._global.alert({ type: '', message: 'Submitted Successfully' });

},error=>{
  this.spinner.hide();
  this._global.alert({ type: 'alert', message: error.error.message});
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
  this._btcaseservice.uploadImageFile(this.caseid, formData).subscribe(data => {
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
    this._global.alert({ type: 'alert', message: error.error.message});
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
