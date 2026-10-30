import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { BatUserProfileComponent } from "./bat-user-profile.component";

const routes:Routes=[{
    path:'',
    component:BatUserProfileComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class BatUserProfileRouting{}