import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RoledetailsComponent } from './roledetails.component';
import { RoledetailsRouting } from './roledetails.routing';
import { AlertModule, ModalModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { NgSelectModule } from '@ng-select/ng-select';
import { FormsModule } from '@angular/forms';
import { NgxOrgChartModule } from '../../role-chart/public-api';



@NgModule({
  declarations: [RoledetailsComponent],
  imports: [
    CommonModule,
    RoledetailsRouting,
    FormsModule,
    AlertModule,
    ModalModule,
    NgxSpinnerModule,
    NgSelectModule,
    NgxOrgChartModule
  ],
  providers:[NgxSpinnerService]
})
export class RoledetailsModule { }
