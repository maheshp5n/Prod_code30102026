import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { BatServiceOrderReturnDetailsComponent } from "./bat-service-order-return-details.component";

const routes:Routes=[{
    path:'',
    component:BatServiceOrderReturnDetailsComponent
}]

@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class BatServiceOrderReturDetailsRouting{}