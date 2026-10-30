import { ProductreturnorderdetailsComponent } from "./productreturnorderdetails.component";
import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { ProductreturnorderdetailsdetailsComponent } from './productreturnorderdetailsdetails/productreturnorderdetailsdetails.component';
import { ProductreturnorderdetailsreletedComponent } from './productreturnorderdetailsreleted/productreturnorderdetailsreleted.component';

const routes:Routes=[
    {
        path:'',
        component:ProductreturnorderdetailsComponent,
        children:[{
            path:'details',
            component:ProductreturnorderdetailsdetailsComponent
        },{
            path:'releated',
            component:ProductreturnorderdetailsreletedComponent
        },
        {
            path:'',redirectTo:'details',pathMatch:'full'
        }]
    }
]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class ProductreturnorderdetailsRouting{}