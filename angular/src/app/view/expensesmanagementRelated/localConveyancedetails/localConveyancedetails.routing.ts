import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { LocalConveyancedetailsComponent } from './localConveyancedetails.component';

const routes: Routes = [
    {
        path: '',
        component: LocalConveyancedetailsComponent
    }
];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class LocalConveyanceDetailsRouting { }
