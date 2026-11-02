import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgxSpinnerModule } from "ngx-spinner";
import { BatEditJobCardModule } from "../../batDirectiveView/bat-edit-job-card/bat-edit-job-card-module";
import { BatJobCardReAssignModule } from "../../batDirectiveView/bat-reassign-job-card/bat-reassign-job-card.module";
import { BatJobCardDetailComponent } from "./bat-job-card-detail.component";
import { BatJobCardDetailRouting } from "./bat-job-card-detail.routing";

@NgModule({
    imports:[
        CommonModule,
        FormsModule,
        BatJobCardDetailRouting,
        NgxSpinnerModule,
        ModalModule,
        BatEditJobCardModule,
        BatJobCardReAssignModule
    ],
    declarations:[BatJobCardDetailComponent],
    providers:[]
})
export class BatJobCardDetailModule{}