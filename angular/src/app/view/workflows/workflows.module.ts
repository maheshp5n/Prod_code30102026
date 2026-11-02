import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { WorkflowsComponent } from './workflows.component';
import { NgSelectModule } from '@ng-select/ng-select';
import { AlertModule } from 'ngx-bootstrap/alert';
import { ModalModule } from 'ngx-bootstrap/modal';
import { WorkflowRouting } from './workflows.routing';



@NgModule({
  declarations: [WorkflowsComponent],
  imports: [
    CommonModule,
    FormsModule,
    NgxSpinnerModule,
    NgSelectModule,
    AlertModule,
    ModalModule,
    WorkflowRouting
  ],
  providers:[NgxSpinnerService]
})
export class WorkflowsModule { }
