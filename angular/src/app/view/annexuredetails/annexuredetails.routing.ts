import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { AnnexuredetailsComponent } from './annexuredetails.component';

export const routes: Routes = [{
  path: '', component: AnnexuredetailsComponent
}];
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AnnexuredetailsRouting { }
