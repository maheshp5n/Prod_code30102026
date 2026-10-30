import { CustomerinventoryComponent } from './customerinventory.component';
import { RouterModule } from '@angular/router';
import { Routes } from "@angular/router";
import { NgModule } from '@angular/core';

const routes: Routes = [{
    path: '',
    component: CustomerinventoryComponent
}]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class CustomerInventoryRouting {

}
