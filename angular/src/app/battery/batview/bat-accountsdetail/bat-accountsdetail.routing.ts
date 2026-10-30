import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { BatAccountsdetailComponent } from "./bat-accountsdetail.component";

const routes:Routes=[{
    path:'',component:BatAccountsdetailComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class BatAccountsdetailsRouting{}