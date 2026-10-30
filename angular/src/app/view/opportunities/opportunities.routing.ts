import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { OpportunitiesComponent } from './opportunities.component';

const routes: Routes = [
    {
        path: '', component: OpportunitiesComponent
    }
]
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class OpportunitiesRouting { }