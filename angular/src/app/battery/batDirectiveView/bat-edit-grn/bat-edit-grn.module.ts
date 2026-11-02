import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { NgxSpinnerModule} from "ngx-spinner";
import { BatEditGRNComponent } from "./bat-edit-grn.component";

@NgModule({
    imports:[
        CommonModule,
        FormsModule,
        BsDatepickerModule,
        NgxSpinnerModule
    ],
    declarations:[BatEditGRNComponent],
    exports : [BatEditGRNComponent],
    providers:[]
})
export class BatEditGRNModule{}