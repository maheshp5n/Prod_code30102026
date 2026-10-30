import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { BatServiceappointmentdetailsComponent } from "./bat-serviceappointmentdetails.component";

const routes: Routes = [
    {
        path: '', component: BatServiceappointmentdetailsComponent
    }
]
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BatServiceappointmentindetailsRouting { }