
import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { LocationdetailsComponent } from './locationdetails.component';

const routes:Routes=[{
    path:'',component:LocationdetailsComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class locationdetailsRouting{

}