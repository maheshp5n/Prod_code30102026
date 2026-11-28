import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgxSpinnerModule } from "ngx-spinner";
import { URLSafeModule } from "../../../pipe/url-safe-pipe.module";
import { EditJobCardQCModule } from "../../batDirectiveView/edit-job-card-qc/edit-job-card-qc.module";
import { BatJobCardQcDetailComponent } from "./bat-job-card-qc-detail.component";
import { BatJobCardQcDetailRouting } from "./bat-job-card-qc-detail.routing";

@NgModule({
    imports:[
        CommonModule,
        FormsModule,
        BatJobCardQcDetailRouting,
        NgxSpinnerModule,
        ModalModule,
        EditJobCardQCModule,
        URLSafeModule
    ],
    declarations:[BatJobCardQcDetailComponent],
    providers:[]
})
export class BatJobCardQCDetailModule{}