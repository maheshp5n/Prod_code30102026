import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Routes } from '@angular/router';
import { NewbatproducttransferComponent } from './newbatproducttransfer.component';

const routes: Routes = [
    {
        path: '',
        component: NewbatproducttransferComponent
    }
];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class NewBatProductTransferRouting { }
