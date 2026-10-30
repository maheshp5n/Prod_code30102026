import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { CasePreRegisComponent } from './case-pre-regis.component';

const routes:Routes=[
    {
        path:'',component:CasePreRegisComponent
    }
];
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class CasePreRegisRouting{

}