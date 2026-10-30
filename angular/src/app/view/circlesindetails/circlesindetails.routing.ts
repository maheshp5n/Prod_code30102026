import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { CirclesindetailsComponent } from './circlesindetails.component';

const routes: Routes = [{
    path: '', component: CirclesindetailsComponent
}];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class CirclesindetailsRouting { }
