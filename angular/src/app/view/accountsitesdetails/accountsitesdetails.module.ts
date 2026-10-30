import { AccountssitedetailsRouting } from './accountssitedetails.routing';
import { AccountsitesdetailsComponent } from './accountsitesdetails.component';
import { AccountService } from '../../services/accounts.services';
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';



@NgModule({
  declarations: [AccountsitesdetailsComponent],
  imports: [
    CommonModule,
    AccountssitedetailsRouting,
    NgxSpinnerModule
  ],
  providers: [AccountService, NgxSpinnerService]
})
export class AccountsitesdetailsModule { }
