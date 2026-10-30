import { NgModule } from '@angular/core';
import { RouterModule, Routes } from "@angular/router";
import { ObjecthistoryfullComponent } from './objecthistoryfull.component';

const routes:Routes=[{
    path:'',
    component:ObjecthistoryfullComponent
}];
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class ObjecthistoryfullRouting{}