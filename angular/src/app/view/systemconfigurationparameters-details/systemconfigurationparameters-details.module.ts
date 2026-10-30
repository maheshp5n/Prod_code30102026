import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SystemconfigurationparametersDetailsRouting } from './systemconfigurationparameters-details.routing';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ModalModule } from 'ngx-bootstrap';
import { SystemconfigurationparametersDetailsComponent } from './systemconfigurationparameters-details.component';
import { AssetsService } from '../../services/assets.services';
import { CreateSystemconfigurationparameterModule } from '../../DirectivesView/create-systemconfigurationparameter/create-systemconfigurationparameter.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    SystemconfigurationparametersDetailsRouting,
    NgxSpinnerModule,
    ModalModule.forRoot(),
    CreateSystemconfigurationparameterModule
],
  declarations: [ SystemconfigurationparametersDetailsComponent ],
  providers: [ AssetsService, NgxSpinnerService]
})
export class SystemconfigurationparametersDetailsModule { }
