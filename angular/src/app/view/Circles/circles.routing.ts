import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { CirclesComponent } from './circles.component';

const routes: Routes = [
    {
        path: '', component: CirclesComponent
    }
];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class CirclesRouting { }
