import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { NgxSpinnerModule } from "ngx-spinner";
import { BatJobCardQcComponent } from "./bat-job-card-qc.component";
import { BatJobCardQCRouting } from "./bat-job-card-qc.routing";

@NgModule({
    imports:[
        CommonModule,
        FormsModule,
        BatJobCardQCRouting,
        NgxSpinnerModule
    ],
    declarations:[BatJobCardQcComponent],
    providers:[]
    
})
export class BatJobCardQcModule{}