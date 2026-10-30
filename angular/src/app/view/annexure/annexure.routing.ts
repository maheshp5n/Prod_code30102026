import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { AnnexureComponent } from './annexure.component';

export const routes: Routes = [{
  path: '', component: AnnexureComponent
}];
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AnnexureRouting { }
