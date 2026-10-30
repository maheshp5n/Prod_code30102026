import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { CreateSiteComponent } from './create-site.component';

const routes:Routes=[
    {
        path:'',component:CreateSiteComponent
    }
];
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class createSiteRouting{

}