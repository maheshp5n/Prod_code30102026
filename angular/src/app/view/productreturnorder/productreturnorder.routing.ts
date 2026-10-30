import { ProductreturnorderComponent } from "./productreturnorder.component";
import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';

const routes:Routes=[{
        path:'',
        component:ProductreturnorderComponent
    }]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class ProductreturnOrderRouting{}