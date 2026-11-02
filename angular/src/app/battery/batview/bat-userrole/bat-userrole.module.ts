import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BatUserroleComponent } from './bat-userrole.component';
import { BatUserroleRouting } from './bat-userrole.routing';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { NgxOrgChartModule } from '../../../role-chart/public-api';
import { NgSelectModule } from '@ng-select/ng-select';
import { AlertModule } from 'ngx-bootstrap/alert';
import { ModalModule } from 'ngx-bootstrap/modal';
import { FormsModule } from '@angular/forms';



@NgModule({
  declarations: [BatUserroleComponent],
  imports: [
    CommonModule,
    BatUserroleRouting,
    FormsModule,
    AlertModule,
    ModalModule,
    NgxSpinnerModule,
    NgSelectModule,
    NgxOrgChartModule
  ],
  providers:[NgxSpinnerService]
})
export class BatUserroleModule { }
