import { Component, OnInit, ViewChild, Output, EventEmitter, Input } from '@angular/core';
import { ObjectDefinition, ObjectAccessResponse, ProfileRequest, ProfileObjectAccessRequest, UserProfile, UINavigationModuleResponse } from '../../services/model';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { ObjectServices } from '../../services/objecct.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { UserprofileService } from '../../services/userprofil.service';
import { ThrowStmt } from '@angular/compiler';



@Component({
  selector: 'app-userprofiledirective',
  templateUrl: './userprofiledirective.component.html',
  styleUrls: ['./userprofiledirective.component.scss']
})
export class UserprofiledirectiveComponent implements OnInit {

  userProfile=new UserProfile();
  searchObjectAccess:string;
  objectDetails:Array<ObjectDefinition>=[];
  objectAccess:Array<ObjectAccessResponse>=[];
  _objectAccessRe:ObjectAccessResponse =new ObjectAccessResponse();
  uinav:Array<UINavigationModuleResponse>=[];

  userprofileNav:Array<UINavigationModuleResponse>=[];

  _profileRequest:ProfileRequest=new ProfileRequest();
  operationMode:boolean=false;
  alertDetails={
    type:'danger',
    message:''
  };
  searchKey:string;
  @ViewChild('alertModal')alertModal:ModalDirective;
  @Output() onProfileCreated:EventEmitter<ProfileRequest>=new EventEmitter<ProfileRequest>();
  @Output() onCanled:EventEmitter<string>=new EventEmitter<string>();
  @Input() set profileDetails(prop:UserProfile){
    if(prop){
      this.userProfile=prop;
      this._profileRequest.profileId=prop.id;
      this._profileRequest.profileName=prop.profileName;
      this._profileRequest.profileDescription=prop.profileDescription;
      this.objectAccess=prop.objectAccessResponse;
      this.getProfileNavigation(prop.id);
    }
    else{
      this.objectAccess=[];
      this.userProfile=new UserProfile();
      this._profileRequest=new ProfileRequest();
    }
  }
  get profileDetails(){
    return this.userProfile;
  }
  @Input() set mode(edit:boolean){
    if(edit){
      this.operationMode=edit;
    }
    else{
      this.getAllNavigation();
    }
    
  }
  get mode(){
    return this.operationMode;
  }
  constructor(private _objecctService:ObjectServices,private _ngx:NgxSpinnerService,private _userProfile:UserprofileService) { }

