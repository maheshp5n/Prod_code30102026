import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { BatPreRegistrationCaseDetailComponent } from "./bat-pre-registration-case-detail.component";

const routes:Routes=[
    {
        path:'',component:BatPreRegistrationCaseDetailComponent
    }
];
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class BatPreRegistrationCaseDetailRouting{

}