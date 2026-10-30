import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { CommercialPageDetailComponent } from './commercial-page-detail.component';



const routes:Routes=[{
    path:'',component:CommercialPageDetailComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class commercialPageDetailRouting{

}