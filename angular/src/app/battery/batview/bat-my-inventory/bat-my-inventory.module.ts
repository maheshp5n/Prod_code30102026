import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BatMyInventoryRouting } from './bat-my-inventory.routing';
import { BatMyInventoryComponent } from './bat-my-inventory.component';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ModalModule } from 'ngx-bootstrap';
import { BatPartcodeChangeDirModule } from '../../batDirectiveView/bat-partcode-change-dir/bat-partcode-change-dir.module';
import { BatPartcodeStatusChangeDirModule } from '../../batDirectiveView/bat-partcode-status-change-dir/bat-partcode-status-change-dir.module';

@NgModule({
  declarations: [BatMyInventoryComponent],
  imports: [
    CommonModule,
    BatMyInventoryRouting,
    FormsModule,
    NgxSpinnerModule,
    ModalModule,
    BatPartcodeChangeDirModule,
    BatPartcodeStatusChangeDirModule
  ],
  providers : [NgxSpinnerService]
})
export class BatMyInventoryModule { }

