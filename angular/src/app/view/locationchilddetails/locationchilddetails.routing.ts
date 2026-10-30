import { LocationchilddetailsComponent } from './locationchilddetails.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';


const routes: Routes = [{
    path: '',
    component: LocationchilddetailsComponent
}];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class LocationchilddetailsRouting { }