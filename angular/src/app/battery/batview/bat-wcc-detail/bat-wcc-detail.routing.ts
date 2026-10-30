import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { BatWccDetailComponent } from "./bat-wcc-detail.component";

const routes: Routes = [
    {
        path: '',
        component: BatWccDetailComponent
    }
]
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BatWCCDetailsRouting{

}