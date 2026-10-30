import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NewserviceorderComponent } from './newserviceorder.component';
import { NewserviceOrderRouting } from './newserviceorder.routing';
import { ServiceorderdirectiveModule } from '../../DirectivesView/serviceorderdirective/serviceorderdirective.module';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';



@NgModule({
  declarations: [NewserviceorderComponent],
  imports: [
    CommonModule,
    ServiceorderdirectiveModule,
    NewserviceOrderRouting,NgxSpinnerModule
  ],
  exports: [NewserviceorderComponent],
  providers:[NgxSpinnerService]
})
export class NewserviceOrderModule{

}