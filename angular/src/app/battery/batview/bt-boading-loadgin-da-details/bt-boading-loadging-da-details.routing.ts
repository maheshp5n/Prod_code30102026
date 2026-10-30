
import { RouterModule } from '@angular/router';
import { Routes } from "@angular/router";
import { NgModule } from '@angular/core';
import { BtBoadingLoadginDaDetailsComponent } from './bt-boading-loadgin-da-details.component';

const routes: Routes = [{
    path: '',
    component: BtBoadingLoadginDaDetailsComponent
}]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BtboadingloadgingDetailsRouting{

}