import { Routes, RouterModule } from "@angular/router";
import { RoledetailsComponent } from './roledetails.component';
import { NgModule } from '@angular/core';

const routes:Routes=[
    {
        path:'',
        component:RoledetailsComponent
    }
]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class RoledetailsRouting{}