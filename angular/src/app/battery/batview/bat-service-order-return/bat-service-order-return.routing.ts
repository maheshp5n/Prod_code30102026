import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { BatServiceOrderReturnComponent } from "./bat-service-order-return.component";

const routes:Routes=[{
    path:'',
    component:BatServiceOrderReturnComponent
}];
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class BatServiceOrderReturnRouting{
    
}