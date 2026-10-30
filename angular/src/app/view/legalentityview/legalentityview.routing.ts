import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { LegalentityviewComponent } from "./legalentityview.component";

const routes:Routes=[{
    path:'',component:LegalentityviewComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class LigalEntityViewRouting{

}