import { RouterModule } from '@angular/router';
import { Routes } from "@angular/router";
import { NgModule } from '@angular/core';
import { BatServiceresourceComponent } from './bat-serviceresource.component';

const routes: Routes = [{
    path: '',
    component: BatServiceresourceComponent
}]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BatServiceresourceRouting{

}