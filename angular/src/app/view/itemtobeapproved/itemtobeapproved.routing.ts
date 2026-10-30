import { ItemtobeapprovedComponent } from "./itemtobeapproved.component";
import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';

const routs:Routes=[{
    path:'',
    component:ItemtobeapprovedComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routs)],
    exports:[RouterModule]
})
export class ItemtobeApprovedRouting{}