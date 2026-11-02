import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { JobwarrantydetailsRouting } from './jobwarrantydetails.routing';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ModalModule } from 'ngx-bootstrap/modal';
import { CreateJobwarrantyModule } from '../../DirectivesView/create-jobwarranty/create-jobwarranty.module';
import { JobwarrantydetailsComponent } from './jobwarrantydetails.component';
import { AssetsService } from '../../services/assets.services';
import { GlobalserviceService } from '../../globalservice.service';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    JobwarrantydetailsRouting,
    NgxSpinnerModule,
    ModalModule.forRoot(), 
    CreateJobwarrantyModule   
],
  declarations: [ JobwarrantydetailsComponent ],
  providers: [ AssetsService, NgxSpinnerService,GlobalserviceService]
})
export class JobwarrantydetailsModule { }
