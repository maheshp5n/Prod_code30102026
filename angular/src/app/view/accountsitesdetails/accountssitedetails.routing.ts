import { AccountsindetailsComponent } from '../accountsindetails/accountsindetails.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
const routes: Routes = [
    {
        path: '',
        component: AccountsindetailsComponent
    }
];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AccountssitedetailsRouting { }
