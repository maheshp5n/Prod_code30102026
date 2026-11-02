import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { Loginservices } from "../../services/login.services";
import { CreatepricebookComponent } from './createpricebook.component';
import { PriceBookService } from '../../services/pricebook.service';
import { RouterModule } from '@angular/router';



@NgModule({
    imports: [
      CommonModule,
      FormsModule,
      ModalModule,
      NgxSpinnerModule,
      RouterModule
      
    ],
    declarations: [CreatepricebookComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
    exports: [CreatepricebookComponent],
    providers: [NgxSpinnerService,PriceBookService, Loginservices]
  })
export class CreatePricebookModule{

}