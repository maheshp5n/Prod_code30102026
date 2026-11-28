import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { NgSelectModule } from "@ng-select/ng-select";
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerModule } from "ngx-spinner";
import { CustomerInventoryServices } from "../../../services/customerinventory.service";
import { BtCustomerInComponent } from "./bt-customer-in.component";

@NgModule({
    imports:[
        CommonModule,
        FormsModule,     
        BsDatepickerModule,
        NgSelectModule,
        NgxSpinnerModule
    ],
    exports:[BtCustomerInComponent],
    declarations:[BtCustomerInComponent],
    providers:[CustomerInventoryServices]
})
export class BtCustomerInventoryInModule{
    
}