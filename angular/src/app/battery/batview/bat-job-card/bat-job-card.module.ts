import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { BsDropdownModule } from "ngx-bootstrap";
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { BatJobCardComponent } from "./bat-job-card.component";
import { BtJobCardRouting } from "./bat-job-card.routing";

@NgModule({
    imports:[
        CommonModule,
        FormsModule,
        BtJobCardRouting,
        NgxSpinnerModule,
        BsDropdownModule
    ],
    declarations:[BatJobCardComponent],
    exports:[BatJobCardComponent],
    providers:[]
})
export class BatJobCardModule{}