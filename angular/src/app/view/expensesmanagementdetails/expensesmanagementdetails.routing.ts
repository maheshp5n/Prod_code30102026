import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { ExpensesmanagementdetailsComponent } from './expensesmanagementdetails.component';

const routes: Routes = [
    {
        path: '',
        component: ExpensesmanagementdetailsComponent
    }
];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ExpensesmanagementDetailsRouting { }
