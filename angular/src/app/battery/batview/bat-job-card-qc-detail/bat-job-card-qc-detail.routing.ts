import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { BatJobCardQcDetailComponent } from "./bat-job-card-qc-detail.component";

const routes : Routes = [
    {
        path:'',
        component:BatJobCardQcDetailComponent
    }
]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class BatJobCardQcDetailRouting{}