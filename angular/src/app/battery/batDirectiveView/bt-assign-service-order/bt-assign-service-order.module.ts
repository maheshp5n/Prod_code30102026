import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { NgxSpinnerModule } from "ngx-spinner";
import { BtAssignServiceOrderComponent } from "./bt-assign-service-order.component";

@NgModule({
    imports:[
        CommonModule,
        FormsModule,
        NgxSpinnerModule
    ],
    declarations:[BtAssignServiceOrderComponent],
    exports:[BtAssignServiceOrderComponent],
    providers:[]
})
export class BtAssignServiceOrderModule{}