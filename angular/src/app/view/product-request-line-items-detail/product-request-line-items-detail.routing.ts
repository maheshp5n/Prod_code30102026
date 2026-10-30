import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { ProductRequestLineItemsDetailComponent } from './product-request-line-items-detail.component';

const routes:Routes=[
    {
        path:'',
        component:ProductRequestLineItemsDetailComponent
    }
]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class ProductRequestLineItemRouting{

}