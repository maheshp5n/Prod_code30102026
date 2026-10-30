import { RouterModule } from '@angular/router';
import { Routes } from "@angular/router";
import { NgModule } from '@angular/core';
import { BatViewAllSchDetailsComponent } from './bat-view-all-sch-details.component';

const routes: Routes = [{
    path: '',
    component: BatViewAllSchDetailsComponent
}]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BatViewAllSchDetailsRouting{}