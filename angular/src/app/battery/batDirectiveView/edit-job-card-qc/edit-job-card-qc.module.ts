import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { NgSelectModule } from "@ng-select/ng-select";
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerModule } from "ngx-spinner";
import { EditJobCardQcComponent } from "./edit-job-card-qc.component";

@NgModule({
    imports:[
        CommonModule,
        FormsModule,
        NgxSpinnerModule,
        BsDatepickerModule,
        NgSelectModule
    ],
    declarations:[EditJobCardQcComponent],
    exports:[EditJobCardQcComponent],
    providers:[]
})
export class EditJobCardQCModule{}