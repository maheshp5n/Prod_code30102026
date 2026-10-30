import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { BatProductRequestItemsDetailsComponent } from './bat-product-request-items-details.component';

const routes:Routes=[
    {
        path:'',
        component:BatProductRequestItemsDetailsComponent
    }
]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class BatProductRequestItemsRouting{

}