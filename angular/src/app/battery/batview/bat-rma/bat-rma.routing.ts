import { RouterModule } from '@angular/router';
import { Routes } from "@angular/router";
import { NgModule } from '@angular/core';
import { BatRmaComponent } from './bat-rma.component';

const routes: Routes = [{
    path: '',
    component: BatRmaComponent
}]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BatRmaRouting{

}