import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { deliverychallanComponent } from './deliverychallan.component';

const routes: Routes = [
    {
        path: '', component: deliverychallanComponent
    }
]
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class deliverychallanRouting { }