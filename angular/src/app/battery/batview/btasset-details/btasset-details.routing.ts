import { RouterModule } from '@angular/router';
import { Routes } from "@angular/router";
import { NgModule } from '@angular/core';
import { BTassetDetailsComponent } from './btasset-details.component';

const routes: Routes = [{
    path: '',
    component: BTassetDetailsComponent
}]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BtAssetDetailsRouting{

}