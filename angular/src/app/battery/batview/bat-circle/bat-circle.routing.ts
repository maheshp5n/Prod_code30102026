import { RouterModule } from '@angular/router';
import { Routes } from "@angular/router";
import { NgModule } from '@angular/core';
import { BatCircleComponent } from './bat-circle.component';

const routes: Routes = [{
    path: '',
    component: BatCircleComponent
}]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BatCircleRouting{

}