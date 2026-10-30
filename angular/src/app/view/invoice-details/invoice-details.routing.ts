import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { InvoiceDetailsComponent } from './invoice-details.component';

const routes: Routes = [
    {
        path: '',
        component: InvoiceDetailsComponent
    }
]
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class InvoicesDetailRouting{
    
}