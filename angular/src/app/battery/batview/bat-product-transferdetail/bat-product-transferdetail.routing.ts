import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { BatProductTransferDetailsComponent } from './bat-product-transfer-details/bat-product-transfer-details.component';
import { BatProductTransferRelatedComponent } from './bat-product-transfer-related/bat-product-transfer-related.component';
import { BatProductTransferdetailComponent } from './bat-product-transferdetail.component';

const routs:Routes=[
    {
        path:'',
        component:BatProductTransferdetailComponent,
        children:[
            {
                path:'details',
                component:BatProductTransferDetailsComponent
            },
            {
                path:'related',
                component:BatProductTransferRelatedComponent
            },
            {
                path:'',redirectTo:'details',pathMatch:'full'
            }
        ]
    }
]
@NgModule({
    imports:[RouterModule.forChild(routs)],
    exports:[RouterModule]
})
export class BatProducttransferDetailsRouting{}