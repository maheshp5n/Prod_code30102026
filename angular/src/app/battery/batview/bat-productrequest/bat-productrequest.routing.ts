import { Routes, RouterModule } from "@angular/router";
import { NgModule } from '@angular/core';
import { BatProductrequestComponent } from "./bat-productrequest.component";

const routes:Routes=[
    {
        path:'',
        component:BatProductrequestComponent
    }
]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class BatProuductRequestRouting{}