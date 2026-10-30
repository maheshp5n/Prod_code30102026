import { Routes, RouterModule } from "@angular/router";
import { AccountsComponent } from "./accounts.component";
import { NgModule } from "@angular/core";

const routs:Routes=[
    {
        path:'',component:AccountsComponent
    }
]
@NgModule({
    imports:[RouterModule.forChild(routs)],
    exports:[RouterModule]
})

export class Accountsrouting{}