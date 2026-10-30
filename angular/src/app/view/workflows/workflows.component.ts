import { Component, OnInit, ViewChild } from '@angular/core';
import { WorkflowResponse, WorkflowServices, WorkflowRequest } from '../../services/workflow.services';
import { NgxSpinnerService } from 'ngx-spinner';
import { ObjectDefinition } from '../../services/model';
import { ObjectServices } from '../../services/objecct.service';
import { ModalDirective } from 'ngx-bootstrap';
import { WabsocketService } from '../../services/wabsocket.service';


@Component({
  selector: 'app-workflows',
  templateUrl: './workflows.component.html',
  styleUrls: ['./workflows.component.scss']
})
export class WorkflowsComponent implements OnInit {

  workflows: Array<WorkflowResponse> = [];
  objects: Array<ObjectDefinition> = [];
  workfowReuest: WorkflowRequest = new WorkflowRequest();
  @ViewChild('newworkflow', { static: false }) newworkflow: ModalDirective;
  @ViewChild('alertModal', { static: false }) alertModal: ModalDirective;

  formMode: boolean = false;
  outputdata: string = '';
  alertDetails = {
    type: 'danger',
    message: ''
  }
  constructor(private _ngxSpinner: NgxSpinnerService, private _workflowService: WorkflowServices, private _objectService: ObjectServices, private _websocket: WabsocketService) { }

  ngOnInit() {
    this.getAllWorkflows();
    this.getObjects();
    this.workfowReuest.on = 'POST';
    this.workfowReuest.dmlCommand = "INSERT";
    this._websocket.compileMessage.subscribe(data => {
      this.outputdata = data;
      this.outputdata.replace('\n', '<br>');
    });
  }

  getAllWorkflows() {
    this._ngxSpinner.show();
    this._workflowService.getWorkflow().subscribe(data => {
      this.workflows = data;
      this._ngxSpinner.hide();
    }, error => this._ngxSpinner.hide());
  }
  getObjects() {
    this._ngxSpinner.show();
    this._objectService.getObjects().subscribe(data => {
      this._ngxSpinner.hide();
      this.objects = data;
    }, error => this._ngxSpinner.hide());
  }
  enableDisable(id: number, flag: boolean) {
    this._ngxSpinner.show();
    this._workflowService.enableDisable(id, flag).subscribe(data => {
      this._ngxSpinner.hide();
      this.getAllWorkflows();
    }, error => { this._ngxSpinner.hide(); alert(`Error: ${error.error.message}`) });
  }
  onHide() {
    this.workfowReuest = new WorkflowRequest();
    this.workfowReuest.on = 'POST';
    this.workfowReuest.dmlCommand = "INSERT";
    this.formMode = false;
  }
  editWorkflow(id: number) {
    for (let i = 0; i < this.workflows.length; i++) {
      if (this.workflows[i].id == id) {
        this.workfowReuest.id = id;
        this.workfowReuest.description = this.workflows[i].description;
        this.workfowReuest.dmlCommand = this.workflows[i].dmlCommmand;
        this.workfowReuest.object = this.workflows[i].object;
        this.workfowReuest.on = this.workflows[i].onevent;
        this.workfowReuest.onEntityManager = this.workflows[i].codeSnip;
        this.formMode = true;
        this.newworkflow.show();
      }
    }
  }
  saveworkflow() {
    if (this.workfowReuest.onEntityManager == undefined || this.workfowReuest.onEntityManager == null || this.workfowReuest.onEntityManager == '') {
      this.showAlert('Code Snip can not be null');
      return;
    }
    this._ngxSpinner.show();
    if (!this.formMode) {
      this._workflowService.setWorkflow(this.workfowReuest).subscribe(data => {
        this._ngxSpinner.hide();
        this.newworkflow.hide();
        this.getAllWorkflows();
      }, error => {
        this._ngxSpinner.hide();
        this.showAlert(`Error : ${error.error.message}`)
      });
    }
    else {
      this._workflowService.editWorkflow(this.workfowReuest.id, this.workfowReuest).subscribe(data => {
        this._ngxSpinner.hide();
        this.newworkflow.hide();
        this.getAllWorkflows();
      }, error => {
        this._ngxSpinner.hide();
        this.showAlert(`Error : ${error.error.message}`);
      });
    }
  }

  showAlert(message) {
    this.alertDetails.type = 'danger';
    this.alertDetails.message = message;
    this.alertModal.show();
  }
  showMessage(message) {
    this.alertDetails.type = 'success',
      this.alertDetails.message = message;
    this.alertModal.show();
  }
  duringCom() {
    this._websocket.sendforcompilation(this.workfowReuest.object, this.workfowReuest.onEntityManager);
  }
}
