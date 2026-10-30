import { Routes, RouterModule } from "@angular/router";
import { NgModule } from '@angular/core';
import { ProducttransferComponent } from './producttransfer.component';

const routes:Routes=[{
    path:'',
    component:ProducttransferComponent
}];
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class ProductTransferRouting{}