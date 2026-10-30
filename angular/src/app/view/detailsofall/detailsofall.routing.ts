import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { DetailsofallComponent } from './detailsofall.component';

const routes: Routes = [
    {
        path: '', component: DetailsofallComponent
    }
];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export  class DetailsofallROuting{

}