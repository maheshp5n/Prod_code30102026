import { Component, OnInit, ViewChild } from '@angular/core';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { NgxSpinnerService } from 'ngx-spinner';
import { GlobalserviceService } from '../../../globalservice.service';
import { UserProfile } from '../../../services/model';
import { ObjectServices } from '../../../services/objecct.service';
import { UserService } from '../../../services/user.services';
import { BatUserProfileService } from '../../bat_services/bat-user-profile-service';
import { BatuserregistrationService } from '../../bat_services/batuserregistration.service';

@Component({
  selector: 'app-bat-user-profile',
  templateUrl: './bat-user-profile.component.html',
  styleUrls: ['./bat-user-profile.component.scss']
})
export class BatUserProfileComponent implements OnInit {

  userProfiles:Array<UserProfile>=[];
  _uerProfileForEdit:UserProfile=new UserProfile();
  alertDetails={
    type:'danger',
    message:''
  };
  @ViewChild('createprofile')createprofile:ModalDirective;
  operationMode:boolean;
  openModalDetails={profile:false}

  constructor(private _objecctService:ObjectServices,
    private _ngx:NgxSpinnerService,
    private _userProfile:BatUserProfileService,
    private _userservice:BatuserregistrationService,
    private _global:GlobalserviceService
    ) { }

  ngOnInit() {
   this.getProfileList();
  }
  createProfileOnHide(){
    this.operationMode=false;
    this._uerProfileForEdit=new UserProfile();
    this.openModalDetails.profile=false;
  }
  onProfileCreated($event){
    this.operationMode=true;
    this.createprofile.hide();
    this.getProfileList();
    
    console.log(this.operationMode);
    
    if(!this.operationMode){
      this._global.alert({type:'',message:`Created Successfully`})
      
    }else{
  //  this.operationMode==true
      this._global.alert({type:'',message:`Updated Successfully`})      
    }
  }
  onProfileCanled($event){
    this.createprofile.hide();
  }
  getProfileList(){
    this._ngx.show();
    this._userservice.getUserProfile().subscribe(data=>{
      try{
        this.userProfiles=[];
        this.userProfiles=data;
        this.userProfiles.forEach(pro=>{
          pro.expended=false;
        })
      }catch(e){}
      this._ngx.hide();
    },error=>this._ngx.hide());
  }
  getObjectAccessByProfileId(profileid:number){
    let call=false;
    for(let i=0;i<this.userProfiles.length;i++){
      if(this.userProfiles[i].id==profileid){
        this.userProfiles[i].expended=!this.userProfiles[i].expended;
        call=this.userProfiles[i].expended;
        break;
      }
    }
    if(call){
      this._ngx.show();
      this._userservice.getObjectAccessByProfileId(profileid).subscribe(data=>{
        try{
          for(let i=0;i<this.userProfiles.length;i++){
            if(this.userProfiles[i].id==profileid){
              this.userProfiles[i].expended=true;
              this.userProfiles[i].objectAccessResponse=data;
              break;
            }
          }
          this._userProfile.getUINavigationByProfileid(profileid).subscribe(data=>{
            try{
              for(let i=0;i<this.userProfiles.length;i++){
                if(this.userProfiles[i].id==profileid){
                  this.userProfiles[i].uiNav=data;
                }
              }
            }catch(ex){}
            this._ngx.hide();
          },er=>{this._ngx.hide();});
        }catch(e){}
      },error=>this._ngx.hide());      
    }
  }
  editOpen(indexId){
    this._uerProfileForEdit=this.userProfiles[indexId];
    this._ngx.show();
    this.operationMode=true;

    this._userservice.getObjectAccessByProfileId(this._uerProfileForEdit.id).subscribe(data=>{
      this.operationMode=true;
      this._ngx.hide();
      this._uerProfileForEdit.objectAccessResponse=data;
      console.log(this.operationMode);
      
      this.createprofile.show();
    },error=>this._ngx.hide());
  }

}
