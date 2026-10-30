import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { BatWccCustomerComponent } from "./bat-wcc-customer.component";

const routes:Routes=[{
    path:'',component:BatWccCustomerComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class BatWccCustomerRouting{

}