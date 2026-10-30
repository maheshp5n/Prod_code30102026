import { Routes, RouterModule } from '@angular/router';
import { PricebookproductsdetailsComponent } from './pricebookproductsdetails.component';
import { NgModule } from '@angular/core';

const routes:Routes=[{
    path:'',component:PricebookproductsdetailsComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class PricebookproductsDetailsRouting{

}