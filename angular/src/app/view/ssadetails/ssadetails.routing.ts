import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { SsadetailsComponent } from './ssadetails.component';


const routes:Routes=[{
    path:'',component:SsadetailsComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class SSADetailsRout{

}