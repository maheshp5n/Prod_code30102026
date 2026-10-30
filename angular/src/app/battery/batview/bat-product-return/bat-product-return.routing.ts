import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { BatProductReturnComponent } from './bat-product-return.component';

const routes: Routes = [{
    path: '',
    component: BatProductReturnComponent
}]
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BatProductReturnRouting { }