  ngOnInit() {
    this.getObjects();
    //this.getAllNavigation();
  }
  getObjects(){
    this._ngx.show();
    this._objecctService.getObjects().subscribe(data=>{
      this.objectDetails=data;
      this._ngx.hide();
    },error=>this._ngx.hide());
  }
  addObject(){
    if(this.objectAccess==undefined||this.objectAccess==null){
      this.objectAccess=[];
    }
    if(this._objectAccessRe.objectName==undefined||this._objectAccessRe.objectName==null||this._objectAccessRe.objectName==''){
      this.showAlert(`Please select Object`);
      return;
    }
    let alreadyExist:boolean=false;
    this.objectAccess.forEach(ob=>{
      if(ob.objectName==this._objectAccessRe.objectName){
        alreadyExist=true;
      }
    });
    if(!alreadyExist){
      let o=new ObjectAccessResponse();
      o.createRights=this._objectAccessRe.createRights;
      o.deleteRights=this._objectAccessRe.deleteRights;
      o.editRights=this._objectAccessRe.editRights;
      o.objectAccessId=this._objectAccessRe.objectAccessId;
      o.objectName=this._objectAccessRe.objectName;
      o.viewAllRights=this._objectAccessRe.viewAllRights;
      o.viewRights=this._objectAccessRe.viewRights;
      this.objectAccess.push(o);
      o.objectLableName= this.getObjectLabelNameByObjectName(this._objectAccessRe.objectName);
    }
    this._objectAccessRe=new ObjectAccessResponse();
  }
  removeObjectFromList(i:number){
    if(this.operationMode){
      this.showAlert(`Can not remove Element in edit mode`);
      return;
    }
    let temp=[];
    for(let j=0;j<this.objectAccess.length;j++){
      if(j!=i){
        temp.push(this.objectAccess[j]);
      }
    }
    this.objectAccess=temp;
  }
  saveProfile(){
    this._profileRequest.objectAccessRights=[];
    if(this._profileRequest.profileName==undefined||this._profileRequest.profileName==null||this._profileRequest.profileName==''){
      this.showAlert(`Please provide profile name`);
      return;
    }
    if(this._profileRequest.defaultpage==undefined||this._profileRequest.defaultpage==null||this._profileRequest.defaultpage==0){
      this.showAlert(`Please select default navigation`);
      return;
    }

    this.objectAccess.forEach(acc=>{
      let _acc=new ProfileObjectAccessRequest();
      _acc.createRights=acc.createRights;
      _acc.deleteRights=acc.deleteRights;
      _acc.editRights=acc.editRights;
      _acc.objectName=acc.objectName;
      _acc.viewAllRights=acc.viewAllRights;
      _acc.viewRights=acc.viewRights;
      this._profileRequest.objectAccessRights.push(_acc);
    });
    this._profileRequest.uiId=[];
    let validDefault=false;
    this.uinav.forEach(ui=>{
      if(ui.hasPrevilage){
        this._profileRequest.uiId.push(ui.uiModulesId);
        if(this._profileRequest.defaultpage==ui.uiModulesId){
          validDefault=true;
        }
      }
    });

    if(!validDefault){
      this.showAlert(`Please select valid default navigation`);
      return;
    }

    if(this._profileRequest.objectAccessRights.length==0){
      this.showAlert(`Please add object access for profile name ${this._profileRequest.profileName}`);
      return;
    }
    if(this.operationMode&&(this._profileRequest.profileId==undefined||this._profileRequest.profileId==null||this._profileRequest.profileId==0)){
      this.showAlert(`Some thing went wrong, Please close and reopen dialog box`);
      return;
    }
    this._ngx.show();
    if(!this.operationMode){
      this._userProfile.postProfile(this._profileRequest).subscribe(data=>{
        this._ngx.hide();
        this.onProfileCreated.emit(data);
      },error=>{
        this._ngx.hide();
        this.showAlert(`Error : ${error.error.message}`);
      });
    }
    else{
      this._userProfile.updateProfile(this._profileRequest.profileId,this._profileRequest).subscribe(data=>{
        this._ngx.hide();
        this.onProfileCreated.emit(data);
      },error=>{
        this._ngx.hide();
        this.showAlert(`Error : ${error.error.message}`);
      });
    }
    
  }
  cancle(){
    this.onCanled.emit('');
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
  getAllNavigation(){
    this._userProfile.getAllNavigation().subscribe(data=>{
      this.uinav=[];
      for(let i=0;i<data.length;i++){
        data[i].hasPrevilage=false;
        this.uinav.push(data[i]);
      }
    });
  }
  getProfileNavigation(profileId){
    this._userProfile.getAllNavigation().subscribe(data=>{
      this.uinav=[];
      for(let i=0;i<data.length;i++){
        data[i].hasPrevilage=false;
        this.uinav.push(data[i]);
        if(this.userProfile){
          if(data[i].url==this.userProfile.defaultPage){
            this._profileRequest.defaultpage=data[i].uiModulesId;
          }
        }
      }
      if(profileId){
        this._userProfile.getUINavigationByProfileid(profileId).subscribe(data=>{
          for(let i=0;i<data.length;i++){
            for(let j=0;j<this.uinav.length;j++){
              if(this.uinav[j].uiModulesId==data[i].uiModulesId){
                this.uinav[j].hasPrevilage=true;
              }
            }
          }
        });
      }
    });
  }

  getObjectLabelNameByObjectName(objectName:string):string{
    for(let i=0;i<this.objectDetails.length;i++){
      if(this.objectDetails[i].objectName===objectName){
        return this.objectDetails[i].objectLableName;
      }
    }
  }
}
