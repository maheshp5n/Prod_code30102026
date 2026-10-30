import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Routes } from "@angular/router";
import { BatFsrServiceComponent } from './bat-fsr-service.component';

const routes: Routes = [{
    path: '',
    component: BatFsrServiceComponent
}];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BatFsrServiceRouting{

}