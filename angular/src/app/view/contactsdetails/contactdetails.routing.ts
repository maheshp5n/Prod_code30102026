import { Routes, RouterModule } from '@angular/router';
import { ContactsdetailsComponent } from './contactsdetails.component';
import { NgModule } from '@angular/core';

const routes: Routes = [
    {
        path: '',
        component: ContactsdetailsComponent
    }
];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ContactDetailsRouting { }
