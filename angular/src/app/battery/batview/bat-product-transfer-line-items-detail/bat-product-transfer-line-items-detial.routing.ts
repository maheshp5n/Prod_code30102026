import {NgModule} from '@angular/core'
import { RouterModule,Routes } from '@angular/router';
import { BatProductTransferComponent } from '../bat-product-transfer/bat-product-transfer.component';
import { BatProductTransferLineItemsDetailComponent } from './bat-product-transfer-line-items-detail.component';

const routes:Routes=[
    {
        path:'',
        component:BatProductTransferLineItemsDetailComponent
    }
]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class BatProductTransferLineItemsDetailRouting{}