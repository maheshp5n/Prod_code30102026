import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import {BatMyInventoryComponent} from "./bat-my-inventory.component";

const routes:Routes=[
    {
        path:'',component:BatMyInventoryComponent
    }
]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})

export class BatMyInventoryRouting{}