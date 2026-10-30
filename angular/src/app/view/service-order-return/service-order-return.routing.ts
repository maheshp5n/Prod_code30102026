
import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { ServiceOrderReturnComponent } from "./service-order-return.component";

const routes: Routes = [
    {
        path: '', component: ServiceOrderReturnComponent
    }
]
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ServiceOrderReturnRouting{

}