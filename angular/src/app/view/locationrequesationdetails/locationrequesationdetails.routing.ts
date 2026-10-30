import { Routes } from '@angular/router';
import { LocationrequesationdetailsComponent } from './locationrequesationdetails.component';
import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
const routes: Routes = [{
    path: '',
    component: LocationrequesationdetailsComponent
}];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class LocationrequesationdetailsRouting { }
