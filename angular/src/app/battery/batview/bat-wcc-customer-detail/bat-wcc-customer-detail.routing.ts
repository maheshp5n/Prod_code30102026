import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { BatWccCustomerDetailComponent } from "./bat-wcc-customer-detail.component";

const routes:Routes=[{
    path:'',component:BatWccCustomerDetailComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class BatWccCustomerDetailRouting{

}