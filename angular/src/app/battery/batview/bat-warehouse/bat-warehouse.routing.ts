import { RouterModule } from '@angular/router';
import { Routes } from "@angular/router";
import { NgModule } from '@angular/core';
import { BatWarehouseComponent } from './bat-warehouse.component';

const routes: Routes = [{
    path: '',
    component: BatWarehouseComponent
}]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BatWarehouseRouting{

}