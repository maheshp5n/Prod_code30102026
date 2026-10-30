import { RouterModule } from '@angular/router';
import { Routes } from "@angular/router";
import { NgModule } from '@angular/core';
import { BatContactsComponent } from './bat-contacts.component';

const routes: Routes = [{
    path: '',
    component: BatContactsComponent
}]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BatContactsRouting{

}