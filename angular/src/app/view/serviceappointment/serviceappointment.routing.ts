import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { ServiceappointmentComponent } from './serviceappointment.component';

const routes: Routes = [
    {
        path: '', component: ServiceappointmentComponent
    }
]
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ServiceappointmentRouting { }