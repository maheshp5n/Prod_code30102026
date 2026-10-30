import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { BatCommercialComponent } from './bat-commercial.component';

const routes: Routes = [
    {
        path: '', component: BatCommercialComponent
    }
];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BatCommercialPageRouting{

}