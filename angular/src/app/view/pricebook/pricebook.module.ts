import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { FormsModule } from "@angular/forms";
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { OrderModule } from 'ngx-order-pipe';
import { FieldValidation } from "../../services/validation.services";
import { pricebookrouting } from './pricebook.routing';
import { PricebookComponent } from './pricebook.component';
import { CreatePricebookModule } from '../../DirectivesView/createpricebook/createpricebook.module';
import { PriceBookService } from '../../services/pricebook.service';
import { SearchfiltPipe } from './searchfilt.pipe';
import { Loginservices } from '../../services/login.services';



@NgModule({
  imports: [
    CommonModule,
    pricebookrouting,
    BsDropdownModule,
    ModalModule,
    FormsModule,
    NgxSpinnerModule,
    CreatePricebookModule,
    OrderModule
  ],
  declarations: [PricebookComponent, SearchfiltPipe],
  exports: [SearchfiltPipe],
  providers: [PriceBookService, NgxSpinnerService, FieldValidation,Loginservices]
})
export class pricebookModule{

}