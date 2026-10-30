import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { VendorServiceAppointmentDetailComponent } from "./vendor-service-appointment-detail.component";
const routes:Routes=[
    {
        path:'',component:VendorServiceAppointmentDetailComponent
    }
]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})

export class VendorServiceAppointmentDetailRouting{}
