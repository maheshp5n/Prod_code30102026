import { Routes, RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { LocationComponent } from './location.component';


const routes:Routes=[
    {
        path:'',component:LocationComponent
    }
];
@NgModule({
    imports:[RouterModule.forChild(routes)],
    exports:[RouterModule]
})

export class locationRouting{

}