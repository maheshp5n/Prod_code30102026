import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { BatPreRegistrationCaseComponent } from "./bat-pre-registration-case.component";

const routes:Routes=[
    {
        path:'',component:BatPreRegistrationCaseComponent
    }
];
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class BatPreRegistrationCaseRouting{

}