import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { BoardingdetailsComponent } from './boardingdetails.component';

const routes: Routes = [
    {
        path: '',
        component: BoardingdetailsComponent
    }
];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BoardingDetailsRouting { }
