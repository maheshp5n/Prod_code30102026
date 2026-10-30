import { RouterModule } from '@angular/router';
import { Routes } from "@angular/router";
import { NgModule } from '@angular/core';
import { BatSiteComponent } from './bat-site.component';

const routes: Routes = [{
    path: '',
    component: BatSiteComponent
}]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BatSiteRouting{

}