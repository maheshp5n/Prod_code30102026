import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { CommercialPageComponent } from './commercial-page.component';

const routes: Routes = [
    {
        path: '', component: CommercialPageComponent
    }
];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class CommercialPageRouting{

}