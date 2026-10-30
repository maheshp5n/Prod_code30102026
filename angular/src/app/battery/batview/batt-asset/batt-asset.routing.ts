import { RouterModule } from '@angular/router';
import { Routes } from "@angular/router";
import { NgModule } from '@angular/core';
import { BattAssetComponent } from './batt-asset.component';

const routes: Routes = [{
    path: '',
    component: BattAssetComponent
}]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BatAssetReouting{
    
}