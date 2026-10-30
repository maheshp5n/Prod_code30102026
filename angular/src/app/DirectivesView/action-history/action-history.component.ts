import { Component, OnInit,  Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { Loginservices } from '../../services/login.services';
import { ActionHistoryObjectAndId, ActionHistoryResponse, Page } from '../../services/model';
import { ObjectServices } from '../../services/objecct.service';

@Component({
  selector: 'app-action-history',
  templateUrl: './action-history.component.html',
  styleUrls: ['./action-history.component.css']
})


export class ActionHistoryComponent implements OnInit {
  actionHistoryObjectAndId: ActionHistoryObjectAndId;
  actionHistoryResponse: Array<ActionHistoryResponse> = [];
  pagedata=new Page<ActionHistoryResponse>();

  @Input()
set actionHistoryInput(actionHistoryObjectAndId: ActionHistoryObjectAndId) {
  this.actionHistoryObjectAndId = actionHistoryObjectAndId;
  this.getObjectActionHistory(this.actionHistoryObjectAndId);
}

get actionHistoryInput(): ActionHistoryObjectAndId {
  return this.actionHistoryObjectAndId;
}


  constructor(private spinner: NgxSpinnerService, private _activatedRoute: ActivatedRoute,
    private _objectService: ObjectServices, private loginService: Loginservices) { }

  ngOnInit(): void {

  }

  getObjectActionHistory(actionHistoryObjectAndId: ActionHistoryObjectAndId) {
    
    this.spinner.show();
    this._objectService.getActionHistory(actionHistoryObjectAndId,`page=0&size=5&sort=created_date,desc`).subscribe(data=>{
      this.spinner.hide();
      this.actionHistoryResponse=data.content;
      this.pagedata=data;
    },error=>this.spinner.hide());
    /*
    this._objectService.getActionHistory(actionHistoryObjectAndId).subscribe(data => {
      this.actionHistoryResponse = data;
      this.spinner.hide();
    }, err => {
      this.spinner.hide();
    });*/
  }

}
