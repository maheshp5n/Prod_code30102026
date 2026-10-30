import { NewprComponent } from './newpr.component';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Routes } from "@angular/router";

const routes: Routes = [{
    path: '',
    component: NewprComponent
}];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class NewprRouting { }