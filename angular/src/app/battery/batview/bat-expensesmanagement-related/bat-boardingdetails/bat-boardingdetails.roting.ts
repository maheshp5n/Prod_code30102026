
import { RouterModule } from '@angular/router';
import { Routes } from "@angular/router";
import { NgModule } from '@angular/core';
import { BatBoardingdetailsComponent } from './bat-boardingdetails.component';

const routes: Routes = [{
    path: '',
    component:BatBoardingdetailsComponent
}]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BatBoardingdetailsRouting{

}