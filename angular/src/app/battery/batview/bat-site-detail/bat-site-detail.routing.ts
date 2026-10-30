import { RouterModule } from '@angular/router';
import { Routes } from "@angular/router";
import { NgModule } from '@angular/core';
import { BatSiteDetailComponent } from './bat-site-detail.component';

const routes: Routes = [{
    path: '',
    component: BatSiteDetailComponent
}]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BatSiteDetailRouting{

}