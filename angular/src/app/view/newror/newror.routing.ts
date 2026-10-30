import { NewrorComponent } from './newror.component';
import { RouterModule } from '@angular/router';
import { Routes } from '@angular/router';
import { NgModule } from '@angular/core';

const routes: Routes = [{
    path: '',
    component: NewrorComponent
}];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class NewrorRouting { }
