import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { ProductsdetailsComponent } from './productsdetails.component';

const routes: Routes = [
    {
        path: '',
        component: ProductsdetailsComponent
    }
];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ProductsDetailsRouting { }
