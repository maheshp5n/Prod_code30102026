import { RouterModule } from '@angular/router';
import { Routes } from "@angular/router";
import { NgModule } from '@angular/core';
import { BatCaseComponent } from './bat-case.component';

const routes: Routes = [{
    path: '',
    component:BatCaseComponent
}]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BatCaseRouting{}