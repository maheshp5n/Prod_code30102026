import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BatRmaDetailRouting } from './bat-rma-detail.routing';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { AlertModule } from 'ngx-bootstrap/alert';
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgSelectModule } from '@ng-select/ng-select';
import { BatRmaDetailComponent } from './bat-rma-detail.component';
import { BtActionhistoryModule } from '../../batDirectiveView/bt-action-history/bt-action-history.module';
import { PdfViewerModule } from 'ng2-pdf-viewer';



@NgModule({
  declarations: [BatRmaDetailComponent],
  imports: [
    CommonModule,
    FormsModule,
    BatRmaDetailRouting,
    NgxSpinnerModule,
    ModalModule,
    AlertModule,
    NgSelectModule,
    BtActionhistoryModule,
    ReactiveFormsModule,PdfViewerModule
  ],
  providers:[NgxSpinnerService]
})
export class BatRmaDetailModule { }
