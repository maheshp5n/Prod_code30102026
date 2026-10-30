import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { ServiceappointmentindetailsComponent } from './serviceappointmentindetails.component';

const routes: Routes = [
    {
        path: '', component: ServiceappointmentindetailsComponent
    }
]
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ServiceappointmentindetailsRouting { }