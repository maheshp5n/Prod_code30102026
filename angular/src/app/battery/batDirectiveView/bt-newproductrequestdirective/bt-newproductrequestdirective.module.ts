import { NgModule} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { NgSelectModule } from '@ng-select/ng-select';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { BtNewproductrequestdirectiveComponent } from './bt-newproductrequestdirective.component';



@NgModule({
  declarations: [BtNewproductrequestdirectiveComponent],
  imports: [
    CommonModule,
    FormsModule,
    NgxSpinnerModule,
    NgSelectModule,
    ModalModule,
    BsDatepickerModule

  ],
  exports:[BtNewproductrequestdirectiveComponent],
  providers:[NgxSpinnerService]
})
export class BtNewProductRequestDerectiveModule { }









