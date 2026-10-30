import { ServiceorderdetailsComponent } from './serviceorderdetails.component';
import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
const routes: Routes = [
    {
        path: '',
        component: ServiceorderdetailsComponent
    }
];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})

export class ServiceOrderDetailsRouting { }
