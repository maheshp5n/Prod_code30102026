import { Routes, RouterModule } from "@angular/router";
import { LigalentityComponent } from "./ligalentity.component";
import { NgModule } from "@angular/core";


const routes:Routes=[
    {
        path:'',component:LigalentityComponent
    }
];
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})

export class LigalEntityRouting{}