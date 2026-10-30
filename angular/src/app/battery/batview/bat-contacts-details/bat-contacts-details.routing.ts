import { RouterModule } from '@angular/router';
import { Routes } from "@angular/router";
import { NgModule } from '@angular/core';
import { BatContactsDetailsComponent } from './bat-contacts-details.component';

const routes: Routes = [{
    path: '',
    component: BatContactsDetailsComponent
}]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BatContactsDetailsRouting{

}