import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../globalservice.service';
import { ligalEntityService } from '../../services/ligalentity.services';
import { ServiceAppointmentAssignToRequest, ServiceCenterForList, ServiceResourseForList, ServiceResourseVendor } from '../../services/model';
import { ServiceAppointmentService } from '../../services/serviceappointment.services';
import { ServiceResourceService } from '../../services/serviceresource.services';
import { siteService } from '../../services/siteService.services';

@Component({
  selector: 'app-assign-to-service-appointment',
  templateUrl: './assign-to-service-appointment.component.html',
  styleUrls: ['./assign-to-service-appointment.component.css']
})
export class AssignToServiceAppointmentComponent implements OnInit {
  _serviceAppointmentId:number;
  _serviceResourseVendor:ServiceResourseVendor;
  resourceList:ServiceResourseVendor;
  serviceAppointmentAssignToRequest:ServiceAppointmentAssignToRequest= new ServiceAppointmentAssignToRequest();
 
@Input()
  set serviceAppointmentId(serviceAppointmentID:number)
  {
    this._serviceAppointmentId = serviceAppointmentID;
    console.log(this._serviceAppointmentId);
    

  }
  get serviceAppointmentId(){
    return this._serviceAppointmentId;
  }
  @Input()
  set serviceResourseListVendor(serviceResourseVendor:ServiceResourseVendor){
    this._serviceResourseVendor = serviceResourseVendor;
    this.resourceList =  this._serviceResourseVendor;

  }
  get serviceResourseListVendor(){
    return  this._serviceResourseVendor;
  }


  @Output()
  onAssignToAppointmentCreated: EventEmitter<any> = new EventEmitter<any>();
  @Output()
  canceled: EventEmitter<string> = new EventEmitter<string>();

  

  
  // get editReassignAppointmentData(): ServiceAppointmentReAssignRequest {
  //   return this.serviceAppointmentReAssignRequest;
  // }


  _isEditMode: boolean;
  message: string;
  popupNgClass: string = 'alert alert-danger center center-block';
  faPlus: string = 'btn btn-primary fa fa-plus';

  legalEntityList: Array<ServiceCenterForList> = [];
  legalEntityForList: ServiceCenterForList = new ServiceCenterForList();
  resourceForList: ServiceResourseForList = new ServiceResourseForList();
  // resourceList: Array<ServiceResourseForList> = [];
  
  // serviceAppointmentReAssignRequest: ServiceAppointmentReAssignRequest = new ServiceAppointmentReAssignRequest();

  constructor(private spinner: NgxSpinnerService, private legalEntityService: ligalEntityService,private _global: GlobalserviceService,
    private siteservice:siteService,private resourceService: ServiceResourceService, private serviceAppointment: ServiceAppointmentService) { }

  ngOnInit() {
    // this.getLegalEntityList();
    this.getServiceResourceList();
    this.faPlus;
  }

  

  getServiceResourceList() {

    console.log("Get fun is callled");
    
  }
  getResourseId(){
  
  }


  postCreate() {

      this.serviceAppointmentAssignToRequest.id = this._serviceAppointmentId;
      this.spinner.show();
      this.serviceAppointment.assignTo(this.serviceAppointmentAssignToRequest).subscribe(data => {
        this.message = "Updated Successfully";
        this.popupNgClass = "alert alert-success center center-block";
        this._global.alert({ type: '', message: this.message });
        this.cancelData();
        this.onAssignToAppointmentCreated.emit(this.serviceAppointmentAssignToRequest.id);
      
          this.spinner.hide();
      
      }, err => {
        this.spinner.hide();
        this.message = err.error.message;
      
      });
    
  }

  cancelData() {
    console.log("before");
    
    // this.serviceAppointmentReAssignRequest = new ServiceAppointmentReAssignRequest();
    this.canceled.emit('cancled');
    console.log("after");

  }
  popupClose() {
    
    if (this.popupNgClass == 'alert alert-success center center-block') {
      this.canceled.emit('cancled');
    }
  }

}
