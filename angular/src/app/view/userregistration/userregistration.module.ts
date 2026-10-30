import { NgModule } from "@angular/core";
import { CommonModule } from '@angular/common';
import { UserregistrationRouting } from './userregistration.routing';
import { UserregistrationComponent } from './userregistration.component';
import { FormsModule } from '@angular/forms';
import { Loginservices } from '../../services/login.services';
import { AccountService } from '../../services/accounts.services';
//import { NgxSpinner } from 'ngx-spinner/lib/ngx-spinner.enum';
import { NgxSpinnerModule } from 'ngx-spinner';
import { AlertModule, ModalModule, BsDropdownModule } from 'ngx-bootstrap';
import { GlobalSearchPipeModule } from '../../pipe/global-search-pipe.module';
import { NgSelectModule } from '@ng-select/ng-select';
import {createlocationModule} from '../../DirectivesView/create-location/create-location.module';


@NgModule({
    imports:[
        CommonModule,
        UserregistrationRouting,
        FormsModule,
        NgxSpinnerModule,
        AlertModule,
        ModalModule,
        GlobalSearchPipeModule,
        NgSelectModule,
        BsDropdownModule,
        createlocationModule
    ],
    declarations:[UserregistrationComponent],
    providers:[Loginservices,AccountService]
})
export class UserregistrationModule{}