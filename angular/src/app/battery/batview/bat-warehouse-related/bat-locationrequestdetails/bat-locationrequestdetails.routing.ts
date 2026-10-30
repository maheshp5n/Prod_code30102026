import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { BatLocationrequestdetailsComponent } from "./bat-locationrequestdetails.component";

const routes:Routes=[{
    path:'',component:BatLocationrequestdetailsComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class BatLocationrequestdetailsRouting{

}