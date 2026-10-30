import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { BatAccountsComponent } from "./bat-accounts.component";

const routs:Routes=[
    {
        path:'',component:BatAccountsComponent
    }
]
@NgModule({
    imports:[RouterModule.forChild(routs)],
    exports:[RouterModule]
})

export class BatAccountsRouting{}