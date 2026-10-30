import { MyinventorydetailComponent } from './myinventorydetail.component';
import { MyinventorydetailRouting } from './myinventorydetails.routing';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { GlobalSearchPipeModule } from '../../pipe/global-search-pipe.module';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MyinventorychangeModule } from '../../DirectivesView/myinventorychange/myinventorychange.module';
import { ModalModule } from 'ngx-bootstrap';



@NgModule({
  declarations: [MyinventorydetailComponent],
  imports: [
    CommonModule,
    MyinventorydetailRouting,
    FormsModule,
    NgxSpinnerModule,
    GlobalSearchPipeModule,
    MyinventorychangeModule,
    ModalModule
  ],
  providers: [NgxSpinnerService]
})
export class MyinventorydetailModule { }
