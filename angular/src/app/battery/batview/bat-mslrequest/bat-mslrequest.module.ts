// import { NgModule } from '@angular/core';
// import { CommonModule } from '@angular/common';
// import { BatMslrequestComponent } from './bat-mslrequest.component';
// import { FormsModule, ReactiveFormsModule } from '@angular/forms';
// import { BatMslrequestRouting } from './bat-mslrequest.routing';
// import { ModalModule } from 'ngx-bootstrap';



// @NgModule({
//   declarations: [BatMslrequestComponent],
//   imports: [
//     CommonModule,
//     FormsModule,ReactiveFormsModule,
//     BatMslrequestRouting,
//     ModalModule,
//   ]
// })
// export class BatMslrequestModule { }

import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ModalModule } from "ngx-bootstrap";
import { FormsModule } from "@angular/forms";
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ligalEntityService } from "../../../services/ligalentity.services";
import { NgxSpinnerModule, NgxSpinnerService } from "ngx-spinner";
import { Loginservices } from "../../../services/login.services";
import { OrderModule } from 'ngx-order-pipe';
import { createMslRqstModule } from '../../../DirectivesView/create-mslrequest/create-mslrequest.module';
import { mslRequestService } from '../../../services/mslRequest.services';
import { PagefilterService } from '../../../services/pagefilterservice';
import { BatMslrequestRouting } from "./bat-mslrequest.routing";
import { BatMslrequestComponent } from "./bat-mslrequest.component";
@NgModule({
    imports: [
      CommonModule,
      BsDropdownModule,
      ModalModule,
      FormsModule,
      NgxSpinnerModule,
      createMslRqstModule,OrderModule,
      BatMslrequestRouting
    ],
    declarations: [BatMslrequestComponent],
    // exports: [SearchfiltPipe],
    providers:[ligalEntityService,NgxSpinnerService,Loginservices,mslRequestService,PagefilterService]
  })
export class BatMslrequestModule{

}
