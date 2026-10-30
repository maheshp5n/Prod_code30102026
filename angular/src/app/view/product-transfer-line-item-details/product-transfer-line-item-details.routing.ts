import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { ProductTransferLineItemDetailsComponent } from './product-transfer-line-item-details.component';

const routes:Routes=[
    {
        path:'',
        component:ProductTransferLineItemDetailsComponent
    }
]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class ProductTransferLineItemDetailRoting{

}