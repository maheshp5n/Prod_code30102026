import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { BatLocationproductdetailsComponent } from "./bat-locationproductdetails.component";

const routes:Routes=[{
    path:'',component:BatLocationproductdetailsComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class BatLocationproductdetailsRouting{

}