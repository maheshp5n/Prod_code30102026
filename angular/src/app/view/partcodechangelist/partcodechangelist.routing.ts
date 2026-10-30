import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { ApprovedlistComponent } from "./approvedlist/approvedlist.component";
import { PartcodechangelistComponent } from "./partcodechangelist.component";
import { PendinglistComponent } from "./pendinglist/pendinglist.component";

const path: Routes = [
    { path: '', redirectTo: 'pending' },
    {
        path: '',
        component: PartcodechangelistComponent,
        children: [
            { path: 'pending', component: PendinglistComponent },
            { path: 'approved', component: ApprovedlistComponent }
        ]
    }]
@NgModule({
    imports: [RouterModule.forChild(path)],
    exports: [RouterModule]
})

export class PartcodechangelistRouting { }