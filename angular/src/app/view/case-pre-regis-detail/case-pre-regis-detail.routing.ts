
import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { CasePreRegisDetailComponent } from './case-pre-regis-detail.component';

const routes:Routes=[
    {
        path:'',component:CasePreRegisDetailComponent
    }
];
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class PreRegisCaseDetailRouting{

}