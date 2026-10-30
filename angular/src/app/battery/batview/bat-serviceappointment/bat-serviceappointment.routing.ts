import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { BatServiceappointmentComponent } from "./bat-serviceappointment.component";

const routes: Routes = [
    {
        path: '', component: BatServiceappointmentComponent
    }
]
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BatServiceappointmentRouting { }