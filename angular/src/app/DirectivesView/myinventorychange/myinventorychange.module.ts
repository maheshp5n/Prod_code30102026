import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MyinventorychangeComponent } from './myinventorychange.component';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { NgSelectModule } from '@ng-select/ng-select';



@NgModule({
  declarations: [MyinventorychangeComponent],
  imports: [
    CommonModule,
    FormsModule,
    NgxSpinnerModule,
    NgSelectModule
  ],
  schemas:[CUSTOM_ELEMENTS_SCHEMA],
  exports:[MyinventorychangeComponent],
  providers:[NgxSpinnerService]

})
export class MyinventorychangeModule { }
