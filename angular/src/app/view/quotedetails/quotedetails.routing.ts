import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { QuotedetailsComponent } from './quotedetails.component';

const routes:Routes=[
    {
        path:'',
        component:QuotedetailsComponent
    }
]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class QuoteDetailsRouting{}