import { Routes, RouterModule } from "@angular/router";
import { ProductrequestComponent } from './productrequest.component';
import { NgModule } from '@angular/core';

const routes:Routes=[
    {
        path:'',
        component:ProductrequestComponent
    }
]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class ProductrequestRoutingModules{}