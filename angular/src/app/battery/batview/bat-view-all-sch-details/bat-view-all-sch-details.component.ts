import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { BtServiceappointmentService } from '../../bat_services/bt-serviceappointment.service';
import { BtServiceAppointmentResponseSchOnly, btServiceAppointmentResponseWithSCH, Page } from '../../bt_model';

@Component({
  selector: 'app-bat-view-all-sch-details',
  templateUrl: './bat-view-all-sch-details.component.html',
  styleUrls: ['./bat-view-all-sch-details.component.css']
})
export class BatViewAllSchDetailsComponent implements OnInit {

  serviceAppointmentId:number;
  schId:number;
  serviceAppointmentResponse: btServiceAppointmentResponseWithSCH = new btServiceAppointmentResponseWithSCH();
  btServiceAppointmentResponseSchOnly:Page<BtServiceAppointmentResponseSchOnly> = new Page<BtServiceAppointmentResponseSchOnly>();
  constructor(private _activatedRoute:ActivatedRoute,private _btserviceAppointment: BtServiceappointmentService,private spinner: NgxSpinnerService) {
    this._activatedRoute.params.subscribe(pram => {
      console.log(pram);
      
      this.serviceAppointmentId = pram['id'];
      console.log(this.serviceAppointmentId);
      
   })
  }
  dropdownSelected = {
    sqId: 1,
    name: 'All',
    filtreparam: 'all',
    sortname: 'createdDate',
    sortorder: 'desc',
    page: 0,
    size: 200,
    querypram: false
  };

  ngOnInit(): void {
    this.btServiceAppointmentResponseSchOnly.content=[];

// this.getServiceAppointmentSchDetailes();

this.getSchDetails(this.serviceAppointmentId);


   
  }

    // /Get SCh Detailes By ID 

    getSchDetails(serviceappointmentId){

      // `filtername=${this.dropdownSelected.filtreparam}`
      this.spinner.show();
      this._btserviceAppointment.getSchDetailsById(serviceappointmentId,`page=${this.dropdownSelected.page}&sort=${this.dropdownSelected.sortname},${this.dropdownSelected.sortorder}&size=${this.dropdownSelected.size}`).subscribe((data)=>{

 
        this.btServiceAppointmentResponseSchOnly = data;
        console.log(this.btServiceAppointmentResponseSchOnly);
        this.spinner.hide();
        
      },(error)=>{
        console.log(error);
        this.spinner.hide();
        
      })
    }
 

}

