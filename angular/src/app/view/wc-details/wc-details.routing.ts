import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { WcDetailsComponent } from './wc-details.component';

const routes: Routes = [
    {
        path: '',
        component: WcDetailsComponent
    }
]
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class WCDetailsRouting{

}