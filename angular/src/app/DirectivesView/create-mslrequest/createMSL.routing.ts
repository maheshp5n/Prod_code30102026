import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { CreateMslrequestComponent } from './create-mslrequest.component';

const routes:Routes=[
    {
        path:'',component:CreateMslrequestComponent
    }
];
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class createMSLRouting{

}