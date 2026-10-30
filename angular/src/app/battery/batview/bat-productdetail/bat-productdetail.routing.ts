import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { BatProductdetailComponent } from './bat-productdetail.component';

const routes: Routes = [
    {
        path: '',
        component: BatProductdetailComponent
    }
];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BatProductDetailRouting { }
