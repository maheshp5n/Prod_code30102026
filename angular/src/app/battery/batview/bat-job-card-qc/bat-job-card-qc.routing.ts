import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { BatJobCardQcComponent } from "./bat-job-card-qc.component";

const routes :Routes = [
    {
        path:'',
        component:BatJobCardQcComponent
    }
]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class BatJobCardQCRouting{}