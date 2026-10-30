import { RouterModule } from '@angular/router';
import { Routes } from "@angular/router";
import { NgModule } from '@angular/core';
import { BatUserroleComponent } from './bat-userrole.component';

const routes: Routes = [{
    path: '',
    component: BatUserroleComponent
}]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BatUserroleRouting{}