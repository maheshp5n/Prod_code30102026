import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { WorkorderComponent } from './workorder.component';

const routes: Routes = [
    {
        path: '', component: WorkorderComponent
    }
]
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class WorkorderRouting { }