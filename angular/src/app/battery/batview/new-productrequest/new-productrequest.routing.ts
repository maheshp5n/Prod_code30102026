import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { Routes } from '@angular/router';
import { NewProductrequestComponent } from './new-productrequest.component';

const routes: Routes = [{
    path: '',
    component: NewProductrequestComponent
}];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class  BtNewProductReqRouting{ }
