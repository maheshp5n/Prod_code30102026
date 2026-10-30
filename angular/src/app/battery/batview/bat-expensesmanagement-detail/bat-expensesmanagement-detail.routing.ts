import { RouterModule } from '@angular/router';
import { Routes } from "@angular/router";
import { NgModule } from '@angular/core';
import { BatExpensesmanagementDetailComponent } from './bat-expensesmanagement-detail.component';

const routes: Routes = [{
    path: '',
    component: BatExpensesmanagementDetailComponent
}]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BatExpensesmanagementDetailRouting{

}