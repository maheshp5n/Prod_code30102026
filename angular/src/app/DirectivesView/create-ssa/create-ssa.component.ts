import { Component, OnInit, ViewChild, Input, Output, EventEmitter } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { ModalDirective } from 'ngx-bootstrap';
import { SSAResponse, SSARequest, SSAForlist, CircleZoneForList, CirclesRequest } from '../../services/model';
import { siteService } from '../../services/siteService.services';
import { ssaService } from '../../services/ssa.services';
import { FieldValidation } from '../../services/validation.services';
import { Loginservices } from '../../services/login.services';
import { GlobalserviceService } from '../../globalservice.service';
import { BtcircleService } from '../../battery/bat_services/btcircle.service';
import { CirclesForList } from '../../battery/bt_model';

@Component({
  selector: 'app-create-ssa',
  templateUrl: './create-ssa.component.html',
  styleUrls: ['./create-ssa.component.scss']
})
export class CreateSSAComponent implements OnInit {
heading:string;
  isVisible: boolean = false;
  _isEditMode: boolean;
  istrue: boolean;
  message: string;
 ssares: SSAResponse=new SSAResponse();
 ssareq:SSARequest=new SSARequest();
 ssalistArray:Array<SSAForlist>;
 circlesList:Array<CirclesForList>;
 faPlus: string = "btn btn-primary fa fa-plus";
  popupNgClass: string = 'alert alert-danger center center-block';
  // @ViewChild('popupSuccessModel',{static:false}) popupSuccessModel: ModalDirective;
  @ViewChild('createCircleModel', { static: false }) createCircleModel: ModalDirective;
  @Input()
  set isEditMode(isEditMode: boolean) {
    this._isEditMode = isEditMode;
  }

  get isEditMode(): boolean {
    return this._isEditMode;
  }


  @Input()
  set editSSAEntityData(ssares:SSAResponse) {
   this.ssares = ssares;
  }

  get editSSAEntityData():SSAResponse  {
   return this.ssares
  }
  @Input()
  set circles(circleName:string){
    this.ssares.circleName=circleName;
  }
  get circles():string{
    return this.ssares.circleName
  }

  @Output()
  onSSACreated: EventEmitter<SSAResponse> = new EventEmitter<SSAResponse>();
  @Output()
  onSSACancle: EventEmitter<string> = new EventEmitter<string>();
  modelmeta = { accountModelOpen: false, legalEntityModelOpen: false, circleModelOpen: false }
 loginData:any;
 domainId:number;

  constructor(private _gloabl: GlobalserviceService ,private loginservice:Loginservices, private spinner: NgxSpinnerService,
    private siteservice:siteService,
    private btcircle:BtcircleService,
    private ssaservice:ssaService) { 

    
  this.loginData = this.loginservice.getLoggedInUser(); 
  this.domainId = this.loginData.emptype['businessDomainId']
  }

  rights(object,right:string):boolean{
    return this.loginservice.isUserValidFor(object,right);
  }
  ngOnInit() {
    if(!this._isEditMode){
      this.ssares=new SSAResponse();
    }
    this.getCircleList();
  }
  getCircleList() {
    if(this.domainId==2){
      this.spinner.show();
      this.siteservice.getCircleList().subscribe(data => {
        this.spinner.hide();
        this.circlesList = data;
        },error=>{
        this.spinner.hide();
        this.message=error.error.message;
        this._gloabl.alert({ type: 'alert', message: this.message });
      })
    }
    else{
    this.btcircle.getCircleForList().subscribe(data=>{
      this.spinner.hide();
      this.circlesList = data;
    
    },error=>{
      this.spinner.hide();
      this.message=error.error.message;
      this._gloabl.alert({ type: 'alert', message: this.message });
    })
    }

  }
  ssaId:number;
  ssaName:string;
  postSSA(){
    console.log(FieldValidation.isEmpty(this.ssares.name))
    if(this.ssares.name===undefined || this.ssares.name===null && FieldValidation.isEmpty(this.ssares.name)){
      this.message="Please Fill Name";
      this._gloabl.alert({ type: 'alert', message: this.message });

    }
    else if((this.ssares.name!==null) &&((this.ssares.name).length>200)){
      this.message="Name Exceeds 200 Characters"
      this._gloabl.alert({ type: 'alert', message: this.message });

    }
    else if(this.ssares.circleName==null){
      this.message="Please Fill Circle Name";
      this._gloabl.alert({ type: 'alert', message: this.message });
    }
    else{
      if(!this.isEditMode){
        this.ssareq.ssaname=this.ssares.name;
        this.ssareq.circleName=this.ssares.circleName;
        this.spinner.show();
        this.ssaservice.saveSSA(this.ssareq).subscribe(data=>{
        this.ssaId=data.id;
        this.ssaName=data.name;
         this.spinner.hide();
         this.onSSACreated.emit(data);
         this.message = 'Submitted Successfully';
        this._gloabl.alert({ type: '', message: this.message });
        this.spinner.hide()
        this.onSSACancle.emit('cancled'); 
        this.ssares=new SSAResponse();
    },error=>{
      this.spinner.hide()
     this.message=error.error.message;
     this._gloabl.alert({ type: 'alert', message: error.error.message });
    
    })

      }
      else{
        this.ssareq.ssaid=this.ssares.id;
        this.ssareq.ssaname=this.ssares.name;
        this.ssareq.circleName=this.ssares.circleName;
        this.spinner.show();
        this.ssaservice.updateSSA(this.ssareq).subscribe(data=>{
          this.spinner.hide();
          this.ssaId=data.id;
          this.ssaName=data.name;
          this.onSSACreated.emit(data);
         this.message = 'Updated Successfully';
        //  this.popupNgClass = 'alert alert-success center center-block';
         this.spinner.hide()
         this._gloabl.alert({ type: '', message: this.message });
         this.onSSACancle.emit('cancled');
         this.ssares=new SSAResponse();
        },error=>{
          this.spinner.hide();
          this.message=error.error.message;
          this._gloabl.alert({ type: 'alert', message: this.message });
        })

      }
    }
   
  }
  // popupClose() {
  //   this.popupSuccessModel.hide();
  //   if (this.popupNgClass === 'alert alert-success center center-block') {
  //     this.onSSACancle.emit('cancled');
  //   }
  // }
  cancelSSAData(){
    this.onSSACancle.emit('cancled');
  }
  newcircles: CirclesRequest = new CirclesRequest()
  addNewCircle() {
    this.isEditMode = false;
    this.heading = "Create Circle";
    this.newcircles = new CirclesRequest();
    this.createCircleModel.show();
  }
  
  onCircleCreated($event) {
    this.getCircleList();
    this.ssares.circleName = $event.circleName;
    // this.ssares.zoneName=$event.zone;

  }
  cancelCircleData(){
    this.createCircleModel.hide();
  }
}
