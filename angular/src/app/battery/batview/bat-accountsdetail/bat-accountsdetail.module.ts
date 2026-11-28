import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AccountService } from '../../../services/accounts.services';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { CreateSiteModule } from '../../../DirectivesView/create-site/create-site.module';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { siteService } from '../../../services/siteService.services';
import { Loginservices } from '../../../services/login.services';
import { CreatecaseModule } from '../../../DirectivesView/createcase/createcase.module';
import { caseService } from '../../../services/caseService.service';
import { ContactsService } from '../../../services/contacts.services';
import { BatAccountsdetailsRouting} from './bat-accountsdetail.routing';
import { BatAccountsdetailComponent } from './bat-accountsdetail.component';
import { BTCreateaccountModule } from '../../batDirectiveView/bt-createaccount/bt-createaccount.module';
import { BtActionhistoryModule } from '../../batDirectiveView/bt-action-history/bt-action-history.module';
import { BtCreatecontactsModule } from '../../batDirectiveView/bt-createcontacts/bt-createcontacts.module';
import {BtCreateSitemodule} from '../../batDirectiveView/bt-createsite/bt-createsite.module'
import {BTCreateCaseModule } from '../../batDirectiveView/btcreatecase/btcreatecase.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    BatAccountsdetailsRouting,
    ModalModule,
    BsDropdownModule,
    CreateSiteModule,
    BTCreateaccountModule,
    CreatecaseModule,
    NgxSpinnerModule,
    BtActionhistoryModule,
    BtCreatecontactsModule,
    BtCreateSitemodule,
    BTCreateCaseModule
  ],
  declarations: [BatAccountsdetailComponent],
  providers: [AccountService, NgxSpinnerService,siteService,Loginservices,caseService,ContactsService]
})
export class BatAccountsdetailsModule { }
