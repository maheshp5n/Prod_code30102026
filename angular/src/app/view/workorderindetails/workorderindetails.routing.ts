import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { WorkorderindetailsComponent } from './workorderindetails.component';

const routes: Routes = [
    {
        path: '', component: WorkorderindetailsComponent
    }
]
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class workorderindetailsRouting { }