import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { BoadingDaDetailsComponent } from './boading-da-details.component';

const routes:Routes=[
    {
        path:'',component:BoadingDaDetailsComponent
    }
];
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class boadingDetailsRouting{

}