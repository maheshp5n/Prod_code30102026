import { Component, OnInit, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { HelpServiceService } from '../../../services/help-service.service';
import { HelpTutorial, Page } from '../../../services/model';
import { BthelpguideService } from '../../bat_services/bthelpguide.service';

@Component({
  selector: 'app-bat-helpguide',
  templateUrl: './bat-helpguide.component.html',
  styleUrls: ['./bat-helpguide.component.css']
})
export class BatHelpguideComponent implements OnInit {
  public page: number = 0;
  serialNumber:number;
  searchitem: string = '';
  message:string;
  helplist:Page<HelpTutorial>=new Page<HelpTutorial>();
  // popupNgClass: string = 'alert alert-danger center center-block';
  // @ViewChild('popupSuccessModel', { static: false }) popupSuccessModel: ModalDirective;

  ngOnInit() {
    this.getTutorialsList();
 
  }
  constructor(private spinner: NgxSpinnerService, private helpServiceService:BthelpguideService,private _global: GlobalserviceService) {
       }
 
  getTutorialsList(){
    this.spinner.show();
    this.helplist.content=[];
    this.helpServiceService.getHelpList().subscribe(data=>{
      this.spinner.hide();
      this.helplist=data;
      this.serialNumber = (((this.page) * this.helplist.size));
    },error=>{
      this.spinner.hide();
      this.message=error.error.message;
      this._global.alert({ type: 'alert', message: this.message });
    });
  }
  setPagePrevious(event: any) {
    event.preventDefault();
    this.page = this.page - 1;
   this.getTutorialsList();
  }

  setPage(event: any) {
    event.preventDefault();
  this.getTutorialsList();
  }

  setPageNext(event: any) {
    event.preventDefault();
    this.page = this.page + 1;
  this.getTutorialsList();
  }
  // popupClose() {
  //   this.popupSuccessModel.hide();
  //   // if(this.popupNgClass=='alert alert-success center center-block'){
  //   //   this.getAll(this.sortColumnName, this.sortOrder);
  //   // }
  // }

  


}
