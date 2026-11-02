import { Component, OnInit, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { EmbedVideoService } from 'ngx-embed-video';
import { NgxSpinnerService } from 'ngx-spinner';
import { HelpServiceService } from '../../services/help-service.service';
import { HelpTutorial, Page } from '../../services/model';

@Component({
  selector: 'app-helpguide',
  templateUrl: './helpguide.component.html',
  styleUrls: ['./helpguide.component.css']
})
export class HelpguideComponent implements OnInit {
  public page: number = 0;
  serialNumber:number;
  searchitem: string = '';
  message:string;
  helplist:Page<HelpTutorial>=new Page<HelpTutorial>();
  popupNgClass: string = 'alert alert-danger center center-block';
  @ViewChild('popupSuccessModel') popupSuccessModel: ModalDirective;

  ngOnInit() {
    this.getTutorialsList();
 
  }
  constructor(private spinner: NgxSpinnerService, private helpServiceService:HelpServiceService) {
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
      this.popupSuccessModel.show();
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
  popupClose() {
    this.popupSuccessModel.hide();
    // if(this.popupNgClass=='alert alert-success center center-block'){
    //   this.getAll(this.sortColumnName, this.sortOrder);
    // }
  }

  
}





