import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { BatInvoiceComponent } from "./bat-invoice.component";


const routes:Routes=[
    {
        path:'',component:BatInvoiceComponent
    }
];
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class BatInvoiceRouting{

}