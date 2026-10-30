import { RouterModule } from '@angular/router';
import { Routes } from "@angular/router";
import { NgModule } from '@angular/core';
import { BatProductComponent } from './bat-product.component';

const routes: Routes = [{
    path: '',
    component: BatProductComponent
}]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BatProductRouting{

}