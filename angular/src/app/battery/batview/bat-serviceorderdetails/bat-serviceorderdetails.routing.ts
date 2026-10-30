import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { BatServiceorderdetailsComponent } from './bat-serviceorderdetails.component';
const routes: Routes = [
    {
        path: '',
        component: BatServiceorderdetailsComponent
    }
];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})

export class BatServiceOrderDetailsRouting { }
