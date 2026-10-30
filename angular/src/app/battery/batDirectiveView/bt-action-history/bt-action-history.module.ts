import { CommonModule } from '@angular/common';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from "@angular/core";
import { RouterModule } from '@angular/router';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { BtActionHistoryComponent } from './bt-action-history.component';

@NgModule({
    declarations:[BtActionHistoryComponent],
    imports:[
        CommonModule,
        NgxSpinnerModule,
        RouterModule
    ],
    schemas:[CUSTOM_ELEMENTS_SCHEMA],
    providers:[NgxSpinnerService],
    exports:[BtActionHistoryComponent]
})
export class BtActionhistoryModule{}