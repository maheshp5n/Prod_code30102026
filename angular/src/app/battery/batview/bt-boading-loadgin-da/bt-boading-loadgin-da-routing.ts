import { RouterModule } from '@angular/router';
import { Routes } from "@angular/router";
import { NgModule } from '@angular/core';
import { BtBoadingLoadginDaComponent } from './bt-boading-loadgin-da.component';

const routes: Routes = [{
    path: '',
    component: BtBoadingLoadginDaComponent
}]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BTBodingLodginDARouting{

}