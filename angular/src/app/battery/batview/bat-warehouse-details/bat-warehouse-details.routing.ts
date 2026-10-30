import { RouterModule } from '@angular/router';
import { Routes } from "@angular/router";
import { NgModule } from '@angular/core';
import { BatWarehouseDetailsComponent } from './bat-warehouse-details.component';

const routes: Routes = [{
    path: '',
    component: BatWarehouseDetailsComponent
}]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BatWarehouseDetailsRouting{

}