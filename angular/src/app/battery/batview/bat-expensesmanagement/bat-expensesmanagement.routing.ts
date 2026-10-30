import { RouterModule } from '@angular/router';
import { Routes } from "@angular/router";
import { NgModule } from '@angular/core';
import { BatExpensesmanagementComponent } from './bat-expensesmanagement.component';

const routes: Routes = [{
    path: '',
    component: BatExpensesmanagementComponent
}]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BatExpensesmanagementRouting{

}