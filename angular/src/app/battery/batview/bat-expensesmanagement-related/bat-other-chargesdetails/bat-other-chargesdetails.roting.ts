
import { RouterModule } from '@angular/router';
import { Routes } from "@angular/router";
import { NgModule } from '@angular/core';
import { BatOtherChargesdetailsComponent } from './bat-other-chargesdetails.component';

const routes: Routes = [{
    path: '',
    component:BatOtherChargesdetailsComponent
}]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BatOtherChargesdetailsRouting{

}