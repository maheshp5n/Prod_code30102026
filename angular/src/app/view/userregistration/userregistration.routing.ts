import { Routes, RouterModule } from "@angular/router";
import { UserregistrationComponent } from "./userregistration.component";
import { NgModule } from "@angular/core";
const routes:Routes=[
    {
        path:'',component:UserregistrationComponent
    }
]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})

export class UserregistrationRouting{}
