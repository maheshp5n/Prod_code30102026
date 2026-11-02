
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ContactcreateModule } from '../../DirectivesView/contactcreate/contactcreate.module';
import { ContactsService } from '../../services/contacts.services';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { AlertModule } from 'ngx-bootstrap/alert';
import { NgxSpinnerService, NgxSpinnerModule } from 'ngx-spinner';
import { PopupSuccessModule } from '../../DirectivesView/popupSuccess/popupSuccess.module';
import { Loginservices } from '../../services/login.services';
import { AccountService } from '../../services/accounts.services';
import { DetailsofallROuting } from './detailsofall.routing';
import { DetailsofallComponent } from './detailsofall.component';
import { caseService } from '../../services/caseService.service';
import { NgSelectModule } from '@ng-select/ng-select';
import { ProductReturnService } from '../../services/productreturnservice';
import { AddproductEntriesModule } from '../../DirectivesView/addproductlistpricebookntries/addproductlistpricebookntries.module';
import { PreRegCaseService } from '../../services/preregcaseservice.services';
import { LocationProductRequestService } from '../../services/locationproductrequest.service';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    DetailsofallROuting,
    ModalModule,
    BsDropdownModule,
    NgxSpinnerModule,
    ContactcreateModule,
    PopupSuccessModule,
    AlertModule,
    NgSelectModule,AddproductEntriesModule
  ],
  declarations: [DetailsofallComponent],
//   exports: [SearchfiltPipe],
  providers: [ContactsService, Loginservices, AccountService, NgxSpinnerService,
    caseService,ProductReturnService,PreRegCaseService,LocationProductRequestService]
})
export class DetailofallModule{

}