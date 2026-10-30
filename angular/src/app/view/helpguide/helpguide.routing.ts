import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { HelpguideComponent } from "./helpguide.component";

const routes: Routes = [
    {
        path: '',
        component: HelpguideComponent
    }
];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class Helpguiderotuing
{ }