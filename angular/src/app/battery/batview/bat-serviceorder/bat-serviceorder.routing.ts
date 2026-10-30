import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { BatServiceorderComponent } from './bat-serviceorder.component';
const routes: Routes = [{
    path: '', component: BatServiceorderComponent
}];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BatServiceOrderRouting {

}
