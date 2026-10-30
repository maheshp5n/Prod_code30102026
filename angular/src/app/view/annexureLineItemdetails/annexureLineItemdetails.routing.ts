import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { AnnexureLineItemdetailsComponent } from './annexureLineItemdetails.component';

const routes: Routes = [
  {
    path: '',
    component: AnnexureLineItemdetailsComponent
  }
];
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AnnexureLineItemdetailsRouting { }
