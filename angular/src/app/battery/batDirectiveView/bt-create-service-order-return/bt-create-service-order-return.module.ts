import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { BtCreateServiceOrderReturnComponent } from "./bt-create-service-order-return.component";

@NgModule({
    imports:[
        CommonModule,
        FormsModule,
        RouterModule
    ],
    declarations:[BtCreateServiceOrderReturnComponent],
    exports:[BtCreateServiceOrderReturnComponent],
    providers:[]
    
})
export class BtCreateServiceOrderReturnModule{}