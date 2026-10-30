import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import {ReportwizardDetailsComponent} from "./reportwizard-details.component"

const routes:Routes=[
    {
        path:'',
        component:ReportwizardDetailsComponent
    }
]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class ReportwizardDetailsRouting{}