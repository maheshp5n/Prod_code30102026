import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ModalModule } from 'ngx-bootstrap';
import { IssuedcomponentsindetailsComponent } from './issuedcomponentsindetails.component';
import { AssetsService } from '../../services/assets.services';
import { IssuedcomponentsindetailsRouting } from './issuedcomponentsindetails.routing';
import { CreateIssuedcomponentModule } from '../../DirectivesView/create-issuedcomponent/create-issuedcomponent.module';
import { GlobalserviceService } from '../../globalservice.service';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IssuedcomponentsindetailsRouting,
    NgxSpinnerModule,
    ModalModule.forRoot(),
    CreateIssuedcomponentModule
],
  declarations: [ IssuedcomponentsindetailsComponent ],
  providers: [ AssetsService, NgxSpinnerService,GlobalserviceService]
})
export class IssuedcomponentsindetailsModule { }
