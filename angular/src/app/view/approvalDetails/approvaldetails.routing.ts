import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { ApprovaldetailsComponent } from './approvaldetails.component';

export const routes: Routes = [{
  path: '', component: ApprovaldetailsComponent
}];
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ApprovaldetailsRouting { }
