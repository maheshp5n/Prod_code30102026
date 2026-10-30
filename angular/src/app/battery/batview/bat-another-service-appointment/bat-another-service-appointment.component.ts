import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { BtServiceappointmentService } from '../../bat_services/bt-serviceappointment.service';
import { anotherSA } from '../../bt_model';


@Component({
  selector: 'app-bat-another-service-appointment',
  templateUrl: './bat-another-service-appointment.component.html',
  styleUrls: ['./bat-another-service-appointment.component.css']
})
export class BatAnotherServiceAppointmentComponent implements OnInit {

   serviceAppointmentres:anotherSA;
    serviceAppointmentParam:number;
 
  constructor( private _btserviceAppointment: BtServiceappointmentService,private router:Router, private _activatedRoute: ActivatedRoute,private spinner: NgxSpinnerService) { 

    this.spinner.show();
    this._activatedRoute.params.subscribe(pram => {
      this.serviceAppointmentParam = pram['id'];
    
      this._btserviceAppointment.getIdanotherServiceAppointmentBy(this.serviceAppointmentParam).subscribe((res)=>{
        this.serviceAppointmentres = res;

        this.router.navigate(['./bat_serviceappointment/'+this.serviceAppointmentres.serviceAppointmentId])
        
      })
    }) 
 
  }

  ngOnInit(): void {

  }

}
