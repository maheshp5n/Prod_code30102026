

import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { PricebookdetailsComponent } from './pricebookdetails.component';

const routes:Routes=[{
    path:'',component:PricebookdetailsComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class pricebookDetailRoting{

}