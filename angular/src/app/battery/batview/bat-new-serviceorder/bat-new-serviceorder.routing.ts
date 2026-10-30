import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Routes } from "@angular/router";
import { BatNewServiceorderComponent } from './bat-new-serviceorder.component';

const routes: Routes = [{
    path: '',
    component: BatNewServiceorderComponent
}];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BatNewserviceOrderRouting{

}