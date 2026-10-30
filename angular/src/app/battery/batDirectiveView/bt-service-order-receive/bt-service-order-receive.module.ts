import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { BtServiceOrderReceiveComponent } from "./bt-service-order-receive.component";

@NgModule({
    imports: [CommonModule,
        FormsModule,
    ],
    exports: [BtServiceOrderReceiveComponent],
    declarations: [BtServiceOrderReceiveComponent],
    providers: []
})
export class BtServiceOrderReceiveModule { }