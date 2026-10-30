
import { RouterModule } from '@angular/router';
import { Routes } from "@angular/router";
import { NgModule } from '@angular/core';
import { BatLocalConveyancedetailsComponent } from './bat-local-conveyancedetails.component';

const routes: Routes = [{
    path: '',
    component:BatLocalConveyancedetailsComponent
}]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BatLocalConveyancedetailsRouting{

}