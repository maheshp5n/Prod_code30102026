import { Component, OnInit, ViewChild } from '@angular/core';
import { ObjectDefinition, ObjectAccessResponse, ProfileRequest, ProfileObjectAccessRequest, UserProfile, UserProfileObjectAccess } from '../../services/model';
import { ObjectServices } from '../../services/objecct.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { ThemeService } from 'ng2-charts';
import { UserprofileService } from '../../services/userprofil.service';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { UserService } from '../../services/user.services';


@Component({
  selector: 'app-userprofile',
  templateUrl: './userprofile.component.html',
  styleUrls: ['./userprofile.component.css']
})
export class UserprofileComponent implements OnInit {
  userProfiles:Array<UserProfile>=[];
  _uerProfileForEdit:UserProfile=new UserProfile();
  alertDetails={
    type:'danger',
    message:''
  };
  @ViewChild('alertModal')alertModal:ModalDirective;
  @ViewChild('createprofile')createprofile:ModalDirective;
  operationMode:boolean=false;
  openModalDetails={profile:false}

  constructor(private _objecctService:ObjectServices,private _ngx:NgxSpinnerService,private _userProfile:UserprofileService,private _userservice:UserService) { }

  ngOnInit() {
   this.getProfileList();
  }
  createProfileOnHide(){
    this.operationMode=false;
    this._uerProfileForEdit=new UserProfile();
    this.openModalDetails.profile=false;
  }
  onProfileCreated($event){
    this.createprofile.hide();
    this.getProfileList();
    if(!this.operationMode){
      this.showMessage(`Created Successfully`);
    }
    else{
      this.showMessage(`Updated Successfully`);
    }
  }
  onProfileCanled($event){
    this.createprofile.hide();
  }
  showAlert(message){
    this.alertDetails.type='danger';
    this.alertDetails.message=message;
    this.alertModal.show();
  }
  showMessage(message){
    this.alertDetails.type='success';
    this.alertDetails.message=message;
    this.alertModal.show();
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
    this._userservice.getObjectAccessByProfileId(this._uerProfileForEdit.id).subscribe(data=>{
      this._ngx.hide();
      this._uerProfileForEdit.objectAccessResponse=data;
      this.operationMode=true;
      this.createprofile.show();
    },error=>this._ngx.hide());
  }
}
