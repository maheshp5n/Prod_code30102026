import {NgModule} from '@angular/core'
import { RouterModule,Routes } from '@angular/router';
import { ProductrequestdetailsComponent} from './productrequestdetails.component' 

const routs:Routes=[
    {
        path:'',
        component:ProductrequestdetailsComponent
    }
]
@NgModule({
    imports:[RouterModule.forChild(routs)],
    exports:[RouterModule]
})
export class ProductrequestRoutingmodule{}