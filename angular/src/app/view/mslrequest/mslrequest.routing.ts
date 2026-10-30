import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { MslrequestComponent } from './mslrequest.component';


const routes:Routes=[
    {
        path:'',component:MslrequestComponent
    }
];
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class MslrequestRouting{

}