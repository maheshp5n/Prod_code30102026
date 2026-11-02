import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ManagecomponentindetailsRouting } from './managecomponentindetails.routing';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ModalModule } from 'ngx-bootstrap/modal';
import { ManagecomponentindetailsComponent } from './managecomponentindetails.component';
import { AssetsService } from '../../services/assets.services';
import { CreateManagecomponentModule } from '../../DirectivesView/create-managecomponent/create-managecomponent.module';
import { GlobalserviceService } from '../../globalservice.service';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ManagecomponentindetailsRouting,
    NgxSpinnerModule,
    ModalModule.forRoot(),
    CreateManagecomponentModule
],
  declarations: [ ManagecomponentindetailsComponent ],
  providers: [ AssetsService, NgxSpinnerService,GlobalserviceService]
})
export class ManagecomponentindetailsModule { }
