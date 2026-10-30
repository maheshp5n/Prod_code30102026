import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { BatUserregistrationComponent } from "./bat-userregistration.component";
const routes:Routes=[
    {
        path:'',component:BatUserregistrationComponent
    }
]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})

export class BatUserregistrationRouting{}
