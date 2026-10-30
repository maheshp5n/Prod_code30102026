import { ProducttransferdetailsComponent } from "./producttransferdetails.component";
import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { ProducttransferdetailsdetailComponent } from './producttransferdetailsdetail/producttransferdetailsdetail.component';
import { ProducttransferdetailsreletedComponent } from './producttransferdetailsreleted/producttransferdetailsreleted.component';

const routs:Routes=[
    {
        path:'',
        component:ProducttransferdetailsComponent,
        children:[
            {
                path:'details',
                component:ProducttransferdetailsdetailComponent
            },
            {
                path:'releated',
                component:ProducttransferdetailsreletedComponent
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
export class ProducttransferDetailsRouting{}