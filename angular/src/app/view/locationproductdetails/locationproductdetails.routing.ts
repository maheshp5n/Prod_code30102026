import { NgModule } from '@angular/core';
import { Routes } from '@angular/router';
import { LocationproductdetailsComponent } from './locationproductdetails.component';
import { RouterModule } from '@angular/router';

const routes: Routes = [{
    path: '',
    component: LocationproductdetailsComponent
}];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class LocationproductdetailsRouting { }