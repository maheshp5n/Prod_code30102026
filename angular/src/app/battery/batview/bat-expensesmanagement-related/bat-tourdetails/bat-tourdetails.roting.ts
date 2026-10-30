
import { RouterModule } from '@angular/router';
import { Routes } from "@angular/router";
import { NgModule } from '@angular/core';
import { BatTourdetailsComponent } from './bat-tourdetails.component';

const routes: Routes = [{
    path: '',
    component:BatTourdetailsComponent
}]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BatTourdetailsRouting{

}