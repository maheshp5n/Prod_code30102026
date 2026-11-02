import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { NgSelectModule } from "@ng-select/ng-select";
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { BatReassignJobCardComponent } from "./bat-reassign-job-card.component";

@NgModule({
    imports:[
        FormsModule,
        CommonModule,
        BsDropdownModule
    ],
    declarations:[BatReassignJobCardComponent],
    exports:[BatReassignJobCardComponent],
    providers:[]
})
export class BatJobCardReAssignModule{}