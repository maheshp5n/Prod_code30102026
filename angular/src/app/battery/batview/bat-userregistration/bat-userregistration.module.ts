import { NgModule } from "@angular/core";
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Loginservices } from '../../../services/login.services';
import { AccountService } from '../../../services/accounts.services';
import { NgxSpinnerModule } from 'ngx-spinner';
import { AlertModule, ModalModule, BsDropdownModule } from 'ngx-bootstrap';
import { NgSelectModule } from '@ng-select/ng-select';
import {createlocationModule} from '../../../DirectivesView/create-location/create-location.module';
import { BatUserregistrationRouting } from "./bat-userregistration.routing";
import { BatUserregistrationComponent } from "./bat-userregistration.component";
import { BtCreatewarehouseModule } from "../../batDirectiveView/bt-createwarehouse/bt-createwarehouse.module";


@NgModule({
    imports:[
        CommonModule,
        BatUserregistrationRouting,
        FormsModule,
        NgxSpinnerModule,
        AlertModule,
        ModalModule,
        NgSelectModule,
        BsDropdownModule,
        createlocationModule,
        BtCreatewarehouseModule
    ],
    declarations:[BatUserregistrationComponent],
    providers:[Loginservices,AccountService]
})
export class BatUserregistrationModule{}