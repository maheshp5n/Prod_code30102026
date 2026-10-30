import {NgModule} from '@angular/core'
import { RouterModule,Routes } from '@angular/router';
import { BatProductrequestdetailComponent } from './bat-productrequestdetail.component';

const routs:Routes=[
    {
        path:'',
        component:BatProductrequestdetailComponent
    }
]
@NgModule({
    imports:[RouterModule.forChild(routs)],
    exports:[RouterModule]
})
export class BatProductrequestdetailRouting{}