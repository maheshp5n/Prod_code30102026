import { CommonModule } from '@angular/common';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from "@angular/core";
import { RouterModule } from '@angular/router';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { ActionHistoryComponent } from './action-history.component';

@NgModule({
    declarations:[ActionHistoryComponent],
    imports:[
        CommonModule,
        NgxSpinnerModule,
        RouterModule
    ],
    schemas:[CUSTOM_ELEMENTS_SCHEMA],
    providers:[NgxSpinnerService],
    exports:[ActionHistoryComponent]
})
export class ActionhistoryModule{}