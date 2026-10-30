import { AccountscasedetailsComponent } from './accountscasedetails.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
const routes: Routes = [{
    path: '',
    component: AccountscasedetailsComponent
}];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AccountscasedetailsRouting { }