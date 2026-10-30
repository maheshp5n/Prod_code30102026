import { RouterModule } from '@angular/router';
import { Routes } from "@angular/router";
import { NgModule } from '@angular/core';
import { BatHelpguideComponent } from './bat-helpguide.component';

const routes: Routes = [{
    path: '',
    component: BatHelpguideComponent
}]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BatHelpguideRouting{

}