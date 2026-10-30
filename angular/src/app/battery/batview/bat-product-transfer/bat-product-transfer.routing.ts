import { Routes, RouterModule } from "@angular/router";
import { NgModule } from '@angular/core';
import { BatProductTransferComponent } from "./bat-product-transfer.component";

const routes:Routes=[{
    path:'',
    component:BatProductTransferComponent
}];
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class BatProductTransferRouting{}