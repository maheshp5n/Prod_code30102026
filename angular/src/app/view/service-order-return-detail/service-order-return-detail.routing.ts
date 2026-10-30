import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { ServiceorderdetailsComponent } from '../serviceorderdetails/serviceorderdetails.component';
import { ServiceOrderReturnDetailComponent } from './service-order-return-detail.component';
const routes: Routes = [
    {
        path: '',
        component: ServiceOrderReturnDetailComponent
    }
];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ServiceOrderReturnDetailRouting{

}