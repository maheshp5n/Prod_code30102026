import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { UserprofileDetailsComponent } from './userprofile-details.component';


const routes:Routes=[{
    path:'',component:UserprofileDetailsComponent
}]
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class UserDetailsRouting{

}