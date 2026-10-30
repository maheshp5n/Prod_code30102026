import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Routes } from "@angular/router";
import { NewserviceorderComponent } from './newserviceorder.component';

const routes: Routes = [{
    path: '',
    component: NewserviceorderComponent
}];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class NewserviceOrderRouting{

}