import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { VendorServiceAppointmentComponent } from "./vendor-service-appointment.component";
const routes:Routes=[
    {
        path:'',component:VendorServiceAppointmentComponent
    }
]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})

export class VendorServiceAppointmentRouting{}
