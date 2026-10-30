import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { BatLegalentitydetailComponent } from "./bat-legalentitydetail.component";

const routes:Routes=[{
    path:'',component:BatLegalentitydetailComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class BatLegalEntityViewRouting{

}