
import { RouterModule } from '@angular/router';
import { Routes } from "@angular/router";
import { NgModule } from '@angular/core';
import { BatLodgingdetailsComponent } from './bat-lodgingdetails.component';

const routes: Routes = [{
    path: '',
    component:BatLodgingdetailsComponent
}]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BatLodgingdetailsRouting{

}