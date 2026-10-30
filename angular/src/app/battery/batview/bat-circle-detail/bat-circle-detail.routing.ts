import { RouterModule } from '@angular/router';
import { Routes } from "@angular/router";
import { NgModule } from '@angular/core';
import { BatCircleDetailComponent } from './bat-circle-detail.component';

const routes: Routes = [{
    path: '',
    component: BatCircleDetailComponent
}]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BatCircleDetailRouting{

}