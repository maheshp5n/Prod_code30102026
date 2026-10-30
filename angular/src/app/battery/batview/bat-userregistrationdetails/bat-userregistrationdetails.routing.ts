import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { BatUserregistrationdetailsComponent } from "./bat-userregistrationdetails.component";


const routes:Routes=[{
    path:'',component:BatUserregistrationdetailsComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class BatUserRegistraionDetailsRouting{

}