import { Routes, RouterModule } from "@angular/router";
import { CityComponent } from "./city.component";
import { NgModule } from "@angular/core";

const routes:Routes=[
    {
        path:'',component:CityComponent
    }
];
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class cityRouting{

}