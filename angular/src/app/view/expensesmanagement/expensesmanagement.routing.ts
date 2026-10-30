import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { ExpensesmanagementComponent } from './expensesmanagement.component';

const routes: Routes = [{
    path: '', component: ExpensesmanagementComponent

}];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})

export class ExpensesmanagementRouting {

}
