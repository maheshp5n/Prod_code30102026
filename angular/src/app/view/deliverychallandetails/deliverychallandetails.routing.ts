import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { deliverychallandetailsComponent } from './deliverychallandetails.component';

const routes:Routes=[
    {
        path:'',
        component:deliverychallandetailsComponent
    }
]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class deliverychallanDetailsRouting{}