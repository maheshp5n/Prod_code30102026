import { AccountscasedetailsComponent } from './accountscasedetails.component';
import { NgxSpinnerModule } from 'ngx-spinner';
import { AccountService } from '../../services/accounts.services';
import { AccountscasedetailsRouting } from './accountscasedetails.routing';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';



@NgModule({
  declarations: [AccountscasedetailsComponent],
  imports: [
    CommonModule,
    NgxSpinnerModule,
    AccountscasedetailsRouting
  ],
  providers: [AccountService]
})
export class AccountscasedetailsModule { }
