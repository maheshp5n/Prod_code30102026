
import { RouterModule } from '@angular/router';
import { Routes } from "@angular/router";
import { NgModule } from '@angular/core';
import { BatSAExpensedetailsComponent } from './bat-saexpensedetails.component';

const routes: Routes = [{
    path: '',
    component:BatSAExpensedetailsComponent
}]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BatSAExpensedetailsRouting{

}