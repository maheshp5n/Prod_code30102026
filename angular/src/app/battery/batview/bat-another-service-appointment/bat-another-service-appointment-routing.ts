import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { BatAnotherServiceAppointmentComponent } from "./bat-another-service-appointment.component";

const routes:Routes=[{
    path:'',component:BatAnotherServiceAppointmentComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class BatAnotherServiceAppointmentRouting{
}
