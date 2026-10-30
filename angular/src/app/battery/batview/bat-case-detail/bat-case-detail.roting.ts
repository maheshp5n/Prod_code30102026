
import { RouterModule } from '@angular/router';
import { Routes } from "@angular/router";
import { NgModule } from '@angular/core';
import { BatCaseDetailComponent } from './bat-case-detail.component';

const routes: Routes = [{
    path: '',
    component:BatCaseDetailComponent
}]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BatCaseDetailRouting{

}