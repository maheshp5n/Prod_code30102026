import { NewprComponent } from './newpr.component';
import { ProductrequestdirectiveModule } from '../../DirectivesView/productrequestdirective/productrequestdirective.module'
import { NewprRouting } from './newpr.routing';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';



@NgModule({
  declarations: [NewprComponent],
  imports: [
    CommonModule,
    ProductrequestdirectiveModule,
    NewprRouting
  ]
})
export class NewprModule { }
