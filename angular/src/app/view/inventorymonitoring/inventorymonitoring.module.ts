import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InventorymonitoringComponent } from './inventorymonitoring.component';
import { InventorymonitoringRouting } from './inventorymonitoring.routing';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { AlertModule, ModalModule, PaginationModule } from 'ngx-bootstrap';
import { GlobalSearchPipeModule } from '../../pipe/global-search-pipe.module';
import { StocksummaryComponent } from './stocksummary/stocksummary.component';
import { StocksComponent } from './stocks/stocks.component';
import { HresholdComponent } from './hreshold/hreshold.component';
import { NgSelectModule } from '@ng-select/ng-select';



@NgModule({
  declarations: [InventorymonitoringComponent, StocksummaryComponent, StocksComponent, HresholdComponent],
  imports: [
    CommonModule,
    FormsModule,
    InventorymonitoringRouting,
    NgxSpinnerModule,
    AlertModule,
    GlobalSearchPipeModule,
    ModalModule,
    NgSelectModule,
    PaginationModule
  ],
  providers:[NgxSpinnerService]
})
export class InventorymonitoringModule { }
