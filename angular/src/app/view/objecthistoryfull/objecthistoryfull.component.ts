import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActionHistoryObjectAndId } from '../../services/model';
import { ObjectServices } from '../../services/objecct.service';

@Component({
  selector: 'app-objecthistoryfull',
  templateUrl: './objecthistoryfull.component.html',
  styleUrls: ['./objecthistoryfull.component.scss']
})
export class ObjecthistoryfullComponent implements OnInit {

  historyData=[];
  params=new ActionHistoryObjectAndId();
  constructor(private _activatedRoute:ActivatedRoute,private _objectService:ObjectServices,private _spinner:NgxSpinnerService) {
    this._activatedRoute.queryParams.subscribe(param=>{
      if(param['object']!=null&&param['object']!=undefined){
        this.params.objectName=param['object'];
      }
      if(param['id']!=null&&param['id']!=undefined){
        this.params.objectId=param['id'];
      } 
      if(param!=undefined){
        this.getHistoryData();
      }
      
    });
   }

  ngOnInit() {
  }
  getHistoryData(){
    this._spinner.show();
    this._objectService.getActionHistoryFull(this.params).subscribe(data=>{
      this._spinner.hide();
      this.historyData=data;
    },error=>this._spinner.hide());
  }

}
