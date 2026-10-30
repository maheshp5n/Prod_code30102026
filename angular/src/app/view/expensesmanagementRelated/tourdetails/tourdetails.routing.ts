import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { TourdetailsComponent } from './tourdetails.component';

const routes: Routes = [
    {
        path: '',
        component: TourdetailsComponent
    }
];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class TourDetailsRouting { }
