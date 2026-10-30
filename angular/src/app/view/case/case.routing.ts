import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { CaseComponent } from './case.component';

const routes:Routes=[
    {
        path:'',component:CaseComponent
    }
];
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class caseRouting{

}