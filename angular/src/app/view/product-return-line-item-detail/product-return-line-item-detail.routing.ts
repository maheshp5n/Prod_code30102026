import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { ProductReturnLineItemDetailComponent } from './product-return-line-item-detail.component';

const routes:Routes=[
    {
        path:'',
        component:ProductReturnLineItemDetailComponent
    }
]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class ProductReturnLineItemDetailRouting{
    
}