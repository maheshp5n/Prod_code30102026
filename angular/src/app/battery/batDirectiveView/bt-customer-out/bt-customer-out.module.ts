import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { BsDatepickerModule } from "ngx-bootstrap";
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { NgxSpinner } from "ngx-spinner/lib/ngx-spinner.enum";
import { BtCustomerOutComponent } from "./bt-customer-out.component";

@NgModule({
    imports:[
        CommonModule,
        FormsModule,
        BsDatepickerModule,
        NgxSpinnerModule,
        
    ],
    exports:[BtCustomerOutComponent],
    declarations:[BtCustomerOutComponent],
    providers:[NgxSpinnerService]
})
export class BtCustmerInventoryOutModule{
    
}