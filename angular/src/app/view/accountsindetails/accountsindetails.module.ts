import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AccountsindetailsComponent } from './accountsindetails.component';
import { AccountsindetailsRouting } from './accountsindetails.routing';
import { AccountService } from '../../services/accounts.services';
import { ModalModule, BsDropdownModule } from 'ngx-bootstrap';
import { ContactcreateModule } from '../../DirectivesView/contactcreate/contactcreate.module';
import { CreateSiteModule } from '../../DirectivesView/create-site/create-site.module';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { siteService } from '../../services/siteService.services';
import { Loginservices } from '../../services/login.services';
import { CreateaccountModule } from '../../DirectivesView/createaccount/createaccount.module';
import { CreatecaseModule } from '../../DirectivesView/createcase/createcase.module';
import { caseService } from '../../services/caseService.service';
import { ContactsService } from '../../services/contacts.services';
import { ActionhistoryModule } from '../../DirectivesView/action-history/action-history.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    AccountsindetailsRouting,
    ModalModule,
    BsDropdownModule,
    ContactcreateModule,
    CreateSiteModule,
    CreateaccountModule,
    CreatecaseModule,
    NgxSpinnerModule,
    ActionhistoryModule
  ],
  declarations: [AccountsindetailsComponent],
  providers: [AccountService, NgxSpinnerService,siteService,Loginservices,caseService,ContactsService]
})
export class AccountsindetailsModule { }
