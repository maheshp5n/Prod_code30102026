import { RouterModule } from '@angular/router';
import { Routes } from "@angular/router";
import { NgModule } from '@angular/core';
import { BatServiceresourceDetailsComponent } from './bat-serviceresource-details.component';

const routes: Routes = [{
    path: '',
    component: BatServiceresourceDetailsComponent
}]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BatServiceresourceDetailsRouting{

}