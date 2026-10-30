import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { BordingDaComponent } from './bording-da.component';

const routes:Routes=[
    {
        path:'',component:BordingDaComponent
    }
];
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})
export class BordingDARouting{

}