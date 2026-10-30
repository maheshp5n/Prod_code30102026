import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { BtCreateNewServiceAppointmentComponent } from "./bt-create-new-service-appointment.component";

const routes: Routes = [
  {
    path: '', component: BtCreateNewServiceAppointmentComponent
  }
]
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BtCreateNewServiceApppointmentRouting { }
