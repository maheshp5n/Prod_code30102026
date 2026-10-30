import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { BatInvoiceDetailComponent } from "./bat-invoice-detail.component";

const routes: Routes = [
    {
        path: '',
        component: BatInvoiceDetailComponent
    }
]
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BatInvoicesDetailRouting{
    
}