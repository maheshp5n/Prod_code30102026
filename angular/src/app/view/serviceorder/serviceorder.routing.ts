import { ServiceorderComponent } from './serviceorder.component';
import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
const routes: Routes = [{
    path: '', component: ServiceorderComponent
}];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ServiceOrderRouting {

}
