import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { BatJobCardDetailComponent } from "./bat-job-card-detail.component";

const routes :Routes = [
    {
        path:'',
        component:BatJobCardDetailComponent
    }
]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class BatJobCardDetailRouting{}