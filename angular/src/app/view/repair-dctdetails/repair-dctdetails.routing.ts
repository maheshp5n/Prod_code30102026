import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { RepairDCTDetailsComponent } from './repair-dctdetails.component';


const routes:Routes=[{
    path:'',component:RepairDCTDetailsComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class RpairDetailsRout{

}