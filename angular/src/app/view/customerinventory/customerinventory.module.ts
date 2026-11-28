import { CustomerInventoryRouting } from './customerinventory.routing';
import { CustomerinventoryComponent } from './customerinventory.component';
import { GlobalSearchPipeModule } from '../../pipe/global-search-pipe.module';
import { NgxSpinnerModule } from 'ngx-spinner';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';



@NgModule({
  declarations: [CustomerinventoryComponent],
  imports: [
    CommonModule,
    CustomerInventoryRouting,
    GlobalSearchPipeModule,
    FormsModule,
    NgxSpinnerModule
  ]
})
export class CustomerinventoryModule { }
