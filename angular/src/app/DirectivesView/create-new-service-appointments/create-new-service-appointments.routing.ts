import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { CreateNewServiceAppointmentsComponent } from './create-new-service-appointments.component';

const routes: Routes = [
  {
    path: '', component: CreateNewServiceAppointmentsComponent
  }
]
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CreateNewServiceApppointmentRouting { }
