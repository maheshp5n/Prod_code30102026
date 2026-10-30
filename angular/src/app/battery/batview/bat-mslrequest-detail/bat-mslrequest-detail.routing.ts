import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { BatMslrequestDetailComponent } from "./bat-mslrequest-detail.component";

const routes:Routes=[{
    path:'',component:BatMslrequestDetailComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class BatMslrequestDetailRouting{

}
