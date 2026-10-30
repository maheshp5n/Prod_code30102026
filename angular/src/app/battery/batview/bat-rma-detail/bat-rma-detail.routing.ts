import { RouterModule } from '@angular/router';
import { Routes } from "@angular/router";
import { NgModule } from '@angular/core';
import { BatRmaDetailComponent } from './bat-rma-detail.component';

const routes: Routes = [{
    path: '',
    component: BatRmaDetailComponent
}]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BatRmaDetailRouting{

}