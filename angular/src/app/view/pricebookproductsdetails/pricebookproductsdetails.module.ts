import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ModalModule, BsDropdownModule } from 'ngx-bootstrap';
import { CreatePricebookModule } from '../../DirectivesView/createpricebook/createpricebook.module';
import { AddproductEntriesModule } from '../../DirectivesView/addproductlistpricebookntries/addproductlistpricebookntries.module';
import { PriceBookService } from '../../services/pricebook.service';
import { Loginservices } from '../../services/login.services';
import { PricebookproductsdetailsComponent } from './pricebookproductsdetails.component';
import { PricebookproductsDetailsRouting } from './pricebookproductsdetails.routing';
import { ActionhistoryModule } from '../../DirectivesView/action-history/action-history.module';

@NgModule({
    imports: [
      CommonModule,
      FormsModule,
      PricebookproductsDetailsRouting,
      NgxSpinnerModule,
      ModalModule,
      BsDropdownModule,
      CreatePricebookModule,
      AddproductEntriesModule,
      ActionhistoryModule
      
  ],
    declarations: [PricebookproductsdetailsComponent],
    providers: [ NgxSpinnerService,PriceBookService,Loginservices]
  })
export class pricebookproductsDetailsModule{

}