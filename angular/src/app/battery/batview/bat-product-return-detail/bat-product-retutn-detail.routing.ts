import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import {BatProductReturnDetailComponent} from './bat-product-return-detail.component';
import {BatProductReturnDetailDetailsComponent} from './bat-product-return-detail-details/bat-product-return-detail-details.component';
import {BatProductReturnDetailRelatedComponent} from './bat-product-return-detail-related/bat-product-return-detail-related.component';

const routes:Routes=[
    {
        path:'',
        component:BatProductReturnDetailComponent,
        children:[{
            path:'details',
            component:BatProductReturnDetailDetailsComponent
        },{
            path:'related',
            component:BatProductReturnDetailRelatedComponent
        },
        {
            path:'',redirectTo:'details', pathMatch : 'full'
        }]
    }
]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class BatProductReturnDetailRouting{}