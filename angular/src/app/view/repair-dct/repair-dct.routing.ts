import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { RepairDCTComponent } from './repair-dct.component';
const routes: Routes = [
    {
        path: '', component: RepairDCTComponent
    }
]
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class RepairDCTRouting{

}