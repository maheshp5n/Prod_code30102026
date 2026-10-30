import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SystemconfigurationparametersRouting } from './systemconfigurationparameters.routing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BsDropdownModule, ModalModule, BsDatepickerModule, TimepickerModule, AlertModule } from 'ngx-bootstrap';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { SystemconfigurationparametersComponent } from './systemconfigurationparameters.component';
import { SearchfiltPipe } from '../systemconfigurationparameters/searchfilt.pipe';
import { AssetsService } from '../../services/assets.services';
import { CreateSystemconfigurationparameterModule } from '../../DirectivesView/create-systemconfigurationparameter/create-systemconfigurationparameter.module';
import { SmsserverconfComponent } from './smsserverconf/smsserverconf.component';
import { MailserverconfComponent } from './mailserverconf/mailserverconf.component';
import { TemplateconfComponent } from './templateconf/templateconf.component';
import { AngularEditorModule } from '@kolkov/angular-editor'

@NgModule({
  imports: [
    CommonModule,
    SystemconfigurationparametersRouting,
    FormsModule,
    BsDropdownModule,
    ModalModule,
    NgxSpinnerModule,
    BsDatepickerModule,
    AngularEditorModule,
    AlertModule
  ],
  declarations: [ SystemconfigurationparametersComponent,SearchfiltPipe, SmsserverconfComponent, MailserverconfComponent, TemplateconfComponent ],
  exports: [SearchfiltPipe],
  providers: [AssetsService,NgxSpinnerService]  
})
export class SystemconfigurationparametersModule { }
