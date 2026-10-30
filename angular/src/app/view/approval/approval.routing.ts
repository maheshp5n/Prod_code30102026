import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { ApprovalComponent } from './approval.component';

export const routes: Routes = [{
  path: '', component: ApprovalComponent
}];
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ApprovalRouting { }
