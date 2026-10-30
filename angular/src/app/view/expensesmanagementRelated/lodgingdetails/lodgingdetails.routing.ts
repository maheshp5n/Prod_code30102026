import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { LodgingdetailsComponent } from './lodgingdetails.component';

const routes: Routes = [
    {
        path: '',
        component: LodgingdetailsComponent
    }
];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class LodgingDetailsRouting { }
