import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { NgSelectModule } from "@ng-select/ng-select";
import { ModalModule } from "ngx-bootstrap";
import { NgxSpinnerModule } from "ngx-spinner";
import { BatEditJobCardComponent } from "./bat-edit-job-card.component";

@NgModule({
    imports:[
        CommonModule,
        FormsModule,
        NgxSpinnerModule,
        NgSelectModule,
        ModalModule
    ],
    declarations:[BatEditJobCardComponent],
    exports:[BatEditJobCardComponent],
    providers:[]
})
export class BatEditJobCardModule{}