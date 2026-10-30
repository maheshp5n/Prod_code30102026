
import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { MslrequestdetailsComponent } from './mslrequestdetails.component';

const routes:Routes=[{
    path:'',component:MslrequestdetailsComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class MslRequestDetailsRouting{

}