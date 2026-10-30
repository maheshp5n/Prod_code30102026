import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { EngineerRatingComponent } from "./engineer-rating.component";


const routes:Routes=[{
    path:'',component:EngineerRatingComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class EngineerRatingRout{

}