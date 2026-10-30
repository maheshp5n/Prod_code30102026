import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { BatLegalentityComponent } from "./bat-legalentity.component";

const routes:Routes=[
    {
        path:'',component:BatLegalentityComponent
    }
];
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})

export class BatLegalEntityRouting{}